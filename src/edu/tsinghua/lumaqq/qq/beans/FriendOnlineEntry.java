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
package edu.tsinghua.lumaqq.qq.beans;

import java.nio.ByteBuffer;


/**
 * 请求在线好友列表的应答包中包含的数据结构
 *
 * @author luma
 * @see edu.tsinghua.lumaqq.qq.packets.in.GetOnlineOpReplyPacket
 */
public class FriendOnlineEntry {
    // 好友状态
    public FriendStatus status;
    /*
     * 用户属性标志
     * @see edu.tsinghua.lumaqq.qq.beans.QQFriend
     */ 
    public int userFlag;
    // 未知
    public char unknown2;
    // 未知字节
    public byte unknown3;
    
    /**
     * 给定一个输入流，解析FriendOnlineEntry结构
     * 
     * @param buf
     * 		ByteBuffer
     */
    public void readBean(ByteBuffer buf) {
        // 读取FriendStatus结构
        status = new FriendStatus();
        status.readBean(buf);
        // 用户属性标志
        userFlag = buf.getInt();
        // 2个未知字节
        unknown2 = buf.getChar();
        // 1个未知字节
        unknown3 = buf.get();
    }
}
