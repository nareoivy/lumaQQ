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

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.UnresolvedAddressException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.tsinghua.lumaqq.qq.QQ;
import edu.tsinghua.lumaqq.qq.packets.InPacket;
import edu.tsinghua.lumaqq.qq.packets.OutPacket;
import edu.tsinghua.lumaqq.qq.packets.PacketParseException;

/**
 * 基于UDP端口通信的QQ端口
 * 
 * @author notXX
 * @author luma
 */
public class UDPPort extends AbstractPort {
    /** Log类 */
	private static final Log log = LogFactory.getLog(UDPPort.class);
	/** 数据报channel */
	protected final DatagramChannel channel;
		
	/**
	 * 构造函数
	 * 
	 * @param policy
	 * 		端口策略
	 * @param address
	 * 		服务器地址
	 * @throws IOException
	 * 		如果构造端口失败
	 */
	public UDPPort(IConnectionPolicy policy, InetSocketAddress address) throws IOException {
	    super(policy);
		channel = DatagramChannel.open();
		channel.configureBlocking(false);
        this.remoteAddress = address;
	}

	/* (non-Javadoc)
	 * @see edu.tsinghua.lumaqq.qq.IPort#channel()
	 */
	public SelectableChannel channel() {
		return channel;
	}
	
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.net.IPort#start()
     */
    public void start() {
		try {
            channel.connect(remoteAddress);
        } catch(UnknownHostException e) {
			log.error("未知的服务器地址");
			processError(new Exception("Unknown Host"));
		} catch(UnresolvedAddressException e) {
			log.error("无法解析服务器地址");
			processError(new Exception("Unable to resolve server address"));
        } catch (IOException e) {
            log.error("连接失败");
            processError(e);
        } 
    }

	/* (non-Javadoc)
	 * @see edu.tsinghua.lumaqq.qq.IPort#receive()
	 */
	public void receive() throws IOException, PacketParseException {
	    receiveBuf.clear();
		for (int len = channel.read(receiveBuf); len > 0; len = channel.read(receiveBuf)) {
		    receiveBuf.flip();
		    InPacket packet = policy.parseIn(receiveBuf, false);
		    if(packet != null)
		    	policy.pushIn(packet);
		    receiveBuf.clear();
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
     * @see edu.tsinghua.lumaqq.qq.AbstractPort#dispose()
     */
    public void dispose() {
        try {
			channel.close();
		} catch(IOException e) {
		}
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
}