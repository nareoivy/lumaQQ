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
package edu.tsinghua.lumaqq.qq.net;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.tsinghua.lumaqq.qq.QQ;
import edu.tsinghua.lumaqq.qq.packets.ErrorPacket;
import edu.tsinghua.lumaqq.qq.packets.InPacket;
import edu.tsinghua.lumaqq.qq.packets.OutPacket;
import edu.tsinghua.lumaqq.qq.packets.PacketParseException;

/**
 * <pre>
 * TCP Socks5 连接类
 * </pre>
 * 
 * @author luma
 */
public class TCPSocks5Port extends AbstractPort implements IProxyHandler {
    /** Log类 */
	private static final Log log = LogFactory.getLog(TCPSocks5Port.class);
	/** 用于连接到proxy，也用于发送实际数据 */
	private SocketChannel channel;
    /** Socks5 代理类 */
	private Socks5Proxy proxy;
    /** 代理是否已经准备好 */
	private boolean ready;
	/**
	 * true表示远程已经关闭了这个连接
	 */
	private boolean remoteClosed;
    
    /**
     * 构造函数
     * 
	 * @param policy
	 * 		端口策略
	 * @param serverAddress
	 * 		服务器地址
	 * @throws IOException
	 * 		如果构造port失败
     */
    public TCPSocks5Port(IConnectionPolicy policy, InetSocketAddress serverAddress) throws IOException {
        super(policy);
        ready = false;
        remoteClosed = false;
        remoteAddress = serverAddress;
        // 创建代理类
        proxy = new Socks5Proxy(this, policy.getProxyUsername(), policy.getProxyPassword());
        proxy.setProxyAddress(policy.getProxy());
        proxy.setRemoteAddress(serverAddress);
        channel = (SocketChannel)proxy.channel();
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.INIOHandler#processConnect(java.nio.channels.SelectionKey)
     */
    public void processConnect(SelectionKey sk) throws IOException {
        // 没有什么要做的
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.net.AbstractPort#getNIOHandler()
     */
    @Override
	public INIOHandler getNIOHandler() {
        if(ready)
            return this;
        else
            return proxy;
    }
    
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.INIOHandler#processRead(java.nio.channels.SelectionKey)
     */
    public void processRead(SelectionKey sk) throws IOException, PacketParseException {
        receive();
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.INIOHandler#processWrite()
     */
    public void processWrite() throws IOException {
        send();
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.IProxyHandler#proxyReady(java.net.InetSocketAddress)
     */
    public void proxyReady(InetSocketAddress bindAddress) throws IOException {
        ready = true;
        channel = (SocketChannel)proxy.channel();
        ((PortGate)getPool()).getPorter().register(this, SelectionKey.OP_READ);
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.IProxyHandler#proxyAuthFail()
     */
    public void proxyAuthFail() {
        proxyError("Proxy Auth Fail");
    }
    
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.net.IProxyHandler#proxyError(java.lang.String)
     */
    public void proxyError(String err) {
    	ErrorPacket packet = policy.createErrorPacket(ErrorPacket.ERROR_PROXY, getId());
    	packet.errorMessage = err;
    	policy.pushIn(packet);
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.IPort#channel()
     */
    public SelectableChannel channel() {
        return channel;
    }
    
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.AbstractPort#dispose()
     */
    public void dispose() {
        proxy.dispose();
    }
    
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.net.IPort#start()
     */
    public void start() {
        proxy.start();
    }
    
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.IPort#receive()
     */
    public void receive() throws IOException, PacketParseException {
    	if(remoteClosed)
    		return;
		//接收数据	    
	    int oldPos = receiveBuf.position();
		for (int r = channel.read(receiveBuf); r > 0; r = channel.read(receiveBuf))
		    ;
		// 得到当前位置
		int pos = receiveBuf.position();		
		receiveBuf.flip();
		// 检查是否读了0字节，这种情况一般表示远程已经关闭了这个连接		
		if(oldPos == pos) {
		    ErrorPacket packet = policy.createErrorPacket(ErrorPacket.ERROR_CONNECTION_BROKEN, getId());
		    policy.pushIn(packet);
		    remoteClosed = true;
		    return;
		}
		// 一直循环到无包可读
	    while(true) {
	        /* 如果有完整的包，则添加这个包，调整各个参数 */
            // 解析出一个包
	        InPacket packet = null;
            try {
                packet = policy.parseIn(receiveBuf, false);
            } catch (PacketParseException e) {
                adjustBuffer(pos);
                throw e;
            }
            if(packet == null) {
            	/*
            	 * packet为null有两种情况，一是缓冲内的数据都已经解析完，
            	 * 一是数据还没有解析完，但是找不到可用的parser。这种情况
            	 * 下我们要对缓冲区重新定位
            	 */
            	if(!policy.relocate(receiveBuf))
            		break;
            }
            policy.pushIn(packet);
	    }		   
	    adjustBuffer(pos);
    }    
	
	/**
	 * 调整buffer
	 * 
	 * @param pos
	 */
	private void adjustBuffer(int pos) {
	    // 如果0不等于当前pos，说明至少分析了一个包
	    if(receiveBuf.position() > 0) {
	        receiveBuf.compact();  
	        receiveBuf.limit(receiveBuf.capacity());    
	    } else {
	        receiveBuf.limit(receiveBuf.capacity());    
	        receiveBuf.position(pos);
	    }
	}
    
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.IPort#send()
     */
    public void send() throws IOException {
		while (!isEmpty()) {
			sendBuf.clear();
			OutPacket packet = remove();
			packet.fill(sendBuf);
			sendBuf.flip();
			if(packet.needAck()) {
			    channel.write(sendBuf);
				// 添加到重发队列
				packet.setTimeout(System.currentTimeMillis() + QQ.QQ_TIMEOUT_SEND);
				policy.pushResend(packet, getId());
				log.debug("已发送 - " + packet.toString());			    
			} else {
			    int count = packet.getSendCount();
			    for(int i = 0; i < count; i++) {
			        sendBuf.rewind();
			        channel.write(sendBuf);
					log.debug("已发送 - " + packet.toString());
			    }
			}
		}
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.net.IPort#send(edu.tsinghua.lumaqq.qq.packets.OutPacket)
     */
    public void send(OutPacket packet) {
		try {
            sendBuf.clear();
            packet.fill(sendBuf);
            sendBuf.flip();
            if(packet.needAck()) {
                channel.write(sendBuf);
            	log.debug("已发送 - " + packet.toString());			    
            } else {
                int count = packet.getSendCount();
                for(int i = 0; i < count; i++) {
                    sendBuf.rewind();
                    channel.write(sendBuf);
            		log.debug("已发送 - " + packet.toString());
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.IPort#send(java.nio.ByteBuffer)
     */
    public void send(ByteBuffer buffer) {
        try {
            channel.write(buffer);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.IPort#isConnected()
     */
    public boolean isConnected() {
        return ready;
    }
}
