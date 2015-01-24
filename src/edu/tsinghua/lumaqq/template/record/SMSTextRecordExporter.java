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
import edu.tsinghua.lumaqq.models.*;
import edu.tsinghua.lumaqq.record.*;
import edu.tsinghua.lumaqq.ui.helper.DateTool;
import java.util.Map;
import java.util.List;

/**
 * 瀵煎嚭鍣ㄥ疄鐜扮被 
 *
 * @author luma
 */
@SuppressWarnings("unchecked")
public class SMSTextRecordExporter implements IRecordExporter {
  protected static String nl;
  public static synchronized SMSTextRecordExporter create(String lineSeparator)
  {
    nl = lineSeparator;
    SMSTextRecordExporter result = new SMSTextRecordExporter();
    nl = null;
    return result;
  }

  protected final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "(";
  protected final String TEXT_2 = ")";
  protected final String TEXT_3 = " And (";
  protected final String TEXT_4 = ")";
  protected final String TEXT_5 = NL + NL + "==============================================================";
  protected final String TEXT_6 = NL;
  protected final String TEXT_7 = NL;
  protected final String TEXT_8 = " ";
  protected final String TEXT_9 = NL;

	public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
    
	Map<String, Object> params = (Map<String, Object>)argument;
	User me = (User)params.get(IRecordExporter.MY_MODEL);
	String mobile = (String)params.get(IRecordExporter.MOBILE_NUMBER);
	List<RecordEntry> entries = (List<RecordEntry>)params.get(IRecordExporter.RECORD_ENTRIES);

    stringBuffer.append(TEXT_1);
    stringBuffer.append( me.qq );
    stringBuffer.append(TEXT_2);
    stringBuffer.append( me.displayName );
    stringBuffer.append(TEXT_3);
    stringBuffer.append( mobile );
    stringBuffer.append(TEXT_4);
    stringBuffer.append( mobile );
    stringBuffer.append(TEXT_5);
     for(RecordEntry entry : entries) { 
    stringBuffer.append(TEXT_6);
    stringBuffer.append(TEXT_7);
    stringBuffer.append( DateTool.format(entry.time) );
    stringBuffer.append(TEXT_8);
     if(entry.sender == 0) { 
    stringBuffer.append( mobile );
     } else { 
    stringBuffer.append( me.displayName );
     } 
    stringBuffer.append(TEXT_9);
    stringBuffer.append( entry.message );
     } 
    return stringBuffer.toString();
  }
}