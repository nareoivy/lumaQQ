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
 * 请求得到自定义表情
 * 1. 头部
 * 2. 未知的8字节
 * 3. session id，4字节
 * ------ 加密开始 --------
 * 4. 中转令牌长度，2字节
 * 5. 中转令牌
 * 6. 群外部ID
 * ------ 加密结束 -------
 * 7. 尾部
 * </pre>
 * 
 * @author luma
 */
public class RequestFacePacket extends _05OutPacket {
    private byte[] fileAgentToken;
    private int sessionId;
    private int clusterId;
    
    public RequestFacePacket(QQUser user) {
        super(QQ.QQ_05_CMD_REQUEST_FACE, true, user);
    }

    /**
     * @param buf
     * @param length
     * @param user
     * @throws PacketParseException
     */
    public RequestFacePacket(ByteBuffer buf, int length, QQUser user) throws PacketParseException {
        super(buf, length, user);
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.packets.Packet#putBody(java.nio.ByteBuffer)
     */
	@Override
    protected void putBody(ByteBuffer buf) {
        buf.putLong(0x0100000000000001L);
        buf.putInt(sessionId);
        buf.putChar((char)fileAgentToken.length);
        buf.put(fileAgentToken);
        buf.putInt(clusterId);
    }
	
	@Override
	public String getPacketName() {
		return "Request Face Packet";
	}
    
    /**
     * @return Returns the fileAgentToken.
     */
    public byte[] getFileAgentToken() {
        return fileAgentToken;
    }
    /**
     * @param fileAgentToken The fileAgentToken to set.
     */
    public void setFileAgentToken(byte[] fileAgentToken) {
        this.fileAgentToken = fileAgentToken;
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
     * @return Returns the clusterId.
     */
    public int getClusterId() {
        return clusterId;
    }
    /**
     * @param clusterId The clusterId to set.
     */
    public void setClusterId(int clusterId) {
        this.clusterId = clusterId;
    }
}
