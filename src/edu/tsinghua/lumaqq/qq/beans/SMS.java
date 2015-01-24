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
 * <pre>
 * 短消息封装类
 * </pre>
 * 
 * @author luma
 * @see edu.tsinghua.lumaqq.qq.packets.in.ReceiveIMPacket
 */
public class SMS {
    public String message;
    public int sender;
    public int head;
    public long time;
    // 如果sender是10000，则senderName为手机号码
    public String senderName;
    
    /**
     * 给定一个输入流，解析SMS结构
     * @param buf
     */
    public void readBindUserSMS(ByteBuffer buf) {
        // 未知1字节，0x0
        buf.get();
        // 发送者QQ号，4字节
        sender = buf.getInt();
        // 发送者头像
        head = buf.getChar();
        // 发送者名称，最多13字节，不足后面补0
        senderName = Util.getString(buf, (byte)0, QQ.QQ_MAX_SMS_SENDER_NAME);
        // 未知的1字节，0x4D
        buf.get();
        // 消息内容
        message = Util.getString(buf, (byte)0);
        
        time = System.currentTimeMillis();
    }
    
    /**
     * 读取移动QQ用户的短信
     * @param buf
     */
    public void readMobileQQSMS(ByteBuffer buf) {
    	// 未知1字节
    	buf.get();
        // 发送者QQ号，4字节
        sender = buf.getInt();
        // 发送者头像
        head = buf.getChar();
        // 发送者名称，最多13字节，不足后面补0
        senderName = Util.getString(buf, (byte)0, QQ.QQ_MAX_SMS_SENDER_NAME);
        // 未知的1字节，0x4D
        buf.get();
        // 发送时间
        time = buf.getInt() * 1000L;
        // 未知的1字节，0x03
        buf.get();
        // 消息内容
        message = Util.getString(buf, (byte)0);
    }
    
    /**
     * 读取移动QQ用户消息（通过手机号描述）
     * 
     * @param buf
     */
    public void readMobileQQ2SMS(ByteBuffer buf) {
    	// 未知1字节
    	buf.get();
    	// 发送者，这种情况下都置为10000
		sender = 10000;
		// 手机号码
		senderName = Util.getString(buf, (byte)0, 18);
		// 未知2字节
		buf.getChar();
		// 时间
		time = buf.getInt() * 1000L;
        // 未知的1字节，0x03
        buf.get();
		// 消息内容
		message = Util.getString(buf, (byte)0);
    }
    
    /**
     * 读取普通手机的短信
     * 
     * @param buf
     */
    public void readMobileSMS(ByteBuffer buf) {
        // 未知1字节，0x0
        buf.get();
        // 发送者
        sender = 10000;
        // 手机号码
        senderName = Util.getString(buf, (byte)0, 20);
        // 消息内容
        message = Util.getString(buf, (byte)0);
        
        time = System.currentTimeMillis();
    }
}
