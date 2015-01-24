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
 * 好友状态结构
 * 
 * @author luma
 */
public class FriendStatus {
    // QQ号
	public int qqNum;
	// 未知
	public byte unknown1;
	// 好友IP
	public byte[] ip;
	// 好友端口
	public int port;
	// 未知
	public byte unknown2;
	// 好友状态，定义在QQ接口中
	public byte status;
	// 未知
	public char unknown3;
	// 未知的密钥，会不会是用来加密和好友通讯的一些信息的，比如点对点通信的时候
	public byte[] unknownKey;
	
	/**
	 * @return true如果好友为正常在线状态
	 */
	public boolean isOnline() {
		return status == QQ.QQ_STATUS_ONLINE;
	}
	
	/**
	 * @return true如果好友为离开状态
	 */
	public boolean isAway() {
		return status == QQ.QQ_STATUS_AWAY;
	}
	
    /**
     * 给定一个输入流，解析FriendStatus结构
     * @param buf ByteBuffer对象
     */
	public void readBean(ByteBuffer buf) {
		// 000-003: 好友QQ号
	    qqNum = buf.getInt();
		// 004: 0x01，未知含义
	    unknown1 = buf.get();
		// 005-008: 好友IP
		ip = new byte[4];
		buf.get(ip);
		// 009-010: 好友端口
		port = buf.getChar();
		// 011: 0x01，未知含义
		unknown2 = buf.get();
		// 012: 好友状态
		status = buf.get();
		// 013-014: 未知含义
		unknown3 = buf.getChar();
		// 015-030: key，未知含义
		unknownKey = new byte[QQ.QQ_LENGTH_KEY];
		buf.get(unknownKey);
	}
}
