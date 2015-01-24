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
package edu.tsinghua.lumaqq.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import edu.tsinghua.lumaqq.qq.QQ;
import edu.tsinghua.lumaqq.qq.Util;

/**
 * 群model
 * 
 * @author luma
 */
public class Cluster extends Model {
	public int clusterId;
	public int externalId;
	public int parentClusterId;
	public Map<Integer, Cluster> subClusters;
	public MessageSetting messageSetting;
	public int messageCount;
	public int headId;
	public ClusterType clusterType;
	public Map<Integer, User> members;
	public List<Dummy> dummies;
	public Map<Integer, Organization> organizations;
	public String name;
	public Group group;
	public int versionId;
	public int creator;
	public byte authType;
	public int category;
	public int oldCategory;
	public String description;
	public String notice;
	public Map<Integer, Integer> admins;
	public Map<Integer, Integer> stockholders;
	public long lastMessageTime;
	
	// false表示群名片还没有自动更新过
	public boolean cardUpdated;
	
	public Cluster() {
		super(Type.CLUSTER);
		initializeValues();
	}
	
	private void initializeValues() {
		clusterId = externalId = parentClusterId = 0;
		messageSetting = MessageSetting.ACCEPT;
		messageCount = 0;
		subClusters = new HashMap<Integer, Cluster>();
		headId = 4;
		clusterType = ClusterType.NORMAL;
		members = new HashMap<Integer, User>();
		dummies = new ArrayList<Dummy>();
		organizations = new HashMap<Integer, Organization>();
		name = EMPTY_STRING;
		group = null;
		versionId = 0;
		creator = 0;
		authType = QQ.QQ_AUTH_CLUSTER_NEED;
		category = 0;
		oldCategory = 0;
		description = notice = EMPTY_STRING;
		cardUpdated = false;
		admins = new HashMap<Integer, Integer>();
		stockholders = new HashMap<Integer, Integer>();
		lastMessageTime = 0;
	}
	
	public void infoCopy(Cluster c) {
		headId = c.headId;
		name = c.name;
		description = c.description;
		notice = c.notice;
		lastMessageTime = c.lastMessageTime;
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		Cluster c = new Cluster();
		c.clusterId = clusterId;
		c.externalId = externalId;
		c.parentClusterId = parentClusterId;
		c.messageSetting = messageSetting;
		c.messageCount = messageCount;
		c.subClusters = subClusters;
		c.headId = headId;
		c.clusterType = clusterType;
		c.members = members;
		c.dummies = dummies;
		c.organizations = organizations;
		c.name = name;
		c.versionId = versionId;
		c.creator = creator;
		c.authType = authType;
		c.category = category;
		c.oldCategory = oldCategory;
		c.description = description;
		c.notice = notice;
		c.cardUpdated = cardUpdated;
		c.admins = admins;
		c.stockholders = stockholders;
		c.lastMessageTime = lastMessageTime;
		return c;
	}
	
	@Override
	public boolean equals(Object arg0) {
		if(arg0 instanceof Cluster) {
			Cluster c = (Cluster)arg0;
			return c.clusterId == clusterId;
		} else
			return false;
	}
	
	@Override
	public int hashCode() {
		return clusterId;
	}
	
	/**
	 * 添加一个组织
	 * 
	 * @param org
	 */
	public void addOrganization(Organization org) {
		organizations.put(org.id, org);
		org.cluster = this;
	}
	
	public Organization getOrganization(int id) {
		return organizations.get(id);
	}
	
	public void decreaseOrganizationId(Organization org) {
		if(organizations.remove(org.id) != null) {
			org.id--;
			organizations.put(org.id, org);
		}
	}	
	
	public void decreaseOrganizationId(int id) {
		Organization org = organizations.remove(id);
		if(org != null) {
			org.id--;
			organizations.put(org.id, org);
		}
	}
	
	/**
	 * 删除一个组织
	 * 
	 * @param org
	 */
	public void removeOrganization(Organization org) {
		organizations.remove(org.id);
		if(org != null)
			org.cluster = null;
	}
	
	/**
	 * @param id
	 * 		组织序号
	 * @return
	 * 		true表示有这个组织
	 */
	public boolean hasOrganization(int id) {
		return organizations.containsKey(id);
	}
	
	/**
	 * 删除所有组织
	 */
	public void removeAllOrganizations() {
		for(Organization org : organizations.values())
			org.cluster = null;
		organizations.clear();
	}
	
	/**
	 * @return
	 * 		讨论组dummy
	 */
	public Dummy getSubjectDummy() {
		for(Dummy dummy : dummies) {
			if(dummy.dummyType == DummyType.SUBJECTS)
				return dummy;
		}
		return null;
	}
	
	/**
	 * 把所有成员的在线状态置为下线
	 */
	public void resetMemberStatus() {
		for(User u : members.values())
			u.status = Status.OFFLINE;
	}
	
	public Cluster getParentCluster() {
		return ModelRegistry.getCluster(parentClusterId);
	}
	
	public void addSubCluster(Cluster sub) {
		subClusters.put(sub.clusterId, sub);
		sub.parentClusterId = clusterId;
		group.addCluster(sub);
	}
	
	public boolean hasSubCluster(int id) {
		return subClusters.containsKey(id);
	}
	
	public void removeSubCluster(Cluster sub) {
		subClusters.remove(sub.clusterId);
		sub.parentClusterId = 0;
		group.removeCluster(sub);
	}
	
	public void removeAllSubClusters() {
		for(Cluster c : subClusters.values()) {
			c.parentClusterId = 0;
			group.removeCluster(c);
		}
		subClusters.clear();
	}
	
	public void addDummy(Dummy dummy) {
		dummies.add(dummy);
		dummy.cluster = this;
	}
	
	public void removeDummy(Dummy dummy) {
		dummies.remove(dummy);
		dummy.cluster = null;
	}
	
	public List<Integer> getMemberQQList() {
		return Arrays.asList(members.keySet().toArray(new Integer[members.size()]));
	}
	
	public void addMember(User user) {
		members.put(user.qq, user);
		user.cluster = this;
		user.group = group;
		ModelRegistry.addUser(user);
	}
	
	public void removeMember(User user) {
		members.remove(user.qq);
		user.cluster = null;
		user.group = null;
		ModelRegistry.removeUser(user);
	}
	
	public User removeMember(int qq) {
		User u = members.remove(qq);
		if(u != null) {
			u.cluster = null;
			u.group = null;
			ModelRegistry.removeUser(u);
		}
		return u;
	}
	
	public User getMember(int qq) {
		return members.get(qq);
	}
	
	public boolean hasMember(int qq) {
		return members.containsKey(qq);
	}
	
	public void removeAllMembers() {
		for(User u : members.values()) {
			u.cluster = null;
			u.group = null;
			ModelRegistry.removeUser(u);
		}
		members.clear();
	}
	
	public int getMemberCount() {
		return members.size();
	}
	
	public boolean isPermanent() {
		return clusterType == ClusterType.NORMAL;
	}
	
	public int getOnlineMemberCount() {
		int online = 0;
		for(User u : members.values()) {
			if(u.isOnline() || u.isAway())
				online++;
		}
		return online;
	}

	public boolean isClusterDirty(int newId) {
		return versionId != newId;
	}
	
	public String getAdminQQString() {
		StringBuilder sb = new StringBuilder();
		for(Integer qq : admins.keySet()) {
			sb.append(qq).append(',');
		}
		if(sb.length() > 0)
			sb.deleteCharAt(sb.length() - 1);
		return sb.toString();
	}
	
	public String getStockholderQQString() {
		StringBuilder sb = new StringBuilder();
		for(Integer qq : stockholders.keySet()) {
			sb.append(qq).append(',');
		}
		if(sb.length() > 0)
			sb.deleteCharAt(sb.length() - 1);
		return sb.toString();
	}
	
	public void parseAdminQQString(String s) {
		admins.clear();
		if(s == null)
			return;
		StringTokenizer st = new StringTokenizer(s, ",");
		while(st.hasMoreTokens()) {
			int qq = Util.getInt(st.nextToken(), 0);
			if(qq != 0)
				addAdmin(qq);
		}
	}
	
	public void parseStockholderQQString(String s) {
		stockholders.clear();
		if(s == null)
			return;
		StringTokenizer st = new StringTokenizer(s, ",");
		while(st.hasMoreTokens()) {
			int qq = Util.getInt(st.nextToken(), 0);
			if(qq != 0)
				addStockholder(qq);
		}
	}
	
	public void addStockholder(int qq) {
		stockholders.put(qq, qq);
	}
	
	public void removeStockholder(int qq) {
		stockholders.remove(qq);
	}
	
	public void addAdmin(int qq) {
		admins.put(qq, qq);
	}
	
	public void removeAdmin(int qq) {
		admins.remove(qq);
	}
	
	/**
	 * 检查一个用户是不是超级用户，这里超级用户是指具有管理权限的成员
	 * 
	 * @param qq
	 * @return
	 */
	public boolean isSuperMember(int qq) {
		return qq == creator || admins.containsKey(qq) || stockholders.containsKey(qq);
	}
	
	public boolean isAdmin(int qq) {
		return admins.containsKey(qq);
	}
	
	public boolean isStockholder(int qq) {
		return stockholders.containsKey(qq);
	}
	
	public void clearStockholders() {
		stockholders.clear();
	}
	
	public void clearAdmins() {
		admins.clear();
	}
	
	public boolean isCreator(int qq) {
		return qq == creator;
	}
}
