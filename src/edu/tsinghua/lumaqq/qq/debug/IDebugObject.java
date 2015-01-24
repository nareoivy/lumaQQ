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
package edu.tsinghua.lumaqq.qq.debug;

/**
 * 调试对象，一段数据
 * 
 * @author luma
 */
public interface IDebugObject {
    /**
     * @return
     * 		数据的字节数组形式
     */
    public byte[] getBytes();
    
    /**
     * @return
     * 		数据的16进制字符串形式
     */
    public String getHexString();
    
    /**
     * @return
     * 		数据的描述性名称
     */
    public String getName();
    
    /**
     * 设置名称
     * 
     * @param name
     * 		名称
     */
    public void setName(String name);
    
    /**
     * @return
     * 		true表示数据是收到的数据，false表示是发出的数据
     */
    public boolean isIncoming();
    
    /**
     * @return
     * 		数据字节长度
     */
    public int getLength();
    
    /**
     * @return
     * 		包体字节数组
     */
    public byte[] getBodyBytes();
    
    /**
     * @return
     * 		包体的十六进制字符串形式
     */
    public String getBodyHexString();
}
