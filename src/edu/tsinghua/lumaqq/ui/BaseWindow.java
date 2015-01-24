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

import edu.tsinghua.lumaqq.qq.events.IQQListener;
import edu.tsinghua.lumaqq.resource.Colors;
import edu.tsinghua.lumaqq.resource.Resources;

import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.events.ShellListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

/**
 * 基础窗口
 * 
 * @author luma
 */
public abstract class BaseWindow extends Window implements ShellListener {
	protected MainShell main;
	protected boolean active;
	protected Resources res;
	
	public BaseWindow(MainShell main) {
		super(main.getShell());
		this.main = main;
		this.res = Resources.getInstance();
		initializeVariables();
	}
	
	@Override
	public int open() {
		int ret = super.open();
		if(getQQListener() != null)
			main.getClient().addQQListener(getQQListener());
		onOpen();
		return ret;
	}
	
	@Override
	protected int getShellStyle() {
		return SWT.NO_TRIM | SWT.NO_BACKGROUND;
	}
	
	@Override
	protected Shell getParentShell() {
		return null;
	}
	
	@Override
	protected void configureShell(Shell newShell) {
		newShell.setText(getTitle());
		newShell.setImage(getImage());
		
    	BorderStyler styler = new BorderStyler(main);
    	styler.setShowMaxButton(true);
    	styler.setHideWhenMinimize(false);
    	styler.decorateShell(newShell);     
	}
	
	@Override
	protected ShellListener getShellListener() {
		return this;
	}
	
	protected Composite getContentContainer() {
		Composite control = ((BorderStyler)getShell().getData(BorderStyler.STYLER)).getCenter();
        GridLayout layout = new GridLayout();
        layout.horizontalSpacing = 0;
        control.setLayout(layout);
        control.setBackground(Colors.MAINSHELL_BACKGROUND);
        return control;
	}
	
    protected void initializeVariables() {
    	active = false;
    }
    
    @Override
    protected Point getInitialLocation(Point size) {
		Rectangle displayRect = getShell().getDisplay().getBounds();
		return new Point((displayRect.width - size.x)/2, (displayRect.height - size.y)/2);
    }
	
	protected abstract String getTitle();
	protected abstract Image getImage();
	protected abstract IQQListener getQQListener();
	
	protected void onOpen() {		
	}
	
	public void shellActivated(ShellEvent e) {
		active = true;
	}

	public void shellDeactivated(ShellEvent e) {
		active = false;
	}
	
	public void shellClosed(ShellEvent e) {
		if(getQQListener() != null)
			main.getClient().removeQQListener(getQQListener());
	}

	public void shellDeiconified(ShellEvent e) {
	}

	public void shellIconified(ShellEvent e) {
	}

	/**
     * 设置窗口激活
     */
    public void setActive() {
        getShell().setActive();
    }
    
    public boolean isActive() {
    	return active;
    }
    
    /**
     * 设置最小化状态
     * 
     * @param b
     */
    public void setMinimized(boolean b) {
        getShell().setMinimized(b);
    }    
    
    /**
     * 设置焦点
     */
    public void setFocus() {
        getShell().setFocus();
    }
}
