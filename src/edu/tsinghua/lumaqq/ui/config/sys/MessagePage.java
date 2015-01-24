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

import edu.tsinghua.lumaqq.resource.Colors;
import edu.tsinghua.lumaqq.resource.Resources;
import edu.tsinghua.lumaqq.ui.MainShell;
import edu.tsinghua.lumaqq.ui.config.AbstractPage;
import edu.tsinghua.lumaqq.ui.helper.OptionHelper;
import edu.tsinghua.lumaqq.ui.helper.UITool;

/**
 * 消息设置页
 * 
 * @author luma
 */
public class MessagePage extends AbstractPage {
    private Button chkAutoEject, chkRejectStranger, chkEnableSound, chkRejectTempSessionIM;
    
    // 属性ID
    private static final int AUTO_EJECT = 0;
    private static final int REJECT_STRANGER = 1;
    private static final int ENABLE_SOUND = 2;
    private static final int REJECT_TEMP_SESSION_IM = 3;
    
    private MainShell main;
    
    /**
     * @param parent
     */
    public MessagePage(Composite parent, MainShell main) {
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
        
        // 接收设置组
        Group recvGroup = UITool.createGroup(container, sys_opt_group_receive);
        
		// 自动弹出check box
		chkAutoEject = UITool.createCheckbox(recvGroup, sys_opt_message_auto_eject);
		chkAutoEject.addSelectionListener(new SelectionAdapter() {
            @Override
			public void widgetSelected(SelectionEvent e) {
                makeDirty(AUTO_EJECT);
            }
		});
		// 拒绝陌生人消息check box
		chkRejectStranger = UITool.createCheckbox(recvGroup, sys_opt_message_reject_stranger);
		chkRejectStranger.addSelectionListener(new SelectionAdapter() {
            @Override
			public void widgetSelected(SelectionEvent e) {
                makeDirty(REJECT_STRANGER);
            }
		});
		// 拒绝临时会话消息
		chkRejectTempSessionIM = UITool.createCheckbox(recvGroup, sys_opt_message_reject_temp_session_im);
		chkRejectTempSessionIM.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				makeDirty(REJECT_TEMP_SESSION_IM);
			}
		});
		
        // 声音设置组
        Group soundGroup = UITool.createGroup(container, sys_opt_group_sound);
        
        // 使能声音提示
		chkEnableSound = UITool.createCheckbox(soundGroup, sys_opt_message_enable_sound);
		chkEnableSound.addSelectionListener(new SelectionAdapter() {
            @Override
			public void widgetSelected(SelectionEvent e) {
                makeDirty(ENABLE_SOUND);
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
        return sys_opt_button_message;
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.shells.AbstractPage#saveDirtyProperty(int)
     */
	@Override
    protected void saveDirtyProperty(int propertyId) {
        OptionHelper options = main.getOptionHelper();
        switch(propertyId) {
            case AUTO_EJECT:
				options.setAutoEject(chkAutoEject.getSelection());
				break;
			case REJECT_STRANGER:
				options.setRejectStranger(chkRejectStranger.getSelection());
				break;
			case ENABLE_SOUND:
				options.setEnableSound(chkEnableSound.getSelection());
				main.getSounder().setEnable(chkEnableSound.getSelection());
				break;
			case REJECT_TEMP_SESSION_IM:
				options.setRejectTempSessionIM(chkRejectTempSessionIM.getSelection());
				break;
        }
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.shells.AbstractPage#initializeValues()
     */
	@Override
    protected void initializeValues() {
        OptionHelper options = main.getOptionHelper();
		chkAutoEject.setSelection(options.isAutoEject());
		chkRejectStranger.setSelection(options.isRejectStranger());
		chkEnableSound.setSelection(options.isEnableSound());
		chkRejectTempSessionIM.setSelection(options.isRejectTempSessionIM());
    }
}
