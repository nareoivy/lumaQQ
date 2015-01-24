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

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.DatagramChannel;

/**
 * <pre>
 * 代理验证器，完成验证任务的功能包装
 * </pre>
 * 
 * @author luma
 */
public class ProxyVerifier implements IProxyHandler {
    /** 代理服务器 */
    private IProxy proxy;
    
    private IProxyHandler handler;
    
    private PortGate pool;

    /**
     * 构造函数
     * @param handler 代理事件处理器
     * @param proxyAddress 代理服务器地址
     * @param type 代理服务器类型，可以为Socks5或者Http
     * @param udp 协议，只对Socks5代理有效
     * @param u 用户名
     * @param p 密码
     * @throws IOException
     */
    public ProxyVerifier(IProxyHandler handler, InetSocketAddress proxyAddress, InetSocketAddress serverAddress, String type, boolean udp, String user, String pass) throws IOException {
        // 创建proxy，对于不支持的代理类型，直接抛出验证失败
    	this.handler = handler;
    	pool = new PortGate();
    	pool.start();
        if(type.equalsIgnoreCase("Socks5")) {
            // 创建代理类
            if(udp) {
                DatagramChannel dc = DatagramChannel.open();
                dc.configureBlocking(false);
                proxy = new Socks5Proxy(this, user, pass, dc);
            } else
                proxy = new Socks5Proxy(this, user, pass);
            proxy.setClientPort(5678);
            proxy.setProxyAddress(proxyAddress);
            proxy.setRemoteAddress(serverAddress);
            pool.getPorter().wakeup(proxy);
        } else if(type.equalsIgnoreCase("Http")) {
            // 得到一个随机服务器
            proxy = new HttpProxy(this, user, pass);
            proxy.setProxyAddress(proxyAddress);
            proxy.setRemoteAddress(serverAddress);
            pool.getPorter().wakeup(proxy);
        } else
            proxyAuthFail();
    }
    
    /**
     * 启动验证
     */
    public void start() {
        proxy.start();
    }
    
    /**
     * 释放资源
     * @throws IOException
     */
    public void dispose() {
    	pool.dispose();
    }

	public void proxyReady(InetSocketAddress bindAddress) throws IOException {
		dispose();
		handler.proxyReady(bindAddress);
	}

	public void proxyAuthFail() {
		dispose();
		handler.proxyAuthFail();
	}

	public void proxyError(String err) {
		dispose();
		handler.proxyError(err);
	}
}
