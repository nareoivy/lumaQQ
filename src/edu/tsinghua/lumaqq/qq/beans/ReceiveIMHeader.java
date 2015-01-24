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
 * 接收到的消息的头部格式封装类
 *
 * @author luma
 * @see edu.tsinghua.lumaqq.qq.packets.in.ReceiveIMPacket
 */
public class ReceiveIMHeader {
    public int sender;
    public int receiver;
    public int sequence;
    public byte[] senderIp;
    public int senderPort;
    public char type; 
    
    /**
     * 给定一个输入流，解析ReceiveIMHeader结构
     * @param buf
     */
    public void readBean(ByteBuffer buf) {
        // 发送者QQ号或者群内部ID
        sender = buf.getInt();
        // 接收者QQ号
        receiver = buf.getInt();
        // 包序号，这个序号似乎和我们发的包里面的序号不同，至少这个是int，我们发的是char
        //     可能这个序号是服务器端生成的一个总的消息序号
        sequence = buf.getInt();
        // 发送者IP，如果是服务器转发的，那么ip就是服务器ip
        senderIp = new byte[4];
        buf.get(senderIp);
        // 发送者端口，如果是服务器转发的，那么就是服务器的端口
        senderPort = buf.getChar();
        // 消息类型，是好友发的，还是陌生人发的，还是系统消息等等
        type = buf.getChar();
    }
}
