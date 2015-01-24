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
import edu.tsinghua.lumaqq.qq.Util;

/**
 * 请求传送文件包的数据封装类，传送文件包是发送消息包的变种格式
 * 
 *  * @author luma
 */
public class FileInfo {    
	// 文件名
    public String fileName;
    // 文件大小
    public int fileSize;
    
    /**
     * 给定一个输入流，解析SendFileRequest结构
     * @param buf
     */
    public void readBean(ByteBuffer buf) throws Exception {
    	// 跳过空格符和分隔符
    	buf.getChar();
    	// 获取后面的所有内容
    	byte[] b = new byte[buf.remaining()];
    	buf.get(b);
    	// 找到分隔符
    	int i = Util.indexOf(b, 0, (byte)0x1F);
    	// 得到文件名
    	fileName = Util.getString(b, 0, i, QQ.QQ_CHARSET_DEFAULT);
    	// 得到文件大小的字符串形式
    	String sizeStr = new String(b, i + 1, b.length - 6 - i);
    	fileSize = Util.getInt(sizeStr, 0);            
    }
}
