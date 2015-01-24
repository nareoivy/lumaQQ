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
package edu.tsinghua.lumaqq.widgets.mac;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;

/**
 * 圆形标志绘制类
 * 
 * @author luma
 */
public class CircleSignPainter implements ISignPainter {
	public void draw(GC gc, Ring ring, double angle, Point absLoc) {
		if(SWT.getPlatform().equals("win32"))
			gc.fillOval(absLoc.x - 1, absLoc.y - 1, ring.getSignRadius() << 1, ring.getSignRadius() << 1);
		else {
			gc.drawLine(absLoc.x, absLoc.y - 1, absLoc.x + 1, absLoc.y - 1);
			gc.drawLine(absLoc.x - 1, absLoc.y, absLoc.x + 2, absLoc.y);
			gc.drawLine(absLoc.x - 1, absLoc.y + 1, absLoc.x + 2, absLoc.y + 1);
			gc.drawLine(absLoc.x, absLoc.y + 2, absLoc.x + 1, absLoc.y + 2);
		}
	}
}
