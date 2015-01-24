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

import static edu.tsinghua.lumaqq.models.ClusterType.*;
import static edu.tsinghua.lumaqq.resource.Messages.*;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSourceListener;
import org.eclipse.swt.dnd.DropTargetListener;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseTrackListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.MessageBox;

import edu.tsinghua.lumaqq.LumaQQ;
import edu.tsinghua.lumaqq.MessageQueue;
import edu.tsinghua.lumaqq.ecore.group.GroupFactory;
import edu.tsinghua.lumaqq.ecore.group.XCluster;
import edu.tsinghua.lumaqq.ecore.group.XGroup;
import edu.tsinghua.lumaqq.ecore.group.XGroups;
import edu.tsinghua.lumaqq.ecore.group.XOrganization;
import edu.tsinghua.lumaqq.ecore.group.XUser;
import edu.tsinghua.lumaqq.ecore.remark.Remark;
import edu.tsinghua.lumaqq.eutil.GroupUtil;
import edu.tsinghua.lumaqq.models.Cluster;
import edu.tsinghua.lumaqq.models.ClusterType;
import edu.tsinghua.lumaqq.models.Dummy;
import edu.tsinghua.lumaqq.models.DummyType;
import edu.tsinghua.lumaqq.models.Group;
import edu.tsinghua.lumaqq.models.GroupType;
import edu.tsinghua.lumaqq.models.Model;
import edu.tsinghua.lumaqq.models.ModelRegistry;
import edu.tsinghua.lumaqq.models.ModelUtils;
import edu.tsinghua.lumaqq.models.Organization;
import edu.tsinghua.lumaqq.models.Status;
import edu.tsinghua.lumaqq.models.User;
import edu.tsinghua.lumaqq.qq.beans.DownloadFriendEntry;
import edu.tsinghua.lumaqq.resource.Resources;
import edu.tsinghua.lumaqq.ui.MainShell;
import edu.tsinghua.lumaqq.ui.ReceiveSystemMessageShell;
import edu.tsinghua.lumaqq.ui.dialogs.SelectGroupDialog;
import edu.tsinghua.lumaqq.ui.listener.ClusterDragSourceListener;
import edu.tsinghua.lumaqq.ui.listener.ClusterDropTargetListener;
import edu.tsinghua.lumaqq.ui.listener.DefaultQTreeListener;
import edu.tsinghua.lumaqq.ui.listener.GroupDropTargetListener;
import edu.tsinghua.lumaqq.ui.listener.GroupNameChangedListener;
import edu.tsinghua.lumaqq.ui.listener.ItemDragSourceListener;
import edu.tsinghua.lumaqq.ui.listener.ItemMouseListener;
import edu.tsinghua.lumaqq.ui.listener.ItemMouseTrackListener;
import edu.tsinghua.lumaqq.ui.provider.FriendTreeModeContentProvider;
import edu.tsinghua.lumaqq.ui.provider.GroupContentProvider;
import edu.tsinghua.lumaqq.ui.provider.ModelLabelProvider;
import edu.tsinghua.lumaqq.ui.sorter.LatestSorter;
import edu.tsinghua.lumaqq.ui.sorter.ModelSorter;
import edu.tsinghua.lumaqq.widgets.qstyle.Animation;
import edu.tsinghua.lumaqq.widgets.qstyle.Blind;
import edu.tsinghua.lumaqq.widgets.qstyle.IFilter;
import edu.tsinghua.lumaqq.widgets.qstyle.IQTreeListener;
import edu.tsinghua.lumaqq.widgets.qstyle.ISlatLabelProvider;
import edu.tsinghua.lumaqq.widgets.qstyle.ISlatListener;
import edu.tsinghua.lumaqq.widgets.qstyle.ItemLayout;
import edu.tsinghua.lumaqq.widgets.qstyle.QTreeViewer;
import edu.tsinghua.lumaqq.widgets.qstyle.Slat;

/**
 * 管理Blind控件的显示模式
 * 
 * @author luma
 */
public class BlindHelper {
	private MainShell main;
	
	// 所有的缺省组和用户自定义组
	private Group latestGroup;
	private Group clusterGroup;
	private Group myFriendGroup;
	private Group strangerGroup;
	private Group blacklistGroup;
	private List<Group> normalGroups;
	
	// QTreeViewer注册表
	private Map<Group, QTreeViewer<Model>> viewers;
	
	private IFilter<Model> onlineFilter;
	
	private Map<Integer, Group> addTo;

	public BlindHelper(MainShell main) {
		this.main = main;
		normalGroups = new ArrayList<Group>();
		viewers = new HashMap<Group, QTreeViewer<Model>>();
		onlineFilter = new OnlineFilter();
		addTo = new HashMap<Integer, Group>();
	}
	
	/**
	 * @return
	 * 		当前组对象，没有返回null
	 */
	public Group getCurrentGroup() {
		Control control = main.getBlind().getCurrentSlatControl();	
		for(Group g : viewers.keySet()) {
			if(viewers.get(g).getQTree() == control) {
				if(isTreeMode()) {
					// 如果是树形模式，且当前在好友组中，判断当前选择了哪个组
					QTreeViewer<Model> viewer = viewers.get(myFriendGroup); 
					if(viewer.getQTree() == control)
						return myFriendGroup;
					else
						return g;
				} else
					return g;
			}
		}
		return null;
	}

	/**
	 * 设置视图背景颜色
	 * 
	 * @param bg
	 * 		Color
	 */
	public void setBackground(Color bg) {
		for(QTreeViewer<Model> viewer : viewers.values()) {
			viewer.getQTree().setBackground(bg);
		}
	}
	
	/**
	 * 设置是否使用小头像
	 * 
	 * @param b
	 * 		true表示使用小头像
	 */
	public void setShowSmallHead(boolean b) {
		ItemLayout layout = b ? ItemLayout.HORIZONTAL : ItemLayout.VERTICAL;
		// 改变好友组
		if(isTreeMode()) {
			QTreeViewer<Model> viewer = viewers.get(myFriendGroup);
			changeViewerLayout(viewer, 1, layout);
		} else {
			QTreeViewer<Model> viewer = viewers.get(myFriendGroup);
			changeViewerLayout(viewer, 0, layout);
			
			for(Group g : normalGroups) {
				viewer = viewers.get(g);
				changeViewerLayout(viewer, 0, layout);
			}
			
			changeViewerLayout(viewers.get(strangerGroup), 0, layout);
			changeViewerLayout(viewers.get(blacklistGroup), 0, layout);
		}
		
		// 改变最近联系人组
		changeViewerLayout(viewers.get(latestGroup), 0, layout);
	}
	
	/**
	 * 改变viewer的布局
	 * 
	 * @param viewer
	 * @param level
	 * @param layout
	 */
	private void changeViewerLayout(QTreeViewer<Model> viewer, int level, ItemLayout layout) {
		switch(layout) {
			case HORIZONTAL:
				viewer.getQTree().setLevelImageSize(level, 20);
				viewer.getQTree().setLevelIndent(16);
				viewer.getQTree().setLevelLayout(level, layout);
				break;
			case VERTICAL:
				viewer.getQTree().setLevelImageSize(level, 40);
				viewer.getQTree().setLevelIndent(0);
				viewer.getQTree().setLevelLayout(level, layout);
				break;
			default:
				SWT.error(SWT.ERROR_INVALID_RANGE);
				break;
		}
	}
	
	/**
	 * 得到model所在QTreeViewer对象
	 * 
	 * @param model
	 * 		model对象
	 * @return
	 * 		包含这个model的QTreeViewer
	 */
	public QTreeViewer<Model> getViewer(Model model) {
		if(model == null)
			return null;
		switch(model.type) {
			case USER:
				return viewers.get(((User)model).group);
			case CLUSTER:
			case DUMMY:
				return viewers.get(clusterGroup);
			case GROUP:
				return viewers.get(model);
			default:
				SWT.error(SWT.ERROR_INVALID_RANGE);
				return null;
		}
	}
	
	/**
	 * @param model
	 * @return
	 * 		true表示这个item有一个动画在进行
	 */
	public boolean hasAnimation(Model model) {
		QTreeViewer<Model> viewer = getViewer(model);
		if(viewer == null)
			return false;
		return viewer.hasAnimation(model);
	}
	
	/**
	 * 闪烁文字
	 * 
	 * @param m
	 */
	public void startBlinkText(Model m) {
		QTreeViewer<Model> viewer = getViewer(m);
		if(viewer == null)
			return;
		if(!viewer.hasAnimation(m))
			viewer.startAnimation(m, Animation.TEXT_BLINK);
	}
	
	/**
	 * 停止文本闪烁
	 * 
	 * @param m
	 */
	public void stopBlinkText(Model m) {
		QTreeViewer<Model> viewer = getViewer(m);
		if(viewer == null)
			return;
		if(viewer.hasAnimation(m))
			viewer.stopAnimation(m);
	}
	
	/**
	 * 开始跳动一个图标
	 * 
	 * @param model
	 */
	public void startBounceImage(Model model) {
		QTreeViewer<Model> viewer = getViewer(model);
		if(viewer == null)
			return;
		if(!viewer.hasAnimation(model))
			viewer.startAnimation(model, Animation.ICON_BOUNCE);
	}
	
	/**
	 * 开始在一个组上闪烁图标
	 * 
	 * @param g
	 * @param img
	 */
	public void startBlinkGroupImage(Group g, Image img) {
		QTreeViewer<Model> viewer = viewers.get(g);
		if(viewer == null)
			return;
		Blind blind = main.getBlind();
		int index = blind.indexOf(viewer.getQTree());
		if(!blind.getSlat(index).isBlinking())
			blind.startBlink(index, img);
	}
	
	/**
	 * 停止在一个组上闪烁
	 * 
	 * @param g
	 */
	public void stopBlinkGroupImage(Group g) {
		QTreeViewer<Model> viewer = viewers.get(g);
		if(viewer == null)
			return;
		Blind blind = main.getBlind();
		int index = blind.indexOf(viewer.getQTree());
		if(blind.getSlat(index).isBlinking())
			blind.stopBlink(index);
	}
	
	/**
	 * 给定一个控件或者一个slat，得到对应的Group对象，如果当前处于树形模式，且
	 * 这个control正好是好友树，则返回我的好友组
	 * 
	 * @param c
	 * 		Control或者slat
	 * @return
	 * 		Group对象，没有返回null
	 */
	public Group getSlatGroup(Control c) {
		Blind blind = main.getBlind();
		int index = blind.indexOf(c);
		Control slatControl = blind.getSlatControl(index);
		for(Map.Entry<Group, QTreeViewer<Model>> entry : viewers.entrySet()) {
			if(entry.getValue().getQTree() == slatControl) {
				if(isTreeMode()) {
					if(viewers.get(myFriendGroup).getQTree() == slatControl)
						return myFriendGroup;
					else
						return entry.getKey();
				} else
					return entry.getKey();
			}
		}
		return null;
	}
	
	/**
	 * 刷新某个组
	 * 
	 * @param g
	 */
	public void refreshGroup(Group g) {
		QTreeViewer<Model> viewer = viewers.get(g);
		if(viewer != null) {
			viewer.refresh();
			main.getBlind().refreshSlat(viewer.getQTree());
		}
	}
	
	/**
	 * 初始化传统模式
	 */
	private void initTraditionalMode() {
		Blind blind = main.getBlind();
		blind.setLabelProvider(new ISlatLabelProvider() {
			public String getText(Control slatControl) {
				for(Group g : viewers.keySet()) {
					QTreeViewer<Model> viewer = viewers.get(g);
					if(viewer.getQTree() == slatControl) 
						return getGroupText(g);
				}
				return "";
			}
		});
		
		MouseListener itemMouseListener = new ItemMouseListener(main);
		MouseTrackListener itemMouseTrackListener = new ItemMouseTrackListener(main);
		IQTreeListener qtreeListener = new DefaultQTreeListener(main);
		DropTargetListener groupDropTargetListener = new GroupDropTargetListener(main);
		DragSourceListener itemDragSourceListener = new ItemDragSourceListener();		
		Transfer[] dummyTransfer = new Transfer[] { TextTransfer.getInstance() };
		ISlatListener slatListener = new GroupNameChangedListener(main);
		
		// 我的好友组
		QTreeViewer<Model> viewer = new QTreeViewer<Model>(blind);
		viewer.setContentProvider(new GroupContentProvider(myFriendGroup));
		viewer.setLabelProvider(ModelLabelProvider.INSTANCE);
		viewer.setSorter(ModelSorter.INSTANCE);
		viewer.setInput(this);
		viewer.addDragSupport(DND.DROP_MOVE, dummyTransfer, itemDragSourceListener);
		viewer.getQTree().addMouseListener(itemMouseListener);
		viewer.getQTree().addMouseTrackListener(itemMouseTrackListener);
		viewer.getQTree().addQTreeListener(qtreeListener);
		viewers.put(myFriendGroup, viewer);
		blind.addSlat(viewer.getQTree());
		blind.addSlatDropSupport(viewer.getQTree(), DND.DROP_MOVE, dummyTransfer, groupDropTargetListener);
		
		// 自定义组
		for(Group g : normalGroups) {
			viewer = new QTreeViewer<Model>(blind);
			viewer.setContentProvider(new GroupContentProvider(g));
			viewer.setLabelProvider(ModelLabelProvider.INSTANCE);
			viewer.setSorter(ModelSorter.INSTANCE);
			viewer.setInput(this);
			viewer.addDragSupport(DND.DROP_MOVE, dummyTransfer, itemDragSourceListener);
			viewer.getQTree().addMouseListener(itemMouseListener);
			viewer.getQTree().addMouseTrackListener(itemMouseTrackListener);
			viewer.getQTree().addQTreeListener(qtreeListener);
			viewers.put(g, viewer);
			blind.addSlat(viewer.getQTree()).addSlatListener(slatListener);
			blind.addSlatDropSupport(viewer.getQTree(), DND.DROP_MOVE, dummyTransfer, groupDropTargetListener);
		}
		
		// 陌生人组
		viewer = new QTreeViewer<Model>(blind);
		viewer.setContentProvider(new GroupContentProvider(strangerGroup));
		viewer.setLabelProvider(ModelLabelProvider.INSTANCE);
		viewer.setSorter(ModelSorter.INSTANCE);
		viewer.setInput(this);
		viewer.addDragSupport(DND.DROP_MOVE, dummyTransfer, itemDragSourceListener);
		viewer.getQTree().addMouseListener(itemMouseListener);
		viewer.getQTree().addMouseTrackListener(itemMouseTrackListener);
		viewer.getQTree().addQTreeListener(qtreeListener);
		viewers.put(strangerGroup, viewer);
		blind.addSlat(viewer.getQTree());
		blind.addSlatDropSupport(viewer.getQTree(), DND.DROP_MOVE, dummyTransfer, groupDropTargetListener);
		
		// 黑名单组
		viewer = new QTreeViewer<Model>(blind);
		viewer.setContentProvider(new GroupContentProvider(blacklistGroup));
		viewer.setLabelProvider(ModelLabelProvider.INSTANCE);
		viewer.setSorter(ModelSorter.INSTANCE);
		viewer.setInput(this);
		viewer.addDragSupport(DND.DROP_MOVE, dummyTransfer, itemDragSourceListener);
		viewer.getQTree().addMouseListener(itemMouseListener);
		viewer.getQTree().addMouseTrackListener(itemMouseTrackListener);
		viewer.getQTree().addQTreeListener(qtreeListener);
		viewers.put(blacklistGroup, viewer);
		blind.addSlat(viewer.getQTree());
		blind.addSlatDropSupport(viewer.getQTree(), DND.DROP_MOVE, dummyTransfer, groupDropTargetListener);
		
		// 群组
		viewer = new QTreeViewer<Model>(blind);
		viewer.setContentProvider(new GroupContentProvider(clusterGroup));
		viewer.setLabelProvider(ModelLabelProvider.INSTANCE);
		viewer.setSorter(ModelSorter.INSTANCE);
		viewer.setInput(this);
		viewer.addDragSupport(DND.DROP_MOVE, dummyTransfer, new ClusterDragSourceListener());
		viewer.addDropSupport(DND.DROP_MOVE, dummyTransfer, new ClusterDropTargetListener(main));
		viewer.getQTree().addMouseListener(itemMouseListener);
		viewer.getQTree().addMouseTrackListener(itemMouseTrackListener);
		viewer.getQTree().addQTreeListener(qtreeListener);
		viewers.put(clusterGroup, viewer);
		blind.addSlat(viewer.getQTree());
		
		// 最近联系人组
		viewer = new QTreeViewer<Model>(blind);
		viewer.setContentProvider(new GroupContentProvider(latestGroup));
		viewer.setLabelProvider(ModelLabelProvider.INSTANCE);
		viewer.setSorter(LatestSorter.INSTANCE);
		viewer.setInput(this);
		viewer.addDragSupport(DND.DROP_MOVE, dummyTransfer, itemDragSourceListener);
		viewer.getQTree().addMouseListener(itemMouseListener);
		viewer.getQTree().addMouseTrackListener(itemMouseTrackListener);
		viewer.getQTree().addQTreeListener(qtreeListener);
		viewers.put(latestGroup, viewer);
		blind.addSlat(viewer.getQTree());
	}
	
	/**
	 * 得到一个组的显示文本，主要是要添加上一些统计数据
	 * 
	 * @param g
	 * 		Group
	 * @return
	 * 		显示文本
	 */
	private String getGroupText(Group g) {
		switch(g.groupType) {
			case CLUSTER_GROUP:
				return g.name;
			case LATEST_GROUP:
				return g.name + ' ' + '(' + g.getOnlineUserCount() + '/' + (g.users.size() + g.clusters.size()) + ')';
			default:
				return g.name + ' ' + '(' + g.getOnlineUserCount() + '/' + g.users.size() + ')';
		}
	}

	/**
	 * 初始化树形模式
	 */
	private void initTreeMode() {
		Blind blind = main.getBlind();
		blind.setLabelProvider(new ISlatLabelProvider() {
			public String getText(Control slatControl) {
				for(Group g : viewers.keySet()) {
					QTreeViewer<Model> viewer = viewers.get(g);
					if(viewer.getQTree() == slatControl) {
						QTreeViewer<Model> temp = viewers.get(myFriendGroup);
						if(temp == viewer)
							return myFriendGroup.name;
						else
							return getGroupText(g);
					}
				}
				return "";
			}
		});
		
		MouseListener slatMouseListener = new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				/*
				 * 点击我的好友组时，收起或者恢复展开状态，但是如果
				 * 我的好友组不是当前组，则不应该执行以下代码
				 */
				if(main.getBlind().getPreviousSlat() == 0) {
					QTreeViewer<Model> viewer = getViewer(myFriendGroup);
					if(viewer.isAllRootCollapsed())
						viewer.restoreExpandStatus();
					else {
						viewer.saveExpandStatus();
						viewer.getQTree().goTop();
						viewer.collapseAll();					
					}					
				}
			}
		};
		MouseListener itemMouseListener = new ItemMouseListener(main);
		MouseTrackListener itemMouseTrackListener = new ItemMouseTrackListener(main);
		DropTargetListener groupDropTargetListener = new GroupDropTargetListener(main);
		DragSourceListener itemDragSourceListener = new ItemDragSourceListener();
		IQTreeListener qtreeListener = new DefaultQTreeListener(main);
		Transfer[] dummyTransfer = new Transfer[] { TextTransfer.getInstance() };
		
		// 好友组，陌生人组，黑名单组
		QTreeViewer<Model> viewer = new QTreeViewer<Model>(blind);
		viewer.setContentProvider(new FriendTreeModeContentProvider(main));
		viewer.setLabelProvider(ModelLabelProvider.INSTANCE);
		viewer.setSorter(ModelSorter.INSTANCE);
		viewer.setInput(this);
		viewer.addDragSupport(DND.DROP_MOVE, dummyTransfer, itemDragSourceListener);
		viewer.addDropSupport(DND.DROP_MOVE, dummyTransfer, groupDropTargetListener);
		viewer.getQTree().addMouseListener(itemMouseListener);
		viewer.getQTree().addMouseTrackListener(itemMouseTrackListener);
		viewer.getQTree().addQTreeListener(qtreeListener);
		viewers.put(myFriendGroup, viewer);
		viewers.put(blacklistGroup, viewer);
		viewers.put(strangerGroup, viewer);
		for(Group g : normalGroups)
			viewers.put(g, viewer);
		blind.addSlat(viewer.getQTree());
		blind.addSlatDropSupport(viewer.getQTree(), DND.DROP_MOVE, dummyTransfer, groupDropTargetListener);
		blind.getSlat(0).addMouseListener(slatMouseListener);
		
		// 群组
		viewer = new QTreeViewer<Model>(blind);
		viewer.setContentProvider(new GroupContentProvider(clusterGroup));
		viewer.setLabelProvider(ModelLabelProvider.INSTANCE);
		viewer.setSorter(ModelSorter.INSTANCE);
		viewer.setInput(this);
		viewer.addDragSupport(DND.DROP_MOVE, dummyTransfer, new ClusterDragSourceListener());
		viewer.addDropSupport(DND.DROP_MOVE, dummyTransfer, new ClusterDropTargetListener(main));
		viewer.getQTree().addMouseListener(itemMouseListener);
		viewer.getQTree().addMouseTrackListener(itemMouseTrackListener);
		viewer.getQTree().addQTreeListener(qtreeListener);
		viewers.put(clusterGroup, viewer);
		blind.addSlat(viewer.getQTree());
		
		// 最近联系人组
		viewer = new QTreeViewer<Model>(blind);
		viewer.setContentProvider(new GroupContentProvider(latestGroup));
		viewer.setLabelProvider(ModelLabelProvider.INSTANCE);
		viewer.setSorter(LatestSorter.INSTANCE);
		viewer.setInput(this);
		viewer.addDragSupport(DND.DROP_MOVE, dummyTransfer, itemDragSourceListener);
		viewer.getQTree().addMouseListener(itemMouseListener);
		viewer.getQTree().addMouseTrackListener(itemMouseTrackListener);
		viewers.put(latestGroup, viewer);
		blind.addSlat(viewer.getQTree());
	}	
	
	/**
	 * 初始化model
	 */
	@SuppressWarnings("unchecked")
	public void initModels() {
		main.getBlind().removeAll();
		viewers.clear();
		ModelRegistry.clearAll();
		normalGroups.clear();
		// 初始化，载入分组文件
		ConfigHelper configHelper = main.getConfigHelper();
		File groupFile = new File(LumaQQ.GROUPS);
		if(!configHelper.checkGroupFile(groupFile))
			return;
		XGroups groups = GroupUtil.load(groupFile);
		if(groups == null)
			return;
		
		Group g = null;
		
		for(XGroup group : (List<XGroup>)groups.getGroup()) {
			// 创建Group
			GroupType groupType = GroupType.valueOf(group.getType()); 
			switch(groupType) {
				case BLACKLIST_GROUP:
					blacklistGroup = ModelUtils.createGroup(group);
					g = blacklistGroup;
					break;
				case CLUSTER_GROUP:
					clusterGroup = ModelUtils.createGroup(group);
					g = clusterGroup;
					break;
				case DEFAULT_FRIEND_GROUP:
					myFriendGroup = ModelUtils.createGroup(group);
					g = myFriendGroup;
					break;
				case FRIEND_GROUP:
					g = ModelUtils.createGroup(group);
					normalGroups.add(g);
					break;
				case LATEST_GROUP:
					latestGroup = ModelUtils.createGroup(group);
					g = latestGroup;
					break;
				case STRANGER_GROUP:
					strangerGroup = ModelUtils.createGroup(group);
					g = strangerGroup;
					break;
			}
			
			if(g == null)
				continue;
			
			g.groupType = groupType;
			
			// 创建User或者Cluster
			if(g == clusterGroup) {
				// 添加一个多人对话容器
				Cluster dialogContainer = new Cluster();				
				dialogContainer.name = cluster_dialogs;
				dialogContainer.clusterType = ClusterType.DIALOG_CONTAINER;
				g.addCluster(dialogContainer);
				ModelRegistry.addCluster(dialogContainer);
				
				// 添加其他群
				List<Cluster> subClusters = new ArrayList<Cluster>();				
				for(XCluster cluster : (List<XCluster>)group.getCluster()) {
					Cluster c = ModelUtils.createCluster(cluster);
					g.addCluster(c);
					ModelRegistry.addCluster(c);
					
					// 创建Cluster中的member
					for(XUser user : (List<XUser>)cluster.getUser()) {
						if(user.getQq() == 0)
							continue;
						User u = ModelUtils.createUser(user);
					    u.remark = main.getConfigHelper().getRemark(u.qq);
						if(u.hasCardName())
							u.displayName = u.cardName;
						c.addMember(u);
					}
					
					if(c.isPermanent()) {
						// 创建两个dummy，一个是群内组织，一个是讨论组
						Dummy orgDummy = new Dummy();
						orgDummy.dummyType = DummyType.CLUSTER_ORGANIZATION;
						orgDummy.name = cluster_organization;
						c.addDummy(orgDummy);
						Dummy subDummy = new Dummy();
						subDummy.dummyType = DummyType.SUBJECTS;
						subDummy.name = cluster_subject;
						c.addDummy(subDummy);
					} else {
						subClusters.add(c);
					}
					
					// 添加群中的组织
					for(XOrganization org : (List<XOrganization>)cluster.getOrganization()) {
						Organization o = ModelUtils.createOrganization(org);
						c.addOrganization(o);
					}
				}
				
				// 建立父子群关系，如果父群是null，则为多人对话
				for(Cluster c : subClusters) {
					Cluster parent = c.getParentCluster();
					if(parent == null)
						dialogContainer.addSubCluster(c);
					else
						parent.addSubCluster(c);
				}
			} else if(g == latestGroup) {
				for(XUser user : (List<XUser>)group.getUser()) {
					if(user.getQq() == 0)
						continue;
					User u = ModelUtils.createUser(user);
					u.remark = main.getConfigHelper().getRemark(u.qq);
					g.addUser(u);
				}
				for(XCluster cluster : (List<XCluster>)group.getCluster()) {
					Cluster c = ModelUtils.createCluster(cluster);
					g.addCluster(c);
				}
			} else {
				// 创建用户
				for(XUser user : (List<XUser>)group.getUser()) {
					User u = ModelUtils.createUser(user);
					u.remark = main.getConfigHelper().getRemark(u.qq);
					g.addUser(u);
				}
			}
		}
	}

	/**
	 * 重设blind显示模式 
	 */
	private void resetMode() {
		main.getBlind().removeAll();
		viewers.clear();
		OptionHelper options = main.getOptionHelper();
		setShowSignature(options.isShowSignature());
		setShowCustomHead(options.isShowCustomHead());
		if(isTreeMode())
			initTreeMode();
		else
			initTraditionalMode();
		
		setShowOnlineOnly(options.isShowOnlineOnly());
		setShowNick(options.isShowNick());
		setShowSmallHead(options.isShowSmallHead());
		setBackground(main.getGroupColor());
		setLatestGroupVisible(options.isEnableLatest());
		setBlacklistGroupVisible(options.isShowBlacklist());
	}

	/**
	 * 修改当前slat的文本 
	 */
	public void editCurrentSlatText() {
		Blind blind = main.getBlind();
		Slat slat = blind.getCurrentSlat();
		if(slat != null)
			slat.editText();
	}
	
	/**
	 * @return
	 * 		所有好友组列表
	 */
	public List<Group> getFriendGroupList() {
		List<Group> ret = new ArrayList<Group>();
		ret.add(myFriendGroup);
		ret.addAll(normalGroups);
		return ret;
	}
	
	/**
	 * @return
	 * 		所有用户组列表，也就是除了群组之外的所有组
	 */
	public List<Group> getUserGroupList() {
		List<Group> ret = new ArrayList<Group>();
		ret.add(myFriendGroup);
		ret.addAll(normalGroups);
		ret.add(strangerGroup);
		ret.add(blacklistGroup);
		return ret;
	}
	
	/**
	 * @return
	 * 		能够接收短消息的组
	 */
	public List<Group> getSMSReceivableGroupList() {
		List<Group> ret = new ArrayList<Group>();
		ret.add(myFriendGroup);
		ret.addAll(normalGroups);
		ret.add(strangerGroup);
		ret.add(blacklistGroup);
		return ret;
	}
	
	/**
	 * @return
	 * 		所有需要导出记录的组列表
	 */
	public List<Group> getExportGroupList() {
		List<Group> ret = new ArrayList<Group>();
		ret.add(myFriendGroup);
		ret.addAll(normalGroups);
		ret.add(strangerGroup);
		ret.add(clusterGroup);
		return ret;
	}
	
	/**
	 * @return Returns the treeMode.
	 */
	public boolean isTreeMode() {
		return main.getOptionHelper().isTreeMode();
	}

	/**
	 * @param treeMode The treeMode to set.
	 */
	public void setTreeMode(boolean treeMode) {
		resetMode();
	}

	/**
	 * @return Returns the myFriendGroup.
	 */
	public Group getMyFriendGroup() {
		return myFriendGroup;
	}

	/**
	 * @return Returns the clusterGroup.
	 */
	public Group getClusterGroup() {
		return clusterGroup;
	}

	/**
	 * @return Returns the latestGroup.
	 */
	public Group getLatestGroup() {
		return latestGroup;
	}

	/**
	 * @return Returns the normalGroups.
	 */
	public List<Group> getNormalGroups() {
		return normalGroups;
	}

	/**
	 * @return Returns the blacklistGroup.
	 */
	public Group getBlacklistGroup() {
		return blacklistGroup;
	}

	/**
	 * @return Returns the strangerGroup.
	 */
	public Group getStrangerGroup() {
		return strangerGroup;
	}
	
	/**
	 * 添加一个XGroup元素
	 * 
	 * @param groups
	 * 		XGroups
	 * @param g
	 * 		Group
	 */
	@SuppressWarnings("unchecked")
	private void addXGroup(XGroups groups, Group g) {
		XGroup group = ModelUtils.createXGroup(g);
		groups.getGroup().add(group);
		switch(g.groupType) {
			case CLUSTER_GROUP:
				for(Cluster c : g.clusters) {				
					// 不需要保存多人对话容器
					if(c.clusterType != DIALOG_CONTAINER) {
						XCluster cluster = ModelUtils.createXCluster(c);
						group.getCluster().add(cluster);
						
						for(User u : c.members.values()) {
							XUser user = ModelUtils.createXUser(u);
							cluster.getUser().add(user);
						}					
						
						for(Organization o : c.organizations.values()) {
							XOrganization org = ModelUtils.createXOrganization(o);
							cluster.getOrganization().add(org);
						}
					}
				}
				break;
			case LATEST_GROUP:
				for(User u : g.users) {
					XUser user = ModelUtils.createXUser(u);
					group.getUser().add(user);
				}
				for(Cluster cluster : g.clusters) {
					XCluster c = ModelUtils.createXCluster(cluster);
					group.getCluster().add(c);
				}
				break;
			case BLACKLIST_GROUP:
			case DEFAULT_FRIEND_GROUP:
			case FRIEND_GROUP:
			case STRANGER_GROUP:
				for(User u : g.users) {
					XUser user = ModelUtils.createXUser(u);
					group.getUser().add(user);
				}
				break;
			default:
				SWT.error(SWT.ERROR_INVALID_RANGE);
				break;
		}
	}

	/**
	 * 保存分组信息
	 */
	public void saveModel() {
		if(myFriendGroup == null)
			return;
		
		XGroups groups = GroupFactory.eINSTANCE.createXGroups();
		
		addXGroup(groups, myFriendGroup);
		for(Group g : normalGroups)
			addXGroup(groups, g);
		addXGroup(groups, strangerGroup);
		addXGroup(groups, blacklistGroup);
		addXGroup(groups, clusterGroup);
		addXGroup(groups, latestGroup);
		
		// 写入到文件
		GroupUtil.save(new File(LumaQQ.GROUPS), groups);
	}
	
	/**
	 * 刷新所有视图
	 */
	public void refreshAll() {
		for(QTreeViewer<Model> viewer : viewers.values()) {
			viewer.refresh();
			main.getBlind().refreshSlat(viewer.getQTree());
		}
	}
	
	/**
	 * 把一个组中的用户存到哈希表中
	 * 
	 * @param hash
	 * @param g
	 */
	private void hashFriend(Map<Integer, User> hash, Group g) {
		for(User u : g.users)
			hash.put(u.qq, u);
	}
	
	/**
	 * @return
	 * 		一个包含了所有好友的哈希表
	 */
	public Map<Integer, User> getFriendMap() {
		Map<Integer, User> ret = new HashMap<Integer, User>();
		hashFriend(ret, myFriendGroup);
		for(Group g : normalGroups)
			hashFriend(ret, g);
		return ret;
	}
	
	/**
	 * 添加一个用户到某种组中。如果是添加到群组，则不操作。如果是自定义的
	 * 好友组，不操作。如果是最近联系人组，则添加一个link
	 * 
	 * @param user
	 * 		User对象
	 * @param type
	 * 		组类型
	 */
	public void addUser(User user, GroupType type) {
		User u = user;
		Group g = null;
		switch(type) {
			case BLACKLIST_GROUP:
				g = blacklistGroup;
				break;
			case DEFAULT_FRIEND_GROUP:
				g = myFriendGroup;
				break;
			case LATEST_GROUP:
				g = latestGroup;
				break;
			case STRANGER_GROUP:
				g = strangerGroup;
				break;
			case CLUSTER_GROUP:
			case FRIEND_GROUP:
				return;
			default:
				SWT.error(SWT.ERROR_INVALID_RANGE);
				return;
		}
		g.addUser(u);
	}
	
	/**
	 * @return
	 * 		需要上传的组的名称列表
	 */
	public List<String> getUploadGroupNameList() {
		List<String> ret = new ArrayList<String>();
		for(Group g : normalGroups)
			ret.add(g.name);
		return ret;
	}
	
	/**
	 * 根据索引得到组对象，默认的顺序是，好友是0，然后是自定义组，然后是陌生人，黑名单，
	 * 手机好友，群，最后是最近联系人。一般不应该用这个来的到组对象，因为自定义组个数
	 * 未知。
	 * 
	 * @param index
	 * 		索引
	 * @return
	 * 		Group对象, 如果索引超出范围，返回null
	 */
	public Group getGroup(int index) {
		if(index < 0)
			return null;
		
		if(index == 0)
			return myFriendGroup;		
		
		index--;
		if(index < normalGroups.size())
			return normalGroups.get(index);
		
		index -= normalGroups.size();
		switch(index) {
			case 0:
				return strangerGroup;
			case 1:
				return blacklistGroup;
			case 2:
				return clusterGroup;
			case 3:
				return latestGroup;
			default:
				return null;
		}
	}
	
	/**
	 * 得到好友组，第0个好友组是我的好友组
	 * 
	 * @param index
	 * 		好友组索引
	 * @return
	 * 		Group
	 */
	public Group getFriendGroup(int index) {
		if(index < 0)
			return null;
		if(index == 0)
			return myFriendGroup;
		return normalGroups.get(index - 1);
	}
	
	/**
	 * @return
	 * 		需要上传的组数目
	 */
	public int getUploadGroupCount() {
		return normalGroups.size() + 1;
	}
	
	/**
	 * @return
	 * 		返回组总数
	 */
	public int getGroupCount() {
		return normalGroups.size() + 6;
	}
	
	/**
	 * 设置最近联系人组的可见性
	 * 
	 * @param b
	 * 		true表示可见
	 */
	public void setLatestGroupVisible(boolean b) {
		Blind blind = main.getBlind();
		QTreeViewer<Model> viewer = viewers.get(latestGroup);
		if(b)
			blind.showSlat(viewer.getQTree());
		else
			blind.hideSlat(viewer.getQTree());
	}
	
	/**
	 * 设置黑名单可见性
	 * 
	 * @param b
	 * 		true表示可见
	 */
	public void setBlacklistGroupVisible(boolean b) {
		Blind blind = main.getBlind();
		QTreeViewer<Model> viewer = viewers.get(blacklistGroup);
		if(isTreeMode()) 
			viewers.get(myFriendGroup).refresh();
		else {
			if(b)
				blind.showSlat(viewer.getQTree());
			else
				blind.hideSlat(viewer.getQTree());			
		}
	}
	
	/**
	 * 重设整个model
	 * 
	 * @param groupNames
	 * 		组名列表，不包括我的好友组
	 * @param friends
	 * 		所有下载项
	 */
	public void resetModel(List<String> groupNames, List<DownloadFriendEntry> friends) {	
		// 清除旧的数据
		ModelRegistry.clearGroup(myFriendGroup, false);
		ModelRegistry.clearGroup(clusterGroup, false);
		for(Group g : normalGroups)
			ModelRegistry.clearGroup(g, true);
		
		// 把旧的内容保存下来
		Map<Integer, User> oldUsers = new HashMap<Integer, User>();
		Map<Integer, Cluster> oldClusters = new HashMap<Integer, Cluster>();
		for(User u : myFriendGroup.users)
			oldUsers.put(u.qq, u);
		int size = clusterGroup.clusters.size();
		for(int i = size - 1; i >= 0; i--) {
			Cluster c = clusterGroup.clusters.get(i);
			if(c.clusterType == ClusterType.DIALOG_CONTAINER)
				continue;
			c.removeAllSubClusters();
			oldClusters.put(c.clusterId, c);	
		}
		for(Group g : normalGroups) {
			for(User u : g.users)
				oldUsers.put(u.qq, u);
			g.removeAllUsers();
		}
		normalGroups.clear();
		myFriendGroup.removeAllUsers();
		clusterGroup.removeAllCluster();
		
		// 创建新的组
		for(String name : groupNames) {
			Group g = new Group();
			g.name = name;
			g.expanded = false;
			normalGroups.add(g);
			ModelRegistry.addGroup(g);
		}
		
		// 添加新的组
		boolean showNick = main.getOptionHelper().isShowNick();
		size = getUploadGroupCount();
		List<DownloadFriendEntry> needInfos = new ArrayList<DownloadFriendEntry>();		
		for(DownloadFriendEntry entry : friends) {
			if(entry.isCluster()) {
				// 如果这个组存在，使用老对象，如果不存在，新建一个Cluster对象
				Cluster c = oldClusters.get(entry.qqNum);
				if(c == null) {
					needInfos.add(entry);
					c = new Cluster();
					c.clusterId = entry.qqNum;
					c.name = String.valueOf(entry.qqNum);
					c.headId = 1;
					c.clusterType = ClusterType.NORMAL;
					
					// 创建两个dummy，一个是群内组织，一个是讨论组
					Dummy orgDummy = new Dummy();
					orgDummy.dummyType = DummyType.CLUSTER_ORGANIZATION;
					orgDummy.name = cluster_organization;
					c.addDummy(orgDummy);
					Dummy subDummy = new Dummy();
					subDummy.dummyType = DummyType.SUBJECTS;
					subDummy.name = cluster_subject;
					c.addDummy(subDummy);
				}
				// 注册cluster
				clusterGroup.addCluster(c);
				ModelRegistry.addCluster(c);
			} else {
				if(entry.group >= size)
					entry.group = 0;
				
				User u = oldUsers.get(entry.qqNum);
				
				if(u == null) {
					needInfos.add(entry);
					u = new User();
					u.qq = entry.qqNum;
					u.nick = String.valueOf(u.qq);
					u.remark = main.getConfigHelper().getRemark(u.qq);
					u.displayName = showNick ? u.nick : ((u.remark == null) ? u.nick : u.remark.getName());
				} else {
					u.status = Status.OFFLINE;				
				}
				getFriendGroup(entry.group).addUser(u);
			}
		}
		
		// 添加多人对话
		Cluster dialogContainer = new Cluster();				
		dialogContainer.name = cluster_dialogs;
		dialogContainer.clusterType = ClusterType.DIALOG_CONTAINER;
		clusterGroup.addCluster(dialogContainer);
		ModelRegistry.addCluster(dialogContainer);
		
		// 刷新
		main.getDisplay().syncExec(new Runnable() {
			public void run() {		
				resetMode();
			}
		});
		
		// 得到新的model的信息
		for(DownloadFriendEntry dfe : needInfos) {
			if(dfe.isCluster() && dfe.qqNum != 0) {
				main.getClient().cluster_GetInfo(dfe.qqNum);
				main.getClient().cluster_getSubjectList(dfe.qqNum);
			} else
				main.getClient().user_GetInfo(dfe.qqNum);
		}
		
		// 得到多人对话列表
		main.getClient().cluster_getDialogList();
		
		// 恢复图标的跳动和闪烁，然后关闭提示框
		main.getDisplay().syncExec(new Runnable() {
			public void run() {
			    resetAllImageEffect();
			}				
		});
	}
	
	/**
	 * 设置是否显示昵称
	 * 
	 * @param b
	 * 		true表示显示昵称
	 */
	public void setShowNick(boolean b) {
		// 刷新所有用户的显示名称
		for(Iterator<User> i = ModelRegistry.getUserIterator(); i.hasNext(); ) {
			User u = i.next();
			if(!u.group.isCluster())
				u.displayName = b ? u.nick : (u.hasRemarkName() ? u.getRemarkName() : u.nick); 
		}
		
		// 刷新群中所有成员的显示名称
		for(Iterator<Cluster> i = ModelRegistry.getClustetIterator(); i.hasNext(); ) {
			Cluster c = i.next();
			for(User u : c.members.values()) {
				User regUser = ModelRegistry.getUser(u.qq);
				if(regUser == null)
					regUser = u;
				u.displayName = u.hasCardName() ? u.cardName : 
					(b ? regUser.nick : 
						((regUser.hasRemarkName() ? regUser.getRemarkName() : regUser.nick)));
			}
		}
	}
	
	/**
	 * 设置是否只显示在线用户
	 * 
	 * @param b
	 * 		true表示只显示在线用户
	 */
	public void setShowOnlineOnly(boolean b) {
		for(Group g : viewers.keySet()) {
			if(!g.isLatest())
				viewers.get(g).setFilter(b ? onlineFilter : null);				
		}
	}
	
	/**
	 * @return
	 * 		当前的qtree viewer
	 */
	public QTreeViewer<Model> getCurrentViewer() {
		return getViewer(getCurrentGroup());
	}
	
	/**
	 * 刷新model，同时刷新链接到这个model的model
	 * 
	 * @param m
	 */
	public void refreshModel(Model m) {
    	QTreeViewer<Model> viewer = getViewer(m);
		viewer.refresh(m);    	
	}

	/**
	 * 在当前组的后面添加一个组，如果处于树形模式，则处理有点不一样
	 */
	public void addGroup() {
		Group g = new Group();
		g.name = "Input Group Name";
		g.groupType = GroupType.FRIEND_GROUP;
		g.expanded = false;
		ModelRegistry.addGroup(g);
		if(isTreeMode()) {
			normalGroups.add(g);
			viewers.put(g, viewers.get(myFriendGroup));
			refreshGroup(myFriendGroup);
			viewers.get(myFriendGroup).editText(g);
		} else {
			Blind blind = main.getBlind();
			int index = Math.min(normalGroups.size(), blind.getCurrentSlatIndex()) + 1;
			normalGroups.add(index - 1, g);
			
			OptionHelper options = main.getOptionHelper();
			MouseListener itemMouseListener = new ItemMouseListener(main);
			MouseTrackListener itemMouseTrackListener = new ItemMouseTrackListener(main);
			DropTargetListener groupDropTargetListener = new GroupDropTargetListener(main);
			DragSourceListener itemDragSourceListener = new ItemDragSourceListener();
			IQTreeListener qtreeListener = new DefaultQTreeListener(main);
			Transfer[] dummyTransfer = new Transfer[] { TextTransfer.getInstance() };
			QTreeViewer<Model> viewer = new QTreeViewer<Model>(blind);
			viewer.setContentProvider(new GroupContentProvider(g));
			viewer.setLabelProvider(ModelLabelProvider.INSTANCE);
			viewer.setSorter(ModelSorter.INSTANCE);
			viewer.setInput(this);
			viewer.addDragSupport(DND.DROP_MOVE, dummyTransfer, itemDragSourceListener);
			viewer.getQTree().addMouseListener(itemMouseListener);
			viewer.getQTree().addMouseTrackListener(itemMouseTrackListener);
			viewer.getQTree().addQTreeListener(qtreeListener);
			viewers.put(g, viewer);
			blind.addSlat(index, viewer.getQTree());
			blind.addSlatDropSupport(viewer.getQTree(), DND.DROP_MOVE, dummyTransfer, groupDropTargetListener);			
			changeViewerLayout(viewer, 0, options.isShowSmallHead() ? ItemLayout.HORIZONTAL : ItemLayout.VERTICAL);			
			Slat slat = blind.getSlat(index);
			slat.addSlatListener(new GroupNameChangedListener(main));
			slat.editText();
		}		
		main.setGroupDirty(true);
	}

	/**
	 * 删除当前组
	 * 
	 * @param g
	 * 		要删除的组对象
	 */
	public void removeGroup(Group g) {
		QTreeViewer<Model> viewer = viewers.remove(g);
		if(viewer == null)
			return;
		ModelRegistry.clearGroup(g, true);
		normalGroups.remove(g);
		Blind blind = main.getBlind();
		if(!isTreeMode())
			blind.removeSlat(viewer.getQTree());	
		refreshAll();
		main.setGroupDirty(true);
	}

	/**
	 * 添加一个Cluster对象
	 * 
	 * @param clusterId
	 * 		id
	 * @param creator
	 * 		true表示我是这个群的创建者
	 * @return
	 * 		创建的Cluster对象
	 */
	public Cluster addCluster(int clusterId, boolean creator) {
		// 找到群组的索引
		Cluster c = ModelRegistry.getCluster(clusterId);
		if(c == null) {
			// 新建一个群Model
			c = new Cluster();
			c.clusterId = clusterId;
			c.headId = 1;	
			// 设置creator
			if(creator)
				c.creator = main.getMyModel().qq;
			// 创建两个dummy，一个是群内组织，一个是讨论组
			Dummy orgDummy = new Dummy();
			orgDummy.dummyType = DummyType.CLUSTER_ORGANIZATION;
			orgDummy.name = cluster_organization;
			c.addDummy(orgDummy);
			Dummy subDummy = new Dummy();
			subDummy.dummyType = DummyType.SUBJECTS;
			subDummy.name = cluster_subject;
			c.addDummy(subDummy);
			// 添加这个model
			clusterGroup.addCluster(c);
			ModelRegistry.addCluster(c);
			refreshGroup(clusterGroup);
			// 如果我是创建者，则激活群
			if(creator)
			    main.getClient().cluster_Activate(clusterId);
			// 请求得到这个群的信息和成员列表
			main.getClient().cluster_GetInfo(clusterId);
			main.getClient().cluster_getSubjectList(clusterId);
		}			
		// 返回model
		return c;
	}

	/**
	 * 添加一个临时群
	 * 
	 * @param type
	 * @param clusterId
	 * @param parentClusterId
	 * @return
	 */
	public Cluster addTempCluster(byte type, int clusterId, int parentClusterId) {
		// 查找父群
		Cluster parent = ModelRegistry.getCluster(parentClusterId);
		if(parent == null)
			return null;
		// 找到群组的索引
		Cluster c = ModelRegistry.getCluster(clusterId);
		if(c == null) {
			// 新建一个群Model
			c = new Cluster();
			c.clusterId = clusterId;
			c.headId = 4;
			c.parentClusterId = parentClusterId;
			c.creator = main.getMyModel().qq;
			c.clusterType = ClusterType.valueOfTemp(type);
			// 添加这个model
			clusterGroup.addCluster(c);
			parent.addSubCluster(c);
			ModelRegistry.addCluster(c);
			refreshGroup(clusterGroup);
			// 请求得到这个群的信息和成员列表
			main.getClient().cluster_AactivateTemp(type, clusterId, parentClusterId);
			main.getClient().cluster_GetTempInfo(type, clusterId, parentClusterId);
		}			
		// 返回model
		return c;	
	}

	/**
	 * 删除一个群的model，如果存在这个群的相关窗口，也将关闭他们
	 * 
	 * @param clusterId
	 * 		群的内部ID
	 * @return
	 * 		成功则返回true
	 */
	public boolean removeCluster(int clusterId) {
	    ShellRegistry shellRegistry = main.getShellRegistry();
	    UIHelper uiHelper = main.getUIHelper();
	    
		// 找到群组
	    Cluster c = ModelRegistry.removeCluster(clusterId);
	    if(c == null || c.clusterType == DIALOG_CONTAINER)
	    	return false;	    

		// 删除群
	    clusterGroup.removeCluster(c);
	    if(c.clusterType != NORMAL) {
	    	Cluster parent = ModelRegistry.getCluster(c.parentClusterId);
	    	if(parent != null)
	    		parent.removeSubCluster(c);
	    }
	    refreshGroup(clusterGroup);
		// 关闭群相关窗口
		if(shellRegistry.hasClusterInfoWindow(c)) {
			shellRegistry.removeClusterInfoWindow(c).close();
		}
		if(shellRegistry.hasSendClusterIMWindow(c)) {
			shellRegistry.removeSendClusterIMWindow(c).close();
		}
		// 把这个群的消息从队列中删除
		main.getMessageQueue().removeMessage(clusterId);
		// 调整动画状态
		resetGroupImageEffect(clusterGroup);
		uiHelper.resetTrayImageEffect();
		// 返回true表示成功
		return true;
	}
	
	/**
	 * 添加一个好友的View part到blind控件中
	 * 
	 * @param qq
	 * 			好友QQ号
	 */
	public void addFriend(int qq) {
		// 首先查看是否已经有了这个好友，如果有了，返回
		User f = ModelRegistry.getUser(qq);
		if(f != null && f.isFriend())
			return;
		
		// 首先我们判断加入好友目的组的哈希表中是否有该项，如果有，则加入到指定的组
		// 如果没有或者指定的组已经不存在，加入到第一个好友组中
		if(addTo.containsKey(qq)) {
			Group g = addTo.remove(qq);
			addFriend(qq, g);
			return;
		}
		
		// 出一个窗口要用户选择一个组，然后把这个好友添加到用户选择的组中
		SelectGroupDialog sgd = new SelectGroupDialog(main.getShell());
		sgd.setModel(getFriendGroupList());
		Group g = sgd.open();
		if(g != null)
			addFriend(qq, g);
	}	
	
	/**
	 * 添加一个好友
	 * 
	 * @param qqNum 
	 * 			好友号码
	 * @param g
	 * 			要添加到的好友组Group
	 */
	public void addFriend(int qq, Group g) {
		boolean ani = false; 
		User f = ModelRegistry.getUser(qq);
		if(f == null) {
			/* 如果是null，那么还没有这个好友，添加之 */
			f = new User();
			f.qq = qq;
			f.nick = String.valueOf(qq);
			f.displayName = f.nick;
			if(!main.getOptionHelper().isShowNick()) {
			    Remark remark = main.getConfigHelper().getRemark(qq);
			    if(remark != null)
			    	f.remark = remark;
			}
		} else {
			// 移动好友
			Group srcGroup = f.group;
			ani = hasAnimation(f);
			if(ani)
				stopAnimation(srcGroup, f);
			srcGroup.removeUser(f);
			refreshGroup(srcGroup);
			resetGroupImageEffect(srcGroup);
		}
		
		// 添加好友到目的组并调整动画状态
		g.addUser(f);
		refreshGroup(g);		
		if(ani)
			startBounce(g, f);
		resetGroupImageEffect(g);
		
		// 添加到组中，同时请求得到这个好友的信息和刷新在线好友列表
		main.addOnline(f);
		main.getClient().user_GetInfo(qq);			
		main.getClient().user_GetOnline();
		
		// 分组信息已改变
		main.setGroupDirty(true);
		
		// 更新服务器端列表
		// 这个功能不再使用，因为LumaQQ会允许不自动更新分组信息，所以，这里搞成自动的会带来问题
		//client.addFriendToList(group, qqNum);
	}		
	
	/**
	 * 停止某个组中某个model的动画
	 * 
	 * @param g
	 * @param m
	 */
	public void stopAnimation(Group g, Model m) {
		QTreeViewer<Model> viewer = getViewer(g);
		if(viewer != null)
			viewer.stopAnimation(m);
	}
	
	/**
	 * 开始跳动一个item
	 * 
	 * @param g
	 * @param m
	 */
	public void startBounce(Group g, Model m) {
		QTreeViewer<Model> viewer = getViewer(g);
		if(viewer != null)
			viewer.startAnimation(m, Animation.ICON_BOUNCE);
	}
	
	/**
	 * 停止Blind上所有的动画效果，如果有的话
	 */
	public void stopAllEffectOnBlind() {
		main.getBlind().stopAllAnimation();
		for(QTreeViewer<Model> viewer : viewers.values())
			viewer.getQTree().stopAllAnimation();
	}
	
	/**
	 * @param g
	 * @return
	 * 		true表示组的Slat上有一个动画
	 */
	public boolean hasGroupAnimation(Group g) {
		QTreeViewer<Model> viewer = viewers.get(g);
		if(viewer == null)
			return false;
		
		int index = main.getBlind().indexOf(viewer.getQTree());
		return main.getBlind().hasSlatAnimation(index);
	}
	
	/**
	 * 得到索引g指定的组，检查它目前是否需要闪烁图标，根据检查结果重置图标特效状态
	 * 
	 * @param g
	 * 		组索引
	 */
	public void resetGroupImageEffect(Group g) {
		if(g == null)
			return;
		
	    MessageQueue mq = main.getMessageQueue();
	    Resources res = Resources.getInstance();
    
	    // 如果是群组
	    if(g.isCluster()) {
	    	stopBlinkGroupImage(g);
	    	
			int sender = mq.nextClusterIMSender();
			Cluster c = ModelRegistry.getCluster(sender);
			if(c == null)
				return;			
			switch(c.clusterType) {
				case NORMAL:
					startBlinkGroupImage(c.group, res.getClusterHead(c.headId));
					startBounceImage(c);
					break;
				case SUBJECT:
					startBlinkGroupImage(c.group, res.getImage(Resources.icoDialog));
					startBlinkText(c.getParentCluster().getSubjectDummy());
					startBounceImage(c.getParentCluster());
					break;
				case DIALOG:
					startBlinkGroupImage(c.group, res.getImage(Resources.icoDialog));
					startBlinkText(c.getParentCluster());
					break;
			}
			return;
	    }
	    
		// 检查这个组还有没有消息，没有了就停止闪烁，有就闪烁下一个消息发送者的头像	
	    // 如果是树形模式，处理有些不同，需要检查是否还有普通消息	    
	    stopBlinkGroupImage(g);
	    stopBlinkText(g);
		if(isTreeMode()) {
			int groupSender = mq.nextGroupSender(g);
			User uGS = ModelRegistry.getUser(groupSender);
			int sender = mq.nextBlinkableIMSender();
			User uS = ModelRegistry.getUser(sender);
			if(uS != null) {
				if(uS.isTM())
					startBlinkGroupImage(g, res.getImage(uS.female ? Resources.icoTMFemale16 : Resources.icoTMMale16));
				else
					startBlinkGroupImage(g, HeadFactory.getOnlineHead(uS));	
			}
			if(uGS != null)
				startBlinkText(uGS.group);				
		} else if(mq.hasGroupMessage(g)) {
			int sender = mq.nextGroupSender(g);
			User u = ModelRegistry.getUser(sender);
			if(u != null) {
				if(u.isTM())
					startBlinkGroupImage(g, res.getImage(u.female ? Resources.icoTMFemale16 : Resources.icoTMMale16));
				else
					startBlinkGroupImage(g, HeadFactory.getOnlineHead(u));	
			}
		}
	}
	
	/**
	 * 重设组内好友或者群的动画状态
	 * 
	 * @param g
	 * 		Group 
	 */
	public void resetUserClusterImageEffect(Group g) {
		MessageQueue mq = main.getMessageQueue();
		
		QTreeViewer<Model> viewer = null;
		viewer = viewers.get(g);
		if(viewer == null)
			return;
		
		switch(g.groupType) {
			case CLUSTER_GROUP:
				for(Cluster c : g.clusters) {
					if(mq.hasMessage(c.clusterId))
						viewer.startAnimation(c, Animation.ICON_BOUNCE);
				}
				break;
			case DEFAULT_FRIEND_GROUP:
			case FRIEND_GROUP:
			case STRANGER_GROUP:
				for(User u : g.users) {
					if(mq.hasMessage(u.qq))
						viewer.startAnimation(u, Animation.ICON_BOUNCE);
				}
				break;
			default:
				break;
		}
	}
	
	/**
	 * 重置所有动画状态
	 */
	public void resetAllImageEffect() {
		resetGroupImageEffect(myFriendGroup);
		resetUserClusterImageEffect(myFriendGroup);
		
		resetGroupImageEffect(strangerGroup);
		resetUserClusterImageEffect(strangerGroup);
		
		resetGroupImageEffect(clusterGroup);
		resetUserClusterImageEffect(clusterGroup);
		
		for(Group g : normalGroups) {
			resetGroupImageEffect(g);
			resetUserClusterImageEffect(g);
		}
		
		main.getUIHelper().resetTrayImageEffect();
	}

	/**
	 * 展开组
	 * 
	 * @param g
	 */
	public void expandGroup(Group g) {
		g.expanded = true;
		QTreeViewer<Model> viewer = getViewer(g);
		viewer.expandItem(g);
		viewer.refresh();
	}
	
	/**
	 * 收起组
	 * 
	 * @param g
	 */
	public void collapseGroup(Group g) {
		g.expanded = false;
		QTreeViewer<Model> viewer = getViewer(g);
		viewer.collapseItem(g);
		viewer.refresh();
	}
	
	/**
	 * 移动一个用户到一个组中
	 * 
	 * @param user
	 * @param destGroup
	 */
	public void moveUser(User user, Group destGroup) {
		/*
		 * 移动用户到这个组，判断各种情况
		 * 1. 好友组到好友组，简单移动
		 * 2. 好友组到陌生人组，删除
		 * 3. 好友组到黑名单组，删除且删除自己
		 * 4. 陌生人组到黑名单组，删除自己
		 * 5. 黑名单组到陌生人组，简单移动 
		 */ 
		Group srcGroup = user.group;
		if(srcGroup == destGroup)
			return;
		switch(srcGroup.groupType) {
			case DEFAULT_FRIEND_GROUP:
			case FRIEND_GROUP:
				switch(destGroup.groupType) {
					case DEFAULT_FRIEND_GROUP:
					case FRIEND_GROUP:
						simpleMove(user, srcGroup, destGroup);
						break;
					case STRANGER_GROUP:
						remove(false, user, destGroup);
						break;
					case BLACKLIST_GROUP:
						remove(true, user, destGroup);
						break;
					default:
						break;
				}
				break;
			case STRANGER_GROUP:
				switch(destGroup.groupType) {
					case DEFAULT_FRIEND_GROUP:
					case FRIEND_GROUP:
					    main.getShellLauncher().openAddReceiveSystemMessageShell(user);
						addTo.put(user.qq, destGroup);
						main.getClient().user_Add(user.qq);
						break;
					case BLACKLIST_GROUP:
						remove(true, user, destGroup);
						break;
					default:
						break;
				}
				break;
			case BLACKLIST_GROUP:
				switch(destGroup.groupType) {
					case DEFAULT_FRIEND_GROUP:
					case FRIEND_GROUP:
					    main.getShellLauncher().openAddReceiveSystemMessageShell(user);
						addTo.put(user.qq, destGroup);
						main.getClient().user_Add(user.qq);
						break;
					case STRANGER_GROUP:
						simpleMove(user, srcGroup, destGroup);
						break;
					default:
						break;
				}
				break;
			default:
				break;
		}
	}
	
	/**
	 * 简单移动
	 * @param user
	 * @param srcGroup
	 * @param destGroup
	 */
	public void simpleMove(User user, Group srcGroup, Group destGroup) {		
		boolean hasAnimation = hasAnimation(user);
		if(hasAnimation)
			stopAnimation(srcGroup, user);
		srcGroup.removeUser(user);
		refreshGroup(srcGroup);
		resetGroupImageEffect(srcGroup);
		
		if(destGroup != null) {
			destGroup.addUser(user);
			refreshGroup(destGroup);
			if(hasAnimation)
				startBounce(destGroup, user);
			resetGroupImageEffect(destGroup);
		}
		if(srcGroup.isFriendly())
			main.setGroupDirty(true);			
	}

	/**
	 * 删除一个用户到指定组中
	 * 
	 * @param destBlacklist
	 * 		目的组是否是黑名单
	 * @param f
	 * @param destGroup
	 */
	private void remove(boolean destBlacklist, User f, Group destGroup) {
		MessageBox box = new MessageBox(main.getShell(), SWT.ICON_WARNING | SWT.YES | SWT.NO);
		box.setText(message_box_common_warning_title);		
		if(destBlacklist)
			box.setMessage(NLS.bind(hint_delete_friend_and_remove_self, String.valueOf(f.qq)));
		else
			box.setMessage(NLS.bind(hint_delete_friend, String.valueOf(f.qq)));
		if(box.open() == SWT.YES) {
			ReceiveSystemMessageShell rsms = new ReceiveSystemMessageShell(main);
			rsms.setFriendModel(f);
			rsms.open(ReceiveSystemMessageShell.DELETE_MODE);
			main.deleteFriendFromServer(f.qq, true, destBlacklist, destGroup);
		}
	}

	/**
	 * 同步最近联系人中的信息
	 * 
	 * @param f
	 * 		需要同步的好友
	 */
	public void synchronizeLatest(User f) {    	
		User u = latestGroup.getUser(f.qq);
    	if(u != null) {
    		u.infoCopy(f);
    		refreshGroup(latestGroup);
    	}
	}
	
	/**
	 * 同步最近联系人中的信息，不立刻刷新
	 * 
	 * @param f
	 * 		需要同步的好友
	 */
	public void synchronizeLatestDelayRefresh(User f) {    	
		User u = latestGroup.getUser(f.qq);
    	if(u != null)
    		u.infoCopy(f);
	}
	
	/**
	 * 同步最近联系人中的群
	 * 
	 * @param c
	 */
	public void synchronizeLatest(Cluster c) {
		Cluster cInLatest = latestGroup.getCluster(c.clusterId);
		if(cInLatest != null) {
			cInLatest.infoCopy(c);
			refreshGroup(latestGroup);
		}
	}
	
	/**
	 * 更新最近联系人组，如果不存在这个用户，则添加
	 * 
	 * @param f
	 */
	public void updateLatest(User f) {
		f.lastMessageTime = System.currentTimeMillis();
	    // 得到最近联系人设定最大数量
	    int max = main.getOptionHelper().getLatestSize();
	    // 得到当前联系人数量
	    int num = latestGroup.users.size() + latestGroup.clusters.size();
	    // 删除多余的联系人
	    if(max > 0) {
		    while(num >= max) {
		    	removeLRUModel();
		    	num--;
		    }
		    // 如果没有这个人，添加
		    if(!latestGroup.hasUser(f.qq)) {
		    	try {
					latestGroup.addUser((User)f.clone());
				} catch(CloneNotSupportedException e) {
				}
		    }
	    }
	    main.getBlindHelper().synchronizeLatest(f);
	}
	
	/**
	 * 更新最近联系人组，如果不存在这个群，则添加
	 * 
	 * @param c
	 */
	public void updateLatest(Cluster c) {
		c.lastMessageTime = System.currentTimeMillis();
	    // 得到最近联系人设定最大数量
	    int max = main.getOptionHelper().getLatestSize();
	    // 得到当前联系人数量
	    int num = latestGroup.users.size() + latestGroup.clusters.size();
	    // 删除多余的联系人
	    if(max > 0) {
		    while(num >= max) {
		    	removeLRUModel();
		    	num--;
		    }
		    // 如果没有这个人，添加
		    if(!latestGroup.hasCluster(c.clusterId)) {
		    	try {
					latestGroup.addCluster((Cluster)c.clone());
				} catch(CloneNotSupportedException e) {
				}
		    }
	    }
	    main.getBlindHelper().synchronizeLatest(c);
	}
	
	/**
	 * 删除掉最近联系人中lastMessageTime最小的model
	 */
	private void removeLRUModel() {
		User lruUser = null;
		Cluster lruCluster = null;
		long min = Long.MAX_VALUE;
		for(User f : latestGroup.users) {
			if(f.lastMessageTime < min) {
				min = f.lastMessageTime;
				lruUser = f;
			}
		}
		for(Cluster c : latestGroup.clusters) {
			if(c.lastMessageTime < min) {
				min = c.lastMessageTime;
				lruCluster = c;
			}
		}		
		if(lruCluster != null)
			latestGroup.removeCluster(lruCluster);
		else if(lruUser != null)
			latestGroup.removeUser(lruUser);
	}
	
	/**
	 * 同步最近联系人中的状态
	 * 
	 * @param f
	 * 		需要同步的好友
	 * @param redraw
	 * 		true表示立刻刷新
	 */
	public void synchronizedLatestStatus(User f, boolean redraw) {
    	if(latestGroup.hasUser(f.qq)) {
    		User u = latestGroup.getUser(f.qq);
    		u.status = f.status;
    		if(redraw)
    			refreshGroup(latestGroup);
    	}
	}

	/**
	 * 设置是否显示个性签名
	 * 
	 * @param b
	 */
	public void setShowSignature(boolean b) {
		ModelLabelProvider.INSTANCE.setShowSignature(b);
	}
	
	/**
	 * 设置是否显示自定义头像
	 * 
	 * @param b
	 */
	public void setShowCustomHead(boolean b) {
		ModelLabelProvider.INSTANCE.setShowCustomHead(b);
	}
}
