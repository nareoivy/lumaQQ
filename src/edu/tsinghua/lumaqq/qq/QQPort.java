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
package edu.tsinghua.lumaqq.qq;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.tsinghua.lumaqq.qq.net.IConnection;
import edu.tsinghua.lumaqq.qq.net.IConnectionPolicy;

/**
 * 一些缺省的QQ端口定义
 *
 * @author luma
 */
public enum QQPort {
	// 主端口
	MAIN("Main"),
	// 用于接收群内自定义表情
	CLUSTER_CUSTOM_FACE("Cluster Custom Face"),
	// 接收自定义头像信息
	CUSTOM_HEAD_INFO("Custom Head Info"),
	// 接收自定义头像数据
	CUSTOM_HEAD_DATA("Custom Head Data");
	
	public String name;
	static final Log log = LogFactory.getLog(QQPort.class);
	
	QQPort(String name) {
		this.name = name;
	}
	
	/**
	 * 创建Port
	 * 
	 * @param client
	 * 		QQClient对象
	 * @param server
	 * 		服务器地址
	 * @param proxy
	 * 		代理地址，如果不用代理，为null
	 * @param start
	 * 		true表示立刻启动
	 * @return
	 * 		IPort对象，如果创建失败，返回null
	 * @throws IOException 
	 * @throws UnknownHostException 
	 */
	public IConnection create(QQClient client, InetSocketAddress server, InetSocketAddress proxy, boolean start) throws IOException {
		return create(client, server, proxy, null, null, start);
	}
	
	/**
	 * 创建Port
	 * 
	 * @param client
	 * 		QQClient对象
	 * @param server
	 * 		服务器地址
	 * @param proxy
	 * 		代理地址，如果不用代理，为null
	 * @param username
	 * 		代理用户名
	 * @param password
	 * 		代理密码
	 * @param start
	 * 		true表示立刻启动
	 * @return
	 * 		IPort对象，如果创建失败，返回null
	 * @throws IOException 
	 */
	public IConnection create(QQClient client, InetSocketAddress server, InetSocketAddress proxy, String username, String password, boolean start) throws IOException, UnknownHostException {
		IConnection conn = null;
		IConnectionPolicy policy = null;
		switch(this) {
			case MAIN:
				policy = client.getConnectionPolicyFactory().createPolicy(client,
						name, 
						QQ.QQ_PROTOCOL_FAMILY_BASIC,
						QQ.QQ_PROTOCOL_FAMILY_BASIC, 
						proxy, username, 
						password);
				int proxyType = client.getProxyType();
		        if (client.getUser().isUdp()) {
		            log.debug("连接UDP服务器: " + server);          
		            if(proxyType == QQ.QQ_PROXY_SOCKS5) {
		                log.debug("使用Socks 5代理 " + proxy);
		                conn = client.getConnectionPool().newUDPSocks5Connection(policy, server, start);
		            } else
		                conn = client.getConnectionPool().newUDPConnection(policy, server, start);
		        } else {
		            log.debug("连接TCP服务器: " + server);             
		            if(proxyType == QQ.QQ_PROXY_SOCKS5) {
		                log.debug("使用Socks 5代理 " + proxy);
		                conn = client.getConnectionPool().newTCPSocks5Connection(policy, server, start);
		            } else if(proxyType == QQ.QQ_PROXY_HTTP) {
		                log.debug("使用HTTP代理 " + proxy);
		                conn = client.getConnectionPool().newTCPHttpConnection(policy, server, start);
		            } else
		                conn = client.getConnectionPool().newTCPConnection(policy, server, start);
		        } 
				break;
			case CLUSTER_CUSTOM_FACE:
				policy = client.getConnectionPolicyFactory().createPolicy(client,
						name, 
						QQ.QQ_PROTOCOL_FAMILY_05,
						QQ.QQ_PROTOCOL_FAMILY_05, 
						proxy, username, 
						password);
				conn = client.getConnectionPool().newTCPConnection(policy, server, start);
				break;
			case CUSTOM_HEAD_INFO:
			case CUSTOM_HEAD_DATA:
				policy = client.getConnectionPolicyFactory().createPolicy(client,
						name, 
						QQ.QQ_PROTOCOL_FAMILY_03,
						QQ.QQ_PROTOCOL_FAMILY_03, 
						proxy, username, 
						password);
				conn = client.getConnectionPool().newUDPConnection(policy, server, start);
				break;
		}
		return conn;
	}
}
