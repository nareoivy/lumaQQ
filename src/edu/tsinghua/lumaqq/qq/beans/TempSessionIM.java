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
package edu.tsinghua.lumaqq.qq.beans;

import java.nio.ByteBuffer;

import edu.tsinghua.lumaqq.qq.Util;

/**
 * 临时会话消息Bean 
 *
 * @author luma
 * @see edu.tsinghua.lumaqq.qq.packets.in.ReceiveIMPacket
 */
public class TempSessionIM {
	public int sender;
	public String nick;
	public String site;
	public String message;
	public long time;
	public FontStyle fontStyle;
	
	/**
	 * 读取Bean
	 * 
	 * @param buf
	 */
	public void readBean(ByteBuffer buf) {
		// 发送者
		sender = buf.getInt();
		// 未知的4字节
		buf.getInt();
		// 昵称
		int len = buf.get() & 0xFF;
		nick = Util.getString(buf, len);
		// 群名称
		len = buf.get() & 0xFF;
		site = Util.getString(buf, len);
		// 未知的1字节
		buf.get();
		// 时间
		time = buf.getInt() * 1000L;
		// 后面的内容长度
		len = buf.getChar();
		// 得到字体属性长度，然后得到消息内容
		int fontStyleLength = buf.get(buf.position() + len - 1) & 0xFF;
		message = Util.getString(buf, len - fontStyleLength);
		// 字体属性
		fontStyle = new FontStyle();
		fontStyle.readBean(buf);
	}
}
