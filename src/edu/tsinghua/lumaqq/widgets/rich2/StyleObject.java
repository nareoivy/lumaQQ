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
package edu.tsinghua.lumaqq.widgets.rich2;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Control;

/**
 * 样式所描述的对象类型和相应的包装
 *
 * @author luma
 */
public class StyleObject {
	public static final int IMAGE = 0;
	public static final int CONTROL = 1;
	
	public int objectType;
	public Object object;
	public boolean disposable;
	
	public StyleObject(int ot, Object obj, boolean d) {
		objectType = ot;
		object = obj;
		disposable = d;
	}
	
	public Rectangle getBounds() {
		switch(objectType) {
			case IMAGE:
				return ((Image)object).getBounds();
			case CONTROL:
				return ((Control)object).getBounds();
			default:
				return new Rectangle(0, 0, 0, 0);
		}
	}
	
	public boolean isDisposed() {
		switch(objectType) {
			case IMAGE:
				return ((Image)object).isDisposed();
			case CONTROL:
				return ((Control)object).isDisposed();
			default:
				return false;
		}
	}
	
	public void dispose() {
		if(disposable) {
			switch(objectType) {
				case IMAGE:
					((Image)object).dispose();
					break;
				case CONTROL:
					((Control)object).dispose();
					break;
			}			
		} else {
			hide();			
		}
	}
	
	public void hide() {
		switch(objectType) {
			case CONTROL:
				((Control)object).setVisible(false);
				break;
		}	
	}
	
	public void show() {
		switch(objectType) {
			case CONTROL:
				((Control)object).setVisible(true);
				break;
		}	
	}
}
