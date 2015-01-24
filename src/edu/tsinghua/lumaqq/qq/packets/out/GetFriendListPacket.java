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
 * 请求得到好友列表的包，其内容为
 * 1. 头部
 * 2. 两个字节的起始好友列表返回位置，注意这个起始位置是排序之后的
 *    加入你有9个好友，号码从10到100每隔10位一个，那么如果这两个数
 *    字的值指定为3，那10, 20, 30就不会返回了
 * 3. 返回的好友列表是否排序，1个字节，非0则排序
 * 4. 2个未知字节
 * 5. 尾部
 * </pre>
 *
 * @author luma
 */
public class GetFriendListPacket extends BasicOutPacket {
    // 好友列表开始位置，缺省是0
    private char startPosition;
    
    /**
     * 构造函数
     */
    public GetFriendListPacket(QQUser user) {
        super(QQ.QQ_CMD_GET_FRIEND_LIST, true, user);
		startPosition = QQ.QQ_POSITION_FRIEND_LIST_START;
    }
    
    /**
     * @param buf
     * @param length
     * @throws PacketParseException
     */
    public GetFriendListPacket(ByteBuffer buf, int length, QQUser user)
            throws PacketParseException {
        super(buf, length, user);
    }
    
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.packets.OutPacket#getPacketName()
     */
	@Override
    public String getPacketName() {
        return "Get Friend List Packet";
    }
    
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.packets.OutPacket#putBody(java.nio.ByteBuffer)
     */
	@Override
    protected void putBody(ByteBuffer buf) {
        buf.putChar(startPosition);
        buf.put(QQ.QQ_FRIEND_LIST_UNSORTED);
        buf.put((byte)0);
        buf.put((byte)1);
    }
    
    /**
     * @param startPosition The startPosition to set.
     */
    public void setStartPosition(char startPosition) {
        this.startPosition = startPosition;
    }
    
	/**
	 * @return Returns the startPosition.
	 */
	public char getStartPosition() {
		return startPosition;
	}
}
