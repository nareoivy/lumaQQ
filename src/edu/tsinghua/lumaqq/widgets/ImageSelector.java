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
package edu.tsinghua.lumaqq.widgets;

import static edu.tsinghua.lumaqq.resource.Messages.*;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import edu.tsinghua.lumaqq.events.IFaceSelectionListener;
import edu.tsinghua.lumaqq.resource.Colors;
import edu.tsinghua.lumaqq.ui.helper.UITool;

/**
 * 表情选择窗口，供用户选择缺省表情和自定义表情
 * 
 * @author luma
 */
public class ImageSelector {
    private Shell shell;
	
	private Label lblPage;	
	private CTabFolder folder;
	
	private int gridX, gridY;
	
	private int col, row, grid, margin;
	private static final int BORDER = 1;
	
	private IFaceSelectionListener listener;
	private IImageSelectorAdvisor provider;
	
    /**
     * 创建一个好友选择窗口
     * 
     * @param parent
     */
    public ImageSelector(Shell parent, IImageSelectorAdvisor p) {   
        provider = p;
		shell = new Shell(parent, SWT.NO_TRIM);
		shell.setBackground(Colors.WHITE);
		shell.addShellListener(new ShellAdapter() {
            @Override
			public void shellDeactivated(ShellEvent e) {
            	if(shell != null && !shell.isDisposed())
            		shell.close();
            }
		});
		shell.addPaintListener(new PaintListener() {
            public void paintControl(PaintEvent e) {
                Rectangle rect = shell.getClientArea();
                rect.width--;
                rect.height--;
                e.gc.setForeground(Colors.FACE_SELECT_SHELL_BORDER);
                e.gc.drawRectangle(rect);
            }
		});
		
		GridLayout layout = new GridLayout();
		layout.marginHeight = layout.marginWidth = layout.verticalSpacing = 1;
		shell.setLayout(layout);
		
		UITool.setDefaultBackground(shell.getBackground());
		
		folder = new CTabFolder(shell, SWT.TOP | SWT.FLAT);
		folder.setBackground(UITool.getDefaultBackground());
		folder.setLayoutData(new GridData(GridData.FILL_BOTH));
		folder.addSelectionListener(new SelectionAdapter() {
            @Override
			public void widgetSelected(SelectionEvent e) {
                gridX = gridY = -1;
                refreshPageLabel();
            }
		});
		
		// 点击某个图片时的事件处理器
		MouseListener ml = new MouseAdapter() {
            @Override
			public void mouseUp(MouseEvent e) {
                if(listener != null) {
                    int page = (Integer)folder.getSelection().getControl().getData();
                    listener.faceSelected(provider, folder.getSelectionIndex(), page * row * col + gridY * col + gridX);
                }
                if((e.stateMask & SWT.MOD1) == 0) {
                	if(shell != null && !shell.isDisposed())
                		shell.close();
                }
            }
		};
		// 鼠标移动时的事件处理器，主要是负责在当前图片上画一个边框
		MouseMoveListener mml = new MouseMoveListener() {
            public void mouseMove(MouseEvent e) {
                int newGridX = getGridX(e.x);
                int newGridY = getGridY(e.y);
                if(newGridX == gridX && newGridY == gridY)
                    return;
                clearFaceFocus();
                gridX = newGridX;
                gridY = newGridY;
                setFaceFocus();
            }
		};
		// 重画一个图片页
		PaintListener pl = new PaintListener() {
            public void paintControl(PaintEvent e) {
                onPaint(e);
            }
		};
		
		// 创建所有组
		int groupCount = provider.getGroupCount();
		for(int i = 0; i < groupCount; i++) {
			CTabItem item = new CTabItem(folder, SWT.NONE);
			item.setText(provider.getGroupName(i));
			Composite container = new Composite(folder, SWT.NONE);
			item.setControl(container);
			container.setData(new Integer(0));
			container.setData("group", new Integer(i));
			container.setBackground(UITool.getDefaultBackground());
			container.addMouseListener(ml);		
			container.addMouseMoveListener(mml);
			container.addPaintListener(pl);
		}
		
		// separator
		Label lblSeparator = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
		lblSeparator.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		// 按钮区
		layout = new GridLayout(4, false);
		layout.horizontalSpacing = 15;
		Composite buttonContainer = UITool.createContainer(shell, new GridData(GridData.FILL_HORIZONTAL), layout);
		// 添加自定义表情
		Label lblLink = UITool.createSimpleLink(buttonContainer, provider.getLinkLabel(), Colors.FACE_SELECT_SHELL_BORDER, Colors.BLUE);
		lblLink.addMouseListener(new MouseAdapter() {
            @Override
			public void mouseDown(MouseEvent e) {
                onLink();
            }
		});		    
		lblLink.setVisible(provider.isAuxiliaryLinkVisible());
		// 页号标签
		GridData gd = new GridData(GridData.HORIZONTAL_ALIGN_END);
		gd.grabExcessHorizontalSpace = true;
		lblPage = UITool.createLabel(buttonContainer, "1/1", gd, SWT.CENTER);
		lblPage.setForeground(Colors.FACE_SELECT_SHELL_BORDER);
		// 上页
		Label lblPrevious = UITool.createSimpleLink(buttonContainer, button_previous, Colors.FACE_SELECT_SHELL_BORDER, Colors.BLUE);
		lblPrevious.addMouseListener(new MouseAdapter() {
            @Override
			public void mouseDown(MouseEvent e) {
                Control c = folder.getSelection().getControl();
                int group = (Integer)c.getData("group");
                if(!provider.canPagination(group))
                    return;
                int page = (Integer)c.getData();
                if(provider.canPrevious(group, page)) {
	                c.setData(page - 1);
	                refreshPageLabel();
	                c.redraw();                    
                }
            }
		});
		// 下页
		Label lblNext = UITool.createSimpleLink(buttonContainer, button_next, Colors.FACE_SELECT_SHELL_BORDER, Colors.BLUE);
		lblNext.addMouseListener(new MouseAdapter() {
            @Override
			public void mouseDown(MouseEvent e) {
                Control c = folder.getSelection().getControl();
                int group = (Integer)c.getData("group");
                if(!provider.canPagination(group))
                    return;
                int page = (Integer)c.getData();
                if(provider.canNext(group, page)) {
	                c.setData(page + 1);
	                refreshPageLabel();
	                c.redraw();                    
                }
            }
		});
		
		// 初始化变量
		gridX = gridY = -1;
		row = provider.getRow();
		col = provider.getCol();
		grid = provider.getGridSize();
		margin = provider.getMargin();
    }
    
    /**
     * link标签按下时调用此方法
     */
    protected void onLink() {
        provider.doLink(this);
    }

    /**
     * 刷新页号标签
     */
    private void refreshPageLabel() {
        Control c = folder.getSelection().getControl();
        Integer group = (Integer)c.getData("group");
        if(group == null)
            return;
        if(!provider.canPagination(group)) {
            lblPage.setText("1/1");
            return;
        }
        Integer page = (Integer)c.getData();
        int total = provider.getImageCount(group) / (row * col) + 1;
        lblPage.setText(String.valueOf(page + 1) + '/' + total);
    }

    /**
     * 根据鼠标x位置得到图片网格x位置
     * 
     * @param x
     * @return
     */
    private int getGridX(int x) {
        return x / (grid + BORDER);
    }
    
    /**
     * 根据鼠标y位置得到图片网格y位置
     * 
     * @param y
     * @return
     */
    private int getGridY(int y) {
        return y / (grid + BORDER);
    }
    
	/**
	 * 画缺省表情 
	 * 
     * @param e
     */
    private void onPaint(PaintEvent e) {
        int gridStartX = getGridX(e.x);
        int gridStartY = getGridY(e.y);
        int gridEndX = getGridX(e.x + e.width);
        int gridEndY = getGridY(e.y + e.height);
        int group = (Integer)((Control)e.getSource()).getData("group");
        int page = (Integer)((Control)e.getSource()).getData();
        
        // 画竖线
        e.gc.setForeground(Colors.FACE_SEPARATOR_BORDER);
        for(int i = gridStartX, x = e.x; i <= gridEndX; i++, x += grid + BORDER)
            e.gc.drawLine(x, e.y, x, e.y + e.height);
        
        // 画横线
        for(int i = gridStartY, y = e.y; i <= gridEndY; i++, y += grid + BORDER)
            e.gc.drawLine(e.x, y, e.x + e.width, y);
        
        // 画表情
        int count = provider.getImageCount(group);
        for(int j = gridStartY, y = margin + BORDER + gridStartY * (grid + BORDER), seq = page * row * col + j * col + gridStartX; j <= gridEndY && seq < count; j++, y += grid + BORDER, seq = page * row * col + j * col + gridStartX) {
            for(int i = gridStartX, x = margin + BORDER + gridStartX * (grid + BORDER); i <= gridEndX & seq < count; i++, x += grid + BORDER, seq++) {
                Image image = provider.getImage(group, seq);
                if(image != null)
                    e.gc.drawImage(image, x, y);
            }
        }
        
        // 画焦点框
        if(gridX < gridStartX || gridX > gridEndX)
            return;
        if(gridY < gridStartY || gridY > gridEndY)
            return;
        int x = BORDER + gridX * (grid + BORDER);
        int y = BORDER + gridY * (grid + BORDER);
        e.gc.setForeground(Colors.BLUE);
        e.gc.drawRectangle(x, y, grid - 1, grid - 1);
    }
    
    /**
     * 清除老的焦点框
     */
    private void clearFaceFocus() {
        if(gridX != -1)
            folder.getSelection().getControl().redraw(gridX * (grid + BORDER), gridY * (grid + BORDER), grid + BORDER * 2, grid + BORDER * 2, true);
    }
    
    /**
     * 设置新的焦点，新的焦点由gridX和gridY的当前值指定
     */
    private void setFaceFocus() {
        folder.getSelection().getControl().redraw(gridX * (grid + BORDER), gridY * (grid + BORDER), grid + BORDER * 2, grid + BORDER * 2, true);
    }

    /**
	 * 打开shell
	 */
	public void open()	{
		// 打开shell
		shell.layout();		
		Rectangle rect = folder.getClientArea();
		Point size = shell.getSize();
		size.x -= rect.width;
		size.y -= rect.height;
		size.x += (grid + BORDER) * col + BORDER;
		size.y += (grid + BORDER) * row + BORDER;
		shell.setSize(size);
		shell.open();
	}
	
	/**
	 * @return true如果窗口已经被disposed
	 */
	public boolean isDisposed() {
		return shell.isDisposed();
	}
	
	/**
	 * 设置窗口位置
	 * @param unlimited
	 * @param y
	 */
	public void setLocation(Point p) {
		shell.setLocation(p);
	}
	
    /**
     * @param listener The listener to set.
     */
    public void setListener(IFaceSelectionListener listener) {
        this.listener = listener;
    }
    
    /**
     * @return
     * 		Shell
     */
    public Shell getShell() {
        return shell;
    }
    
    /**
     * @return Returns the provider.
     */
    public IImageSelectorAdvisor getProvider() {
        return provider;
    }
}
