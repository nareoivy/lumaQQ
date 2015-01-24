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
package edu.tsinghua.lumaqq.qq.packets.in;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import edu.tsinghua.lumaqq.qq.beans.DownloadFriendEntry;
import edu.tsinghua.lumaqq.qq.beans.QQUser;
import edu.tsinghua.lumaqq.qq.packets.BasicInPacket;
import edu.tsinghua.lumaqq.qq.packets.PacketParseException;

/**
 * <pre>
 * 请求下载分组好友列表的回复包，格式为
 * 1. 头部
 * 2. 操作字节，下载为0x1
 * 3. 回复码，1字节
 * 4. 4个未知字节，全0
 * 5. 下一个下载包的起始好友号，4字节
 * 6. 好友的QQ号，4字节
 * 7. 好友类型，0x1表示普通好友，0x4表示群
 * 8. 分组序号，1字节，但是这个很奇怪，不是1，2，3那样的，而是用序号乘4，比如如果是属于第2个组，
 *    那么这个就是8，注意我的好友组是第0组，但是有可能这个数字不是4的倍数，那就不知道什么
 *    意思了，但是除以4得到组序号的方法仍然不受影响
 * 9. 如果还有更多好友，重复4，5，6部分
 * 10. 尾部
 * 
 * 这个包解析后产生的数据可以通过哈希表friends访问，每一个组为一个list，用组的索引为key，
 * 比如第0，第1组，分别可以得到一个List对象，list中包含了好友的qq号
 * </pre>
 * 
 * @author luma
 */
public class DownloadGroupFriendReplyPacket extends BasicInPacket {
	public List<DownloadFriendEntry> friends;
	public int beginFrom;
	public byte replyCode;
	public byte subCommand;
	
    /**
     * 构造函数
     * @param buf 缓冲区
     * @param length 包长度
     * @throws PacketParseException 解析错误
     */
    public DownloadGroupFriendReplyPacket(ByteBuffer buf, int length, QQUser user) throws PacketParseException {
        super(buf, length, user);
    }   
    
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.packets.OutPacket#getPacketName()
     */
	@Override
    public String getPacketName() {
        return "Download Group Friend Reply Packet";
    }
	
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.packets.InPacket#parseBody(java.nio.ByteBuffer)
     */
	@Override
    protected void parseBody(ByteBuffer buf) throws PacketParseException {
    	// 操作字节，下载为0x1
    	subCommand = buf.get();
		// 回复码，1字节
    	replyCode = buf.get();
		// 4个未知字节，全0
    	buf.getInt();
        // 起始好友号
        beginFrom = buf.getInt();
		// 循环读取各好友信息，加入到list中
        friends = new ArrayList<DownloadFriendEntry>();
		while(buf.hasRemaining()) {
			DownloadFriendEntry dfe = new DownloadFriendEntry();
			dfe.readBean(buf);
			friends.add(dfe);
		}
    }
}
