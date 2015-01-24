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

import static edu.tsinghua.lumaqq.resource.Messages.*;

import java.io.File;
import java.io.IOException;

import edu.tsinghua.lumaqq.ecore.EcoreFactory;
import edu.tsinghua.lumaqq.ecore.LoginOption;
import edu.tsinghua.lumaqq.ecore.ProxyType;
import edu.tsinghua.lumaqq.ecore.face.FaceConstant;
import edu.tsinghua.lumaqq.ecore.face.FaceFactory;
import edu.tsinghua.lumaqq.ecore.face.FaceGroup;
import edu.tsinghua.lumaqq.ecore.face.Faces;
import edu.tsinghua.lumaqq.ecore.group.GroupFactory;
import edu.tsinghua.lumaqq.ecore.group.XCluster;
import edu.tsinghua.lumaqq.ecore.group.XGroup;
import edu.tsinghua.lumaqq.ecore.group.XGroups;
import edu.tsinghua.lumaqq.ecore.group.XOrganization;
import edu.tsinghua.lumaqq.ecore.group.XUser;
import edu.tsinghua.lumaqq.ecore.login.LoginFactory;
import edu.tsinghua.lumaqq.ecore.login.Logins;
import edu.tsinghua.lumaqq.ecore.option.GUIOption;
import edu.tsinghua.lumaqq.ecore.option.KeyOption;
import edu.tsinghua.lumaqq.ecore.option.MessageOption;
import edu.tsinghua.lumaqq.ecore.option.OpType;
import edu.tsinghua.lumaqq.ecore.option.OptionFactory;
import edu.tsinghua.lumaqq.ecore.option.Options;
import edu.tsinghua.lumaqq.ecore.option.OtherOption;
import edu.tsinghua.lumaqq.ecore.option.SyncOption;
import edu.tsinghua.lumaqq.eutil.FaceUtil;
import edu.tsinghua.lumaqq.eutil.GroupUtil;
import edu.tsinghua.lumaqq.eutil.LoginUtil;
import edu.tsinghua.lumaqq.eutil.OptionUtil;
import edu.tsinghua.lumaqq.qq.beans.ContactInfo;
import edu.tsinghua.lumaqq.qq.beans.QQFriend;

/**
 * 这个类负责提供一些创建数据的方法，纯粹是为了免得个别的文件行数太多，
 * 所以把这些集中操作底层数据的方法抽出来放在一个工具类里
 * 
 * @author luma
 */
public class ModelUtils {	    
	/**
	 * 从QQFriend类创建一个contact info结构
	 * @param friend QQFriend
	 * @return ContactInfo
	 */
	public static ContactInfo createContact(QQFriend friend) {
		ContactInfo ret = new ContactInfo();
		ret.nick = friend.nick;		
		ret.qq = friend.qqNum;		
		ret.age = friend.age;
		if(friend.isGG())
			ret.gender = gender_gg;
		else
			ret.gender = gender_mm;
		return ret;
	}
	
	/**
	 * 从User创建一个ContactInfo
	 * 
	 * @param u
	 * 		User对象
	 * @return
	 * 		ContactInfo对象
	 */
	public static ContactInfo createContact(User u) {
		ContactInfo ret = new ContactInfo();
		ret.nick = u.nick;	
		ret.qq = u.qq;		
		ret.head = u.headId;
		return ret;
	}

	/**
	 * 从Friend元素创建一个ContactInfo对象
	 * 
	 * @param user 
	 * 		Friend元素对象
	 * @return
	 * 		ContactInfo
	 */
	public static ContactInfo createContact(XUser user) {
		ContactInfo ret = new ContactInfo();
		ret.qq = user.getQq();
		ret.nick = user.getNick();
		ret.head = user.getHeadId();
		return ret;
	}	
	
	/**
	 * 从XML元素中创建Group
	 * 
	 * @param group 
	 * 		XGroup元素对象
	 * @return 
	 * 		Group对象
	 */
	public static Group createGroup(XGroup group) {
		Group ret = new Group();
		ret.name = group.getName();
		return ret;
	}
	
	/**
	 * 从XUser元素创建一个User
	 * 
	 * @param user 
	 * 		XUser 
	 * @return 
	 * 		User
	 */
	public static User createUser(XUser user) {
		User ret = new User();
		ret.qq = user.getQq();
		if(ret.qq == 0)
			return null;
		ret.nick = user.getNick();
		ret.cardName = user.getCardName();
		ret.displayName = ret.nick;
		ret.headId = user.getHeadId();
		ret.windowX = user.getWindowX();
		ret.windowY = user.getWindowY();
		ret.member = user.isMember();
		ret.status = Status.OFFLINE;
		ret.pinned = user.isPinned();
		ret.userFlag = user.getUserFlag();
		ret.organizationId = user.getOrganizationId(); 
		ret.info = createContact(user);
		ret.talkMode = user.isTalkMode();
		ret.lastMessageTime = user.getLastMessageTime();
		ret.signature = user.getSignature();
		ret.level = user.getLevel();
		ret.female = user.isFemale();
		ret.hasCustomHead = user.isHasCustomHead();
		ret.customHeadId = user.getCustomHeadId();
		ret.customHeadTimestamp = user.getCustomHeadTimestamp();
		return ret;
	}	
	
	/**
	 * 从User对象创建一个XUser元素对象
	 * 
	 * @param u
	 * 		User
	 * @return
	 * 		XUser
	 */
	public static XUser createXUser(User u) {
		XUser ret = GroupFactory.eINSTANCE.createXUser();
		ret.setQq(u.qq);
		ret.setNick(u.nick);
		ret.setHeadId(u.headId);
		ret.setWindowX(u.windowX);
		ret.setWindowY(u.windowY);
		ret.setMember(u.member);
		ret.setTalkMode(u.talkMode);
		ret.setOrganizationId(u.organizationId);
		ret.setPinned(u.pinned);
		ret.setCardName(u.cardName);
		ret.setUserFlag(u.userFlag);
		ret.setLastMessageTime(u.lastMessageTime);
		ret.setSignature(u.signature);
		ret.setLevel(u.level);
		ret.setFemale(u.female);
		ret.setHasCustomHead(u.hasCustomHead);
		ret.setCustomHeadId(u.customHeadId);
		ret.setCustomHeadTimestamp(u.customHeadTimestamp);
		return ret;
	}
	
	/**
	 * 创建XOrganization元素对象
	 * 
	 * @param org
	 * @return
	 */
	public static XOrganization createXOrganization(Organization org) {
		XOrganization ret = GroupFactory.eINSTANCE.createXOrganization();
		ret.setId(org.id);
		ret.setName(org.name);
		ret.setPath(org.path);
		return ret;
	}
	
	/**
	 * 创建Organization对象
	 * 
	 * @param org
	 * @return
	 */
	public static Organization createOrganization(XOrganization org) {
		Organization ret = new Organization();
		ret.id = org.getId();
		ret.path = org.getPath();
		ret.name = org.getName();
		return ret;
	}
	
	/**
	 * 从QQFriend类创建一个User
	 * 
	 * @param friend
	 * 		QQFriend对象
	 * @return
	 * 		User
	 */
	public static User createUser(QQFriend friend) {
		User ret = new User();
		ret.headId = friend.head;
		ret.status = Status.OFFLINE;
		ret.qq = friend.qqNum;
		ret.nick = friend.nick;
		ret.displayName = ret.nick;
		ret.member = friend.isMember();
		ret.info = createContact(friend);
		ret.userFlag = friend.userFlag;
		ret.female = !friend.isGG();
		return ret;
	}
	
	/**
	 * 创建群的Model
	 * 
	 * @param cluster
	 * 		XCluster
	 * @return
	 * 		Cluster
	 */
	public static Cluster createCluster(XCluster cluster) {
		Cluster ret = new Cluster();
		ret.clusterId = cluster.getClusterId();
		if(ret.clusterId == -1)
			return null;
		ret.externalId = cluster.getExternalId();
		if(ret.externalId == -1)
			return null;
		ret.parentClusterId = cluster.getParentClusterId();		
		ret.headId = cluster.getHeadId();
		ret.name = cluster.getName();
		ret.creator = cluster.getCreator();
		ret.clusterType = ClusterType.valueOf(cluster.getType());
		ret.messageSetting = MessageSetting.valueOf(cluster.getMessageSetting());
		ret.authType = (byte)cluster.getAuthType();
		ret.category = cluster.getCategory();
		ret.oldCategory = cluster.getOldCategory();
		ret.description = cluster.getDescription();
		ret.notice = cluster.getNotice();
		ret.lastMessageTime = cluster.getLastMessageTime();
		ret.parseAdminQQString(cluster.getAdmins());
		ret.parseStockholderQQString(cluster.getStockholders());
		return ret;
	}
	    
    /**
     * 创建缺省组文件
     * @param file 目的文件
     * @throws IOException 创建失败
     */
	@SuppressWarnings("unchecked")
	public static void createDefaultGroupXmlFile(File file) throws IOException {
    	file.getParentFile().mkdirs();
        file.createNewFile();
        XGroups groups = GroupFactory.eINSTANCE.createXGroups();
        // 我的好友
        XGroup group = GroupFactory.eINSTANCE.createXGroup();
        group.setName(group_default_friend);
        group.setType(GroupType.DEFAULT_FRIEND_GROUP.toString());
        groups.getGroup().add(group);
        // 群/校友录
        group = GroupFactory.eINSTANCE.createXGroup();
        group.setName(group_default_cluster);
        group.setType(GroupType.CLUSTER_GROUP.toString());
        groups.getGroup().add(group);
        // 陌生人
        group = GroupFactory.eINSTANCE.createXGroup();
        group.setName(group_default_stranger);
        group.setType(GroupType.STRANGER_GROUP.toString());
        groups.getGroup().add(group);
        // 黑名单
        group = GroupFactory.eINSTANCE.createXGroup();
        group.setName(group_default_blacklist);
        group.setType(GroupType.BLACKLIST_GROUP.toString());
        groups.getGroup().add(group);
        // 最近联系人
        group = GroupFactory.eINSTANCE.createXGroup();
        group.setName(group_default_latest);
        group.setType(GroupType.LATEST_GROUP.toString());
        groups.getGroup().add(group);
        // 保存
        GroupUtil.save(file, groups);
    }
	
	/**
     * 创建缺省登陆历史信息文件
     * @param file
     */
	public static void createDefaultLoginXmlFile(File file){
		Logins logins = createDefaultLogins();
        LoginUtil.save(file, logins);
    }
	
	/**
	 * 创建缺省的Logins对象
	 * 
	 * @return
	 */
	public static Logins createDefaultLogins() {
		Logins logins = LoginFactory.eINSTANCE.createLogins();
		logins.setLastLogin("");
		LoginOption lo = EcoreFactory.eINSTANCE.createLoginOption();
		lo.setUseTcp(true);
		lo.setAutoSelect(true);
		lo.setServer("");
		lo.setProxyType(ProxyType.NONE_LITERAL);
		lo.setProxyServer("");
		lo.setProxyPort(0);
		lo.setProxyUsername("");
		lo.setProxyPassword("");
		lo.setTcpPort(80);
		logins.setNetwork(lo);
		return logins;
	}

	/**
	 * 从Group创建一个XGroup元素对象，这相当于createGroup的反方法
	 * 
	 * @param group
	 * 		Group
	 * @return 
	 * 		XGroup元素对象
	 */
	public static XGroup createXGroup(Group group) {
		XGroup ret = GroupFactory.eINSTANCE.createXGroup();
		ret.setName(group.name);
		ret.setType(group.groupType.toString());
		return ret;
	}

	/**
	 * 从User创建XUser元素，相当于createUser的反方法
	 * 
	 * @param user
	 * 		User
	 * @return 
	 * 		XUser元素对象
	 */
	public static XUser createFriendElement(User user) {
		XUser ret = GroupFactory.eINSTANCE.createXUser();
		ContactInfo info = user.info;
		if(info == null)
			info = new ContactInfo();
		ret.setQq(user.qq);
		ret.setNick(user.nick);
		ret.setHeadId(user.headId);
		ret.setMember(user.member);
		ret.setWindowX(user.windowX);
		ret.setWindowY(user.windowY);
		ret.setTalkMode(user.talkMode);
		return ret;
	}
	
	/**
	 * 从Cluster对象创建一个XCluster元素
	 * 
	 * @param c
	 */
	public static XCluster createXCluster(Cluster c) {
		XCluster ret = GroupFactory.eINSTANCE.createXCluster();
		ret.setClusterId(c.clusterId);
		ret.setExternalId(c.externalId);
		ret.setParentClusterId(c.parentClusterId);
		ret.setName(c.name);
		ret.setHeadId(c.headId);
		ret.setMessageSetting(c.messageSetting.toString());
		ret.setType(c.clusterType.toString());
		ret.setCreator(c.creator);
		ret.setAuthType(c.authType);
		ret.setCategory(c.category);
		ret.setOldCategory(c.oldCategory);
		ret.setDescription(c.description);
		ret.setNotice(c.notice);
		ret.setAdmins(c.getAdminQQString());
		ret.setStockholders(c.getStockholderQQString());
		ret.setLastMessageTime(c.lastMessageTime);
		return ret;
	}
	
	/**
	 * 创建一个缺省的表情配置文件
	 * 
	 * @param file
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public static void createDefaultFaceFile(File file) throws IOException {
		Faces faces = FaceFactory.eINSTANCE.createFaces();
		faces.setNextId(33);
		faces.setNextGroupId(3);
		
		FaceGroup defaultGroup = FaceFactory.eINSTANCE.createFaceGroup();
		defaultGroup.setName(face_group_default);
		defaultGroup.setId(FaceConstant.DEFAULT_GROUP_ID);
		faces.getGroup().add(defaultGroup);
		
		FaceGroup recvGroup = FaceFactory.eINSTANCE.createFaceGroup();
		recvGroup.setName(face_group_recv);
		recvGroup.setId(FaceConstant.RECEIVED_GROUP_ID);
		faces.getGroup().add(recvGroup);
		
		FaceGroup chGroup = FaceFactory.eINSTANCE.createFaceGroup();
		chGroup.setName(face_group_custom_head);
		chGroup.setId(FaceConstant.CUSTOM_HEAD_GROUP_ID);
		faces.getGroup().add(chGroup);
		
		FaceUtil.save(file, faces);
	}

	/**
	 * 创建一个缺省的系统设置文件
	 * @param file
	 */
	public static void createDefaultSysOptFile(File file) throws IOException {
		Options options = OptionFactory.eINSTANCE.createOptions();
		// 登陆设置
		LoginOption lo = EcoreFactory.eINSTANCE.createLoginOption();
		lo.setUseTcp(true);
		lo.setAutoSelect(true);
		lo.setServer("");
		lo.setProxyType(ProxyType.NONE_LITERAL);
		lo.setProxyServer("");
		lo.setProxyPort(0);
		lo.setProxyUsername("");
		lo.setProxyPassword("");
		lo.setTcpPort(80);
		options.setLoginOption(lo);
		// 界面设置
		GUIOption go = OptionFactory.eINSTANCE.createGUIOption();
		go.setLocationX(50);
		go.setLocationY(50);
		go.setWidth(-1);
		go.setHeight(-1);
		go.setShowNick(false);
		go.setShowSmallHead(false);
		go.setShowFriendTip(true);
		go.setShowOnlineOnly(false);		
		go.setShowOnlineTip(true);
		go.setShowLastLoginTip(true);
		go.setTreeMode(true);
		go.setOnlineTipLocationX(-1);
		go.setOnlineTipLocationY(-1);
		go.setFontName(default_font);
		go.setFontSize(9);
		go.setBold(false);
		go.setItalic(false);
		go.setFontColor(0);
		go.setGroupBackground(0xFFFFFF);
		go.setShowBlacklist(true);
		go.setAutoDock(true);
		go.setUseTabIMWindow(false);	
		go.setShowSignature(true);
		go.setShowCustomHead(true);
		go.setMinimizeWhenClose(false);
		go.setOnTop(true);
		go.setImOnTop(false);
		go.setHideWhenMinimize(false);
		go.setBarExpanded(false);
		options.setGuiOption(go);		
		// 消息设置
		MessageOption mo = OptionFactory.eINSTANCE.createMessageOption();
		mo.setAutoEject(false);
		mo.setEnableSound(true);
		mo.setRejectStranger(false);
		mo.setRejectTempSessionIM(false);
		mo.setUseEnterInMessageMode(false);
		mo.setUseEnterInTalkMode(false);
		options.setMessageOption(mo);
		// 热键设置
		KeyOption ko = OptionFactory.eINSTANCE.createKeyOption();
		ko.setMessageKey("<Ctrl><Alt>Z");
		options.setKeyOption(ko);
		// 同步设置
		SyncOption so = OptionFactory.eINSTANCE.createSyncOption();
		so.setAutoDownloadGroup(true);
		so.setAutoUploadGroup(OpType.PROMPT_LITERAL);
		so.setAutoDownloadFriendRemark(true);
		so.setAutoCheckUpdate(false);
		options.setSyncOption(so);
		// 其他设置
		OtherOption oo = OptionFactory.eINSTANCE.createOtherOption();
		oo.setBrowser("");
		oo.setKeepStrangerInLatest(false);
		oo.setLatestSize(20);
		oo.setEnableLatest(true);
		oo.setShowFakeCam(false);
		options.setOtherOption(oo);
		// 写入文件
		OptionUtil.save(file, options);
	}
}
