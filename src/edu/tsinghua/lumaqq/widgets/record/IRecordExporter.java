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
package edu.tsinghua.lumaqq.widgets.record;


/**
 * 聊天记录导出器接口
 * 
 * @author luma
 */
public interface IRecordExporter {
	// 参数的关键字
	/** 导出类型，比如是导出好友记录还是群记录还是系统记录 */
	public static final String EXPORT_TYPE = "Export Type";
	/** 我自己的User对象 */
	public static final String MY_MODEL = "My Model";
	/** 好友的User对象 */
	public static final String FRIEND_MODEL = "Friend Model";
	/** 群的Cluster对象 */
	public static final String CLUSTER_MODEL = "Cluster Model";
	/** 手机号码 */
	public static final String MOBILE_NUMBER = "Mobile Number";
	/** RecordEntry列表 */
	public static final String RECORD_ENTRIES = "Record Entries";
	
	// 导出类型
	public static final int EXPORT_FRIEND = 0;
	public static final int EXPORT_CLUSTER = 1;
	public static final int EXPORT_SYSTEM = 2;
	public static final int EXPORT_SMS = 3;
	
	/**
	 * 产生导出文件的内容
	 * 
	 * @param argument
	 * 		参数
	 * @return
	 * 		String
	 */
	public String generate(Object argument);
}
