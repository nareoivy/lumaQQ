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
import java.util.ArrayList;
import java.util.List;

import edu.tsinghua.lumaqq.qq.QQ;
import edu.tsinghua.lumaqq.qq.beans.QQUser;
import edu.tsinghua.lumaqq.qq.beans.UserProperty;
import edu.tsinghua.lumaqq.qq.packets.BasicInPacket;
import edu.tsinghua.lumaqq.qq.packets.PacketParseException;

/**
 * <pre>
 * 用户属性回复包
 * 1. 头部
 * 2. 子命令，1字节
 * 当2部分为0x01时：
 * 3. 下一个包的起始位置，2字节
 * 4. 6部分的长度，1字节
 * 5. QQ号，4字节
 * 6. 用户属性字节，已知位如下
 * 	  bit30 -> 是否有个性签名
 * 7. 如果有更多好友，重复5-6部分
 * Note: 当2部分为其他值时，尚未仔细解析过后面的格式，非0x01值一般出现在TM中
 * 8. 尾部
 * </pre>
 * 
 * @author luma
 */
public class UserPropertyOpReplyPacket extends BasicInPacket {
	public byte subCommand;
	public boolean finished;
	public char startPosition;
	public List<UserProperty> properties;

	public UserPropertyOpReplyPacket(ByteBuffer buf, int length, QQUser user) throws PacketParseException {
		super(buf, length, user);
	}
	
	@Override
	public String getPacketName() {
		return "User Property Op Reply Packet";
	}

	@Override
	protected void parseBody(ByteBuffer buf) throws PacketParseException {
		subCommand = buf.get();
		switch(subCommand) {
			case QQ.QQ_SUB_CMD_GET_USER_PROPERTY:
				startPosition = buf.getChar();
				finished = startPosition == QQ.QQ_POSITION_USER_PROPERTY_END;
				int pLen = buf.get() & 0xFF;
				properties = new ArrayList<UserProperty>();
				while(buf.hasRemaining()) {
					UserProperty p = new UserProperty(pLen);
					p.readBean(buf);
					properties.add(p);
				}				
				break;
		}
	}

}
