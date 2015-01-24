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
import edu.tsinghua.lumaqq.qq.packets.BasicOutPacket;
import edu.tsinghua.lumaqq.qq.packets.PacketParseException;

/**
 * <pre>
 * 搜索在线用户的包，格式为
 * 
 * ********* 格式1 **********
 * 头部
 * ------- 加密开始（会话密钥）--------
 * 搜索类型，1字节，0x31, 表示搜索全部在线用户
 * 1字节分隔符: 0x1F
 * 页号，从0开始，字符串形式
 * ------- 加密结束 ----------
 * 尾部
 * 
 * ******** 格式2 ***********
 * 头部
 * ---------- 加密开始（会话密钥）----------
 * 搜索类型，1字节，0x32，表示按照昵称搜索，或者0x33，表示按照QQ号搜索
 * 1字节分隔符: 0x1F
 * QQ号的字符串形式，如果没有提供，为1字节，0x2D
 * 1字节分隔符: 0x1F
 * 昵称的字符串形式，如果没有提供，为1字节，0x2D
 * 1字节分隔符: 0x1F
 * Email的字符串形式，由于2006不支持根据Email搜索，这个字段未用，为1字节, 0x2D
 * 1字节分隔符: 0x1F
 * 页号，从0开始，字符串形式
 * 1字节结束符，1字节，0x00
 * ------ 加密结束 ----------       
 * 尾部
 * </pre> 
 * 
 * @author luma
 */
public class SearchUserPacket extends BasicOutPacket {
	private byte searchType;
	private String page;
	private String qqStr;
	private String nick;
	private String email;
	
	/** 分隔符 */
	private static final byte DELIMIT = 0x1F;
	/** 如果字段为空，用0x2D替代，即'-'字符 */
	private static final byte NULL = 0x2D;
	
    /**
     * 构造函数
     */
    public SearchUserPacket(QQUser user) {
        super(QQ.QQ_CMD_SEARCH_USER, true, user);
		page = "0";
		searchType = QQ.QQ_SEARCH_ALL;
		qqStr = nick = email = "";
    }
	
    /**
     * @param buf
     * @param length
     * @throws PacketParseException
     */
    public SearchUserPacket(ByteBuffer buf, int length, QQUser user)
            throws PacketParseException {
        super(buf, length, user);
    }
    
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.packets.OutPacket#getPacketName()
     */
	@Override
    public String getPacketName() {
        return "Search User Packet";
    }
    
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.packets.OutPacket#putBody(java.nio.ByteBuffer)
     */
	@Override
    protected void putBody(ByteBuffer buf) {
		// 开始组装内容
		switch(searchType) {
			case QQ.QQ_SEARCH_ALL:
				buf.put(searchType);
				buf.put(DELIMIT);
				buf.put(page.getBytes());	
				break;
			case QQ.QQ_SEARCH_CUSTOM_ACCURATE:
			case QQ.QQ_SEARCH_CUSTOM_FUZZY:
				buf.put(searchType);
				buf.put(DELIMIT);
				// QQ号
				if(qqStr == null || qqStr.equals("")) 
					buf.put(NULL);
				else 
					buf.put(qqStr.getBytes());
				buf.put(DELIMIT);	
				// 昵称
				if(nick == null || nick.equals("")) 
					buf.put(NULL);
				else
					buf.put(Util.getBytes(nick));
				buf.put(DELIMIT);			
				// email
				if(email == null || email.equals("")) 
					buf.put(NULL);
				else
					buf.put(email.getBytes());
				buf.put(DELIMIT);	
				// 结尾
				buf.put(page.getBytes());
				buf.put((byte)0x0);
				break;
		}
    }
    
    /**
	 * @param page The page to set.
	 */
	public void setPage(int page) {
		this.page = String.valueOf(page);
	}
	
	/**
	 * @param searchType The searchType to set.
	 */
	public void setSearchType(byte searchType) {
		this.searchType = searchType;
	}
	
	/**
	 * @param nick The nick to set.
	 */
	public void setNick(String nick) {
		this.nick = nick;
	}
	
	/**
	 * @param qqNum The qqNum to set.
	 */
	public void setQQStr(int qqNum) {
		this.qqStr = String.valueOf(qqNum);
	}
	
	/**
	 * @param qqStr
	 */
	public void setQQStr(String qqStr) {
		this.qqStr = qqStr;
	}
	
	/**
	 * @param email The email to set.
	 */
	public void setEmail(String email) {
		this.email = email;
	}
}
