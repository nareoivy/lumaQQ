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
 * 群类型常量
 * 
 * @author luma
 */
public enum ClusterType {	
	NORMAL,  	// 普通群
	DIALOG, 	// 多人对话
	DIALOG_CONTAINER, // 多人对话容器
	SUBJECT;   	// 讨论组
	
	public static ClusterType valueOfTemp(byte b) {
		switch(b) {
			case QQ.QQ_CLUSTER_TYPE_DIALOG:
				return DIALOG;
			case QQ.QQ_CLUSTER_TYPE_SUBJECT:
				return SUBJECT;
			default:
				SWT.error(SWT.ERROR_INVALID_RANGE);
				return null;
		}
	}
	
	public byte toQQConstant() {
		switch(this) {
			case NORMAL:
				return QQ.QQ_CLUSTER_TYPE_PERMANENT;
			case DIALOG:
				return QQ.QQ_CLUSTER_TYPE_DIALOG;
			case SUBJECT:
				return QQ.QQ_CLUSTER_TYPE_SUBJECT;
			default:
				return 0;
		}
	}
}
