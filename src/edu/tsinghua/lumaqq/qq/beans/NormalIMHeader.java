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
 * 普通消息的头部，普通消息是指从好友或者陌生人那里来的消息。什么不是普通消息？比如系统消息
 * 这个头部跟在ReceiveIMHeader之后
 *
 * @author luma
 */
public class NormalIMHeader {
    public char senderVersion;
    public int sender;
    public int receiver;
    public byte[] fileSessionKey;
    public char type;
    public char sequence;
    public long sendTime;
    public char senderHead;
    
    /**
     * 给定一个输入流，解析NormalIMHeader结构
     * @param buf
     */
    public void readBean(ByteBuffer buf) {
        // 发送者的QQ版本
        senderVersion = buf.getChar();
        // 发送者的QQ号
        sender = buf.getInt();
        // 接受者的QQ号
        receiver = buf.getInt();
        // md5处理的发送方的uid和session key，用来在传送文件时加密一些消息
        fileSessionKey = new byte[16];
        buf.get(fileSessionKey);
        // 普通消息类型，比如是文本消息还是其他什么消息
        type = buf.getChar();
        // 消息序号
        sequence = buf.getChar();
        // 发送时间
        sendTime = buf.getInt() * 1000L;
        // 发送者头像
        senderHead = buf.getChar();
    }
    
    @Override
    public String toString() {
    	StringBuilder temp = new StringBuilder(30);
    	temp.append("NormalIMHeader [");
    	temp.append("senderVersion(char): ");
    	temp.append((int)senderVersion);
    	temp.append(", ");
    	temp.append("sender: ");
    	temp.append(sender);
    	temp.append(", ");
    	temp.append("receiver: ");
    	temp.append(receiver);
    	temp.append(", ");
    	temp.append("type(char): ");
    	temp.append((int)type);
    	temp.append(", ");
    	temp.append("sequence(char): ");
    	temp.append((int)sequence);
    	temp.append(", ");
    	temp.append("sendTime: ");
    	temp.append(sendTime);
    	temp.append(", ");
    	temp.append("senderHead(char): ");
    	temp.append((int)senderHead);
    	temp.append("]");
    	return temp.toString();
    }
}
