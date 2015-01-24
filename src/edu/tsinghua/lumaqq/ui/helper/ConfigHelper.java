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

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;

import edu.tsinghua.lumaqq.LumaQQ;
import edu.tsinghua.lumaqq.ecore.face.FaceGroup;
import edu.tsinghua.lumaqq.ecore.face.Faces;
import edu.tsinghua.lumaqq.ecore.group.GroupFactory;
import edu.tsinghua.lumaqq.ecore.group.XGroup;
import edu.tsinghua.lumaqq.ecore.group.XGroups;
import edu.tsinghua.lumaqq.ecore.group.XUser;
import edu.tsinghua.lumaqq.ecore.option.Options;
import edu.tsinghua.lumaqq.ecore.proxy.Proxies;
import edu.tsinghua.lumaqq.ecore.remark.Remark;
import edu.tsinghua.lumaqq.ecore.remark.Remarks;
import edu.tsinghua.lumaqq.ecore.reply.Replies;
import edu.tsinghua.lumaqq.eutil.FaceUtil;
import edu.tsinghua.lumaqq.eutil.GroupUtil;
import edu.tsinghua.lumaqq.eutil.OptionUtil;
import edu.tsinghua.lumaqq.eutil.ProxyUtil;
import edu.tsinghua.lumaqq.eutil.RemarkUtil;
import edu.tsinghua.lumaqq.eutil.ReplyUtil;
import edu.tsinghua.lumaqq.hotkey.KeyBinder;
import edu.tsinghua.lumaqq.models.ModelRegistry;
import edu.tsinghua.lumaqq.models.ModelUtils;
import edu.tsinghua.lumaqq.models.User;
import edu.tsinghua.lumaqq.ui.MainShell;

/**
 * 检查，维护配置文件
 * 
 * @author luma
 */
public class ConfigHelper {
    private static Log log = LogFactory.getLog(ConfigHelper.class);    
    private MainShell main;
    private Proxies proxies;
	private Remarks remarks;
	private Map<Integer, Remark> remarkHash;
	
	private XGroups selfGroups;
	private User me;
	private String selfFilename;
	
	private boolean autoReply;
	private Replies replies;
    
    public ConfigHelper(MainShell main) {
        this.main = main;
        remarkHash = new HashMap<Integer, Remark>();
    }
    
    /**
     * @return
     * 		自己的model
     */
    public User getMyself() {
    	return me;
    }
    
    /**
     * 初始化用户信息文件
     */
    public void initSelf() {
    	if(me != null && me.qq == main.getClient().getUser().getQQ())
    		return;
    		
    	selfFilename = LumaQQ.SELF;
    	File file = new File(selfFilename);
    	selfGroups = GroupUtil.load(file);
    	if(selfGroups == null) {
    		selfGroups = createDefaultSelf();
    		GroupUtil.save(file, selfGroups);
    	}
    	XGroup g = (XGroup)selfGroups.getGroup().get(0);
    	XUser u = (XUser)g.getUser().get(0);
    	me = ModelUtils.createUser(u);
    }
    
    /**
     * 保存用户信息文件
     */
    @SuppressWarnings("unchecked")
    public void saveSelf() {
    	if(selfGroups != null) {
    		File file = new File(selfFilename);
    		XUser u = ModelUtils.createXUser(me);
    		XGroup g = (XGroup)selfGroups.getGroup().get(0);
    		g.getUser().clear();
    		g.getUser().add(u);
    		GroupUtil.save(file, selfGroups);    		
    	}
    }
    
    /**
     * @return
     * 		缺省self文件
     */
    @SuppressWarnings("unchecked")
    private XGroups createDefaultSelf() {
    	XGroups groups = GroupFactory.eINSTANCE.createXGroups();
    	XGroup group = GroupFactory.eINSTANCE.createXGroup();
    	group.setName("Self");
    	groups.getGroup().add(group);
    	XUser user = GroupFactory.eINSTANCE.createXUser();
    	user.setQq(main.getClient().getUser().getQQ());
    	user.setNick(String.valueOf(user.getQq()));
    	group.getUser().add(user);
    	return groups;
    }
	
	/**
	 * 初始化代理列表
	 */
	public void initProxies() {	    
		// 检查代理列表文件，如果没有则创建一个，如果创建失败则退出
		File proxyFile = new File(LumaQQ.PROXIES);
		// 读入代理列表文件
	    proxies = ProxyUtil.load(proxyFile);
	    if(proxies == null) {
	    	proxies = ProxyUtil.createDefault();
	    	ProxyUtil.save(proxyFile, proxies);
	    }
	}
	
	/**
	 * 保存代理列表文件
	 */
	public void saveProxies() {
		File proxyFile = new File(LumaQQ.PROXIES);
		ProxyUtil.save(proxyFile, proxies);
	}
	
	/**
	 * 初始化自定义表情配置文件
	 */
	@SuppressWarnings("unchecked")
	public void initFaces() {
		FaceRegistry faces = FaceRegistry.getInstance();
		
		// 创建表情目录
		File dir = new File(LumaQQ.CUSTOM_FACE_DIR);
		if(!dir.exists())
		    dir.mkdirs();
		
		// 检查自定义表情配置文件，如果没有则创建一个
		File faceFile = new File(LumaQQ.CUSTOM_FACES);
		if(!checkFaceFile(faceFile))
			return;
		
		// 读入配置文件
		Faces f = FaceUtil.load(faceFile);
		if(f == null)
			return;
		faces.setFacesModel(f);
		faces.setFileName(faceFile.getAbsolutePath());
		
		// 初始化表情组文件夹
		for(FaceGroup group : (List<FaceGroup>)f.getGroup()) {
			dir = new File(LumaQQ.CUSTOM_FACE_DIR + group.getId());
			if(!dir.exists())
				dir.mkdirs();
		}
	}
	
	/**
	 * 初始化快捷回复消息
	 */
	public void initReplies() {	    
		// 检查快捷回复信息文件，如果没有则创建一个，如果创建失败则退出
		File replyFile = new File(LumaQQ.REPLIES);
		// 读入回复信息文件
		replies = ReplyUtil.load(replyFile);
		if(replies == null) {
			createDefaultReplyXmlFile(replyFile);
		}
		main.getMenuHelper().renewAawyMenu();
	}	
	
	public void saveReplies() {
		File replyFile = new File(LumaQQ.REPLIES);
		ReplyUtil.save(replyFile, replies);
	}
	
	/**
	 * @return
	 * 		当前自动回复
	 */
	public String getCurrentAutoReplyString() {
		return (String)replies.getAutoReply().get(replies.getCurrentAutoReply());
	}
	
	/**
	 * @return
	 * 		当前快捷回复
	 */
	public String getCurrentQuickReplyString() {
		return (String)replies.getQuickReply().get(replies.getCurrentQuickReply());
	}
	
	/**
	 * 创建缺省快捷回复信息文件
	 * 
	 * @param file
	 * 		文件对象
	 */
	@SuppressWarnings("unchecked")
	public void createDefaultReplyXmlFile(File file) {
		file.getParentFile().mkdirs();
		replies = ReplyUtil.createDefault();
		replies.setCurrentAutoReply(0);
		replies.setCurrentQuickReply(0);
		replies.getQuickReply().add(menu_quick_reply_o);
		replies.getQuickReply().add(menu_quick_reply_ok);
		replies.getQuickReply().add(menu_quick_reply_enough);
		replies.getQuickReply().add(menu_quick_reply_understand);
		replies.getQuickReply().add(menu_quick_reply_bye);
		replies.getQuickReply().add(menu_quick_reply_kao);
		replies.getAutoReply().add(menu_status_away_absent);
		replies.getAutoReply().add(menu_status_away_working);
		replies.getAutoReply().add(menu_status_away_eating);
		ReplyUtil.save(file, replies);
	}

	/**
	 * 初始化系统设置
	 */ 
	public void initSystemOptions() {
		// 检查快捷回复信息文件，如果没有则创建一个，如果创建失败则退出
		File sysOptFile = new File(LumaQQ.SYSTEM_OPTIONS);
		sysOptFile.getParentFile().mkdirs();
		if(!checkSysOptFile(sysOptFile))
			main.close();
		// 读入系统设置文件
		Options o = OptionUtil.load(sysOptFile);
		if(o == null)
		    main.close();
		main.getOptionHelper().setOptionsModel(o);
		main.getOptionHelper().setFileName(sysOptFile.getAbsolutePath());
	}
	
	/**
	 * 一些其他初始化操作，需要在mainshell初始化之后才能做
	 */
	public void postInitSystemOptions() {
		// 初始化字体信息
		main.createDefaultStyle();
		// 设置组背景颜色
		RGB rgb = main.getOptionHelper().getGroupBackgroup();
		if(rgb != null) 
		    main.setGroupColor(new Color(main.getDisplay(), rgb));
		// 初始化快捷键
		initHotkeys();
	}
	
	/**
	 * 初始化热键
	 * 
	 * @return
	 * 		初始化成功返回true
	 */
	public boolean initHotkeys() {
		KeyBinder binder = KeyBinder.getInstance();
		if(KeyBinder.initOk) {
			binder.deregisterAll();
			if(!binder.register(main.getOptionHelper().getMessageKey(), main.getMessageKeyListener())) {
				log.error("提起消息热键绑定失败");
				return false;
			}
			return true;
		} else {
			main.getDisplay().removeFilter(SWT.KeyDown, main.getFalseMessageKeyListener());
			main.getDisplay().addFilter(SWT.KeyDown, main.getFalseMessageKeyListener());
			return true;
		}
	}

	/**
	 * 初始化备注信息
	 */
	@SuppressWarnings("unchecked")
	public void initRemarks() {	    
		File remarksFile = new File(LumaQQ.REMARKS);
		
		remarks = RemarkUtil.load(remarksFile);
		if(remarks == null)  {
			remarks = RemarkUtil.createDefault();
			RemarkUtil.save(remarksFile, remarks);
		}
		
		// 把备注名称都添加到好友的model中
		remarkHash.clear();
		for(Remark remark : (List<Remark>)remarks.getRemark()) {
			User f = ModelRegistry.getUser(remark.getQq());
			if(f != null) 
				f.remark = remark;
			remarkHash.put(remark.getQq(), remark);
		}
	}
	
	/**
	 * 得到好友的备注对象
	 * 
	 * @param qq
	 * 		QQ号
	 * @return
	 * 		备注对象
	 */
	public Remark getRemark(int qq) {
		return remarkHash.get(qq);
	}
	
	/**
	 *  保存一个备注
	 *  
	 * @param remark
	 */
	public void putRemark(Remark remark) {
		remarkHash.put(remark.getQq(), remark);
	}
	
	/**
	 * 保存备注信息文件
	 */
	@SuppressWarnings("unchecked")
	public void saveRemarks() {
		File remarksFile = new File(LumaQQ.REMARKS);
		remarks.getRemark().clear();
		remarks.getRemark().addAll(remarkHash.values());
		RemarkUtil.save(remarksFile, remarks);
	}
	
	/**
     * 检查分组信息文件的存在性，如果不存在就创建一个，如果创建也失败则返回false
     * 
     * @param file
     * 		分组信息文件的File对象
     * @return
     * 		true表示初始化成功
     */
    public boolean checkGroupFile(File file) {
        if(!file.exists()) {
            try {
            	ModelUtils.createDefaultGroupXmlFile(file);
	        } catch (IOException e) {
	            log.error("无法创建分组信息文件");
	            return false;
	        }
        } 
        return true;
    }
    
    /**
     * 检查系统设置文件是否存在，不存在就创建一个，如果创建也失败则返回false
     * 
     * @param file
     * 		系统设置文件对象
     * @return
     * 		true表示初始化成功
     */
    public boolean checkSysOptFile(File file) {
        if(!file.exists()) {
            try {
            	ModelUtils.createDefaultSysOptFile(file);
	        } catch (IOException e) {
	            log.error("无法创建系统设置文件");
	            return false;
	        }
        } 
        return true;
    }
    
    /**
	 * 检查自定义表情配置文件是否存在，不存在就创建一个，如果创建也失败则返回false
	 *
     * @param file
     * 		自定义表情配置文件对象
     * @return
     * 		true表示初始化成功
     */
    public boolean checkFaceFile(File file) {
    	if(!file.exists()) {
    		try {
    			file.getParentFile().mkdirs();
				ModelUtils.createDefaultFaceFile(file);				
			} catch (IOException e) {
				log.error("无法创建表情配置文件，自定义表情无法使用");
				return false;
			}
    	}
    	return true;
    }

	/**
	 * @return Returns the proxies.
	 */
	public Proxies getProxies() {
		return proxies;
	}

	/**
	 * @return Returns the remarks.
	 */
	public Remarks getRemarks() {
		return remarks;
	}

	/**
	 * @return Returns the autoReply.
	 */
	public boolean isAutoReply() {
		return autoReply;
	}

	/**
	 * @param autoReply The autoReply to set.
	 */
	public void setAutoReply(boolean autoReply) {
		this.autoReply = autoReply;
	}

	/**
	 * @return Returns the replies.
	 */
	public Replies getReplies() {
		return replies;
	}
}
