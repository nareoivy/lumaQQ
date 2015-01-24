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

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;

import edu.tsinghua.lumaqq.LumaQQ;
import edu.tsinghua.lumaqq.resource.Colors;
import edu.tsinghua.lumaqq.resource.Resources;
import edu.tsinghua.lumaqq.ui.BorderStyler;
import edu.tsinghua.lumaqq.ui.MainShell;
import edu.tsinghua.lumaqq.ui.SendIMTabWindow;
import edu.tsinghua.lumaqq.ui.config.AbstractPage;
import edu.tsinghua.lumaqq.ui.helper.OptionHelper;
import edu.tsinghua.lumaqq.ui.helper.UITool;

/**
 * GUI参数设置页
 * 
 * @author luma
 */
public class GUIPage extends AbstractPage {
    private Button chkShowFriendTip, chkShowOnlineTip, chkShowLastLogin;
    private Button chkAutoDock, chkUseTabIM;
    private MainShell main;
	private Button chkShowSignature, chkMinimizeWhenClose, chkOnTop, chkHideWhenMinimize, chkImOnTop, chkShowCustomHead;
    
    // 属性标识
    private static final int AUTO_DOCK = 0;
    private static final int SHOW_FRIEND_TIP = 1;
    private static final int SHOW_ONLINE_TIP = 2;
    private static final int SHOW_LAST_LOGIN = 3;
    private static final int USE_TAB_IM = 4;
    private static final int SHOW_SIGNATURE = 5;
    private static final int MINIMIZE_WHEN_CLOSE = 6;
    private static final int ON_TOP = 7;
    private static final int HIDE_WHEN_MINIMIZE = 8;
    private static final int IM_ON_TOP = 9;
    private static final int SHOW_CUSTOM_HEAD = 10;
    
    /**
     * @param parent
     * @param main
     */
    public GUIPage(Composite parent, MainShell main) {    	
        super(parent);
        this.main = main;
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
        
        // 窗口设置组
        Group winGroup = UITool.createGroup(container, sys_opt_group_window);
        
        // 主窗口总在最前
        chkOnTop = UITool.createCheckbox(winGroup, sys_opt_gui_on_top);
		chkOnTop.addSelectionListener(new SelectionAdapter() {
            @Override
			public void widgetSelected(SelectionEvent e) {
                makeDirty(ON_TOP);
                chkAutoDock.setEnabled(chkOnTop.getSelection());
                chkHideWhenMinimize.setEnabled(!chkOnTop.getSelection());
            }
		});
		
		// 最小化时隐藏
		chkHideWhenMinimize = UITool.createCheckbox(winGroup, sys_opt_gui_hide_when_minimize);
		chkHideWhenMinimize.addSelectionListener(new SelectionAdapter() {
            @Override
			public void widgetSelected(SelectionEvent e) {
                makeDirty(HIDE_WHEN_MINIMIZE);
            }
		});
		
        // 停靠时自动隐藏check box
		chkAutoDock = UITool.createCheckbox(winGroup, sys_opt_gui_auto_hide);
		chkAutoDock.addSelectionListener(new SelectionAdapter() {
            @Override
			public void widgetSelected(SelectionEvent e) {
                makeDirty(AUTO_DOCK);
            }
		});
		
		// 使用标签页式消息窗口
		chkUseTabIM = UITool.createCheckbox(winGroup, sys_opt_gui_use_tab_im);
		chkUseTabIM.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				makeDirty(USE_TAB_IM);
			}
		});
		
		// 点击关闭按钮时最小化
		chkMinimizeWhenClose = UITool.createCheckbox(winGroup, sys_opt_gui_minimize_when_close);
		chkMinimizeWhenClose.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				makeDirty(MINIMIZE_WHEN_CLOSE);
			}
		});
		
		// 聊天窗口总在最前
		chkImOnTop = UITool.createCheckbox(winGroup, sys_opt_gui_im_on_top);
		chkImOnTop.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				makeDirty(IM_ON_TOP);
			}
		});
		// 在Linux下，SWT顶层窗口获得焦点有一定的问题，所以只好禁止掉
		if(!LumaQQ.IS_WIN32)
			chkImOnTop.setVisible(false);
		
        // 提示设置组
        Group tipGroup = UITool.createGroup(container, sys_opt_group_tip);
        
		// 是否显示好友浮动提示提示
		chkShowFriendTip = UITool.createCheckbox(tipGroup, sys_opt_gui_show_tip);
		chkShowFriendTip.addSelectionListener(new SelectionAdapter() {
            @Override
			public void widgetSelected(SelectionEvent e) {
                makeDirty(SHOW_FRIEND_TIP);
            }
		});
		// 是否显示好友上站通知
		chkShowOnlineTip = UITool.createCheckbox(tipGroup, sys_opt_gui_show_online_tip);
		chkShowOnlineTip.addSelectionListener(new SelectionAdapter() {
            @Override
			public void widgetSelected(SelectionEvent e) {
                makeDirty(SHOW_ONLINE_TIP);
            }
		});
		// 是否显示上次登录信息
		chkShowLastLogin = UITool.createCheckbox(tipGroup, sys_opt_gui_show_last_login_tip);
		chkShowLastLogin.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				makeDirty(SHOW_LAST_LOGIN);
			}
		});		
		
        // 观感设置组
        Group feelGroup = UITool.createGroup(container, sys_opt_group_feel);
        
        // 是否显示个性签名
        chkShowSignature = UITool.createCheckbox(feelGroup, sys_opt_gui_show_signature);
        chkShowSignature.addSelectionListener(new SelectionAdapter() {
            @Override
			public void widgetSelected(SelectionEvent e) {
                makeDirty(SHOW_SIGNATURE);
            }
		});
        
        // 是否显示自定义头像
        chkShowCustomHead = UITool.createCheckbox(feelGroup, sys_opt_gui_show_custom_head);
        chkShowCustomHead.addSelectionListener(new SelectionAdapter() {
            @Override
			public void widgetSelected(SelectionEvent e) {
                makeDirty(SHOW_CUSTOM_HEAD);
            }
		});
		
        return container;
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
        return sys_opt_button_gui;
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.shells.AbstractPage#saveDirtyProperty(int)
     */
	@Override
    protected void saveDirtyProperty(int propertyId) {
        OptionHelper options = main.getOptionHelper();
        
        switch(propertyId) {
            case AUTO_DOCK:
				options.setAutoDock(chkAutoDock.getSelection());
				if(!chkAutoDock.getSelection() && !main.isOff())
					main.off();				
				break;
			case SHOW_FRIEND_TIP:
				options.setShowFrientTip(chkShowFriendTip.getSelection());
				break;
			case SHOW_ONLINE_TIP:
				options.setShowOnlineTip(chkShowOnlineTip.getSelection());
				break;
			case USE_TAB_IM:
				options.setUseTabIMWindow(chkUseTabIM.getSelection());
				if(chkUseTabIM.getSelection()) {
					main.getShellRegistry().clearAllSendClusterIMWindow();
					main.getShellRegistry().clearAllSendIMWindow();
				} else {
					SendIMTabWindow window = main.getShellRegistry().getSendIMTabWindow();
					if(window != null)
						window.closeWindow();
				}
				break;
			case SHOW_SIGNATURE:
				options.setShowSignature(chkShowSignature.getSelection());
				main.getBlindHelper().setShowSignature(chkShowSignature.getSelection());
				main.getBlindHelper().refreshAll();
				break;
			case MINIMIZE_WHEN_CLOSE:
				options.setMinimizeWhenClose(chkMinimizeWhenClose.getSelection());
				break;
			case ON_TOP:
				options.setOnTop(chkOnTop.getSelection());
				break;
			case HIDE_WHEN_MINIMIZE:
				options.setHideWhenMinimize(chkHideWhenMinimize.getSelection());
				((BorderStyler)main.getShell().getData(BorderStyler.STYLER)).setHideWhenMinimize(chkHideWhenMinimize.getSelection());
				break;
			case IM_ON_TOP:
				options.setIMOnTop(chkImOnTop.getSelection());
				break;
			case SHOW_CUSTOM_HEAD:
				options.setShowCustomHead(chkShowCustomHead.getSelection());
				main.getBlindHelper().setShowCustomHead(chkShowCustomHead.getSelection());
				main.getBlindHelper().refreshAll();
				break;
			case SHOW_LAST_LOGIN:
				options.setShowLastLoginTip(chkShowLastLogin.getSelection());
				break;
        }
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.shells.AbstractPage#initializeValues()
     */
	@Override
    protected void initializeValues() {
		OptionHelper options = main.getOptionHelper();
		chkShowFriendTip.setSelection(options.isShowFriendTip());
		chkShowOnlineTip.setSelection(options.isShowOnlineTip());
		chkAutoDock.setSelection(options.isAutoDock());
		chkUseTabIM.setSelection(options.isUseTabIMWindow());
		chkShowSignature.setSelection(options.isShowSignature());
		chkMinimizeWhenClose.setSelection(options.isMinimizeWhenClose());
		chkOnTop.setSelection(options.isOnTop());
		chkHideWhenMinimize.setSelection(options.isHideWhenMinimize());
		chkImOnTop.setSelection(options.isIMOnTop());
        chkAutoDock.setEnabled(chkOnTop.getSelection());
        chkHideWhenMinimize.setEnabled(!chkOnTop.getSelection());
        chkShowCustomHead.setSelection(options.isShowCustomHead());
        chkShowLastLogin.setSelection(options.isShowLastLoginTip());
    }
}
