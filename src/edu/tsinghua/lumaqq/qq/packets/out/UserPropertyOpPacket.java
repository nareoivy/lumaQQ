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
import edu.tsinghua.lumaqq.qq.packets.BasicOutPacket;
import edu.tsinghua.lumaqq.qq.packets.PacketParseException;

/**
 * <pre>
 * 用户属性操作请求包
 * 1. 头部
 * 2. 子命令，1字节
 * 3. 起始位置，2字节
 * 4. 尾部
 * </pre>
 * 
 * @author luma
 */
public class UserPropertyOpPacket extends BasicOutPacket {
	private byte subCommand;
	private char startPosition;
	
	public UserPropertyOpPacket(QQUser user) {
		super(QQ.QQ_CMD_USER_PROPERTY_OP, true, user);
		subCommand = QQ.QQ_SUB_CMD_GET_USER_PROPERTY;
		startPosition = QQ.QQ_POSITION_USER_PROPERTY_START;
	}

	public UserPropertyOpPacket(ByteBuffer buf, int length, QQUser user) throws PacketParseException {
		super(buf, length, user);
	}

	@Override
	public String getPacketName() {
		return "User Property Op Packet";
	}
	
	@Override
	protected void putBody(ByteBuffer buf) {
		buf.put(subCommand);
		buf.putChar(startPosition);
	}

	/**
	 * @return Returns the subCommand.
	 */
	public byte getSubCommand() {
		return subCommand;
	}

	/**
	 * @param subCommand The subCommand to set.
	 */
	public void setSubCommand(byte subCommand) {
		this.subCommand = subCommand;
	}

	/**
	 * @return Returns the startPosition.
	 */
	public char getStartPosition() {
		return startPosition;
	}

	/**
	 * @param startPosition The startPosition to set.
	 */
	public void setStartPosition(char startPosition) {
		this.startPosition = startPosition;
	}
}
