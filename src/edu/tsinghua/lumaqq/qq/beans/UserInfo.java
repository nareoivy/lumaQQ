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

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;

import edu.tsinghua.lumaqq.qq.Util;

/**
 * 在线用户的结构表示
 * 
 * @author luma
 * @see edu.tsinghua.lumaqq.qq.packets.in.SearchUserReplyPacket
 */
public class UserInfo {
	public int qqNum;
	public String nick;
	public String province;
	public int face;
	
    /**
     * 给定一个输入流，解析UserInfo结构
     * @param buf
     */
	public void readBean(ByteBuffer buf) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int i = 0;
		while(true) {
			byte b = buf.get();
			if(b != 0x1F) { // 不等于0x1F则继续判断
				if(b != 0x1E)  // 不等于0x1E则写入输出流
					baos.write(b);	
				else { // 等于0x1E则完结这个字段
					if(i == 0)
					    qqNum = Util.getInt(new String(baos.toByteArray()), 8422190);
					else if(i == 1)
					    nick = Util.getString(baos.toByteArray());
					else if(i == 2)
					    province = Util.getString(baos.toByteArray());
					else if(i == 3)
						face = Util.getInt(new String(baos.toByteArray()), 0);
					i++;
					baos.reset();
				}
			} else
				break;
		}
	}
}
