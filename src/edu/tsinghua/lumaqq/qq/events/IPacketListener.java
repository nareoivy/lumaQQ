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
package edu.tsinghua.lumaqq.qq.events;

import edu.tsinghua.lumaqq.qq.packets.InPacket;


/**
 * 定义了两个方法，一个在包到达时触发，一个在包发送完时触发
 *
 * @author luma
 */
public interface IPacketListener {
    /**
     * 包到达时调用此方法
     * 
     * @param e
     */
    public void packetArrived(PacketEvent e);
    
    /**
     * 是否接收某个输入包
     * 
     * @param in
     * 		InPacket子类
     * @return
     * 		true表示接收
     */
    public boolean accept(InPacket in);
}
