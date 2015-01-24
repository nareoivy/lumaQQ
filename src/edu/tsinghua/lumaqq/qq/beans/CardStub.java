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
package edu.tsinghua.lumaqq.qq.beans;

import java.nio.ByteBuffer;

import edu.tsinghua.lumaqq.qq.Util;

/**
 * 群名片存根bean
 * 
 * @author luma
 * @see edu.tsinghua.lumaqq.qq.packets.in.ClusterCommandReplyPacket
 */
public class CardStub {
	public int qq;
	public String name;
	
	public void readBean(ByteBuffer buf) {
		qq = buf.getInt();
		int len = buf.get() & 0xFF;
		name = Util.filterUnprintableCharacter(Util.getString(buf, len));
	}
}
