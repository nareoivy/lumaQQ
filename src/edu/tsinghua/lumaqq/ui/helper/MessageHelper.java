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
package edu.tsinghua.lumaqq.ui.helper;

import static edu.tsinghua.lumaqq.models.MessageSetting.*;
import static edu.tsinghua.lumaqq.resource.Messages.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.graphics.Image;

import edu.tsinghua.lumaqq.LumaQQ;
import edu.tsinghua.lumaqq.MessageQueue;
import edu.tsinghua.lumaqq.Sounder;
import edu.tsinghua.lumaqq.models.Cluster;
import edu.tsinghua.lumaqq.models.GroupType;
import edu.tsinghua.lumaqq.models.ModelRegistry;
import edu.tsinghua.lumaqq.models.Status;
import edu.tsinghua.lumaqq.models.User;
import edu.tsinghua.lumaqq.qq.QQ;
import edu.tsinghua.lumaqq.qq.QQClient;
import edu.tsinghua.lumaqq.qq.Util;
import edu.tsinghua.lumaqq.qq.beans.ClusterIM;
import edu.tsinghua.lumaqq.qq.beans.NormalIM;
import edu.tsinghua.lumaqq.qq.packets.in.ReceiveIMPacket;
import edu.tsinghua.lumaqq.qq.packets.in.SystemNotificationPacket;
import edu.tsinghua.lumaqq.record.IKeyConstants;
import edu.tsinghua.lumaqq.record.RecordEntry;
import edu.tsinghua.lumaqq.resource.Resources;
import edu.tsinghua.lumaqq.ui.MainShell;
import edu.tsinghua.lumaqq.ui.ReceiveIMWindow;
import edu.tsinghua.lumaqq.ui.SMSWindow;
import edu.tsinghua.lumaqq.ui.SendClusterIMWindow;
import edu.tsinghua.lumaqq.ui.SendIMTabWindow;
import edu.tsinghua.lumaqq.ui.SendIMWindow;
import edu.tsinghua.lumaqq.widgets.rich.IRichContent;

/**
 * 消息处理帮助类
 * 
 * @author luma
 */
public class MessageHelper {
    private static Log log = LogFactory.getLog(MessageHelper.class);
    
    private MainShell main;
    private Resources res;
    
	// 分片缓冲，有的长消息会变成几个分片发送，需要保存起来等待所有分片完成
    // key是消息id，value是个Object数组，保存了消息的分片
	private Map<Integer, Object[]> fragmentCache; 
    
    public MessageHelper(MainShell main) {
        this.main = main;
        res = Resources.getInstance();
        fragmentCache = new HashMap<Integer, Object[]>();
    }    
    
    /**
     * 把字节数组转换为String，它为我们处理缺省表情的问题
     * 
     * @param b
     * 		消息字节数组
     * @return
     * 		String
     */
    public String convertBytes(byte[] b) {
        StringBuffer sb = new StringBuffer();
        int offset = 0;
        int length = 0;
        for(int i = 0; i < b.length; i++) {
            if(b[i] == QQ.QQ_TAG_DEFAULT_FACE) {
                sb.append(Util.getString(b, offset, length));                
                sb.append((char)b[i]).append((char)(b[i + 1] & 0xFF));
                i++;
                offset = i + 1;
                length = 0;
            } else
                length++;
        }
        if(length > 0)
            sb.append(Util.getString(b, offset, length));
        return sb.toString();
    }
    
    /**
     * 检查这个消息是完整消息还是分片
     * 
     * @return
     * 		true表示这个消息是分片消息
     */
    private boolean isFragment(NormalIM im) {
        return im.totalFragments > 1;
    }
    
    /**
     * 检查这个消息是完整消息还是分片
     * 
     * @return
     * 		true表示这个消息是分片消息
     */
    private boolean isFragment(ClusterIM im) {
        return im.fragmentCount > 1;
    }
    
    /**
     * 添加一个普通消息分片
     * 
     * @param im
     */
    private void addFragment(NormalIM im) {
        Object[] fragments = fragmentCache.get(im.messageId);
        if(fragments == null || fragments.length != im.totalFragments) {
            fragments = new Object[im.totalFragments];
            fragmentCache.put(im.messageId, fragments);
        }
        fragments[im.fragmentSequence] = im;
    }
    
    /**
     * 添加一个群消息分片
     * 
     * @param im
     */
    private void addFragment(ClusterIM im) {
        Object[] fragments = fragmentCache.get(im.messageId);
        if(fragments == null || fragments.length != im.fragmentCount) {
            fragments = new Object[im.fragmentCount];
            fragmentCache.put(im.messageId, fragments);
        }
        fragments[im.fragmentSequence] = im;
    }
    
    /**
     * 得到完整的消息，同时把这个消息从分片缓冲中清楚。调用此方法前，必须先用
     * isMessageComplete()判断分片是否都已经收到
     * 
     * @param messageId
     * 		消息id
     * @return
     * 		ClusterIM对象
     */
    private ClusterIM getIntegratedClusterIM(int messageId) {
        Object[] fragments = fragmentCache.remove(messageId);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
		for(Object f : fragments) {
            try {
                baos.write(((ClusterIM)f).messageBytes);
            } catch (IOException e) {
            }
        }        
        ClusterIM ret = (ClusterIM)fragments[fragments.length - 1];
        ret.messageBytes = baos.toByteArray();
    	ret.message = convertBytes(ret.messageBytes);
        return ret;
    }
    
    /**
     * 得到完整的普通消息
     * 
     * @param messageId
     * 		消息ID
     * @return
     * 		NormalIM对象
     */
    private NormalIM getIntegratedNormalIM(int messageId) {
        Object[] fragments = fragmentCache.remove(messageId);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
		for(Object f : fragments) {
            try {
                baos.write(((NormalIM)f).messageBytes);
            } catch (IOException e) {
            }
        }        
        NormalIM ret = (NormalIM)fragments[0];
    	ret.message = NormalIMFormalizer.deformalize(baos.toByteArray());
        return ret;
    }
    
    /**
     * 检查是否一个长消息的分片都已经收到了
     * 
     * @param messageId
     * 		消息id
     * @return
     * 		true表示已经收到
     */
    private boolean isMessageComplete(int messageId) {      
        if(!fragmentCache.containsKey(messageId))
            return false;
        
        Object[] fragments = fragmentCache.get(messageId);
		for(Object f : fragments) {
            if(f == null)
                return false;
        }
        return true;
    }
    
    /**
     * 推入一条短信
     * 
     * @param packet
     */
    public void putSMS(ReceiveIMPacket packet) {
    	MessageQueue mq = main.getMessageQueue();
    	ShellRegistry shellRegistry = main.getShellRegistry();
    	
    	// 保存消息
    	Object key = null;
    	if(packet.sms.sender == 10000) {
    		/*
    		 * 普通手机
    		 */
    		RecordEntry entry = new RecordEntry();
    		entry.type = IKeyConstants.NORMAL;
    		entry.owner = 9999;
    		entry.sender = 0;
    		entry.senderParent = 0;
    		entry.receiver = main.getMyModel().qq;
    		entry.time = System.currentTimeMillis();
    		entry.message = packet.sms.senderName + '|' + packet.sms.message;
    		main.getRecordManager().addRecord(entry);
    		key = packet.sms.senderName;
    	} else {
    		/*
    		 * 绑定手机用户和移动QQ用户
    		 */
    		RecordEntry entry = new RecordEntry();
    		entry.type = IKeyConstants.NORMAL;
    		entry.owner = packet.sms.sender;
    		entry.sender = packet.sms.sender;
    		entry.senderParent = 0;
    		entry.receiver = main.getMyModel().qq;
    		entry.time = System.currentTimeMillis();
    		entry.message = packet.sms.message;
    		main.getRecordManager().addRecord(entry);
    		key = ModelRegistry.getUser(packet.sms.sender);
    		if(key == null) {
    			User f = new User();
    			f.qq = packet.sms.sender;
    			key = f;
    		} else {
    			// 添加到最近联系人
    			final User u = (User)key;
    			main.getDisplay().syncExec(new Runnable() {
    				public void run() {
    					main.getBlindHelper().updateLatest(u);
    				}
    			});
    		}
    	}
    	
    	if(shellRegistry.hasSMSWindow(key)) {
    		SMSWindow window = shellRegistry.getSMSWindow(key);
    		window.putSMS(packet);
    		if(!window.isActive())
    			window.startBlink();
    	} else {
    		if(!main.getMessageQueue().hasNext())
    			main.getUIHelper().startBlinkImage(res.getImage(Resources.icoMobile));
    		if(!main.getMessageQueue().hasSMS())
    			main.getUIHelper().startBlinkSMSIcon();
    		
    		mq.putSMS(packet);
    		// 播放声音
    		main.getSounder().play(LumaQQ.MSG_SOUND); 		
    	}
    }
	
	/**
	 * 推入一个群通知消息，一个群可以有很多种类型的消息，一般来说，除了普通群消息之外的
	 * 其他消息，我们都看做是一个群通知，也就是要闪那个小喇叭，而不是把他显示在发送消息
	 * 的窗口中。这样的群通知消息有申请加入群，退出群，同意加入群等等等等。判断的逻辑基
	 * 本上也和系统消息差不多，先看小喇叭闪没闪，没闪就闪他，再看是不是第一条消息，是就
	 * 再闪tray icon
	 * 
	 * @param packet
	 * 			消息包
	 */
	public void putClusterNotification(ReceiveIMPacket packet) {
		String clusterName;
		Cluster c = ModelRegistry.getCluster(packet.header.sender);
		if(c == null)
			clusterName = String.valueOf(packet.externalId);
		else
			clusterName = c.name;
		if(clusterName.equals(""))
			clusterName = String.valueOf(packet.externalId);
		
		// 保存到聊天记录
		String sender = String.valueOf(packet.sender);
		RecordEntry entry = new RecordEntry();
		entry.type = IKeyConstants.SYSTEM;
		entry.owner = 10000;
		entry.sender = packet.header.sender;
		entry.senderParent = packet.sender;
		entry.receiver = main.getMyModel().qq;
		entry.time = System.currentTimeMillis();
		
		switch(packet.header.type) {
			case QQ.QQ_RECV_IM_ADDED_TO_CLUSTER:
				entry.subType = IKeyConstants.SUB_ADDED_TO_CLUSTER;
			case QQ.QQ_RECV_IM_CREATE_CLUSTER:
				entry.subType = IKeyConstants.SUB_CREATE_CLUSTER;
				if(packet.clusterType == QQ.QQ_CLUSTER_TYPE_PERMANENT)
					entry.message = NLS.bind(cluster_message_permanent_cluster_created, clusterName);
				else
					entry.message = NLS.bind(cluster_message_temporary_cluster_created, clusterName);
				packet.message = entry.message;
				break;
			case QQ.QQ_RECV_IM_DELETED_FROM_CLUSTER:
				entry.subType = IKeyConstants.SUB_DELETED_FROM_CLUSTER;
				if(packet.sender == main.getMyModel().qq)
					entry.message = NLS.bind(cluster_message_removed, clusterName);
				else
					entry.message = NLS.bind(cluster_message_exit, sender, clusterName);
				packet.message = entry.message;
				break;
			case QQ.QQ_RECV_IM_REQUEST_JOIN_CLUSTER:
				entry.subType = IKeyConstants.SUB_REQUEST_JOIN_CLUSTER;
				entry.message = NLS.bind(cluster_message_request, new Object[] { sender, clusterName, packet.message });
				packet.message = entry.message;
				break;
			case QQ.QQ_RECV_IM_APPROVE_JOIN_CLUSTER:
				entry.subType = IKeyConstants.SUB_APPROVE_JOIN_CLUSTER;
				entry.message = NLS.bind(cluster_message_approved, sender);
				packet.message = entry.message;
				break;
			case QQ.QQ_RECV_IM_REJECT_JOIN_CLUSTER:
				entry.subType = IKeyConstants.SUB_REJECT_JOIN_CLUSTER;
				entry.message = NLS.bind(cluster_message_rejected, sender, packet.message);
				packet.message = entry.message;
				break;
			case QQ.QQ_RECV_IM_CLUSTER_NOTIFICATION:
				switch(packet.opCode) {
					case QQ.QQ_CLUSTER_OP_SET_ADMIN:
						entry.subType = IKeyConstants.SUB_CLUSTER_NOTIFICATION_SET_ADMIN;
						entry.message = NLS.bind(cluster_message_admin_entitled, clusterName);
						packet.message = entry.message;
						break;
					case QQ.QQ_CLUSTER_OP_UNSET_ADMIN:
						entry.subType = IKeyConstants.SUB_CLUSTER_NOTIFICATION_UNSET_ADMIN;
						entry.message = NLS.bind(cluster_message_admin_withdrawed, clusterName);
						packet.message = entry.message;
						break;
				}
				break;
			default:
				entry.subType = IKeyConstants.SUB_UNKNOWN;
				entry.message = "";
				packet.message = entry.message;
				break;
		}
		main.getRecordManager().addRecord(entry);
		// 调整动画状态
		if(!main.getMessageQueue().hasNext())
		    main.getUIHelper().startBlinkImage(res.getImage(Resources.icoSysMsg));
		if(!main.getMessageQueue().hasSystemMessage())
			main.getUIHelper().startBlinkSystemMessageIcon();
		
		main.getMessageQueue().putSystemMessage(packet);
		// 播放声音
		main.getSounder().play(LumaQQ.SYS_MSG_SOUND);
		log.debug("一个系统消息被推入队列");
	}
	
	/**
	 * 处理延迟队列中的消息
	 */
	public void processPostponedIM() {
		ReceiveIMPacket packet;
		MessageQueue mq = main.getMessageQueue();
		while((packet = (ReceiveIMPacket)mq.getPostponedMessage()) != null) { 
			log.debug("发现一条延迟消息，处理之");
			switch(packet.header.type) {
				case QQ.QQ_RECV_IM_CLUSTER:
				case QQ.QQ_RECV_IM_TEMP_CLUSTER:
					putClusterIM(packet);
					break;
				default:
					putNormalIM(packet);
					break;
			}
		}
	}
	
	/**
	 * 推入一个临时会话消息
	 * 
	 * @param packet
	 */
	public void putTempSessionIM(ReceiveIMPacket packet) {		
		// 得到一些工具类
		BlindHelper blindHelper = main.getBlindHelper();
		OptionHelper options = main.getOptionHelper();
		
		// 如果设置了拒绝临时会话消息，返回
	    if(options.isRejectTempSessionIM())
	    	return;
	    
		// 如果现在是延迟处理
		MessageQueue mq = main.getMessageQueue();
		if(mq.isPostpone()) {
			mq.postponeMessage(packet);
			return;
		}
	    
		// 得到发送者，如果不存在这个用户，添加到陌生人
		User f = ModelRegistry.getUser(packet.tempSessionIM.sender);
		boolean isStranger = false;
		boolean blinkable = false;
		if(f == null) {		    
		    isStranger = true;		    
	    	f = new User();
	    	f.qq = packet.tempSessionIM.sender;
	    	f.nick = String.valueOf(f.qq);
	    	f.displayName = f.nick;
	    	main.getClient().user_GetInfo(f.qq);
		} else if(f.group.isBlackList()) {
        	log.debug("收到一个坏人来的消息，忽略");
        	return;
		} else if(f.group.isCluster()) {
			isStranger = true;
		} else
			blinkable = true;
		
		// 保存到聊天记录中，使用当前时间
	    // 本来以前是使用包中的时间的，但是每个人的机器都不一样，所以往往记录就乱了
	    // 现在使用收到的时间，其实也有问题，对于那些留言就不太好判断时间了(没有太好的办法，不是没有办法)
		RecordEntry entry = new RecordEntry();
		entry.type = IKeyConstants.NORMAL;
		entry.owner = packet.tempSessionIM.sender;
		entry.sender = packet.tempSessionIM.sender;
		entry.senderParent = 0;
		entry.receiver = main.getMyModel().qq;
		entry.time = System.currentTimeMillis();
		entry.message = packet.tempSessionIM.message;
		main.getRecordManager().addRecord(entry);
		
		// 如果最近联系人功能是打开的，添加到最近联系人中
		if(options.isEnableLatest() && (!isStranger || (isStranger && options.isKeepStrangerInLatest()))) 
			blindHelper.updateLatest(f);
		
	    UIHelper uiHelper = main.getUIHelper();

		// 从model得到好友的头像
		Image head = HeadFactory.getOnlineHead(f);
		// 检查这是否是第一个消息，如果是，则需要闪烁tray icon，同时还要
		// 在tab上闪烁图标，还要在friend上跳动一个图标，如果不是，则
		// 不需要闪动tray icon，但是其他两个还是要的。如果当前好友已经
		// 有其他消息还没有读，则声音提示就免掉
		if(!mq.hasNext())
		    uiHelper.startBlinkImage(HeadFactory.getOnlineSmallHead(f));
		if(blinkable) {
			blindHelper.startBlinkGroupImage(f.group, head);
			blindHelper.startBlinkText(f.group);			
			if(!blindHelper.hasAnimation(f)) {		    
				blindHelper.startBounceImage(f);
				main.getSounder().play(LumaQQ.MSG_SOUND);
			}
		}
		
		// 推入队列
		mq.putMessage(packet);
	}
	 
	/**
	 * 推入一条消息并更新各种图标的闪烁状态，这个方法会检查是否好友列表已经
	 * 得到，如果没有，推入延迟队列
	 * 
	 * @param packet
	 * 			消息包
	 */
	public void putNormalIM(ReceiveIMPacket packet) {
		// 如果现在是延迟处理
		MessageQueue mq = main.getMessageQueue();
		if(mq.isPostpone()) {
			mq.postponeMessage(packet);
			return;
		}
		
	    // 如果这个消息是分片消息，如果这个消息已经完成，则继续处理，否则推入分片缓冲
	    if(isFragment(packet.normalIM)) {
	        addFragment(packet.normalIM);
	        if(isMessageComplete(packet.normalIM.messageId)) {
	            packet.normalIM = getIntegratedNormalIM(packet.normalIM.messageId);
	        } else {
	            return;
	        }
	    } else {
	        packet.normalIM.message = NormalIMFormalizer.deformalize(packet.normalIM.messageBytes);
	    }
	    
		// 得到好友在model中的位置，但是有可能为null，因为也许这是用户的第一次登陆
		// 其好友列表还没得到，但是这时候有消息来了，所有也无法闪烁图标了，对于
		// 这种情况，需要特殊处理一下，基本的方法是把消息推入延迟处理队列	    
	    User f = ModelRegistry.getUser(packet.normalHeader.sender);	    
	    boolean iAmHisStranger = packet.header.type == QQ.QQ_RECV_IM_STRANGER;
	    boolean noSuchUser = f == null || f.group.isCluster();
	    boolean heIsMyStranger = noSuchUser || f.group.isStranger();
	    boolean heIsMyBlacklist = f != null && f.group.isBlackList(); 
	    if(heIsMyBlacklist || noSuchUser && iAmHisStranger) {
        	log.debug("收到一个坏人来的消息，忽略");
        	return;
	    }
	    
	    // 检查陌生人消息设置
	    BlindHelper blindHelper = main.getBlindHelper();
	    OptionHelper options = main.getOptionHelper();
	    if(heIsMyStranger && options.isRejectStranger())
	    	return;
	    
	    // 如果不存在这个用户，添加到陌生人
	    if(noSuchUser) {
	    	f = new User();
	    	f.qq = packet.normalHeader.sender;
	    	f.nick = String.valueOf(f.qq);
	    	f.displayName = f.nick;
	    	blindHelper.addUser(f, GroupType.STRANGER_GROUP);
	    	main.getBlindHelper().refreshGroup(f.group);
	    	main.getClient().user_GetInfo(f.qq);
	    }
	    
		// 保存到聊天记录中，使用当前时间
	    // 本来以前是使用包中的时间的，但是每个人的机器都不一样，所以往往记录就乱了
	    // 现在使用收到的时间，其实也有问题，对于那些留言就不太好判断时间了(没有太好的办法，不是没有办法)
		RecordEntry entry = new RecordEntry();
		entry.type = IKeyConstants.NORMAL;
		entry.owner = f.qq;
		entry.sender = f.qq;
		entry.senderParent = 0;
		entry.receiver = main.getMyModel().qq;
		entry.time = System.currentTimeMillis();
		entry.message = packet.normalIM.message;
		main.getRecordManager().addRecord(entry);
		
	    ShellRegistry shellRegistry = main.getShellRegistry();
	    UIHelper uiHelper = main.getUIHelper();
	    QQClient client = main.getClient();
	    boolean useTabIM = main.getOptionHelper().isUseTabIMWindow();
		
		// 如果最近联系人功能是打开的，添加到最近联系人中
		if(options.isEnableLatest() && (!heIsMyStranger || (heIsMyStranger && options.isKeepStrangerInLatest()))) 
			blindHelper.updateLatest(f);
		// 在这里要检查好友的发送消息状态，如果在这个时候，好友的发送消息窗口是打开的
		// 并且与好友处于聊天模式中，那么就直接把这个消息推到这个窗口，而不需要再
		// 闪烁图标了
		if(!useTabIM && shellRegistry.hasSendIMWindow(f) && f.talkMode) {
			// 得到发送消息窗口实例
		    SendIMWindow sms = shellRegistry.getSendIMWindow(f);
			// 在把这条消息推入窗口之前，我们还需要检查是否之前还有这个好友的消息，这种情况是
			// 可能的，发生在用户开始处于消息模式，后来处于聊天模式，而这之间有消息未读时
			while(mq.hasMessage(f.qq)) {
				ReceiveIMPacket p = (ReceiveIMPacket)mq.getMessage(f.qq);
				sms.appendMessage(f, p.normalIM, p.normalHeader);
			}
			// 现在轮到把这条消息推入窗口中了
			sms.appendMessage(f, packet.normalIM, packet.normalHeader);
			// 停止跳动和闪烁头像
			blindHelper.getViewer(f).stopAnimation(f);
			blindHelper.resetGroupImageEffect(f.group);
			uiHelper.resetTrayImageEffect();
			// 如果聊天窗口当前不是active的，闪烁图标并播放声音提示用户有消息到来
			if(!sms.isActive()) {
			    sms.startBlinkImage();
				main.getSounder().play(LumaQQ.MSG_SOUND);			    
			}
			return;
		} else if(useTabIM && shellRegistry.isSendIMTabWindowOpened()) {
			// 推入消息
			SendIMTabWindow window = shellRegistry.getSendIMTabWindow();
			while(mq.hasMessage(f.qq)) {
				ReceiveIMPacket p = (ReceiveIMPacket)mq.getMessage(f.qq);
				window.putMessage(f, p);
			}
			window.putMessage(f, packet);
			// 停止跳动和闪烁头像
			blindHelper.getViewer(f).stopAnimation(f);
			blindHelper.resetGroupImageEffect(f.group);
			uiHelper.resetTrayImageEffect();
			return;
		}
		// 修改好友的是否有消息属性，这个属性主要用于排序，也就是如果一个好友是隐身的
		// 那么一般他在排在下面，需要翻页才能看到，很不方便，所以通过设置这个属性，
		// 能够让好友有消息来时排到列表的最前面，方便用户选择查看消息
		if(f.isOffline()) {
			// 如果好友当前是离线状态，临时设置他的状态为在线
			f.status = Status.ONLINE;
			// 把好友加入到当前在线名单
			main.getCurrentOnlines().add(f);		    
		}
		f.hasMessage = true;
		f.headId = packet.normalHeader.senderHead;
		blindHelper.refreshGroup(f.group);
		// 从model得到好友的头像
		Image head = HeadFactory.getOnlineHead(f);
		// 检查这是否是第一个消息，如果是，则需要闪烁tray icon，同时还要
		// 在tab上闪烁图标，还要在friend上跳动一个图标，如果不是，则
		// 不需要闪动tray icon，但是其他两个还是要的。如果当前好友已经
		// 有其他消息还没有读，则声音提示就免掉
		if(!mq.hasNext())
		    uiHelper.startBlinkImage(HeadFactory.getOnlineSmallHead(f));
		blindHelper.startBlinkGroupImage(f.group, head);
		blindHelper.startBlinkText(f.group);
		if(!blindHelper.hasAnimation(f)) {		    
			blindHelper.startBounceImage(f);
			main.getSounder().play(LumaQQ.MSG_SOUND);
		}
		
		// 推入队列
		mq.putMessage(packet);
		// 检查是否这个好友的消息窗口已经被打开，如果是，使下一条按钮使能
		if(!useTabIM && shellRegistry.hasReceiveIMWindow(f)) {
			ReceiveIMWindow rms = shellRegistry.getReceiveIMWindow(f);
			rms.setNextButtonEnabled(true);
		}
		// 检查当前是否在离开状态，并且是否设置了自动回复，如果是，自动回复消息
		if(client.getUser().getStatus() == QQ.QQ_STATUS_AWAY 
				&& packet.normalIM.replyType != QQ.QQ_IM_AUTO_REPLY
				&& main.getConfigHelper().isAutoReply()) {
			client.im_Send(packet.normalHeader.sender, Util.getBytes(main.getConfigHelper().getCurrentAutoReplyString()), QQ.QQ_IM_AUTO_REPLY);	
		}
		// 检查是否设置了自动弹出消息且当前没有打开的查看消息窗口，则自动弹出
		// 如果没有设置自动弹出，且目前主窗口处于最小化状态，显示消息提示
		if((!shellRegistry.hasReceiveIMWindow(f) || useTabIM) && options.isAutoEject()) {
	        main.getShellLauncher().openNormalIMWindow(f);
		}	    
	}
	
	/**
	 * 推入一条群消息并更新各种图标的闪烁状态，如果好友列表还没有全部得到，
	 * 则推入延迟队列
	 * 
	 * @param packet
	 * 		消息包对象
	 * @param resolved
	 * 		true表示其中的自定义表情都已经得到
	 */
	public void putClusterIM(ReceiveIMPacket packet) {
	    MessageQueue mq = main.getMessageQueue();	    
	    if(mq.isPostpone()) {
	    	mq.postponeMessage(packet);
	    	return;
	    }
	    
	    ShellRegistry shellRegistry = main.getShellRegistry();
	    Sounder sounder = main.getSounder();
	    BlindHelper blindHelper = main.getBlindHelper();
	    OptionHelper options = main.getOptionHelper();
	    boolean useTabIM = main.getOptionHelper().isUseTabIMWindow();
	    
		// 得到群的内部ID
		int clusterId = packet.header.sender;
		if(packet.header.type == QQ.QQ_RECV_IM_TEMP_CLUSTER)
		    clusterId = packet.clusterIM.clusterId;
		// 得到群的model。如果没有，则新建一个
		Cluster c = null;
		if(packet.header.type == QQ.QQ_RECV_IM_TEMP_CLUSTER)
		    c = main.getBlindHelper().addTempCluster(packet.clusterIM.type, clusterId, packet.clusterIM.externalId);
		else
		    c = main.getBlindHelper().addCluster(clusterId, false);
		if(c == null) 
			return;
		
		// 如果群消息发送者是我自己，则不处理这条消息
		if(main.getMyModel().qq == packet.clusterIM.sender) return;
		
		// 得到消息设定，如果设置了阻止群消息，返回
		if(c.messageSetting == BLOCK)
	        return;
	        
	    // 如果这个消息是分片消息，如果这个消息已经完成，则继续处理，否则推入分片缓冲
	    if(!packet.clusterIM.faceResolved && isFragment(packet.clusterIM)) {
	        addFragment(packet.clusterIM);
	        if(isMessageComplete(packet.clusterIM.messageId)) {
	            packet.clusterIM = getIntegratedClusterIM(packet.clusterIM.messageId);
	        } else {
	            return;
	        }
	    } 
	    
	    // 检查消息里面是否有自定义表情，如果是，提交给自定义表情接收器，返回
	    if(!packet.clusterIM.faceResolved) {
	        if(packet.clusterIM.message == null)
	            packet.clusterIM.message = convertBytes(packet.clusterIM.messageBytes);			    
	        if(packet.clusterIM.message.indexOf(IRichContent.CUSTOM_FACE_TAG) != -1) {		            
	            main.getFaceReceiver().addClusterIM(packet);
	            return;
	        } 
	    }
	    
		// 检查versionId，如果不符合，更新群信息
		if(c.isClusterDirty(packet.clusterIM.versionId)) {
		    c.versionId = packet.clusterIM.versionId;
		    if(c.isPermanent()) {
		    	main.getClient().cluster_GetInfo(clusterId);
		    	main.getClient().cluster_getSubjectList(clusterId);
		    } else
		        main.getClient().cluster_GetTempInfo(c.clusterType.toQQConstant(), clusterId, c.parentClusterId);
		}
		// 保存到聊天记录
		RecordEntry entry = new RecordEntry();
		entry.type = IKeyConstants.CLUSTER;
		entry.owner = clusterId;
		entry.sender = packet.clusterIM.sender;
		entry.senderParent = clusterId;
		entry.receiver = main.getMyModel().qq;
		entry.time = System.currentTimeMillis();
		entry.message = packet.clusterIM.message;
	    main.getRecordManager().addRecord(entry);
		// 如果这个群的聊天窗口当前处于打开状态，则把消息直接推入，但是也是有一个例外
		// 就是用户设置了阻止该群的一切消息时。
	    // 如果这个群的聊天窗口没有打开，则要根据用户的设置来判断动作
		if(!useTabIM && shellRegistry.hasSendClusterIMWindow(c)) {
			if(c.messageSetting != BLOCK) {
				// 得到发送消息窗口实例
			    SendClusterIMWindow scms = shellRegistry.getSendClusterIMWindow(c);
				// 把这条消息推入窗口中
				scms.appendMessage(c, packet.clusterIM, false);			    
				// 如果窗口不是active的，闪烁图标并播放声音提醒用户
				// 目前禁止声音提示，有些用户觉得群窗口打开时播放声音太烦
				if(!scms.isActive()) {
				    scms.startBlinkImage();						    
					//soundDaemon.play(LumaQQ.MSG_SOUND);
				}
			}
		} else if(useTabIM && shellRegistry.isSendIMTabWindowOpened()) {
			SendIMTabWindow window = shellRegistry.getSendIMTabWindow();
			if(main.getOptionHelper().isAutoEject() || window.hasContainer(c)) {
				window.putMessage(c, packet);
				if(!window.isActive())
					window.startBlinkImage();					
			}		
		} else {
			if(c.messageSetting == ACCEPT) {
				// 检查这是否是第一个消息，如果是，则需要闪烁tray icon，同时还要
				// 在tab上闪烁图标，还要在群上跳动一个图标，如果不是，则
				// 不需要闪动tray icon，但是其他两个还是要的。如果当前群有
			    // 消息未读，则不播放提示声音					
				if(!mq.hasNext()) {
					if(c.isPermanent())
						main.getUIHelper().startBlinkImage(res.getSmallClusterHead(c.headId));
					else
						main.getUIHelper().startBlinkImage(res.getImage(Resources.icoDialog));						
				}
				if(!blindHelper.hasAnimation(c)) {
					blindHelper.startBounceImage(c);
					// 播放消息提示声音
					sounder.play(LumaQQ.MSG_SOUND);						    
				}
				switch(c.clusterType) {
					case NORMAL:
						blindHelper.startBlinkGroupImage(c.group, res.getClusterHead(c.headId));
						break;
					case SUBJECT:
						blindHelper.startBlinkGroupImage(c.group, res.getImage(Resources.icoDialog));
						blindHelper.startBlinkText(c.getParentCluster().getSubjectDummy());
						blindHelper.startBounceImage(c.getParentCluster());
						break;
					case DIALOG:
						blindHelper.startBlinkGroupImage(c.group, res.getImage(Resources.icoDialog));
						blindHelper.startBlinkText(c.getParentCluster());
						break;
				}
				// 推入队列
				mq.putMessage(packet);
			} else if(c.messageSetting == EJECT) {
				// 推入队列
				mq.putMessage(packet);
				// 弹出窗口
				main.getShellLauncher().openClusterIMWindow(c);
				// 播放消息提示声音
				sounder.play(LumaQQ.MSG_SOUND);
			} else if(c.messageSetting == COUNTER) {
				// 推入队列
				mq.putMessage(packet, false);
			}
			
			// 同步最近联系人列表
			if(options.isEnableLatest()) 
				blindHelper.updateLatest(c);
			
			// 消息数增1，刷新界面显示
		    c.messageCount++;
		    main.getBlindHelper().refreshModel(c);
		}
	}

    /**
	 * 推入一个系统消息并更新动画，这个比较简单，如果系统消息按钮没有闪烁就闪烁它
	 * 如果这是第一个消息，则tray icon也要闪烁，其他没什么要判断的了
	 * 
	 * @param packet
	 * 			系统通知包
	 */
	public void putSystemNotificationAndUpdateAnimate(SystemNotificationPacket packet) {
		// 保存到聊天记录
		String qq = String.valueOf(packet.from);
		RecordEntry entry = new RecordEntry();
		entry.type = IKeyConstants.SYSTEM;
		entry.owner = 10000;
		entry.sender = packet.from;
		entry.senderParent = 0;
		entry.receiver = main.getMyModel().qq;
		entry.time = System.currentTimeMillis();		
		switch(packet.type) {
			case QQ.QQ_SYS_BEING_ADDED:
				entry.subType = IKeyConstants.SUB_BEING_ADDED;
			case QQ.QQ_SYS_BEING_ADDED_EX:
				entry.subType = IKeyConstants.SUB_BEING_ADDED_EX;
				entry.message = NLS.bind(receive_system_message_addme, qq);
				packet.message = entry.message;
				break;
			case QQ.QQ_SYS_ADD_FRIEND_REQUEST:
				entry.subType = IKeyConstants.SUB_ADD_FRIEND_REQUEST;
			case QQ.QQ_SYS_ADD_FRIEND_REQUEST_EX:
				entry.subType = IKeyConstants.SUB_ADD_FRIEND_REQUEST_EX;
				entry.message = NLS.bind(receive_system_message_request, qq, packet.message);
				packet.message = entry.message;
				break;
			case QQ.QQ_SYS_ADD_FRIEND_APPROVED_AND_ADD:
				entry.subType = IKeyConstants.SUB_ADD_FRIEND_APPROVED_AND_ADD;
				entry.message = NLS.bind(receive_system_message_approved_and_add, qq);
				packet.message = entry.message;
				break;
			case QQ.QQ_SYS_ADD_FRIEND_APPROVED:
				entry.subType = IKeyConstants.SUB_ADD_FRIEND_APPROVED;
				entry.message = NLS.bind(receive_system_message_approved, qq);
				packet.message = entry.message;
				break;
			case QQ.QQ_SYS_ADD_FRIEND_REJECTED:
				entry.subType = IKeyConstants.SUB_ADD_FRIEND_REJECTED;
				entry.message = NLS.bind(receive_system_message_rejected, qq, packet.message);
				packet.message = entry.message;
				break;
			default:
				entry.subType = IKeyConstants.SUB_UNKNOWN;
				entry.message = "";
				packet.message = entry.message;
				break;
		}
		// 保存记录
		main.getRecordManager().addRecord(entry);
		// 调整动画状态
		if(!main.getMessageQueue().hasNext())
		    main.getUIHelper().startBlinkImage(res.getImage(Resources.icoSysMsg));
		if(!main.getMessageQueue().hasSystemMessage())
			main.getUIHelper().startBlinkSystemMessageIcon();
		
		main.getMessageQueue().putSystemMessage(packet);
		// 播放声音
		main.getSounder().play(LumaQQ.SYS_MSG_SOUND);
		log.debug("一个系统消息被推入队列");
	}
	
    /**
	 * 把对方的文件传输请求推入到发送消息窗口中
	 * 
	 * @param packet
	 * 		ReceiveIMPacket对象
	 */
	public void putSendFileRequestIM(ReceiveIMPacket packet) {
	}
}
