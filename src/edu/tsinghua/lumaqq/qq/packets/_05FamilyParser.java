/*
* LumaQQ - Java QQ Client
*
* Copyright (C) 2004 notXX
*                    luma <stubma@163.com>
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
import edu.tsinghua.lumaqq.qq.packets.in._05.RequestAgentReplyPacket;
import edu.tsinghua.lumaqq.qq.packets.in._05.RequestBeginReplyPacket;
import edu.tsinghua.lumaqq.qq.packets.in._05.RequestFaceReplyPacket;
import edu.tsinghua.lumaqq.qq.packets.in._05.TransferReplyPacket;
import edu.tsinghua.lumaqq.qq.packets.in._05.Unknown05InPacket;
import edu.tsinghua.lumaqq.qq.packets.out._05.RequestAgentPacket;
import edu.tsinghua.lumaqq.qq.packets.out._05.RequestBeginPacket;
import edu.tsinghua.lumaqq.qq.packets.out._05.TransferPacket;
import edu.tsinghua.lumaqq.qq.packets.out._05.Unknown05OutPacket;

/**
 * 05系列包的解析器
 * 
 * @author luma
 */
public class _05FamilyParser implements IParser {
	private int offset;
	private int length;
	
	public _05FamilyParser() {		
	}
	
	public boolean isDuplicate(InPacket in) {
		return false;
	}
	
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.packets.IParser#accept(java.nio.ByteBuffer)
     */
    public boolean accept(ByteBuffer buf) {
        offset = buf.position();
        int bufferLength = buf.limit() - offset;
        if(bufferLength <= 0)
            return false;
        
        // 检查包头
        if(buf.get(offset) != QQ.QQ_HEADER_05_FAMILY)
            return false;
        
        // 如果长度小于5，则没有长度标志
        if(bufferLength < 5)
            return false;
        
        // 检查包长是否大于可用长度
        length = buf.getChar(offset + 3);
        if(length > bufferLength)
            return false;
        
        // 检查包尾
        if(buf.get(offset + length - 1) != QQ.QQ_TAIL_05_FAMILY)
            return false;

        return true;
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.packets.IParser#getLength(java.nio.ByteBuffer)
     */
    public int getLength(ByteBuffer buf) {
        return length;
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.packets.IParser#parseIncoming(java.nio.ByteBuffer, int, edu.tsinghua.lumaqq.qq.beans.QQUser)
     */
    public InPacket parseIncoming(ByteBuffer buf, int len, QQUser user)
            throws PacketParseException {
        try {
			switch(buf.getChar(offset + 5)) {
			    case QQ.QQ_05_CMD_REQUEST_AGENT:
			        return new RequestAgentReplyPacket(buf, len, user);
			    case QQ.QQ_05_CMD_REQUEST_FACE:
			        return new RequestFaceReplyPacket(buf, len, user);
			    case QQ.QQ_05_CMD_REQUEST_BEGIN:
			        return new RequestBeginReplyPacket(buf, len, user);
			    case QQ.QQ_05_CMD_TRANSFER:
			        return new TransferReplyPacket(buf, len, user);
			    default:
			        return new Unknown05InPacket(buf, len, user);
			}
		} catch(PacketParseException e) {
			buf.position(offset);
			return new Unknown05InPacket(buf, len, user);
		}
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.packets.IParser#parseOutcoming(java.nio.ByteBuffer, int, edu.tsinghua.lumaqq.qq.beans.QQUser)
     */
    public OutPacket parseOutcoming(ByteBuffer buf, int len, QQUser user)
            throws PacketParseException {
        try {
			switch(buf.getChar(offset + 5)) {
			    case QQ.QQ_05_CMD_REQUEST_AGENT:
			        return new RequestAgentPacket(buf, len, user);
			    case QQ.QQ_05_CMD_REQUEST_BEGIN:
			        return new RequestBeginPacket(buf, len, user);
			    case QQ.QQ_05_CMD_TRANSFER:
			        return new TransferPacket(buf, len, user);
			    default:
			        return new Unknown05OutPacket(buf, len, user);
			}
		} catch(PacketParseException e) {
			buf.position(offset);
			return new Unknown05OutPacket(buf, len, user);
		}
    }
    
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.packets.IParser#isDuplicatedNeedReply(edu.tsinghua.lumaqq.qq.packets.InPacket)
     */
    public boolean isDuplicatedNeedReply(InPacket in) {
    	return false;
    }

	/* (non-Javadoc)
	 * @see edu.tsinghua.lumaqq.qq.packets.IParser#relocate(java.nio.ByteBuffer)
	 */
	public int relocate(ByteBuffer buf) {
		int offset = buf.position();
    	if(buf.remaining() < 5)
    		return offset;
		int len = buf.getChar(offset + 3);
		if(len == 0 || offset + len > buf.limit())
			return offset;
		else
			return offset + len;
	}

	/* (non-Javadoc)
	 * @see edu.tsinghua.lumaqq.qq.packets.IParser#getHistory()
	 */
	public PacketHistory getHistory() {
		return null;
	}
}
