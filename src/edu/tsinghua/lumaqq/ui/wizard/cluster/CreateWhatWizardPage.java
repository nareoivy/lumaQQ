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

import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

import edu.tsinghua.lumaqq.ui.helper.UITool;

/**
 * 选择要创建群的类型
 * 
 * @author luma
 */
public class CreateWhatWizardPage extends WizardPage {    
    private Button rdoPermanent, rdoDialog, rdoSubject;    
    private ClusterWizardModel model;
    
    /**
     * @param pageName
     */
    protected CreateWhatWizardPage(String pageName) {
        super(pageName);
        setTitle(create_what_title);
        setMessage(create_what_message);
        setPageComplete(true);
    }
    
    @Override
    public void setWizard(IWizard newWizard) {
    	super.setWizard(newWizard);
    	model = (ClusterWizardModel)((ClusterWizard)newWizard).getModel();
    }

    /* (non-Javadoc)
     * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
     */
    public void createControl(Composite parent) {
        Composite control = new Composite(parent, SWT.NONE);
        control.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        GridLayout layout = new GridLayout();
        layout.marginHeight = layout.marginWidth = 15;
        layout.verticalSpacing = 8;
        control.setLayout(layout);
                
        UITool.setDefaultBackground(control.getBackground());
        
        // 固定群
        rdoPermanent = UITool.createRadio(control, create_what_permanent);
        rdoPermanent.setSelection(true);
        rdoPermanent.addSelectionListener(new SelectionAdapter() {
        	@Override
			public void widgetSelected(SelectionEvent e) {
        		model.setClusterType(ClusterWizardModel.PERMANENT_CLUSTER);
        	}
        });
        // 多人对话
        rdoDialog = UITool.createRadio(control, create_what_dialog);
        rdoDialog.addSelectionListener(new SelectionAdapter() {
        	@Override
        	public void widgetSelected(SelectionEvent e) {
        		model.setClusterType(ClusterWizardModel.DIALOG);
        	}
        });
        // 讨论组
        rdoSubject = UITool.createRadio(control, create_what_subject);
        rdoSubject.addSelectionListener(new SelectionAdapter() {
        	@Override
        	public void widgetSelected(SelectionEvent e) {
        		model.setClusterType(ClusterWizardModel.SUBJECT);
        	}
        });
        
        setControl(control);
    }
    
    @Override
    public void setVisible(boolean visible) {
    	super.setVisible(visible);
    	if(visible) {
    		switch(model.getClusterType()) {
    			case ClusterWizardModel.DIALOG:
    				rdoPermanent.setSelection(false);
    				rdoDialog.setSelection(true);
    				rdoSubject.setSelection(false);
    				break;
    			case ClusterWizardModel.SUBJECT:
    				rdoPermanent.setSelection(false);
    				rdoDialog.setSelection(false);
    				rdoSubject.setSelection(true);
    				break;
    			case ClusterWizardModel.PERMANENT_CLUSTER:
    				rdoPermanent.setSelection(true);
    				rdoDialog.setSelection(false);
    				rdoSubject.setSelection(false);
    				break;    				
    		}
    	}
    }
}
