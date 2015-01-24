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
package edu.tsinghua.lumaqq.qq.robot;

import edu.tsinghua.lumaqq.qq.QQ;
import edu.tsinghua.lumaqq.qq.packets.in.ReceiveIMPacket;

/**
 * 什么也不干，只返回一条信息的机器人。对于自动回复，不回复
 * 
 * @author luma
 */
public class DummyRobot implements IRobot {
	/* (non-Javadoc)
	 * @see edu.tsinghua.lumaqq.qq.robot.IRobot#getReply(edu.tsinghua.lumaqq.qq.packets.in.ReceiveIMPacket)
	 */
	public String getReply(ReceiveIMPacket packet) {
		/*
		 * Example:
		 * 1. 如何得到发送者QQ号
		 * packet.header.sender
		 * 
		 * 2. 如何得到消息内容
		 * packet.normalIM.messageBytes是消息的字节数组内容，如果需要得到字符串形式
		 * new String(packet.normalIM.messageBytes, QQ.QQ_CHARSET_DEFAULT)
		 * 对于消息格式，参见edu.tsinghua.lumaqq.qq.beans.NormalIM
		 * 
		 * 3. 如何判断这个消息是一个大消息中的分片？
		 * if(packet.normalIM.totalFragments > 1) {
		 * 		// 做你的处理，怎么处理，你决定，你可以把他缓存起来等待所有分片都收到为止
		 * }
		 * 
		 * 4. 如何知道这个消息的分片序号？
		 * packet.normalIM.fragmentSequence
		 * 
		 * 5. 如何知道这个消息的id？消息id也是用在分片情况时，同一个消息的分片，消息id相同
		 * packet.normalIM.messageId
		 * 
		 * 6. 如何知道一个消息是不是自动回复？
		 * if(packet.normalIM.replyType != QQ.QQ_IM_AUTO_REPLY) {
		 * 		// 做你想做的
		 * }
		 * 
		 * 更多内容和可用字段
		 * 参考edu.tsinghua.lumaqq.qq.beans.NormalIMHeader
		 * 参考edu.tsinghua.lumaqq.qq.beans.ReceiveIMHeader
		 */
		if(packet.normalIM.replyType != QQ.QQ_IM_AUTO_REPLY)
			return "Hello, I am robot";
		else
			return null;
	}
}
