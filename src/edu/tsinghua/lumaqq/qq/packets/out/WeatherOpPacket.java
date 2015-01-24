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
 * 天气数据操作请求包
 * 1. 头部
 * 2. 子命令，1字节
 * 3. 我的IP地址，4字节
 * 4. 我的端口，2字节，查天气预报不需要，一般都是0x0000
 * 5. 尾部
 * </pre>
 * 
 * @author luma
 */
public class WeatherOpPacket extends BasicOutPacket {
	private byte subCommand;
	private byte[] ip;
	
	public WeatherOpPacket(QQUser user) {
		super(QQ.QQ_CMD_WEATHER_OP, true, user);
		subCommand = QQ.QQ_SUB_CMD_GET_WEATHER;
	}

	public WeatherOpPacket(ByteBuffer buf, int length, QQUser user) throws PacketParseException {
		super(buf, length, user);
	}

	@Override
	public String getPacketName() {
		return "Weather Op Packet";
	}
	
	@Override
	protected void putBody(ByteBuffer buf) {
		buf.put(subCommand);
		buf.put(ip);
		buf.putChar((char)0);
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
	 * @return Returns the ip.
	 */
	public byte[] getIp() {
		return ip;
	}

	/**
	 * @param ip The ip to set.
	 */
	public void setIp(byte[] ip) {
		this.ip = ip;
	}
}
