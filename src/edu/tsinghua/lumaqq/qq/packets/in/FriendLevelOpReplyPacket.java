/*
* LumaQQ - Java QQ Client
*
* Copyright (C) 2004 lxleaves_zhang
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

import edu.tsinghua.lumaqq.qq.beans.FriendLevel;
import edu.tsinghua.lumaqq.qq.beans.QQUser;
import edu.tsinghua.lumaqq.qq.packets.BasicInPacket;
import edu.tsinghua.lumaqq.qq.packets.PacketParseException;

/**
 * <pre>
 * 这个查询QQ号等级的应答包，格式是
 * 1. 头部
 * 2. 子命令，1字节
 * 3. 查询的号码, 4字节
 * 4. 活跃天数, 4字节
 * 5. 等级, 2字节
 * 6. 升级剩余天数, 2字节
 * 7. 如果有更多好友，重复3-6部分
 * 8. 尾部
 * </pre>
 *
 * @author 张松
 */
public class FriendLevelOpReplyPacket extends BasicInPacket {
    public List<FriendLevel> friendLevels;
    public byte subCommand;

    public FriendLevelOpReplyPacket(ByteBuffer buf, int length, QQUser user) throws PacketParseException {
        super(buf, length, user);
    }
    
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.packets.Packet#parseBody(java.nio.ByteBuffer)
     */
    @Override
    protected void parseBody(ByteBuffer buf) {
    	subCommand = buf.get();
        friendLevels = new ArrayList<FriendLevel>();
        while(buf.hasRemaining()) {
            FriendLevel friend = new FriendLevel();
            friend.readBean(buf);
            friendLevels.add(friend);
        }
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.packets.Packet#getPacketName()
     */
    @Override
    public String getPacketName() {
        return "Get Friend Level Reply Packet";
    }
}
