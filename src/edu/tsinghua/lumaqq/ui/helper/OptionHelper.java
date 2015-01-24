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

import java.io.File;

import org.eclipse.swt.graphics.RGB;

import edu.tsinghua.lumaqq.LumaQQ;
import edu.tsinghua.lumaqq.ecore.ProxyType;
import edu.tsinghua.lumaqq.ecore.option.OpType;
import edu.tsinghua.lumaqq.ecore.option.Options;
import edu.tsinghua.lumaqq.eutil.OptionUtil;
import edu.tsinghua.lumaqq.qq.Util;

/**
 * 管理系统设置的工具类，因为系统设置是一块比较大的部分，所以在这里用一个类
 * 来处理他
 * 
 * @author luma
 */
public class OptionHelper {
	private Options options;
	private String fileName;
	
	/**
	 * 构造函数
	 * @param options
	 */
	public OptionHelper() {
	    // 没什么要做的
	}
	
	/**
	 * 设置系统设置文件的根元素对象
	 * @param options
	 */
	public void setOptionsModel(Options options) {
		this.options = options;
	}
	
	/**
	 * @return options元素对象
	 */
	public Options getOptionsModel() {
		return options;
	}
	
	/**
	 * @return 是否是自动选择服务器
	 */
	public boolean isAutoSelect() {
		return options.getLoginOption().isAutoSelect();
	}
	
	/**
	 * @return 是否使用tcp方式登陆
	 */
	public boolean isUseTcp() {
		return options.getLoginOption().isUseTcp();
	}
	
	/**
	 * 设置是否是自动选择服务器
	 * @param b
	 */
	public void setAutoSelect(boolean b) {
		options.getLoginOption().setAutoSelect(b);
	}
	
	/**
	 * 设置是否用tcp方式登陆
	 * @param b
	 */
	public void setUseTcp(boolean b) {
		options.getLoginOption().setUseTcp(b);
	}
	
	/**
	 * @return 代理服务器地址
	 */
	public String getProxyServer() {
	    return options.getLoginOption().getProxyServer();
	}
	
	/**
	 * 设置代理服务器地址
	 * @param s
	 */
	public void setProxyServer(String s) {
	    options.getLoginOption().setProxyServer(s);
	}
	
	/**
	 * @return 代理服务器端口
	 */
	public int getProxyPort() {
        return options.getLoginOption().getProxyPort();
	}
	
	/**
	 * 设置代理服务器端口
	 * @param port
	 */
	public void setProxyPort(int port) {
	    options.getLoginOption().setProxyPort(port);
	}
	
	/**
	 * @return 代理服务器类型
	 */
	public ProxyType getProxyType() {
	    return options.getLoginOption().getProxyType();
	}
	
	/**
	 * 设置代理服务器类型
	 * @param s
	 */
	public void setProxyType(ProxyType pt) {
	    options.getLoginOption().setProxyType(pt);
	}
	
	/**
	 * @return 代理进行验证的用户名
	 */
	public String getProxyUsername() {
	    return options.getLoginOption().getProxyUsername();
	}
	
	/**
	 * 设置代理服务器的验证用户名
	 * @param u
	 */
	public void setProxyUsername(String u) {
	    options.getLoginOption().setProxyUsername(u);
	}
	
	/**
	 * @return 代理服务器的验证密码
	 */
	public String getProxyPassword() {
	    return options.getLoginOption().getProxyPassword();
	}
	
	/**
	 * 设置代理服务器的验证密码
	 * @param p
	 */
	public void setProxyPassword(String p) {
	    options.getLoginOption().setProxyPassword(p);
	}
	
	/**
	 * 得到TCP服务器缺省连接端口，因为TCP服务器有两个端口可用，如果失败，返回80
	 */
	public int getTcpPort() {
	    return options.getLoginOption().getTcpPort();
	}
	
	/**
	 * 设置TCP服务器端口
	 * 
	 * @param port
	 * 			端口的字符串形式
	 */
	public void setTcpPort(int port) {
	    options.getLoginOption().setTcpPort(port);
	}

	public void setTcpPort(String text) {
		int port = Util.getInt(text, 80);
	    options.getLoginOption().setTcpPort(port);
	}
	
	/**
	 * @return 主窗口上次关闭时的x位置
	 */
	public int getLocationX() {
		return options.getGuiOption().getLocationX();
	}
	
	/**
	 * @return 主窗口的宽度，如果为-1，表示使用缺省值
	 */
	public int getWidth() {
		return options.getGuiOption().getWidth();
	}
	
	/**
	 * @return 主窗口的高度，如果为-1，表示使用缺省值
	 */
	public int getHeight() {
		return options.getGuiOption().getHeight();
	}
	
	/**
	 * 设置主窗口宽度
	 * @param width
	 */
	public void setWidth(int width) {
	    options.getGuiOption().setWidth(width);
	}
	
	/**
	 * 设置主窗口高度
	 * @param height
	 */
	public void setHeight(int height) {
	    options.getGuiOption().setHeight(height);
	}
	
	/**
	 * @return true表示显示小头像
	 */
	public boolean isShowSmallHead() {
		return options.getGuiOption().isShowSmallHead();
	}
	
	/**
	 * @return true表示鼠标移动到好友上时显示提示
	 */
	public boolean isShowFriendTip() {
		return options.getGuiOption().isShowFriendTip();
	}
	
	/**
	 * 设置是否鼠标在好友上面时显示提示
	 * @param b
	 */
	public void setShowFrientTip(boolean b) {
		options.getGuiOption().setShowFriendTip(b);
	}
	
	/**
	 * @return true表示显示好友上线提示
	 */
	public boolean isShowOnlineTip() {
	    return options.getGuiOption().isShowOnlineTip();
	}
	
	/**
	 * @return 好友上线提示窗口的x坐标位置，如果为-1，表示使用缺省值
	 */
	public int getOnlineTipLocationX() {
		return options.getGuiOption().getOnlineTipLocationX();
	}
	
	/**
	 * @return 好友上线提示窗口的y坐标位置，如果为-1，表示使用缺省值
	 */
	public int getOnlineTipLocationY() {
		return options.getGuiOption().getOnlineTipLocationY();
	}
	
	/**
	 * 设置好友上线提示窗口的x坐标位置
	 * @param x 
	 */
	public void setOnlineTipLocationX(int x) {
	    options.getGuiOption().setOnlineTipLocationX(x);
	}
	
	/**
	 * 设置好友上线提示窗口的y坐标位置
	 * @param y
	 */
	public void setOnlineTipLocationY(int y) {
	    options.getGuiOption().setOnlineTipLocationY(y);
	}
	
	/**
	 * 设置是否显示好友上线提示
	 * @param b
	 */
	public void setShowOnlineTip(boolean b) {
	    options.getGuiOption().setShowOnlineTip(b);
	}
	
	/**
	 * 设置显示大头像还是小头像
	 * @param b
	 */
	public void setShowSmallHead(boolean b) {
		options.getGuiOption().setShowSmallHead(b);
	}
	
	/**
	 * @return 主窗口上次关闭时的y位置
	 */
	public int getLocationY() {
		return options.getGuiOption().getLocationY();
	}
	
	/**
	 * 检查是否自动弹出消息
	 * @return
	 */
	public boolean isAutoEject() {
		return options.getMessageOption().isAutoEject();
	}
	
	/**
	 * 检查是否拒绝陌生人消息
	 * @return
	 */
	public boolean isRejectStranger() {
		return options.getMessageOption().isRejectStranger();
	}
	
	/**
	 * 保存主窗口关闭时的x位置
	 * @param x
	 */
	public void setLocationX(int x) {
		options.getGuiOption().setLocationX(x);
	}
	
	/**
	 * 保存主窗口关闭时的y位置
	 * @param y
	 */
	public void setLocationY(int y) {
		options.getGuiOption().setLocationY(y);
	}
	
	/**
	 * 检查好友列表是显示真实姓名还是显示昵称
	 * @return true表示显示昵称
	 */
	public boolean isShowNick() {
		return options.getGuiOption().isShowNick();
	}
	
	/**
	 * 设置好友列表是显示真实姓名还是显示昵称
	 * @param b true表示显示昵称
	 */
	public void setShowNick(boolean b) {
		options.getGuiOption().setShowNick(b);
	}
	
	/**
	 * 检查是否当前只显示在线用户
	 * @return true表示只显示在线用户
	 */
	public boolean isShowOnlineOnly() {
		return options.getGuiOption().isShowOnlineOnly();
	}
	
	/**
	 * 设置是否只显示在线用户
	 * @param b
	 */
	public void setShowOnlineOnly(boolean b) {
		options.getGuiOption().setShowOnlineOnly(b);
	}
	
	/**
	 * 设置是否自动弹出消息
	 * @param b
	 */
	public void setAutoEject(boolean b) {
		options.getMessageOption().setAutoEject(b);
	}
	
	/**
	 * 检查当前声音是否使能
	 * @return true如果声音提示被打开
	 */
	public boolean isEnableSound() {
		return options.getMessageOption().isEnableSound();
	}
	
	/**
	 * 设置当前声音是否使能
	 * @param b true表示声音使能
	 */
	public void setEnableSound(boolean b) {
		options.getMessageOption().setEnableSound(b);
	}
	
	/**
	 * 设置是否拒绝陌生人消息
	 * @param b
	 */
	public void setRejectStranger(boolean b) {
		options.getMessageOption().setRejectStranger(b);
	}
	
	/**
	 * @return true如果消息模式采用enter键发送消息
	 */
	public boolean isUseEnterInMessageMode() {
		return options.getMessageOption().isUseEnterInMessageMode();
	}
	
	/**
	 * 设置消息模式是否使用enter键发送消息
	 * @param b
	 */
	public void setUseEnterInMessageMode(boolean b) {
		options.getMessageOption().setUseEnterInMessageMode(b);
	}
	
	/**
	 * @return true如果聊天模式采用enter键发送消息
	 */
	public boolean isUseEnterInTalkMode() {
		return options.getMessageOption().isUseEnterInTalkMode();
	}
	
	/**
	 * 设置聊天模式是否采用enter键发送消息
	 * @param b
	 */
	public void setUseEnterInTalkMode(boolean b) {
		options.getMessageOption().setUseEnterInTalkMode(b);
	}
	
	/**
	 * @return 登陆服务器
	 */
	public String getServer() {
		return options.getLoginOption().getServer();
	}
	
	/**
	 * 设置登陆服务器
	 * @param s
	 */
	public void setServer(String s) {
		options.getLoginOption().setServer(s);
	}
	
	/**
	 * 得到浏览器命令行
	 * @return 命令行，如果没有，为空字符串
	 */
	public String getBrowser() {
	    return options.getOtherOption().getBrowser();
	}
	
	/**
	 * 设置浏览器命令行
	 * @param browser
	 */
	public void setBrowser(String browser) {
	    options.getOtherOption().setBrowser(browser);
	}

	/**
	 * 保存设置
	 */
	public void save() {
		File file = new File(fileName);
		OptionUtil.save(file, options);
	}

	/**
	 * @param string
	 */
	public void setFileName(String string) {
		this.fileName = string;
	}
	
	/**
	 * @return 缺省消息字体
	 */
	public String getFontName() {
	    return options.getGuiOption().getFontName();
	}
	
	/**
	 * 设置缺省消息字体
	 * @param s
	 */
	public void setFontName(String s) {
	    options.getGuiOption().setFontName(s);
	}
	
	/**
	 * @return 缺省字体大小
	 */
	public int getFontSize() {
	    return options.getGuiOption().getFontSize();
	}
	
	/**
	 * 设置字体大小
	 * @param s
	 */
	public void setFontSize(int s) {
	    options.getGuiOption().setFontSize(s);
	}
	
	/**
	 * @return true表示粗体
	 */
	public boolean getBold() {
	    return options.getGuiOption().isBold();
	}
	
	/**
	 * 设置粗体
	 * @param b
	 */
	public void setBold(boolean b) {
	    options.getGuiOption().setBold(b);
	}
	
	/**
	 * @return true表示斜体
	 */
	public boolean getItalic() {
	    return options.getGuiOption().isItalic();
	}
	
	/**
	 * 设置是否斜体
	 * @param b
	 */
	public void setItalic(boolean b) {
	    options.getGuiOption().setItalic(b);
	}

	/**
	 * @return 字体颜色
	 */
	public int getFontColor() {
	    return options.getGuiOption().getFontColor();
	}
	
	/**
	 * 设置字体颜色
	 * @param b
	 */
	public void setFontColor(int b) {
	    options.getGuiOption().setFontColor(b);
	}
	
	/**
	 * 设置字体颜色
	 * @param rgb
	 */
	public void setFontColor(RGB rgb) {
		options.getGuiOption().setFontColor((rgb.red << 16) | (rgb.green << 8) | rgb.blue);
	}
	
	/**
	 * @return 组背景RGB字符串
	 */
	public RGB getGroupBackgroup() {
		int c = options.getGuiOption().getGroupBackground();
		RGB rgb = new RGB((c >>> 16) & 0xFF, (c >>> 8) & 0xFF, c & 0xFF);
		return rgb;
	}
	
	/**
	 * 设置组的背景颜色
	 * 
	 * @param rgb
	 * 			RGB值的字符串形式
	 */
	public void setGroupBackgroup(RGB rgb) {
	    options.getGuiOption().setGroupBackground((rgb.red << 16) | (rgb.green << 8) | rgb.blue);
	}
	
	/**
	 * @return true表示是否把陌生人也考虑在最近联系人中
	 */
	public boolean isKeepStrangerInLatest() {
	    return options.getOtherOption().isKeepStrangerInLatest();
	}
	
	/**
	 * 设置是否把陌生人添加到最近联系人组
	 * 
	 * @param b
	 * 		true表示添加
	 */
	public void setKeepStrangerInLatest(boolean b) {
	    options.getOtherOption().setKeepStrangerInLatest(b);
	}
	
	/**
	 * @return 最近联系人组的大小
	 */
	public int getLatestSize() {
	    return options.getOtherOption().getLatestSize();
	}
	
	/**
	 * 设置最近联系人组的大小
	 * 
	 * @param size
	 * 			保存多少个最近联系人
	 */
	public void setLatestSize(int size) {
	    options.getOtherOption().setLatestSize(size);
	}

	public void setLatestSize(String text) {
		int size = Util.getInt(text, 20);
	    options.getOtherOption().setLatestSize(size);
	}
	
	/**
	 * @return true使用最近联系人功能
	 */
	public boolean isEnableLatest() {
	    return options.getOtherOption().isEnableLatest();
	}
	
	/**
	 * 打开或者关闭最近联系人功能
	 * 
	 * @param b
	 * 			true表示打开最近联系人功能
	 */
	public void setEnableLatest(boolean b) {
	    options.getOtherOption().setEnableLatest(b);
	}
	
	/**
	 * @return true表示显示虚拟摄像头
	 */
	public boolean isShowFakeCam() {
	    return options.getOtherOption().isShowFakeCam();
	}
	
	/**
	 * 打开或者关闭虚拟摄像头功能
	 * 
	 * @param b
	 * 			true表示显示虚拟摄像头
	 */
	public void setShowFakeCam(boolean b) {
	    options.getOtherOption().setShowFakeCam(b);
	}
	
	public boolean isAutoDownloadGroup() {
	    return options.getSyncOption().isAutoDownloadGroup();
	}
	
	public void setAutoDownloadGroup(boolean b) {
	    options.getSyncOption().setAutoDownloadGroup(b);
	}
	
	public OpType getAutoUploadGroup() {
	    return options.getSyncOption().getAutoUploadGroup();
	}
	
	public void setAutoUploadGroup(OpType ot) {
        options.getSyncOption().setAutoUploadGroup(ot);
	}
	
	public boolean isAutoDownloadFriendRemark() {
	    return options.getSyncOption().isAutoDownloadFriendRemark();
	}
	
	public void setAutoDownloadFriendRemark(boolean b) {
	    options.getSyncOption().setAutoDownloadFriendRemark(b);
	}
	
	public boolean isTreeMode() {
		return options.getGuiOption().isTreeMode();
	}
	
	public void setTreeModel(boolean b) {
		options.getGuiOption().setTreeMode(b);
	}
	
	public boolean isMinimizeWhenClose() {
		return options.getGuiOption().isMinimizeWhenClose();
	}
	
	public void setMinimizeWhenClose(boolean b) {
		options.getGuiOption().setMinimizeWhenClose(b);
	}
	
	public boolean isOnTop() {
		return options.getGuiOption().isOnTop();
	}
	
	public void setOnTop(boolean b) {
		options.getGuiOption().setOnTop(b);
	}
	
	public boolean isHideWhenMinimize() {
		return options.getGuiOption().isHideWhenMinimize();
	}
	
	public void setHideWhenMinimize(boolean b) {
		options.getGuiOption().setHideWhenMinimize(b);
	}
	
	public boolean isShowBlacklist() {
		return options.getGuiOption().isShowBlacklist();
	}
	
	public void setShowBlacklist(boolean b) {
		options.getGuiOption().setShowBlacklist(b);
	}

	public boolean isAutoDock() {
		return options.getGuiOption().isAutoDock();
	}

	public void setAutoDock(boolean b) {
		options.getGuiOption().setAutoDock(b);
	}
	
	public boolean isAutoCheckUpdate() {
		return options.getSyncOption().isAutoCheckUpdate();
	}
	
	public void setAutoCheckUpdate(boolean b) {
		options.getSyncOption().setAutoCheckUpdate(b);
	}
	
	public boolean isUseTabIMWindow() {
		return options.getGuiOption().isUseTabIMWindow();
	}
	
	public void setUseTabIMWindow(boolean b) {
		options.getGuiOption().setUseTabIMWindow(b);
	}
	
	public void setShowSignature(boolean b) {
		options.getGuiOption().setShowSignature(b);
	}
	
	public boolean isShowSignature() {
		return options.getGuiOption().isShowSignature();
	}
	
	public String getMessageKey() {
		return options.getKeyOption().getMessageKey();
	}
	
	public void setMessageKey(String key) {
		options.getKeyOption().setMessageKey(key);
	}
	
	public boolean isIMOnTop() {
		if(!LumaQQ.IS_WIN32)
			return false;
		else
			return options.getGuiOption().isImOnTop();
	}
	
	public void setIMOnTop(boolean b) {
		options.getGuiOption().setImOnTop(b);
	}
	
	public boolean isRejectTempSessionIM() {
		return options.getMessageOption().isRejectTempSessionIM();
	}
	
	public void setRejectTempSessionIM(boolean b) {
		options.getMessageOption().setRejectTempSessionIM(b);
	}
	
	public void setShowCustomHead(boolean b) {
		options.getGuiOption().setShowCustomHead(b);
	}
	
	public boolean isShowCustomHead() {
		return options.getGuiOption().isShowCustomHead();
	}
	
	public boolean isShowLastLoginTip() {
		return options.getGuiOption().isShowLastLoginTip();
	}
	
	public void setShowLastLoginTip(boolean b) {
		options.getGuiOption().setShowLastLoginTip(b);
	}
	
	public boolean isBarExpanded() {
		return options.getGuiOption().isBarExpanded();
	}
	
	public void setBarExpanded(boolean b) {
		options.getGuiOption().setBarExpanded(b);
	}
}
