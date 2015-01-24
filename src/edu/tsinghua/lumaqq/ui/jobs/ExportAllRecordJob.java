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
package edu.tsinghua.lumaqq.ui.jobs;

import static edu.tsinghua.lumaqq.resource.Messages.*;
import edu.tsinghua.lumaqq.models.Cluster;
import edu.tsinghua.lumaqq.models.Group;
import edu.tsinghua.lumaqq.models.ModelRegistry;
import edu.tsinghua.lumaqq.models.User;
import edu.tsinghua.lumaqq.ui.MainShell;
import edu.tsinghua.lumaqq.widgets.record.IRecordExporterFactory;

public class ExportAllRecordJob extends AbstractJob {
	private String dir, fileTypeName;
	private IRecordExporterFactory factory;
	
	public ExportAllRecordJob(String dir, String fileTypeName, IRecordExporterFactory factory) {
		this.dir = dir;
		this.fileTypeName = fileTypeName;
		this.factory = factory;
	}
	
	@Override
	public void prepare(MainShell m) {
		super.prepare(m);
	}
	
	@Override
	public void clear() {
	}

	@Override
	public boolean isSuccess() {
		return true;
	}
	
	@Override
	protected void preLoop() {
		monitor.beginTask("", ModelRegistry.getUserClusterCount());
		monitor.subTask(job_export_all_record);
		
	    // 列举所有model，导出聊天记录
		String ext = factory.getFilterExtension(fileTypeName);
	    for(Group g : main.getBlindHelper().getExportGroupList()) {		            
            // 判断model类型，导出
            if(g.isCluster()) {
            	for(Cluster c : g.clusters) {
            		if(c.isPermanent())
            			main.getExportHelper().exportMessage(c, fileTypeName, dir + '(' + String.valueOf(c.externalId) + ')' + c.name + ext, null);
            		monitor.worked(1);
            	}
            } else {
            	for(User f : g.users) {
            		main.getExportHelper().exportMessage(f, fileTypeName, dir + String.valueOf(f.qq) + ext, null);             		
            		monitor.worked(1);
            	}
            }                
	    }
	    
	    monitor.done();
	    finished = true;
	}
}
