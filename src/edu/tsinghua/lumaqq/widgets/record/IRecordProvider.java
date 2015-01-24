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

import java.util.Comparator;
import java.util.List;

import edu.tsinghua.lumaqq.record.RecordEntry;

/**
 * 聊天记录内容提供者
 * 
 * @author luma
 */
public interface IRecordProvider {
	/**
	 * @return
	 * 		聊天记录表格的内容提供者
	 */
	public IWaterfallContentProvider getRecordContentProvider();
	
	/**
	 * @return
	 * 		聊天记录表格的标签提供者
	 */
	public IWaterfallLabelProvider getRecordLabelProvider();
	
	/**
	 * @return
	 * 		得到聊天记录导出类型
	 */
	public int getExportType();
	
	/**
	 * @return
	 * 		得到聊天记录排序器
	 */
	public Comparator<Object> getRecordSorter();
	
	/**
	 * 设置聊天记录列表
	 * 
	 * @param records
	 */
	public void setRecords(List<RecordEntry> records);
	
	/**
	 * 生成一个可以拷贝到剪贴板的字符串
	 * 
	 * @param entry
	 * 		MessageEntry对象
	 */
	public String copyToClipboard(RecordEntry entry);
	
	/**
	 * @return
	 * 		得到聊天记录列表
	 */
	public List<RecordEntry> getRecords();
	
	/**
	 * @return
	 * 		唯一的id，可以是qq号，群id等等，用来指定查看谁的消息
	 */
	public int getId();
	
	/**
	 * 设置model对象
	 * 
	 * @param m
	 * 		Model
	 */
	public void setModel(Object m);
	
	/**
	 * @return
	 * 		Model
	 */
	public Object getModel();
}
