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

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

import edu.tsinghua.lumaqq.models.Cluster;
import edu.tsinghua.lumaqq.models.Model;
import edu.tsinghua.lumaqq.models.User;
import edu.tsinghua.lumaqq.ui.CheckUpdateShell;
import edu.tsinghua.lumaqq.ui.InfoManagerWindow;
import edu.tsinghua.lumaqq.ui.MemberEditShell;
import edu.tsinghua.lumaqq.ui.ReceiveIMWindow;
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

/**
 * 保存窗口的打开状态，保存窗口和好友的关系，避免打开多个相同窗口
 * 
 * @author luma
 */
public class ShellRegistry {
    // 存放当前已经打开的查看消息窗口
    private Map<Integer, ReceiveIMWindow> rimwMap;
    // 存放当前已经打开的发送消息窗口
    private Map<Integer, SendIMWindow> simwMap;
    // 存放当前已经打开的用户资料查看窗口
    private Map<Integer, UserInfoWindow> uiwMap;
    // 存放当前已经打开的群资料查看窗口
    private Map<Integer, ClusterInfoWindow> ciwMap;
    // 存放当前已经打开的群消息发送窗口
    private Map<Integer, SendClusterIMWindow> scimwMap;
    // 存放临时群/组织信息修改窗口，创建的窗口不保存
    private Map<Model, MemberEditShell> mesMap;
    // 临时会话消息窗口
    private Map<Integer, TempSessionIMWindow> tsimMap;
    
    // 不为null表示系统消息列表窗口已经打开
    private SystemMessageListWindow systemMessageListShell;
    // 不为null表示搜索窗口已经打开
    private WizardWindow searchWizard;
    // 不为null表示创建群窗口已经打开
    private WizardWindow clusterWizard;
    // 不为null表示系统设置窗口已经打开
    private SystemOptionWindow systemOptionWindow;
    // 不为null表示检测更新窗口已经打开
    private CheckUpdateShell checkUpdateShell;
    // 不为null表示表情管理窗口已经打开
    private FaceWindow faceWindow;
    // 不为null表示消息发送标签页窗口已经打开
    private SendIMTabWindow imTabWindow;
    // 短信窗口
    private SMSWindow smsWindow;
    // 一对一模式短信窗口
    private Map<Object, SMSWindow> singleSMSWindows;
    // 信息管理器
    private InfoManagerWindow infoWin;
    // 天气预报窗口
    private WeatherWindow weatherWindow;
    
    public ShellRegistry() {
        rimwMap = new Hashtable<Integer, ReceiveIMWindow>();
        simwMap = new Hashtable<Integer, SendIMWindow>();
        uiwMap = new Hashtable<Integer, UserInfoWindow>();
        ciwMap = new Hashtable<Integer, ClusterInfoWindow>();
        scimwMap = new Hashtable<Integer, SendClusterIMWindow>();
        mesMap = new Hashtable<Model, MemberEditShell>();
        tsimMap = new Hashtable<Integer, TempSessionIMWindow>();
        singleSMSWindows = new Hashtable<Object, SMSWindow>();
    }
    
    public void addTempSessionIMWindow(User f, TempSessionIMWindow win) {
    	tsimMap.put(f.qq, win);
    }
    
    public TempSessionIMWindow getTempSessionIMWindow(User f) {
    	return tsimMap.get(f.qq);
    }
    
    public TempSessionIMWindow removeTempSessionIMWindow(User f) {
    	return tsimMap.remove(f.qq);
    }
    
    public boolean hasTempSessionIMWindow(User f) {
    	return tsimMap.containsKey(f.qq);
    }
    
    public boolean hasSMSWindow(Object key) {
    	return singleSMSWindows.containsKey(key);
    }
    
    public void addSMSWindow(Object key, SMSWindow window) {
    	singleSMSWindows.put(key, window);
    }
    
    public SMSWindow getSMSWindow(Object key) {
    	return singleSMSWindows.get(key);
    }
    
    public SMSWindow removeSMSWindow(Object key) {
    	return singleSMSWindows.remove(key);
    }
    
    public void registerWeatherWindow(WeatherWindow win) {
    	this.weatherWindow = win;
    }
    
    public void deregisterWeatherWindow() {
    	this.weatherWindow = null;
    }
    
    public boolean isWeatherWindowOpen() {
    	return weatherWindow != null;
    }
    
    public WeatherWindow getWeatherWindow() {
    	return weatherWindow;
    }
    
    public void registerInfoManagerWindow(InfoManagerWindow imw) {
    	infoWin = imw;
    }
    
    public void deregisterInfoManagerWindow() {
    	infoWin = null;
    }
    
    public boolean isInfoManagerWindowOpen() {
    	return infoWin != null;
    }
    
    public InfoManagerWindow getInfoManagerWindow() {
    	return infoWin;
    }
    
    public void registerSMSWindow(SMSWindow sw) {
    	smsWindow = sw;
    }
    
    public void deregisterSMSWindow() {
    	smsWindow = null;
    }
    
    public boolean hasSMSWindow() {
    	return smsWindow != null;
    }
    
    public SMSWindow getSMSWindow() {
    	return smsWindow;
    }
    
    /**
     * 注册一个临时群/组织信息修改窗口
     * 
     * @param m
     * @param mes
     */
    public void registerMemberEditShell(Model m, MemberEditShell mes) {
    	mesMap.put(m, mes);
    }
    
    /**
     * 检查是否存在临时群/组织信息修改窗口
     * 
     * @param m
     * @return
     */
    public boolean hasMemberEditShell(Model m) {
    	return mesMap.containsKey(m);
    }
    
    /**
     * 注销临时群/组织信息修改窗口
     * 
     * @param m
     */
    public void deregisterMemberEditShell(Model m) {
    	mesMap.remove(m);
    }
    
    /**
     * 得到临时群/组织信息修改窗口
     * 
     * @param m
     * @return
     */
    public MemberEditShell getMemberEditShell(Model m) {
    	return mesMap.get(m);
    }
	
	/**
	 * 移除一个查看消息窗口项
	 * 
	 * @param f
	 * 		好友model
	 * @return
	 * 		查看消息窗口
	 */
	public ReceiveIMWindow removeReceiveIMWindow(User f) {
		return rimwMap.remove(f.qq);
	}
	
	/**
	 * 检查好友是否有查看消息窗口打开
	 * 
	 * @param f
	 * 		好友model
	 * @return
	 * 		true表示好友f有一个查看消息窗口处于打开状态
	 */
	public boolean hasReceiveIMWindow(User f) {
	    return rimwMap.containsKey(f.qq);
	}
	
	/**
	 * @param f
	 * 		好友model
	 * @return
	 * 		查看消息窗口对象
	 */
	public ReceiveIMWindow getReceiveIMWindow(User f) {
	    return rimwMap.get(f.qq);
	}
	
	/**
	 * 注册查看消息窗口
	 * 
	 * @param f
	 * 		好友model
	 * @param rms
	 * 		查看消息窗口对象
	 */
	public void addReceiveIMWindow(User f, ReceiveIMWindow rms) {
	    rimwMap.put(f.qq, rms);
	}
	
	/**
	 * 检查是否存在好友的发送消息窗口
	 * 
	 * @param f
	 * 		好友model
	 * @return
	 * 		true表示存在
	 */
	public boolean hasSendIMWindow(User f) {
	    return simwMap.containsKey(f.qq);
	}
	
	/**
	 * @param f
	 * 		好友model
	 * @return
	 * 		发送消息窗口
	 */
	public SendIMWindow getSendIMWindow(User f) {
	    return simwMap.get(f.qq);
	}
	
	/**
	 * 添加一个发送消息窗口
	 * 
	 * @param f
	 * 		好友model
	 * @param sms
	 * 		发送消息窗口
	 */
	public void addSendIMWindow(User f, SendIMWindow sms) {
	    simwMap.put(f.qq, sms);
	}
	
	/**
	 * 删除一个发送消息窗口
	 * 
	 * @param f
	 * 		好友model
	 * @return
	 * 		发送消息窗口
	 */
	public SendIMWindow removeSendIMWindow(User f) {
	    return simwMap.remove(f.qq);
	}
	
	/**
	 * 关闭所有普通消息窗口
	 */
	public void clearAllSendIMWindow() {
		Map<Integer, SendIMWindow> temp = new HashMap<Integer, SendIMWindow>(simwMap);
		for(SendIMWindow window : temp.values())
			window.closeWindow();
	}
	
	/**
	 * 检查是否存在好友的资料窗口
	 * 
	 * @param f
	 * 		好友model
	 * @return
	 * 		true表示存在
	 */
	public boolean hasUserInfoWindow(User f) {
	    return uiwMap.containsKey(f.qq);
	}
	
	/**
	 * 检查是否存在好友的资料窗口
	 * 
	 * @param qq
	 * 		好友QQ号
	 * @return
	 * 		true表示存在
	 */
	public boolean hasUserInfoWindow(int qq) {
	    return uiwMap.containsKey(qq);
	}
	
	/**
	 * @param f
	 * 		好友model
	 * @return
	 * 		资料窗口
	 */
	public UserInfoWindow getUserInfoWindow(User f) {
	    return uiwMap.get(f.qq);
	}
	
	/**
	 * @param qq
	 * 		好友QQ号
	 * @return
	 * 		资料窗口
	 */
	public UserInfoWindow getUserInfoWindow(int qq) {
	    return uiwMap.get(qq);
	}
	
	/**
	 * 添加一个资料窗口
	 * 
	 * @param f
	 * 		好友model
	 * @param uis
	 * 		资料窗口
	 */
	public void addUserInfoWindow(User f, UserInfoWindow uis) {
	    uiwMap.put(f.qq, uis);
	}
	
	/**
	 * 删除一个资料窗口
	 * 
	 * @param f
	 * 		好友model
	 * @return
	 * 		好友资料窗口
	 */
	public UserInfoWindow removeUserInfoWindow(User f) {
	    return uiwMap.remove(f.qq);
	}
	
	/**
	 * 移除一个群资料窗口
	 * 
	 * @param c
	 * 		群model
	 * @return 
	 * 		群资料窗口
	 */
	public ClusterInfoWindow removeClusterInfoWindow(Cluster c) {
		return ciwMap.remove(c.clusterId);
	}
	
	/**
	 * 检查是否有群资料窗口打开
	 * 
	 * @param c
	 * 		群model
	 * @return
	 * 		true表示好友f有一个查看消息窗口处于打开状态
	 */
	public boolean hasClusterInfoWindow(Cluster c) {
	    return ciwMap.containsKey(c.clusterId);
	}
	
	/**
	 * @param c
	 * 		群model
	 * @return
	 * 		群资料窗口
	 */
	public ClusterInfoWindow getClusterInfoWindow(Cluster c) {
	    return ciwMap.get(c.clusterId);
	}
	
	/**
	 * 注册群资料窗口
	 * 
	 * @param c
	 * 		群model
	 * @param cis
	 * 		群资料窗口
	 */
	public void addClusterInfoWindow(Cluster c, ClusterInfoWindow cis) {
	    ciwMap.put(c.clusterId, cis);
	}
	
	/**
	 * 移除一个群发送消息窗口项
	 * 
	 * @param c
	 * 		群model
	 * @return
	 * 		群发送消息窗口
	 */
	public SendClusterIMWindow removeSendClusterIMWindow(Cluster c) {
		return scimwMap.remove(c.clusterId);
	}
	
	/**
	 * @return
	 * 		当前打开的群信息窗口model遍历器
	 */
	public Iterator<Integer> getSendClusterIMWindowModelIterator() {
	    return scimwMap.keySet().iterator();
	}
	
	/**
	 * 检查是否有群发送消息窗口打开
	 * 
	 * @param c
	 * 		群model
	 * @return
	 * 		true表示群c有一个群发送消息窗口处于打开状态
	 */
	public boolean hasSendClusterIMWindow(Cluster c) {
	    return scimwMap.containsKey(c.clusterId);
	}
	
	/**
	 * @param c
	 * 		群model
	 * @return
	 * 		群发送消息窗口
	 */
	public SendClusterIMWindow getSendClusterIMWindow(Cluster c) {
	    return scimwMap.get(c.clusterId);
	}
	
	/**
	 * 注册群发送消息窗口
	 * 
	 * @param c
	 * 		群model
	 * @param scimw
	 * 		群发送消息窗口
	 */
	public void addSendClusterIMWindow(Cluster c, SendClusterIMWindow scimw) {
	    scimwMap.put(c.clusterId, scimw);
	}
	
	/**
	 * 关闭所有群聊天窗口 
	 */
	public void clearAllSendClusterIMWindow() {
		Map<Integer, SendClusterIMWindow> temp = new HashMap<Integer, SendClusterIMWindow>(scimwMap);
		for(SendClusterIMWindow window : temp.values())
			window.closeWindow();
	}
	
    /**
     * @return
     * 		true表示系统消息列表窗口当前已经打开
     */
    public boolean isSystemMessageListWindowOpened() {
        return systemMessageListShell != null;
    }
    
    /**
     * 注册系统消息列表窗口
     * 
     * @param smls
     * 		系统消息列表窗口对象
     */
    public void registerSystemMessageListWindow(SystemMessageListWindow smls) {
        this.systemMessageListShell = smls;
    }
    
    /**
     * 注销系统消息列表窗口
     */
    public void deregisterSystemMessageListWindow() {
        this.systemMessageListShell = null;
    }
    
    /**
     * 注册消息发送标签页窗口
     * 
     * @param sitw
     * 		SendIMTabWindow对象
     */
    public void registerSendIMTabWindow(SendIMTabWindow sitw) {
    	this.imTabWindow = sitw;
    }
    
    /**
     * 注销消息发送标签页窗口
     */
    public void deregisterSendIMTabWindow() {
    	this.imTabWindow = null;
    }
    
    /**
     * @return
     * 		得到消息发送标签页窗口
     */
    public SendIMTabWindow getSendIMTabWindow() {
    	return imTabWindow;
    }
    
    /**
     * @return
     * 		true表示消息发送标签页窗口是打开的
     */
    public boolean isSendIMTabWindowOpened() {
    	return imTabWindow != null;
    }
    
    /**
     * @return
     * 		系统消息列表窗口
     */
    public SystemMessageListWindow getSystemMessageListWindow() {
        return systemMessageListShell;
    }
    
    /**
     * @return
     * 		true表示搜索窗口当前已经打开
     */
    public boolean isSearchWizardOpened() {
        return searchWizard != null;
    }
    
    /**
     * 注册搜索窗口
     * 
     * @param ss
     * 		搜索窗口对象
     */
    public void registerSearchWizard(WizardWindow ss) {
        this.searchWizard = ss;
    }
    
    /**
     * 注销搜索窗口
     */
    public void deregisterSearchWizard() {
        this.searchWizard = null;
    }
    
    /**
     * @return
     * 		搜索窗口
     */
    public WizardWindow getSearchWizard() {
        return searchWizard;
    }
    
    /**
     * @return
     * 		true表示创建群窗口当前已经打开
     */
    public boolean isClusterWizardOpened() {
        return clusterWizard != null;
    }
    
    /**
     * 注册创建群窗口
     * 
     * @param wizard
     * 		创建群窗口对象
     */
    public void registerClusterWizard(WizardWindow wizard) {
        this.clusterWizard = wizard;
    }
    
    /**
     * 注销创建群窗口
     */
    public void deregisterClusterWizard() {
        this.clusterWizard = null;
    }
    
    /**
     * @return
     * 		创建群窗口
     */
    public WizardWindow getClusterWizard() {
        return clusterWizard;
    }
    
    /**
     * @return
     * 		true表示系统设置窗口当前已经打开
     */
    public boolean isSystemOptionWindowOpened() {
        return systemOptionWindow != null;
    }
    
    /**
     * 注册系统设置窗口
     * 
     * @param sos
     * 		系统设置窗口对象
     */
    public void registerSystemOptionWindow(SystemOptionWindow sos) {
        this.systemOptionWindow = sos;
    }
    
    /**
     * 注销系统设置窗口
     */
    public void deregisterSystemOptionWindow() {
        this.systemOptionWindow = null;
    }
    
    /**
     * @return
     * 		系统设置窗口
     */
    public SystemOptionWindow getSystemOptionWindow() {
        return systemOptionWindow;
    }
    
    /**
     * @return
     * 		true表示检测更新窗口当前已经打开
     */
    public boolean isCheckUpdateShellOpened() {
        return checkUpdateShell != null;
    }
    
    /**
     * 注册检测更新窗口
     * 
     * @param cus
     * 		检测更新窗口对象
     */
    public void registerCheckUpdateShell(CheckUpdateShell cus) {
        this.checkUpdateShell = cus;
    }
    
    /**
     * 注销检测更新窗口
     */
    public void deregisterCheckUpdateShell() {
        this.checkUpdateShell = null;
    }
    
    /**
     * @return
     * 		检测更新窗口
     */
    public CheckUpdateShell getCheckUpdateShell() {
        return checkUpdateShell;
    }
    
    /**
     * @return
     * 		表情管理窗口
     */
    public FaceWindow getFaceWindow() {
        return faceWindow;
    }
    
    /**
     * @return
     * 		true表示表情管理窗口已经打开
     */
    public boolean isFaceWindowOpened() {
        return faceWindow != null;
    }
    
    /**
     * 注册表情管理窗口
     * 
     * @param fw
     */
    public void registerFaceWindow(FaceWindow fw) {
        this.faceWindow = fw;
    }
    
    /**
     * 注销表情管理窗口
     */
    public void deregisterFaceWindow() {
        this.faceWindow = null;
    }
}
