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

import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;

import edu.tsinghua.lumaqq.ui.helper.UITool;

/**
 * 查找什么。搜索向导的第一页
 * 
 * @author luma
 */
public class SearchWhatWizardPage extends WizardPage {
    private Button rdoUser, rdoCluster;
    private Label lblOnline;
    private SearchWizard wizard;
    private SearchWizardModel model;
    
    /**
     * @param pageName
     */
    public SearchWhatWizardPage(String pageName) {
        super(pageName);
        setTitle(search_what_title);
        setMessage(search_what_message);
    }

    /* (non-Javadoc)
     * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
     */
    public void createControl(Composite parent) {
        Composite control = new Composite(parent, SWT.NONE);
        control.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        GridLayout layout = new GridLayout();
        layout.marginWidth = layout.marginHeight = 15;
        layout.verticalSpacing = 8;
        control.setLayout(layout);
        
        UITool.setDefaultBackground(control.getBackground());
        
        Group g = UITool.createGroup(control, "", new GridData(GridData.FILL_HORIZONTAL), new GridLayout());
        
        // 查找用户
        rdoUser = UITool.createRadio(g, search_what_user);
        rdoUser.addSelectionListener(new SelectionAdapter() {
        	@Override
        	public void widgetSelected(SelectionEvent e) {
        		model.setSearchWhat(SearchWizardModel.USER);
        	}
        });
        // 查找群/校友录
        rdoCluster = UITool.createRadio(g, search_what_cluster);
        rdoCluster.addSelectionListener(new SelectionAdapter() {
        	@Override
        	public void widgetSelected(SelectionEvent e) {
        		model.setSearchWhat(SearchWizardModel.CLUSTER);
        	}
        });
        // 上线人数
        lblOnline = UITool.createLabel(control, "", new GridData(GridData.FILL_HORIZONTAL));
        setOnline(wizard.getMainShell().getCurrentOnlineNumber());
        
        setControl(control);
    }
    
    /* (non-Javadoc)
     * @see org.eclipse.jface.wizard.WizardPage#isPageComplete()
     */
    @Override
	public boolean isPageComplete() {
        return true;
    }
    
    @Override
    public void setVisible(boolean visible) {
    	super.setVisible(visible);
    	if(visible) {
    		rdoUser.setSelection(model.getSearchWhat() == SearchWizardModel.USER);
    		rdoCluster.setSelection(model.getSearchWhat() == SearchWizardModel.CLUSTER);
    	}
    }
    
    /**
     * 设置在线人数标签
     * 
     * @param online
     */
    public void setOnline(String online) {
        lblOnline.setText(NLS.bind(search_label_online, online));
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
}
