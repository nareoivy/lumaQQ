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

import edu.tsinghua.lumaqq.qq.packets.in.ReceiveIMPacket;

/**
 * 为LumaQQ提供通用的聊天机器人支持，用户如果有现成的机器人程序，可以通过实现这个接口
 * 对其包装，然后修改robot.xml文件，即可通过系统菜单打开/关闭机器人功能
 * 
 * @author luma
 */
public interface IRobot {
	/**
	 * 根据message得到一条回复消息
	 * 
	 * @param packet
	 * 		接受消息包
	 * @return
	 * 		回复的消息，返回null表示不响应这条消息
	 */
	public String getReply(ReceiveIMPacket packet);
}
