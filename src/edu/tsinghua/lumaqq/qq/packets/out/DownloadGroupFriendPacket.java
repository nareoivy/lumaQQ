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
 * 请求下载分组好友列表的消息包，格式为
 * 1. 头部
 * 2. 操作字节标志，下载为0x1
 * 3. 未知字节0x2
 * 4. 未知4字节，全0
 * 5. 起始好友号，如果这是第一个包，全0，有时候一个包还下载不完所有的好友，在这里填上起始好友号
 * 6. 尾部
 * </pre>
 * 
 * @author luma
 */
public class DownloadGroupFriendPacket extends BasicOutPacket {
	private int beginFrom;
	
    /**
     * 构造函数
     */
    public DownloadGroupFriendPacket(QQUser user) {
        super(QQ.QQ_CMD_DOWNLOAD_GROUP_FRIEND, true, user);
        beginFrom = 0;
    }
    
    /**
     * @param buf
     * @param length
     * @throws PacketParseException
     */
    public DownloadGroupFriendPacket(ByteBuffer buf, int length, QQUser user)
            throws PacketParseException {
        super(buf, length, user);
    }
    
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.packets.OutPacket#getPacketName()
     */
	@Override
    public String getPacketName() {
        return "Download Group Friend Packet";
    }
    
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.packets.OutPacket#putBody(java.nio.ByteBuffer)
     */
	@Override
    protected void putBody(ByteBuffer buf) {
		// 操作字节
		buf.put((byte)1);
		// 未知字节0x2，未知的4字节0，未知的4字节0
		buf.put((byte)2);
		buf.putInt(0);
		buf.putInt(beginFrom);
    }
    
    public int getBeginFrom() {
		return beginFrom;
	}
	
	public void setBeginFrom(int beginFrom) {
		this.beginFrom = beginFrom;
	}
}
