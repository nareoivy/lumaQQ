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
import edu.tsinghua.lumaqq.qq.beans.FriendRemark;
import edu.tsinghua.lumaqq.qq.beans.QQUser;
import edu.tsinghua.lumaqq.qq.packets.BasicOutPacket;
import edu.tsinghua.lumaqq.qq.packets.PacketParseException;

/**
 * <pre>
 * 上传下载好友备注的消息包，格式为
 * 1. 头部
 * 2. 子命令，1字节
 * 3. 页号，1字节，从1开始，如果为0，表示此字段未用
 * 4. 操作对象的QQ号，4字节
 * 5. 未知1字节，0x00
 * 6. 以下为备注信息，一共7个域，域的顺序依次次是
 *    姓名、手机、电话、地址、邮箱、邮编、备注
 *    每个域都有一个前导字节，这个字节表示了这个域的字节长度
 * 7. 尾部
 * 
 * Note: 如果子命令是0x00(批量下载备注)，只有2，3部分
 * 		 如果子命令是0x01(上传备注)，所有部分都要，3部分未用
 *       如果子命令是0x02(删除好友)，仅保留1,2,4,7部分
 *       如果子命令是0x03(下载备注)，仅保留1,2,4,7部分
 * 
 * 0x05,modify remark
 * 0x05
 * qq number
 * remark length, 1 byte
 * remark
 * </pre>
 * 
 * @author luma
 */
public class FriendDataOpPacket extends BasicOutPacket {
	// 操作类型，上传还是下载
	private byte subCommand;
	// 操作的对象的QQ号
	private int qq;
	// 好友备注对象
	private FriendRemark remark;
	// 页号
	private int page;
	
    /**
     * 构造函数
     */
    public FriendDataOpPacket(QQUser user) {
        super(QQ.QQ_CMD_FRIEND_DATA_OP, true, user);
		subCommand = QQ.QQ_SUB_CMD_UPLOAD_FRIEND_REMARK;
		remark = new FriendRemark();
		page = 0;
    }
    
    /**
     * @param buf
     * @param length
     * @throws PacketParseException
     */
    public FriendDataOpPacket(ByteBuffer buf, int length, QQUser user)
            throws PacketParseException {
        super(buf, length, user);
    }
    
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.packets.OutPacket#getPacketName()
     */
	@Override
    public String getPacketName() {
        switch(subCommand) {
            case QQ.QQ_SUB_CMD_BATCH_DOWNLOAD_FRIEND_REMARK:
                return "Friend Data Packet - Batch Download Remark";
            case QQ.QQ_SUB_CMD_UPLOAD_FRIEND_REMARK:
                return "Friend Data Packet - Upload Remark";
            case QQ.QQ_SUB_CMD_DOWNLOAD_FRIEND_REMARK:
                return "Friend Data Packet - Download Remark";
            case QQ.QQ_SUB_CMD_REMOVE_FRIEND_FROM_LIST:
                return "Friend Data Packet - Remove Friend From List";
            default:
                return "Friend Data Packet - Unknown Sub Command";
        }
    }
    
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.packets.OutPacket#putBody(java.nio.ByteBuffer)
     */
	@Override
    protected void putBody(ByteBuffer buf) {
		// 操作类型
		buf.put(subCommand);
		// 未知字节0x0，仅在上传时
		if(subCommand == QQ.QQ_SUB_CMD_UPLOAD_FRIEND_REMARK || subCommand == QQ.QQ_SUB_CMD_BATCH_DOWNLOAD_FRIEND_REMARK)
			buf.put((byte)page);
		// 操作对象的QQ号
		if(subCommand != QQ.QQ_SUB_CMD_BATCH_DOWNLOAD_FRIEND_REMARK)
		    buf.putInt(qq);
		// 后面的内容为一个未知字节0和备注信息，仅在上传时
		if(subCommand == QQ.QQ_SUB_CMD_UPLOAD_FRIEND_REMARK) {
			buf.put((byte)0);
			// 备注信息
			// 姓名
			if(remark.name == null || remark.name.equals(""))
				buf.put((byte)0);
			else {
				byte[] b = Util.getBytes(remark.name);
				buf.put((byte)b.length);
				buf.put(b);					
			}
			// 手机
			if(remark.mobile == null || remark.mobile.equals(""))
			    buf.put((byte)0);
			else  {
				byte[] b = Util.getBytes(remark.mobile);
				buf.put((byte)b.length);
				buf.put(b);					
			}
			// 电话
			if(remark.telephone == null || remark.telephone.equals(""))
			    buf.put((byte)0);
			else  {
				byte[] b = Util.getBytes(remark.telephone);
				buf.put((byte)b.length);
				buf.put(b);				
			}
			// 地址
			if(remark.address == null || remark.address.equals(""))
			    buf.put((byte)0);
			else  {
				byte[] b = Util.getBytes(remark.address);
				buf.put((byte)b.length);
				buf.put(b);					
			}
			// 邮箱
			if(remark.email == null || remark.email.equals(""))
			    buf.put((byte)0);
			else  {
				byte[] b = Util.getBytes(remark.email);
				buf.put((byte)b.length);
				buf.put(b);					
			}
			// 邮编
			if(remark.zipcode == null || remark.zipcode.equals(""))
			    buf.put((byte)0);
			else  {
				byte[] b = Util.getBytes(remark.zipcode);
				buf.put((byte)b.length);
				buf.put(b);						
			}
			// 备注
			if(remark.note == null || remark.note.equals(""))
			    buf.put((byte)0);
			else  {
				byte[] b = Util.getBytes(remark.note);
				buf.put((byte)b.length);
				buf.put(b);					
			}
		}
    }
	
	@Override
	protected void parseBody(ByteBuffer buf) throws PacketParseException {
		subCommand = buf.get();
	}
    
	/**
	 * @return Returns the address.
	 */
	public String getAddress() {
		return remark.address;
	}
	
	/**
	 * @param address The address to set.
	 */
	public void setAddress(String address) {
		remark.address = address;
	}
	
	/**
	 * @return Returns the email.
	 */
	public String getEmail() {
		return remark.email;
	}
	
	/**
	 * @param email The email to set.
	 */
	public void setEmail(String email) {
		remark.email = email;
	}
	
	/**
	 * @return Returns the mobile.
	 */
	public String getMobile() {
		return remark.mobile;
	}
	
	/**
	 * @param mobile The mobile to set.
	 */
	public void setMobile(String mobile) {
		remark.mobile = mobile;
	}
	
	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return remark.name;
	}
	
	/**
	 * @param name The name to set.
	 */
	public void setName(String name) {
		remark.name = name;
	}
	
	/**
	 * @return Returns the note.
	 */
	public String getNote() {
		return remark.note;
	}
	
	/**
	 * @param note The note to set.
	 */
	public void setNote(String note) {
		remark.note = note;
	}
	
	/**
	 * @return Returns the telephone.
	 */
	public String getTelephone() {
		return remark.telephone;
	}
	
	/**
	 * @param telephone The telephone to set.
	 */
	public void setTelephone(String telephone) {
		remark.telephone = telephone;
	}
	
	/**
	 * @return Returns the zipcode.
	 */
	public String getZipcode() {
		return remark.zipcode;
	}
	
	/**
	 * @param zipcode The zipcode to set.
	 */
	public void setZipcode(String zipcode) {
		remark.zipcode = zipcode;
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
	public void setQQ(int qqNum) {
		this.qq = qqNum;
	}
	
	/**
	 * @return Returns the remark.
	 */
	public FriendRemark getRemark() {
		return remark;
	}
	
	/**
	 * @param remark The remark to set.
	 */
	public void setRemark(FriendRemark remark) {
		this.remark = remark;
	}
	
	/**
	 * @return Returns the type.
	 */
	public byte getSubCommand() {
		return subCommand;
	}
	
	/**
	 * @param type The type to set.
	 */
	public void setSubCommand(byte type) {
		this.subCommand = type;
	}
    /**
     * @return Returns the page.
     */
    public int getPage() {
        return page;
    }
    /**
     * @param page The page to set.
     */
    public void setPage(int page) {
        this.page = page;
    }
}
