/*
* LumaQQ - Java QQ Client
*
* Copyright (C) 2004 notXX
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
package edu.tsinghua.lumaqq.qq.packets;

import java.nio.ByteBuffer;

import edu.tsinghua.lumaqq.qq.QQ;
import edu.tsinghua.lumaqq.qq.beans.QQUser;

/**
 * 这个包和协议无关，它用来通知上层，有些错误发生了，上层应该检查errorCode字段
 * 来获得更具体的信息
 * 
 * @author luma
 */
public class ErrorPacket extends BasicInPacket {
    // 错误代码
    /** 远端已经关闭连接 */
    public static final int ERROR_CONNECTION_BROKEN = 0;
    /** 操作超时 */
    public static final int ERROR_TIMEOUT = 1;
    /** 代理服务器错误 */
    public static final int ERROR_PROXY = 2;
    /** 网络错误 */
    public static final int ERROR_NETWORK = 3;
    /** 运行时错误，调试用 */
    public static final int RUNTIME_ERROR = 4;
    
    private int family;
    public int errorCode;
    public String connectionId;
    public String errorMessage;
    
    // 用在超时错误中
    public OutPacket timeoutPacket;
    
    public ErrorPacket(int errorCode, QQUser user) {
        super(QQ.QQ_CMD_UNKNOWN, user);
        this.errorCode = errorCode;
        family = QQ.QQ_PROTOCOL_ALL;
        errorMessage = "";
    }
    
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.packets.Packet#parseBody(java.nio.ByteBuffer)
     */
    @Override
	protected void parseBody(ByteBuffer buf) throws PacketParseException {
    }
    
    @Override
    public int getFamily() {
    	return family;
    }

	/**
	 * @param family the family to set
	 */
	public void setFamily(int family) {
		this.family = family;
	}
	
	@Override
	public String toString() {
		switch(errorCode) {
			case ERROR_TIMEOUT:
				return "Sending Timeout";
			case ERROR_CONNECTION_BROKEN:
				return "Connection Broken";
			case ERROR_NETWORK:
				return "Network Error";
			case ERROR_PROXY:
				return "Proxy Error";
			default:
				return "Unknown Error";
		}
	}
}
