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

import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static java.lang.Math.min;
import static java.lang.Math.round;
import static java.lang.Math.sin;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;

/**
 * 圆形边界绘制器
 * 
 * @author luma
 */
public class CircleBorderPainter implements IBorderPainter {
	private static Point loc = new Point(0, 0);
	public void draw(GC gc, ISignPainter signPainter, Ring ring) {
		// 初始化变量
		int signCount = ring.getSignCount();		
		double delta = 2 * PI / signCount;
		double angle = 0.0;
		Rectangle bound = ring.getClientArea();
		if(ring.getMaxAvailableHeight() != -1)
			bound.height = min(bound.height, ring.getMaxAvailableHeight());
		Point origin = ring.getOrigin();
		int radius = min(bound.width >> 1, bound.height >> 1) - ((ring.getSignRadius() << 1) - 1);
		int currentSign = ring.getCurrentSign();
		boolean rotating = ring.isRotating();
		boolean showTrack = ring.isShowTrack();
		boolean clockwise = ring.isClockwise();
		int trackSignCount = ring.getTrackSignCount() + 1;
		RGB activeRgb = ring.getActiveSignForeground().getRGB();
		RGB normalRgb = ring.getForeground().getRGB();		
		int redDelta = (normalRgb.red - activeRgb.red) / trackSignCount;
		int greenDelta = (normalRgb.green - activeRgb.green) / trackSignCount;
		int blueDelta = (normalRgb.blue - activeRgb.blue) / trackSignCount;
		trackSignCount--;
		Color temp = null;
		
		// 画所有标志
		for(int i = 0; i < signCount; i++) {
			// 计算标志参考点
			loc.x = (int)round(origin.x + cos(angle) * radius);
			loc.y = (int)round(origin.y - sin(angle) * radius);
			
			/*
			 * 画标志，这里主要要判断标志的位置，然后根据是否正在旋转，是否显示
			 * 旋转轨迹等等，来设置标志的前景色。
			 * 活动标志使用活动标志前景色
			 * 轨迹标志的颜色是渐弱的
			 * 非轨迹标志为普通前景色
			 */
			if(rotating) {
				if(currentSign == i) {
					gc.setBackground(ring.getActiveSignForeground());
					gc.setForeground(ring.getActiveSignForeground());
				} else if(showTrack) {
					int gap = (i > currentSign) ? (i - currentSign) : (i + signCount - currentSign);
					if(!clockwise)
						gap = signCount - gap;
					if(gap <= trackSignCount) {
						temp = new Color(ring.getDisplay(), 
								activeRgb.red + redDelta * gap, 
								activeRgb.green + greenDelta * gap,
								activeRgb.blue + blueDelta * gap);
						gc.setBackground(temp);
						gc.setForeground(temp);
					} else {
						gc.setBackground(ring.getForeground());
						gc.setForeground(ring.getForeground());
					}
				} else {
					gc.setBackground(ring.getForeground());
					gc.setForeground(ring.getForeground());
				}
			} else {
				gc.setBackground(ring.getForeground());				
				gc.setForeground(ring.getForeground());				
			}
			
			signPainter.draw(gc, ring, angle, loc);
			
			// release
			if(temp != null && !temp.isDisposed()) {
				temp.dispose();
				temp = null;
			}
			
			// increase angle
			angle += delta;
		}
	}
}
