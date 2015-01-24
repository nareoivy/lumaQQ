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

import edu.tsinghua.lumaqq.qq.beans.QQUser;
import edu.tsinghua.lumaqq.qq.packets.PacketParseException;
import edu.tsinghua.lumaqq.qq.packets._05InPacket;

/**
 * <pre>
 * 请求开始传送的回复包
 * 1. 头部
 * 2. 未知的8字节，和请求包一致
 * 3. session id, 4字节
 * -------- 加密开始 --------
 * 4. 未知1字节，请求发送时是0x04，请求接收时是0x00
 * 5. 未知1字节，请求发送时是0x4C，请求接收时是0x00
 * 6. 未知2字节，全0
 * 7. 未知2字节，0xC350
 * 8. 未知2字节，0xEA60
 * 9. 未知4字节
 * -------- 加密结束 --------
 * 10. 尾部
 * </pre>
 * 
 * @author luma
 */
public class RequestBeginReplyPacket extends _05InPacket {
    public int sessionId;
    
    /**
     * @param buf
     * @param length
     * @param user
     * @throws PacketParseException
     */
    public RequestBeginReplyPacket(ByteBuffer buf, int length, QQUser user) throws PacketParseException {
        super(buf, length, user);
    }
    
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.packets._05InPacket#getPacketName()
     */
	@Override
    public String getPacketName() {
        return "Request Begin Reply Packet";
    }
	
	@Override
	protected int getEncryptStart() {
		return 12;
	}
	
	@Override
	protected int getDecryptStart() {
		return 12;
	}
	
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.packets.Packet#parseBody(java.nio.ByteBuffer)
     */
	@Override
    protected void parseBody(ByteBuffer buf) throws PacketParseException {
        buf.getLong();
        sessionId = buf.getInt();
    }
}
