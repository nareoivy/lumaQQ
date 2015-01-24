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
import java.util.List;

import edu.tsinghua.lumaqq.qq.QQ;
import edu.tsinghua.lumaqq.qq.beans.QQUser;
import edu.tsinghua.lumaqq.qq.packets.PacketParseException;

/**
 * <pre>
 * 修改群成员的请求包，格式为：
 * 1. 头部
 * 2. 命令类型，1字节，修改群成员是0x02
 * 3. 群内部id，4字节
 * 4. 操作类型，删除还是添加
 * 5. 删除或添加的成员QQ号，每个4字节，如果我即删除又添加了，需要发两个包
 * 6. 尾部
 * </pre>
 * 
 * @author luma
 */
public class ClusterModifyMemberPacket extends ClusterCommandPacket {
	private List<Integer> members;
	private byte operation;
	
	public ClusterModifyMemberPacket(QQUser user) {
		super(user);
		subCommand = QQ.QQ_CLUSTER_CMD_MODIFY_MEMBER;
	}	

	/**
	 * @param buf
	 * @param length
	 * @throws PacketParseException
	 */
	public ClusterModifyMemberPacket(ByteBuffer buf, int length, QQUser user)
			throws PacketParseException {
		super(buf, length, user);
	}
    
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.packets.OutPacket#getPacketName()
     */
	@Override
    public String getPacketName() {
        return "Cluster Modify Member Packet";
    }
	
	/* (non-Javadoc)
	 * @see edu.tsinghua.lumaqq.qq.packets.OutPacket#putBody(java.nio.ByteBuffer)
	 */
	@Override
	protected void putBody(ByteBuffer buf) {
		buf.put(subCommand);
		buf.putInt(clusterId);
		buf.put(operation);
		for(int i : members)
			buf.putInt(i);
	}
	
	/**
	 * @return Returns the operation.
	 */
	public byte getOperation() {
		return operation;
	}
	
	/**
	 * @param operation The operation to set.
	 */
	public void setOperation(byte operation) {
		this.operation = operation;
	}

	/**
	 * @return Returns the members.
	 */
	public List<Integer> getMembers() {
		return members;
	}

	/**
	 * @param members The members to set.
	 */
	public void setMembers(List<Integer> members) {
		this.members = members;
	}
}
