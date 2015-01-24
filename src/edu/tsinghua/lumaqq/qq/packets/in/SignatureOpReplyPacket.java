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
package edu.tsinghua.lumaqq.qq.packets.in;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import edu.tsinghua.lumaqq.qq.QQ;
import edu.tsinghua.lumaqq.qq.beans.QQUser;
import edu.tsinghua.lumaqq.qq.beans.Signature;
import edu.tsinghua.lumaqq.qq.packets.BasicInPacket;
import edu.tsinghua.lumaqq.qq.packets.PacketParseException;

/**
 * <pre>
 * 个性签名操作的回复包
 * 1. 头部
 * 2. 子命令，1字节
 * 3. 回复码，1字节
 * 
 * 如果2部分为0x00, 0x01，则
 * 4. 尾部
 * 
 * 如果2部分为0x02，即得到个性签名，则还有
 * 4. 下一个起始的QQ号，4字节。为这个回复包中所有QQ号的最大值加1
 * 5. QQ号，4字节
 * 6. 个性签名最后修改时间，4字节。这个修改时间的用处在于减少网络I/O，只有第一次我们需要
 *    得到所有的个性签名，以后我们只要送出个性签名，然后服务器会比较最后修改时间，修改过的
 *    才发回来
 * 7. 个性签名字节长度，1字节
 * 8. 个性签名
 * 9. 如果有更多，重复5-8部分
 * 10. 尾部
 * </pre>
 * 
 * @author luma
 */
public class SignatureOpReplyPacket extends BasicInPacket {
	public byte subCommand;
	public byte replyCode;
	public int nextQQ;
	public List<Signature> signatures;

	public SignatureOpReplyPacket(ByteBuffer buf, int length, QQUser user) throws PacketParseException {
		super(buf, length, user);
	}

	@Override
	protected void parseBody(ByteBuffer buf) throws PacketParseException {
		subCommand = buf.get();
		replyCode = buf.get();
		if(subCommand == QQ.QQ_SUB_CMD_GET_SIGNATURE) {
			nextQQ = buf.getInt();
			signatures = new ArrayList<Signature>();
			while(buf.hasRemaining()) {
				Signature sig = new Signature();
				sig.readBean(buf);
				signatures.add(sig);
			}
		}
	}
	
	@Override
	public String getPacketName() {
		return "Signature Op Reply Packet";
	}
}
