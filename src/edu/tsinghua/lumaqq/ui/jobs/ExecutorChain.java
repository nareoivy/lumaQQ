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
import java.util.List;

import edu.tsinghua.lumaqq.ui.MainShell;

/**
 * 任务执行器链，用于串起多个Executor
 *
 * @author luma
 */
public class ExecutorChain extends Thread implements IExecutorListener {
	private List<IExecutor> chain;
	private MainShell main;
	
	public ExecutorChain(MainShell main) {
		chain = new ArrayList<IExecutor>();
		this.main = main;
		setDaemon(true);
		setName("Executor Chain");
	}
	
	public void addExecutor(IExecutor exec) {
		chain.add(exec);
	}
	
	@Override
	public void run() {
		for(IExecutor exec : chain) {
			synchronized(this) {
				exec.addExecutorListener(this);
				if(exec.isBlocked())
					exec.execute();
				else {
					exec.execute();
					try {
						wait();
					} catch(InterruptedException e) {
					}
				}
				
				if(!main.getClient().getUser().isLoggedIn())
					break;				
			}
		}
	}
	
	public void execute() {
		if(!chain.isEmpty())
			start();
	}

	public void allCompleted() {
		synchronized(this) {
			notify();
		}
	}
}
