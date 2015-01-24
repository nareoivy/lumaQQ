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
 * 群名片bean
 * 
 * @author luma
 * @see edu.tsinghua.lumaqq.qq.packets.out.ClusterModifyCardPacket
 */
public class Card {
	public String name;
	public String phone;
	public String remark;
	public String email;
	public int genderIndex;
	
	/**
	 * 从字节缓冲区中解析一个群名片结构
	 * 
	 * @param buf
	 */
	public void readBean(ByteBuffer buf) {
		int len = buf.get() & 0xFF;
		name = Util.filterUnprintableCharacter(Util.getString(buf, len));
		
		genderIndex = buf.get() & 0xFF;
		
		len = buf.get() & 0xFF;
		phone = Util.filterUnprintableCharacter(Util.getString(buf, len));
		
		len = buf.get() & 0xFF;
		email = Util.filterUnprintableCharacter(Util.getString(buf, len));
		
		len = buf.get() & 0xFF;
		remark = Util.filterUnprintableCharacter(Util.getString(buf, len));
	}
	
	/**
	 * 写入bean的内容到缓冲区中
	 * 
	 * @param buf
	 */
	public void writeBean(ByteBuffer buf) {
		byte[] b = Util.getBytes(name);
		buf.put((byte)b.length);
		buf.put(b);
		
		buf.put((byte)genderIndex);
		
		b = Util.getBytes(phone);
		buf.put((byte)b.length);
		buf.put(b);
		
		b = Util.getBytes(email);
		buf.put((byte)b.length);
		buf.put(b);
		
		b = Util.getBytes(remark);
		buf.put((byte)b.length);
		buf.put(b);
	}
}
