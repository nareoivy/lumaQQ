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
import edu.tsinghua.lumaqq.qq.Util;
import edu.tsinghua.lumaqq.qq.beans.QQUser;
import edu.tsinghua.lumaqq.qq.packets.BasicInPacket;
import edu.tsinghua.lumaqq.qq.packets.PacketParseException;


/**
 * <pre>
 * QQ登陆应答包
 * 1. 头部
 * 2. 回复码, 1字节
 * 2部分如果是0x00
 * 3. session key, 16字节
 * 4. 用户QQ号，4字节
 * 5. 我的外部IP，4字节
 * 6. 我的外部端口，2字节
 * 7. 服务器IP，4字节
 * 8. 服务器端口，2字节
 * 9. 本次登录时间，4字节，为从1970-1-1开始的毫秒数除1000
 * 10. 未知的2字节
 * 11. 用户认证令牌,24字节
 * 12. 一个未知服务器1的ip，4字节
 * 13. 一个未知服务器1的端口，2字节
 * 14. 一个未知服务器2的ip，4字节
 * 15. 一个未知服务器2的端口，2字节
 * 16. 8个未知字节
 * 17. client key，32字节，这个key用在比如登录QQ家园之类的地方
 * 18. 12个未知字节
 * 19. 上次登陆的ip，4字节
 * 20. 上次登陆的时间，4字节
 * 21. 49个未知字节
 * 2部分如果是0x0A，表示重定向
 * 3. 用户QQ号，4字节
 * 4. 未知10字节，0x0101 0x0000 0x0001 0x0000 0x0000
 * 5. 重定向到的服务器IP，4字节
 * 2部分如果是0x09，表示登录失败
 * 3. 一个错误消息
 * </pre>
 *
 * @author luma
 */
public class LoginReplyPacket extends BasicInPacket {
	public byte[] sessionKey;
	public byte[] ip;
	public byte[] serverIp;
	public byte[] lastLoginIp;
	public byte[] redirectIp;
	public int port;
	public int serverPort;
	public int redirectPort;
	public long loginTime;
	public long lastLoginTime;
	public byte replyCode;
	public String replyMessage;
	public byte[] clientKey;
	// 认证令牌，用在一些需要认证身份的地方，比如网络硬盘
	public byte[] authToken;
	
    /**
     * 构造函数
     * @param buf 缓冲区
     * @param length 包长度
     * @throws PacketParseException 解析错误
     */
    public LoginReplyPacket(ByteBuffer buf, int length, QQUser user) throws PacketParseException {
        super(buf, length, user);
    }   
    
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.packets.InPacket#getPacketName()
     */
	@Override
    public String getPacketName() {
        return "Login Reply Packet";
    }
	
	@Override
	public byte[] getDecryptKey(byte[] body) {
		return user.getPasswordKey();
	}
	
	@Override
	public byte[] getFallbackDecryptKey(byte[] body) {
		return user.getInitKey();
	}
        
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.packets.InPacket#parseBody(java.nio.ByteBuffer)
     */
	@Override
    protected void parseBody(ByteBuffer buf) throws PacketParseException {
        replyCode = buf.get();
        switch(replyCode) {
            case QQ.QQ_REPLY_OK:
                // session key
            	sessionKey = new byte[QQ.QQ_LENGTH_KEY];
                buf.get(sessionKey);
                // 用户QQ号
                buf.getInt();
                // 用户IP
                ip = new byte[4];
                buf.get(ip);
                // 用户端口
                port = buf.getChar();
                // 服务器自己的IP
                serverIp = new byte[4];                    
                buf.get(serverIp);
                // 服务器的端口
                serverPort = buf.getChar();
                // 本次登陆时间，为什么要乘1000？因为这个时间乘以1000才对，-_-!...
                loginTime = buf.getInt() * 1000L;
                // 未知的2字节
                buf.getChar();
                // 认证令牌
                authToken = new byte[24];
                buf.get(authToken);
                // 一个未知服务器1的ip
                // 一个未知服务器1的端口
                // 一个未知服务器2的ip
                // 一个未知服务器2的端口
                // 8个未知字节
                buf.position(buf.position() + 20);
                // client key，这个key用在比如登录QQ家园之类的地方
                clientKey = new byte[32];
                buf.get(clientKey);
                // 12个未知字节
                buf.position(buf.position() + 12);
                // 上次登陆的ip
                lastLoginIp = new byte[4];
                buf.get(lastLoginIp);
                // 上次登陆的时间
                lastLoginTime = buf.getInt() * 1000L;
                // 49个未知字节
                // do nothing
                break;
            case QQ.QQ_REPLY_LOGIN_FAIL:
				// 登录失败，我们得到服务器发回来的消息
                byte[] b = buf.array();
			    replyMessage = Util.getString(b, 1, b.length - 1, QQ.QQ_CHARSET_DEFAULT);
                break;
            case QQ.QQ_REPLY_LOGIN_REDIRECT:
				// 登陆重定向，可能是为了负载平衡
				// 用户QQ号
				buf.getInt();
				// 未知10字节，跳过
				buf.position(buf.position() + 10);
				//重定向到的服务器IP
				redirectIp = new byte[4];
				buf.get(redirectIp);
				// 使用缺省端口
				redirectPort = user.getPort();
                break;
        }
    }
}
