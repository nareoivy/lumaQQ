/*
* LumaQQ - Java QQ Client
*
* Copyright (C) 2004 luma <stubma@163.com>
*                    notXX
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

/**
 * <pre>
 * 05系列的输入包基类
 * 1. 包头标识，1字节
 * 2. source，2字节
 * 3. 包长度，2字节
 * 4. 包命令，2字节
 * 5. 包序号，2字节
 * 6. 用户QQ号，4字节
 * 7. 包体
 * 8. 包尾，1字节
 * 
 * 值得注意的是：这种包的包体并非完全加密型，而是部分加密型
 * </pre>
 * 
 * @author luma
 */
public abstract class _05InPacket extends InPacket {
    private int qqNum;

    /**
     * @param header
     * @param source
     * @param command
     * @param user
     */
    public _05InPacket(char command, QQUser user) {
        super(QQ.QQ_HEADER_05_FAMILY, QQ.QQ_CLIENT_VERSION, command, user);
    }
    
    /**
     * @param buf
     * @param length
     * @param user
     * @throws PacketParseException
     */
    public _05InPacket(ByteBuffer buf, int length, QQUser user)
            throws PacketParseException {
        super(buf, length, user);
    }
    
    /**
     * @param buf
     * @param user
     * @throws PacketParseException
     */
    public _05InPacket(ByteBuffer buf, QQUser user) throws PacketParseException {
        super(buf, user);
    }
    
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.packets.Packet#getLength(int)
     */
	@Override
    protected int getLength(int bodyLength) {
        return QQ.QQ_LENGTH_05_FAMILY_HEADER + QQ.QQ_LENGTH_05_FAMILY_TAIL + bodyLength;
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.packets.Packet#getHeadLength()
     */
	@Override
    protected int getHeadLength() {
        return QQ.QQ_LENGTH_05_FAMILY_HEADER;
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.packets.Packet#getTailLength()
     */
	@Override
    protected int getTailLength() {
        return QQ.QQ_LENGTH_05_FAMILY_TAIL;
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.packets.Packet#putHead(java.nio.ByteBuffer)
     */
	@Override
    protected void putHead(ByteBuffer buf) {
        buf.put(QQ.QQ_HEADER_05_FAMILY);
        buf.putChar(source);
        buf.putChar((char)0);
        buf.putChar(command);
        buf.putChar(sequence);
        buf.putInt(user.getQQ());
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.packets.Packet#putBody(java.nio.ByteBuffer)
     */
	@Override
    protected void putBody(ByteBuffer buf) {
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.packets.Packet#getBodyBytes(java.nio.ByteBuffer, int)
     */
	@Override
    protected byte[] getBodyBytes(ByteBuffer buf, int length) {
	    // 得到包体长度
	    int bodyLen = length - QQ.QQ_LENGTH_05_FAMILY_HEADER - QQ.QQ_LENGTH_05_FAMILY_TAIL;
	    // 得到包体内容
	    byte[] body = new byte[bodyLen]; 
	    buf.get(body);
	    return body;
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.packets.Packet#putTail(java.nio.ByteBuffer)
     */
	@Override
    protected void putTail(ByteBuffer buf) {
        buf.put(QQ.QQ_TAIL_05_FAMILY);
    }

	@Override
    protected byte[] encryptBody(byte[] b) {
        return null;
    }
	
	@Override
	public byte[] getDecryptKey(byte[] body) {
		return user.getFileAgentKey();
	}
    
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.packets.Packet#parseHeader(java.nio.ByteBuffer)
     */
	@Override
    protected void parseHeader(ByteBuffer buf) throws PacketParseException {
        header = buf.get();
        source = buf.getChar();
        buf.getChar();
        command = buf.getChar();
        sequence = buf.getChar();
        qqNum = buf.getInt();
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.packets.Packet#parseTail(java.nio.ByteBuffer)
     */
	@Override
    protected void parseTail(ByteBuffer buf) throws PacketParseException {
        buf.get();
    }
    
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.packets.Packet#getPacketName()
     */
	@Override
    public String getPacketName() {
        return "Unknown Incoming Packet - 05 Family";
    }
    
    public int getQqNum() {
		return qqNum;
	}

	public void setQqNum(int qqNum) {
		this.qqNum = qqNum;
	}
	
	@Override
	public int getFamily() {
		return QQ.QQ_PROTOCOL_FAMILY_05;
	}
}
