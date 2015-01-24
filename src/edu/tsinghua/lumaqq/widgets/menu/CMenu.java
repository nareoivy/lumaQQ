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
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

import edu.tsinghua.lumaqq.ui.helper.UITool;

/**
 * 自定义菜单组件 
 *
 * @author luma
 */
public class CMenu {	
	private List<CMenuItem> items;
	private CMenuItem activeItem;
	private CMenu parent;
	private CMenu activeChild;
	
	private Point location;
	private Point size;
	private boolean visible;
	private boolean sizeDirty;
	
	private Shell menuShell;
	private Listener listener;
	
	private int fontHeight;
	
	static final int ITEM_TOP_MARGIN = 1;
	static final int ITEM_BOTTOM_MARGIN = 1;
	static final int ITEM_LEFT_MARGIN = 5;
	static final int ITEM_RIGHT_MARGIN = 5;
	static final int IMAGE_TEXT_SPACING = 5;
	static final int ARROW = 4;
	static final int TEXT_ARROW_SPACING = 10;
	static final int VERTICAL_SPACING = 1;
	static final int TOP_MARGIN = 3;
	static final int BOTTOM_MARGIN = 3;
	static final int LEFT_MARGIN = 1;
	static final int RIGHT_MARGIN = 1;
	static final int SEPARATOR_HEIGHT = 7;
	static final int BORDER = 1;
	
	private int imageSize;
	
	private Color background;
	private Color textColor;
	private Color textHoverColor;
	private Color activeBackground;
	private Color borderColor;
	private Color disabledTextColor;
	
	private static final Color DEFAULT_BACKGROUND = new Color(Display.getCurrent(), 0xE0, 0xEE, 0xFC);
	private static final Color DEFAULT_TEXT_COLOR = new Color(Display.getCurrent(), 0x07, 0x1E, 0x81);
	private static final Color DEFAULT_TEXT_HOVER_COLOR = Display.getCurrent().getSystemColor(SWT.COLOR_WHITE);
	private static final Color DEFAULT_ACTIVE_BACKGROUND = new Color(Display.getCurrent(), 0x2E, 0x91, 0xEC);
	private static final Color DEFAULT_BORDER_COLOR = new Color(Display.getCurrent(), 0x87, 0xA4, 0xC5);
	private static final Color DEFAULT_DISABLED_TEXT_COLOR = Display.getCurrent().getSystemColor(SWT.COLOR_GRAY);
	private static final Color DARK_SEPARATOR = new Color(Display.getCurrent(), 0xAB, 0xC2, 0xDB);
	private static final Color LIGHT_SEPARATOR = new Color(Display.getCurrent(), 0xDF, 0xED, 0xFB);
	
	private Map<String, Object> datas;
	private static final String DEFAULT_DATA = "default_data";
	
	private List<IMenuListener> listeners;
	
	// 是否做双缓冲
	private static final boolean DOUBLE_BUFFERED;
	static {
	    // 如果不是Mac，底层也不是gtk，则做双缓冲
		String platform = SWT.getPlatform();
		DOUBLE_BUFFERED = !"carbon".equals(platform);
	}
	
	public CMenu(CMenuItem item) {
		item.setChild(this);
		items = new ArrayList<CMenuItem>();
		imageSize = 16;
		fontHeight = UITool.calculateDefaultFontHeight(new GC(Display.getDefault()));
		initColors();
	}

	public CMenu() {
		items = new ArrayList<CMenuItem>();
		imageSize = 16;
		fontHeight = UITool.calculateDefaultFontHeight(new GC(Display.getDefault()));
		initColors();
	}
	
	private void initColors() {
		 background = DEFAULT_BACKGROUND;
		 textColor = DEFAULT_TEXT_COLOR;
		 textHoverColor = DEFAULT_TEXT_HOVER_COLOR;
		 activeBackground = DEFAULT_ACTIVE_BACKGROUND;
		 borderColor = DEFAULT_BORDER_COLOR;
		 disabledTextColor = DEFAULT_DISABLED_TEXT_COLOR;
	}
	
	public void addMenuListener(IMenuListener ml) {
		if(listeners == null)
			listeners = new ArrayList<IMenuListener>();
		listeners.add(ml);
	}
	
	void fireMenuShownEvent() {
		if(listeners == null)
			return;
		
		MenuEvent e = new MenuEvent(this);
		for(IMenuListener ml : listeners)
			ml.menuShown(e);
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
	
	void addItem(CMenuItem item) {
		items.add(item);
	}
	
	void addItem(CMenuItem item, int index) {
		items.add(index, item);
	}
	
	/**
	 * 显示菜单
	 */
	void show() {
		fireMenuShownEvent();
		if(menuShell != null && !menuShell.isDisposed()) {	
			// 检查菜单大小是否需要重新计算
			if(sizeDirty)
				computeSize();

			// 调整菜单位置，显示
			adjustShellLocation();
			if(!menuShell.getVisible())
				menuShell.setVisible(true);
		} else {
			menuShell = new Shell(Display.getCurrent(), SWT.NO_TRIM | SWT.ON_TOP | SWT.NO_BACKGROUND | SWT.DOUBLE_BUFFERED);
			listener = new Listener() {
				public void handleEvent(Event e) {
					switch(e.type) {
						case SWT.Paint:
							handlePaint(e);
							break;
						case SWT.MouseMove:
							handleMouseMove(e);
							break;
						case SWT.MouseUp:
							handleMouseUp(e);
							break;
						case SWT.Dispose:
							handleDispose(e);
							break;
					}
				}
			};
			menuShell.addListener(SWT.Paint, listener);
			menuShell.addListener(SWT.MouseMove, listener);
			menuShell.addListener(SWT.Dispose, listener);
			menuShell.addListener(SWT.MouseUp, listener);
			computeSize();
			if(location == null)
				location = new Point(0, 0);
			adjustShellLocation();
			menuShell.setSize(size);
			menuShell.setVisible(true);
			menuShell.redraw();
		}
	}
	
	/**
	 * 调整菜单位置
	 */
	private void adjustShellLocation() {
		Rectangle displayBound = menuShell.getDisplay().getBounds();
		int shellX, shellY;
		int rightExceed = location.x + size.x - displayBound.width;
		int leftExceed = - (location.x - size.x);
		int topExceed = - (location.y - size.y);
		int bottomExceed = location.y + size.y - displayBound.height;
		if(rightExceed <= 0 || rightExceed <= leftExceed)
			shellX = location.x;
		else
			shellX = -leftExceed;
		if(bottomExceed <= 0 || bottomExceed <= topExceed)
			shellY = location.y;
		else
			shellY = -topExceed;
		menuShell.setLocation(shellX, shellY);
	}

	/**
	 * 隐藏菜单
	 */
	void hide() {
		hide(true);
	}
	
	/**
	 * 隐藏菜单，可选是否也隐藏上一级菜单
	 * 
	 * @param hideParent
	 */
	void hide(boolean hideParent) {
		hideSelf();
		if(parent != null && hideParent)
			parent.hide(hideParent);
		if(activeChild != null)
			activeChild.hideSelf();
	}
	
	void hideSelf() {
		visible = false;
		if(menuShell != null && !menuShell.isDisposed()) {
			menuShell.setVisible(false);
			menuShell.dispose();
			menuShell = null;
		}
	}

	/**
	 * @return
	 * 		菜单大小
	 */
	public Point getSize() {
		if(size == null)
			computeSize();
		return size;
	}
	
	public int getItemCount() {
		return items.size();
	}
	
	public CMenuItem[] getItems() {
		return items.toArray(new CMenuItem[getItemCount()]);
	}
	
	public CMenuItem getItem(int index) {
		return items.get(index);
	}
	
	void removeItem(CMenuItem item) {
		items.remove(item);
	}
	
	/**
	 * 菜单的大小
	 */
	protected void computeSize() {
		if(size == null)
			size = new Point(0, 0);
		
		GC gc = new GC(Display.getCurrent());
		
		int maxTextWidth = 0;
		int itemHeight = getItemHeight();
		size.x = LEFT_MARGIN + RIGHT_MARGIN + imageSize + IMAGE_TEXT_SPACING + ITEM_LEFT_MARGIN + ITEM_RIGHT_MARGIN + BORDER + BORDER + ARROW + TEXT_ARROW_SPACING;
		size.y = TOP_MARGIN + BOTTOM_MARGIN + BORDER + BORDER;
		for(CMenuItem item : items) {
			if(!item.isVisible())
				continue;
			if(item.getStyle() == SWT.SEPARATOR)
				size.y += SEPARATOR_HEIGHT + VERTICAL_SPACING;
			else {
				size.y += itemHeight + VERTICAL_SPACING;
				maxTextWidth = Math.max(maxTextWidth, gc.textExtent(item.getText()).x);				
			}
		}
		size.x += maxTextWidth;
		size.y -= VERTICAL_SPACING;
		
		gc.dispose();
		sizeDirty = false;
	}

	protected void handleDispose(Event e) {
		menuShell.removeListener(SWT.Dispose, listener);
	}

	protected void handleMouseUp(Event e) {
		if(activeItem != null) {
			if(activeItem.isEnabled()) {
				switch(activeItem.getStyle()) {
					case SWT.PUSH:
						setVisible(false);
						activeItem.fireSelectionEvent();
						break;
					case SWT.CHECK:
						setVisible(false);
						activeItem.setSelected(!activeItem.isSelected());
						activeItem.fireSelectionEvent();
						break;
					case SWT.RADIO:
						setVisible(false);
						activeItem.setSelected(!activeItem.isSelected());
						unselect(activeItem);
						activeItem.fireSelectionEvent();
						break;
				}				
			}
		} else
			setVisible(false);
	}

	/**
	 * 取消其他radio菜单的选择状态
	 * 
	 * @param except
	 * 		除了这个之外
	 */
	private void unselect(CMenuItem except) {
		int index = items.indexOf(except);
		if(index == -1)
			return;
		int size = getItemCount();
		for(int i = index + 1; i < size; i++) {
			CMenuItem item = getItem(i);
			if(item.getStyle() == SWT.RADIO)
				item.setSelected(false);
			else if(item.getStyle() == SWT.SEPARATOR)
				break;
		}
		
		for(int i = index - 1; i >= 0; i--) {
			CMenuItem item = getItem(i);
			if(item.getStyle() == SWT.RADIO)
				item.setSelected(false);
			else if(item.getStyle() == SWT.SEPARATOR)
				break;
		}
	}

	protected void handleMouseMove(Event e) {
		if(e.x >= BORDER + LEFT_MARGIN && e.x < size.x - BORDER - RIGHT_MARGIN) {
			CMenuItem item = getItemAtY(e.y);
			if(item != activeItem) {
				// 隐藏前面的子菜单
				if(activeChild != null)
					activeChild.hide(false);
				activeChild = null;		
				
				// 设置当前item，重画菜单
				activeItem = item;
				
				// 看看是不是要显示新的子菜单
				if(item != null && item.isEnabled() && item.getStyle() == SWT.CASCADE) {
					CMenu child = activeItem.getChild();
					if(child != null) {
						Point shellLoc = menuShell.getLocation();
						int childX, childY;
						Point childSize = child.getSize();
						Rectangle displayBound = menuShell.getDisplay().getBounds();
						int rightExceed = shellLoc.x + size.x - BORDER - RIGHT_MARGIN + childSize.x - displayBound.width;
						int leftExceed = 0 - (shellLoc.x - childSize.x + BORDER + LEFT_MARGIN);
						if(rightExceed <= 0 || rightExceed <= leftExceed)
							childX = shellLoc.x + size.x - BORDER - RIGHT_MARGIN;
						else
							childX = -leftExceed;
						int itemY = getYAtItem(activeItem);
						int topExceed = 0 - (shellLoc.y + itemY + getItemHeight() - childSize.y);
						int bottomExceed = shellLoc.y + itemY + childSize.y - displayBound.height;
						if(bottomExceed <= 0 || bottomExceed <= topExceed)
							childY = shellLoc.y + itemY;
						else
							childY = -topExceed;
						child.setLocation(childX, childY);
						child.setParent(this);
						activeChild = child;
						child.setVisible(true);
					} 
				} 

				menuShell.redraw();
			}			
		}
	}

	protected void handlePaint(Event e) {
		if(e.width == 0 || e.height == 0)
			return;
		
		int x = BORDER + LEFT_MARGIN;
		int y = Math.max(TOP_MARGIN + BORDER, e.y);
		CMenuItem item = getItemAtY(y);
		if(item == null)
			return;
		y = getYAtItem(item);

		GC gc;
		Image bufferImage = null;
		int startX, startY;
		if(DOUBLE_BUFFERED) {
			bufferImage = new Image(menuShell.getDisplay(), e.width, e.height);
			gc = new GC(bufferImage);
			startX = startY = 0;
			y -= e.y;
			x -= e.x;
		} else {
			gc = e.gc;
			startX = e.x;
			startY = e.y;
		}
		
		// 画背景
		gc.setBackground(background);
		gc.fillRectangle(startX, startY, e.width, e.height);
		// 画边框
		gc.setForeground(borderColor);
		if(e.x == 0)
			gc.drawLine(0, startY, 0, startY + e.height);
		if(e.y == 0)
			gc.drawLine(startX, 0, startX + e.width, 0);
		if(e.x + e.width == size.x)
			gc.drawLine(startX + e.width - 1, startY, startX + e.width - 1, startY + e.height);
		if(e.y + e.height == size.y)
			gc.drawLine(startX, startY + e.height - 1, startX + e.width, startY + e.height - 1);
		
		// 画item
		gc.setForeground(textColor);
		int itemHeight = getItemHeight();
		int itemIndex = items.indexOf(item);
		int itemCount = items.size();
		int pEnd = startY + e.height;
		while(y < pEnd && itemIndex < itemCount) {
			// get item
			item = items.get(itemIndex);
			// 检查可见性
			if(!item.isVisible()) {
				itemIndex++;
				continue;
			}
			// set foreground
			if(item == activeItem) 
				gc.setForeground(textHoverColor);
			if(!item.isEnabled())
				gc.setForeground(disabledTextColor);
			
			// draw item
			if(item.getStyle() == SWT.SEPARATOR) {
				int quater = (size.x - (BORDER << 1) - LEFT_MARGIN - RIGHT_MARGIN - ITEM_LEFT_MARGIN - ITEM_RIGHT_MARGIN) >> 2;	
				int sX = x + ITEM_LEFT_MARGIN;
				int sY = y + ((SEPARATOR_HEIGHT - 1) >> 1);
				gc.setForeground(LIGHT_SEPARATOR);
				gc.setBackground(DARK_SEPARATOR);
				gc.fillGradientRectangle(sX, sY, quater, 1, false);
				
				sX += quater;
				gc.setForeground(DARK_SEPARATOR);
				gc.drawLine(sX, sY, sX + quater + quater, sY);
				
				sX += quater + quater;
				gc.setBackground(LIGHT_SEPARATOR);
				gc.fillGradientRectangle(sX, sY, quater, 1, false);
				y += SEPARATOR_HEIGHT + VERTICAL_SPACING;
			} else {
				// 画背景
				if(item == activeItem) {
					gc.setBackground(activeBackground);
					gc.fillRectangle(x, y, size.x - (BORDER << 1) - LEFT_MARGIN - RIGHT_MARGIN, itemHeight);
				}
				
				if(item.getStyle() == SWT.PUSH || item.getStyle() == SWT.CASCADE) {
					// 画图片
					Image image = item.getImage();
					if(image != null) {
						Rectangle bound = image.getBounds();
						gc.drawImage(image,
								0,
								0,
								bound.width,
								bound.height,
								x + ITEM_LEFT_MARGIN,
								y + ITEM_TOP_MARGIN,
								imageSize,
								imageSize
						);				
					}					
				} else if(item.getStyle() == SWT.CHECK && item.isSelected()) {
					int cX = x + ITEM_LEFT_MARGIN + ((imageSize - 7) >> 1);
					int cY = y + ((itemHeight - 7) >> 1);
					gc.drawLine(cX, cY + 2, cX, cY + 4);
					gc.drawLine(cX + 1, cY + 3, cX + 1, cY + 5);
					gc.drawLine(cX + 2, cY + 4, cX + 2, cY + 6);
					gc.drawLine(cX + 3, cY + 3, cX + 3, cY + 5);
					gc.drawLine(cX + 4, cY + 2, cX + 4, cY + 4);
					gc.drawLine(cX + 5, cY + 1, cX + 5, cY + 3);
					gc.drawLine(cX + 6, cY, cX + 6, cY + 2);
				} else if(item.getStyle() == SWT.RADIO && item.isSelected()) {
					int rX = x + ITEM_LEFT_MARGIN + ((imageSize - 6) >> 1);
					int rY = y + ((itemHeight - 6) >> 1);
					gc.setBackground((item == activeItem) ? textHoverColor : textColor);
					gc.fillRectangle(rX, rY + 1, 6, 4);
					gc.fillRectangle(rX + 1, rY, 4, 6);
				}
				
				// 画文字
				gc.drawText(item.getText(), 
						x + ITEM_LEFT_MARGIN + imageSize + IMAGE_TEXT_SPACING,
						y + ((itemHeight - fontHeight) >> 1),
						true);
				
				if(item.getStyle() == SWT.CASCADE) {
					int arrowX = x + size.x - BORDER - ITEM_RIGHT_MARGIN - ARROW;
					int arrowY = (itemHeight - 7) >> 1;
					gc.drawLine(arrowX, y + arrowY, arrowX, y + arrowY + 6);
					gc.drawLine(arrowX + 1, y + arrowY + 1, arrowX + 1, y + arrowY + 5);
					gc.drawLine(arrowX + 2, y + arrowY + 2, arrowX + 2, y + arrowY + 4);
					gc.drawLine(arrowX + 3, y + arrowY + 3, arrowX + 3, y + arrowY + 3);				
				}
				
				y += itemHeight + VERTICAL_SPACING;
			}			

			itemIndex++;
			gc.setForeground(textColor);
		}
		
		if(DOUBLE_BUFFERED) {
			e.gc.drawImage(bufferImage, e.x, e.y);
			bufferImage.dispose();
			gc.dispose();
		}
	}
	
	CMenuItem getItemAtY(int y) {
		y -= TOP_MARGIN + BORDER;
		if(y < 0)
			return null;
		
		int itemHeight = getItemHeight();
		for(CMenuItem item : items) {		
			if(!item.isVisible())
				continue;
			if(item.getStyle() == SWT.SEPARATOR)
				y -= SEPARATOR_HEIGHT + VERTICAL_SPACING;
			else
				y -= itemHeight + VERTICAL_SPACING;
			
			if(y <= 0)
				return item;
		}
		return null;
	}
	
	int getYAtItem(CMenuItem i) {
		int y = TOP_MARGIN + BORDER;
		int itemHeight = getItemHeight();
		for(CMenuItem item : items) {
			if(!item.isVisible())
				continue;
			if(item == i)
				return y;
			
			if(item.getStyle() == SWT.SEPARATOR)
				y += SEPARATOR_HEIGHT + VERTICAL_SPACING;
			else
				y += itemHeight + VERTICAL_SPACING;
		}
		return -1;
	}

	private int getItemHeight() {
		return Math.max(fontHeight, imageSize) + ITEM_TOP_MARGIN + ITEM_BOTTOM_MARGIN;
	}

	/**
	 * @return Returns the location.
	 */
	public Point getLocation() {
		return location;
	}

	/**
	 * @param location The location to set.
	 */
	public void setLocation(Point location) {
		this.location = location;
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
		if(visible)
			show();
		else
			hide();
	}

	/**
	 * @return Returns the activeBackground.
	 */
	public Color getActiveBackground() {
		return activeBackground;
	}

	/**
	 * @param activeBackground The activeBackground to set.
	 */
	public void setActiveBackground(Color activeBackground) {
		this.activeBackground = activeBackground;
	}

	/**
	 * @return Returns the background.
	 */
	public Color getBackground() {
		return background;
	}

	/**
	 * @param background The background to set.
	 */
	public void setBackground(Color background) {
		this.background = background;
	}

	/**
	 * @return Returns the textColor.
	 */
	public Color getTextColor() {
		return textColor;
	}

	/**
	 * @param textColor The textColor to set.
	 */
	public void setTextColor(Color textColor) {
		this.textColor = textColor;
	}

	/**
	 * @return Returns the textHoverColor.
	 */
	public Color getTextHoverColor() {
		return textHoverColor;
	}

	/**
	 * @param textHoverColor The textHoverColor to set.
	 */
	public void setTextHoverColor(Color textHoverColor) {
		this.textHoverColor = textHoverColor;
	}

	/**
	 * @return Returns the borderColor.
	 */
	public Color getBorderColor() {
		return borderColor;
	}

	/**
	 * @param borderColor The borderColor to set.
	 */
	public void setBorderColor(Color borderColor) {
		this.borderColor = borderColor;
	}
	
	public void setLocation(int x, int y) {
		if(location == null)
			location = new Point(x, y);
		location.x = x;
		location.y = y;
	}

	/**
	 * @return Returns the parent.
	 */
	CMenu getParent() {
		return parent;
	}

	/**
	 * @param parent The parent to set.
	 */
	void setParent(CMenu parent) {
		this.parent = parent;
	}

	/**
	 * @return Returns the sizeDirty.
	 */
	boolean isSizeDirty() {
		return sizeDirty;
	}

	/**
	 * @param sizeDirty The sizeDirty to set.
	 */
	void setSizeDirty(boolean sizeDirty) {
		this.sizeDirty = sizeDirty;
	}

	/**
	 * @return the imageSize
	 */
	public int getImageSize() {
		return imageSize;
	}

	/**
	 * @param imageSize the imageSize to set
	 */
	public void setImageSize(int imageSize) {
		this.imageSize = imageSize;
	}
}
