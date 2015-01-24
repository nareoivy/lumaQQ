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
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import edu.tsinghua.lumaqq.ui.sorter.UserQQSorter;

/**
 * Model的注册表，用于根据关键字段快速查找model。链接不存在注册表中。
 * Cluster的映射里面，0是多人对话容器
 * 
 * @author luma
 */
public class ModelRegistry {
	private static Map<Integer, User> userMapping = new HashMap<Integer, User>();
	private static Map<Integer, Cluster> clusterMapping = new HashMap<Integer, Cluster>();
	private static Map<String, Group> groupMapping = new HashMap<String, Group>();
	public static void clearAll() {
		userMapping.clear();
		clusterMapping.clear();
		groupMapping.clear();
	}
	
	public static Cluster getDialogContainer() {
		return getCluster(0);
	}
	
	public static Iterator<User> getUserIterator() {
		return userMapping.values().iterator();
	}
	
	public static List<User> getSortedUserList() {
		List<User> ret = new ArrayList<User>(userMapping.values());
		Collections.sort(ret, new UserQQSorter());
		return ret;
	}
	
	public static Cluster removeCluster(Cluster c) {
		return clusterMapping.remove(c.clusterId);
	}
	
	public static Cluster removeCluster(int clusterId) {
		return clusterMapping.remove(clusterId);
	}
	
	public static User removeUser(User u) {
		User _u = getUser(u.qq);
		if(_u == null)
			return null;
		
		if(_u.group == null)
			return userMapping.remove(u.qq);
		else
			return null;
	}
	
	public static User removeUser(int qq) {
		return userMapping.remove(qq);
	}
	
	public static Iterator<Cluster> getClustetIterator() {
		return clusterMapping.values().iterator();
	}
	
	public static int getUserCount() {
		return userMapping.size();
	}
	
	public static int getUserClusterCount() {
		return userMapping.size() + clusterMapping.size();
	}
	
	/**
	 * 清除某组的用户注册项
	 * 
	 * @param g
	 * 		Group
	 * @param includeGroup	
	 * 		是否清除组
	 */
	public static void clearGroup(Group g, boolean includeGroup) {
		switch(g.groupType) {
			case CLUSTER_GROUP:
				for(Cluster c : g.clusters)
					clusterMapping.remove(c.clusterId);
				break;
			case LATEST_GROUP:
				break;
			default:
				for(User u : g.users)
					userMapping.remove(u.qq);
				break;
		}
		if(includeGroup)
			groupMapping.remove(g.name);
	}
	
	public static void addUser(User u) {
		if(u.qq == 0)
			return;
		
		// 组权重为0的用户不保存
		if(u.group.groupType.weight == 0)
			return;
		
		// 其他组的用户比组内的用户级别高，主要是因为用户可能有重复，
		// 我们倾向于保存非组内和非最近联系人的User对象
		if(userMapping.containsKey(u.qq)) {
			User existUser = userMapping.get(u.qq);
			if(existUser == null || u.group.groupType.weight >= existUser.group.groupType.weight)
				userMapping.put(u.qq, u);
		} else
			userMapping.put(u.qq, u);
	}
	
	public static void addGroup(Group g) {
		groupMapping.put(g.name, g);
	}
	
	public static void addCluster(Cluster c) {
		clusterMapping.put(c.clusterId, c);
	}
	
	public static User getUser(int qq) {
		return userMapping.get(qq);
	}
	
	public static Cluster getCluster(int clusterId) {
		return clusterMapping.get(clusterId);
	}
	
	public static Group getGroup(String name) {
		return groupMapping.get(name);
	}
	
	public static boolean hasUser(int qq) {
		return userMapping.containsKey(qq);
	}
	
	public static boolean hasCluster(int clusterId) {
		return clusterMapping.containsKey(clusterId);
	}
	
	public static boolean hasGroup(String name) {
		return groupMapping.containsKey(name);
	}
}
