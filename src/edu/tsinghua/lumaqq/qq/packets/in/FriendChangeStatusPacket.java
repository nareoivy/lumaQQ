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
 * 好友状态改变包，这个是从服务器发来的包，格式为
 * 1. 头部
 * 2. 好友QQ号，4字节
 * 3. 未知的4字节
 * 4. 未知的4字节
 * 5. 好友改变到的状态，1字节
 * 6. 好友的客户端版本，2字节。这个版本号不是包头中的source，是内部表示，比如2004是0x04D1
 * 7. 未知用途的密钥，16字节
 * 8. 用户属性标志，4字节
 * 9. 我自己的QQ号，4字节
 * 10. 未知的2字节
 * 11. 未知的1字节
 * 12. 尾部
 * </pre>
 *
 * @author luma
 */
public class FriendChangeStatusPacket extends BasicInPacket {
	public int friendQQ;
	public int myQQ;
	public byte status;
	public int userFlag;
	public byte[] unknownKey;
	public char clientVersion;
	
    /**
     * 构造函数
     * @param buf 缓冲区
     * @param length 包长度
     * @throws PacketParseException 解析错误
     */
    public FriendChangeStatusPacket(ByteBuffer buf, int length, QQUser user) throws PacketParseException {
        super(buf, length, user);
    }    
    
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.packets.OutPacket#getPacketName()
     */
	@Override
    public String getPacketName() {
        return "Friend Change Status Packet";
    }
    
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.packets.InPacket#parseBody(java.nio.ByteBuffer)
     */
	@Override
    protected void parseBody(ByteBuffer buf) throws PacketParseException {
		friendQQ = buf.getInt();
		buf.getInt();
		buf.getInt();
		status = buf.get();
		clientVersion = buf.getChar();
		unknownKey = new byte[QQ.QQ_LENGTH_KEY];
		buf.get(unknownKey);
		userFlag = buf.getInt();
		myQQ = buf.getInt();
		buf.getChar();
		buf.get();
    }
}
