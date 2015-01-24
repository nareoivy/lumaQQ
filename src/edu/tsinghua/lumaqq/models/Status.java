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
package edu.tsinghua.lumaqq.models;

import edu.tsinghua.lumaqq.qq.QQ;

import org.eclipse.swt.SWT;

/**
 * 在线状态常量
 * 
 * @author luma
 */
public enum Status {
	ONLINE,
	AWAY,
	HIDDEN,
	OFFLINE;
	
	public byte toQQConstant() {
		switch(this) {
			case ONLINE:
				return QQ.QQ_STATUS_ONLINE;
			case AWAY:
				return QQ.QQ_STATUS_AWAY;
			case HIDDEN:
				return QQ.QQ_STATUS_HIDDEN;
			case OFFLINE:
				return QQ.QQ_STATUS_OFFLINE;
			default:
				SWT.error(SWT.ERROR_INVALID_RANGE);
				return 0;
		}
	}
	
	public static Status valueOf(byte b) {
		switch(b) {
			case QQ.QQ_STATUS_AWAY:
				return AWAY;
			case QQ.QQ_STATUS_HIDDEN:
				return HIDDEN;
			case QQ.QQ_STATUS_OFFLINE:
				return OFFLINE;
			case QQ.QQ_STATUS_ONLINE:
				return ONLINE;
			default:
				return OFFLINE;
		}
	}
}
