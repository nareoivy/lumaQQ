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

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import edu.tsinghua.lumaqq.ui.MainShell;

/**
 * 执行器抽象类
 *
 * @author luma
 */
public abstract class AbstractExecutor implements IExecutor {
    protected Queue<IJob> jobs;
    protected MainShell main;
    protected List<IExecutorListener> listeners;
	protected boolean exitOnEmpty;
    
    public AbstractExecutor(MainShell main) {
    	this.main = main;
    	jobs = new LinkedList<IJob>();
		listeners = new ArrayList<IExecutorListener>();
    }
    
	public void addJob(IJob job) {
        jobs.offer(job);
	}

	public void addExecutorListener(IExecutorListener lis) {
		listeners.add(lis);
	}
	
    /**
     * 得到一个Job链的所有job个数
     * 
     * @param job
     * 	 	链头部Job
     * @return
     * 		job个数
     */
    private int getJobCount(IJob job) {
        int i = 0;
        if(job.getSuccessLink() != null)
            i += getJobCount(job.getSuccessLink()) + 1;
        if(job.getFailLink() != null)
            i += getJobCount(job.getFailLink()) + 1;
        return i;
    }
    
    /**
     * @return
     * 		所有Job的个数
     */
    public int getAllJobCount() {
        int total = 0;
		for(IJob job : jobs) 
            total += getJobCount(job) + 1;
        return total;
    }

	/* (non-Javadoc)
	 * @see edu.tsinghua.lumaqq.ui.jobs.IExecutor#setExitOnEmpty(boolean)
	 */
	public void setExitOnEmpty(boolean exitOnEmpty) {
		this.exitOnEmpty = exitOnEmpty;
	}
}
