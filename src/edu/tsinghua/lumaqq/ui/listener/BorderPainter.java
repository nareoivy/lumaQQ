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
package edu.tsinghua.lumaqq.ui.listener;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Control;

/**
 * 边框工具类
 * 
 * @author luma
 */
public class BorderPainter {
    /**
     * 在一个控件周围画指定颜色的边框，控件位于边框的中部，但是如果
     * 控件尺寸大于maxHeight，则简单的在外围画一圈
     * 
     * @param control
     * @param gc
     * @param color
     */
    public static void drawCenterBorder(Control control, GC gc, Color color, int maxHeight) {
		Rectangle rect = control.getBounds();
		int gap = (maxHeight - rect.height) / 2;
		if(gap <= 0) {
		    rect.y--;
		    rect.height++;
		} else {
		    rect.y -= gap + 1;
		    rect.height += gap + gap + 1;
		}
	    rect.x -= 3;
	    rect.width += 5;
		gc.setForeground(color);
		gc.drawRectangle(rect);
    }
    
    /**
     * 在控件的周围画一个边框，边框只比控件大一个象素尺寸
     * 
     * @param control
     * @param gc
     * @param color
     */
    public static void drawAroundBorder(Control control, GC gc, Color color) {
        Rectangle rect = control.getBounds();
        rect.x--;
        rect.y--;
        rect.width++;
        rect.height++;
        gc.setForeground(color);
        gc.drawRectangle(rect);
    }
}
