/*
 * LumaQQ - Java QQ Client
 *
 * Copyright (C) 2004 whg2001 <whg_2001@sohu.com>
 * 					 luma <stubma@163.com>
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

import static edu.tsinghua.lumaqq.resource.Messages.*;

import java.util.LinkedList;
import java.util.Queue;

import org.eclipse.osgi.util.NLS;
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
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import edu.tsinghua.lumaqq.LumaQQ;
import edu.tsinghua.lumaqq.models.User;
import edu.tsinghua.lumaqq.resource.Colors;
import edu.tsinghua.lumaqq.resource.Resources;
import edu.tsinghua.lumaqq.ui.config.sys.SystemOptionWindow;
import edu.tsinghua.lumaqq.ui.helper.DrawHelper;
import edu.tsinghua.lumaqq.ui.helper.FaceRegistry;
import edu.tsinghua.lumaqq.ui.helper.HeadFactory;

/**
 * 好友上线提示窗口
 * 
 * @author 王华刚 luma 
 */
public class OnlineTipShell  {
	private Shell shell;
	private Display display;
	private Resources res;
	//需要显示的好友队列
	private Queue<User> friendLink;
	// 鼠标按下时，设置此变量为true，表示处于移动窗口状态
	private boolean isMove;
	// 鼠标按下时的位置
	private int downX, downY;
	// hand cursor
	private Cursor handCursor;
	private MainShell main;
	
	private String name;
	private Rectangle nameRect;
	private boolean mouseOnName;
	private Rectangle settingRect;
	private boolean mouseOnSetting;
	
	private static final Rectangle CLOSE_RECT = new Rectangle(156, 4, 7, 6);
	private boolean mouseOnClose;

	// 执行检查需要显示的队列并显示的Runnable对象
	private Runnable showRunnable = new Runnable() {
		public void run() {
	        // 得到下一个要显示的好友model
		    User f = getNextFriendModel();
		    // model为null，说明没有更多的好友要显示，关闭窗口
		    if(f == null) {
				Point p = display.getCursorLocation();
				// 但是如果这个时候鼠标在窗口之上，不关闭
				// 不在，则关闭
				if(!shell.getBounds().contains(p)) 
				    closeNow();
				else
				    display.timerExec(5000, this);
		    } else if(shell.isVisible()) {
			    // 显示
				setUser(f);
				// 等五秒再调用自己一次
				display.timerExec(5000, this);
			}		        
		}
	};

	/**
	 * 添加要显示的好友到队列
	 * @param f
	 */
	public void addFriendModel(User f) {
		synchronized(friendLink) {
			friendLink.offer(f);
			if(!shell.isVisible()) {
				setUser(f);
			    show();
			    display.asyncExec(showRunnable);
			}
		}
	}
	
	/**
	 * @return 下一个要显示的好友model，没有则返回null
	 */
	private User getNextFriendModel() {
	    synchronized(friendLink) {
			return friendLink.poll();
	    }
	}

	/**
	 * 构造函数
	 * @param main
	 */
	public OnlineTipShell(MainShell main) {
		// 初始化变量
		this.main = main;
		this.display = main.getDisplay();
		isMove = false;
		friendLink = new LinkedList<User>();
		res = Resources.getInstance();
		handCursor = display.getSystemCursor(SWT.CURSOR_HAND);
		mouseOnClose = false;
		mouseOnName = false;
		mouseOnSetting = false;
		name = "";
		// 初始化窗口布局
		initLayout();
	}

	/**
	 * 立刻关闭提示窗口
	 */
	private void closeNow() {
		synchronized(friendLink) {
	        // 设置窗口为不可见 
			shell.setVisible(false);
	        // 清空可能遗留下来的model
	        friendLink.clear();		    
		}
	}
	
	/**
	 * 打开提示窗口
	 */
	private void setUser(User f) {
		shell.setData(f);
		// 设置用户信息
		name = NLS.bind(onlinetip_user, f.displayName, String.valueOf(f.qq));
		shell.redraw();
	}
	
	/**
	 * 打开窗口
	 */
	public void show() {
	    if(!shell.isVisible()) {
	        // 得到上站通知的显示位置，-1表示缺省
	        int x = main.getOptionHelper().getOnlineTipLocationX();
	        int y = main.getOptionHelper().getOnlineTipLocationY();
		    // 得到屏幕客户区的大小，之所以是客户区是因为任务条之类的东西不计算在内
	        //    不过这个在Linux下面好像没有效果
			Rectangle displayRect = display.getClientArea();
			Point size = shell.getSize();
			// 如果有变量等于-1，采用缺省值
	        if(x == -1)
	            x = displayRect.width - size.x - 2;
	        if(y == -1)
	            y = displayRect.height - size.y - 2;
	        // 设置shell位置
			shell.setLocation(x, y);
			// 设置shell为可见
		    shell.setVisible(true);
	    }
	}
	
	/**
	 * @return
	 * 	true表示窗口已经disposed
	 */
	public boolean isDisposed() {
	    return shell.isDisposed();
	}

	/**
	 * 初始化窗口布局
	 */
	private void initLayout() {
		// 创建tip窗口
		shell = new Shell(display, SWT.ON_TOP | SWT.NO_TRIM | SWT.NO_BACKGROUND);
		shell.setSize(167, 74);
		shell.addMouseMoveListener(new MouseMoveListener() {
			public void mouseMove(MouseEvent e) {
				if (isMove) {
					Point loc = shell.getLocation();
					int x = loc.x + e.x - downX;
					int y = loc.y + e.y - downY;
					shell.setLocation(x, y);
					main.getOptionHelper().setOnlineTipLocationX(x);
					main.getOptionHelper().setOnlineTipLocationY(y);
				} else {
					boolean onClose = CLOSE_RECT.contains(e.x, e.y);
					boolean onName = nameRect.contains(e.x, e.y);
					boolean onSetting = settingRect.contains(e.x, e.y);
					boolean redraw = onName != mouseOnName;
					
					if(onClose != mouseOnClose)
						mouseOnClose = onClose;
					if(redraw)
						mouseOnName = onName;
					if(onSetting != mouseOnSetting)
						mouseOnSetting = onSetting;
					
					shell.setCursor((onClose || onName || onSetting) ? handCursor : null);	
					if(redraw)
						shell.redraw();
				}
			}
		});
		shell.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				if(mouseOnClose)
					closeNow();
				else if(nameRect.contains(e.x, e.y)) {
					mouseOnName = false;
					mouseOnSetting = false;
					mouseOnClose = false;
					closeNow();
					main.getShellLauncher().openNormalIMWindow((User)shell.getData());
				} else if(settingRect.contains(e.x, e.y)) {
					mouseOnName = false;
					mouseOnSetting = false;
					mouseOnClose = false;
			        closeNow();
			        main.getShellLauncher().openSystemOptionWindow().setCurrentPage(SystemOptionWindow.GUI);
				} else {
					downX = e.x;
					downY = e.y;
					isMove = true;					
				}
			}
			@Override
			public void mouseUp(MouseEvent e) {
				isMove = false;
			}
		});
		shell.addPaintListener(new PaintListener() {
            public void paintControl(PaintEvent e) {
            	GC gc = null;
            	Image buffer = null;
            	if(LumaQQ.DOUBLE_BUFFER) {
            		buffer = new Image(e.gc.getDevice(), 236, 156);
            		gc = new GC(buffer);
            	} else
            		gc = e.gc;
            	
                gc.drawImage(res.getImage(Resources.bmpOnlineTipBackground), 0, 0);
                
                int x = 12;
                int y = 21;
                FaceRegistry reg = FaceRegistry.getInstance();
                User u = (User)shell.getData();
                if(u != null) {
                	Image face = (u.hasCustomHead && reg.hasFace(reg.getMd5ById(u.customHeadId))) ? res.getCustomHead(u.customHeadId, false) : HeadFactory.getOnlineHead(u);
                	DrawHelper.drawImage(gc, 
                			face,
                			x,
                			y,
                			40,
                			40,
                			false,
                			true,
                			1,
                			Colors.BLACK);                	
                }
                
                x += 45;
                nameRect = DrawHelper.drawString(gc, 
                		name,
                		x, 
                		y,
                		167 - x - 3,
                		55 - y,
                		mouseOnName ? res.getItalicDefaultFont() : res.getDefaultFont(), 
                		Colors.BLACK);
                
                x = 129;
                y = 55;
                settingRect = DrawHelper.drawString(gc,
                		onlinetip_setting,
                		x, 
                		y,
                		167 - x - 3,
                		-1,
                		res.getDefaultFont(),
                		Colors.LIGHT_BLUE);
                
            	if(LumaQQ.DOUBLE_BUFFER) {
            		e.gc.drawImage(buffer, 0, 0);
            		gc.dispose();
            		buffer.dispose();
            	}
            }		    
		});
	}
}