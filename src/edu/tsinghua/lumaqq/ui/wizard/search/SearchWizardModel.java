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
package edu.tsinghua.lumaqq.ui.wizard.search;

import edu.tsinghua.lumaqq.qq.beans.AdvancedUserInfo;
import edu.tsinghua.lumaqq.qq.beans.ClusterInfo;
import edu.tsinghua.lumaqq.qq.beans.UserInfo;

/**
 * 查找向导信息封装bean
 * 
 * @author luma
 */
public class SearchWizardModel {
    /** 查找用户 */
    public static final int USER = 0;
    /** 查找群 */
    public static final int CLUSTER = 1;
    
    // 页名称
    public static final String PAGE_SEARCH_WHAT = "1";
    public static final String PAGE_HOW_TO_SEARCH_USER = "2";
    public static final String PAGE_HOW_TO_SEARCH_CLUSTER = "3";
    public static final String PAGE_SEARCH_USER_ACCURATE = "4";
    public static final String PAGE_SEARCH_USER_ADVANCED = "5";
    public static final String PAGE_SEARCH_USER_RESULT = "6";
    public static final String PAGE_SEARCH_CLUSTER_RESULT = "7";
    public static final String PAGE_ADD = "8";
    
    // 用户搜索模式
    public static final int ONLINE = 0;
    public static final int ACCURATE = 1;
    public static final int ADVANCED = 2;
    
    // 群搜索模式
    public static final int DEMO_CLUSTER = 0;
    public static final int BY_CLUSTER_ID = 1;
    public static final int BY_CATEGORY = 2;
    
    // 添加页的状态
    public static final int INIT = -1;
    public static final int ADDING = 0;
    public static final int AUTH_INPUTING = 1;
    public static final int AUTH_SENDING = 2;
    public static final int ADD_TIMEOUT = 3;
    public static final int ADD_FINISHED = 4;
    public static final int AUTH_SENT = 5;
    public static final int ADD_DENY = 6;
    public static final int JOIN_TIMEOUT = 7;
    public static final int JOIN_DENY = 8;
    public static final int AUTH_TIMEOUT = 9;
    public static final int JOINING = 10;
    public static final int JOIN_FINISHED = 11;
    
    private int searchWhat;
    private int userSearchMode;
    private int clusterSearchMode;    
    private String startingPage;
    
    // 根据id搜索群时要用到
    private int clusterId;
    
    // 根据分类查找群时
    private int categoryId;
    
    // 精确查找用户时
    private String qq;
    private String email;
    private String nick;
    
    // 高级查找用户时
    private boolean online;
    private boolean withCam;
    private int province;
    private int city;
    private int age;
    private int gender;
    
    // 添加页的状态
    private int status;
    
    // 选择的要添加的对象
    private Object selectedModel;
    
    private boolean enterResultPage;

    /**
     * 构造函数
     */
    public SearchWizardModel() {
    	searchWhat = USER;
    	userSearchMode = ONLINE;
    	clusterSearchMode = BY_CLUSTER_ID;
    	startingPage = PAGE_SEARCH_WHAT;
    	online = true;
    	withCam = false;
    	province = city = age = gender = 0;
    	status = INIT;
        enterResultPage = true;
        categoryId = 0;
	}
    
	/**
	 * @return
	 * 		得到一个标志model的id，对于用户，就是qq号，对于群，就是内部id
	 */
	public int getSelectedModelId() {
	    if(selectedModel == null)
	        return 0;
	    
	    if(selectedModel instanceof UserInfo)
	        return ((UserInfo)selectedModel).qqNum;
	    
	    if(selectedModel instanceof AdvancedUserInfo)
	        return ((AdvancedUserInfo)selectedModel).qqNum;
	    
	    if(selectedModel instanceof ClusterInfo)
	        return ((ClusterInfo)selectedModel).clusterId;
	    
	    return 0;
	}
    
	/**
	 * @return Returns the searchWhat.
	 */
	public int getSearchWhat() {
		return searchWhat;
	}

	/**
	 * @param searchWhat The searchWhat to set.
	 */
	public void setSearchWhat(int searchWhat) {
		this.searchWhat = searchWhat;
	}

	/**
	 * @return Returns the searchMode.
	 */
	public int getUserSearchMode() {
		return userSearchMode;
	}

	/**
	 * @param searchMode The searchMode to set.
	 */
	public void setUserSearchMode(int searchMode) {
		this.userSearchMode = searchMode;
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
	 * @return Returns the clusterSearchMode.
	 */
	public int getClusterSearchMode() {
		return clusterSearchMode;
	}

	/**
	 * @param clusterSearchMode The clusterSearchMode to set.
	 */
	public void setClusterSearchMode(int clusterSearchMode) {
		this.clusterSearchMode = clusterSearchMode;
	}

	/**
	 * @return Returns the clusterId.
	 */
	public int getClusterId() {
		return clusterId;
	}

	/**
	 * @param clusterId The clusterId to set.
	 */
	public void setClusterId(int clusterId) {
		this.clusterId = clusterId;
	}

	/**
	 * @return Returns the email.
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email The email to set.
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return Returns the nick.
	 */
	public String getNick() {
		return nick;
	}

	/**
	 * @param nick The nick to set.
	 */
	public void setNick(String nick) {
		this.nick = nick;
	}

	/**
	 * @return Returns the qq.
	 */
	public String getQQ() {
		return qq;
	}

	/**
	 * @param qq The qq to set.
	 */
	public void setQQ(String qq) {
		this.qq = qq;
	}

	/**
	 * @return Returns the age.
	 */
	public int getAge() {
		return age;
	}

	/**
	 * @param age The age to set.
	 */
	public void setAge(int age) {
		this.age = age;
	}

	/**
	 * @return Returns the city.
	 */
	public int getCity() {
		return city;
	}

	/**
	 * @param city The city to set.
	 */
	public void setCity(int city) {
		this.city = city;
	}

	/**
	 * @return Returns the gender.
	 */
	public int getGender() {
		return gender;
	}

	/**
	 * @param gender The gender to set.
	 */
	public void setGender(int gender) {
		this.gender = gender;
	}

	/**
	 * @return Returns the online.
	 */
	public boolean isOnline() {
		return online;
	}

	/**
	 * @param online The online to set.
	 */
	public void setOnline(boolean online) {
		this.online = online;
	}

	/**
	 * @return Returns the province.
	 */
	public int getProvince() {
		return province;
	}

	/**
	 * @param province The province to set.
	 */
	public void setProvince(int province) {
		this.province = province;
	}

	/**
	 * @return Returns the withCam.
	 */
	public boolean isWithCam() {
		return withCam;
	}

	/**
	 * @param withCam The withCam to set.
	 */
	public void setWithCam(boolean withCam) {
		this.withCam = withCam;
	}

	/**
	 * @return Returns the status.
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * @param status The status to set.
	 */
	public void setStatus(int status) {
		this.status = status;
	}

	/**
	 * @return Returns the selectedModel.
	 */
	public Object getSelectedModel() {
		return selectedModel;
	}

	/**
	 * @param selectedModel The selectedModel to set.
	 */
	public void setSelectedModel(Object selectedModel) {
		this.selectedModel = selectedModel;
	}

	/**
	 * @return Returns the enterResultPage.
	 */
	public boolean isEnterResultPage() {
		return enterResultPage;
	}

	/**
	 * @param enterResultPage The enterResultPage to set.
	 */
	public void setEnterResultPage(boolean enterResultPage) {
		this.enterResultPage = enterResultPage;
	}

	/**
	 * @return Returns the categoryId.
	 */
	public int getCategoryId() {
		return categoryId;
	}

	/**
	 * @param categoryId The categoryId to set.
	 */
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
}
