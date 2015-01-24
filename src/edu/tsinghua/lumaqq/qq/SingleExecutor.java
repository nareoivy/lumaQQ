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
package edu.tsinghua.lumaqq.qq;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * 单一线程执行器，负责包含所有的可模块化任务，减少线程数。目前的设置是不管创建
 * 几个QQClient，都只使用一个线程来进行一些固定任务，也许QQClient太多的时候这样
 * 会有问题
 * 
 * @author luma
 */
public class SingleExecutor {
	private int clientCount;
	private ScheduledExecutorService executor;
	
	public SingleExecutor() {
		clientCount = 0;
	}
	
	public <T> Future<T> submit(Callable<T> callable) {
		if(executor == null)
			executor = Executors.newSingleThreadScheduledExecutor();
		return executor.submit(callable);
	}
	
	public ScheduledFuture scheduleWithFixedDelay(Runnable command, long initialDelay, long delay, TimeUnit unit) {
		if(executor == null)
			executor = Executors.newSingleThreadScheduledExecutor();
		return executor.scheduleWithFixedDelay(command, initialDelay, delay, unit);
	}
	
	public <T> ScheduledFuture<T> schedule(Callable<T> callable, long delay, TimeUnit unit) {
		if(executor == null)
			executor = Executors.newSingleThreadScheduledExecutor();
		return executor.schedule(callable, delay, unit);
	}
	
	/**
	 * 停止执行器线程
	 */
	private void dispose() {
		if(executor != null) {
			executor.shutdownNow();
			executor = null;			
		}
	}
	
	/**
	 * 增加QQClient数目
	 */
	public void increaseClient() {
		clientCount++;
	}
	
	/**
	 * 减少QQ客户端数目，如果数目为0，则释放资源
	 */
	public void decreaseClient() {
		clientCount--;
		if(clientCount <= 0)
			dispose();
	}
}
