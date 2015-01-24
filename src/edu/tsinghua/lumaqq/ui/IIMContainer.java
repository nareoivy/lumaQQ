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
package edu.tsinghua.lumaqq.ui;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;

import edu.tsinghua.lumaqq.models.Model;
import edu.tsinghua.lumaqq.qq.events.IQQListener;
import edu.tsinghua.lumaqq.qq.packets.in.ReceiveIMPacket;

/**
 * 模块化聊天界面容器接口，方便实现标签页
 * 
 * @author luma
 */
public interface IIMContainer {
	public IQQListener getQQListener();
	
	/**
	 * 设置按键事件处理器
	 * 
	 * @param ka
	 */
	public void setKeyHandler(IContainerKeyHandler ka);
	
	/**
	 * @return
	 * 		本聊天面板的标识头像
	 */
	public Image getHead();
	
	/**
	 * 释放资源
	 */
	public void release();
	
	/**
	 * @return
	 * 		未读消息数
	 */
	public int getUnreadCount();
	
	/**
	 * 设置容器为活动容器，活动状态下，未读消息计数不增加
	 * 
	 * @param active
	 * 		true表示活动
	 */
	public void setActive(boolean active);
	
	/**
	 * @return
	 * 		true表示为活动消息容器
	 */
	public boolean isActiveContainer();
	
	/**
	 * 设置焦点
	 */
	public void setKeyboardFocus();
	
	/**
	 * @return
	 * 		model的名称，可能是昵称，群名称等
	 */
	public String getDisplayedName();
	
	/**
	 * @return
	 * 		model的id，qq号或者群外部id
	 */
	public int getId();
	
	/**
	 * @return
	 * 		容器相关model
	 */
	public Model getModel();
	
	/**
	 * 设置容器相关model
	 * 
	 * @param model
	 * 		Model子类
	 */
	public void setModel(Model model);
	
	/**
	 * 设置主窗口对象
	 * 
	 * @param main
	 * 		MainShell
	 */
	public void setMainShell(MainShell main);
	
	/**
	 * 发送
	 */
	public void send();
	
	/**
	 * 推入收到的消息
	 * 
	 * @param packet
	 * 		ReceiveIMPacket对象
	 */
	public void putMessage(ReceiveIMPacket packet);
	
	/**
	 * 设置是否使用什么热键发送消息
	 * 
	 * @param useEnter
	 * 		true表示使用enter发送
	 */
	public void setUseEnter(boolean useEnter);

	/**
	 * @return
	 * 		容器
	 */
	public Composite getComposite();
	
	/**
	 * 设置输入文本
	 * 
	 * @param text
	 * 		文本字符串
	 */
	public void setText(String text);
	
	/**
	 * 追加文本到消息容器
	 * 
	 * @param text
	 * 		要追加的文本
	 */
	public void appendText(String text);
	
	/**
	 * 初始化
	 */
	public void init();
}
