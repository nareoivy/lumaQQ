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
package edu.tsinghua.lumaqq.ui.helper;


/**
 * Busy flag，保证某些长时间任务一次只能执行一个
 * 
 * @author luma
 */
public class BusyFlag {
	protected static Thread busyflag = null;

	/**
	 * @return
	 * 		true表示得到了忙碌标志，false表示有其他任务在运行
	 */
	public static synchronized boolean get() {
		if (busyflag == null) {
			busyflag = Thread.currentThread();
			return true;
		}
		return false;
	}

	/**
	 * 释放忙碌标志
	 */
	public static synchronized void release () {
		if (busyflag == Thread.currentThread())
			busyflag = null;
	}
}
