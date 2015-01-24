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

/**
 * QTree内容提供者
 * 
 * @author luma
 */
public interface IQTreeContentProvider<E> {
	/**
	 * 得到所有的根元素
	 * 
	 * @param inputElement
	 * 		输入对象
	 * @return
	 * 		根元素对象数组
	 */
	public E[] getElements(Object inputElement);
	
	/**
	 * 得到子节点对象
	 * 
	 * @param parent
	 * 		父节点
	 * @return
	 * 		子节点对象数组
	 */
	public E[] getChildren(E parent);
	
	/**
	 * 得到父节点
	 * 
	 * @param child
	 * 		子节点
	 * @return
	 * 		父节点
	 */
	public E getParent(E child);
	
	/**
	 * 检查是否一个节点有子节点
	 * 
	 * @param parent
	 * 		父节点
	 * @return
	 * 		true表示有
	 */
	public boolean hasChildren(E parent);
}
