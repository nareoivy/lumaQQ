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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import edu.tsinghua.lumaqq.resource.Resources;

/**
 * <pre>
 * 检查是否有更新的窗口
 * 在资源文件中，保存着当前版本的最后修改时间。在服务器上，有一个update.txt文件，
 * 保存着最新版本的修改时间，通过比较这两个时间，来判断当前版本是否最新。时间的格式
 * 为yyyy-mm-dd hh-mm
 * 后面的每一行用yyyy-mm-dd开头，表示一个修正
 * 如果用*开头，表示说明文字
 * </pre>
 * 
 * @author luma
 */
public class CheckUpdateShell extends ShellAdapter implements Runnable {
	// Log对象
    private static Log log = LogFactory.getLog(CheckUpdateShell.class);
    private Shell shell;
    private MainShell main;
    private Display display;
    private Label lblHint;
    private Button btnCancel, btnIKnow;
    private Thread thread;
	private Resources res = Resources.getInstance();
	// shell缺省宽高
	private static final int WIDTH = 328;
	private static final int HEIGHT = 150;
	
    /**
     * 构造函数
     * @param main
     */
    public CheckUpdateShell(MainShell main) {
        this.main = main;
        this.display = main.getDisplay();
		shell = new Shell(this.display, SWT.MIN | SWT.CLOSE | SWT.BORDER);
		shell.setImage(res.getImage(Resources.icoUpdate));
		shell.setText(check_update_title);
		shell.setSize(WIDTH, HEIGHT);
		// 添加listener
		shell.addShellListener(this);
		
		// 初始化界面布局
		initLayout();
    }

    /**
     * 初始化界面布局
     */
    private void initLayout() {
        // 设置shell的layout
        GridLayout layout = new GridLayout();
        layout.marginHeight = layout.marginWidth = 0;
        layout.verticalSpacing = layout.horizontalSpacing = 5;
        layout.numColumns = 2;
        layout.makeColumnsEqualWidth = true;
        shell.setLayout(layout);
        // 图片标签
        Label bar = new Label(shell, SWT.NONE);
        bar.setImage(res.getImage(Resources.bmpCheckUpdate));
        GridData gd = new GridData(GridData.FILL_HORIZONTAL);
        gd.heightHint = bar.getImage().getBounds().height;
        gd.horizontalSpan = layout.numColumns;
        bar.setLayoutData(gd);
        // 提示标签
        lblHint = new Label(shell, SWT.LEFT | SWT.WRAP);
        lblHint.setText(check_update_label_checking);
        gd = new GridData(GridData.FILL_BOTH);
        gd.horizontalSpan = layout.numColumns;
        gd.horizontalIndent = 10;
        lblHint.setLayoutData(gd);
        // 取消按钮
        btnCancel = new Button(shell, SWT.PUSH);
        btnCancel.setText(button_cancel);
        btnCancel.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL));
        btnCancel.addSelectionListener(new SelectionAdapter() {
            @Override
			public void widgetSelected(SelectionEvent r) {
                close();
            }
        });
        // 我知道了按钮
        btnIKnow = new Button(shell, SWT.PUSH);
        btnIKnow.setText(check_update_button_i_know);
        btnIKnow.setEnabled(false);
        btnIKnow.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL));
        btnIKnow.addSelectionListener(new SelectionAdapter() {
            @Override
			public void widgetSelected(SelectionEvent r) {
                close();
            }
        });
    }

    /**
     * 关闭窗口
     */
    private void close() {
        if(thread != null) {
            thread.interrupt();
            thread = null;
        }
        shell.close();
    }

    /**
	 * 打开shell
	 */
	public void open()	{
		// 打开shell
		shell.layout();
		// set dialog to center of screen
		Rectangle shellRect = shell.getBounds();
		Rectangle displayRect = display.getBounds();
		shell.setLocation((displayRect.width - shellRect.width) / 2, (displayRect.height - shellRect.height) / 2);
		// set dialog to center of screen
		shell.open();
		// 开始检查
		thread = new Thread(this);
		thread.setName("Check Update");
		thread.setDaemon(true);
		thread.start();
	}
	
    /* (non-Javadoc)
     * @see org.eclipse.swt.events.ShellListener#shellClosed(org.eclipse.swt.events.ShellEvent)
     */
    @Override
	public void shellClosed(ShellEvent e) {
        main.getShellRegistry().deregisterCheckUpdateShell();
    }

    /**
     * 设置提示信息
     * @param hint
     */
    private void setHint(String hint) {
        lblHint.setText(hint);
    }
    
    /**
     * 操作失败，设置各控件状态
     * @param msg
     */
    private void fail(final String msg) {
        display.syncExec(new Runnable() {
            public void run() {
                try {
	                setHint(NLS.bind(check_update_label_error, msg));
	                btnCancel.setText(button_close);
	                btnCancel.setEnabled(true);
	                btnIKnow.setEnabled(false);                   
                } catch(SWTException e) {       
                    // 没什么要做的
                }
            }
        });
    }
    
    /**
     * 操作成功，当前版本已经最新，设置各控件状态
     */
    private void latest() {
        display.syncExec(new Runnable() {
	        public void run() {
	            try {
		            setHint(check_update_label_latest);
		            btnCancel.setEnabled(false);
		            btnIKnow.setEnabled(true);
                } catch(SWTException e) {     
                    // 没什么要做的
                }
	        }
        });
    }
    
    /**
     * 操作成功，当前版本不是最新，设置各控件状态
     */
    private void old() {
        display.syncExec(new Runnable() {
            public void run() {           
                try {
	                setHint(check_update_lable_old);
	                btnCancel.setEnabled(false);
	                btnIKnow.setEnabled(true);      
                } catch(SWTException e) {       
                    // 没什么要做的
                }
            }
        });
    }
    
    /* (non-Javadoc)
     * @see java.lang.Runnable#run()
     */
    public void run() {
        // 得到当前版本时间，包含了日月年小时分
        String timeTag = update_time;
        log.debug("当前版本修改时间 " + timeTag);
        BufferedReader br = null;
        try {
            // 打开输入流，读取服务器上的文件
            URL url = new URL(url_update);
            br = new BufferedReader(new InputStreamReader(url.openStream(), "GBK"));
            // 读取最新版本修改时间
            String latestTag = br.readLine();
            // 比较，如果已经是最新，返回，如果不是，读取修正列表
            if(timeTag.compareTo(latestTag) >= 0) {                
                latest();           
            } else {
                old();                
            }
            thread = null;            
        } catch (Exception e) {            
            fail(e.getMessage());
            thread = null;
        } finally {
            try {
                if(br != null)
                    br.close();
            } catch (IOException e1) {
                log.error(e1.getMessage());
            }
        }
    }
}
