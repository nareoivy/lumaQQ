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

import edu.tsinghua.lumaqq.record.RecordEntry;

/**
 * 系统消息记录提供者
 * 
 * @author luma
 */
public class SystemRecordProvider extends ListRecordProvider {
	public IWaterfallLabelProvider getRecordLabelProvider() {
		return new SystemMessageLabelProvider();
	}
	
	public int getExportType() {
		return IRecordExporter.EXPORT_SYSTEM;
	}

	public String copyToClipboard(RecordEntry entry) {
		return entry.message.substring(entry.message.lastIndexOf('|') + 1);
	}

	/* (non-Javadoc)
	 * @see edu.tsinghua.lumaqq.widgets.IRecordProvider#getId()
	 */
	public int getId() {
		return 10000;
	}

	/* (non-Javadoc)
	 * @see edu.tsinghua.lumaqq.widgets.IRecordProvider#setModel(edu.tsinghua.lumaqq.models.Model)
	 */
	public void setModel(Object m) {
	}

	public Object getModel() {
		return null;
	}
}
