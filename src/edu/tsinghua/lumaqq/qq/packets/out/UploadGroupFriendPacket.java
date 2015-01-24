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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.tsinghua.lumaqq.qq.QQ;
import edu.tsinghua.lumaqq.qq.beans.QQUser;
import edu.tsinghua.lumaqq.qq.packets.BasicOutPacket;
import edu.tsinghua.lumaqq.qq.packets.PacketParseException;

/**
 * <pre>
 * 上传分组中好友列表的消息包，格式为
 * 1. 头部
 * 2. 好友的QQ号，4字节
 * 3. 好友所在的组序号，0表示我的好友组，自己添加的组从1开始
 * 4. 如果有更多好友，重复2，3部分
 * 5. 尾部
 * 
 * 并不需要每次都上传所有的好友，比如如果在使用的过程中添加了一个好友，那么
 * 可以只上传这个好友即可
 * </pre>
 * 
 * @author luma
 */
public class UploadGroupFriendPacket extends BasicOutPacket {
	private Map<Integer, List<Integer>> friends;
	
    /**
     * 构造函数
     */
    public UploadGroupFriendPacket(QQUser user) {
        super(QQ.QQ_CMD_UPLOAD_GROUP_FRIEND, true, user);
		friends = new HashMap<Integer, List<Integer>>();
    }
    
    /**
     * @param buf
     * @param length
     * @throws PacketParseException
     */
    public UploadGroupFriendPacket(ByteBuffer buf, int length, QQUser user)
            throws PacketParseException {
        super(buf, length, user);
    }
    
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.packets.OutPacket#getPacketName()
     */
	@Override
    public String getPacketName() {
        return "Upload Group Friend Packet";
    }
    
    /** 
     * @see edu.tsinghua.lumaqq.qq.packets.BasicOutPacket#putBody(java.nio.ByteBuffer)   
     */
	@Override
    protected void putBody(ByteBuffer buf) {
		// 写入每一个好友的QQ号和组序号
		int gSize = friends.size() + 1;
		for(int i = 0; i < gSize; i++) {
			List<Integer> gList = friends.get(i);
			// 等于null说明这是一个空组，不用处理了			
			if(gList != null) {
				for(Integer qq : gList) {
					buf.putInt(qq);
					buf.put((byte)i);					
				}				
			}
		}
    }
    
    /**
	 * 添加好友信息
	 * 
	 * @param gIndex 
	 * 		好友所在的组索引
	 * @param qqNum 
	 * 		好友的QQ号
	 */
	public void addFriend(int gIndex, int qqNum) {
		List<Integer> gList = null;
		if(friends.containsKey(gIndex))
			gList = friends.get(gIndex);
		else {
			gList = new ArrayList<Integer>();
			friends.put(gIndex, gList);
		}
		gList.add(qqNum);
	}
}
