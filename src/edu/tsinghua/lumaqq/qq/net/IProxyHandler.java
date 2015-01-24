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

/**
 * <pre>
 * 代理事件定义接口。如果一个Port是连接到一个代理服务器，则它应该实现此接口
 * </pre>
 * 
 * @author luma
 */
public interface IProxyHandler {
    /**
     * 代理在CONNECT或者UDP ASSOCIATE命令成功后调用这个方法，或者在代理验证成功时调用
     * @param bindAddress 返回的BND.ADDR和BND.PORT。这个参数只对UDP方式有用处
     * @throws IOException
     */
    public void proxyReady(InetSocketAddress bindAddress) throws IOException;
    
    /**
     * 代理验证失败时调用此方法
     */
    public void proxyAuthFail();
    
    /**
     * 如果代理服务器发生未知错误，调用这个方法
     * 
     * @param err
     * 		错误信息
     */
    public void proxyError(String err);
}
