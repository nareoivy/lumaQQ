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
package edu.tsinghua.lumaqq.qq.packets.out._05;

import static org.apache.commons.codec.digest.DigestUtils.md5;
import java.nio.ByteBuffer;
import edu.tsinghua.lumaqq.qq.QQ;
import edu.tsinghua.lumaqq.qq.beans.QQUser;
import edu.tsinghua.lumaqq.qq.packets.PacketParseException;
import edu.tsinghua.lumaqq.qq.packets._05OutPacket;

/**
 * <pre>
 * 请求中转服务器
 * 1. 头部
 * 2. 未知的8字节，有点想随机生成的
 * 3. 未知的4字节，全0
 * 4. 文件中转令牌长度，2字节
 * 5. 文件中转令牌
 * ----- 加密部分开始 --------
 * 6. 未知字节，0x04
 * 7. 未知字节，0x4C
 * 8. 群内部ID，4字节
 * 9. 图片长度，4字节
 * 10. 图片的md5，16字节
 * 11. 图片文件名MD5，16字节
 * 12. 未知的2字节，全0
 * ------ 加密部分结束 --------
 * 13. 尾部 
 * </pre>
 * 
 * @author luma
 */
public class RequestAgentPacket extends _05OutPacket {
    private int clusterId;
    private int imageLength;
    private byte[] md5;
    private String fileName;
    
    /**
     * @param user
     * @throws PacketParseException
     */
    public RequestAgentPacket(QQUser user) {
        super(QQ.QQ_05_CMD_REQUEST_AGENT, true, user);
    }
    
    /**
     * @param buf
     * @param length
     * @param user
     * @throws PacketParseException
     */
    public RequestAgentPacket(ByteBuffer buf, int length, QQUser user) throws PacketParseException {
        super(buf, length, user);
    }
    
    /* (non-Javadoc)
	 * @see edu.tsinghua.lumaqq.qq.packets._05OutPacket#getPacketName()
	 */
	@Override
	public String getPacketName() {
		return "Request Agent Packet";
	}

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.packets.Packet#putBody(java.nio.ByteBuffer)
     */
	@Override
    protected void putBody(ByteBuffer buf) {
        buf.putLong(0x0100000000000000L);
        buf.putInt(0);
        buf.putChar((char)user.getFileAgentToken().length);
        buf.put(user.getFileAgentToken());
        
        buf.put((byte)0x04);
        buf.put((byte)0x4C);
        buf.putInt(clusterId);
        buf.putInt(imageLength);
        buf.put(md5);
        buf.put(md5(fileName.getBytes()));
        buf.putChar((char)0);
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
    /**
     * @return Returns the imageLength.
     */
    public int getImageLength() {
        return imageLength;
    }
    /**
     * @param imageLength The imageLength to set.
     */
    public void setImageLength(int imageLength) {
        this.imageLength = imageLength;
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
     * @return Returns the fileName.
     */
    public String getFileName() {
        return fileName;
    }
    /**
     * @param fileName The fileName to set.
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
