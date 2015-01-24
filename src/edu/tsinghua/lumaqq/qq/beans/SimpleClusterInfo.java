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
 * 讨论组信息封装类 
 * 
 * @author luma
 * @see edu.tsinghua.lumaqq.qq.packets.in.ClusterCommandReplyPacket
 */
public class SimpleClusterInfo {
	public int id;
	public String name;
	
    /**
     * 给定一个输入流，解析Subject结构
     * 
     * @param buf
     */
    public void readBean(ByteBuffer buf) {
    	id = buf.getInt();
    	int len = buf.get() & 0xFF;
    	name = Util.getString(buf, len);
    }
}
