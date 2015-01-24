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
import java.nio.channels.SelectionKey;

import edu.tsinghua.lumaqq.qq.packets.PacketParseException;

/**
 * <pre>
 * 网络事件处理器，当Selector可以进行操作时，调用这个接口中的方法。一个Port或者一个Proxy
 * 必须实现此接口
 * </pre>
 * 
 * @author luma
 */
public interface INIOHandler {
    /**
     * 当channel得到connect事件时调用这个方法
     * @param sk
     * 			SelectionKey
     * @throws IOException
     */
    public void processConnect(SelectionKey sk) throws IOException;
    
    /**
     * 当channel可读时调用这个方法
     * @param sk
     * 			SelectionKey
     * @throws PacketParseException
     * @throws IOException
     */
    public void processRead(SelectionKey sk) throws IOException, PacketParseException;
    
    /**
     * 当channel可写时调用这个方法
     * @throws IOException
     */
    public void processWrite() throws IOException;
    
    /**
     * 当channel发生错误时调用
     * @param e
     */
    public void processError(Exception e);
}
