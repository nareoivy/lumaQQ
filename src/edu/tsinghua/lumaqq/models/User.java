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

import static edu.tsinghua.lumaqq.models.Status.AWAY;
import static edu.tsinghua.lumaqq.models.Status.HIDDEN;
import static edu.tsinghua.lumaqq.models.Status.OFFLINE;
import static edu.tsinghua.lumaqq.models.Status.ONLINE;
import edu.tsinghua.lumaqq.ecore.remark.Remark;
import edu.tsinghua.lumaqq.qq.QQ;
import edu.tsinghua.lumaqq.qq.beans.ContactInfo;

/**
 * 用户model
 * 
 * @author luma
 */
public class User extends Model implements Cloneable {
	// QQ号
	public int qq;
	// 头像ID
	public int headId;
	// 昵称
	public String nick;
	// 备注
	public Remark remark;
	// 详细资料
	public ContactInfo info;
	// 是否聊天模式
	public boolean talkMode;
	// 消息窗口X位置
	public int windowX;
	// 消息窗口Y位置
	public int windowY;
	// 状态
	public Status status;
	// 是否有消息未读
	public boolean hasMessage;
	// 上次收到消息的时间，用来给最近联系人排序
	public long lastMessageTime;
	// 是否会员
	public boolean member;
	// 显示名
	public String displayName;
	// ip
	public byte[] ip;
	// 端口
	public int port;
	// 所属的群，没有则为null
	public Cluster cluster;
	// 所属组
	public Group group;
	// 所属组织序号
	public int organizationId;
	// 是否置顶
	public boolean pinned;
	// 是否美眉
	public boolean female;
	// 用户属性标志
	public int userFlag;
	// 等级
	public int level;
	// 群名片信息
	public String cardName;
	public String cardEmail;
	public String cardRemark;
	public int cardGenderIndex;
	public String cardPhone;
	// 个性签名
	public String signature;
	public int signatureModifiedTime;
	public boolean hasSignature;
	// 自定义头像
	public boolean hasCustomHead;
	public int customHeadId;
	public int customHeadTimestamp;
	
	public User() {
		super(Type.USER);
		initializeValues();
	}
	
	/**
	 * 初始化字段
	 */
	private void initializeValues() {
		qq = 0;
		headId = 0;
		nick = EMPTY_STRING;
		remark = null;
		group = null;
		info = null;
		talkMode = true;
		windowX = 150;
		windowY = 150;
		status = OFFLINE;
		hasMessage = false;
		lastMessageTime = 0;
		member = false;
		displayName = EMPTY_STRING;
		ip = null;
		port = 0;
		cluster = null;
		organizationId = 0;
		pinned = false;
		cardName = EMPTY_STRING;
		cardEmail = EMPTY_STRING;
		cardPhone = EMPTY_STRING;
		cardRemark = EMPTY_STRING;
		cardGenderIndex = 2;
		userFlag = 0;
		female = false;
		signature = EMPTY_STRING;
		signatureModifiedTime = 0;
		hasSignature = false;
		hasCustomHead = false;
		customHeadId = 0;
		customHeadTimestamp = 0;
		level = 0;
	}
	
	/**
	 * 复制基本信息字段，不复制比如group这样的字段
	 */
	public void infoCopy(User f) {
		status = f.status;
		info = f.info;
		displayName = f.displayName;
		cardName = f.cardName;
		cardGenderIndex = f.cardGenderIndex;
		cardEmail = f.cardEmail;
		cardPhone = f.cardPhone;
		cardRemark = f.cardRemark;
		headId = f.headId;
		ip = f.ip;
		port = f.port;
		nick = f.nick;
		talkMode = f.talkMode;
		userFlag = f.userFlag;
		female = f.female;
		windowX = f.windowX;
		windowY = f.windowY;
		member = f.member;
		signature = f.signature;
		signatureModifiedTime = f.signatureModifiedTime;
		hasSignature = f.hasSignature;
		hasCustomHead = f.hasCustomHead;
		customHeadId = f.customHeadId;
		customHeadTimestamp = f.customHeadTimestamp;
		level = f.level;
		lastMessageTime = f.lastMessageTime;
	}

    /**
     * @return true如果好友是会员，否则为false
     */
    public boolean isMember() {
    	return (userFlag & QQ.QQ_FLAG_MEMBER) != 0;
    }
    
    /**
     * 是否绑定手机
     * 
     * @return
     */
    public boolean isBind() {
        return (userFlag & QQ.QQ_FLAG_BIND) != 0;
    }
    
    /**
     * @return
     * 		true表示是移动QQ
     */
    public boolean isMobile() {
    	return (userFlag & QQ.QQ_FLAG_MOBILE) != 0 && isBind();
    }
    
    /**
     * @return
     * 		true表示用户有摄像头
     */
    public boolean hasCam() {
    	return (userFlag & QQ.QQ_FLAG_CAM) != 0;
    }
    
    /**
     * @return
     * 		true表示用户使用TM登录
     */
    public boolean isTM() {
    	return (userFlag & QQ.QQ_FLAG_TM) != 0;
    }
	
	@Override
	public boolean equals(Object arg0) {
		if(arg0 instanceof User) {
			User u = (User)arg0;
			return u.qq == qq;
		} else
			return false;
	}
	
	@Override
	public int hashCode() {
		return qq;
	}
	
	/**
	 * 检查用户是否我的好友，这个不考虑重复的model
	 * 
	 * @return	
	 * 		true表示是好友
	 */
	public boolean isFriend() {
		User u = ModelRegistry.getUser(qq);
		if(u == null)
			return false;
		switch(u.group.groupType) {
			case DEFAULT_FRIEND_GROUP:
			case FRIEND_GROUP:
				return true;
			default:
				return false;
		}
	}
	
	/**
	 * @return
	 * 		true表示这个用户model在一个好友组内
	 */
	public boolean isInFriendGroup() {
		switch(group.groupType) {
			case DEFAULT_FRIEND_GROUP:
			case FRIEND_GROUP:
				return true;
			default:
				return false;
		}
	}
	
	/**
	 * 检查是否对用户可以发消息，可以发消息是指他是好友或者是陌生人
	 * 
	 * @return
	 * 		true表示可以
	 */
	public boolean isContactable() {
		User u = ModelRegistry.getUser(qq);
		if(u == null)
			return false;
		switch(u.group.groupType) {
			case DEFAULT_FRIEND_GROUP:
			case FRIEND_GROUP:
			case STRANGER_GROUP:
			case LATEST_GROUP:
				return true;
			default:
				return false;
		}
	}
	
	/**
	 * 把自己从父model中删除
	 */
	public void removeSelfFromParent() {
		if(group == null)
			return;
		group.removeUser(this);
	}
	
	public boolean hasRemarkName() {
		return remark != null && remark.getName() != null && !remark.getName().trim().equals(EMPTY_STRING);
	}
	
	public String getRemarkName() {
		if(remark == null)
			return EMPTY_STRING;
		else
			return remark.getName();
	}
	
	public boolean isOnline() {
		return status == ONLINE;
	}
	
	public boolean isOffline() {
		return status == OFFLINE;
	}
	
	public boolean isAway() {
		return status == AWAY;
	}
	
	public boolean isHidden() {
		return status == HIDDEN;
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		User u = new User();
		u.qq = qq;
		u.headId = headId;
		u.nick = nick;
		u.remark = remark;
		u.group = null;
		u.info = info;
		u.talkMode = talkMode;
		u.windowX = windowX;
		u.windowY = windowY;
		u.status = status;
		u.hasMessage = hasMessage;
		u.member = member;
		u.displayName = displayName;
		u.ip = ip;
		u.port = port;
		u.cluster = null;
		u.displayName = displayName;
		u.cardName = cardName;
		u.cardEmail = cardEmail;
		u.cardGenderIndex = cardGenderIndex;
		u.cardPhone = cardPhone;
		u.cardRemark = cardRemark;
		return u;
	}
	
	/**
	 * @return
	 * 		true表示用户有群名片名称
	 */
	public boolean hasCardName() {
		return cardName != null && !cardName.equals(EMPTY_STRING);
	}
}
