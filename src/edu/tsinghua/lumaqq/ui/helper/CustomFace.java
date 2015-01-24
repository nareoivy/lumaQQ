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
package edu.tsinghua.lumaqq.ui.helper;

/**
 * 自定义表情信息封装。
 *
 * @author luma
 */
public class CustomFace {
	public byte format;
	
	// 字母形式的表情id，从'A'开始
	public char id;
	// 数字形式的表情id，存放在face.xml中
	public int faceId;
	// 表情文件名，为md5加上扩展名
	public String filename;
	// 表情快捷键
	public String shortcut;
	// 中转密钥
	public byte[] agentKey;
	// 会话id
	public int sessionId;
	// 中转服务器ip
	public byte[] agentIp;
	// 中转服务器端口
	public int agentPort;
	
	@Override
	public int hashCode() {
		return faceId;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof CustomFace)
			return faceId == ((CustomFace)obj).faceId;
		else if(obj instanceof Integer)
			return faceId == (Integer)obj;
		else
			return false;
	}
}
