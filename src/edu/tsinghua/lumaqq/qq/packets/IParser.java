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
package edu.tsinghua.lumaqq.qq.packets;

import java.nio.ByteBuffer;

import edu.tsinghua.lumaqq.qq.beans.QQUser;

/**
 * 包解析器
 * 
 * @author luma
 */
public interface IParser {    
	/**
	 * 判断此parser是否可以处理这个包，判断不能影响到buf的指针位置
	 * 
	 * @param buf
	 * 		ByteBuffer
	 * @return
	 * 		true表示这个parser可以处理这个包
	 */
	public boolean accept(ByteBuffer buf);
	
	/**
	 * @param buf TODO
	 * @return
	 * 		包的总长度
	 */
	public int getLength(ByteBuffer buf);
	
	/**
	 * 从buf当前位置解析出一个输入包对象，解析完毕后指针位于length之后
	 * 
	 * @param buf
	 * 		ByteBuffer
	 * @param length
	 * 		包长度
	 * @param user
	 * 		QQ用户对象
	 * @return
	 * 		InPacket子类，如果解析不了返回null
	 * @throws PacketParseException
	 */
	public InPacket parseIncoming(ByteBuffer buf, int length, QQUser user) throws PacketParseException;
	
	/**
	 * 从buf当前位置解析出一个输出包对象，解析完毕后指针位于length之后
	 * 
	 * @param buf
	 * 		ByteBuffer
	 * @param length
	 * 		包长度
	 * @param user
	 * 		QQ用户对象
	 * @return
	 * 		OutPacket子类，如果解析不了，返回null
	 * @throws PacketParseException
	 */
	public OutPacket parseOutcoming(ByteBuffer buf, int length, QQUser user) throws PacketParseException;
	
	/**
	 * 检查这个输入包是否重复
	 * 
	 * @param in
	 * @return
	 * 		true表示重复
	 */
	public boolean isDuplicate(InPacket in);
	
	/** 
	 * @param in
	 * 		收到的包
	 * @return
	 * 		true表示即使这个包是重复包也要回复
	 */
	public boolean isDuplicatedNeedReply(InPacket in);
	
	/**
	 * 假设buf的当前位置处是一个包，返回下一个包的起始位置。这个方法
	 * 用来重新调整buf指针。如果无法重定位，返回当前位置
	 * 
	 * @param buf
	 * 		ByteBuffer
	 * @return
	 * 		下一个包的起始位置
	 */
	public int relocate(ByteBuffer buf);
	
	/**
	 * @return
	 * 		PacketHistory类
	 */
	public PacketHistory getHistory();
}
