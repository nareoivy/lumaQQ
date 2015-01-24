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

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;

import edu.tsinghua.lumaqq.widgets.BaseComposite;

/**
 * 用来显示等级条的组件
 * 
 * @author luma
 */
public class LevelBar extends BaseComposite implements PaintListener {
	private GlyphProducer producer;
	
	public LevelBar(Composite parent) {
		super(parent, SWT.NO_BACKGROUND | SWT.NONE | SWT.DOUBLE_BUFFERED);
		producer = new GlyphProducer();
		addPaintListener(this);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.events.PaintListener#paintControl(org.eclipse.swt.events.PaintEvent)
	 */
	public void paintControl(PaintEvent event) {
		int glyphDeepth = producer.getGlyphDeepth();
		int[] glyphWeights = producer.getGlyphWeights();
		Image[] glyphs = producer.getGlyphs();
		int[] glyphCounts = producer.getGlyphCounts();
		
		if(glyphDeepth == 0)
			return;
		if(glyphWeights == null || glyphWeights.length != glyphDeepth)
			return;
		if(glyphs == null || glyphs.length != glyphDeepth)
			return;
		if(glyphCounts == null)
			return;
		
		Rectangle clientArea = getClientArea();
		if (event.height <= 0)
			return;
		if (clientArea.width == 0 || clientArea.height == 0)
			return;
		
		GC gc = event.gc;		
		calculateGlyphCount();
		gc.fillRectangle(0, 0, clientArea.width, clientArea.height);
		int x = 0, y = 0;		
		Rectangle rect;
		for(int i = 0; i < glyphDeepth; i++) {
			rect = glyphs[i].getBounds();
			y = Math.max(0, (clientArea.height - rect.height) >> 1);
			for(int j = 0; j < glyphCounts[i]; j++) {
				gc.drawImage(glyphs[i], x, y);
				x += rect.width;
			}
		}
	}

	private void calculateGlyphCount() {
		int v = producer.getValue();
		int glyphDeepth = producer.getGlyphDeepth();
		int[] glyphWeights = producer.getGlyphWeights();
		int[] glyphCounts = producer.getGlyphCounts();
		
		for(int i = 0; i < glyphDeepth; i++) {
			glyphCounts[i] = v / glyphWeights[i];
			v %= glyphWeights[i];
		}
	}

	/**
	 * @param glyphDeepth The glyphDeepth to set.
	 */
	public void setGlyphDeepth(int glyphDeepth) {
		producer.setGlyphDeepth(glyphDeepth);
	}

	/**
	 * @param glyphs The glyphs to set.
	 */
	public void setGlyphs(Image[] glyphs) {
		producer.setGlyphs(glyphs);
	}

	/**
	 * @param glyphWeights The glyphWeights to set.
	 */
	public void setGlyphWeights(int[] glyphWeights) {
		producer.setGlyphWeights(glyphWeights);
	}

	/**
	 * @param value The value to set.
	 */
	public void setValue(int value) {
		producer.setValue(value);
		setToolTipText("Level: " + value);
	}
}
