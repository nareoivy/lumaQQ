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

import static edu.tsinghua.lumaqq.resource.Messages.*;

import java.util.ArrayList;
import java.util.List;

import edu.tsinghua.lumaqq.qq.QQ;
import edu.tsinghua.lumaqq.qq.beans.DownloadFriendEntry;
import edu.tsinghua.lumaqq.qq.events.QQEvent;
import edu.tsinghua.lumaqq.qq.packets.in.DownloadGroupFriendReplyPacket;
import edu.tsinghua.lumaqq.qq.packets.in.GroupDataOpReplyPacket;
import edu.tsinghua.lumaqq.ui.MainShell;

/**
 * 下载好友分组的任务
 * 
 * @author luma
 */
public class DownloadGroupJob extends AbstractJob {    
	// 组名list和组内好友的hash，用在下载分组信息的时候，因为下载分组信息是
	// 分两部分进行的，一部分得到组名称一部分得到组的好友，光完成一部分还不
	// 行，所以需要暂时保存一下结果
	private List<String> groupNames;
	private List<DownloadFriendEntry> friends;
	
	// 分组好友是否已经下载完毕
	private boolean downloadGroupFriendFinished;

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.ui.jobs.IJob#prepare(edu.tsinghua.lumaqq.ui.MainShell)
     */
    @Override
    public void prepare(MainShell m) {
        super.prepare(m);
		downloadGroupFriendFinished = false;
        main.getClient().addQQListener(this);
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.ui.jobs.IJob#clear()
     */
    @Override
	public void clear() {
        main.getClient().removeQQListener(this);
    }
    
    @Override
    protected void preLoop() {
    	if(monitor != null) {
    		monitor.beginTask("", 100);        
    		monitor.subTask(job_download_group_1);
    	}    		
    	
		main.getDisplay().syncExec(new Runnable() {
            public void run() {
            	main.setWaitingPanelHint(hint_download_group);
            }
		});
		
		main.getClient().user_GetGroupNames();
		main.getClient().user_DownloadGroups(0);
		
		if(monitor != null)
			monitor.worked(10);
    }
    
    @Override
    protected void postLoop() {
		main.getDisplay().syncExec(new Runnable() {
            public void run() {
            	if(main.getCurrentPanel() != MainShell.PANEL_MAIN) {
            		main.stopWaitingPanelAnimation();
            		main.switchPanel(MainShell.PANEL_MAIN);            		
            	}
            }
		});
    }
    
    @Override
    protected void OnQQEvent(QQEvent e) {
        switch(e.type) {
			case QQEvent.FRIEND_GET_GROUP_NAMES_OK:
				processDownloadGroupNameSuccess(e);
				break;
			case QQEvent.FRIEND_DOWNLOAD_GROUPS_OK:
				processDownloadGroupFriendSuccess(e);
				break;
			case QQEvent.FRIEND_DOWNLOAD_GROUPS_FAIL:
				processDownloadGroupFriendFail(e);
				break;
			case QQEvent.SYS_TIMEOUT:
			    switch(e.operation) {
			        case QQ.QQ_CMD_DOWNLOAD_GROUP_FRIEND:        
			        case QQ.QQ_CMD_GROUP_DATA_OP:
			            processDownloadGroupFriendFail(e);
			        	break;
			    }
				break;
        }
    }

	/**
	 * 处理下载分组好友列表失败事件
	 * 
	 * @param e
	 * 		QQEvent
	 */
	private void processDownloadGroupFriendFail(QQEvent e) {
		groupNames = null;
		friends = null;
		errorMessage = job_download_group_error;
		wake();
	}

	/**
	 * 处理下载分组好友列表成功事件
	 * 
	 * @param e
	 * 			QQEvent
	 */
	private void processDownloadGroupFriendSuccess(QQEvent e) {		
	    if(friends == null)
		    friends = new ArrayList<DownloadFriendEntry>();
	        
		DownloadGroupFriendReplyPacket packet = (DownloadGroupFriendReplyPacket)e.getSource();		
	    friends.addAll(packet.friends);
		if(packet.beginFrom == 0) {
			downloadGroupFriendFinished = true;
			if(groupNames != null)
				resetModel();
		} else {
			downloadGroupFriendFinished = false;
			main.getClient().user_DownloadGroups(packet.beginFrom);
			
			if(monitor != null)
				monitor.worked(10);
		}
	}

	/**
	 * 处理下载分组名称成功事件
	 * 
	 * @param e
	 * 		QQEvent
	 */
	private void processDownloadGroupNameSuccess(QQEvent e) {
		GroupDataOpReplyPacket packet = (GroupDataOpReplyPacket)e.getSource();
		groupNames = packet.groupNames;
		if(downloadGroupFriendFinished && friends != null)
			resetModel();
	}
	
	/**
	 * 重新设置shutter的model
	 */
	private void resetModel() {		
		if(monitor != null)
			monitor.subTask(job_download_group_2);
	    
		downloadGroupFriendFinished = false;
		main.getBlindHelper().resetModel(groupNames, friends);
		if(monitor != null)
			monitor.worked(20);
		
		friends = null;
		groupNames = null;
		main.getDisplay().syncExec(new Runnable() {
			public void run() {
				// 得到在线好友, 设置firstTime为true是为了防止不必要的上线提示
				main.getTipHelper().setFirstTime(true);
				main.getClient().user_GetOnline();		
				// 处理延迟消息
				main.getMessageQueue().setPostpone(false);
			    main.getMessageHelper().processPostponedIM();
			}						
		});
		
		if(monitor != null)
			monitor.done();
		wake();
	}

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.ui.jobs.IJob#isSuccess()
     */
    @Override
	public boolean isSuccess() {
        return errorMessage != null;
    }
}
