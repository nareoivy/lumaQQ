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

import edu.tsinghua.lumaqq.qq.packets.OutPacket;

/**
 * 连接接口，用于隔离具体的网络实现
 *
 * @author luma
 */
public interface IConnection {
	/**
	 * 添加一个包到发送队列
	 * 
	 * @param out
	 * 		OutPacket子类
	 */
	public void add(OutPacket out);
	
	/**
	 * @return
	 * 		连接策略接口
	 */
	public IConnectionPolicy getPolicy();
	
	/**
	 * @return
	 * 		连接池
	 */
	public IConnectionPool getPool();
	
	/**
	 * 情况发送队列
	 */
	public void clearSendQueue();
	
	/**
	 * 启动这个端口
	 */
	public void start();
	
	/**
	 * @return
	 * 		连接id
	 */
	public String getId();
	
	/**
	 * 释放连接资源，用户不应该调用这个方法，为了释放一个连接，用户应该始终使用
	 * IConnectionPool的release方法
	 */
	public void dispose();
	
	/**
	 * @return
	 * 		服务器地址
	 */
	public InetSocketAddress getRemoteAddress();
}
