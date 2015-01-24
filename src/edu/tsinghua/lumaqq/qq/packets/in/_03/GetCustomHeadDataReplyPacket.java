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
import edu.tsinghua.lumaqq.qq.QQ;
import edu.tsinghua.lumaqq.qq.beans.QQUser;
import edu.tsinghua.lumaqq.qq.packets.PacketParseException;
import edu.tsinghua.lumaqq.qq.packets._03InPacket;

/**
 * <pre>
 * 请求自定义头像数据的回复包
 * 1. 包头
 * 2. 未知的4字节
 * 3. 未知的4字节
 * 4. 后面的内容长度，2字节，exclusive
 * 5. 未知1字节，0x00
 * 6. 好友QQ号，4字节
 * 7. 自定义头像时间戳，4字节
 * 8. 自定义头像文件大小，4字节
 * 9. 数据的绝对偏移，4字节
 * 10. 数据的长度，4字节
 * 11. 数据
 *
 * Note: 一般来说，包的source字段是0x0000，如果是客户端版本号，则其没有包体内容。这到底是应该
 * 看成一种新的包还是看成一种错误情况，不好说。目前把它当作错误情况，source为客户端版本号时不解析
 * </pre>
 * 
 * @author luma
 */
public class GetCustomHeadDataReplyPacket extends _03InPacket {
	public int qq;
	public int timestamp;
	public int headLength;
	public int offset;
	public int dataLength;
	public byte[] data;

	public GetCustomHeadDataReplyPacket(ByteBuffer buf, int length, QQUser user) throws PacketParseException {
		super(buf, length, user);
	}
	
	@Override
	public String getPacketName() {
		return "Get Custom Head Data Reply Packet";
	}

	@Override
	protected void parseBody(ByteBuffer buf) throws PacketParseException {
		if(source != QQ.QQ_CLIENT_VERSION) {
			buf.getInt();
			buf.getInt();
			buf.getChar();
			buf.get();
			qq = buf.getInt();
			timestamp = buf.getInt();
			headLength = buf.getInt();
			offset = buf.getInt();
			dataLength = buf.getInt();
			data = new byte[dataLength];
			buf.get(data);			
		}
	}
}
