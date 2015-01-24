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
package edu.tsinghua.lumaqq.qq.packets.in;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import edu.tsinghua.lumaqq.qq.QQ;
import edu.tsinghua.lumaqq.qq.beans.FriendRemark;
import edu.tsinghua.lumaqq.qq.beans.QQUser;
import edu.tsinghua.lumaqq.qq.packets.BasicInPacket;
import edu.tsinghua.lumaqq.qq.packets.PacketParseException;

/**
 * <pre>
 * 上传下载好友备注的回复包，格式为：
 * 1. 头部
 * 2. 子命令，1字节
 * 3. 如果是0x0，后面的部分为
 * 	  a. 是否还有更多备注，0x00表示有，0x01表示无
 *    b. QQ号，4字节
 *    c. 一个未知字节，0x00
 *    d. 名称长度，1字节
 *    e. 名称
 *    f. 手机号码长度, 1字节
 *    g. 手机号码
 *    h. 常用电话长度, 1字节
 *    i. 常用电话
 *    j. 联系地址长度, 1字节
 *    k. 联系地址
 *    l. 电子邮箱长度, 1字节
 *    m. 电子邮箱
 *    n. 邮编长度，1字节
 *    o. 邮编
 *    p. 备注长度，1字节
 *    q. 备注
 *    r. 如果还有更多，重复b - q部分
 *    如果是0x1或者0x02，后面的部分为
 *    i. 1字节应答码，0x0表示成功
 *    如果是0x3，后面的部分为(后面也可能什么都没有，说明这个好友没有备注)
 *    i.   操作对象的QQ号，4字节
 *    ii.  一个未知字节0
 *    iii. 分隔符0x1
 *    iv.  以下为备注信息，一共7个域，域的顺序依次次是
 *   	   姓名、手机、电话、地址、邮箱、邮编、备注
 *         每个域都有一个前导字节，这个字节表示了这个域的字节长度
 * 4. 尾部
 * </pre>
 * 
 * @author luma
 */
public class FriendDataOpReplyPacket extends BasicInPacket {
	// 应答码，仅用在上传回复
	public byte replyCode;
	// 操作对象的QQ号
	public int qqNum;
	// 备注信息，仅用在下载时
	public FriendRemark remark;
	// 操作字节
	public byte subCommand;
	// 是否有备注，如果子命令是下载备注，则这个字段表示这个好友是否有备注
	// 如果子命令是批量下载备注，则这个字段表示是否还有更多的备注可以下载
	public boolean hasRemark;
	// 仅用在批量下载备注时，key是qq号，value是FriendRemark对象
	public Map<Integer, FriendRemark> remarks;
	
    /**
     * 构造函数
     * @param buf 缓冲区
     * @param length 包长度
     * @throws PacketParseException 解析错误
     */
    public FriendDataOpReplyPacket(ByteBuffer buf, int length, QQUser user) throws PacketParseException {
        super(buf, length, user);
    } 
    
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.packets.OutPacket#getPacketName()
     */
	@Override
    public String getPacketName() {
        switch(subCommand) {
            case QQ.QQ_SUB_CMD_BATCH_DOWNLOAD_FRIEND_REMARK:
                return "Friend Data Reply Packet - Batch Download Remark";
            case QQ.QQ_SUB_CMD_UPLOAD_FRIEND_REMARK:
                return "Friend Data Reply Packet - Upload Remark";
            case QQ.QQ_SUB_CMD_DOWNLOAD_FRIEND_REMARK:
                return "Friend Data Reply Packet - Download Remark";
            case QQ.QQ_SUB_CMD_REMOVE_FRIEND_FROM_LIST:
                return "Friend Data Reply Packet - Remove Friend From List";
            default:
                return "Friend Data Reply Packeet - Unknown Sub Command";
        }
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.packets.InPacket#parseBody(java.nio.ByteBuffer)
     */
	@Override
    protected void parseBody(ByteBuffer buf) throws PacketParseException {
		// 操作字节
		subCommand = buf.get();
		switch(subCommand) {
		    case QQ.QQ_SUB_CMD_BATCH_DOWNLOAD_FRIEND_REMARK:
		        hasRemark = buf.get() == 0x0;
		    	remarks = new HashMap<Integer, FriendRemark>();
		    	while(buf.hasRemaining()) {
					int qq = buf.getInt();
					// 跳过一个未知字节0x0
					buf.get();
					// 创建备注对象 
					FriendRemark r = new FriendRemark();
					// 读入备注对象
					r.readBean(buf);
					// 加入到哈希表
					remarks.put(qq, r);
		    	}
		        break;
		    case QQ.QQ_SUB_CMD_UPLOAD_FRIEND_REMARK:
		    case QQ.QQ_SUB_CMD_REMOVE_FRIEND_FROM_LIST:
				replyCode = buf.get();
		    	break;
		    case QQ.QQ_SUB_CMD_DOWNLOAD_FRIEND_REMARK:
			    if(buf.hasRemaining()) {
					// 读取操作对象的QQ号
					qqNum = buf.getInt();
					// 创建备注对象 
					remark = new FriendRemark();
					// 跳过一个未知字节0x0
					buf.get();
					// 读入备注对象
					remark.readBean(buf);
					
					hasRemark = true;
			    } else
			        hasRemark = false;
		    	break;		        
		}
    }
}
