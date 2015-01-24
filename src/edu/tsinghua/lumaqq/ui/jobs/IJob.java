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

import org.eclipse.jface.operation.IRunnableWithProgress;

import edu.tsinghua.lumaqq.ui.MainShell;

/**
 * 任务定义接口
 * 
 * @author luma
 */
public interface IJob extends IRunnableWithProgress {    
    /**
     * 做一些任务执行前的准备工作
     * 
     * @param main
     * 		MainShell对象
     */
    public void prepare(MainShell main);
    
    /**
     * 任务结束后做一些收尾工作
     */
    public void clear();
    
    /**
     * 返回错误信息字符串，错误信息字符串只有在isSuccess返回false的情况下
     * 才有意义。但是isSuccess为false时并一定要有一个错误字符串
     * 
     * @return
     * 		错误字符串，null表示没有错误信息
     */
    public String getErrorString();
    
    /**
     * @return
     * 		此任务成功时执行的任务，null表示没有
     */
    public IJob getSuccessLink();
    
    /**
     * @return
     * 		任务失败时执行的任务，null表示无
     */
    public IJob getFailLink();
    
    /**
     * 设置成功链
     * 
     * @param job
     * 		IJob
     */
    public void setSuccessLink(IJob job);
    
    /**
     * 设置失败链
     * 
     * @param job
     * 		IJob
     */
    public void setFailLink(IJob job);
    
    /**
     * @return
     * 		true表示任务执行成功
     */
    public boolean isSuccess();
    
    /**
     * @return
     * 		如果要按照失败链或者成功链往下执行，则提供给参数给下一个任务
     */
    public Object getLinkArgument();
    
    /**
     * 设置链参数
     * 
     * @param arg
     */
    public void setLinkArgument(Object arg);
    
    /**
     * 添加任务事件监听器
     * 
     * @param lis
     */
    public void addJobListener(IJobListener lis);
}
