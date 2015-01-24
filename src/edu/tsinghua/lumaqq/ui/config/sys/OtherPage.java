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
package edu.tsinghua.lumaqq.ui.config.sys;

import static edu.tsinghua.lumaqq.resource.Messages.*;

import java.io.File;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import edu.tsinghua.lumaqq.resource.Colors;
import edu.tsinghua.lumaqq.resource.Resources;
import edu.tsinghua.lumaqq.ui.MainShell;
import edu.tsinghua.lumaqq.ui.config.AbstractPage;
import edu.tsinghua.lumaqq.ui.helper.OptionHelper;
import edu.tsinghua.lumaqq.ui.helper.UITool;
import edu.tsinghua.lumaqq.ui.listener.CenterBorderPaintListener;

/**
 * 其他设置页
 * 
 * @author luma
 */
public class OtherPage extends AbstractPage {    
    private Text textBrowser;
    private Button chkEnableLatest, chkKeepStranger;
    private Label lblLatestSize;
    private Text textLatestSize;
    private Button chkShowFakeCam;
    
    private PaintListener paintListener;
    private MainShell main;
    
    private static final int BROWSER = 1;
    private static final int ENABLE_LATEST = 2;
    private static final int KEEP_STRANGER = 3;
    private static final int LATEST_SIZE = 4;
    private static final int SHOW_FAKE_CAM = 5;
    
    /**
     * @param parent
     */
    public OtherPage(Composite parent, MainShell main) {
        super(parent);
        this.main = main;
    }
    
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.shells.AbstractPage#initialVariable()
     */
    @Override
	protected void initialVariable() {
        paintListener = new CenterBorderPaintListener(new Class[] { Text.class }, 20, Colors.PAGE_CONTROL_BORDER);
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.ui.config.AbstractPage#createContent(org.eclipse.swt.widgets.Composite)
     */
	@Override
    protected Control createContent(Composite parent) {
        Composite container = new Composite(parent, SWT.NONE);
        container.setBackground(Colors.PAGE_BACKGROUND);
        GridLayout layout = new GridLayout();
        layout.marginHeight = layout.marginWidth = 15;
        layout.verticalSpacing = 15;
        container.setLayout(layout);
        
        // 设置使用缺省背景色
        UITool.setDefaultBackground(null);
        
        // 浏览器设置组
        layout = new GridLayout(3, false);
        layout.marginHeight = layout.marginWidth = 8;        
        Group browserGroup = UITool.createGroup(container, sys_opt_group_browser, layout);
        browserGroup.addPaintListener(paintListener);
        
        // 选择外部浏览器
        UITool.createLabel(browserGroup, sys_opt_other_label_browser);
        textBrowser = UITool.createSingleText(browserGroup, new GridData(GridData.FILL_HORIZONTAL | GridData.VERTICAL_ALIGN_CENTER));
        textBrowser.addModifyListener(new ModifyListener() {
            public void modifyText(ModifyEvent e) {
                makeDirty(BROWSER);
            }
        });
		// 选择按钮
		Button btnSelect = UITool.createButton(browserGroup, sys_opt_other_button_select);
		btnSelect.addSelectionListener(new SelectionAdapter() {
            @Override
			public void widgetSelected(SelectionEvent e) {
                FileDialog dialog = new FileDialog(parentShell, SWT.OPEN);
                String browser = dialog.open();
                if(browser != null) {
                    File file = new File(browser);
                    if(file.exists()) {
                        browser = file.getAbsolutePath() + " [URL]";
                        textBrowser.setText(browser);
                    }
                }
            }
		});
		
		// 最近联系人设置组
		layout = new GridLayout();
		layout.marginHeight = layout.marginWidth = 8;
		Group latestGroup = UITool.createGroup(container, sys_opt_group_latest, layout);
		
		// 打开/关闭最近联系人
		chkEnableLatest = UITool.createCheckbox(latestGroup, sys_opt_other_enable_latest);
		chkEnableLatest.addSelectionListener(new SelectionAdapter() {
            @Override
			public void widgetSelected(SelectionEvent e) {
                refreshLatestControl();
                makeDirty(ENABLE_LATEST);
            }
		});
		// 是否添加陌生人到最近联系人
		chkKeepStranger = UITool.createCheckbox(latestGroup, sys_opt_other_keep_stranger);
		chkKeepStranger.addSelectionListener(new SelectionAdapter() {
            @Override
			public void widgetSelected(SelectionEvent e) {
                makeDirty(KEEP_STRANGER);
            }
		});
		// 最近联系人个数
		layout = new GridLayout(2, false);
		layout.marginHeight = 8;
		layout.marginWidth = 0;
		Composite c = UITool.createContainer(latestGroup, new GridData(GridData.FILL_HORIZONTAL), layout);
		c.addPaintListener(paintListener);
		lblLatestSize = UITool.createLabel(c, sys_opt_other_latest_size);
		GridData gd = new GridData(GridData.VERTICAL_ALIGN_CENTER);
		gd.widthHint = 100;
		textLatestSize = UITool.createSingleText(c, gd);
		textLatestSize.addKeyListener(new KeyAdapter() {
            @Override
			public void keyPressed(KeyEvent e) {
                if(!Character.isDigit(e.character) && !Character.isISOControl(e.character))
                    e.doit = false;
            }
		});
		textLatestSize.addModifyListener(new ModifyListener() {
            public void modifyText(ModifyEvent e) {
                makeDirty(LATEST_SIZE);
            }
		});
		
		// 伪装设置组
		Group disguiseGroup = UITool.createGroup(container, sys_opt_group_disguise);
		
		// 虚拟摄像头
		chkShowFakeCam = UITool.createCheckbox(disguiseGroup, sys_opt_other_show_fake_cam);
		chkShowFakeCam.addSelectionListener(new SelectionAdapter() {
            @Override
			public void widgetSelected(SelectionEvent e) {
                makeDirty(SHOW_FAKE_CAM);
            }
		});
		
        return container;
    }
    
    /**
     * 刷新最近联系人设置组中的控件使能状态
     */
    private void refreshLatestControl() {
        boolean enabled = chkEnableLatest.getSelection();
        chkKeepStranger.setEnabled(enabled);
        textLatestSize.setEnabled(enabled);
        lblLatestSize.setEnabled(enabled);
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.shells.AbstractPage#getImage()
     */
	@Override
    protected Image getImage() {
        return Resources.getInstance().getImage(Resources.icoSysOpt24);
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.shells.AbstractPage#getTitle()
     */
	@Override
    protected String getTitle(int page) {
        return sys_opt_button_other;
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.shells.AbstractPage#saveDirtyProperty(int)
     */
	@Override
    protected void saveDirtyProperty(int propertyId) {
        OptionHelper options = main.getOptionHelper();
        switch(propertyId) {
            case BROWSER:
                options.setBrowser(textBrowser.getText().trim());
                break;
            case ENABLE_LATEST:
                options.setEnableLatest(chkEnableLatest.getSelection());
       		    main.getBlindHelper().setLatestGroupVisible(chkEnableLatest.getSelection());
                break;
            case KEEP_STRANGER:
                options.setKeepStrangerInLatest(chkKeepStranger.getSelection());
                break;
            case LATEST_SIZE:
                options.setLatestSize(textLatestSize.getText());
                break;
            case SHOW_FAKE_CAM:
                options.setShowFakeCam(chkShowFakeCam.getSelection());
        		main.getClient().getUser().setShowFakeCam(chkShowFakeCam.getSelection());
                break;
        }
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.shells.AbstractPage#initializeValues()
     */
	@Override
    protected void initializeValues() {
        OptionHelper options = main.getOptionHelper();
		textBrowser.setText(options.getBrowser());
		chkEnableLatest.setSelection(options.isEnableLatest());
		chkKeepStranger.setSelection(options.isKeepStrangerInLatest());
		textLatestSize.setText(String.valueOf(options.getLatestSize()));
		chkShowFakeCam.setSelection(options.isShowFakeCam());
		refreshLatestControl();
    }
}
