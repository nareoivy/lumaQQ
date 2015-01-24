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
import edu.tsinghua.lumaqq.qq.beans.QQUser;
import edu.tsinghua.lumaqq.qq.packets.PacketParseException;

/**
 * <pre>
 * 发送认证信息的包，格式为：
 * 1. 头部
 * 2. 群命令类型，1字节，认证消息是0x8
 * 3. 群内部ID，4字节
 * 4. 认证消息的类型，比如是请求，拒绝还是同意，1字节
 * 5. 接收者QQ号，4字节，如果是请求加入一个群，这个字段没有用处，为全0
 * 6. 附加消息的长度，1字节
 * 7. 附加消息
 * 8. 尾部
 * </pre>
 * 
 * @author luma
 */
public class ClusterAuthPacket extends ClusterCommandPacket {
	public int type;
	private String message;
	private int receiver;
	
    /**
     * 构造函数
     */
    public ClusterAuthPacket(QQUser user) {
        super(user);
		this.subCommand = QQ.QQ_CLUSTER_CMD_JOIN_CLUSTER_AUTH;
		this.message = "";
		this.receiver = 0;
    }

    /**
     * @param buf
     * @param length
     * @throws PacketParseException
     */
    public ClusterAuthPacket(ByteBuffer buf, int length, QQUser user)
            throws PacketParseException {
        super(buf, length, user);
    }
    
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.packets.OutPacket#getPacketName()
     */
	@Override
    public String getPacketName() {
        return "Cluster Auth Packet";
    }
    
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.packets.OutPacket#putBody(java.nio.ByteBuffer)
     */
	@Override
    protected void putBody(ByteBuffer buf) {
		// 群命令类型
		buf.put(subCommand);
		// 群内部ID
		buf.putInt(clusterId);
		// 认证消息类型
		buf.put((byte)type);
		// 接收者QQ号
		buf.putInt(receiver);
		// 附加消息长度
		byte[] b = message.getBytes();
		buf.put((byte)(b.length & 0xFF));
		// 附加消息
		buf.put(b);
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
	 * @return Returns the type.
	 */
	public int getType() {
		return type;
	}
	
	/**
	 * @param type The type to set.
	 */
	public void setType(int type) {
		this.type = type;
	}
    /**
     * @return Returns the receiver.
     */
    public int getReceiver() {
        return receiver;
    }
    /**
     * @param receiver The receiver to set.
     */
    public void setReceiver(int receiver) {
        this.receiver = receiver;
    }
}
