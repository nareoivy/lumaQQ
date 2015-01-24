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
 * 聊天记录
 * 
 * @author luma
 */
public class RecordEntry {
	/** 消息的类型 */
	public int type;
	
	/** 消息的子类型 */
	public int subType;
	
	/** 
	 * Owner是一个消息的拥有者，它并不一定是这条消息的发送者
	 * Owner就相当于是对消息的一个最粗的分类 
	 * 比如群消息，其发送者是具体的某个用户，但是这条消息属于群，所以
	 * owner是群的内部ID
	 */
	public int owner;
	
	/**
	 * sender是一个消息真正的发送者，sender和owner之间一般是从属的关系
	 * 但是也不一定是。 
	 */
	public int sender;
	
	/**
	 * senderParent是消息发送者的父组织，对于群成员来说，这个就是群内部id
	 * 对于其他情况，一般无意义。senderParent不一定等于owner，比如群通知 
	 */
	public int senderParent;
	
	/**
	 * receiver是一条消息的接收者，这个一般都是用户
	 */
	public int receiver;
	
	/**
	 * 消息的发送时间，从1970-1-1开始的毫秒数
	 */
	public long time;
	
	/** 消息内容 */
	public String message;
}
