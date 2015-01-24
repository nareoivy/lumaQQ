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

import static java.lang.Math.*;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.graphics.TextLayout;

import edu.tsinghua.lumaqq.resource.Colors;
import edu.tsinghua.lumaqq.resource.Resources;
import edu.tsinghua.lumaqq.widgets.qstyle.GlyphProducer;

/**
 * 自绘帮助类
 * 
 * @author luma
 */
public class DrawHelper {
	private static GlyphProducer producer;
	
	/**
	 * 画一个图片，如果指定的绘图区域不等于图片，则根据scale标志决定是否缩放，
	 * 如果不缩放，则只画中部的部分
	 * 
	 * @param gc
	 * 		GC
	 * @param image
	 * 		图像
	 * @param x
	 * 		receiver的起始点x
	 * @param y
	 * 		receiver的起始点y
	 * @param w
	 * 		画多宽
	 * @param h
	 * 		画多高
	 * @param scale
	 * 		是否缩放
	 * @param drawBorder
	 * 		是否画边框
	 * @param borderWidth
	 * 		边框宽度
	 * @param borderColor
	 * 		边框颜色
	 */
	public static void drawImage(GC gc,
			Image image, 
			int x,
			int y,
			int w,
			int h,
			boolean scale,
			boolean drawBorder,
			int borderWidth,
			Color borderColor) {		
		// get image
		Rectangle rect = image.getBounds();
		// draw image
		if(scale)
			gc.drawImage(image, 0, 0, rect.width, rect.height, x, y, w, h);
		else {
			int startX = (rect.width - w) >> 1;
			int startY = (rect.height - h) >> 1;
			gc.drawImage(image,
					max(0, startX), 
					max(0, startY),
					min(w, rect.width),
					min(h, rect.height),
					x - min(0, startX),
					y - min(0, startY),
					min(w, rect.width),
					min(h, rect.height));
		}
		
		// draw border
		if(drawBorder) {
			gc.setForeground(borderColor);
			gc.setLineWidth(borderWidth);
			rect.x = x - 1;
			rect.y = y - 1;
			rect.width = w + 1;
			rect.height = h + 1;
			gc.drawRectangle(rect);
		}
	}
	
	/**
	 * 画字符串
	 * 
	 * @param gc
	 * @param text
	 * 		字符串
	 * @param x
	 * 		起始位置x
	 * @param y
	 * 		起始位置y
	 * @param w
	 * 		最大宽度
	 * @param h
	 * 		最大高度，指定-1表示忽略此参数
	 * @param font
	 * 		字体
	 * @param color
	 * 		字体颜色
	 * @return
	 * 		实际文本的边框
	 */
	public static Rectangle drawString(GC gc,
			String text,
			int x,
			int y,
			int w,
			int h,
			Font font,
			Color color) {
		TextLayout layout = new TextLayout(gc.getDevice());
		layout.setText(text);
		layout.setWidth(w);
		layout.setSpacing(2);
		layout.setFont(font);
		gc.setForeground(color);
		if(h != -1)
			gc.setClipping(x, y, w, h);
		layout.draw(gc, x, y);
		Rectangle rect = layout.getBounds();
		rect.x = x;
		rect.y = y;
		layout.dispose();
		if(h != -1)
			gc.setClipping((Rectangle)null);
		return rect;
	}
	
	/**
	 * 画一个水平分隔条
	 * 
	 * @param gc
	 * @param x
	 * @param y
	 * @param w
	 */
	public static void drawSeparator(GC gc,
			int x,
			int y,
			int w) {
		int quarter = w >>> 2;
		gc.setForeground(Colors.GRAY_LIGTHEST);
		gc.setBackground(Colors.GRAY_DARKER);
		gc.fillGradientRectangle(x, y, quarter, 1, false);
		gc.setForeground(Colors.GRAY_DARKER);
		gc.fillRectangle(x + quarter, y, quarter << 1, 1);
		gc.setBackground(Colors.GRAY_LIGTHEST);
		gc.fillGradientRectangle(x + quarter * 3, y, quarter, 1, false);
	}
	
	/**
	 * 画等级条
	 * 
	 * @param gc
	 * @param level
	 * 		等级值
	 * @param x
	 * 		起始x
	 * @param y
	 * 		起始y
	 */
	public static Rectangle drawLevel(GC gc,
			int level,
			int x, 
			int y) {
		Rectangle ret = new Rectangle(x, y, 0, 0);
		if(producer == null) {
			producer = new GlyphProducer();
			producer.setGlyphDeepth(3);
			producer.setGlyphs(new Image[] { 
					Resources.getInstance().getImage(Resources.icoSun),
					Resources.getInstance().getImage(Resources.icoMoon),
					Resources.getInstance().getImage(Resources.icoStar)
			});
			producer.setGlyphWeights(new int[] { 16, 4, 1 });
		}
		producer.setValue(level);
		producer.calculateGlyphCount();
		for(int i = 0; i < producer.getGlyphDeepth(); i++) {
			Rectangle rect = producer.getGlyphs()[i].getBounds();
			for(int j = 0; j < producer.getGlyphCounts()[i]; j++) {
				gc.drawImage(producer.getGlyphs()[i], x, y);
				x += rect.width;
				ret.width += rect.width;
				ret.height = max(ret.height, rect.height);
			}
		}
		return ret;
	}
}
