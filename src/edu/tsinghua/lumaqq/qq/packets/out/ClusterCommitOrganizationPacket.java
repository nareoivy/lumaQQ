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
import edu.tsinghua.lumaqq.qq.beans.QQOrganization;
import edu.tsinghua.lumaqq.qq.beans.QQUser;
import edu.tsinghua.lumaqq.qq.packets.PacketParseException;

/**
 * <pre>
 * 提交组织架构到服务器
 * 1. 头部
 * 2. 命令，1字节，0x11
 * 3. 群内部id，4字节
 * 4. 组织个数，2字节
 * 5. 组织序号，1字节，从1开始
 * 6. 组织的层次关系，4字节。层次关系的格式参见ClusterCommandReplyPacket注释
 * 7. 组织名称字节长度，1字节
 * 8. 组织名称
 * 9. 如果有更多组织，重复5-8部分
 * 10. 尾部
 * </pre>
 * 
 * @author luma
 */
public class ClusterCommitOrganizationPacket extends ClusterCommandPacket {
	private List<QQOrganization> organizations;
	
	public ClusterCommitOrganizationPacket(ByteBuffer buf, int length, QQUser user) throws PacketParseException {
		super(buf, length, user);
	}

	public ClusterCommitOrganizationPacket(QQUser user) {
		super(user);
		subCommand = QQ.QQ_CLUSTER_CMD_COMMIT_ORGANIZATION;
	}
	
	@Override
	public String getPacketName() {
		return "Cluster Commit Organization Packet";
	}
	
	@Override
	protected void putBody(ByteBuffer buf) {
		buf.put(subCommand);
		buf.putInt(clusterId);
		buf.putChar((char)organizations.size());
		for(QQOrganization org : organizations) {
			buf.put((byte)org.id);
			buf.putInt(org.path);
			byte[] nameBytes = Util.getBytes(org.name);
			buf.put((byte)nameBytes.length);
			buf.put(nameBytes);
		}
	}

	/**
	 * @return Returns the organizations.
	 */
	public List<QQOrganization> getOrganizations() {
		return organizations;
	}

	/**
	 * @param organizations The organizations to set.
	 */
	public void setOrganizations(List<QQOrganization> organizations) {
		this.organizations = organizations;
	}
}
