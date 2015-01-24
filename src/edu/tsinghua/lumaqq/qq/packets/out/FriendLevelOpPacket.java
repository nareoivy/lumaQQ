/*
* LumaQQ - Java QQ Client
*
* Copyright (C) 2004 lxleaves_zhang
* 					 luma <stubma@163.com>
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
import java.util.ArrayList;
import java.util.List;

import edu.tsinghua.lumaqq.qq.QQ;
import edu.tsinghua.lumaqq.qq.beans.QQUser;
import edu.tsinghua.lumaqq.qq.packets.BasicOutPacket;
import edu.tsinghua.lumaqq.qq.packets.PacketParseException;

/**
 * <pre>
 * 这个查询QQ号等级的包，格式是
 * 1. 头部
 * 2. 子命令，1字节
 * 3. 查询的号码，4字节
 * 4. 如果有更多好友，重复3部分
 * 5. 尾部
 * 
 * QQ的做法是一次最多请求70个。号码必须按照大小排序，本来之前不排序也可以，后来腾讯可能在服务器端动了些手脚，必须
 * 得排序了。这种顺序并没有在这个类中维护，所以是否排序目前是上层的责任，这个类假设收到的是一个排好序的用户QQ号
 * 列表
 * </pre>
 *
 * @author 张松
 * @author luma
 */
public class FriendLevelOpPacket extends BasicOutPacket {
    private List<Integer> friends;
    private byte subCommand;

    public FriendLevelOpPacket(ByteBuffer buf, int length, QQUser user) throws PacketParseException {
        super(buf, length, user);
    }

    public FriendLevelOpPacket(QQUser user) {
        super(QQ.QQ_CMD_FRIEND_LEVEL_OP, true, user);      
        subCommand = QQ.QQ_SUB_CMD_GET_FRIEND_LEVEL;
    }

    /*
     * @see edu.tsinghua.lumaqq.qq.packets.Packet#putBody(java.nio.ByteBuffer)
     */
    @Override
    protected void putBody(ByteBuffer buf) {
        buf.put(subCommand);
        for(int friend : friends) 
            buf.putInt(friend);
    }

    /*
     * (non-Javadoc)
     * 
     * @see edu.tsinghua.lumaqq.qq.packets.OutPacket#getPacketName()
     */
    @Override
    public String getPacketName() {
        return "Get Friends Level Packet";
    }

	/**
	 * @return Returns the subCommand.
	 */
	public byte getSubCommand() {
		return subCommand;
	}

	/**
	 * @param subCommand The subCommand to set.
	 */
	public void setSubCommand(byte subCommand) {
		this.subCommand = subCommand;
	}

	/**
	 * @return Returns the friends.
	 */
	public List<Integer> getFriends() {
		return friends;
	}

	/**
	 * @param friends The friends to set.
	 */
	public void setFriends(List<Integer> friends) {
		this.friends = friends;
	}
	
	/**
	 * 添加一个要获得等级的好友
	 * 
	 * @param qq
	 */
	public void addFriend(int qq) {
		if(friends == null)
			friends = new ArrayList<Integer>();
		friends.add(qq);
	}
}
