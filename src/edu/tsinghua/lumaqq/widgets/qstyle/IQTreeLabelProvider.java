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

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;

/**
 * QTree的label provider
 * 
 * @author luma
 */
public interface IQTreeLabelProvider<E> {
	/**
	 * 得到文本
	 * 
	 * @param element
	 * 		model对象
	 * @return
	 * 		文本
	 */
	public String getText(E element);
	
	/**
	 * 得到主图标
	 * 
	 * @param element
	 * 		model对象
	 * @return
	 * 		主图标Image对象
	 */
	public Image getImage(E element);
	
	/**
	 * 得到装饰图标
	 * 
	 * @param element
	 * 		model对象
	 * @return
	 * 		装饰图标Image对象
	 */
	public Image getDecoration(E element);
	
	/**
	 * 得到附件图标
	 * 
	 * @param element
	 * 		model对象
	 * @param index
	 * 		附件索引
	 * @return
	 * 		附件图标对象
	 */
	public Image getAttachment(E element, int index);
	
	/**
	 * 得到前缀图标
	 * 
	 * @param element
	 * 		model对象
	 * @return
	 * 		前缀图标对象
	 */
	public Image getPrefix(E element);
	
	/**
	 * 得到前景色
	 * 
	 * @param element
	 * 		model对象
	 * @return
	 * 		前景色
	 */
	public Color getForeground(E element);
	
	/**
	 * 得到model的展开状态
	 * 
	 * @param element
	 * 		model对象
	 * @return
	 * 		true表示是展开的
	 */
	public boolean isExpaned(E element);
	
	/**
	 * 修改model的展开状态
	 * 
	 * @param element
	 * 		model对象
	 * @param exp
	 * 		true表示展开
	 */
	public void setExpanded(E element, boolean exp);
}
