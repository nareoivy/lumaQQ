/*
* LumaQQ - Java QQ Client
*
* Copyright (C) 2004 luma <stubma@163.com>
* 					 糊涂虫 <jtdeng518@163.com>
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

import static edu.tsinghua.lumaqq.resource.Messages.hint_get_friend;
import static edu.tsinghua.lumaqq.resource.Messages.job_get_friend_list;
import edu.tsinghua.lumaqq.models.GroupType;
import edu.tsinghua.lumaqq.models.ModelUtils;
import edu.tsinghua.lumaqq.models.User;
import edu.tsinghua.lumaqq.qq.QQ;
import edu.tsinghua.lumaqq.qq.beans.QQFriend;
import edu.tsinghua.lumaqq.qq.events.QQEvent;
import edu.tsinghua.lumaqq.qq.packets.OutPacket;
import edu.tsinghua.lumaqq.qq.packets.in.GetFriendListReplyPacket;
import edu.tsinghua.lumaqq.ui.MainShell;

/**
 * 得到好友列表的任务
 * 
 * @author luma
 */
public class GetFriendListJob extends AbstractJob {	
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.ui.jobs.AbstractJob#prepare(edu.tsinghua.lumaqq.ui.MainShell)
     */
    @Override
    public void prepare(MainShell m) {
        super.prepare(m);
        main.getClient().addQQListener(this);
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.ui.jobs.IJob#clear()
     */
    @Override
	public void clear() {
        main.getClient().removeQQListener(this);
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.ui.jobs.IJob#isSuccess()
     */
    @Override
	public boolean isSuccess() {
        return errorMessage != null;
    }
    
    @Override
    protected void preLoop() {
    	if(monitor != null) {
    		monitor.beginTask("", 100);
    		monitor.subTask(job_get_friend_list);    		
    	}
    	
		main.getDisplay().syncExec(new Runnable() {
            public void run() {
            	main.setWaitingPanelHint(hint_get_friend);
            }
		});
        
        main.getClient().user_GetList();
    }
    
    @Override
    protected void postLoop() {
		main.getDisplay().syncExec(new Runnable() {
            public void run() {
        		main.getBlindHelper().refreshAll();
            }
		});
		
		if(monitor != null)
			monitor.done();
    }
    
	/**
	 * 处理得到好友列表成功事件，我们得到每一个好友，然后生成model对象，添加到总的model中
	 * 如果事件是get friend list end事件，还需要处理postponed message
	 * 
	 * @param e
	 * 		QQEvent
	 */
	private void processGetFriendListSuccess(QQEvent e) {	
		if(monitor != null)
			monitor.worked(15);
	    
		GetFriendListReplyPacket packet = (GetFriendListReplyPacket)e.getSource();	
		for(QQFriend friend : packet.friends) {
			User u = ModelUtils.createUser(friend);
			u.remark = main.getConfigHelper().getRemark(u.qq);
			main.getBlindHelper().addUser(u, GroupType.DEFAULT_FRIEND_GROUP);	
		}
		
		// 如果是得到好友列表结束事件，返回
		if(packet.position == QQ.QQ_POSITION_FRIEND_LIST_END)
		    wake();
		else
		    main.getClient().user_GetList(packet.position);
	}
	
	@Override
	protected void OnQQEvent(QQEvent e) {
        switch(e.type) {
            case QQEvent.FRIEND_GET_LIST_OK:
                processGetFriendListSuccess(e);
                break;
			case QQEvent.SYS_TIMEOUT:
			    switch(e.operation) {
			        case QQ.QQ_CMD_GET_FRIEND_LIST:
						processGetFriendListTimeout(e);
			        	break;
			    }
				break;
        }
	}

	/**
	 * 处理得到好友列表超时事件，我们简单得重发超时的包
	 * 
	 * @param e
	 * 		QQEvent
	 */
	private void processGetFriendListTimeout(QQEvent e) {
		main.getClient().sendPacket((OutPacket)e.getSource());
	}
}
