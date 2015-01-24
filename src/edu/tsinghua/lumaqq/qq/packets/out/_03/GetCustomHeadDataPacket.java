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
package edu.tsinghua.lumaqq.qq.packets.out._03;

import java.nio.ByteBuffer;
import edu.tsinghua.lumaqq.qq.QQ;
import edu.tsinghua.lumaqq.qq.beans.QQUser;
import edu.tsinghua.lumaqq.qq.packets.PacketParseException;
import edu.tsinghua.lumaqq.qq.packets._03OutPacket;

/**
 * <pre>
 * 请求得到自定义头像数据:
 * 1. 包头
 * 2. 未知4字节
 * 3. 未知4字节
 * 4. 后面内容的长度，2字节，exclusive
 * 5. 好友QQ号，4字节
 * 6. 未知1字节，一般是0x01
 * 7. 自定义头像时间戳，4字节
 * 8. 从何处开始得到数据，4字节
 * Note: 初始时，置为0xFFFFFFFF表示从最开始请求
 * 9. 得到多少数据，4字节
 * Note: 请求时，设为0x00000000表示请求所有数据
 * </pre>
 *
 * @author luma
 */
public class GetCustomHeadDataPacket extends _03OutPacket {
	private int qq;
	private int timestamp;
	private int offset;
	private int length;	
	
	private static int SERIAL_COUNT = 1;
	
	public GetCustomHeadDataPacket(QQUser user) {
		super(QQ.QQ_03_CMD_GET_CUSTOM_HEAD_DATA, true, user);
		offset = 0xFFFFFFFF;
		length = 0;
		serialNumber = SERIAL_COUNT++;
	}
	
	public GetCustomHeadDataPacket(ByteBuffer buf, int length, QQUser user) throws PacketParseException {
		super(buf, length, user);
	}
	
	@Override
	public String getPacketName() {
		return "Get Custom Head Data Packet";
	}

	@Override
	protected void putBody(ByteBuffer buf) {
		buf.putInt(0);
		buf.putInt(0);
		
		buf.putChar((char)0);
		int pos = buf.position();
		
		buf.putInt(qq);
		buf.put((byte)0x01);
		buf.putInt(timestamp);
		buf.putInt(offset);
		buf.putInt(length);
		
		buf.putChar(pos - 2, (char)(buf.position() - pos));
	}

	/**
	 * @return the qq
	 */
	public int getQQ() {
		return qq;
	}

	/**
	 * @param qq the qq to set
	 */
	public void setQQ(int qq) {
		this.qq = qq;
	}

	/**
	 * @return the timestamp
	 */
	public int getTimestamp() {
		return timestamp;
	}

	/**
	 * @param timestamp the timestamp to set
	 */
	public void setTimestamp(int timestamp) {
		this.timestamp = timestamp;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}
}
