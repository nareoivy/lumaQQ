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
import edu.tsinghua.lumaqq.qq.Util;
import edu.tsinghua.lumaqq.qq.beans.QQUser;
import edu.tsinghua.lumaqq.qq.packets.PacketParseException;

/**
 * <pre>
 * 修改群信息的请求包，格式为：
 * 1. 头部
 * 2. 命令类型，1字节，修改群信息是0x03
 * 3. 群的内部ID，4字节
 * 4. 群类型，1字节
 * 5. 群的认证类型，1字节
 * 6. 2004群分类，4字节
 * 7. 2005群分类，4字节
 * 8. 群名称长度，1字节
 * 9. 群名称
 * 10. 未知的两字节，全0
 * 11. 群声明长度，1字节
 * 12. 群声明
 * 13. 群简介长度，1字节
 * 14. 群简介
 * 16. 尾部
 * </pre>
 * 
 * @author luma
 */
public class ClusterModifyInfoPacket extends ClusterCommandPacket {
	private byte authType;
	private int category;
	private int oldCategory;
	private String name;
	private String notice;
	private String description;
	private byte type;
	
	/**
	 * 构造函数
	 */
	public ClusterModifyInfoPacket(QQUser user) {
		super(user);		
		subCommand = QQ.QQ_CLUSTER_CMD_MODIFY_CLUSTER_INFO;
		authType = QQ.QQ_AUTH_CLUSTER_NEED;
		name = description = notice = "";
		type = QQ.QQ_CLUSTER_TYPE_PERMANENT;
	}

    /**
     * @param buf
     * @param length
     * @throws PacketParseException
     */
    public ClusterModifyInfoPacket(ByteBuffer buf, int length, QQUser user)
            throws PacketParseException {
        super(buf, length, user);
    }
    
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.packets.OutPacket#getPacketName()
     */
	@Override
    public String getPacketName() {
        return "Cluster Modify Info Packet";
    }
    
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.packets.OutPacket#putBody(java.nio.ByteBuffer)
     */
	@Override
    protected void putBody(ByteBuffer buf) {
		// 群命令类型
		buf.put(subCommand);
		// 群内部ID
		buf.putInt(clusterId);
		// 未知1字节
		buf.put(type);
		// 认证类型
		buf.put(authType);
		// 2004群分类
		buf.putInt(oldCategory);
		// 群分类，同学，朋友，之类的
		buf.putInt(category);
		// 群名称长度和群名称
		byte[] b = Util.getBytes(name);
		buf.put((byte)(b.length & 0xFF));
		buf.put(b);
		// 未知的2字节
		buf.putChar((char)0);
		// 群声明长度和群声明
		b = Util.getBytes(notice);
		buf.put((byte)(b.length & 0xFF));
		buf.put(b);
		// 群描述长度和群描述
		b = Util.getBytes(description);
		buf.put((byte)(b.length & 0xFF));
		buf.put(b);
    }
    
    /**
	 * @return Returns the authType.
	 */
	public byte getAuthType() {
		return authType;
	}
	
	/**
	 * @param authType The authType to set.
	 */
	public void setAuthType(byte authType) {
		this.authType = authType;
	}
	
	/**
	 * @return Returns the description.
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * @param description The description to set.
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @param name The name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * @return Returns the notice.
	 */
	public String getNotice() {
		return notice;
	}
	
	/**
	 * @param notice The notice to set.
	 */
	public void setNotice(String notice) {
		this.notice = notice;
	}

	/**
	 * @return Returns the type.
	 */
	public byte getType() {
		return type;
	}

	/**
	 * @param type The type to set.
	 */
	public void setType(byte type) {
		this.type = type;
	}

	/**
	 * @return Returns the category.
	 */
	public int getCategory() {
		return category;
	}

	/**
	 * @param category The category to set.
	 */
	public void setCategory(int category) {
		this.category = category;
	}

	/**
	 * @return Returns the oldCategory.
	 */
	public int getOldCategory() {
		return oldCategory;
	}

	/**
	 * @param oldCategory The oldCategory to set.
	 */
	public void setOldCategory(int oldCategory) {
		this.oldCategory = oldCategory;
	}
}
