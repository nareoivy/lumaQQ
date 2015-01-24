/*
* LumaQQ - Java QQ Client
*
* Copyright (C) 2004 notXX
*                    luma <stubma@163.com>
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
package edu.tsinghua.lumaqq.qq.net;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.Queue;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.tsinghua.lumaqq.qq.QQ;
import edu.tsinghua.lumaqq.qq.packets.ErrorPacket;
import edu.tsinghua.lumaqq.qq.packets.OutPacket;

/**
 * 部分实现包收发.
 * 
 * @author notxx
 * @author luma
 */
abstract class AbstractPort implements IPort, INIOHandler {
	private static final Log log = LogFactory.getLog(AbstractPort.class);	

	/** 发送缓冲区 */
	protected ByteBuffer sendBuf;	
	/** 接收缓冲区 */
	protected ByteBuffer receiveBuf;
	/** 发送队列 */
	protected Queue<OutPacket> sendQueue;	
    /** 端口策略 */
    protected IConnectionPolicy policy;
    /** 连接池 */
    protected IConnectionPool pool;
    /** 端口名称 */
    protected String name;    
    /** 远程地址 */
    protected InetSocketAddress remoteAddress;    
    /**
	 * 构造函数
	 */
	public AbstractPort(IConnectionPolicy policy) {
	    this.policy = policy;
	    sendQueue = new LinkedList<OutPacket>();
	    sendBuf = ByteBuffer.allocateDirect(QQ.QQ_MAX_PACKET_SIZE);
	    receiveBuf = ByteBuffer.allocateDirect(QQ.QQ_MAX_PACKET_SIZE);
	}
	
	public String getId() {
		return policy.getConnectionId();
	}
	
	public synchronized void clearSendQueue() {
		sendQueue.clear();
	}
	
	public IConnectionPool getPool() {
		return pool;
	}
	
	public void setPool(IConnectionPool pool) {
		this.pool = pool;
	}
	
	/* (non-Javadoc)
	 * @see edu.tsinghua.lumaqq.qq.net.IPort#add(edu.tsinghua.lumaqq.qq.packets.OutPacket)
	 */
	public synchronized void add(OutPacket packet) {
		sendQueue.offer(packet);
		policy.flush();
	}

	/* (non-Javadoc)
	 * @see edu.tsinghua.lumaqq.qq.IPort#isEmpty()
	 */
	public synchronized boolean isEmpty() {
		return sendQueue.isEmpty();
	}
	
	/* (non-Javadoc)
	 * @see edu.tsinghua.lumaqq.qq.net.IPort#remove()
	 */
	public synchronized OutPacket remove() {
		return sendQueue.poll();
	}
	
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.net.IPort#getNIOHandler()
     */
    public INIOHandler getNIOHandler() {
        return this;
    }
    
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.net.INIOHandler#processError(java.lang.Exception)
     */
    public void processError(Exception e) {
        log.debug("网络出错，关闭连接, id: " + getId());    
        ErrorPacket packet = policy.createErrorPacket(ErrorPacket.ERROR_NETWORK, getId());
        packet.errorMessage = e.getMessage();
        if(packet.errorMessage == null || packet.errorMessage.equals(""))
        	packet.errorMessage = "Network Error";
        policy.pushIn(packet);
    }
    
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.net.IPort#getRemoteAddress()
     */
    public InetSocketAddress getRemoteAddress() {
        return remoteAddress;
    }
    
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.net.IPort#getPortPolicy()
     */
    public IConnectionPolicy getPolicy() {
    	return policy;
    }
}