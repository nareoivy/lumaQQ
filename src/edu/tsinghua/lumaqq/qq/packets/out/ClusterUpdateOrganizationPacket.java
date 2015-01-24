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
 * 刷新群内组织架构的请求包
 * 1. 头部
 * 2. 命令，1字节，0x12
 * 3. 群内部id，4字节
 * 4. 未知1字节，0x00
 * 5. 未知4字节，0x00000000
 * 6. 尾部
 * </pre>
 * 
 * @author luma
 */
public class ClusterUpdateOrganizationPacket extends ClusterCommandPacket {
	public ClusterUpdateOrganizationPacket(QQUser user) {
		super(user);
		subCommand = QQ.QQ_CLUSTER_CMD_UPDATE_ORGANIZATION;
	}

	public ClusterUpdateOrganizationPacket(ByteBuffer buf, int length, QQUser user) throws PacketParseException {
		super(buf, length, user);
	}
	
	@Override
	public String getPacketName() {
		return "Cluster Update Organization Packet";
	}
	
	@Override
	protected void putBody(ByteBuffer buf) {
		buf.put(subCommand);
		buf.putInt(clusterId);
		buf.put((byte)0);
		buf.putInt(0);
	}
}
