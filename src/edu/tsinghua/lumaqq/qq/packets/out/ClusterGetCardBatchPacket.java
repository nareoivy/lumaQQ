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
 * 批量得到群名片中的真实姓名
 * 1. 头部
 * 2. 命令，1字节，0x0F
 * 3. 群内部ID，4字节
 * 4. local name card version id, 4 bytes
 * 5. 起始记录位置，4字节，从0开始，为1表示从第二条记录开始得到
 * 6. 尾部
 * </pre>
 * 
 * @author luma
 */
public class ClusterGetCardBatchPacket extends ClusterCommandPacket {
	private int start;
	
	public ClusterGetCardBatchPacket(ByteBuffer buf, int length, QQUser user) throws PacketParseException {
		super(buf, length, user);
	}

	public ClusterGetCardBatchPacket(QQUser user) {
		super(user);
		subCommand = QQ.QQ_CLUSTER_CMD_GET_CARD_BATCH;
	}	
	
	@Override
	public String getPacketName() {
		return "Cluster Get Card Batch Packet";
	}
	
	@Override
	protected void putBody(ByteBuffer buf) {
		buf.put(subCommand);
		buf.putInt(clusterId);
		buf.putInt(0);
		buf.putInt(start);
	}

	/**
	 * @return Returns the start.
	 */
	public int getStart() {
		return start;
	}

	/**
	 * @param start The start to set.
	 */
	public void setStart(int start) {
		this.start = start;
	}
}
