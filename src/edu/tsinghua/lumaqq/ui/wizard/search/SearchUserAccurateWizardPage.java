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
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

import edu.tsinghua.lumaqq.resource.Colors;
import edu.tsinghua.lumaqq.ui.helper.UITool;
import edu.tsinghua.lumaqq.ui.listener.AroundBorderPaintListener;

/**
 * 精确查找用户页
 * 
 * @author luma
 */
public class SearchUserAccurateWizardPage extends WizardPage {
    private Text textQQ, textNick, textEmail;
    private SearchWizard wizard;
    private SearchWizardModel model;
    
    /**
     * @param pageName
     */
    public SearchUserAccurateWizardPage(String pageName) {
        super(pageName);
        setTitle(search_user_accurate_title);
        setMessage(search_user_accurate_message);
    }

    /* (non-Javadoc)
     * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
     */
    public void createControl(Composite parent) {
        Composite control = new Composite(parent, SWT.NONE);
        control.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        GridLayout layout = new GridLayout(2, false);
        layout.marginHeight = layout.marginWidth = 15;
        layout.verticalSpacing = 8;
        control.setLayout(layout);        
        control.addPaintListener(new AroundBorderPaintListener(new Class[] { Text.class }));

        UITool.setDefaultBackground(control.getBackground());
        ModifyListener ml = new ModifyListener() {
            public void modifyText(ModifyEvent e) {
                setPageComplete(validatePage());
            }
        };
        
        // qq号
        UITool.createLabel(control, search_user_accurate_qq);
        GridData gd = new GridData();
        gd.heightHint = 18;
        gd.widthHint = 150;        
        textQQ = UITool.createSingleText(control, gd);
        textQQ.setBackground(Colors.WHITE);
        textQQ.addModifyListener(ml);
        // 昵称
        UITool.createLabel(control, search_user_accurate_nick);
        gd = new GridData();
        gd.heightHint = 18;
        gd.widthHint = 150;        
        textNick = UITool.createSingleText(control, gd);
        textNick.setBackground(Colors.WHITE);
        textNick.addModifyListener(ml);
        // Email
        UITool.createLabel(control, search_user_accurate_email);
        gd = new GridData();
        gd.heightHint = 18;
        gd.widthHint = 150;        
        textEmail = UITool.createSingleText(control, gd);
        textEmail.setBackground(Colors.WHITE);
        textEmail.addModifyListener(ml);
        
        setControl(control);
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
     * 		true如果输入有效
     */
    private boolean validatePage() {
        if(textQQ.getText().trim().equals("") && 
                textNick.getText().trim().equals("") &&
                textEmail.getText().trim().equals("")) {
            setErrorMessage(error_at_least_one_field);
            return false;
        } else {
        	model.setQQ(textQQ.getText().trim());
        	model.setNick(textNick.getText().trim());
        	model.setEmail(textEmail.getText().trim());
        }
        
        setErrorMessage(null);
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
    
    /**
     * 精确查找
     * 
     * @param page
     * @return
     * 		包序号
     */
    public char doSearch(int page) {
        return wizard.getMainShell().getClient().user_Search(
                page, 
                model.getQQ(), 
                model.getNick(),
                model.getEmail());
    }
}
