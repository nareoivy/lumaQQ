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
package edu.tsinghua.lumaqq.ui.wizard.cluster;

import java.util.ArrayList;
import java.util.List;

import edu.tsinghua.lumaqq.models.Cluster;
import edu.tsinghua.lumaqq.models.User;
import edu.tsinghua.lumaqq.qq.QQ;

/**
 * 向导内数据的封装bean
 * 
 * @author luma
 */
public class ClusterWizardModel {
	// 群类型
    public static final int PERMANENT_CLUSTER = 0;
    public static final int DIALOG = 1;
    public static final int SUBJECT = 2;
    
    // 页名称
    public static final String PAGE_CREATE_WHAT = "1";
    public static final String PAGE_PERMANENT_CLUSTER_INFO = "2";
    public static final String PAGE_TEMP_CLUSTER_INFO = "3";
    public static final String PAGE_MEMBER_SELECT = "4";
    public static final String PAGE_CREATE = "5";
    
	private int clusterType;
	private String startingPage;
	private byte authType;
	private int category;
	private String name;
	private String description;
	private String notice;
	private Cluster parentCluster;
	private List<User> members;
	
	/**
	 * 构造函数
	 */
	public ClusterWizardModel() {
		clusterType = PERMANENT_CLUSTER;
		startingPage = PAGE_CREATE_WHAT;
		authType = QQ.QQ_AUTH_CLUSTER_NEED;
		category = 0;
		name = description = notice = "";
		parentCluster = null;
		members = new ArrayList<User>();
	}
	
    public List<Integer> getMemberQQArray() {
		List<Integer> temp = new ArrayList<Integer>();
		for(User u : members) 
			temp.add(u.qq);
		return temp;
    }
	
    public void removeAllMember() {
    	members.clear();
    }
    
	public void addMember(User member) {
		if(!members.contains(member))
			members.add(member);
	}
	
	public void removeMember(User member) {
		members.remove(member);
	}
	
	public int getParentClusterId() {
		return (parentCluster == null) ? 0 : parentCluster.clusterId;
	}

	/**
	 * @return Returns the clusterType.
	 */
	public int getClusterType() {
		return clusterType;
	}

	/**
	 * @param clusterType The clusterType to set.
	 */
	public void setClusterType(int clusterType) {
		this.clusterType = clusterType;
	}

	/**
	 * @return Returns the startingPage.
	 */
	public String getStartingPage() {
		return startingPage;
	}

	/**
	 * @param startingPage The startingPage to set.
	 */
	public void setStartingPage(String startingPage) {
		this.startingPage = startingPage;
	}

	/**
	 * @return Returns the authType.
	 */
	public byte getAuthType() {
		return authType;
	}

	/**
	 * @param authType The authType to set.
	 */
	public void setAuthType(byte authType) {
		this.authType = authType;
	}

	/**
	 * @return Returns the category.
	 */
	public int getCategory() {
		return category;
	}

	/**
	 * @param category The category to set.
	 */
	public void setCategory(int category) {
		this.category = category;
	}

	/**
	 * @return Returns the description.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description The description to set.
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name The name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return Returns the notice.
	 */
	public String getNotice() {
		return notice;
	}

	/**
	 * @param notice The notice to set.
	 */
	public void setNotice(String notice) {
		this.notice = notice;
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
	 * @return Returns the members.
	 */
	public List<User> getMembers() {
		return members;
	}

	/**
	 * @param members The members to set.
	 */
	public void setMembers(List<User> members) {
		this.members = members;
	}
}
