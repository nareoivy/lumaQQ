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
package edu.tsinghua.lumaqq.qq.packets.in;

import java.nio.ByteBuffer;

import edu.tsinghua.lumaqq.qq.beans.QQUser;
import edu.tsinghua.lumaqq.qq.packets.BasicInPacket;
import edu.tsinghua.lumaqq.qq.packets.PacketParseException;

/**
 * <pre>
 * 发送验证消息的回复包
 * 1. 头部
 * 2. 子命令，1字节
 * 3. 要添加的QQ号，4字节
 * 4. 回复码，1字节
 * 5. 尾部
 * </pre>
 * 
 * @author luma
 */
public class AuthorizeReplyPacket extends BasicInPacket {
	public byte subCommand;
	public int to;
	public byte replyCode;

	public AuthorizeReplyPacket(ByteBuffer buf, int length, QQUser user) throws PacketParseException {
		super(buf, length, user);
	}

	@Override
	protected void parseBody(ByteBuffer buf) throws PacketParseException {
		subCommand = buf.get();
		to = buf.getInt();
		replyCode = buf.get();
	}
	
	@Override
	public String getPacketName() {
		return "Authorize Reply Packet";
	}
}
