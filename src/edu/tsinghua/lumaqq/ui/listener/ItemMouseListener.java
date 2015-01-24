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
package edu.tsinghua.lumaqq.ui.listener;

import edu.tsinghua.lumaqq.models.Cluster;
import edu.tsinghua.lumaqq.models.Dummy;
import edu.tsinghua.lumaqq.models.Group;
import edu.tsinghua.lumaqq.models.Model;
import edu.tsinghua.lumaqq.models.ModelRegistry;
import edu.tsinghua.lumaqq.models.User;
import edu.tsinghua.lumaqq.ui.MainShell;
import edu.tsinghua.lumaqq.ui.SMSWindow;
import edu.tsinghua.lumaqq.ui.config.user.UserInfoWindow;
import edu.tsinghua.lumaqq.ui.helper.MenuHelper;
import edu.tsinghua.lumaqq.widgets.qstyle.QItem;
import edu.tsinghua.lumaqq.widgets.qstyle.QTree;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;

/**
 * 视图的鼠标事件处理器
 * 
 * @author luma
 */
public class ItemMouseListener implements MouseListener {
	private MainShell main;
	
	/**
	 * 构造函数
	 * @param main
	 */
	public ItemMouseListener(MainShell main) {
		this.main = main;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.swt.events.MouseListener#mouseDoubleClick(org.eclipse.swt.events.MouseEvent)
	 */
	public void mouseDoubleClick(MouseEvent e) {
		// 如果当前提示窗口处于显示状态，隐藏之
		main.getTipHelper().closeFriendTipShell();
		// 得到源model，如果是好友，则开发好友消息窗口或者短消息窗口
		// 如果是群，则打开群消息窗口
		QTree tree = (QTree)e.getSource();
		QItem item = tree.getItemUnderMouse();
		if(item == null)
			return;
		Model model = (Model)item.getData();
		switch(model.type) {
			case USER:
				User u = (User)model;
				// 首先判断是否双击在手机图标上
				if(item.getMouseOnAttachment() == 3) {
					// 如果u是在最近联系人中，不使用这个model
					if(u.group.isLatest()) {
						u = ModelRegistry.getUser(u.qq);
						if(u == null)
							return;
					}
					SMSWindow window = main.getShellLauncher().openSMSWindow(u);
					window.setReceiver(String.valueOf(u.qq));
				} else if(u.isContactable()) {
					// 如果u是在最近联系人中，不使用这个model
					// 假如不做这个操作，那么当真正的model有消息时，动画
					// 将不会正常停止
					if(u.group.isLatest()) {
						u = ModelRegistry.getUser(u.qq);
						if(u == null)
							return;
					}
					// 是好友，是陌生人，是最近联系人，则打开消息窗口
					if(main.getMessageQueue().isNextTempSessionMessage(u.qq))
						main.getShellLauncher().openTempSessionIMWindow(u.qq);
					else
						main.getShellLauncher().openNormalIMWindow(u);
					main.getBlindHelper().refreshGroup(u.group);
				} else {
					// 如果是群内成员，缺省显示群名片页，否则显示缺省页
					if(u.group.isCluster()) {
						if(u.qq == main.getMyModel().qq)
							u.info = main.getMyModel().info;
						UserInfoWindow uiw = main.getShellLauncher().openUserInfoWindow(u, (u.qq == main.getMyModel().qq) ? UserInfoWindow.EDITABLE : UserInfoWindow.READ_ONLY);
						uiw.setCurrentPage(UserInfoWindow.CARD);
					} else
						main.getShellLauncher().openUserInfoWindow(u, UserInfoWindow.READ_ONLY);
				}
				break;
			case CLUSTER:
				Cluster c = ModelRegistry.getCluster(((Cluster)model).clusterId);
				if(c == null)
					return;
				else
					main.getShellLauncher().openClusterIMWindow(c);
				break;
			default:
				break;
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.events.MouseListener#mouseDown(org.eclipse.swt.events.MouseEvent)
	 */
	public void mouseDown(MouseEvent e) {
		main.getMenuHelper().hideAllMenu();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.events.MouseListener#mouseUp(org.eclipse.swt.events.MouseEvent)
	 */
	public void mouseUp(MouseEvent e) {
		// 如果当前提示窗口处于显示状态，隐藏之
		main.getTipHelper().closeFriendTipShell();
		// 如果是右键，弹出菜单
		if((e.stateMask & SWT.BUTTON3) != 0) {
			MenuHelper helper = main.getMenuHelper();
			QTree tree = (QTree)e.getSource();
			QItem item = tree.getItemUnderMouse();
			if(item == null || item.isMouseOnBlank()) {
				// item为null或者鼠标在item的空白区域，则显示组菜单
				Group g = main.getBlindHelper().getCurrentGroup();
				if(g == null)
					return;
				switch(g.groupType) {
					default:
						helper.setGroupMenuLocation(tree.toDisplay(e.x, e.y));
						helper.setGroupMenuData(g);
						helper.setGroupMenuVisible(true);
						break;
				}
				return;
			}
			
			// 判断model类型，显示不同菜单
			Model model = (Model)item.getData();
			switch(model.type) {
				case USER:
					User u = (User)model;
					if(u.group.isCluster()) {
						helper.setMemberMenuData(model);
						helper.setMemberMenuLocation(tree.toDisplay(e.x, e.y));
						helper.setMemberMenuVisible(true);
					} else {
						helper.setFriendMenuLocation(tree.toDisplay(e.x, e.y));
						helper.setFriendMenuData(model);
						helper.setFriendMenuVisible(true);						
					}
					break;
				case CLUSTER:
					Cluster c = ModelRegistry.getCluster(((Cluster)model).clusterId);
					if(c == null)
						return;
					switch(c.clusterType) {
						case DIALOG_CONTAINER:
							helper.setDialogContainerMenuData(c);
							helper.setDialogContainerMenuLocation(tree.toDisplay(e.x, e.y));
							helper.setDialogContainerMenuVisible(true);
							break;
						default:
							helper.setClusterMenuLocation(tree.toDisplay(e.x, e.y));
							helper.setClusterMenuData(c);
							helper.setClusterMenuVisible(true);
							break;
					}
					break;
				case ORGANIZATION:
					helper.setOrganizationMenuData(model);
					helper.setOrganizationMenuLocation(tree.toDisplay(e.x, e.y));
					helper.setOrganizationMenuVisible(true);
					break;
				case GROUP:
					helper.setGroupMenuLocation(tree.toDisplay(e.x, e.y));
					helper.setGroupMenuData(model);
					helper.setGroupMenuVisible(true);
					break;
				case DUMMY:
					Dummy dummy = (Dummy)model;
					switch(dummy.dummyType) {
						case CLUSTER_ORGANIZATION:
							helper.setOrganizationContainerMenuData(model);
							helper.setOrganizationContainerMenuLocation(tree.toDisplay(e.x, e.y));
							helper.setOrganizationContainerMenuVisible(true);
							break;
						case SUBJECTS:
							helper.setSubjectMenuData(model);
							helper.setSubjectMenuLocation(tree.toDisplay(e.x, e.y));
							helper.setSubjectMenuVisible(true);
							break;
					}
				default:
					break;
			}
		}
	}
}
