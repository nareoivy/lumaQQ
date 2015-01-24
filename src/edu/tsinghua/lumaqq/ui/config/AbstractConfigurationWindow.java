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
package edu.tsinghua.lumaqq.ui.config;

import static edu.tsinghua.lumaqq.resource.Messages.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseTrackAdapter;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

import edu.tsinghua.lumaqq.events.BaseQQListener;
import edu.tsinghua.lumaqq.resource.Colors;
import edu.tsinghua.lumaqq.widgets.qstyle.Slat;

/**
 * 通用配置对话框基类，其实现一个简单的配置对话框框架，左边是页面列表，右边是页面区
 * 
 * @author luma
 */
public abstract class AbstractConfigurationWindow extends BaseQQListener {
    protected static Log log = LogFactory.getLog(AbstractConfigurationWindow.class);
    
    protected Shell shell;
    protected Shell parentShell;
    
    protected static final int MAX_PAGE = 10;
    
    // 确定按钮
    private Slat btnOK;
    // 关闭按钮
    private Slat btnClose;
    // 当前页ID
    private int currentPageId;
    // 页数组
    private List<AbstractPage> pages;
    // 页标签数组
    private List<CLabel> labels;
    // 页列表容器
    private Composite pageList;
    // 页容器
    private Composite pageContainer;
    // 样式
    protected int style;
    // 页数
    protected int pageCount;
    
    private static final int NONE_SELECTED = -1;
    
    /**
     * 创建一个配置窗口
     * 
     * @param parent
     * 		父窗口
     * @param style
     * 		样式
     */
    protected AbstractConfigurationWindow(Shell parent, int style) {      
        parentShell = parent;
        this.style = style;
        initializeVariables();
        
        shell = new Shell(parent.getDisplay(), SWT.BORDER | SWT.TITLE | SWT.MIN | SWT.CLOSE);
        shell.setText(getTitle());
        shell.setImage(getImage());
        shell.setBackground(Colors.DIALOG_BACKGROUND);
        GridLayout layout = new GridLayout();
        shell.setLayout(new GridLayout());
        shell.addShellListener(new ShellAdapter() {
            @Override
			public void shellClosed(ShellEvent e) {
                onShellClose();
            }
        });
        shell.addDisposeListener(new DisposeListener() {
            public void widgetDisposed(DisposeEvent e) {
                onDispose();
            }
        });
        
        // 创建窗口顶部组件
        createTopControl(shell);
        
        // 页面区容器
        Composite topContainer = new Composite(shell, SWT.NONE);
        topContainer.setLayoutData(new GridData(GridData.FILL_BOTH));
        layout = new GridLayout(2, false);
        layout.verticalSpacing = layout.marginHeight = layout.marginWidth = 0;
        topContainer.setLayout(layout);
        topContainer.setBackground(Colors.DIALOG_BACKGROUND);
        
        // 页列表
        pageList = new Composite(topContainer, SWT.NONE);
        GridData gd = new GridData(GridData.FILL_VERTICAL);
        gd.widthHint = 115;
        pageList.setLayoutData(gd);
        layout = new GridLayout();
        layout.horizontalSpacing = layout.verticalSpacing = 0;
        layout.marginHeight = layout.marginWidth = 1;
        pageList.setLayout(layout);
        pageList.setBackground(Colors.PAGE_LIST_BACKGROUND);
        pageList.addPaintListener(new PaintListener() {
            public void paintControl(PaintEvent e) {
                Composite c = (Composite)e.getSource();
                Rectangle rect = c.getClientArea();
                rect.width--;
                rect.height--;
                e.gc.setForeground(Colors.PAGE_LIST_BORDER);
                e.gc.drawRectangle(rect);
            }
        });
        
        // 页面容器
        pageContainer = new Composite(topContainer, SWT.NONE);
        pageContainer.setLayoutData(new GridData(GridData.FILL_BOTH));        
        pageContainer.setLayout(new FormLayout());
        pageContainer.setBackground(Colors.DIALOG_BACKGROUND);
        pageContainer.addPaintListener(new PaintListener() {
            public void paintControl(PaintEvent e) {
                Composite c = (Composite)e.getSource();
                Rectangle rect = c.getClientArea();
                rect.width--;
                rect.height--;
                e.gc.setForeground(Colors.PAGE_LIST_BORDER);
                e.gc.drawRectangle(rect);
            }
        });
        
        // 按钮区容器
        Composite buttonContainer = new Composite(shell, SWT.NONE);
        buttonContainer.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_END));
        layout = new GridLayout(2, true);
        layout.horizontalSpacing = 15;
        layout.verticalSpacing = 0;
        layout.marginWidth = 0;
        layout.marginHeight = 3;
        buttonContainer.setLayout(layout);
        buttonContainer.setBackground(Colors.DIALOG_BACKGROUND);
        
        // 确定按钮
        btnOK = new Slat(buttonContainer);
        btnOK.setText(button_ok);
        gd = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
        gd.widthHint = 65;
        btnOK.setVisible(showOKButton());
        btnOK.setLayoutData(gd);
        btnOK.addMouseListener(new MouseAdapter() {
            @Override
			public void mouseUp(MouseEvent e) {
                onOK();
            }
        });
        
        // 关闭按钮
        btnClose = new Slat(buttonContainer);
        btnClose.setText(button_close);
        gd = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
        gd.widthHint = 65;
        btnClose.setVisible(showCloseButton());
        btnClose.setLayoutData(gd);
        btnClose.addMouseListener(new MouseAdapter() {
            @Override
			public void mouseUp(MouseEvent e) {
                close();
            }
        });
    }
    
    /**
     * 给子类一个创建顶部组件的机会，比如创建一个工具条
     * 
     * @param parent
     */
    protected void createTopControl(Composite parent) {
    }

    /**
     * 创建一个配置窗口
     * 
     * @param parent
     */
    protected AbstractConfigurationWindow(Shell parent) {        
        this(parent, 0);
    }
    
    /**
     * 初始化变量
     */
    protected void initializeVariables() {
        currentPageId = NONE_SELECTED;
        labels = new ArrayList<CLabel>();
        pages = new ArrayList<AbstractPage>();
    }
    
	/**
	 * 打开窗口
	 */
	public void open()	{
	    // 初始化配置页
	    initialPages();
		// 打开shell
		shell.layout();
		configureShellSize();
		shell.setLocation(getInitialLocation());
		shell.open();
	}
	
	/**
     * 配置Shell的大小
     */
    protected void configureShellSize() {
        shell.setSize(getInitialSize());
    }

    /**
	 * 根据页ID得到页对象
	 * 
	 * @param id
	 * 		ID
	 * @return
	 * 		AbstractPage对象
	 */
	protected AbstractPage getPage(int id) {
	    return pages.get(id);
	}
	
	/**
	 * 添加一个配置页
	 * 
	 * @param page
	 * 		AbstractPage子类
	 */
	protected void addPage(AbstractPage page) {
	    // 添加页
	    pages.add(page);
	    page.setVisible(false);
	    page.initializeValues();
	    // 设置页布局
	    if(page.getContent().getLayoutData() == null || !(page.getContent().getLayoutData() instanceof FormData)) {
		    FormData fd = new FormData();
		    fd.top = fd.left = new FormAttachment(0, 1);
		    fd.bottom = fd.right = new FormAttachment(100, -1);
		    page.setLayoutData(fd);	        
	    }
	    
	    // 添加页名称到页列表
	    CLabel label = new CLabel(pageList, SWT.CENTER);
	    label.setText(page.getTitle(pageCount));
	    label.setBackground(Colors.PAGE_LIST_BACKGROUND);
	    GridData gd = new GridData(GridData.FILL_HORIZONTAL);
	    gd.heightHint = 20;
	    label.setLayoutData(gd);
	    labels.add(label);
	    label.addMouseTrackListener(new MouseTrackAdapter() {
            @Override
			public void mouseEnter(MouseEvent e) {
                // 如果label不是当前lable，则在鼠标进入时变色
                int id = labels.indexOf(e.getSource());
                if(currentPageId != id)
                    ((CLabel)e.getSource()).setBackground(Colors.PAGE_LIST_HOVERD);
            }
            
            @Override
			public void mouseExit(MouseEvent e) {
                // 如果label不是当前label，则在鼠标退出时还原原来的颜色
                int id = labels.indexOf(e.getSource());
                if(currentPageId != id)
                    ((CLabel)e.getSource()).setBackground(Colors.PAGE_LIST_BACKGROUND);
            }
	    });
	    label.addMouseListener(new MouseAdapter() {
            @Override
			public void mouseDown(MouseEvent e) {
                // 如果label不是当前label，则设置当前页
                int id = labels.indexOf(e.getSource());
                if(currentPageId != id)
                    setCurrentPage(id);
            }
	    });
	    
	    // 如果当前没有页没选中，设置这个页为当前页
	    pageCount++;
	    if(currentPageId == NONE_SELECTED)
	        setCurrentPage(0);
	}
	
	/**
	 * 设置左边的页面列表宽度
	 * 
	 * @param w
	 */
	protected void setPageListWidth(int w) {
	    GridData gd = (GridData)pageList.getLayoutData();
	    gd.widthHint = w;
	    pageList.getParent().layout();
	}

	/**
	 * 删除当前页 
	 */
	public void removeCurrentPage() {
	    if(currentPageId == NONE_SELECTED)
	        return;
	    
	    int page = currentPageId;
	    setCurrentPage(NONE_SELECTED);
	    labels.remove(page).dispose();
	    getPageListContainer().layout();
	    pages.remove(page);
	    pageCount--;
	    pageRemoved(page);
	    
	    if(pageCount > 0)
	        setCurrentPage(0);
	}
	
	/**
	 * 页被删除时调用此方法
	 * 
	 * @param index
	 * 		页号
	 */
	protected void pageRemoved(int index) {	    
	}
	
	/**
	 * 设置当前页
	 * 
	 * @param i
	 * 		页索引
	 */
	public void setCurrentPage(int i) {
	    if(i == currentPageId)
	        return;
	    
	    if(i != NONE_SELECTED && (i < 0 || i >= pageCount))
	        return;
	    
	    if(currentPageId != NONE_SELECTED) {
	        getCurrentPage().setVisible(false);
	        getCurrentPageLabel().setBackground(Colors.PAGE_LIST_BACKGROUND);
	    }
	    currentPageId = i;
	    if(currentPageId != NONE_SELECTED) {
		    getCurrentPage().setVisible(true);
		    getCurrentPageLabel().setBackground(Colors.PAGE_LIST_SELECTED);	        
	    }
	    
	    pageChanged();
	}
	
	/**
	 * @return
	 * 		当前页面标签
	 */
	protected CLabel getCurrentPageLabel() {
	    return labels.get(currentPageId);
	}
	
	/**
	 * @return
	 * 		当前页对象
	 */
	protected AbstractPage getCurrentPage() {
	    return pages.get(currentPageId);
	}
	
	/**
	 * @return
	 * 		窗口初始大小
	 */
	protected Point getInitialSize() {
	    return new Point(400, 300);
	}
	
	/**
	 * @return
	 * 		窗口初始位置 
	 */
	protected Point getInitialLocation() {
		// 缺省实现是把窗口居中
		Rectangle shellRect = shell.getBounds();
		Rectangle displayRect = shell.getDisplay().getClientArea();
		return new Point((displayRect.width - shellRect.width) / 2, (displayRect.height - shellRect.height) / 2);
	}
    
	/**
	 * 初始化配置页 
	 */
	protected void initialPages() {	    
	}
	
    /**
     * @return
     * 		窗口标题
     */
    protected abstract String getTitle();
    
    /**
     * @return
     * 		窗口图标
     */
    protected abstract Image getImage();
    
    /**
     * 当前页变化时调用此方法
     */
    protected void pageChanged() {        
    }
    
    /**
     * 窗口关闭时调用此方法
     */
    protected void onShellClose() {        
    }
    
    /**
     * 窗口释放时调用此方法
     */
    protected void onDispose() {        
    }
    
    /**
     * 保存所有的更改
     */
    protected void saveAll() {
		for(AbstractPage page : pages)
			page.save();
    }
    
    /**
     * 保存所有的更改并关闭窗口
     */
    protected void saveAllAndClose() {
        saveAll();
        close();
    }
    
    /**
     * 关闭窗口
     */
    public void close() {
        shell.close();
    }
    
    /**
     * 确定按钮按下时调用这个方法
     */
    protected void onOK() {        
    }
    
    /**
     * 设置确定按钮的文字
     * 
     * @param text
     * 		按钮文本
     */
    protected void setOKButtonText(String text) {
        btnOK.setText(text);
    }
    
    /**
     * 设置确定按钮使能状态
     * 
     * @param b
     * 		true表示enable
     */
    public void setOKButtonEnabled(boolean b) {
        btnOK.setEnabled(b);
    }
    
    /**
     * @return
     * 		true表示显示确定按钮
     */
    protected boolean showOKButton() {
        return true;
    }
    
    /**
     * @return
     * 		true表示显示关闭按钮
     */
    protected boolean showCloseButton() {
        return true;
    }
    
    /**
     * 设置关闭按钮的文本
     * 
     * @param text
     * 		按钮文本
     */
    protected void setCloseButtonText(String text) {
        btnClose.setText(text);
    }
    
    /**
     * @return
     * 		当前页索引
     */
    protected int getCurrentPageId() {
        return currentPageId;
    }
    
    /**
     * @return
     * 		页数
     */
    protected int getPageCount() {
        return pages.size();
    }
    
    /**
     * 设置窗口最小化状态
     * 
     * @param b
     */
    public void setMinimized(boolean b) {
        shell.setMinimized(b);
    }
    
    /**
     * 激活窗口
     */
    public void setActive() {
        shell.setActive();
    }
    
    /**
     * @return
     * 		页面容器
     */
    protected Composite getPageContainer() {
        return pageContainer;
    }
    
    /**
     * 刷新所有页的model
     * 
     * @param model
     * 		model对象
     */
    protected void refreshPageModels(Object model) {
		for(AbstractPage page : pages)
			page.setModel(model);
    }
    
    /**
     * 刷新所有页的值
     */
    protected void refreshPageValues() {
		for(AbstractPage page : pages)
			page.refreshValues();
    }
    
    /**
     * @return
     * 		page遍历器
     */
    protected Iterator<AbstractPage> getPageIterator() {
        return pages.iterator();
    }
    
	/**
	 * @return
	 * 		Shell
	 */
	public Shell getShell() {
		return shell;
	}
	
    /**
     * @return
     * 		页面列表容器
     */
    protected Composite getPageListContainer() {
        return pageList;
    }
}
