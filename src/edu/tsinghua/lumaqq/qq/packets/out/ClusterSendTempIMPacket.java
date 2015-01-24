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
 * 发送临时群消息
 * 1. 头部
 * 2. 命令类型，1字节，0x35
 * 3. 群类型，1字节
 * 4. 父群内部ID，4字节
 * 5. 群内部ID，4字节
 * 6. 后面的数据的总长度，2字节
 * 7. Content Type, 2字节，0x0001表示纯文件，0x0002表示有自定义表情
 * 8. 消息分片数，1字节
 * 9. 分片序号，1字节，从0开始
 * 11. 消息id，2字节，同一条消息的不同分片id相同
 * 12. 4字节，未知
 * 13. 消息内容，最后一个分片追加空格
 * Note: 结尾处的空格是必须的，如果不追加空格，会导致有些缺省表情显示为乱码
 * 14. 消息的尾部，包含一些消息的参数，比如字体颜色啦，等等等等，顺序是
 *     1. 字体修饰属性，bold，italic之类的，2字节，具体的设置是
 *         i.   bit0-bit4用来表示字体大小，所以最大是32
 *         ii.  bit5表示是否bold
 *         iii. bit6表示是否italic
 *         iv.  bit7表示是否underline
 *     2. 颜色Red，1字节
 *     3. 颜色Green，1字节
 *     4. 颜色Blue，1字节
 *     5. 1个未知字节，置0先
 *     6. 消息编码，2字节，0x8602为GB，0x0000为EN，其他未知，好像可以自定义，因为服务器好像不干涉
 *     7. 可变长度的一段信息，字体名后面跟一个回车符，比如0xcb, 0xce, 0xcc, 0xe5,表示宋体
 * 15. 1字节，表示14和15部分的字节长度
 * 16. 尾部 
 * 
 * 注意：只有最后一个分片有14, 15, 16部分
 * </pre>
 * 
 * @author luma
 */
public class ClusterSendTempIMPacket extends ClusterSendIMExPacket {
    // 临时群类型
    private byte type;
    // 父群内部ID
    private int parentClusterId;
    
	/**
	 * 构造函数
	 */
	public ClusterSendTempIMPacket(QQUser user) {
		super(user);		
		subCommand = QQ.QQ_CLUSTER_CMD_SEND_TEMP_IM;
	}
	
    /**
     * @param buf
     * @param length
     * @throws PacketParseException
     */
    public ClusterSendTempIMPacket(ByteBuffer buf, int length, QQUser user)
            throws PacketParseException {
        super(buf, length, user);
    }
    
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.packets.OutPacket#getPacketName()
     */
	@Override
    public String getPacketName() {
        return "Cluster Send Temp Cluster IM Packet";
    }
    
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.packets.OutPacket#putBody(java.nio.ByteBuffer)
     */
	@Override
    protected void putBody(ByteBuffer buf) {
		// 命令类型
		buf.put(subCommand);
		// 群类型
		buf.put(type);
		// 父群ID
		buf.putInt(parentClusterId);
		// 群内部ID
		buf.putInt(clusterId);
	    // 后面数据的长度，这个长度需要根据消息长度和字体名称长度计算才能知道，
		// 所以先来产生消息和字体名称字节数组，先占个位置		
		int pos = buf.position();
		buf.putChar((char)0);
		// 未知2字节
		buf.putChar((char)1);
		// 分片数
		buf.put((byte)totalFragments);
		// 分片序号
		buf.put((byte)fragmentSequence);
		// 消息id
		buf.putChar(messageId);
		// 未知4字节
		buf.putInt(0);
	    // 以0结束的消息，首先我们要根据用户设置的message，解析出一个网络可发送的格式
	    //    这一步比较麻烦，暂时想不到好的办法
		byte[] msgBytes = null;
	    int j, i = 0;
	    while((j = message.indexOf((char)QQ.QQ_TAG_DEFAULT_FACE, i)) != -1) {
	    	String sub = message.substring(i, j);
	    	if(!sub.equals("")) {
	    	    msgBytes = Util.getBytes(sub);
			    buf.put(msgBytes);
	    	}
		    buf.put(QQ.QQ_TAG_DEFAULT_FACE);
		    buf.put((byte)(message.charAt(j + 1) & 0xFF));
		    i = j + 2;
	    }
	    if(i < message.length()) {
	    	String sub = message.substring(i);
	    	msgBytes = Util.getBytes(sub);		
		    buf.put(msgBytes);	    	
	    }
		// 只有最后一个分片有空格和字体属性
		if(fragmentSequence == totalFragments - 1) {
			buf.put((byte)0x20);
			fontStyle.writeBean(buf);
		}
        // 写入长度
	    int cur = buf.position();
	    buf.position(pos);
	    buf.putChar((char)(cur - pos - 2));
	    buf.position(cur);
    }
    
    /**
     * @return Returns the externalId.
     */
    public int getParentClusterId() {
        return parentClusterId;
    }
    /**
     * @param externalId The externalId to set.
     */
    public void setParentClusterId(int externalId) {
        this.parentClusterId = externalId;
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
}
