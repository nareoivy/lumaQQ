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
package edu.tsinghua.lumaqq.qq.events;

import java.util.EventObject;

/**
 * <pre>
 * QQ事件类。QQ事件是比包事件更高一级的封装。其代表了某个具体动作的发生，包事件是粗粒度的，
 * QQ事件是细粒度的。每个QQ事件都有一个相应的包与之关联，这些包中的字段是否可用，要根据
 * QQ事件来判断。按理来说是应该写个事件参考手册的。但是懒，就不写了。
 * </pre>
 * 
 * @author luma
 */
public class QQEvent extends EventObject {
	/*
	 * Cluster事件，范围0x0000 ~ 0x0FFF
	 */
	
	/**  */
	private static final long serialVersionUID = -2834141251489252806L;

	/**
	 * <code>CLUSTER_ACTIVATE_FAIL</code>在激活一个群失败时发生，source是ClusterCommandReplyPacket
	 */
	public static final int CLUSTER_ACTIVATE_FAIL = 0x0000;

	/**
	 * <code>CLUSTER_ACTIVATE_OK</code>在激活一个群成功时发生，source是ClusterCommandReplyPacket
	 */
	public static final int CLUSTER_ACTIVATE_OK = 0x0001;

	/**
	 * <code>CLUSTER_ACTIVATE_TEMP_FAIL</code>事件在激活临时群
	 * 失败时发生，source是ClusterCommandReplyPacket
	 */
	public static final int CLUSTER_ACTIVATE_TEMP_FAIL = 0x0002;

	/**
	 * <code>CLUSTER_ACTIVATE_TEMP_OK</code>事件在激活临时群
	 * 成功时发生，source是ClusterCommandReplyPacket
	 */
	public static final int CLUSTER_ACTIVATE_TEMP_OK = 0x0003;

	/**
	 * <code>CLUSTER_ADDED_TO</code>事件在别人把我加为群中成员时发生，别人可以是一开始就
	 * 创建了群并我加入到群中，也可以是先创建了群，后来才加的我，反正都是触发这一个事件，source
	 * 是ReceiveIMPacket
	 */
	public static final int CLUSTER_ADDED_TO = 0x0004;

	/**
	 * <code>CLUSTER_ADMIN_ENTITLED</code>事件在群创建者把自己设为管理员时发生,
	 * source是ReceiveIMPacket
	 */
	public static final int CLUSTER_ADMIN_ENTITLED = 0x0005;

	/**
	 * <code>CLUSTER_ADMIN_WITHDRAWED</code>事件在群创建者把自己的管理员身份撤销时发生,
	 * source是ReceiveIMPacket
	 */
	public static final int CLUSTER_ADMIN_WITHDRAWED = 0x0006;

	/**
	 * <code>CLUSTER_APPROVE_JOIN</code>事件发生在别人同意了我加入他创建的群时，
	 * source是ReceiveIMPacket
	 */
	public static final int CLUSTER_APPROVE_JOIN = 0x0007;

	/**
	 * <code>CLUSTER_AUTH_SEND_FAIL</code>在加入群认证信息失败时发生，source
	 * 是ClusterCommandReplyPacket
	 */
	public static final int CLUSTER_AUTH_SEND_FAIL = 0x0008;

	/**
	 * <code>CLUSTER_AUTH_SEND_OK</code>在发送群认证信息成功时发生，source
	 * 是ClusterCommandReplyPacket，这只是一个简单的服务器确认事件，表示认证信息已经被
	 * 服务器收到，并非认证已经通过，所以是send success
	 */
	public static final int CLUSTER_AUTH_SEND_OK = 0x0009;

	/** 
	 * <code>QQ_COMMIT_MEMBER_ORGANIZATON_FAIL</code>事件在提交成员分组
	 * 失败时发生，source是ClusterCommandReplyPacket
	 */
	public static final int CLUSTER_COMMIT_MEMBER_ORG_FAIL = 0x000A;

	/** 
	 * <code>CLUSTER_COMMIT_MEMBER_ORG_OK</code>事件在提交成员分组
	 * 成功时发生，source是ClusterCommandReplyPacket
	 */
	public static final int CLUSTER_COMMIT_MEMBER_ORG_OK = 0x000B;

	/** 
	 * <code>CLUSTER_COMMIT_ORG_FAIL</code>事件在提交组织架构失败时发生，
	 * source是ClusterCommandReplyPacket
	 */
	public static final int CLUSTER_COMMIT_ORG_FAIL = 0x000C;

	/** 
	 * <code>CLUSTER_COMMIT_ORG_OK</code>事件在提交组织架构成功时发生，
	 * source是ClusterCommandReplyPacket
	 */
	public static final int CLUSTER_COMMIT_ORG_OK = 0x000D;

	/**
	 * <code>CLUSTER_CREATE_FAIL</code>在创建一个群失败时发生，source是ClusterCommandReplyPacket
	 */
	public static final int CLUSTER_CREATE_FAIL = 0x000E;

	/**
	 * <code>CLUSTER_CREATE_OK</code>在创建一个群成功时发生，source是ClusterCommandReplyPacket
	 */
	public static final int CLUSTER_CREATE_OK = 0x000F;

	/**
	 * <code>CLUSTER_CREATE_TEMP_FAIL</code>事件在创建临时群
	 * 失败时发生，source是ClusterCommandReplyPacket
	 */
	public static final int CLUSTER_CREATE_TEMP_FAIL = 0x0010;

	/**
	 * <code>CLUSTER_CREATE_TEMP_OK</code>事件在创建临时群
	 * 成功时发生，source是ClusterCommandReplyPacket
	 */
	public static final int CLUSTER_CREATE_TEMP_OK = 0x0011;

	/** 
	 * <code>CLUSTER_DISMISS_FAIL</code>在解散群失败时发生，
	 * source是ClusterCommandReplyPacket
	 */
	public static final int CLUSTER_DISMISS_FAIL = 0x0012;

	/** 
	 * <code>CLUSTER_DISMISS_OK</code>在解散群成功时发生，
	 * source是ClusterCommandReplyPacket
	 */
	public static final int CLUSTER_DISMISS_OK = 0x0013;

	/**
	 * <code>CLUSTER_EXIT_FAIL</code>在退出群失败时发生，source是ClusterCommandReplyPacket
	 */
	public static final int CLUSTER_EXIT_FAIL = 0x0014;

	/**
	 * <code>CLUSTER_EXIT_OK</code>在退出群成功时发生，source是ClusterCommandReplyPacket
	 */
	public static final int CLUSTER_EXIT_OK = 0x0015;

	/**
	 * <code>CLUSTER_EXIT_TEMP_FAIL</code>事件在退出临时群
	 * 失败时发生，source是ClusterCommandReplyPacket
	 */
	public static final int CLUSTER_EXIT_TEMP_FAIL = 0x0016;

	/**
	 * <code>CLUSTER_EXIT_TEMP_OK</code>事件在退出临时群
	 * 成功时发生，source是ClusterCommandReplyPacket
	 */
	public static final int CLUSTER_EXIT_TEMP_OK = 0x0017;

	/** 
	 * <code>CLUSTER_GET_CARD_FAIL</code>事件在得到单个成员群名片
	 * 失败时发生，source是ClusterCommandReplyPacket
	 */
	public static final int CLUSTER_GET_CARD_FAIL = 0x0018;

	/** 
	 * <code>CLUSTER_GET_CARD_OK</code>事件在得到单个成员群名片
	 * 成功时发生，source是ClusterCommandReplyPacket
	 */
	public static final int CLUSTER_GET_CARD_OK = 0x0019;

	/** 
	 * <code>CLUSTER_GET_CARDS_FAIL</code>事件在批量得到群名片
	 * 真实姓名失败时发生，source是ClusterCommandReplyPacket
	 */
	public static final int CLUSTER_GET_CARDS_FAIL = 0x001A;

	/** 
	 * <code>CLUSTER_GET_CARDS_OK</code>事件在批量得到群名片
	 * 真实姓名成功时发生，source是ClusterCommandReplyPacket
	 */
	public static final int CLUSTER_GET_CARDS_OK = 0x001B;

	/**
	 * <code>CLUSTER_GET_INFO_FAIL</code>在得到群信息失败时发生，source是ClusterCommandReplyPacket
	 */
	public static final int CLUSTER_GET_INFO_FAIL = 0x001C;

	/**
	 * <code>CLUSTER_GET_INFO_OK</code>在得到群信息成功时发生，source是ClusterCommandReplyPacket
	 */
	public static final int CLUSTER_GET_INFO_OK = 0x001D;

	/**
	 * <code>CLUSTER_GET_MEMBER_INFO_FAIL</code>在得到群成员信息失败时发生，source是ClusterCommandReplyPacket
	 */
	public static final int CLUSTER_GET_MEMBER_INFO_FAIL = 0x001E;

	/**
	 * <code>CLUSTER_GET_MEMBER_INFO_OK</code>在得到群成员信息成功时发生，source是ClusterCommandReplyPacket
	 */
	public static final int CLUSTER_GET_MEMBER_INFO_OK = 0x001F;

	/**
	 * <code>CLUSTER_GET_ONLINE_MEMBER_FAIL</code>在得到在线群成员失败时发生，source是ClusterCommandReplyPacket
	 */
	public static final int CLUSTER_GET_ONLINE_MEMBER_FAIL = 0x0020;

	/**
	 * <code>CLUSTER_GET_ONLINE_MEMBER_OK</code>在得到在线群成员成功时发生，source是ClusterCommandReplyPacket
	 */
	public static final int CLUSTER_GET_ONLINE_MEMBER_OK = 0x0021;

	/**
	 * <code>CLUSTER_GET_TEMP_INFO_FAIL</code>事件在得到临时群
	 * 信息失败时发生，source是ClusterCommandReplyPacket
	 */
	public static final int CLUSTER_GET_TEMP_INFO_FAIL = 0x0022;

	/**
	 * <code>CLUSTER_GET_TEMP_INFO_OK</code>事件在得到临时群
	 * 信息成功时发生，source是ClusterCommandReplyPacket
	 */
	public static final int CLUSTER_GET_TEMP_INFO_OK = 0x0023;

	/**
	 * <code>CLUSTER_JOIN</code>事件发生在有人想加入我创建的群时，source是ReceiveIMPacket
	 */
	public static final int CLUSTER_JOIN = 0x0024;

	/**
	 * <code>CLUSTER_JOIN_DENY</code>在我申请加入群，但是这个群禁止加入成员时发生，source是
	 * ClusterCommandReplyPacket
	 */
	public static final int CLUSTER_JOIN_DENY = 0x0025;

	/**
	 * <code>CLUSTER_JOIN_FAIL</code>在加入群失败时发生，source是ClusterCommandReplyPacket
	 */
	public static final int CLUSTER_JOIN_FAIL = 0x0026;

	/**
	 * <code>CLUSTER_JOIN_NEED_AUTH</code>在我申请加入群，但是这个群需要认证时发生，source
	 * 是ClusterCommandReplyPacket
	 */
	public static final int CLUSTER_JOIN_NEED_AUTH = 0x0027;

	/**
	 * <code>CLUSTER_JOIN_OK</code>在加入群成功时发生，source是ClusterCommandReplyPacket
	 */
	public static final int CLUSTER_JOIN_OK = 0x0028;

	/** 
	 * <code>CLUSTER_MODIFY_CARD_FAIL</code>事件在修改群名片
	 * 信息失败时发生，source是ClusterCommandReplyPacket
	 */
	public static final int CLUSTER_MODIFY_CARD_FAIL = 0x0029;

	/** 
	 * <code>CLUSTER_MODIFY_CARD_OK</code>事件在修改群名片
	 * 信息成功时发生，source是ClusterCommandReplyPacket
	 */
	public static final int CLUSTER_MODIFY_CARD_OK = 0x002A;

	/**
	 * <code>CLUSTER_MODIFY_INFO_FAIL</code>在修改群信息失败时发生，source是ClusterCommandReplyPacket
	 */
	public static final int CLUSTER_MODIFY_INFO_FAIL = 0x002B;

	/**
	 * <code>CLUSTER_MODIFY_INFO_OK</code>在修改群信息成功时发生，source是ClusterCommandReplyPacket
	 */
	public static final int CLUSTER_MODIFY_INFO_OK = 0x002C;

	/**
	 * <code>CLUSTER_MODIFY_MEMBER_FAIL</code>事件发生在修改群成员列表失败时，其
	 * source是ClusterCommandReplyPacket
	 */
	public static final int CLUSTER_MODIFY_MEMBER_FAIL = 0x002D;

	/**
	 * <code>CLUSTER_MODIFY_MEMBER_OK</code>事件发生在修改群成员列表成功时，
	 * 其source是ClusterCommandReplyPacket
	 */
	public static final int CLUSTER_MODIFY_MEMBER_OK = 0x002E;

	/** 
	 * <code>CLUSTER_MODIFY_TEMP_INFO_FAIL</code>事件在修改临时群
	 * 信息失败时发生，source是ClusterCommandReplyPacket
	 */
	public static final int CLUSTER_MODIFY_TEMP_INFO_FAIL = 0x002F;

	/** 
	 * <code>CLUSTER_MODIFY_TEMP_INFO_OK</code>事件在修改临时群
	 * 信息成功时发生，source是ClusterCommandReplyPacket
	 */
	public static final int CLUSTER_MODIFY_TEMP_INFO_OK = 0x0030;

	/**
	 * <code>CLUSTER_MODIFY_TEMP_MEMBER_FAIL</code>事件在修改临时群成员
	 * 失败时发生，source是ClusterCommandReplyPacket
	 */
	public static final int CLUSTER_MODIFY_TEMP_MEMBER_FAIL = 0x0031;

	/**
	 * <code>CLUSTER_MODIFY_TEMP_MEMBER_OK</code>事件在修改临时群成员
	 * 成功时发生，source是ClusterCommandReplyPacket
	 */
	public static final int CLUSTER_MODIFY_TEMP_MEMBER_OK = 0x0032;

	/**
	 * <code>CLUSTER_REJECT_JOIN</code>事件发生在别人拒绝了我加入他创建的群时，
	 * source是ReceiveIMPacket
	 */
	public static final int CLUSTER_REJECT_JOIN = 0x0033;

	/**
	 * <code>CLUSTER_REMOVED_FROM</code>事件在群的创建者把我删除后发生，
	 * source是ReceiveIMPacket。这个事件会在某个人退出群后，或者管理员删除某个人后
	 * 发生，在第一种情况下，这个事件传达给管理员，在第二种情况下，这个事件传达给这个用户。
	 * 所以，必须判断包中的sender QQ号，如果等于自己的QQ号，说明是自己被删除了，如果不
	 * 等于，说明我自己是管理员，有个成员主动退出了
	 */
	public static final int CLUSTER_REMOVED_FROM = 0x0034;

	/**
	 * <code>CLUSTER_SEARCH_FAIL</code>事件在搜索群失败时发生，这也许是搜索出错，也许是没有搜到
	 * 任何结果等等，source是ClusterCommandReplyPacket
	 */
	public static final int CLUSTER_SEARCH_FAIL = 0x0035;

	/**
	 * <code>CLUSTER_SEARCH_OK</code>事件在搜索群成功时发生，source是ClusterCommandReplyPacket
	 */
	public static final int CLUSTER_SEARCH_OK = 0x0036;

	/**
	 * <code>CLUSTER_SET_ROLE_FAIL</code>事件在群创建者设置管理员失败时发生,
	 * source是ClusterCommandReplyPacket这个事件不一定是针对自己的，需要
	 * 检查接收者是否为自己
	 */
	public static final int CLUSTER_SET_ROLE_FAIL = 0x0037;

	/**
	 * <code>CLUSTER_SET_ROLE_OK</code>事件在群创建者设置管理员成功时发生,
	 * source是ClusterCommandReplyPacket，这个事件不一定是针对自己的，需要
	 * 检查接收者是否为自己
	 */
	public static final int CLUSTER_SET_ROLE_OK = 0x0038;

	/** 
	 * <code>QQ_GET_SUBJECT_LIST_FAIL</code>事件在子群操作失败时发生，
	 * source是ClusterCommandReplyPacket
	 */
	public static final int CLUSTER_SUB_OP_FAIL = 0x0039;

	/** 
	 * <code>QQ_GET_SUBJECT_LIST_SUCCESS</code>事件在子群操作成功时发生，
	 * source是ClusterCommandReplyPacket
	 */
	public static final int CLUSTER_SUB_OP_OK = 0x003A;

	/**
	 * <code>CLUSTER_TRANSFER_ROLE_FAIL</code>事件在群创建者转让身份失败时发生,
	 * source是ClusterCommandReplyPacket, 这个事件不一定是针对自己的，需要
	 * 检查接收者是否为自己
	 */
	public static final int CLUSTER_TRANSFER_ROLE_FAIL = 0x003B;

	/**
	 * <code>CLUSTER_TRANSFER_ROLE_OK</code>事件在群创建者转让身份成功时发生,
	 * source是ClusterCommandReplyPacket, 这个事件不一定是针对自己的，需要
	 * 检查接收者是否为自己
	 */
	public static final int CLUSTER_TRANSFER_ROLE_OK = 0x003C;

	/** 
	 * <code>CLUSTER_UPDATE_ORG_FAIL</code>事件在更新组织架构失败时发生，
	 * source是ClusterCommandReplyPacket
	 */
	public static final int CLUSTER_UPDATE_ORG_FAIL = 0x003D;

	/** 
	 * <code>CLUSTER_UPDATE_ORG_OK</code>事件在更新组织架构成功时发生，
	 * source是ClusterCommandReplyPacket
	 */
	public static final int CLUSTER_UPDATE_ORG_OK = 0x003E;
	
	/*
	 * 错误事件，0x1000 ~ 0x1FFF
	 */

	/**
	 * <code>ERROR_CONNECTION_BROKEN</code>事件发生在连接被远程关闭时, Source是ErrorPacket
	 */
	public static final int ERROR_CONNECTION_BROKEN = 0x1000;

	/**
	 * <code>ERROR_NETWORK</code>事件在网络出错时发生，和QQ协议本身没有关系，其Source为
	 * ErrorPacket
	 */
	public static final int ERROR_NETWORK = 0x1001;

	/**
	 * <code>ERROR_PROXY</code>事件是一个代理事件，和QQ协议本身没有关系，其在代理发生错误时触发，
	 * 其Source是ErrorPacket
	 */
	public static final int ERROR_PROXY = 0x1002;

	/**
	 * <code>ERROR_RUNTIME</code>事件在发生运行时错误时触发，和QQ协议本身没有关系，其Source为
	 * ErrorPacket。其portName为空，不可用。其errorMessage为崩溃报告。
	 */
	public static final int ERROR_RUNTIME = 0x1003;
	
	/*
	 * 文件传输事件，0x2000 ~ 0x2FFF
	 */

	/**
	 * <code>FILE_ACCEPT</code>事件在对方接受了自己的传输文件请求时发生，source是ReceiveIMPacket，
	 * 接到这个包就可以抽出好友的端口开始初始化连接了
	 */
	public static final int FILE_ACCEPT = 0x2000;

	/**
	 * <code>FILE_CANCEL</code>在用户取消传送文件操作时发生，source是ReceiveIMPacket
	 */
	public static final int FILE_CANCEL = 0x2001;

	/** 
	 * <code>FILE_FACE_DATA_RECEIVED</code>事件在接收到表情文件数据时发生，source
	 * 是TransferReplyPacket
	 */
	public static final int FILE_FACE_DATA_RECEIVED = 0x2002;

	/** 
	 * <code>FILE_FACE_INFO_RECEIVED</code>事件在接收到表情文件信息时发生，source
	 * 是TransferReplyPacket
	 */
	public static final int FILE_FACE_INFO_RECEIVED = 0x2003;

	/**
	 * <code>FILE_NOTIFY_ARGS</code>在发送文件方通知另一方其IP和端口信息时发生，
	 * source是ReceiveIMPacket
	 */
	public static final int FILE_NOTIFY_ARGS = 0x2004;

	/**
	 * <code>FILE_REJECT</code>事件在对方拒绝了自己的传输文件请求时发生，source是ReceiveIMPacket
	 */
	public static final int FILE_REJECT = 0x2005;

	/**
	 * <code>FILE_REQUEST_AGENT_FAIL</code>事件在请求中转服务器成功时发生，source
	 * 是RequestAgentReplyPacket
	 */
	public static final int FILE_REQUEST_AGENT_FAIL = 0x2006;

	/**
	 * <code>QQ_REQUEST_AGENT_OK</code>事件在请求中转服务器成功时发生，source
	 * 是RequestAgentReplyPacket
	 */
	public static final int FILE_REQUEST_AGENT_OK = 0x2007;

	/**
	 * <code>FILE_REQUEST_AGENT_REDIRECT</code>事件在请求中转服务器重定向时发生，source
	 * 是RequestAgentReplyPacket
	 */
	public static final int FILE_REQUEST_AGENT_REDIRECT = 0x2008;

	/**
	 * <code>FILE_REQUEST_BEGIN_OK</code>事件在请求开始传送成功时
	 * 发生，source是RequestBeginReplyPacket
	 */
	public static final int FILE_REQUEST_BEGIN_OK = 0x2009;

	/**
	 * <code>FILE_REQUEST_FACE_OK</code>事件在请求自定义表情文件成功时发生，
	 * source是RequestFaceReplyPacket
	 */
	public static final int FILE_REQUEST_FACE_OK = 0x200A;

	/**
	 * <code>FILE_REQUEST_ME_CONNECT</code>发生在文件传输的连接建立过程完成时，source
	 * 是ReceiveIMPacket
	 */
	public static final int FILE_REQUEST_ME_CONNECT = 0x200B;

	/**
	 * <code>FILE_REQUEST_SEND_TO_ME</code>事件发生在对方请求向我传送文件时，source是ReceiveIMPacket
	 */
	public static final int FILE_REQUEST_SEND_TO_ME = 0x200C;

	/**
	 * <code>FILE_SEND_REQUEST_OK</code>事件在传送文件请求发送成功时发生，source是
	 * SendIMPacket
	 */
	public static final int FILE_SEND_REQUEST_OK = 0x200D;

	/**
	 * <code>QQ_TRANSFER_FACE_INFO_SUCCESS</code>事件在传送表情文件信息或数据成功时
	 * 发生，source是TransferReplyPacket。到底是对信息的回复还是对数据的回复，需要判断
	 * 包中的session id，如果和当前session id相同，则是对信息的回复，否则是对数据的回复
	 */
	public static final int FILE_TRANSFER_FACE_OK = 0x200E;
	
	/*
	 * 好友事件，0x3000 ~ 0x3FFF
	 */

	/**
	 * <code>FRIEND_ADD_ALREADY</code>事件发生在我添加一个好友，但是
	 * 对方已经是我的好友，source是AddFriendExReplyPacket
	 */
	public static final int FRIEND_ADD_ALREADY = 0x3000;

	/**
	 * <code>FRIEND_ADD_DENY</code>事件发生在我添加一个好友，但是对方设置了
	 * 禁止别人把我添加为好友，source是AddFriendExReplyPacket
	 */
	public static final int FRIEND_ADD_DENY = 0x3001;

	/**
	 * <code>FRIEND_ADD_FAIL</code>事件发生在请求信息发送失败时，source是AddFriendExPacket
	 */
	public static final int FRIEND_ADD_FAIL = 0x3002;

	/**
	 * <code>FRIEND_ADD_NEED_AUTH</code>事件发生在我添加一个好友，但是对方需要认证时，
	 * source是AddFriendExReplyPacket
	 */
	public static final int FRIEND_ADD_NEED_AUTH = 0x3003;

	/**
	 * <code>FRIEND_ADD_OK</code>事件发生在我添加一个好友成功时，
	 * source是AddFriendExReplyPacket
	 */
	public static final int FRIEND_ADD_OK = 0x3004;

	/**
	 * <code>FRIEND_AUTH_SEND_FAIL</code>事件在你发送认证信息给别人失败时发生，
	 * source是AddFriendAuthResponsePacket
	 */
	public static final int FRIEND_AUTH_SEND_FAIL = 0x3005;

	/**
	 * <code>FRIEND_AUTH_SEND_OK</code>事件在你发送认证信息给别人成功时发生，
	 * source是AddFriendAuthResponsePacket，注意不是AddFriendAuthReplyPacket，这个包没用
	 */
	public static final int FRIEND_AUTH_SEND_OK = 0x3006;

	/**
	 * <code>FRIEND_AUTHORIZE_SEND_FAIL</code>在验证消息发送失败时发生，
	 * source是AuthorizeReplyPacket 
	 */
	public static final int FRIEND_AUTHORIZE_SEND_FAIL = 0x3007;

	/**
	 * <code>FRIEND_AUTHORIZE_SEND_OK</code>在验证消息发送成功时发生，
	 * source是AuthorizeReplyPacket 
	 */
	public static final int FRIEND_AUTHORIZE_SEND_OK = 0x3008;

	/** 
	 * <code>FRIEND_CUSTOM_HEAD_CHANGED</code>收到好友自定义头像变化通知时
	 * 发生，source是ReceiveIMPacket
	 */
	public static final int FRIEND_CUSTOM_HEAD_CHANGED = 0x3009;

	/**
	 * <code>FRIEND_DELETE_FAIL</code>事件在删除好友失败是发生，
	 * source是DeleteFriendPacket
	 */
	public static final int FRIEND_DELETE_FAIL = 0x300A;

	/**
	 * <code>FRIEND_DELETE_OK</code>事件在删除好友成功时发生，
	 * source是DeleteFriendPacket，注意不是DeleteFriendReplyPacket，
	 * 因为Reply包毫无价值
	 */
	public static final int FRIEND_DELETE_OK = 0x300B;

	/**
	 * <code>FRIEND_DOWNLOAD_GROUPS_FAIL</code>事件在下载分组中的好友列表失败时发生，source是
	 * DownloadGroupFriendReplyPacket
	 */
	public static final int FRIEND_DOWNLOAD_GROUPS_FAIL = 0x300C;

	/**
	 * <code>FRIEND_DOWNLOAD_GROUPS_OK</code>事件在下载分组中的好友列表成功时发生，source是
	 * DownloadGroupFriendReplyPacket
	 */
	public static final int FRIEND_DOWNLOAD_GROUPS_OK = 0x300D;

	/**
	 * <code>FRIEND_GET_GROUP_NAMES_OK</code>事件在下载分组名称成功时发生，source是GroupNameOpReplyPacket
	 */
	public static final int FRIEND_GET_GROUP_NAMES_OK = 0x300E;
	
	/**
	 * <code>FRIEND_GET_LEVEL_OK</code>事件在得到用户级别成功时发生，
	 * source是FriendLevelOpPacket
	 */
	public static final int FRIEND_GET_LEVEL_OK = 0x300F;

	/**
	 * <code>FRIEND_GET_LIST_OK</code>事件发生在得到好友列表成功
	 * 时，source是GetFriendListReplyPacket，需要检查回复包的标志来判断是否
	 * 还有更多好友需要得到
	 */
	public static final int FRIEND_GET_LIST_OK = 0x3010;

	/**
	 * <code>FRIEND_GET_ONLINE_OK</code>事件在得到在线好友列表成功时发生，source是
	 * GetOnlineOpReplyPacket，用户应该检查position字段判断是否还有更多在线好友
	 */
	public static final int FRIEND_GET_ONLINE_OK = 0x3011;

	/**
	 * <code>FRIEND_GET_REMARK_OK</code>在下载好友备注信息成功时发生，source是
	 * FriendDataOpReplyPacket
	 */
	public static final int FRIEND_GET_REMARK_OK = 0x3012;

	/**
	 * <code>FRIEND_GET_REMARKS_OK</code>在批量下载好友备注信息成功时发生，source是
	 * FriendDataOpReplyPacket
	 */
	public static final int FRIEND_GET_REMARKS_OK = 0x3013;

	/** 
	 * <code>FRIEND_PROPERTY_CHANGED</code>收到好友属性变化通知时
	 * 发生，source是ReceiveIMPacket
	 */
	public static final int FRIEND_PROPERTY_CHANGED = 0x3014;

	/**
	 * <code>FRIEND_REMOVE_FROM_LIST_OK</code>在把好友从服务器端列表
	 * 中删除成功时发生，source是FriendDataOpPacket
	 */
	public static final int FRIEND_REMOVE_FROM_LIST_OK = 0x3015;

	/**
	 * <code>FRIEND_REMOVE_SELF_FAIL</code>事件在把自己从别人的好友列表中删除失败时发生，
	 * source是RemoveSelfReplyPacket，不过没什么用
	 */
	public static final int FRIEND_REMOVE_SELF_FAIL = 0x3016;

	/**
	 * <code>FRIEND_REMOVE_SELF_OK</code>事件在把自己从别人的好友列表中删除成功时发生，
	 * source是RemoveSelfReplyPacket，不过没什么用
	 */
	public static final int FRIEND_REMOVE_SELF_OK = 0x3017;

	/**
	 * <code>FRIEND_SIGNATURE_CHANGED</code>事件在收到系统的个性签名改变通知时发生，
	 * source是ReceiveIMPacket
	 */
	public static final int FRIEND_SIGNATURE_CHANGED = 0x3018;

	/**
	 * <code>FRIEND_STATUS_CHANGED</code>事件发生在某个好友的状态改变时，source是FriendChangeStatusPacket
	 */
	public static final int FRIEND_STATUS_CHANGED = 0x3019;

	/**
	 * <code>FRIEND_UPDATE_GROUP_NAMES_OK</code>事件在上传分组名称成功时发生，source是GroupNameOpReplyPacket,
	 * 但是基本没有什么可用信息，通知事件而已
	 */
	public static final int FRIEND_UPDATE_GROUP_NAMES_OK = 0x301A;

	/**
	 * <code>FRIEND_UPLOAD_GROUPS_FAIL</code>事件在上传分组好友列表失败时发生，source是
	 * UploadGroupFriendReplyPacket，通知事件而已
	 */
	public static final int FRIEND_UPLOAD_GROUPS_FAIL = 0x301B;

	/**
	 * <code>FRIEND_UPLOAD_GROUPS_OK</code>事件在上传分组中的好友列表成功时发生，source是
	 * UploadGroupFriendPacket，不过没有什么可用信息，通知事件而已
	 */
	public static final int FRIEND_UPLOAD_GROUPS_OK = 0x301C;

	/**
	 * <code>FRIEND_UPLOAD_REMARKS_OK</code>在上传好友备注信息成功时发生，source是
	 * FriendDataOpPacket
	 */
	public static final int FRIEND_UPLOAD_REMARKS_OK = 0x301D;
	
	/**
	 * <code>FRIEND_GET_AUTH_INFO_OK</code>事件在得到验证信息成功后发生，source是AuthInfoOpReplyPacket 
	 */
	public static final int FRIEND_GET_AUTH_INFO_OK = 0x301E;
	
	/**
	 * <code>FRIEND_GET_AUTH_INFO_FROM_URL</code>在请求验证信息后发生，表明验证码图片需要从一个url获取,
	 * source是AuthInfoOpReplyPacket 
	 */
	public static final int FRIEND_GET_AUTH_INFO_FROM_URL = 0x301F;
	
	/** 
	 * <code>FRIEND_SUBMIT_AUTO_INFO_OK</code>在提交验证信息成功后发生，source是AuthInfoOpReplyPacket
	 */
	public static final int FRIEND_SUBMIT_AUTO_INFO_OK = 0x3020;
	
	/**
	 * <code>FRIEND_SUBMIT_AUTO_INFO_FAIL</code>事件在提交验证信息失败后发生，source是AuthInfoOpReplyPacket
	 */
	public static final int FRIEND_SUBMIT_AUTO_INFO_FAIL = 0x3021;
	
	/** 
	 * <cdoe>FRIEND_GET_AUTH_QUESTION_OK</code>事件在得到认证问题成功时发生，source是AuthQuestionOpReplyPacket
	 */
	public static final int FRIEND_GET_AUTH_QUESTION_OK = 0x3022;
	
	/**
	 * <code>FRIEND_GET_AUTH_QUESTION_FAIL</code>事件在得到认证问题失败时发生，source是AuthQuestionOpReplyPacket
	 */
	public static final int FRIEND_GET_AUTH_QUESTION_FAIL = 0x3023;
	
	/** 
	 * <code>FRIEND_WRONG_ANSWER</code>事件在认证问题回答错误时发生，source是AuthQuestionOpReplyPacket
	 */
	public static final int FRIEND_WRONG_ANSWER = 0x3024;
	
	/** 
	 * <code>FRIEND_RIGHT_ANSWER</code>事件在认证问题回答正确时发生，source是AuthQuestionOpReplyPacket
	 */
	public static final int FRIEND_RIGHT_ANSWER = 0x3025;
	
	/*
	 * IM事件, 0x4000 ~ 0x4FFF
	 */

	/**
	 * <code>IM_CLUSTER_RECEIVED</code>在收到一条固定群消息时发生，source是ReceiveIMPacket
	 */
	public static final int IM_CLUSTER_RECEIVED = 0x4000;

	/**
	 * <code>IM_CLUSTER_SEND_EX_FAIL</code>事件在发送扩展群消息失败时
	 * 发生，source是ClusterCommandReplyPacket
	 */
	public static final int IM_CLUSTER_SEND_EX_FAIL = 0x4001;

	/**
	 * <code>IM_CLUSTER_SEND_EX_OK</code>事件在发送扩展群消息成功时
	 * 发生，source是ClusterCommandReplyPacket
	 */
	public static final int IM_CLUSTER_SEND_EX_OK = 0x4002;

	/**
	 * <code>IM_DUPLICATED</code>事件在收到一个重复的消息时发生，其source是
	 * ReceiveIMPacket。添加这个事件是为了解决有些消息的回复服务器收不到的问题
	 */
	public static final int IM_DUPLICATED = 0x4003;

	/**
	 * <code>IM_RECEIVED</code>事件在收到一个普通消息是发生，
	 * source是ReceiveIMPacket
	 */
	public static final int IM_RECEIVED = 0x4004;

	/**
	 * <code>IM_SEND_OK</code>事件在发送消息成功时发生，表示消息已经成功发送，source是
	 * SendIMPacket，注意不是SendIMReplyPacket，这个没什么用
	 */
	public static final int IM_SEND_OK = 0x4005;

	/**
	 * <code>IM_TEMP_CLUSTER_RECEIVED</code>事件在收到一条临时群消息时发生，source
	 * 是ReceiveIMPacket
	 */
	public static final int IM_TEMP_CLUSTER_RECEIVED = 0x4006;

	/**
	 * <code>IM_TEMP_CLUSTER_SEND_FAIL</code>事件在发送临时群
	 * 消息失败时发生，source是ClusterCommandReplyPacket
	 */
	public static final int IM_TEMP_CLUSTER_SEND_FAIL = 0x4007;

	/**
	 * <code>IM_TEMP_CLUSTER_SEND_OK</code>事件在发送临时群
	 * 消息成功时发生，source是ClusterCommandReplyPacket
	 */
	public static final int IM_TEMP_CLUSTER_SEND_OK = 0x4008;

	/**
	 * <code>IM_TEMP_SESSION_RECEIVED</code>事件在收到一条临时会话消息时发生，
	 * source是ReceiveIMPacket
	 */
	public static final int IM_TEMP_SESSION_RECEIVED = 0x4009;

	/** 
	 * <code>IM_TEMP_SESSION_SEND_FAIL</code>在发送临时会话消息失败时
	 * 发生，其source是TempSessionOpReplyPacket
	 */
	public static final int IM_TEMP_SESSION_SEND_FAIL = 0x400A;

	/** 
	 * <code>IM_TEMP_SESSION_SEND_OK</code>在发送临时会话消息成功时
	 * 发生，其source是TempSessionOpReplyPacket
	 */
	public static final int IM_TEMP_SESSION_SEND_OK = 0x400B;

	/**
	 * <code>IM_UNKNOWN_CLUSTER_TYPE_RECEIVED</code>事件在收到一条未知类型群消息时发生，
	 * source是ReceiveIMPacket
	 */
	public static final int IM_UNKNOWN_CLUSTER_TYPE_RECEIVED = 0x400C;

	/**
	 * <code>IM_UNKNOWN_TYPE_RECEIVED</code>表示收到了一条目前我不能处理的消息，sourc是ReceiveIMPacket
	 */
	public static final int IM_UNKNOWN_TYPE_RECEIVED = 0x400D;
	
	/*
	 * 登录事件，0x5000 ~ 0x5FFF
	 */

	/**
	 * <code>QQ_LOGIN_ERROR</code>事件在登录错误时发生，Source是LoginReplyPacket
	 */
	public static final int LOGIN_FAIL = 0x5000;

	/**
	 * <code>LOGIN_GET_TOKEN_FAIL</code>事件在请求得到登录令牌失败时发生，源是
	 * GetLoginTokenReplyPacket
	 */
	public static final int LOGIN_GET_TOKEN_FAIL = 0x5001;

	/**
	 * <code>LOGIN_GET_TOKEN_OK</code>事件在请求得到登录令牌成功时发生，源是
	 * GetLoginTokenReplyPacket
	 */
	public static final int LOGIN_GET_TOKEN_OK = 0x5002;

	/**
	 * <code>LOGIN_NEED_VERIFY</code>事件在请求得到登录令牌需要输入验证码时发生，源是
	 * GetLoginTokenReplyPacket
	 */
	public static final int LOGIN_NEED_VERIFY = 0x5003;

	/**
	 * <code>LOGIN_OK</code>事件在登录成功是发生，Source是LoginReplyPacket
	 */
	public static final int LOGIN_OK = 0x5004;

	/**
	 * <code>LOGIN_REDIRECT_NULL</code>事件在重定向到一个0地址时发生，source是
	 * LoginReplyPacket
	 */
	public static final int LOGIN_REDIRECT_NULL = 0x5005;

	/**
	 * <code>LOGIN_UNKNOWN_ERROR</code>事件在登录时发生未知错误时发生，Source是LoginReplyPacket
	 */
	public static final int LOGIN_UNKNOWN_ERROR = 0x5006;
	
	/*
	 * 短信事件，0x6000 ~ 0x6FFF
	 */

	/** 
	 * <code>SMS_RECEIVED</code>事件发生在收到手机短信后，其source是ReceiveIMPacket
	 */
	public static final int SMS_RECEIVED = 0x6000;

	/**
	 * <code>SMS_SEND_OK</code>事件发生在短消息发送出去之后，其source是SendSMSReplyPacket
	 * 注意这个事件并不说明发送到底成功与否，我们需要检查SendSMSReplyPacket中的信息来判断
	 */
	public static final int SMS_SEND_OK = 0x6001;

	/*
	 * 系统事件，0x7000 ~ 0x7FFF
	 */
	
	/**
	 * <code>SYS_ADDED_BY_OTHERS</code>事件发生在有人将我加为好友时，source是SystemNotificationPacket
	 */
	public static final int SYS_ADDED_BY_OTHERS = 0x7000;

	/**
	 * <code>SYS_ADDED_BY_OTHERS_EX</code>事件发生在有人将我加为好友时，source是SystemNotificationPacket
	 */
	public static final int SYS_ADDED_BY_OTHERS_EX = 0x7001;

	/**
	 * <code>SYS_APPROVE_ADD</code>事件发生在我请求加一个人，
	 * 那个人同意我加的时候，source是SystemNotificationPacket
	 */
	public static final int SYS_APPROVE_ADD = 0x7002;

	/**
	 * <code>SYS_APPROVE_ADD_BIDI</code>事件发生在我请求加别人为好友时，那个人同意并且加我
	 * 为好友，source是SystemNotificationPacket
	 */
	public static final int SYS_APPROVE_ADD_BIDI = 0x7003;

	/**
	 * <code>SYS_BROADCAST_RECEIVED</code>事件在收到一条系统广播消息时发生，
	 * source是ReceiveIMPacket
	 */
	public static final int SYS_BROADCAST_RECEIVED = 0x7004;

	/**
	 * <code>SYS_KICKED</code>事件在收到你的QQ号在其他地方登陆导致你被系统踢出时发生，
	 * source是SystemNotificationPacket。系统通知和系统消息是不同的两种事件，系统通知是对你一个人发
	 * 出的（或者是和你相关的），系统消息是一种广播式的，每个人都会收到，要分清楚这两种事件。此外
	 * 系统通知的载体是SystemNotificationPacket，而系统消息是ReceiveIMPacket，ReceiveIMPacket的功
	 * 能和格式很多。这也是一个区别。注意其后的我被其他人加为好友，验证被通过被拒绝等等，都是系统
	 * 通知范畴
	 */
	public static final int SYS_KICKED = 0x7005;

	/**
	 * <code>SYS_REJECT_ADD</code>事件发生在我请求加一个人，那个人拒绝时，
	 * source是SystemNotificationPacket
	 */
	public static final int SYS_REJECT_ADD = 0x7006;

	/**
	 * <code>SYS_REQUEST_ADD</code>事件发生在有人请求加我为好友时，上面的是我没有设置验证
	 * 是发生的，这个事件是我如果设了验证时发生的，两者不会都发生。source是SystemNotificationPacket
	 */
	public static final int SYS_REQUEST_ADD = 0x7007;

	/**
	 * <code>SYS_REQUEST_ADD_EX</code>事件发生在有人请求加我为好友时，source是SystemNotificationPacket。
	 * 这是QQ_REQUEST_ADD_ME的扩展事件，在2005中使用
	 */
	public static final int SYS_REQUEST_ADD_EX = 0x7008;

	/**
	 * <code>SYS_TIMEOUT</code>事件在操作超时时发生，也就是请求包没有能收到回复，
	 * source是要发送的那个包，通知QQEvent的operation字段表示了操作的类型。要注意超时事件和
	 * Fail事件的不同，超时是指包没有收到任何确认，fail是指确认收到了，并且根据确认包的内容，
	 * 操作失败了
	 */
	public static final int SYS_TIMEOUT = 0x7009;

	/**
	 * <code>SYS_TIMEOUT_03</code>在03协议族操作超时时发生，也就是请求包没有能收到回复，
	 * source是要发送的那个包，通知QQEvent的operation字段表示了操作的类型。要注意超时事件和
	 * Fail事件的不同，超时是指包没有收到任何确认，fail是指确认收到了，并且根据确认包的内容，
	 * 操作失败了。由于不同的协议族中的命令可能值相同，所以目前只好为不同协议族的超时加上不同的事件
	 */
	public static final int SYS_TIMEOUT_03 = 0x700A;

	/**
	 * <code>SYS_TIMEOUT_05</code>在05协议族操作超时时发生，也就是请求包没有能收到回复，
	 * source是要发送的那个包，通知QQEvent的operation字段表示了操作的类型。要注意超时事件和
	 * Fail事件的不同，超时是指包没有收到任何确认，fail是指确认收到了，并且根据确认包的内容，
	 * 操作失败了。由于不同的协议族中的命令可能值相同，所以目前只好为不同协议族的超时加上不同的事件
	 */
	public static final int SYS_TIMEOUT_05 = 0x700B;

	/*
	 * 用户事件，0x8000 ~ 0x8FFF
	 */
	
	/**
	 * <code>USER_ADVANCED_SEARCH_END</code>事件在高级搜索结束时发生，源是
	 * AdvancedSearchUserReplyPacket
	 */
	public static final int USER_ADVANCED_SEARCH_END = 0x8000;

	/**
	 * <code>USER_ADVANCED_SEARCH_OK</code>事件在高级搜索成功时发生，源是
	 * AdvancedSearchUserReplyPacket
	 */
	public static final int USER_ADVANCED_SEARCH_OK = 0x8001;

	/**
	 * <code>USER_DELETE_SIGNATURE_FAIL</code>事件在删除个性签名失败时发生,
	 * source是SignatureOpReplyPacket
	 */
	public static final int USER_DELETE_SIGNATURE_FAIL = 0x8002;

	/**
	 * <code>USER_DELETE_SIGNATURE_OK</code>事件在删除个性签名成功时发生,
	 * source是SignatureOpReplyPacket
	 */
	public static final int USER_DELETE_SIGNATURE_OK = 0x8003;

	/** 
	 * <code>USER_GET_CUSTOM_HEAD_DATA_OK</code>在收到自定义头像数据
	 * 成功时发生，source是GetCustomHeadDataReplyPacket
	 */
	public static final int USER_GET_CUSTOM_HEAD_DATA_OK = 0x8004;

	/** 
	 * <code>USER_GET_CUSTOM_HEAD_INFO_OK</code>在收到自定义头像信息
	 * 成功时发生，source是GetCustomHeadInfoReplyPacket
	 */
	public static final int USER_GET_CUSTOM_HEAD_INFO_OK = 0x8005;

	/**
	 * <code>USER_GET_INFO_OK</code>事件发生在得到用户信息成功时，source是GetUserInfoReplyPacket
	 */
	public static final int USER_GET_INFO_OK = 0x8006;

	/**
	 * <code>USER_GET_PROPERTY_OK</code>事件在得到用户属性成功时发生，
	 * source是UserPropertyOpReplyPacket
	 */
	public static final int USER_GET_PROPERTY_OK = 0x8007;

	/**
	 * <code>USER_GET_SIGNATURE_FAIL</code>事件在得到个性签名失败时发生,
	 * source是SignatureOpReplyPacket
	 */
	public static final int USER_GET_SIGNATURE_FAIL = 0x8008;

	/**
	 * <code>USER_GET_SIGNATURE_OK</code>事件在得到个性签名成功时发生,
	 * source是SignatureOpReplyPacket
	 */
	public static final int USER_GET_SIGNATURE_OK = 0x8009;

	/**
	 * <code>USER_KEEP_ALIVE_FAIL</code>事件在连接失去时发生，这种情况一般时Keep Alive包没有反应
	 * 时触发的，source无用处
	 */
	public static final int USER_KEEP_ALIVE_FAIL = 0x800A;

	/**
	 * <code>USER_KEEP_ALIVE_OK</code>事件在Keep Alive包收到确认时发生，source是KeepAliveReplyPacket
	 */
	public static final int USER_KEEP_ALIVE_OK = 0x800B;

	/** 
	 * <code>USER_MEMBER_LOGIN_HINT_RECEIVED</code>在收到会员登录提示时发生，source是ReceiveIMPacket
	 */
	public static final int USER_MEMBER_LOGIN_HINT_RECEIVED = 0x800C;
	
	/**
	 * <code>USER_MODIFY_INFO_FAIL</code>事件在修改用户信息失败时发生，
	 * source是ModifyInfoPacket，注意不是ModifyInfoReplyPacket，因为
	 * Reply包毫无价值
	 */
	public static final int USER_MODIFY_INFO_FAIL = 0x800D;

	/**
	 * <code>USER_MODIFY_INFO_OK</code>事件在修改用户信息成功是发生，source是
	 * ModifyInfoPacket
	 */
	public static final int USER_MODIFY_INFO_OK = 0x800E;

	/**
	 * <code>USER_MODIFY_SIGNATURE_FAIL</code>事件在修改个性签名失败时发生,
	 * source是SignatureOpReplyPacket
	 */
	public static final int USER_MODIFY_SIGNATURE_FAIL = 0x800F;

	/**
	 * <code>USER_MODIFY_SIGNATURE_OK</code>事件在修改个性签名成功时发生,
	 * source是SignatureOpReplyPacket
	 */
	public static final int USER_MODIFY_SIGNATURE_OK = 0x8010;

	/** 
	 * <code>USER_PRIVACY_DATA_OP_OK</code>在隐私数据操作失败时发生，
	 * source是PrivacyDataOpReplyPacket。为了知道具体是什么操作，用户需要
	 * 判断source中的subCommand字段
	 */
	public static final int USER_PRIVACY_DATA_OP_FAIL = 0x8011;

	/** 
	 * <code>USER_PRIVACY_DATA_OP_OK</code>在隐私数据操作成功时发生，
	 * source是PrivacyDataOpReplyPacket。为了知道具体是什么操作，用户需要
	 * 判断source中的subCommand字段
	 */
	public static final int USER_PRIVACY_DATA_OP_OK = 0x8012;

	/**
	 * <code>USER_REQUEST_KEY_FAIL</code>事件在请求密钥失败之后发生，其source是RequestKeyPacket
	 */
	public static final int USER_REQUEST_KEY_FAIL = 0x8013;

	/**
	 * <code>USER_REQUEST_KEY_OK</code>事件在请求密钥成功之后发生，其source是RequestKeyReplyPacket
	 */
	public static final int USER_REQUEST_KEY_OK = 0x8014;

	/**
	 * <code>USER_SEARCH_OK</code>事件在搜索在线用户成功时发生，source是SearchUserReplyPacket
	 */
	public static final int USER_SEARCH_OK = 0x8015;

	/**
	 * <code>USER_STATUS_CHANGE_FAIL</code>事件发生你自己的状态改变失败时，source是ChangeStatusReplyPacket
	 */
	public static final int USER_STATUS_CHANGE_FAIL = 0x8016;

	/**
	 * <code>USER_STATUS_CHANGE_OK</code>事件发生你自己的状态改变成功时，source是ChangeStatusReplyPacket
	 */
	public static final int USER_STATUS_CHANGE_OK = 0x8017;

	/** 
	 * <code>USER_WEATHER_GET_FAIL</code>在得到天气预报失败时发生，source
	 * 是WeatherOpReplyPacket，但是这种情况下这个包无可用信息
	 */
	public static final int USER_WEATHER_GET_FAIL = 0x8018;

	/** 
	 * <code>USER_WEATHER_GET_OK</code>在得到天气预报成功时发生，source
	 * 是WeatherOpReplyPacket
	 */
	public static final int USER_WEATHER_GET_OK = 0x8019;

	public int operation;

	// QQ事件类型
	public int type;

	/**
	 * 缺省构造函数
	 */
	public QQEvent() {
		this(new Object());
	}

	/**
	 * @param source
	 */
	public QQEvent(Object source) {
		super(source);
	}
}
