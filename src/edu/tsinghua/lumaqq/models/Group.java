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
import java.util.Iterator;
import java.util.List;

import org.eclipse.swt.SWT;

/**
 * 组model
 * 
 * @author luma
 */
public class Group extends Model {
	public String name;
	public List<User> users;
	public List<Cluster> clusters;
	public GroupType groupType;
	
	public Group() {
		super(Type.GROUP);
		initializeValues();
	}
	
	private void initializeValues() {
		name = EMPTY_STRING;
		users = new ArrayList<User>();
		clusters = new ArrayList<Cluster>();
		groupType = GroupType.FRIEND_GROUP;
	}
	
	public User getUser(int qq) {
		for(User u : users) {
			if(u.qq == qq)
				return u;
		}
		return null;
	}
	
	public boolean hasUser(int qq) {
		for(User u : users) {
			if(u.qq == qq)
				return true;
		}
		return false;
	}
	
	public boolean hasCluster(int id) {
		for(Cluster c : clusters) {
			if(c.clusterId == id)
				return true;
		}
		return false;
	}
	
	public Cluster getCluster(int id) {
		for(Cluster c : clusters) {
			if(c.clusterId == id)
				return c;
		}
		return null;
	}
	
	public int getOnlineUserCount() {
		int online = 0;
		for(User u : users) {
			if(!u.isOffline())
				online++;
		}
		return online;
	}
	
	public void removeUser(int index) {
		if(index < 0 || index >= users.size())
			SWT.error(SWT.ERROR_INVALID_ARGUMENT);
		User u = users.remove(index);
		u.group = null;
		ModelRegistry.removeUser(u);
	}
	
	public void addUser(User user) {		
		if(hasUser(user.qq))
			return;
		users.add(user);
		user.group = this;
		ModelRegistry.addUser(user);
	}
	
	public void removeAllUsers() {
		for(Iterator<User> i = users.iterator(); i.hasNext(); ) {
			User u = i.next();
			u.group = null;
			i.remove();
			ModelRegistry.removeUser(u);
		}
	}
	
	public void removeAllCluster() {
		for(Iterator<Cluster> i = clusters.iterator(); i.hasNext(); ) {
			i.next().group = null;
			i.remove();
		}
	}
	
	public void removeUser(User user) {
		users.remove(user);
		user.group = null;
		ModelRegistry.removeUser(user);
	}
	
	/**
	 * 删除一个重复的用户
	 * 
	 * @param user
	 * 		User对象
	 */
	public void removeDuplicate(User user) {
		users.remove(user);
		user.group = null;
	}
	
	public void addCluster(Cluster c) {
		c.group = this;
		if(clusters.contains(c)) 
			return;
		clusters.add(c);
	}
	
	public void removeCluster(Cluster c) {
		clusters.remove(c);
		c.group = null;
		ModelRegistry.removeCluster(c);
	}
	
	public int getUserCount() {
		return users.size();
	}
	
	public boolean isCluster() {
		return groupType == GroupType.CLUSTER_GROUP;
	}

	public boolean isFriendly() {
		switch(groupType) {
			case DEFAULT_FRIEND_GROUP:
			case FRIEND_GROUP:
				return true;
			default:
				return false;
		}
	}
	
	public boolean isDefaultFriend() {
		return groupType == GroupType.DEFAULT_FRIEND_GROUP;
	}
	
	public boolean isStranger() {
		return groupType == GroupType.STRANGER_GROUP;
	}

	public boolean isBlackList() {
		return groupType == GroupType.BLACKLIST_GROUP;
	}
	
	public boolean isLatest() {
		return groupType == GroupType.LATEST_GROUP;
	}
}
