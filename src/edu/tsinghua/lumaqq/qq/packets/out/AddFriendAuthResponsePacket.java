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

import edu.tsinghua.lumaqq.qq.QQ;
import edu.tsinghua.lumaqq.qq.Util;
import edu.tsinghua.lumaqq.qq.beans.QQUser;
import edu.tsinghua.lumaqq.qq.packets.BasicOutPacket;
import edu.tsinghua.lumaqq.qq.packets.PacketParseException;


/**
 * <pre>
 * 这个包是用来处理添加好友需要认证的情况，格式为
 * 1. 头部
 * 2. 认证的目的对象的QQ号的字符串形式
 * 3. 分隔符1字节，0x1F
 * 4. 命令，是请求还是拒绝请求，还是同意请求，1字节
 * 5. 分隔符1字节，0x1F
 * 6. 附带的消息
 * 7. 尾部
 * </pre>
 *
 * @author luma
 */
public class AddFriendAuthResponsePacket extends BasicOutPacket {
    private static final byte DELIMIT = 0x1F;
    
    private byte action;
    private int to;
    private String message;
    
    public AddFriendAuthResponsePacket(QQUser user) {
        super(QQ.QQ_CMD_ADD_FRIEND_AUTH, true, user);
        message = "";
    }

    /**
     * @param buf
     * @param length
     * @throws PacketParseException
     */
    public AddFriendAuthResponsePacket(ByteBuffer buf, int length, QQUser user)
            throws PacketParseException {
        super(buf, length, user);
    }
    
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.packets.OutPacket#putBody(java.nio.ByteBuffer)
     */
	@Override
    protected void putBody(ByteBuffer buf) {
	    // 组装内容
	    // 目的的QQ号的字符串形式
	    buf.put(String.valueOf(to).getBytes());
	    // 分隔符
	    buf.put(DELIMIT);
	    // 响应码
	    buf.put(action);
	    // 分隔符
	    buf.put(DELIMIT);
	    // 附带消息
	    byte[] msg = Util.getBytes(message);
	    buf.put(msg);
    }    
    
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.packets.OutPacket#getPacketName()
     */
	@Override
    public String getPacketName() {
        return "Add Friend Auth Response Packet";
    }
    
    /**
     * @return Returns the message.
     */
    public String getMessage() {
        return message;
    }
    
    /**
     * @param message The message to set.
     */
    public void setMessage(String message) {
        this.message = message;
    }
    
    /**
     * @return Returns the to.
     */
    public int getTo() {
        return to;
    }
    
    /**
     * @param to The to to set.
     */
    public void setTo(int to) {
        this.to = to;
    }
    
    /**
     * @return Returns the type.
     */
    public byte getAction() {
        return action;
    }
    
    /**
     * @param action The type to set.
     */
    public void setAction(byte action) {
        this.action = action;
    }
}
