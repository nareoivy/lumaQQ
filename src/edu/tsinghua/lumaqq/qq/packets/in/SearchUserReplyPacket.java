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

import edu.tsinghua.lumaqq.qq.beans.QQUser;
import edu.tsinghua.lumaqq.qq.beans.UserInfo;
import edu.tsinghua.lumaqq.qq.packets.BasicInPacket;
import edu.tsinghua.lumaqq.qq.packets.PacketParseException;

/**
 * <pre>
 * 搜索在线用户的回复包，格式为
 * 
 * ************* 格式1 *************
 * 头部
 * ------ 加密开始（会话密钥）------
 * 回复码，1字节
 * 未知4字节
 * 分割符，1字节，0x1F
 * -------- UserInfo Start (Repeat) -------
 * 用户QQ号的字符串形式
 * 分割符，1字节，0x1E
 * 用户昵称
 * 分割符，1字节，0x1E
 * 用户所在地区
 * 分割符，1字节，0x1E
 * 头像号码的字符串形式
 * 分割符，1字节，0x1E
 * 分割符，1字节，0x1F
 * -------- UserInfo End -----------
 * --------- 加密结束 ---------
 * 尾部
 * </pre>
 * 
 * @author luma
 */
public class SearchUserReplyPacket extends BasicInPacket {
	public byte replyCode;
	public List<UserInfo> users;
	
    /**
     * 构造函数
     * @param buf 缓冲区
     * @param length 包长度
     * @throws PacketParseException 解析错误
     */
    public SearchUserReplyPacket(ByteBuffer buf, int length, QQUser user) throws PacketParseException {
        super(buf, length, user);       
    }   
    
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.packets.InPacket#getPacketName()
     */
	@Override
    public String getPacketName() {
        return "Search User Reply Packet";
    }
	
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.packets.InPacket#parseBody(java.nio.ByteBuffer)
     */
	@Override
    protected void parseBody(ByteBuffer buf) throws PacketParseException {
		// 回复码，1字节
		replyCode = buf.get();
		// 未知4字节
		buf.getInt();
		// 分割符，1字节，0x1F
		buf.getChar();
		 
        // 只要还有数据就继续读取下一个friend结构
    	users = new ArrayList<UserInfo>();
		while(buf.hasRemaining()) {
        	UserInfo ui = new UserInfo();
            ui.readBean(buf);
            
            // 添加到list
            users.add(ui);
		}
    }
}
