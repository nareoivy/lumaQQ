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
package edu.tsinghua.lumaqq;

import java.io.File;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import edu.tsinghua.lumaqq.ecore.LoginOption;
import edu.tsinghua.lumaqq.ecore.global.GlobalSetting;
import edu.tsinghua.lumaqq.ecore.global.Robot;
import edu.tsinghua.lumaqq.eutil.GlobalUtil;
import edu.tsinghua.lumaqq.qq.QQ;
import edu.tsinghua.lumaqq.qq.QQClient;
import edu.tsinghua.lumaqq.qq.beans.QQUser;
import edu.tsinghua.lumaqq.qq.net.PortGateFactory;
import edu.tsinghua.lumaqq.qq.robot.IRobot;
import edu.tsinghua.lumaqq.resource.Colors;
import edu.tsinghua.lumaqq.ui.MainShell;
import edu.tsinghua.lumaqq.ui.dialogs.LoginDialog;
import edu.tsinghua.lumaqq.ui.helper.OptionHelper;


/**
 * LumaQQ的GUI主程序，主要负责构造界面
 *
 * @author luma
 */


public class LumaQQ {
	/** 安装目录 */
	public static String INSTALL_DIR;
	/** 自定义表情目录 */
	public static String CUSTOM_FACE_DIR;
	
	/** 聊天机器人配置文件 */
	public static String ROBOTS;
	/** IP数据库文件 */
	public static String IP_FILE;
	/** 群分类文件 */
	public static String CLUSTER_CATEGORY_FILE;
	/** 全局配置信息文件 */
	public static String GLOBAL_SETTING;
	
	
	// 用户文件路径
	/** 用户登录历史信息文件 */
	public static String LOGIN_HISTORY;
	/** 用户组信息文件 */
	public static String GROUPS;
	/** 自动回复和快捷回复设置文件 */
	public static String REPLIES;
	/** 代理服务器列表文件 */
	public static String PROXIES;
	/** 系统参数设置文件 */
	public static String SYSTEM_OPTIONS;
	/** 好友备注信息文件 */
	public static String REMARKS;
	/** 手机好友信息文件 */
	public static String MOBILES;
	/** 自定义表情配置文件 */
	public static String CUSTOM_FACES;
	/** 聊天记录文件目录 */
	public static String RECORD_DIRECTORY;
	/** 用户信息文件 */
	public static String SELF;
	
	// 声音文件路径
	/** 普通消息声音文件 */
	public static String MSG_SOUND;
	/** 系统消息声音 */
	public static String SYS_MSG_SOUND;
	
	// 登陆服务器数组，需要从xml文件中读取
    /** UDP服务器的列表 */
    public static String[] udpServers;
    /** TCP服务器列表 */
    public static String[] tcpServers;
    
    // 机器人列表
    public static List<Robot> robots;
    
    // 全局设置
    public static String language;
    
	// 是否windows
    public static boolean IS_WIN32;
    public static boolean IS_GTK;
    public static boolean IS_CARBON;
    public static boolean DOUBLE_BUFFER;

	/**
	 * LumaQQ启动主程序
	 * @param args
	 */
	public static void main(String[] args) {
		// 获得平台类型
		detectPlatform();
		// 初始化系统文件路径，读取全局设置
		initSysFilePath(args);
		readGlobalSettingFile();
		Shell shell = new Shell(new Display());
		// 初始化颜色
		Colors.init();
		// 服务器列表读取成功，开始登陆
		LoginDialog login = new LoginDialog(shell, true);
		System.out.println("111111111");
		if(login.open()) {
			System.out.println("22222");
			// 创建QQ用户对象
			QQUser me = new QQUser(login.getQQ(), login.getMd5Password());			
			me.setUdp(false);
			if(login.isLoginHidden()) {
				me.setLoginMode(QQ.QQ_LOGIN_MODE_HIDDEN);
				me.setStatus(QQ.QQ_STATUS_HIDDEN);
			} else {
				me.setLoginMode(QQ.QQ_LOGIN_MODE_NORMAL);
				me.setStatus(QQ.QQ_STATUS_ONLINE);
			}
			// 创建QQ客户端对象
			QQClient client = new QQClient();
			client.setConnectionPoolFactory(new PortGateFactory());
			client.setUser(me);
			// 初始化用户文件路径
			initUserFilePath(me);
			// 传递给主界面
			MainShell main = new MainShell();
			main.setClient(client);
			// 检查是否使用登录框的网络设置
			if(login.isUseNetworkSetting()) 
				syncLoginOption(login.getLogins().getNetwork(), main.getOptionHelper());
			// 打开主界面
		    main.open();
		} 
		System.out.println("sssss");
	} 

	/**
	 * 同步登录设置
	 * 
	 * @param network
	 * @param optionHelper
	 */
	public static void syncLoginOption(LoginOption lo, OptionHelper optionHelper) {
		optionHelper.setProxyType(lo.getProxyType());
		optionHelper.setProxyServer(lo.getProxyServer());
		optionHelper.setProxyPort(lo.getProxyPort());
		optionHelper.setProxyUsername(lo.getProxyUsername());
		optionHelper.setProxyPassword(lo.getProxyPassword());
		optionHelper.setUseTcp(lo.isUseTcp());
		optionHelper.setServer(lo.getServer());
		optionHelper.setTcpPort(lo.getTcpPort());
		optionHelper.setAutoSelect(lo.isAutoSelect());
	}

	/**
	 * 获得平台类型
	 */
	private static void detectPlatform() {
	    // 如果不是Mac，底层也不是gtk，则做双缓冲
		String platform = SWT.getPlatform();
		IS_WIN32 = "win32".equals(platform);
		IS_GTK = "gtk".equals(platform);
		IS_CARBON = "carbon".equals(platform);
		DOUBLE_BUFFER = !IS_CARBON;
	}

	/**
	 * 初始化用户文件路径，对于每个用户来说，这些值是变化的。在改变用户的时候将被调用
	 * 
	 * @param me
	 * 		QQ用户对象
	 */
	public static void initUserFilePath(QQUser me) {
		GROUPS = INSTALL_DIR + "/" + me.getQQ() + "/groups.xml";
		REPLIES = INSTALL_DIR + "/" + me.getQQ() + "/replies.xml";
		PROXIES = INSTALL_DIR + "/" + me.getQQ() + "/proxies.xml";
		SYSTEM_OPTIONS = INSTALL_DIR + "/" + me.getQQ() + "/sysopts.xml";
		REMARKS = INSTALL_DIR + "/" + me.getQQ() + "/remarks.xml";
		MOBILES = INSTALL_DIR + "/" + me.getQQ() + "/mobiles.xml";		
		CUSTOM_FACE_DIR = INSTALL_DIR + "/" + me.getQQ() + "/custom_face/";
		CUSTOM_FACES = INSTALL_DIR + "/" + me.getQQ() + "/faces.xml";
		RECORD_DIRECTORY = INSTALL_DIR + "/" + me.getQQ() + "/record";
		SELF = INSTALL_DIR + "/" + me.getQQ() + "/me.xml";
	}

	/**
	 * 初始化系统文件的路径，对于每个用户来说，这些值是不变的，所以这个方法只调用一次
	 * @param args
	 */
	private static void initSysFilePath(String[] args) {
		if(args.length == 0)
			INSTALL_DIR = ".";
		else
			INSTALL_DIR = args[0];
		LOGIN_HISTORY = INSTALL_DIR + "/logins.xml";
		ROBOTS = INSTALL_DIR + "/xml/robots.xml";
		IP_FILE = INSTALL_DIR + "/QQWry.dat";
		CLUSTER_CATEGORY_FILE = INSTALL_DIR + "/cluster_category.db";
		GLOBAL_SETTING = INSTALL_DIR + "/xml/global.xml";
		MSG_SOUND = INSTALL_DIR + "/sound/msg.au";
		SYS_MSG_SOUND = INSTALL_DIR + "/sound/system.wav";
	}
	
	/**
	 * 读取全局配置信息文件
	 */
	@SuppressWarnings("unchecked")
	private static void readGlobalSettingFile() {
		// 检查文件
		File file = new File(LumaQQ.GLOBAL_SETTING);		
		// 载入文件
		GlobalSetting global = GlobalUtil.load(file);
		// 如果global为null，则创建一个缺省的全局配置文件
		if(global == null || global.getServers().getTCPServer().size() + global.getServers().getUDPServer().size() < 1) {
			global = GlobalUtil.createDefault();
			GlobalUtil.save(file, global);
		}
		// 读取设置
		language = global.getLanguage().getName();			
		// 得到UDP服务器列表
		int i = 0;
		List<String> list = global.getServers().getUDPServer();
		udpServers = new String[list.size()];
		for(String server : list)
			udpServers[i++] = server;
		// 得到TCP服务器列表
		i = 0;
		list = global.getServers().getTCPServer();
		tcpServers = new String[list.size()];
		for(String server: list)
			tcpServers[i++] = server;
		// 得到机器人
		robots = global.getRobots().getRobot();
	}
	
	/**
	 * @return
	 * 		true表示有robot
	 */
	public static boolean hasRobots() {
		if(robots == null)
			return false;
		else
			return !robots.isEmpty();
	}
	
	/**
	 * 得到机器人借口
	 * 
	 * @param index
	 * @return
	 */
	public static IRobot getRobot(int index) {
		if(robots == null || index < 0 || index >= robots.size())
			return null;
		else {
			Robot robot = robots.get(index);
			try {
				Class klass = Class.forName(robot.getClass_());
				return (IRobot)klass.newInstance();
			} catch (Exception e) {
				return null;
			}
		}
	}
	
	/**
	 * @return Returns the robots.
	 */
	public static List<Robot> getRobots() {
		return robots;
	}
}
