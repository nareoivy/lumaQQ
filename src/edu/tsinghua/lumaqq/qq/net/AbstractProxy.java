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
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <pre>
 * 实现Proxy的一些共同的部分
 * </pre>
 * 
 * @author luma
 */
public abstract class AbstractProxy implements IProxy, INIOHandler {
    /** Log类 */
	private static final Log log = LogFactory.getLog(AbstractProxy.class);

    /** 连接Proxy的channel */
    protected SocketChannel socketChannel;
    /** udp channel */
    protected DatagramChannel datagramChannel;
    /** 代理事件处理器 */
    protected IProxyHandler handler;
    /** byte buffer */
    protected ByteBuffer buffer;
    /** 用户名 */
    protected String username;
    /** 密码 */
    protected String password;
    /** 要连接的服务器地址 */
    protected InetSocketAddress serverAddress;
    /** 代理服务器地址 */
    protected InetSocketAddress proxyAddress;
    /** 当前状态 */
    protected int status;
    /** proxy是否已经连接上 */
    protected boolean connected;
    /** 是否udp，该字段只对Socks5代理有效 */
    protected boolean udp;
    /** 客户端端口，该字段制作UDP Socks5时有效 */
    protected int clientPort;
    
    /**
     * 构造函数
     * 
     * @throws IOException
     */
    public AbstractProxy(IProxyHandler handler) throws IOException {
        this.handler = handler;
        buffer = ByteBuffer.allocateDirect(300);
        username = password = "";
        connected = false;
        udp = false;
        
        socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);            
    }
    
    /**
     * 创建一个UDP Socks5代理对象
     * 
     * @param handler
     * @param channel
     */
    public AbstractProxy(IProxyHandler handler, DatagramChannel channel) {
        this.handler = handler;
        buffer = ByteBuffer.allocateDirect(300);
        username = password = "";
        connected = true;
        udp = true;
        datagramChannel = channel;
    }
    
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.IProxy#channel()
     */
    public SelectableChannel channel() {
        if(socketChannel != null)
            return socketChannel;
        else
            return datagramChannel;
    }
    
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.net.IProxy#getNIOHandler()
     */
    public INIOHandler getNIOHandler() {
        return this;
    }
    
    /**
     * 发送
     */
    protected void send() {
        try {
            if(connected) {
                if(socketChannel != null)
                    socketChannel.write(buffer);
                if(datagramChannel != null)
                    datagramChannel.write(buffer);
            }
        } catch (IOException e) {
            log.error(e.getMessage());
            handler.proxyError(e.getMessage());
        }
    }
    
    /**
     * 接收数据
     * @throws IOException
     */
    protected void receive() {
        try {
            buffer.clear();
            if(socketChannel != null)
                for (int len = socketChannel.read(buffer); len > 0; len = socketChannel.read(buffer));
            else
                datagramChannel.receive(buffer);
            buffer.flip();
        } catch (IOException e) {
            log.error(e.getMessage());
            handler.proxyError(e.getMessage());
        }
    }
    
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.IProxy#start()
     */
    public void start() {
        try {
            // 打开连接代理的channel
            if(socketChannel != null)
                socketChannel.connect(proxyAddress);
            if(datagramChannel != null)
                datagramChannel.connect(proxyAddress);
        } catch (Exception e) {
            log.error(e.getMessage());
            handler.proxyError(e.getMessage());
        }
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.IProxy#dispose()
     */
    public void dispose() {
        try {
			if(socketChannel != null)
			    socketChannel.close();
			if(datagramChannel != null)
			    datagramChannel.close();
		} catch(Exception e) {
		}
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.INIOHandler#processConnect(java.nio.channels.SelectionKey)
     */
    public void processConnect(SelectionKey sk) throws IOException {
        if(connected) return;
        //完成SocketChannel的连接
        socketChannel.finishConnect();
        while(!socketChannel.isConnected()) {
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                // 没有什么要做的
            }            
            socketChannel.finishConnect();
        }
    	sk.interestOps(SelectionKey.OP_READ);
    	connected = true;
    	log.debug("已连接上代理服务器");
    }
    
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.INIOHandler#processError(java.lang.Exception)
     */
    public void processError(Exception e) {
        handler.proxyError(e.getMessage());
    }
    
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.IProxy#setProxyAddress(java.net.InetSocketAddress)
     */
    public void setProxyAddress(InetSocketAddress proxyAddress) {
        this.proxyAddress = proxyAddress;
    }
        
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.IProxy#setServerAddress(java.net.InetSocketAddress)
     */
    public void setRemoteAddress(InetSocketAddress serverAddress) {
        this.serverAddress = serverAddress;
    }
    
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.IProxy#setClientPort(int)
     */
    public void setClientPort(int p) {
        clientPort = p;
    }
    
    /**
     * @param password The password to set.
     */
    public void setPassword(String password) {
        this.password = password;
    }
    
    /**
     * @param username The username to set.
     */
    public void setUsername(String username) {
        this.username = username;
    }
}
