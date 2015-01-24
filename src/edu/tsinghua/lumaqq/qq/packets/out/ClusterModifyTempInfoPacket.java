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
import edu.tsinghua.lumaqq.qq.packets.PacketParseException;

/**
 * <pre>
 * 修改临时群资料
 * 1. 头部
 * 2. 命令，1字节, 0x34
 * 3. 临时群类型, 1字节
 * 4. 父群内部id，4字节
 * 5. 临时群内部id，4字节
 * 6. 临时群名称字节长度，1字节
 * 7. 临时群名称
 * 8. 尾部
 * </pre>
 * 
 * @author luma
 */
public class ClusterModifyTempInfoPacket extends ClusterCommandPacket {
	private int parentClusterId;
	private String name;
	private byte type;
	
	public ClusterModifyTempInfoPacket(ByteBuffer buf, int length, QQUser user) throws PacketParseException {
		super(buf, length, user);
	}

	public ClusterModifyTempInfoPacket(QQUser user) {
		super(user);
		subCommand = QQ.QQ_CLUSTER_CMD_MODIFY_TEMP_INFO;
	}
	
	@Override
	public String getPacketName() {
		return "Cluster Modify Temp Cluster Info Packet";		
	}
	
	@Override
	protected void putBody(ByteBuffer buf) {
		buf.put(subCommand);
		buf.put(type);
		buf.putInt(parentClusterId);
		buf.putInt(clusterId);
		byte[] nameBytes = Util.getBytes(name);
		buf.put((byte)(nameBytes.length & 0xFF));
		buf.put(nameBytes);
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
}
