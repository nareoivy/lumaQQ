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

/**
 * <pre>
 * 03协议族输出包基类，这个协议族的格式为
 * 1. 协议族包头，1字节，0x03
 * 2. 命令，1字节
 * 3. 包序号，2字节
 * 4. 包流水号，4字节
 * Note: 4部分是一个依次递增的号码，也许是序号，名字是次要的。
 * 5. 未知的4字节
 * 6. 未知的4字节
 * 7. 未知的4字节
 * 8. 未知的4字节
 * 9. 未知的4字节
 * 10. 未知的4字节
 * 11. 未知的4字节
 * 12. 未知的4字节
 * 13. 分片数，1字节
 * Note: 对于一个发出的包来说，13部分一般都是0x01
 * 14. 当前分片，1字节，从0开始
 * 15. 未知1字节
 * 16. 发送者版本号，2字节
 * 17. 未知1字节
 * 18. 包体
 * 
 * Note: 此协议族无加密，无包尾
 * </pre>
 *
 * @author luma
 */
public abstract class _03OutPacket extends OutPacket {
	protected byte totalFragment;
	protected byte currentFragment;
	protected int serialNumber;
	
	public _03OutPacket(char command, boolean ack, QQUser user) {
		super(QQ.QQ_HEADER_03_FAMILY, command, ack, user);
		totalFragment = 0x01;
		currentFragment = 0x00;
	}

	public _03OutPacket(ByteBuffer buf, int length, QQUser user) throws PacketParseException {
		super(buf, length, user);
	}

	@Override
	protected int getLength(int bodyLength) {
		return QQ.QQ_LENGTH_FTP_FAMILY_HEADER + bodyLength;
	}

	@Override
	protected boolean validateHeader() {
		return true;
	}

	@Override
	protected int getHeadLength() {
		return QQ.QQ_LENGTH_FTP_FAMILY_HEADER;
	}

	@Override
	protected int getTailLength() {
		return 0;
	}
	
	@Override
	protected void postFill(ByteBuffer buf, int startPos) {
	}

	@Override
	protected void putHead(ByteBuffer buf) {
		buf.put(QQ.QQ_HEADER_03_FAMILY);
		buf.put((byte)command);
		buf.putChar(sequence);
		buf.putInt(serialNumber);
		buf.putInt(0);
		buf.putInt(0);
		buf.putInt(0);
		buf.putInt(0);
		buf.putInt(0);
		buf.putInt(0);
		buf.putInt(0);
		buf.putInt(0);
		buf.put(totalFragment);
		buf.put(currentFragment);
		buf.put((byte)0x0);
		buf.putChar(source);
		buf.put((byte)0x0);		
	}
	
	@Override
	protected byte[] getBodyBytes(ByteBuffer buf, int length) {
	    // 得到包体长度
	    int bodyLen = length - QQ.QQ_LENGTH_FTP_FAMILY_HEADER;
	    // 得到包体内容
	    byte[] body = new byte[bodyLen]; 
	    buf.get(body);
	    return body;
	}

	@Override
	protected void putTail(ByteBuffer buf) {
	}
	
	@Override
	protected int getEncryptStart() {
		return -1;
	}
	
	@Override
	protected int getDecryptStart() {
		return -1;
	}

	@Override
	protected void parseHeader(ByteBuffer buf) throws PacketParseException {
		header = buf.get();
		command = (char)buf.get();
		sequence = buf.getChar();
		buf.position(buf.position() + 36);
		totalFragment = buf.get();
		currentFragment = buf.get();
		buf.get();
		source = buf.getChar();
		buf.get();
	}

	@Override
	protected void parseTail(ByteBuffer buf) throws PacketParseException {
	}

	/**
	 * @return the currentFragment
	 */
	public byte getCurrentFragment() {
		return currentFragment;
	}

	/**
	 * @param currentFragment the currentFragment to set
	 */
	public void setCurrentFragment(byte currentFragment) {
		this.currentFragment = currentFragment;
	}

	/**
	 * @return the totalFragment
	 */
	public byte getTotalFragment() {
		return totalFragment;
	}

	/**
	 * @param totalFragment the totalFragment to set
	 */
	public void setTotalFragment(byte totalFragment) {
		this.totalFragment = totalFragment;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof _03InPacket) {
			_03InPacket in = (_03InPacket)obj;
			return command == in.command &&
				serialNumber == in.serialNumber;
		} else if(obj instanceof _03OutPacket) {
			_03OutPacket out = (_03OutPacket)obj;
			return command == out.command &&
				serialNumber == out.serialNumber;
		} else
			return false;
	}
	
	public int getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(int serialNumber) {
		this.serialNumber = serialNumber;
	}
	
	@Override
	public int getFamily() {
		return QQ.QQ_PROTOCOL_FAMILY_03;
	}
}
