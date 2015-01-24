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
package edu.tsinghua.lumaqq.qq.net;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;

import edu.tsinghua.lumaqq.qq.packets.ErrorPacket;
import edu.tsinghua.lumaqq.qq.packets.InPacket;
import edu.tsinghua.lumaqq.qq.packets.OutPacket;
import edu.tsinghua.lumaqq.qq.packets.PacketParseException;

/**
 * 连接策略
 *
 * @author luma
 */
public interface IConnectionPolicy {
	/**
	 * 根据回复包获得请求包
	 * 
	 * @param in
	 * 		回复包
	 * @return
	 * 		请求包
	 */
	public OutPacket retrieveSent(InPacket in);
	
	/**
	 * @return
	 * 		支持的协议族
	 */
	public int getSupportedFamily();
	
	/**
	 * @return
	 * 		负责缓冲区重定位的协议族
	 */
	public int getRelocateFamily();
	
	/**
	 * @return
	 * 		连接id
	 */
	public String getConnectionId();
	
	/**
	 * @return
	 * 		代理服务器地址，如果为null，表示无代理
	 */
	public InetSocketAddress getProxy();
	
	/**
	 * @return
	 * 		代理服务器用户名，如果为null或者空，表示无需用户名
	 */
	public String getProxyUsername();
	
	/**
	 * @return
	 * 		代理服务器密码，如果为null或者空，表示无需密码
	 */
	public String getProxyPassword();

	/**
	 * @param proxy the proxy to set
	 */
	public void setProxy(InetSocketAddress proxy);

	/**
	 * @param proxyPassword the proxyPassword to set
	 */
	public void setProxyPassword(String proxyPassword);

	/**
	 * @param proxyUsername the proxyUsername to set
	 */
	public void setProxyUsername(String proxyUsername);

	/**
	 * 检测是否某个包之前已经得到了确认
	 * 
	 * @param packet
	 * @param add
	 * @return
	 */
	public boolean isReplied(OutPacket packet, boolean add);

	/**
	 * 保存输出包到缓冲中
	 * 
	 * @param packet
	 */
	public void putSent(OutPacket packet);

	/**
	 * 解析缓冲区数据，返回输入包对象
	 * 
	 * @param buf
	 * 		字节缓冲区
	 * @param debug
	 * 		true表示debug模式，在debug模式下，容忍重复包
	 * @return
	 * 		包对象，如果无法找到一个合适的解析器来生成包，则返回null
	 * @throws PacketParseException
	 * 		如果找到了解析器，但是解析失败
	 */
	public InPacket parseIn(ByteBuffer buf, boolean debug) throws PacketParseException;

	/**
	 * 推入一个输入包到接收队列
	 * 
	 * @param in
	 * 		InPacket子类
	 */
	public void pushIn(InPacket in);

	/**
	 * 推入一个包到重发队列
	 * 
	 * @param out
	 * 		可能需要重发的包
	 * @param portName
	 * 		端口名
	 */
	public void pushResend(OutPacket out, String portName);
	
	/**
	 * 使发送队列中的包全部立刻发送出去
	 */
	public void flush();

	/**
	 * 创建ErrorPacket对象
	 * 
	 * @param errorCode
	 * 		错误码
	 * @param portName
	 * 		端口名
	 * @return
	 * 		ErrorPacket对象
	 */
	public ErrorPacket createErrorPacket(int errorCode, String portName);

	/**
	 * 把position设置到下一个包的起始位置处。一般当某段数据没有parser
	 * 可以时，调用此方法跳过这段数据
	 * 
	 * @param buf
	 * 		缓冲区 
	 * @return
	 * 		true表示重定位成功，false表示失败或者推迟重定位
	 */
	public boolean relocate(ByteBuffer buf);
}
