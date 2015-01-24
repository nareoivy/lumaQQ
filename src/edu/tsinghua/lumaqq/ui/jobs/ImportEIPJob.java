/*
* LumaQQ - Java QQ Client
*
* Copyright (C) 2004 notXX
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

import static edu.tsinghua.lumaqq.resource.Messages.job_import_eip;
import edu.tsinghua.lumaqq.customface.EIPImporter;
import edu.tsinghua.lumaqq.customface.FaceEntry;
import edu.tsinghua.lumaqq.ecore.face.FaceGroup;
import edu.tsinghua.lumaqq.ui.MainShell;
import edu.tsinghua.lumaqq.ui.config.face.FaceWindow;
import edu.tsinghua.lumaqq.ui.helper.FaceRegistry;

public class ImportEIPJob extends AbstractJob {
    private String fileName, destDir;
    private FaceWindow window;
    private EIPImporter importer;
    private FaceEntry entry;
    private FaceGroup defaultGroup;
    
    public ImportEIPJob(FaceWindow window, String fileName, String destDir, FaceGroup g) {
    	this.window = window;
    	this.fileName = fileName;
    	this.destDir = destDir;
    	this.defaultGroup = g;
    }
    
    @Override
    public void prepare(MainShell m) {
    	super.prepare(m);
    	importer = new EIPImporter(fileName, destDir);
    }
    
	/* (non-Javadoc)
	 * @see edu.tsinghua.lumaqq.ui.jobs.IJob#clear()
	 */
	@Override
	public void clear() {
        importer.dispose();
	}

	/* (non-Javadoc)
	 * @see edu.tsinghua.lumaqq.ui.jobs.IJob#isSuccess()
	 */
	@Override
	public boolean isSuccess() {
		return true;
	}
	
	@Override
	protected void preLoop() {
		monitor.beginTask("", 100);
		monitor.subTask(job_import_eip);		

		FaceRegistry util = FaceRegistry.getInstance();
		for(entry = importer.getNextEntry(); entry != null; entry = importer.getNextEntry()) {
		    monitor.worked(1);
		    
		    // 如果这个表情已经存在，跳过
		    if(util.hasFace(entry.md5))
		        continue;            
		    FaceGroup g;
		    if(entry.groupName.equals("."))
		    	g = defaultGroup;
		    else {
		    	// 如果不存在这个组，则添加一个组
		    	if(!util.hasGroup(entry.groupName)) {
		    		window.getShell().getDisplay().syncExec(new Runnable() {
		    			public void run() {
		    				window.addGroup(entry.groupName);            			
		    			}
		    		});
		    	}
		    	g = util.getFaceGroup(entry.groupName);            	
		    }
		    // 如果保存这个表情失败，跳过
		    if(!importer.saveEntry(g))
		        continue;
		    // 添加表情
		    util.addFace(entry, g);            
		}   
        
		monitor.done();
		finished = true;
	}
}
