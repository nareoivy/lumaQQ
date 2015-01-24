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
 * 修改临时群成员列表：
 * 1. 头部
 * 2. 群命令类型，1字节，0x31
 * 3. 临时群类型，1字节，0x01是多人对话，0x02是讨论组
 * 4. 父群内部ID，4字节
 * 5. 临时群内部ID，4字节
 * 6. 操作类型，0x01是添加，0x02是删除，常量定义在QQ.java中
 * 7. 要操作的QQ号，4字节
 * 8. 如果有更多成员要操作，重复7部分
 * 9. 尾部
 * </pre>
 * 
 * @author luma
 */
public class ClusterModifyTempMemberPacket extends ClusterCommandPacket {
    private byte type;
    private int parentClusterId;
	private byte operation;
	private List<Integer> members;
    
    /**
     * 构造函数
     */
    public ClusterModifyTempMemberPacket(QQUser user) {
        super(user);
		subCommand = QQ.QQ_CLUSTER_CMD_MODIFY_TEMP_MEMBER;
    }

    /**
     * @param buf
     * @param length
     * @throws PacketParseException
     */
    public ClusterModifyTempMemberPacket(ByteBuffer buf, int length, QQUser user)
            throws PacketParseException {
        super(buf, length, user);
    }
    
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.packets.OutPacket#getPacketName()
     */
	@Override
    public String getPacketName() {
        return "Modify Temp Cluster Member Packet";
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.packets.OutPacket#putBody(java.nio.ByteBuffer)
     */
	@Override
    protected void putBody(ByteBuffer buf) {
		// 命令类型
		buf.put(subCommand);
		// 临时群类型
		buf.put(type);
		// 父群ID
		buf.putInt(parentClusterId);
		// 临时群ID
		buf.putInt(clusterId);
		// 操作方式
		buf.put(operation);
		// 成员QQ号
		for(int i : members)
			buf.putInt(i);
    }
    
    /**
     * @return Returns the externalId.
     */
    public int getParentClusterId() {
        return parentClusterId;
    }
    /**
     * @param externalId The externalId to set.
     */
    public void setParentClusterId(int externalId) {
        this.parentClusterId = externalId;
    }
    /**
     * @return Returns the type.
     */
    public byte getType() {
        return type;
    }
    /**
     * @param type The type to set.
     */
    public void setType(byte type) {
        this.type = type;
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
