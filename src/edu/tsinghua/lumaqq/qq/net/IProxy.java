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
import java.nio.channels.SelectableChannel;

/**
 * <pre>
 * 代理的简单操作接口。一个Proxy必须实现此接口
 * </pre>
 * 
 * @author luma
 */
public interface IProxy {    
    /**
     * 开始初始化和代理服务器之间的连接，准备使用它。
     */
    public void start();
    
    /**
     * 释放proxy所占的资源
     * @throws IOException
     */
    public void dispose();
    
    /**
     * @return 和Proxy连接的channel
     */
    public SelectableChannel channel();
    
    /**
     * @return
     * 		NIO事件处理器
     */
    public INIOHandler getNIOHandler();
    
    /**
     * 设置代理服务器地址
     * @param proxyAddress
     */
    public void setProxyAddress(InetSocketAddress proxyAddress);
    
    /**
     * 设置要连接的服务器地址
     * @param serverAddress
     */
    public void setRemoteAddress(InetSocketAddress serverAddress);
    
    /**
     * 设置UDP端口，只在UDP Socks5时有效
     * @param p
     */
    public void setClientPort(int p);
}
