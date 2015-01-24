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
package edu.tsinghua.lumaqq.ui.helper;

import edu.tsinghua.lumaqq.models.Cluster;
import edu.tsinghua.lumaqq.models.Organization;
import edu.tsinghua.lumaqq.models.User;
import edu.tsinghua.lumaqq.ui.BrowserShell;
import edu.tsinghua.lumaqq.ui.CheckUpdateShell;
import edu.tsinghua.lumaqq.ui.IPSeekerWindow;
import edu.tsinghua.lumaqq.ui.InfoManagerWindow;
import edu.tsinghua.lumaqq.ui.LastLoginTipWindow;
import edu.tsinghua.lumaqq.ui.MainShell;
import edu.tsinghua.lumaqq.ui.MemberEditShell;
import edu.tsinghua.lumaqq.ui.ReceiveIMWindow;
import edu.tsinghua.lumaqq.ui.ReceiveSystemMessageShell;
import edu.tsinghua.lumaqq.ui.SMSWindow;
import edu.tsinghua.lumaqq.ui.SendClusterIMWindow;
import edu.tsinghua.lumaqq.ui.SendIMTabWindow;
import edu.tsinghua.lumaqq.ui.SendIMWindow;
import edu.tsinghua.lumaqq.ui.SystemMessageListWindow;
import edu.tsinghua.lumaqq.ui.TempSessionIMWindow;
import edu.tsinghua.lumaqq.ui.WeatherWindow;
import edu.tsinghua.lumaqq.ui.config.cluster.ClusterInfoWindow;
import edu.tsinghua.lumaqq.ui.config.face.FaceWindow;
import edu.tsinghua.lumaqq.ui.config.sys.SystemOptionWindow;
import edu.tsinghua.lumaqq.ui.config.user.UserInfoWindow;
import edu.tsinghua.lumaqq.ui.wizard.WizardWindow;
import edu.tsinghua.lumaqq.ui.wizard.cluster.ClusterWizard;
import edu.tsinghua.lumaqq.ui.wizard.search.SearchWizard;

/**
 * Shell factory class, to encapsulate some creation code
 * 
 * @author luma
 */
public class ShellFactory {
	/**
	 * 创建临时会话消息窗口
	 * 
	 * @param main
	 * @param f
	 * @return
	 */
	public static TempSessionIMWindow createTempSessionIMWindow(MainShell main, User f) {
		TempSessionIMWindow win = new TempSessionIMWindow(main, f);
		main.getShellRegistry().addTempSessionIMWindow(f, win);
		return win;
	}
	
	/**
	 * 创建天气预报窗口
	 * 
	 * @param main
	 * @return
	 */
	public static WeatherWindow createWeatherWindow(MainShell main) {
		WeatherWindow window = new WeatherWindow(main);
		main.getShellRegistry().registerWeatherWindow(window);
		return window;
	}
	
	/**
	 * 创建短信窗口
	 * 
	 * @param main
	 * 		MainShell
	 * @return
	 * 		SMSWindow
	 */
	public static SMSWindow createSMSWindow(MainShell main) {
		SMSWindow window = new SMSWindow(main);
		main.getShellRegistry().registerSMSWindow(window);
		return window;
	}
	
	/**
	 * 创建短信窗口
	 * 
	 * @param main
	 * 		MainShell
	 * @param friend
	 * 		用户model
	 * @return
	 * 		SMSWindow
	 */
	public static SMSWindow createSMSWindow(MainShell main, User friend) {
		SMSWindow window = new SMSWindow(main, friend);
		main.getShellRegistry().addSMSWindow(friend, window);
		return window;
	}
	
	/**
	 * 创建短信窗口
	 * 
	 * @param main
	 * 		MainShell
	 * @param mobile
	 * 		手机号码
	 * @return
	 * 		SMSWindow
	 */
	public static SMSWindow createSMSWindow(MainShell main, String mobile) {
		SMSWindow window = new SMSWindow(main, mobile);
		main.getShellRegistry().addSMSWindow(mobile, window);
		return window;
	}
	
	/**
	 * 创建信息管理器窗口
	 * 
	 * @param main
	 * @return
	 */
	public static InfoManagerWindow createInfoManagerWindow(MainShell main) {
		return new InfoManagerWindow(main);
	}
	
    /**
	 * 创建一个查看消息窗口
	 * 
	 * @return
	 * 		查看消息窗口
	 */
	public static ReceiveIMWindow createReceiveIMWindow(MainShell main, User f) {
	    ReceiveIMWindow rms = new ReceiveIMWindow(main, f);
		main.getShellRegistry().addReceiveIMWindow(f, rms);		
		return rms;
	}
	
	/**
	 * 创建一个发送消息窗口
	 * 
	 * @return
	 * 		发送消息窗口
	 */
	public static SendIMWindow createSendIMWindow(MainShell main, User f) {
	    SendIMWindow sms = new SendIMWindow(main, f);
		main.getShellRegistry().addSendIMWindow(f, sms);		
		return sms;
	}
	
	/**
	 * 创建一个群消息发送窗口
	 * 
	 * @param main
	 * 		MainShell
	 * @return
	 * 		群消息发送窗口
	 */
	public static SendClusterIMWindow createSendClusterIMWindow(MainShell main, Cluster c) {
	    return new SendClusterIMWindow(main, c);
	}
	
    /**
     * 创建一个消息发送标签页窗口
     * 
     * @param main
     * 		MainShell
     * @return
     * 		SendIMTabWindow
     */
    public static SendIMTabWindow createSendIMTabWindow(MainShell main) {
    	SendIMTabWindow window = new SendIMTabWindow(main);
    	return window;
    }
    
    /**
     * 创建一个系统消息列表窗口
     * 
     * @param main
     * 		MainShell
     * @return
     * 		系统消息列表窗口
     */
    public static SystemMessageListWindow createSystemMessageListWindow(MainShell main) {
        return new SystemMessageListWindow(main);
    }
    
    /**
     * 创建一个搜索窗口
     * 
     * @param main
     * 		MainShell
     * @param startPage
     * 		起始页
     * @return
     * 		搜索窗口
     */
    public static WizardWindow createSearchWizard(MainShell main) {
	    SearchWizard wizard = new SearchWizard();
	    WizardWindow window = new WizardWindow(main, wizard);	    
	    wizard.init(main);
	    window.create();
        return window;
    }
    
    /**
     * 创建一个创建群向导
     * 
     * @param main
     * 		MainShell 
     * @return
     * 		群向导
     */
    public static WizardWindow createClusterWizard(MainShell main) {
	    ClusterWizard wizard = new ClusterWizard();
	    WizardWindow window = new WizardWindow(main, wizard);
	    wizard.init(main);
	    window.create();
        return window;
    }
    
    /**
     * 创建一个系统设置窗口
     * 
     * @param main
     * 		MainShell 
     * @return
     * 		系统设置窗口
     */
    public static SystemOptionWindow createSystemOptionWindow(MainShell main) {
        return new SystemOptionWindow(main);
    }
    
    /**
     * 创建一个检测更新窗口
     * 
     * @param main
     * 		MainShell 
     * @return
     * 		检测更新窗口
     */
    public static CheckUpdateShell createCheckUpdateShell(MainShell main) {
        return new CheckUpdateShell(main);
    }
    
    /**
     * 创建一个查看系统消息窗口
     * 
     * @param main
     * 		MainShell
     * @return
     * 		查看系统消息窗口
     */
    public static ReceiveSystemMessageShell createReceiveSystemMessageShell(MainShell main) {
        return new ReceiveSystemMessageShell(main);
    }
    
    /**
     * 创建一个IP查询窗口
     * 
     * @param main
     * 		MainShell
     * @return
     * 		IP查询窗口
     */
    public static IPSeekerWindow createIPSeekerWindow(MainShell main) {
        return new IPSeekerWindow(main);
    }
    
    /**
     * 创建一个临时群/组织信息编辑窗口
     * 
     * @param main
     * @param type
     * @param parentCluster
     * @param parentOrganization
     * @return
     */
    public static MemberEditShell createMemberEditShell(MainShell main, int type, Cluster parentCluster, Organization parentOrganization) {
    	MemberEditShell shell = new MemberEditShell(main, type);
    	shell.setParentCluster(parentCluster);
    	shell.setParentOrganization(parentOrganization);
    	return shell;
    }
    
    /**
     * 创建一个群资料窗口
     * 
     * @param main
     * 		MainShell
     * @return
     * 		群资料窗口
     */
    public static ClusterInfoWindow createClusterInfoWindow(MainShell main, Cluster c) {    
    	if(c.isCreator(main.getMyModel().qq))
    		return new ClusterInfoWindow(main, c, ClusterInfoWindow.EDITABLE_CREATOR);
    	else if(c.isSuperMember(main.getMyModel().qq))
    		return new ClusterInfoWindow(main, c, ClusterInfoWindow.EDITABLE_ADMIN);
    	else
    		return new ClusterInfoWindow(main, c, ClusterInfoWindow.READ_ONLY);
    }
    
    /**
     * 创建一个浏览器窗口
     * 
     * @param main
     * 		MainShell
     * @return
     * 		浏览器窗口
     */
    public static BrowserShell createBrowserShell(MainShell main) {
        return new BrowserShell(main);
    }
    
    /**
     * 创建一个好友资料窗口
     * 
     * @param main
     * 		MainShell
     * @param model
     * 		好友model
     * @param style
     * 		窗口样式
     * @return
     * 		好友资料窗口
     */
    public static UserInfoWindow createUserInfoWindow(MainShell main, User model, int style) {
        return new UserInfoWindow(main, model, style);
    }
    
    /**
     * 创建一个表情管理窗口
     * 
     * @param main
     * 		MainShell
     * @return
     * 		FaceWindow对象
     */
    public static FaceWindow createFaceWindow(MainShell main) {
        return new FaceWindow(main);
    }
    
    /**
     * 创建一个上次登录信息提示窗口
     * 
     * @param main
     * @return
     */
    public static LastLoginTipWindow createLastLoginTipWindow(MainShell main) {
    	return new LastLoginTipWindow(main);
    }
}
