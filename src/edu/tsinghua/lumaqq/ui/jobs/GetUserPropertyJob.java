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

import edu.tsinghua.lumaqq.models.ModelRegistry;
import edu.tsinghua.lumaqq.models.User;
import edu.tsinghua.lumaqq.qq.QQ;
import edu.tsinghua.lumaqq.qq.beans.UserProperty;
import edu.tsinghua.lumaqq.qq.events.QQEvent;
import edu.tsinghua.lumaqq.qq.packets.in.UserPropertyOpReplyPacket;
import edu.tsinghua.lumaqq.ui.MainShell;

/**
 * 得到用户属性的任务
 * 
 * @author luma
 */
public class GetUserPropertyJob extends AbstractJob {
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.ui.jobs.IJob#prepare(edu.tsinghua.lumaqq.ui.MainShell)
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
    
    @Override
    protected void preLoop() {
        main.getClient().user_GetProperty(QQ.QQ_POSITION_USER_PROPERTY_START);
    }
    
    @Override
    protected void postLoop() {
		if(monitor != null)
			monitor.done();
    }
    
    @Override
    protected void OnQQEvent(QQEvent e) {
        switch(e.type) {
            case QQEvent.USER_GET_PROPERTY_OK:
                processGetUserPropertySuccess(e);
            	break;
            case QQEvent.SYS_TIMEOUT:
                if(e.operation == QQ.QQ_CMD_USER_PROPERTY_OP)
                    processUserPropertyOpTimeout(e);
                break;
        }
    }

    private void processGetUserPropertySuccess(QQEvent e) {
    	if(monitor != null) {
    		if(monitor.isCanceled())
    			wake();        
    		monitor.worked(10);    		
    	}
        
        UserPropertyOpReplyPacket packet = (UserPropertyOpReplyPacket)e.getSource();
        for(UserProperty p : packet.properties) {
        	User u = ModelRegistry.getUser(p.qq);
        	if(u != null) {
        		u.hasSignature = p.hasSignature();
        		if(!u.hasSignature)
        			u.signature = "";
        		
        		u.hasCustomHead = p.hasCustomHead();
        	}
        }
        
        if(packet.finished)
        	wake();
        else
        	main.getClient().user_GetProperty(packet.startPosition);
    }
    
    /**
     * 处理下载好友备注超时事件，重发一次
     * 
     * @param e
     * 		QQEvent
     */
    private void processUserPropertyOpTimeout(QQEvent e) {
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
