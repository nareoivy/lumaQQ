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
import java.util.List;
import edu.tsinghua.lumaqq.qq.QQ;
import edu.tsinghua.lumaqq.qq.beans.QQUser;
import edu.tsinghua.lumaqq.qq.packets.PacketParseException;
import edu.tsinghua.lumaqq.qq.packets._03OutPacket;

/**
 * <pre>
 * 请求得到好友自定义头像信息
 * 1. 包头
 * 2. 未知4字节
 * 3. 未知4字节
 * 4. 后面内容的长度，2字节，exclusive
 * 5. 未知2字节，基本上是0x0100
 * 6. 请求的好友个数，2字节
 * Note: 6部分在QQ中最大值是20
 * 7. 好友QQ号，4字节
 * 8. 如果有更多好友，重复7部分
 * </pre>
 *
 * @author luma
 */
public class GetCustomHeadInfoPacket extends _03OutPacket {
	private List<Integer> qqList;
	
	private static int SERIAL_COUNT = 1;

	public GetCustomHeadInfoPacket(QQUser user) {
		super(QQ.QQ_03_CMD_GET_CUSTOM_HEAD_INFO, true, user);
		serialNumber = SERIAL_COUNT++;
	}

	public GetCustomHeadInfoPacket(ByteBuffer buf, int length, QQUser user) throws PacketParseException {
		super(buf, length, user);
	}
	
	@Override
	public String getPacketName() {
		return "Get Custom Head Info Packet";
	}

	@Override
	protected void putBody(ByteBuffer buf) {
		buf.putInt(0);
		buf.putInt(0);
		// 长度，留待回填
		buf.putChar((char)0);
		int pos = buf.position();
		// 未知
		buf.putChar((char)0x0100);
		buf.putChar((char)qqList.size());
		for(Integer qq : qqList)
			buf.putInt(qq);
		// 回填长度
		buf.putChar(pos - 2, (char)(buf.position() - pos));
	}

	/**
	 * @return the qqList
	 */
	public List<Integer> getQqList() {
		return qqList;
	}

	/**
	 * @param qqList the qqList to set
	 */
	public void setQqList(List<Integer> qqList) {
		this.qqList = qqList;
	}
}
