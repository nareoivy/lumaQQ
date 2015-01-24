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
package edu.tsinghua.lumaqq.qq;

import java.util.concurrent.Callable;

import edu.tsinghua.lumaqq.qq.events.PacketEvent;
import edu.tsinghua.lumaqq.qq.packets.InPacket;

/**
 * 触发PacketEvent
 * 
 * @author luma
 */
public class PacketEventTrigger<T> implements Callable<T> {
	private QQClient client;
	
	public PacketEventTrigger(QQClient client) {
		this.client = client;
	}

	/* (non-Javadoc)
	 * @see java.util.concurrent.Callable#call()
	 */
	public T call() throws Exception {
		InPacket packet = client.removeIncomingPacket();
        while(packet != null) {
            // 通知所有包事件监听器
            PacketEvent e = new PacketEvent(packet);
            client.firePacketArrivedEvent(e);
            // 得到下一个包
            packet = client.removeIncomingPacket();
        }
		return null;
	}
}
