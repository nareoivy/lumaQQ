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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.tsinghua.lumaqq.qq.QQ;
import edu.tsinghua.lumaqq.qq.packets.ErrorPacket;
import edu.tsinghua.lumaqq.qq.packets.InPacket;
import edu.tsinghua.lumaqq.qq.packets.OutPacket;
import edu.tsinghua.lumaqq.qq.packets.PacketParseException;

/**
 * <pre>
 * UDP Socks5 连接类
 * </pre>
 * 
 * @author luma
 */
public class UDPSocks5Port extends AbstractPort implements IProxyHandler {
    /** Log类 */
	private static final Log log = LogFactory.getLog(UDPSocks5Port.class);
    /** UDP channel */
	private DatagramChannel channel;
    /** Socks5 代理类 */
	private Socks5Proxy proxy;
    /** 代理是否已经准备好 */
	private boolean ready;

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
    public UDPSocks5Port(IConnectionPolicy policy, InetSocketAddress serverAddress) throws IOException {
        super(policy);
        ready = false;
        this.remoteAddress = serverAddress;
		// 打开UDP relay的channel
		channel = DatagramChannel.open();
		channel.configureBlocking(false);
		channel.socket().bind(new InetSocketAddress(0));
        // 创建代理类
        proxy = new Socks5Proxy(this, policy.getProxyUsername(), policy.getProxyPassword(), channel);
        proxy.setProxyAddress(policy.getProxy());
        proxy.setRemoteAddress(serverAddress);
		proxy.setClientPort(channel.socket().getLocalPort());
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
     * @see edu.tsinghua.lumaqq.qq.IPort#channel()
     */
    public SelectableChannel channel() {
        return channel;
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.IPort#receive()
     */
    public void receive() throws IOException, PacketParseException {
	    receiveBuf.clear();
		for (int len = channel.read(receiveBuf); len > 0; len = channel.read(receiveBuf)) {
		    receiveBuf.flip();
		    // 跳过代理头
		    skipProxyHeader();
		    // 从当前位置分析包
		    InPacket packet = policy.parseIn(receiveBuf, false);
		    if(packet == null) {
		        receiveBuf.clear();
		        continue;
		    }
			policy.pushIn(packet);
		    receiveBuf.clear();
		}
    }
    
    /**
     * 跳过代理头部
     */
    protected void skipProxyHeader() {
        // 得到当前pos
        int pos = receiveBuf.position();
        // 得到地址类型
        byte addressType = receiveBuf.get(3);
        // 如果是域名，得到域名长度，如果是ip，直接得到头部长度
        if(addressType == Socks5Proxy.ATYP_DOMAIN_NAME)
            receiveBuf.position(pos + 6 + receiveBuf.get(4));
        else if(addressType == Socks5Proxy.ATYP_IPV4)
            receiveBuf.position(pos + 10);
        else if(addressType == Socks5Proxy.ATYP_IPV6)
            receiveBuf.position(pos + 22);
        else
            log.error("代理头部包含不支持的地址类型");
    }

    /**
     * 添加代理包的头部，Socks5代理包的格式为
     * +----+------+------+----------+----------+----------+
     * |RSV | FRAG | ATYP | DST.ADDR | DST.PORT |   DATA   |
     * +----+------+------+----------+----------+----------+
     * | 2  |  1   |  1   | Variable |    2     | Variable |
     * +----+------+------+----------+----------+----------+
     */
    protected void fillProxyHeader() {
        sendBuf.putChar((char)0)
        	.put((byte)0)
        	.put(proxy.isIp ? Socks5Proxy.ATYP_IPV4 : Socks5Proxy.ATYP_DOMAIN_NAME)
        	.put(proxy.remoteAddress)
        	.putChar((char)proxy.remotePort);
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.IPort#send()
     */
    public void send() throws IOException {
		while (!isEmpty()) {
			sendBuf.clear();
			fillProxyHeader();
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
            fillProxyHeader();
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
            if(ready)
                channel.write(buffer);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
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
     * @see edu.tsinghua.lumaqq.qq.IPort#isConnected()
     */
    public boolean isConnected() {
        return true;
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.IHandler#processConnect(java.nio.channels.SelectionKey)
     */
    public void processConnect(SelectionKey sk) throws IOException {
        // 没有什么要做的
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.IHandler#processRead(java.nio.channels.SelectionKey)
     */
    public void processRead(SelectionKey sk) throws IOException, PacketParseException {
        receive();
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.IHandler#processWrite()
     */
    public void processWrite() throws IOException {
        send();
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.IProxyHandler#proxyReady(java.net.InetSocketAddress)
     */
    public void proxyReady(InetSocketAddress bindAddress) throws IOException {
        ready = true;
        // 连接Socks Server的bind address
		channel.connect(bindAddress);
		((PortGate)getPool()).getPorter().register(this, SelectionKey.OP_READ);
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.IProxyHandler#proxyFail()
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
}
