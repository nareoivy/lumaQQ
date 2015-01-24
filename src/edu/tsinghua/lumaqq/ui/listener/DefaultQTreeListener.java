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

import edu.tsinghua.lumaqq.ecore.remark.RemarkFactory;
import edu.tsinghua.lumaqq.models.Dummy;
import edu.tsinghua.lumaqq.models.DummyType;
import edu.tsinghua.lumaqq.models.Group;
import edu.tsinghua.lumaqq.models.Model;
import edu.tsinghua.lumaqq.models.ModelRegistry;
import edu.tsinghua.lumaqq.models.User;
import edu.tsinghua.lumaqq.qq.Util;
import edu.tsinghua.lumaqq.resource.Resources;
import edu.tsinghua.lumaqq.ui.MainShell;
import edu.tsinghua.lumaqq.ui.helper.OptionHelper;
import edu.tsinghua.lumaqq.widgets.qstyle.IQTreeListener;
import edu.tsinghua.lumaqq.widgets.qstyle.QTreeEvent;

/**
 * QTree的事件监听器
 * 
 * @author luma
 */
public class DefaultQTreeListener implements IQTreeListener {
	private MainShell main;
	
	public DefaultQTreeListener(MainShell main) {
		this.main = main;
	}
	
	/* (non-Javadoc)
	 * @see edu.tsinghua.lumaqq.widgets.qstyle.IQTreeListener#treeExpanded(edu.tsinghua.lumaqq.widgets.qstyle.QTreeEvent)
	 */
	public void treeExpanded(QTreeEvent e) {
		Resources res = Resources.getInstance();
		Model model = (Model)e.item.getData();
		model.expanded = true;
		switch(model.type) {
			case GROUP:
				e.item.setImage(res.getImage(Resources.icoExpanded));
				break;
			case ORGANIZATION:
				e.item.setPrefix(res.getImage(Resources.icoExpanded9));
				break;
			case DUMMY:
				e.item.setPrefix(res.getImage(Resources.icoExpanded9));
				/*
				 * 在QQ中，如果你点开群内组织，则群的成员信息和状态将会刷新，在LumaQQ中，
				 * 打开一个群窗口的时候这些信息会刷新，再多一个刷新手段，似乎没有什么必要，
				 * 手动刷新更好些，所以下面的代码被注释掉。如果需要和QQ一样的行为，取消掉
				 * 下面代码的注释即可。
				 * 这里只自动更新群名片
				 */
				Dummy dummy = (Dummy)model;
				if(dummy.dummyType == DummyType.CLUSTER_ORGANIZATION) {
					//main.getClient().getClusterInfo(dummy.cluster.clusterId);
					if(!dummy.cluster.cardUpdated)
						main.getClient().cluster_GetCardBatch(dummy.cluster.clusterId, 0);
				}
				break;
			default:
				break;
		}
	}

	/* (non-Javadoc)
	 * @see edu.tsinghua.lumaqq.widgets.qstyle.IQTreeListener#treeCollapsed(edu.tsinghua.lumaqq.widgets.qstyle.QTreeEvent)
	 */
	public void treeCollapsed(QTreeEvent e) {
		Resources res = Resources.getInstance();
		Model model = (Model)e.item.getData();
		model.expanded = false;
		switch(model.type) {
			case GROUP:
				e.item.setImage(res.getImage(Resources.icoCollapsed));
				break;
			case ORGANIZATION:
			case DUMMY:
				e.item.setPrefix(res.getImage(Resources.icoCollapsed9));
				break;
			default:
				break;
		}
	}

	/* (non-Javadoc)
	 * @see edu.tsinghua.lumaqq.widgets.qstyle.IQTreeListener#itemTextChanged(edu.tsinghua.lumaqq.widgets.qstyle.QTreeEvent)
	 */
	@SuppressWarnings("unchecked")
	public void itemTextChanged(QTreeEvent e) {
		Model model = (Model)e.item.getData();
		switch(model.type) {
			case GROUP:
				Group g = (Group)model;
				g.name = e.item.getText();
				main.setGroupDirty(true);
				main.getBlindHelper().refreshModel(g);
				break;
			case USER:
				User u = (User)model;
				g = u.group;
				if(g.isBlackList()) {					
					// 在黑名单中的编辑，发生在添加坏人时，如果输入的QQ号不对，返回
					int qq = Util.getInt(e.item.getText(), 0);
					if(qq == 0) {
						g.removeUser(u);
						main.getBlindHelper().refreshGroup(g);
						return;
					}
					// 如果这个人已经在黑名单中了，则操作也无效，返回
					if(g.hasUser(qq)) {
						g.removeDuplicate(u);
						main.getBlindHelper().refreshGroup(g);
						return;
					}
					
					u.qq = qq;
					u.nick = String.valueOf(qq);
					u.displayName = u.nick;
					ModelRegistry.addUser(u);
					main.getBlindHelper().refreshGroup(g);
					main.deleteFriend(u, false, true);
					main.getClient().user_GetInfo(qq);
				} else {
					// 如果新文本为空，不做修改
					if(e.item.getText().trim().equals("")) {
						e.item.setText(u.displayName);
						return;
					}					
					
					// 如果这个用户没有备注，创建一个
					if(u.remark == null) {
						u.remark = RemarkFactory.eINSTANCE.createRemark();
						u.remark.setName(e.item.getText());
						u.remark.setQq(u.qq);						
						main.getConfigHelper().getRemarks().getRemark().add(u.remark);
					} else
						u.remark.setName(e.item.getText());
					main.getConfigHelper().saveRemarks();
					
					// 如果当前是显示昵称状态，则切换到显示备注
					// 如果当前是显示备注状态，刷新这个用户即可
					OptionHelper options = main.getOptionHelper();
					if(options.isShowNick()) {
						options.setShowNick(false);
						main.getBlindHelper().setShowNick(false);
					}
					u.displayName = u.remark.getName();
					main.getBlindHelper().refreshAll();		
				}
				break;
			default:
				break;
		}
	}
}
