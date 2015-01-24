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

import edu.tsinghua.lumaqq.qq.QQ;
import edu.tsinghua.lumaqq.qq.Util;

/**
 * 群信息封装bean
 * 
 * @author luma
 * @see edu.tsinghua.lumaqq.qq.packets.out.ClusterCommandPacket
 */
public class ClusterInfo {
	public int clusterId;
	// 如果是固定群，这个表示外部ID，如果是临时群，这个表示父群ID
	public int externalId;
	// type字段表示固定群或者临时群的群类型
	public byte type;
	public int creator;
	public byte authType;
	// 2004的群分类
	public int oldCategory;
	// 2005采用的分类
	public int category;
	public int versionId;
	public String name;
	public String description;
	public String notice;
	public String unknownId;

	/**
	 * 构造函数
	 */
	public ClusterInfo() {
		clusterId = externalId = creator = 0;
		type = QQ.QQ_CLUSTER_TYPE_PERMANENT;
		authType = QQ.QQ_AUTH_CLUSTER_NEED;
		category = 0;
		versionId = 0;
		description = notice = name = "";
	}
	
	/**
	 * 读取临时群信息
	 * 
	 * @param buf
	 */
	public void readTempClusterInfo(ByteBuffer buf) {
    	// 群类型
    	type = buf.get();
    	// 父群内部ID
    	externalId = buf.getInt();
    	// 临时群内部ID
    	clusterId = buf.getInt();
    	// 群的创建者
    	creator = buf.getInt();
    	// 认证类型
    	authType = buf.get();
    	// 未知的1字节
    	buf.get();
        // 群的分类
        category = buf.getChar();
        // 群组名称的长度
        int len = buf.get() & 0xFF;
        byte[] b1 = new byte[len];
        buf.get(b1);
        // 转换成字符串
        name = Util.getString(b1);
        name = Util.filterUnprintableCharacter(name);
	}
	
    /**
     * 给定一个输入流，解析ClusterInfo结构，这个方法适合于得到群信息的回复包
     * @param buf
     */
    public void readClusterInfo(ByteBuffer buf) {
    	// 读取群ID，群ID也叫做内部ID，
    	clusterId = buf.getInt();
    	// 读取外部ID
    	externalId = buf.getInt();
    	// 群类型
    	type = buf.get();
        // 4个未知字节
        buf.getInt();
    	// 群的创建者
    	creator = buf.getInt();
    	// 认证类型
    	authType = buf.get();
        // 群的分类
        oldCategory = buf.getInt();
        // 未知2字节
        buf.getChar();
        // 群分类
        category = buf.getInt();
        // 未知2字节
        buf.getChar();
        // 未知1字节
        buf.get();
        // 未知4字节
        buf.getInt();
        // Member Version ID
        versionId = buf.getInt();
        // 群组名称的长度
        int len = buf.get() & 0xFF;
        byte[] b1 = new byte[len];
        buf.get(b1);
        // 未知的两字节
        buf.getChar();
        // 群声明长度
        len = buf.get() & 0xFF;
        byte[] b2 = new byte[len];
        buf.get(b2);
        // 群描述长度
        len = buf.get() & 0xFF;
        byte[] b3 = new byte[len];
        buf.get(b3);
        // 转换成字符串
        name = Util.filterUnprintableCharacter(Util.getString(b1));
        notice = Util.filterUnprintableCharacter(Util.getString(b2));
        description = Util.filterUnprintableCharacter(Util.getString(b3));
    }
    
    /**
     * 从搜索群的回复中生成一个ClusterInfo结构
     * @param buf
     */
    public void readClusterInfoFromSearchReply(ByteBuffer buf) {
    	// 内部ID
    	clusterId = buf.getInt();
    	// 外部ID
    	externalId = buf.getInt();
    	// 群类型
    	type = buf.get();
    	// 未知的4字节
    	buf.getInt();
    	// 创建者ID
    	creator = buf.getInt();
    	// 2004群分类
    	oldCategory = buf.getInt();
    	// 2005群分类
    	category = buf.getInt();
    	// 未知的2字节
    	buf.getChar();
    	// 群名称长度和群名称
    	int len = buf.get() & 0xFF;
    	byte[] b1 = new byte[len];
    	buf.get(b1);
    	// 两个未知字节
    	buf.getChar();
    	// 认证类型
    	authType = buf.get();
    	// 群描述长度和群描述
    	len = buf.get() & 0xFF;
    	byte[] b2 = new byte[len];
    	buf.get(b2);
        // 转换成字符串
    	name = Util.filterUnprintableCharacter(Util.getString(b1));
    	description = Util.filterUnprintableCharacter(Util.getString(b2));
    	// 未知1字节
    	buf.get();
    	// 未知id长度和未知id
    	len = buf.get() & 0xFF;
    	unknownId = Util.getString(buf, len);
    }
}
