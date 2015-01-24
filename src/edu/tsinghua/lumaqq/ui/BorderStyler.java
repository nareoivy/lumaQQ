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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.MouseTrackAdapter;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import edu.tsinghua.lumaqq.LumaQQ;
import edu.tsinghua.lumaqq.resource.Colors;
import edu.tsinghua.lumaqq.resource.Resources;

/**
 * 用来给一个窗口加上自定义边框
 * 
 * @author luma
 */
public class BorderStyler implements IBorderControlProvider {
	private Resources res;
	private Display display;
	private Composite center;
	private MainShell main;
	
	private int downX, downY;
	private boolean isMove;
	
	private boolean hideWhenMinimize;
	private boolean resizable;
	private boolean showMaxButton; 
	private boolean showMinButton;
	private boolean checkMinimizeWhenClose;
	private boolean maximizeWhenDoubleClick;
	
	public static final String STYLER = "styler";
	private Label top;
	private Label left;
	private Label right;
	private Label bottom;
	private Shell shell;
	
	private List<MouseListener> mouseListeners;
	private Composite topDown;
	private Rectangle oldBound;
	private Label lblMax;
	
	private int minWidth, minHeight;
	
	/**
	 * 构造函数
	 * 
	 * @param main
	 */
	public BorderStyler(MainShell main) {
		this.main = main;
		display = main.getDisplay();
		initializeVariables();
	}
	
	/**
	 * 构造函数
	 */
	public BorderStyler() {
		display = Display.getCurrent();
		initializeVariables();
	}
	
	/**
	 * 初始化变量
	 */
	protected void initializeVariables() {
		res = Resources.getInstance();
		hideWhenMinimize = true;
		resizable = true;
		showMaxButton = false;
		showMinButton = true;
		checkMinimizeWhenClose = false;
		maximizeWhenDoubleClick = true;
		minWidth = minHeight = 100;
	}
	
	public void addMouseListener(MouseListener ml) {
		if(mouseListeners == null)
			mouseListeners = new ArrayList<MouseListener>();
		mouseListeners.add(ml);
	}
	
	/**
	 * 重画标题条
	 */
	public void repaintTitleBar() {
		topDown.redraw();
	}
	
	/**
	 * 设置窗口位置和大小，提供最小尺寸检查
	 * 
	 * @param bound
	 */
	private void setShellBound(Rectangle bound) {
		bound.width = Math.max(minWidth, bound.width);
		bound.height = Math.max(minHeight, bound.height);
		shell.setBounds(bound);
	}
	
	/**
	 * 设置窗口大小，提供最小尺寸检查
	 * 
	 * @param size
	 */
	private void setShellSize(Point size) {
		size.x = Math.max(minWidth, size.x);
		size.y = Math.max(minHeight, size.y);
		shell.setSize(size);
	}
	
	/**
	 * 装饰一个shell
	 * 
	 * @param shell
	 * @return
	 * 		内容区容器
	 */
	public Composite decorateShell(Shell s) {	
		shell = s;
		shell.setData(STYLER, this);
		
		// 设置layout
		GridLayout layout = new GridLayout(3, false);//三列
		layout.marginHeight = layout.marginWidth = layout.verticalSpacing = layout.horizontalSpacing = 0;
		shell.setLayout(layout);
		//左边的角 -- Shell 左边的一个小方角用Composite来绘制
		Composite leftTop = new Composite(shell, SWT.NONE | SWT.NO_BACKGROUND);
//		if(resizable)
//			leftTop.setCursor(display.getSystemCursor(SWT.CURSOR_SIZENWSE));
		GridData gd = new GridData();//设置部局
		gd.widthHint = gd.heightHint = 5; //设置高与宽都为5像素
		leftTop.setLayoutData(gd);
		leftTop.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent e) {
				Rectangle bound = ((Control) e.getSource()).getBounds();
				int i = 0;
				for(Color c : Colors.MAINSHELL_BORDERS) {
					e.gc.setForeground(c);
					e.gc.drawLine(i, i, bound.width, i);
					e.gc.drawLine(i, i, i, bound.height);
					i++;
				}
			}
		});
		if(resizable) {
			leftTop.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseDown(MouseEvent e) {
					Control control = (Control)e.getSource();
					Point loc = control.toDisplay(e.x, e.y);
					downX = loc.x;
					downY = loc.y;
				}
				@Override
				public void mouseUp(MouseEvent e) {
					Control control = (Control)e.getSource();
					Point loc = control.toDisplay(e.x, e.y);
					int dx = downX - loc.x;
					int dy = downY - loc.y;
					if(dx == 0 && dy == 0)
						return;
					Rectangle bound = shell.getBounds();
					bound.x -= dx;					
					bound.y -= dy;
					bound.width += dx;
					bound.height += dy;					
					setShellBound(bound);
				}
			});			
		}
		
		top = new Label(shell, SWT.LEFT);
		if(resizable)
			top.setCursor(display.getSystemCursor(SWT.CURSOR_SIZENS));
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.heightHint = 5;
		top.setLayoutData(gd);
		top.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent e) {				
				Rectangle bound = ((Control) e.getSource()).getBounds();
				int i = 0;
				for(Color c : Colors.MAINSHELL_BORDERS) {
					e.gc.setForeground(c);
					e.gc.drawLine(0, i, bound.width, i);
					i++;
				}
			}
		});
		if(resizable) {
			top.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseDown(MouseEvent e) {
					Control control = (Control)e.getSource();
					downY = control.toDisplay(e.x, e.y).y;
				}
				@Override
				public void mouseUp(MouseEvent e) {
					Control control = (Control)e.getSource();
					int newY = control.toDisplay(e.x, e.y).y;
					int dy = downY - newY;
					if(dy == 0)
						return;
					Rectangle bound = shell.getBounds();
					bound.y -= dy;
					bound.height += dy;
					setShellBound(bound);
				}
			});			
		}
		
		Composite rightTop = new Composite(shell, SWT.NONE | SWT.NO_BACKGROUND);
		if(resizable)
			rightTop.setCursor(display.getSystemCursor(SWT.CURSOR_SIZENESW));
		gd = new GridData();
		gd.widthHint = gd.heightHint = 5;
		rightTop.setLayoutData(gd);
		rightTop.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent e) {
				Rectangle bound = ((Control) e.getSource()).getBounds();
				int i = 0;
				for(Color c : Colors.MAINSHELL_BORDERS) {
					e.gc.setForeground(c);
					e.gc.drawLine(bound.width - i - 1, i, 0, i);
					e.gc.drawLine(bound.width - i - 1, i, bound.width - i - 1, bound.height);
					i++;
				}
			}
		});
		if(resizable) {
			rightTop.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseDown(MouseEvent e) {
					Control control = (Control)e.getSource();
					Point loc = control.toDisplay(e.x, e.y);
					downX = loc.x;
					downY = loc.y;
				}
				@Override
				public void mouseUp(MouseEvent e) {
					Control control = (Control)e.getSource();
					Point loc = control.toDisplay(e.x, e.y);
					int dx = loc.x - downX; 
					int dy = downY - loc.y;
					if(dx == 0 && dy == 0)
						return;
					Rectangle bound = shell.getBounds();			
					bound.y -= dy;
					bound.width += dx;
					bound.height += dy;					
					setShellBound(bound);
				}
			});			
		}
		
		left = new Label(shell, SWT.LEFT);
		gd = new GridData(GridData.FILL_VERTICAL);
		gd.widthHint = 5;
		gd.verticalSpan = 2;
		left.setLayoutData(gd);
		if(resizable)
			left.setCursor(display.getSystemCursor(SWT.CURSOR_SIZEWE));
		left.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent e) {				
				Rectangle bound = ((Control) e.getSource()).getBounds();
				int i = 0;
				for(Color c : Colors.MAINSHELL_BORDERS) {
					e.gc.setForeground(c);
					e.gc.drawLine(i, 0, i, bound.height);
					i++;
				}
			}
		});
		if(resizable) {
			left.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseDown(MouseEvent e) {
					Control control = (Control)e.getSource();
					downX = control.toDisplay(e.x, e.y).x;
				}
				@Override
				public void mouseUp(MouseEvent e) {
					Control control = (Control)e.getSource();
					int newX = control.toDisplay(e.x, e.y).x;
					int dx = downX - newX;
					if(dx == 0)
						return;
					Rectangle bound = shell.getBounds();
					bound.x -= dx;
					bound.width += dx;
					setShellBound(bound);
				}
			});			
		}
		
		topDown = new Composite(shell, SWT.LEFT);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.heightHint = 23;
		topDown.setLayoutData(gd);
		topDown.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent e) {
				Rectangle bound = ((Control)e.getSource()).getBounds();
				e.gc.setForeground(Colors.MAINSHELL_TITLE_TOP);
				e.gc.setBackground(Colors.MAINSHELL_TITLE_BOTTOM);
				e.gc.fillGradientRectangle(0, 0, bound.width, bound.height, true);
				
				e.gc.setForeground(Colors.MAINSHELL_TITLE_SEPARATOR_TOP);
				e.gc.drawLine(2, 19, bound.width - 2, 19);
				e.gc.setForeground(Colors.MAINSHELL_TITLE_SEPARATOR_BOTTOM);
				e.gc.drawLine(2, 20, bound.width - 2, 20);	
				
				Image image = shell.getImage();
				Rectangle imgBound = (image == null) ? new Rectangle(0, 0, 16, 16) : image.getBounds();
				if(image != null) {
					e.gc.drawImage(image, 2, (18 - imgBound.height) >> 1);
				}
				
				String text = shell.getText();
				if(text != null) {
					String name = JFaceResources.getDefaultFont().getFontData()[0].getName();
					e.gc.setForeground(Colors.WHITE);
					e.gc.setFont(JFaceResources.getFontRegistry().getBold(name));
					Point extent = e.gc.textExtent(text);
					e.gc.drawString(text, imgBound.width + 7, (18 - extent.y) >> 1, true);
				}
			}
		});
		topDown.setLayout(new FormLayout());
		topDown.addMouseListener(new MouseListener() {
			public void mouseDown(MouseEvent e) {
				downX = e.x;
				downY = e.y;
				isMove = true;	
				fireMouseDownEvent(e);
			}
			
			public void mouseUp(MouseEvent e) {
				isMove = false;
				fireMouseUpEvent(e);
			}
			
			public void mouseDoubleClick(MouseEvent e) {
				if(maximizeWhenDoubleClick && showMaxButton) 
					doMaximize();
			}
		});
		topDown.addMouseMoveListener(new MouseMoveListener() {
			public void mouseMove(MouseEvent e) {
				if (isMove) {
					Point loc = shell.getLocation();
					int x = loc.x + e.x - downX;
					int y = loc.y + e.y - downY;
					shell.setLocation(x, y);
				}
			}
		});
		
		// 关闭按钮
		Label lblClose = new Label(topDown, SWT.CENTER);		
		lblClose.setImage(res.getImage(Resources.bmpCloseNormal));
		FormData fd = new FormData();
		fd.top = new FormAttachment(0, 0);
		fd.bottom = new FormAttachment(0, 16);
		fd.right = new FormAttachment(100, -2);
		fd.left = new FormAttachment(100, -19);
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
				if(checkMinimizeWhenClose && main != null) {
					if(main.getOptionHelper().isMinimizeWhenClose()) {
						Label lbl = (Label)e.getSource();
						lbl.setImage(res.getImage(Resources.bmpCloseHover));
						
						shell.setMinimized(true);
						shell.setVisible(false);
					} else
						shell.close();
				} else
					shell.close();
			}
		});
		
		Label referenceLabel = lblClose;
		
		// 最大化按钮
		if(showMaxButton) {
			lblMax = new Label(topDown, SWT.CENTER);
			lblMax.setImage(res.getImage(Resources.bmpMaxNormal));
			fd = new FormData();
			fd.top = new FormAttachment(0, 0);
			fd.bottom = new FormAttachment(0, 16);
			fd.right = new FormAttachment(referenceLabel, 0, SWT.LEFT);
			fd.left = new FormAttachment(referenceLabel, -17, SWT.LEFT);
			lblMax.setLayoutData(fd);
			lblMax.addMouseTrackListener(new MouseTrackAdapter() {
				@Override
				public void mouseEnter(MouseEvent e) {
					Label lbl = (Label)e.getSource();
					lbl.setImage(shell.getMaximized() ? res.getImage(Resources.bmpRestoreHover) : res.getImage(Resources.bmpMaxHover));
				}
				
				@Override
				public void mouseExit(MouseEvent e) {
					Label lbl = (Label)e.getSource();
					lbl.setImage(shell.getMaximized() ? res.getImage(Resources.bmpRestoreNormal) : res.getImage(Resources.bmpMaxNormal));
				}
			});
			lblMax.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseDown(MouseEvent e) {
					Label lbl = (Label)e.getSource();
					lbl.setImage(shell.getMaximized() ? res.getImage(Resources.bmpRestoreDown) : res.getImage(Resources.bmpMaxDown));
				}
				
				@Override
				public void mouseUp(MouseEvent e) {
					doMaximize();
				}
			});
			
			referenceLabel = lblMax;
		}		
		
		// 最小化按钮
		if(showMinButton) {
			Label lblMin = new Label(topDown, SWT.CENTER);		
			lblMin.setImage(res.getImage(Resources.bmpMinNormal));
			fd = new FormData();
			fd.top = new FormAttachment(0, 0);
			fd.bottom = new FormAttachment(0, 16);
			fd.right = new FormAttachment(referenceLabel, 0, SWT.LEFT);
			fd.left = new FormAttachment(referenceLabel, -16, SWT.LEFT);
			lblMin.setLayoutData(fd);
			lblMin.addMouseTrackListener(new MouseTrackAdapter() {
				@Override
				public void mouseEnter(MouseEvent e) {
					Label lbl = (Label)e.getSource();
					lbl.setImage(res.getImage(Resources.bmpMinHover));
				}
				
				@Override
				public void mouseExit(MouseEvent e) {
					Label lbl = (Label)e.getSource();
					lbl.setImage(res.getImage(Resources.bmpMinNormal));
				}
			});
			lblMin.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseDown(MouseEvent e) {
					Label lbl = (Label)e.getSource();
					lbl.setImage(res.getImage(Resources.bmpMinDown));
				}
				
				@Override
				public void mouseUp(MouseEvent e) {
					Label lbl = (Label)e.getSource();
					lbl.setImage(res.getImage(Resources.bmpMinHover));
					shell.setMinimized(true);
					if(hideWhenMinimize)
						shell.setVisible(false);
				}
			});			
			
			referenceLabel = lblMin;
		}

		right = new Label(shell, SWT.LEFT);
		gd = new GridData(GridData.FILL_VERTICAL);
		gd.widthHint = 5;
		gd.verticalSpan = 2;
		right.setLayoutData(gd);
		if(resizable)
			right.setCursor(display.getSystemCursor(SWT.CURSOR_SIZEWE));
		right.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent e) {
				Rectangle bound = ((Control) e.getSource()).getBounds();
				int i = 4;
				for(Color c : Colors.MAINSHELL_BORDERS) {
					e.gc.setForeground(c);
					e.gc.drawLine(i, 0, i, bound.height);
					i--;
				}
			}
		});
		if(resizable) {
			right.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseDown(MouseEvent e) {
					Control control = (Control)e.getSource();
					downX = control.toDisplay(e.x, e.y).x;
				}
				@Override
				public void mouseUp(MouseEvent e) {
					Control control = (Control)e.getSource();
					int newX = control.toDisplay(e.x, e.y).x;
					int dx = newX - downX;
					if(dx == 0)
						return;
					Point size = shell.getSize();
					size.x += dx;
					setShellSize(size);
				}
			});			
		}
		
		center = new Composite(shell, SWT.NONE);
		center.setLayoutData(new GridData(GridData.FILL_BOTH));
		layout = new GridLayout();
		layout.marginHeight = layout.marginWidth = layout.horizontalSpacing = layout.verticalSpacing = 0;
		center.setLayout(layout);

		Composite leftBottom = new Composite(shell, SWT.NONE | SWT.NO_BACKGROUND);
		if(resizable)
			leftBottom.setCursor(display.getSystemCursor(SWT.CURSOR_SIZENESW));
		gd = new GridData();
		gd.widthHint = gd.heightHint = 5;
		leftBottom.setLayoutData(gd);
		leftBottom.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent e) {
				Rectangle bound = ((Control) e.getSource()).getBounds();
				int i = 0;
				for(Color c : Colors.MAINSHELL_BORDERS) {
					e.gc.setForeground(c);
					e.gc.drawLine(i, bound.height - i - 1, i, 0);
					e.gc.drawLine(i, bound.height - i - 1, bound.width, bound.height - i - 1);
					i++;
				}
			}
		});
		if(resizable) {
			leftBottom.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseDown(MouseEvent e) {
					Control control = (Control)e.getSource();
					Point loc = control.toDisplay(e.x, e.y);
					downX = loc.x;
					downY = loc.y;
				}
				@Override
				public void mouseUp(MouseEvent e) {
					Control control = (Control)e.getSource();
					Point loc = control.toDisplay(e.x, e.y);
					int dx = downX - loc.x;
					int dy = loc.y - downY;
					if(dx == 0 && dy == 0)
						return;
					Rectangle bound = shell.getBounds();
					bound.x -= dx;					
					bound.width += dx;
					bound.height += dy;					
					setShellBound(bound);
				}
			});			
		}
		
		bottom = new Label(shell, SWT.LEFT);
		if(resizable)
			bottom.setCursor(display.getSystemCursor(SWT.CURSOR_SIZENS));
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.heightHint = 5;
		bottom.setLayoutData(gd);
		bottom.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent e) {
				Rectangle bound = ((Control) e.getSource()).getBounds();
				int i = 0;
				for(Color c : Colors.MAINSHELL_BORDERS) {
					e.gc.setForeground(c);
					e.gc.drawLine(0, 5 - i - 1, bound.width, 5 - i - 1);
					i++;
				}
			}
		});
		if(resizable) {
			bottom.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseDown(MouseEvent e) {
					Control control = (Control)e.getSource();
					downY = control.toDisplay(e.x, e.y).y;
				}
				@Override
				public void mouseUp(MouseEvent e) {
					Control control = (Control)e.getSource();
					int newY = control.toDisplay(e.x, e.y).y;
					int dy = newY - downY;	
					if(dy == 0)
						return;
					Point size = shell.getSize();
					size.y += dy;
					setShellSize(size);
				}
			});			
		}
		
		Composite rightBottom = new Composite(shell, SWT.NONE | SWT.NO_BACKGROUND);
		if(resizable)
			rightBottom.setCursor(display.getSystemCursor(SWT.CURSOR_SIZENWSE));
		gd = new GridData();
		gd.widthHint = gd.heightHint = 5;
		rightBottom.setLayoutData(gd);
		rightBottom.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent e) {
				Rectangle bound = ((Control) e.getSource()).getBounds();
				int i = 0;
				for(Color c : Colors.MAINSHELL_BORDERS) {
					e.gc.setForeground(c);
					e.gc.drawLine(bound.width - i - 1, bound.height - i - 1, bound.width - i - 1, 0);
					e.gc.drawLine(bound.width - i - 1, bound.height - i - 1, 0, bound.height - i - 1);
					i++;
				}
			}
		});
		if(resizable) {
			rightBottom.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseDown(MouseEvent e) {
					Control control = (Control)e.getSource();
					Point loc = control.toDisplay(e.x, e.y);
					downX = loc.x;
					downY = loc.y;
				}
				@Override
				public void mouseUp(MouseEvent e) {
					Control control = (Control)e.getSource();
					Point loc = control.toDisplay(e.x, e.y);
					int dx = loc.x - downX;
					int dy = loc.y - downY;
					if(dx == 0 && dy == 0)
						return;
					Point size = shell.getSize();
					size.x += dx;
					size.y += dy;
					setShellSize(size);
				}
			});			
		}
		
		return center;
	}

	/**
	 * 最大化或还原
	 */
	protected void doMaximize() {
		// 不知道为什么，在linux下面setMaximize方法没作用，nnd
		// 而且setBounds之后还需要setLocation，没办法
		if(LumaQQ.IS_GTK) {
			if(shell.getMaximized()) {						
				shell.setBounds(oldBound);	
				shell.setLocation(oldBound.x, oldBound.y);
				shell.setMaximized(false);
			} else {
				oldBound = shell.getBounds();
				shell.setBounds(display.getClientArea());
				shell.setMaximized(true);
			}
		} else
			shell.setMaximized(!shell.getMaximized());
		lblMax.setImage(shell.getMaximized() ? res.getImage(Resources.bmpRestoreHover) : res.getImage(Resources.bmpMaxHover));
	}

	protected void fireMouseUpEvent(MouseEvent e) {
		if(mouseListeners == null)
			return;
		
		for(MouseListener ml : mouseListeners)
			ml.mouseUp(e);
	}

	protected void fireMouseDownEvent(MouseEvent e) {
		if(mouseListeners == null)
			return;
		
		for(MouseListener ml : mouseListeners)
			ml.mouseDown(e);
	}

	/**
	 * @return Returns the hideWhenMinimize.
	 */
	public boolean isHideWhenMinimize() {
		return hideWhenMinimize;
	}

	/**
	 * @param hideWhenMinimize The hideWhenMinimize to set.
	 */
	public void setHideWhenMinimize(boolean hideWhenMinimize) {
		this.hideWhenMinimize = hideWhenMinimize;
	}

	/**
	 * @return Returns the resizable.
	 */
	public boolean isResizable() {
		return resizable;
	}

	/**
	 * @param resizable The resizable to set.
	 */
	public void setResizable(boolean resizable) {
		this.resizable = resizable;
	}

	/**
	 * @return Returns the showMaxButton.
	 */
	public boolean isShowMaxButton() {
		return showMaxButton;
	}

	/**
	 * @param showMaxButton The showMaxButton to set.
	 */
	public void setShowMaxButton(boolean showMaxButton) {
		this.showMaxButton = showMaxButton;
	}

	public Control getLeft() {
		return left;
	}

	public Control getRight() {
		return right;
	}

	public Control getTop() {
		return top;
	}

	public Control getBottom() {
		return bottom;
	}

	/**
	 * @return Returns the center.
	 */
	public Composite getCenter() {
		return center;
	}

	/**
	 * @return Returns the checkMinimizeWhenClose.
	 */
	public boolean isCheckMinimizeWhenClose() {
		return checkMinimizeWhenClose;
	}

	/**
	 * @param checkMinimizeWhenClose The checkMinimizeWhenClose to set.
	 */
	public void setCheckMinimizeWhenClose(boolean checkMinimizeWhenClose) {
		this.checkMinimizeWhenClose = checkMinimizeWhenClose;
	}

	/**
	 * @return Returns the maximizeWhenDoubleClick.
	 */
	public boolean isMaximizeWhenDoubleClick() {
		return maximizeWhenDoubleClick;
	}

	/**
	 * @param maximizeWhenDoubleClick The maximizeWhenDoubleClick to set.
	 */
	public void setMaximizeWhenDoubleClick(boolean maximizeWhenDoubleClick) {
		this.maximizeWhenDoubleClick = maximizeWhenDoubleClick;
	}

	/**
	 * @return Returns the minHeight.
	 */
	public int getMinHeight() {
		return minHeight;
	}

	/**
	 * @param minHeight The minHeight to set.
	 */
	public void setMinHeight(int minHeight) {
		this.minHeight = minHeight;
	}

	/**
	 * @return Returns the minWidth.
	 */
	public int getMinWidth() {
		return minWidth;
	}

	/**
	 * @param minWidth The minWidth to set.
	 */
	public void setMinWidth(int minWidth) {
		this.minWidth = minWidth;
	}

	/**
	 * @return the showMinButton
	 */
	public boolean isShowMinButton() {
		return showMinButton;
	}

	/**
	 * @param showMinButton the showMinButton to set
	 */
	public void setShowMinButton(boolean showMinButton) {
		this.showMinButton = showMinButton;
	}
}
