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
package edu.tsinghua.lumaqq.ui.helper;

import static edu.tsinghua.lumaqq.resource.Messages.cluster_im_menu_member_add;
import static edu.tsinghua.lumaqq.resource.Messages.hint_delete_friend;
import static edu.tsinghua.lumaqq.resource.Messages.hint_please_clear_friend;
import static edu.tsinghua.lumaqq.resource.Messages.menu_cluster_exit;
import static edu.tsinghua.lumaqq.resource.Messages.menu_cluster_message_export;
import static edu.tsinghua.lumaqq.resource.Messages.menu_cluster_message_manage;
import static edu.tsinghua.lumaqq.resource.Messages.menu_cluster_modifyinfo;
import static edu.tsinghua.lumaqq.resource.Messages.menu_cluster_send;
import static edu.tsinghua.lumaqq.resource.Messages.menu_cluster_viewinfo;
import static edu.tsinghua.lumaqq.resource.Messages.menu_dialog_new;
import static edu.tsinghua.lumaqq.resource.Messages.menu_dialog_refresh;
import static edu.tsinghua.lumaqq.resource.Messages.menu_friend_addgroup;
import static edu.tsinghua.lumaqq.resource.Messages.menu_friend_delfriend;
import static edu.tsinghua.lumaqq.resource.Messages.menu_friend_message_export;
import static edu.tsinghua.lumaqq.resource.Messages.menu_friend_message_manage;
import static edu.tsinghua.lumaqq.resource.Messages.menu_friend_pin;
import static edu.tsinghua.lumaqq.resource.Messages.menu_friend_rename;
import static edu.tsinghua.lumaqq.resource.Messages.menu_friend_sendreceive;
import static edu.tsinghua.lumaqq.resource.Messages.menu_friend_sendsms;
import static edu.tsinghua.lumaqq.resource.Messages.menu_friend_showall;
import static edu.tsinghua.lumaqq.resource.Messages.menu_friend_showlarge;
import static edu.tsinghua.lumaqq.resource.Messages.menu_friend_showonlineonly;
import static edu.tsinghua.lumaqq.resource.Messages.menu_friend_showsmall;
import static edu.tsinghua.lumaqq.resource.Messages.menu_friend_temp_session;
import static edu.tsinghua.lumaqq.resource.Messages.menu_friend_unpin;
import static edu.tsinghua.lumaqq.resource.Messages.menu_friend_viewinfo;
import static edu.tsinghua.lumaqq.resource.Messages.menu_group_addbadguy;
import static edu.tsinghua.lumaqq.resource.Messages.menu_group_addfriend;
import static edu.tsinghua.lumaqq.resource.Messages.menu_group_alumni_create;
import static edu.tsinghua.lumaqq.resource.Messages.menu_group_alumni_search;
import static edu.tsinghua.lumaqq.resource.Messages.menu_group_cluster;
import static edu.tsinghua.lumaqq.resource.Messages.menu_group_cluster_create;
import static edu.tsinghua.lumaqq.resource.Messages.menu_group_cluster_search;
import static edu.tsinghua.lumaqq.resource.Messages.menu_group_color;
import static edu.tsinghua.lumaqq.resource.Messages.menu_group_community;
import static edu.tsinghua.lumaqq.resource.Messages.menu_group_delete;
import static edu.tsinghua.lumaqq.resource.Messages.menu_group_disable_user_tip;
import static edu.tsinghua.lumaqq.resource.Messages.menu_group_displayname;
import static edu.tsinghua.lumaqq.resource.Messages.menu_group_displayname_nick;
import static edu.tsinghua.lumaqq.resource.Messages.menu_group_displayname_remark;
import static edu.tsinghua.lumaqq.resource.Messages.menu_group_enable_user_tip;
import static edu.tsinghua.lumaqq.resource.Messages.menu_group_group_color;
import static edu.tsinghua.lumaqq.resource.Messages.menu_group_hideblacklist;
import static edu.tsinghua.lumaqq.resource.Messages.menu_group_rename;
import static edu.tsinghua.lumaqq.resource.Messages.menu_group_showall;
import static edu.tsinghua.lumaqq.resource.Messages.menu_group_showblacklist;
import static edu.tsinghua.lumaqq.resource.Messages.menu_group_showonlineonly;
import static edu.tsinghua.lumaqq.resource.Messages.menu_group_traditional_mode;
import static edu.tsinghua.lumaqq.resource.Messages.menu_group_tree_mode;
import static edu.tsinghua.lumaqq.resource.Messages.menu_organization_add;
import static edu.tsinghua.lumaqq.resource.Messages.menu_organization_discuss;
import static edu.tsinghua.lumaqq.resource.Messages.menu_organization_modify;
import static edu.tsinghua.lumaqq.resource.Messages.menu_organization_refresh;
import static edu.tsinghua.lumaqq.resource.Messages.menu_organization_remove;
import static edu.tsinghua.lumaqq.resource.Messages.menu_status_away_custom;
import static edu.tsinghua.lumaqq.resource.Messages.menu_status_away_null;
import static edu.tsinghua.lumaqq.resource.Messages.menu_subject_new;
import static edu.tsinghua.lumaqq.resource.Messages.menu_subject_refresh_all;
import static edu.tsinghua.lumaqq.resource.Messages.menu_sys_about;
import static edu.tsinghua.lumaqq.resource.Messages.menu_sys_apply;
import static edu.tsinghua.lumaqq.resource.Messages.menu_sys_changeuser;
import static edu.tsinghua.lumaqq.resource.Messages.menu_sys_checkupdate;
import static edu.tsinghua.lumaqq.resource.Messages.menu_sys_debug;
import static edu.tsinghua.lumaqq.resource.Messages.menu_sys_exit;
import static edu.tsinghua.lumaqq.resource.Messages.menu_sys_friend;
import static edu.tsinghua.lumaqq.resource.Messages.menu_sys_friend_download_remark;
import static edu.tsinghua.lumaqq.resource.Messages.menu_sys_friend_export_record;
import static edu.tsinghua.lumaqq.resource.Messages.menu_sys_group;
import static edu.tsinghua.lumaqq.resource.Messages.menu_sys_group_download;
import static edu.tsinghua.lumaqq.resource.Messages.menu_sys_group_upload;
import static edu.tsinghua.lumaqq.resource.Messages.menu_sys_info_window;
import static edu.tsinghua.lumaqq.resource.Messages.menu_sys_lumaqq_homepage;
import static edu.tsinghua.lumaqq.resource.Messages.menu_sys_personinfo;
import static edu.tsinghua.lumaqq.resource.Messages.menu_sys_robot;
import static edu.tsinghua.lumaqq.resource.Messages.menu_sys_robot_off;
import static edu.tsinghua.lumaqq.resource.Messages.menu_sys_robot_on;
import static edu.tsinghua.lumaqq.resource.Messages.menu_sys_searchip;
import static edu.tsinghua.lumaqq.resource.Messages.menu_sys_status;
import static edu.tsinghua.lumaqq.resource.Messages.menu_sys_sysopt;
import static edu.tsinghua.lumaqq.resource.Messages.menu_sys_temp_session;
import static edu.tsinghua.lumaqq.resource.Messages.menu_sys_tool;
import static edu.tsinghua.lumaqq.resource.Messages.menu_sys_weather;
import static edu.tsinghua.lumaqq.resource.Messages.message_box_common_info_title;
import static edu.tsinghua.lumaqq.resource.Messages.message_box_common_question_title;
import static edu.tsinghua.lumaqq.resource.Messages.message_box_common_warning_title;
import static edu.tsinghua.lumaqq.resource.Messages.message_box_empty_organization;
import static edu.tsinghua.lumaqq.resource.Messages.message_box_empty_organization_first;
import static edu.tsinghua.lumaqq.resource.Messages.message_box_exit_cluster;
import static edu.tsinghua.lumaqq.resource.Messages.message_box_exit_my_cluster;
import static edu.tsinghua.lumaqq.resource.Messages.message_box_max_organization_level;
import static edu.tsinghua.lumaqq.resource.Messages.status_away;
import static edu.tsinghua.lumaqq.resource.Messages.status_hidden;
import static edu.tsinghua.lumaqq.resource.Messages.status_offline;
import static edu.tsinghua.lumaqq.resource.Messages.status_online;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.ColorDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

import edu.tsinghua.lumaqq.LumaQQ;
import edu.tsinghua.lumaqq.ecore.global.Robot;
import edu.tsinghua.lumaqq.models.Cluster;
import edu.tsinghua.lumaqq.models.Dummy;
import edu.tsinghua.lumaqq.models.Group;
import edu.tsinghua.lumaqq.models.GroupType;
import edu.tsinghua.lumaqq.models.Model;
import edu.tsinghua.lumaqq.models.Organization;
import edu.tsinghua.lumaqq.models.User;
import edu.tsinghua.lumaqq.qq.QQ;
import edu.tsinghua.lumaqq.qq.QQClient;
import edu.tsinghua.lumaqq.qq.Util;
import edu.tsinghua.lumaqq.qq.beans.Member;
import edu.tsinghua.lumaqq.qq.beans.QQOrganization;
import edu.tsinghua.lumaqq.resource.Resources;
import edu.tsinghua.lumaqq.ui.MainShell;
import edu.tsinghua.lumaqq.ui.MemberEditShell;
import edu.tsinghua.lumaqq.ui.SMSWindow;
import edu.tsinghua.lumaqq.ui.config.sys.SystemOptionWindow;
import edu.tsinghua.lumaqq.ui.config.user.UserInfoWindow;
import edu.tsinghua.lumaqq.ui.debug.Debugger;
import edu.tsinghua.lumaqq.ui.dialogs.AboutDialog;
import edu.tsinghua.lumaqq.ui.jobs.DialogJobExecutor;
import edu.tsinghua.lumaqq.ui.jobs.DownloadFriendRemarkJob;
import edu.tsinghua.lumaqq.ui.jobs.DownloadGroupJob;
import edu.tsinghua.lumaqq.ui.jobs.IExecutor;
import edu.tsinghua.lumaqq.ui.jobs.UploadGroupJob;
import edu.tsinghua.lumaqq.ui.wizard.WizardWindow;
import edu.tsinghua.lumaqq.ui.wizard.search.SearchWizardModel;
import edu.tsinghua.lumaqq.widgets.menu.CMenu;
import edu.tsinghua.lumaqq.widgets.menu.CMenuItem;
import edu.tsinghua.lumaqq.widgets.menu.IMenuListener;
import edu.tsinghua.lumaqq.widgets.menu.ISelectionListener;
import edu.tsinghua.lumaqq.widgets.menu.MenuEvent;
import edu.tsinghua.lumaqq.widgets.menu.SelectionEvent;
import edu.tsinghua.lumaqq.widgets.qstyle.QTreeViewer;

/**
 * 包含一些菜单初始化的方法和工具方法
 * 
 * @author luma
 */
public class MenuHelper {
    private MainShell main;
    private ShellLauncher shellLauncher;
    private Resources res;
    private ExportHelper exportHelper;
    private Shell shell;
    private QQClient client;
    private Display display;
    private OptionHelper options;
    private BlindHelper blindHelper;
    
    private CMenu statusMenu, sysMenu, groupMenu, friendMenu, awayMenu, displayMenu, clusterMenu;
    private CMenu organizationMenu, organizationContainerMenu, subjectMenu, memberMenu, dialogContainerMenu;
    
	private CMenu robotMenu;	
	private boolean robotInitialized;
    
    public MenuHelper(MainShell main) {
        this.main = main;
        shellLauncher = main.getShellLauncher();
        res = Resources.getInstance();
        exportHelper = main.getExportHelper();
        shell = main.getShell();
        client = main.getClient();
        display = main.getDisplay();
        options = main.getOptionHelper();
        blindHelper = main.getBlindHelper();
        robotInitialized = false;
    }    

	/**
	 * 创建所有菜单
	 */
	public void initMenu() {
		initStatusMenu();
		initSysMenu();
		initGroupMenu();
		initFriendMenu();
		initClusterMenu();
		initOrganizationContainerMenu();
		initSubjectMenu();
		initMemberMenu();
		initDialogContainerMenu();
		initOrganizationMenu();
	}
	
	/**
	 * 初始化组织菜单
	 */
	private void initOrganizationMenu() {
		organizationMenu = new CMenu();
		// 更新组织结构
		CMenuItem mi = new CMenuItem(organizationMenu, SWT.PUSH);
		mi.setImage(res.getImage(Resources.icoRefresh));
		mi.setText(menu_organization_refresh);
		mi.addSelectionListener(new ISelectionListener() {
			
			public void widgetSelected(SelectionEvent e) {
				Organization org = (Organization)organizationMenu.getData();
				client.cluster_UpdateOrganization(org.cluster.clusterId);
			}
		});
		// 发起讨论
		mi = new CMenuItem(organizationMenu, SWT.PUSH);
		mi.setImage(res.getImage(Resources.icoDialog));
		mi.setText(menu_organization_discuss);
		mi.addSelectionListener(new ISelectionListener() {
			
			public void widgetSelected(SelectionEvent e) {
				Organization org = (Organization)organizationMenu.getData();
				List<Integer> members = new ArrayList<Integer>();
				int myQQ = main.getMyModel().qq;
				boolean hasMe = false;
				for(User u : org.cluster.members.values()) {
					if(u.organizationId == org.id) {						
						members.add(u.qq);
						if(u.qq == myQQ)
							hasMe = true;
					}
				}
				if(members.isEmpty() || members.size() == 1 && members.get(0) == myQQ) {
					MessageDialog.openInformation(main.getShell(), message_box_common_info_title, message_box_empty_organization);
					return;
				} else if(members.size() == 2 && (members.get(0) == myQQ || members.get(1) == myQQ)) {
					if(members.get(0) != myQQ) 
						shellLauncher.openIMShell(members.get(0));
					else if(members.get(1) != myQQ)
						shellLauncher.openIMShell(members.get(1));
				} else {		
					if(!hasMe)
						members.add(myQQ);
					
					// 查找看是否已经存在了相应的讨论组
					Cluster subject = null;
					boolean hasSubject = true;
					for(Cluster c : org.cluster.subClusters.values()) {
						if(c.name.equals(org.name) && c.members.size() == members.size()) {
							for(Integer qq : members) {
								if(!c.hasMember(qq)) {
									hasSubject = false;
									break;
								}									
							}
							if(hasSubject) {
								subject = c;
								break;
							} else
								hasSubject = true;
						}
					}
					// 如果讨论组已经存在，直接打开，如果不存在，新建一个
					if(subject == null) {
						main.getUIHelper().addPromotingDiscuss(
								main.getClient().cluster_CreateTemporary(org.name, 
										QQ.QQ_CLUSTER_TYPE_SUBJECT,
										org.cluster.clusterId,
										members)
						);						
					} else {
						shellLauncher.openClusterIMWindow(subject);
					}
				}
			}
		});
		// separator
		new CMenuItem(organizationMenu, SWT.SEPARATOR);
		// 修改组织
		final CMenuItem miModify = new CMenuItem(organizationMenu, SWT.PUSH);
		miModify.setText(menu_organization_modify);
		miModify.addSelectionListener(new ISelectionListener() {
			
			public void widgetSelected(SelectionEvent e) {
				Organization org = (Organization)organizationMenu.getData();
				Organization parent = null;
				for(Organization o : org.cluster.organizations.values()) {
					if(o.isParentOf(org)) {
						parent = o;
						break;
					}
				}
				shellLauncher.openMemberEditShell(MemberEditShell.ORGANIZATION, 
						org.cluster,
						parent,
						org);
			}
		});
		// 添加分组
		final CMenuItem miAdd = new CMenuItem(organizationMenu, SWT.PUSH);
		miAdd.setText(menu_organization_add);
		miAdd.addSelectionListener(new ISelectionListener() {
			
			public void widgetSelected(SelectionEvent e) {
				Organization org = (Organization)organizationMenu.getData();
				if(org.getLevel() >= 3) {
					MessageDialog.openInformation(main.getShell(), message_box_common_info_title, message_box_max_organization_level);
					return;
				}
				// TODO 按理说这里还要检查该层组织个数，不过算了，麻烦
				shellLauncher.openMemberEditShell(MemberEditShell.ORGANIZATION,
						org.cluster,
						org,
						null);
			}
		});
		// 删除分组
		final CMenuItem miRemove = new CMenuItem(organizationMenu, SWT.PUSH);
		miRemove.setText(menu_organization_remove);
		miRemove.addSelectionListener(new ISelectionListener() {
			
			public void widgetSelected(SelectionEvent e) {
				Organization org = (Organization)organizationMenu.getData();
				Cluster cluster = org.cluster;				
				// 查看其是否有子组织或成员
				boolean hasChildren = false;
				for(Organization o : cluster.organizations.values()) {
					if(o.isChildOf(org)) {
						hasChildren = true;
						break;
					}
				}
				if(!hasChildren) {
					for(User u : cluster.members.values()) {
						if(u.organizationId == org.id) {
							hasChildren = true;
							break;
						}
					}
				}
				if(hasChildren) {
					MessageDialog.openInformation(main.getShell(), message_box_common_info_title, message_box_empty_organization_first);
					return;
				}
				// 从ui中删除
				Organization parentOrg = org.getParent();
				cluster.removeOrganization(org);
				
				// 调整组织的id，被删除组织之后的组织id减1
				int id = org.id + 1;
				while(cluster.hasOrganization(id)) {
					cluster.decreaseOrganizationId(id);
					id++;
				}
				// 调整成员的组织id
				List<Member> changedMember = new ArrayList<Member>();
				for(User u : cluster.members.values()) {
					if(u.organizationId > org.id) {
						u.organizationId--;
						Member m = new Member();
						m.qq = u.qq;
						m.organization = u.organizationId;
						changedMember.add(m);
					}
				}
				// 找到这个父组织的最后一个子组织，然后从它开始调整path，如果
				// 要删除的组织没有父组织则不需要做这个操作
				if(parentOrg != null) {
					Organization lastChild = org;
					for(Organization o : cluster.organizations.values()) {
						if(o.isChildOf(parentOrg)) {
							if(o.getShiftMaskLevelId() > lastChild.getShiftMaskLevelId())
								lastChild = o;
						}
					}		
					
					// 它自己就是最后一个时也不需要调整path
					if(lastChild != org) {
						int level = lastChild.getLevel();
						int newValue = org.getShiftMaskLevelId();
						for(Organization o : cluster.organizations.values()) {
							if(lastChild.isPrefixOf(o))
								o.setLevelId(level, newValue);
						}
						lastChild.setLevelId(newValue);
					}
				}
				
				// 提交改变
				List<QQOrganization> temp = new ArrayList<QQOrganization>();
				for(Organization o : cluster.organizations.values()) {
					QQOrganization qqOrg = new QQOrganization();
					qqOrg.id = o.id;
					qqOrg.path = o.path;
					qqOrg.name = o.name;
					temp.add(qqOrg);
				}
				main.getClient().cluster_CommitOrganization(cluster.clusterId, temp);
				main.getClient().cluster_CommitMemberOrganization(cluster.clusterId, changedMember);
				main.getBlindHelper().refreshGroup(cluster.group);
			}
		});
		
		// 菜单监听器
		organizationMenu.addMenuListener(new IMenuListener() {			
			public void menuShown(MenuEvent e) {
				Organization org = (Organization)organizationMenu.getData();
				boolean admin = org.cluster.isSuperMember(main.getMyModel().qq);
				miModify.setEnabled(admin);
				miAdd.setEnabled(admin);
				miRemove.setEnabled(admin);
			}
		});
	}

	/**
	 * 初始化多人对话容器菜单
	 */
	private void initDialogContainerMenu() {
		dialogContainerMenu = new CMenu();
		// 创建多人对话
		CMenuItem mi = new CMenuItem(dialogContainerMenu, SWT.PUSH);
		mi.setImage(res.getImage(Resources.icoDialog));
		mi.setText(menu_dialog_new);
		mi.addSelectionListener(new ISelectionListener() {			
			public void widgetSelected(SelectionEvent e) {				
				shellLauncher.openMemberEditShell(MemberEditShell.TEMP_CLUSTER, null, null, null);
			}
		});
		// 刷新
		mi = new CMenuItem(dialogContainerMenu, SWT.PUSH);
		mi.setImage(res.getImage(Resources.icoRefresh));
		mi.setText(menu_dialog_refresh);
		mi.addSelectionListener(new ISelectionListener() {			
			public void widgetSelected(SelectionEvent e) {
				client.cluster_getDialogList();
			}
		});
	}

	/**
	 * 初始化成员菜单
	 */
	private void initMemberMenu() {
		memberMenu = new CMenu();
		// 发消息
		final CMenuItem miSend = new CMenuItem(memberMenu, SWT.PUSH);
		miSend.setImage(res.getImage(Resources.icoSendReceiveMessage));
		miSend.setText(menu_friend_sendreceive);
		miSend.addSelectionListener(new ISelectionListener() {			
			public void widgetSelected(SelectionEvent e) {
				User u = (User)memberMenu.getData();
				shellLauncher.openNormalIMWindow(u);
			}
		});
		// 临时会话
		CMenuItem mi = new CMenuItem(memberMenu, SWT.PUSH);
		mi.setImage(res.getImage(Resources.icoTempSessionIM));
		mi.setText(menu_friend_temp_session);
		mi.addSelectionListener(new ISelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				User u = (User)memberMenu.getData();
				shellLauncher.openTempSessionIMWindow(u.qq);
			}
		});
		// 加为好友
		final CMenuItem miAdd = new CMenuItem(memberMenu, SWT.PUSH);
		miAdd.setImage(res.getImage(Resources.icoAddFriend));
		miAdd.setText(cluster_im_menu_member_add);
		miAdd.addSelectionListener(new ISelectionListener() {			
			public void widgetSelected(SelectionEvent e) {
				User u = (User)memberMenu.getData();
				shellLauncher.openAddReceiveSystemMessageShell(u);					
				client.user_Add(u.qq);	
			}
		});
		// 查看资料
		mi = new CMenuItem(memberMenu, SWT.PUSH);
		mi.setImage(res.getImage(Resources.icoPersonInfo));
		mi.setText(menu_friend_viewinfo);
		mi.addSelectionListener(new ISelectionListener() {			
			public void widgetSelected(SelectionEvent e) {
				User u = (User)memberMenu.getData();
				if(u.qq == main.getMyModel().qq)
					u.info = main.getMyModel().info;
				UserInfoWindow uiw = shellLauncher.openUserInfoWindow(u, (u.qq == main.getMyModel().qq) ? UserInfoWindow.EDITABLE : UserInfoWindow.READ_ONLY);
				uiw.setCurrentPage(UserInfoWindow.CARD);
			}
		});
		// separator
		new CMenuItem(memberMenu, SWT.SEPARATOR);
		// 改名
		mi = new CMenuItem(memberMenu, SWT.PUSH);
		mi.setImage(res.getImage(Resources.icoRename));
		mi.setText(menu_friend_rename);
		mi.addSelectionListener(new ISelectionListener() {			
			public void widgetSelected(SelectionEvent e) {
				User f = (User)memberMenu.getData();
				blindHelper.getViewer(f).editText(f);
			}
		});
		// 置顶
		final CMenuItem miPin = new CMenuItem(memberMenu, SWT.PUSH);
		miPin.setImage(res.getImage(Resources.icoPin));
		miPin.setText(menu_friend_pin);
		miPin.addSelectionListener(new ISelectionListener() {			
			public void widgetSelected(SelectionEvent e) {
				User f = (User)memberMenu.getData();
				f.pinned = !f.pinned;
				blindHelper.refreshGroup(f.group);
			}
		});
		
		// add menu listener
		memberMenu.addMenuListener(new IMenuListener() {			
			public void menuShown(MenuEvent e) {
				User u = (User)memberMenu.getData();
				boolean friend = u.isFriend();
				miSend.setEnabled(friend);
				miAdd.setEnabled(!friend);
				miPin.setText(u.pinned ? menu_friend_unpin : menu_friend_pin);
			}
		});
	}

	/**
	 * 初始化讨论组菜单
	 */
	private void initSubjectMenu() {
		subjectMenu = new CMenu();
		// 创建讨论组
		CMenuItem mi = new CMenuItem(subjectMenu, SWT.PUSH);
		mi.setImage(res.getImage(Resources.icoNewSubject));
		mi.setText(menu_subject_new);
		mi.addSelectionListener(new ISelectionListener() {
			
			public void widgetSelected(SelectionEvent e) {
				Dummy dummy = (Dummy)subjectMenu.getData();
				shellLauncher.openMemberEditShell(MemberEditShell.TEMP_CLUSTER, dummy.cluster, null, null);
			}
		});
		// 刷新
		mi = new CMenuItem(subjectMenu, SWT.PUSH);
		mi.setImage(res.getImage(Resources.icoRefresh));
		mi.setText(menu_subject_refresh_all);
		mi.addSelectionListener(new ISelectionListener() {
			
			public void widgetSelected(SelectionEvent e) {
				Dummy dummy = (Dummy)subjectMenu.getData();
				client.cluster_getSubjectList(dummy.cluster.clusterId);
			}
		});
	}

	/**
	 * 初始化组织菜单
	 */
	private void initOrganizationContainerMenu() {
		organizationContainerMenu = new CMenu();
		// 更新组织结构
		CMenuItem mi = new CMenuItem(organizationContainerMenu, SWT.PUSH);
		mi.setImage(res.getImage(Resources.icoRefresh));
		mi.setText(menu_organization_refresh);
		mi.addSelectionListener(new ISelectionListener() {
			
			public void widgetSelected(SelectionEvent e) {
				Dummy dummy = (Dummy)organizationContainerMenu.getData();
				client.cluster_UpdateOrganization(dummy.cluster.clusterId);
			}
		});
		// 添加分组
		final CMenuItem miAdd = new CMenuItem(organizationContainerMenu, SWT.PUSH);
		miAdd.setImage(res.getImage(Resources.icoOrganization));
		miAdd.setText(menu_organization_add);
		miAdd.addSelectionListener(new ISelectionListener() {
			
			public void widgetSelected(SelectionEvent e) {
				Dummy dummy = (Dummy)organizationContainerMenu.getData();
				// TODO 按理说这里还要检查顶层组织个数，不过算了，麻烦
				shellLauncher.openMemberEditShell(MemberEditShell.ORGANIZATION, dummy.cluster, null, null);
			}
		});
		
		// 添加菜单监听器
		organizationContainerMenu.addMenuListener(new IMenuListener() {
			
			public void menuShown(MenuEvent e) {
				Dummy dummy = (Dummy)organizationContainerMenu.getData();
				miAdd.setEnabled(dummy.cluster.isSuperMember(main.getMyModel().qq));
			}
		});
	}

	/**
	 * 初始化群菜单
	 */
	private void initClusterMenu() {
		clusterMenu = new CMenu();
		// 发群组消息
		CMenuItem mi = new CMenuItem(clusterMenu, SWT.PUSH);
		mi.setText(menu_cluster_send);
		mi.setImage(res.getImage(Resources.icoSendReceiveMessage));
		mi.addSelectionListener(
			new ISelectionListener() {
				public void widgetSelected(SelectionEvent e) {					
					shellLauncher.openClusterIMWindow((Cluster)clusterMenu.getData());
				}
			}
		);
		// 查看群组资料
		final CMenuItem miInfo = new CMenuItem(clusterMenu, SWT.PUSH);
		miInfo.setImage(res.getImage(Resources.icoClusterInfo));
		miInfo.addSelectionListener(
			new ISelectionListener() {
				public void widgetSelected(SelectionEvent e) {	
					// 得到群的model
					Cluster c = (Cluster)clusterMenu.getData();
					if(c.isPermanent())
						shellLauncher.openClusterInfoWindow(c);
					else
						shellLauncher.openMemberEditShell(MemberEditShell.TEMP_CLUSTER, c.getParentCluster(), null, c);
				}
			}
		);
		// separator
		mi = new CMenuItem(clusterMenu, SWT.SEPARATOR);
		// 消息管理菜单
		mi = new CMenuItem(clusterMenu, SWT.CASCADE);
		mi.setText(menu_cluster_message_manage);
		mi.setImage(res.getImage(Resources.icoMessageManage));
		CMenu msgMenu = new CMenu(mi);
		// 导出为文本文件菜单
		mi = new CMenuItem(msgMenu, SWT.PUSH);
		mi.setText(menu_cluster_message_export);
		mi.setImage(res.getImage(Resources.icoTxtFile));
		mi.addSelectionListener(
			new ISelectionListener() {
				public void widgetSelected(SelectionEvent e) {
					// 得到群的model
					Cluster c = (Cluster)clusterMenu.getData();
					// 导出
					exportHelper.exportMessage(c);
				}
			}
		);
		// separator
		mi = new CMenuItem(clusterMenu, SWT.SEPARATOR);
		// 退出该群
		mi = new CMenuItem(clusterMenu, SWT.PUSH);
		mi.setText(menu_cluster_exit);
		mi.setImage(res.getImage(Resources.icoExit));
		mi.addSelectionListener(
			new ISelectionListener() {
				public void widgetSelected(SelectionEvent e) {				    
					Cluster c = (Cluster)clusterMenu.getData();
					boolean yes = false;
					if(c.creator == main.getMyModel().qq)
					    yes = MessageDialog.openQuestion(shell, message_box_common_question_title, message_box_exit_my_cluster);
					else
					    yes = MessageDialog.openQuestion(shell, message_box_common_question_title, message_box_exit_cluster);
					if(yes) {
						if(c.isPermanent()) {
							if(c.creator == main.getMyModel().qq)
								client.cluster_Dismiss(c.clusterId);
							else
								client.cluster_Exit(c.clusterId);													
						} else
						    client.cluster_SearchTemp(c.clusterType.toQQConstant(), c.clusterId, c.parentClusterId);			
					}
				}
			}
		);
		// 菜单事件监听器
		clusterMenu.addMenuListener(new IMenuListener() {
            public void menuShown(MenuEvent e) {
				// 得到群的model
				Cluster c = (Cluster)clusterMenu.getData();
				
				if(!c.isPermanent() || c.isSuperMember(main.getMyModel().qq))
				    miInfo.setText(menu_cluster_modifyinfo);
				else
				    miInfo.setText(menu_cluster_viewinfo);
            }
		});		
	}

	/**
	 * 初始化组菜单
	 */
	private void initGroupMenu() {
		groupMenu = new CMenu();
		// 显示大/小头像
		final CMenuItem miHeadSize = new CMenuItem(groupMenu, SWT.PUSH);
		miHeadSize.addSelectionListener(new ISelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				boolean b = options.isShowSmallHead();
			    blindHelper.setShowSmallHead(!b);
			    options.setShowSmallHead(!b);
			}
		});
		// 群操作菜单
		final CMenuItem miCluster = new CMenuItem(groupMenu, SWT.CASCADE);
		miCluster.setText(menu_group_cluster);
		miCluster.setImage(res.getImage(Resources.icoCluster));
		CMenu subMenu = new CMenu(miCluster);
		// 查找一个群
		CMenuItem mi = new CMenuItem(subMenu, SWT.PUSH);
		mi.setText(menu_group_cluster_search);
		mi.setImage(res.getImage(Resources.icoSearchCluster));
		mi.addSelectionListener(new ISelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				if(main.getClient().getUser().isLoggedIn()) {
					WizardWindow window = shellLauncher.openSearchWizard();
					SearchWizardModel model = (SearchWizardModel)window.getModel();
					model.setSearchWhat(SearchWizardModel.CLUSTER);
					model.setStartingPage(SearchWizardModel.PAGE_HOW_TO_SEARCH_CLUSTER);
					window.showStartingPage();
				}
			}
		});	
		// 创建一个群
		mi = new CMenuItem(subMenu, SWT.PUSH);
		mi.setText(menu_group_cluster_create);
		mi.setImage(res.getImage(Resources.icoAddCluster));
		mi.addSelectionListener(new ISelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				shellLauncher.openClusterWizard();
			}
		});	
		// 查找校友录
		mi = new CMenuItem(subMenu, SWT.PUSH);
		mi.setText(menu_group_alumni_search);
		mi.setImage(res.getImage(Resources.icoSearchCluster));
		mi.addSelectionListener(new ISelectionListener() {
            public void widgetSelected(SelectionEvent e) {
                shellLauncher.goAlumni();
            }
		});
		// 创建校友录
		mi = new CMenuItem(subMenu, SWT.PUSH);
		mi.setText(menu_group_alumni_create);
		mi.setImage(res.getImage(Resources.icoAddAlbum));
		mi.addSelectionListener(new ISelectionListener() {
            public void widgetSelected(SelectionEvent e) {
                shellLauncher.goAlumni();
            }
		});
		// separator
		mi = new CMenuItem(subMenu, SWT.SEPARATOR);
		// 登录群社区
		mi = new CMenuItem(subMenu, SWT.PUSH);
		mi.setText(menu_group_community);
		mi.setImage(res.getImage(Resources.icoCluster));
		mi.addSelectionListener(new ISelectionListener() {
            public void widgetSelected(SelectionEvent e) {
                shellLauncher.goClusterCommunity();
            }
		});
		// 颜色设置
		mi = new CMenuItem(groupMenu, SWT.CASCADE);
		mi.setText(menu_group_color);
		mi.setImage(res.getImage(Resources.icoColor));
		// 设置背景颜色
		CMenu colorMenu = new CMenu(mi);
		CMenuItem sub = new CMenuItem(colorMenu, SWT.PUSH);
		sub.setText(menu_group_group_color);
		sub.addSelectionListener(new ISelectionListener() {
			public void widgetSelected(SelectionEvent e) {
			    // 创建颜色对话框
			    ColorDialog dialog = new ColorDialog(shell);
			    // 设置当前颜色
			    RGB oldRGB = options.getGroupBackgroup();
			    dialog.setRGB(oldRGB);
			    // 打开对话框，如果返回值不为null，设置新颜色
			    RGB rgb = dialog.open();
			    if(rgb != null) {
			        options.setGroupBackgroup(rgb);
			        if(main.getGroupColor() != null)
			            main.getGroupColor().dispose();
			        main.setGroupColor(new Color(display, rgb));
			    }
			}
		});
		// 名称显示
		final CMenuItem miName = new CMenuItem(groupMenu, SWT.CASCADE);
		miName.setText(menu_group_displayname);
		// 显示昵称
		displayMenu = new CMenu(miName);
		final CMenuItem miShowNick = new CMenuItem(displayMenu, SWT.RADIO);
		miShowNick.setText(menu_group_displayname_nick);
		miShowNick.addSelectionListener(new ISelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				if(miShowNick.isSelected()) {
					blindHelper.setShowNick(true);
					blindHelper.refreshAll();
					options.setShowNick(true);					
				}
			}
		});
		// 显示备注名称
		final CMenuItem miShowRemark = new CMenuItem(displayMenu, SWT.RADIO);
		miShowRemark.setText(menu_group_displayname_remark);
		miShowRemark.addSelectionListener(new ISelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				if(miShowRemark.isSelected()) {
					blindHelper.setShowNick(false);
					blindHelper.refreshAll();
					options.setShowNick(false);					
				}
			}
		});
		// 显示/不显示用户提示
		final CMenuItem miSwitchFriendTip = new CMenuItem(groupMenu, SWT.PUSH);
		miSwitchFriendTip.addSelectionListener(new ISelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				main.getOptionHelper().setShowFrientTip(!main.getOptionHelper().isShowFriendTip());
			}
		});
		// Blind模式切换
		final CMenuItem miSwitchMode = new CMenuItem(groupMenu, SWT.PUSH);
		miSwitchMode.addSelectionListener(new ISelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				main.getMessageQueue().setPostpone(true);
				blindHelper.stopAllEffectOnBlind();			
				boolean b = options.isTreeMode();
				options.setTreeModel(!b);
				blindHelper.setTreeMode(!b);		
				blindHelper.resetAllImageEffect();
				main.getMessageQueue().setPostpone(false);
				main.getMessageHelper().processPostponedIM();
			}
		});
		// separator
		mi = new CMenuItem(groupMenu, SWT.SEPARATOR);
		// 添加组
		mi = new CMenuItem(groupMenu, SWT.PUSH);
		mi.setText(menu_friend_addgroup);
		mi.addSelectionListener(new ISelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				blindHelper.addGroup();
				
				// 这个功能不再使用，因为LumaQQ会允许不自动更新分组信息，所以，这里搞成自动的会带来问题
				//main.getMVCHelper().updateGroupInServer();
			}
		});
		// 添加用户
		mi = new CMenuItem(groupMenu, SWT.PUSH);
		mi.setText(menu_group_addfriend);
		mi.setImage(res.getImage(Resources.icoAddFriend));
		mi.addSelectionListener(new ISelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				shellLauncher.openSearchWizard();
			}
		});
		// separator
		mi = new CMenuItem(groupMenu, SWT.SEPARATOR);
		// 只显示在线好友
		final CMenuItem miShowOnlineOnly = new CMenuItem(groupMenu, SWT.PUSH);
		miShowOnlineOnly.addSelectionListener(new ISelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				boolean b = options.isShowOnlineOnly();
				blindHelper.setShowOnlineOnly(!b);
				options.setShowOnlineOnly(!b);
				blindHelper.refreshAll();
			}
		});
		// 组操作区域，包括重命名，删除该组等等
		// separator
		mi = new CMenuItem(groupMenu, SWT.SEPARATOR);
		// 重命名
		final CMenuItem miRename = new CMenuItem(groupMenu, SWT.PUSH);
		miRename.setText(menu_group_rename);
		miRename.setImage(res.getImage(Resources.icoRename));
		miRename.addSelectionListener(new ISelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				if(blindHelper.isTreeMode()) {
					QTreeViewer<Model> viewer = blindHelper.getCurrentViewer();
					if(viewer == null)
						return;
					Group g = (Group)groupMenu.getData();
					viewer.editText(g);
				} else
					blindHelper.editCurrentSlatText();
			}
		});
		// 删除该组
		final CMenuItem miDelete = new CMenuItem(groupMenu, SWT.PUSH);
		miDelete.setText(menu_group_delete);
		miDelete.setImage(res.getImage(Resources.icoDelGroup));
		miDelete.addSelectionListener(new ISelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				Group g = (Group)groupMenu.getData();
				if(g.users.size() > 0) {
					MessageBox box = new MessageBox(shell, SWT.ICON_WARNING | SWT.OK);
					box.setText(message_box_common_warning_title);
					box.setMessage(hint_please_clear_friend);
					box.open();
				} else {
					blindHelper.removeGroup(g);
					
					// 这个功能不再使用，因为LumaQQ会允许不自动更新分组信息，所以，这里搞成自动的会带来问题
					//main.getMVCHelper().updateGroupInServer();
				}
			}
		});
		// 黑名单组特定操作项，包括添加坏人名单，隐藏显示黑名单等等
		// separator
		mi = new CMenuItem(groupMenu, SWT.SEPARATOR);
		// 添加坏人名单
		final CMenuItem miAddBadGuy = new CMenuItem(groupMenu, SWT.PUSH);
		miAddBadGuy.setText(menu_group_addbadguy);
		miAddBadGuy.setImage(res.getImage(Resources.icoAddFriend));
		miAddBadGuy.addSelectionListener(new ISelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				User f = new User();
				f.qq = 0;
				f.nick = "Input His QQ";
				f.displayName = f.nick;
				blindHelper.addUser(f, GroupType.BLACKLIST_GROUP);
				QTreeViewer<Model> viewer = blindHelper.getViewer(f);
				blindHelper.refreshGroup(f.group);
				blindHelper.expandGroup(f.group);
				viewer.editText(f);
			}
		});
		// 隐藏显示黑名单
		final CMenuItem miSwitchBlacklist = new CMenuItem(groupMenu, SWT.PUSH);
		miSwitchBlacklist.addSelectionListener(new ISelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				boolean b = options.isShowBlacklist();
				options.setShowBlacklist(!b);
				blindHelper.setBlacklistGroupVisible(!b);
			}
		});
		
		// 添加菜单显示事件监听器
		groupMenu.addMenuListener(new IMenuListener() {
			public void menuShown(MenuEvent e) {
				// 显示什么头像的菜单使能状态
				if(options.isShowSmallHead()) {
					miHeadSize.setText(menu_friend_showlarge);
					miHeadSize.setImage(res.getImage(Resources.icoLargeHead));
				} else {
					miHeadSize.setText(menu_friend_showsmall);
					miHeadSize.setImage(res.getImage(Resources.icoSmallHead));				
				}
				// 模式菜单
				if(options.isTreeMode()) {
					miSwitchMode.setText(menu_group_traditional_mode);
					miSwitchMode.setImage(res.getImage(Resources.icoTraditionalMode));
				} else {
					miSwitchMode.setText(menu_group_tree_mode);
					miSwitchMode.setImage(res.getImage(Resources.icoTreeMode));
				}
				// 得到当前组的model
				Group g = (Group)groupMenu.getData();
				// 设置名称显示子菜单的使能状态和选择状态
				// 显示昵称还是显示备注的选择状态
				if(options.isShowNick()) {
					miShowNick.setSelected(true);
					miShowRemark.setSelected(false);
				} else {
					miShowNick.setSelected(false);
					miShowRemark.setSelected(true);
				}						
				// 设置群操作菜单的使能性
				miCluster.setEnabled(g.isCluster());			
				// 设置用户提示菜单
				if(main.getOptionHelper().isShowFriendTip()) {
					miSwitchFriendTip.setImage(res.getImage(Resources.icoDisableFriendTip));
					miSwitchFriendTip.setText(menu_group_disable_user_tip);
				} else {
					miSwitchFriendTip.setImage(res.getImage(Resources.icoEnableFriendTip));
					miSwitchFriendTip.setText(menu_group_enable_user_tip);
				}
				// 设置只显示在线用户还是显示所有用户菜单的文字标签
				miShowOnlineOnly.setText(options.isShowOnlineOnly() ? menu_group_showall : menu_group_showonlineonly);
				// 设置组改名组删除菜单的使能性				
				if(g.isFriendly() && !g.isDefaultFriend()) {
					miRename.setEnabled(true);
					miDelete.setEnabled(true);
				} else {
					miRename.setEnabled(false);
					miDelete.setEnabled(false);		
				}
				// 设置黑名单操作项的使能性
				miAddBadGuy.setEnabled(g.isBlackList());
				// 设置隐藏显示黑名单的图标和文字
				if(options.isShowBlacklist()) {
					miSwitchBlacklist.setText(menu_group_hideblacklist);
					miSwitchBlacklist.setImage(res.getImage(Resources.icoHideBlackList));
				} else {
					miSwitchBlacklist.setText(menu_group_showblacklist);
					miSwitchBlacklist.setImage(res.getImage(Resources.icoShowBlackList));						
				}
			}
		});
	}	

	/**
	 * 初始化在好友上点右键出现的菜单
	 */
	private void initFriendMenu() {
		friendMenu = new CMenu();
		// 收发消息
		CMenuItem mi = new CMenuItem(friendMenu, SWT.PUSH);
		mi.setText(menu_friend_sendreceive);
		mi.setImage(res.getImage(Resources.icoSendReceiveMessage));
		mi.addSelectionListener(new ISelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				// 得到当前选择用户的view part，打开消息窗口
			    shellLauncher.openNormalIMWindow((User)friendMenu.getData());
			}
		});
		// 发送短信
		mi = new CMenuItem(friendMenu, SWT.PUSH);
		mi.setText(menu_friend_sendsms);
		mi.setImage(res.getImage(Resources.icoMobile));
		mi.addSelectionListener(new ISelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				User f = (User)friendMenu.getData();
				SMSWindow window = shellLauncher.openSMSWindow(f);
				window.setReceiver(String.valueOf(f.qq));
			}
		});
		// 临时会话
		mi = new CMenuItem(friendMenu, SWT.PUSH);
		mi.setImage(res.getImage(Resources.icoTempSessionIM));
		mi.setText(menu_friend_temp_session);
		mi.addSelectionListener(new ISelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				User u = (User)friendMenu.getData();
				shellLauncher.openTempSessionIMWindow(u.qq);
			}
		});
		// separator
		mi = new CMenuItem(friendMenu, SWT.SEPARATOR);
		// 查看资料
		mi = new CMenuItem(friendMenu, SWT.PUSH);
		mi.setText(menu_friend_viewinfo);
		mi.setImage(res.getImage(Resources.icoPersonInfo));
		mi.addSelectionListener(new ISelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				// 得到当前选择用户的view part，打开资料窗口
				shellLauncher.openUserInfoWindow((User)friendMenu.getData(), UserInfoWindow.READ_ONLY);
			}
		});
		// 消息管理菜单
		mi = new CMenuItem(friendMenu, SWT.CASCADE);
		mi.setText(menu_friend_message_manage);
		mi.setImage(res.getImage(Resources.icoMessageManage));
		CMenu msgMenu = new CMenu(mi);
		// 导出为文本文件菜单
		mi = new CMenuItem(msgMenu, SWT.PUSH);
		mi.setText(menu_friend_message_export);
		mi.setImage(res.getImage(Resources.icoTxtFile));
		mi.addSelectionListener(new ISelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				// 得到好友的model
				User f = (User)friendMenu.getData();
				// 导出
				exportHelper.exportMessage(f);
			}
		});
		// separator
		mi = new CMenuItem(friendMenu, SWT.SEPARATOR);
		// 显示大/小头像
		final CMenuItem miSwitchHead = new CMenuItem(friendMenu, SWT.PUSH);
		miSwitchHead.setText(menu_friend_showsmall);
		miSwitchHead.setImage(res.getImage(Resources.icoSmallHead));
		miSwitchHead.addSelectionListener(new ISelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				boolean b = options.isShowSmallHead();
				blindHelper.setShowSmallHead(!b);
				options.setShowSmallHead(!b);
			}
		});
		// 添加组
		mi = new CMenuItem(friendMenu, SWT.PUSH);
		mi.setText(menu_friend_addgroup);
		mi.addSelectionListener(new ISelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				blindHelper.addGroup();
			}
		});
		// separator
		mi = new CMenuItem(friendMenu, SWT.SEPARATOR);
		// 只显示在线好友
		final CMenuItem miShowOnlineOnly = new CMenuItem(friendMenu, SWT.PUSH);
		miShowOnlineOnly.addSelectionListener(new ISelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				boolean b = options.isShowOnlineOnly();
				blindHelper.setShowOnlineOnly(!b);
				options.setShowOnlineOnly(!b);
				blindHelper.refreshAll();
			}
		});
		// separator
		mi = new CMenuItem(friendMenu, SWT.SEPARATOR);
		// 从该组删除
		mi = new CMenuItem(friendMenu, SWT.PUSH);
		mi.setText(menu_friend_delfriend);
		mi.setImage(res.getImage(Resources.icoDelFriend));
		mi.addSelectionListener(new ISelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				User f = (User)friendMenu.getData();					
				// 检查要删除的这个好友是否在一个好友组中，如果是，需要从服务器删除，如果不是
				// 直接删除即可
				if(!f.isInFriendGroup()) {
					blindHelper.simpleMove(f, f.group, null);		
				} else {
					MessageBox box = new MessageBox(shell, SWT.ICON_QUESTION | SWT.YES | SWT.NO);
					box.setText(message_box_common_question_title);
					box.setMessage(NLS.bind(hint_delete_friend, f.displayName));
					if(box.open() == SWT.YES) {
					    shellLauncher.openDeleteReceiveSystemMessageShell(f);
					    main.deleteFriend(f, false, false);
					}
				}
			}
		});
		// 改名
		final CMenuItem miRename = new CMenuItem(friendMenu, SWT.PUSH);
		miRename.setText(menu_friend_rename);
		miRename.setImage(res.getImage(Resources.icoRename));
		miRename.addSelectionListener(new ISelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				User f = (User)friendMenu.getData();
				blindHelper.getViewer(f).editText(f);
			}
		});
		// 置顶
		final CMenuItem miPin = new CMenuItem(friendMenu, SWT.PUSH);
		miPin.setImage(res.getImage(Resources.icoPin));
		miPin.addSelectionListener(new ISelectionListener() {
			
			public void widgetSelected(SelectionEvent e) {
				User f = (User)friendMenu.getData();
				f.pinned = !f.pinned;
				blindHelper.refreshGroup(f.group);
			}
		});
		// 添加事件监听器，因为需要判断当前显示的是小头像还是大头像，从而使相应的菜单disable
		friendMenu.addMenuListener(new IMenuListener() {
			public void menuShown(MenuEvent e) {
				// 显示什么头像的菜单
				if(options.isShowSmallHead()) {
					miSwitchHead.setText(menu_friend_showlarge);
					miSwitchHead.setImage(res.getImage(Resources.icoLargeHead));
				} else {
					miSwitchHead.setText(menu_friend_showsmall);
					miSwitchHead.setImage(res.getImage(Resources.icoSmallHead));				
				}
				// 显示在线用户还是全部用户的选项
				if(options.isShowOnlineOnly())
					miShowOnlineOnly.setText(menu_friend_showall);
				else
					miShowOnlineOnly.setText(menu_friend_showonlineonly);
				// 改名
				User f = (User)friendMenu.getData();
				miRename.setEnabled(!f.group.isBlackList());
				// 置顶
				miPin.setText(f.pinned ? menu_friend_unpin : menu_friend_pin);
			}
		});
	}

	/**
	 * 初始化系统菜单
	 */
	private void initSysMenu() {
		// 系统弹出菜单
		sysMenu = new CMenu();
		// 分组管理菜单
		final CMenuItem miGroupManage = new CMenuItem(sysMenu, SWT.CASCADE);
		miGroupManage.setText(menu_sys_group);
		miGroupManage.setImage(res.getImage(Resources.icoGroup));
		CMenu groupManageMenu = new CMenu(miGroupManage);
		// 上传分组
		CMenuItem sub = new CMenuItem(groupManageMenu, SWT.PUSH);
		sub.setText(menu_sys_group_upload);
		sub.setImage(res.getImage(Resources.icoUploadGroup));
		sub.addSelectionListener(new ISelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				// 检查是否已经登陆，没有则返回
				if(!client.getUser().isLoggedIn()) return;
				IExecutor executor = new DialogJobExecutor(main);
			    executor.addJob(new UploadGroupJob());
			    executor.execute();
			}
		});
		// 下载分组
		sub = new CMenuItem(groupManageMenu, SWT.PUSH);
		sub.setText(menu_sys_group_download);
		sub.setImage(res.getImage(Resources.icoDownloadGroup));
		sub.addSelectionListener(new ISelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				if(client.getUser().isLoggedIn()) {
					main.getMessageQueue().setPostpone(true);
					blindHelper.stopAllEffectOnBlind();			
					IExecutor executor = new DialogJobExecutor(main);
				    executor.addJob(new DownloadGroupJob());
				    executor.execute();		
					blindHelper.resetAllImageEffect();
					main.getMessageQueue().setPostpone(false);
					main.getMessageHelper().processPostponedIM();
				}
			}
		});
		// 好友与资料
		final CMenuItem miFriend = new CMenuItem(sysMenu, SWT.CASCADE);
		miFriend.setText(menu_sys_friend);
		miFriend.setImage(res.getImage(Resources.icoFriendInfoManage));
		CMenu infoMenu = new CMenu(miFriend);
		// 批量下载好友备注
		CMenuItem mi = new CMenuItem(infoMenu, SWT.PUSH);
		mi.setText(menu_sys_friend_download_remark);
		mi.setImage(res.getImage(Resources.icoDownloadRemark));
		mi.addSelectionListener(new ISelectionListener() {
            public void widgetSelected(SelectionEvent e) {
				// 检查是否已经登陆，没有则返回
				if(!client.getUser().isLoggedIn()) return;
				DialogJobExecutor executor = new DialogJobExecutor(main);
				executor.setModeless(true);
				executor.setCancelable(true);
				executor.addJob(new DownloadFriendRemarkJob());
				executor.execute();
            }
		});
		// 信息管理器
		mi = new CMenuItem(infoMenu, SWT.PUSH);
		mi.setText(menu_sys_info_window);
		mi.setImage(res.getImage(Resources.icoMessageManage));
		mi.addSelectionListener(new ISelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				shellLauncher.openInfoManagerWindow();
			}
		});
		// 导出聊天记录
		mi = new CMenuItem(infoMenu, SWT.PUSH);
		mi.setText(menu_sys_friend_export_record);
		mi.setImage(res.getImage(Resources.icoTxtFile));
		mi.addSelectionListener(new ISelectionListener() {
            public void widgetSelected(SelectionEvent e) {
                exportHelper.exportAllMessage();
            }
		});
		// 工具
		final CMenuItem miTool = new CMenuItem(sysMenu, SWT.CASCADE);
		miTool.setText(menu_sys_tool);
		miTool.setImage(res.getImage(Resources.icoTool));
		CMenu toolMenu = new CMenu(miTool);
		// 申请QQ号码
		mi = new CMenuItem(toolMenu, SWT.PUSH);
		mi.setText(menu_sys_apply);
		mi.setImage(res.getImage(Resources.icoApply));
		mi.addSelectionListener(new ISelectionListener() {
            public void widgetSelected(SelectionEvent e) {
                shellLauncher.goApply();
            }
		});
		// IP查询
		mi = new CMenuItem(toolMenu, SWT.PUSH);
		mi.setText(menu_sys_searchip);
		mi.setImage(res.getImage(Resources.icoSearchIp));
		mi.addSelectionListener(new ISelectionListener() {
            public void widgetSelected(SelectionEvent e) {
                shellLauncher.openIPSeekerWindow();
            }
		});
		// 天气查询
		mi = new CMenuItem(toolMenu, SWT.PUSH);
		mi.setText(menu_sys_weather);
		mi.setImage(res.getImage(Resources.icoSun));
		mi.addSelectionListener(new ISelectionListener() {
            public void widgetSelected(SelectionEvent e) {
                shellLauncher.openWeatherWindow();
            }
		});
		// 临时会话
		mi = new CMenuItem(toolMenu, SWT.PUSH);
		mi.setText(menu_sys_temp_session);
		mi.setImage(res.getImage(Resources.icoTempSessionIM));
		mi.addSelectionListener(new ISelectionListener() {
            public void widgetSelected(SelectionEvent e) {
                InputDialog dialog = new InputDialog(main.getShell(),
                		"Input QQ Number",
                		"Input the QQ number which you want to talk",
                		null,
                		null);
                if(IDialogConstants.OK_ID == dialog.open()) {
                	String value = dialog.getValue();
                	if(value == null)
                		return;
                	int qq = Util.getInt(value, 0);
                	if(qq == 0)
                		return;
                	shellLauncher.openTempSessionIMWindow(qq);
                }
            }
		});
		// separator
		final CMenuItem miSep1 = new CMenuItem(sysMenu, SWT.SEPARATOR);
		// 个人设定
		final CMenuItem miPersonalInfo = new CMenuItem(sysMenu, SWT.PUSH);
		miPersonalInfo.setText(menu_sys_personinfo);
		miPersonalInfo.setImage(res.getImage(Resources.icoPersonInfo));
		miPersonalInfo.addSelectionListener(new ISelectionListener() {
			public void widgetSelected(SelectionEvent e)  {
			    shellLauncher.openUserInfoWindow(main.getMyModel(), UserInfoWindow.EDITABLE);
			}
		});
		// 系统参数
		mi = new CMenuItem(sysMenu, SWT.PUSH);
		mi.setText(menu_sys_sysopt);
		mi.setImage(res.getImage(Resources.icoSysOpt));
		mi.addSelectionListener(new ISelectionListener() {
			public void widgetSelected(SelectionEvent e)  {
				shellLauncher.openSystemOptionWindow();
			}
		});
		// separator
		new CMenuItem(sysMenu, SWT.SEPARATOR);
		// 更改用户
		final CMenuItem miChangeUser = new CMenuItem(sysMenu, SWT.PUSH);
		miChangeUser.setText(menu_sys_changeuser);
		miChangeUser.setImage(res.getImage(Resources.icoChangeUser));
		miChangeUser.addSelectionListener(new ISelectionListener() {
			public void widgetSelected(SelectionEvent e)  {
			    main.changeUser();
			}
		});
		// separator
		final CMenuItem miSep2 = new CMenuItem(sysMenu, SWT.SEPARATOR);
		// 检查LumaQQ更新
		mi = new CMenuItem(sysMenu, SWT.PUSH);
		mi.setText(menu_sys_checkupdate);
		mi.setImage(res.getImage(Resources.icoUpdate));
		mi.addSelectionListener(new ISelectionListener() {
            public void widgetSelected(SelectionEvent e) {
                shellLauncher.openCheckUpdateShell();
            }
		});
		// LumaQQ主页
		mi = new CMenuItem(sysMenu, SWT.PUSH);
		mi.setText(menu_sys_lumaqq_homepage);
		mi.setImage(res.getImage(Resources.icoFirefox));
		mi.addSelectionListener(new ISelectionListener() {
            public void widgetSelected(SelectionEvent e) {
    		    shellLauncher.goLumaQQ();
            }
		});
		// 关于
		mi = new CMenuItem(sysMenu, SWT.PUSH);
		mi.setText(menu_sys_about);
		mi.setImage(res.getImage(Resources.icoAbout));
		mi.addSelectionListener(new ISelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				AboutDialog ad = new AboutDialog(shell);
				ad.open();
			}
		});
		// separator
		new CMenuItem(sysMenu, SWT.SEPARATOR);
		// 调试窗口
		mi = new CMenuItem(sysMenu, SWT.PUSH);
		mi.setText(menu_sys_debug);
		mi.setImage(res.getImage(Resources.icoDebug));
		mi.addSelectionListener(new ISelectionListener() {
            public void widgetSelected(SelectionEvent e) {
                new Debugger(main).open();
            }
		});
		// separator
		new CMenuItem(sysMenu, SWT.SEPARATOR);
		// 聊天机器人
    	final CMenuItem miRobot = new CMenuItem(sysMenu, SWT.CASCADE);
    	miRobot.setText(menu_sys_robot);
    	miRobot.setImage(res.getImage(Resources.icoMachine));
    	robotMenu = new CMenu(miRobot);
		// separator
    	final CMenuItem miRobotSeparator = new CMenuItem(sysMenu, SWT.SEPARATOR);        	
		// 退出
		mi = new CMenuItem(sysMenu, SWT.PUSH);
		mi.setText(menu_sys_exit);
		mi.setImage(res.getImage(Resources.icoExit));
		mi.addSelectionListener(new ISelectionListener() {
			public void widgetSelected(SelectionEvent e) {
			    main.close();					
			}
		});
		// separator
		final CMenuItem miStatusSeparator = new CMenuItem(sysMenu, SWT.SEPARATOR);
		// 状态菜单
		final CMenuItem miStatus = new CMenuItem(sysMenu, SWT.CASCADE);
		miStatus.setText(menu_sys_status);
		miStatus.setImage(res.getImage(Resources.icoLumaQQ));
		miStatus.setMenu(statusMenu);
		
		sysMenu.addMenuListener(new IMenuListener() {
            public void menuShown(MenuEvent e) {
            	// 是否已经登录
            	boolean logged = main.getClient().getUser().isLoggedIn();
            	
            	initRobotMenu();
            	if(LumaQQ.hasRobots()) {
            		miRobot.setVisible(true);
            		miRobotSeparator.setVisible(true);
            	} else {
            		miRobot.setVisible(false);
            		miRobotSeparator.setVisible(false);
            	}
            	
            	// sysmenu的data是一个整数，为0表示在系统菜单按钮上显示
            	// 为1表示在tray icon上显示
            	Integer loc = (Integer)sysMenu.getData();
            	miStatus.setVisible(logged && loc == 1);
            	miStatusSeparator.setVisible(logged && loc == 1);
            	
            	// other items
            	miGroupManage.setVisible(logged);
            	miFriend.setVisible(logged);
            	miTool.setVisible(logged);
            	miSep1.setVisible(logged);
            	miPersonalInfo.setVisible(logged);
            	miChangeUser.setVisible(logged);
            	miSep2.setVisible(logged);
            }
		});
	}
	
	/**
	 * 初始化机器人子菜单
	 */
	private void initRobotMenu() {
		if(robotInitialized)
			return;
		
    	// robot menu items
    	List<Robot> robots = LumaQQ.getRobots();
    	int size = robots.size();
    	for(int i = 0; i < size; i++) {
    		Robot robot = robots.get(i);
    		CMenuItem rItem = new CMenuItem(robotMenu, SWT.RADIO);
    		rItem.setText(robot.getName());
    		rItem.setData(i);
    		if(i == 0)
    			rItem.setSelected(true);
    		rItem.addSelectionListener(new ISelectionListener() {
    			public void widgetSelected(SelectionEvent event) {
    				int index = (Integer)event.item.getData();
    				client.setRobot(LumaQQ.getRobot(index));
    			}
    		});
    	}
        // enable/disable robot
        CMenuItem mi = new CMenuItem(robotMenu, SWT.SEPARATOR);
        mi = new CMenuItem(robotMenu, SWT.PUSH);
        if(client.isRobotMode())
        	mi.setText(menu_sys_robot_off);
        else
        	mi.setText(menu_sys_robot_on);
        mi.addSelectionListener(new ISelectionListener() {
            public void widgetSelected(SelectionEvent event) {
                CMenuItem onOff = event.item;
                client.setRobotMode(!client.isRobotMode());
                if(client.isRobotMode())
                    onOff.setText(menu_sys_robot_off);
                else
                    onOff.setText(menu_sys_robot_on);
                if(client.getRobot() == null) {
                	for(CMenuItem mi : robotMenu.getItems()) {
                		if(mi.isSelected()) {
                        	int index = (Integer)mi.getData();
                            client.setRobot(LumaQQ.getRobot(index));
                            break;
                		}
                	}
                }
            }
        });
        
        robotInitialized = true;
	}

	/**
	 * 创建，初始化状态菜单
	 */
	private void initStatusMenu() {	
		// 更改状态弹出菜单
		statusMenu = new CMenu();
		// 上线
		CMenuItem mi = new CMenuItem(statusMenu, SWT.PUSH);
		mi.setText(status_online);
		mi.setImage(res.getImage(Resources.icoOnline));
		mi.addSelectionListener(new ISelectionListener() {
			public void widgetSelected(SelectionEvent e) {
			    main.getClient().getUser().setLoginMode(QQ.QQ_LOGIN_MODE_NORMAL);
				if(main.checkLogin(false, false)) {
					main.getUIHelper().startStatusAnimation();
					client.status_Online();						
				} else
				    client.getUser().setStatus(QQ.QQ_STATUS_ONLINE);
			}
		});
		// separator
		new CMenuItem(statusMenu, SWT.SEPARATOR);
		// 离开，离开菜单有子菜单
		mi = new CMenuItem(statusMenu, SWT.CASCADE);
		mi.setText(status_away);
		mi.setImage(res.getImage(Resources.icoAway));
		awayMenu = new CMenu(mi);
		// 自定义
		CMenuItem sub = new CMenuItem(awayMenu, SWT.PUSH);
		sub.setText(menu_status_away_custom);
		sub.setImage(res.getImage(Resources.icoReply));
		sub.addSelectionListener(new ISelectionListener() {
			public void widgetSelected(SelectionEvent e) {
				shellLauncher.openSystemOptionWindow().setCurrentPage(SystemOptionWindow.REPLY);
			}
		});
		new CMenuItem(awayMenu, SWT.SEPARATOR);
		// 无回复
		sub = new CMenuItem(awayMenu, SWT.RADIO);
		sub.setText(menu_status_away_null);
		sub.addSelectionListener(new ISelectionListener() {
			public void widgetSelected(SelectionEvent e) {
			    main.getClient().getUser().setLoginMode(QQ.QQ_LOGIN_MODE_NORMAL);
				if(main.checkLogin(false, false)) {
					main.getConfigHelper().setAutoReply(false);
					if(client.getUser().getStatus() != QQ.QQ_STATUS_AWAY) {
						main.getUIHelper().startStatusAnimation();
						client.status_Away();					
					}
				} else
				    client.getUser().setStatus(QQ.QQ_STATUS_AWAY);
			}
		});
		// separator
		new CMenuItem(statusMenu, SWT.SEPARATOR);
		// 隐身
		mi = new CMenuItem(statusMenu, SWT.PUSH);
		mi.setText(status_hidden);
		mi.setImage(res.getImage(Resources.icoHidden));
		mi.addSelectionListener(new ISelectionListener() {
			public void widgetSelected(SelectionEvent e) {
			    main.getClient().getUser().setLoginMode(QQ.QQ_LOGIN_MODE_HIDDEN);
				if(main.checkLogin(false, false)) {
					if(client.getUser().getStatus() != QQ.QQ_STATUS_HIDDEN) {
						main.getUIHelper().startStatusAnimation();
						client.status_Hidden();						
					}
				} else
				    client.getUser().setStatus(QQ.QQ_STATUS_HIDDEN);
			}
		});
		// separator
		new CMenuItem(statusMenu, SWT.SEPARATOR);
		// 离线
		mi = new CMenuItem(statusMenu, SWT.PUSH);
		mi.setText(status_offline);
		mi.setImage(res.getImage(Resources.icoOffline));
		mi.addSelectionListener(new ISelectionListener() {
			public void widgetSelected(SelectionEvent e) {
			    main.checkGroupDirty();
				main.getClient().getUser().setStatus(QQ.QQ_STATUS_OFFLINE);
			    main.logout();
			}
		});
	}	
	
    /**
     * 设置QQClient
     * 
     * @param client
     * 		QQ客户端对象
     */
    public void setClient(QQClient client) {
        this.client = client;
    }
    
    /**
     * 设置状态菜单的可见性
     * 
     * @param b
     */
    public void setStatusMenuVisible(boolean b) {
    	hideAllMenu();
        statusMenu.setVisible(b);
    }
    
    /**
     * 设置状态菜单的位置
     * 
     * @param loc
     * 		菜单左上角位置
     */
    public void setStatusMenuLocation(Point loc) {
        statusMenu.setLocation(loc);
    }
    
    /**
     * 设置组菜单位置
     * 
     * @param loc
     */
    public void setGroupMenuLocation(Point loc) {
        groupMenu.setLocation(loc);
    }
    
    /**
     * 设置组菜单数据
     * 
     * @param data
     */
    public void setGroupMenuData(Object data) {
    	groupMenu.setData(data);
    }
    
    /**
     * 设置组菜单可见性
     * 
     * @param b
     */
    public void setGroupMenuVisible(boolean b) {
    	hideAllMenu();
        groupMenu.setVisible(b);
    }    
    
    /**
     * 设置好友菜单位置
     * 
     * @param loc
     */
    public void setFriendMenuLocation(Point loc) {
        friendMenu.setLocation(loc);
    }
    
    /**
     * 设置好友菜单可见性
     * 
     * @param b
     */
    public void setFriendMenuVisible(boolean b) {
    	hideAllMenu();
        friendMenu.setVisible(b);
    }
    
    /**
     * 设置好友菜单的附加数据
     * 
     * @param data
     */
    public void setFriendMenuData(Object data) {
        friendMenu.setData(data);
    }
    
    /**
     * 设置成员菜单位置
     * 
     * @param loc
     */
    public void setMemberMenuLocation(Point loc) {
    	memberMenu.setLocation(loc);
    }
    
    /**
     * 设置成员菜单可见性
     * 
     * @param b
     */
    public void setMemberMenuVisible(boolean b) {
    	hideAllMenu();
    	memberMenu.setVisible(b);
    }
    
    /**
     * 设置成员菜单的附加数据
     * 
     * @param data
     */
    public void setMemberMenuData(Object data) {
        memberMenu.setData(data);
    }
    
    /**
     * 设置群菜单的附加数据
     * 
     * @param data
     */
    public void setClusterMenuData(Object data) {
        clusterMenu.setData(data);
    }
    
    /**
     * 设置群菜单位置
     * 
     * @param loc
     */
    public void setClusterMenuLocation(Point loc) {
    	clusterMenu.setLocation(loc);
    }
    
    /**
     * 设置群菜单可见性
     * 
     * @param b
     */
    public void setClusterMenuVisible(boolean b) {
    	hideAllMenu();
        clusterMenu.setVisible(b);
    }
    
    public void setDialogContainerMenuVisible(boolean b) {
    	hideAllMenu();
    	dialogContainerMenu.setVisible(b);
    }
    
    public void setDialogContainerMenuData(Object data) {
    	dialogContainerMenu.setData(data);
    }
    
    public void setDialogContainerMenuLocation(Point loc) {
    	dialogContainerMenu.setLocation(loc);
    }
    
    /**
     * 设置系统菜单的可见性
     * 
     * @param b
     */
    public void setSystemMenuVisible(boolean b) {
    	hideAllMenu();
        sysMenu.setVisible(b);
    }
    
    /**
     * 设置系统菜单的data
     * 
     * @param data
     */
    public void setSystemMenuData(Object data) {
    	sysMenu.setData(data);
    }
    
    /**
     * @return
     * 		true如果系统菜单可见
     */
    public boolean isSystemMenuVisible() {
    	return sysMenu.isVisible();
    }
    
    public void setSystemMenuLocation(Point loc) {
    	sysMenu.setLocation(loc);
    }
    
    public void setSystemMenuLocation(int x, int y) {
    	sysMenu.setLocation(x, y);
    }
    
    public void setSubjectMenuVisible(boolean b) {
    	hideAllMenu();
    	subjectMenu.setVisible(b);
    }
    
    public void setSubjectMenuData(Object data) {
    	subjectMenu.setData(data);
    }
    
    public void setSubjectMenuLocation(Point loc) {
    	subjectMenu.setLocation(loc);
    }
    
    public void setOrganizationContainerMenuVisible(boolean b) {
    	hideAllMenu();
    	organizationContainerMenu.setVisible(b);
    }
    
    public void setOrganizationContainerMenuData(Object data) {
    	organizationContainerMenu.setData(data);
    }    
    
    public void setOrganizationContainerMenuLocation(Point loc) {
    	organizationContainerMenu.setLocation(loc);
    }

    public void setOrganizationMenuVisible(boolean b) {
    	hideAllMenu();
    	organizationMenu.setVisible(b);
    }
    
    public void setOrganizationMenuData(Object data) {
    	organizationMenu.setData(data);
    }    
    
    public void setOrganizationMenuLocation(Point loc) {
    	organizationMenu.setLocation(loc);
    }

	/**
	 * 重新设置离开菜单，发生在用户添加或修改了自动回复时
	 */
    @SuppressWarnings("unchecked")
	public void renewAawyMenu() {
		int count = awayMenu.getItemCount();
		List<String> autoReplies = main.getConfigHelper().getReplies().getAutoReply();
		int size = autoReplies.size();
		// 从3开始调整，前面3个是固定的，分别是自定义，separator和无回复消息
		int loop = Math.min(count, 3 + size);
		for(int i = 3, j = 0; i < loop; i++) {
			CMenuItem mi = awayMenu.getItem(i);
			mi.setText(autoReplies.get(j++));
		}
		// 处理长度不一致的部分，如果自动回复减少了，则把多余的菜单项释放掉，如果少了，添加菜单项
		size -= count - 3;
		if(size > 0) { // 大于0说明自动回复增加了
			for(int i = 0; i < size; i++) {
				CMenuItem mi = new CMenuItem(awayMenu, SWT.RADIO);
				final int index = count - 3 + i;
				mi.setText(autoReplies.get(index));
				mi.addSelectionListener(new ISelectionListener() {
					public void widgetSelected(SelectionEvent e) {
					    main.getClient().getUser().setLoginMode(QQ.QQ_LOGIN_MODE_NORMAL);
					    if(main.checkLogin(false, false)) {
							main.getConfigHelper().setAutoReply(true);
							main.getConfigHelper().getReplies().setCurrentAutoReply(index);
							if(client.getUser().getStatus() != QQ.QQ_STATUS_AWAY) {
								main.getUIHelper().startStatusAnimation();
								client.status_Away();							        
							}
					    } else {						        
							main.getConfigHelper().setAutoReply(true);
							main.getConfigHelper().getReplies().setCurrentAutoReply(index);
							client.getUser().setStatus(QQ.QQ_STATUS_AWAY);
					    }
					}
				});
			}
		} else if (size < 0) { // 小于0说明自动回复减少了
			size = Math.abs(size);
			for(int i = size - 1; i >= 0 ; i--) {
				CMenuItem mi = awayMenu.getItem(loop + i);
				mi.dispose();
			}
		}
	}
	
	/**
	 * 隐藏所有菜单
	 * 没办法，在MagicLinux 1.2下测试的时候，菜单居然出来了之后无法消失
	 * 只好加了这么一个方法
	 */
	public void hideAllMenu() {
		clusterMenu.setVisible(false);
		dialogContainerMenu.setVisible(false);
		friendMenu.setVisible(false);
		groupMenu.setVisible(false);
		memberMenu.setVisible(false);
		organizationContainerMenu.setVisible(false);
		organizationMenu.setVisible(false);
		statusMenu.setVisible(false);
		subjectMenu.setVisible(false);
		sysMenu.setVisible(false);
	}
}
