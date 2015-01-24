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
public class ClusterHtmlRecordExporter implements IRecordExporter {
  protected static String nl;
  public static synchronized ClusterHtmlRecordExporter create(String lineSeparator)
  {
    nl = lineSeparator;
    ClusterHtmlRecordExporter result = new ClusterHtmlRecordExporter();
    nl = null;
    return result;
  }

  protected final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "<HTML>" + NL + "<HEAD>" + NL + "<TITLE>(";
  protected final String TEXT_2 = ")";
  protected final String TEXT_3 = "</TITLE>" + NL + "</HEAD>" + NL + "" + NL + "<BODY>" + NL + "<center><b>(";
  protected final String TEXT_4 = ")";
  protected final String TEXT_5 = "</b></center><br/>" + NL + "<TABLE width=\"100%\" border=1 cellpadding=3>" + NL + "<TR bgcolor=\"#718BD6\">" + NL + "\t<TH><font color=\"#FFFFFF\">Nick</font></TH>" + NL + "\t<TH><font color=\"#FFFFFF\">Time</font></TH>" + NL + "\t<TH><font color=\"#FFFFFF\">Message</font></TH>" + NL + "</TR>";
  protected final String TEXT_6 = NL + "\t";
  protected final String TEXT_7 = NL + "\t<TR ";
  protected final String TEXT_8 = "bgcolor=\"#F6F6F6\"";
  protected final String TEXT_9 = "bgcolor=\"#FFFFFF\"";
  protected final String TEXT_10 = ">" + NL + "\t\t<TD><font color=\"#006699\">";
  protected final String TEXT_11 = "</font></TD>" + NL + "\t\t<TD><font color=\"#000000\">";
  protected final String TEXT_12 = "</font></TD>" + NL + "\t\t<TD><font color=\"#006699\">";
  protected final String TEXT_13 = "</font></TD>" + NL + "\t</TR>" + NL + "\t";
  protected final String TEXT_14 = NL + "</TABLE>" + NL + "</BODY>" + NL + "</HTML>";

	public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
    
	Map<String, Object> params = (Map<String, Object>)argument;
	Cluster c = (Cluster)params.get(CLUSTER_MODEL);
	List<RecordEntry> entries = (List<RecordEntry>)params.get(IRecordExporter.RECORD_ENTRIES);

    stringBuffer.append(TEXT_1);
    stringBuffer.append( c.externalId );
    stringBuffer.append(TEXT_2);
    stringBuffer.append( c.name );
    stringBuffer.append(TEXT_3);
    stringBuffer.append( c.externalId );
    stringBuffer.append(TEXT_4);
    stringBuffer.append( c.name );
    stringBuffer.append(TEXT_5);
     int i = 0; 
     for(RecordEntry entry : entries) { 
    stringBuffer.append(TEXT_6);
     User f = ModelRegistry.getUser(entry.sender); 
    stringBuffer.append(TEXT_7);
     if(i % 2 == 0) { 
    stringBuffer.append(TEXT_8);
     } else { 
    stringBuffer.append(TEXT_9);
     } 
    stringBuffer.append(TEXT_10);
     if(f == null) { 
    stringBuffer.append( entry.sender );
     } else { 
    stringBuffer.append( f.displayName );
     } 
    stringBuffer.append(TEXT_11);
    stringBuffer.append( DateTool.format(entry.time) );
    stringBuffer.append(TEXT_12);
    stringBuffer.append( entry.message );
    stringBuffer.append(TEXT_13);
     i++; 
     } 
    stringBuffer.append(TEXT_14);
    return stringBuffer.toString();
  }
}