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
import edu.tsinghua.lumaqq.qq.beans.Card;
import edu.tsinghua.lumaqq.qq.beans.QQUser;
import edu.tsinghua.lumaqq.qq.packets.PacketParseException;

/**
 * <pre>
 * 修改群名片请求包
 * 1. 头部 
 * 2. 子命令，1字节，0x0E
 * 3. 群内部ID，4字节
 * 4. 我的QQ号，4字节
 * unknown 4 bytes
 * 5. 真实姓名长度，1字节
 * 6. 真实姓名
 * 7. 性别索引，1字节，性别的顺序是'男', '女', '-'，所以男是0x00，等等
 * 8. 电话字符串长度，1字节
 * 9. 电话的字符串表示
 * 10. 电子邮件长度，1字节
 * 11. 电子邮件
 * 12. 备注长度，1字节
 * 13. 备注内容
 * 14. 尾部
 * </pre>
 * 
 * @author luma
 */
public class ClusterModifyCardPacket extends ClusterCommandPacket  {	
	private Card card;
	
	public ClusterModifyCardPacket(ByteBuffer buf, int length, QQUser user) throws PacketParseException {
		super(buf, length, user);
	}

	public ClusterModifyCardPacket(QQUser user) {
		super(user);
		subCommand = QQ.QQ_CLUSTER_CMD_MODIFY_CARD;
	}
	
	@Override
	public String getPacketName() {
		return "Cluster Modify Card Packet";
	}
	
	@Override
	protected void putBody(ByteBuffer buf) {
		buf.put(subCommand);
		buf.putInt(clusterId);
		buf.putInt(user.getQQ());
		card.writeBean(buf);
	}

	/**
	 * @return Returns the card.
	 */
	public Card getCard() {
		return card;
	}

	/**
	 * @param card The card to set.
	 */
	public void setCard(Card card) {
		this.card = card;
	}
}
