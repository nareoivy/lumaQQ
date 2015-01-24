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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import edu.tsinghua.lumaqq.models.ModelRegistry;
import edu.tsinghua.lumaqq.models.User;
import edu.tsinghua.lumaqq.qq.QQ;
import edu.tsinghua.lumaqq.qq.beans.FriendLevel;
import edu.tsinghua.lumaqq.qq.events.QQEvent;
import edu.tsinghua.lumaqq.qq.packets.in.FriendLevelOpReplyPacket;
import edu.tsinghua.lumaqq.ui.MainShell;

/**
 * 得到用户等级的任务
 * 
 * @author luma
 */
public class GetFriendLevelJob extends AbstractJob {	
	private List<Integer> friends;

	private static final int MAX = 70;
	private int page;
	
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.ui.jobs.IJob#prepare(edu.tsinghua.lumaqq.ui.MainShell)
     */
    @Override
    public void prepare(MainShell m) {
        super.prepare(m);
        page = 0;
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
        friends = new ArrayList<Integer>();
        Iterator<User> i = ModelRegistry.getUserIterator();
        while(i.hasNext()) {
        	friends.add(i.next().qq);
        }
        // 排序
        Collections.sort(friends, new Comparator<Integer>() {
        	public int compare(Integer o1, Integer o2) {
        		return o1 - o2;
        	}
        });
        request(page);
    }
    
    @Override
    protected void postLoop() {
		if(monitor != null)
			monitor.done();
    }

    private void request(int p) {
    	List<Integer> subList = friends.subList(Math.min(p * MAX, friends.size()), Math.min(p * MAX + MAX, friends.size()));
    	if(subList.isEmpty())
    		wake();
    	else
    		main.getClient().user_GetLevel(subList);
	}
    
    @Override
    protected void OnQQEvent(QQEvent e) {
        switch(e.type) {
            case QQEvent.FRIEND_GET_LEVEL_OK:
                processGetFriendLevelSuccess(e);
            	break;
            case QQEvent.SYS_TIMEOUT:
                if(e.operation == QQ.QQ_CMD_FRIEND_LEVEL_OP)
                    processFriendLevelOpTimeout(e);
                break;
        }
    }

    private void processGetFriendLevelSuccess(QQEvent e) {
    	if(monitor != null) {
    		if(monitor.isCanceled())
    			wake();        
    		monitor.worked(10);    		
    	}
    	
    	FriendLevelOpReplyPacket packet = (FriendLevelOpReplyPacket)e.getSource();
    	for(FriendLevel level : packet.friendLevels) {
    		User u = ModelRegistry.getUser(level.qq);
    		if(u != null) {
    			u.level = level.level;
    			if(u.qq == main.getMyModel().qq)
    				main.getMyModel().level = level.level;
    		}
    	}
    	
    	page++;
    	request(page);
    }
    
    /**
     * 处理下载好友备注超时事件，重发一次
     * 
     * @param e
     * 		QQEvent
     */
    private void processFriendLevelOpTimeout(QQEvent e) {
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
