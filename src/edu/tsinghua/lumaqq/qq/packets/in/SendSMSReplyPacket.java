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

import edu.tsinghua.lumaqq.qq.Util;
import edu.tsinghua.lumaqq.qq.beans.QQUser;
import edu.tsinghua.lumaqq.qq.beans.SMSReply;
import edu.tsinghua.lumaqq.qq.packets.BasicInPacket;
import edu.tsinghua.lumaqq.qq.packets.PacketParseException;

/**
 * <pre>
 * 发送短消息的回复包，格式为：
 * 1. 头部
 * 2. 未知1字节
 * 3. 四个未知字节，全0
 * 4. 未知1字节
 * 5. 回复消息长度，1字节
 * 6. 回复消息
 * 7. 接受者中的手机号码个数，1字节
 * 8. 手机的号码，18字节，不够的部分为0
 * 9. 未知的2字节，一般为0x0000
 * 10. 回复码，1字节，表示对于这个接受者来说，短信发送的状态如何
 * 11. 附加消息长度，1字节
 * 12. 附加消息
 * 13. 未知的1字节，一般都是0x00
 * 14. 如果有更多手机号，重复8-13部分
 * 注：8-14部分只有当7部分不为0时存在
 * 15. 接受者中QQ号码的个数，1字节
 * 16. QQ号码，4字节
 * 17. 回复码，1字节，表示对于这个接受者来说，短信发送的状态如何
 * 18. 附加消息长度，1字节
 * 19. 附加消息
 * 20. 未知的1字节，一般都是0x00
 * 21. 如果有更多QQ号，重复16-20部分
 * 注：16-21部分只有当15部分不为0时才存在
 * 22. 未知的1字节，一般是0x00
 * 23. 参考消息长度，1字节
 * 24. 参考消息
 * 25. 尾部
 * </pre>
 * 
 * @author luma
 */
public class SendSMSReplyPacket extends BasicInPacket {    
	public String message;
	public List<SMSReply> replies;
	public String reference;
	
    /**
     * @param buf
     * @param length
     * @throws PacketParseException
     */
    public SendSMSReplyPacket(ByteBuffer buf, int length, QQUser user) throws PacketParseException {
        super(buf, length, user);
    }
    
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.packets.InPacket#getPacketName()
     */
	@Override
    public String getPacketName() {
        return "Send SMS Reply Packet";
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.packets.InPacket#parseBody(java.nio.ByteBuffer)
     */
	@Override
    protected void parseBody(ByteBuffer buf) throws PacketParseException {
		// 未知1字节
		buf.get();
		// 未知4字节
		buf.getInt();
		// 未知1字节
		buf.get();
		// 回复消息
		int len = buf.get() & 0xFF;
		message = Util.getString(buf, len);
		
		/*
		 * 回复消息
		 */
		replies = new ArrayList<SMSReply>();
		// 手机个数
		len = buf.get() & 0xFF;
		while(len-- > 0) {
			SMSReply reply = new SMSReply();
			reply.readMobileBean(buf);
			replies.add(reply);
		}
		// QQ号个数
		len = buf.get() & 0xFF;
		while(len-- > 0) {
			SMSReply reply = new SMSReply();
			reply.readQQBean(buf);
			replies.add(reply);
		}
		// 未知1字节
		buf.get();
		// 参考消息
		len = buf.get() & 0xFF;
		reference = Util.getString(buf, len);
    }
}
