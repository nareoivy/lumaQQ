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

/**
 * 任务执行器接口
 * 
 * @author luma
 */
public interface IExecutor {
	/**
	 * 添加一个任务
	 * 
	 * @param job
	 */
	public void addJob(IJob job);
	
	/**
	 * 执行 
	 */
	public void execute();
	
	/**
	 * 添加事件监听器
	 * 
	 * @param lis
	 * 		监听器
	 */
	public void addExecutorListener(IExecutorListener lis);
	
	/**
	 * @return
	 * 		所有的任务个数
	 */
	public int getAllJobCount();
	
	/**
	 * @return
	 * 		true表示这个任务执行器的execute方法只会在完成时返回
	 */
	public boolean isBlocked();

	/**
	 * 设置是否在任务队列为空时退出
	 * 
	 * @param exitOnEmpty
	 */
	public void setExitOnEmpty(boolean exitOnEmpty);
}
