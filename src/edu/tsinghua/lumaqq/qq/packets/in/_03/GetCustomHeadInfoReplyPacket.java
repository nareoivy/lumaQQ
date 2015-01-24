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
package edu.tsinghua.lumaqq.qq.packets.in._03;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import edu.tsinghua.lumaqq.qq.beans.CustomHead;
import edu.tsinghua.lumaqq.qq.beans.QQUser;
import edu.tsinghua.lumaqq.qq.packets.PacketParseException;
import edu.tsinghua.lumaqq.qq.packets._03InPacket;

/**
 * <pre>
 * 请求自定义头像的回复包
 * 1. 包头
 * 2. 未知的4字节
 * 3. 未知的4字节
 * 4. 后面的内容长度，2字节，exclusive
 * 5. 未知的2字节，一般是0x0002
 * 6. 自定义头像信息个数，2字节
 * 7. 好友QQ号，4字节
 * 8. 自定义头像时间戳，4字节
 * 9. 自定义头像MD5，16字节
 * 10. 如果有更多自定义头像信息，重复7-9部分
 * </pre>
 *
 * @author luma
 */
public class GetCustomHeadInfoReplyPacket extends _03InPacket {
	public List<CustomHead> heads;

	public GetCustomHeadInfoReplyPacket(ByteBuffer buf, int length, QQUser user) throws PacketParseException {
		super(buf, length, user);
	}
	
	@Override
	public String getPacketName() {
		return "Get Custom Head Info Reply Packet";
	}

	@Override
	protected void parseBody(ByteBuffer buf) throws PacketParseException {
		buf.getInt();
		buf.getInt();
		buf.getChar();
		buf.getChar();
		int count = buf.getChar();
		heads = new ArrayList<CustomHead>();
		while(count > 0 && buf.hasRemaining()) {
			CustomHead head = new CustomHead();
			head.readBean(buf);
			heads.add(head);
		}
	}
}
