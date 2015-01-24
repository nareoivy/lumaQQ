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

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;

import edu.tsinghua.lumaqq.events.BaseQQListener;
import edu.tsinghua.lumaqq.ui.MainShell;

/**
 * 抽象任务实现
 * 
 * @author luma
 */
public abstract class AbstractJob extends BaseQQListener implements IJob {
	protected boolean finished;
	protected int myQQ;
    protected IJob fail;
    protected IJob success;
    protected String errorMessage;
    protected MainShell main;
    protected List<IJobListener> listeners;
    protected IProgressMonitor monitor;
    protected int exitCode;
    
	protected static final int SUCCESS = 0;
	protected static final int TIMEOUT = 1;
	protected static final int FAIL = 2;
	
    /**
     * 构造函数
     */
    public AbstractJob() {
    	listeners = new ArrayList<IJobListener>();
    	exitCode = SUCCESS;
    }
    
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.ui.jobs.IJob#addJobListener(edu.tsinghua.lumaqq.ui.jobs.IJobListener)
     */
    public void addJobListener(IJobListener lis) {
    	listeners.add(lis);
    }
    
    /**
     * 触发任务成功事件
     * 
     * @param e
     */
    protected void fireJobSuccessEvent(JobEvent e) {
    	for(IJobListener lis : listeners)
    		lis.jobSuccess(e);
    }
    
    /**
     * 触发任务失败事件
     * 
     * @param e
     */
    protected void fireJobFailEvent(JobEvent e) {
    	for(IJobListener lis : listeners)
    		lis.jobFailed(e);
    }
    
    /* (non-Javadoc)
     * @see org.eclipse.jface.operation.IRunnableWithProgress#run(org.eclipse.core.runtime.IProgressMonitor)
     */
    public void run(IProgressMonitor m) throws InvocationTargetException, InterruptedException {
    	if(!canRun())
    		return;
    	
    	monitor = m;
    	
    	preLoop();
    	loop();
    	postLoop();
    }
    
    /**
     * 在循环前调用
     */
    protected void preLoop() {
    }
    
    /**
     * 在循环结束后调用
     */
    protected void postLoop() {
    }
    
    /**
     * @return
     * 		true表示任务执行的条件已经具备，false表示不具备。
     */
    protected boolean canRun() {
    	return true;
    }
    
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.ui.jobs.IJob#clear()
     */
    public void clear() {
    	if(!listeners.isEmpty()) {
    		JobEvent e = new JobEvent(this, errorMessage);
    		if(isSuccess())
    			fireJobSuccessEvent(e);
    		else
    			fireJobFailEvent(e);
    	}
    }
    
    /**
     * 循环
     */
    protected void loop() {
		synchronized(this) {
			while(!finished && main.getClient().getUser().getQQ() == myQQ && main.getClient().getUser().isLoggedIn()) {
				try {
					this.wait(getInterval());
                    if(monitor != null)
                    	if(monitor.isCanceled())
                    		break;
				} catch (InterruptedException e) {                    
				}	        
				onLoop();
			}				
		}
    }        
	
	/**
	 * 唤醒任务线程，使其返回
	 */
	protected void wake() {
		synchronized(this) {
			finished = true;
		    notify();
		}
	}
	
	/**
	 * 循环时调用
	 */
	protected void onLoop() {
	}

	/**
	 * 循环睡眠时间
	 * 
	 * @return
	 */
	protected long getInterval() {
		return 2000;
	}
    
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.ui.jobs.IJob#prepare(edu.tsinghua.lumaqq.ui.MainShell)
     */
    public void prepare(MainShell m) {
        this.main = m;
        finished = false;
        myQQ = main.getClient().getUser().getQQ();
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.ui.jobs.IJob#getErrorString()
     */
    public String getErrorString() {
        return errorMessage;
    }
    
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.ui.jobs.IJob#getLinkArgument()
     */
    public Object getLinkArgument() {
    	return null;
    }
    
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.ui.jobs.IJob#setLinkArgument(java.lang.Object)
     */
    public void setLinkArgument(Object arg) {
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.ui.jobs.IJob#getSuccessLink()
     */
    public IJob getSuccessLink() {
        return success;
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.ui.jobs.IJob#getFailLink()
     */
    public IJob getFailLink() {
        return fail;
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.ui.jobs.IJob#setSuccessLink(edu.tsinghua.lumaqq.ui.jobs.IJob)
     */
    public void setSuccessLink(IJob job) {
        success = job;
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.ui.jobs.IJob#setFailLink(edu.tsinghua.lumaqq.ui.jobs.IJob)
     */
    public void setFailLink(IJob job) {
        fail = job;
    }
    
    public boolean isSuccess() {
    	return exitCode == SUCCESS;
    }
}
