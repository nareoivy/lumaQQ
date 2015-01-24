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
package edu.tsinghua.lumaqq.widgets.rich;

import static edu.tsinghua.lumaqq.resource.Messages.*;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTError;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Caret;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.ScrollBar;

import edu.tsinghua.lumaqq.widgets.menu.CMenu;
import edu.tsinghua.lumaqq.widgets.menu.CMenuItem;
import edu.tsinghua.lumaqq.widgets.menu.IMenuListener;
import edu.tsinghua.lumaqq.widgets.menu.ISelectionListener;
import edu.tsinghua.lumaqq.widgets.menu.MenuEvent;
import edu.tsinghua.lumaqq.widgets.menu.SelectionEvent;

/**
 * 新的聊天输入框，支持不同大小字体，颜色，显示任意大小图像
 * 
 * @author luma
 */
public class RichBox extends Canvas {
	// 光标
	private Cursor beamCursor;
	// 游标
	private Caret caret;
	// 只读标志
    private boolean readonly;
    // 事件监听器
    private Listener listener;
    // 垂直偏移
	private int verticalScrollOffset;
    // view顶部的行号
	private int topLine; 
	// 上次重画时的最顶行
	private int lastPaintTopLine;
	// content and other tools
	private IWrappedRichContent content;
	private ContentHelper contentHelper;
	// GC和gc引用次数
	private GC gc;
	private int gcReference;
	
	// the client area height. Needed to calculate
	// content width for new
	private int clientAreaHeight;
	// the client area width. Needed during Resize
	// callback to determine
	private int clientAreaWidth; 
	
	// margins
	private int leftMargin;
	private int topMargin;
	private int rightMargin;
	private int bottomMargin;
	
	// 光标的X偏移位置，不包括左边缘，这个变量用来保证光标上下移动时有一个基本的基准点
	private int columnX;
	
	// 选择的起始和结束偏移，不包括结束位置
	private int selectionStart, selectionEnd;
	// 选择的锚点，选择开始的参考点
	private int selectionAnchor;
	// true表示运行用双击选择行
	private boolean doubleClickEnabled;

	// 右键菜单
	private boolean enableContextMenu;
	private CMenu contextMenu;
	
	// true表示禁止动画
	private boolean forbiddenAnimation;
	
	// caret offset，注意要处理图片的情况，过一个图片我们要把offset增加2
	private int caretOffset;
	
	// 自动滚动的方向
	private int autoScrollDirection;
	
	// 通过用这个颜色在图片上画一次，使图片成为被选择状态
	private Color backXorColor;
	
	// 剪贴板
	private Clipboard clipboard;
	
	static final char TAB = '\t';
	
	// 按键处理的映射表，key是按键值，value是动作的id
	private Map<Integer, Integer> keyActionMap;
	
	// 用户自定义按键的映射表，key是按键值，vlaue是Runnable对象
	private Map<Integer, Runnable> userActionMap;
	
	// 按键动作id
	/** 对应于Home */
	public static final int LINE_START = 1;
	/** 对应于End */
	public static final int LINE_END = 2;
	/** 上箭头 */
	public static final int LINE_UP = 3;
	/** 下箭头 */
	public static final int LINE_DOWN = 4;
	/** 左箭头 */
	public static final int COLUMN_LEFT = 5;
	/** 右箭头 */
	public static final int COLUMN_RIGHT = 6;
	/** Ctrl + Home */
	public static final int TEXT_START = 7;
	/** Ctrl + End */
	public static final int TEXT_END = 8;
	/** 上翻页 */
	public static final int PAGE_UP = 9;
	/** 下翻页 */
	public static final int PAGE_DOWN = 10;
	/** 向左选择一个字符 */
	public static final int SELECT_COLUMN_LEFT = 11;
	/** 向右选择一个字符 */
	public static final int SELECT_COLUMN_RIGHT = 12;
	/** 向上选择一行 */
	public static final int SELECT_LINE_UP = 13;
	/** 向下选择一行 */
	public static final int SELECT_LINE_DOWN = 14;
	/** 剪切 */
	public static final int CUT = 15;
	/** 拷贝 */
	public static final int COPY = 16;
	/** 粘贴 */
	public static final int PASTE = 17;
	/** 删除光标前一个字符 */
	public static final int DELETE_PREVIOUS = 18;
	/** 删除光标后一个字符 */
	public static final int DELETE_NEXT = 19;
	/** 全选 */
	public static final int SELECT_ALL = 20;
	/** 用户自定义按键 */
	public static final int CUSTOM_ACTION = 21;
	/** 回车换行 */
	public static final int NEW_LINE = 22;
	/** 选择到行首 */
	public static final int SELECT_COLUMN_START = 23;
	/** 选择到行尾 */
	public static final int SELECT_COLUMN_END = 24;
	
	// 回车换行
	private static final String CRLF = System.getProperty("line.separator");
	
	// 自动滚动的间隔
	private static final int AUTO_SCROLL_INTERVAL = 100;
	
	// 是否是Mac
	private static final boolean IS_CARBON;
	// 是否做双缓冲
	private static final boolean DOUBLE_BUFFERED;
	static {
	    // 如果不是Mac，底层也不是gtk，则做双缓冲
		String platform = SWT.getPlatform();
		IS_CARBON = "carbon".equals(platform);
		DOUBLE_BUFFERED = !IS_CARBON;
	}
	
	/*
	 * 一些颜色的handle
	 * 在GTK下运行时，有时候背景颜色，前景颜色设置都无效，这个应该是和GTK的相关设置有一定关系
	 * 这里我提供一个解决办法，以便让有此问题的人也可以正常看到背景和前景色
	 */
	private Color cacheBg;
	private boolean gtkColorError;
	
	// 自动滚动的任务
	private Runnable autoScrollTimer = new Runnable() {
        public void run() {
			if (autoScrollDirection == SWT.UP) {
				doLineUp();
				getDisplay().timerExec(AUTO_SCROLL_INTERVAL, this);
			} else if (autoScrollDirection == SWT.DOWN) {
				doLineDown();
				getDisplay().timerExec(AUTO_SCROLL_INTERVAL, this);
			}
        }
	};
	
	// 事件类型定义
	static final int VerifyKey = 3005;
	
	private AnimationManager animationManager;
    private ImageResolver imageResolver;
	
    /**
     * @param parent
     * @param style
     */
    public RichBox(Composite parent) {
		super(parent, SWT.V_SCROLL | SWT.NO_REDRAW_RESIZE | SWT.NO_BACKGROUND);
		initialVariables();
		initialControl();
		initialContextMenu();
		installListeners();
		setScrollBars();
		createKeyBindings();
    }
    
	/**
     * 初始化右键菜单
     */
    private void initialContextMenu() {
        contextMenu = new CMenu();
        
        final CMenuItem miCopy = new CMenuItem(contextMenu, SWT.PUSH);
        miCopy.setText(richbox_menu_copy);
        miCopy.addSelectionListener(new ISelectionListener() {
            public void widgetSelected(SelectionEvent e) {
            	if(!isDisposed())
            		copy();
            }
        });
        final CMenuItem miCut = new CMenuItem(contextMenu, SWT.PUSH);
        miCut.setText(richbox_menu_cut);
        miCut.addSelectionListener(new ISelectionListener() {
            public void widgetSelected(SelectionEvent e) {
            	if(!isDisposed())
            		cut();
            }
        });
        final CMenuItem miPaste = new CMenuItem(contextMenu, SWT.PUSH);
        miPaste.setText(richbox_menu_paste);
        miPaste.addSelectionListener(new ISelectionListener() {
            public void widgetSelected(SelectionEvent e) {
            	if(!isDisposed())
            		paste();
            }
        });
        CMenuItem mi = new CMenuItem(contextMenu, SWT.SEPARATOR);
        mi = new CMenuItem(contextMenu, SWT.PUSH);
        mi.setText(richbox_menu_select_all);
        mi.addSelectionListener(new ISelectionListener() {
            public void widgetSelected(SelectionEvent e) {
            	if(!isDisposed())
            		doSelectAll();
            }
        });
        mi = new CMenuItem(contextMenu, SWT.PUSH);
        mi.setText(richbox_menu_clear);
        mi.addSelectionListener(new ISelectionListener() {
            public void widgetSelected(SelectionEvent e) {
            	if(!isDisposed())
            		clear();
            }
        });
        
        contextMenu.addMenuListener(new IMenuListener() {
            public void menuShown(MenuEvent e) {
                miCopy.setEnabled(hasSelection());
                miCut.setEnabled(!readonly & hasSelection());
                miPaste.setEnabled(!readonly);
            }
        });
    }

    /**
	 * Adds a verify key listener. A VerifyKey event is sent by the widget when
	 * a key is pressed. The widget ignores the key press if the listener sets
	 * the doit field of the event to false.
	 * <p>
	 * 
	 * @param l
	 *            the listener
	 * @exception SWTException
	 *                <ul>
	 *                <li>ERROR_WIDGET_DISPOSED - if the receiver has been
	 *                disposed</li>
	 *                <li>ERROR_THREAD_INVALID_ACCESS - if not called from the
	 *                thread that created the receiver</li>
	 *                </ul>
	 * @exception IllegalArgumentException
	 *                <ul>
	 *                <li>ERROR_NULL_ARGUMENT when listener is null</li>
	 *                </ul>
	 */
	public void addVerifyKeyListener(VerifyKeyListener l) {
		checkWidget();
		if (l == null) {
			SWT.error(SWT.ERROR_NULL_ARGUMENT);
		}
		RichBoxListener typedListener = new RichBoxListener(l);
		addListener(VerifyKey, typedListener);
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
		addListener(SWT.Paint, listener);
		addListener(SWT.Resize, listener);
		addListener(SWT.Traverse, listener);
		addDisposeListener(animationManager);
		addDisposeListener(imageResolver);
    }

	/**
     * @param event
     */
    protected void handleVerticalScroll(Event event) {
		setVerticalScrollOffset(getVerticalBar().getSelection(), false);
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

		if (pixelOffset == verticalScrollOffset) {
			return false;
		}
		if (verticalBar != null && adjustScrollBar) {
			verticalBar.setSelection(pixelOffset);
		}
		int h = clientAreaHeight - topMargin - bottomMargin;
		int w = clientAreaWidth - leftMargin - rightMargin;
		int delta = pixelOffset - verticalScrollOffset;
		int srcY = (delta > 0) ? delta : 0;
		int destY = (delta > 0) ? 0 : -delta;
		delta = Math.abs(delta);
		int copyHeight = h - delta;
	    scroll(leftMargin, topMargin + destY, leftMargin, topMargin + srcY, w, copyHeight, true);
	    int gap = (delta << 1) - h;
	    if(gap > 0)
	        redraw(leftMargin, copyHeight + topMargin, w, gap, true);

		verticalScrollOffset = pixelOffset;
		calculateTopLine();
		setCaretLocation();
		
		if(verticalScrollOffset == 0)
		    setScrollBars();
		return true;
	}
	
	/**
	 * 设置光标位置, 这个方法会修改光标X位置
	 */
	private void setCaretLocation() {
		int lineIndex = content.getLineAtOffset(caretOffset);
		int lineOffset = content.getLineStartOffset(lineIndex);
		int offsetInLine = caretOffset - lineOffset;
		int newCaretX = contentHelper.getXAtOffset(getGC(), lineIndex, offsetInLine);
		releaseGC();
		setCaretLocation(newCaretX, lineIndex);
	}
	
    /**
     * 设置光标位置和大小，这个方法会修改光标X位置
     * 
     * @param newCaretX
     * 		光标的X象素位置，不包括左边缘
     * @param lineIndex
     * 		光标所在行号
     */
    private void setCaretLocation(int newCaretX, int lineIndex) {
        int y = contentHelper.getHeightOfLines(getGC(), 0, lineIndex) - verticalScrollOffset + topMargin;
        int h = contentHelper.getLineHeight(getGC(), lineIndex);
        releaseGC();
        releaseGC();
        // 如果光标和上下边缘有所重叠，则调整光标的大小
        if(y < topMargin && topMargin - y < h) {
            h -= topMargin - y;
            y = topMargin;
        } else if(y < clientAreaHeight - bottomMargin && y + h > clientAreaHeight - bottomMargin) {
            h = clientAreaHeight - bottomMargin - y;
        } else if(y < topMargin || y >= clientAreaHeight - bottomMargin && clientAreaHeight != 0) {
            caret.setVisible(false);
            return;
        }
        caret.setVisible(true);
        caret.setLocation(newCaretX + leftMargin , y);
        caret.setSize(1, h);
        columnX = newCaretX;
    }

    /**
     * 计算最顶行索引
     */
    void calculateTopLine() {
        topLine = contentHelper.getLineAtY(getGC(), verticalScrollOffset);
        releaseGC();
    }

    /**
     * @param event
     */
    protected void handleTraverse(Event event) {
		switch (event.detail) {
			case SWT.TRAVERSE_ESCAPE:
			case SWT.TRAVERSE_PAGE_NEXT:
			case SWT.TRAVERSE_PAGE_PREVIOUS:
				event.doit = true;
				break;
			case SWT.TRAVERSE_RETURN:
			case SWT.TRAVERSE_TAB_NEXT:
			case SWT.TRAVERSE_TAB_PREVIOUS:
				if (readonly || (event.stateMask & SWT.MODIFIER_MASK) != 0) {
					event.doit = true;
				}
				break;
		}
    }
    
    /**
     * @return
     * 		被选择的内容
     */
    public String getSelection() {
        return content.getText(selectionStart, selectionEnd);
    }
    
    /**
     * @return
     * 		true表示有选择的区域
     */
    public boolean hasSelection() {
        return selectionEnd != selectionStart;
    }

    /**
     * @param event
     */
    protected void handleResize(Event event) {
		int oldHeight = clientAreaHeight;
		int oldWidth = clientAreaWidth;

		Rectangle clientArea = getClientArea();
		clientAreaHeight = clientArea.height;
		clientAreaWidth = clientArea.width;
		
		// rewrap and redraw
		if(oldWidth != clientAreaWidth) {
		    content.wrap(clientAreaWidth - leftMargin - rightMargin);
		    redraw();
		}
		// refresh scroll bar
		setScrollBars();
		// reset caret
		if (oldHeight != clientAreaHeight)
			calculateTopLine();		
		if(clientAreaHeight > 0 && clientAreaWidth - leftMargin - rightMargin > 0)
		    showCaret();
    }

    /**
     * @param event
     */
    protected void handleMouseMove(Event event) {
		if ((event.stateMask & SWT.BUTTON1) == 0)
			return;
		
		event.y -= topMargin;
		event.x -= leftMargin;
		doMouseLocationChange(event.x, event.y, true);
		doAutoScroll(event);
    }
    
	/**
	 * 如果鼠标出了客户区，自动滚动
	 */
	private void doAutoScroll(Event event) {
		Rectangle area = getClientArea();

		if (event.y > area.height)
			doAutoScroll(SWT.DOWN);
		else if (event.y < 0)
			doAutoScroll(SWT.UP);
		else
			endAutoScroll();
	}
	
	/**
	 * 结束自动滚动
	 */
	private void endAutoScroll() {
		autoScrollDirection = SWT.NULL;
	}

	/**
	 * 开始自动滚动
	 * <p>
	 * 
	 * @param direction
	 *            SWT.UP, SWT.DOWN
	 */
	private void doAutoScroll(int direction) {
		// 方向相同则不用管
		if (autoScrollDirection == direction)
			return;

		// 方向不合法则返回
		if (direction != SWT.UP && direction != SWT.DOWN)     
		    return;
		// 开始滚动
		autoScrollDirection = direction;
		getDisplay().timerExec(AUTO_SCROLL_INTERVAL, autoScrollTimer);
	}

	/**
	 * 往上选择一行
	 */
	private void doLineUp() {
		int oldColumnX;
		int caretLine = content.getLineAtOffset(caretOffset);
		
		// 保存当前光标X偏移
		oldColumnX = columnX;
		
		if (caretLine > 0)
			caretLine = doSimpleLineUp();
		
		// 显示光标
		showCaret(caretLine);
		// save the original horizontal caret position
		columnX = oldColumnX;
	}
	
	/**
	 * 显示光标，这个方法会修改光标X位置
	 */
	private void showCaret() {
		int caretLine = content.getLineAtOffset(caretOffset);
		showCaret(caretLine);		    
	}
	
	/**
	 * 把光标置于caretLine行，并且让光标可见，也就是要让caretLine行可见
	 * 这个方法会修改光标X位置
	 */
	private void showCaret(int caretLine) {
		boolean scrolled = showLine(caretLine);
		if (scrolled == false)
			setCaretLocation();
	}
	
	/**
	 * 光标所在行减1，保持光标水平位置基本不变
	 * 
	 * @return
	 * 		光标所在行号
	 */
	private int doSimpleLineUp() {
		int caretLine = content.getLineAtOffset(caretOffset);
		if (caretLine > 0) {
			caretLine--;
			caretOffset = contentHelper.getOffsetAtX(getGC(), caretLine, columnX);
			releaseGC();
		}
		return caretLine;
	}
	
	/**
	 * 使指定的行处于view中，如果这一行当前不在view中，则需要滚动
	 * <p>
	 * 
	 * @param line
	 * 		需要显示的行的行号
	 * @return 
	 * 		true表示经过了滚动，false表示没有
	 */
	private boolean showLine(int line) {
		boolean scrolled = false;

		GC _gc = getGC();
		if (line <= topLine) {
			scrolled = setVerticalScrollOffset(contentHelper.getHeightOfLines(_gc, 0, line), true);
		} else if (line > getFullVisibleBottomLine(_gc)) {
			scrolled = setVerticalScrollOffset(contentHelper.getHeightOfLines(_gc, 0, line + 1) - clientAreaHeight + topMargin + bottomMargin, true);
		}
		releaseGC();
		return scrolled;
	}

	/**
     * @return
     * 		得到view中的最后一个全部可见行的行号
     */
    private int getFullVisibleBottomLine(GC _gc) {
        int y = clientAreaHeight - topMargin - bottomMargin + verticalScrollOffset;
        int line = contentHelper.getLineAtY(_gc, y);
        if(contentHelper.getHeightOfLines(_gc, 0, line + 1) > y)
            line--;
        return line;
    }
    
    /**
     * @param _gc
     * 		GC
     * @return
     * 		得到view中可见的最后一行的行号，不需要全部可见
     */
    private int getBottomLine(GC _gc) {
        int y = clientAreaHeight - topMargin - bottomMargin + verticalScrollOffset;
        return contentHelper.getLineAtY(_gc, y);
    }

    /**
     * 往下选择一行
	 */
	private void doLineDown() {
		int oldColumnX;
		int caretLine;

		caretLine = content.getLineAtOffset(caretOffset);
		// 保存光标X参照点以便移动后恢复
		oldColumnX = columnX;
		
		if (caretLine < content.getLineCount() - 1)
			caretLine = doSimpleLineDown();

		// 显示光标
		showCaret(caretLine);
		// save the original horizontal caret position
		columnX = oldColumnX;
	}	

	/**
	 * 光标所在行加1，保持光标水平位置基本不变
	 * 
	 * @return index of the new line relative to the first line in the document
	 */
	private int doSimpleLineDown() {
		int caretLine = content.getLineAtOffset(caretOffset);
		if (caretLine < content.getLineCount() - 1) {
			caretLine++;
			caretOffset = contentHelper.getOffsetAtX(getGC(), caretLine, columnX);
			releaseGC();
		}
		return caretLine;
	}
	
    /**
     * @param event
     */
    protected void handleMouseDoubleClick(Event event) {
		if (event.button != 1 || doubleClickEnabled == false)
			return;
		
		// 得到应该被选择的区域
		int caretLine = content.getLineAtOffset(caretOffset);
		int lineStartOffset = content.getLineStartOffset(caretLine);
		int nextLineStartOffset = content.getLineStartOffset(caretLine + 1);
		
		// 重置选择区
		resetSelection(lineStartOffset, nextLineStartOffset);
	
		// 重置光标
		caretOffset = lineStartOffset;
		setCaretLocation();
    }
    
    /**
     * 将被选择区域置为指定的值，我们需要重画旧的选择区，再画新的选择区
     * 
     * @param start
     * @param end
     */
    private void resetSelection(int start, int end) {
		int oldStart = selectionStart;
		int oldEnd = selectionEnd;		
		resetSelection();
		redrawRange(oldStart, oldEnd);
		
		// 画新的选择区		
		selectionStart = start;
		selectionEnd = end;
		redrawRange(selectionStart, selectionEnd);
    }
    
    /**
     * 重画start和end之间的字符，不包括end
     * 
     * @param start
     * @param end
     */
    private void redrawRange(int start, int end) {
        if(end <= start)
            return;
        
        GC _gc = getGC();
        int startLine = content.getLineAtOffset(start);
        int endLine = content.getLineAtOffset(end - 1);
        int bottomLine = getBottomLine(_gc);
        if(endLine < topLine || startLine > bottomLine)
            return;
        
        // 让开始行和结束行出现在view中
        while(startLine < topLine)
            startLine++;
        while(endLine > bottomLine)
            endLine--;
        
        int y = contentHelper.getHeightOfLines(_gc, 0, startLine) - verticalScrollOffset + topMargin;
        int h = contentHelper.getHeightOfLines(_gc, startLine, endLine + 1);
        releaseGC();
        redraw(leftMargin, y, clientAreaWidth - leftMargin - rightMargin, h, true);
    }
    
	/**
	 * Resets the selection.
	 */
	private void resetSelection() {
		selectionStart = selectionEnd = caretOffset;
	}

    /**
     * @param event
     */
    protected void handleMouseUp(Event event) {
        endAutoScroll();
        
		if ((event.stateMask & SWT.BUTTON3) != 0 && enableContextMenu) {
		    contextMenu.setLocation(toDisplay(event.x, event.y));
			contextMenu.setVisible(true);
		}
    }

    /**
     * @param event
     */
    protected void handleMouseDown(Event event) {
    	contextMenu.setVisible(false);
        if(event.button == 2) {
            paste();
            return;
        }
		if (event.button != 1
				|| (IS_CARBON && (event.stateMask & SWT.MOD4) != 0)) {
			return;
		}
		event.x -= leftMargin;
		event.y -= topMargin;
		if(event.x < 0 || event.y < 0)
		    return;
		doMouseLocationChange(event.x, event.y, false);
    }
    
    /**
     * 清除选择区
     */
    private void clearSelection() {
        int oldStart = selectionStart;
        int oldEnd = selectionEnd;
        resetSelection();
        redrawRange(oldStart, oldEnd);
    }

    /**
     * @param x
     * @param y
     * @param move
     * 		true表示鼠标在移动中
     */
    private void doMouseLocationChange(int x, int y, boolean move) {
        GC _gc = getGC();
        int lineIndex = contentHelper.getLineAtY(_gc, y + verticalScrollOffset);
		caretOffset = contentHelper.getOffsetAtX(_gc, lineIndex, x);
		setCaretLocation();
        releaseGC();
        
        if(move) {
            // 判断谁先谁后，谁应该是start，谁是end
            if(caretOffset >= selectionAnchor)
                resetSelection(selectionAnchor, caretOffset);
            else
                resetSelection(caretOffset, selectionAnchor);
        } else {
            selectionAnchor = caretOffset;
            clearSelection();
        }
    }

    /**
     * @param event
     */
    protected void handleKeyDown(Event event) {
		Event verifyEvent = new Event();

		verifyEvent.character = event.character;
		verifyEvent.keyCode = event.keyCode;
		verifyEvent.stateMask = event.stateMask;
		verifyEvent.doit = true;
		notifyListeners(VerifyKey, verifyEvent);
		if (verifyEvent.doit == true) {
			handleKey(event);
		}
    }

    /**
     * @param event
     */
    protected void handleKey(Event event) {
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
		    // 如果是只读模式，直接返回
		    if(readonly)
		        return;
		    
		    // 如果没有事件和这个按键绑定，则要检查按键的内容，如果不包含一些
		    // 特殊的按键，则当作普通的输入处理，否则忽略掉		    
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
			// -ignore anything below SPACE except for line delimiter keys and tab.
			// -ignore DEL
			if (!ignore && event.character > 31 && event.character != SWT.DEL
					|| event.character == TAB) {
				doContent(event.character);
			}
		} else if(action == CUSTOM_ACTION) {
		    getUserKeyAction(key).run();
		} else {
			invokeAction(action);
		}
    }
    
	/**
	 * 输入一个字符
	 * 
     * @param character
     */
    private void doContent(char key) {
		Event event;
		event = new Event();
		event.start = selectionStart;
		event.end = selectionEnd;
		
		// replace a CR line break with the widget line break
		// CR does not make sense on Windows since most (all?) applications
		// don't recognize CR as a line break.
		if (key == SWT.CR || key == SWT.LF) {
			event.text = CRLF;
		} else if(key == TAB) {
		    event.text = "    ";
		} else {
			event.text = new String(new char[] { key });
		}
		caretOffset = selectionStart + event.text.length();
		clearSelection();
		modifyContent(event);
		showCaret();
    }
    
    /**
     * 修改内容
     * 
     * @param text
     */
    private void doContent(String text) {
		Event event;
		event = new Event();
		event.start = selectionStart;
		event.end = selectionEnd;

		event.text = text.replaceAll("\t", "    ");
		event.text = event.text.replaceAll("\r", "");
		caretOffset = selectionStart + event.text.length();
		clearSelection();
		modifyContent(event);
		showCaret();
    }

    /**
     * 修改内容
     * 
     * @param event
     * 		事件
     */
    private void modifyContent(Event event) {
		event.doit = true;
		notifyListeners(SWT.Verify, event);
		if (event.doit) {
			stopAnimation(content.getText(event.start, event.end - event.start));
		    content.replaceText(new TextEvent(event));
		    startAnimation(event.text);
			setScrollBars();
		}
    }

    /**
	 * 执行按键动作
	 * 
     * @param action
     * 		动作ID
     */
    private void invokeAction(int action) {
        int caretLine;
        int oldColumnX = columnX;
        
        switch(action) {
            case LINE_UP:
    			caretLine = doSimpleLineUp();
    			showCaret(caretLine);
    			clearSelection();
    			columnX = oldColumnX;
                break;
            case LINE_DOWN:
                caretLine = doSimpleLineDown();
                showCaret(caretLine);
                clearSelection();
    			columnX = oldColumnX;
                break;
            case COLUMN_LEFT:
                doCaretLeft();
                clearSelection();
                break;
            case COLUMN_RIGHT:
                doCaretRight();
                clearSelection();
                break;
            case LINE_START:
                doLineStart();
                clearSelection();
                break;
            case LINE_END:
                doLineEnd();
                clearSelection();
                break;
            case TEXT_START:
                doTextStart();
                clearSelection();
                break;
            case TEXT_END:
                doTextEnd();
                clearSelection();
                break;
            case PAGE_UP:
                doPageUp();
                clearSelection();
                break;
            case PAGE_DOWN:
                doPageDown();
                clearSelection();
                break;
            case SELECT_COLUMN_LEFT:
                doSelectionCaretLeft();
                break;
            case SELECT_COLUMN_RIGHT:
                doSelectionCaretRight();
                break;
            case SELECT_COLUMN_START:
            	doSelectionColumnStart();
            	break;
            case SELECT_COLUMN_END:
            	doSelectionColumnEnd();
            	break;
            case SELECT_LINE_UP:
                doSelectionLineUp();
                break;
            case SELECT_LINE_DOWN:
                doSelectionLineDown();
                break;
            case COPY:
                copy();
                break;
            case CUT:
                cut();
                break;
            case PASTE:
                paste();
                break;
            case DELETE_PREVIOUS:
                if(!readonly)
                    doBackspace();
                break;
            case DELETE_NEXT:
                if(!readonly)
                    doDelete();
                break;            
            case SELECT_ALL:
                doSelectAll();
                break;
            case NEW_LINE:
                if(!readonly)
                    doContent(SWT.CR);
                break;
        }
    }    

	/**
     * 全选
     */
    private void doSelectAll() {
        caretOffset = 0;        
        resetSelection(0, content.getCharCount());
        showCaret();
    }

    /**
     * 粘贴
     */
    private void paste() {
        if(readonly)
            return;
        
        String text = (String)clipboard.getContents(TextTransfer.getInstance());
        if(text == null || text.equals(""))
            return;        
        
		Event event;
		event = new Event();
		event.start = selectionStart;
		event.end = selectionEnd;
		event.text = text.replaceAll("\t", "    ");
		
		caretOffset = selectionStart + event.text.length();
		clearSelection();
		modifyContent(event);
		showCaret();
    }

    /**
     * 剪切
     */
    private void cut() {
        copy();
        if(!readonly)
            doDelete();
    }

    /**
	 * 删除前一个字符或者被选择区域
	 */
	void doBackspace() {
		Event event = new Event();
		event.text = "";
		if (selectionEnd != selectionStart) {
			event.start = selectionStart;
			event.end = selectionEnd;
			caretOffset = selectionStart;
			clearSelection();
			modifyContent(event);
			showCaret();
		} else if (caretOffset > 0) {
			int line = content.getLineAtOffset(caretOffset);
			int lineOffset = content.getLineStartOffset(line);

			if (caretOffset == lineOffset) {
				lineOffset = content.getLineStartOffset(line - 1);
				event.start = lineOffset + content.getLine(line - 1).length();
				event.end = caretOffset;
				caretOffset = event.start;
			} else {
				event.start = caretOffset - 1;
				event.end = caretOffset;
				if(content.isImageTag(event.start - 1)) {
				    event.start--;		
				    caretOffset--;
				}
				caretOffset--;
			}
			resetSelection();
			modifyContent(event);
			showCaret();
		}
	}

	/**
	 * 删除下一个字符或者被选择区
	 */
	void doDelete() {
		Event event = new Event();
		event.text = "";
		if (selectionEnd != selectionStart) {
			event.start = selectionStart;
			event.end = selectionEnd;
			caretOffset = selectionStart;
			clearSelection();
			modifyContent(event);
			showCaret();
		} else if (caretOffset < content.getCharCount()) {
			int line = content.getLineAtOffset(caretOffset);
			int lineOffset = content.getLineStartOffset(line);
			int lineLength = content.getLine(line).length();

			if (caretOffset == lineOffset + lineLength) {
				event.start = caretOffset;
				event.end = content.getLineStartOffset(line + 1);
			} else {
				event.start = caretOffset;
				event.end = event.start + 1;
				if(content.isImageTag(event.start))
				    event.end++;
			}
			modifyContent(event);
			showCaret();
		}
	}

	/**
	 * 拷贝
	 * <p>
	 * 
	 * @exception SWTException
	 *                <ul>
	 *                <li>ERROR_WIDGET_DISPOSED - if the receiver has been
	 *                disposed</li>
	 *                <li>ERROR_THREAD_INVALID_ACCESS - if not called from the
	 *                thread that created the receiver</li>
	 *                </ul>
	 */
	public void copy() {
		checkWidget();
		int length = selectionEnd - selectionStart;
		if (length > 0) {
			try {
				setClipboardContent(selectionStart, length);
			} catch (SWTError error) {
				// Copy to clipboard failed. This happens when another
				// application
				// is accessing the clipboard while we copy. Ignore the error.
				// Rethrow all other errors.
				if (error.code != DND.ERROR_CANNOT_SET_CLIPBOARD) {
					throw error;
				}
			}
		}
	}
	
	/**
	 * 设置剪切版内容
	 * 
     * @param start
     * 		起始位置
     * @param length
     * 		长度
     */
    private void setClipboardContent(int start, int length) {
        if(content == null)
            return;
        String text = content.getText(start, length);
        setClipboardContent(text);
    }
    
    /**
     * 设置剪切版内容
     * 
     * @param text
     */
    private void setClipboardContent(String text) {
        TextTransfer textTransfer = TextTransfer.getInstance();
        clipboard.setContents(new Object[] { text }, new Transfer[] { textTransfer });
    }

    /**
     * 下移一行并选择
     */
    private void doSelectionLineDown() {
        int old = caretOffset;
        doLineDown();
		if(old == caretOffset)
		    return;
		if(caretOffset == selectionEnd)
		    resetSelection(caretOffset, caretOffset);
		else if(caretOffset > selectionEnd)
		    resetSelection(selectionStart, caretOffset);
		else 
		    resetSelection(caretOffset, selectionEnd);
    }

    /**
     * 上移一行并选择
     */
    private void doSelectionLineUp() {
        int old = caretOffset;
        doLineUp();
		if(old == caretOffset)
		    return;
		if(caretOffset == selectionStart)
		    resetSelection(caretOffset, caretOffset);
		else if(caretOffset < selectionStart)
		    resetSelection(caretOffset, selectionEnd);
		else
		    resetSelection(selectionStart, caretOffset);
    }

    /**
     * 光标右移并选择
     */
    private void doSelectionCaretRight() {
        int old = caretOffset;
        doSimpleCaretRigth();
		if(old == caretOffset)
		    return;
		if(caretOffset == selectionEnd)
		    resetSelection(caretOffset, caretOffset);
		else if(caretOffset > selectionEnd)
		    resetSelection(selectionStart, caretOffset);
		else 
		    resetSelection(caretOffset, selectionEnd);
    }

    /**
     * 光标左移1格，选中移动的位置
     */
    private void doSelectionCaretLeft() {
        int old = caretOffset;
		doSimpleCaretLeft();
		if(old == caretOffset)
		    return;
		if(caretOffset == selectionStart)
		    resetSelection(caretOffset, caretOffset);
		else if(caretOffset < selectionStart)
		    resetSelection(caretOffset, selectionEnd);
		else
		    resetSelection(selectionStart, caretOffset);
    }
    
	/**
	 * 选择到行尾
	 */
	private void doSelectionColumnEnd() {
		int caretLine = content.getLineAtOffset(caretOffset);
		int lineOffset = content.getLineStartOffset(caretLine);
		int lineLength = content.getLine(caretLine).length();
		int lineEndOffset = lineOffset + lineLength;
		if(hasSelection()) {
			int startLine = content.getLineAtOffset(selectionStart);
			int endLine = content.getLineAtOffset(selectionEnd);
			if(startLine == endLine)
				resetSelection((caretOffset == selectionEnd) ? selectionStart : selectionEnd, lineEndOffset);
			else if(caretLine == startLine)
				resetSelection(lineEndOffset, selectionEnd);
			else
				resetSelection(selectionStart, lineEndOffset);
		} else
			resetSelection(caretOffset, lineEndOffset);
		caretOffset = lineEndOffset;
		showCaret(caretLine);
	}

	/**
	 * 选择到行首
	 */
	private void doSelectionColumnStart() {
		int caretLine = content.getLineAtOffset(caretOffset);
		int lineOffset = content.getLineStartOffset(caretLine);
		if(hasSelection()) {
			int startLine = content.getLineAtOffset(selectionStart);
			int endLine = content.getLineAtOffset(selectionEnd);
			if(startLine == endLine)
				resetSelection(lineOffset, (caretOffset == selectionStart) ? selectionEnd : selectionStart);
			else if(caretLine == startLine)
				resetSelection(lineOffset, selectionEnd);
			else
				resetSelection(selectionStart, lineOffset);
		} else	
			resetSelection(lineOffset, caretOffset);
		caretOffset = lineOffset;
		showCaret(caretLine);
	}

    /**
	 * 下翻一页，不改变光标的X参考点
	 */
	private void doPageDown() {
		int lineCount = content.getLineCount();
		int oldColumnX = columnX;
		int caretLine;

		caretLine = content.getLineAtOffset(caretOffset);
		if (caretLine < lineCount - 1) {
		    GC _gc = getGC();
			int verticalMaximum = contentHelper.getHeightOfLines(_gc, 0, lineCount); 
			int pageSize = clientAreaHeight - topMargin - bottomMargin;
			int scrollLines = Math.min(lineCount - caretLine - 1, getFullVisibleLineCount());
			
			scrollLines = Math.max(1, scrollLines);
			caretLine += scrollLines;
			caretOffset = contentHelper.getOffsetAtX(_gc, caretLine, columnX);
			if(pageSize > verticalMaximum)
			    showCaret();
			else
			    setVerticalScrollOffset(contentHelper.getHeightOfLines(_gc, 0, caretLine), true);
			releaseGC();
		}
		// 显示光标
		showCaret(caretLine);
		columnX = oldColumnX;
	}
	
	/**
	 * 上翻一页，保持光标X位置不变。上翻页将光标减掉view中的全部可见行数。
	 */
	private void doPageUp() {
		int oldColumnX = columnX;
		int caretLine = content.getLineAtOffset(caretOffset);

		// 如果当前光标所在的行大于0，上翻一页
		if (caretLine > 0) {
	        GC _gc = getGC();
			int scrollLines = Math.max(1, Math.min(caretLine, getFullVisibleLineCount()));

			caretLine -= scrollLines;
			caretOffset = contentHelper.getOffsetAtX(_gc, caretLine, columnX);
			setVerticalScrollOffset(contentHelper.getHeightOfLines(_gc, 0, caretLine), true);
			releaseGC();
		}
		// 显示光标
		showCaret(caretLine);
		columnX = oldColumnX;
	}
	
    /**
     * @return
     * 		返回窗口中完全可见行的行数
     */
    private int getFullVisibleLineCount() {
        GC _gc = getGC();
        int fullVisibleBottomLine = getFullVisibleBottomLine(_gc);
        int fullVisibleTopLine = topLine;
        if(contentHelper.getHeightOfLines(_gc, 0, fullVisibleTopLine) - verticalScrollOffset < 0)
            fullVisibleTopLine++;
        releaseGC();
        int lineCount = fullVisibleBottomLine - fullVisibleTopLine + 1;
        if(lineCount < 0)
            return 0;
        else
            return lineCount;
    }
    
    /**
     * 光标置于最后
     */
    private void doTextEnd() {
        if(caretOffset < content.getCharCount()) {
            caretOffset = content.getCharCount();
            showCaret();
        }
    }
    
    /**
     * 光标置于最开头
     */
    private void doTextStart() {
        if(caretOffset != 0) {
            caretOffset = 0;
            showCaret();
        }
    }
    
    /**
     * 将光标置于行结尾
     */
    private void doLineEnd() {
		int caretLine = content.getLineAtOffset(caretOffset);
		int lineOffset = content.getLineStartOffset(caretLine);
		int lineLength = content.getLine(caretLine).length();
		int lineEndOffset = lineOffset + lineLength;
		if (caretOffset < lineEndOffset) {
			caretOffset = lineEndOffset;
			showCaret();
		}
    }
    
	/**
	 * 将光标置于行开始
	 */
	private void doLineStart() {
		int caretLine = content.getLineAtOffset(caretOffset);
		int lineOffset = content.getLineStartOffset(caretLine);
		if (caretOffset > lineOffset) {
			caretOffset = lineOffset;
			showCaret(caretLine);
		}
	}

	/**
	 *如果存在被选择区，则把光标放置到被选择区结束，否则简单移动
	 * <p>
	 * 
	 * @see #doSimpleCaretRigth
	 */
    private void doCaretRight() {
		if (selectionEnd > selectionStart) {
			int caretLine;

			caretOffset = selectionEnd;
			caretLine = content.getLineAtOffset(caretOffset);
			showCaret(caretLine);
		} else {
			doSimpleCaretRigth();
		}
	}
	
	/**
	 * 往后移动光标一格，如果光标在一行的末尾，则要置光标到下一行的开头，如果后面是个图片，
	 * 要注意移动两个位置
	 */
	private void doSimpleCaretRigth() {
		int caretLine = content.getLineAtOffset(caretOffset);
		int lineOffset = content.getLineStartOffset(caretLine);
		int offsetInLine = caretOffset - lineOffset;
		if (offsetInLine < content.getLine(caretLine).length()) {
			if(content.isImageTag(caretOffset))
			    caretOffset++;
			caretOffset++;
			showCaret();
		} else if (caretLine < content.getLineCount() - 1) {
			caretLine++;
			caretOffset = content.getLineStartOffset(caretLine);
			showCaret(caretLine);
		}
	}
	
	/**
	 * 如果存在被选择区，则把光标放置到被选择区开始，否则简单移动
	 * <p>
	 * 
	 * @see #doSimpleCaretLeft
	 */
	private void doCaretLeft() {
		if (selectionEnd > selectionStart) {
			int caretLine;

			caretOffset = selectionStart;
			caretLine = content.getLineAtOffset(caretOffset);
			showCaret(caretLine);
		} else {
			doSimpleCaretLeft();
		}
	}
	
	/**
	 * 往前移动光标一格，如果光标在一行的开头，则要置光标到上一行的末尾，如果前面是个图片，
	 * 要注意移动两个位置
	 */
	private void doSimpleCaretLeft() {
		int caretLine = content.getLineAtOffset(caretOffset);
		int lineOffset = content.getLineStartOffset(caretLine);
		int offsetInLine = caretOffset - lineOffset;
		if (offsetInLine > 0) {
			caretOffset--;
			if(content.isImageTag(caretOffset - 1))
			    caretOffset--;
			showCaret(caretLine);
		} else if (caretLine > 0) {
			caretLine--;
			lineOffset = content.getLineStartOffset(caretLine);
			int newCaretOffset = lineOffset + content.getLine(caretLine).length();
			if(newCaretOffset == caretOffset)
			    caretOffset--;
			else
			    caretOffset = newCaretOffset;
			if(content.isImageTag(caretOffset - 1))
			    caretOffset--;
			showCaret();
		}
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
	 * 添加缺省按键绑定
	 */
	private void createKeyBindings() {
		// 导航按键绑定
		setKeyBinding(SWT.HOME, LINE_START);
		setKeyBinding(SWT.END, LINE_END);
		setKeyBinding(SWT.PAGE_UP, PAGE_UP);
		setKeyBinding(SWT.PAGE_DOWN, PAGE_DOWN);
		setKeyBinding(SWT.HOME | SWT.MOD1, TEXT_START);
		setKeyBinding(SWT.END | SWT.MOD1, TEXT_END);
		setKeyBinding(SWT.PAGE_UP | SWT.MOD1, TEXT_START);
		setKeyBinding(SWT.PAGE_DOWN | SWT.MOD1, TEXT_END);
		setKeyBinding(SWT.ARROW_LEFT, COLUMN_LEFT);
		setKeyBinding(SWT.ARROW_RIGHT, COLUMN_RIGHT);
		setKeyBinding(SWT.ARROW_UP, LINE_UP);
		setKeyBinding(SWT.ARROW_DOWN, LINE_DOWN);

		// Selection
		setKeyBinding(SWT.ARROW_UP | SWT.MOD2, SELECT_LINE_UP);
		setKeyBinding(SWT.ARROW_DOWN | SWT.MOD2, SELECT_LINE_DOWN);
		setKeyBinding(SWT.ARROW_LEFT | SWT.MOD2, SELECT_COLUMN_LEFT);
		setKeyBinding(SWT.ARROW_RIGHT | SWT.MOD2, SELECT_COLUMN_RIGHT);
		setKeyBinding(SWT.HOME | SWT.MOD2, SELECT_COLUMN_START);
		setKeyBinding(SWT.END | SWT.MOD2, SELECT_COLUMN_END);
		setKeyBinding('A' | SWT.MOD1, SELECT_ALL);

		// Modification
		setKeyBinding('X' | SWT.MOD1, CUT);
		setKeyBinding('C' | SWT.MOD1, COPY);
		setKeyBinding('V' | SWT.MOD1, PASTE);		
		setKeyBinding(SWT.BS, DELETE_PREVIOUS);
		setKeyBinding(SWT.DEL, DELETE_NEXT);
		setKeyBinding(SWT.CR, NEW_LINE);
	}

    /**
     * @param event
     */
    protected void handleDispose(Event event) {
		removeListener(SWT.Dispose, listener);
		notifyListeners(SWT.Dispose, event);
		event.type = SWT.None;
		
		if (caret != null) {
		    caret.dispose();
		    caret = null;
		}
		if(backXorColor != null)
		    backXorColor.dispose();
		clipboard.dispose();
		beamCursor = null;
    }

    /**
     * @param event
     */
    protected void handlePaint(Event event) {
		// Check if there is work to do
		if (event.height == 0)
			return;
		
        // adjust y position for pixel based scrolling and top margin
		int startLine = contentHelper.getLineAtY(event.gc, verticalScrollOffset);
		int paintYFromTopLine = contentHelper.getHeightOfLines(event.gc, topLine, startLine);
		int topLineOffset = contentHelper.getYOfLine(event.gc, topLine) - verticalScrollOffset;
		int startY = paintYFromTopLine + topLineOffset + topMargin; 
		int renderHeight = event.y + event.height - startY;

		performPaint(event.gc, startLine, startY, renderHeight);
    }

    /**
     * @param _gc
     * @param startLine
     * @param startY
     * @param renderHeight
     */
    private void performPaint(GC _gc, int startLine, int startY, int renderHeight) {
		Rectangle clientArea = getClientArea();
		Color background = getBackground();
		
		// Check if there is work to do. We never want to try and create
		// an Image with 0 width or 0 height.
		if (clientArea.width == 0)
			return;
		
		if (renderHeight > 0) {
			// renderHeight will be negative when only top margin needs
			// redrawing
			Color foreground = getForeground();
			int lineCount = content.getLineCount();
			int paintY, paintHeight;
			Image lineBuffer;
			GC lineGC;
			boolean doubleBuffer = DOUBLE_BUFFERED && lastPaintTopLine == topLine;
			lastPaintTopLine = topLine;
			if (doubleBuffer) {
				paintY = 0;
				paintHeight = renderHeight;
				lineBuffer = new Image(getDisplay(), clientArea.width, renderHeight);
				lineGC = new GC(lineBuffer);
				lineGC.setForeground(foreground);
				lineGC.setBackground(background);
			} else {
				paintY = startY;
				paintHeight = startY + renderHeight;
				lineBuffer = null;
				lineGC = _gc;
			}
			
			if(gtkColorError) {
				lineGC.setBackground(background);
				lineGC.fillRectangle(0, paintY, clientArea.width, paintHeight - paintY);
			}
			
			for (int i = startLine; paintY < paintHeight && i < lineCount; paintY += contentHelper.getLineHeight(lineGC, i), i++) {
				drawLine(lineGC, i, paintY);
			}
			if (!gtkColorError && paintY < paintHeight) {
				lineGC.setBackground(background);
				lineGC.fillRectangle(0, paintY, clientArea.width, paintHeight - paintY);
			}
			if (doubleBuffer) {
				clearMargin(lineGC, background, clientArea, startY);
				_gc.drawImage(lineBuffer, 0, startY);
				lineGC.dispose();
				lineBuffer.dispose();
			}
		}
		clearMargin(_gc, background, clientArea, 0);
    }    

	/**
	 * Clears the widget margin.
	 * 
	 * @param _gc
	 *            GC to render on
	 * @param background
	 *            background color to use for clearing the margin
	 * @param clientArea
	 *            widget client area dimensions
	 */
	private void clearMargin(GC _gc, Color background, Rectangle clientArea, int y) {
		// clear the margin background
	    Color old = _gc.getBackground();
		_gc.setBackground(background);	
		if (topMargin > 0) {
			_gc.fillRectangle(0, -y, clientArea.width, topMargin);
		}
		if (bottomMargin > 0) {
			_gc.fillRectangle(0, clientArea.height - bottomMargin - y, clientArea.width, bottomMargin);
		}
		if (leftMargin > 0) {
			_gc.fillRectangle(0, -y, leftMargin, clientArea.height);
		}
		if (rightMargin > 0) {
			_gc.fillRectangle(clientArea.width - rightMargin, -y, rightMargin, clientArea.height);
		}
		_gc.setBackground(old);
	}
    
	/**
	 * Adjusts the maximum and the page size of the scroll bars to reflect
	 * content width/length changes.
	 */
	private void setScrollBars() {
		ScrollBar verticalBar = getVerticalBar();

		if (verticalBar != null) {
			Rectangle clientArea = getClientArea();
			clientArea.height -= topMargin + bottomMargin;
			int maximum = contentHelper.getHeightOfLines(getGC(), 0, content.getLineCount());
			releaseGC();

			// only set the real values if the scroll bar can be used
			// (ie. because the thumb size is less than the scroll maximum)
			// avoids flashing on Motif, fixes 1G7RE1J and 1G5SE92
			boolean largerThanClient = clientArea.height < maximum;
			if (largerThanClient) {
				verticalBar.setValues(verticalBar.getSelection(), 
				        verticalBar.getMinimum(),
				        maximum, 
				        clientArea.height, 
						verticalBar.getIncrement(),
						clientArea.height); 																		
			}
			
			verticalBar.setVisible(largerThanClient || verticalBar.getSelection() > 0);
		}
	}
	
    /**
     * 设置内容
     * 
     * @param text
     * 		内容
     */
    public void setText(String text) {
        checkWidget();
        content.setText(text);
        caretOffset = 0;
        resetSelection();
        setScrollBars();
        redraw();
        showCaret();
        animationManager.stopAll();
        startAnimation(text);
    }

    /**
     * 画一行
     * 
     * @param lineGC
     * 		GC
     * @param i
     * 		行号
     * @param paintY
     * 		需要重绘区域的起始Y坐标
     */
    private void drawLine(GC lineGC, int i, int paintY) {
        ILineStyler styler = content.getLineStyler();
        LineStyle style = styler.getLineStyle(i);
        Rectangle clientArea = getClientArea();
        Color oldForeground = lineGC.getForeground();
        Color oldBackground = lineGC.getBackground();
        if(style.foreground != null)
            lineGC.setForeground(style.foreground);
        if(style.background != null)
            lineGC.setBackground(style.background);
        lineGC.setFont(styler.getLineFont(i));
        lineGC.fillRectangle(leftMargin, paintY, clientArea.width, contentHelper.getLineHeight(lineGC, i));
        
        String line = content.getLine(i);
        int textHeight = lineGC.textExtent(line).y;
        int lineHeight = contentHelper.getLineHeight(lineGC, i);
        int textY = (lineHeight > textHeight) ? (paintY + ((lineHeight - textHeight) >>> 1)) : paintY;
        drawString(lineGC, i, line, leftMargin, paintY, textY, lineHeight, null, null);
        lineGC.setForeground(oldForeground);
        lineGC.setBackground(oldBackground);
    }
    
	/**
	 * 画一行，行内可能有被选择的部分
	 * 
	 * @param _gc
	 * 		GC
	 * @param i
	 * 		行号
	 * @param line
	 * 		行字符串，不包括回车换行
	 * @param paintX
	 * 		画字符串的起始X位置
	 * @param paintY
	 * 		重画区域的起始Y
	 * @param textY
	 * 		画文本的起始Y位置
	 * @param lineHeight
	 * 		行高
	 * @param selectionForeground
	 * 		选择文本的前景
	 * @param selectionBackground
	 * 		选择文本的背景
	 * 
	 * @author luma
	 */
	private void drawString(GC _gc, int i, String line, int paintX, int paintY, int textY, int lineHeight, Color selectionForeground, Color selectionBackground) {
		if (selectionForeground == null) selectionForeground = getDisplay().getSystemColor(SWT.COLOR_LIST_SELECTION_TEXT);
		if (selectionBackground == null) selectionBackground = getDisplay().getSystemColor(SWT.COLOR_LIST_SELECTION);
		
		boolean hasSelection = hasSelectionInLine(i);
		if (hasSelection) {		    
		    // 得到行内的被选择区域的相对起始偏移和结束偏移
		    int lineStartOffset = content.getLineStartOffset(i);
		    int start = selectionStart - lineStartOffset;
		    if(start < 0)
		        start = 0;
		    int end = selectionEnd - lineStartOffset;
		    if(end >= line.length())
		        end = line.length();
		    
		    // 画这行
			if(start > 0) 
				paintX = drawString(_gc, line.substring(0, start), paintX, paintY, textY, lineHeight, false);
			paintX = drawString(_gc, line.substring(start, end), paintX, paintY, textY, lineHeight, selectionForeground, selectionBackground, true);
			if(end < line.length())
				drawString(_gc, line.substring(end), paintX, paintY, textY, lineHeight, false);		    
		} else 
		    drawString(_gc, line, paintX, paintY, textY, lineHeight, false);
	}
	
	/**
	 * @param i
	 * 		行号
	 * @return
	 * 		true表示行中有被选择的部分
	 */
	private boolean hasSelectionInLine(int i) {
	    if(selectionStart == selectionEnd)
	        return false;
	    
	    int lineStartOffset = content.getLineStartOffset(i);
	    int nextLineStartOffset = content.getLineStartOffset(i + 1);
	    if(selectionEnd <= lineStartOffset || selectionStart >= nextLineStartOffset)
	        return false;
	    return true;
	}
	
	/**
	 * Draw a line with specified foreground and background
	 * 
	 * @param _gc
	 * @param line
	 * @param paintX
	 * @param paintY
	 * 		重画区域的起始Y
	 * @param textY
	 * 		画文本的起始Y位置
	 * @param lineHeight
	 * 		行高
	 * @param foreground
	 * @param background
	 * @param selection
	 * 		true表示这段文本是被选择的
	 * 
	 * @author luma
	 */
	private int drawString(GC _gc, String line, int paintX, int paintY, int textY, int lineHeight, Color foreground, Color background, boolean selection) {
		Color oldForeground = _gc.getForeground();
		Color oldBackground = _gc.getBackground();
		_gc.setForeground(foreground);
		_gc.setBackground(background);
		int x = drawString(_gc, line, paintX, paintY, textY, lineHeight, selection);
		_gc.setForeground(oldForeground);
		_gc.setBackground(oldBackground);
		return x;
	}

	/**
	 * Draw a line without any seletion in it
	 * 
	 * @param gc
	 * @param line
	 * @param paintX
	 * @param paintY
	 * 		重画区域的起始Y
	 * @param textY
	 * 		画文本的起始Y位置
	 * @param lineHeight
	 * 		行高
	 * @param selection
	 * 		true表示这段文本是被选择的
	 * @return
	 * 		the paintX after draw finished
	 * 
	 * @author luma
	 */
	private int drawString(GC gc, String line, int paintX, int paintY, int textY, int lineHeight, boolean selection) {
		int i = 0;
		int j = contentHelper.findNextImage(line, 0);
		for (; j != -1; i = j + 2, j = contentHelper.findNextImage(line, i)) {
		    // 画文本
			String sub = line.substring(i, j);
			int textWidth = gc.textExtent(sub).x;
			gc.fillRectangle(paintX, paintY, textWidth, lineHeight);
			gc.drawString(sub, paintX, textY);
			paintX += textWidth;
			// 画图片
			int tag = line.charAt(j);
			int code = line.charAt(j + 1);
			Image image = imageResolver.getImage(tag, code);
			int imageY = (lineHeight > image.getBounds().height) ? (paintY + ((lineHeight - image.getBounds().height) >>> 1)) : paintY;
			gc.drawImage(image, paintX, imageY);
			// 如果图片处于被选择状态，在图片上做一个反色
			if(selection) {
				gc.setXORMode(true);
				Color old = gc.getBackground();
				gc.setBackground(backXorColor);
				gc.fillRectangle(paintX, paintY, image.getBounds().width, lineHeight);
				gc.setBackground(old);
				gc.setXORMode(false);			    
			}			    
			paintX += image.getBounds().width;
		}
		// 检查结尾
		if (i < line.length()) {
			String sub = line.substring(i);
			int textWidth = gc.textExtent(sub).x;
			gc.fillRectangle(paintX, paintY, textWidth, lineHeight);
			gc.drawString(sub, paintX, textY);
			paintX += textWidth;
		}
		return paintX;
	}
	
	/**
	 * 为某行添加style，设置为null相当于使用缺省style
	 * 
	 * @param lineIndex
	 * 		行号
	 * @param style
	 * 		样式
	 */
	public void setLineStyle(int lineIndex, LineStyle style) {
	    content.setLineStyle(lineIndex, style);
	    setCaretLocation();
	}
	
	/**
	 * 设置缺省style
	 * 
	 * @param style
	 */
	public void setDefaultStyle(LineStyle style) {
	    if(style != null) {
	        content.setDefaultStyle(style);	        
	        setCaretLocation();
	    }
	}
	
	/**
	 * @return
	 * 		全部的内容
	 */
	public String getText() {
	    return content.getText(0, content.getCharCount());
	}
	
	/**
	 * 得到全部的内容，用指定的适配器转换格式
	 * 
	 * @param adapter
	 * 		格式适配器
	 * @return
	 * 		转换后的内容
	 */
	public String getText(IFormatAdapter adapter) {
	    return adapter.convert(getText());
	}
	
	/**
	 * 添加文本，文本如果包含多行，则都使用指定的style。文本将以行的形式添加，即在文本
	 * 的开头会追加换行
	 * 
	 * @param text
	 * 		文本
	 * @param style
	 * 		文本的style
	 */
	public void appendText(String text, LineStyle style) {
	    checkWidget();
	    
	    if(text == null || text.equals(""))
	        return;
	    
	    int oldLineCount = content.getHardLineCount();
	    caretOffset = content.getCharCount();
	    resetSelection(caretOffset, caretOffset);
	    if(content.isDelimit(caretOffset - 1))
	        doContent(text);
	    else
	        doContent(CRLF + text);
	    if(style != null) {
		    int newLineCount = content.getHardLineCount();
		    if(newLineCount == oldLineCount)
		        content.setLineStyle(newLineCount - 1, style);
		    else {
			    for(int i = oldLineCount; i < newLineCount; i++)
			        content.setLineStyle(i, style);       
		    }
	    }
	    caretOffset = content.getCharCount();	    
	    setScrollBars();
	    showCaret();
	}
	
	/**
	 * 在一段内容中寻找动画
	 * 
	 * @param text
	 */
	private void startAnimation(String text) {
	    if(forbiddenAnimation)
	        return;
		int count = AnimationThread.MAX;
		int i = contentHelper.findPreviousImage(text, text.length() - 1);
		while(i != -1 && count > 0) {
			animationManager.startAnimation(text.charAt(i), text.charAt(i + 1));
			count--;
			i = contentHelper.findPreviousImage(text, i - 1);
		}
	}
	
	/**
	 * 在一段内容中寻找动画，停止它们
	 * 
	 * @param text
	 */
	private void stopAnimation(String text) {
	    if(forbiddenAnimation)
	        return;
		int i = contentHelper.findNextImage(text, 0);
		while(i != -1) {
			animationManager.stopAnimation(text.charAt(i), text.charAt(i + 1));
			i = contentHelper.findNextImage(text, i + 2);
		}
	}
	
	/**
	 * 在光标的当前位置插入图片
	 * 
	 * @param tag
	 * 		图片类型
	 * @param index
	 * 		索引，最大值65535
	 */
	public void insertImage(int tag, int index) {
	    checkWidget();
	    
	    if(readonly)
	        return;
	    
	    char[] c = new char[2];
	    c[0] = (char)tag;
	    c[1] = (char)index;
	    
	    String text = new String(c);
	    doContent(text);
	    showCaret();
	}

	/**
	 * 从startLine开始重画后面的所有行
	 * 
	 * @param startLine
	 */
	void redrawFromLine(int startLine) {
	    int y = contentHelper.getHeightOfLines(getGC(), 0, startLine) - verticalScrollOffset;
	    releaseGC();
	    int h = clientAreaHeight - topMargin - bottomMargin;
	    if(y > h)
	        return;
	    
	    if(y < 0)
	        y = 0;
	    
	    redraw(leftMargin, y, clientAreaWidth - leftMargin - rightMargin, h - y, true);
	}
	
    /**
     * 初始化组件
     */
    private void initialControl() {
        setCaret(caret);
		setCaretLocation();
		setCursor(beamCursor);
    }

    /**
     * 初始化变量
     */
    private void initialVariables() {
		beamCursor = getDisplay().getSystemCursor(SWT.CURSOR_IBEAM);
		caret = new Caret(this, SWT.NULL);
		keyActionMap = new HashMap<Integer, Integer>();
		userActionMap = new HashMap<Integer, Runnable>();
		clipboard = new Clipboard(getDisplay());
		imageResolver = new ImageResolver(this);
		animationManager = new AnimationManager(3, this);

		leftMargin = topMargin = rightMargin = bottomMargin = 5;		
		verticalScrollOffset = 0;
		clientAreaHeight = clientAreaWidth = 0;
		caretOffset = 0;
		resetSelection();
		topLine = 0;
		lastPaintTopLine = -1;
		autoScrollDirection = SWT.NULL;
		columnX = 0;
		gcReference = 0;
		doubleClickEnabled = true;		
		readonly = false;
		enableContextMenu = true;
		forbiddenAnimation = false;
		
		// 检测颜色功能是否正常
		Color oldBg = super.getBackground();	
		setBackground(getDisplay().getSystemColor(SWT.COLOR_WHITE));
		gtkColorError = super.getBackground() != cacheBg;		
		setBackground(oldBg);
		
		Color c = getDisplay().getSystemColor(SWT.COLOR_LIST_SELECTION);
		Color back = getBackground();
		backXorColor = new Color(getDisplay(), c.getRed() ^ back.getRed(), c.getGreen() ^ back.getGreen(), c.getBlue() ^ back.getBlue());
		
		IRichContent unwrappedContent = new RichContent(imageResolver);
		content = new WrappedContent(this, unwrappedContent, imageResolver);
		contentHelper = content.getContentHelper();
    }
	
	/**
	 * @return
	 * 		GC
	 */
	public GC getGC() {
	    checkWidget();
	    if(gcReference == 0)
	        gc = new GC(this);
	    gcReference++;
	    return gc;
	}
	
	/**
	 * 释放GC
	 */
	public void releaseGC() {
	    checkWidget();
	    gcReference--;
	    if(gcReference == 0) {
	        gc.dispose();
		    gc = null;	        
	    }
	}
	
    /**
     * @param bottomMargin The bottomMargin to set.
     */
    public void setBottomMargin(int bottomMargin) {
        this.bottomMargin = bottomMargin;
    }
    
    /**
     * @param leftMargin The leftMargin to set.
     */
    public void setLeftMargin(int leftMargin) {
        this.leftMargin = leftMargin;
    }
    
    /**
     * @param rightMargin The rightMargin to set.
     */
    public void setRightMargin(int rightMargin) {
        this.rightMargin = rightMargin;
    }
    
    /**
     * @param topMargin The topMargin to set.
     */
    public void setTopMargin(int topMargin) {
        this.topMargin = topMargin;
    }
    
    /**
     * @return Returns the bottomMargin.
     */
    public int getBottomMargin() {
        return bottomMargin;
    }
    
    /**
     * @return Returns the leftMargin.
     */
    public int getLeftMargin() {
        return leftMargin;
    }
    
    /**
     * @return Returns the rightMargin.
     */
    public int getRightMargin() {
        return rightMargin;
    }
    
    /**
     * @return Returns the topMargin.
     */
    public int getTopMargin() {
        return topMargin;
    }
    
    /* (non-Javadoc)
     * @see org.eclipse.swt.widgets.Control#setBackground(org.eclipse.swt.graphics.Color)
     */
    @Override
	public void setBackground(Color back) {
        if(back != null) {
        	if(backXorColor != null && !backXorColor.isDisposed())
        		backXorColor.dispose();
			Color c = getDisplay().getSystemColor(SWT.COLOR_LIST_SELECTION);
			backXorColor = new Color(getDisplay(), c.getRed() ^ back.getRed(), c.getGreen() ^ back.getGreen(), c.getBlue() ^ back.getBlue());
			cacheBg = back;
			super.setBackground(back);
        }
    }    
	
	@Override
	public Color getBackground() {
		return cacheBg;
	}
    
    /**
     * @return Returns the doubleClickEnabled.
     */
    public boolean isDoubleClickEnabled() {
        return doubleClickEnabled;
    }
    
    /**
     * @param doubleClickEnabled The doubleClickEnabled to set.
     */
    public void setDoubleClickEnabled(boolean doubleClickEnabled) {
        this.doubleClickEnabled = doubleClickEnabled;
    }
    
    /**
     * @return Returns the contentHelper.
     */
    public ContentHelper getContentHelper() {
        return contentHelper;
    }
    
    /**
     * @return Returns the readonly.
     */
    public boolean isReadonly() {
        return readonly;
    }
    
    /**
     * @param readonly The readonly to set.
     */
    public void setReadonly(boolean readonly) {
        this.readonly = readonly;
    }

    /**
     * 清空
     */
    public void clear() {
        setText("");
    }
    
    /**
     * @return Returns the enableContextMenu.
     */
    public boolean isEnableContextMenu() {
        return enableContextMenu;
    }
    
    /**
     * @param enableContextMenu The enableContextMenu to set.
     */
    public void setEnableContextMenu(boolean enableContextMenu) {
        this.enableContextMenu = enableContextMenu;
    }

    /**
     * @return
     */
    public ImageResolver getImageResolver() {
        return imageResolver;
    }
    
    /**
     * @return Returns the forbiddenAnimation.
     */
    public boolean isForbiddenAnimation() {
        return forbiddenAnimation;
    }
    
    /**
     * @param forbiddenAnimation The forbiddenAnimation to set.
     */
    public void setForbiddenAnimation(boolean forbiddenAnimation) {
        this.forbiddenAnimation = forbiddenAnimation;
        if(forbiddenAnimation)
            animationManager.stopAll();
    }
}
