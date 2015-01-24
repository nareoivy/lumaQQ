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
package edu.tsinghua.lumaqq.qq.packets;

/**
 * 当包解析出错时，抛出这个异常
 * 
 * @author luma
 */
public class PacketParseException extends Exception {
	private static final long serialVersionUID = 3257284738459775545L;
	
	public PacketParseException() {
		super();
	}
	
	public PacketParseException(String arg0) {
		super(arg0);
	}
	
	public PacketParseException(Throwable arg0) {
		super(arg0);
	}
	
	public PacketParseException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}
}
