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
package edu.tsinghua.lumaqq;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import edu.tsinghua.lumaqq.models.Group;
import edu.tsinghua.lumaqq.models.ModelRegistry;
import edu.tsinghua.lumaqq.qq.QQ;
import edu.tsinghua.lumaqq.qq.packets.BasicInPacket;
import edu.tsinghua.lumaqq.qq.packets.InPacket;
import edu.tsinghua.lumaqq.qq.packets.in.ReceiveIMPacket;

/**
 * 消息队列实现类
 * 
 *  * @author luma
 */
public class MessageQueue {
	// 总队列
	private Queue<InPacket> queue;
	// 系统消息队列
	private Queue<InPacket> sysQueue;
	// 延迟处理队列
	private Queue<InPacket> postponeQueue;
	// 用户消息队列映射哈希表
	private Map<Integer, Queue<InPacket>> userQueueMap; 
	// 用户临时会话消息映射哈希表
	private Map<Integer, Queue<InPacket>> tempSessionQueueMap;
	// 短消息队列
	private Queue<InPacket> smsQueue;
	
	// true表示收到的消息应该延迟处理
	private boolean postpone;
	
	/**
	 * 私有构造函数，singleton模式 
	 */
	public MessageQueue() {
		queue = new LinkedList<InPacket>();
		sysQueue = new LinkedList<InPacket>();
		postponeQueue = new LinkedList<InPacket>();
		userQueueMap = new HashMap<Integer, Queue<InPacket>>();
		tempSessionQueueMap = new HashMap<Integer, Queue<InPacket>>();
		smsQueue = new LinkedList<InPacket>();
		postpone = false;
	}
	
	/**
	 * 情况所有数据
	 */
	public void clear() {
		queue.clear();
		sysQueue.clear();
		postponeQueue.clear();
		userQueueMap.clear();
	}
	
	/**
	 * 添加一个短消息到队列末尾
	 * 
	 * @param in
	 * 		要添加的消息包对象
	 */
	public void putSMS(InPacket in) {
	    if(in != null) {
	        smsQueue.offer(in);
	        queue.offer(in);
	    }
	}
	
	/**
	 * 得到队列中第一条短消息，并且把它从队列中删除
	 * 
	 * @return 
	 * 		InPacket，如果队列为空，返回null
	 */
	public InPacket getSMS() {
		InPacket ret = smsQueue.poll();
		queue.remove(ret);
		return ret;
	}
	
	/**
	 * 得到下一条手机短信
	 * 
	 * @param qq
	 * @return
	 */
	public InPacket getSMS(int qq) {
		int size = smsQueue.size();
		while(size-- > 0) {
			ReceiveIMPacket packet = (ReceiveIMPacket)smsQueue.poll();
			if(packet.sms.sender == qq) {
				queue.remove(packet);
				return packet;				
			} else
				smsQueue.offer(packet);
		}
		return null;
	}
	
	/**
	 * 得到下一条手机短信
	 * 
	 * @param mobile
	 * @return
	 */
	public InPacket getSMS(String mobile) {
		int size = smsQueue.size();
		while(size-- > 0) {
			ReceiveIMPacket packet = (ReceiveIMPacket)smsQueue.poll();
			if(packet.sms.sender == 0 && packet.sms.senderName.equals(mobile)) {
				queue.remove(packet);
				return packet;				
			} else
				smsQueue.offer(packet);
		}
		return null;
	}

	/**
	 * 把一个普通消息包推入消息队列
	 * @param packet 
	 * 		消息包对象
	 */
	public void putMessage(BasicInPacket packet) {
		putMessage(packet, true);
	}
	
	/**
	 * @param packet 
	 * 		消息包对象
	 * @param global
	 * 		true表示添加这个消息到总队列中。推到总队列中的效果是这条消息会在tray中
	 * 		闪烁。有时候消息是不需要在tray中闪烁的，比如把群消息设为只显示计数，不显示
	 * 		提示时。
	 */
	public void putMessage(InPacket packet, boolean global) {
		ReceiveIMPacket im = (ReceiveIMPacket)packet;
		if(im.header.type == QQ.QQ_RECV_IM_TEMP_SESSION) {
			putTempSessionMessage(im);
			return;
		}
		
		// 得到QQ号，判断是否已经存在该用户的消息队列，如果这个是群消息，这个其实就是群的内部ID
		int qq;
		if(im.header.type == QQ.QQ_RECV_IM_TEMP_CLUSTER)
		    qq = im.clusterIM.clusterId;
		else
		    qq = im.header.sender;
		Queue<InPacket> userQueue = null;
		if(!userQueueMap.containsKey(qq)) {
			userQueue = new LinkedList<InPacket>();
			userQueueMap.put(qq, userQueue);
		} else {
			userQueue = userQueueMap.get(qq);
		}
		// 消息推入用户队列组队列和总队列
		userQueue.offer(packet);
		if(global)
			queue.offer(packet);
	}
	
	/**
	 * 把临时会话消息推入队列
	 * 
	 * @param packet
	 */
	protected void putTempSessionMessage(ReceiveIMPacket packet) {
		int qq = packet.tempSessionIM.sender;
		Queue<InPacket> tempQueue = null;
		if(tempSessionQueueMap.containsKey(qq))
			tempQueue = tempSessionQueueMap.get(qq);
		else {
			tempQueue = new LinkedList<InPacket>();
			tempSessionQueueMap.put(qq, tempQueue);
		}
		tempQueue.offer(packet);
		queue.offer(packet);
	}

	/**
	 * 把一个系统消息推入队列
	 * @param packet
	 */
	public void putSystemMessage(BasicInPacket packet) {
		sysQueue.offer(packet);
		queue.offer(packet);
	}

	/**
	 * 得到一条普通消息，并把他从队列中删除
	 * 
	 * @param qq 
	 * 		发送消息的好友QQ号
	 * @return 
	 * 		如果有消息在返回消息，否则返回null
	 */
	public InPacket getMessage(int qq) {
		// 检查是否有这个队列，有则取第一个消息，如果取后队列为空，删除这个队列
		if(userQueueMap.containsKey(qq)) {
			Queue<InPacket> userQueue = userQueueMap.get(qq);
			InPacket p = userQueue.poll();
			if(p == null)
				return null;
			// 从总队列中删除
			queue.remove(p);
			// 如果用户消息队列为空，删除这个队列
			if(userQueue.isEmpty())
				userQueueMap.remove(qq);
			return p;
		} else
			return null;
	}
	
	/**
	 * 得到一条普通消息，不把他从队列中删除
	 * 
	 * @param qq 
	 * 		发送消息的好友QQ号
	 * @return 
	 * 		如果有消息在返回消息，否则返回null
	 */
	public InPacket peekMessage(int qq) {
		// 检查是否有这个队列，有则取第一个消息，如果取后队列为空，删除这个队列
		if(userQueueMap.containsKey(qq)) {
			Queue<InPacket> userQueue = userQueueMap.get(qq);
			return userQueue.peek();
		} else
			return null;
	}
	
	/**
	 * 把qq号指定的好友或者群的所有消息删除
	 * 
	 * @param qq 
	 * 		可能是好友的QQ号，也可能是群的内部ID
	 */
	public void removeMessage(int qq) {
		// 检查是否有这个队列
		if(userQueueMap.containsKey(qq)) {
			Queue<InPacket> userQueue = userQueueMap.remove(qq);
			for(InPacket p : userQueue)
				queue.remove(p);
		}
	}
	
	/**
	 * 得到一条普通消息，这条消息是该组内队列的第一条
	 * 
	 * @param g 
	 * 		Group
	 * @return 
	 * 		如果有则返回消息，否则返回null
	 */
	public InPacket getGroupMessage(Group g) {
		int nextSender = nextGroupSender(g);
		if(nextSender == -1)
			return null;
		else {
			Queue<InPacket> userQueue = userQueueMap.get(nextSender);
			InPacket ret = userQueue.poll();
			queue.remove(ret);
			return ret;
		}
	}

	/**
	 * 得到系统消息队列的第一个包，但是不删除它
	 * 
	 * @return
	 * 		系统消息包对象
	 */
	public InPacket peekSystemMessage() {
		InPacket ret = sysQueue.peek();
		return ret;
	}
	
	/**
	 * 得到一条系统消息，并把他从队列删除
	 * 
	 * @return 
	 * 		如果有消息，返回消息，否则返回null
	 */
	public InPacket getSystemMessage() {
		InPacket ret = sysQueue.poll();
		queue.remove(ret);
		return ret;
	}

	/**
	 * 检查是否某个好友还有消息未读
	 * 
	 * @param qqNum
	 * 		好友QQ号
	 * @return 
	 * 		true如果有消息未读
	 */
	public boolean hasMessage(int qqNum) {
		return userQueueMap.containsKey(qqNum);
	}
	
	/**
	 * 检查是否有某个用户的临时会话消息
	 * 
	 * @param qqNum
	 * 		QQ号
	 * @return
	 * 		true表示有临时会话消息未读
	 */
	public boolean hasTempSessionMessage(int qqNum) {
		return tempSessionQueueMap.containsKey(qqNum);
	}
	
	/**
	 * 得到下一条临时会话消息
	 * 
	 * @param qqNum
	 * @return
	 */
	public InPacket getTempSessionMessage(int qqNum) {
		if(hasTempSessionMessage(qqNum)) {
			Queue<InPacket> tempQueue = tempSessionQueueMap.get(qqNum);
			InPacket p = tempQueue.poll();
			if(p == null)
				return null;
			// 从总队列中删除
			queue.remove(p);
			// 如果用户消息队列为空，删除这个队列
			if(tempQueue.isEmpty())
				tempSessionQueueMap.remove(qqNum);
			return p;
		} else
			return null;
	}
	
	/**
	 * 得到下一条临时会话消息，不从队列中删除
	 * 
	 * @param qqNum
	 * @return
	 */
	public InPacket peekTempSessionMessage(int qqNum) {
		if(hasTempSessionMessage(qqNum)) {
			Queue<InPacket> tempQueue = tempSessionQueueMap.get(qqNum);
			return tempQueue.peek();
		} else
			return null;
	}
	
	/**
	 * 检查是否某个群下面有讨论组的消息，如果父群id是0，则检查
	 * 是否有多人对话消息
	 * 
	 * @param parentClusterId
	 * 		父群id，0表示多人对话容器
	 * @return
	 * 		子群id，如果为-1表示没有子群有消息
	 */
	public int hasSubClusterIM(int parentClusterId) {
		for(InPacket p : queue) {
			if(p instanceof ReceiveIMPacket) {
			    ReceiveIMPacket packet = (ReceiveIMPacket)p;
			    switch(packet.header.type) {
			    	case QQ.QQ_RECV_IM_TEMP_CLUSTER:
			    		if(packet.clusterIM.externalId == parentClusterId)
			    			return packet.clusterIM.clusterId;
			    	default:
			    		break;
			    }
			}
		}
		return -1;
	}
	
	/**
	 * 检查某个组是否有消息未读
	 * 
	 * @param g 
	 * 		Group
	 * @return 
	 * 		true如果有消息未读
	 */
	public boolean hasGroupMessage(Group g) {
		return nextGroupSender(g) != -1;
	}

	/**
	 * @return 
	 * 		true如果还有任何消息未读
	 */
	public boolean hasNext() {
		return !queue.isEmpty();
	}

	/**
	 * @return 
	 * 		true如果还有系统消息未读
	 */
	public boolean hasSystemMessage() {
		return !sysQueue.isEmpty();
	}
	
	/**
	 * @return 
	 * 		true如果还有短消息
	 */
	public boolean hasSMS() {
	    return !smsQueue.isEmpty();
	}
	
	/**
	 * 好友的下一条消息是不是临时会话消息
	 * 
	 * @param qq
	 * @return
	 */
	public boolean isNextTempSessionMessage(int qq) {
		InPacket normal = peekMessage(qq);
		InPacket temp = peekTempSessionMessage(qq);
		if(temp == null && normal == null)
			return false;
		if(temp == null)
			return false;
		for(InPacket in : queue) {
			if(in == normal)
				return false;
			if(temp == in)
				return true;
		}
		return false;
	}

	/**
	 * @return 
	 * 		下一条消息的发送者的QQ号，如果是0，表示是系统消息，-1表示
	 * 		无消息如果是群消息，返回的将是群的内部ID
	 */
	public int nextSender() {
		InPacket packet = queue.peek();
		if(packet == null)
			return -1;

		if(packet instanceof ReceiveIMPacket) {
			ReceiveIMPacket im = (ReceiveIMPacket)packet;
			if(im.header.type == QQ.QQ_RECV_IM_SYS_MESSAGE)
				return 0;
			else if(im.header.type == QQ.QQ_RECV_IM_TEMP_CLUSTER)
			    return im.clusterIM.clusterId;
			else
				return im.header.sender;
		} else
			return 0;
	}
	
	/**
	 * 返回下一个消息的来源，对于普通消息，返回QQ_IM_FROM_FRIEND，对于
	 * 系统消息，返回QQ_IM_FROM_SYS，对于群消息，有两种情况，因为群消息
	 * 包含了普通消息和通知消息，对于普通消息，我们返回QQ_IM_FROM_CLUSTER，
	 * 对于通知消息，我们返回QQ_IM_FROM_SYS
	 * 
	 * @return
	 * 		消息来源标识常量
	 */
	public int nextMessageSource() {
		InPacket packet = queue.peek();
		if(packet == null)
			return -1;
		
		if(packet instanceof ReceiveIMPacket) {
			ReceiveIMPacket im = (ReceiveIMPacket)packet;
			return im.getMessageCategory();
		} else
			return QQ.QQ_IM_FROM_SYS;
	}
	
	/**
	 * 返回该组内下一条消息发送者的QQ号
	 * 
	 * @param g
	 * 		Group
	 * @return
	 * 		QQ号，如果没有消息，返回-1
	 */
	public int nextGroupSender(Group g) {
		for(InPacket p : queue) {
			if(p instanceof ReceiveIMPacket) {
			    ReceiveIMPacket packet = (ReceiveIMPacket)p;
			    if(packet.header.type == QQ.QQ_RECV_IM_BIND_USER || packet.header.type == QQ.QQ_RECV_IM_MOBILE_QQ) 
			        continue;
				// 得到下一个包的发送者QQ号		
			    int sender = packet.header.sender;
			    if(packet.header.type == QQ.QQ_RECV_IM_TEMP_CLUSTER)
			    	sender = packet.clusterIM.clusterId;
				// 在g指定的组中查找是否有这个好友
			    if(ModelRegistry.hasUser(sender)) {
			    	if(ModelRegistry.getUser(sender).group == g)
			    		return sender;
			    } else if(ModelRegistry.hasCluster(sender)) {
			    	if(ModelRegistry.getCluster(sender).group == g)
			    		return sender;
			    }
			}
		}
		return -1;
	}
	
	/**
	 * @return
	 * 		下一个应该闪烁的消息的发送者
	 */
	public int nextBlinkableIMSender() {
		for(InPacket p : queue) {
			if(p instanceof ReceiveIMPacket) {
			    ReceiveIMPacket packet = (ReceiveIMPacket)p;
			    switch(packet.header.type) {
			    	case QQ.QQ_RECV_IM_FRIEND: 
			    	case QQ.QQ_RECV_IM_STRANGER:
			    	case QQ.QQ_RECV_IM_TEMP_SESSION:
			    		return packet.header.sender;
			    	default:
			    		break;
			    }
			}
		}
		return -1;
	}
	
	/**
	 * @return
	 * 		下一条群消息的群号，-1表示没有
	 */
	public int nextClusterIMSender() {
		for(InPacket p : queue) {
			if(p instanceof ReceiveIMPacket) {
			    ReceiveIMPacket packet = (ReceiveIMPacket)p;
			    switch(packet.header.type) {
			    	case QQ.QQ_RECV_IM_CLUSTER:
			    		return packet.header.sender;
			    	case QQ.QQ_RECV_IM_TEMP_CLUSTER:
			    		return packet.clusterIM.clusterId;
			    	default:
			    		break;
			    }
			}
		}
		return -1;
	}
	
	/**
	 * 一个消息在好友列表还没得到之前到达了，延迟处理这个消息
	 * 
	 * @param packet 
	 * 		消息包
	 */
	public void postponeMessage(InPacket packet) {
		postponeQueue.offer(packet);
	}
	
	/**
	 * 返回下一个延迟处理的消息
	 * 
	 * @return 
	 * 		如果有则返回消息，没有返回null
	 */
	public InPacket getPostponedMessage() {
		return postponeQueue.poll();
	}

	/**
	 * @return Returns the postpone.
	 */
	public synchronized boolean isPostpone() {
		return postpone;
	}

	/**
	 * @param postpone The postpone to set.
	 */
	public synchronized void setPostpone(boolean postpone) {
		this.postpone = postpone;
	}
}
