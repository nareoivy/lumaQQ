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

import edu.tsinghua.lumaqq.qq.QQ;
import edu.tsinghua.lumaqq.qq.beans.FriendOnlineEntry;
import edu.tsinghua.lumaqq.qq.beans.QQUser;
import edu.tsinghua.lumaqq.qq.packets.BasicInPacket;
import edu.tsinghua.lumaqq.qq.packets.PacketParseException;


/**
 * <pre>
 * 得到在线好友列表的应答包，格式为
 * 1. 头部
 * 2. 在线好友是否已经全部得到，1字节
 * 3. 31字节的FriendStatus结构
 * 4. 2个未知字节
 * 5. 1个字节扩展标志
 * 6. 1个字节通用标志
 * 7. 2个未知字节
 * 8. 1个未知字节
 * 9. 如果有更多在线好友，重复2-8部分
 * 10. 尾部
 * 
 * 这个回复包最多返回30个在线好友，如果有更多，需要继续请求
 * </pre>
 *
 * @author luma
 */
public class GetOnlineOpReplyPacket extends BasicInPacket {
	// true表示没有更多在线好友了
	public boolean finished;
	// 下一个请求包的起始位置，仅当finished为true时有效
    public int position;
    // 在线好友bean列表
    public List<FriendOnlineEntry> onlineFriends;
    
    /**
     * 构造函数
     * @param buf 缓冲区
     * @param length 包长度
     * @throws PacketParseException 解析错误
     */
    public GetOnlineOpReplyPacket(ByteBuffer buf, int length, QQUser user) throws PacketParseException {
        super(buf, length, user);
    }
    
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.packets.OutPacket#getPacketName()
     */
	@Override
    public String getPacketName() {
        return "Get Friend Online Reply Packet";
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.packets.InPacket#parseBody(java.nio.ByteBuffer)
     */
	@Override
    protected void parseBody(ByteBuffer buf) throws PacketParseException {
	    // 当前好友列表位置
		finished = buf.get() == QQ.QQ_POSITION_ONLINE_LIST_END;
		position = 0;
        // 只要还有数据就继续读取下一个friend结构
        onlineFriends = new ArrayList<FriendOnlineEntry>();
        while(buf.hasRemaining()) {
            FriendOnlineEntry entry = new FriendOnlineEntry();
            entry.readBean(buf);
            
            // 添加到List
            onlineFriends.add(entry);
            
            // 如果还有更多好友，计算position
            if(!finished)
            	position = Math.max(position, entry.status.qqNum);
        }
        
        position++;
    }
}
