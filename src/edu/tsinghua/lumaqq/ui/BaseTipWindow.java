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

import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.MouseTrackAdapter;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.graphics.TextLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import edu.tsinghua.lumaqq.resource.Resources;
import edu.tsinghua.lumaqq.widgets.qstyle.Slat;

/**
 * 提示窗口基类
 *
 * @author luma
 */
public abstract class BaseTipWindow extends Window {
	protected MainShell main;
	protected Resources res;
	private Slat btnDefault;
	
	private int downX, downY;
	private boolean isMove;
	
	private int shellWidth;
	private int shellHeight;
	
	public BaseTipWindow(MainShell main) {
		super(main.getShell());
		this.main = main;
		this.res = Resources.getInstance();
		initializeVariables();
	}
	
	protected int getAutoCloseDelay() {
		return 5000;
	}
	
	protected boolean isAutoClose() {
		return true;
	}
	
	@Override
	protected void configureShell(Shell newShell) {
		newShell.setText(getTitle());
		newShell.setImage(getImage()); 
		newShell.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent e) {
				Shell shell = (Shell)e.getSource();
				Rectangle rect = shell.getClientArea();
				rect.width--;
				rect.height--;
				Color c = new Color(shell.getDisplay(), 0x1E, 0x3E, 0x93);
				e.gc.setForeground(c);
				e.gc.drawRectangle(0, 0, rect.width, rect.height);
				c.dispose();
				
				c = new Color(shell.getDisplay(), 0xAA, 0xE1, 0xFF);
				e.gc.setForeground(c);
				e.gc.drawRectangle(1, 1, rect.width - 2, rect.height - 2);
				c.dispose();
	
				c = new Color(shell.getDisplay(), 0x53, 0xAE, 0xF3);
				e.gc.setForeground(c);
				e.gc.drawRectangle(2, 2, rect.width - 4, rect.height - 4);
				c.dispose();		
				
				Color top = new Color(shell.getDisplay(), 0x15, 0x77, 0xD3);
				Color bottom = new Color(shell.getDisplay(), 0x68, 0xB6, 0xF7);
				String title = getTitle();
				Point extent = e.gc.textExtent(title);
				int height = Math.max(18, extent.y + 6);
				e.gc.setForeground(top);
				e.gc.setBackground(bottom);
				e.gc.fillGradientRectangle(2, 2, rect.width - 4, height, true);
				top.dispose();
				bottom.dispose();
				
				c = new Color(shell.getDisplay(), 0x4F, 0x93, 0xD5);
				e.gc.setForeground(c);
				e.gc.setBackground(shell.getBackground());
				e.gc.drawRectangle(3, 2 + height, rect.width - 6, 2 + height);
				c.dispose();
				
				Image image = getImage();
				Rectangle imageRect = image.getBounds();
				e.gc.drawImage(getImage(), 4, 2 + ((height - imageRect.height) >> 1));
				
				e.gc.setForeground(shell.getDisplay().getSystemColor(SWT.COLOR_WHITE));
				e.gc.setFont(Resources.getInstance().getDefaultFont());
				e.gc.drawString(title, 6 + imageRect.width, Math.max(5, (height - extent.y) >> 1), true);
				
				bottom = new Color(shell.getDisplay(), 0xCC, 0xEB, 0xFF);
				e.gc.setForeground(shell.getDisplay().getSystemColor(SWT.COLOR_WHITE));
				e.gc.setBackground(bottom);
				e.gc.fillGradientRectangle(3, 3 + height, rect.width - 5, rect.height - 5 - height, true);
				bottom.dispose();
				
				Rectangle btnRect = btnDefault.getBounds();
				c = new Color(shell.getDisplay(), 0x9A, 0xBD, 0xE2);
				int x = btnRect.x;
				int y = btnRect.y - 5;
				while(x < rect.width - 3) {
					e.gc.setForeground(c);
					e.gc.drawPoint(x, y);
					e.gc.setForeground(shell.getDisplay().getSystemColor(SWT.COLOR_WHITE));
					e.gc.drawPoint(x, y + 1);
					x += 3;
				}
				c.dispose();
				
				e.gc.setForeground(shell.getDisplay().getSystemColor(SWT.COLOR_BLUE));
				TextLayout layout = new TextLayout(shell.getDisplay());
				layout.setWidth(215);
				layout.setText(getTip());
				layout.setFont(res.getDefaultFont());
				layout.draw(e.gc, 3 + getLeftMargin(), 2 + height + getTopMargin());
				layout.dispose();
			}
		});
		newShell.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				downX = e.x;
				downY = e.y;
				isMove = true;	
			}
			
			@Override
			public void mouseUp(MouseEvent e) {
				isMove = false;
			}
		});
		newShell.addMouseMoveListener(new MouseMoveListener() {
			public void mouseMove(MouseEvent e) {
				if (isMove) {
					Point loc = getShell().getLocation();
					int x = loc.x + e.x - downX;
					int y = loc.y + e.y - downY;
					getShell().setLocation(x, y);
				}
			}
		});
		
		// create default button
		newShell.setLayout(new FormLayout());
		// close button
		Label lblClose = new Label(newShell, SWT.CENTER);		
		lblClose.setImage(res.getImage(Resources.bmpCloseNormal));
		FormData fd = new FormData();
		fd.top = new FormAttachment(0, 3);
		fd.height = 16;
		fd.right = new FormAttachment(100, -3);
		fd.width = 17;
		lblClose.setLayoutData(fd);
		lblClose.addMouseTrackListener(new MouseTrackAdapter() {
			@Override
			public void mouseEnter(MouseEvent e) {
				Label lbl = (Label)e.getSource();
				lbl.setImage(res.getImage(Resources.bmpCloseHover));
			}
			
			@Override
			public void mouseExit(MouseEvent e) {
				Label lbl = (Label)e.getSource();
				lbl.setImage(res.getImage(Resources.bmpCloseNormal));
			}
		});
		lblClose.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				Label lbl = (Label)e.getSource();
				lbl.setImage(res.getImage(Resources.bmpCloseDown));
			}
			
			@Override
			public void mouseUp(MouseEvent e) {
				getShell().close();
			}
		});
		btnDefault = new Slat(newShell);
		btnDefault.setText(getButtonLabel());
		fd = new FormData();
		fd.bottom = new FormAttachment(100, -6);
		fd.left = new FormAttachment(0, 6);
		btnDefault.setLayoutData(fd);
		btnDefault.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				getShell().close();
				onButton();
			}
		});
		btnDefault.setVisible(showButton());
	}
	
	private void decideShellSize(Shell newShell) {
		GC gc = new GC(newShell);
		Rectangle imgRect = getImage().getBounds();
		Point extent = gc.textExtent(getTitle());
		int titleWidth = imgRect.width + extent.x + 27;
		
		TextLayout layout = new TextLayout(newShell.getDisplay());
		layout.setWidth(215);
		layout.setText(getTip());
		
		shellWidth = Math.max(titleWidth, 215 + getLeftMargin() + getRightMargin() + 6);
		shellHeight = layout.getBounds().height + getTopMargin() + getBottomMargin() + Math.max(18, extent.y + 6) + 13 + btnDefault.getSize().y;
		
		gc.dispose();
		layout.dispose();
	}

	@Override
	protected Control createContents(Composite parent) {
		return null;
	}
	
	@Override
	protected Rectangle getConstrainedShellBounds(Rectangle preferredSize) {
		decideShellSize(getShell());
		Rectangle rect = getShell().getDisplay().getClientArea();
		rect.x = rect.width - shellWidth;
		rect.y = rect.height - shellHeight;
		rect.width = shellWidth;
		rect.height = shellHeight;
		return rect;
	}

	@Override
	public int open() {
		int ret = super.open();
		if(isAutoClose()) {
			getShell().getDisplay().timerExec(getAutoCloseDelay(), new Runnable() {
				public void run() {
					try {
						if(getShell() != null)
							getShell().close();
					} catch(Exception e) {
					}
				}
			});
		}
		onOpen();
		return ret;
	}
	
	@Override
	protected int getShellStyle() {
		return SWT.NO_TRIM | SWT.NO_BACKGROUND | SWT.ON_TOP;
	}
	
	@Override
	protected Shell getParentShell() {
		return null;
	}
	
    protected void initializeVariables() {
    }
    
	protected abstract String getTitle();
	protected abstract Image getImage();
	protected abstract String getButtonLabel();
	protected abstract String getTip();
	
	protected int getTopMargin() {
		return 30;
	}
	
	protected int getBottomMargin() {
		return 30;
	}
	
	protected int getLeftMargin() {
		return 15;
	}
	
	protected int getRightMargin() {
		return 20;
	}
	
	protected void onOpen() {		
	}
	
	protected boolean showButton() {
		return true;
	}
	
	protected void onButton() {
	}
}
