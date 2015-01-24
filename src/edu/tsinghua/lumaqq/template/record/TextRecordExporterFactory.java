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
package edu.tsinghua.lumaqq.template.record;

import java.util.HashMap;
import java.util.Map;

import edu.tsinghua.lumaqq.widgets.record.IRecordExporter;

/**
 * 文本格式导出器工厂
 *
 * @author luma
 */
class TextRecordExporterFactory {
	private static Map<Integer, IRecordExporter> cache = new HashMap<Integer, IRecordExporter>();
	
	/**
	 * 根据导出类型得到导出器实例
	 * 
	 * @param exportType
	 * @return
	 */
	static IRecordExporter getExporter(int exportType) {
		IRecordExporter exporter = cache.get(exportType);
		if(exporter == null) {
			switch(exportType) {
				case IRecordExporter.EXPORT_FRIEND:
					exporter = new FriendTextRecordExporter();
					break;
				case IRecordExporter.EXPORT_CLUSTER:
					exporter = new ClusterTextRecordExporter();
					break;
				case IRecordExporter.EXPORT_SYSTEM:
					exporter = new SystemTextRecordExporter();
					break;
				case IRecordExporter.EXPORT_SMS:
					exporter = new SMSTextRecordExporter();
					break;
			}
			cache.put(exportType, exporter);
		}
		return exporter;
	}
}
