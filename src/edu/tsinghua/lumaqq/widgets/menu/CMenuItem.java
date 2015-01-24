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
package edu.tsinghua.lumaqq.widgets.menu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;


/**
 * 自定义菜单项
 * Style可以是CHECK, RADIO, PUSH, SEPARATOR, CASCADE
 *
 * @author luma
 */
public class CMenuItem {
	private int style;
	private CMenu parent;
	private CMenu child;
	
	private String text;
	private Image image;	
	private boolean selected;
	private boolean enabled;
	private boolean visible;
	
	private List<ISelectionListener> listeners;
	
	private Map<String, Object> datas;
	private static final String DEFAULT_DATA = "default_data";
	
	public CMenuItem(CMenu menu, int style) {
		this(menu, style, menu.getItemCount());
	}
	
	public CMenuItem(CMenu menu, int style, int index) {
		checkStyle(style);
		parent = menu;
		parent.addItem(this, index);
		listeners = new ArrayList<ISelectionListener>();
		enabled = true;
		selected = false;
		visible = true;
	}
	
	public void setMenu(CMenu menu) {
		child = menu;
	}

	private void checkStyle(int style) {
		style &= (SWT.CHECK | SWT.RADIO | SWT.PUSH | SWT.SEPARATOR | SWT.CASCADE);
		if(style == 0)
			style = SWT.PUSH;
		this.style = style;
	}
	
	public void setData(Object data) {
		if(datas == null)
			datas = new HashMap<String, Object>();
		datas.put(DEFAULT_DATA, data);
	}
	
	public void setData(String key, Object data) {
		if(datas == null)
			datas = new HashMap<String, Object>();
		datas.put(key, data);
	}
	
	public Object getData() {
		if(datas == null)
			return null;
		else
			return datas.get(DEFAULT_DATA);
	}
	
	public Object getData(String key) {
		if(datas == null)
			return null;
		else
			return datas.get(key);
	}
	
	void fireSelectionEvent() {
		SelectionEvent e = new SelectionEvent(this);
		for(ISelectionListener sl : listeners)
			sl.widgetSelected(e);
	}

	public void addSelectionListener(ISelectionListener sl) {
		listeners.add(sl);
	}
	
	public void dispose() {
		parent.removeItem(this);
	}
	
	/**
	 * @return Returns the child.
	 */
	public CMenu getChild() {
		return child;
	}

	/**
	 * @param child The child to set.
	 */
	public void setChild(CMenu child) {
		this.child = child;
	}

	/**
	 * @return Returns the image.
	 */
	public Image getImage() {
		return image;
	}

	/**
	 * @param image The image to set.
	 */
	public void setImage(Image image) {
		this.image = image;
	}

	/**
	 * @return Returns the name.
	 */
	public String getText() {
		return text;
	}

	/**
	 * @param name The name to set.
	 */
	public void setText(String name) {
		this.text = name;
	}

	/**
	 * @return Returns the selected.
	 */
	public boolean isSelected() {
		return selected;
	}

	/**
	 * @param selected The selected to set.
	 */
	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	/**
	 * @return Returns the style.
	 */
	public int getStyle() {
		return style;
	}

	/**
	 * @return Returns the enabled.
	 */
	public boolean isEnabled() {
		return enabled;
	}

	/**
	 * @param enabled The enabled to set.
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	/**
	 * @return Returns the visible.
	 */
	public boolean isVisible() {
		return visible;
	}

	/**
	 * @param visible The visible to set.
	 */
	public void setVisible(boolean visible) {
		this.visible = visible;
		parent.setSizeDirty(true);
	}
}
