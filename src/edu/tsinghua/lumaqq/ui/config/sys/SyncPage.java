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
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;

import edu.tsinghua.lumaqq.ecore.option.OpType;
import edu.tsinghua.lumaqq.resource.Colors;
import edu.tsinghua.lumaqq.resource.Resources;
import edu.tsinghua.lumaqq.ui.MainShell;
import edu.tsinghua.lumaqq.ui.config.AbstractPage;
import edu.tsinghua.lumaqq.ui.helper.OptionHelper;
import edu.tsinghua.lumaqq.ui.helper.UITool;

/**
 * 同步配置页
 * 
 * @author luma
 */
public class SyncPage extends AbstractPage {
    private Button chkAutoDownloadGroup;
    private Button chkAutoDownloadFriendRemark;
    private Button chkAutoCheckUpdate;
    private Button rdoPromptUpload;
    private Button rdoAlwaysUpload;
    private Button rdoNeverUpload;
    
    private static final int AUTO_DOWNLOAD = 0;
    private static final int AUTO_UPLOAD = 1;
    private static final int AUTO_DOWNLOAD_FRIEND_REMARK = 2;
    private static final int AUTO_CHECK_UPDATE = 3;
    
    private MainShell main;
    
    /**
     * @param parent
     */
    public SyncPage(Composite parent, MainShell main) {
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
        
        // 下载设置组
        layout = new GridLayout();
        layout.marginHeight = layout.marginWidth = 8;
        Group downGroup = UITool.createGroup(container, sys_opt_group_download_group, layout);
        
        // 是否自动同步分组
        chkAutoDownloadGroup = UITool.createCheckbox(downGroup, sys_opt_sync_auto_download_group);
        chkAutoDownloadGroup.addSelectionListener(new SelectionAdapter() {
            @Override
			public void widgetSelected(SelectionEvent e) {
                makeDirty(AUTO_DOWNLOAD);
            }
        });
        
        // 是否自动下载好友备注
        chkAutoDownloadFriendRemark = UITool.createCheckbox(downGroup, sys_opt_sync_auto_download_friend_remark);
        chkAutoDownloadFriendRemark.addSelectionListener(new SelectionAdapter() {
            @Override
			public void widgetSelected(SelectionEvent e) {
                makeDirty(AUTO_DOWNLOAD_FRIEND_REMARK);
            }
        });
        
        // 上传设置组
        layout = new GridLayout();
        layout.marginHeight = layout.marginWidth = 8;
        Group upGroup = UITool.createGroup(container, sys_opt_group_upload_group, layout);

        SelectionListener sl = new SelectionAdapter() {
            @Override
			public void widgetSelected(SelectionEvent e) {
                makeDirty(AUTO_UPLOAD);
            }
        };
        
        // prompt
        rdoPromptUpload = UITool.createRadio(upGroup, sys_opt_sync_prompt_upload_group);
        rdoPromptUpload.addSelectionListener(sl);
        // always
        rdoAlwaysUpload = UITool.createRadio(upGroup, sys_opt_sync_always_upload_group);
        rdoAlwaysUpload.addSelectionListener(sl);
        // never
        rdoNeverUpload = UITool.createRadio(upGroup, sys_opt_sync_never_upload_group);        
        rdoNeverUpload.addSelectionListener(sl);
        
        // 检测更新设置组
        layout = new GridLayout();
        layout.marginHeight = layout.marginWidth = 8;
        Group cuGroup = UITool.createGroup(container, sys_opt_group_check_update, layout);
        
        // 自动检测
        chkAutoCheckUpdate = UITool.createCheckbox(cuGroup, sys_opt_sync_auto_check_update);
        chkAutoCheckUpdate.addSelectionListener(new SelectionAdapter() {
            @Override
			public void widgetSelected(SelectionEvent e) {
                makeDirty(AUTO_CHECK_UPDATE);
            }
        });

        return container;
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.ui.config.AbstractPage#saveDirtyProperty(int)
     */
	@Override
    protected void saveDirtyProperty(int propertyId) {
        OptionHelper options = main.getOptionHelper();
        
        switch(propertyId) {
            case AUTO_DOWNLOAD:
				options.setAutoDownloadGroup(chkAutoDownloadGroup.getSelection());
				break;
			case AUTO_UPLOAD:
			    if(rdoAlwaysUpload.getSelection())
			        options.setAutoUploadGroup(OpType.ALWAYS_LITERAL);
			    else if(rdoNeverUpload.getSelection())
			        options.setAutoUploadGroup(OpType.NEVER_LITERAL);
			    else
			        options.setAutoUploadGroup(OpType.PROMPT_LITERAL);
			    break;
			case AUTO_DOWNLOAD_FRIEND_REMARK:
			    options.setAutoDownloadFriendRemark(chkAutoDownloadFriendRemark.getSelection());
			    break;
			case AUTO_CHECK_UPDATE:
				options.setAutoCheckUpdate(chkAutoCheckUpdate.getSelection());
				break;
        }
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.ui.config.AbstractPage#initializeValues()
     */
	@Override
    protected void initializeValues() {
		OptionHelper options = main.getOptionHelper();
		chkAutoDownloadGroup.setSelection(options.isAutoDownloadGroup());
		OpType s = options.getAutoUploadGroup();
		if(s == OpType.ALWAYS_LITERAL)
		    rdoAlwaysUpload.setSelection(true);
		else if(s == OpType.NEVER_LITERAL)
		    rdoNeverUpload.setSelection(true);
		else
		    rdoPromptUpload.setSelection(true);
		chkAutoCheckUpdate.setSelection(options.isAutoCheckUpdate());
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.ui.config.AbstractPage#getImage()
     */
	@Override
    protected Image getImage() {
        return Resources.getInstance().getImage(Resources.icoSysOpt24);
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.ui.config.AbstractPage#getTitle()
     */
	@Override
    protected String getTitle(int page) {
        return sys_opt_button_sync;
    }
}
