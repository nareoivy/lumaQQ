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
package edu.tsinghua.lumaqq.widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Composite;

/**
 * 基础Composite类，之所以添加这么一个类是因为做某些人的GTK系统上，设置背景色无效，
 * 虽然这不太可能是我程序的问题（也许是SWT的问题，或者GTK设置的问题），但是为了程序
 * 的完美，所以提供一个容错的基类，处理这样的情况
 * 
 * @author luma
 */
public abstract class BaseComposite extends Composite {
	/*
	 * 一些颜色的handle
	 * 在GTK下运行时，有时候背景颜色，前景颜色设置都无效，这个应该是和GTK的相关设置有一定关系
	 * 这里我提供一个解决办法，以便让有此问题的人也可以正常看到背景和前景色
	 */
	private Color cacheBg;
	private boolean gtkColorError;
	private Color cacheFg;

	public BaseComposite(Composite parent, int style) {
		super(parent, style);
		
		Color oldBg = super.getBackground();
		Color oldFg = super.getForeground();
		
		setBackground(getDisplay().getSystemColor(SWT.COLOR_WHITE));
		setForeground(getDisplay().getSystemColor(SWT.COLOR_BLACK));
		gtkColorError = super.getBackground() != cacheBg;
		if(!gtkColorError)
			gtkColorError = super.getForeground() != cacheFg;
		
		setBackground(oldBg);
		setForeground(oldFg);
	}
	
	@Override
	public void setBackground(Color color) {
		cacheBg = color;
		super.setBackground(color);
	}
	
	@Override
	public Color getBackground() {
		return cacheBg;
	}
	
	@Override
	public Color getForeground() {
		return cacheFg;
	}
	
	@Override
	public void setForeground(Color color) {
		cacheFg = color;
		super.setForeground(color);
	}
	
	/**
	 * @return
	 * 		true表示设置颜色时出错
	 */
	protected boolean isGTKColorError() {
		return gtkColorError;
	}
}
