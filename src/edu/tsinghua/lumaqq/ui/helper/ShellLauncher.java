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

import static edu.tsinghua.lumaqq.resource.Messages.*;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.osgi.util.NLS;

import edu.tsinghua.lumaqq.MessageQueue;
import edu.tsinghua.lumaqq.models.Cluster;
import edu.tsinghua.lumaqq.models.Model;
import edu.tsinghua.lumaqq.models.ModelRegistry;
import edu.tsinghua.lumaqq.models.Organization;
import edu.tsinghua.lumaqq.models.User;
import edu.tsinghua.lumaqq.qq.Util;
import edu.tsinghua.lumaqq.qq.packets.InPacket;
import edu.tsinghua.lumaqq.qq.packets.in.ReceiveIMPacket;
import edu.tsinghua.lumaqq.record.RecordEntry;
import edu.tsinghua.lumaqq.ui.BrowserShell;
import edu.tsinghua.lumaqq.ui.CheckUpdateShell;
import edu.tsinghua.lumaqq.ui.IPSeekerWindow;
import edu.tsinghua.lumaqq.ui.InfoManagerWindow;
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

/**
 * 用来封装一些打开窗口的方法
 * 
 * @author luma
 */
public class ShellLauncher {
    private MainShell main;
    
    public ShellLauncher(MainShell main) {
        this.main = main;
    }    
    
    /**
     * 浏览搜索和创建校友录页面
     */
    public void goAlumni() {
        openBrowserShell(url_alumni, "Search/Create Alumni", NLS.bind(message_box_manual_browse, url_alumni));
    }
    
    /**
     * 去QQ家园
     */
    public void goQQHome() {
    	openBrowserShell(NLS.bind(url_qqhome, String.valueOf(main.getMyModel().qq), Util.convertByteToHexStringWithoutSpace(main.getClient().getUser().getClientKey())),
    			"",
				message_box_please_set_browser);
    }
    
    /**
     * 去QQ秀商城
     */
    public void goQQShowMall() {
    	openBrowserShell(NLS.bind(url_qqshow_mall, String.valueOf(main.getMyModel().qq), Util.convertByteToHexStringWithoutSpace(main.getClient().getUser().getClientKey())),
    			"",
				message_box_please_set_browser);
    }
    
    /**
     * 登录群社区
     */
    public void goClusterCommunity() {
    	openBrowserShell(url_cluster_community, "Group Community", NLS.bind(message_box_manual_browse, url_cluster_community));
    }
    
    /**
     * 申请免费QQ号
     */
    public void goApply() {
    	openBrowserShell(url_apply, "Apply Free QQ Number", NLS.bind(message_box_manual_browse, url_apply));
    }
    
    /**
     * 聊天室
     */
    public void goChatroom() {
    	openBrowserShell(url_chatroom, "QQ Chatroom", NLS.bind(message_box_manual_browse, url_chatroom));
    }
    
    /**
     * QQ游戏
     */
    public void goQQGame() {
    	openBrowserShell(url_game, "QQ Game", NLS.bind(message_box_manual_browse, url_game));
    }
    
    /**
     * LumaQQ主页
     */
    public void goLumaQQ() {
    	openBrowserShell(url_lumaqq, "LumaQQ HomePage", NLS.bind(message_box_manual_browse, url_lumaqq));
    }
    
    /**
     * 根据分类搜索群
     * 
     * @param categoryId
     * 		群分类ID
     */
    public void searchCluster(int categoryId) {
    	String url = NLS.bind(url_search_cluster, String.valueOf(categoryId));
    	openBrowserShell(url, "Search Cluster By Category", NLS.bind(message_box_manual_browse, url));
    }
    
    /**
     * 打开天气预报窗口
     * 
     * @return
     * 		天气预报窗口
     */
    public WeatherWindow openWeatherWindow() {
    	if(main.getShellRegistry().isWeatherWindowOpen()) {
    		WeatherWindow w = main.getShellRegistry().getWeatherWindow();
    		w.setMinimized(false);
    		w.setActive();
    		return w;
    	} else {
    		WeatherWindow w = ShellFactory.createWeatherWindow(main);
    		w.open();
    		return w;
    	}
    }
    
    /**
     * 打开短信窗口
     * 
     * @param friend
     * @return
     */
    public SMSWindow openSMSWindow(User friend) {
    	if(main.getShellRegistry().hasSMSWindow(friend)) {
    		SMSWindow w = main.getShellRegistry().getSMSWindow(friend);
    		w.setMinimized(false);
    		w.setActive();
    		return w;
    	} else {
    		SMSWindow w = ShellFactory.createSMSWindow(main, friend);
    		w.open();
    		return w;
    	}
    }
    
    /**
     * 打开短信窗口
     * 
     * @param mobile
     * @return
     */
    public SMSWindow openSMSWindow(String mobile) {
    	if(main.getShellRegistry().hasSMSWindow(mobile)) {
    		SMSWindow w = main.getShellRegistry().getSMSWindow(mobile);
    		w.setMinimized(false);
    		w.setActive();
    		return w;
    	} else {
    		SMSWindow w = ShellFactory.createSMSWindow(main, mobile);
    		w.open();
    		return w;
    	}
    }
    
    /**
     * 打开短信窗口 
     */
    public SMSWindow openSMSWindow() {
    	if(main.getShellRegistry().hasSMSWindow()) {
    		SMSWindow w = main.getShellRegistry().getSMSWindow();
    		w.setMinimized(false);
    		w.setActive();
    		return w;
    	} else {
    		SMSWindow w = ShellFactory.createSMSWindow(main);
    		w.open();
    		return w;
    	}
    }
    
    /**
	 * 打开系统消息列表窗口
	 */
	public void openSystemMessageListWindow() {
		if(main.getShellRegistry().isSystemMessageListWindowOpened()) {
		    SystemMessageListWindow smls = main.getShellRegistry().getSystemMessageListWindow();
		    smls.setMinimized(false);
		    smls.setActive();
		} else {
		    SystemMessageListWindow smls = ShellFactory.createSystemMessageListWindow(main);
		    main.getShellRegistry().registerSystemMessageListWindow(smls);
			smls.open();
		}
	}	
	
	/**
	 * 打开搜索用户窗口
	 * 
	 * @param startPage
	 * 		起始页
	 */
	public WizardWindow openSearchWizard() {
		if(main.getShellRegistry().isSearchWizardOpened()) {
		    WizardWindow ss = main.getShellRegistry().getSearchWizard();
			ss.setMinimized(false);
			ss.setActive();
			return ss;
		} else {
		    WizardWindow window = ShellFactory.createSearchWizard(main);
		    main.getShellRegistry().registerSearchWizard(window);
			window.open();
			return window;
		}
	}

    /**
	 * 打开群组创建向导
	 */
	public WizardWindow openClusterWizard() {
		if(main.getShellRegistry().isClusterWizardOpened()) {
		    WizardWindow window = main.getShellRegistry().getClusterWizard();
			window.setMinimized(false);
			window.setActive();
			return window;
		} else {
		    WizardWindow window = ShellFactory.createClusterWizard(main);
		    main.getShellRegistry().registerClusterWizard(window);
			window.open();
			return window;
		}
	}	

	/**
	 * 打开系统设置对话框
	 * 
	 * @return
	 * 		系统设置窗口对象
	 */
	public SystemOptionWindow openSystemOptionWindow() {
	    SystemOptionWindow sos = null;
		if(main.getShellRegistry().isSystemOptionWindowOpened()) {
		    sos = main.getShellRegistry().getSystemOptionWindow();
		    sos.setMinimized(false);
		    sos.setActive();
		} else {
			sos = ShellFactory.createSystemOptionWindow(main);
			main.getShellRegistry().registerSystemOptionWindow(sos);
			sos.open();			
		}
		return sos;
	}
	
	/**
     * 打开一个检测更新窗口
     */
    public void openCheckUpdateShell() {
        if(!main.getShellRegistry().isCheckUpdateShellOpened()) {
            CheckUpdateShell cus = ShellFactory.createCheckUpdateShell(main);
            main.getShellRegistry().registerCheckUpdateShell(cus);
            cus.open();                    
        }
    }
    
    /**
     * 打开临时群/组织信息修改窗口
     * 
     * @param type
     * @param parentCluster
     * @param parentOrganization
     * @param model
     * @return
     */
    public MemberEditShell openMemberEditShell(int type, Cluster parentCluster, Organization parentOrganization, Model model) {
    	ShellRegistry shellRegistry = main.getShellRegistry();
    	if(model != null && shellRegistry.hasMemberEditShell(model)) {
    		MemberEditShell shell = shellRegistry.getMemberEditShell(model);
    		shell.setFocus();
    		shell.setActive();
    		return shell;
    	} else {
    		MemberEditShell shell = ShellFactory.createMemberEditShell(main, type, parentCluster, parentOrganization);
    		if(model != null) 
    			shellRegistry.registerMemberEditShell(model, shell);
			if(type == MemberEditShell.TEMP_CLUSTER)
				shell.setCluster((Cluster)model);
			else
				shell.setOrganization((Organization)model);    			
    		shell.open();
    		return shell;
    	}    	
    }
    
    /**
     * 打开一个删除模式的查看系统消息窗口
     * 
     * @param f
     * 		好友model
     */
    public void openDeleteReceiveSystemMessageShell(User f) {
		ReceiveSystemMessageShell rsms = ShellFactory.createReceiveSystemMessageShell(main);
		rsms.setFriendModel(f);
		rsms.open(ReceiveSystemMessageShell.DELETE_MODE);
    }
    
    /**
     * 打开一个添加模式的查看系统消息窗口
     * 
     * @param f
     * 		好友model
     */
    public void openAddReceiveSystemMessageShell(User f) {
		ReceiveSystemMessageShell rsms = ShellFactory.createReceiveSystemMessageShell(main);
		rsms.setFriendModel(f);
		rsms.open(ReceiveSystemMessageShell.ADD_MODE);
    }
    
    /**
     * 打开一个IP查询窗口
     */
    public void openIPSeekerWindow() {
        IPSeekerWindow iss = ShellFactory.createIPSeekerWindow(main);
        iss.open();
    }    
	
	/**
	 * 打开群消息发送窗口
	 * 
	 * @param c
	 * 		群model
	 */
	public void openClusterIMWindow(Cluster c) {
		// 如果设置了打开标签页消息窗口
		if(main.getOptionHelper().isUseTabIMWindow()) {
			openIMTabWindow(c);
			return;
		}
		
	    ShellRegistry shellRegistry = main.getShellRegistry();
	    MessageQueue mq = main.getMessageQueue();
	    
	    // 如果该群消息窗口已经打开，则直接使用，如果没有，创建一个
	    SendClusterIMWindow scms = null;
		if(shellRegistry.hasSendClusterIMWindow(c)) {
			scms = shellRegistry.getSendClusterIMWindow(c);
			scms.setMinimized(false);
			scms.setActive();
		} else {
			scms = ShellFactory.createSendClusterIMWindow(main, c);
			shellRegistry.addSendClusterIMWindow(c, scms);
			scms.open();
		}
		// 群消息数清0
		c.messageCount = 0;
		BlindHelper blindHelper = main.getBlindHelper();
		blindHelper.refreshModel(c);
		
		int clusterId = c.clusterId;
		if(mq.hasMessage(clusterId)) {
			while(mq.hasMessage(clusterId)) {
				// 得到消息包
				ReceiveIMPacket packet = (ReceiveIMPacket)mq.getMessage(clusterId);    
				scms.appendMessage(c, packet.clusterIM, false);
			}
			blindHelper.getViewer(c).stopAnimation(c);
			switch(c.clusterType) {
				case SUBJECT:
					blindHelper.stopBlinkText(c.getParentCluster().getSubjectDummy());
					blindHelper.getViewer(c).stopAnimation(c.getParentCluster());
					break;
				case DIALOG:
					blindHelper.stopBlinkText(c.getParentCluster());
					break;
			}
			// 得到群的组索引和组的view part，重设组的图标特效状态
			blindHelper.resetGroupImageEffect(main.getBlindHelper().getClusterGroup());
			// 重设系统托盘区的图标特效状态
			main.getUIHelper().resetTrayImageEffect();		
		}	
	}	
    
    /**
	 * 打开一个消息窗口，不管sender到底是群还是好友
	 * 
	 * @param sender
	 * 		原始消息的发送者
	 */
	public void openIMShell(int sender) {
	    Cluster c = ModelRegistry.getCluster(sender);
		if(c == null)
			openNormalIMWindow(ModelRegistry.getUser(sender));
		else
			openClusterIMWindow(c);
	}

	/**
	 * 打开一个临时会话消息窗口
	 * 
	 * @param f
	 */
	public void openTempSessionIMWindow(int qq) {
		// 得到User对象，如果没有这个好友，新建一个临时对象
		User f = ModelRegistry.getUser(qq);
		boolean noUser = f == null;
		if(noUser) {
			f = new User();
			f.qq = qq;
			f.nick = f.displayName = String.valueOf(qq);
		}
		
		// 如果还没有这个用户的临时会话消息窗口就创建一个，如果有就直接得到一个
		ShellRegistry shellRegistry = main.getShellRegistry();
		MessageQueue mq = main.getMessageQueue();
		TempSessionIMWindow sms = null;
		if(shellRegistry.hasTempSessionIMWindow(f)) {
			sms = shellRegistry.getTempSessionIMWindow(f);
			sms.setMinimized(false);
			sms.setActive();
			sms.setFocus();				
		} else {
			sms = ShellFactory.createTempSessionIMWindow(main, f);
			sms.open();
		}
		// 把这个好友目前的所有消息都提取出来，显示到发送消息窗口中
		if(mq.hasTempSessionMessage(f.qq)) {
			while(mq.hasTempSessionMessage(f.qq)) {
				// 得到消息包
				ReceiveIMPacket packet = (ReceiveIMPacket)mq.getTempSessionMessage(f.qq);
				sms.appendMessage(f, packet.tempSessionIM);
			}
			// 停止好友头像的跳动
			main.getBlindHelper().stopAnimation(f.group, f);
			f.hasMessage = false;
			// 得到好友的组索引和组的view part，重设组的图标特效状态
			main.getBlindHelper().resetGroupImageEffect(f.group);
			// 重设系统托盘区的图标特效状态
			main.getUIHelper().resetTrayImageEffect();				
		}
		// 如果用户不在列表中，请求得到好友信息
		if(noUser)
			main.getClient().user_GetInfo(qq);
	}

	/**
	 * 打开消息发送或者接收窗口
	 * 
	 * @param f
	 * 		好友model
	 * @return
	 * 		打开的窗口对象，可能是SendMessageShell，也可能是ReceiveMessageShell
	 */
	public void openNormalIMWindow(User f) {
		// 如果设置了打开标签页消息窗口
		if(main.getOptionHelper().isUseTabIMWindow()) {
			openIMTabWindow(f);
			return;
		}
		
	    ShellRegistry shellRegistry = main.getShellRegistry();
	    MessageQueue mq = main.getMessageQueue();
	
		// 得到QQ号
		int qqNum = f.qq;
		// 判断是否是聊天模式，一共有两种模式，聊天模式和消息模式
		// 对于消息模式，有消息时打开一个查看消息窗口，一次看一条，用户点下一条按钮看其他的消息
		// 没有消息时，打开的是发送消息窗口
		// 但是如果是聊天模式情况就不一样，首先查看消息窗口是否被打开，而且将会一次性的
		// 把目前所有的消息都显示在发送消息窗口中。发送消息窗口有两种模式，分别对应于聊
		// 天模式和消息模式。这个模式信息就是保存在好友的model中。
		if(f.talkMode) {
			// 如果还没有这个好友的发送消息窗口就创建一个，如果有就直接得到一个
			SendIMWindow sms = null;
			if(shellRegistry.hasSendIMWindow(f)) {
				sms = shellRegistry.getSendIMWindow(f);
				sms.setMinimized(false);
				sms.setActive();
				sms.setFocus();				
			} else {
				sms = ShellFactory.createSendIMWindow(main, f);
				sms.open();
			}
			// 把这个好友目前的所有消息都提取出来，显示到发送消息窗口中
			if(mq.hasMessage(qqNum)) {
				while(mq.hasMessage(qqNum)) {
					// 得到消息包
					ReceiveIMPacket packet = (ReceiveIMPacket)mq.getMessage(qqNum);
					sms.appendMessage(f, packet.normalIM, packet.normalHeader);
				}
				// 停止好友头像的跳动
				main.getBlindHelper().getViewer(f).stopAnimation(f);
				f.hasMessage = false;
				main.getBlindHelper().refreshGroup(f.group);
				// 得到好友的组索引和组的view part，重设组的图标特效状态
				main.getBlindHelper().resetGroupImageEffect(f.group);
				// 重设系统托盘区的图标特效状态
				main.getUIHelper().resetTrayImageEffect();				
			}
		} else {
			if(mq.hasMessage(qqNum)) { // 如果这个好友有消息未读，则打开的是读消息窗口
				// 得到消息包
				ReceiveIMPacket packet = (ReceiveIMPacket)mq.getMessage(qqNum);
				// 检查这个好友是否还有消息，如果没有了则停止闪烁
				if(!mq.hasMessage(qqNum)) {
					main.getBlindHelper().stopAnimation(f.group, f);
					f.hasMessage = false;
					main.getBlindHelper().refreshModel(f);
				}
				// 得到好友的组索引和组的view part，重设组的图标特效状态
				main.getBlindHelper().resetGroupImageEffect(f.group);
				// 重设系统托盘区的图标特效状态
				main.getUIHelper().resetTrayImageEffect();				
				// 检查是否当前已经有一个该好友的查看消息窗口打开了，如果是，则只需要设置
				// 消息，如果否，则需要创建一个新窗口并加入到哈希表中
				ReceiveIMWindow rms = null;
				if(shellRegistry.hasReceiveIMWindow(f)) {
					rms = shellRegistry.getReceiveIMWindow(f);
					rms.setMinimized(false);
				} else {
					// 打开读消息窗口
					rms = ShellFactory.createReceiveIMWindow(main, f);
					rms.open();
				}
				rms.setMessage(packet);
				rms.setNextButtonEnabled(mq.hasMessage(qqNum));
				rms.setActive();
			} else { // 否则我们打开发送消息窗口
				// 检查当前是否已经有一个发送消息窗口打开了，如果是，返回，如果否，创建一个新窗口
			    SendIMWindow sms = null;
				if(shellRegistry.hasSendIMWindow(f)) {
					sms = shellRegistry.getSendIMWindow(f);
					sms.setMinimized(false);
					sms.setFocus();
				} else {
					sms = ShellFactory.createSendIMWindow(main, f);
					sms.open();				
				}
				sms.setActive();
			}			
		}
	}
	
	/**
	 * 打开群组资料窗口
	 * 
	 * @param c
	 * 		群model
	 * @return
	 * 		群组资料窗口
	 */
	public ClusterInfoWindow openClusterInfoWindow(Cluster c) {
	    ShellRegistry shellRegistry = main.getShellRegistry();
		if(shellRegistry.hasClusterInfoWindow(c)) {
			ClusterInfoWindow cis = shellRegistry.getClusterInfoWindow(c);
			cis.setMinimized(false);
			cis.setActive();
			return cis;
		} else {
			ClusterInfoWindow cis = ShellFactory.createClusterInfoWindow(main, c);
			shellRegistry.addClusterInfoWindow(c, cis);
			cis.open();
			return cis;
		}
	}	

	/**
	 * 打开一个浏览器窗口
	 * 
     * @param url
     * 		目的URL
     * @param title
     * 		窗口标题
     * @param errorString
     * 		如果出错，显示什么错误信息
     */
    public void openBrowserShell(String url, String title, String errorString) {
        // 查看是否设置了外部浏览器，如果设置了，就不使用缺省的浏览器
        String browser = main.getOptionHelper().getBrowser();
        try {
            if(browser.equals("")) {
        		MessageDialog dialog = new MessageDialog(main.getShell(),
        		        message_box_common_question_title, 
        		        null,
        		        message_box_browser_not_set,
        		        MessageDialog.QUESTION,
        		        new String[]{IDialogConstants.YES_LABEL, IDialogConstants.NO_LABEL, IDialogConstants.CANCEL_LABEL}, 
        		        0);
        		
                switch(dialog.open()) {
                    case 0:
	                    main.getShellLauncher().openSystemOptionWindow().setCurrentPage(SystemOptionWindow.OTHER);
                    	break;
                    case 1:
	    	            BrowserShell bs = ShellFactory.createBrowserShell(main);
	    	            bs.setUrl(url);
	    	            bs.setTitle(title);
	    	            bs.open();	
	    	            break;                    
                }
            } else
                Runtime.getRuntime().exec(browser.replaceAll("\\[URL\\]", url));
        } catch (Throwable t) {
            MessageDialog.openWarning(main.getShell(), message_box_common_warning_title, errorString);
        } 
    }
	
	/**
	 * 打开一个系统消息查看窗口
	 * 
	 * @param packet
	 * 		系统消息包
	 */
	public void openReceiveSystemMessageShell(InPacket packet) {
		// 如果没有系统消息了，停止闪烁消息按钮
		if(!main.getMessageQueue().hasSystemMessage())
			main.getUIHelper().stopBlinkSystemMessageIcon();
		// 调整tray icon的闪烁状态
		main.getUIHelper().resetTrayImageEffect();
		// 打开查看系统消息窗口
		ReceiveSystemMessageShell rsms = ShellFactory.createReceiveSystemMessageShell(main);
		rsms.setSystemMessage(packet);
		rsms.open();
	}
	
	/**
	 * 从记录中打开系统消息窗口
	 * 
	 * @param entry
	 * 		系统消息记录
	 */
	public void openReceiveSystemMessageShell(RecordEntry entry) {
		ReceiveSystemMessageShell rsms = ShellFactory.createReceiveSystemMessageShell(main);
		rsms.setSystemMessage(entry);
		rsms.open();
	}
	
	/**
	 * 打开一个好友资料查看窗口，窗口里的信息将根据friendmodel来设置
	 * 
	 * @param f
	 * 		好友model
	 * @param style
	 * 		窗口样式
	 */
	public UserInfoWindow openUserInfoWindow(User f, int style) {
	    ShellRegistry shellRegistry = main.getShellRegistry();
		// 查找是否已经存在这个好友的资料查看窗口，如果是，使该窗口激活，如果否，创建一个新的
		if(shellRegistry.hasUserInfoWindow(f)) {
		    UserInfoWindow uiw = shellRegistry.getUserInfoWindow(f);
			uiw.setMinimized(false);
			uiw.setActive();
			return uiw;
		} else {
		    UserInfoWindow uiw = ShellFactory.createUserInfoWindow(main, f, style);
			shellRegistry.addUserInfoWindow(f, uiw);
			uiw.open();
			return uiw;
		}		
	}
	
	/**
	 * 打开一个表情管理窗口
	 */
	public void openFaceWindow() {
	    ShellRegistry shellRegistry = main.getShellRegistry();
	    if(shellRegistry.isFaceWindowOpened()) {
	        FaceWindow window = shellRegistry.getFaceWindow();
	        window.setMinimized(false);
	        window.setActive();
	    } else {
	        FaceWindow window = ShellFactory.createFaceWindow(main);
	        shellRegistry.registerFaceWindow(window);
	        window.open();
	    }
	}
	
	/**
	 * 打开一个消息发送标签页窗口
	 * 
	 * @param model
	 * 		Model
	 */
	public SendIMTabWindow openIMTabWindow(Model model) {
		ShellRegistry shellRegistry = main.getShellRegistry();
		SendIMTabWindow window = null;
		if(shellRegistry.isSendIMTabWindowOpened()) {
			window = shellRegistry.getSendIMTabWindow();
			window.setMinimized(false);
			window.setActive();
		} else {
			window = ShellFactory.createSendIMTabWindow(main);
			shellRegistry.registerSendIMTabWindow(window);
			window.open();
		}
		
		window.addTabIM(model);
		window.activeContainer(model);
		
		// 把这个好友目前的所有消息都提取出来，显示到发送消息窗口中
		MessageQueue mq = main.getMessageQueue();
		if(model instanceof User) {
			User f = (User)model;
			if(mq.hasMessage(f.qq)) {
				while(mq.hasMessage(f.qq)) {
					// 得到消息包
					ReceiveIMPacket packet = (ReceiveIMPacket)mq.getMessage(f.qq);
					window.putMessage(model, packet);
				}
				// 停止好友头像的跳动
				main.getBlindHelper().getViewer(model).stopAnimation(model);
				f.hasMessage = false;
				main.getBlindHelper().refreshGroup(f.group);
				// 得到好友的组索引和组的view part，重设组的图标特效状态
				main.getBlindHelper().resetGroupImageEffect(f.group);
				// 重设系统托盘区的图标特效状态
				main.getUIHelper().resetTrayImageEffect();				
			}
		} else if(model instanceof Cluster) {
			BlindHelper blindHelper = main.getBlindHelper();
			Cluster c = (Cluster)model;			
			if(mq.hasMessage(c.clusterId)) {
				while(mq.hasMessage(c.clusterId)) {
					// 得到消息包
					ReceiveIMPacket packet = (ReceiveIMPacket)mq.getMessage(c.clusterId);    
					window.putMessage(c, packet);
				}
				blindHelper.getViewer(c).stopAnimation(c);
				switch(c.clusterType) {
					case SUBJECT:
						blindHelper.stopBlinkText(c.getParentCluster().getSubjectDummy());
						blindHelper.getViewer(c).stopAnimation(c.getParentCluster());
						break;
					case DIALOG:
						blindHelper.stopBlinkText(c.getParentCluster());
						break;
				}
				// 得到群的组索引和组的view part，重设组的图标特效状态
				blindHelper.resetGroupImageEffect(main.getBlindHelper().getClusterGroup());
				// 重设系统托盘区的图标特效状态
				main.getUIHelper().resetTrayImageEffect();		
			}
		}
		
		return window;
	}
	
	/**
	 * 打开信息管理器
	 * 
	 * @return
	 * 		信息管理器窗口
	 */
	public InfoManagerWindow openInfoManagerWindow() {
		if(main.getShellRegistry().isInfoManagerWindowOpen()) {
			InfoManagerWindow w = main.getShellRegistry().getInfoManagerWindow();
			w.setMinimized(false);
			w.setActive();
			return w;
		} else {
			InfoManagerWindow w = ShellFactory.createInfoManagerWindow(main);
			main.getShellRegistry().registerInfoManagerWindow(w);
			w.open();
			return w;
		}
	}
	
	/**
	 * 打开上次登录信息窗口
	 * 
	 * @param main
	 */
	public void openLastLoginTipWindow(MainShell main) {
		ShellFactory.createLastLoginTipWindow(main).open();
	}
}
