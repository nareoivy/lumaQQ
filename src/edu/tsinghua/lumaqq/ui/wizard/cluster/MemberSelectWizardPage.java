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

import static edu.tsinghua.lumaqq.resource.Messages.member_select_from;
import static edu.tsinghua.lumaqq.resource.Messages.member_select_message;
import static edu.tsinghua.lumaqq.resource.Messages.member_select_nick;
import static edu.tsinghua.lumaqq.resource.Messages.member_select_qq;
import static edu.tsinghua.lumaqq.resource.Messages.member_select_title;

import java.util.List;

import edu.tsinghua.lumaqq.events.FriendSelectionEvent;
import edu.tsinghua.lumaqq.events.IFriendSelectionListener;
import edu.tsinghua.lumaqq.models.Model;
import edu.tsinghua.lumaqq.models.User;
import edu.tsinghua.lumaqq.ui.FriendSelectionShell;
import edu.tsinghua.lumaqq.ui.provider.ListContentProvider;
import edu.tsinghua.lumaqq.ui.sorter.UserQQSorter;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

/**
 * 成员选择类型
 * 
 * @author luma
 */
public class MemberSelectWizardPage extends WizardPage implements IFriendSelectionListener {
    private TableViewer viewer;
    private FriendSelectionShell fss;
    private ClusterWizard wizard;
    private ClusterWizardModel model;
    
    /**
     * @param pageName
     */
    protected MemberSelectWizardPage(String pageName) {
        super(pageName);
        setTitle(member_select_title);
        setMessage(member_select_message);
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
        
        model.addMember(wizard.getMainShell().getMyModel());
        viewer = new TableViewer(control, SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER | SWT.H_SCROLL);
        viewer.setContentProvider(new ListContentProvider<User>(model.getMembers()));
        viewer.setLabelProvider(new FriendModelLabelProvider());
        viewer.setSorter(new UserQQSorter());
        
        Table t = viewer.getTable();
        TableColumn tc = new TableColumn(t, SWT.LEFT);
        tc.setText(member_select_qq);
        tc.setWidth(90);
        tc = new TableColumn(t, SWT.CENTER);
        tc.setText(member_select_nick);
        tc.setWidth(150);
        tc = new TableColumn(t, SWT.CENTER);
        tc.setText(member_select_from);
        tc.setWidth(100);
        t.setHeaderVisible(true);
        t.setLinesVisible(false);
        
        t.setLayoutData(new GridData(GridData.FILL_BOTH));
        
        viewer.setInput(this);        
        
        setControl(control);
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
        if(visible) {
	        fss = new FriendSelectionShell(getShell(), false);
	        fss.addFriendSelectionListener(this);   
	        model.removeAllMember();
	        model.addMember(wizard.getMainShell().getMyModel());
	        viewer.refresh();
	        switch(model.getClusterType()) {
	            case ClusterWizardModel.SUBJECT:
	                fss.setClusterModel(model.getParentCluster());
	            	break;
	            default:
		            fss.setModel(wizard.getMainShell().getBlindHelper().getFriendGroupList());
	            	break;
	        }
        } 
        
        fss.setVisible(visible);            

        super.setVisible(visible);
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.events.FriendSelectionListener#friendSelected(edu.tsinghua.lumaqq.events.FriendSelectionEvent)
     */
    public void friendSelected(FriendSelectionEvent e) {
        int myQQ = wizard.getMainShell().getMyModel().qq;
        List<Model> selected = e.getModels();
		for(Model node : selected) {
			User f = (User)node;
            if(f.qq != myQQ) 
            	model.addMember(f);
        }
        viewer.refresh();
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.events.FriendSelectionListener#friendDeselected(edu.tsinghua.lumaqq.events.FriendSelectionEvent)
     */
    public void friendDeselected(FriendSelectionEvent e) {
        int myQQ = wizard.getMainShell().getMyModel().qq;
        List<Model> selected = e.getModels();
		for(Model node : selected) {
			User f = (User)node;
            if(f.qq != myQQ) 
            	model.removeMember(f);
        }
        viewer.refresh();
    }
}
