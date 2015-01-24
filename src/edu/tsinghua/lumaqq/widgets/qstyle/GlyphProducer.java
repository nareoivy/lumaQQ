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

import org.eclipse.swt.graphics.Image;

/**
 * 根据等级，计算图形化表示
 *
 * @author luma
 */
public class GlyphProducer {
	private int glyphDeepth;
	private int[] glyphWeights;
	private Image[] glyphs;
	private int[] glyphCounts;
	private int value;
	
	/**
	 * 计算图形化表示
	 */
	public void calculateGlyphCount() {
		int v = value;
		for(int i = 0; i < glyphDeepth; i++) {
			glyphCounts[i] = v / glyphWeights[i];
			v %= glyphWeights[i];
		}
	}
	
	/**
	 * @return Returns the glyphDeepth.
	 */
	public int getGlyphDeepth() {
		return glyphDeepth;
	}

	/**
	 * @param glyphDeepth The glyphDeepth to set.
	 */
	public void setGlyphDeepth(int glyphDeepth) {
		this.glyphDeepth = glyphDeepth;
		if(glyphCounts == null || glyphCounts.length < glyphDeepth)
			glyphCounts = new int[glyphDeepth];
	}

	/**
	 * @return Returns the glyphs.
	 */
	public Image[] getGlyphs() {
		return glyphs;
	}

	/**
	 * @param glyphs The glyphs to set.
	 */
	public void setGlyphs(Image[] glyphs) {
		this.glyphs = glyphs;
	}

	/**
	 * @return Returns the glyphWeights.
	 */
	public int[] getGlyphWeights() {
		return glyphWeights;
	}

	/**
	 * @param glyphWeights The glyphWeights to set.
	 */
	public void setGlyphWeights(int[] glyphWeights) {
		this.glyphWeights = glyphWeights;
	}

	/**
	 * @return Returns the value.
	 */
	public int getValue() {
		return value;
	}

	/**
	 * @param value The value to set.
	 */
	public void setValue(int value) {
		this.value = value;
	}

	/**
	 * @return the glyphCounts
	 */
	public int[] getGlyphCounts() {
		return glyphCounts;
	}
}
