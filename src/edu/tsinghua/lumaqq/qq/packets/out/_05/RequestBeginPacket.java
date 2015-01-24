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
package edu.tsinghua.lumaqq.qq.packets.out._05;

import java.nio.ByteBuffer;
import edu.tsinghua.lumaqq.qq.QQ;
import edu.tsinghua.lumaqq.qq.beans.QQUser;
import edu.tsinghua.lumaqq.qq.packets.PacketParseException;
import edu.tsinghua.lumaqq.qq.packets._05OutPacket;

/**
 * <pre>
 * 请求开始中转
 * 1. 头部
 * 2. 未知8字节，发送图片时后面的7字节是md5的第4字节开始的7字节，其他情况下未知
 * 3. session id, 4字节
 * ------ 加密开始 -------
 * 4. 未知1字节，0x04，如果是请求接收，0x00
 * 5. 未知1字节，0x4C，如果是请求接收，0x00
 * ------ 加密结束 --------
 * 6. 尾部
 * </pre>
 * 
 * @author luma
 */
public class RequestBeginPacket extends _05OutPacket {
    private int sessionId;
    private byte[] md5;
    private boolean requestSend;
    
    /**
     * @param buf
     * @param length
     * @param user
     * @throws PacketParseException
     */
    public RequestBeginPacket(ByteBuffer buf, int length, QQUser user)
            throws PacketParseException {
        super(buf, length, user);
    }
    
    /**
     * @param user
     */
    public RequestBeginPacket(QQUser user) {
        super(QQ.QQ_05_CMD_REQUEST_BEGIN, true, user);
        requestSend = true;
    }
    
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.packets._05OutPacket#getPacketName()
     */
	@Override
    public String getPacketName() {
        return "Request Begin Packet";
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.packets.Packet#putBody(java.nio.ByteBuffer)
     */
	@Override
    protected void putBody(ByteBuffer buf) {
        if(md5 == null)
            buf.putLong(0x0100000000000000L);
        else {
            buf.put((byte)0x01);
            buf.put(md5, 4, 7);
        }
        buf.putInt(sessionId);
        if(requestSend) {
	        buf.put((byte)0x04);
	        buf.put((byte)0x4C);            
        } else {
	        buf.put((byte)0);
	        buf.put((byte)0);       
        }
    }
    
    /**
     * @return Returns the sessionId.
     */
    public int getSessionId() {
        return sessionId;
    }
    /**
     * @param sessionId The sessionId to set.
     */
    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }
    /**
     * @return Returns the md5.
     */
    public byte[] getMd5() {
        return md5;
    }
    /**
     * @param md5 The md5 to set.
     */
    public void setMd5(byte[] md5) {
        this.md5 = md5;
    }
    /**
     * @return Returns the requestSend.
     */
    public boolean isRequestSend() {
        return requestSend;
    }
    /**
     * @param requestSend The requestSend to set.
     */
    public void setRequestSend(boolean requestSend) {
        this.requestSend = requestSend;
    }
}
