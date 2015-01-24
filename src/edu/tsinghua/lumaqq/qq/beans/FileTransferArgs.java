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
package edu.tsinghua.lumaqq.qq.beans;

import java.nio.ByteBuffer;

import edu.tsinghua.lumaqq.qq.QQ;

/**
 * 传送文件的ip，端口信息封装类
 * 
 * @author luma
 */
public class FileTransferArgs {
    // 传输类型
    public byte transferType;
    // 连接方式
    public byte connectMode;
    // 发送者外部ip
    public byte[] internetIp;
    // 发送者外部端口
    public int internetPort;
    // 第一个监听端口
    public int majorPort;
    // 发送者真实ip
    public byte[] localIp;
    // 第二个监听端口
    public int minorPort;
    
    /**
     * 给定一个输入流，解析FileTransferArgs结构
     * @param buf
     */
    public void readBean(ByteBuffer buf) throws Exception {
    	// 跳过19个无用字节
        buf.position(buf.position() + 19);
        // 读取传输类型
        transferType = buf.get();
        // 读取连接方式
    	connectMode = buf.get();
    	// 读取发送者外部ip
    	internetIp = new byte[4];
    	buf.get(internetIp);
    	// 读取发送者外部端口
    	internetPort = buf.getChar();
    	// 读取文件传送端口
    	if(connectMode != QQ.QQ_TRANSFER_FILE_DIRECT_TCP)
    		majorPort = buf.getChar();
    	else
    		majorPort = internetPort;
    	// 读取发送者真实ip
    	localIp = new byte[4];
    	buf.get(localIp);
    	// 读取发送者真实端口
    	minorPort = buf.getChar();
    }
}
