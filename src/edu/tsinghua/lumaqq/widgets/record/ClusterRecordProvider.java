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

import edu.tsinghua.lumaqq.models.Cluster;
import edu.tsinghua.lumaqq.record.RecordEntry;

/**
 * 群聊天记录提供者
 * 
 * @author luma
 */
public class ClusterRecordProvider extends ListRecordProvider {
	private Cluster c;
	
	public ClusterRecordProvider(Cluster c) {
		this.c = c;
	}

	public IWaterfallLabelProvider getRecordLabelProvider() {
		return new NormalClusterIMLabelProvider();
	}
	
	/* (non-Javadoc)
	 * @see edu.tsinghua.lumaqq.widgets.record.IRecordProvider#getExportType()
	 */
	public int getExportType() {
		return IRecordExporter.EXPORT_CLUSTER;
	}

	/* (non-Javadoc)
	 * @see edu.tsinghua.lumaqq.widgets.IRecordProvider#copyToClipboard(edu.tsinghua.lumaqq.MessageEntry)
	 */
	public String copyToClipboard(RecordEntry entry) {
		return entry.message;
	}

	/* (non-Javadoc)
	 * @see edu.tsinghua.lumaqq.widgets.IRecordProvider#getId()
	 */
	public int getId() {
		if(c == null)
			return 0;
		else
			return c.clusterId;
	}

	/* (non-Javadoc)
	 * @see edu.tsinghua.lumaqq.widgets.IRecordProvider#setModel(edu.tsinghua.lumaqq.models.Model)
	 */
	public void setModel(Object m) {
		c = (Cluster)m;
	}

	public Object getModel() {
		return c;
	}
}
