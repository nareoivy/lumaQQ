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
 * 讨论组操作请求：
 * 1. 头部
 * 2. 命令类型，1字节，0x36
 * 3. 子命令，1字节
 * 4. 根据3的不同，有：
 * 		i. 3为0x02(得到讨论组)时，4为群内部ID，4字节
 * 		ii. 3为0x01(得到多人对话)时，这里为0，4字节
 * 5. 尾部
 * </pre>
 * 
 * @author luma
 */
public class ClusterSubClusterOpPacket extends ClusterCommandPacket {
	private byte opByte;
	
	public ClusterSubClusterOpPacket(QQUser user) {
		super(user);
		subCommand = QQ.QQ_CLUSTER_CMD_SUB_CLUSTER_OP;
	}

	public ClusterSubClusterOpPacket(ByteBuffer buf, int length, QQUser user) throws PacketParseException {
		super(buf, length, user);
	}
	
	@Override
	public String getPacketName() {
		return "Cluster Subject Op Packet";
	}
	
	@Override
	protected void putBody(ByteBuffer buf) {
		// 命令类型
		buf.put(subCommand);
		// 子命令
		buf.put(opByte);
		switch(opByte) {
			case QQ.QQ_CLUSTER_SUB_CMD_GET_SUBJECT_LIST:
				buf.putInt(clusterId);
				break;
			case QQ.QQ_CLUSTER_SUB_CMD_GET_DIALOG_LIST:
				buf.putInt(0);
				break;
		}
	}

	/**
	 * @return Returns the opByte.
	 */
	public byte getOpByte() {
		return opByte;
	}

	/**
	 * @param opByte The opByte to set.
	 */
	public void setOpByte(byte opByte) {
		this.opByte = opByte;
	}
}
