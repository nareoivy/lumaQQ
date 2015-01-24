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

import static edu.tsinghua.lumaqq.resource.Messages.job_upload_group_1;
import static edu.tsinghua.lumaqq.resource.Messages.job_upload_group_error;

import java.util.List;

import edu.tsinghua.lumaqq.models.Group;
import edu.tsinghua.lumaqq.models.User;
import edu.tsinghua.lumaqq.qq.QQ;
import edu.tsinghua.lumaqq.qq.QQClient;
import edu.tsinghua.lumaqq.qq.events.QQEvent;
import edu.tsinghua.lumaqq.qq.packets.out.UploadGroupFriendPacket;
import edu.tsinghua.lumaqq.ui.MainShell;
import edu.tsinghua.lumaqq.ui.helper.BlindHelper;

/**
 * 上传分组任务
 * 
 * @author luma
 */

public class UploadGroupJob extends AbstractJob {
	private boolean uploadNameFinished, uploadFriendFinished;
	
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.ui.jobs.IJob#prepare(edu.tsinghua.lumaqq.ui.MainShell)
     */
    @Override
    public void prepare(MainShell m) {
        super.prepare(m);
        uploadNameFinished = false;
        uploadFriendFinished = false;
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
        monitor.beginTask("", 100);
        monitor.subTask(job_upload_group_1);
        
        QQClient client = main.getClient();
		// 组装上传分组名称包，注意i从1开始，因为我的好友组是缺省的，不需要上传
		// 同时组装上传分组好友列表包
        BlindHelper helper = main.getBlindHelper();
		List<String> groups = helper.getUploadGroupNameList();
		UploadGroupFriendPacket ugfPacket = new UploadGroupFriendPacket(main.getClient().getUser());
		int size = helper.getUploadGroupCount();
		for(int i = 0; i < size; i++) {
			Group g = helper.getGroup(i);
			if(g == null)
				continue;
			for(User u : g.users)
				ugfPacket.addFriend(i, u.qq);
		}
		
		monitor.worked(10);
		
		// 发送
		client.user_UploadGroupNames(groups);
		client.sendPacket(ugfPacket);
		
		monitor.worked(20);
    }
    
    @Override
    protected void postLoop() {
		main.setGroupDirty(false);
		
		monitor.done();
    }

	/**
	 * 处理上传分组好友列表失败事件
	 * 
	 * @param e
	 * 			QQEvent
	 */
	private void processUploadFail(QQEvent e) {
	    uploadFriendFinished = uploadNameFinished = true;
	    errorMessage = job_upload_group_error;
	    wake();
	}

	/**
	 * 处理上传分组好友列表成功事件
	 * 
	 * @param e
	 * 		QQEvent
	 */
	private void processUploadGroupFriendSuccess(QQEvent e) {
		monitor.worked(20);
	    uploadFriendFinished = true;
	    if(uploadNameFinished)
	    	wake();
	}

    /**
     * @param e
     */
    private void processUploadGroupNameSuccess(QQEvent e) {
		monitor.worked(20);
        uploadNameFinished = true;
        if(uploadFriendFinished)
        	wake();
    }
    
    @Override
    protected void OnQQEvent(QQEvent e) {
        switch(e.type) {
			case QQEvent.FRIEND_UPLOAD_GROUPS_FAIL:
				processUploadFail(e);
				break;
			case QQEvent.FRIEND_UPLOAD_GROUPS_OK:
				processUploadGroupFriendSuccess(e);
				break;
			case QQEvent.FRIEND_UPDATE_GROUP_NAMES_OK:
			    processUploadGroupNameSuccess(e);
				break;
			case QQEvent.SYS_TIMEOUT:
			    switch(e.operation) {
			        case QQ.QQ_CMD_UPLOAD_GROUP_FRIEND:
			        case QQ.QQ_CMD_GROUP_DATA_OP:
						processUploadFail(e);
			        	break;
			    }
				break;
        }
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.ui.jobs.IJob#isSuccess()
     */
    @Override
	public boolean isSuccess() {
        return errorMessage != null;
    }
}
