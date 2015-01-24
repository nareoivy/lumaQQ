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
package edu.tsinghua.lumaqq.qq.packets.out;

import java.nio.ByteBuffer;

import edu.tsinghua.lumaqq.qq.QQ;
import edu.tsinghua.lumaqq.qq.Util;
import edu.tsinghua.lumaqq.qq.beans.ContactInfo;
import edu.tsinghua.lumaqq.qq.beans.QQUser;
import edu.tsinghua.lumaqq.qq.packets.BasicOutPacket;
import edu.tsinghua.lumaqq.qq.packets.PacketParseException;


/**
 * <pre>
 * 修改用户个人信息的请求包，格式是:
 * 1. 头部
 * 2. 旧密码，新密码以及ContactInfo里面的域，但是不包括第一项QQ号，用0x1F分隔，依次往下排，最后要用
 *    一个0x1F结尾。但是开头不需要0x1F，如果哪个字段没有，就是空
 * 3. 尾部
 * </pre>
 *
 * @author luma
 */
public class ModifyInfoPacket extends BasicOutPacket {
    private String newPassword;
    private String oldPassword;
    private ContactInfo contactInfo;
    
    /** 分隔符 */
    private static final byte DELIMIT = 0x1F;
    
    /**
     * 构造函数
     */
    public ModifyInfoPacket(QQUser user) {
        super(QQ.QQ_CMD_MODIFY_INFO, true, user);
    }

    /**
     * @param buf
     * @param length
     * @throws PacketParseException
     */
    public ModifyInfoPacket(ByteBuffer buf, int length, QQUser user)
            throws PacketParseException {
        super(buf, length, user);
    }
    
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.packets.OutPacket#getPacketName()
     */
	@Override
    public String getPacketName() {
        return "Modify Info Packet";
    }
    
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.packets.OutPacket#putBody(java.nio.ByteBuffer)
     */
	@Override
    protected void putBody(ByteBuffer buf) {    
	    // 组装内容，首先是旧密码和新密码
	    if(oldPassword != null && newPassword != null && !newPassword.equals("")) {
	        buf.put(oldPassword.getBytes());
	        buf.put(DELIMIT);
	        buf.put(newPassword.getBytes());
	    } else
	        buf.put(DELIMIT);
	    buf.put(DELIMIT);
	    // 写入contactInfo，除了QQ号
	    String[] infos = contactInfo.getInfoArray();
	    for(int i = 1; i < QQ.QQ_COUNT_MODIFY_USER_INFO_FIELD; i++) {
		    byte[] b = Util.getBytes(infos[i]);
		    buf.put(b);
		    buf.put(DELIMIT);
	    }
    }
    
    /**
     * @return Returns the contactInfo.
     */
    public ContactInfo getContactInfo() {
        return contactInfo;
    }
    
    /**
     * @param contactInfo The contactInfo to set.
     */
    public void setContactInfo(ContactInfo contactInfo) {
        this.contactInfo = contactInfo;
    }
    
    /**
     * @return Returns the newPassword.
     */
    public String getNewPassword() {
        return newPassword;
    }
    
    /**
     * @param newPassword The newPassword to set.
     */
    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
    
    /**
     * @return Returns the oldPassword.
     */
    public String getOldPassword() {
        return oldPassword;
    }
    
    /**
     * @param oldPassword The oldPassword to set.
     */
    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }
}
