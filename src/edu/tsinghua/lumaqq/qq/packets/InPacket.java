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

import edu.tsinghua.lumaqq.qq.beans.QQUser;

/**
 * 所有输入包的基类
 * 
 * @author luma
 */
public abstract class InPacket extends Packet {
    /**
     * @param header
     * @param source
     * @param command
     * @param sequence
     * @param user
     */
    public InPacket(byte header, char source, char command, QQUser user) {
        super(header, source, command, (char)0, user);
    }
    
    /**
     * @param buf
     * @param length
     * @param user
     * @throws PacketParseException
     */
    public InPacket(ByteBuffer buf, int length, QQUser user)
            throws PacketParseException {
        super(buf, length, user);
    }
    
    /**
     * @param buf
     * @param user
     * @throws PacketParseException
     */
    public InPacket(ByteBuffer buf, QQUser user) throws PacketParseException {
        super(buf, user);
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.packets.Packet#validateHeader()
     */
	@Override
    protected boolean validateHeader() {
        return true;
    }
}
