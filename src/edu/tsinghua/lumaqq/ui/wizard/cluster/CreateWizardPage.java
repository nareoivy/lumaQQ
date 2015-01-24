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
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import edu.tsinghua.lumaqq.ui.helper.UITool;

/**
 * 创建群
 * 
 * @author luma
 */
public class CreateWizardPage extends WizardPage {
    protected static final int INIT = -1;
    protected static final int CREATING = 0;
    protected static final int CREATED = 1;
    protected static final int FAILED = 2;
    protected static final int TIMEOUT = 3;
    
    private int status;
    private ClusterWizard wizard;
    
    private CLabel lblHint;
    
    /**
     * @param pageName
     */
    protected CreateWizardPage(String pageName) {
        super(pageName);
        setTitle(create_title);
        setMessage(create_message);
    }

    /* (non-Javadoc)
     * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
     */
    public void createControl(Composite parent) {
        Composite control = new Composite(parent, SWT.NONE);
        control.setLayoutData(new GridData(GridData.FILL_BOTH));
        GridLayout layout = new GridLayout();
        layout.marginHeight = layout.marginWidth = 15;
        layout.verticalSpacing = 8;
        control.setLayout(layout);
        
        UITool.setDefaultBackground(control.getBackground());
        
        lblHint = new CLabel(control, SWT.CENTER);
        lblHint.setLayoutData(new GridData(GridData.FILL_BOTH));
        
        setControl(control);
    }
    
    public void setHint(String hint) {
        lblHint.setText(hint);
    }
    
    /**
     * @return Returns the status.
     */
    public int getStatus() {
        return status;
    }
    
    /* (non-Javadoc)
     * @see org.eclipse.jface.wizard.WizardPage#setWizard(org.eclipse.jface.wizard.IWizard)
     */
    @Override
	public void setWizard(IWizard newWizard) {
        super.setWizard(newWizard);
        this.wizard = (ClusterWizard)newWizard;
    }
    
    /* (non-Javadoc)
     * @see org.eclipse.jface.dialogs.IDialogPage#setVisible(boolean)
     */
    @Override
	public void setVisible(boolean visible) {
        super.setVisible(visible);
        if(visible)
            wizard.doCreate();
    }
    
    /**
     * @param status The status to set.
     */
    public void setStatus(int status) {
        this.status = status;
        switch(status) {
            case INIT:
                setHint("");
                break;
            case CREATING:
                setHint(hint_creating);
                break;
            case CREATED:
                setHint(NLS.bind(hint_created, String.valueOf(wizard.getClusterId())));
                break;
            case FAILED:
                setHint(NLS.bind(hint_failed, wizard.getErrorMessage()));
                break;
            case TIMEOUT:
                setHint(hint_timeout);
                break;
        }
        setPageComplete(validatePage());
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
     */
    private boolean validatePage() {
        return status == CREATED || status == FAILED;
    }
}
