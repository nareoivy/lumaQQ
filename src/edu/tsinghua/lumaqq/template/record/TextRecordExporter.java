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


import edu.tsinghua.lumaqq.widgets.record.*;
import java.util.Map;

/**
 * 瀵煎嚭鍣ㄥ疄鐜扮被 
 *
 * @author luma
 */
@SuppressWarnings("unchecked")
public class TextRecordExporter implements IRecordExporter {
  protected static String nl;
  public static synchronized TextRecordExporter create(String lineSeparator)
  {
    nl = lineSeparator;
    TextRecordExporter result = new TextRecordExporter();
    nl = null;
    return result;
  }

  protected final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
	public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
     
	Map<String, Object> params = (Map<String, Object>)argument;
	Integer exportType = (Integer)params.get(IRecordExporter.EXPORT_TYPE);
	IRecordExporter exporter = TextRecordExporterFactory.getExporter(exportType);
	if(exporter != null)
		stringBuffer.append(exporter.generate(argument));

    return stringBuffer.toString();
  }
}