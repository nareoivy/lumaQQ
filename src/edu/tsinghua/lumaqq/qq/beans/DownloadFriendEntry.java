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

import edu.tsinghua.lumaqq.qq.QQ;

/**
 * <pre>
 * 这个Bean用在下载好友分组时
 * </pre>
 * 
 * @author luma
 * @see edu.tsinghua.lumaqq.qq.packets.in.DownloadGroupFriendReplyPacket
 */
public class DownloadFriendEntry {
	// 好友的QQ号，或者是群的内部ID
	public int qqNum;
	// 好友类型，是好友，还是群
	public byte type;
	// 好友所在的组
	public int group;
		
    /**
     * 给定一个字节流，解析DownloadFriendEntry结构
     * @param buf ByteBuffer对象
     */
	public void readBean(ByteBuffer buf) {
		// 好友QQ号或者群的内部ID
	    qqNum = buf.getInt();
		// 类型
	    type = buf.get();
		// 组
	    group = buf.get() >>> 2;
	}
	
	/**
	 * @return true表示这一项表示一个群 
	 */
	public boolean isCluster() {
		return type == QQ.QQ_ID_IS_CLUSTER;
	}
}
