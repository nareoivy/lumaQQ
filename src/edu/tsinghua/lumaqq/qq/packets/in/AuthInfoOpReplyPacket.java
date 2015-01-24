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
import edu.tsinghua.lumaqq.qq.beans.QQUser;
import edu.tsinghua.lumaqq.qq.packets.BasicInPacket;
import edu.tsinghua.lumaqq.qq.packets.PacketParseException;


/**
 * <pre>
 * 这个请求验证信息的应答包，格式是
 * 
 * ************ 格式1，请求验证信息的回复包 **********
 * 头部
 * -------- 加密开始（会话密钥）--------
 * 子命令，1字节，0x01
 * 未知2字节，0x0001
 * 回复码，1字节
 * 验证信息长度，2字节
 * 验证信息
 * -------- 加密结束 ----------
 * 尾部
 * 
 * ************ 格式2，提交验证信息的回复包 **********
 * 头部
 * -------- 加密开始（会话密钥）--------
 * 子命令，1字节，0x02
 * 未知2字节，0x0001
 * 回复码，1字节
 * 如果回复码是0x00, 继续
 * auth info length, 2 bytes
 * auth info
 * -------- 加密结束 ----------
 * 尾部
 * </pre>
 *
 * @author luma
 */
public class AuthInfoOpReplyPacket extends BasicInPacket {
	public byte subCommand;
    public byte replyCode;
    public byte[] authInfo;
    
    /**
     * 构造函数
     * @param buf 缓冲区
     * @param length 包长度
     * @throws PacketParseException 解析错误
     */
    public AuthInfoOpReplyPacket(ByteBuffer buf, int length, QQUser user) throws PacketParseException {
        super(buf, length, user);
    }
    
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.packets.OutPacket#getPacketName()
     */
	@Override
    public String getPacketName() {
        return "Get Auth Info Reply Packet";
    }
    
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.packets.InPacket#parseBody(java.nio.ByteBuffer)
     */
	@Override
    protected void parseBody(ByteBuffer buf) throws PacketParseException {
		subCommand = buf.get();
		buf.getChar();
		replyCode = buf.get();
		switch(subCommand) {
			case QQ.QQ_SUB_CMD_GET_AUTH_INFO:
				int len = buf.getChar();
				authInfo = new byte[len];
				buf.get(authInfo);
				break;
		}
    }
}
