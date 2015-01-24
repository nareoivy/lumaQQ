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
package edu.tsinghua.lumaqq.ui.config.cluster;

import static edu.tsinghua.lumaqq.resource.Messages.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.tsinghua.lumaqq.events.FriendSelectionEvent;
import edu.tsinghua.lumaqq.events.IFriendSelectionListener;
import edu.tsinghua.lumaqq.models.Cluster;
import edu.tsinghua.lumaqq.models.Group;
import edu.tsinghua.lumaqq.models.Model;
import edu.tsinghua.lumaqq.models.User;
import edu.tsinghua.lumaqq.qq.QQ;
import edu.tsinghua.lumaqq.resource.Colors;
import edu.tsinghua.lumaqq.resource.Resources;
import edu.tsinghua.lumaqq.ui.FriendSelectionShell;
import edu.tsinghua.lumaqq.ui.MainShell;
import edu.tsinghua.lumaqq.ui.config.AbstractPage;
import edu.tsinghua.lumaqq.ui.config.user.UserInfoWindow;
import edu.tsinghua.lumaqq.ui.helper.UITool;
import edu.tsinghua.lumaqq.ui.listener.AroundBorderPaintListener;
import edu.tsinghua.lumaqq.ui.provider.ListContentProvider;
import edu.tsinghua.lumaqq.ui.sorter.UserQQSorter;

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

/**
 * 成员列表页
 * 
 * @author luma
 */
public class MemberPage extends AbstractPage implements IFriendSelectionListener {
    private Cluster model;
    private List<User> members;
    private TableViewer viewer;
    private Button btnAdd;
    private MainShell main;
    private FriendSelectionShell fss;
	private Button btnSetAdmin;
	private Button btnUnsetAdmin;
	private Button btnTransferRole;
    
	/**
	 * @param parent
	 */
	public MemberPage(Composite parent, MainShell main, Cluster model, int style) {
		super(parent, style);
		this.model = model;
		this.main = main;
	}
	
	@Override
	public void setModel(Object model) {
		this.model = (Cluster)model;
	}
	
	/**
	 * 创建好友选择窗口
	 */
	private void createFriendSelectShell() {
		fss = new FriendSelectionShell(parentShell, false);
		fss.setModel(main.getBlindHelper().getFriendGroupList());
		// 把当前表中的成员在fss中设置为已选择状态
		for(User f : members)
			fss.select(f);
		fss.setClusterModel(model);
		fss.selectFromRoot(model);
		fss.addFriendSelectionListener(this);
	}
	
	/* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.ui.config.AbstractPage#initialVariable()
     */
	@Override
    protected void initialVariable() {
        members = new ArrayList<User>();
    }

	/* (non-Javadoc)
	 * @see edu.tsinghua.lumaqq.ui.config.AbstractPage#createContent(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Control createContent(Composite parent) {
        Composite content = new Composite(parent, SWT.NONE);
        content.setBackground(Colors.PAGE_BACKGROUND);
        GridLayout layout = new GridLayout(2, false);
        layout.marginHeight = layout.marginWidth = 10;
        content.setLayout(layout);
        content.addPaintListener(new AroundBorderPaintListener(new Class[] { Table.class }, Colors.PAGE_CONTROL_BORDER));
        
        // 设置使用缺省背景色
        UITool.setDefaultBackground(null);
        
        viewer = new TableViewer(content, SWT.V_SCROLL | SWT.SINGLE | SWT.H_SCROLL | SWT.FULL_SELECTION);
        viewer.setContentProvider(new ListContentProvider<User>(members));
        viewer.setLabelProvider(new FriendModelLabelProvider());
        viewer.setUseHashlookup(true);
        viewer.setSorter(new UserQQSorter());
        viewer.addSelectionChangedListener(new ISelectionChangedListener() {
            public void selectionChanged(SelectionChangedEvent event) {
                User f = getSelectedMember();
                if(f == null) {
                	btnAdd.setEnabled(false);
                	if(style == ClusterInfoWindow.EDITABLE_CREATOR) {
                		btnSetAdmin.setEnabled(false);
                		btnUnsetAdmin.setEnabled(false);
                		btnTransferRole.setEnabled(false);                		
                	}
                } else {
                	btnAdd.setEnabled(f.qq != main.getMyModel().qq && !f.isFriend());
                	if(style == ClusterInfoWindow.EDITABLE_CREATOR) {
                		btnSetAdmin.setEnabled(!model.isSuperMember(f.qq));
                		btnUnsetAdmin.setEnabled(f.qq != main.getMyModel().qq && model.isSuperMember(f.qq));
                		btnTransferRole.setEnabled(f.qq != main.getMyModel().qq);                		
                	}
                }
            }
        });
        
        Table t = viewer.getTable();
        TableColumn tc = new TableColumn(t, SWT.CENTER);
        tc.setWidth(20);
        tc = new TableColumn(t, SWT.LEFT);
        tc.setText(cluster_info_members_qq);
        tc.setWidth(80);
        tc = new TableColumn(t, SWT.LEFT);
        tc.setText(cluster_info_members_nick);
        tc.setWidth(90);
        tc = new TableColumn(t, SWT.LEFT);
        tc.setText(cluster_info_members_gender);
        tc.setWidth(40);
        tc = new TableColumn(t, SWT.LEFT);
        tc.setText(cluster_info_members_age);
        tc.setWidth(40);
        t.setHeaderVisible(true);
        t.setLinesVisible(false);
        GridData gd = new GridData(GridData.FILL_BOTH);
        gd.verticalSpan = 5;
        t.setLayoutData(gd);
        t.addSelectionListener(new SelectionAdapter() {
            @Override
			public void widgetDefaultSelected(SelectionEvent e) {
                IStructuredSelection selection = (IStructuredSelection)viewer.getSelection();
                User f = (User)selection.getFirstElement();
                if(f != null) {
                    main.getShellLauncher().openUserInfoWindow(f, UserInfoWindow.READ_ONLY);
					main.getClient().user_GetInfo(f.qq);                    
                }
            }
        });
        viewer.setInput(this);
        
        // 设置成员
        if(style != ClusterInfoWindow.READ_ONLY) {
        	Button btnModify = UITool.createButton(content, cluster_info_members_modify + " >>", new GridData(GridData.HORIZONTAL_ALIGN_FILL));
        	btnModify.addSelectionListener(new SelectionAdapter() {
        		@Override
				public void widgetSelected(SelectionEvent e) {
        			// 判断窗口是否dispose，因为linux下面这个窗口是有关闭按钮的
        			// 而windows下面没有，所以在linux下面这个窗口可能被dispose
        			if(fss == null || fss.isDisposed())
        				createFriendSelectShell();
        			
        			if(fss.isVisible()) {
        				fss.setVisible(false);
        				((Button)e.getSource()).setText(cluster_info_members_modify + " >>");
        			} else {
        				fss.setVisible(true);	
        				((Button)e.getSource()).setText(cluster_info_members_modify + " <<");
        			}
        		}
        	});        	
        }
        // 加为好友
        btnAdd = UITool.createButton(content, cluster_info_members_add, new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.VERTICAL_ALIGN_BEGINNING));
        btnAdd.setEnabled(false);
        btnAdd.addSelectionListener(new SelectionAdapter() {
            @Override
			public void widgetSelected(SelectionEvent e) {
                IStructuredSelection selection = (IStructuredSelection)viewer.getSelection();
                User f = (User)selection.getFirstElement();
				// 找到第一个好友组的model
				Group destModel = main.getBlindHelper().getMyFriendGroup();
				if(destModel != null) {
					// 创建窗口
					main.getShellLauncher().openAddReceiveSystemMessageShell(f);
					main.getBlindHelper().addFriend(f.qq, destModel);													
				}	
            }
        });
        // 设置管理员
        if(style == ClusterInfoWindow.EDITABLE_CREATOR) {
        	gd = new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.VERTICAL_ALIGN_BEGINNING);
        	gd.verticalIndent = 15;
        	btnSetAdmin = UITool.createButton(content, cluster_info_members_set_admin, gd);
        	btnSetAdmin.setEnabled(false);
			btnSetAdmin.addSelectionListener(new SelectionAdapter() {
        		@Override
        		public void widgetSelected(SelectionEvent e) {
        			User f = getSelectedMember();
        			main.getClient().cluster_SetMemberRole(model.clusterId, f.qq, QQ.QQ_CLUSTER_OP_SET_ADMIN);        			
        		}
        	});
        	
        	gd = new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.VERTICAL_ALIGN_BEGINNING);
        	btnUnsetAdmin = UITool.createButton(content, cluster_info_members_unset_admin, gd);
        	btnUnsetAdmin.setEnabled(false);
			btnUnsetAdmin.addSelectionListener(new SelectionAdapter() {
        		@Override
        		public void widgetSelected(SelectionEvent e) {
        			User f = getSelectedMember();
        			main.getClient().cluster_SetMemberRole(model.clusterId, f.qq, QQ.QQ_CLUSTER_OP_UNSET_ADMIN);        			
        		}
        	});
        	
        	gd = new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.VERTICAL_ALIGN_BEGINNING);
        	btnTransferRole = UITool.createButton(content, cluster_info_members_transfer_role, gd);
        	btnTransferRole.setEnabled(false);
			btnTransferRole.addSelectionListener(new SelectionAdapter() {
        		@Override
        		public void widgetSelected(SelectionEvent e) {
        			User f = getSelectedMember();
        			main.getClient().cluster_TransferRole(model.clusterId, f.qq);
        		}
        	});
        }
        
        return content;
	}

	/* (non-Javadoc)
	 * @see edu.tsinghua.lumaqq.ui.config.AbstractPage#saveDirtyProperty(int)
	 */
	@Override
	protected void saveDirtyProperty(int propertyId) {
	}

	/* (non-Javadoc)
	 * @see edu.tsinghua.lumaqq.ui.config.AbstractPage#initializeValues()
	 */
	@Override
	protected void initializeValues() {
	    members.clear();
	    members.addAll(model.members.values());
	    viewer.refresh();	    
	}

	/* (non-Javadoc)
	 * @see edu.tsinghua.lumaqq.ui.config.AbstractPage#getImage()
	 */
	@Override
	protected Image getImage() {
        return Resources.getInstance().getImage(Resources.icoViewPersonInfo24);
	}

	/* (non-Javadoc)
	 * @see edu.tsinghua.lumaqq.ui.config.AbstractPage#getTitle()
	 */
	@Override
	protected String getTitle(int page) {
		return cluster_info_page_members;
	}
	
	/**
	 * @return
	 * 		要删除的成员model列表
	 */
	public List<Integer> getMembersToBeRemoved() {
		Map<Integer, User> temp = new HashMap<Integer, User>(model.members);
		for(User f : members)
			temp.remove(f.qq);
	    return new ArrayList<Integer>(temp.keySet());
	}
	
	/**
	 * @return
	 * 		要添加的成员model列表
	 */
	public List<Integer> getMembersToBeAdded() {
		List<Integer> temp = new ArrayList<Integer>();
		for(User f : members) {
			if(!model.hasMember(f.qq))
				temp.add(f.qq);
		}
	    return temp;
	}
	
	/**
	 * 发送删除群成员请求包
	 * 
	 * @return
	 * 		包序号
	 */
	public char doRemoveMember() {
	    List<Integer> remove = getMembersToBeRemoved();
	    if(remove.isEmpty())
	        return 0;
	    
	    if(model.isPermanent())
	        return main.getClient().cluster_ModifyMember(model.clusterId, 
	                QQ.QQ_CLUSTER_SUB_CMD_REMOVE_MEMBER,
	                remove);
	    else
	        return main.getClient().cluster_ModifyTempMember(model.clusterType.toQQConstant(),
	                model.clusterId,
	                model.parentClusterId,
	                QQ.QQ_CLUSTER_SUB_CMD_REMOVE_MEMBER,
	                remove);
	}
	
	/**
	 * 发送添加群成员请求包
	 * 
	 * @return	
	 * 		包序号
	 */
	public char doAddMember() {
	    List<Integer> add = getMembersToBeAdded();
	    if(add.isEmpty())
	        return 0;
	    
	    if(model.isPermanent())
	        return main.getClient().cluster_ModifyMember(model.clusterId, 
	                QQ.QQ_CLUSTER_SUB_CMD_ADD_MEMBER,
	                add);
	    else
	        return main.getClient().cluster_ModifyTempMember(model.clusterType.toQQConstant(),
	                model.clusterId,
	                model.parentClusterId,
	                QQ.QQ_CLUSTER_SUB_CMD_ADD_MEMBER,
	                add);
	}

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.events.FriendSelectionListener#friendSelected(edu.tsinghua.lumaqq.events.FriendSelectionEvent)
     */
    public void friendSelected(FriendSelectionEvent e) {
		for(Model node : e.getModels())
			members.add((User)node);
        viewer.refresh();
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.events.FriendSelectionListener#friendDeselected(edu.tsinghua.lumaqq.events.FriendSelectionEvent)
     */
    public void friendDeselected(FriendSelectionEvent e) {
		for(Model node : e.getModels())
			members.remove(node);
        viewer.refresh();
    }

	/**
	 * @return
	 */
	private User getSelectedMember() {
		IStructuredSelection selection = (IStructuredSelection)viewer.getSelection();
		User f = (User)selection.getFirstElement();
		return f;
	}
}
