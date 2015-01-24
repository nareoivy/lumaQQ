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
package edu.tsinghua.lumaqq.qq.packets.in._05;

import java.nio.ByteBuffer;

import edu.tsinghua.lumaqq.qq.QQ;
import edu.tsinghua.lumaqq.qq.Util;
import edu.tsinghua.lumaqq.qq.beans.QQUser;
import edu.tsinghua.lumaqq.qq.packets.PacketParseException;
import edu.tsinghua.lumaqq.qq.packets._05InPacket;

/**
 * <pre>
 * 传送包的回复包，这个包不一定是回复包，也可能是服务器主动发出，需要判断source
 * 如果source是QQ_CLIENT，则这个是回复包
 * 1. 头部
 * 2. 未知的8字节，和请求包一致
 * 3. session id，但是这个值和本次会话的id已经不同，不知有何用处
 * 4. 未知的4字节，和请求包一致
 * 5. 后面内容的长度，不包括包尾，2字节
 * 6. 未知内容
 * 7. 尾部
 * 
 * 如果source不是QQ_CLIENT，则是服务器主动发出的包
 * 不同的序号，包的内容也不同，文件基本信息使用序号0x0000，文件数据使用序号0x0001
 * 当序号是0x0000时，格式如下
 * 1. 头部
 * 2. 未知的8字节
 * 3. session id, 4字节
 * 4. 未知的4字节
 * 5. 后面的内容长度，2字节
 * 6. 未知的2字节
 * 7. 图片的md5，16字节
 * 8. 图片文件名的md5，16字节
 * 9. 图片文件长度，4字节
 * 10. 文件名长度
 * 11. 文件名
 * 12. 未知的8字节
 * 13. 尾部
 * 
 * 当序号为0x0001时，格式如下
 * 1. 头部
 * 2. 未知的8字节
 * 3. session id, 4字节
 * 4. 未知的4字节
 * 5. 数据分片长度
 * 6. 数据内容
 * 7. 尾部
 * </pre>
 * 
 * @author luma
 */
public class TransferReplyPacket extends _05InPacket {
    public int sessionId;
    
    // 用于服务器主动发出的文件信息包中
    public byte[] md5;
    public byte[] fileNameMd5;
    public String fileName;
    public int imageLength;
    
    // 文件数据包
    public byte[] data;
    
    /**
     * @param buf
     * @param length
     * @param user
     * @throws PacketParseException
     */
    public TransferReplyPacket(ByteBuffer buf, int length, QQUser user) throws PacketParseException {
        super(buf, length, user);
    }

	@Override
	public String getPacketName() {
		return "Transfer Reply Packet";
	}

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.packets.Packet#parseBody(java.nio.ByteBuffer)
     */
	@Override
    protected void parseBody(ByteBuffer buf) throws PacketParseException {
        // 未知的8字节
        buf.getLong();
        // session id, 4字节
        sessionId = buf.getInt();     
        // 未知的4字节
        buf.getInt();
        // 后面的内容长度，2字节
        int len = buf.getChar();
        
        if(source != QQ.QQ_CLIENT_VERSION && sequence == 0) {
            buf.getChar();
            md5 = new byte[16];
            buf.get(md5);
            fileNameMd5 = new byte[16];
            buf.get(fileNameMd5);
            imageLength = buf.getInt();
            int fileNameLen = buf.getChar();
            fileName = Util.getString(buf, fileNameLen);
        } else {
            data = new byte[len];
            buf.get(data);
        }
    }
}
