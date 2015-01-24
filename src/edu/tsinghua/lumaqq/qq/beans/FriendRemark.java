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
 * 存放好友的备注信息
 * 
 * @author luma
 * @see edu.tsinghua.lumaqq.qq.packets.in.FriendDataOpReplyPacket
 */
public class FriendRemark {    
	// 姓名
	public String name;
	// 手机号码
	public String mobile;
	// 电话号码
	public String telephone;
	// 联系地址
	public String address;
	// 电子邮箱
	public String email;
	// 邮编
	public String zipcode;
	// 备注
	public String note;
	
	/**
	 * 构造函数
	 */
	public FriendRemark() {
		name = mobile = telephone = address = email = zipcode = note = "";
	}
	
    /**
     * 给定一个输入流，解析FriendRemark结构
     * @param buf
     */
	public void readBean(ByteBuffer buf) {
		// 循环读取字段
		for(int i = 0; i < QQ.QQ_COUNT_REMARK_FIELD; i++) {
			// 判断字段是否存在		    
			int len = buf.get() & 0xFF;
			if(len > 0) {
				String s = Util.filterUnprintableCharacter(Util.getString(buf, len));			
				// 根据i的值赋值
				switch(i) {
					case 0:
						name = s;
						break;
					case 1:
						mobile = s;
						break;
					case 2:
						telephone = s;
						break;
					case 3:
						address = s;
						break;
					case 4:
						email = s;
						break;
					case 5:
						zipcode = s;
						break;
					case 6:
						note = s;
						break;
				}
			}
		}
	}
}