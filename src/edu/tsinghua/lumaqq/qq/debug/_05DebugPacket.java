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
import edu.tsinghua.lumaqq.qq.packets._05OutPacket;

/**
 * 05协议族调试包
 *
 * @author luma
 */
public class _05DebugPacket extends _05OutPacket {
    private byte[] body;
    private int cryptographStart;

	public _05DebugPacket(char command, QQUser user) {
		super(command, true, user);
		cryptographStart = -1;
	}
	
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.packets.OutPacket#getPacketName()
     */
    @Override
	public String getPacketName() {
        return "05 Debug Packet - 0x" + Integer.toHexString(command).toUpperCase();
    }
    
	@Override
	protected void putBody(ByteBuffer buf) {
        buf.put(body);
	}
	
	@Override
	protected int getEncryptStart() {
		return cryptographStart;
	}
	
	@Override
	protected int getDecryptStart() {
		return cryptographStart;
	}

	/**
	 * @return the body
	 */
	public byte[] getBody() {
		return body;
	}

	/**
	 * @param body the body to set
	 */
	public void setBody(byte[] body) {
		this.body = body;
	}

	/**
	 * @param cryptographStart the cryptographStart to set
	 */
	public void setCryptographStart(int cryptographStart) {
		this.cryptographStart = cryptographStart;
	}
}
