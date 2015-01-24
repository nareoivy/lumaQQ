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
package edu.tsinghua.lumaqq.qq.debug;

import java.nio.ByteBuffer;

import edu.tsinghua.lumaqq.qq.beans.QQUser;
import edu.tsinghua.lumaqq.qq.packets.BasicOutPacket;
import edu.tsinghua.lumaqq.qq.packets.PacketParseException;

/**
 * 包装一个调试包，包内容自定义
 * 
 * @author luma
 */
public class BasicDebugPacket extends BasicOutPacket {
    private byte[] body;
    
    /**
     * 创建一个调试包
     * 
     * @param command
     * 		包命令类型
     */
    public BasicDebugPacket(char command, QQUser user) {
        super(command, true, user);
    }
    
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.packets.OutPacket#getPacketName()
     */
    @Override
	public String getPacketName() {
        return "Basic Debug Packet - 0x" + Integer.toHexString(command).toUpperCase();
    }
    
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.packets.OutPacket#parseBody(java.nio.ByteBuffer)
     */
    @Override
	protected void parseBody(ByteBuffer buf) throws PacketParseException {
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.packets.OutPacket#putBody(java.nio.ByteBuffer)
     */
    @Override
	protected void putBody(ByteBuffer buf) {
        buf.put(body);
    }

    /**
     * @return Returns the body.
     */
    public byte[] getBody() {
        return body;
    }
    
    /**
     * @param body The body to set.
     */
    public void setBody(byte[] body) {
        this.body = body;
    }
}
