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

import java.lang.reflect.InvocationTargetException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;

import edu.tsinghua.lumaqq.ui.MainShell;
import edu.tsinghua.lumaqq.ui.helper.BusyFlag;

/**
 * 用来执行一系列的长时间任务，缺省打开一个模态对话框，用户在任务执行期间不能执行其他操作，
 * 可以通过设置modeless属性显示一个非模态对话框，在非模态下，用户可以进行其他操作，但是
 * 依然不能进行其他长时间任务
 * 
 * @author luma
 */
public class DialogJobExecutor extends AbstractExecutor {
    /**
     * 执行所有任务
     * 
     * @author luma
     */
    private class JobSequencer implements IRunnableWithProgress {
        private IProgressMonitor monitor;
        
        /* (non-Javadoc)
         * @see org.eclipse.jface.operation.IRunnableWithProgress#run(org.eclipse.core.runtime.IProgressMonitor)
         */
        public void run(IProgressMonitor m)
                throws InvocationTargetException, InterruptedException {
            this.monitor = m;
            monitor.beginTask(taskName, getAllJobCount() * 100);
			for(IJob job : jobs)
                runJob(job);
            monitor.done();
        }
        
        /**
         * 运行一个任务
         * 
         * @param job
         * @throws InvocationTargetException
         * @throws InterruptedException
         */
        private void runJob(IJob job) throws InvocationTargetException, InterruptedException {
            job.prepare(main);
            job.run(new SubProgressMonitor(monitor, 100));
            job.clear();
            followLink(job);
        }
        
        /**
         * 执行任务的成功链或失败链
         * 
         * @param job
         * @throws InvocationTargetException
         * @throws InterruptedException
         */
        private void followLink(IJob job) throws InvocationTargetException, InterruptedException {
            if(job.isSuccess()) {
                // follow success link
                if(job.getSuccessLink() != null) {
                	IJob linkJob = job.getSuccessLink();
                	linkJob.setLinkArgument(job.getLinkArgument());
                	runJob(linkJob);
                }
            } else {
                // TODO add error to error list
                if(job.getErrorString() != null) {
                    
                }
                
                // follow fail link
                if(job.getFailLink() != null) {
                	IJob linkJob = job.getFailLink();
                	linkJob.setLinkArgument(job.getLinkArgument());
                	runJob(linkJob);
                }
            }
        }
    }
    
    private String taskName;
    private boolean modeless;
    private boolean cancelable;
    /**
     * 创建一个任务执行器
     * 
     * @param main
     * 		MainShell对象
     */
    public DialogJobExecutor(MainShell main) {
        this(main, "");
    }
    
    /**
     * 创建一个任务执行器
     * 
     * @param main
     * 		MainShell对象
     * @param taskName
     * 		总任务名
     */
    public DialogJobExecutor(MainShell main, String taskName) {
    	super(main);
        this.taskName = taskName;
        modeless = false;
        cancelable = false;
    }
    
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.ui.jobs.IExecutor#execute()
     */
    public void execute() {
        if(!BusyFlag.get()) {
            MessageDialog.openWarning(main.getShell(), message_box_common_warning_title, message_box_job_running);
            return;
        }
        
        main.getDisplay().syncExec(new Runnable() {
        	public void run() {
        		JobProgressMonitorDialog dialog = new JobProgressMonitorDialog(main.getShell(), modeless);
        		try {
        			dialog.run(true, cancelable, new JobSequencer());
        		} catch (Exception e) {
        		} 
        	}
        });
        BusyFlag.release();
        
        for(IExecutorListener lis : listeners)
        	lis.allCompleted();
    }
    
    /**
     * @return Returns the modeless.
     */
    public boolean isModeless() {
        return modeless;
    }
    
    /**
     * @param modeless The modeless to set.
     */
    public void setModeless(boolean modeless) {
        this.modeless = modeless;
    }
    
    /**
     * @return Returns the cancelable.
     */
    public boolean isCancelable() {
        return cancelable;
    }
    
    /**
     * @param cancelable The cancelable to set.
     */
    public void setCancelable(boolean cancelable) {
        this.cancelable = cancelable;
    }
    
    public boolean isBlocked() {
    	return true;
    }
}
