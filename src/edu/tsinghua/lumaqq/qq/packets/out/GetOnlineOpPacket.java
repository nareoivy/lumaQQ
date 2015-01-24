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
 * 获取在线好友列表的请求包，格式为
 * 1. 头部
 * 2. 1个字节，只有值为0x02或者0x03时服务器才有反应，不然都是返回0xFF
 *    经过初步的试验，发现3得到的好友都是一些系统服务，号码比如72000001到72000013，
 *    就是那些移动QQ，会员服务之类的；而2是用来得到好友的
 * 3. 起始位置，4字节。这个起始位置的含义与得到好友列表中的字段完全不同。估计是两拨人
 *    设计的，-_-!...
 *    这个起始位置需要有回复包得到，我们已经知道，在线好友的回复包一次最多返回30个好友，
 *    那么如果你的在线好友超过30个，就需要计算这个值。第一个请求包，这个字段肯定是0，后面
 *    的请求包和前一个回复包就是相关的了。具体的规则是这样的，在前一个回复包中的30个好友里面，
 *    找到QQ号最大的那个，然后把他的QQ号加1，就是下一个请求包的起始位置了！
 * 6. 尾部
 * </pre>
 *
 * @author luma
 */
public class GetOnlineOpPacket extends BasicOutPacket {
	// position，缺省设成0
	private int startPosition;
	private byte subCommand;
	
	/**
	 * 构造函数
	 */
	public GetOnlineOpPacket(QQUser user) {
	    super(QQ.QQ_CMD_GET_ONLINE_OP, true, user);
	    startPosition = QQ.QQ_POSITION_ONLINE_LIST_START;
	    subCommand = QQ.QQ_SUB_CMD_GET_ONLINE_FRIEND;
	}
	
    /**
     * @param buf
     * @param length
     * @throws PacketParseException
     */
    public GetOnlineOpPacket(ByteBuffer buf, int length, QQUser user)
            throws PacketParseException {
        super(buf, length, user);
    }
    
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.packets.OutPacket#getPacketName()
     */
	@Override
    public String getPacketName() {
        return "Get Friend Online Packet";
    }
    
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.packets.OutPacket#putBody(java.nio.ByteBuffer)
     */
	@Override
    protected void putBody(ByteBuffer buf) {
        buf.put((byte)0x2);
        buf.putInt(startPosition);      
    }
    
    /**
	 * @return Returns the position.
	 */
	public int getStartPosition() {
		return startPosition;
	}
	
	/**
	 * @param position The position to set.
	 */
	public void setStartPosition(int position) {
		this.startPosition = position;
	}

	/**
	 * @return Returns the subCommand.
	 */
	public byte getSubCommand() {
		return subCommand;
	}

	/**
	 * @param subCommand The subCommand to set.
	 */
	public void setSubCommand(byte subCommand) {
		this.subCommand = subCommand;
	}
}
