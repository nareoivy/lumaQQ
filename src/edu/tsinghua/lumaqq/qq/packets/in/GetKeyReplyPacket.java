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
 * 请求密钥的回复包，格式为:
 * 1. 头部
 * 2. 子命令，1字节
 * 3. 未知字节，应该是回复码，0表示成功
 * 4. 密钥，16字节
 * 5. 未知的8字节
 * 6. 未知的4字节
 * 7. 文件中转认证令牌字节长度
 * 8. 令牌
 * 9. 未知的4字节
 * 10. 尾部
 * </pre>
 *
 * @author luma
 */
public class GetKeyReplyPacket extends BasicInPacket {
    public byte[] key;
    public byte[] token;
    public byte subCommand;
    public byte replyCode;
    
    /**
     * 构造函数
     * @param buf 缓冲区
     * @param length 包长度
     * @throws PacketParseException 解析错误
     */
    public GetKeyReplyPacket(ByteBuffer buf, int length, QQUser user) throws PacketParseException {
        super(buf, length, user);
    }   
    
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.packets.InPacket#getPacketName()
     */
	@Override
    public String getPacketName() {
        return "Request Key Reply Packet";
    }
    
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.packets.InPacket#parseBody(java.nio.ByteBuffer)
     */
	@Override
    protected void parseBody(ByteBuffer buf) throws PacketParseException {
        // 子命令
        subCommand = buf.get();
        // 回复码
        replyCode = buf.get();
        if(replyCode == QQ.QQ_REPLY_OK) {
	        // 密钥
	        key = new byte[QQ.QQ_LENGTH_KEY];
	        buf.get(key);
	        // 未知的8字节
	        // 未知的4字节
	        buf.position(buf.position() + 12);
	        // 文件中转认证令牌字节长度
	        int len = buf.get() & 0xFF;
	        // 令牌
	        token = new byte[len];
	        buf.get(token);            
        }
    }
}
