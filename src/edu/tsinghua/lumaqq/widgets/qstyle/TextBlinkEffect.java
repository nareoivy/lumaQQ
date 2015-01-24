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

import static java.lang.Math.max;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;

/**
 * 文本闪烁效果
 * 
 * @author luma
 */
public class TextBlinkEffect implements IEffect {
	public static final IEffect INSTANCE = new TextBlinkEffect();
	
	/* (non-Javadoc)
	 * @see edu.tsinghua.lumaqq.widgets.qstyle.IEffect#onPaint(edu.tsinghua.lumaqq.widgets.qstyle.QItem, org.eclipse.swt.graphics.GC, edu.tsinghua.lumaqq.widgets.qstyle.ItemLayout, int, int)
	 */
	public void onPaint(QItem item, GC gc, ItemLayout layout, int y, int frame) {
		switch(layout) {
			case HORIZONTAL:	
				paintHorizontal(item, gc, y, frame);
				break;
			case VERTICAL:
				paintVertical(item, gc, y, frame);
				break;
			default:
				SWT.error(SWT.ERROR_INVALID_RANGE);
				break;
		}	
	}
	
	/**
	 * 画垂直模式
	 * 
	 * @param item
	 * 		item对象
	 * @param gc
	 * 		GC
	 * @param y
	 * 		Item的y坐标
	 * @param frame
	 * 		帧数
	 */
	private void paintVertical(QItem item, GC gc, int y, int frame) {
		QTree parent = item.getParent();
		int level = item.getLevel();
		int size = parent.getLevelImageSize(level);
		int x = parent.getItemIndent(level);
		int itemHeight = parent.getItemHeight(level);
		int itemWidth = parent.getItemWidth(level);
		int imageX = x + (max(0, itemWidth - size) >>> 1);
		int imageY = y + QTree.ITEM_TOP_MARGIN;
		int prefixX = 0;
		int prefixY = 0;
		if(item.getPrefix() != null) {
			Rectangle prefixBound = item.getPrefix().getBounds();
			prefixX = imageX - QTree.PREFIX_ICON_SPACING - prefixBound.width;
			prefixY = imageY + ((size - prefixBound.height) >> 1);
		}
		
		// 画前缀
		if(item.getPrefix() != null) 
			item.paintPrefix(gc, item.getPrefix(), prefixX, prefixY);		
		
		// 画图标
		item.paintIcon(gc, item.getImage(), item.imageBound, imageX, imageY, size);
		
		// 画文本
		if(frame % 2 == 0)
			item.paintVerticalText(gc, x, y, itemWidth, itemHeight, item.getForeground());

		// 画附件
		item.paintVerticalAttachment(gc, x, y, itemWidth, itemHeight, imageX, imageY, size);
	}
	
	/**
	 * 画水平模式
	 * 
	 * @param item
	 * 		item对象
	 * @param gc
	 * 		GC
	 * @param y
	 * 		Item的y坐标
	 * @param frame
	 * 		帧数
	 */
	private void paintHorizontal(QItem item, GC gc, int y, int frame) {
		QTree parent = item.getParent();
		int level = item.getLevel();
		int size = parent.getLevelImageSize(level);
		int x = parent.getItemIndent(level);
		int itemHeight = parent.getItemHeight(level);
		int itemWidth = parent.getItemWidth(level);
		int imageX = x + QTree.ITEM_LEFT_MARING;
		int imageY = y + (max(0, itemHeight - size) >>> 1);
		int prefixX = 0;
		int prefixY = 0;
		if(item.getPrefix() != null) {
			Rectangle prefixBound = item.getPrefix().getBounds();
			prefixX = imageX;
			prefixY = imageY + ((itemHeight - prefixBound.height) >> 1);
			imageX += prefixBound.width + QTree.PREFIX_ICON_SPACING;
		}
		
		// 画前缀
		if(item.getPrefix() != null)
			item.paintPrefix(gc, item.getPrefix(), prefixX, prefixY);
		
		// 偶数帧画图标
		item.paintIcon(gc, item.getImage(), item.imageBound, imageX, imageY, size);
		
		// 画文本
		if(frame % 2 == 0)
			item.paintHorizontalText(gc, x, y, itemWidth, itemHeight, item.getForeground());
		
		// 画附件
		item.paintHorizontalAttachment(gc, x, y, itemWidth, itemHeight, imageX, imageY, size);
	}
}
