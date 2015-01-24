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
package edu.tsinghua.lumaqq.qq;

import static edu.tsinghua.lumaqq.qq.QQPort.*;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import edu.tsinghua.lumaqq.qq.beans.Card;
import edu.tsinghua.lumaqq.qq.beans.ContactInfo;
import edu.tsinghua.lumaqq.qq.beans.FriendRemark;
import edu.tsinghua.lumaqq.qq.beans.Member;
import edu.tsinghua.lumaqq.qq.beans.QQOrganization;
import edu.tsinghua.lumaqq.qq.beans.QQUser;
import edu.tsinghua.lumaqq.qq.beans.Signature;
import edu.tsinghua.lumaqq.qq.events.IQQListener;
import edu.tsinghua.lumaqq.qq.events.PacketEvent;
import edu.tsinghua.lumaqq.qq.events.QQEvent;
import edu.tsinghua.lumaqq.qq.net.ConnectionPolicyFactory;
import edu.tsinghua.lumaqq.qq.net.IConnection;
import edu.tsinghua.lumaqq.qq.net.IConnectionPolicy;
import edu.tsinghua.lumaqq.qq.net.IConnectionPolicyFactory;
import edu.tsinghua.lumaqq.qq.net.IConnectionPool;
import edu.tsinghua.lumaqq.qq.net.IConnectionPoolFactory;
import edu.tsinghua.lumaqq.qq.packets.ErrorPacket;
import edu.tsinghua.lumaqq.qq.packets.InPacket;
import edu.tsinghua.lumaqq.qq.packets.OutPacket;
import edu.tsinghua.lumaqq.qq.packets.Packet;
import edu.tsinghua.lumaqq.qq.packets.in.ReceiveIMPacket;
import edu.tsinghua.lumaqq.qq.packets.out.AddFriendAuthResponsePacket;
import edu.tsinghua.lumaqq.qq.packets.out.AddFriendExPacket;
import edu.tsinghua.lumaqq.qq.packets.out.AdvancedSearchUserPacket;
import edu.tsinghua.lumaqq.qq.packets.out.AuthorizePacket;
import edu.tsinghua.lumaqq.qq.packets.out.ChangeStatusPacket;
import edu.tsinghua.lumaqq.qq.packets.out.ClusterActivatePacket;
import edu.tsinghua.lumaqq.qq.packets.out.ClusterActivateTempPacket;
import edu.tsinghua.lumaqq.qq.packets.out.ClusterAuthPacket;
import edu.tsinghua.lumaqq.qq.packets.out.ClusterCommitMemberOrganizationPacket;
import edu.tsinghua.lumaqq.qq.packets.out.ClusterCommitOrganizationPacket;
import edu.tsinghua.lumaqq.qq.packets.out.ClusterCreatePacket;
import edu.tsinghua.lumaqq.qq.packets.out.ClusterCreateTempPacket;
import edu.tsinghua.lumaqq.qq.packets.out.ClusterDismissPacket;
import edu.tsinghua.lumaqq.qq.packets.out.ClusterExitPacket;
import edu.tsinghua.lumaqq.qq.packets.out.ClusterExitTempPacket;
import edu.tsinghua.lumaqq.qq.packets.out.ClusterGetCardBatchPacket;
import edu.tsinghua.lumaqq.qq.packets.out.ClusterGetCardPacket;
import edu.tsinghua.lumaqq.qq.packets.out.ClusterGetInfoPacket;
import edu.tsinghua.lumaqq.qq.packets.out.ClusterGetMemberInfoPacket;
import edu.tsinghua.lumaqq.qq.packets.out.ClusterGetOnlineMemberPacket;
import edu.tsinghua.lumaqq.qq.packets.out.ClusterGetTempInfoPacket;
import edu.tsinghua.lumaqq.qq.packets.out.ClusterJoinPacket;
import edu.tsinghua.lumaqq.qq.packets.out.ClusterModifyCardPacket;
import edu.tsinghua.lumaqq.qq.packets.out.ClusterModifyInfoPacket;
import edu.tsinghua.lumaqq.qq.packets.out.ClusterModifyMemberPacket;
import edu.tsinghua.lumaqq.qq.packets.out.ClusterModifyTempInfoPacket;
import edu.tsinghua.lumaqq.qq.packets.out.ClusterModifyTempMemberPacket;
import edu.tsinghua.lumaqq.qq.packets.out.ClusterSearchPacket;
import edu.tsinghua.lumaqq.qq.packets.out.ClusterSendIMExPacket;
import edu.tsinghua.lumaqq.qq.packets.out.ClusterSendTempIMPacket;
import edu.tsinghua.lumaqq.qq.packets.out.ClusterSetRolePacket;
import edu.tsinghua.lumaqq.qq.packets.out.ClusterSubClusterOpPacket;
import edu.tsinghua.lumaqq.qq.packets.out.ClusterTransferRolePacket;
import edu.tsinghua.lumaqq.qq.packets.out.ClusterUpdateOrganizationPacket;
import edu.tsinghua.lumaqq.qq.packets.out.DeleteFriendPacket;
import edu.tsinghua.lumaqq.qq.packets.out.DownloadGroupFriendPacket;
import edu.tsinghua.lumaqq.qq.packets.out.FriendDataOpPacket;
import edu.tsinghua.lumaqq.qq.packets.out.FriendLevelOpPacket;
import edu.tsinghua.lumaqq.qq.packets.out.GetFriendListPacket;
import edu.tsinghua.lumaqq.qq.packets.out.GetKeyPacket;
import edu.tsinghua.lumaqq.qq.packets.out.GetLoginTokenPacket;
import edu.tsinghua.lumaqq.qq.packets.out.GetOnlineOpPacket;
import edu.tsinghua.lumaqq.qq.packets.out.GetUserInfoPacket;
import edu.tsinghua.lumaqq.qq.packets.out.GroupDataOpPacket;
import edu.tsinghua.lumaqq.qq.packets.out.KeepAlivePacket;
import edu.tsinghua.lumaqq.qq.packets.out.LoginPacket;
import edu.tsinghua.lumaqq.qq.packets.out.LogoutPacket;
import edu.tsinghua.lumaqq.qq.packets.out.ModifyInfoPacket;
import edu.tsinghua.lumaqq.qq.packets.out.PrivacyDataOpPacket;
import edu.tsinghua.lumaqq.qq.packets.out.ReceiveIMReplyPacket;
import edu.tsinghua.lumaqq.qq.packets.out.RemoveSelfPacket;
import edu.tsinghua.lumaqq.qq.packets.out.SearchUserPacket;
import edu.tsinghua.lumaqq.qq.packets.out.SendIMPacket;
import edu.tsinghua.lumaqq.qq.packets.out.SendSMSPacket;
import edu.tsinghua.lumaqq.qq.packets.out.SignatureOpPacket;
import edu.tsinghua.lumaqq.qq.packets.out.TempSessionOpPacket;
import edu.tsinghua.lumaqq.qq.packets.out.UploadGroupFriendPacket;
import edu.tsinghua.lumaqq.qq.packets.out.UserPropertyOpPacket;
import edu.tsinghua.lumaqq.qq.packets.out.WeatherOpPacket;
import edu.tsinghua.lumaqq.qq.packets.out._03.GetCustomHeadDataPacket;
import edu.tsinghua.lumaqq.qq.packets.out._03.GetCustomHeadInfoPacket;
import edu.tsinghua.lumaqq.qq.packets.out._05.RequestAgentPacket;
import edu.tsinghua.lumaqq.qq.packets.out._05.RequestBeginPacket;
import edu.tsinghua.lumaqq.qq.packets.out._05.RequestFacePacket;
import edu.tsinghua.lumaqq.qq.packets.out._05.TransferPacket;
import edu.tsinghua.lumaqq.qq.robot.IRobot;

/**
 * QQ的客户端类，这个类为用户提供一个方便的接口，比如发送消息之类的，只需要调用一个
 * 方法便可以完成了。
 * 
 * LumaQQ网络层通过NIO实现，每个Client分配一个Selector，每个Selector可以监听多个
 * Channel，channel包装在IPort的实现类中，对于一个Client，有一个Main Port，这个
 * Main Port定义为连接QQ登陆服务器的那个Port。系统登陆之初之有一个Port即Main Port，
 * 根据需要可以开启其他Port
 * 
 * @author luma
 */
public class QQClient implements IQQListener {    
    /** 
     * 单线程执行器
     */
    protected static final SingleExecutor executor = new SingleExecutor();
    /** 包处理器数目 */
	protected static final int PROCESSOR_COUNT = 4;
    // 登陆的服务器IP
    private String loginServer;
    // TCP方式登录时的服务器端口，UDP方式时无用处
    private int tcpLoginPort;
    // 包处理器路由器
    private ProcessorRouter router;
    // QQ监听器
    private LinkedList<IQQListener> qqListeners, qqListenersBackup;
    // QQ用户
    private QQUser user;
    // 当前是否正在登陆
    private boolean logging;
    // 当前是否在重定向登录
    private boolean loginRedirect;
    // 网络连接池
    private IConnectionPool pool;
    // 网络连接池工厂
    private IConnectionPoolFactory poolFactory;
    // 连接策略工厂
    private IConnectionPolicyFactory policyFactory;
    // 代理类型
    private int proxyType;
    // 代理服务器地址
    private InetSocketAddress proxyAddress;
    // 代理服务器验证用户名，null表示不需要验证
    private String proxyUsername;
    // 代理服务器验证密码
    private String proxyPassword;
    
    // 是否打开了机器人功能
    private boolean robotMode;   
    
    // 机器人实例
    private IRobot robot;
    // 是否监听器有改变，这是为了处理同步问题而设置的，在实际fireQQEvent时，
    // 不使用qqListeners，而是用backup，这样在事件触发期间如果要修改
    // 监听器也是可以的，不然有可能造成并发修改异常。如果有更好的办法当
    // 然，目前只想出这种办法
    private boolean listenerChanged;
    /** 包事件触发过程 */
    protected Callable<Object> packetEventTrigger;
    /** 重发过程 */
    protected ResendTrigger<Object> resendTrigger;
    /** Keep Alive过程 */
    protected Runnable keepAliveTrigger;

	protected ScheduledFuture keepAliveFuture;	
	/** 接收队列 */
	protected Queue<InPacket> receiveQueue;
	
	/** 输入包和port的映射，用来判断一个包来自于哪个port */
	protected Map<InPacket, String> inConn;

    /**
     * 构造函数
     */
    public QQClient() {
        qqListeners = new LinkedList<IQQListener>();    
        qqListenersBackup = new LinkedList<IQQListener>();
        receiveQueue = new LinkedList<InPacket>();
        logging = false;
        loginRedirect = false;
        robotMode = false;
        listenerChanged = false;
        qqListeners.add(this);
        qqListenersBackup.add(this);
        packetEventTrigger = new PacketEventTrigger<Object>(this);
        keepAliveTrigger = new KeepAliveTrigger(this);
        resendTrigger = new ResendTrigger<Object>(this);
        inConn = new HashMap<InPacket, String>();
        policyFactory = new ConnectionPolicyFactory();
        
        router = new ProcessorRouter(this, PROCESSOR_COUNT);
        router.installProcessor(new BasicFamilyProcessor(this));
        router.installProcessor(new _05FamilyProcessor(this));
        router.installProcessor(new _03FamilyProcessor(this));
        
        executor.increaseClient();
    }
    
    /**
	 * 添加一个包到接收队列
	 * @param packet
	 */
    public synchronized void addIncomingPacket(InPacket packet, String portName) {
	    if(packet == null) 
	        return;
		receiveQueue.offer(packet);
		inConn.put(packet, portName);
		executor.submit(packetEventTrigger);
	}

    /**
     * 添加一个QQ事件监听器
     * 
     * @param listener
     * 		QQListener
     */
    public synchronized void addQQListener(IQQListener listener) {
    	if(!qqListeners.contains(listener)) {
	    	qqListeners.add(listener);
	    	setListenerChanged(true);    		
    	}
    }
    
    /**
     * 添加一个包到重发队列
     * 
     * @param packet
     * @param port
     */
    public void addResendPacket(OutPacket packet, String port) {
    	resendTrigger.add(packet, port);
    }
    
    /**
     * 检查监听器是否已经改变，如果是则更新监听器
     */
    private synchronized void checkListenerChange() {
    	if(isListenerChanged()) {
    		qqListenersBackup.clear();
    		qqListenersBackup.addAll(qqListeners);
    		setListenerChanged(false);
    	}
    }
    
    /**
     * 激活临时群
     * 
     * @param type
     * @param clusterId
     * @param parentClusterId
     */
    public void cluster_AactivateTemp(byte type, int clusterId, int parentClusterId) {
    	if(user.isLoggedIn()) {
    		ClusterActivateTempPacket packet = new ClusterActivateTempPacket(user);
    		packet.setClusterId(clusterId);
    		packet.setType(type);
    		packet.setParentClusterId(parentClusterId);
    		pool.send(MAIN.name, packet, true);	
    	}
    }
    
    /**
     * 激活群
     * @param clusterId
     */
    public void cluster_Activate(int clusterId) {
    	if(user.isLoggedIn()) {
    		ClusterActivatePacket packet = new ClusterActivatePacket(user);
    		packet.setClusterId(clusterId);
    		pool.send(MAIN.name, packet, true);	
    	}
    }
    
    /**
     * 同意加入群
     * @param clusterId
     */
    public void cluster_ApproveJoin(int clusterId, int receiver) {
    	if(user.isLoggedIn()) {
    		ClusterAuthPacket packet = new ClusterAuthPacket(user);
    		packet.setType(QQ.QQ_CLUSTER_AUTH_APPROVE);
    		packet.setClusterId(clusterId);
    		packet.setReceiver(receiver);
    		pool.send(MAIN.name, packet, true);	
    	}
    }
    
    /**
     * 提交成员组织
     * 
     * @param clusterId
     * @param members
     * @return
     * 		包序号
     */
    public char cluster_CommitMemberOrganization(int clusterId, List<Member> members) {
    	if(user.isLoggedIn()) {
    		ClusterCommitMemberOrganizationPacket packet = new ClusterCommitMemberOrganizationPacket(user);
    		packet.setClusterId(clusterId);
    		packet.setMembers(members);
    		pool.send(MAIN.name, packet, true);
    		return packet.getSequence();
    	}
    	return 0;
    }
    
    /**
     * 提交组织架构
     * 
     * @param clusterId
     * @param organizations
     */
    public char cluster_CommitOrganization(int clusterId, List<QQOrganization> organizations) {
    	if(user.isLoggedIn()) {
    		ClusterCommitOrganizationPacket packet = new ClusterCommitOrganizationPacket(user);
    		packet.setClusterId(clusterId);
    		packet.setOrganizations(organizations);
    		pool.send(MAIN.name, packet, true);
    		return packet.getSequence();
    	}
    	return 0;
    }
    
    /**
     * 创建一个固定群
     * @param name 群名称
     * @param notice 群声明
     * @param description 群描述
     * @param members 群成员
     * @param authType 群认证类型
     * @param category 群的分类
     */
    public void cluster_CreatePermanent(String name, String notice, String description, List<Integer> members, int category, byte authType) {
    	if(user.isLoggedIn()) {
    		ClusterCreatePacket packet = new ClusterCreatePacket(user);
    		packet.setType(QQ.QQ_CLUSTER_TYPE_PERMANENT);
    		packet.setAuthType(authType);
    		packet.setCategory(category);
    		packet.setName(name);
    		packet.setNotice(notice);
    		packet.setDescription(description);
    		packet.setMembers(members);
    		pool.send(MAIN.name, packet, true);	
    	}
    }
    
    /**
     * 创建一个临时群
     * 
     * @param name
     * 		群名称
     * @param type
     * 		临时群类型
     * @param parentClusterId
     * 		父群内部ID
     * @param members
     * 		成员QQ号数组
     * @return
     * 		包序号
     */
    public char cluster_CreateTemporary(String name, byte type, int parentClusterId, List<Integer> members) {
    	if(user.isLoggedIn()) {
    		ClusterCreateTempPacket packet = new ClusterCreateTempPacket(user);
    		packet.setType(type);
    		packet.setName(name);
    		packet.setMembers(members);
    		packet.setParentClusterId(parentClusterId);
    		pool.send(MAIN.name, packet, true);
    		return packet.getSequence();
    	}
    	return 0;
    }
    
    /**
     * 解散群
     * 
     * @param clusterId
     */
    public void cluster_Dismiss(int clusterId) {
    	if(user.isLoggedIn()) {
    		ClusterDismissPacket packet = new ClusterDismissPacket(user);
    		packet.setClusterId(clusterId);
    		pool.send(MAIN.name, packet, false);	
    	}
    }
    
    /**
     * 退出一个群
     * @param clusterId 群内部ID
     */
    public void cluster_Exit(int clusterId) {
    	if(user.isLoggedIn()) {
    		ClusterExitPacket packet = new ClusterExitPacket(user);
    		packet.setClusterId(clusterId);
    		pool.send(MAIN.name, packet, true);	
    	}
    }
    
    /**
     * 得到某个成员的群名片
     * 
     * @param clusterId
     * @param qq
     */
    public void cluster_GetCard(int clusterId, int qq) {
    	if(user.isLoggedIn()) {
    		ClusterGetCardPacket packet = new ClusterGetCardPacket(user);
    		packet.setClusterId(clusterId);
    		packet.setQQ(qq);
    		pool.send(MAIN.name, packet, true);	  	        
    	}
    }
    
    /**
     * 批量得到群名片
     * 
     * @param clusterId
     * @param start
     */
    public void cluster_GetCardBatch(int clusterId, int start) {
    	if(user.isLoggedIn()) {
    		ClusterGetCardBatchPacket packet = new ClusterGetCardBatchPacket(user);
    		packet.setClusterId(clusterId);
    		packet.setStart(start);
    		pool.send(MAIN.name, packet, true);	  	        
    	}
    }
    
    /**
     * 请求得到多人对话列表
     */
    public void cluster_getDialogList() {
    	if(user.isLoggedIn()) {
    		ClusterSubClusterOpPacket packet = new ClusterSubClusterOpPacket(user);
    		packet.setOpByte(QQ.QQ_CLUSTER_SUB_CMD_GET_DIALOG_LIST);
    		pool.send(MAIN.name, packet, false);
    	}
    }
    
    /**
     * 请求群的信息
     * 
     * @param clusterId
     * 		群内部ID
     */
    public void cluster_GetInfo(int clusterId) {
    	if(user.isLoggedIn()) {
    		ClusterGetInfoPacket packet = new ClusterGetInfoPacket(user);
    		packet.setClusterId(clusterId);
    		pool.send(MAIN.name, packet, true);	  	        
    	}
    }
    
    /**
     * 得到群中成员的信息
     * @param clusterId 群的内部ID
     * @param members 成员的QQ号列表，元素类型是Integer或者Member
     */
    public void cluster_GetMemberInfo(int clusterId, List<? extends Object> members) {
    	if(user.isLoggedIn()) {
    		// 由于一次最多只能得到61个成员的信息，所以这里按照30个成员一组进行拆分
    		// 因为QQ是一次拆这么多
    		int size = members.size();
    		int times = (size + 29) / 30;
    		for(int i = 0; i < times; i++) {
	    		ClusterGetMemberInfoPacket packet = new ClusterGetMemberInfoPacket(user);
	    		packet.setClusterId(clusterId);
    			for(int j = 30 * i; j < 30 * i + 30 && j < size; j++) {
    				Object obj = members.get(j);
    				if(obj instanceof Integer)
    					packet.addMember((Integer)obj);
    				else if(obj instanceof Member)
    					packet.addMember(((Member)obj).qq);
    			}
        		pool.send(MAIN.name, packet, true);				    				
    		}
    	}
    }
    
    /**
     * 得到群的在线成员
     * @param clusterId
     */
    public void cluster_GetOnlineMember(int clusterId) {
    	if(user.isLoggedIn()) {
    		ClusterGetOnlineMemberPacket packet = new ClusterGetOnlineMemberPacket(user);
    		packet.setClusterId(clusterId);
    		pool.send(MAIN.name, packet, true);	
    	}
    }
    
    /**
     * 请求得到讨论组列表
     * 
     * @param clusterId
     * 		群id
     */
    public void cluster_getSubjectList(int clusterId) {
    	if(user.isLoggedIn()) {
    		ClusterSubClusterOpPacket packet = new ClusterSubClusterOpPacket(user);
    		packet.setOpByte(QQ.QQ_CLUSTER_SUB_CMD_GET_SUBJECT_LIST);
    		packet.setClusterId(clusterId);
    		pool.send(MAIN.name, packet, false);
    	}
    }
    
    /**
     * 请求临时群信息
     * 
     * @param type
     * @param clusterId
     * @param parentClusterId
     */
    public void cluster_GetTempInfo(byte type, int clusterId, int parentClusterId) {
        if(user.isLoggedIn()) {
            ClusterGetTempInfoPacket packet = new ClusterGetTempInfoPacket(user);
            packet.setType(type);
            packet.setClusterId(clusterId);
            packet.setParentClusterId(parentClusterId);
    		pool.send(MAIN.name, packet, true);	
        }
    }
    
    /**
     * 加入群
     * @param clusterId
     * @return
     * 		包序号
     */
    public char cluster_Join(int clusterId) {
    	if(user.isLoggedIn()) {
    		ClusterJoinPacket packet = new ClusterJoinPacket(user);
    		packet.setClusterId(clusterId);
    		pool.send(MAIN.name, packet, true);	
    		return packet.getSequence();
    	}
    	return 0;
    }
    
    /**
     * 修改群名片
     * 
     * @param clusterId
     * @param card
     */
    public void cluster_ModifyCard(int clusterId, Card card) {
    	if(user.isLoggedIn()) {
    		ClusterModifyCardPacket packet = new ClusterModifyCardPacket(user);
    		packet.setClusterId(clusterId);
    		packet.setCard(card);
    		pool.send(MAIN.name, packet, true);
    	}
    }
    
    /**
     * 修改固定群信息
     * @param clusterId 群的内部ID
     * @param name 群名称
     * @param notice 群声明
     * @param description 群简介
     * @param oldCategory 2004群分类
     * @param category 群分类
     * @param authType 群认证类型
     * @return
     * 		包序号
     */
    public char cluster_ModifyInfo(int clusterId, String name, String notice, String description, int oldCategory, int category, byte authType) {
    	if(user.isLoggedIn()) {
    		ClusterModifyInfoPacket packet = new ClusterModifyInfoPacket(user);
    		packet.setClusterId(clusterId);
    		packet.setName(name);
    		packet.setNotice(notice);
    		packet.setDescription(description);
    		packet.setOldCategory(oldCategory);
    		packet.setCategory(category);
    		packet.setAuthType(authType);
    		pool.send(MAIN.name, packet, true);	
	        return packet.getSequence();
    	}
    	return 0;
    }
    
    /**
     * 修改群的成员列表
     * 
     * @param clusterId
     * 		群id
     * @param operation
     * 		操作类型
     * @param members
     * 		操作的成员QQ号
     * @return
     * 		包序号
     */
    public char cluster_ModifyMember(int clusterId, byte operation, List<Integer> members) {
    	if(user.isLoggedIn()) {
    		ClusterModifyMemberPacket packet = new ClusterModifyMemberPacket(user);
    		packet.setClusterId(clusterId);
    		packet.setOperation(operation);
    		packet.setMembers(members);
    		pool.send(MAIN.name, packet, true);	
    		return packet.getSequence();
    	}
    	return 0;
    }
    
    /**
     * 修改临时群信息
     * 
     * @param type
     * @param clusterId
     * @param parentClusterId
     * @param name
     * @return
     */
    public char cluster_ModifyTempInfo(byte type, int clusterId, int parentClusterId, String name) {
    	if(user.isLoggedIn()) {
    		ClusterModifyTempInfoPacket packet = new ClusterModifyTempInfoPacket(user);
    		packet.setType(type);
    		packet.setClusterId(clusterId);
    		packet.setParentClusterId(parentClusterId);
    		packet.setName(name);
    		pool.send(MAIN.name, packet, true);	
	        return packet.getSequence();
    	}
    	return 0;
    }
    
    /**
     * 修改临时群成员
     * 
     * @param type
     * 		临时群类型
     * @param clusterId
     * 		临时群id
     * @param parentClusterId
     * 		父群ID
     * @param operation
     * 		操作类型
     * @param members
     * 		成员QQ号数组
     * @return
     * 		包序号
     */
    public char cluster_ModifyTempMember(byte type, int clusterId, int parentClusterId, byte operation, List<Integer> members) {
        if(user.isLoggedIn()) {
            ClusterModifyTempMemberPacket packet = new ClusterModifyTempMemberPacket(user);
            packet.setType(type);
            packet.setClusterId(clusterId);
            packet.setParentClusterId(parentClusterId);
            packet.setMembers(members);
            packet.setOperation(operation);
    		pool.send(MAIN.name, packet, true);	
            return packet.getSequence();
        }
        return 0;
    }
    
    /**
     * 拒绝加入群
     * @param clusterId
     * @param message
     */
    public void cluster_RejectJoin(int clusterId, int receiver, String message) {
    	if(user.isLoggedIn()) {
    		ClusterAuthPacket packet = new ClusterAuthPacket(user);
    		packet.setType(QQ.QQ_CLUSTER_AUTH_REJECT);
    		packet.setClusterId(clusterId);
    		packet.setReceiver(receiver);
    		packet.setMessage(message);    
    		pool.send(MAIN.name, packet, true);	
    	}
    }
    
    /**
     * 请求加入群
     * @param clusterId 群内部ID
     * @param message 请求的附加信息
     * @return
     * 		包序号
     */
    public char cluster_RequestJoin(int clusterId, String message) {
    	if(user.isLoggedIn()) {
    		ClusterAuthPacket packet = new ClusterAuthPacket(user);
    		packet.setType(QQ.QQ_CLUSTER_AUTH_REQUEST);
    		packet.setClusterId(clusterId);
    		packet.setMessage(message);
    		pool.send(MAIN.name, packet, true);	
    		return packet.getSequence();
    	}
    	return 0;
    }
    
    /**
     * 根据外部ID搜索群
     * 
     * @param externalId 群的外部ID
     * @return
     * 		包序号
     */
    public char cluster_SearchById(int externalId) {
    	if(user.isLoggedIn()) {
    		ClusterSearchPacket packet = new ClusterSearchPacket(user);
    		packet.setExternalId(externalId);
    		packet.setSearchType(QQ.QQ_CLUSTER_SEARCH_BY_ID);
    		pool.send(MAIN.name, packet, true);	
    		return packet.getSequence();
    	} else
    	    return 0;
    }
    
    /**
     * 搜索示范群
     * 
     * @return
     * 		包序号
     */
    public char cluster_SearchDemo() {
    	if(user.isLoggedIn()) {
    		ClusterSearchPacket packet = new ClusterSearchPacket(user);
    		packet.setSearchType(QQ.QQ_CLUSTER_SEARCH_DEMO);
    		pool.send(MAIN.name, packet, true);	
    		return packet.getSequence();
    	} else
    	    return 0;
    }
    
    /**
     * 退出一个临时群
     * 
     * @param type
     * @param clusterId
     * @param parentClusterId
     */
    public void cluster_SearchTemp(byte type, int clusterId, int parentClusterId) {
    	if(user.isLoggedIn()) {
    		ClusterExitTempPacket packet = new ClusterExitTempPacket(user);
    		packet.setType(type);
    		packet.setParentClusterId(parentClusterId);
    		packet.setClusterId(clusterId);
    		pool.send(MAIN.name, packet, true);	
    	}
    }
    
    /**
     * 设置群成员角色
     * 
     * @param clusterId
     * 		群内部id
     * @param qq
     * 		需要设置的成员
     * @param opCode
     * 		操作码
     */
    public void cluster_SetMemberRole(int clusterId, int qq, byte opCode) {
    	if(user.isLoggedIn()) {
    		ClusterSetRolePacket packet = new ClusterSetRolePacket(user);
    		packet.setClusterId(clusterId);
    		packet.setQq(qq);
    		packet.setOpCode(opCode);
    		pool.send(MAIN.name, packet, false);
    	}
    }
    
    /**
     * 转让身份
     * 
     * @param clusterId
     * 		群内部id
     * @param qq
     * 		转让到的qq号
     */
    public void cluster_TransferRole(int clusterId, int qq) {
    	if(user.isLoggedIn()) {
    		ClusterTransferRolePacket packet = new ClusterTransferRolePacket(user);
    		packet.setClusterId(clusterId);
    		packet.setQq(qq);
    		pool.send(MAIN.name, packet, false);
    	}
    }
    
    /**
     * 更新组织架构
     * 
     * @param clusterId
     * 		群内部ID
     */
    public void cluster_UpdateOrganization(int clusterId) {
    	if(user.isLoggedIn()) {
    		ClusterUpdateOrganizationPacket packet = new ClusterUpdateOrganizationPacket(user);
    		packet.setClusterId(clusterId);
    		pool.send(MAIN.name, packet, true);
    	}
    }
    
    /**
     * 请求密钥
     * @param request
     */
    public void common_RequestKey(byte request) {
        if(pool != null && user.isLoggedIn() && pool.hasConnection(MAIN.name)) {
			GetKeyPacket packet = new GetKeyPacket(user);
			packet.setRequest(request);
    		pool.send(MAIN.name, packet, true);
        }
    }
    
    /**
     * 请求得到自定义头像数据
     * 
     * @param qq
     * @param timestamp
     * @param offset
     * @param length
     * @param port
     */
    public void customHead_GetData(int qq, int timestamp, int offset, int length, String port) {
    	if(user.isLoggedIn()) {
    		GetCustomHeadDataPacket packet = new GetCustomHeadDataPacket(user);
    		packet.setQQ(qq);
    		packet.setTimestamp(timestamp);
    		packet.setOffset(offset);
    		packet.setLength(length);
    		pool.send(port, packet, false);
    	}
    }
    
    /**
     * 请求得到自定义头像数据
     * 
     * @param qq
     * @param timestamp
     * @param port
     */
    public void customHead_GetData(int qq, int timestamp, String port) {
    	if(user.isLoggedIn()) {
    		GetCustomHeadDataPacket packet = new GetCustomHeadDataPacket(user);
    		packet.setQQ(qq);
    		packet.setTimestamp(timestamp);
    		pool.send(port, packet, false);
    	}
    }
    
    /**
     * 请求得到自定义头像信息
     * 
     * @param qq
     * 		请求的QQ号列表
     * @param port
     * 		发送端口名
     */
    public void customHead_GetInfo(List<Integer> qq, String port) {
    	if(user.isLoggedIn()) {
    		GetCustomHeadInfoPacket packet = new GetCustomHeadInfoPacket(user);
    		packet.setQqList(qq);
    		pool.send(port, packet, false);
    	}
    }
    
    /**
	 * 检查机器人设置，如果机器人打开了，发送一条机器人消息
	 * 
	 * @param packet
	 * 		接受消息包
	 * @param receiver
	 * 		接收者
	 */
	private void doRobot(ReceiveIMPacket packet) {
		// 检查机器人设置
		if(isRobotMode()) {
			if(robot != null) {
			    String reply = robot.getReply(packet);
			    if(reply != null)
			        im_Send(packet.header.sender, Util.getBytes(reply));
			}
		}
	}
    
    /**
     * 回复中转服务器已经收到表情数据
     * 
     * @param sessionId
     * @param port
     * @return
     */
    public char face_ReplyData(int sessionId, String port) {
        if(user.isLoggedIn()) {
            TransferPacket packet = new TransferPacket(user);
            packet.setSessionId(sessionId);
            packet.setDataReply(true);
            pool.send(port, packet, false);
            return packet.getSequence();
        }
        return 0;
    }
    
    /**
     * 请求得到表情图片
     * 
     * @param clusterId
     * @param sessionId
     * @param agentToken
     * @param key
     * @return
     */
    public char face_Request(int clusterId, int sessionId, byte[] key, String port) {
        if(user.isLoggedIn()) {
	        RequestFacePacket packet = new RequestFacePacket(user);
	        packet.setClusterId(clusterId);
	        packet.setEncryptKey(key);
	        packet.setFileAgentToken(user.getFileAgentToken());
	        packet.setSessionId(sessionId);
	        pool.send(port, packet, false);
	        return packet.getSequence();
        }
        return 0;
    }
    
    /**
     * 请求中转服务器
     * 
     * @param clusterId
     * 		群内部ID
     * @param imageLength
     * 		图片大小
     * @param md5
     * 		图片md5
     * @param port
     * 		端口名称
     * @return
     * 		包序号
     */
    public char face_RequestAgent(int clusterId, int imageLength, byte[] md5, String fileName, String port) {
        if(user.isLoggedIn()) {
            RequestAgentPacket packet = new RequestAgentPacket(user);
            packet.setClusterId(clusterId);
            packet.setImageLength(imageLength);
            packet.setMd5(md5);
            packet.setFileName(fileName);
            pool.send(port, packet, false);
            return packet.getSequence();
        }
        return 0;
    }
    
    /**
     * 请求发送表情图片数据
     * 
     * @param port
     * @return
     */
    public char face_RequestData(int sessionId, String port) {
        if(user.isLoggedIn()) {
            TransferPacket packet = new TransferPacket(user);
            packet.setSessionId(sessionId);
            pool.send(port, packet, false);
            return packet.getSequence();
        }
        return 0;
    }
    
    /**
     * 请求开始接收会话
     * 
     * @param sessionId
     * @param key
     * @param port
     * @return
     */
    public char face_RequestReceiveBegin(int sessionId, byte[] key, String port) {
        if(user.isLoggedIn()) {
	        RequestBeginPacket packet = new RequestBeginPacket(user);
	        packet.setSessionId(sessionId);
	        packet.setRequestSend(false);
	        packet.setEncryptKey(key);
	        pool.send(port, packet, false);
	        return packet.getSequence();
        }
        return 0;
    }
    
    /**
     * 请求开始一个会话
     * 
     * @param sessionId
     * 		会话id
     * @param port
     * 		端口名称
     */
    public void face_RequestSendBegin(int sessionId, byte[] md5, String port) {
        if(user.isLoggedIn()) {
            RequestBeginPacket packet = new RequestBeginPacket(user);
            packet.setSessionId(sessionId);
            packet.setMd5(md5);
            pool.send(port, packet, false);
        }
    }
    
    /**
     * 传送表情文件数据
     * 
     * @param sessionId
     * @param fragment
     * @param last
     * @param port
     * @return
     * 		包序号
     */
    public char face_TransferData(int sessionId, byte[] fragment, boolean last, String port) {
        if(user.isLoggedIn()) {
            TransferPacket packet = new TransferPacket(user, true, last);
            packet.setSessionId(sessionId);
            packet.setFragment(fragment);
            pool.send(port, packet, false);
            return packet.getSequence();
        }
        return 0;
    }
    
    /**
     * 传送表情文件信息
     * 
     * @param sessionId
     * @param imageLength
     * @param fileName
     * @param md5
     * @param port
     * @return
     * 		包序号
     */
    public char face_TransferInfo(int sessionId, int imageLength, String fileName, byte[] md5, String port) {
        if(user.isLoggedIn()) {
            TransferPacket packet = new TransferPacket(user, false, false);
            packet.setSessionId(sessionId);
            packet.setImageLength(imageLength);
            packet.setFileName(fileName);
            packet.setMd5(md5);
            pool.send(port, packet, false);
            return packet.getSequence();
        }
        return 0;
    }
    
    /**
	 * 通知包处理器包到达事件
	 * 
	 * @param e
	 */
	public void firePacketArrivedEvent(PacketEvent e) {
		router.packetArrived(e);
	}
    
    /**
     * 触发QQ事件
     * @param e QQEvent
     */
    public void fireQQEvent(QQEvent e) {
    	checkListenerChange();
		int size = qqListenersBackup.size();
		for(int i = 0; i < size; i++)
			qqListenersBackup.get(i).qqEvent(e);
    }
    
    /**
	 * 在程序出现运行时异常时产生一个崩溃报告
	 * 
	 * @param e
	 * 		异常对象
	 * @param p
	 * 		包对象
	 * @return
	 * 		崩溃报告内容
	 */
	public String generateCrashReport(Throwable e, Packet p) {
		StringBuilder sb = new StringBuilder();
		// stack trace
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		pw.flush();
		sb.append(sw.getBuffer());
		sb.append(System.getProperty("line.separator"));
		sb.append(System.getProperty("line.separator"));
		// packet format
		sb.append("Packet Dump:");
		sb.append(System.getProperty("line.separator"));
		sb.append(Util.convertByteToHexString(p.dump()));
		sb.append(System.getProperty("line.separator"));
		sb.append(System.getProperty("line.separator"));
		// user qq number
		sb.append("User QQ: ");
		sb.append(System.getProperty("line.separator"));
		sb.append(String.valueOf(user.getQQ()));
		sb.append(System.getProperty("line.separator"));
		sb.append(System.getProperty("line.separator"));
		// init key
		sb.append("Init Key:");
		sb.append(System.getProperty("line.separator"));
		sb.append(Util.convertByteToHexString(user.getInitKey()));
		sb.append(System.getProperty("line.separator"));
		sb.append(System.getProperty("line.separator"));
		// session key
		sb.append("Session Key:");
		sb.append(System.getProperty("line.separator"));
		sb.append(Util.convertByteToHexString(user.getSessionKey()));
		sb.append(System.getProperty("line.separator"));
		sb.append(System.getProperty("line.separator"));
		// auth token
		sb.append("Auth Token:");
		sb.append(System.getProperty("line.separator"));
		sb.append(Util.convertByteToHexString(user.getAuthToken()));
		sb.append(System.getProperty("line.separator"));
		sb.append(System.getProperty("line.separator"));
		// login token
		sb.append("Login Token:");
		sb.append(System.getProperty("line.separator"));
		sb.append(Util.convertByteToHexString(user.getLoginToken()));
		sb.append(System.getProperty("line.separator"));
		sb.append(System.getProperty("line.separator"));
		// client key
		sb.append("Client Key:");
		sb.append(System.getProperty("line.separator"));
		sb.append(Util.convertByteToHexString(user.getClientKey()));
		sb.append(System.getProperty("line.separator"));
		sb.append(System.getProperty("line.separator"));
		// file session key
		sb.append("File Session Key:");
		sb.append(System.getProperty("line.separator"));
		sb.append(Util.convertByteToHexString(user.getFileSessionKey()));
		sb.append(System.getProperty("line.separator"));
		sb.append(System.getProperty("line.separator"));
		// file agent key
		sb.append("File Agent Key:");
		sb.append(System.getProperty("line.separator"));
		sb.append(Util.convertByteToHexString(user.getFileAgentKey()));
		sb.append(System.getProperty("line.separator"));
		sb.append(System.getProperty("line.separator"));
		// file agent token
		sb.append("File Agent Token:");
		sb.append(System.getProperty("line.separator"));
		sb.append(Util.convertByteToHexString(user.getFileAgentToken()));
		sb.append(System.getProperty("line.separator"));
		sb.append(System.getProperty("line.separator"));
		// unknown 03 key
		sb.append("Unknown 03 Key:");
		sb.append(System.getProperty("line.separator"));
		sb.append(Util.convertByteToHexString(user.getUnknown03Key()));
		sb.append(System.getProperty("line.separator"));
		sb.append(System.getProperty("line.separator"));
		// unknown 03 token
		sb.append("Unkonwn 03 Token:");
		sb.append(System.getProperty("line.separator"));
		sb.append(Util.convertByteToHexString(user.getUnknown03Token()));
		sb.append(System.getProperty("line.separator"));
		sb.append(System.getProperty("line.separator"));
		// unknown 06key
		sb.append("Unknown 06 Key:");
		sb.append(System.getProperty("line.separator"));
		sb.append(Util.convertByteToHexString(user.getUnknown06Key()));
		sb.append(System.getProperty("line.separator"));
		sb.append(System.getProperty("line.separator"));
		// unknown 06 token
		sb.append("Unkonwn 06 Token:");
		sb.append(System.getProperty("line.separator"));
		sb.append(Util.convertByteToHexString(user.getUnknown06Token()));
		sb.append(System.getProperty("line.separator"));
		sb.append(System.getProperty("line.separator"));
		// unknown 07 key
		sb.append("Unknown 07 Key:");
		sb.append(System.getProperty("line.separator"));
		sb.append(Util.convertByteToHexString(user.getUnknown07Key()));
		sb.append(System.getProperty("line.separator"));
		sb.append(System.getProperty("line.separator"));
		// unknown 07 token
		sb.append("Unkonwn 07 Token:");
		sb.append(System.getProperty("line.separator"));
		sb.append(Util.convertByteToHexString(user.getUnknown07Token()));
		sb.append(System.getProperty("line.separator"));
		sb.append(System.getProperty("line.separator"));
		// unknown 08 key
		sb.append("Unknown 08 Key:");
		sb.append(System.getProperty("line.separator"));
		sb.append(Util.convertByteToHexString(user.getUnknown08Key()));
		sb.append(System.getProperty("line.separator"));
		sb.append(System.getProperty("line.separator"));
		// unknown 08 token
		sb.append("Unkonwn 08 Token:");
		sb.append(System.getProperty("line.separator"));
		sb.append(Util.convertByteToHexString(user.getUnknown08Token()));
		sb.append(System.getProperty("line.separator"));
		sb.append(System.getProperty("line.separator"));
		
		return sb.toString();
	}
    
    /**
     * 根据远程地址得到一个port
     * 
     * @param address
     * 		端口远程地址
     * @return
     * 		IPort对象，如果不存在，返回null
     */
    public synchronized IConnection getConnection(InetSocketAddress address) {
        return pool.getConnection(address);
    }
    
    /**
     * 得到一个端口
     * 
     * @param name
     * 		端口名
     * @return
     * 		IPort对象，如果不存在，则返回null
     */
    public synchronized IConnection getConnection(String name) {
        return pool.getConnection(name);
    }
    
    /**
     * 得到和某个输入包相关的端口策略 
     * 
     * @param in
     * 		回复包
     * @return
     * 		连接策略对象
     */
    public synchronized IConnectionPolicy getConnectionPolicy(InPacket in) {
    	String id = inConn.remove(in);
    	IConnection con = getConnection(id);
    	if(con == null)
    		return null;
    	else
    		return con.getPolicy();
    }
    
    /**
	 * @return the policyFactory
	 */
	public IConnectionPolicyFactory getConnectionPolicyFactory() {
		return policyFactory;
	}
    
    /**
	 * @return the pool
	 */
	public IConnectionPool getConnectionPool() {
		return pool;
	}
    
    /**
	 * @return the poolFactory
	 */
	public IConnectionPoolFactory getConnectionPoolFactory() {
		return poolFactory;
	}
    
    /**
     * @return 登陆服务器IP
     */
    public String getLoginServer() {
        return loginServer;
    }
    
    /**
     * @return Returns the portGate.
     */
    public IConnectionPool getPortGate() {
        return pool;
    }
    
    /**
     * @return
     * 		代理类型
     */
    public int getProxyType() {
    	return proxyType;
    }
    
    /**
	 * @return Returns the robot.
	 */
	public IRobot getRobot() {
		return robot;
	}
    
    /**
     * @return Returns the user.
     */
    public QQUser getUser() {
        return user;
    }
    
    /**
     * 发送一个消息，用给定的字体
     * @param receiver 接受者QQ号
     * @param message 消息
     * @param fontName 字体
     */
    public void im_Send(int receiver, byte[] message) {
        this.im_Send(receiver, message, QQ.QQ_IM_NORMAL_REPLY);
    }
    
    /**
     * 发送一条消息，可以指定回复类型
     * @param receiver 接收者QQ号
     * @param message 消息
     * @param fontName 字体名
     * @param replyType 回复方式，正常回复还是自动回复
     */
    public void im_Send(int receiver, byte[] message, byte replyType) {
        this.im_Send(receiver, message, "宋体", false, false, false, 9, 0, 0, 0, replyType);
    }
    
    /**
     * 发送普通消息
     * 
     * @param receiver
     * @param message
     * @param messageId
     * @param totalFragments
     * @param fragementSequence
     * @param fontName
     * @param bold
     * @param italic
     * @param underline
     * @param size
     * @param red
     * @param green
     * @param blue
     * @param replyType
     */
    public void im_Send(
            int receiver, 
            byte[] message, 
            char messageId,
            int totalFragments,
            int fragementSequence,
            String fontName, 
            boolean bold, 
            boolean italic, 
            boolean underline, 
            int size, 
            int red,
            int green,
            int blue,
            byte replyType) {
    	if(user.isLoggedIn()) {
	        SendIMPacket packet = new SendIMPacket(user);
	        packet.setReceiver(receiver);
	        packet.setMessage(message);
	        packet.setTotalFragments(totalFragments);
	        packet.setFragmentSequence(fragementSequence);
	        packet.setMessageId(messageId);
	        packet.setFontName(fontName);
	        packet.setFontSize((byte)(size & 0xFF));
	        packet.setRed((byte)(red & 0xFF));
	        packet.setGreen((byte)(green & 0xFF));
	        packet.setBlue((byte)(blue & 0xFF));
	        packet.setBold(bold);
	        packet.setItalic(italic);
	        packet.setUnderline(underline);
	        packet.setReplyType(replyType);
    		pool.send(MAIN.name, packet, true);	
    	}
    }
    
    /**
     * 发送一个消息，指定字体和字体颜色
     * 
     * @param receiver 接受者QQ号
     * @param message 消息
     * @param fontName 字体
     * @param bold 是否粗体
     * @param italic 是否斜体
     * @param underline 是否下划线
     * @param size 字体大小，最大是31
     * @param red 颜色Red
     * @param green 颜色Green
     * @param blue 颜色Blue
     * @param replyType 回复类型，自动回复还是正常回复
     */
    public void im_Send(
            int receiver, 
            byte[] message, 
            String fontName, 
            boolean bold, 
            boolean italic, 
            boolean underline, 
            int size, 
            int red,
            int green,
            int blue,
            byte replyType) {
        im_Send(
                receiver, 
                message,
                (char)0,
                1,
                0,
                fontName,
                bold,
                italic,
                underline,
                size,
                red,
                green,
                blue,
                replyType);    	
    }
    
    /**
     * 发送群消息
     * @param clusterId 群内部ID
     * @param message 消息内容
     */
    public void im_SendCluster(int clusterId, String message) {
    	if(user.isLoggedIn()) {
    		ClusterSendIMExPacket packet = new ClusterSendIMExPacket(user);
    		packet.setClusterId(clusterId);
    		packet.setMessage(message);
    		packet.setMessageId(packet.getSequence());
    		pool.send(MAIN.name, packet, true);	
    	}
    }
    
    /**
     * 发送固定群消息
     * 
     * @param clusterId
     * 		群内部ID
     * @param message
     * 		消息
     * @param messageId
     * 		消息id
     * @param totalFragments
     * 		分片数
     * @param fragmentSequence
     * 		分片序号
     */
    public void im_SendCluster(int clusterId, String message, char messageId, int totalFragments, int fragmentSequence) {
    	if(user.isLoggedIn()) {
    		ClusterSendIMExPacket packet = new ClusterSendIMExPacket(user);
    		packet.setClusterId(clusterId);
    		packet.setMessage(message);
    		packet.setMessageId(messageId);
    		packet.setTotalFragments(totalFragments);
    		packet.setFragmentSequence(fragmentSequence);
    		pool.send(MAIN.name, packet, true);	
    	}
    }
    
    /**
     * 发送固定群消息
     * 
     * @param clusterId
     * @param message
     * @param messageId
     * @param totalFragments
     * @param fragmentSequence
     * @param fontName
     * @param bold
     * @param italic
     * @param underline
     * @param fontSize
     * @param red
     * @param green
     * @param blue
     */
    public void im_SendCluster(int clusterId, 
            String message,
            char messageId,
            int totalFragments,
            int fragmentSequence,
            String fontName,
            boolean bold, 
            boolean italic,
            boolean underline,
            int fontSize,
            int red,
            int green,
            int blue) {
    	if(user.isLoggedIn()) {
    		ClusterSendIMExPacket packet = new ClusterSendIMExPacket(user);
    		packet.setClusterId(clusterId);
    		packet.setMessage(message);
    		packet.setMessageId(messageId);
    		packet.setTotalFragments(totalFragments);
    		packet.setFragmentSequence(fragmentSequence);
    		packet.setFontName(fontName);
    		packet.setFontSize((byte)fontSize);
    		packet.setBold(bold);
    		packet.setItalic(italic);
    		packet.setUnderline(underline);
    		packet.setRed((byte)red);
    		packet.setGreen((byte)green);
    		packet.setBlue((byte)blue);
    		pool.send(MAIN.name, packet, true);	
    	}
    }
    
    /**
     * 发送临时群消息
     * 
     * @param type
     * 		临时群类型
     * @param clusterId
     * 		群内部id
     * @param parentClusterId
     * 		父群id
     * @param message
     * 		消息
     */
    public void im_SendTempCluster(byte type, int clusterId, int parentClusterId, String message) {
    	if(user.isLoggedIn()) {
    		ClusterSendTempIMPacket packet = new ClusterSendTempIMPacket(user);
    		packet.setClusterId(clusterId);
    		packet.setType(type);
    		packet.setParentClusterId(parentClusterId);
    		packet.setMessage(message);
    		packet.setMessageId(packet.getSequence());    		
    		pool.send(MAIN.name, packet, true);	
    	}
    }
	
	/**
     * 发送临时群消息
     * 
     * @param type
     * 		临时群类型
     * @param clusterId
     * 		群内部id
     * @param parentClusterId
     * 		父群id
     * @param message
     * 		消息
     * @param messageId
     * 		消息id
     * @param totalFragments
     * 		分片数
     * @param fragmentSequence
     * 		分片序号
     */
    public void im_SendTempCluster(byte type, int clusterId, int parentClusterId, String message, char messageId, int totalFragments, int fragmentSequence) {
    	if(user.isLoggedIn()) {
    		ClusterSendTempIMPacket packet = new ClusterSendTempIMPacket(user);
    		packet.setClusterId(clusterId);
    		packet.setType(type);
    		packet.setParentClusterId(parentClusterId);
    		packet.setMessage(message);
    		packet.setMessageId(packet.getSequence());   
    		packet.setTotalFragments(totalFragments);
    		packet.setFragmentSequence(fragmentSequence);
    		pool.send(MAIN.name, packet, true);	
    	}
    }
	
	/**
     * 发送临时群消息
     * 
     * @param type
     * @param clusterId
     * @param parentClusterId
     * @param message
     * @param messageId
     * @param totalFragments
     * @param fragmentSequence
     * @param fontName
     * @param bold
     * @param italic
     * @param underline
     * @param fontSize
     * @param red
     * @param green
     * @param blue
     */
    public void im_SendTempCluster(byte type, 
            int clusterId,
            int parentClusterId,
            String message,
            char messageId, 
            int totalFragments, 
            int fragmentSequence,
            String fontName,
            boolean bold, 
            boolean italic,
            boolean underline,
            int fontSize,
            int red,
            int green,
            int blue) {
    	if(user.isLoggedIn()) {
    		ClusterSendTempIMPacket packet = new ClusterSendTempIMPacket(user);
    		packet.setClusterId(clusterId);
    		packet.setType(type);
    		packet.setParentClusterId(parentClusterId);
    		packet.setMessage(message);
    		packet.setMessageId(messageId);   
    		packet.setTotalFragments(totalFragments);
    		packet.setFragmentSequence(fragmentSequence);
    		packet.setFontName(fontName);
    		packet.setFontSize((byte)fontSize);
    		packet.setBold(bold);
    		packet.setItalic(italic);
    		packet.setUnderline(underline);
    		packet.setRed((byte)red);
    		packet.setGreen((byte)green);
    		packet.setBlue((byte)blue);
    		pool.send(MAIN.name, packet, true);	
    	}
    }
	
    /**
	 * 发送临时会话消息
	 * 
	 * @param receiver
	 * @param message
	 */
	public void im_SendTempSession(int receiver, String message, String myNick) {
		im_SendTempSession(receiver,
				message,
				myNick,
				"宋体",
				false,
				false,
				false,
				9,
				0,
				0,
				0);
	}
    
    /**
	 * 发送临时会话消息
	 * 
	 * @param receiver
	 * @param message
	 * @param fontName
	 * @param bold
	 * @param italic
	 * @param underline
	 * @param size
	 * @param red
	 * @param green
	 * @param blue
	 */
	public void im_SendTempSession(int receiver, 
			String message, 
			String myNick,
            String fontName, 
            boolean bold, 
            boolean italic, 
            boolean underline, 
            int size, 
            int red,
            int green,
            int blue) {
		if(user.isLoggedIn()) {
			TempSessionOpPacket packet = new TempSessionOpPacket(user);
			packet.setSubCommand(QQ.QQ_SUB_CMD_SEND_TEMP_SESSION_IM);
	        packet.setReceiver(receiver);
	        packet.setMessage(message);
	        packet.setFontName(fontName);
	        packet.setFontSize((byte)(size & 0xFF));
	        packet.setRed((byte)(red & 0xFF));
	        packet.setGreen((byte)(green & 0xFF));
	        packet.setBlue((byte)(blue & 0xFF));
	        packet.setBold(bold);
	        packet.setItalic(italic);
	        packet.setUnderline(underline);
    		pool.send(MAIN.name, packet, true);	
		}
	}
    
    /**
	 * @return
	 * 		true表示接收队列为空
	 */
	public synchronized boolean isEmpty() {
		return receiveQueue.isEmpty();
	}
    
    /**
     * 得到listener changed标志
     * 
     * @return
     * 		true表示有新的listener添加了进来
     */
    private synchronized boolean isListenerChanged() {
    	return listenerChanged;
    }
    
    /**
	 * @return Returns the logging.
	 */
	public boolean isLogging() {
		return logging;
	}
    
    /**
	 * @return
	 * 		true表示正处于机器人状态
	 */
	public boolean isRobotMode() {
		return robotMode;
	}
    
    /**
     * 发送keep alive包
     */
    public void keepAlive() {
        if(user.isLoggedIn()) {
			KeepAlivePacket packet = new KeepAlivePacket(user);
			pool.send(QQPort.MAIN.name, packet, false);
        }
    }
    
    /**
     * 登陆，使用缺省端口
     * 
     * @throws Exception
     * 				如果登录出错
     */
    public void login() throws Exception {    	
        if (user != null && !logging && loginServer != null) {
	        // 登陆
            if(user.isUdp())
                login(loginServer, QQ.QQ_PORT_UDP);
            else
                login(loginServer, tcpLoginPort);
       } 
    }
    
    /**
     * 用指定的服务器和端口登录
     * 
     * @param server 登陆服务器
     * @param p 登陆端口
     * @throws Exception
     * 				如果登录出错
     */
    protected void login(String server, int p) throws Exception {
        // 检查是否设置了用户名和密码
        if (user != null && poolFactory != null) {
        	// 停止以前的线程，如果有的话
        	logout();
        	// 设置状态为正在登陆
        	logging = true;    
            // 检查是什么登陆模式，udp还是tcp
            try {
            	// 如果port为0，设置缺省port
            	if(p == 0) {
            		if(user.isUdp())
            			p = QQ.QQ_PORT_UDP;
            		else
            			p = tcpLoginPort;
            	}
            	// 创建连接
                IConnection con = MAIN.create(this, new InetSocketAddress(server, p), proxyAddress, proxyUsername, proxyPassword, false);                
                // 如果登录令牌还是空，就构造请求登录令牌包，如果不是，直接登录
                OutPacket packet = null;
                if(user.getLoginToken() == null)
                    packet = new GetLoginTokenPacket(user);
                else
                    packet = new LoginPacket(user);
                // 启动连接池
                pool.start();
                // 启动端口
            	con.start();
                // 发送请求包              
                pool.send(MAIN.name, packet, false);
            } catch (Exception e) {
			    throw e;
			}
        }
    }
    
    /**
     * 退出QQ会话
     */
    public void logout() {
        // 得到当前登录状态
        boolean isLoggedIn = user.isLoggedIn();
	    // 设置用户状态
    	if(!loginRedirect)
	    	user.setLoginToken(null);    
    	user.setLoggedIn(false);    	
    	// 设置正在登录标志为false
    	logging = false;
    	loginRedirect = false;

    	// 清空发送队列
    	if(pool != null) {
    		IConnection con = pool.getConnection(MAIN.name);
    		if(con != null) {
    			con.clearSendQueue();
    			// 如果已经登录了，则发送logout包
    			if(isLoggedIn) {
    				LogoutPacket packet = new LogoutPacket(user);
    				pool.send(MAIN.name, packet, false);
    			}
    		}    		
    		pool.dispose();
    	}
    	pool = poolFactory.newPool();
    	
    	// 执行清理工作
    	if(keepAliveFuture != null) {
    		keepAliveFuture.cancel(false);
    		keepAliveFuture = null;
    	}
    	resendTrigger.clear();
    }
    
    /**
	 * 处理连接丢失事件
	 */
	private void processKeepAliveFail() {
		user.setLoggedIn(false);
		logout();
	}
    
    /**
	 * 处理请求登录令牌成功事件
     */
    private void processGetLoginTokenSuccess() {
        LoginPacket packet = new LoginPacket(user);
		pool.send(MAIN.name, packet, false);	
    }
    
    /**
     * 处理Keep Alive成功信息，对Keep Alive Success，QQClient不作什么
     * 进一步的分析，只是顺便刷新一下好友状态
     * 
     * @param e
     */
    private void processKeepAliveSuccess(QQEvent e) {
        GetOnlineOpPacket packet = new GetOnlineOpPacket(user);
        sendPacket(packet);
    }

    /**
	 * 处理登陆成功事件
	 */
	private void processLoginSuccess() {
	    if(!user.isLoggedIn()) {
			user.setLoggedIn(true);
			this.logging = false;
			// 请求密钥
			common_RequestKey(QQ.QQ_SUB_CMD_REQUEST_UNKNOWN03_KEY);
			common_RequestKey(QQ.QQ_SUB_CMD_REQUEST_FILE_AGENT_KEY);
			common_RequestKey(QQ.QQ_SUB_CMD_REQUEST_UNKNOWN06_KEY);
			common_RequestKey(QQ.QQ_SUB_CMD_REQUEST_UNKNOWN07_KEY);
			common_RequestKey(QQ.QQ_SUB_CMD_REQUEST_UNKNOWN08_KEY);
			// 得到我的信息
			GetUserInfoPacket guiPacket = new GetUserInfoPacket(user);
	        sendPacket(guiPacket);
			// 手动更改我的在线状态，虽然服务器可能也会给我发送这样的信息
			ChangeStatusPacket csPacket = new ChangeStatusPacket(user);
			csPacket.setShowFakeCam(user.isShowFakeCam());
	        sendPacket(csPacket);	   
	        // 调度keep alive动作
	        keepAliveFuture = executor.scheduleWithFixedDelay(keepAliveTrigger, QQ.QQ_INTERVAL_KEEP_ALIVE, QQ.QQ_INTERVAL_KEEP_ALIVE, TimeUnit.MILLISECONDS);
	    }
	}
    
    /**
	 * 处理登陆超时事件
	 */
	private void processLoginTimeout() {
		logging = false;
		logout();
	}
        
    /**
	 * 处理普通消息事件，核心层的处理是检查机器人设置，如果机器人打开了，则自动返回消息
	 * 
	 * @param e
	 * 		QQEvent
	 */
	private void processNormalIM(QQEvent e) {
		// 先返回确认
		processReceiveIM(e);
		
		// 得到消息包
		ReceiveIMPacket packet = (ReceiveIMPacket)e.getSource();
		doRobot(packet);
	}
    
    /**
     * 处理普通消息事件，只是发一个确认而已
     * 
     * @param e
     */
    private void processReceiveIM(QQEvent e) {
        // 得到包
        ReceiveIMPacket packet = (ReceiveIMPacket)e.getSource();
        
        // 生成确认包
        ReceiveIMReplyPacket reply = new ReceiveIMReplyPacket(packet.reply, user);
        reply.setSequence(packet.getSequence());
		pool.send(MAIN.name, reply, false);	
    }
    
    /* (non-Javadoc)
	 * @see edu.tsinghua.lumaqq.QQListener#qqEvent(edu.tsinghua.lumaqq.QQEvent)
	 */
	public void qqEvent(QQEvent e) {
		switch(e.type) {
		    case QQEvent.LOGIN_GET_TOKEN_OK:
		        processGetLoginTokenSuccess();
		    	break;
			case QQEvent.LOGIN_OK:
				processLoginSuccess();
				break;
			case QQEvent.USER_KEEP_ALIVE_OK:
			    processKeepAliveSuccess(e);
				break;
			case QQEvent.IM_RECEIVED:
				processNormalIM(e);
				break;
			case QQEvent.SYS_KICKED:
			case QQEvent.IM_CLUSTER_RECEIVED:		
			case QQEvent.IM_TEMP_CLUSTER_RECEIVED:
			case QQEvent.IM_UNKNOWN_CLUSTER_TYPE_RECEIVED:
			case QQEvent.FILE_REQUEST_SEND_TO_ME:
			case QQEvent.FILE_ACCEPT:
			case QQEvent.FILE_REJECT:
			case QQEvent.FILE_CANCEL:
			case QQEvent.FILE_NOTIFY_ARGS:
			case QQEvent.FILE_REQUEST_ME_CONNECT:
			case QQEvent.IM_UNKNOWN_TYPE_RECEIVED:
			case QQEvent.CLUSTER_JOIN:
			case QQEvent.CLUSTER_APPROVE_JOIN:
			case QQEvent.CLUSTER_REJECT_JOIN:
			case QQEvent.CLUSTER_ADDED_TO:
			case QQEvent.CLUSTER_REMOVED_FROM:
			case QQEvent.IM_DUPLICATED:
			case QQEvent.CLUSTER_ADMIN_ENTITLED:
			case QQEvent.CLUSTER_ADMIN_WITHDRAWED:
			case QQEvent.FRIEND_SIGNATURE_CHANGED:
			case QQEvent.SMS_RECEIVED:
			case QQEvent.IM_TEMP_SESSION_RECEIVED:
			case QQEvent.USER_MEMBER_LOGIN_HINT_RECEIVED:
			case QQEvent.FRIEND_CUSTOM_HEAD_CHANGED:
			case QQEvent.FRIEND_PROPERTY_CHANGED:
			    processReceiveIM(e);
				break;
			case QQEvent.ERROR_NETWORK:
			case QQEvent.ERROR_CONNECTION_BROKEN:
			case QQEvent.ERROR_PROXY:
				processError(e);
				break;
			case QQEvent.SYS_TIMEOUT:
			    switch(e.operation) {
			        case QQ.QQ_CMD_LOGIN:
			        case QQ.QQ_CMD_GET_LOGIN_TOKEN:
						processLoginTimeout();
			        	break;
			    }				
				break;
			case QQEvent.USER_KEEP_ALIVE_FAIL:
				processKeepAliveFail();
				break;
		}
	}

	private void processError(QQEvent e) {
		if(e.getSource() instanceof ErrorPacket) {
			ErrorPacket packet = (ErrorPacket)e.getSource();
			releaseConnection(packet.connectionId);
		}
	}

	/**
     * 释放这个QQClient，如果不继续使用QQClient，则必须释放此客户端以便
     * SingleExecutor释放资源
     */
    public void release() {
    	executor.decreaseClient();
    }
    
    /**
     * 关闭一个端口
     * 
     * @param name
     * 		端口名称
     */
    public synchronized void releaseConnection(String name) {
        if(pool != null)
            pool.release(name);
    }
    
    /**
	 * 从接收队列中得到第一个包，并且把这个包从队列中移除
	 * 
	 * @return
	 * 		接收队列的第一个包，没有返回null
	 */
	public synchronized InPacket removeIncomingPacket() {
		return receiveQueue.poll();
	}
    
    /**
     * 移去一个QQ事件监听器
     * @param listener QQListener
     */
    public void removeQQListener(IQQListener listener) {
    	if(qqListeners.contains(listener)) {
	    	qqListeners.remove(listener);
	    	setListenerChanged(true);    		
    	}
    }
    
    /**
     * 删除一个重发包
     * 
     * @param packet
     */
    public void removeResendPacket(InPacket packet) {
    	resendTrigger.remove(packet);
    }
    
    /**
     * 通用方法，发送一个packet
     * 这个方法用在一些包构造比较复杂的情况下，比如上传分组信息这种包，
     * 包中数据的来源是无法知道的也不是用几个参数就能概括的，可能也和实现有关。
     * 
     * @param packet OutPacket子类
     */
    public void sendPacket(OutPacket packet) {
    	if(user.isLoggedIn())
    		pool.send(MAIN.name, packet, true);
    }
    
    /**
     * 通过指定port发送一个包
     * 
     * @param packet
     * @param port
     */
    public void sendPacket(OutPacket packet, String port) {
        sendPacket(packet, port, true);
    }

    /**
     * 通过指定port发送一个包
     * 
     * @param packet
     * 		输出包对象
     * @param port
     * 		port名称 
     * @param monitor
     * 		true表示加入到包监视缓冲
     */
    public void sendPacket(OutPacket packet, String port, boolean monitor) {
        if(user.isLoggedIn())
            pool.send(port, packet, monitor);
    }

	/**
     * 不管有没有登录，都把包发出去
     * 
     * @param packet
     * @param port
     */
    public void sendPacketAnyway(OutPacket packet, String port) {
        pool.send(port, packet, true);
    }

    /**
	 * @param policyFactory the policyFactory to set
	 */
	public void setConnectionPolicyFactory(IConnectionPolicyFactory policyFactory) {
		this.policyFactory = policyFactory;
	}

	/**
	 * @param poolFactory the poolFactory to set
	 */
	public void setConnectionPoolFactory(IConnectionPoolFactory poolFactory) {
		this.poolFactory = poolFactory;
	}

    /**
     * 设置listener changed标志
     * 
     * @param b
     */
    private synchronized void setListenerChanged(boolean b) {
    	listenerChanged = b;
    }

    /**
     * 设置正在登陆标志
     * 
     * @param logging
     */
    public void setLogging(boolean logging) {
        this.logging = logging;
    }
	
	/**
     * @param loginRedirect The loginRedirect to set.
     */
    public void setLoginRedirect(boolean loginRedirect) {
        this.loginRedirect = loginRedirect;
    }

	/**
     * @param loginServer
     *            登陆服务器IP
     */
    public void setLoginServer(String loginServer) {
        this.loginServer = loginServer;
    }

	/**
     * 设置代理服务器地址
     * @param proxyAddress
     */
    public void setProxy(InetSocketAddress proxyAddress) {
        this.proxyAddress = proxyAddress;
    } 
	
	/**
     * @param password The password to set.
     */
    public void setProxyPassword(String password) {
        if(password == null || password.equals(""))
            proxyPassword = null;
        else
            proxyPassword = password;
    }

	/**
     * 设置代理类型
     * @param type
     */
    public void setProxyType(String type) {
        if(type.equalsIgnoreCase("Socks5"))
            proxyType = QQ.QQ_PROXY_SOCKS5;
        else if(type.equalsIgnoreCase("Http"))
            proxyType = QQ.QQ_PROXY_HTTP;
        else
            proxyType = QQ.QQ_PROXY_NONE;
    }
	
    /**
     * @param username The username to set.
     */
    public void setProxyUsername(String username) {
        if(username == null || username.equals(""))
            proxyUsername = null;
        else
            proxyUsername = username;
    }
    
    /**
	 * @param robot The robot to set.
	 */
	public void setRobot(IRobot robot) {
		this.robot = robot;
	}
    
    /**
	 * 设置机器人状态
	 * 
	 * @param robotMode
	 */
	public void setRobotMode(boolean robotMode) {
		this.robotMode = robotMode;
	}
    
    /**
     * @param tcpLoginPort The tcpLoginPort to set.
     */
    public void setTcpLoginPort(int tcpLoginPort) {
        this.tcpLoginPort = tcpLoginPort;
    }
    
    /**
     * @param user The user to set.
     */
    public void setUser(QQUser user) {
        this.user = user;
    }
    
    /**
     * 发送短信
     * 
     * @param mobiles
     * 		接受者的手机号码
     * @param qq
     * 		接受者的QQ号码
     * @param senderName
     * 		发送者名称
     * @param message
     * 		消息字节数组
     * @param seq
     * 		消息序号
     * @return
     * 		包序号
     */
    public char sms_Send(List<String> mobiles, List<Integer> qq, String senderName, byte[] message, char seq) {
    	if(user.isLoggedIn()) {
    		SendSMSPacket packet = new SendSMSPacket(user);
    		packet.setMessageSequence(seq);
    		packet.setMessage(message);
    		packet.setSenderName(senderName);
    		packet.setReceiverMobile(mobiles);
    		packet.setReceiverQQ(qq);
    		pool.send(MAIN.name, packet, true);
    		return packet.getSequence();
    	}
    	return 0;
    }
    
	/**
     * 使我的状态改变成离开
     */
    public void status_Away() {
    	if(user.isLoggedIn() && pool.hasConnection(MAIN.name)) {
	        user.setStatus(QQ.QQ_STATUS_AWAY);
	    	ChangeStatusPacket packet = new ChangeStatusPacket(user);
	    	packet.setShowFakeCam(user.isShowFakeCam());
    		pool.send(MAIN.name, packet, false);		        
    	}
    }
    
    /**
     * 使我的状态改变为隐身
     */
    public void status_Hidden() {
    	if(user.isLoggedIn() && pool.hasConnection(MAIN.name)) {
	        user.setStatus(QQ.QQ_STATUS_HIDDEN);
	    	ChangeStatusPacket packet = new ChangeStatusPacket(user);
	    	packet.setShowFakeCam(user.isShowFakeCam());
    		pool.send(MAIN.name, packet, false);	    	        
    	}
    }
    
    /**
     * 使我的状态改变成在线
     */
    public void status_Online() {
    	if(user.isLoggedIn() && pool.hasConnection(MAIN.name)) {
	        user.setStatus(QQ.QQ_STATUS_ONLINE);
	    	ChangeStatusPacket packet = new ChangeStatusPacket(user);
	    	packet.setShowFakeCam(user.isShowFakeCam());
    		pool.send(MAIN.name, packet, false);	    
    	}
    }
    
    /**
     * 添加一个好友
     * @param qqNum 要添加的人的QQ号
     * @return 发送出的包的序号
     */
    public char user_Add(int qqNum) {
    	if(user.isLoggedIn()) {
	        AddFriendExPacket packet = new AddFriendExPacket(user);
	        packet.setTo(qqNum);
    		pool.send(MAIN.name, packet, false);	
	        return packet.getSequence();
    	}
    	return 0;
    }
    
    /**
     * 添加好友到服务器端的好友列表中
     * 
     * @param group
     * 		好友的组号，我的好友组是0，然后是1，2，...
     * @param qqNum
     * 		要添加的好友QQ号
     */
    public void user_AddToList(int group, int qqNum) {
        if(user.isLoggedIn()) {
            UploadGroupFriendPacket packet = new UploadGroupFriendPacket(user);
            packet.addFriend(group, qqNum);
    		pool.send(MAIN.name, packet, false);	
        }
    }
    
	/**
     * @param page
     * @param online
     * @param hasCam
     * @param provinceIndex
     * @param cityIndex
     * @param ageIndex
     * @param genderIndex
     * @return
     */
    public char user_AdvancedSearch(
            int page,
            boolean online,
            boolean hasCam,
            int provinceIndex,
            int cityIndex,
            int ageIndex,
            int genderIndex) {
        if(user.isLoggedIn()) {
            AdvancedSearchUserPacket packet = new AdvancedSearchUserPacket(user);
            packet.setPage((char)page);
            packet.setSearchOnline(online);
            packet.setHasCam(hasCam);
            packet.setProvinceIndex((char)provinceIndex);
            packet.setCityIndex((char)cityIndex);
            packet.setAgeIndex((byte)ageIndex);
            packet.setGenderIndex((byte)genderIndex);
    		pool.send(MAIN.name, packet, false);	
            return packet.getSequence();
        } else
            return 0;
    }
	
	/**
     * 如果我要同意一个人加我为好友的请求，用这个方法发送同意消息
     * @param qqNum 请求加我的人的QQ号
     */
    public void user_ApproveAdd(int qqNum) {
    	if(user.isLoggedIn()) {
	        AddFriendAuthResponsePacket packet = new AddFriendAuthResponsePacket(user);
	        packet.setTo(qqNum);
	        packet.setAction(QQ.QQ_MY_AUTH_APPROVE);
    		pool.send(MAIN.name, packet, true);	
    	}
    }
    
    /**
     * 删除一个好友
     * @param qqNum 要删除的好友的QQ号
     * @return 如果包发送成功则返回包序号，否则返回0
     */
    public char user_Delete(int qqNum) {
    	if(user.isLoggedIn()) {
	        DeleteFriendPacket packet = new DeleteFriendPacket(user);
	        packet.setTo(qqNum);
    		pool.send(MAIN.name, packet, true);		
	        return packet.getSequence();
    	}
    	return 0;
    }
    
	/**
     * 删除个性签名
     */
    public char user_DeleteSignature() {
    	if(user.isLoggedIn()) {
    		SignatureOpPacket packet = new SignatureOpPacket(user);
    		packet.setSubCommand(QQ.QQ_SUB_CMD_DELETE_SIGNATURE);
    		pool.send(MAIN.name, packet, false);
    		return packet.getSequence();
    	}
    	return 0;
    }	

	/**
     * 下载好友分组
     * 
     * @param beginFrom
     * 		起始好友号
     */
    public void user_DownloadGroups(int beginFrom) {
    	if(user.isLoggedIn()) {
    		DownloadGroupFriendPacket packet = new DownloadGroupFriendPacket(user);
    		packet.setBeginFrom(beginFrom);
    		pool.send(MAIN.name, packet, true);	
    	}
    }

	/**
     * 下载分组名称
     */
    public void user_GetGroupNames() {
    	if(user.isLoggedIn()) {
    		GroupDataOpPacket packet = new GroupDataOpPacket(user);
    		packet.setType(QQ.QQ_SUB_CMD_DOWNLOAD_GROUP_NAME);
    		pool.send(MAIN.name, packet, true);	
    	}
    }
	
	/**
     * 得到一个用户的详细信息
     * @param qqNum 想要得到的用户QQ号
     */
    public void user_GetInfo(int qqNum) {
    	if(user.isLoggedIn()) {
	        GetUserInfoPacket packet = new GetUserInfoPacket(user);
	        packet.setQQ(qqNum);
    		pool.send(MAIN.name, packet, false);			
    	}
    }
    
    /**
     * 得到好友等级
     * 
     * @param friends
     */
    public void user_GetLevel(List<Integer> friends) {
    	if(user.isLoggedIn()) {
    		FriendLevelOpPacket packet = new FriendLevelOpPacket(user);
    		packet.setFriends(friends);
    		packet.setSubCommand(QQ.QQ_SUB_CMD_GET_FRIEND_LEVEL);
    		pool.send(MAIN.name, packet, false);
    	}
    }
    
    /**
	 * 请求取得好友名单，如果成功，将会触发get friend list success和
	 * get friend list end事件，用户需要监听这两个事件来得到所有的
	 * 好友信息
	 */
	public void user_GetList() {
	    if(pool.hasConnection(MAIN.name)) {
			// 请求取得好友名单
			GetFriendListPacket packet = new GetFriendListPacket(user);
    		pool.send(MAIN.name, packet, false);			        
	    }
	}
    
    /**
	 * 请求取得好友名单，position指定了名单开始的偏移，建议初级用户不要使用此API
	 * @param position
	 */
	public void user_GetList(char position) {
	    if(pool.hasConnection(MAIN.name)) {
			// 请求取得好友名单
			GetFriendListPacket packet = new GetFriendListPacket(user);
			packet.setStartPosition(position);
    		pool.send(MAIN.name, packet, false);		        
	    }
	} 
    
    /**
     * 请求取得当前在线好友列表
     */
    public void user_GetOnline() {
    	if(pool.hasConnection(MAIN.name)) {
	        GetOnlineOpPacket packet = new GetOnlineOpPacket(user);
    		pool.send(MAIN.name, packet, false);		
    	}
    }
    
    /**
     * 请求取得当前在线好友列表
     */
    public void user_GetOnline(int startPosition) {
    	if(pool.hasConnection(MAIN.name)) {
	        GetOnlineOpPacket packet = new GetOnlineOpPacket(user);
	        packet.setStartPosition(startPosition);
    		pool.send(MAIN.name, packet, false);			
    	}
    }
    
    /**
     * 得到用户属性
     * 
     * @param startPosition
     */
    public void user_GetProperty(char startPosition) {
    	if(pool.hasConnection(MAIN.name)) {
    		UserPropertyOpPacket packet = new UserPropertyOpPacket(user);
    		packet.setStartPosition(startPosition);
    		packet.setSubCommand(QQ.QQ_SUB_CMD_GET_USER_PROPERTY);
    		pool.send(MAIN.name, packet, false);
    	}
    }
    
    /**
     * 下载好友备注信息
     * 
     * @param qqNum 
     * 		好友的QQ号
     */
    public void user_GetRemark(int qqNum) {
    	if(user.isLoggedIn()) {
    		FriendDataOpPacket packet = new FriendDataOpPacket(user);
    		packet.setSubCommand(QQ.QQ_SUB_CMD_DOWNLOAD_FRIEND_REMARK);
    		packet.setQQ(qqNum);
    		pool.send(MAIN.name, packet, false);	
    	}
    }
    
    /**
     * 批量下载好友备注
     * 
     * @param page
     * 		页号
     */
    public void user_GetRemarks(int page) {
    	if(user.isLoggedIn()) {
    		FriendDataOpPacket packet = new FriendDataOpPacket(user);
    		packet.setSubCommand(QQ.QQ_SUB_CMD_BATCH_DOWNLOAD_FRIEND_REMARK);
    		packet.setPage(page);
    		pool.send(MAIN.name, packet, false);	
    	}
    }
    
    /**
     * 得到个性签名
     * 
     * @param sigs
     */
    public void user_GetSignature(List<Signature> sigs) {
    	if(user.isLoggedIn()) {
    		SignatureOpPacket packet = new SignatureOpPacket(user);
    		packet.setSubCommand(QQ.QQ_SUB_CMD_GET_SIGNATURE);
    		packet.setSignatures(sigs);
    		pool.send(MAIN.name, packet, false);
    	}
    }
    
    /**
     * 修改用户的个人信息
     * @param oldPassword 老密码，如果不修改密码，设成null
     * @param newPassword 新密码，如果不修改密码，设成null
     * @param contactInfo 其他信息
     */
    public void user_ModifyInfo(String oldPassword, String newPassword, ContactInfo contactInfo) {
    	if(user.isLoggedIn()) {
	        ModifyInfoPacket packet = new ModifyInfoPacket(user);
	        packet.setOldPassword(oldPassword);
	        packet.setNewPassword(newPassword);
	        String[] infos = contactInfo.getInfoArray();
	        for(int i = 1; i < QQ.QQ_COUNT_MODIFY_USER_INFO_FIELD; i++) {
	            if(infos[i].equals("-"))
	                infos[i] = "";
	        }
	        packet.setContactInfo(contactInfo);
    		pool.send(MAIN.name, packet, true);			
    	}
    }
    
    /**
     * 修改个性签名
     * 
     * @param sig
     * 		个性签名
     */
    public char user_ModifySignature(String sig) {
    	if(user.isLoggedIn()) {
    		SignatureOpPacket packet = new SignatureOpPacket(user);
    		packet.setSubCommand(QQ.QQ_SUB_CMD_MODIFY_SIGNATURE);
    		packet.setSignature(sig);
    		pool.send(MAIN.name, packet, false);
    		return packet.getSequence();
    	}
    	return 0;
    }
    
    /**
     * 如果我要拒绝一个人加我为好友的请求，用这个方法发送拒绝消息
     * @param qqNum 请求加我的人的QQ号
     * @param message 附加消息
     */
    public void user_RejectAdd(int qqNum, String message) {
    	if(user.isLoggedIn()) {
	        AddFriendAuthResponsePacket packet = new AddFriendAuthResponsePacket(user);
	        packet.setTo(qqNum);
	        packet.setMessage(message);
	        packet.setAction(QQ.QQ_MY_AUTH_REJECT);
    		pool.send(MAIN.name, packet, true);	
    	}
    }
    
    /**
     * 把好友从服务器端的好友列表中删除
     * 
     * @param qqNum
     * 		要删除的好友QQ号
     */
    public void user_RemoveFromList(int qqNum) {
        if(user.isLoggedIn()) {
            FriendDataOpPacket packet = new FriendDataOpPacket(user);
            packet.setSubCommand(QQ.QQ_SUB_CMD_REMOVE_FRIEND_FROM_LIST);
            packet.setQQ(qqNum);
    		pool.send(MAIN.name, packet, true);	
        }
    }
    
    /**
     * 把某人的好友列表中的自己删除
     * @param qqNum 我想把我自己删除的好友的QQ号
     */
    public void user_RemoveSelfFrom(int qqNum) {
    	if(user.isLoggedIn()) {
	        RemoveSelfPacket packet = new RemoveSelfPacket(user);
	        packet.setRemoveFrom(qqNum);
    		pool.send(MAIN.name, packet, true);			
    	}
    }
    
    /**
     * 搜索所有的在线用户
     * @param page 页号，从0开始
     * @return
     * 		包序号
     */
    public char user_Search(int page) {
    	if(user.isLoggedIn()) {
    		SearchUserPacket packet = new SearchUserPacket(user);
    		packet.setPage(page);
    		pool.send(MAIN.name, packet, false);	
	        return packet.getSequence();
    	} else
    	    return 0;
    }
    
    /**
     * 自定义搜索用户
     * @param page 页号
     * @param qqNum 要搜索的QQ号字符串形式
     * @param nick 要搜索的昵称
     * @param email 要搜索的email
     * @param matchEntire 字符串是否完全匹配
     * @return
     * 		包序号
     */
    public char user_Search(int page, String qqStr, String nick, String email) {
    	if(user.isLoggedIn()) {
    		SearchUserPacket packet = new SearchUserPacket(user);
    		if(qqStr == null || qqStr.trim().equals(""))
    			packet.setSearchType(QQ.QQ_SEARCH_CUSTOM_FUZZY);
    		else
    			packet.setSearchType(QQ.QQ_SEARCH_CUSTOM_ACCURATE);
    		packet.setPage(page);
    		packet.setQQStr(qqStr);
    		packet.setNick(nick);
    		packet.setEmail(email);
    		pool.send(MAIN.name, packet, false);	
	        return packet.getSequence();
    	} else
    	    return 0;
    }
    
    /**
     * 如果要加的人需要认证，用这个方法发送验证请求
     * @param qqNum 要加的人的QQ号
     * @param message 附加的请求消息内容
     * @return
     * 		包序号
     */
    public char user_SendAuth(int qqNum, String message) {
    	if(user.isLoggedIn()) {
    		AuthorizePacket packet = new AuthorizePacket(user);
	        packet.setTo(qqNum);
	        packet.setMessage(message);    
    		pool.send(MAIN.name, packet, true);	
	        return packet.getSequence();
    	}
    	return 0;
    }

	/**
     * 设置仅能通过QQ号找到我
     */
    public void user_SetSearchMeByQQOnly() {
    	if(user.isLoggedIn()) {
    		PrivacyDataOpPacket packet = new PrivacyDataOpPacket(user);
    		packet.setSubCommand(QQ.QQ_SUB_CMD_SEARCH_ME_BY_QQ_ONLY);
    		packet.setOpCode(QQ.QQ_VALUE_SET);
    		pool.send(MAIN.name, packet, false);
    	}
    }

	/**
     * 共享我的地理位置
     */
    public void user_ShareGeography() {
    	if(user.isLoggedIn()) {
    		PrivacyDataOpPacket packet = new PrivacyDataOpPacket(user);
    		packet.setSubCommand(QQ.QQ_SUB_CMD_SHARE_GEOGRAPHY);
    		packet.setOpCode(QQ.QQ_VALUE_SET);
    		pool.send(QQPort.MAIN.name, packet, false);
    	}
    }
	
	/**
     * 取消设置仅能通过QQ号找到我
     */
    public void user_UnsetSearchMeByQQOnly() {
    	if(user.isLoggedIn()) {
    		PrivacyDataOpPacket packet = new PrivacyDataOpPacket(user);
    		packet.setSubCommand(QQ.QQ_SUB_CMD_SEARCH_ME_BY_QQ_ONLY);
    		packet.setOpCode(QQ.QQ_VALUE_UNSET);
    		pool.send(MAIN.name, packet, false);
    	}
    }

	/**
     * 不共享我的地理位置
     */
    public void user_UnshareGeography() {
    	if(user.isLoggedIn()) {
    		PrivacyDataOpPacket packet = new PrivacyDataOpPacket(user);
    		packet.setSubCommand(QQ.QQ_SUB_CMD_SHARE_GEOGRAPHY);
    		packet.setOpCode(QQ.QQ_VALUE_UNSET);
    		pool.send(MAIN.name, packet, false);
    	}
    }

	/**
     * 上传分组名称
     * 
     * @param groups
     */
    public void user_UploadGroupNames(List<String> groups) {
    	if(user.isLoggedIn()) {
    		GroupDataOpPacket packet = new GroupDataOpPacket(user);
    		packet.setType(QQ.QQ_SUB_CMD_UPLOAD_GROUP_NAME);
    		packet.setGroups(groups);
    		pool.send(MAIN.name, packet, true);	
    	}
    }

	/**
     * 上传好友备注信息
     * 
     * @param qqNum 
     * 		好友的QQ号
     * @param remark
     * 		备注类
     */
    public void user_UploadRemark(int qqNum, FriendRemark remark) {
    	if(user.isLoggedIn()) {
    		FriendDataOpPacket packet = new FriendDataOpPacket(user);
    		packet.setRemark(remark);
    		packet.setQQ(qqNum);
    		pool.send(MAIN.name, packet, true);	
    	}
    }

	/**
     * 请求自己这里的天气预报
     * 
     * @param ip
     */
    public char weather_Get() {
    	if(user.isLoggedIn()) {
    		WeatherOpPacket packet = new WeatherOpPacket(user);
    		packet.setIp(user.getIp());
    		pool.send(MAIN.name, packet, false);
    		return packet.getSequence();
    	}
    	return 0;
    }

	/**
     * 请求天气预报
     * 
     * @param ip
     */
    public char weather_Get(byte[] ip) {
    	if(user.isLoggedIn()) {
    		WeatherOpPacket packet = new WeatherOpPacket(user);
    		packet.setIp(ip);
    		pool.send(MAIN.name, packet, false);
    		return packet.getSequence();
    	}
    	return 0;
    }
    
    public void login_RefreshPuzzle(byte[] token) {
    	if(pool.hasConnection(MAIN.name)) {
    		GetLoginTokenPacket packet = new GetLoginTokenPacket(user);
    		packet.setSubCommand(QQ.QQ_SUB_CMD_SUBMIT_VERIFY_CODE);
    		packet.setPuzzleToken(token);
    		pool.send(MAIN.name, packet, false);
    	}
    }
    
    public void login_submitVerifyCode(byte[] token, String verifyCode) {
    	if(pool.hasConnection(MAIN.name)) {
    		GetLoginTokenPacket packet = new GetLoginTokenPacket(user);
    		packet.setSubCommand(QQ.QQ_SUB_CMD_SUBMIT_VERIFY_CODE);
    		packet.setPuzzleToken(token);
    		packet.setVerifyCode(verifyCode);
    		pool.send(MAIN.name, packet, false);
    	}
    }
}
