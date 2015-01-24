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
package edu.tsinghua.lumaqq.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Shell;

import edu.tsinghua.lumaqq.LumaQQ;
import edu.tsinghua.lumaqq.QQShowManager;
import edu.tsinghua.lumaqq.models.ModelRegistry;
import edu.tsinghua.lumaqq.models.User;
import edu.tsinghua.lumaqq.resource.Colors;
import edu.tsinghua.lumaqq.resource.Resources;
import edu.tsinghua.lumaqq.ui.config.user.UserInfoWindow;
import edu.tsinghua.lumaqq.ui.helper.DrawHelper;

/**
 * <pre>
 * 好友提示窗口
 * </pre>
 * 
 * @author luma
 */
public class FriendTipShell {
	private Shell shell;
	private MainShell main;
	private Image qqShowImage;
	private Resources res;
	private String name;
	private String sig;
	private int level;
	private Cursor handCursor;
	
	private Rectangle nameRect;
	private Rectangle levelRect;
	private boolean mouseOnName;
	private boolean mouseOnLevel;

	// 是否应该关闭shell的标志，当鼠标离开好友后，应该关闭tip shell，但是不是立刻关闭，
	// 我们在这里等待1.5秒钟，如果没有什么进一步的动作，就关闭
	private boolean shouldClose;
	// 执行关闭任务的Runnable对象
	private Runnable closeRunnable = new Runnable() {
		public void run() {
			if(isShouldClose()) {
				if(shell.isVisible()) {
					Point p = main.getDisplay().getCursorLocation();
					if(shell.getBounds().contains(p)) {
						main.getDisplay().timerExec(1500, this);
					} else {
						setShouldClose(false);						
						setVisible(false);									
					}
				}
			} else
				main.getDisplay().timerExec(1500, this);
		}		
	};
		
	/**
	 * 构造函数
	 * @param main
	 */
	public FriendTipShell(MainShell main) {
	    this.main = main;
		// 初始化变量
		handCursor = main.getDisplay().getSystemCursor(SWT.CURSOR_HAND);
		shouldClose = false;
		res = Resources.getInstance();
		name = "";
		sig = "";
		// 初始化窗口布局
		initLayout();
	}
	
	/**
	 * 初始化窗口布局
	 */
	private void initLayout() {
		// 创建tip窗口
		shell = new Shell(main.getShell(), SWT.ON_TOP | SWT.NO_TRIM | SWT.NO_BACKGROUND);
		shell.addPaintListener(new PaintListener() {
            public void paintControl(PaintEvent e) {
            	GC gc = null;
            	Image buffer = null;
            	if(LumaQQ.DOUBLE_BUFFER) {
            		buffer = new Image(e.gc.getDevice(), 236, 156);
            		gc = new GC(buffer);
            	} else
            		gc = e.gc;
            	
            	gc.drawImage(res.getImage(Resources.bmpTipBackground), 0, 0);
            	if(qqShowImage != null)
            		DrawHelper.drawImage(gc, qqShowImage, 6, 16, 73, 109, false, true, 1, Colors.QQ_SHOW_BORDER);
            	
            	int x = 6 + 73 + 6;
            	int y = 16;
            	nameRect = DrawHelper.drawString(gc, 
            			name, 
            			x, 
            			y, 
            			Integer.MAX_VALUE, 
            			-1, 
            			mouseOnName ? res.getItalicDefaultFont() : res.getDefaultFont(), 
            			Colors.BLUE);
            	y += nameRect.height;
            	
            	y += 2;
            	DrawHelper.drawString(gc, 
            			sig, 
            			x, 
            			y, 
            			236 - x - 3,
            			100 - y, 
            			res.getDefaultFont(), 
            			Colors.GRAY_TEXT);
            	y = 101;
            	
            	DrawHelper.drawSeparator(gc, x, y, 236 - x - 3);
            	y += 6;
            	
            	levelRect = DrawHelper.drawLevel(gc, level, x, y);
            	
            	if(LumaQQ.DOUBLE_BUFFER) {
            		e.gc.drawImage(buffer, 0, 0);
            		gc.dispose();
            		buffer.dispose();
            	}
            }		    
		});
		shell.addMouseMoveListener(new MouseMoveListener() {
			public void mouseMove(MouseEvent e) {
				if(nameRect != null) {
					boolean b = nameRect.contains(e.x, e.y);
					if(mouseOnName != b) {
						mouseOnName = b;
						shell.setCursor(mouseOnName ? handCursor : shell.getDisplay().getSystemCursor(SWT.CURSOR_ARROW));
						shell.redraw();
					}					
				}
				if(levelRect != null) {
					boolean b = levelRect.contains(e.x, e.y);
					if(mouseOnLevel != b) {
						mouseOnLevel = b;
						shell.setToolTipText(b ? ("Level: " + level) : null);
					}
				}
			}
		});
		shell.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				if(nameRect != null) {
					if(nameRect.contains(e.x, e.y)) {
						setVisible(false);
						User f = (User)shell.getData();
						main.getShellLauncher().openUserInfoWindow(f, UserInfoWindow.READ_ONLY);
					}						
				}
			}
		});
		
		// 设置大小
		shell.setSize(236, 156);
	}
	
	/**
	 * 设置窗口位置
	 * @param p
	 */
	public void setLocation(Point p) {
	    shell.setLocation(p);
	}
	
	/**
	 * 设置可见状态
	 * @param b
	 */
	public void setVisible(boolean b) {
	    shell.setVisible(b);
	    if(b)
	    	main.getDisplay().timerExec(1500, closeRunnable); 
	    else {
			mouseOnName = false;
			mouseOnLevel = false;
			shell.setToolTipText(null);
			shell.setCursor(shell.getDisplay().getSystemCursor(SWT.CURSOR_ARROW));
	    }
	}
	
	/**
	 * @return true表示可见
	 */
	public boolean isVisible() {
	    return shell.isVisible();
	}
	
	/**
	 * 重新布局
	 */
	public void layout() {
	    shell.layout();
	}

	/**
	 * 设置Data
	 * @param obj
	 */
	public void setData(Object obj) {
	    shell.setData(obj);
	}
	
	/**
	 * 把好友model中的信息填充到tip shell中 
	 * @param f
	 */
	public void setModel(User f) {
		// 设置各标签信息
		// 备注名称
		User u = ModelRegistry.getUser(f.qq);
		if(u != null) {
			if(!u.getRemarkName().equals(""))
				name = u.nick + '(' + u.getRemarkName() + ')' + '(' + u.qq + ')';
			else
				name = u.nick + '(' + u.qq + ')';	
		}
		// 个性签名
		if(u.hasSignature && u.signature != null)
			sig = u.signature;
		else
			sig = "";
		// 等级
		level = u.level;
		// QQ秀
		QQShowManager sm = QQShowManager.getInstance();
		if(sm.isCached(f.qq))
			qqShowImage = sm.getQQShowImage(f.qq);
		else
			qqShowImage = res.getImage(Resources.bmpDefaultQQShow);
		
		shell.redraw();
	}
	
	/**
	 * 设置是否应该关闭tip shell
	 * @param b
	 */
	public synchronized void setShouldClose(boolean b) {
		shouldClose = b;
	}
	
	/**
	 * @return true如果应该关闭
	 */
	public synchronized boolean isShouldClose() {
		return shouldClose;
	}
	
	/**
	 * 立刻关闭提示窗口
	 */
	public void closeNow() {
		setShouldClose(false);
		if(shell.isVisible() && !shell.isDisposed())
			setVisible(false);
	}
    
    /**
     * @return
     * 		true如果shell资源已经释放
     */
    public boolean isDisposed() {
    	return shell.isDisposed();
    }
    
    /**
     * 设置窗口大小
     * @param width
     * @param height
     */
    public void setSize(int width, int height) {
        shell.setSize(width, height);
    }
}
