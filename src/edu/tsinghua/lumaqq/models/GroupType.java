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

/**
 * 组类型
 * 
 * @author luma
 */
public enum GroupType {
	// 缺省好友组，比如“我的好友”组，这种组和普通好友组的区别是不需要上传组名
	DEFAULT_FRIEND_GROUP(2),
	// 普通好友组
	FRIEND_GROUP(2),
	// 陌生人组
	STRANGER_GROUP(2),
	// 黑名单组
	BLACKLIST_GROUP(2),
	// 最近联系人组
	LATEST_GROUP(0),
	// 群组
	CLUSTER_GROUP(1);
	
	// 组的权重
	int weight;
	
	GroupType(int w) {
		weight = w;
	}
}
