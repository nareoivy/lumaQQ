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

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.TextLayout;
import org.eclipse.swt.graphics.TextStyle;
import org.eclipse.swt.widgets.Display;

import edu.tsinghua.lumaqq.models.ModelRegistry;
import edu.tsinghua.lumaqq.models.User;
import edu.tsinghua.lumaqq.record.RecordEntry;
import edu.tsinghua.lumaqq.ui.helper.DateTool;

/**
 * 手机短信聊天记录标签提供者
 *
 * @author luma
 */
public class SMSLableProvider implements IWaterfallLabelProvider {
	private static final TextStyle STYLE = new TextStyle(null, Display.getDefault().getSystemColor(SWT.COLOR_RED), null);
	private static final String CRLF = System.getProperty("line.separator");
	
	public String getText(Object element) {
		RecordEntry entry = (RecordEntry)element;
		if(entry.sender == 0) {
			int index = entry.message.indexOf('|');
			String mobile = entry.message.substring(0, index);
			String message = entry.message.substring(index + 1);
			String date = DateTool.format(entry.time);
			return date + "  " + mobile + CRLF + message;
		} else {
			int index = entry.message.indexOf('|');
			String message = entry.message.substring(index + 1);
			User f = ModelRegistry.getUser(entry.sender);
			String fName = (f == null) ? String.valueOf(entry.sender) : (f.displayName.equals("") ? f.nick : f.displayName);
			String date = DateTool.format(entry.time);
			return date + "  " + fName + CRLF + message;
		}
	}

	public void installStyle(Object element, TextLayout layout) {
		RecordEntry entry = (RecordEntry)element;
		if(entry.sender == 0) {
			int index = entry.message.indexOf('|');
			String date = DateTool.format(entry.time);
			layout.setStyle(STYLE, 0, date.length() + index + 1);
		} else {
			User f = ModelRegistry.getUser(entry.sender);
			String fName = (f == null) ? String.valueOf(entry.sender) : (f.displayName.equals("") ? f.nick : f.displayName);
			String date = DateTool.format(entry.time);
			layout.setStyle(STYLE, 0, date.length() + fName.length() + 1);
		}
	}
}
