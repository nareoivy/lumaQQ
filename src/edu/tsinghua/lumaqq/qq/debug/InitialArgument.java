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
 * <pre>
 * 独立调试模式下的一些必须的参数封装类
 * </pre>
 * 
 * @author luma
 */
public class InitialArgument {
    // QQ号
    private int qq;
    // 密码
    private String password;
    // 登录的回复包字节数组
    private byte[] loginReply;    
    
    /**
     * @return Returns the loginReply.
     */
    public byte[] getLoginReply() {
        return loginReply;
    }
    /**
     * @param loginReply The loginReply to set.
     */
    public void setLoginReply(byte[] loginReply) {
        this.loginReply = loginReply;
    }
    /**
     * @return Returns the password.
     */
    public String getPassword() {
        return password;
    }
    /**
     * @param password The password to set.
     */
    public void setPassword(String password) {
        this.password = password;
    }
    /**
     * @return Returns the qq.
     */
    public int getQQ() {
        return qq;
    }
    /**
     * @param qq The qq to set.
     */
    public void setQQ(int qq) {
        this.qq = qq;
    }
}
