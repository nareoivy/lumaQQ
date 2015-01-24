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

import edu.tsinghua.lumaqq.qq.QQ;
import edu.tsinghua.lumaqq.qq.Util;

/**
 * IDebugObject接口的缺省实现
 * 
 * @author luma
 */
public abstract class AbstractDebugObject implements IDebugObject {
    protected String name;
    protected byte[] bytes;
    protected String hexString;
    protected boolean incoming;
    protected String bodyHexString;
    protected byte[] bodyBytes;

    /**
     * 创建一个调试对象，忽略incoming属性
     * 
     * @param n
     * 		名称
     * @param b
     * 		字节数组
     */
    public AbstractDebugObject(String n, byte[] b) {
        this(n, b, false);
    }
    
    /**
     * 创建一个调试对象
     * 
     * @param n
     * 		名称
     * @param b
     * 		字节数组
     * @param i
     * 		是否接收到的包
     */
    public AbstractDebugObject(String n, byte[] b, boolean i) {
        name = n;
        bytes = b;
        incoming = i;
        bodyBytes = bytes;
    }
    
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.debug.IDebugObject#getBytes()
     */
    public byte[] getBytes() {
        return bytes;
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.debug.IDebugObject#getHexString()
     */
    public String getHexString() {
        if(hexString == null) {
            if(bytes != null)
                hexString = Util.convertByteToHexString(bytes);
            else
                hexString = QQ.EMPTY_STRING;
        }        
        return hexString;
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.debug.IDebugObject#getName()
     */
    public String getName() {
        return name;
    }
    
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.debug.IDebugObject#setName(java.lang.String)
     */
    public void setName(String name) {
        this.name = name;
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.debug.IDebugObject#isIncoming()
     */
    public boolean isIncoming() {
        return incoming;
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.debug.IDebugObject#getLength()
     */
    public int getLength() {
        if(bytes == null)
            return 0;
        else
            return bytes.length;
    }    

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.debug.IDebugObject#getBodyBytes()
     */
    public byte[] getBodyBytes() {
        return bodyBytes;
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.debug.IDebugObject#getBodyHexString()
     */
    public String getBodyHexString() {
        if(bodyHexString == null)
            bodyHexString = Util.convertByteToHexString(bodyBytes);
        return bodyHexString;
    }
}
