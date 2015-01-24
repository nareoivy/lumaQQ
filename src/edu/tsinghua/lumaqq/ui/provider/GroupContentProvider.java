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
package edu.tsinghua.lumaqq.ui.provider;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.tsinghua.lumaqq.models.Cluster;
import edu.tsinghua.lumaqq.models.ClusterType;
import edu.tsinghua.lumaqq.models.Dummy;
import edu.tsinghua.lumaqq.models.Group;
import edu.tsinghua.lumaqq.models.Model;
import edu.tsinghua.lumaqq.models.Organization;
import edu.tsinghua.lumaqq.models.User;
import edu.tsinghua.lumaqq.ui.helper.BlindHelper;
import edu.tsinghua.lumaqq.widgets.qstyle.IQTreeContentProvider;

import org.eclipse.swt.SWT;

/**
 * 通用组内容提供者
 * 
 * @author luma
 */
public class GroupContentProvider implements IQTreeContentProvider<Model> {
	private static final Model[] ARRAY_OF_MODEL = new Model[0];
	private static final List<Model> temp = new ArrayList<Model>();
	private Group group;
	
	public GroupContentProvider(Group g) {
		group = g;
	}
	
	public Model[] getElements(Object inputElement) {
		if(!(inputElement instanceof BlindHelper))
			return ARRAY_OF_MODEL;
		
		switch(group.groupType) {
			case CLUSTER_GROUP:
				return getPermanentClusterArray(group);
			case BLACKLIST_GROUP:
			case DEFAULT_FRIEND_GROUP:
			case FRIEND_GROUP:
			case STRANGER_GROUP:
				return group.users.toArray(ARRAY_OF_MODEL);
			case LATEST_GROUP:
				temp.clear();
				temp.addAll(group.users);
				temp.addAll(group.clusters);
				return temp.toArray(ARRAY_OF_MODEL);
			default:
				SWT.error(SWT.ERROR_INVALID_RANGE);
				return ARRAY_OF_MODEL;
		}
	}
	
	/**
	 * 得到固定群数组
	 * 
	 * @param group
	 * 		群Group对象
	 * @return
	 * 		Model数组
	 */
	private Model[] getPermanentClusterArray(Group group) {
		temp.clear();
		for(Cluster c : group.clusters) {
			if(c.clusterType == ClusterType.NORMAL || c.clusterType == ClusterType.DIALOG_CONTAINER)
				temp.add(c);				
		}
		return temp.toArray(ARRAY_OF_MODEL);
	}

	/**
	 * 得到子节点
	 * 
	 * @param parent
	 * @return
	 */
	public Model[] getChildren(Model parent) {
		switch(parent.type) {
			case CLUSTER:
				Cluster c = (Cluster)parent;
				if(c.group.isLatest())
					return ARRAY_OF_MODEL;
				else {
					switch(c.clusterType) {
						case NORMAL:
							return c.dummies.toArray(ARRAY_OF_MODEL);
						case DIALOG_CONTAINER:
							return c.subClusters.values().toArray(ARRAY_OF_MODEL);
						default:
							return ARRAY_OF_MODEL;
					}					
				}
			case ORGANIZATION:
				Organization org = (Organization)parent;
				return getChildrenOfOrganization(org);
			case DUMMY:
				Dummy dummy = (Dummy)parent;				
				switch(dummy.dummyType) {
					case CLUSTER_ORGANIZATION:
						return getChildrenOfTopOrganization(dummy);
					case SUBJECTS:
						return dummy.cluster.subClusters.values().toArray(ARRAY_OF_MODEL);
					default:
						return ARRAY_OF_MODEL;
				}
			default:
				return ARRAY_OF_MODEL;
		}
	}
	
	/**
	 * 得到某个组织的孩子节点
	 * 
	 * @param org
	 * @return
	 */
	private Model[] getChildrenOfOrganization(Organization org) {
		temp.clear();
		addMember(org.id, org.cluster);
		addOrganization(org.getLevel() + 1, org, org.cluster.organizations);
		return temp.toArray(ARRAY_OF_MODEL);
	}
	
	/**
	 * 得到顶层组织结构
	 * 
	 * @param dummy
	 * @return
	 */
	private Model[] getChildrenOfTopOrganization(Dummy dummy) {
		temp.clear();
		addMember(0, dummy.cluster);
		addOrphanMember(dummy.cluster);
		addOrganization(0, null, dummy.cluster.organizations);
		return temp.toArray(ARRAY_OF_MODEL);
	}
	
	/**
	 * 也许有些成员输入某个组织，但是这个组织不存在（正常的话不会有
	 * 这个错误，为了容错考虑），把这些成员叫做孤儿成员
	 * 
	 * @param c
	 */
	private void addOrphanMember(Cluster c) {
		for(User u : c.members.values()) {
			if(u.organizationId != 0 && !c.hasOrganization(u.organizationId))
				temp.add(u);
		}
	}

	/**
	 * 添加属于某个组织的成员
	 * 
	 * @param organizationId
	 * 		组织id，为0表示不属于某个群内组织
	 * @param c
	 * 		Cluster对象
	 */
	private void addMember(int organizationId, Cluster c) {
		for(User u : c.members.values()) {
			if(u.organizationId == organizationId)
				temp.add(u);
		}
	}
	
	/**
	 * 添加某一层的组织，可以指定父组织，如果父组织为null，则都添加
	 * 
	 * @param level
	 * 		层号，从0开始
	 * @param parent
	 * 		父组织
	 * @param organizations
	 * 		所有组织列表
	 */
	private void addOrganization(int level, Organization parent, Map<Integer, Organization> organizations) {
		for(Organization org : organizations.values()) {
			if(org.getLevel() == level) {
				if(parent == null || org.isChildOf(parent))
					temp.add(org);
			}
		}
	}

	public Model getParent(Model child) {
		return null;
	}

	public boolean hasChildren(Model parent) {
		return false;
	}
}
