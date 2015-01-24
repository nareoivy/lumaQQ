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

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.LinkedList;
import java.util.Queue;

import edu.tsinghua.lumaqq.events.BaseQQListener;
import edu.tsinghua.lumaqq.qq.QQ;
import edu.tsinghua.lumaqq.qq.QQPort;
import edu.tsinghua.lumaqq.qq.events.QQEvent;
import edu.tsinghua.lumaqq.qq.net.IConnection;
import edu.tsinghua.lumaqq.qq.packets.ErrorPacket;
import edu.tsinghua.lumaqq.qq.packets.in.ReceiveIMPacket;
import edu.tsinghua.lumaqq.qq.packets.in._05.TransferReplyPacket;
import edu.tsinghua.lumaqq.ui.helper.FaceRegistry;
import edu.tsinghua.lumaqq.ui.helper.FileTool;
import edu.tsinghua.lumaqq.ui.helper.IMInParser;
import edu.tsinghua.lumaqq.ui.helper.ReceivedFaceSaver;

/**
 * 群贴图接收类，封装群贴图的接收过程
 * 
 * @author luma
 */
public class ClusterCustomFaceReceiver extends BaseQQListener {
    private Queue<ReceiveIMPacket> imQueue;
    private MainShell main;
    
    // 一些临时变量
    private ReceiveIMPacket im;
    private IMInParser parser;    
    private int count;
    private int currentIndex;
    private InetSocketAddress agentAddress;
    
    private IConnection conn;
    
    // 用在接收文件数据时
    private byte[] buffer;
    private int position;
    private int packetCount;
    
    public ClusterCustomFaceReceiver(MainShell main) {
        this.main = main;
        imQueue = new LinkedList<ReceiveIMPacket>();        
        parser = new IMInParser();
    }
    
    /**
     * 删除队列中第一个对象，这个方法必须在一个表情已经接收完毕之后调用
     */
    public synchronized ReceiveIMPacket removeFirst() {
        synchronized(imQueue) {
			return imQueue.poll();  
        }
    }
    
    /**
     * @return
     * 		队列中第一个消息包对象
     */
    public synchronized ReceiveIMPacket getClusterIM() {
        synchronized(imQueue) {
	        return imQueue.peek();        
        }
    }
    
    /**
     * 添加一个群消息对象到队列中
     * 
     * @param p
     */
    public void addClusterIM(ReceiveIMPacket p) {
        int size = 0;
        synchronized(imQueue) {
	        imQueue.offer(p);        
	        size = imQueue.size();
        }
        if(size == 1)
            start();
    }
    
    /**
     * 关闭连接 
     */
    private void dispose() {
        if(conn == null)
            return;
        
        main.getClient().releaseConnection(conn.getId());
        conn = null;
    }
    
    /**
     * 创建一个到中转服务器的连接
     * 
     * @return
     * 		true表示连接创建成功
     */
    private boolean createPort() {
        if(conn != null && conn.getRemoteAddress().equals(agentAddress))
            return true;
        if(conn != null)
            dispose();
        try {
        	conn = QQPort.CLUSTER_CUSTOM_FACE.create(main.getClient(), agentAddress, null, true);
        } catch (IOException e) {
            // 失败，结束发送
            conn = null;
            return false;
        }                
        return true;
    }
    
    /**
     * 开始接收的流程
     */
    public void start() {     
        boolean found = false;
        do {
            // 得到下一个要解析的消息
	        im = getClusterIM();
	        if(im == null)
	            return;
	        // 解析消息
	        parser.parseInMessage(im.clusterIM);
	        // 如果消息里面没有自定义表情，推回接收队列
	        // 如果有，但是自定义表情以前全部得到过，继续下一个
	        count = parser.getDistinctFaceCount();        
	        if(count <= 0)
	            pushBack();
	        else {
			    currentIndex = 0;
			    findNextFaceNeedToBeDownloaded();
			    if(currentIndex < count)
			        found = true;
			    else
			        pushBack();
	        }	        
        } while(!found);

        // 得到中转服务器地址，开始请求
        agentAddress = parser.getAgentAddress(currentIndex);
        if(createPort()) {
	        packetCount = 0;
	        main.getClient().face_Request(im.header.sender, parser.getSessionId(currentIndex), parser.getAgentKey(), conn.getId());            
        } else
            clear();
    }
    
    /**
     * 清空队列，返回 
     */
    private void clear() {
        synchronized(imQueue) {
            while(!imQueue.isEmpty())
                pushBack();
        }
        dispose();
    }
    
    /**
     * 推回一个自定义表情已经解析完毕的消息
     */
    private void pushBack() {
        ReceiveIMPacket p = removeFirst();
        p.clusterIM.faceResolved = true;
        p.clusterIM.message = parser.toDisplayFormat();
        main.getClient().addIncomingPacket(p, conn.getId());
    }
    
    /**
     * 调整currentIndex到一个没有下载过的表情上
     */
    private void findNextFaceNeedToBeDownloaded() {
        FaceRegistry util = FaceRegistry.getInstance();
        while(currentIndex < count) {
	        String md5 = FileTool.getNameExcludeExtension(parser.getFaceFileName(currentIndex));
	        if(util.hasFace(md5))
	            currentIndex++;            
	        else
	            break;
        }
    }
    
    @Override
    protected void OnQQEvent(QQEvent e) {
        switch(e.type) {
            case QQEvent.FILE_REQUEST_FACE_OK:
                processRequestFaceSuccess(e);	
            	break;
            case QQEvent.FILE_FACE_INFO_RECEIVED:
                processReceiveFaceInfo(e);
            	break;
            case QQEvent.FILE_FACE_DATA_RECEIVED:
                processReceiveFaceData(e);
            	break;
//            case QQEvent.FILE_REQUEST_BEGIN_OK:
//                processRequestBeginSuccess(e);
//            	break;
            case QQEvent.ERROR_CONNECTION_BROKEN:
                processConnectionBroken(e);	
            	break;
        }
    }

    /**
     * 处理收到表情文件数据事件
     * 
     * @param e
     */
    private void processReceiveFaceData(QQEvent e) {
        TransferReplyPacket p = (TransferReplyPacket)e.getSource();
        if(p.sessionId == parser.getSessionId(currentIndex)) {
            System.arraycopy(p.data, 0, buffer, position, p.data.length);
            position += p.data.length;
            
            packetCount++;
            if(position >= buffer.length) {
                // 保存文件
                String filename = parser.getFaceFileName(currentIndex);
                if(filename != null) {
                	String md5 = FileTool.getNameExcludeExtension(filename);
                	ReceivedFaceSaver saver = new ReceivedFaceSaver(buffer, 0, buffer.length, md5, filename);
                	if(saver.saveEntry())
                		FaceRegistry.getInstance().addReceivedFace(md5, filename);
                }
                buffer = null;
                // 发送回复
                main.getClient().face_ReplyData(p.sessionId, conn.getId());                
                // 继续得到其他表情
			    findNextFaceNeedToBeDownloaded();
			    if(currentIndex < count) {
	                // 得到中转服务器地址，开始请求
	                agentAddress = parser.getAgentAddress(currentIndex);
	                if(createPort()) {
		                packetCount = 0;
		                main.getClient().face_Request(im.header.sender, parser.getSessionId(currentIndex), parser.getAgentKey(), conn.getId());	                    
	                } else
	                    clear();
			    } else {
	                // 推回这个消息
	                pushBack();
	                // 继续分析其他消息
	                start();
			    }
            } else if(packetCount == 10) {
                packetCount = 0;
                main.getClient().face_ReplyData(p.sessionId, conn.getId());
            }
        }
    }

    /**
     * 处理远程连接断开事件
     * 
     * @param e
     */
    private void processConnectionBroken(QQEvent e) {
        ErrorPacket packet = (ErrorPacket)e.getSource();
        if(conn != null && conn.getId().equals(packet.connectionId)) {
            synchronized(imQueue) {
    	        imQueue.clear();            
            }
            dispose();
        }
    }

//    /**
//     * 处理请求开始成功事件
//     * 
//     * @param e
//     */
//    private void processRequestBeginSuccess(QQEvent e) {
//        RequestBeginReplyPacket packet = (RequestBeginReplyPacket)e.getSource();
//        if(packet.sessionId == parser.getSessionId(currentIndex))
//            main.getClient().requestData(packet.sessionId, portName);
//    }

    /**
     * 处理收到表情文件基本信息事件
     * 
     * @param e
     */
    private void processReceiveFaceInfo(QQEvent e) {
        TransferReplyPacket packet = (TransferReplyPacket)e.getSource();
        if(packet.sessionId == parser.getSessionId(currentIndex)) {
            // 保存文件基本信息
            buffer = new byte[packet.imageLength];
            position = 0;
            // 本来QQ是需要先request begin的，但是不知道为什么这个包发出去之后连接总是断开，不发倒
            // 没事，只好注释掉
            //main.getClient().requestReceiveBegin(packet.sessionId, key, portName);
            // 请求发送文件数据
	        main.getClient().face_RequestData(packet.sessionId, conn.getId());            
        }
    }

    /**
     * 初始请求表情成功事件
     * 
     * @param e
     */
    private void processRequestFaceSuccess(QQEvent e) {
    }
}
