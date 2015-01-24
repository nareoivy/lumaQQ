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

import java.util.List;

/**
 * 导出器的工厂接口 
 *
 * @author luma
 */
public interface IRecordExporterFactory {
	/**
	 * @return
	 * 		现有的导出器名称列表
	 */
	public List<String> getExporterNameList();
	
	/**
	 * 根据名称得到导出器实例
	 * 
	 * @param name
	 * 		导出器名称
	 * @return
	 * 		IRecordExporter
	 */
	public IRecordExporter getExporter(String name);
	
	/**
	 * 根据文件类型得到过滤器模式
	 * 
	 * @param name
	 * 		文件类型名
	 * @return	
	 * 		过滤器模式
	 */
	public String getFilterExtension(String name);
	
	/**
	 * 根据文件类型得到过滤器名称 
	 * 
	 * @param name
	 * 		文件类型名
	 * @return
	 * 		过滤器名称
	 */
	public String getFilterName(String name);
	
	/**
	 * @return
	 * 		缺省导出格式名
	 */
	public String getDefaultExporterName();
}
