/*
* LumaQQ - Java QQ Client
*
* Copyright (C) 2004 notXX
*                    luma <stubma@163.com>
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
 * 传送数据包
 * 如果传送基本信息
 * 1. 头部
 * 2. 未知的8字节
 * 3. session id, 4字节
 * 4. 未知的4字节
 * 5. 后面的数据长度，2字节，不包括包尾，包括本身
 * 6. 未知2字节，和5相同？
 * 7. 图片的md5
 * 8. 文件名的md5
 * 9. 文件长度，4字节
 * 10. 文件名长度，2字节
 * 11. 文件名
 * 12. 未知的8字节
 * 13. 尾部
 * 
 * 如果传送数据信息
 * 1. 头部
 * 2. 未知的8字节，如果不是最后一个分片，则为，0x1000000000000001，如果是，则为随机字节
 * 3. session id, 4字节
 * 4. 未知的4字节
 * 5. 数据分片长度，2字节
 * 6. 数据分片
 * 7. 包尾
 * 
 * 如果是接收方，则这个包用来通知服务器开始发送表情
 * 1. 头部
 * 2. 未知的8字节
 * 3. session id, 4字节
 * 4. 未知的4字节
 * 5. 后面的数据长度，2字节
 * 6. 4个字节，全0
 * 7. 尾部
 * 
 * 如果是接收方对文件数据的回复，则为
 * 1. 头部
 * 2. 未知的8字节
 * 3. session id, 4字节
 * 4. 未知的4字节
 * 5. 后面的数据长度，2字节
 * 6. 一个字节，0x02，所以5一般是0x0001
 * 7. 尾部
 * </pre>
 *  
 * @author luma
 */
public class TransferPacket extends _05OutPacket {
    // 公用字段
    private int sessionId;
    
    // 用于发送基本信息时
    private byte[] md5;
    private int imageLength;
    private String fileName;
    
    // 用户发送数据时
    private byte[] fragment;
    
    private boolean data;
    private boolean last;
    
    private boolean requestSend;
    private boolean dataReply;
   
    /**
     * @param user
     * @param data
     * 		true表示这是数据分片
     * @param last
     * 		true表示这是最后一个数据分片
     */
    public TransferPacket(QQUser user, boolean data, boolean last) {
        super(QQ.QQ_05_CMD_TRANSFER, data ? (last ? true : false) : true, user);
        this.data = data;
        this.last = last;
        requestSend = true;
    }
    
    /**
     * 构造一个请求发送数据包
     * 
     * @param user
     */
    public TransferPacket(QQUser user) {
        super(QQ.QQ_05_CMD_TRANSFER, false, user);
        requestSend = false;
        dataReply = false;
    }
    
    /**
     * @param buf
     * @param length
     * @param user
     * @throws PacketParseException
     */
    public TransferPacket(ByteBuffer buf, int length, QQUser user) throws PacketParseException {
        super(buf, length, user);
    }
    
	@Override
	public String getPacketName() {
		return "Transfer Packet";
	}

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.packets.Packet#putBody(java.nio.ByteBuffer)
     */
	@Override
    protected void putBody(ByteBuffer buf) {
        if(!requestSend) {
	        // 2. 未知的8字节
	        buf.putLong(0x0100000000000000L);
	        // 3. session id, 4字节	        
	        buf.putInt(sessionId);
	        // 未知4字节
	        buf.putInt(0);
	        // 后面的内容长度和内容
	        if(dataReply) {
	            buf.putChar((char)0x0001);
	            buf.put((byte)0x02);
	        } else {
		        buf.putChar((char)0x04);
		        buf.putInt(0);	        	            
	        }
        } else if(data) {
            // 2. 未知的8字节，如果不是最后一个分片，则为，0x1000000000000001，如果是，则为随机字节
            if(last)
                buf.putLong(0x0100000000000000L);
            else
                buf.putLong(0x0100000000000001L);            
            // 3. session id, 4字节
            buf.putInt(sessionId);
            // 4. 未知的4字节
            buf.putInt(0);
            // 5. 数据分片长度，2字节
            buf.putChar((char)fragment.length);
            // 6. 数据分片
            buf.put(fragment);
        } else {
	        // 2. 未知的8字节
	        buf.putLong(0x0100000000000000L);
	        // 3. session id, 4字节
	        buf.putInt(sessionId);
	        // 4. 未知的4字节
	        buf.putInt(0);
	        // 5. 后面的数据长度，2字节
	        buf.putChar((char)0);        
	        // 6. 未知2字节，和5相同？
	        int pos = buf.position();
	        buf.putChar((char)0);
	        // 7. 图片的md5
	        buf.put(md5);
	        // 8. 文件名md5
	        byte[] fileNameBytes = fileName.getBytes();
	        buf.put(md5(fileNameBytes));
	        // 9. 文件长度，4字节
	        buf.putInt(imageLength);
	        // 10. 文件名长度，2字节
	        buf.putChar((char)fileName.length());
	        // 11. 文件名
	        buf.put(fileNameBytes);
	        // 12. 未知的8字节
	        buf.putLong(0);
	        
	        char len = (char)(buf.position() - pos);
	        buf.putChar(pos - 2, len);
	        buf.putChar(pos, len);            
        }
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
    /**
     * @return Returns the fragment.
     */
    public byte[] getFragment() {
        return fragment;
    }
    /**
     * @param fragment The fragment to set.
     */
    public void setFragment(byte[] fragment) {
        this.fragment = fragment;
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
    public boolean isDataReply() {
        return dataReply;
    }
    public void setDataReply(boolean dataReply) {
        this.dataReply = dataReply;
    }
}
