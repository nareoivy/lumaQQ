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
package edu.tsinghua.lumaqq.ui.wizard.cluster;

import static edu.tsinghua.lumaqq.resource.Messages.*;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;

import edu.tsinghua.lumaqq.qq.QQ;
import edu.tsinghua.lumaqq.resource.Colors;
import edu.tsinghua.lumaqq.ui.MainShell;
import edu.tsinghua.lumaqq.ui.dialogs.ClusterCategoryDialog;
import edu.tsinghua.lumaqq.ui.helper.UITool;
import edu.tsinghua.lumaqq.ui.listener.AroundBorderPaintListener;
import edu.tsinghua.lumaqq.ui.listener.CenterBorderPaintListener;

/**
 * 固定群信息填写页
 * 
 * @author luma
 */
public class PermanentClusterInfoWizardPage extends WizardPage {
	private Text textName;
    private ClusterWizard wizard;
    private ClusterWizardModel model;
	private Text textCategory;
	private MainShell main;
    
    /**
     * @param pageName
     */
    protected PermanentClusterInfoWizardPage(String pageName, MainShell main) {
        super(pageName);
        setTitle(permanent_title);
        setMessage(permanent_message);
        this.main = main;
    }

    /* (non-Javadoc)
     * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
     */
    public void createControl(Composite parent) {
        final Composite control = new Composite(parent, SWT.NONE);
        control.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        GridLayout layout = new GridLayout(2, false);
        layout.marginHeight = layout.marginWidth = 15;
        layout.verticalSpacing = 8;
        control.setLayout(layout);
        control.addPaintListener(new AroundBorderPaintListener(new Class[] { Text.class, CCombo.class }));

        UITool.setDefaultBackground(control.getBackground());
        
        // 群名称
        UITool.createLabel(control, permanent_name);
        GridData gd = new GridData(GridData.FILL_HORIZONTAL);
        gd.heightHint = 18;
        textName = UITool.createSingleText(control, gd);
        textName.setBackground(Colors.WHITE);
        textName.addModifyListener(new ModifyListener() {
            public void modifyText(ModifyEvent e) {
            	if(validatePage()) {
            		setPageComplete(true); 
            		model.setName(textName.getText().trim());
            	} else
            		setPageComplete(false);
            }
        });
        // 群分类
        UITool.createLabel(control, permanent_category);
        layout = new GridLayout(2, false);
        layout.marginHeight = 1;
        layout.marginWidth = 3;
        Composite temp = UITool.createContainer(control, new GridData(GridData.FILL_HORIZONTAL), layout);
        temp.addPaintListener(new CenterBorderPaintListener(new Class[] { Text.class }, 18, Colors.PAGE_CONTROL_BORDER));
        textCategory = UITool.createSingleText(temp, new GridData(GridData.FILL_HORIZONTAL), SWT.SINGLE | SWT.READ_ONLY);
        Button btnSetting = UITool.createButton(temp, button_setting);
        btnSetting.addSelectionListener(new SelectionAdapter() {
        	@Override
        	public void widgetSelected(SelectionEvent e) {
        		ClusterCategoryDialog dialog = new ClusterCategoryDialog(control.getShell(), main);
        		dialog.setCategoryId(model.getCategory());
        		if(IDialogConstants.OK_ID == dialog.open()) {
        			model.setCategory(dialog.getCategoryId());
        			textCategory.setText(main.getClusterCategoryUtility().getCategoryPath(model.getCategory()));
        		}
        	}
        });
        // 群公告
        UITool.createLabel(control, permanent_notice, new GridData(GridData.VERTICAL_ALIGN_BEGINNING));
        gd = new GridData(GridData.FILL_HORIZONTAL);
        gd.heightHint = 50;
        Text textNotice = UITool.createMultiText(control, gd);
        textNotice.setBackground(Colors.WHITE);
        textNotice.addModifyListener(new ModifyListener() {
        	public void modifyText(ModifyEvent e) {
        		model.setNotice(((Text)e.getSource()).getText());
        	}
        });
        // 群简介
        UITool.createLabel(control, permanent_description, new GridData(GridData.VERTICAL_ALIGN_BEGINNING));
        gd = new GridData(GridData.FILL_HORIZONTAL);
        gd.heightHint = 50;
        Text textDescription = UITool.createMultiText(control, gd);
        textDescription.setBackground(Colors.WHITE);
        textDescription.addModifyListener(new ModifyListener() {
        	public void modifyText(ModifyEvent e) {
        		model.setDescription(((Text)e.getSource()).getText());
        	}
        });
        
        // 验证组
        gd = new GridData(GridData.FILL_HORIZONTAL);
        gd.horizontalSpan = 2;
        Group authGroup = UITool.createGroup(control, permanent_auth, gd, new GridLayout());
        UITool.createRadio(authGroup, permanent_no_auth).addSelectionListener(new SelectionAdapter() {
        	@Override
        	public void widgetSelected(SelectionEvent e) {
        		model.setAuthType(QQ.QQ_AUTH_CLUSTER_NO);
        	}
        });        
        Button rdoNeedAuth = UITool.createRadio(authGroup, permanent_need_auth);
        rdoNeedAuth.setSelection(true);
        rdoNeedAuth.addSelectionListener(new SelectionAdapter() {
        	@Override
        	public void widgetSelected(SelectionEvent e) {
        		model.setAuthType(QQ.QQ_AUTH_CLUSTER_NEED);
        	}
        });
        UITool.createRadio(authGroup, permanent_no_add).addSelectionListener(new SelectionAdapter() {
        	@Override
        	public void widgetSelected(SelectionEvent e) {
        		model.setAuthType(QQ.QQ_AUTH_CLUSTER_REJECT);
        	}
        });
        
        setControl(control);
    }

    /**
     * 发送创建固定群请求包
     */
    public void doCreate() {
        wizard.getMainShell().getClient().cluster_CreatePermanent(
                model.getName(),
                model.getNotice(),
                model.getDescription(), 
                model.getMemberQQArray(),
                model.getCategory(),
                model.getAuthType());
    }
    
    /* (non-Javadoc)
     * @see org.eclipse.jface.wizard.WizardPage#setWizard(org.eclipse.jface.wizard.IWizard)
     */
    @Override
	public void setWizard(IWizard newWizard) {
        super.setWizard(newWizard);
        wizard = (ClusterWizard)newWizard;
        model = (ClusterWizardModel)wizard.getModel();
    }
    
    /* (non-Javadoc)
     * @see org.eclipse.jface.wizard.WizardPage#isPageComplete()
     */
    @Override
	public boolean isPageComplete() {
        return validatePage();
    }

    /**
     * @return
     * 		true表示用户输入有效
     */
    private boolean validatePage() {
        if("".equals(textName.getText().trim())) {
            setErrorMessage(error_empty_name);
            return false;
        }
        
        setErrorMessage(null);
        return true;
    }
}
