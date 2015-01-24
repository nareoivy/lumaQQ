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
 * 得到单个成员的全部群名片信息
 * 1. 头部
 * 2. 命令，1字节，0x10
 * 3. 群内部ID，4字节
 * 4. 需要得到群名片的成员QQ号，4字节
 * 5. 尾部
 * </pre>
 * 
 * @author luma
 */
public class ClusterGetCardPacket extends ClusterCommandPacket {
	private int qq;
	
	public ClusterGetCardPacket(ByteBuffer buf, int length, QQUser user) throws PacketParseException {
		super(buf, length, user);
	}

	public ClusterGetCardPacket(QQUser user) {
		super(user);
		subCommand = QQ.QQ_CLUSTER_CMD_GET_CARD;
	}
	
	@Override
	public String getPacketName() {
		return "Cluster Get Card Packet";
	}
	
	@Override
	protected void putBody(ByteBuffer buf) {
		buf.put(subCommand);
		buf.putInt(clusterId);
		buf.putInt(qq);
	}

	/**
	 * @return Returns the qq.
	 */
	public int getQQ() {
		return qq;
	}

	/**
	 * @param qq The qq to set.
	 */
	public void setQQ(int qq) {
		this.qq = qq;
	}
}
