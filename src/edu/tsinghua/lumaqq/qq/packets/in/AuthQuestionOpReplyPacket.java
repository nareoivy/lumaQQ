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

import edu.tsinghua.lumaqq.qq.QQ;
import edu.tsinghua.lumaqq.qq.Util;
import edu.tsinghua.lumaqq.qq.beans.QQUser;
import edu.tsinghua.lumaqq.qq.packets.BasicInPacket;
import edu.tsinghua.lumaqq.qq.packets.PacketParseException;

/**
 * <pre>
 * 认证问题操作的回复包
 * 
 * ******** 格式1，得到认证问题的回复包 ********
 * 头部
 * ------- 加密开始，会话密钥 --------	
 * 子命令，1字节，0x03
 * 未知2字节，0x0001
 * 回复码，1字节 （如果回复码为0x00，继续后面的部分）
 * 问题长度，1字节
 * 问题
 * ------- 加密结束 --------
 * 尾部
 * 
 * ******** 格式2，回答问题的回复包 ********
 * 头部
 * ------- 加密开始，会话密钥 --------
 * 子命令，1字节，0x03
 * 未知2字节，0x0001
 * 回复码，1字节 （如果回复码为0x00，继续后面的部分）
 * 验证信息长度，2字节
 * 验证信息
 * ------- 加密结束 --------
 * 尾部
 * </pre>
 * 
 * @author luma
 */
public class AuthQuestionOpReplyPacket extends BasicInPacket {
	public byte subCommand;
	public byte replyCode;
	public byte[] authInfo;
	public String question;

	public AuthQuestionOpReplyPacket(ByteBuffer buf, int length, QQUser user) throws PacketParseException {
		super(buf, length, user);
	}

	@Override
	protected void parseBody(ByteBuffer buf) throws PacketParseException {
		subCommand = buf.get();
		buf.getChar();
		replyCode = buf.get();
		switch(subCommand) {
			case QQ.QQ_SUB_CMD_GET_QUESTION:
				if(replyCode == QQ.QQ_REPLY_OK) {
					int len = buf.get() & 0xFF;
					question = Util.getString(buf, len);
				}
				break;
			case QQ.QQ_SUB_CMD_ANSWER_QUESTION:
				if(replyCode == QQ.QQ_REPLY_OK) {
					int len = buf.getChar();
					authInfo = new byte[len];
					buf.get(authInfo);
				}
				break;
		}
	}
	
	@Override
	public String getPacketName() {
		return "Auth Question Op Reply Packet";
	}
}
