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

import edu.tsinghua.lumaqq.qq.QQ;
import edu.tsinghua.lumaqq.qq.Util;

/**
 * 单条短信的回复信息，针对一个接受者
 * 
 * @author luma
 * @see edu.tsinghua.lumaqq.qq.packets.in.SendSMSReplyPacket
 */
public class SMSReply {
	// true表示接受者是QQ号
	public boolean isQQ;
	public String message;
	public int qq; // 仅当isQQ为true时有效
	public String mobile; // 仅当isQQ为false时有效
	public byte replyCode;
	public byte unknown;
	
	/**
	 * 读取回复信息，接受者类型是手机号码
	 * 
	 * @param buf
	 */
	public void readMobileBean(ByteBuffer buf) {
		isQQ = false;
		mobile = Util.getString(buf, (byte)0, QQ.QQ_MAX_SMS_MOBILE_LENGTH);
		buf.getChar();
		replyCode = buf.get();
		int len = buf.get() & 0xFF;
		message = Util.getString(buf, len);
		unknown = buf.get();
	}
	
	/**
	 * 读取回复信息，接受者是一个QQ号
	 * 
	 * @param buf
	 */
	public void readQQBean(ByteBuffer buf) {
		isQQ = true;
		qq = buf.getInt();
		replyCode = buf.get();
		int len = buf.get() & 0xFF;
		message = Util.getString(buf, len);
		unknown = buf.get();
	}
}
