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
import edu.tsinghua.lumaqq.qq.Util;
import edu.tsinghua.lumaqq.qq.beans.QQUser;
import edu.tsinghua.lumaqq.qq.packets.BasicOutPacket;
import edu.tsinghua.lumaqq.qq.packets.PacketParseException;


/**
 * <pre>
 * 这个用来得到某个用户的验证信息
 * 
 * ********** 格式1，请求验证信息 *********
 * 头部
 * --------- 加密开始（会话密钥）--------
 * 子命令，1字节, 0x01
 * 未知2字节, 0x0001
 * 要得到验证信息的QQ号，4字节
 * --------- 加密结束 --------
 * 尾部
 * 
 * ********** 格式2，提交验证信息 *********
 * 头部
 * --------- 加密开始（会话密钥）--------
 * 子命令，1字节, 0x02
 * 未知2字节, 0x0001
 * 要添加为好友的QQ号，4字节
 * 验证信息长度，2字节
 * 验证信息
 * Cookie长度，2字节，由于验证码图片是一个url里面取出来的，所以需要一个Cookie标识
 * Cookie
 * --------- 加密结束 --------
 * 尾部
 * </pre>
 *
 * @author luma
 */
public class AuthInfoOpPacket extends BasicOutPacket {
	private byte subCommand;
    private int to;
    private String authString;
    private String cookie;
    
    /**
     * 构造函数
     */
    public AuthInfoOpPacket(QQUser user) {
        super(QQ.QQ_CMD_AUTH_INFO_OP, true, user);
    }

    /**
     * @param buf
     * @param length
     * @throws PacketParseException
     */
    public AuthInfoOpPacket(ByteBuffer buf, int length, QQUser user)
            throws PacketParseException {
        super(buf, length, user);
    }
    
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.packets.OutPacket#putBody(java.nio.ByteBuffer)
     */
	@Override
    protected void putBody(ByteBuffer buf) {
		buf.put(subCommand);
		buf.putChar((char)1);
	    buf.putInt(to);
	    switch(subCommand) {
	    	case QQ.QQ_SUB_CMD_SUBMIT_AUTH_INFO:
	    		byte[] b = Util.getBytes(authString);
	    		buf.putChar((char)b.length);
	    		buf.put(b);
	    		b = Util.getBytes(cookie);
	    		buf.putChar((char)b.length);
	    		buf.put(b);
	    		break;
	    }
    }
    
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.packets.OutPacket#getPacketName()
     */
	@Override
    public String getPacketName() {
        return "Get Auth Info Packet";
    }
    
    /**
     * @return Returns the to.
     */
    public int getTo() {
        return to;
    }
    
    /**
     * @param to The to to set.
     */
    public void setTo(int to) {
        this.to = to;
    }

	public byte getSubCommand() {
		return subCommand;
	}

	public void setSubCommand(byte subCommand) {
		this.subCommand = subCommand;
	}

	public String getAuthString() {
		return authString;
	}

	public void setAuthString(String authString) {
		this.authString = authString;
	}

	public String getCookie() {
		return cookie;
	}

	public void setCookie(String cookie) {
		this.cookie = cookie;
	}
}
