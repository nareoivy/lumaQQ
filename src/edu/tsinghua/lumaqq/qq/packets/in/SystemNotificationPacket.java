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

import edu.tsinghua.lumaqq.qq.QQ;
import edu.tsinghua.lumaqq.qq.Util;
import edu.tsinghua.lumaqq.qq.beans.QQUser;
import edu.tsinghua.lumaqq.qq.packets.BasicInPacket;
import edu.tsinghua.lumaqq.qq.packets.PacketParseException;


/**
 * <pre>
 * 系统消息包，系统消息和ReceiveIMPacket里面的系统通知有什么区别呢？
 * 系统消息是表示你被别人加为好友了之类的消息，所以有源有目的，其他人
 * 收不到的，系统通知是系统发给大家的消息。
 * 
 * ********* 对方同意加他为好友 ********
 * 头部
 * ------- 加密开始（会话密钥）--------
 * 系统消息类型，字符串形式，"03"
 * 分隔符，1字节，0x1F
 * 源QQ号，字符串形式
 * 分隔符，1字节，0x1F
 * 目的QQ号，字符串形式
 * 分隔符，1字节，0x1F
 * 附加消息，由于同意没有附加消息，这里是0x30
 * 分隔符，1字节，0x1F
 * 未知id的长度，2字节
 * 未知id
 * ------- 加密结束 --------
 * 尾部
 * 
 * ********** 对方拒绝加他为好友 **********
 * 头部
 * ------- 加密开始（会话密钥）--------
 * 系统消息类型，字符串形式，"04"
 * 分隔符，1字节，0x1F
 * 源QQ号，字符串形式
 * 分隔符，1字节，0x1F
 * 目的QQ号，字符串形式
 * 分隔符，1字节，0x1F
 * 拒绝消息
 * 分隔符，1字节，0x1F
 * 未知id的长度，2字节
 * 未知id
 * ------- 加密结束 --------
 * 尾部
 * 
 * ********** 自己被某人加为好友 **********
 * 头部
 * ------- 加密开始（会话密钥）--------
 * 系统消息类型，字符串形式，"40"
 * 分隔符，1字节，0x1F
 * 源QQ号，字符串形式
 * 分隔符，1字节，0x1F
 * 目的QQ号，字符串形式
 * 分隔符，1字节，0x1F
 * 未知token的长度，1字节
 * 未知token
 * 未知id长度，2字节
 * 未知id
 * ------- 加密结束 --------
 * 尾部
 * 
 * ********** 对方请求加自己为好友 **********
 * 头部
 * ------- 加密开始（会话密钥）--------
 * 系统消息类型，字符串形式，"41"
 * 分隔符，1字节，0x1F
 * 源QQ号，字符串形式
 * 分隔符，1字节，0x1F
 * 目的QQ号，字符串形式
 * 分隔符，1字节，0x1F
 * 附加消息长度，1字节
 * 附加消息
 * 是否允许对方加自己为好友，1字节，0x01表示允许，0x02表示不允许
 * 未知id长度，2字节
 * 未知id
 * ------- 加密结束 --------
 * 尾部
 * 
 * ********** 对方同意加他为好友，并把自己也加为他的好友 **********
 * 头部
 * ------- 加密开始（会话密钥）--------
 * 系统消息类型，字符串形式，"43"
 * 分隔符，1字节，0x1F
 * 源QQ号，字符串形式
 * 分隔符，1字节，0x1F
 * 目的QQ号，字符串形式
 * 分隔符，1字节，0x1F
 * 附加消息长度，1字节
 * 附加消息
 * 未知1字节
 * 未知id长度，2字节
 * 未知id
 * ------- 加密结束 --------
 * 尾部
 * </pre>
 *
 * Note: 只有使用2005的0x00A8发送认证消息，才会收到QQ_SYS_ADD_FRIEND_REQUEST_EX消息
 * @author luma
 */
public class SystemNotificationPacket extends BasicInPacket {   
	private static final byte DELIMIT = 0x1F; 
    // 消息类型
    public int type;
    // 从哪里来，是源的QQ号
    public int from;
    // 到哪里去，目的的QQ号
    public int to;
    // 附加的消息，比如如果别人拒绝了你加他为好友，并说了理由，那就在这里了
    public String message;
    
    // 未知id, for QQ_SYS_ADD_FRIEND_REJECTED
    public String unknownId;
    
    // 未知令牌，for QQ_SYS_BEING_ADDED_EX
    public byte[] unknownToken;
    
    // only for QQ_SYS_ADD_FRIEND_REQUEST_EX
    public boolean allowAddReverse;
    
    /**
     * 构造函数
     * @param buf 缓冲区
     * @param length 包长度
     * @throws PacketParseException 解析错误
     */
    public SystemNotificationPacket(ByteBuffer buf, int length, QQUser user) throws PacketParseException {
        super(buf, length, user);
    }   
    
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.packets.InPacket#getPacketName()
     */
	@Override
    public String getPacketName() {
        return "System Notification Packet";
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.packets.InPacket#parseBody(java.nio.ByteBuffer)
     */
	@Override
    protected void parseBody(ByteBuffer buf) throws PacketParseException {
		// get type
		String s = Util.getString(buf, 2);
		type = Util.getInt(s, 0);
		// divider
		buf.get();
		// source
		s = Util.getString(buf, DELIMIT);
		from = Util.getInt(s, 0);
		// to
		s = Util.getString(buf, DELIMIT);
		to = Util.getInt(s, 0);
		
		switch(type) {
			case QQ.QQ_SYS_ADD_FRIEND_APPROVED:
				// 空附加消息
				Util.getString(buf, DELIMIT);
				// 未知id
				int len = buf.getChar();
				unknownId = Util.getString(buf, len);
				break;
			case QQ.QQ_SYS_ADD_FRIEND_REJECTED:
				// 拒绝消息
				message = Util.getString(buf, DELIMIT);
				// 未知id
				len = buf.getChar();
				unknownId = Util.getString(buf, len);
				break;
			case QQ.QQ_SYS_BEING_ADDED_EX:
				// 未知token
				len = buf.get() & 0xFF;
				unknownToken = new byte[len];
				buf.get(unknownToken);
				// 未知id
				len = buf.getChar();
				unknownId = Util.getString(buf, len);
				break;
			case QQ.QQ_SYS_ADD_FRIEND_REQUEST_EX:
				// 附加消息
				len = buf.get() & 0xFF;
				message = Util.getString(buf, len);
				// 是否允许对方加自己
				allowAddReverse = buf.get() == QQ.QQ_FLAG_ALLOW_ADD_REVERSE;
				// 未知id
				len = buf.getChar();
				unknownId = Util.getString(buf, len);
				break;
			case QQ.QQ_SYS_ADD_FRIEND_APPROVED_AND_ADD:
				// 附加消息
				len = buf.get() & 0xFF;
				message = Util.getString(buf, len);
				// 未知1字节
				buf.get();
				// 未知id
				len = buf.getChar();
				unknownId = Util.getString(buf, len);
				break;
		}
    }
}
