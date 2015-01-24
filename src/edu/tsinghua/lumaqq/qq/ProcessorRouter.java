/*
* LumaQQ - Java QQ Client
*
* Copyright (C) 2004 luma <stubma@163.com>
*                    notXX
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

import edu.tsinghua.lumaqq.qq.events.IPacketListener;
import edu.tsinghua.lumaqq.qq.events.PacketEvent;
import edu.tsinghua.lumaqq.qq.packets.ErrorPacket;
import edu.tsinghua.lumaqq.qq.packets.InPacket;

/**
 * 包处理器路由器，一个处理器可以声明处理具有某种包头的包。
 * 一种类型的包可以有多个处理器
 * 
 * @author luma
 */
class ProcessorRouter {    
    private IPacketListener[] listeners;
    private int count;
    private QQClient client;
    
    public ProcessorRouter(QQClient client, int capacity) {
    	this.client = client;
        listeners = new IPacketListener[capacity];
        count = 0;
    }
    
    /**
     * 装载包处理器
     * 
     * @param listener
     */
    public void installProcessor(IPacketListener listener) {
        listeners[count++] = listener;
    }
    
    /**
     * 包到达时，QQClient调用此方法
     * 
     * @param event
     */
    public void packetArrived(PacketEvent event) {
    	InPacket packet = (InPacket)event.getSource();
        try {
			for(int i = 0; i < count; i++) {
			    if(listeners[i].accept(packet)) {
			    	listeners[i].packetArrived(event);
			    	return;
			    }
			}
		} catch(Throwable e) {
			ErrorPacket error = new ErrorPacket(ErrorPacket.RUNTIME_ERROR, client.getUser());
			error.errorMessage = client.generateCrashReport(e, packet);
			error.setFamily(QQ.QQ_PROTOCOL_FAMILY_BASIC);
			error.connectionId = QQPort.MAIN.name;
			client.addIncomingPacket(error, error.connectionId);
		}
    }
}
