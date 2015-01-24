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

import edu.tsinghua.lumaqq.qq.QQ;
import edu.tsinghua.lumaqq.qq.beans.QQUser;
import edu.tsinghua.lumaqq.qq.packets.BasicInPacket;
import edu.tsinghua.lumaqq.qq.packets.PacketParseException;

/**
 * <pre>
 * 请求登录令牌的回复包
 * 
 * ******** 格式1 ***********
 * 头部
 * ---------- 加密开始（使用请求包中的随机密钥加密）-----------------
 * 子命令，1字节
 * 未知2字节，0x0005
 * 回复码，1字节，为0x00时，表示ok
 * 登录令牌长度，2字节
 * 登录令牌，长度由上面一部分指定
 * ---------- 加密结束 -------------
 * 尾部
 * 
 * ******** 格式2 ***********
 * 头部
 * ---------- 加密开始（使用请求包中的随机密钥加密）-----------------
 * 子命令，1字节
 * 未知2字节，0x0005
 * 回复码，1字节，为0x01时，表示需要输入验证码
 * 验证码图片令牌长度，2字节
 * 验证码图片令牌，长度由上面一部分指定
 * 验证码图片数据长度，不包含这个部分，图片是png格式
 * 验证码图片数据
 * ---------- 加密结束 -------------
 * 尾部
 * </pre>
 * 
 * @author luma
 */
public class GetLoginTokenReplyPacket extends BasicInPacket {
	public byte subCommand;
	public byte replyCode;
    public byte[] token; // 登录令牌，或者验证码令牌
    public byte[] puzzleData;
    
    /**
     * @param buf
     * @throws PacketParseException
     */
    public GetLoginTokenReplyPacket(ByteBuffer buf, QQUser user)
            throws PacketParseException {
        super(buf, user);
    }
    
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.packets.InPacket#getPacketName()
     */
	@Override
    public String getPacketName() {
        return "Request Login Token Reply Packet";
    }
    
    /**
     * @param buf
     * @param length
     * @throws PacketParseException
     */
    public GetLoginTokenReplyPacket(ByteBuffer buf, int length, QQUser user) throws PacketParseException {
        super(buf, length, user);
    }
    
    @Override
    public byte[] getDecryptKey(byte[] body) {
    	return user.getLoginTokenRandomKey();
    }
    
    @Override
    public byte[] getFallbackDecryptKey(byte[] body) {
    	return null;
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.packets.InPacket#parseBody(java.nio.ByteBuffer)
     */
	@Override
    protected void parseBody(ByteBuffer buf) throws PacketParseException {
		// 子命令
		subCommand = buf.get();
		// 未知2字节
		buf.getChar();
		// 回复码
		replyCode = buf.get();
		
		switch(replyCode) {
			case QQ.QQ_REPLY_OK:
		        // 登录令牌长度
		        int len = buf.getChar();
		        // 登录令牌
		        token = new byte[len];
		        buf.get(token); 
				break;
			case QQ.QQ_REPLY_LOGIN_NEED_VERIFY:
		        // 验证码令牌长度
		        len = buf.getChar();
		        // 验证码令牌
		        token = new byte[len];
		        buf.get(token); 
		        // 验证码图片数据长度，不包含这个部分
		        len = buf.getChar();
		        // 验证码图片，png格式
		        puzzleData = new byte[len];
		        buf.get(puzzleData);
				break;
		}           
    }
}
