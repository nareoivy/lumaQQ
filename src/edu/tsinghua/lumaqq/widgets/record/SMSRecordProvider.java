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

import edu.tsinghua.lumaqq.models.User;
import edu.tsinghua.lumaqq.record.RecordEntry;

/**
 * 手机短信聊天记录提供者
 *
 * @author luma
 */
public class SMSRecordProvider extends ListRecordProvider {
	private boolean isUser;
	private User friend;
	private String mobile;

	public SMSRecordProvider(User friend) {
		this.friend = friend;
		isUser = true;
	}
	
	public SMSRecordProvider(String mobile) {
		this.mobile = mobile;
		isUser = false;
	}
	
	public IWaterfallLabelProvider getRecordLabelProvider() {
		return new SMSLableProvider();
	}
	
	public int getExportType() {
		if(isUser)
			return IRecordExporter.EXPORT_FRIEND;
		else
			return IRecordExporter.EXPORT_SMS;
	}

	public String copyToClipboard(RecordEntry entry) {
		int index = entry.message.indexOf('|');
		return entry.message.substring(index + 1);
	}

	public int getId() {
		return isUser ? friend.qq : 9999;
	}

	public void setModel(Object m) {
		if(isUser)
			friend = (User)m;
		else
			mobile = (String)m;
	}

	public Object getModel() {
		return isUser ? friend : mobile;
	}
}
