/*
* LumaQQ - Java QQ Client
*
* Copyright (C) 2004 luma <stubma@163.com>
*
* This program is free software; you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation; either version 2 of the License, or
* (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program; if not, write to the Free Software
* Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
*/
package edu.tsinghua.lumaqq.ui;

import static edu.tsinghua.lumaqq.resource.Messages.*;

import java.net.InetSocketAddress;

import edu.tsinghua.lumaqq.ecore.face.Face;
import edu.tsinghua.lumaqq.qq.QQ;
import edu.tsinghua.lumaqq.qq.QQClient;
import edu.tsinghua.lumaqq.qq.QQPort;
import edu.tsinghua.lumaqq.qq.Util;
import edu.tsinghua.lumaqq.qq.events.QQEvent;
import edu.tsinghua.lumaqq.qq.net.IConnection;
import edu.tsinghua.lumaqq.qq.packets.ErrorPacket;
import edu.tsinghua.lumaqq.qq.packets.in.ClusterCommandReplyPacket;
import edu.tsinghua.lumaqq.qq.packets.in._05.RequestAgentReplyPacket;
import edu.tsinghua.lumaqq.qq.packets.in._05.RequestBeginReplyPacket;
import edu.tsinghua.lumaqq.qq.packets.in._05.TransferReplyPacket;
import edu.tsinghua.lumaqq.qq.packets.out.ClusterCommandPacket;
import edu.tsinghua.lumaqq.ui.helper.FaceRegistry;
import edu.tsinghua.lumaqq.ui.helper.FileSegmentor;
import edu.tsinghua.lumaqq.ui.helper.IMOutParser;
import edu.tsinghua.lumaqq.ui.helper.MessageIDGenerator;

/**
 * 发送一条群消息的流程封装类。发送一条消息不是一个非常easy的过程，必须要判断多种情况，比如
 * 是否含有自定义表情，等等，不同的内容，需要的流程不一样
 * 
 * @author luma
 */
public class ClusterIMDelegate {
    private QQClient client;
    private String originalMessage;
    private String message;
    private int totalFragments;
    private int currentFragment;
    private char messageId;
    private IIMSender sender;
    
    // 用在自定义表情时
    private int sessionId;
    private int[] sessionIdArray;
    private InetSocketAddress agentAddress;
    private IConnection port;
    
    private IMOutParser tool;
    
    // 当前发送第几个表情
    private int currentIndex;
    // 一共有几个表情，这个数目包含了重复的
    private int count;
    
    // 期待的请求中转服务器回复包的序号
    private char expectedSequence;
    
    /** 最多一次发送多少个自定义表情，这个值是QQ的限制，是否能超过这个限制，没试过 */
    private static final int MAX_CUSTOM_FACES = 20;
    
    // 临时变量，在每个表情发送时，这些变量会被初始化
    private int segmentCount;
    private int nextSegment;    
    private FileSegmentor segmentor;
    private int status;
    
    /** 表情还没开始发送 */
    private static final int BEFORE_SENDING = 0;
    /** 正在发送一个表情，这个表情还没发完 */
    private static final int SENDING_ONE = 1;
    /** 上次已经发完了一个表情 */
    private static final int JUST_FINISH = 2;
    
    public ClusterIMDelegate(QQClient client) {
        this.client = client;      
        tool = new IMOutParser();
        expectedSequence = 0;
    }
    
    /**
     * 开始一个消息发送流程
     */
    public void start() {
        if(originalMessage == null)
            return;
        
        // 对原始消息做预处理
        preprocess();
	    
	    // 通知发送开始
	    sender.notifyStart(originalMessage);   
	    // 执行相应的流程
	    if(tool.hasCustomFace())
	        doCustomFlow();
	    else
	        doNormalFlow();
    }
    
    /**
     * 发送结束
     */
    private void over() {
        resetVariables();
	    sender.notifyOver();
    }
    
    /**
     * 发送失败
     */
    private void fail(String msg) {
        resetVariables();
        sender.notifyFail(msg);
    }
    
    /**
     * 发送超时
     */
    private void timeout() {
        dispose();
        String temp = originalMessage;
        resetVariables();
        sender.notifyTimeout(temp);
    }
    
    /**
     * 重置某些状态变量
     */
    private void resetVariables() {
	    expectedSequence = 0;
	    currentIndex = 0;
        originalMessage = null;
    }
    
	/**
	 * 释放资源
	 */
	public void dispose() {
		if(port == null)
			return;
		
		client.releaseConnection(port.getId());
		port = null;
	}

	/**
	 * 发送下一个分片
	 * 
	 * @return
	 * 		true表示发送成功，false表示已经没有更多分片需要发送
	 */
	private boolean sendNextFragment() {
	    // 得到下一个要发送的分片
	    String fragment = getNextFragment();
	    if(fragment == null)
	        return false;
	    sender.send(fragment);
	    return true;
	}
    
    /**
     * 普通消息发送流程
     */
    private void doNormalFlow() {
        sendNextFragment();
    }
    
	/**
	 * @return
	 * 		下一个要发送的分片，如果为null，表示已经发送完成
	 */
	private String getNextFragment() {
	    if(++currentFragment == totalFragments)
	        return null;
	    
	    int start = QQ.QQ_MAX_SEND_IM * currentFragment;
	    int end = QQ.QQ_MAX_SEND_IM + start;
	    if(end > message.length())
	        end = message.length();
	    return message.substring(start, end);
	}

    /**
     * 自定义表情发送流程
     */
    private void doCustomFlow() {        
        if(tool.getDistinctFaceCount() > MAX_CUSTOM_FACES) {
            fail(text_too_more_faces);
            return;
        }
        // 建立初始连接
        if(port == null)
            agentAddress = selectOriginalAgent(); 
        createPort();
        
        // 发送请求包
        sendRequestAgent();
    }
    
    /**
     * 创建一个到中转服务器的连接
     */
    private void createPort() {
        // 如果这个连接已经建立了，直接返回
        if(port != null)
            return;
        // 首先查询是否存在这样的连接，有则复用，无则创建
        port = client.getConnection(agentAddress);
        if(port == null) {        
            try {
            	port = QQPort.CLUSTER_CUSTOM_FACE.create(client, agentAddress, null, true);
            } catch (Exception e) {
                // 失败，结束发送
                port = null;
                fail("Can't establish conection");
                return;
            }   
        } 
    }

    /**
     * 发送请求中转包，使用第一个自定义表情的相关信息
     */
    private void sendRequestAgent() {
        int id = tool.getId(currentIndex);
        FaceRegistry util = FaceRegistry.getInstance();
        Face face = util.getFace(id);
        byte[] md5 = Util.convertHexStringToByteNoSpace(face.getMd5());
        int imageLength = util.getFaceLength(face.getMd5());        
        expectedSequence = client.face_RequestAgent(sender.getSenderId(), imageLength, md5, face.getFilename(), port.getId());
    }

    /**
     * 随机选择一个初始代理服务器
     * 
     * @return
     * 		初始代理服务器的地址
     */
    private InetSocketAddress selectOriginalAgent() {        
        String ip = QQ.QQ_SERVER_GROUP_FILE[Util.random().nextInt(QQ.QQ_SERVER_GROUP_FILE.length)];
        return new InetSocketAddress(ip, 443);
    }
    
    /**
     * 处理QQ事件
     * 
     * @param e
     */
    public void delegateQQEvent(QQEvent e) {
    	switch(e.type) {
    		case QQEvent.IM_CLUSTER_SEND_EX_OK:
    		case QQEvent.IM_TEMP_CLUSTER_SEND_OK:
    		    processSendIMSuccess(e);
    			break;
			case QQEvent.IM_CLUSTER_SEND_EX_FAIL:
			case QQEvent.IM_TEMP_CLUSTER_SEND_FAIL:
			    processSendIMFail(e);
				break;
			case QQEvent.FILE_REQUEST_AGENT_REDIRECT:
			    processRequestAgentRedirect(e);
				break;
			case QQEvent.FILE_REQUEST_AGENT_OK:
			    processRequestAgentOK(e);
				break;
			case QQEvent.FILE_REQUEST_AGENT_FAIL:
			    processRequestAgentFail(e);
				break;
			case QQEvent.FILE_REQUEST_BEGIN_OK:
			    processRequestBeginSuccess(e);
				break;
			case QQEvent.FILE_TRANSFER_FACE_OK:
			    processTransferFaceSuccess(e);
				break;
			case QQEvent.ERROR_CONNECTION_BROKEN:
			    processConnectionBroken(e);
				break;
			case QQEvent.SYS_TIMEOUT:
				switch(e.operation) {
			        case QQ.QQ_CMD_CLUSTER_CMD:
					    processSendIMTimeOut(e);
			        	break;
				}
				break;
			case QQEvent.SYS_TIMEOUT_05:
			    switch(e.operation) {
			        case QQ.QQ_05_CMD_REQUEST_AGENT:
			        case QQ.QQ_05_CMD_REQUEST_BEGIN:
			        case QQ.QQ_05_CMD_TRANSFER:
			            timeout();
			        	break;
			    }
			    break;
    	}
    }
    
    /**
     * 处理连接被远端关闭事件
     * 
     * @param e
     */
    private void processConnectionBroken(QQEvent e) {
        ErrorPacket packet = (ErrorPacket)e.getSource();
        if(port != null && packet.connectionId.equals(port.getId()))
            dispose();
    }

    /**
     * 初始请求中转服务器失败事件
     * 
     * @param e
     */
    private void processRequestAgentFail(QQEvent e) {
        RequestAgentReplyPacket packet = (RequestAgentReplyPacket)e.getSource();
        if(expectedSequence == packet.getSequence())
            fail(packet.message);
    }

    /**
     * 处理发送表情成功事件
     * 
     * @param e
     */
    private void processTransferFaceSuccess(QQEvent e) {
        TransferReplyPacket packet = (TransferReplyPacket)e.getSource();
        if(expectedSequence == packet.getSequence()) {
            switch(status) {
                case BEFORE_SENDING:
                    startSendFace();
                    break;
                case JUST_FINISH:
                    sendNextFace();
                    status = BEFORE_SENDING;
                    break;
                case SENDING_ONE:
                    sendFaceFragment();
                    break;
            }
        }
    }
    
    /**
     * 发送表情分片
     */
    private void sendFaceFragment() {
        boolean needDelay = (segmentCount - nextSegment) > 5;
        for(int i = 0; nextSegment < segmentCount && i < 10; nextSegment++, i++) {
            // 这里为什么要delay呢，也是没有办法的办法。对于一些比较大的表情，QQ好像需要一次
            // 发送10个包，然后再等待服务器的回复，再发10个包...
            // 这个回复还非等不可，不然接收方接收的图片会乱掉，想了半天，这大概是目前能想到的
            // "最好"的办法了
            if(needDelay) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                }
            }
            expectedSequence = client.face_TransferData(sessionId, segmentor.getFragment(nextSegment), nextSegment == segmentCount - 1, port.getId());              
        }
        if(nextSegment >= segmentCount) {
            segmentor.close();
            segmentor = null;
            status = JUST_FINISH;
        } else
            status = SENDING_ONE;
    }

    /**
     * 发送下一个表情，如果没有了，则开始发送消息
     */
    private void sendNextFace() {
        currentIndex++;
        while(currentIndex < count && tool.isReferred(currentIndex))
            currentIndex++;
        
        if(currentIndex < count)
            sendRequestAgent();
        else {
            // 把自定义表情的内容替换成可发送的内容
	        message = tool.toProtocolFormat(sessionIdArray, agentAddress, client.getUser().getFileAgentKey());
	        initialFragmentStatus();            
            // 开始正式发送消息
            sendNextFragment();
        }   
        return;
    }

    /**
     * 开始发送第currentIndex个表情
     */
    private void startSendFace() {
        int id = tool.getId(currentIndex);
        FaceRegistry util = FaceRegistry.getInstance();
        String path = util.getFacePath(id);
        if(path == null) {
            fail("File isn't exist");
            return;
        }
        segmentor = new FileSegmentor(path, 1024);
        if(!segmentor.isLoadSuccess()) {
            fail("Can't open face file");
            return;
        }
        
        segmentCount = segmentor.getTotalFragments();
        nextSegment = 0;
        sendFaceFragment();
    }
    
    /**
     * 初始请求开始会话成功事件
     * 
     * @param e
     */
    private void processRequestBeginSuccess(QQEvent e) {
        RequestBeginReplyPacket packet = (RequestBeginReplyPacket)e.getSource();
        if(packet.sessionId != sessionId)
            return;
        
        int id = tool.getId(currentIndex);
        FaceRegistry util = FaceRegistry.getInstance();
        Face face = util.getFace(id);
        byte[] md5 = Util.convertHexStringToByteNoSpace(face.getMd5());
        int imageLength = util.getFaceLength(face.getMd5());
        
        expectedSequence = client.face_TransferInfo(sessionId, imageLength, face.getFilename(), md5, port.getId());
    }

    /**
     * 处理请求服务器成功事件
     * 
     * @param e
     */
    private void processRequestAgentOK(QQEvent e) {
        RequestAgentReplyPacket packet = (RequestAgentReplyPacket)e.getSource();
        if(packet.getSequence() != expectedSequence)
            return;
        
        // 得到会话id
        sessionId = packet.sessionId;        
        sessionIdArray[currentIndex] = sessionId;
        // 请求开始发送
        int id = tool.getId(currentIndex);
        FaceRegistry util = FaceRegistry.getInstance();
        Face face = util.getFace(id);
        byte[] md5 = Util.convertHexStringToByteNoSpace(face.getMd5());       
        
        // 非第一个表情不需要request begin包
        if(currentIndex > 0) {
            int imageLength = util.getFaceLength(face.getMd5());
            expectedSequence = client.face_TransferInfo(sessionId, imageLength, face.getFilename(), md5, port.getId());
        } else
            client.face_RequestSendBegin(sessionId, md5, port.getId());
    }

    /**
     * 处理重定向事件
     * 
     * @param e
     */
    private void processRequestAgentRedirect(QQEvent e) {        
        RequestAgentReplyPacket packet = (RequestAgentReplyPacket)e.getSource();
        if(packet.getSequence() != expectedSequence)
            return;
        
        // 关闭原来的连接，创建一个到重定向服务器的连接
        dispose();
        agentAddress = new InetSocketAddress(Util.convertIpToString(packet.redirectIp), packet.redirectPort);
        createPort();
        sendRequestAgent();
    }

    /**
     * 处理超时事件
     */
    private void processSendIMTimeOut(QQEvent e) {
        ClusterCommandPacket packet = (ClusterCommandPacket)e.getSource();
        if(packet.getClusterId() == sender.getSenderId()) {
	        switch(packet.getSubCommand()) {
	        	case QQ.QQ_CLUSTER_CMD_SEND_IM_EX:
	        	case QQ.QQ_CLUSTER_CMD_SEND_TEMP_IM:
	        	    timeout();
	        		break;
	        	default:
	        	    break;
	        }  
        }
    }

    /**
     * 处理消息发送失败事件
     * 
     * @param e
     */
    private void processSendIMFail(QQEvent e) {
		ClusterCommandReplyPacket packet = (ClusterCommandReplyPacket)e.getSource();
		if(packet.clusterId == sender.getSenderId())
		    fail(packet.errorMessage);
    }

    /**
     * 处理消息发送成功事件
     */
    public void processSendIMSuccess(QQEvent e) {
		ClusterCommandReplyPacket packet = (ClusterCommandReplyPacket)e.getSource();
		if(packet.clusterId == sender.getSenderId()) {
		    if(!sendNextFragment()) 
		    	over();
		}
    }
    
    /**
     * 初始化一些变量
     */
    private void initialFragmentStatus() {
	    // 计算分片数
	    totalFragments = (message.length() - 1) / QQ.QQ_MAX_SEND_IM + 1;
	    // 初始化其他变量
	    currentFragment = -1;
	    messageId = MessageIDGenerator.getNext();
    }

    /**
     * 对原始消息做预处理，主要是检查是否有自定义表情
     */
    private void preprocess() {
        tool.parseOutMessage(originalMessage);
        if(!tool.hasCustomFace()) {
            message = originalMessage;
            // 初始化变量
            initialFragmentStatus();
        } else
            initialCustomFaceStatus();
    }
    
    /**
     * 初始化自定义表情相关变量
     */
    private void initialCustomFaceStatus() {
        currentIndex = 0;
        count = tool.getFaceCount();
        status = BEFORE_SENDING;
        if(sessionIdArray == null || count > sessionIdArray.length)
            sessionIdArray = new int[count];
    }

    /**
     * @param originalMessage The originalMessage to set.
     */
    public void setOriginalMessage(String originalMessage) {
        this.originalMessage = originalMessage;
    }

    /**
     * @param sender The sender to set.
     */
    public void setSender(IIMSender sender) {
        this.sender = sender;
    }
    
    /**
     * @return Returns the currentFragment.
     */
    public int getCurrentFragment() {
        return currentFragment;
    }
    
    /**
     * @return Returns the messageId.
     */
    public char getMessageId() {
        return messageId;
    }
    
    /**
     * @return Returns the totalFragments.
     */
    public int getTotalFragments() {
        return totalFragments;
    }
}
