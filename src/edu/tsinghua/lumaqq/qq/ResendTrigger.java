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
package edu.tsinghua.lumaqq.qq;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import edu.tsinghua.lumaqq.qq.net.IConnection;
import edu.tsinghua.lumaqq.qq.packets.ErrorPacket;
import edu.tsinghua.lumaqq.qq.packets.InPacket;
import edu.tsinghua.lumaqq.qq.packets.OutPacket;

public class ResendTrigger<T> implements Callable<T> {
	private QQClient client;
	
    // 超时队列
    private Queue<OutPacket> timeoutQueue;
    // 包 to Port的映射
    private Map<OutPacket, String> toPort;

    // temp variable
    private String portName;
	
	public ResendTrigger(QQClient client) {
        this.client = client;
        timeoutQueue = new LinkedList<OutPacket>();
        toPort = new HashMap<OutPacket, String>();
        QQClient.executor.schedule(this, QQ.QQ_TIMEOUT_SEND, TimeUnit.MILLISECONDS);
	}
	
    /**
     * 添加一个包到超时队列
     * 
     * @param packet
     * 		发送包对象
     * @param name
     * 		port名称
     */
    public synchronized void add(OutPacket packet, String name) {
        timeoutQueue.offer(packet);
        toPort.put(packet, name);
    }
    
    /**
     * 清空重发队列
     */
    public synchronized void clear() {
    	timeoutQueue.clear();
    	toPort.clear();
    }
    
    /**
     * 得到超时队列的第一个包，不把它从队列中删除
     * 
     * @return 
     * 		超时队列的第一个包，如果没有，返回null
     */
    public synchronized OutPacket get() {
		return timeoutQueue.peek();
    }
    
    /**
     * 得到超时队列的第一个包，并把它从队列中删除
     * 
     * @return 
     * 		超时队列的第一个包，如果没有，返回null
     */
    public synchronized OutPacket remove() {
        OutPacket ret = timeoutQueue.poll();
        if(ret != null)
        	portName = toPort.remove(ret);
        return ret;
    }
    
    /**
     * 删除ack对应的请求包
     * 
     * @param ack
     */
    public synchronized void remove(InPacket ack) {
        for(Iterator<OutPacket> i = timeoutQueue.iterator(); i.hasNext(); ) {
        	OutPacket packet = i.next();
        	if(ack.equals(packet)) {
        		toPort.remove(packet);
        		i.remove();
        		break;
        	}
        }
    }
    
    /**
     * 得到下一个包的超时时间
     * 
     * @return 
     * 		下一个包的超时时间，如果队列为空，返回一个固定值
     */
    private long getTimeoutLeft() {
        OutPacket packet = get();
        if(packet == null)
            return QQ.QQ_TIMEOUT_SEND;
        else
            return packet.getTimeout() - System.currentTimeMillis();
    }    
    
    /**
     * 触发超时事件
     * @param p
     */
    private void fireOperationTimeOutEvent(OutPacket packet, String portName) {
    	ErrorPacket error = new ErrorPacket(ErrorPacket.ERROR_TIMEOUT, client.getUser());
    	error.timeoutPacket = packet;
    	error.setHeader(packet.getHeader());
    	error.setFamily(packet.getFamily());
    	client.addIncomingPacket(error, portName);
    }

	/* (non-Javadoc)
	 * @see java.util.concurrent.Callable#call()
	 */
	public T call() throws Exception {
    	long t = getTimeoutLeft();
		while(t <= 0) {
			OutPacket packet = remove();      
			
			// 发送
			IConnection port = client.getConnection(portName);
			if(port != null && packet != null && !port.getPolicy().isReplied(packet, false)) {
				if(packet.needResend()) {
					// 重发次数未到最大，重发
					client.sendPacketAnyway(packet, portName);
				} else {
					// 触发操作超时事件
					fireOperationTimeOutEvent(packet, portName);
				}   
			}  
			t = getTimeoutLeft();
		}
		QQClient.executor.schedule(this, t, TimeUnit.MILLISECONDS);

    	return null;
	}
}
