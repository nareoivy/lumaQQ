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

import edu.tsinghua.lumaqq.qq.beans.QQUser;
import edu.tsinghua.lumaqq.qq.packets.BasicInPacket;
import edu.tsinghua.lumaqq.qq.packets.PacketParseException;

/**
 * <pre>
 * Keep Alive的应答包，格式为
 * 头部
 * ----------- 加密开始(会话密钥) --------
 * 回复码，1字节
 * 在线人数，4字节
 * 我的ip，4字节
 * 我的端口，2字节
 * 未知2字节
 * 当前时间，4字节
 * 未知4字节
 * 未知1字节
 * ----------- 加密结束 -----------
 * 尾部
 * </pre>
 *
 * @author luma
 */
public class KeepAliveReplyPacket extends BasicInPacket {
	public byte replyCode;
	public int time;
	public int onlines;
	public byte[] ip;
	public int port;
	
    /**
     * 构造函数
     * @param buf 缓冲区
     * @param length 包长度
     * @throws PacketParseException 解析错误
     */
    public KeepAliveReplyPacket(ByteBuffer buf, int length, QQUser user) throws PacketParseException {
        super(buf, length, user);
    }   
    
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.packets.InPacket#getPacketName()
     */
	@Override
    public String getPacketName() {
        return "Keep Alive Reply Packet";
    }
	
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.packets.InPacket#parseBody(java.nio.ByteBuffer)
     */
	@Override
    protected void parseBody(ByteBuffer buf) throws PacketParseException {
		replyCode = buf.get();
		onlines = buf.getInt();
		ip = new byte[4];
		buf.get(ip);
		port = buf.getChar();
		buf.getChar();
		time = buf.getInt();
    }
}
