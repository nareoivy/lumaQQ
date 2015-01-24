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

import edu.tsinghua.lumaqq.qq.QQClient;

/**
 * 缺省的连接策略工厂实现
 *
 * @author luma
 */
public class ConnectionPolicyFactory implements IConnectionPolicyFactory {
	public IConnectionPolicy createPolicy(QQClient client, String id, int supportedFamily, int relocatedFamily, InetSocketAddress proxy, String proxyUsername, String proxyPassword) {
		IConnectionPolicy policy = new ConnectionPolicy(client, id, supportedFamily, relocatedFamily);
		policy.setProxy(proxy);
		policy.setProxyUsername(proxyUsername);
		policy.setProxyPassword(proxyPassword);
		return policy;
	}
}
