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
package edu.tsinghua.lumaqq.template;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.tsinghua.lumaqq.template.record.HtmlRecordExporter;
import edu.tsinghua.lumaqq.template.record.TextRecordExporter;
import edu.tsinghua.lumaqq.widgets.record.IRecordExporter;
import edu.tsinghua.lumaqq.widgets.record.IRecordExporterFactory;

/**
 * 文本格式记录导出器工厂 
 *
 * @author luma
 */
public class RecordExporterFactory implements IRecordExporterFactory {
	private Map<String, IRecordExporter> cache;
	private List<String> nameList;

	public RecordExporterFactory() {
		cache = new HashMap<String, IRecordExporter>();
		nameList = new ArrayList<String>();
		nameList.add("Text");
		nameList.add("HTML");
	}
	
	public List<String> getExporterNameList() {
		return nameList;
	}

	public IRecordExporter getExporter(String name) {
		IRecordExporter exporter = cache.get(name);
		if(exporter == null) {
			if(name.equals("Text"))
				exporter = new TextRecordExporter();
			else if(name.equals("HTML"))
				exporter = new HtmlRecordExporter();
			cache.put(name, exporter);
		}
		return exporter;
	}

	public String getFilterExtension(String name) {
		if(name.equals("Text"))
			return ".txt";
		else if(name.equals("HTML"))
			return ".html";
		else
			return ".*";
	}

	public String getFilterName(String name) {
		if(name.equals("Text"))
			return "Text File";
		else if(name.equals("HTML"))
			return "HTML File";
		else
			return "Any Type";
	}

	public String getDefaultExporterName() {
		return "Text";
	}	
}
