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
 * 请求密钥包，格式为：
 * 1. 头部
 * 2. 密钥类型，一个字节，0x3或者0x4
 * 3. 尾部
 * 
 * 这个包用来请求得到一些操作的密钥，比如文件中转，或者语音视频之类的都有可能
 * </pre>
 *
 * @author luma
 */
public class GetKeyPacket extends BasicOutPacket {    
    private byte request;
    
    /**
     * 构造函数
     */
    public GetKeyPacket(QQUser user) {
        super(QQ.QQ_CMD_REQUEST_KEY, true, user);
    }
        
    /**
     * @param buf
     * @param length
     * @throws PacketParseException
     */
    public GetKeyPacket(ByteBuffer buf, int length, QQUser user)
            throws PacketParseException {
        super(buf, length, user);
    }
    
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.packets.OutPacket#getPacketName()
     */
	@Override
    public String getPacketName() {
        return "Request Key Packet";
    }
    
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.packets.OutPacket#putBody(java.nio.ByteBuffer)
     */
	@Override
    protected void putBody(ByteBuffer buf) {
        buf.put(request);
    }
    
    /**
     * @param content The content to set.
     */
    public void setRequest(byte request) {
        this.request = request;
    }
    
    /**
     * @return Returns the request.
     */
    public byte getRequest() {
        return request;
    }
}
