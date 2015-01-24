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
import edu.tsinghua.lumaqq.qq.Util;
import edu.tsinghua.lumaqq.qq.beans.QQUser;
import edu.tsinghua.lumaqq.qq.packets.PacketParseException;

/**
 * <pre>
 * 创建临时群的请求包
 * 1. 头部
 * 2. 子命令类型，1字节，0x30
 * 3. 临时群类型，1字节
 * 4. 父群内部ID，4字节
 * 5. 名称长度，1字节
 * 6. 名称
 * 7. 成员QQ号，4字节
 * 8. 如果有更多成员，重复6部分
 * 10. 尾部
 * </pre>
 * 
 * @author luma
 */
public class ClusterCreateTempPacket extends ClusterCommandPacket {
    private int parentClusterId;
	private String name;
	private List<Integer> members;
	private byte type;

    /**
     * @param buf
     * @param length
     * @param user
     * @throws PacketParseException
     */
    public ClusterCreateTempPacket(ByteBuffer buf, int length,
            QQUser user) throws PacketParseException {
        super(buf, length, user);
    }
    
    /**
     * @param user
     */
    public ClusterCreateTempPacket(QQUser user) {
        super(user);
        subCommand = QQ.QQ_CLUSTER_CMD_CREATE_TEMP;
    }

	@Override
	public String getPacketName() {
		return "Cluster - Create Temp Cluster Packet";
	}
	
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.packets.OutPacket#putBody(java.nio.ByteBuffer)
     */
	@Override
    protected void putBody(ByteBuffer buf) {
        // 子命令类型，1字节，0x30
        buf.put(subCommand);
        // 临时群类型，1字节
        buf.put(type);
        // 父群内部ID，4字节
        buf.putInt(parentClusterId);
        // 名称长度，1字节
        byte[] b = Util.getBytes(name);
        buf.put((byte)(b.length & 0xFF));
        // 名称
        buf.put(b);
        // 成员QQ号，4字节
        for(int i : members)
            buf.putInt(i);
    }

    /**
     * @return Returns the name.
     */
    public String getName() {
        return name;
    }
    /**
     * @param name The name to set.
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * @return Returns the parentClusterId.
     */
    public int getParentClusterId() {
        return parentClusterId;
    }
    /**
     * @param parentClusterId The parentClusterId to set.
     */
    public void setParentClusterId(int parentClusterId) {
        this.parentClusterId = parentClusterId;
    }

    /**
     * @return Returns the tempType.
     */
    public byte getType() {
        return type;
    }
    /**
     * @param tempType The tempType to set.
     */
    public void setType(byte tempType) {
        this.type = tempType;
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
