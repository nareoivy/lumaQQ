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
package edu.tsinghua.lumaqq.ui.wizard.search;

import static edu.tsinghua.lumaqq.resource.Messages.*;
import static edu.tsinghua.lumaqq.ui.wizard.search.SearchWizardModel.*;

import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

import edu.tsinghua.lumaqq.resource.Colors;
import edu.tsinghua.lumaqq.ui.helper.UITool;
import edu.tsinghua.lumaqq.widgets.mac.Ring;

/**
 * 添加好友或者群的页面
 * 
 * @author luma
 */
public class AddFriendClusterWizardPage extends WizardPage {
    private CLabel lblHint;
    private Text textAuth;
    private Button btnSend;
    private SearchWizard wizard;
    private SearchWizardModel model;
    private Ring ring;
    
    /**
     * @param pageName
     */
    protected AddFriendClusterWizardPage(String pageName) {
        super(pageName);
        setTitle(add_title);
        setMessage(add_message);
    }
    
    /* (non-Javadoc)
     * @see org.eclipse.jface.wizard.WizardPage#setWizard(org.eclipse.jface.wizard.IWizard)
     */
    @Override
	public void setWizard(IWizard newWizard) {
        super.setWizard(newWizard);
        wizard = (SearchWizard)newWizard;
        model = (SearchWizardModel)wizard.getModel();
    }

    /* (non-Javadoc)
     * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
     */
    public void createControl(Composite parent) {
        Composite control = new Composite(parent, SWT.NONE);
        control.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        GridLayout layout = new GridLayout();
        layout.verticalSpacing = 20;
        control.setLayout(layout);
        
        UITool.setDefaultBackground(control.getBackground());
        
        // busy ring
        GridData gd = new GridData(GridData.HORIZONTAL_ALIGN_CENTER);
        gd.heightHint = gd.widthHint = 18;
        ring = UITool.createRing(control, gd);
        // 提示标签
        lblHint = new CLabel(control, SWT.CENTER | SWT.WRAP);
        lblHint.setLayoutData(new GridData(GridData.FILL_BOTH));
        
        layout = new GridLayout(2, false);
        Composite c = UITool.createContainer(control, new GridData(GridData.HORIZONTAL_ALIGN_CENTER), layout);
        // 认证框
        gd = new GridData(GridData.HORIZONTAL_ALIGN_CENTER);
        gd.heightHint = 60;
        gd.widthHint = 300;
        textAuth = UITool.createMultiText(c, gd, SWT.MULTI | SWT.WRAP | SWT.BORDER);
        textAuth.setBackground(Colors.WHITE);
        // 发送按钮
        btnSend = UITool.createButton(c, button_send, new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.VERTICAL_ALIGN_FILL), SWT.PUSH);
        btnSend.addSelectionListener(new SelectionAdapter() {
            @Override
			public void widgetSelected(SelectionEvent e) {
                wizard.doAuth(textAuth.getText());
            }
        });
        
        setAuthControlVisible(false);
        model.setStatus(INIT);
        refresh();
        
        setControl(control);
    }
    
    /**
     * 设置认证控件的可见状态
     * 
     * @param b
     */
    public void setAuthControlVisible(boolean b) {
        textAuth.setVisible(b);
        btnSend.setVisible(b);
    }
    
    /* (non-Javadoc)
     * @see org.eclipse.jface.dialogs.IDialogPage#setVisible(boolean)
     */
    @Override
	public void setVisible(boolean visible) {
        super.setVisible(visible);
        if(visible) {
        	model.setEnterResultPage(false);
            wizard.doAdd();
        } else {
        	model.setStatus(INIT);
            refresh();
        }
    }
    
    /**
     * 设置状态，刷新各个控件状态
     * 
     * @param status
     */
    public void refresh() {
        switch(model.getStatus()) {
            case INIT:
            	ring.stop();
                setHint("");
                setAuthControlVisible(false);
                break;
            case ADDING:
            case JOINING:
            	ring.rotate();
                setHint(hint_processing);
                setAuthControlVisible(false);
                break;
            case AUTH_INPUTING:
            	ring.stop();
                setHint(hint_need_auth);
                setAuthControlVisible(true);
                setSendButtonEnable(true);
                break;
            case AUTH_SENDING:
            	ring.rotate();
                setHint(hint_processing);
                setAuthControlVisible(true);
                setSendButtonEnable(false);
                break;
            case ADD_FINISHED:
            	ring.stop();
                setHint(hint_added);
                setAuthControlVisible(false);
                break;
            case ADD_TIMEOUT:
            case JOIN_TIMEOUT:
            	ring.stop();
                setHint(error_timeout);
                setAuthControlVisible(false);
                break;
            case ADD_DENY:
            	ring.stop();
                setHint(hint_add_deny);
                setAuthControlVisible(false);
                break;
            case AUTH_SENT:
            	ring.stop();
                setHint(hint_auth_sent);
                setAuthControlVisible(false);
                break;
            case AUTH_TIMEOUT:
            	ring.stop();
                setHint(hint_auth_timeout);
                setAuthControlVisible(true);
                setSendButtonEnable(true);
                break;
            case JOIN_DENY:
            	ring.stop();
                setHint(hint_join_deny);
                setAuthControlVisible(false);
                break;
            case JOIN_FINISHED:
            	ring.stop();
                setHint(hint_joined);
                setAuthControlVisible(false);
                break;
        }
        
        setPageComplete(isPageComplete());
    }
    
    /**
     * 设置发送按钮的使能状态
     * 
     * @param b
     */
    private void setSendButtonEnable(boolean b) {
        btnSend.setEnabled(b);
    }
    
    /**
     * 设置提示信息
     * 
     * @param hint
     */
    private void setHint(String hint) {
        lblHint.setText(hint);
    }
    
    /* (non-Javadoc)
     * @see org.eclipse.jface.wizard.WizardPage#isPageComplete()
     */
    @Override
	public boolean isPageComplete() {
    	int status = model.getStatus();
        return status != INIT &&
        	status != ADDING &&
        	status != JOINING &&
        	status != AUTH_SENDING;
    }
}
