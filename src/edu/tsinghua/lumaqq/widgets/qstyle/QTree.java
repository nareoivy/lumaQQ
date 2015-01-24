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

import static java.lang.Math.max;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.FontMetrics;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.ScrollBar;
import org.eclipse.swt.widgets.Text;

import edu.tsinghua.lumaqq.widgets.BaseComposite;

/**
 * 实现一个QQ那样的树型控件
 * 
 * @author luma
 */
public class QTree extends BaseComposite {
	// 动画效果的runnable
	private Runnable animateRunnable = new Runnable() {
		public void run() {
			synchronized(animateList) {
				int size = animateList.size();
				for(int i = 0; i < size; i++) 
					updateItem(animateList.get(i));
				
				frame++;
				if(size > 0)
					getDisplay().timerExec(ANIMATION_INTERVAL, this);
			}
		}
	};
	
	static final QItem[] EMPTY_ITEMS = new QItem[0];
	static final int MAX_DEEPTH = 16;
	static final int ITEM_TOP_MARGIN = 3;
	static final int ITEM_BOTTOM_MARGIN = 3;
	static final int ITEM_IMAGE_TEXT_SPACING = 3;
	static final int ITEM_LEFT_MARING = 2;
	static final int ITEM_RIGHT_MARGIN = 2;
	static final int PREFIX_ICON_SPACING = 2; 
	static final char TAB = '\t';
	
    // 汉字声母与GB2312编码对照表
    private static char[][] pyTable = new char[][] {
            {0xB0A1, 0xB0C4, 'a'},
            {0xB0C5, 0xB2C0, 'b'},
            {0xB2C1, 0xB4ED, 'c'},
            {0xB4EE, 0xB6E9, 'd'},
            {0xB6EA, 0xB7A1, 'e'},
            {0xB7A2, 0xB8C0, 'f'},
            {0xB8C1, 0xB9FD, 'g'},
            {0xB9FE, 0xBBF6, 'h'},
            {0xBBF7, 0xBFA5, 'j'},
            {0xBFA6, 0xC0AB, 'k'},
            {0xC0AC, 0xC2E7, 'l'},
            {0xC2E8, 0xC4C2, 'm'},
            {0xC4C3, 0xC5B5, 'n'},
            {0xC5B6, 0xC5BD, 'o'},
            {0xC5BE, 0xC6D9, 'p'},
            {0xC6DA, 0xC8BA, 'q'},
            {0xC8BB, 0xC8F5, 'r'},
            {0xC8F6, 0xCBF9, 's'},
            {0xCBFA, 0xCDD9, 't'},
            {0xCDDA, 0xCEF3, 'w'},
            {0xCEF4, 0xD1B8, 'x'},
            {0xD1B9, 0xD4D0, 'y'},
            {0xD4D1, 0xD7F9, 'z'}
    };
	
	private static final int ANIMATION_INTERVAL = 500;
	
	private QItem[] items;
	private int itemCount;
	private List<QItem> expandCache;
	
	// item的图标文字布局和图标大小数组，按照层次关系保存，顶层的由itemLayouts[0]指定，等等
	private ItemLayout[] itemLayouts;
	private int[] itemImageSizes;
	
	int verticalOffset;
	int fontHeight;
	
	private int clientAreaHeight;
	private int clientAreaWidth;
	private int levelIndent;
	
	private int deferredHeight;
	
    // 事件监听器
    private Listener listener;
    private List<IQTreeListener> qtreeListeners;
	
	private QItem itemUnderMouse;
	private boolean enableIconHover;
	
	private int frame;
	List<QItem> animateList;
	
	private boolean editable;
	private QTreeEditor editor;
	
	// 快速查找结果缓冲
	private List<QItem> findResult;
	private int nextResult;
	// 上一次快速查找所用的字符
	private char lastChar;
	
	// 用什么键打开/收起item
	private int itemOpenOnButton;
	private boolean expandItemOnSingleClick;
	
	// 按键处理的映射表，key是按键值，value是动作的id
	private Map<Integer, Integer> keyActionMap;	
	// 用户自定义按键的映射表，key是按键值，vlaue是Runnable对象
	private Map<Integer, Runnable> userActionMap;
	
	/** 用户自定义按键 */
	public static final int CUSTOM_ACTION = 1;
	/** 下翻页 */
	public static final int PAGE_DOWN = 2;
	/** 上翻页 */
	public static final int PAGE_UP = 3;
	/** 到最顶 */
	public static final int GO_TOP = 4;
	/** 到最底 */
	public static final int GO_BOTTOM = 5;
	
	// 是否是Mac
	private static final boolean IS_CARBON;
	static {
	    // 如果不是Mac，底层也不是gtk，则做双缓冲
		String platform = SWT.getPlatform();
		IS_CARBON = "carbon".equals(platform);
	}
	
	/**
	 * 创建一个QTree对象
	 * 
	 * @param parent
	 * 		父容器
	 */
	public QTree(Composite parent) {
		super(parent, SWT.V_SCROLL | SWT.DOUBLE_BUFFERED);
		itemCount = 0;
		items = new QItem[16];
		itemLayouts = new ItemLayout[MAX_DEEPTH];
		itemImageSizes = new int[MAX_DEEPTH];
		verticalOffset = 0;
		fontHeight = calculateFontHeight();
		clientAreaHeight = clientAreaWidth = 0;
		deferredHeight = 0;
		enableIconHover = true;
		itemUnderMouse = null;
		animateList = new ArrayList<QItem>();
		findResult = new ArrayList<QItem>();
		lastChar = 0;
		nextResult = 0;
		levelIndent = 16;
		keyActionMap = new HashMap<Integer, Integer>();
		userActionMap = new HashMap<Integer, Runnable>();		
		itemOpenOnButton = SWT.BUTTON1;
		expandItemOnSingleClick = true;
		qtreeListeners = new ArrayList<IQTreeListener>();
		editable = true;
		expandCache = new ArrayList<QItem>();
		
		for(int i = 0; i < MAX_DEEPTH; i++) {
			itemLayouts[i] = ItemLayout.HORIZONTAL;
			itemImageSizes[i] = 16;
		}
		
		installListeners();
		createKeyBindings();
		
		editor = new QTreeEditor(this);
	}
	
    /**
     * 装载事件监听器
     */
    private void installListeners() {
		listener = new Listener() {
			public void handleEvent(Event event) {
				switch (event.type) {
				case SWT.Dispose:
					handleDispose(event);
					break;
				case SWT.KeyDown:
					handleKeyDown(event);
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
				case SWT.MouseMove:
					handleMouseMove(event);
					break;
				case SWT.MouseExit:
					handleMouseExit(event);
					break;
				case SWT.Paint:
					handlePaint(event);
					break;
				case SWT.Resize:
					handleResize(event);
					break;
				case SWT.Traverse:
					handleTraverse(event);
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
		addListener(SWT.KeyDown, listener);
		addListener(SWT.MouseDown, listener);
		addListener(SWT.MouseUp, listener);
		addListener(SWT.MouseDoubleClick, listener);
		addListener(SWT.MouseMove, listener);
		addListener(SWT.MouseExit, listener);
		addListener(SWT.Paint, listener);
		addListener(SWT.Resize, listener);
		addListener(SWT.Traverse, listener);
    }
	
	/**
	 * 处理鼠标按下事件
	 * 
	 * @param event
	 */
	protected void handleMouseDown(Event event) {
		if(editor.getEditor() != null) {
			Text t = (Text)editor.getEditor();
			editor.getItem().setText(t.getText());
		}
		disposeEditor();
		setFocus();
	}
	
	/**
	 * 如果有编辑器打开，关闭它
	 */
	private void disposeEditor() {
		if(editor.getEditor() != null)
			editor.getEditor().dispose();
		editor.setEditor(null);
		editor.setItem(null);
	}

	/**
	 * 处理鼠标移出事件
	 * 
	 * @param event
	 */
	protected void handleMouseExit(Event event) {
		// 恢复旧item的状态
		if(itemUnderMouse != null) {
			itemUnderMouse.clearHitTestFlag();
			itemUnderMouse = null;
		}
	}

	/**
	 * 处理垂直滚动事件
	 * 
	 * @param event
	 */
	protected void handleVerticalScroll(Event event) {
		// 检查是否和现有值一样
		ScrollBar verticalBar = getVerticalBar();
		if(verticalBar == null)
			return;		
		int newOffset = verticalBar.getSelection();
		if(newOffset == verticalOffset)
			return;
		
		setVerticalOffset(newOffset, false);
	}

	/**
	 * 设置垂直位移
	 * 
	 * @param newOffset
	 * 		新的位移值
	 */
	private void setVerticalOffset(int newOffset, boolean adjustVerticalBar) {
		// 设置新位置
		ScrollBar bar = getVerticalBar();
		if(bar == null)
			return;
		if(adjustVerticalBar)
			bar.setSelection(newOffset);
		
		// 校验范围
		if(newOffset > bar.getMaximum() - bar.getThumb())
			newOffset = bar.getMaximum() - bar.getThumb();
		if(newOffset < 0)
			newOffset = 0;
		
		// 滚动		
		int delta = newOffset - verticalOffset;
		int srcY = (delta > 0) ? delta : 0;
		int destY = (delta > 0) ? 0 : -delta;
		delta = Math.abs(delta);
		int copyHeight = clientAreaHeight - delta;
		scroll(0, destY, 0, srcY, clientAreaWidth, copyHeight);
	    int gap = (delta << 1) - clientAreaHeight;
	    if(gap > 0)
	        redraw(0, copyHeight, clientAreaWidth, gap, true);

		verticalOffset = newOffset;
		if(verticalOffset > 0 && !bar.getVisible()) {
			bar.setMaximum(getTotalHeight() + clientAreaHeight - getItemHeight(0));
			bar.setPageIncrement(clientAreaHeight);
			bar.setThumb(clientAreaHeight);
			bar.setIncrement(10);
			bar.setVisible(true);
		} else if(verticalOffset == 0 && bar.getVisible() && getTotalHeight() <= clientAreaHeight) {
			bar.setVisible(false);
		}
	}

	/**
	 * 滚动客户区
	 * 
	 * @param destX
	 * @param destY
	 * @param srcX
	 * @param srcY
	 * @param width
	 * @param height
	 */
	private void scroll(int destX, int destY, int srcX, int srcY, int width, int height) {
		GC gc = new GC(this);
		gc.copyArea(srcX, srcY, width, height, destX, destY);
		gc.dispose();
		
		if(clientAreaHeight - height >= height)
			redraw(srcX, srcY, width, height, true);
		else {
			int h = clientAreaHeight - height;
			int oldDeferredHeight = deferredHeight;
			deferredHeight += h;
			redraw(srcX, (srcY >= destY) ? (height - oldDeferredHeight) : srcY, width, h + oldDeferredHeight, true);
		}
	}

	/**
	 * 处理按钮遍历事件
	 * 
	 * @param event
	 */
	protected void handleTraverse(Event event) {
		switch (event.detail) {
			case SWT.TRAVERSE_PAGE_NEXT:
			case SWT.TRAVERSE_PAGE_PREVIOUS:
				event.doit = false;
				break;
			default:
				event.doit = true;
				break;
		}
	}

	/**
	 * 处理resize事件
	 * 
	 * @param event
	 */
	protected void handleResize(Event event) {		
		Rectangle clientArea = getClientArea();
		int oldHeight = clientAreaHeight;		
		clientAreaHeight = clientArea.height;
		clientAreaWidth = clientArea.width;
		
		if(oldHeight != clientAreaHeight)
			setScrollBar();
	}

	/**
	 * 处理重画事件
	 * 
	 * @param event
	 */
	protected void handlePaint(Event event) {
		// Check if there is work to do
		Rectangle clientArea = getClientArea();
		if (event.height <= 0)
			return;
		if (clientArea.width == 0 || clientArea.height == 0)
			return;
		
		// get gc and other info
		QItem item = getItemAtY(event.y + verticalOffset);
		int paintY = (item == null) ? event.y : (getYAtItem(item) - verticalOffset);
		int endY = event.y + event.height;			
		GC gc = event.gc;
		
		// 如果gtk设置颜色时出现错误，则先用背景色重画所有部分
		if(isGTKColorError()) {
			gc.setBackground(getBackground());
			gc.fillRectangle(0, paintY, clientArea.width, endY - paintY);
		}
		
		// draw item
		while(paintY <= endY && item != null) {
			item.onPaint(gc, paintY, frame);
			paintY += getItemHeight(item.getLevel());
			item = nextItem(item);
		}
		if(!isGTKColorError() && paintY < endY) {
			gc.setBackground(getBackground());
			gc.fillRectangle(0, paintY, clientArea.width, endY - paintY);
		}
		
		deferredHeight = 0;
	}

	/**
	 * 查找下一个item。对于没有expanded的不考虑
	 * 
	 * @param item
	 * 		当前QItem
	 * @return
	 * 		下一个item，如果已经是最后一个，返回null
	 */
	private QItem nextItem(QItem item) {
		if(item.isExpanded() && item.getItemCount() > 0) {
			return item.getItem(0);
		} else {
			QItem next = null;
			while(next == null && item != null) {
				QItem parentItem = item.getParentItem();
				int index = (parentItem == null) ? indexOf(item) : parentItem.indexOf(item);
				int count = (parentItem == null) ? itemCount : parentItem.getItemCount();
				if(index >= count - 1)
					item = parentItem;
				else
					next = (parentItem == null) ? getItem(index + 1) : parentItem.getItem(index + 1);				
			}
			return next;
		}
	}

	/**
	 * 处理鼠标移动事件
	 * 
	 * @param event
	 */
	protected void handleMouseMove(Event event) {
		QItem item = getItem(event.x, event.y);
		if(item != itemUnderMouse) {
			// 恢复旧item的状态
			if(itemUnderMouse != null) {
				itemUnderMouse.clearHitTestFlag();
				updateItem(itemUnderMouse);
			}
			// 刷新当前item
			if(item != null) {
				int relativeX = event.x - getItemIndent(item);
				int relativeY = event.y - getYAtItem(item) + verticalOffset;
				item.mouseTest(relativeX, relativeY);
			}
			itemUnderMouse = item;	
		} else if(itemUnderMouse != null && enableIconHover){
			// 刷新当前item
			int relativeX = event.x - getItemIndent(itemUnderMouse);
			int relativeY = event.y - getYAtItem(itemUnderMouse) + verticalOffset;
			itemUnderMouse.mouseTest(relativeX, relativeY);
			updateItemIfNeeded(itemUnderMouse);
		}
	}

	/**
	 * 如果item需要重画，则重画他，否则不重画
	 * 
	 * @param item
	 */
	private void updateItemIfNeeded(QItem item) {
		if(item.needRedraw) {
			updateItem(item);
			item.needRedraw = false;
		}
	}

	/**
	 * 处理鼠标双击事件
	 * 
	 * @param event
	 */
	protected void handleMouseDoubleClick(Event event) {
		if(!expandItemOnSingleClick) {
			if((event.stateMask & itemOpenOnButton) != 0)
				if(itemUnderMouse != null && (itemUnderMouse.isMouseOnIcon() || itemUnderMouse.isMouseOnText()))
					itemUnderMouse.setExpanded(!itemUnderMouse.isExpanded());
		}
	}

	/**
	 * 处理鼠标松开事件
	 * 
	 * @param event
	 */
	protected void handleMouseUp(Event event) {
		if(expandItemOnSingleClick) {
			if((event.stateMask & itemOpenOnButton) != 0)
				if(itemUnderMouse != null && (itemUnderMouse.isMouseOnIcon() || itemUnderMouse.isMouseOnText()))
					itemUnderMouse.setExpanded(!itemUnderMouse.isExpanded());
		}
	}

	/**
	 * 处理按键事件
	 * 
	 * @param event
	 */
	protected void handleKeyDown(Event event) {
		int action;
		int key;
		if (event.keyCode != 0) {
			// special key pressed (e.g., F1)
		    key = event.keyCode | event.stateMask;
			action = getKeyBinding(key);
		} else {
			// character key pressed
		    key = event.character | event.stateMask;
			action = getKeyBinding(key);
			if (action == SWT.NULL) {
				// see if we have a control character
				if ((event.stateMask & SWT.CTRL) != 0 && (event.character >= 0)
						&& event.character <= 31) {
					// get the character from the CTRL+char sequence, the control
					// key subtracts 64 from the value of the key that it modifies
					key = (event.character + 64) | event.stateMask;
					action = getKeyBinding(key);
				}
			}
		}
		if (action == SWT.NULL) {		    
		    // 如果没有事件和这个按键绑定，则要检查按键的内容，如果不包含一些
		    // 特殊的按键，则当作普通的输入处理，否则忽略掉	
			// 对于普通的输入，tree的动作是快速查找
			boolean ignore = false;
			if (IS_CARBON) {
				// Ignore acclerator key combinations (we do not want to
				// insert a character in the text in this instance). Do not
				// ignore COMMAND+ALT combinations since that key sequence
				// produces characters on the mac.
				ignore = (event.stateMask ^ SWT.COMMAND) == 0
						|| (event.stateMask ^ (SWT.COMMAND | SWT.SHIFT)) == 0;
			} else {
				// Ignore acclerator key combinations (we do not want to
				// insert a character in the text in this instance). Don't
				// ignore CTRL+ALT combinations since that is the Alt Gr
				// key on some keyboards.
				ignore = (event.stateMask ^ SWT.ALT) == 0
						|| (event.stateMask ^ SWT.CTRL) == 0
						|| (event.stateMask ^ (SWT.ALT | SWT.SHIFT)) == 0
						|| (event.stateMask ^ (SWT.CTRL | SWT.SHIFT)) == 0;
			}
			// ignore anything below SPACE except for line delimiter keys and tab.
			// ignore DEL
			if (!ignore && event.character > 31 && 
					event.character != SWT.DEL && 
					event.character != TAB) {
				doFind(event.character);
			}
		} else if(action == CUSTOM_ACTION) {
			Runnable r = getUserKeyAction(key);
		    if(r != null)
		    	r.run();
		} else {
			invokeAction(key, action);
		}
	}

	/**
	 * 执行某个动作
	 * 
	 * @param action
	 * 		动作id
	 */
	public void invokeAction(int action) {
		if(action != CUSTOM_ACTION)
			invokeAction(0, action);
	}
	
	/**
	 * 执行某个动作
	 * 
	 * @param action
	 * 		动作id
	 */
	public void invokeAction(int key, int action) {
		switch(action) {
			case PAGE_DOWN:
				doPageDown();
				break;
			case PAGE_UP:
				doPageUp();
				break;
			case GO_TOP:
				doGoTop();
				break;
			case GO_BOTTOM:
				doGoBottom();
				break;
			case CUSTOM_ACTION:
				Runnable r = getUserKeyAction(key);
			    if(r != null)
			    	r.run();
			    break;
		}
	}

	/**
	 * 滚动到最底
	 */
	private void doGoBottom() {
		ScrollBar bar = getVerticalBar();
		if(bar != null)
			setVerticalOffset(bar.getMaximum(), true);
	}
	
	/**
	 * 到顶部
	 */
	public void goTop() {
		doGoTop();
	}

	/**
	 * 滚动到最顶
	 */
	private void doGoTop() {
		ScrollBar bar = getVerticalBar();
		if(bar != null)
			setVerticalOffset(0, true);
	}

	/**
	 * 上翻页
	 */
	private void doPageUp() {
		ScrollBar bar = getVerticalBar();
		if(bar != null)
			setVerticalOffset(verticalOffset - clientAreaHeight, true);
	}

	/**
	 * 下翻页
	 */
	private void doPageDown() {
		ScrollBar bar = getVerticalBar();
		if(bar != null)
			setVerticalOffset(verticalOffset + clientAreaHeight, true);
	}

	/**
	 * 查找文本是以character开头（或者拼音以这个开头）的item，显示这个item
	 * 
	 * @param character
	 * 		首字符或拼音首字符
	 */
	private void doFind(char character) {
		collectResult(character);
		if(findResult.size() <= 0)
			return;
		
		revealItem(findResult.get(nextResult++), false);
		if(nextResult >= findResult.size())
			nextResult = 0;
	}

	/**
	 * 收集符合首字符的结果，只在已经展开的范围内搜索
	 * 
	 * @param character
	 * 		首字符或者拼音首字符
	 */
	private void collectResult(char character) {
		if(character == lastChar)
			return;
		if(itemCount <= 0)
			return;
		
		lastChar = character;
		findResult.clear();
		internalCollectResult(items[0]);
		nextResult = 0;
	}

	/**
	 * 递归的搜索符合条件的item
	 * 
	 * @param item
	 * 		查找的item对象 
	 */
	private void internalCollectResult(QItem item) {
		// 检查自己
		if(getFirstPy(item.getText()) == lastChar)
			findResult.add(item);
		// 检查子item
		if(item.isExpanded() && item.getItemCount() > 0)
			internalCollectResult(item.getItem(0));
		// 检查兄弟item
		QItem parentItem = item.getParentItem();
		int index = (parentItem == null) ? indexOf(item) : parentItem.indexOf(item);
		int count = (parentItem == null) ? itemCount : parentItem.getItemCount();
		if(index < count - 1)
			internalCollectResult((parentItem == null) ? getItem(index + 1) : parentItem.getItem(index + 1));
	}

	/**
	 * 处理dispose事件
	 * 
	 * @param event
	 */
	protected void handleDispose(Event event) {
		removeListener(SWT.Dispose, listener);		
		
		if(editor != null)
			editor.dispose();
		
		items = null;
		itemLayouts = null;
		itemImageSizes = null;
		qtreeListeners = null;
		keyActionMap = null;
		userActionMap = null;
		findResult = null;		
	}

	/**
	 * 设置某层的图标大小
	 * 
	 * @param level
	 * 		层号
	 * @param size
	 * 		图片尺寸
	 */
	public void setLevelImageSize(int level, int size) {
		checkWidget();
		checkLevel(level);
		itemImageSizes[level] = size;
		redraw();
	}
	
	/**
	 * 得到某层的图标大小
	 * 
	 * @param level
	 * 		层号
	 * @return
	 * 		图标尺寸
	 */
	public int getLevelImageSize(int level) {
		checkWidget();
		checkLevel(level);
		return itemImageSizes[level];
	}
	
	/**
	 * 设置某层的item布局
	 * 
	 * @param level
	 * 		层号
	 * @param layout
	 * 		布局常量
	 */
	public void setLevelLayout(int level, ItemLayout layout) {
		checkWidget();
		checkLevel(level);
		itemLayouts[level] = layout;
		redraw();
	}
	
	/**
	 * 得到某层的item布局
	 * 
	 * @param level
	 * 		层号
	 * @return
	 * 		布局常量
	 */
	public ItemLayout getLevelLayout(int level) {
		checkWidget();
		checkLevel(level);
		return itemLayouts[level];
	}
	
	/**
	 * 搜索根节点，查询一个item的索引
	 * 
	 * @param item
	 * 		要查询索引的item对象
	 * @return
	 * 		item的索引，如果没找到，返回-1
	 */
	public int indexOf(QItem item) {
		checkWidget();		
		for(int i = 0; i < itemCount; i++) {
			if(items[i] == item)
				return i;
		}
		return -1;
	}
	
	/**
	 * 返回某点下的item对象，坐标都是相对于客户区的值
	 * 
	 * @param x
	 * 		x位置
	 * @param y
	 * 		y位置
	 * @return
	 * 		QItem对象，如果坐标下无item，返回null
	 */
	public QItem getItem(int x, int y) {
		checkWidget();
		QItem item = getItemAtY(y + verticalOffset, null);
		
		if(item == null)
			return null;
		else if(x < getItemIndent(item))
			return null;
		else
			return item;
	}
	
	/**
	 * 得到Item的绝对y坐标，假设其父item都是展开的
	 * 
	 * @param item
	 * 		QItem对象
	 * @return
	 * 		绝对y坐标
	 */
	private int getYAtItem(QItem item) {
		QItem parentItem = item.getParentItem();
		int y = getYInParent(item);	
		while(parentItem != null) {
			y += getItemHeight(parentItem.getLevel());
			y += getYInParent(parentItem);
			parentItem = parentItem.getParentItem();
		}
		return y;
	}
	
	/**
	 * 得到Item的相对坐标，假设其父item都是展开的
	 * 
	 * @param item
	 * 		QItem对象
	 * @return
	 * 		相对坐标
	 */
	int getRelativeYAtItem(QItem item) {
		return getYAtItem(item) - verticalOffset;
	}
	
	/**
	 * 得到Item的缩进值
	 * 
	 * @param item
	 * 		QItem对象
	 * @return
	 * 		水平缩进象素值
	 */
	int getItemIndent(QItem item) {
		return item.getLevel() * levelIndent;
	}
	
	/**
	 * 得到Item的缩进值
	 * 
	 * @param level
	 * 		层号
	 * @return
	 * 		水平缩进象素值
	 */
	int getItemIndent(int level) {
		return level * levelIndent;
	}
	
	/**
	 * 得到item在父item中的y坐标，假设父item是展开的
	 * 
	 * @param item
	 * 		QItem对象
	 * @return
	 * 		y坐标
	 */
	private int getYInParent(QItem item) {
		QItem parentItem = item.getParentItem();
		int index = (parentItem == null) ? indexOf(item) : item.getParentItem().indexOf(item);
		int y = 0;
		int h = getItemHeight(item.getLevel());
		for(int i = 0; i < index; i++) {
			y += h;
			
			QItem temp = (parentItem == null) ? getItem(i) : parentItem.getItem(i);
			if(temp.isExpanded())
				y += getItemChildrenHeight(temp);
		}
		return y;
	}

	/**
	 * 得到绝对坐标y下的item对象
	 * 
	 * @param y
	 * 		绝对坐标y
	 * @return
	 * 		QItem对象，没有返回null
	 */
	private QItem getItemAtY(int y) {
		return getItemAtY(y, null);
	}
	
	/**
	 * 在item的子item中查找y偏移位置的子item
	 * 
	 * @param y
	 * 		y坐标，相对于父item来说
	 * @param item
	 * 		父item，null表示从根节点开始查找
	 * @return
	 * 		QItem对象，如果没有，返回null
	 */
	private QItem getItemAtY(int y, QItem item) {
		if(y < 0)
			return null;
		int level = (item == null) ? 0 : (item.getLevel() + 1);
		int count = (item == null) ? itemCount : item.getItemCount();
		int h = getItemHeight(level);
		int i;
		for(i = 0; i < count && y >= 0; i++) {
			y -= h;
			
			// 如果这个child是展开的，则在其子item中查找
			QItem child = (item == null) ? getItem(i) : item.getItem(i);
			if(child.isExpanded()) {
				QItem ret = getItemAtY(y, child);
				if(ret != null)
					return ret;
				y -= getItemChildrenHeight(child);
			}
		}
		
		// 如果i还是小于count，则定位到了一个item
		if(y < 0)
			return (item == null) ? getItem(i - 1) : item.getItem(i - 1);
		else
			return null;
	}

	/**
	 * 得到一个item的所有子item的总高度
	 * 
	 * @param item
	 * 		父item
	 * @return
	 * 		子item总高度
	 */
	private int getItemChildrenHeight(QItem item) {
		int count = item.getItemCount();
		int ret = 0;
		int h = getItemHeight(item.getLevel() + 1);
		for(int i = 0; i < count; i++) {
			ret += h;			
			if(item.getItem(i).isExpanded())
				ret += getItemChildrenHeight(item.getItem(i));
		}
		return ret;
	}
	
	/**
	 * 返回某点下的ite对象
	 * 
	 * @param p
	 * 		相对于客户区的坐标
	 * @return
	 * 		QItem对象，如果坐标下无item，返回null
	 */
	public QItem getItem(Point p) {
		return getItem(p.x, p.y);
	}
	
	/**
	 * 得到第index个子item
	 * 
	 * @param index
	 * 		子item索引 
	 * @return
	 * 		QItem对象
	 */
	public QItem getItem(int index) {
		checkWidget();
		checkIndex(index);
		return items[index];
	}

	/**
	 * 检查index是否符合范围
	 * 
	 * @param index		
	 * 		子item索引
	 */
	private void checkIndex(int index) {
		if(index < 0 || index >= itemCount)
			SWT.error(SWT.ERROR_INVALID_ARGUMENT);
	}		
	
	/**
	 * 得到level级的item高度
	 * 
	 * @param level
	 * 		level，从0开始
	 * @return
	 * 		象素高度
	 */
	public int getItemHeight(int level) {
		checkWidget();
		switch(itemLayouts[level]) {
			case HORIZONTAL:
				return max(fontHeight, itemImageSizes[level]) + ITEM_BOTTOM_MARGIN + ITEM_TOP_MARGIN;
			case VERTICAL:
				return itemImageSizes[level] + fontHeight + ITEM_BOTTOM_MARGIN + ITEM_TOP_MARGIN + ITEM_IMAGE_TEXT_SPACING;
			default:
				SWT.error(SWT.ERROR_INVALID_RANGE);
				return -1;
		}
	}
	
	/**
	 * 得到某层item的宽度
	 * 
	 * @param level
	 * 		层号
	 * @return
	 * 		item的象素宽度
	 */
	public int getItemWidth(int level) {
		checkWidget();
		return clientAreaWidth - getItemIndent(level);
	}
	
	/**
	 * 检查层号参数是否符合要求
	 * 
	 * @param level
	 * 		层号
	 */
	private void checkLevel(int level) {
		if(level < 0 || level >= MAX_DEEPTH)
			SWT.error(SWT.ERROR_INVALID_ARGUMENT);
	}
	
	/**
	 * 扩展item数组大小
	 */
	private void expandItemArray() {
		QItem[] newItems = new QItem[items.length << 1];
		System.arraycopy(items, 0, newItems, 0, itemCount);
		items = newItems;
	}
	
	/**
	 * 向item数组中添加一个item到数组中
	 * 
	 * @param item
	 * 		要添加的QItem对象
	 */
	private void internalAddItem(QItem item) {
		if(itemCount >= items.length)
			expandItemArray();
		
		items[itemCount++] = item;
	}
	
	/**
	 * 从item数组中删除一个item，不校验index的合法性
	 * 
	 * @param index
	 * 		item的index
	 */
	private void internalRemoveItem(int index) {
		System.arraycopy(items, index + 1, items, index, itemCount - index - 1);
		items[--itemCount] = null;
	}
	
	/**
	 * 计算字体高度
	 * 
	 * @return
	 * 		字体高度象素
	 */
	private int calculateFontHeight() {
		GC gc = new GC(this);
		Font originalFont = gc.getFont();
		FontData[] fontDatas = originalFont.getFontData();		
		FontMetrics metrics = gc.getFontMetrics();
		
		int ascent = metrics.getAscent() + metrics.getLeading();
		int descent = metrics.getDescent();
		
		getFontData(fontDatas, SWT.BOLD);
		Font boldFont = new Font(getDisplay(), fontDatas);
		gc.setFont(boldFont);
		metrics = gc.getFontMetrics();
		ascent = max(ascent, metrics.getAscent() + metrics.getLeading());
		descent = max(descent, metrics.getDescent());
		boldFont.dispose();
		
		getFontData(fontDatas, SWT.ITALIC);
		Font italicFont = new Font(getDisplay(), fontDatas);
		gc.setFont(italicFont);
		metrics = gc.getFontMetrics();
		ascent = max(ascent, metrics.getAscent() + metrics.getLeading());
		descent = max(descent, metrics.getDescent());
		italicFont.dispose();
		
		getFontData(fontDatas, SWT.BOLD | SWT.ITALIC);
		Font boldItalicFont = new Font(getDisplay(), fontDatas);
		gc.setFont(boldItalicFont);
		metrics = gc.getFontMetrics();
		ascent = max(ascent, metrics.getAscent() + metrics.getLeading());
		descent = max(descent, metrics.getDescent());
		boldItalicFont.dispose();
		
		gc.setFont(originalFont);
		gc.dispose();
		
		return ascent + descent;
	}
	
	/**
	 * 设置font data为某种样式
	 * 
	 * @param fontDatas
	 * 		FontData数组
	 * @param style
	 * 		样式
	 */
	private void getFontData(FontData[] fontDatas, int style) {
		for(FontData fd : fontDatas)
			fd.setStyle(style);
	}
	
	/**
	 * @return Returns the items.
	 */
	public QItem[] getItems() {
		QItem[] ret = new QItem[itemCount];
		System.arraycopy(items, 0, ret, 0, itemCount);
		return ret;
	}

	/**
	 * @return Returns the itemCount.
	 */
	public int getItemCount() {
		return itemCount;
	}

	/**
	 * 添加一个item，这个item可能是根节点，也可能是子节点
	 * 
	 * @param item
	 * 		QItem对象
	 */
	public void addItem(QItem item) {
		internalAddItem(item);
	}
	
	/**
	 * 删除一个item
	 * 
	 * @param index
	 * 		item的索引
	 */
	public void removeItem(int index) {
		checkIndex(index);
		internalRemoveItem(index);
	}
	
	/**
	 * 删除从index开始的后面所有item
	 * 
	 * @param index
	 */
	void removeItemFrom(int index) {
		checkIndex(index);
		for(int i = index; i < itemCount; i++)
			items[index] = null;
		itemCount = index;
	}

	/**
	 * Item的expanded状态改变之后，重画这个item之下的所有item，并且
	 * 要更新滚动条
	 * 
	 * @param item
	 * 		状态发生变化的item
	 */
	void updateExpanded(QItem item) {
		lastChar = 0;
		redraw();
		setScrollBar();
		
		QTreeEvent e = new QTreeEvent(this, item);
		if(item.isExpanded())
			sendQTreeExpandedEvent(e);
		else
			sendQTreeCollapsedEvent(e);
	}
	
	/**
	 * @return
	 * 		所有的item的高度之和，不考虑没有expanded的item
	 */
	private int getTotalHeight() {
		int h = itemCount * getItemHeight(0);
		for(int i = 0; i < itemCount; i++) {
			if(items[i].isExpanded())
				h += getItemChildrenHeight(items[i]);
		}
		return h;
	}
	
	/**
	 * 调整滚动条参数
	 */
	private void setScrollBar() {
		ScrollBar verticalBar = getVerticalBar();

		if (verticalBar != null) {
			int totalHeight = getTotalHeight();
			verticalBar.setVisible(clientAreaHeight < totalHeight || verticalOffset > 0);
			
			if(verticalBar.getVisible()) {				
				verticalBar.setValues(verticalOffset, 
				        verticalBar.getMinimum(),
				        totalHeight + clientAreaHeight - getItemHeight(0), 
				        clientAreaHeight, 
						10,
						clientAreaHeight); 					
			} else {
				verticalBar.setMaximum(clientAreaHeight);
				verticalBar.setThumb(1);
				setVerticalOffset(0, true);
			}
		}
	}
	
	/**
	 * 重画一个item
	 * 
	 * @param item
	 * 		item对象
	 */
	void updateItem(QItem item) {
		if(!item.isLogicalVisible())
			return;
		
		int y = getRelativeYAtItem(item);
		int itemHeight = getItemHeight(item.getLevel());
		if(y > -itemHeight && y < clientAreaHeight)
			redraw(0, y, clientAreaWidth, getItemHeight(item.getLevel()), true);
	}

	/**
	 * @return Returns the enableIconHover.
	 */
	public boolean isEnableIconHover() {
		return enableIconHover;
	}

	/**
	 * @param enableIconHover The enableIconHover to set.
	 */
	public void setEnableIconHover(boolean enableIconHover) {
		this.enableIconHover = enableIconHover;
	}
	
	/**
	 * 开始一个特效
	 * 
	 * @param item
	 * 		使用特效的item对象
	 * @param type
	 * 		特效类型
	 */
	public void startAnimation(QItem item, Animation type) {
		synchronized(animateList) {
			if(animateList.contains(item))
				return;
			
			animateList.add(item);
			item.effect = type.getEffector();
			item.animating = true;
		}
		getDisplay().timerExec(0, animateRunnable);
	}
	
	/**
	 * 停止item的动画
	 * 
	 * @param item
	 * 		要停止特效的item对象
	 */
	public void stopAnimation(QItem item) {
		synchronized(animateList) {
			animateList.remove(item);
			item.effect = null;
			item.animating = false;
		}
		updateItem(item);
	}
	
	/**
	 * 停止所有动画
	 */
	public void stopAllAnimation() {
		synchronized(animateList) {
			animateList.clear();
		}
		redraw();
	}

	/**
	 * @return Returns the itemUnderMouse.
	 */
	public QItem getItemUnderMouse() {
		return itemUnderMouse;
	}
	
	/**
	 * 添加一个用户自定义按键事件
	 * 
	 * @param key
	 * @param action
	 */
	public void setUserKeyAction(int key, Runnable action) {
	    if(action == null)
	        setKeyBinding(key, SWT.NULL);
	    else
	        setKeyBinding(key, CUSTOM_ACTION);
	    
		int keyValue = key & SWT.KEY_MASK;
		int modifierValue = key & SWT.MODIFIER_MASK;
		char keyChar = (char)keyValue;

		if (Character.isLetter(keyChar)) {
			// 添加key的大写形式
			char ch = Character.toUpperCase(keyChar);
			int newKey = ch | modifierValue;
			if (action == null)
			    userActionMap.remove(newKey);
			else
			    userActionMap.put(newKey, action);
			// 添加key的小写形式
			ch = Character.toLowerCase(keyChar);
			newKey = ch | modifierValue;
			if (action == null)
			    userActionMap.remove(newKey);
			else
			    userActionMap.put(newKey, action);
		} else {
		    // 不是字符形式的按键则直接添加
			if (action == null)
			    userActionMap.remove(key);
			else
			    userActionMap.put(key, action);
		}       
	}
	
	/**
	 * 得到用户自定义动作对象
	 * 
	 * @param key
	 * 		按键值
	 * @return
	 * 		Runnable对象
	 */
	public Runnable getUserKeyAction(int key) {
	    return userActionMap.get(key);
	}
	
	/**
	 * 添加一个按键绑定
	 * <p>
	 * 
	 * @param key
	 *            a key code defined in SWT.java or a character. Optionally ORd
	 *            with a state mask. Preferred state masks are one or more of
	 *            SWT.MOD1, SWT.MOD2, SWT.MOD3, since these masks account for
	 *            modifier platform differences. However, there may be cases
	 *            where using the specific state masks (i.e., SWT.CTRL,
	 *            SWT.SHIFT, SWT.ALT, SWT.COMMAND) makes sense.
	 * @param action
	 * 		动作ID
	 * @exception SWTException
	 *                <ul>
	 *                <li>ERROR_WIDGET_DISPOSED - if the receiver has been
	 *                disposed</li>
	 *                <li>ERROR_THREAD_INVALID_ACCESS - if not called from the
	 *                thread that created the receiver</li>
	 *                </ul>
	 */
	public void setKeyBinding(int key, int action) {
		checkWidget();

		int keyValue = key & SWT.KEY_MASK;
		int modifierValue = key & SWT.MODIFIER_MASK;
		char keyChar = (char)keyValue;

		if (Character.isLetter(keyChar)) {
			// 添加key的大写形式
			char ch = Character.toUpperCase(keyChar);
			int newKey = ch | modifierValue;
			if (action == SWT.NULL)
				keyActionMap.remove(newKey);
			else
				keyActionMap.put(newKey, action);
			// 添加key的小写形式
			ch = Character.toLowerCase(keyChar);
			newKey = ch | modifierValue;
			if (action == SWT.NULL)
				keyActionMap.remove(newKey);
			else
				keyActionMap.put(newKey, action);
		} else {
		    // 不是字符形式的按键则直接添加
			if (action == SWT.NULL)
				keyActionMap.remove(key);
			else
				keyActionMap.put(key, action);
		}
	}
	
	/**
	 * 删除一个按键绑定
	 * 
	 * @param key
	 */
	public void removeKeyBinding(int key) {
	    setKeyBinding(key, SWT.NULL);
	}
	
	/**
	 * 创建一些缺省的按键事件
	 */
	private void createKeyBindings() {
		setKeyBinding(SWT.PAGE_UP, PAGE_UP);
		setKeyBinding(SWT.PAGE_DOWN, PAGE_DOWN);
		setKeyBinding(SWT.HOME, GO_TOP);
		setKeyBinding(SWT.END, GO_BOTTOM);
	}
	
	/**
	 * 删除一个用户自定义按键绑定
	 * 
	 * @param key
	 *            a key code defined in SWT.java or a character. Optionally ORd
	 *            with a state mask. Preferred state masks are one or more of
	 *            SWT.MOD1, SWT.MOD2, SWT.MOD3, since these masks account for
	 *            modifier platform differences. However, there may be cases
	 *            where using the specific state masks (i.e., SWT.CTRL,
	 *            SWT.SHIFT, SWT.ALT, SWT.COMMAND) makes sense.
	 */
	public void removeUserKeyAction(int key) {
	    setUserKeyAction(key, null);
	}
	
    /**
	 * 查找和相关按键绑定的动作id
	 * <p>
	 * 
	 * @param key
	 *            a key code defined in SWT.java or a character. Optionally ORd
	 *            with a state mask. Preferred state masks are one or more of
	 *            SWT.MOD1, SWT.MOD2, SWT.MOD3, since these masks account for
	 *            modifier platform differences. However, there may be cases
	 *            where using the specific state masks (i.e., SWT.CTRL,
	 *            SWT.SHIFT, SWT.ALT, SWT.COMMAND) makes sense.
	 * @return 
	 * 		动作ID，SWT.NULL表示没有动作
	 * @exception SWTException
	 *                <ul>
	 *                <li>ERROR_WIDGET_DISPOSED - if the receiver has been
	 *                disposed</li>
	 *                <li>ERROR_THREAD_INVALID_ACCESS - if not called from the
	 *                thread that created the receiver</li>
	 *                </ul>
	 */
	public int getKeyBinding(int key) {
		checkWidget();
		Integer action = keyActionMap.get(key);
		if (action == null) 
		    return SWT.NULL;
		else
		    return action;
	}
	
	/**
	 * 如果存在这个item，跳到这个item的位置
	 * 
	 * @param item
	 * 		要显示的Item
	 * @param expand
	 * 		true表示如果这个item不可见，则展开其父item
	 */
	public void revealItem(QItem item, boolean expand) {
		// 如果设置了expand标志，确保item可见		
		if(!item.isLogicalVisible()) {
			if(expand)
				expandToItem(item);
			else
				return;
		}
		
		// 滚动到item起始处
		setVerticalOffset(getYAtItem(item), true);
	}

	/**
	 * 一直展开，直到item为止
	 * 
	 * @param item
	 * 		展开到的item
	 */
	public void expandToItem(QItem item) {
		QItem parentItem = item.getParentItem();
		while(parentItem != null) {
			parentItem.setExpanded(true, false);
			parentItem = parentItem.getParentItem();
		}
	}
	
    /**
     * 得到一个中文字符串的第一个汉字的声母，如果参数的第一个字母是英文，则返回英文
     * 
     * @param s
     * 		字符串
     * @return
     * 		第一个汉字的声母，或者英文字符
     */
    static char getFirstPy(String s) {
		if(s == null)
			return 0;
		
        // 去掉两边的空格
        String _s = s.trim();
        if(_s.length() < 1) return 0;
        // 得到第一个字母的编码，如果长度不等于2，那么不是中文字符或者是未知的编码，直接返回
        char c = 0;
        try {
            // TODO 对于GBK，目前还不知道该怎么办
            byte[] b = _s.substring(0, 1).getBytes("GB2312");
            if(b.length != 2)
                return Character.toLowerCase(_s.charAt(0));
            c = (char)((b[0] << 8 | b[1] & 0xFF) & 0xFFFF);
        } catch (UnsupportedEncodingException e) {
            return Character.toLowerCase(_s.charAt(0));
        }
        
        // 在表中查找，如果找到了，就返回声母，如果找不到，直接返回
		for(char[] py : pyTable) {
			if(c >= py[0] && c <= py[1])
				return py[2];
		}
        return Character.toLowerCase(c);
    }

	/**
	 * @return Returns the levelIndent.
	 */
	public int getLevelIndent() {
		return levelIndent;
	}

	/**
	 * @param levelIndent The levelIndent to set.
	 */
	public void setLevelIndent(int levelIndent) {
		this.levelIndent = levelIndent;
	}

	/**
	 * @param itemOpenOnButton The itemOpenOnButton to set.
	 */
	public void setItemOpenOnButton(int itemOpenOnButton) {
		this.itemOpenOnButton = itemOpenOnButton;
	}

	/**
	 * @param itemOpenOnSingleClick The itemOpenOnSingleClick to set.
	 */
	public void setExpandItemOnSingleClick(boolean itemOpenOnSingleClick) {
		this.expandItemOnSingleClick = itemOpenOnSingleClick;
	}
	
	/**
	 * 添加一个QTree事件监听器
	 * 
	 * @param l
	 * 		IQTreeListener
	 */
	public void addQTreeListener(IQTreeListener l) {
		if(!qtreeListeners.contains(l))
			qtreeListeners.add(l);
	}
	
	/**
	 * 删除一个QTree事件监听器
	 * 
	 * @param l
	 * 		IQTreeListener
	 */
	public void removeQTreeListener(IQTreeListener l) {
		qtreeListeners.remove(l);
	}
	
	/**
	 * 发送item展开事件
	 * 
	 * @param e
	 * 		QTreeEvent
	 */
	private void sendQTreeExpandedEvent(QTreeEvent e) {
		for(IQTreeListener l : qtreeListeners)
			l.treeExpanded(e);
	}
	
	/**
	 * 发送item收起事件
	 * 
	 * @param e
	 * 		QTreeEvent
	 */
	private void sendQTreeCollapsedEvent(QTreeEvent e) {
		for(IQTreeListener l : qtreeListeners)
			l.treeCollapsed(e);
	}
	
	/**
	 * 发送item文本改变事件
	 * 
	 * @param e
	 * 		QTreeEvent
	 */
	private void sendQTreeItemTextChangedEvent(QTreeEvent e) {
		for(IQTreeListener l : qtreeListeners)
			l.itemTextChanged(e);
	}

	/**
	 * @return Returns the editable.
	 */
	public boolean isEditable() {
		return editable;
	}

	/**
	 * @param editable The editable to set.
	 */
	public void setEditable(boolean editable) {
		this.editable = editable;
	}
	
	/**
	 * 设置滚动条，然后重画
	 */
	void refresh() {
		setScrollBar();
		redraw();
	}
	
	/**
	 * 编辑某个item的文本
	 * 
	 * @param item
	 * 		QItem
	 */
	public void editItemText(QItem item) {
		if(!editable)
			return;
		if(editor.getItem() == item)
			return;
		
		if(editor.getEditor() != null)
			editor.getEditor().dispose();
		
		Text text = new Text(this, SWT.SINGLE);
		if(item.getText() != null)
			text.setText(item.getText());
		text.selectAll();
		text.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				onEditorKeyPressed(e);
			}
		});
		text.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				onEditorFocusLost(e);
			}
		});		
		editor.setEditor(text, item);
	}
	
	/**
	 * 编辑器失去焦点时调用
	 * 
	 * @param e
	 */
	protected void onEditorFocusLost(FocusEvent e) {
		QTreeEvent event = new QTreeEvent(this, editor.getItem());
		Text text = (Text)e.getSource();
		editor.getItem().setText(text.getText());
		disposeEditor();
		sendQTreeItemTextChangedEvent(event);
	}

	/**
	 * 处理编辑器中的按键事件
	 * 
	 * @param e
	 */
	protected void onEditorKeyPressed(KeyEvent e) {
		Text text = (Text)e.getSource();
		if(e.keyCode == SWT.CR) {
			QTreeEvent event = new QTreeEvent(this, editor.getItem());
			editor.getItem().setText(text.getText());
			disposeEditor();			
			sendQTreeItemTextChangedEvent(event);
		} else if(e.keyCode == SWT.ESC)
			disposeEditor();
	}

	/**
	 * 收起所有根节点
	 */
	public void collapseAll() {
		for(int i = 0; i < itemCount; i++)
			items[i].setExpanded(false, false);
		redraw();
	}
	
	/**
	 * 展开所有根节点
	 */
	public void expandAll() {
		for(int i = 0; i < itemCount; i++)
			items[i].setExpanded(true, false);
		redraw();
	}
	
	/**
	 * 保存当前的根节点展开状态
	 */
	public void saveExpandStatus() {
		expandCache.clear();
		for(int i = 0; i < itemCount; i++) {
			if(items[i].isExpanded())
				expandCache.add(items[i]);
		}
	}
	
	/**
	 * 恢复根节点的张开状态
	 */
	public void restoreExpandStatus() {
		for(QItem item : expandCache)
			item.setExpanded(true, false);
		redraw();
	}
	
	/**
	 * @param item
	 * @return
	 * 		true表示这个item现在有动画效果在显示
	 */
	public boolean hasAnimation(QItem item) {
		return animateList.contains(item);
	}
}
