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
 * 设置成员角色的请求包
 * 1. 头部
 * 2. 群命令类型，1字节，0x1B
 * 3. 群内部ID，4字节
 * 4. 要设置角色的QQ号，4字节
 * 5. 操作码，0x00为删除管理员，0x01为设置管理员
 * 6. 尾部
 * </pre>
 * 
 * @author luma
 */
public class ClusterSetRolePacket extends ClusterCommandPacket {
	private byte opCode;
	private int qq;

    /**
     * 构造函数
     */
    public ClusterSetRolePacket(QQUser user) {
        super(user);
		this.subCommand = QQ.QQ_CLUSTER_CMD_SET_ROLE;
    }

    /**
     * @param buf
     * @param length
     * @throws PacketParseException
     */
    public ClusterSetRolePacket(ByteBuffer buf, int length, QQUser user)
            throws PacketParseException {
        super(buf, length, user);
    }
    
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.packets.OutPacket#getPacketName()
     */
	@Override
    public String getPacketName() {
        return "Cluster Set Role Packet";
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
		// 接收者QQ号
		buf.putInt(qq);
		// 操作
		buf.put(opCode);
    }

	/**
	 * @return Returns the qq.
	 */
	public int getQq() {
		return qq;
	}

	/**
	 * @param qq The qq to set.
	 */
	public void setQq(int qq) {
		this.qq = qq;
	}

	/**
	 * @return Returns the opCode.
	 */
	public byte getOpCode() {
		return opCode;
	}

	/**
	 * @param opCode The opCode to set.
	 */
	public void setOpCode(byte opCode) {
		this.opCode = opCode;
	}
}
