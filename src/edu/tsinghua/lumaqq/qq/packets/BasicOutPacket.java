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
 * 基本协议族的输出包基类
 * 基本协议族的包都具有以下的格式:
 * 1. 包头标志，1字节，0x02
 * 2. 客户端版本代码，2字节
 * 3. 命令，2字节
 * 4. 包序号, 2字节
 * 5. 用户QQ号，4字节
 * 6. 包体
 * 7. 包尾标志，1字节，0x03
 * Note: 6部分将用会话密钥加密, 登录包例外，6部分要用密码密钥加密。请求登录令牌包例外，6部分不需加密
 * </pre>
 * 
 * @author notxx
 * @author luma
 */
public abstract class BasicOutPacket extends OutPacket {
    private int qqNum;
    
	/**
	 * 构造一个参数指定的包.
	 * @param command
	 *                   命令.
	 * @param ack
	 *                   是否需要回应.
	 */
	protected BasicOutPacket(char command, boolean ack, QQUser user) {
		super(QQ.QQ_HEADER_BASIC_FAMILY, command, ack, user);
	}
	
	protected BasicOutPacket(ByteBuffer buf, QQUser user) throws PacketParseException {
	    this(buf, buf.limit() - buf.position(), user);
	}
	
	protected BasicOutPacket(ByteBuffer buf, int length, QQUser user) throws PacketParseException {
	    super(buf, length, user);
	}
	
	/* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.packets.OutPacket#validateHeader()
     */
	@Override
    protected boolean validateHeader() {
        return qqNum == user.getQQ();
    }
    
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.packets.OutPacket#getBodyBytes(java.nio.ByteBuffer, int)
     */
	@Override
    protected byte[] getBodyBytes(ByteBuffer buf, int length) {
	    // 得到包体长度
	    int bodyLen = length - QQ.QQ_LENGTH_BASIC_FAMILY_OUT_HEADER - QQ.QQ_LENGTH_BASIC_FAMILY_TAIL;
	    if(!user.isUdp()) bodyLen -= 2;	    
	    // 得到加密的包体内容
	    byte[] body = new byte[bodyLen]; 
	    buf.get(body);
	    return body;
    }
	
	/* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.packets.OutPacket#postFill(java.nio.ByteBuffer, int)
     */
	@Override
    protected void postFill(ByteBuffer buf, int startPos) {
	    // 如果是tcp包，到包的开头处填上包长度，然后回到目前的pos
	    if(!user.isUdp()) {
	        int len = buf.position() - startPos;
	        buf.putChar(startPos, (char)len);
	    }
    }
    
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.packets.OutPacket#getUDPLength(int)
     */
	@Override
    protected int getLength(int bodyLength) {
        return QQ.QQ_LENGTH_BASIC_FAMILY_OUT_HEADER + QQ.QQ_LENGTH_BASIC_FAMILY_TAIL + bodyLength + (user.isUdp() ? 0 : 2);
    }
	
	/* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.packets.OutPacket#parseTail(java.nio.ByteBuffer)
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
        return "Unknown Outcoming Packet - Basic Family";
    }
	
	/* (non-Javadoc)
	 * @see edu.tsinghua.lumaqq.qq.packets.OutPacket#putHead(java.nio.ByteBuffer)
	 */
	@Override
	protected void putHead(ByteBuffer buf) {
	    if(!user.isUdp())
	        buf.putChar((char)0);
		buf.put(getHeader());
		buf.putChar(source);
		buf.putChar(command);
		buf.putChar(sequence);
		buf.putInt(user.getQQ());
	}
	
	/* (non-Javadoc)
	 * @see edu.tsinghua.lumaqq.qq.packets.OutPacket#putTail(java.nio.ByteBuffer)
	 */
	@Override
	protected void putTail(ByteBuffer buf) {
		buf.put(QQ.QQ_TAIL_BASIC_FAMILY);
	}
	
	/* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.packets.OutPacket#parseHeader(java.nio.ByteBuffer)
     */
	@Override
    protected void parseHeader(ByteBuffer buf) throws PacketParseException {
	    if(!user.isUdp())
	        buf.getChar();
	    header = buf.get();
		source = buf.getChar();
		command = buf.getChar();
		sequence = buf.getChar();
		qqNum = buf.getInt();
    }
    
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.packets.Packet#getHeadLength()
     */
	@Override
    protected int getHeadLength() {
        return QQ.QQ_LENGTH_BASIC_FAMILY_OUT_HEADER + (user.isUdp() ? 0 : 2);
    }
    
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.packets.Packet#getTailLength()
     */
	@Override
    protected int getTailLength() {
        return QQ.QQ_LENGTH_BASIC_FAMILY_TAIL;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
	@Override
    public String toString() {
        return "包名: " + getPacketName() + " 序号: " + (int)sequence; 
    }
	
	@Override
	public byte[] getDecryptKey(byte[] body) {
		return user.getSessionKey();
	}
	
	@Override
	public byte[] getFallbackDecryptKey(byte[] body) {
		return user.getPasswordKey();
	}
	
	@Override
	public byte[] getEncryptKey(byte[] body) {
		return user.getSessionKey();
	}
	
	@Override
	public int getFamily() {
		return QQ.QQ_PROTOCOL_FAMILY_BASIC;
	}
}