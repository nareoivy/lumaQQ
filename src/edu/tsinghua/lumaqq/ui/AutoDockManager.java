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
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseTrackAdapter;
import org.eclipse.swt.events.MouseTrackListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Shell;

public class AutoDockManager {
	/**
	 * 定时探测
	 * 
	 * @author luma
	 */
	private class DockRunnable implements Runnable {
		public void run() {
			if(status == PENDING) {
				Point loc = shell.getDisplay().getCursorLocation();
				if(shell.getBounds().contains(loc))
					shell.getDisplay().timerExec(1000, this);
				else
					dockShell(shell, dockSide);
			}
		}
	}
	
	private MainShell main;
	private Shell shell;
	private Rectangle lastBound;
	
	private int status;
	private int dockSide;
	private Runnable dockRunnable;
	
	public static final String DOCK_MANAGER = "dock_manager";
	
	public static final int DOCKING = 0;
	public static final int PENDING = 1;
	public static final int OFF = 2;
	public static final int PRE_PENDING = 3;
	
	public AutoDockManager(MainShell main) {
		this.main = main;
		dockRunnable = new DockRunnable();
	}
	
	/**
	 * 为shell添加dock支持
	 * 
	 * @param s
	 */
	public void addDockSupport(Shell s, IBorderControlProvider cp) {
		// shell如果没有no trim和on top的样式，则不添加此项功能
		shell = s;
		int desiredStyle = SWT.NO_TRIM | SWT.ON_TOP;
		if((shell.getStyle() & desiredStyle) != desiredStyle)
			return;
		
		BorderStyler styler = (BorderStyler)shell.getData(BorderStyler.STYLER);
		if(styler == null)
			return;
		
		shell.setData(DOCK_MANAGER, this);
		status = OFF;
		
		styler.addMouseListener(new MouseAdapter() {			
			@Override
			public void mouseUp(MouseEvent e) {
				if(!main.getOptionHelper().isAutoDock())
					return;
				
				dockShell(shell);
			}
		});
		shell.addControlListener(new ControlListener() {
			public void controlMoved(ControlEvent e) {
				if(status == PENDING)
					dockShell(shell); 
			}
			
			public void controlResized(ControlEvent e) {
				if(lastBound == null)
					lastBound = shell.getBounds();
			}
		});
		
		MouseTrackListener mtl = new MouseTrackAdapter() {
			@Override
			public void mouseEnter(MouseEvent e) {
				if(status == DOCKING)
					pend();
			}
		};
		cp.getLeft().addMouseTrackListener(mtl);
		cp.getRight().addMouseTrackListener(mtl);
		cp.getTop().addMouseTrackListener(mtl);
		cp.getBottom().addMouseTrackListener(mtl);
	}

	/**
	 * @param shell
	 */
	private void dockShell(Shell shell) {		
		Rectangle shellBound = shell.getBounds();
		Rectangle displayBound = shell.getDisplay().getBounds();
		
		if(status == DOCKING) {
			switch(dockSide) {
				case SWT.RIGHT:
					shell.setLocation(displayBound.width - 1, 0);
					break;
				case SWT.LEFT:
					shell.setLocation(1 - shellBound.width, 0);
					break;
				case SWT.TOP:
					shell.setLocation(shellBound.x, 1 - shellBound.height);
					break;
				case SWT.BOTTOM:
					shell.setLocation(shellBound.x, displayBound.height - 1);
					break;
			}
		} else if(status == PENDING) {
			if(dockSide == SWT.RIGHT || dockSide == SWT.LEFT)
				shell.setSize(shellBound.width, lastBound.height);
			status = OFF;
		} else if(shellBound.x + shellBound.width > displayBound.width) { // 右边
			shellBound.x = displayBound.width - 1;
			shellBound.y = 0;
			shellBound.height = shell.getDisplay().getClientArea().height;					
			shell.setBounds(shellBound);
			status = DOCKING;
			dockSide = SWT.RIGHT;
		} else if(shellBound.x < 0) { // 左边
			shellBound.x = 1 - shellBound.width;
			shellBound.y = 0;
			shellBound.height = shell.getDisplay().getClientArea().height;
			shell.setBounds(shellBound);
			status = DOCKING;
			dockSide = SWT.LEFT;
		} else if(shellBound.y < 0) { // 上边
			shellBound.y = 1 - shellBound.height;
			shell.setBounds(shellBound);
			status = DOCKING;
			dockSide = SWT.TOP;
		} else if(shellBound.y + shellBound.height > displayBound.height) { // 下边
			shellBound.y = displayBound.height - 1;
			shell.setBounds(shellBound);
			status = DOCKING;
			dockSide = SWT.BOTTOM;
		} 
	}
	
	private void dockShell(Shell shell, int side) {
		Rectangle shellBound = shell.getBounds();
		Rectangle displayBound = shell.getDisplay().getBounds();
		
		if(shellBound.x != 0 &&
				shellBound.y != 0 &&
				shellBound.x + shellBound.width != displayBound.width &&
				shellBound.y + shellBound.height != displayBound.height)
			return;
		
		status = DOCKING;
		dockSide = side;
		switch(side) {
			case SWT.RIGHT:
				shellBound.x = displayBound.width - 1;
				shellBound.y = 0;
				shellBound.height = shell.getDisplay().getClientArea().height;					
				shell.setBounds(shellBound);
				break;
			case SWT.LEFT:
				shellBound.x = 1 - shellBound.width;
				shellBound.y = 0;
				shellBound.height = shell.getDisplay().getClientArea().height;
				shell.setBounds(shellBound);
				break;
			case SWT.TOP:
				shellBound.y = 1 - shellBound.height;
				shell.setBounds(shellBound);
				break;
			case SWT.BOTTOM:
				shellBound.y = displayBound.height - 1;
				shell.setBounds(shellBound);
				break;
		}
	}

	/**
	 * @return Returns the docking.
	 */
	public boolean isDocking() {
		return status == DOCKING;
	}
	
	public boolean isPending() {
		return status == PENDING;
	}
	
	public boolean isOff() {
		return status == OFF;
	}

	/**
	 * 暂时取消dock，如果鼠标移出主窗口则回复dock
	 */
	public void pend() {
		Rectangle shellBound = shell.getBounds();
		Rectangle displayBound = shell.getDisplay().getBounds();
		
		status = PRE_PENDING;
		switch(dockSide) {
			case SWT.RIGHT:
				shell.setLocation(displayBound.width - shellBound.width, 0);
				break;
			case SWT.LEFT:
				shell.setLocation(0, 0);
				break;
			case SWT.TOP:
				shell.setLocation(shellBound.x, 0);
				break;
			case SWT.BOTTOM:
				shell.setLocation(shellBound.x, displayBound.height - shellBound.height);
				break;
		}
		status = PENDING;
		shell.getDisplay().timerExec(1000, dockRunnable);
	}
	
	/**
	 * 取消dock
	 */
	public void off() {
		Rectangle shellBound = shell.getBounds();
		Rectangle displayBound = shell.getDisplay().getBounds();
		
		switch(dockSide) {
			case SWT.RIGHT:
				shell.setBounds(displayBound.width - shellBound.width, 0, lastBound.width, lastBound.height);
				break;
			case SWT.LEFT:
				shell.setBounds(0, 0, lastBound.width, lastBound.height);
				break;
			case SWT.TOP:
				shell.setLocation(shellBound.x, 0);
				break;
			case SWT.BOTTOM:
				shell.setLocation(shellBound.x, displayBound.height - shellBound.height);
				break;
		}
		status = OFF;
	}
}
