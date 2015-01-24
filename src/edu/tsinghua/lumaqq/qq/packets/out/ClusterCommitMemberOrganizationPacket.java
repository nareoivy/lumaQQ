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
import edu.tsinghua.lumaqq.qq.beans.Member;
import edu.tsinghua.lumaqq.qq.beans.QQUser;
import edu.tsinghua.lumaqq.qq.packets.PacketParseException;

/**
 * <pre>
 * 提交成员分组情况到服务器
 * 1. 头部
 * 2. 命令，1字节，0x13
 * 3. 群内部id，4字节
 * 4. 未知1字节，0x00
 * 5. 成员QQ号，4字节
 * 6. 成员所属组织序号，1字节，没有组织时是0x00
 * 7. 如果有更多成员，重复5-6部分
 * 8. 尾部
 * 
 * 注意：不需要一次提交所有成员分组情况，如果只有个别成员的分组变动了（比如拖动操作），
 * 那么只需要提交改变的成员。所以这个操作不象修改临时群成员那样，又有添加又有删除的，
 * 可以一个包搞定了
 * </pre>
 * 
 * @author luma
 */
public class ClusterCommitMemberOrganizationPacket extends ClusterCommandPacket {
	private List<Member> members;
	
	public ClusterCommitMemberOrganizationPacket(ByteBuffer buf, int length, QQUser user) throws PacketParseException {
		super(buf, length, user);
	}

	public ClusterCommitMemberOrganizationPacket(QQUser user) {
		super(user);
		subCommand = QQ.QQ_CLUSTER_CMD_COMMIT_MEMBER_ORGANIZATION;
	}
	
	@Override
	public String getPacketName() {
		return "Cluster Commit Member Organization Packet";
	}
	
	@Override
	protected void putBody(ByteBuffer buf) {
		buf.put(subCommand);
		buf.putInt(clusterId);
		buf.put((byte)0);
		for(Member m : members) {
			buf.putInt(m.qq);
			buf.put((byte)m.organization);
		}
	}
	
	public void addMember(Member m) {
		members.add(m);
	}

	/**
	 * @return Returns the members.
	 */
	public List<Member> getMembers() {
		return members;
	}

	/**
	 * @param members The members to set.
	 */
	public void setMembers(List<Member> members) {
		this.members = members;
	}
}
