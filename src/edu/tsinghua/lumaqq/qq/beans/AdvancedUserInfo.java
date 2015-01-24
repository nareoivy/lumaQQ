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

import edu.tsinghua.lumaqq.qq.Util;

/**
 * 高级用户信息，这个结果由高级搜索返回
 * 
 * @author luma
 * @see edu.tsinghua.lumaqq.qq.packets.in.AdvancedSearchUserReplyPacket
 */
public class AdvancedUserInfo {
    public int qqNum;
    public int age;
    public int genderIndex;
    public boolean online;
    public String nick;
    public int provinceIndex;
    public int cityIndex;
    public int face;
    
    /**
     * 给定一个输入流，解析AdvancedUserInfo结构
     * @param buf
     */
	public void readBean(ByteBuffer buf) {
	    // QQ号
	    qqNum = buf.getInt();
	    // 性别
	    genderIndex = buf.get() & 0xFF;
	    // 年龄
	    age = buf.getChar();
	    // 是否在线
	    online = buf.get() != 0x00;
	    // 昵称
	    int len = buf.get() & 0xFF;
	    nick = Util.getString(buf, len);
	    // 省
	    provinceIndex = buf.getChar();
	    // 城市 
	    cityIndex = buf.getChar();
	    // 头像
	    face = buf.getChar();
	    // 未知1字节
	    buf.get();
	}
}
