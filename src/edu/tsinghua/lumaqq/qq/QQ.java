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


/**
 * <pre>
 * 定义一些QQ用到的常量，常量的命名方式经过调整，统一为
 * QQ_[类别]_[名称]
 * 
 * 比如表示长度的常量，为QQ_LENGTH_XXXXX
 * 表示最大值的常量，为QQ_MAX_XXXX
 * </pre>
 * 
 * @author luma
 */
public interface QQ {    	
    /** 空字符串 */
    public static final String EMPTY_STRING = "";
    
    // 协议族标识
    /** 基本协议族 */
    public static final int QQ_PROTOCOL_FAMILY_BASIC = 0x1;
	/**
	 * 05开头的协议族，目前发现的用途
	 * 1. 得到群内自定义表情
	 */
    public static final int QQ_PROTOCOL_FAMILY_05 = 0x2;
	/** 
	 * 03开头的协议族，目前发现的用途
	 * 1. 得到自定义头像
	 */
    public static final int QQ_PROTOCOL_FAMILY_03 = 0x4;
    
    /** 所有协议族 */
    public static final int QQ_PROTOCOL_ALL = 0xFFFFFFFF;
    
    /** 基本协议族输入包的包头长度 */
    public static final int QQ_LENGTH_BASIC_FAMILY_IN_HEADER = 7;
    /** 基本协议族输出包的包头长度 */
    public static final int QQ_LENGTH_BASIC_FAMILY_OUT_HEADER = 11;
    /** 基本协议族包尾长度 */
    public static final int QQ_LENGTH_BASIC_FAMILY_TAIL = 1;
    /** FTP协议族包头长度 */
    public static final int QQ_LENGTH_FTP_FAMILY_HEADER = 46;
    /** 05协议族包头长度 */
    public static final int QQ_LENGTH_05_FAMILY_HEADER = 13;
    /** 05协议族包尾长度 */
    public static final int QQ_LENGTH_05_FAMILY_TAIL = 1;
    /** 网络硬盘协议族输入包包头长度 */
    public static final int QQ_LENGTH_DISK_FAMILY_IN_HEADER = 82;
    /** 网络硬盘协议族输出包包头长度 */
    public static final int QQ_LENGTH_DISK_FAMILY_OUT_HEADER = 154;
    
	// QQ包类型定义
	/** QQ基本协议族包头 */
	public static final byte QQ_HEADER_BASIC_FAMILY = 0x02;
	/** QQ P2P协议族 */
	public static final byte QQ_HEADER_P2P_FAMILY = 0x00;
	/** 03协议族包头 */
	public static final byte QQ_HEADER_03_FAMILY = 0x03;
    /** 04开头的协议族，未知含义，文件中转包有用到过 */
	public static final byte QQ_HEADER_04_FAMILY = 0x04;
	/** 05协议族包头 */
	public static final byte QQ_HEADER_05_FAMILY = 0x05;
	/** QQ基本协议族包尾 */
	public static final byte QQ_TAIL_BASIC_FAMILY = 0x03;
	/** 05系列协议族包尾 */
	public static final byte QQ_TAIL_05_FAMILY = 0x03;
    
    // 代理类型，和协议没有关系
    /** 代理类型 - 无代理 */
    public static final int QQ_PROXY_NONE = 0;
    /** 代理类型 - Http代理 */
    public static final int QQ_PROXY_HTTP = 1;
    /** 代理类型 - Socks5代理 */
    public static final int QQ_PROXY_SOCKS5 = 2;
    
	/** 不需要确认的包的发送次数，这个值应该是随便的，由于QQ Logout包发了4次，所以我选4 */ 
    public static final int QQ_SEND_TIME_NOACK_PACKET = 4;

    /** QQ登录包, 第一段35字节固定内容 */
    public static final byte[] QQ_LOGIN_FIX_1_35 = new byte[] {
    	0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x41, (byte)0xD1, 0x18, (byte)0xAC, 0x14, 0x78, 0x58, (byte)0xF1, (byte)0xD0, (byte)0x81, 0x4D, 0x7D, 0x7D, 0x7B, (byte)0xD9, 0x1F
    };
    
    /** QQ登录包, 第二段16字节固定内容 */
    public static final byte[] QQ_LOGIN_FIX_2_16 = new byte[] {
    	0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x01, 0x01, 0x00, 0x00, 0x00
    };
    
    /** QQ登录包, 第三段16字节固定内容 */
    public final byte[] QQ_LOGIN_FIX_3_16 = new byte[] {
    	(byte)0xFD, (byte)0xAC, 0x10, (byte)0xDC, 0x5C, (byte)0xE9, 0x74, 0x42, (byte)0x9F, 0x45, 0x7B, 0x73, 0x0E, (byte)0xB7, (byte)0x97, 0x65
    };
    
    /** QQ登录包, 第四段321字节固定内容（近似可以认为是固定） */
    public static final byte[] QQ_LOGIN_FIX_4_321 = new byte[] {
    	0x40, 0x01, 0x01, 0x58, (byte)0x83, (byte)0xD0, 0x00, 0x10, (byte)0x9D, 
    	0x14, 0x64, 0x0A, 0x2E, (byte)0xE2, 0x11, (byte)0xF7, (byte)0x90, (byte)0xF0, (byte)0xB5, 
    	0x5F, 0x16, (byte)0xFB, 0x41, 0x5D, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 
    	0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 
    	0x00, 0x00, 0x00, 0x02, 0x76, 0x3C, (byte)0xEE, 0x4A, 0x00, 0x10, (byte)0x86, (byte)0x81, (byte)0xAD, 0x1F, 
    	(byte)0xC8, (byte)0xC9, (byte)0xCC, (byte)0xCF, (byte)0xCA, (byte)0x9F, (byte)0xFF, (byte)0x88, (byte)0xC0, 0x5C, (byte)0x88, (byte)0xD5, 0x00, 0x00, 
    	0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 
    	0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 
    	0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 
    	0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 
    	0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 
    	0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 
    	0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 
    	0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 
    	0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 
    	0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 
    	0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 
    	0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 
    	0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 
    	0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 
    	0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 
    	0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 
    	0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 
    	0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00
    };
    
	/** 包最大大小 */
	public static final int QQ_MAX_PACKET_SIZE = 65535;
	/**
	 * 消息最大长度，QQ是最大700字节
	 */
	public static final int QQ_MAX_SEND_IM = 700;
	/** 密钥长度 */
	public static final int QQ_LENGTH_KEY = 16;
	/** 登陆信息长度 */
	public static final int QQ_LENGTH_LOGIN_DATA = 449;
    
	/** QQ UDP缺省端口 */
	public static final int QQ_PORT_UDP = 8000;
	/** QQ TCP缺省端口 */
	public static final int QQ_PORT_TCP = 80;
	/** 使用HTTP代理时连接QQ服务器的端口 */
	public static final int QQ_PORT_HTTP = 443;
	   
    /** QQ缺省编码方式 */
	public static final String QQ_CHARSET_DEFAULT = "GBK";
	/** 消息编码，好像可以自己胡乱定义 */
	public static final char QQ_CHARSET_GB = 0x8602;
	public static final char QQ_CHARSET_EN = 0x0000;	
	public static final char QQ_CHARSET_BIG5 = 0x8603;
	
	/** 单位: ms */
	public static final long QQ_TIMEOUT_SEND = 5000;
	/** 最大重发次数 */
	public static final int QQ_MAX_RESEND = 5;
	/** Keep Alive包发送间隔，单位: ms */
	public static final long QQ_INTERVAL_KEEP_ALIVE = 100000;
	
	// 和虚拟摄像头有关系
	/** 显示虚拟摄像头 */
	public static final int QQ_CAM_SHOW_FAKE = 1;
	/** 隐藏虚拟摄像头 */
	public static final int QQ_CAM_DONT_SHOW_FAKE = 0;

	/** QQ分组的名称最大字节长度，注意一个汉字是两个字节 */
	public static final int QQ_MAX_GROUP_NAME = 16;
	/** QQ昵称的最长长度 */
	public static final int QQ_MAX_NAME_LENGTH = 250;
	/** QQ缺省表情个数 */
	public static final int QQ_COUNT_DEFAULT_FACE = 96;
	/** 得到用户信息的回复包字段个数 */
	public static final int QQ_COUNT_GET_USER_INFO_FIELD = 37;
	/** 修改用户信息的请求包字段个数，比实际的多1，最开始的QQ号不包括 */
	public static final int QQ_COUNT_MODIFY_USER_INFO_FIELD = 35;
	/** 用户备注信息的字段个数 */
	public static final int QQ_COUNT_REMARK_FIELD = 7;

	/** 客户端版本号标志 - QQ2005 */
	public static final char QQ_CLIENT_VERSION_0E1B = 0x0E1B;
	/** 客户端版本号 - QQ2006 */
	public static final char QQ_CLIENT_VERSION_0F5F = 0x0F5F;
	/**
	 * 服务器端版本号 (不一定)
	 * 不一定真的是表示服务器端版本号，似乎和发出的包不同，这个有其他的含义，
	 * 感觉像是包的类型标志
	 */
	public static final char QQ_SERVER_VERSION_0100 = 0x0100;
	
	// 用于网络硬盘协议族，标识版本
	public static final char QQ_DISK_SERVER_VERSION_0101 = 0x0101;
	public static final char QQ_DISK_CLIENT_VERSION_0207 = 0x0207;
	public static final char QQ_DISK_SERVER_VERSION = QQ_DISK_SERVER_VERSION_0101;
	public static final char QQ_DISK_CLIENT_VERSION = QQ_DISK_CLIENT_VERSION_0207;
	
	/** 中转服务器版本号 */
	public static final char QQ_AGENT_SERVER_VERSION_04D1 = 0x04D1;
	
	/** 程序缺省使用的客户端版本号 */
	public static final char QQ_CLIENT_VERSION = QQ_CLIENT_VERSION_0F5F;
		
	/** 正常登陆 */
	public static final byte QQ_LOGIN_MODE_NORMAL = 0x0A;
	/** 隐身登陆 */
	public static final byte QQ_LOGIN_MODE_HIDDEN = 0x28;
	/** 性别-男 */
	public static final byte QQ_GENDER_GG = 0x0;
	/** 性别-女 */
	public static final byte QQ_GENDER_MM = 0x1;
	/** 性别-未知 */
	public static final byte QQ_GENDER_UNKNOWN = (byte)0xFF;
	
	/** 标志-QQ会员 */
	public static final byte QQ_FRIEND_FLAG_QQ_MEMBER = 0x01;
	/** 标志-手机 */
	public static final byte QQ_FRIEND_FLAG_MOBILE = 0x10;
	/** 标志-手机绑定 */
	public static final byte QQ_FRIEND_FLAG_BIND_MOBILE = 0x20;
	
	/** 在线状态-在线 */
	public static final byte QQ_STATUS_ONLINE = 0x0A;
	/** 在线状态-离线 */
	public static final byte QQ_STATUS_OFFLINE = 0x14;
	/** 在线状态-离开 */
	public static final byte QQ_STATUS_AWAY = 0x1E;
	/** 在线状态-隐身 */
	public static final byte QQ_STATUS_HIDDEN = 0x28;
	
	// 用户标志，比如QQFriend类，好友状态改变包都包含这样的标志
	/** 有摄像头 */
	public static final int QQ_FLAG_CAM = 0x80;
	/** 绑定了手机 */
	public static final int QQ_FLAG_BIND = 0x40;
	/** 移动QQ用户 */
	public static final int QQ_FLAG_MOBILE = 0x20;
	/** 会员 */
	public static final int QQ_FLAG_MEMBER = 0x02;
	/** TM登录 */
	public static final int QQ_FLAG_TM = 0x40000;
	
	// 用户属性，在UserProperty中，相关命令0x0065
	/** 有个性签名 */
	public static final int QQ_FLAG_HAS_SIGNATURE = 0x40000000;
	/** 有自定义头像 */
	public static final int QQ_FLAG_HAS_CUSTOM_HEAD = 0x100000;

	/** 好友列表从第一个好友开始 */
	public static final char QQ_POSITION_FRIEND_LIST_START = 0x0000;
	/** 好友列表已经全部得到 */
	public static final char QQ_POSITION_FRIEND_LIST_END = 0xFFFF;
	/** 在线好友列表从第一个好友开始 */
	public static final byte QQ_POSITION_ONLINE_LIST_START = 0x00;
	/** 在线好友列表已经全部得到 */
	public static final byte QQ_POSITION_ONLINE_LIST_END = (byte)0xFF;
	/** 用户属性列表从第一个好友开始 */
	public static final char QQ_POSITION_USER_PROPERTY_START = 0x0000;
	/** 用户属性列表结束 */
	public static final char QQ_POSITION_USER_PROPERTY_END = 0xFFFF;
	/** 不对得到的好友列表排序 */
	public static final byte QQ_FRIEND_LIST_UNSORTED = 0;
	/** 对得到的好友列表排序 */
	public static final byte QQ_FRIEND_LIST_SORTED = 1;
	
	// 回复常量	
	/** 通用常量，操作成功 */
	public static final byte QQ_REPLY_OK					= 0x00;
	/** 对方已经是我的好友 */
	public static final byte QQ_REPLY_ADD_FRIEND_ALREADY = (byte)0x99;
	/** 请求登录令牌成功 */
	public static final byte QQ_REPLY_REQUEST_LOGIN_TOKEN_OK = 0x00;
	/** 登录信息-登录失败 */
	public static final byte QQ_REPLY_LOGIN_FAIL = 0x09;
	/** 登录信息-重定向 */
	public static final byte QQ_REPLY_LOGIN_REDIRECT = 0x0A;
	/** 改变在线状态成功 */
	public static final byte QQ_REPLY_CHANGE_STATUS_OK = 0x30;
	/** 发送认证消息成功 */
	public static final byte QQ_REPLY_ADD_FRIEND_AUTH_OK = 0x30;
	/** 高级搜索结束，没有更多数据 */
	public static final byte QQ_REPLY_ADVANCED_SEARCH_END = 1;
	/** 申请中转服务器，重定向 */
	public static final char QQ_REPLY_REQUEST_AGENT_REDIRECT = 0x0001;
	/** 申请中转服务器成功 */
	public static final char QQ_REPLY_REQUEST_AGENT_OK = 0x0000;
	/** 要发送的图片太大 */
	public static final char QQ_REPLY_REQUEST_AGENT_TOO_LONG = 0x0003;
	/** 登录需要输入验证码 */
	public static final char QQ_REPLY_LOGIN_NEED_VERIFY = 0x01;
	
	/** 命令常量 - 登出 */
	public static final char QQ_CMD_LOGOUT = 0x0001;
	/** 命令常量 - 保持在线状态 */
	public static final char QQ_CMD_KEEP_ALIVE = 0x0002;
	/** 命令常量 - 修改自己的信息 */
	public static final char QQ_CMD_MODIFY_INFO = 0x0004;
	/** 命令常量 - 查找用户 */
	public static final char QQ_CMD_SEARCH_USER = 0x0005;
	/** 命令常量 - 得到好友信息 */
	public static final char QQ_CMD_GET_USER_INFO = 0x0006;
	/** 命令常量 - 删除一个好友 */
	public static final char QQ_CMD_DELETE_FRIEND = 0x000A;
	/** 命令常量 - 发送验证信息 */
	public static final char QQ_CMD_ADD_FRIEND_AUTH = 0x000B;
	/** 命令常量 - 改变自己的在线状态 */
	public static final char QQ_CMD_CHANGE_STATUS = 0x000D;
	/** 命令常量 - 确认收到了系统消息 */
	public static final char QQ_CMD_ACK_SYS_MSG = 0x0012;
	/** 命令常量 - 发送消息 */
	public static final char QQ_CMD_SEND_IM = 0x0016;
	/** 命令常量 - 接收消息 */
	public static final char QQ_CMD_RECV_IM = 0x0017;
	/** 命令常量 - 把自己从对方好友名单中删除 */
	public static final char QQ_CMD_REMOVE_SELF = 0x001C;
	/** 请求一些操作需要的密钥，比如文件中转，视频也有可能 */
	public static final char QQ_CMD_REQUEST_KEY = 0x001D;
	/** 命令常量 - 登陆 */
	public static final char QQ_CMD_LOGIN = 0x0022;
	/** 命令常量 - 得到好友列表 */
	public static final char QQ_CMD_GET_FRIEND_LIST = 0x0026;
	/** 命令常量 - 得到在线好友列表 */
	public static final char QQ_CMD_GET_ONLINE_OP = 0x0027;
	/** 命令常量 - 发送短消息 */
	public static final char QQ_CMD_SEND_SMS = 0x002D;
	/** 命令常量 - 群相关命令 */
	public static final char QQ_CMD_CLUSTER_CMD = 0x0030;
	/** 命令常量 - 测试连接 */
	public static final char QQ_CMD_TEST = 0x0031;
	/** 命令常量 - 分组数组操作 */
	public static final char QQ_CMD_GROUP_DATA_OP = 0x003C;
	/** 命令常量 - 上传分组中的好友QQ号列表 */
	public static final char QQ_CMD_UPLOAD_GROUP_FRIEND = 0x003D;
	/** 命令常量 - 好友相关数据操作 */
	public static final char QQ_CMD_FRIEND_DATA_OP = 0x003E;
	/** 命令常量 - 下载分组中的好友QQ号列表 */
	public static final char QQ_CMD_DOWNLOAD_GROUP_FRIEND = 0x0058;
    /** 命令常量 - 好友等级信息相关操作 */
    public static final char QQ_CMD_FRIEND_LEVEL_OP = 0x005C; 
    /** 命令常量 - 隐私数据操作 */
    public static final char QQ_CMD_PRIVACY_DATA_OP = 0x005E;
	/** 命令常量 - 群数据操作命令 */
	public static final char QQ_CMD_CLUSTER_DATA_OP = 0x005F;
	/** 命令常量 - 好友高级查找 */
	public static final char QQ_CMD_ADVANCED_SEARCH = 0x0061;
	/** 命令常量 - 用户属性操作 */
	public static final char QQ_CMD_USER_PROPERTY_OP = 0x0065;
	/** 命令常量 - 临时会话操作 */
	public static final char QQ_CMD_TEMP_SESSION_OP = 0x0066;
	/** 命令常量 - 个性签名的操作 */
	public static final char QQ_CMD_SIGNATURE_OP = 0x0067;
	/** 命令常量 - 接收到系统消息 */
	public static final char QQ_CMD_RECV_MSG_SYS = 0x0080;
	/** 命令常量 - 好友改变状态 */
	public static final char QQ_CMD_RECV_MSG_FRIEND_CHANGE_STATUS = 0x0081;
	/** 命令常量 - 天气操作 */
	public static final char QQ_CMD_WEATHER_OP = 0x00A6;
	/** 命令常量 - 添加好友 */
	public static final char QQ_CMD_ADD_FRIEND_EX = 0x00A7;
	/** 命令常量 - 发送验证消息 */
	public static final char QQ_CMD_AUTHORIZE = 0x00A8;
	/** 命令常量 - 请求验证信息 */
	public static final char QQ_CMD_AUTH_INFO_OP = 0x00AE;
	/** 命令常量 - 认证问题操作 */
	public static final char QQ_CMD_AUTH_QUESTION_OP = 0x00B7;
	/** 命令常量 - 请求登陆令牌 */
	public static final char QQ_CMD_GET_LOGIN_TOKEN = 0x00BA;
	/** 命令常量 - 未知命令，调试用途 */
	public static final char QQ_CMD_UNKNOWN = 0xFFFF;
	
	// 0x00B7的子命令
	/** 得到问题 */
	public static final byte QQ_SUB_CMD_GET_QUESTION = 0x03;
	/** 回答问题 */
	public static final byte QQ_SUB_CMD_ANSWER_QUESTION = 0x04;
	
	// 0x00A8的子命令
	/** 普通验证，即验证码验证 */
	public static final byte QQ_SUB_CMD_NORMAL_AUTHORIZE = 0x02;
	/** 双重验证，即验证码和问题双重验证 */
	public static final byte QQ_SUB_CMD_DOUBLE_AUTHORIZE = 0x10;
	
	// 0x00BA的子命令
	/** 登录认证 */
	public static final byte QQ_SUB_CMD_GET_LOGIN_AUTH = 0x01;
	/** 刷新验证码图片 */
	public static final byte QQ_SUB_CMD_SUBMIT_VERIFY_CODE = 0x02;
	
	// 0x005E的子命令
	/** 只能通过号码搜到我 */
	public static final byte QQ_SUB_CMD_SEARCH_ME_BY_QQ_ONLY = 0x03;
	/** 共享地理位置 */
	public static final byte QQ_SUB_CMD_SHARE_GEOGRAPHY = 0x04;
	
	// 是否设置一个选项，用在如0x005E这样的命令中，其他地方如果类似也可使用
	/** 设置 */
	public static final byte QQ_VALUE_SET = 0x01;
	/** 取消设置 */
	public static final byte QQ_VALUE_UNSET = 0x00;
	
	// 用户类型，用在高级查找中
	/** 普通用户 */
	public static final byte QQ_USER_TYPE_NORMAL = 0x01;
	
	// 0x005C的子命令
	/** 得到好友等级信息 */
	public static final byte QQ_SUB_CMD_GET_FRIEND_LEVEL = 0x02;
	
	// 0x005F的子命令	
	/** 得到群在线成员 */
	public static final byte QQ_SUB_CMD_GET_CLUSTER_ONLINE_MEMBER = 0x01; 
	
	/** 自定义头像上传服务器 */
	public static final String QQ_SERVER_UPLOAD_CUSTOM_HEAD = "cface.qq.com";
	/** 自定义头像下载服务器 */
	public static final String QQ_SERVER_DOWNLOAD_CUSTOM_HEAD = "cface_tms.qq.com";
	/** 网络硬盘服务器 */
	public static final String[] QQ_SERVER_DISK = new String[] {
		"219.133.38.39",
		"219.133.38.40",
		"219.133.38.99",
		"219.133.38.100",
		"219.133.38.101",
		"219.133.38.102",
		"219.133.38.176",
		"219.133.38.202",
		"219.133.51.105",
		"219.133.51.106"
	};
	/** 网络硬盘服务器端口 */
	public static final int QQ_SERVER_DISK_PORT = 9910;
	
	/** 命令常量 - 请求中转 */
	public static final char QQ_05_CMD_REQUEST_AGENT = 0x0021;
	/** 命令常量 - 请求得到自定义表情 */
	public static final char QQ_05_CMD_REQUEST_FACE = 0x0022;
	/** 命令常量 - 开始传送 */
	public static final char QQ_05_CMD_TRANSFER = 0x0023;
	/** 命令常量 - 请求开始传送 */
	public static final char QQ_05_CMD_REQUEST_BEGIN = 0x0026;
	
	/*
	 * 03协议族
	 */
	/** 请求得到好友自定义头像数据 */
	public static final char QQ_03_CMD_GET_CUSTOM_HEAD_DATA = 0x0002;
	/** 请求得到好友自定义头像信息 */
	public static final char QQ_03_CMD_GET_CUSTOM_HEAD_INFO = 0x0004;
	
	/**
	 * 初始群自定义表情中转服务器，从这些初始的服务器开始请求，直到请求
	 * 到一个愿意提供中转的服务器为止
	 */
	public static final String[] QQ_SERVER_GROUP_FILE = new String[] {
	        "219.133.40.128" // GroupFile.tencent.com
	};
	
	// 用于0x001D
	/** 请求密钥类型 - 未知 */
	public static final byte QQ_SUB_CMD_REQUEST_UNKNOWN03_KEY = 0x3;
	/** 请求密钥类型 - 文件中转密钥 */
	public static final byte QQ_SUB_CMD_REQUEST_FILE_AGENT_KEY = 0x4;
	/** 请求密钥类型 - 未知 */
	public static final byte QQ_SUB_CMD_REQUEST_UNKNOWN06_KEY = 0x6;
	/** 请求密钥类型 - 未知 */
	public static final byte QQ_SUB_CMD_REQUEST_UNKNOWN07_KEY = 0x7;
	/** 请求密钥类型 - 未知 */
	public static final byte QQ_SUB_CMD_REQUEST_UNKNOWN08_KEY = 0x8;
	
	// 用户0x00AE
	/** 请求验证信息 */
	public static final byte QQ_SUB_CMD_GET_AUTH_INFO = 0x01;
	/** 提交验证信息 */
	public static final byte QQ_SUB_CMD_SUBMIT_AUTH_INFO = 0x02;
	
	/** 群操作命令 - 创建群 */
	public static final byte QQ_CLUSTER_CMD_CREATE_CLUSTER		= 0x01;
	/** 群操作命令 - 修改群成员 */
	public static final byte QQ_CLUSTER_CMD_MODIFY_MEMBER		= 0x02;
	/** 群操作命令 - 修改群资料 */
	public static final byte QQ_CLUSTER_CMD_MODIFY_CLUSTER_INFO	= 0x03;
	/** 群操作命令 - 得到群资料 */
	public static final byte QQ_CLUSTER_CMD_GET_CLUSTER_INFO	= 0x04;
	/** 群操作命令 - 激活群 */
	public static final byte QQ_CLUSTER_CMD_ACTIVATE_CLUSTER	= 0x05;
	/** 群操作命令 - 搜索群 */
	public static final byte QQ_CLUSTER_CMD_SEARCH_CLUSTER		= 0x06;
	/** 群操作命令 - 加入群 */
	public static final byte QQ_CLUSTER_CMD_JOIN_CLUSTER		= 0x07;
	/** 群操作命令 - 加入群的验证消息 */
	public static final byte QQ_CLUSTER_CMD_JOIN_CLUSTER_AUTH	= 0x08;
	/** 群操作命令 - 退出群 */
	public static final byte QQ_CLUSTER_CMD_EXIT_CLUSTER		= 0x09;
	/** 
	 * 群操作命令 - 发送群消息 
	 * 
	 * @deprecated 2004以后使用了QQ_CLUSTER_CMD_SEND_IM_EX
	 */
	@Deprecated
	public static final byte QQ_CLUSTER_CMD_SEND_IM				= 0x0A;
	/** 群操作命令 - 得到在线成员 */
	public static final byte QQ_CLUSTER_CMD_GET_ONLINE_MEMBER	= 0x0B;
	/** 群操作命令 - 得到成员资料 */
	public static final byte QQ_CLUSTER_CMD_GET_MEMBER_INFO		= 0x0C;
	/** 群操作命令 - 修改群名片 */
	public static final byte QQ_CLUSTER_CMD_MODIFY_CARD 		= 0x0E;
	/** 群操作命令 - 批量得到成员群名片中的真实姓名 */
	public static final byte QQ_CLUSTER_CMD_GET_CARD_BATCH		= 0x0F;
	/** 群操作命令 - 得到某个成员的群名片 */
	public static final byte QQ_CLUSTER_CMD_GET_CARD 			= 0x10;
	/** 群操作命令 - 提交组织架构到服务器 */
	public static final byte QQ_CLUSTER_CMD_COMMIT_ORGANIZATION = 0x11;
	/** 群操作命令 - 从服务器获取组织架构 */
	public static final byte QQ_CLUSTER_CMD_UPDATE_ORGANIZATION	= 0x12;
	/** 群操作命令 - 提交成员分组情况到服务器 */
	public static final byte QQ_CLUSTER_CMD_COMMIT_MEMBER_ORGANIZATION = 0x13;
	/** 群操作命令 - 得到各种version id */
	public static final byte QQ_CLUSTER_CMD_GET_VERSION_ID		= 0x19;
	/** 群操作命令 - 扩展格式的群消息 */
	public static final byte QQ_CLUSTER_CMD_SEND_IM_EX 			= 0x1A;
	/** 群操作命令 - 设置成员角色 */
	public static final byte QQ_CLUSTER_CMD_SET_ROLE = 0x1B;
	/** 群操作命令 - 转让自己的角色给他人 */
	public static final byte QQ_CLUSTER_CMD_TRANSFER_ROLE = 0x1C;
	/** 解散群，如果自己是群的创建者，则使用这个命令 */
	public static final byte QQ_CLUSTER_CMD_DISMISS_CLUSTER = 0x1D;
	/** 群操作命令 - 创建临时群 */
	public static final byte QQ_CLUSTER_CMD_CREATE_TEMP = 0x30;
	/** 群操作命令 - 修改临时群成员列表 */
	public static final byte QQ_CLUSTER_CMD_MODIFY_TEMP_MEMBER = 0x31;
	/** 群操作命令 - 退出临时群 */
	public static final byte QQ_CLUSTER_CMD_EXIT_TEMP = 0x32;
	/** 群操作命令 - 得到临时群资料 */
	public static final byte QQ_CLUSTER_CMD_GET_TEMP_INFO = 0x33;
	/** 群操作命令 - 修改临时群资料 */
	public static final byte QQ_CLUSTER_CMD_MODIFY_TEMP_INFO = 0x34;
	/** 群操作命令 - 发送临时群消息 */
	public static final byte QQ_CLUSTER_CMD_SEND_TEMP_IM = 0x35;
	/** 群操作命令 - 子群操作 */
	public static final byte QQ_CLUSTER_CMD_SUB_CLUSTER_OP = 0x36;
	/** 群操作命令 - 激活临时群 */
	public static final byte QQ_CLUSTER_CMD_ACTIVATE_TEMP = 0x37;
	
	/** 群操作命令回复 - 不存在这个群 */
	public static final byte QQ_REPLY_NO_SUCH_CLUSTER		= 0x02;
	/** 群操作命令回复 - 群已经被删除 */
	public static final byte QQ_REPLY_TEMP_CLUSTER_REMOVED 	= 0x03;
	/** 群操作命令回复 - 你已经不是临时群的成员 */
	public static final byte QQ_REPLY_NOT_TEMP_CLUSTER_MEMBER = 0x04;
	/** 群操作命令回复 - 你已经不是固定群的成员 */
	public static final byte QQ_REPLY_NOT_CLUSTER_MEMBER  	  = 0x0A;
	
	// 临时会话操作回复码
	/** 临时会话消息发送成功，但是对方可能不在线，无法及时回复 */
	public static final byte QQ_REPLY_MAYBE_OFFLINE = 0x02;
	
	// 短信回复码，用来表示单条短信的状态
	/** 短信发送成功 */
	public static final byte QQ_REPLY_SMS_OK = 0x00;
	/** 队列中，准备发送 */
	public static final byte QQ_REPLY_SMS_QUEUED = 0x01;
	/** 发送失败 */
	public static final byte QQ_REPLY_SMS_FAIL = 0x04;
	
	// 请求验证信息回复码
	/** 通过url得到验证码图片进行验证, 用于QQ_SUB_CMD_GET_AUTH_INFO */
	public static final byte QQ_REPLY_GET_PUZZLE_FROM_URL = 0x01;
	/** 错误的验证码，用于QQ_SUB_CMD_SUBMIT_AUTH_INFO */
	public static final byte QQ_REPLY_INVALID_AUTH = 0x01;
	
	// 认证问题命令回复码
	/** 错误的问题答案 */
	public static final byte QQ_REPLY_WRONG_ANSWER = 0x01;
	
	/** 群类型常量 - 固定群 */
	public static final byte QQ_CLUSTER_TYPE_PERMANENT = 0x01;
	/** 临时群类型常量 - 多人对话 */
	public static final byte QQ_CLUSTER_TYPE_DIALOG = 0x01;
	/** 临时群类型常量 - 讨论组 */
	public static final byte QQ_CLUSTER_TYPE_SUBJECT = 0x02;
	
	/** 群成员角色操作 - 设置管理员 */
	public static final byte QQ_ROLE_OP_SET_ADMIN = 0x01;
	/** 群成员角色操作 - 取消管理员 */
	public static final byte QQ_ROLE_OP_UNSET_ADMIN = 0x00;
	
	/** 群成员角色标志位 - 管理员 */
	public static final int QQ_ROLE_ADMIN = 0x01;
	/** 群成员角色标志位 - 股东 */
	public static final int QQ_ROLE_STOCKHOLDER = 0x02;
	/** 成员角色 - 群名片可让群管理员修改  */
	public static final int QQ_ROLE_MANAGED = 0x08;
	
	/** 群操作子命令 - 添加成员，用在修改成员列表命令中 */
	public static final byte QQ_CLUSTER_SUB_CMD_ADD_MEMBER = 0x01;
	/** 群操作子命令 - 删除成员，用在修改成员列表命令中 */
	public static final byte QQ_CLUSTER_SUB_CMD_REMOVE_MEMBER = 0x02;
	
	/** 群操作子命令 - 得到群内的讨论组列表 */
	public static final byte QQ_CLUSTER_SUB_CMD_GET_SUBJECT_LIST = 0x02;
	/** 群操作子命令 - 得到多人对话列表 */
	public static final byte QQ_CLUSTER_SUB_CMD_GET_DIALOG_LIST = 0x01;
	
	/** 群认证消息类型 - 请求加入群 */
	public static final byte QQ_CLUSTER_AUTH_REQUEST   = 0x01;
	/** 群认证消息类型 - 同意加入群 */
	public static final byte QQ_CLUSTER_AUTH_APPROVE   = 0x02;
	/** 群认证消息类型 - 拒绝加入群 */
	public static final byte QQ_CLUSTER_AUTH_REJECT    = 0x03;
	/** 加入群的回复码 - 加入成功 */
	public static final byte QQ_CLUSTER_JOIN_OK			= 0x01;	
	/** 加入群的回复码 - 对方需要认证 */
	public static final byte QQ_CLUSTER_JOIN_NEED_AUTH	= 0x02;
	/** 加入群的回复码 - 群禁止加入 */
	public static final byte QQ_CLUSTER_JOIN_DENIED = 0x03;
	/** 群的搜索方式 - 根据群号搜索 */
	public static final byte QQ_CLUSTER_SEARCH_BY_ID 	= 0x01;
	/** 群的搜索方式 - 搜索示范群 */
	public static final byte QQ_CLUSTER_SEARCH_DEMO		= 0x02;
	
	// 操作码，用在设置角色时
	/** 取消管理员 */
	public static final byte QQ_CLUSTER_OP_UNSET_ADMIN = 0x00;
	/** 设置管理员 */
	public static final byte QQ_CLUSTER_OP_SET_ADMIN = 0x01;
	  
	// 群消息的content type
	/** 消息中不包含自定义表情 */
	public static final char QQ_CONTENT_TYPE_DEFAULT = 0x0001;
	/** 消息中包含自定义表情 */
	public static final char QQ_CONTENT_TYPE_RICH = 0x0002;
	
	/** 消息回复类型 - 正常回复 */
	public static final byte QQ_IM_NORMAL_REPLY = 0x01;
	/** 消息回复类型 - 自动回复 */
	public static final byte QQ_IM_AUTO_REPLY = 0x02;
	
	// 消息来源，主要在ReceiveIMPacket中使用，和协议关系不大
	/** 来自好友 */
	public static final int QQ_IM_FROM_USER = 0;
	/** 来自系统 */
	public static final int QQ_IM_FROM_SYS = 1;
	/** 来自群 */
	public static final int QQ_IM_FROM_CLUSTER = 2;
	/** 来自短消息 */
	public static final int QQ_IM_FROM_SMS = 3;
	/** 来自临时会话 */
	public static final int QQ_IM_FROM_TEMP_SESSION = 4;
	
	// 子命令常量，用于命令0x0027
	/** 得到在线好友 */
	public static final byte QQ_SUB_CMD_GET_ONLINE_FRIEND = 0x2;
	/** 得到系统服务 */
	public static final byte QQ_SUB_CMD_GET_ONLINE_SERVICE = 0x3;
	
	// 子命令常量，服务于命令0x003C
	/** 服务器端数据操作 - 上传组名 */
	public static final byte QQ_SUB_CMD_UPLOAD_GROUP_NAME = 0x2;
	/** 服务器端数据操作 - 下载组名 */
	public static final byte QQ_SUB_CMD_DOWNLOAD_GROUP_NAME = 0x1;
	
	// 子命令常量，用于命令0x0066
	/** 临时会话操作 - 发送临时会话消息 */
	public static final byte QQ_SUB_CMD_SEND_TEMP_SESSION_IM = 0x01;
	
	// 子命令常量，服务于命令0x003E
	/** 服务器端数据操作 - 批量下载好友备注 */
	public static final byte QQ_SUB_CMD_BATCH_DOWNLOAD_FRIEND_REMARK = 0x0;
	/** 服务器端数据操作 - 上传好友备注 */
	public static final byte QQ_SUB_CMD_UPLOAD_FRIEND_REMARK = 0x1;
	/** 服务器端数据操作 - 添加好友到列表中 */
	public static final byte QQ_SUB_CMD_REMOVE_FRIEND_FROM_LIST = 0x2;
	/** 服务器端数据操作 - 下载好友备注 */
	public static final byte QQ_SUB_CMD_DOWNLOAD_FRIEND_REMARK = 0x3;
	
	// 子命令常量，用于子命令0x0067
	/** 修改个性签名 */
	public static final byte QQ_SUB_CMD_MODIFY_SIGNATURE = 0x01;
	/** 删除个性签名 */
	public static final byte QQ_SUB_CMD_DELETE_SIGNATURE = 0x02;
	/** 得到个性签名 */
	public static final byte QQ_SUB_CMD_GET_SIGNATURE = 0x03;
	
	// 子命令，用于0x0065
	/** 得到用户属性 */
	public static final byte QQ_SUB_CMD_GET_USER_PROPERTY = 0x01;
	
	// 子命令，用于0x00A6
	/** 得到天气数据 */
	public static final byte QQ_SUB_CMD_GET_WEATHER = 0x01;
	
	// 这两个常量用在下载好友分组时
	/** 号码类型 - 号码代表一个用户 */
	public static final byte QQ_ID_IS_FRIEND = 0x1;
	/** 号码类型 - 号码是一个群 */
	public static final byte QQ_ID_IS_CLUSTER = 0x4;
	
	// 消息类型，就是ReceiveIMHeader中的类型，对于有些类型，我们做为通知来处理
	// 而不是显示在消息窗口中，比如请求加入，验证之类的消息
	/** 来自好友的消息 */
	public static final char QQ_RECV_IM_FRIEND = 0x0009;
	/** 来自陌生人的消息 */
	public static final char QQ_RECV_IM_STRANGER = 0x000A;
	/** 手机短消息 - 普通绑定用户 */
	public static final char QQ_RECV_IM_BIND_USER = 0x000B;
	/** 手机短消息 - 普通手机 */
	public static final char QQ_RECV_IM_MOBILE = 0x000C;
	/** 会员登录提示，这个消息基本没内容，就是用来提醒你是会员，可以显示一个窗口来告诉你上次登录时间和ip */
	public static final char QQ_RECV_IM_MEMBER_LOGIN_HINT = 0x0012;
	/** 手机短消息 - 移动QQ用户 */
	public static final char QQ_RECV_IM_MOBILE_QQ = 0x0013;
	/** 手机短消息 - 移动QQ用户(使用手机号描述) */
	public static final char QQ_RECV_IM_MOBILE_QQ_2 = 0x0014;
	/** 好友属性改变通知 */
	public static final char QQ_RECV_IM_PROPERTY_CHANGE = 0x001E;
	/** 临时会话消息 */
	public static final char QQ_RECV_IM_TEMP_SESSION = 0x001F;
	/** 未知类型的群消息，在2003时是普通群消息 */
	public static final char QQ_RECV_IM_UNKNOWN_CLUSTER = 0x0020;
	/** 通知我被加入到一个群，这个群先前已经建立，我是后来被加的 */
	public static final char QQ_RECV_IM_ADDED_TO_CLUSTER = 0x0021;
	/** 我被踢出一个群 */
	public static final char QQ_RECV_IM_DELETED_FROM_CLUSTER = 0x0022;
	/** 有人请求加入群 */
	public static final char QQ_RECV_IM_REQUEST_JOIN_CLUSTER = 0x0023;
	/** 同意对方加入群 */	
	public static final char QQ_RECV_IM_APPROVE_JOIN_CLUSTER = 0x0024;
	/** 拒绝对方加入群 */
	public static final char QQ_RECV_IM_REJECT_JOIN_CLUSTER = 0x0025;
	/** 通知我被加入到一个群，我是在群被创建的时候就被加的 */
	public static final char QQ_RECV_IM_CREATE_CLUSTER = 0x0026;
	/** 临时群消息 */
	public static final char QQ_RECV_IM_TEMP_CLUSTER = 0x002A;
	/** 固定群消息 */
	public static final char QQ_RECV_IM_CLUSTER = 0x002B;
	/** 群通知 */
	public static final char QQ_RECV_IM_CLUSTER_NOTIFICATION = 0x002C;
	/** 收到的系统消息 */ 
	public static final char QQ_RECV_IM_SYS_MESSAGE = 0x0030;
	/** 收到个性签名改变通知 */
	public static final char QQ_RECV_IM_SIGNATURE_CHANGE = 0x0041;
	/** 收到自定义头像变化通知 */
	public static final char QQ_RECV_IM_CUSTOM_HEAD_CHANGE = 0x0049;
	/** 收到扩展格式的普通消息 */
	public static final char QQ_RECV_IM_FRIEND_EX = 0x0084;
	
	// 移动QQ手机短信的发送者描述类型
	/** 用QQ号描述发送者 */
	public static final byte QQ_MOBILE_QQ_BY_QQ = 0x00;
	/** 用手机号码描述发送者 */
	public static final byte QQ_MOBILE_QQ_BY_MOBILE = 0x01;
	
	// 系统消息类型
	/** 同一个QQ号在其他地方登录，我被踢出 */
	public static final byte QQ_RECV_IM_KICK_OUT = 0x01;
	
	// 消息类型，这个类型比上面的类型又再低一级，他们基本从属于QQ_RECV_IM_FRIEND
	// 所以他们是normalIMHeader中的类型
	/** 普通文件消息 */
	public static final char QQ_IM_TYPE_TEXT = 0x000B;
	/** 一个TCP连接请求 */
	public static final char QQ_IM_TYPE_TCP_REQUEST = 0x0001;
	/** 接收TCP连接请求 */
	public static final char QQ_IM_TYPE_ACCEPT_TCP_REQUEST = 0x0003;
	/** 拒绝TCP连接请求 */
	public static final char QQ_IM_TYPE_REJECT_TCP_REQUEST = 0x0005;
	/** UDP连接请求 */
	public static final char QQ_IM_TYPE_UDP_REQUEST = 0x0035;
	/** 接受UDP连接请求 */
	public static final char QQ_IM_TYPE_ACCEPT_UDP_REQUEST = 0x0037;
	/** 拒绝UDP连接请求 */
	public static final char QQ_IM_TYPE_REJECT_UDP_REQUEST = 0x0039;
	/** 通知文件传输端口 */
	public static final char QQ_IM_TYPE_NOTIFY_IP = 0x003B;
	/** 请求对方主动连接 */
	public static final char QQ_IM_TYPE_ARE_YOU_BEHIND_FIREWALL = 0x003F;
	/** 未知含意 */
	public static final char QQ_IM_TYPE_ARE_YOU_BEHIND_PROXY = 0x0041;
	/** 未知含意，0x0041的回复 */
	public static final char QQ_IM_TYPE_YES_I_AM_BEHIND_PROXY = 0x0042;
	/** 通知文件中转服务器信息 */
	public static final char QQ_IM_TYPE_NOTIFY_FILE_AGENT_INFO = 0x004B;
	/** 取消TCP或者UDP连接请求 */
	public static final char QQ_IM_TYPE_REQUEST_CANCELED = 0x0049;
	
	// 以下常量用于QQ短信功能
	/** 短消息发送者最大名称字节长度 */
	public static final int QQ_MAX_SMS_SENDER_NAME = 13;
	/** 接受者手机号最大长度 */
	public static final int QQ_MAX_SMS_MOBILE_LENGTH = 18;
	/** 短信发送时，发送者名称和短信内容的字符数之和的最大值 */
	public static final int QQ_MAX_SMS_LENGTH = 58;
	/** 发送模式 - 免提短信 */
	public static final byte QQ_SMS_MODE_HAND_FREE = 0x20;
	/** 发送模式 - 普通 */
	public static final byte QQ_SMS_MODE_NORMAL = 0x00;
	/** 短消息内容 - 普通短消息 */
	public static final byte QQ_SMS_CONTENT_NORMAL = 0x00;
	/** 短消息内容 - 言语传情 */
	public static final byte QQ_SMS_CONTENT_LOVE_WORD = 0x01;
	/** 短消息内容 - 精美图片 */
	public static final byte QQ_SMS_CONTENT_PICTURE = 0x02;
	/** 短消息内容 - 悦耳铃声 */
	public static final byte QQ_SMS_CONTENT_RING = 0x03;
	
	// 以下常量用于消息中的表情，对于自定义表情的表示格式参考NormalIM.java的注释
	/** 系统自带表情前导字节 */
	public static final byte QQ_TAG_DEFAULT_FACE = 0x14;
	/** 自定义表情前导字节 */
	public static final byte QQ_TAG_CUSTOM_FACE = 0x15;
	/** 新自定义表情，普通格式 */
	public static final byte QQ_FORMAT_TAG_NEW_CUSTOM_FACE = 0x33;
	/** 已经出现过的自定义表情 */
	public static final byte QQ_FORMAT_TAG_EXISTING_CUSTOM_FACE = 0x34;
	/** 新自定义表情，存储在服务器端  */
	public static final byte QQ_FORMAT_TAG_NEW_SERVER_SIDE_CUSTOM_FACE = 0x36;
	/** 已经出现过的服务器端自定义表情 */
	public static final byte QQ_FORMAT_TAG_EXISTING_SERVER_SIDE_CUSTOM_SIDE = 0x37;
	/** 未知自定义表情格式描述1，未知含义 */
	public static final byte QQ_FORMAT_TAG_UNKNOWN_1 = 0x38;
	/** 未知自定义表情格式描述2，未知含义 */
	public static final byte QQ_FORMAT_TAG_UNKNOWN_2 = 0x39;
	
	// 认证类型，加一个人为好友时是否需要验证等等
	/** 不需认证 */
	public static final byte QQ_AUTH_NO = 0;
	/** 需要认证 */
	public static final byte QQ_AUTH_NEED = 1;
	/** 对方拒绝加好友 */
	public static final byte QQ_AUTH_REJECT = 2;
	/** 需要回答问题 */
	public static final byte QQ_AUTH_QUESTION = 3;
	
	// 认证类型，加入一个群是否需要验证等等
	/** 不需认证 */
	public static final byte QQ_AUTH_CLUSTER_NO = 1;
	/** 需要认证 */
	public static final byte QQ_AUTH_CLUSTER_NEED = 2;
	/** 群拒绝添加成员 */
	public static final byte QQ_AUTH_CLUSTER_REJECT = 3;
	
	// 这三个常量用在添加好友认证的包中，表示你是请求，或者你拒绝还是同意别人的请求
	/** 通过认证 */
	public static final byte QQ_MY_AUTH_APPROVE = 0x30;
	/** 拒绝认证  */
	public static final byte QQ_MY_AUTH_REJECT = 0x31;
	/** 请求认证 */
	public static final byte QQ_MY_AUTH_REQUEST = 0x32;
	
	// 联系方法的可见类型
	/** 完全公开 */
	public static final int QQ_CONTACT_OPEN = 0;
	/** 仅好友可见 */
	public static final int QQ_CONTACT_ONLY_FRIENDS = 1;
	/** 完全保密 */
	public static final int QQ_CONTACT_CLOSE = 2;
	
	// 系统通知的类型
	/** 自己被别人加为好友 */
	public static final int QQ_SYS_BEING_ADDED = 1;
	/** 
	 * 对方请求加你为好友
	 * 当对方不使用0x00A8命令发送认证消息，才会收到此系统通知
	 */
	public static final int QQ_SYS_ADD_FRIEND_REQUEST = 2;
	/** 同意对方加自己为好友 */
	public static final int QQ_SYS_ADD_FRIEND_APPROVED = 3;
	/** 拒绝对方加自己为好友 */
	public static final int QQ_SYS_ADD_FRIEND_REJECTED = 4;
	/** 广告 */
	public static final int QQ_SYS_ADVERTISEMENT = 6;
	/** 更新提示 */
	public static final int QQ_SYS_UPDATE_HINT = 9;
	/** 对方把你加为了好友 */
	public static final int QQ_SYS_BEING_ADDED_EX = 40;
	/** 
	 * 对方请求加你为好友
	 * 当对方使用0x00A8命令发送认证消息，才会收到此系统通知
	 */
	public static final int QQ_SYS_ADD_FRIEND_REQUEST_EX = 41;
	/** 同意对方加自己为好友，同时加对方为好友 */
	public static final int QQ_SYS_ADD_FRIEND_APPROVED_AND_ADD = 43;
	
	// QQ_SYS_ADD_FRIEND_REQUEST_EX消息中的最后一个字节标志，是否允许对方加自己为好友
	/** 允许对方也加自己为好友 */
	public static final byte QQ_FLAG_ALLOW_ADD_REVERSE = 0x01;
	/** 不允许对方加自己为好友 */
	public static final byte QQ_FLAG_NOT_ALLOW_ADD_REVERSE = 0x02;
	
	// 这是搜索用户时指定的搜索类类型，比如是查看全部在线用户，还是自定义查找
	/** 看谁在线上 */
	public static final byte QQ_SEARCH_ALL = 0x31;
	/** 自定义搜索 - 精确查找，也就是按照QQ号查找 */
	public static final byte QQ_SEARCH_CUSTOM_ACCURATE = 0x33;
	/** 自定义搜索 - 模糊查找，也就是按照昵称或者email查找 */
	public static final byte QQ_SEARCH_CUSTOM_FUZZY = 0x32;
	
	// 用于自定义头像
	/** 最大自定义头像分片大小 */
	public static final int QQ_MAX_CUSTOM_HEAD_FRAGMENT_SIZE = 800;
	
    // 传输类型
    /** 传输文件 */
    public static final byte QQ_TRANSFER_FILE = 0x65;
    /** 传输自定义表情 */
    public static final byte QQ_TRANSFER_FACE = 0x6B;
    
    // 请求传送文件消息中的一个标志字节，传输类型之后那个，意思不明，姑且这样
    /** UDP，可能不是这意思 */
    public static final byte QQ_TRANSFER_FILE_UDP = 0;
    /** 直接UDP，可能不是这意思 */
    public static final byte QQ_TRANSFER_FILE_DIRECT_UDP = 1;
    /** TCP，可能不是这意思 */
    public static final byte QQ_TRANSFER_FILE_TCP = 2;
    /** 直接TCP，可能不是这意思 */
    public static final byte QQ_TRANSFER_FILE_DIRECT_TCP = 3;
	
	/** 最大的做MD5的长度，当传送一个文件时，如果这个文件很大，则只对文件的前面一部分做MD5 */
	public static final int QQ_MAX_FILE_MD5_LENGTH = 10002432;
}
