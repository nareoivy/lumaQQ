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
package edu.tsinghua.lumaqq.record;

/**
 * 消息关键字用到的一些常量
 * 
 * @author luma
 */
public interface IKeyConstants {
	// 类型
	public static final int ALL = 0;
	public static final int NORMAL = 1;
	public static final int CLUSTER = 2;
	public static final int SYSTEM = 3;
	
	// 子类型
	public static final int SUB_UNKNOWN = -1;
	public static final int SUB_ALL = 0;
	public static final int SUB_ADDED_TO_CLUSTER = 1;
	public static final int SUB_CREATE_CLUSTER = 2;
	public static final int SUB_DELETED_FROM_CLUSTER = 3;
	public static final int SUB_REQUEST_JOIN_CLUSTER = 4;
	public static final int SUB_APPROVE_JOIN_CLUSTER = 5;
	public static final int SUB_REJECT_JOIN_CLUSTER = 6;
	public static final int SUB_CLUSTER_NOTIFICATION_SET_ADMIN = 7;
	public static final int SUB_CLUSTER_NOTIFICATION_UNSET_ADMIN = 8;
	public static final int SUB_BEING_ADDED = 9;
	public static final int SUB_BEING_ADDED_EX = 10;
	public static final int SUB_ADD_FRIEND_REQUEST = 11;
	public static final int SUB_ADD_FRIEND_REQUEST_EX = 12;
	public static final int SUB_ADD_FRIEND_APPROVED = 13;
	public static final int SUB_ADD_FRIEND_REJECTED = 14;
	public static final int SUB_ADD_FRIEND_APPROVED_AND_ADD = 15;
}
