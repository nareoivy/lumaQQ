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

import edu.tsinghua.lumaqq.ui.MainShell;

/**
 * 在后台运行一些非关键任务，不需要堵塞用户操作。
 * 
 * @author luma
 */
public class BackgroundJobExecutor extends AbstractExecutor implements Runnable {    
	/**
     * 创建一个任务执行器
     * 
     * @param main
     * 		MainShell对象
     */
    public BackgroundJobExecutor(MainShell main) {
    	super(main);
    	exitOnEmpty = true;
    }
    
    @Override
    public void addJob(IJob job) {
    	synchronized(jobs) {
    		jobs.offer(job);
    		jobs.notify();
    	}
    }
    
    /**
     * 运行所有任务
     */
    public void run() {
    	while(true) {
    		IJob job = null;
    		synchronized(jobs) {
    			job = jobs.poll();
    			if(job == null) {    			
    				if(exitOnEmpty)
    					break;
    				else {
    					try {
    						jobs.wait();
    					} catch(InterruptedException e) {
    					}    					
    				}
    			}
    		}
    		if(job != null)
    			runJob(job);
    	}
		
        for(IExecutorListener lis : listeners)
        	lis.allCompleted();
    }
    
    private void followLink(IJob job) {
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
    
    /**
     * 运行一个任务
     * 
     * @param job
     */
    private void runJob(IJob job) {
        job.prepare(main);
        try {
			job.run(null);
		} catch(Exception e) {
		}
        job.clear();
        followLink(job);
    }

	/* (non-Javadoc)
	 * @see edu.tsinghua.lumaqq.ui.jobs.IExecutor#execute()
	 */
	public void execute() {
		Thread t = new Thread(this);
		t.setName("Background Jobs");
		t.setDaemon(true);
		t.start();
	}
	
	public boolean isBlocked() {
		return false;
	}
}
