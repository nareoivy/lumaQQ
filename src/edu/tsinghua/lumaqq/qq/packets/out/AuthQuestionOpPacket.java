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
package edu.tsinghua.lumaqq.qq.packets.out;

import java.nio.ByteBuffer;

import edu.tsinghua.lumaqq.qq.QQ;
import edu.tsinghua.lumaqq.qq.Util;
import edu.tsinghua.lumaqq.qq.beans.QQUser;
import edu.tsinghua.lumaqq.qq.packets.BasicOutPacket;
import edu.tsinghua.lumaqq.qq.packets.PacketParseException;

/**
 * <pre>
 * 认证问题操作命令
 * 
 * ******* 格式 *******
 * 子命令, 1字节，0x02, get my question
 * length of question, 1 byte
 * question
 * length of answer, 1 byte
 * answer
 * unknown 1 byte, 0x00
 * tail
 * 
 * ******* 格式1，得到认证问题 *******
 * 头部
 * -------- 加密开始，会话密钥 ---------
 * 子命令，1字节，0x03
 * 未知2字节，0x0001
 * 要得到认证问题的QQ号，4字节
 * -------- 加密结束 --------
 * 尾部
 * 
 * *********** 格式2，回答问题 ----------
 * 头部
 * -------- 加密开始，会话密钥 ---------
 * 子命令，1字节，0x04
 * 未知2字节，0x0001
 * 要得到认证问题的QQ号，4字节
 * 问题答案长度，1字节
 * 问题答案
 * -------- 加密结束 --------
 * 尾部
 * </pre>
 * 
 * @author luma
 */
public class AuthQuestionOpPacket extends BasicOutPacket {
	private byte subCommand;
	private int to;
	private String answer;
	
    /**
     * 构造函数
     */
    public AuthQuestionOpPacket(QQUser user) {
        super(QQ.QQ_CMD_AUTH_QUESTION_OP, true, user);
        subCommand = QQ.QQ_SUB_CMD_GET_QUESTION;
    }

    /**
     * @param buf
     * @param length
     * @throws PacketParseException
     */
    public AuthQuestionOpPacket(ByteBuffer buf, int length, QQUser user)
            throws PacketParseException {
        super(buf, length, user);
    }
    
	@Override
	protected void putBody(ByteBuffer buf) {
		buf.put(subCommand);
		buf.putChar((char)1);
		buf.putInt(to);
		switch(subCommand) {
			case QQ.QQ_SUB_CMD_ANSWER_QUESTION:
				byte[] b = Util.getBytes(answer);
				buf.put((byte)b.length);
				buf.put(b);
				break;
		}
	}
	
	@Override
	public String getPacketName() {
		return "Auth Question Op Packet";
	}

	public byte getSubCommand() {
		return subCommand;
	}

	public void setSubCommand(byte subCommand) {
		this.subCommand = subCommand;
	}

	public int getTo() {
		return to;
	}

	public void setTo(int to) {
		this.to = to;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}
}
