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
import java.util.ArrayList;
import java.util.List;

import edu.tsinghua.lumaqq.qq.QQ;
import edu.tsinghua.lumaqq.qq.Util;
import edu.tsinghua.lumaqq.qq.beans.QQUser;
import edu.tsinghua.lumaqq.qq.packets.BasicOutPacket;
import edu.tsinghua.lumaqq.qq.packets.PacketParseException;

/**
 * <pre>
 * 上传下载分组名字的消息包，格式为
 * 1. 头部
 * 2. 操作方式字节，如果为0x2，为上传组名，如果为0x1，为请求下载组名
 *    如果为0x2，后面的部分为
 * 	  i.   组序号，qq缺省的组，比如我的好友，序号是0，其他我们自己添加的组，从1开始，一个字节。
 *         但是要注意的是，这里不包括我的好友组，因为我的好友组是QQ的缺省组，无需上传名称
 *    ii.  16个字节的组名，如果组名长度少于16个字节，后面的填0。之所以是16个，是因为QQ的组名长度最多8个汉字
 *    iii. 如果有更多组，重复i，ii部分
 *    如果为0x1，后面的部分为
 *    i.   未知字节0x2
 *    ii.  4个未知字节，全0 
 * 3. 尾部
 * 
 * 这个包没有限制添加的组名叫什么，也没有明确规定第一个组必须是
 * 我的好友组，这些规范需要在上层程序中实现。当然也可以不一定非要第一个组是
 * 我的好友组，这些客户端的trick随便你怎么搞
 * 
 * 每次上传都必须上传所有组名
 * </pre>
 * 
 * @author luma
 */
public class GroupDataOpPacket extends BasicOutPacket {
	private List<String> groups;
	private byte type;
	
    /**
     * 构造函数
     */
    public GroupDataOpPacket(QQUser user) {
        super(QQ.QQ_CMD_GROUP_DATA_OP, true, user);
		type = QQ.QQ_SUB_CMD_UPLOAD_GROUP_NAME;
		groups = new ArrayList<String>();
    }

    /**
     * @param buf
     * @param length
     * @throws PacketParseException
     */
    public GroupDataOpPacket(ByteBuffer buf, int length, QQUser user)
            throws PacketParseException {
        super(buf, length, user);
    }
    
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.packets.OutPacket#getPacketName()
     */
	@Override
    public String getPacketName() {
        switch(type) {
            case QQ.QQ_SUB_CMD_UPLOAD_GROUP_NAME:
                return "Group Data Packet - Upload Group";
            case QQ.QQ_SUB_CMD_DOWNLOAD_GROUP_NAME:
                return "Group Data Packet - Download Group";
            default:
                return "Group Data Packet - Unknown Sub Command";
        }
    }
    
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.packets.OutPacket#putBody(java.nio.ByteBuffer)
     */
	@Override
    protected void putBody(ByteBuffer buf) {
		// 上传操作标志字节
		buf.put(type);
		if(type == QQ.QQ_SUB_CMD_UPLOAD_GROUP_NAME) {
			// 循环写入各个组
			int size = groups.size();
			for(int i = 0; i < size; i++) {
				String name = groups.get(i);
				// 组序号
				buf.put((byte)(i + 1));
				// 组名称
				byte[] nameBytes = Util.getBytes(name);
				// 超过最大长度的，截短；小于最大长度的，补0
				if(nameBytes.length > QQ.QQ_MAX_GROUP_NAME)
					buf.put(nameBytes, 0, QQ.QQ_MAX_GROUP_NAME);
				else {
					buf.put(nameBytes);
					int j = QQ.QQ_MAX_GROUP_NAME - nameBytes.length;
					while(j-- > 0)
						buf.put((byte)0);
				}
			}
		} else {
			// 未知字节0x2
			buf.put((byte)0x2);
			// 未知4字节，全0
			buf.putInt(0);
		}
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
	 * @return Returns the groups.
	 */
	public List getGroups() {
		return groups;
	}
	/**
	 * @param groups The groups to set.
	 */
	public void setGroups(List<String> groups) {
		this.groups = groups;
	}
}
