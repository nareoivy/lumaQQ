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
import edu.tsinghua.lumaqq.qq.Util;


/**
 * 好友的信息
 * 
 * @author luma
 * @see edu.tsinghua.lumaqq.qq.packets.in.GetFriendListReplyPacket
 */
public class QQFriend {
    // QQ号
    public int qqNum;
    // 头像，参看ContactInfo的头像注释
    public char head;
    // 年龄
    public byte age;
    // 性别
    public byte gender;
    // 昵称
    public String nick;
    // 用户属性标志
	// bit1 => 会员
	// bit5 => 移动QQ
	// bit6 => 绑定到手机
	// bit7 => 是否有摄像头
    // bit18 => 是否TM登录
    public int userFlag;
    
    /**
     * @return true如果好友是会员，否则为false
     */
    public boolean isMember() {
    	return (userFlag & QQ.QQ_FLAG_MEMBER) != 0;
    }
    
    /**
     * 是否绑定手机
     * 
     * @return
     */
    public boolean isBind() {
        return (userFlag & QQ.QQ_FLAG_BIND) != 0;
    }
    
    /**
     * @return
     * 		true表示是移动QQ
     */
    public boolean isMobile() {
    	return (userFlag & QQ.QQ_FLAG_MOBILE) != 0;
    }
    
    /**
     * @return
     * 		true表示用户有摄像头
     */
    public boolean hasCam() {
    	return (userFlag & QQ.QQ_FLAG_CAM) != 0;
    }
    
    /**
     * @return
     * 		true表示用户使用TM登录
     */
    public boolean isTM() {
    	return (userFlag & QQ.QQ_FLAG_TM) != 0;
    }
    
    /**
     * @return true如果是男性的话
     */
    public boolean isGG() {
    	return gender == QQ.QQ_GENDER_GG;
    }
    
    /**
     * 给定一个输入流，解析QQFriend结构
     * @param buf
     */
    public void readBean(ByteBuffer buf) {
        // 000-003: 好友QQ号
        qqNum = buf.getInt();
        // 004-005: 头像
        head = buf.getChar();
        // 006: 年龄
        age = buf.get();
        // 007: 性别
        gender = buf.get();
        // 008: 昵称长度
        int len = buf.get() & 0xFF;
        // 009-009+昵称长度: 昵称
        byte[] b = new byte[len];
        buf.get(b);
        nick = Util.filterUnprintableCharacter(Util.getString(b));
        // 用户属性
        userFlag = buf.getInt();
    }
}
