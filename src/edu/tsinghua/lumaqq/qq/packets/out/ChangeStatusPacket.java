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
import edu.tsinghua.lumaqq.qq.beans.QQUser;
import edu.tsinghua.lumaqq.qq.packets.BasicOutPacket;
import edu.tsinghua.lumaqq.qq.packets.PacketParseException;

/**
 * <pre>
 * 改变状态的包，格式为
 * 1. 头部
 * 2. 想要切换到的状态，一个字节
 * 3. 是否显示虚拟摄像头，4字节
 * 4. 尾部
 * </pre>
 *
 * @author luma
 */
public class ChangeStatusPacket extends BasicOutPacket {
    // 是否显示虚拟摄像头
    private boolean showFakeCam;
    
    /**
     * 构造函数
     */
    public ChangeStatusPacket(QQUser user) {
        super(QQ.QQ_CMD_CHANGE_STATUS, true, user);
        showFakeCam = false;
    }
	
    /**
     * @param buf
     * @param length
     * @throws PacketParseException
     */
    public ChangeStatusPacket(ByteBuffer buf, int length, QQUser user)
            throws PacketParseException {
        super(buf, length, user);
    }
    
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.packets.OutPacket#getPacketName()
     */
	@Override
    public String getPacketName() {
        return "Change Status Packet";
    }
    
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.packets.OutPacket#putBody(java.nio.ByteBuffer)
     */
	@Override
    protected void putBody(ByteBuffer buf) {
		// 设置状态
		buf.put(user.getStatus());
		// 显示虚拟摄像头		
		buf.putInt(showFakeCam ? QQ.QQ_CAM_SHOW_FAKE : QQ.QQ_CAM_DONT_SHOW_FAKE);
    }
    
    /**
     * @return Returns the showFakeCam.
     */
    public synchronized boolean isShowFakeCam() {
        return showFakeCam;
    }
    
    /**
     * @param showFakeCam The showFakeCam to set.
     */
    public synchronized void setShowFakeCam(boolean showFakeCam) {
        this.showFakeCam = showFakeCam;
    }
}
