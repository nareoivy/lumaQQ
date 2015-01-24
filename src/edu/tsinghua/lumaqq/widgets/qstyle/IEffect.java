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
package edu.tsinghua.lumaqq.widgets.qstyle;

import org.eclipse.swt.graphics.GC;

/**
 * 动画特效接口
 * 
 * @author luma
 */
interface IEffect {
	/**
	 * 画item
	 * 
	 * @param item
	 * 		item对象
	 * @param gc
	 * 		GC
	 * @param layout
	 * 		item布局
	 * @param y
	 * 		item的y坐标
	 * @param frame
	 * 		绝对帧数
	 */
	public void onPaint(QItem item, GC gc, ItemLayout layout, int y, int frame);
}
