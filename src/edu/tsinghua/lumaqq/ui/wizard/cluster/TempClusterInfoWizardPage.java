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

import static edu.tsinghua.lumaqq.resource.Messages.error_empty_name;
import static edu.tsinghua.lumaqq.resource.Messages.error_parent_empty;
import static edu.tsinghua.lumaqq.resource.Messages.temp_message;
import static edu.tsinghua.lumaqq.resource.Messages.temp_name;
import static edu.tsinghua.lumaqq.resource.Messages.temp_parent_cluster;
import static edu.tsinghua.lumaqq.resource.Messages.temp_title;

import java.util.ArrayList;
import java.util.List;

import edu.tsinghua.lumaqq.models.Cluster;
import edu.tsinghua.lumaqq.models.Group;
import edu.tsinghua.lumaqq.qq.QQ;
import edu.tsinghua.lumaqq.resource.Colors;
import edu.tsinghua.lumaqq.ui.MainShell;
import edu.tsinghua.lumaqq.ui.helper.UITool;
import edu.tsinghua.lumaqq.ui.listener.AroundBorderPaintListener;
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
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

/**
 * 临时群信息填写页
 * 
 * @author luma
 */
public class TempClusterInfoWizardPage extends WizardPage {
    private Text textName;
    private Label lblParent;
    private CCombo comboParent;
    private ClusterWizard wizard;
    private List<Cluster> clusters;
    private ClusterWizardModel model;
    
    /**
     * @param pageName
     */
    protected TempClusterInfoWizardPage(String pageName) {
        super(pageName);
        setTitle(temp_title);
        setMessage(temp_message);
    }

    /* (non-Javadoc)
     * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
     */
    public void createControl(Composite parent) {
        Composite control = new Composite(parent, SWT.NONE);
        control.setLayoutData(new GridData());
        GridLayout layout = new GridLayout(2, false);
        layout.marginHeight = layout.marginWidth = 15;
        layout.verticalSpacing = 8;
        control.setLayout(layout);
        control.addPaintListener(new AroundBorderPaintListener(new Class[] { Text.class, CCombo.class }));

        UITool.setDefaultBackground(control.getBackground());
        
        // 群名称
        UITool.createLabel(control, temp_name);
        // 群名称文本框
        GridData gd = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
        gd.heightHint = 18;
        gd.widthHint = 200;
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
        // 父群
        lblParent = UITool.createLabel(control, temp_parent_cluster);
        // 父群选择框
        gd = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
        comboParent = UITool.createCCombo(control, gd);
        comboParent.setBackground(Colors.WHITE);        
        comboParent.addSelectionListener(new SelectionAdapter() {
        	@Override
        	public void widgetSelected(SelectionEvent e) {
                if(comboParent.getItemCount() == 0)
                    model.setParentCluster(null);
                else
                    model.setParentCluster(clusters.get(comboParent.getSelectionIndex()));
        	}
        });
        initParentCluster();
        refreshControlStatus();
        
        setControl(control);
    }
    
    /**
     * 刷新控件状态
     */
    private void refreshControlStatus() {
        boolean visible = model.getClusterType() == ClusterWizardModel.SUBJECT;
        lblParent.setVisible(visible);
        comboParent.setVisible(visible);
    }

    /**
     * 把现有的群添加到下拉框中
     */
    private void initParentCluster() {
        clusters = new ArrayList<Cluster>();
        if(model.getClusterType() == ClusterWizardModel.DIALOG)
            return;
        
        MainShell main = wizard.getMainShell();
        Group g = main.getBlindHelper().getClusterGroup();
        for(Cluster c : g.clusters) {
            if(c.isPermanent()) {
                comboParent.add(c.name);
                clusters.add(c);
            }
        }
        int index = 0;
        if(clusters.isEmpty()) {
        	model.setParentCluster(null);
        } else if(model.getParentCluster() == null) {
        	model.setParentCluster(clusters.get(0));
        } else {
        	index = clusters.indexOf(model.getParentCluster());
        	if(index == -1)
        		index = 0;
        	model.setParentCluster(clusters.get(index));
        }
       	comboParent.select(index);
        comboParent.setVisibleItemCount(10);
    }
    
    /**
     * 发送创建临时群请求
     */
    public void doCreate() {
        wizard.getMainShell().getClient().cluster_CreateTemporary(
                getClusterName(),
                (model.getClusterType() == ClusterWizardModel.DIALOG) ? QQ.QQ_CLUSTER_TYPE_DIALOG : QQ.QQ_CLUSTER_TYPE_SUBJECT,
                model.getParentClusterId(),
                model.getMemberQQArray());
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
     * @see org.eclipse.jface.dialogs.IDialogPage#setVisible(boolean)
     */
    @Override
	public void setVisible(boolean visible) {
        if(visible)
            refreshControlStatus();
        super.setVisible(visible);        
    }
    
    /**
     * @return
     * 		群名称
     */
    public String getClusterName() {
        return textName.getText().trim();
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
     * 		true表示输入有效
     */
    private boolean validatePage() {
        if("".equals(textName.getText().trim())) {
            setErrorMessage(error_empty_name);
            return false;
        }
        
        if(model.getClusterType() == ClusterWizardModel.SUBJECT) {
            if(comboParent.getSelectionIndex() == -1) {
                setErrorMessage(error_parent_empty);
                return false;
            }
        }
        
        setErrorMessage(null);
        return true;
    }
}
