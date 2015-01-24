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
package edu.tsinghua.lumaqq.ui.dialogs;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Decorations;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;

/**
 * 抓屏对话框，用户在这里设置需要抓的屏大小和延时
 *
 * @author luma
 */
public class SnapshotDialog extends Dialog {
	private int delaySecond;
	private int x, y, w, h;
	private Image snapshot;
	private Rectangle screenBound;
	
	private Button rdoFull;
	private Spinner spinDelay;
	private Composite simScreen;
	private Decorations areaSlider;
	private Button rdoPart;
	private Spinner spinX, spinY, spinW, spinH; 
	
	private int downX, downY;
	private boolean moving;
	private Button btnShow;
	
	private Runnable snapAction = new Runnable() {
		public void run() {
			GC gc = new GC(getShell().getDisplay());
			int actW = Math.min(w, screenBound.width - x);
			int actH = Math.min(h, screenBound.height - y);
			snapshot = new Image(getShell().getDisplay(), actW, actH);
			gc.copyArea(snapshot, x, y);
			gc.dispose();
			
			postSnap();
		}
	};
	private static final int SHOW_AREA_ID = 999;
	private Image screen;

	public SnapshotDialog(Shell parentShell) {
		super(parentShell);
	}

	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("JSnapshot");
		newShell.addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				screen.dispose();				
			}
		});
	}
	
	@Override
	protected int getShellStyle() {
		return SWT.SHELL_TRIM ^ (SWT.MAX | SWT.RESIZE);
	}
	
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite control = (Composite)super.createDialogArea(parent);
		screenBound = getShell().getDisplay().getBounds();
		control.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent e) {
				Rectangle rect = simScreen.getBounds();
				rect.x--;
				rect.y--;
				rect.width++;
				rect.height++;
				e.gc.drawRectangle(rect);
			}
		});
		
		// 延迟时间
		Composite container = new Composite(control, SWT.NONE);
		container.setLayoutData(new GridData());
		container.setLayout(new GridLayout(2, false));
		Label lblDelay = new Label(container, SWT.LEFT);
		lblDelay.setLayoutData(new GridData());
		lblDelay.setText("Delay Second:");
		spinDelay = new Spinner(container, SWT.BORDER);
		spinDelay.setLayoutData(new GridData());
		spinDelay.setMinimum(0);
		spinDelay.setMaximum(Integer.MAX_VALUE);
		spinDelay.setIncrement(1);
		spinDelay.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				delaySecond = spinDelay.getSelection();
			}
		});
		
		// separator
		new Label(control, SWT.SEPARATOR | SWT.HORIZONTAL).setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		// 抓全部
		rdoFull = new Button(control, SWT.RADIO);
		rdoFull.setText("Full Screen (" + screenBound.width + 'x' + screenBound.height + ')');
		rdoFull.setLayoutData(new GridData());
		rdoFull.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				x = y = 0;
				w = screenBound.width;
				h = screenBound.height;
				simScreen.setBackground(simScreen.getParent().getBackground());
			}
		});
		// 抓部分
		rdoPart = new Button(control, SWT.RADIO);
		rdoPart.setText("Part of Screen");
		rdoPart.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		rdoPart.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				getAreaSize();
				updateAreaBound();
				simScreen.setBackground(getShell().getDisplay().getSystemColor(SWT.COLOR_WHITE));
			}
		});
		// x, y, w, h的spinner，本来不想要，但是descorations在linux下面有点不正常，只好加上这个功能
		Composite spinContainer = new Composite(control, SWT.NONE);
		spinContainer.setLayoutData(new GridData());
		GridLayout layout = new GridLayout(8, false);
		layout.marginHeight = layout.marginWidth = 0;
		spinContainer.setLayout(layout);
		Label lbl = new Label(spinContainer, SWT.LEFT);
		lbl.setLayoutData(new GridData());
		lbl.setText("X:");
		spinX = new Spinner(spinContainer, SWT.NONE);
		spinX.setLayoutData(new GridData());
		spinX.setMinimum(0);
		spinX.setMaximum(screenBound.width);
		spinX.setIncrement(3);
		spinX.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				x = spinX.getSelection();
				updateSliderBound();
			}
		});
		lbl = new Label(spinContainer, SWT.LEFT);
		lbl.setLayoutData(new GridData());
		lbl.setText("Y:");
		spinY = new Spinner(spinContainer, SWT.NONE);
		spinY.setLayoutData(new GridData());
		spinY.setMinimum(0);
		spinY.setMaximum(screenBound.height);
		spinY.setIncrement(3);
		spinY.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				y = spinY.getSelection();
				updateSliderBound();
			}
		});
		lbl = new Label(spinContainer, SWT.LEFT);
		lbl.setLayoutData(new GridData());
		lbl.setText("Width:");
		spinW = new Spinner(spinContainer, SWT.NONE);
		spinW.setLayoutData(new GridData());
		spinW.setMinimum(0);
		spinW.setMaximum(Integer.MAX_VALUE);
		spinW.setIncrement(3);
		spinW.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				w = spinW.getSelection();
				updateSliderBound();
			}
		});
		lbl = new Label(spinContainer, SWT.LEFT);
		lbl.setLayoutData(new GridData());
		lbl.setText("Height:");
		spinH = new Spinner(spinContainer, SWT.NONE);
		spinH.setLayoutData(new GridData());
		spinH.setMinimum(0);
		spinH.setMaximum(Integer.MAX_VALUE);
		spinH.setIncrement(3);
		spinH.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				h = spinH.getSelection();
				updateSliderBound();
			}
		});
		
		// 模拟屏幕，1/3大小
		simScreen = new Composite(control, SWT.NONE);
		GridData gd = new GridData();
		gd.widthHint = screenBound.width / 3;
		gd.heightHint = screenBound.height / 3;
		simScreen.setLayoutData(gd);
		layout = new GridLayout();
		layout.marginHeight = layout.marginWidth = 0;
		simScreen.setLayout(layout);
		simScreen.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent e) {
				e.gc.drawImage(screen, 0, 0);
			}
		});
		
		// 抓屏区域滑块
		areaSlider = new Decorations(simScreen, SWT.BORDER | SWT.RESIZE);
		areaSlider.setLocation(0, 0);
		areaSlider.setCursor(getShell().getDisplay().getSystemCursor(SWT.CURSOR_SIZEALL));
		areaSlider.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				downX = e.x;
				downY = e.y;
				moving = true;
			}
			@Override
			public void mouseUp(MouseEvent e) {
				moving = false;
			}
		});
		areaSlider.addMouseMoveListener(new MouseMoveListener() {
			public void mouseMove(MouseEvent e) {
				if(moving && rdoPart.getSelection()) {
					Point loc = areaSlider.getLocation();
					int x = loc.x + e.x - downX;
					int y = loc.y + e.y - downY;
					areaSlider.setLocation(x, y);	
				}
			}
		});
		areaSlider.addControlListener(new ControlListener() {
			public void controlMoved(ControlEvent e) {
				if(rdoPart.getSelection()) {
					getAreaSize();
					updateAreaBound();
				}
			}
			public void controlResized(ControlEvent e) {
				if(rdoPart.getSelection()) {
					getAreaSize();
					updateAreaBound();					
				}
			}
		});

		initialize();
		
		return control;
	}
	
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		btnShow = createButton(parent, SHOW_AREA_ID , "Show Area", false);
		btnShow.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				drawAreaXOR();
			}
			@Override
			public void mouseUp(MouseEvent e) {
				drawAreaXOR();
				
				// 区域边框如果和button有些重叠，则重叠的部分会留下残影，为了消除这些，这里重画所有button
				Composite bar = (Composite)getButtonBar();
				for(Control child : bar.getChildren())
					child.redraw();
			}
		});
		super.createButtonsForButtonBar(parent);
	}
	
	/**
	 * 在屏幕上画矩形
	 */
	private void drawAreaXOR() {
		GC gc = new GC(getShell().getDisplay());
		gc.setXORMode(true);
		gc.setLineWidth(5);
		gc.setForeground(getShell().getDisplay().getSystemColor(SWT.COLOR_GREEN));
		gc.drawRectangle(x, y, w, h);
		gc.dispose();
	}
	
	/**
	 * 得到抓屏区域大小
	 */
	private void getAreaSize() {
		Rectangle rect = areaSlider.getBounds();
		x = rect.x * 3;
		y = rect.y * 3;
		w = rect.width * 3;
		h = rect.height * 3;
	}

	/**
	 * 初始化控件和变量
	 */
	private void initialize() {		
		rdoFull.setSelection(true);
		spinDelay.setSelection(0);
		Image temp = new Image(getShell().getDisplay(), screenBound.width, screenBound.height);
		GC gc = new GC(getShell().getDisplay());
		gc.copyArea(temp, 0, 0);
		gc.dispose();
		ImageData data = temp.getImageData().scaledTo(screenBound.width / 3, screenBound.height / 3);
		screen = new Image(getShell().getDisplay(), data);
		temp.dispose();
		
		delaySecond = 0;
		x = y = 0;
		w = screenBound.width;
		h = screenBound.height;
		
		moving = false;
	}	
	
	/**
	 * 抓图完成后执行的动作
	 */
	protected void postSnap() {
		getShell().setVisible(true);
		getShell().setMinimized(false);
		getShell().setActive();
		
		SnapshotPreviewDialog dialog = new SnapshotPreviewDialog(getShell(), snapshot);
		if(dialog.open() == IDialogConstants.OK_ID)
			close();
	}
	
	@Override
	protected void okPressed() {
		getShell().setVisible(false);
		getShell().getDisplay().timerExec(delaySecond * 1000, snapAction);
	}
	
	/**
	 * 更新radiobutton的文本
	 */
	private void updateAreaBound() {
		spinX.setSelection(x);
		spinY.setSelection(y);
		spinW.setSelection(w);
		spinH.setSelection(h);
	}
	
	/**
	 * 设置滑块的位置大小
	 */
	private void updateSliderBound() {
		areaSlider.setBounds(x / 3, y / 3, w / 3, h / 3);
	}

	/**
	 * @return the snapshot
	 */
	public Image getSnapshot() {
		return snapshot;
	}
}
