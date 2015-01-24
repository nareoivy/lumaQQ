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
package edu.tsinghua.lumaqq.widgets.record;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.graphics.TextLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.ScrollBar;

import edu.tsinghua.lumaqq.widgets.BaseComposite;

/**
 * 瀑布组件，垂直显示各项内容，单列
 * 
 * @author luma
 */
public class Waterfall extends BaseComposite {
	// 行高缓冲数组
	private int[] height;
	private int totalHeight;
	
	private Object[] element;
	private String[] textCache;
	private Comparator<Object> sorter;
	private IWaterfallContentProvider contentProvider;
	private IWaterfallLabelProvider labelProvider;
	private Object input;
	private int activeLine;
	
	private int verticalOffset;	
	private Rectangle clientArea;
	
	private Listener listener;
	private List<MouseListener> mouseListeners;
	private List<SelectionListener> selectionListeners;  
	
	// 是否做双缓冲
	private static final boolean DOUBLE_BUFFERED;
	static {
	    // 如果不是Mac，底层也不是gtk，则做双缓冲
		String platform = SWT.getPlatform();
		DOUBLE_BUFFERED = !"carbon".equals(platform);
	}
	
	public Waterfall(Composite parent) {
		super(parent, SWT.NO_BACKGROUND | SWT.NONE | SWT.V_SCROLL);
		initializeVariables();
		installListeners();
	}
	
	/**
	 * 初始化事件监听器
	 */
	private void installListeners() {
		listener = new Listener() {
			public void handleEvent(Event event) {
				switch(event.type) {
					case SWT.Dispose:
						handleDispose(event);
						break;
					case SWT.Resize:
						handleResize(event);
						break;
					case SWT.MouseDown:
						handleMouseDown(event);
						break;
					case SWT.MouseUp:
						handleMouseUp(event);
						break;
					case SWT.MouseDoubleClick:
						handleMouseDoubleClick(event);
						break;
					case SWT.Paint:
						handlePaint(event);
						break;
				}
			}
		};
		ScrollBar verticalBar = getVerticalBar();
		if (verticalBar != null) {
			verticalBar.addListener(SWT.Selection, new Listener() {
				public void handleEvent(Event event) {
					handleVerticalScroll(event);
				}
			});
		}
		addListener(SWT.Dispose, listener);
		addListener(SWT.MouseDown, listener);
		addListener(SWT.MouseUp, listener);
		addListener(SWT.MouseDoubleClick, listener);
		addListener(SWT.Paint, listener);
		addListener(SWT.Resize, listener);
	}

	protected void handleMouseUp(Event event) {
		setFocus();
		fireMouseUpEvent(new MouseEvent(event));
	}

	protected void handlePaint(Event event) {
		if(event.width <= 0 || event.height <= 0)
			return;
		
		GC gc = null;
		Image bufferImage = null;
		int startY, paintY, endY;
		startY = event.y + verticalOffset;
		endY = startY + event.height;
		int line = getLineAtY(startY);	
		startY = getYAtLine(line);
		int paintEndY;
		if(DOUBLE_BUFFERED) {
			bufferImage = new Image(getDisplay(), clientArea.width, event.height);
			gc = new GC(bufferImage);
			paintY = startY - verticalOffset - event.y;
			paintEndY = event.height;
		} else {
			gc = event.gc;
			paintY = startY - verticalOffset;
			paintEndY = paintY + event.height;
		}
		
		Color color = new Color(getDisplay(), 0xC0, 0xE8, 0xFF);
		TextLayout layout = new TextLayout(getDisplay());		
		layout.setFont(getFont());
		while(startY < endY && line < element.length) {
			if(line == activeLine)
				gc.setBackground(color);
			else
				gc.setBackground(getDisplay().getSystemColor(SWT.COLOR_WHITE));
			gc.fillRectangle(0, paintY, clientArea.width, height[line]);
			
			layout.setText(textCache[line]);
			layout.setWidth(Integer.MAX_VALUE);
			labelProvider.installStyle(element[line], layout);
			layout.draw(gc, 0, paintY);
			startY += height[line];
			paintY += height[line];
			line++;
		}
		
		if(startY < endY) {
			gc.setBackground(getDisplay().getSystemColor(SWT.COLOR_WHITE));
			gc.fillRectangle(0, paintY, clientArea.width, paintEndY - paintY);
		}
		layout.dispose();
		color.dispose();
		
		if(DOUBLE_BUFFERED) {
			event.gc.drawImage(bufferImage, 0, event.y);
			gc.dispose();
			bufferImage.dispose();
		}
	}

	protected void handleMouseDoubleClick(Event event) {
		int line = getLineAtY(event.y + verticalOffset);
		if(line >= element.length)
			return;
		
		event.text = textCache[line];
		event.data = element[line];
		fireMouseDoubleClickEvent(new MouseEvent(event));
		fireDefaultSelectionEvent(new SelectionEvent(event));
	}

	protected void handleDispose(Event event) {
		removeListener(SWT.Dispose, listener);
		notifyListeners(SWT.Dispose, event);
		event.type = SWT.None;
	}

	protected void handleMouseDown(Event event) {
		int line = getLineAtY(event.y + verticalOffset);
		if(line >= element.length)
			return;
		
		activeLine = line;
		redraw();
		
		event.text = textCache[line];
		event.data = element[line];
		fireMouseDoubleClickEvent(new MouseEvent(event));
		fireSelectionEvent(new SelectionEvent(event));
	}

	protected void handleVerticalScroll(Event event) {
		setVerticalScrollOffset(getVerticalBar().getSelection(), false);
	}

	protected void handleResize(Event event) {
		clientArea = getClientArea();
		setScrollBar();
		redraw();
	}

	private void initializeVariables() {
		height = new int[50];
		totalHeight = 0;
		verticalOffset = 0;
		clientArea = getClientArea();
		activeLine = -1;
		mouseListeners = new ArrayList<MouseListener>();
		selectionListeners = new ArrayList<SelectionListener>();
	}
	
	public void refresh() {
		activeLine = -1;
		initializeContent();
		setScrollBar();
		redraw();
	}
	
	/**
	 * 调整卷滚条
	 */
	private void setScrollBar() {
		ScrollBar verticalBar = getVerticalBar();

		if (verticalBar != null) {
			// only set the real values if the scroll bar can be used
			// (ie. because the thumb size is less than the scroll maximum)
			// avoids flashing on Motif, fixes 1G7RE1J and 1G5SE92
			boolean largerThanClient = clientArea.height < totalHeight;
			if (largerThanClient) {
				verticalOffset = Math.min(verticalOffset, totalHeight - clientArea.height);
				verticalBar.setValues(verticalOffset, 
				        verticalBar.getMinimum(),
				        totalHeight, 
				        clientArea.height, 
						10,
						clientArea.height); 																		
			} else
				verticalOffset = 0;
			
			verticalBar.setVisible(largerThanClient);
		}
	}
	
	/**
	 * Scrolls the widget vertically.
	 * <p>
	 * 
	 * @param pixelOffset
	 *            the new vertical scroll offset
	 * @param adjustScrollBar
	 *            true= the scroll thumb will be moved to reflect the new scroll
	 *            offset. false = the scroll thumb will not be moved
	 * @return true=the widget was scrolled false=the widget was not scrolled,
	 *         the given offset is not valid.
	 */
	private boolean setVerticalScrollOffset(int pixelOffset, boolean adjustScrollBar) {
		ScrollBar verticalBar = getVerticalBar();
		if(!verticalBar.getVisible())
			return false;

		if (pixelOffset == verticalOffset) {
			return false;
		}
		if (verticalBar != null && adjustScrollBar) {
			verticalBar.setSelection(pixelOffset);
		}
		verticalOffset = pixelOffset;
		redraw();
		return true;
	}

	/**
	 * 初始化内容和缓冲区
	 */
	private void initializeContent() {
		if(contentProvider == null || labelProvider == null)
			return;
		
		element = contentProvider.getElements(input);
		if(sorter != null)
			Arrays.sort(element, sorter);
		
		if(element == null)
			element = new Object[0];
		
		// 分配缓冲区
		if(textCache == null || textCache.length < element.length)
			textCache = new String[element.length];
		while(height.length < element.length)
			expandHeights();
		
		// build cache
		totalHeight = 0;
		if(element.length == 0)
			return;		
		int i = 0;
		TextLayout layout = new TextLayout(getDisplay());
		layout.setWidth(Integer.MAX_VALUE);
		for(Object obj : element) {
			textCache[i] = labelProvider.getText(obj);
			layout.setText(textCache[i]);
			labelProvider.installStyle(obj, layout);
			height[i] = layout.getBounds().height;
			totalHeight += height[i];
			i++;
		}
		layout.dispose();
	}
	
	private void expandHeights() {
		int[] newHeight = new int[height.length << 1];
		System.arraycopy(height, 0, newHeight, 0, height.length);
		height = newHeight;
	}
	
	private int getLineAtY(int y) {
		if(y <= 0)
			return 0;
		int i;
		for(i = 0; i < element.length && y > 0; i++) {
			y -= height[i];
		}
		if(i == 0)
			return 0;
		else
			return i - 1;
	}
	
	private int getYAtLine(int line) {
		int y = 0;
		for(int i = 0; i < line; i++)
			y += height[i];
		return y;
	}

	/**
	 * @return Returns the sorter.
	 */
	public Comparator<Object> getSorter() {
		return sorter;
	}

	/**
	 * @param sorter The sorter to set.
	 */
	public void setSorter(Comparator<Object> sorter) {
		this.sorter = sorter;
	}

	/**
	 * @return Returns the contentProvider.
	 */
	public IWaterfallContentProvider getContentProvider() {
		return contentProvider;
	}

	/**
	 * @param contentProvider The contentProvider to set.
	 */
	public void setContentProvider(IWaterfallContentProvider contentProvider) {
		this.contentProvider = contentProvider;
	}

	/**
	 * @return Returns the labelProvider.
	 */
	public IWaterfallLabelProvider getLabelProvider() {
		return labelProvider;
	}

	/**
	 * @param labelProvider The labelProvider to set.
	 */
	public void setLabelProvider(IWaterfallLabelProvider labelProvider) {
		this.labelProvider = labelProvider;
	}

	/**
	 * @return Returns the input.
	 */
	public Object getInput() {
		return input;
	}

	/**
	 * @param input The input to set.
	 */
	public void setInput(Object input) {
		this.input = input;
		initializeContent();
	}
	
	@Override
	public void addMouseListener(MouseListener ml) {
		mouseListeners.add(ml);
	}
	
	@Override
	public void removeMouseListener(MouseListener ml) {
		mouseListeners.remove(ml);
	}
	
	/**
	 * 添加TableItem选择事件监听器
	 * 
	 * @param listener
	 */
	public void addSelectionListener(SelectionListener listener) {
		selectionListeners.add(listener);
	}
	
	/**
	 * 除去TableItem选择事件监听器
	 * 
	 * @param listener
	 */
	public void removeSelectionListener(SelectionListener listener) {
		selectionListeners.remove(listener);
	}
	
	protected void fireMouseDoubleClickEvent(MouseEvent e) {
		for(MouseListener ml : mouseListeners)
			ml.mouseDoubleClick(e);
	}
	
	protected void fireMouseDownEvent(MouseEvent e) {
		for(MouseListener ml : mouseListeners)
			ml.mouseDown(e);
	}
	
	protected void fireMouseUpEvent(MouseEvent e) {
		for(MouseListener ml : mouseListeners)
			ml.mouseUp(e);
	}	
	
	protected void fireSelectionEvent(SelectionEvent e) {
		for(SelectionListener sl : selectionListeners)
			sl.widgetSelected(e);
	}
	
	protected void fireDefaultSelectionEvent(SelectionEvent e) {
		for(SelectionListener sl : selectionListeners)
			sl.widgetDefaultSelected(e);
	}

	public Object getSelection() {
		if(activeLine == -1)
			return null;
		return element[activeLine];
	}
}
