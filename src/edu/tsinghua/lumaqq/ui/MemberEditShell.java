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
package edu.tsinghua.lumaqq.ui;

import static edu.tsinghua.lumaqq.resource.Messages.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.tsinghua.lumaqq.events.BaseQQListener;
import edu.tsinghua.lumaqq.events.FriendSelectionEvent;
import edu.tsinghua.lumaqq.events.IFriendSelectionListener;
import edu.tsinghua.lumaqq.models.Cluster;
import edu.tsinghua.lumaqq.models.ClusterType;
import edu.tsinghua.lumaqq.models.Model;
import edu.tsinghua.lumaqq.models.Organization;
import edu.tsinghua.lumaqq.models.User;
import edu.tsinghua.lumaqq.qq.QQ;
import edu.tsinghua.lumaqq.qq.beans.Member;
import edu.tsinghua.lumaqq.qq.beans.QQOrganization;
import edu.tsinghua.lumaqq.qq.events.QQEvent;
import edu.tsinghua.lumaqq.qq.packets.in.ClusterCommandReplyPacket;
import edu.tsinghua.lumaqq.resource.Colors;
import edu.tsinghua.lumaqq.resource.Resources;
import edu.tsinghua.lumaqq.ui.helper.UITool;
import edu.tsinghua.lumaqq.ui.listener.AroundBorderPaintListener;
import edu.tsinghua.lumaqq.ui.provider.ListContentProvider;
import edu.tsinghua.lumaqq.ui.sorter.UserQQSorter;
import edu.tsinghua.lumaqq.widgets.mac.Ring;
import edu.tsinghua.lumaqq.widgets.qstyle.Slat;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;

/**
 * 临时群和组织的信息修改窗口
 * 
 * @author luma
 */
public class MemberEditShell extends BaseQQListener implements IFriendSelectionListener {
	private class ViewLabelProvider extends LabelProvider implements ITableLabelProvider {
		public Image getColumnImage(Object element, int columnIndex) {
			switch(columnIndex) {
				case 0:
					User u = (User)element;
					return res.getSmallHead(u.headId);
				default:
					return null;
			}
		}

		public String getColumnText(Object element, int columnIndex) {
			User u = (User)element;
			switch(columnIndex) {
				case 0:
					return String.valueOf(u.qq);
				case 1:
					return u.nick;
				case 2:
					if(u.info != null)
						return u.info.gender;
					else
						return "";
				case 3:
					if(u.info != null)
						return String.valueOf(u.info.age);
					else
						return "";
				default:
					return "";
			}
		}		
	}
	
	private Shell shell;
    private Display display;
	private MainShell main;
	private TableViewer viewer;
	private Slat btnOK;
	private Resources res;
	private Text textName;
	private FriendSelectionShell fss;
	private Ring ring;
	
	private List<User> members;
	private String name;
	
	// 父群和父组织，如果父群不为空，那么创建一个顶层组织
	// 如果父群是空，那么创建一个多人对话
	private Cluster parentCluster;
	private Organization parentOrganization;
	
	// 新建组织时，创建的新组织bean
	private QQOrganization newOrg;
	
	// 实际创建的对象或者修改的对象，可能为Cluster或者Organization
	private Model model;
	
	// model的类型
	private int type;
	
	// 期待的返回包序号
	private char expected;
	
	// 用在修改临时群信息时，因为修改一个群信息最多牵涉到3个包
	private char addMemberSequence, removeMemberSequence, modifyInfoSequence;
	
	// 用在修改组织信息时
	private char commitMemberSequence, commitOrganizationSequence;
	
	public static final int TEMP_CLUSTER = 0;
	public static final int ORGANIZATION = 1;	
	
	public MemberEditShell(MainShell m, int t) {
		main = m;
		type = t;
        display = main.getDisplay();
        res = Resources.getInstance();
        shell = new Shell(display, SWT.MIN | SWT.TITLE | SWT.CLOSE);
        shell.setText(member_edit_title);
        shell.setImage(res.getImage(Resources.icoLumaQQ));
        shell.setBackground(Colors.DIALOG_BACKGROUND);
        shell.setSize(420, 330);
        shell.addShellListener(new ShellAdapter() {
        	@Override
        	public void shellActivated(ShellEvent e) {
        		onShellActivated(e);
        	}
        	@Override
        	public void shellClosed(ShellEvent e) {
        		onShellClosed(e);
        	}
        });
        
        addMemberSequence = removeMemberSequence = modifyInfoSequence = 0;
        commitMemberSequence = commitOrganizationSequence = 0;
		fss = new FriendSelectionShell(shell, false);
		fss.addFriendSelectionListener(this);
        expected = 0;
        name = "";
        members = new ArrayList<User>();
        if(type == TEMP_CLUSTER)
        	members.add(main.getMyModel());
        
        initLayout();
        validate();
	}

	/**
	 * @param e
	 */
	protected void onShellClosed(ShellEvent e) {
		main.getClient().removeQQListener(this);
		if(fss != null && !fss.isDisposed())
			fss.setVisible(false);
		if(model != null)
			main.getShellRegistry().deregisterMemberEditShell(model);
	}

	/**
	 * @param e
	 */
	protected void onShellActivated(ShellEvent e) {
		if(fss == null || fss.isDisposed()) {
			fss = new FriendSelectionShell(shell, false);
			fss.addFriendSelectionListener(this);
		}
		fss.setVisible(true);
	}	
    
    /**
     * 设置焦点
     */
    public void setFocus() {
        shell.setFocus();
    }
    
    /**
     * 设置窗口激活
     */
    public void setActive() {
        shell.setActive();
    }

	/**
	 * 初始化窗口布局
	 */
	private void initLayout() {
		GridLayout layout = new GridLayout(2, false);
		shell.setLayout(layout);
		shell.addPaintListener(new AroundBorderPaintListener(new Class[] { Text.class, Table.class }, Colors.PAGE_CONTROL_BORDER));
		
		UITool.setDefaultBackground(shell.getBackground());		

		// 名称
		UITool.createLabel(shell, member_edit_name);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.heightHint = 18;
		textName = UITool.createSingleText(shell, gd);
		textName.setBackground(Colors.WHITE);
		textName.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				Text t = (Text)e.getSource();
				name = t.getText().trim();
				validate();
			}
		});
		
		// 成员列表
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 2;
		gd.verticalIndent = 20;
		UITool.createLabel(shell, member_edit_list, gd);
		
		gd = new GridData(GridData.FILL_BOTH);
		gd.horizontalSpan = 2;
		viewer = new TableViewer(shell, SWT.FULL_SELECTION | SWT.H_SCROLL | SWT.V_SCROLL | SWT.SINGLE);
		viewer.setContentProvider(new ListContentProvider<User>(members));
		viewer.setLabelProvider(new ViewLabelProvider());
		viewer.setSorter(new UserQQSorter());
		Table t = viewer.getTable();
		TableColumn tc = new TableColumn(t, SWT.LEFT);
		tc.setText(member_edit_qq);
		tc.setMoveable(true);
		tc.setWidth(120);
		tc = new TableColumn(t, SWT.CENTER);
		tc.setText(member_edit_nick);
		tc.setMoveable(true);
		tc.setWidth(120);
		tc = new TableColumn(t, SWT.CENTER);
		tc.setText(member_edit_gender);
		tc.setMoveable(true);
		tc.setWidth(80);
		tc = new TableColumn(t, SWT.CENTER);
		tc.setText(member_edit_age);
		tc.setMoveable(true);
		tc.setWidth(80);
		t.setHeaderVisible(true);
		t.setLinesVisible(false);
		t.setLayoutData(gd);
		viewer.setInput(this);
		
		// 按钮区
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 2;
		Composite comp = UITool.createContainer(shell, gd, new GridLayout(3, false));
		// busy ring
		ring = UITool.createRing(comp);
		// 确定按钮
		gd = new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.HORIZONTAL_ALIGN_END);
		gd.grabExcessHorizontalSpace = true;
		gd.widthHint = 80;
		btnOK = UITool.createSlat(comp, button_ok, gd);
		btnOK.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				onOK();
			}
		});
		// 取消按钮
		gd = new GridData();
		gd.widthHint = 80;
		UITool.createSlat(comp, button_cancel, gd).addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				shell.close();
			}
		});
	}
	
    /**
     * 确认按钮事件处理
     */
    protected void onOK() {    	
		btnOK.setEnabled(false);
		ring.rotate();
		if(type == TEMP_CLUSTER) {					
			if(model == null)
				createTempCluster();
			else
				modifyTempCluster();
		} else {
			if(model == null)
				createOrganization();
			else
				modifyOrganization();
		}
	}

	/**
	 * 修改组织
	 */
	private void modifyOrganization() {
		// 修改名称
		Organization org = (Organization)model;
		if(!name.equals(org.name)) {
			List<QQOrganization> temp = new ArrayList<QQOrganization>();
			for(Organization o : parentCluster.organizations.values()) {
				QQOrganization qqOrg = new QQOrganization();
				qqOrg.id = o.id;
				qqOrg.path = o.path;
				qqOrg.name = o.name;
				if(o == org) 
					qqOrg.name = name;
				temp.add(qqOrg);
			}
			commitOrganizationSequence = main.getClient().cluster_CommitOrganization(parentCluster.clusterId, temp);
		}
		
		// 得到旧成员
		Map<Integer, User> oldMember = new HashMap<Integer, User>();
		for(User u : parentCluster.members.values()) {
			if(u.organizationId == org.id)
				oldMember.put(u.qq, u);
		}
		// 添加新成员
		List<Member> toBeCommited = new ArrayList<Member>();
		for(User u : members) {
			if(oldMember.remove(u.qq) == null) {
				Member m = new Member();
				m.qq = u.qq;
				m.organization = org.id;
				toBeCommited.add(m);
			}
		}
		// 添加删除的成员
		for(User u : oldMember.values()) {
			Member m = new Member();
			m.qq = u.qq;
			m.organization = 0;
			toBeCommited.add(m);
		}
		// 修改
		if(!toBeCommited.isEmpty()) {
			commitMemberSequence = main.getClient().cluster_CommitMemberOrganization(parentCluster.clusterId, toBeCommited);
		}
	}

	/**
	 * 创建组织
	 */
	private void createOrganization() {
		// 创建组织
		List<QQOrganization> temp = new ArrayList<QQOrganization>();
		for(Organization o : parentCluster.organizations.values()) {
			QQOrganization org = new QQOrganization();
			org.id = o.id;
			org.path = o.path;
			org.name = o.name;
			temp.add(org);
		}
		newOrg = buildNewOrganization();
		temp.add(newOrg);
		commitOrganizationSequence = main.getClient().cluster_CommitOrganization(parentCluster.clusterId, temp);
		
		// 修改成员结构		
		if(!members.isEmpty()) {
			List<Member> mList = new ArrayList<Member>();
			for(User u : members) {
				Member m = new Member();
				m.qq = u.qq;
				m.organization = newOrg.id;
				mList.add(m);
			}
			commitMemberSequence = main.getClient().cluster_CommitMemberOrganization(parentCluster.clusterId, mList);
		}
	}
	
	/**
	 * 生成正确的QQOrganization对象
	 * 
	 * @return
	 */
	private QQOrganization buildNewOrganization() {
		if(parentOrganization == null) {		
			QQOrganization org = new QQOrganization();
			org.id = parentCluster.organizations.size() + 1;
			org.path = org.id << 24;
			org.name = name;
			return org;
		} else {
			int index = 1;
			for(Organization o : parentCluster.organizations.values()) {
				if(o.isChildOf(parentOrganization))
					index++;
			}
			int shift = 18 - parentOrganization.getLevel() * 6;
			QQOrganization org = new QQOrganization();
			org.id = parentCluster.organizations.size() + 1;
			org.path = parentOrganization.path | (index << shift);
			org.name = name;
			return org;
		}
	}

	/**
	 * 修改临时群
	 */
    private void modifyTempCluster() {
    	Cluster c = (Cluster)model;
    	// 修改名称
    	if(!name.equals(c.name)) {
    		modifyInfoSequence = main.getClient().cluster_ModifyTempInfo(c.clusterType.toQQConstant(),
    				c.clusterId, 
    				c.parentClusterId,
    				name);
    	}
    	// 添加成员    	
    	List<Integer> qqList = new ArrayList<Integer>();
    	Map<Integer, User> temp = new HashMap<Integer, User>();    	
    	temp.putAll(c.members);
    	for(User u : members) {
    		if(temp.remove(u.qq) == null)
    			qqList.add(u.qq);
    	}
    	if(qqList.size() > 0) {
    		addMemberSequence = main.getClient().cluster_ModifyTempMember(
    				c.clusterType.toQQConstant(), 
    				c.clusterId,
    				c.parentClusterId,
    				QQ.QQ_CLUSTER_SUB_CMD_ADD_MEMBER,
    				qqList);    		
    	}
    	// 删除成员
    	if(temp.size() > 0) {
    		qqList.clear();
    		qqList.addAll(temp.keySet());
    		removeMemberSequence = main.getClient().cluster_ModifyTempMember(
    				c.clusterType.toQQConstant(), 
    				c.clusterId,
    				c.parentClusterId,
    				QQ.QQ_CLUSTER_SUB_CMD_REMOVE_MEMBER,
    				qqList);
    	}
    	
    	if(isTempClusterModifySuccess())
    		shell.close();
	}

	/**
     * 创建临时群
     */
	private void createTempCluster() {
		List<Integer> temp = new ArrayList<Integer>();
		for(User u : members) 
			temp.add(u.qq);
		if(parentCluster == null || parentCluster.clusterType == ClusterType.DIALOG_CONTAINER) {
			expected = main.getClient().cluster_CreateTemporary(name,
					QQ.QQ_CLUSTER_TYPE_DIALOG,
					0,
					temp);
		} else {
			expected = main.getClient().cluster_CreateTemporary(
					name, 
					QQ.QQ_CLUSTER_TYPE_SUBJECT,
					parentCluster.clusterId,
					temp);
		}		
	}

	/**
	 * 打开shell
	 */
	public void open()	{
		// 设置窗口位置
	    Rectangle rect = display.getClientArea();
	    Point size = shell.getSize();
	    shell.setLocation((rect.width - size.x) / 2, (rect.height - size.y) / 2);
		// 打开shell
		shell.layout();
		shell.open();
		// add listener
		main.getClient().addQQListener(this);
	}

	/**
	 * 校验参数情况
	 */
	protected void validate() {
		if(name.equals("") || type == TEMP_CLUSTER && members.size() <= 1)
			btnOK.setEnabled(false);
		else
			btnOK.setEnabled(true);
	}

	/* (non-Javadoc)
	 * @see edu.tsinghua.lumaqq.events.IFriendSelectionListener#friendSelected(edu.tsinghua.lumaqq.events.FriendSelectionEvent)
	 */
	public void friendSelected(FriendSelectionEvent e) {
		for(Model m : e.getModels()) {
			User u = (User)m;
			if(type == TEMP_CLUSTER && u.qq == main.getMyModel().qq)
				continue;
			if(!members.contains(m))
				members.add(u);
		}
		viewer.refresh();
		validate();
	}

	/* (non-Javadoc)
	 * @see edu.tsinghua.lumaqq.events.IFriendSelectionListener#friendDeselected(edu.tsinghua.lumaqq.events.FriendSelectionEvent)
	 */
	public void friendDeselected(FriendSelectionEvent e) {
		for(Model m : e.getModels()) {
			User u = (User)m;
			if(type == TEMP_CLUSTER && u.qq == main.getMyModel().qq)
				continue;
			members.remove(u);
		}
		viewer.refresh();
		validate();
	}

	/**
	 * @return Returns the parentCluster.
	 */
	public Cluster getParentCluster() {
		return parentCluster;
	}

	/**
	 * @param parentCluster The parentCluster to set.
	 */
	public void setParentCluster(Cluster parentCluster) {
		this.parentCluster = parentCluster;
	}

	/**
	 * @return Returns the parentOrganization.
	 */
	public Organization getParentOrganization() {
		return parentOrganization;
	}

	/**
	 * @param parentOrganization The parentOrganization to set.
	 */
	public void setParentOrganization(Organization parentOrganization) {
		this.parentOrganization = parentOrganization;
	}

	/**
	 * @return Returns the members.
	 */
	public List<User> getMembers() {
		return members;
	}

	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param cluster The cluster to set.
	 */
	public void setCluster(Cluster cluster) {
		this.model = cluster;
		if(cluster != null) {
			textName.setText(cluster.name);
			members.clear();
			members.addAll(cluster.members.values());
			viewer.refresh();
			shell.setText(member_edit_title);
			if(parentCluster == null || parentCluster.clusterType == ClusterType.DIALOG_CONTAINER)
				fss.setModel(main.getBlindHelper().getFriendGroupList());
			else
				fss.setClusterModel(parentCluster);
			
			for(User u : members)
				fss.select(u);
		} else if(parentCluster == null || parentCluster.clusterType == ClusterType.DIALOG_CONTAINER) {
			shell.setText(member_edit_title_add);
			fss.setModel(main.getBlindHelper().getFriendGroupList());
		} else {
			shell.setText(member_edit_title_add);
			fss.setClusterModel(parentCluster);
		}
		validate();
	}

	/**
	 * @return
	 * 		新建的model或者被修改的model
	 */
	public Model getModel() {
		return model;
	}
	
	/**
	 * @param organization The organization to set.
	 */
	public void setOrganization(Organization organization) {
		this.model = organization;
		fss.setClusterModel(parentCluster);
		if(organization != null) {
			textName.setText(organization.name);
			members.clear();
			for(User u : organization.cluster.members.values()) {
				if(u.organizationId == organization.id)
					members.add(u);
			}
			shell.setText(member_edit_title);
			viewer.refresh();
			
			for(User u : members)
				fss.select(u);
		} else
			shell.setText(member_edit_title_add);
		
		validate();
	}
	
	/**
	 * @return
	 * 		true表示临时群信息修改完成
	 */
	private boolean isTempClusterModifySuccess() {
		return modifyInfoSequence == 0 &&
			addMemberSequence == 0 &&
			removeMemberSequence == 0;
	}
	
	/**
	 * @return
	 * 		true表示临时群信息修改完成
	 */
	private boolean isOrganizationModifySuccess() {
		return commitMemberSequence == 0 && commitOrganizationSequence == 0;
	}
	
	/**
	 * 临时群信息修改成功时调用
	 */
	private void whenTempClusterModifySuccess() {
		// 保存修改后的信息
		Cluster c = (Cluster)model;
		c.name = name;
		c.removeAllMembers();
		for(User u : members) 
			c.addMember(u);
		// 刷新
		ring.stop();
		main.getBlindHelper().refreshGroup(c.group);
		shell.close();
	}
	
	/**
	 * 组织信息修改成功时调用
	 */
	private void whenOrganizationModifySuccess() {
		if(model == null) {
			Organization org = new Organization();
			org.id = newOrg.id;
			org.path = newOrg.path;
			org.name = newOrg.name;
			parentCluster.addOrganization(org);
			for(User u : members) {
				User m = parentCluster.getMember(u.qq);
				if(m != null)
					m.organizationId = org.id;
			}
		} else {
			Organization org = (Organization)model;
			org.name = name;
			// 得到旧成员
			Map<Integer, User> oldMember = new HashMap<Integer, User>();
			for(User u : parentCluster.members.values()) {
				if(u.organizationId == org.id)
					oldMember.put(u.qq, u);
			}
			// 添加新成员
			for(User u : members) {
				if(oldMember.remove(u.qq) == null)
					u.organizationId = org.id;
			}
			// 添加删除的成员
			for(User u : oldMember.values())
				u.organizationId = 0;
		}
		
		// refresh ui
		display.syncExec(new Runnable() {
			public void run() {
				ring.stop();
				main.getBlindHelper().refreshGroup(parentCluster.group);
				shell.close();
			}
		});
	}
	
	@Override
	protected void OnQQEvent(QQEvent e) {
		switch(e.type) {
			case QQEvent.CLUSTER_COMMIT_ORG_OK:
				processCommitOrganizationSuccess(e);
				break;
			case QQEvent.CLUSTER_COMMIT_MEMBER_ORG_OK:
				processCommitMemberOrganizationSuccess(e);
				break;
			case QQEvent.CLUSTER_CREATE_TEMP_OK:
				processCreateTempClusterSuccess(e);
				break;
			case QQEvent.CLUSTER_MODIFY_TEMP_INFO_OK:
				processModifyTempClusterInfoSuccess(e);
				break;
			case QQEvent.CLUSTER_MODIFY_TEMP_INFO_FAIL:
			case QQEvent.CLUSTER_COMMIT_MEMBER_ORG_FAIL:
			case QQEvent.CLUSTER_COMMIT_ORG_FAIL:
			case QQEvent.CLUSTER_CREATE_TEMP_FAIL:
			case QQEvent.CLUSTER_MODIFY_TEMP_MEMBER_FAIL:
				processClusterCommandFail(e);
				break;
			case QQEvent.CLUSTER_MODIFY_TEMP_MEMBER_OK:
				processModifyTempClusterMemberSuccess(e);
				break;	
		}
	}
	
	/**
	 * 处理提交成员结构成功事件
	 * 
	 * @param e
	 */
	private void processCommitMemberOrganizationSuccess(QQEvent e) {
		ClusterCommandReplyPacket packet = (ClusterCommandReplyPacket)e.getSource();
		if(packet.getSequence() == commitMemberSequence)
			commitMemberSequence = 0;
		
		if(isOrganizationModifySuccess())
			whenOrganizationModifySuccess();
	}

	/**
	 * 处理提交组织结构成功事件
	 * 
	 * @param e
	 */
	private void processCommitOrganizationSuccess(QQEvent e) {
		ClusterCommandReplyPacket packet = (ClusterCommandReplyPacket)e.getSource();
		if(packet.getSequence() == commitOrganizationSequence)
			commitOrganizationSequence = 0;
		
		if(isOrganizationModifySuccess())
			whenOrganizationModifySuccess();
	}

	/**
	 * 处理修改临时群成员成功事件
	 * 
	 * @param e
	 */
	private void processModifyTempClusterMemberSuccess(QQEvent e) {
		ClusterCommandReplyPacket packet = (ClusterCommandReplyPacket)e.getSource();
		if(packet.getSequence() == addMemberSequence)
			addMemberSequence = 0;
		if(packet.getSequence() == removeMemberSequence)
			removeMemberSequence = 0;
		
		if(isTempClusterModifySuccess())
			whenTempClusterModifySuccess();
	}

	/**
	 * 处理修改临时群信息成功事件
	 * 
	 * @param e
	 */
	private void processModifyTempClusterInfoSuccess(QQEvent e) {
		ClusterCommandReplyPacket packet = (ClusterCommandReplyPacket)e.getSource();
		if(packet.getSequence() == modifyInfoSequence)
			modifyInfoSequence = 0;
		
		if(isTempClusterModifySuccess())
			whenTempClusterModifySuccess();
	}

	/**
	 * 处理群命令错误事件
	 * 
	 * @param e
	 */
	private void processClusterCommandFail(QQEvent e) {
		ClusterCommandReplyPacket packet = (ClusterCommandReplyPacket)e.getSource();
		if(packet.getSequence() == expected) {
		    showError(packet.errorMessage);
		    shell.close();		
		}
	}

	/**
	 * 处理临时群创建成功事件
	 * 
	 * @param e
	 */
	private void processCreateTempClusterSuccess(QQEvent e) {
		ClusterCommandReplyPacket packet = (ClusterCommandReplyPacket)e.getSource();
		if(expected == packet.getSequence()) {
			ring.stop();
			showMessage(NLS.bind(message_box_temp_cluster_created, String.valueOf(packet.clusterId)));
			shell.close();
		}
	}
	
	/**
	 * 显示提示框
	 * 
	 * @param msg
	 */
	private void showMessage(String msg) {
		MessageDialog.openInformation(shell, message_box_common_info_title, msg);
	}
	
	/**
	 * 显示警告框
	 * 
	 * @param msg
	 */
	private void showError(String msg) {
		MessageDialog.openError(shell, message_box_common_fail_title, msg);
	}
}
