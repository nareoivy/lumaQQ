/*
* LumaQQ - Java QQ Client
*
* Copyright (C) 2004 luma <stubma@163.com>
*                    notXX
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
package edu.tsinghua.lumaqq.qq.packets.in._05;

import java.nio.ByteBuffer;

import edu.tsinghua.lumaqq.qq.Util;
import edu.tsinghua.lumaqq.qq.beans.QQUser;
import edu.tsinghua.lumaqq.qq.packets.PacketParseException;
import edu.tsinghua.lumaqq.qq.packets._05InPacket;

/**
 * <pre>
 * 请求中转服务器的回复包，格式为
 * 1. 头部
 * 2. 未知的8字节，和请求包保持相同
 * ------ 加密开始 -------
 * 3. 回复码，2字节
 * 4. 被请求的服务器IP，4字节，little-endian
 * 5. 被请求的服务器端口，2字节
 * 6. 会话ID，重定向时为0
 * 7. 重定向IP，4字节，little-endian
 * 8. 重定向的port，2字节
 * 9. 后面的消息长度，2字节
 * 10. 消息内容
 * ------ 加密结束 -------
 * 11. 尾部
 * </pre>
 *  
 * @author luma
 */
public class RequestAgentReplyPacket extends _05InPacket {
    public char replyCode;
    public byte[] redirectIp;
    public int redirectPort;
    public int sessionId;
    public String message;
    
    /**
     * @param buf
     * @param length
     * @param user
     * @throws PacketParseException
     */
    public RequestAgentReplyPacket(ByteBuffer buf, int length, QQUser user) throws PacketParseException {
        super(buf, length, user);
    }
    
    /* (non-Javadoc)
	 * @see edu.tsinghua.lumaqq.qq.packets._05InPacket#getPacketName()
	 */
	@Override
	public String getPacketName() {
		return "Request Agent Reply Packet";
	}
	
	@Override
	protected int getEncryptStart() {
		return 8;
	}
	
	@Override
	protected int getDecryptStart() {
		return 8;
	}

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.packets.Packet#parseBody(java.nio.ByteBuffer)
     */
	@Override
    protected void parseBody(ByteBuffer buf) throws PacketParseException {
        buf.getLong();

        replyCode = buf.getChar();
        // 原服务器IP
        buf.getInt();
        // 原服务器端口
        buf.getChar();
        // 会话ID
        sessionId = buf.getInt();
        // 重定向IP
        redirectIp = new byte[4];
        buf.get(redirectIp);
        // 重定向端口
        redirectPort = buf.getChar();        
        // 消息长度
        int len = buf.getChar();
        message = Util.getString(buf, len);
        
        // swap ip bytes
        byte temp = redirectIp[0];
        redirectIp[0] = redirectIp[3];
        redirectIp[3] = temp;
        temp = redirectIp[1];
        redirectIp[1] = redirectIp[2];
        redirectIp[2] = temp;
    }
}
