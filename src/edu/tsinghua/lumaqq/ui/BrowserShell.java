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

import static edu.tsinghua.lumaqq.resource.Messages.*;

import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.browser.TitleEvent;
import org.eclipse.swt.browser.TitleListener;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import edu.tsinghua.lumaqq.resource.Resources;

/**
 * <pre>
 * 浏览器窗口，因为有些功能需要到网上完成
 * </pre>
 * 
 * @author luma
 */
public class BrowserShell implements TitleListener {	
	// 用于监听main shell关闭事件，如果主shell关闭了，它也应该被关闭
	private class ParentShellListener extends ShellAdapter {
		@Override
		public void shellClosed(ShellEvent e) {
			if(shell != null && !shell.isDisposed())
				shell.close();
		}
	}	
	
    private Shell shell;
    private Text textUrl;
    private Browser browser;
	private Resources res = Resources.getInstance();
    
    /**
     * 构造函数
     * @param main
     */
    public BrowserShell(MainShell main) {
		shell = new Shell(main.getDisplay(), SWT.SHELL_TRIM);
		shell.setImage(res.getImage(Resources.icoLumaQQ));
		// 添加自己为主shell的事件监听器，这样如果主窗口关闭，自己也跟着关闭
		main.getShell().addShellListener(new ParentShellListener());
		
		// 初始化界面布局
		initLayout();
    }
    
	/**
     * 初始化界面布局
     */
    private void initLayout() {
        // 设置shell的layout
        GridLayout layout = new GridLayout();
        layout.marginHeight = layout.marginWidth = 5;
        layout.numColumns = 2;
        shell.setLayout(layout);
        // 工具条
        ToolBar tb = new ToolBar(shell, SWT.FLAT | SWT.HORIZONTAL);
        GridData gd = new GridData(GridData.FILL_HORIZONTAL);
        gd.horizontalSpan = 2;
        tb.setLayoutData(gd);
        // 后退按钮
        ToolItem ti = new ToolItem(tb, SWT.PUSH);
        ti.setImage(res.getImage(Resources.icoBack));
        ti.setText(browser_button_back);
        ti.addSelectionListener(new SelectionAdapter() {
            @Override
			public void widgetSelected(SelectionEvent e) {
                if(browser.isBackEnabled())
                    browser.back();
            }
        });
        // 前进按钮
        ti = new ToolItem(tb, SWT.PUSH);
        ti.setImage(res.getImage(Resources.icoForward));
        ti.setText(browser_button_forward);
        ti.addSelectionListener(new SelectionAdapter() {
            @Override
			public void widgetSelected(SelectionEvent e) {
                if(browser.isForwardEnabled())
                    browser.forward();
            }
        });
        // 刷新按钮
        ti = new ToolItem(tb, SWT.PUSH);
        ti.setImage(res.getImage(Resources.icoRefresh));
        ti.setText(browser_button_refresh);
        ti.addSelectionListener(new SelectionAdapter() {
            @Override
			public void widgetSelected(SelectionEvent e) {
                browser.refresh();
            }
        });
        // 停止按钮
        ti = new ToolItem(tb, SWT.PUSH);
        ti.setImage(res.getImage(Resources.icoStop));
        ti.setText(browser_button_stop);
        ti.addSelectionListener(new SelectionAdapter() {
            @Override
			public void widgetSelected(SelectionEvent e) {
                browser.stop();
            }
        });
        // 地址栏标签
        Label lblUrl = new Label(shell, SWT.NONE);
        lblUrl.setText(browser_label_url);
        lblUrl.setLayoutData(new GridData());
        // 地址栏框
        textUrl = new Text(shell, SWT.SINGLE | SWT.BORDER);
        textUrl.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        textUrl.addKeyListener(new KeyAdapter() {
            @Override
			public void keyPressed(KeyEvent e) {
                if(e.character == SWT.CR) {
                    String url = textUrl.getText();
                    if(!url.startsWith("http://"))
                        url = "http://" + url;
                    setUrl(url);
                }
            }
        });
        // 创建浏览器组件
        browser = new Browser(shell, SWT.NONE);
        gd = new GridData(GridData.FILL_BOTH);
        gd.horizontalSpan = 2;
        browser.setLayoutData(gd);
        browser.addTitleListener(this);
    }
    
    /**
     * 设置初始页面
     * @param urlString
     */
    public void setUrl(String urlString) {
        browser.setUrl(urlString);
        textUrl.setText(urlString);
    }
    
    /**
     * 设置窗口标题
     * @param title
     */
    public void setTitle(String title) {
        shell.setText(title);
    }

	/**
	 * 打开shell
	 */
	public void open()	{
		// 打开shell
	    shell.setMaximized(true);
		shell.layout();
		// set dialog to center of screen
		shell.open();
	}

    /* (non-Javadoc)
     * @see org.eclipse.swt.browser.TitleListener#changed(org.eclipse.swt.browser.TitleEvent)
     */
    public void changed(TitleEvent e) {
        setTitle(e.title);
    }
}
