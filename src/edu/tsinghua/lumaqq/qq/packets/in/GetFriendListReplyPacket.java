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
import java.util.ArrayList;
import java.util.List;

import edu.tsinghua.lumaqq.qq.beans.QQFriend;
import edu.tsinghua.lumaqq.qq.beans.QQUser;
import edu.tsinghua.lumaqq.qq.packets.BasicInPacket;
import edu.tsinghua.lumaqq.qq.packets.PacketParseException;


/**
 * <pre>
 * 请求好友列表的应答包，格式为
 * 1. 头部
 * 2. 下一次好友列表开始位置，这个位置是你所有好友排序后的位置，如果为0xFFFF，那就是你的好友已经全部得到了
 *    每次都固定的返回50个好友，所以如果不足50个了，那么这个值一定是0xFFFF了
 * 3. 好友QQ号，4字节
 * 4. 头像，2字节
 * 5. 年龄，1字节
 * 6. 性别，1字节
 * 7. 昵称长度，1字节
 * 8. 昵称，不定字节，由8指定
 * 9. 用户标志字节，4字节
 * 10. 重复3-9的结构
 * 11.尾部
 * </pre>
 *
 * @author luma
 * @see edu.tsinghua.lumaqq.qq.beans.QQFriend
 */
public class GetFriendListReplyPacket extends BasicInPacket {
    public char position;
    public List<QQFriend> friends;
    
    /**
     * 构造函数
     * @param buf 缓冲区
     * @param length 包长度
     * @throws PacketParseException 解析错误
     */
    public GetFriendListReplyPacket(ByteBuffer buf, int length, QQUser user) throws PacketParseException {
        super(buf, length, user);
    }
    
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.packets.OutPacket#getPacketName()
     */
	@Override
    public String getPacketName() {
        return "Get Friend List Reply Packet";
    }
    
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.packets.InPacket#parseBody(java.nio.ByteBuffer)
     */
	@Override
    protected void parseBody(ByteBuffer buf) throws PacketParseException {
	    // 当前好友列表位置
		position = buf.getChar();
        // 只要还有数据就继续读取下一个friend结构
        friends = new ArrayList<QQFriend>();
        while(buf.hasRemaining()) {
            QQFriend friend = new QQFriend();
            friend.readBean(buf);
            
            // 添加到list
            friends.add(friend);
        }
    }
}
