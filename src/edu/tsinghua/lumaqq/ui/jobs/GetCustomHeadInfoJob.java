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
package edu.tsinghua.lumaqq.ui.jobs;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.tsinghua.lumaqq.models.ModelRegistry;
import edu.tsinghua.lumaqq.models.User;
import edu.tsinghua.lumaqq.qq.QQ;
import edu.tsinghua.lumaqq.qq.QQPort;
import edu.tsinghua.lumaqq.qq.beans.CustomHead;
import edu.tsinghua.lumaqq.qq.events.QQEvent;
import edu.tsinghua.lumaqq.qq.net.IConnection;
import edu.tsinghua.lumaqq.qq.packets.in._03.GetCustomHeadInfoReplyPacket;
import edu.tsinghua.lumaqq.ui.MainShell;
import edu.tsinghua.lumaqq.ui.helper.FaceRegistry;

/**
 * 得到自定义头像信息的任务
 *
 * @author luma
 */
public class GetCustomHeadInfoJob extends AbstractJob {
    private static Log log = LogFactory.getLog(GetCustomHeadInfoJob.class);
    
	private List<Integer> friends;
	private List<CustomHead> headInfo;
	private IConnection conn;
	private int page;
	
	private static final int MAX_INFO = 20;
	
	@Override
	public void prepare(MainShell m) {
        super.prepare(m);
        headInfo = new ArrayList<CustomHead>();
        main.getClient().addQQListener(this);
	}
	
	@Override
	public Object getLinkArgument() {
		return headInfo;
	}

	@Override
	public void clear() {
		if(conn != null) {
			main.getClient().releaseConnection(conn.getId());
			conn = null;
		}
        main.getClient().removeQQListener(this);
	}

	@Override
	public boolean isSuccess() {
		return true;
	}
	
	@Override
	protected boolean canRun() {
		// 创建一个port
		try {
			conn = QQPort.CUSTOM_HEAD_INFO.create(main.getClient(), new InetSocketAddress(QQ.QQ_SERVER_DOWNLOAD_CUSTOM_HEAD, 4000), null, true);
		} catch(IOException e) {
			log.error("无法连接到自定义头像下载服务器");
			return false;
		} 
		
		// 得到有自定义头像好友的列表
		friends = new ArrayList<Integer>();
		Iterator<User> iter = ModelRegistry.getUserIterator();
		while(iter.hasNext()) {
			User u = iter.next();
			if(u.hasCustomHead)
				friends.add(u.qq);
		}
		
		// 判断列表是否大于0
		if(friends.isEmpty())
			return false;
		
		return true;
	}
	
	@Override
	protected void preLoop() {
		// 排序列表
		Collections.sort(friends, new Comparator<Integer>() {
			public int compare(Integer o1, Integer o2) {
				return o1 - o2;
			}
		});
		
		// 开始请求自定义头像信息
		log.debug("请求大小:" + friends.size());
		page = 1;
		main.getClient().customHead_GetInfo(friends.subList(0, Math.min(MAX_INFO, friends.size())), conn.getId());
	}
	
	@Override
	protected void OnQQEvent(QQEvent e) {
		switch(e.type) {
			case QQEvent.USER_GET_CUSTOM_HEAD_INFO_OK:
				processGetCustomHeadInfoSuccess(e);
				break;
			case QQEvent.SYS_TIMEOUT_03:
				switch(e.operation) {
					case QQ.QQ_03_CMD_GET_CUSTOM_HEAD_INFO:
						processGetCustomHeadInfoTimeout(e);
						break;
				}
				break;
		}
	}
	
	/**
	 * 处理得到自定义头像信息成功事件
	 * 
	 * @param e
	 */
	private synchronized void processGetCustomHeadInfoSuccess(QQEvent e) {
		GetCustomHeadInfoReplyPacket packet = (GetCustomHeadInfoReplyPacket)e.getSource();
		FaceRegistry reg = FaceRegistry.getInstance();
		for(CustomHead head : packet.heads) {
			User u = ModelRegistry.getUser(head.qq);
			if(u.customHeadTimestamp < head.timestamp || !reg.hasFace(reg.getMd5ById(u.customHeadId)))
				headInfo.add(head);
		}
		
		postGetCustomHeadInfo();
	}
	
	/**
	 * 处理得到自定义头像信息超时事件
	 * 
	 * @param e
	 */
	private synchronized void processGetCustomHeadInfoTimeout(QQEvent e) {
		postGetCustomHeadInfo();
	}
	
	/**
	 * 在得到自定义头像信息之后调用。判断是否已经得到所有好友自定义头像信息，如果是，开始请求自定义头像数据
	 */
	private void postGetCustomHeadInfo() {
		if(friends.size() > page * MAX_INFO) {
			main.getClient().customHead_GetInfo(friends.subList(page * MAX_INFO, Math.min(friends.size(), page * MAX_INFO + MAX_INFO)), conn.getId());
			page++;
		} else {
			wake();
		}
	}
}
