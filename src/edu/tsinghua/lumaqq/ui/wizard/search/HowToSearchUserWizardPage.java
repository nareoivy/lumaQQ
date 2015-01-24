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
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import edu.tsinghua.lumaqq.ui.helper.UITool;

/**
 * 用户搜索方式选择页
 * 
 * @author luma
 */
public class HowToSearchUserWizardPage extends WizardPage {
    private Button rdoOnline, rdoAccurate, rdoAdvanced;
    private Label lblHint;
    private SearchWizard wizard;
    private SearchWizardModel model;
    
    /**
     * @param pageName
     */
    public HowToSearchUserWizardPage(String pageName) {
        super(pageName);
        setTitle(how_to_search_user_title);
        setMessage(how_to_search_user_message);
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
        
        // 提示信息
        lblHint = UITool.createLabel(control, how_to_search_online_hint, new GridData(GridData.FILL_HORIZONTAL));        
        
        // 看谁在线上
        rdoOnline = UITool.createRadio(control, how_to_search_online);
        rdoOnline.setSelection(true);
        rdoOnline.addSelectionListener(new SelectionAdapter() {
            @Override
			public void widgetSelected(SelectionEvent e) {
            	model.setUserSearchMode(SearchWizardModel.ONLINE);
                refreshHint();                
            }
        });
        // 精确查找
        rdoAccurate = UITool.createRadio(control, how_to_search_accurate);
        rdoAccurate.addSelectionListener(new SelectionAdapter() {
            @Override
			public void widgetSelected(SelectionEvent e) {
            	model.setUserSearchMode(SearchWizardModel.ACCURATE);
                refreshHint();
            }
        });
        // 高级查找
        rdoAdvanced = UITool.createRadio(control, how_to_search_advanced);
        rdoAdvanced.addSelectionListener(new SelectionAdapter() {
            @Override
			public void widgetSelected(SelectionEvent e) {
            	model.setUserSearchMode(SearchWizardModel.ADVANCED);
                refreshHint();
            }
        });
        
        setControl(control);
    }
    
    /**
     * 刷新提示
     */
    private void refreshHint() {
        if(rdoOnline.getSelection())
            lblHint.setText(how_to_search_online_hint);
        else if(rdoAccurate.getSelection())
            lblHint.setText(how_to_search_accurate_hint);
        else if(rdoAdvanced.getSelection())
            lblHint.setText(how_to_search_advanced_hint);
    }

    /* (non-Javadoc)
     * @see org.eclipse.jface.wizard.WizardPage#isPageComplete()
     */
    @Override
	public boolean isPageComplete() {
        return true;
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
     * @see org.eclipse.jface.dialogs.IDialogPage#setVisible(boolean)
     */
    @Override
	public void setVisible(boolean visible) {
        super.setVisible(visible);
        if(visible)
        	model.setEnterResultPage(true);
    }
}
