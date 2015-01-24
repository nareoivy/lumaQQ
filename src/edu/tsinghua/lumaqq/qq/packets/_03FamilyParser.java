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
package edu.tsinghua.lumaqq.qq.packets;

import java.nio.ByteBuffer;

import edu.tsinghua.lumaqq.qq.QQ;
import edu.tsinghua.lumaqq.qq.beans.QQUser;
import edu.tsinghua.lumaqq.qq.packets.in._03.GetCustomHeadDataReplyPacket;
import edu.tsinghua.lumaqq.qq.packets.in._03.GetCustomHeadInfoReplyPacket;
import edu.tsinghua.lumaqq.qq.packets.in._03.Unknown03InPacket;
import edu.tsinghua.lumaqq.qq.packets.out._03.GetCustomHeadDataPacket;
import edu.tsinghua.lumaqq.qq.packets.out._03.GetCustomHeadInfoPacket;
import edu.tsinghua.lumaqq.qq.packets.out._03.Unknown03OutPacket;

/**
 * FTP协议族解析器
 *
 * @author luma
 */
public class _03FamilyParser implements IParser {
	private int offset;
	private int length;
	private PacketHistory history;
	
	public _03FamilyParser() {
		history = new PacketHistory();
	}
	
	public boolean isDuplicate(InPacket in) {
		return history.check(in, true);
	}
	
	public boolean accept(ByteBuffer buf) {
        offset = buf.position();
        length = buf.limit() - offset;
        if(length <= QQ.QQ_LENGTH_FTP_FAMILY_HEADER)
        	return false;
        
        // 检查包头
        if(buf.get(offset) != QQ.QQ_HEADER_03_FAMILY)
            return false;
        
        return true;
	}

	public int getLength(ByteBuffer buf) {
		return length;
	}

	public InPacket parseIncoming(ByteBuffer buf, int length, QQUser user) throws PacketParseException {
		try {
			switch(buf.get(offset + 1)) {
				case QQ.QQ_03_CMD_GET_CUSTOM_HEAD_DATA:
					return new GetCustomHeadDataReplyPacket(buf, length, user);
				case QQ.QQ_03_CMD_GET_CUSTOM_HEAD_INFO:
					return new GetCustomHeadInfoReplyPacket(buf, length, user);
				default:
					return new Unknown03InPacket(buf, length, user);
			}
		} catch(PacketParseException e) {
			buf.position(offset);
			return new Unknown03InPacket(buf, length, user);
		}
	}

	public OutPacket parseOutcoming(ByteBuffer buf, int length, QQUser user) throws PacketParseException {
		try {
			switch(buf.get(offset + 1)) {
				case QQ.QQ_03_CMD_GET_CUSTOM_HEAD_DATA:
					return new GetCustomHeadDataPacket(buf, length, user);
				case QQ.QQ_03_CMD_GET_CUSTOM_HEAD_INFO:
					return new GetCustomHeadInfoPacket(buf, length, user);
				default:
					return new Unknown03OutPacket(buf, length, user);
			}
		} catch(PacketParseException e) {
			buf.position(offset);
			return new Unknown03OutPacket(buf, length, user);
		}
	}

	/* (non-Javadoc)
	 * @see edu.tsinghua.lumaqq.qq.packets.IParser#isDuplicatedNeedReply(edu.tsinghua.lumaqq.qq.packets.InPacket)
	 */
	public boolean isDuplicatedNeedReply(InPacket in) {
		return false;
	}

	public int relocate(ByteBuffer buf) {
		return 0;
	}

	public PacketHistory getHistory() {
		return history;
	}
}
