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
import java.util.List;

import edu.tsinghua.lumaqq.qq.packets.OutPacket;

/**
 * 连接池接口，用于管理所有连接
 *
 * @author luma
 */
public interface IConnectionPool {	
	/**
	 * 立刻发送所有包
	 */
	public void flush();
	
	/**
	 * 启动连接池
	 */
	public void start();
	
	/**
	 * 校验代理
	 * 
	 * @param handler
	 * @param proxyAddress
	 * @param serverAddress
	 * @param type
	 * @param udp
	 * @param user
	 * @param pass
	 * @return
	 * 		true表示代理校验线程启动成功，false表示失败
	 */
	public boolean verifyProxy(IProxyHandler handler, InetSocketAddress proxyAddress, InetSocketAddress serverAddress, String type, boolean udp, String user, String pass);
	
	/**
	 * 释放连接
	 */
	public void release(IConnection con);
	
	/**
	 * 释放指定id的连接
	 * 
	 * @param id
	 * 		连接id
	 */
	public void release(String id);
	
	/**
	 * 发送一个包
	 * 
	 * @param id
	 * 		连接id
	 * @param packet
	 * 		要发送的包
	 * @param keepSent
	 * 		true表示保存发送包到缓冲中以便能够根据回复包得到请求包
	 */
	public void send(String id, OutPacket packet, boolean keepSent);
	
	/**
	 * 新建一个UDP连接
	 * 
	 * @param policy
	 * 		连接策略
	 * @param server
	 * 		服务器地址
	 * @param start
	 * 		是否立刻启动这个连接
	 * @return
	 * 		IConnection对象, 如果为null，表示创建失败
	 */
	public IConnection newUDPConnection(IConnectionPolicy policy, InetSocketAddress server, boolean start);
	
	/**
	 * 新建一个UDP Socks5连接
	 * 
	 * @param policy
	 * 		连接策略
	 * @param server
	 * 		服务器地址
	 * @param start
	 * 		是否立刻启动这个连接
	 * @return
	 * 		IConnection对象, 如果为null，表示创建失败
	 */
	public IConnection newUDPSocks5Connection(IConnectionPolicy policy, InetSocketAddress server, boolean start);
	
	/**
	 * 新建一个TCP连接
	 * 
	 * @param policy
	 * 		连接策略
	 * @param server
	 * 		服务器地址
	 * @param start
	 * 		是否立刻启动这个连接
	 * @return
	 * 		IConnection对象, 如果为null，表示创建失败
	 */
	public IConnection newTCPConnection(IConnectionPolicy policy, InetSocketAddress server, boolean start);
	
	/**
	 * 新建一个TCP Socks5连接
	 * 
	 * @param policy
	 * 		连接策略
	 * @param server
	 * 		服务器地址
	 * @param start
	 * 		是否立刻启动这个连接
	 * @return
	 * 		IConnection对象, 如果为null，表示创建失败
	 */
	public IConnection newTCPSocks5Connection(IConnectionPolicy policy, InetSocketAddress server, boolean start);
	
	/**
	 * 新建一个TCP Http连接
	 * 
	 * @param policy
	 * 		连接策略
	 * @param server
	 * 		服务器地址
	 * @param start
	 * 		是否立刻启动这个连接
	 * @return
	 * 		IConnection对象, 如果为null，表示创建失败
	 */
	public IConnection newTCPHttpConnection(IConnectionPolicy policy, InetSocketAddress server, boolean start);
	
	/**
	 * 根据id得到连接
	 * 
	 * @param id
	 * 		连接id
	 * @return
	 * 		IConnection对象，如果null，表示失败
	 */
	public IConnection getConnection(String id);
	
	/**
	 * 根据地址得到连接
	 * 
	 * @param address
	 * 		服务器地址
	 * @return
	 * 		连接到这个服务器地址的连接对象，null表示没有
	 */
	public IConnection getConnection(InetSocketAddress address);
	
	/**
	 * 关闭这个连接池，释放所有资源。一个释放掉的连接池不可继续使用，必须新建一个新的连接池对象
	 */
	public void dispose();
	
	/**
	 * 检测是否存在某个id的连接
	 * 
	 * @param id
	 * 		连接id
	 * @return
	 * 		true表示存在这个连接
	 */
	public boolean hasConnection(String id);
	
	/**
	 * @return
	 * 		连接对象列表
	 */
	public List<IConnection> getConnections();
}
