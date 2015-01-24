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
import edu.tsinghua.lumaqq.qq.beans.QQUser;
import edu.tsinghua.lumaqq.qq.packets.BasicOutPacket;
import edu.tsinghua.lumaqq.qq.packets.PacketParseException;


/**
 * <pre>
 * 收到消息之后我们发出的确认包
 * 1. 头部
 * 2. 消息发送者QQ号，4字节
 * 3. 消息接收者QQ号，4字节，也就是我
 * 4. 消息序号，4字节
 * 5. 发送者IP，4字节
 * 6. 尾部
 * </pre>
 *
 * @author luma
 */
public class ReceiveIMReplyPacket extends BasicOutPacket {
    private byte[] reply;
    
    /**
     * 构造函数
     */
    public ReceiveIMReplyPacket(byte[] reply, QQUser user) {
        super(QQ.QQ_CMD_RECV_IM, false, user);
        this.reply = reply;
        this.sendCount = 1;
    }

    /**
     * @param buf
     * @param length
     * @throws PacketParseException
     */
    public ReceiveIMReplyPacket(ByteBuffer buf, int length, QQUser user) throws PacketParseException {
        super(buf, length, user);
    }
    
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.packets.OutPacket#getPacketName()
     */
	@Override
    public String getPacketName() {
        return "Receive IM Reply Packet";
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.packets.OutPacket#putBody(java.nio.ByteBuffer)
     */
	@Override
    protected void putBody(ByteBuffer buf) {
        buf.put(reply);
    }
}
