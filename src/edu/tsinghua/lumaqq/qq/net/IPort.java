/*
* LumaQQ - Java QQ Client
*
* Copyright (C) 2004  notXX
* 					  luma <stubma@163.com>
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
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;

import edu.tsinghua.lumaqq.qq.packets.OutPacket;
import edu.tsinghua.lumaqq.qq.packets.PacketParseException;

/**
 * 发送接收包的接口.
 * 
 * @author notxx
 * @author luma
 */
public interface IPort extends IConnection {		
	/**
	 * 获得对应的通道
	 * 
	 * @return 对应的通道
	 */
	public SelectableChannel channel();
	
	/**
	 * @return
	 * 		NIO事件处理器
	 */
	public INIOHandler getNIOHandler();
	
	/**
	 * @return
	 * 		发送队列中的下一个包，如果没有，返回null
	 */
	public OutPacket remove();
	
	/**
	 * 发送队列是否是空的
	 * 
	 * @return 是空的返回true, 否则返回false
	 */
	public boolean isEmpty();

	/**
	 * 接收一个包到接收队列
	 * 
	 * @return 如果还有数据可以接收返回true, 否则返回false.
	 * @throws IOException
	 *                  一般IO错误.
	 * @throws PacketParseException
	 *                  包格式错误.
	 */
	public void receive() throws IOException, PacketParseException;

	/**
	 * 从发送队列发送一个包.
	 * 
	 * @return 如果还有包需要发送返回true, 否则返回false.
	 * @throws IOException
	 *                  一般IO错误.
	 */
	public void send() throws IOException;
	
	/**
	 * 立即发送一个包，不添加到发送队列
	 * @param packet
	 */
	public void send(OutPacket packet);
	
	/**
	 * 立即发送ByteBuffer中的内容，不添加到发送队列
	 * @param buffer
	 */
	public void send(ByteBuffer buffer);
    
    /**
     * @return true表示已经连接上
     */
    public boolean isConnected();
}