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

import edu.tsinghua.lumaqq.ecore.remark.Remark;
import edu.tsinghua.lumaqq.ecore.remark.RemarkFactory;
import edu.tsinghua.lumaqq.models.ModelRegistry;
import edu.tsinghua.lumaqq.models.User;
import edu.tsinghua.lumaqq.qq.QQ;
import edu.tsinghua.lumaqq.qq.beans.FriendRemark;
import edu.tsinghua.lumaqq.qq.events.QQEvent;
import edu.tsinghua.lumaqq.qq.packets.in.FriendDataOpReplyPacket;
import edu.tsinghua.lumaqq.ui.MainShell;

/**
 * 下载好友备注的任务
 * 
 * @author luma
 */
public class DownloadFriendRemarkJob extends AbstractJob {	
	// 备注工具类
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
    	if(monitor != null)
    		monitor.beginTask("", 100);    		
        
    	if(monitor != null)
    		monitor.subTask(job_download_remark_1);        
        main.getClient().user_GetRemarks(page++);
    }
    
    @Override
    protected void postLoop() {
		if(monitor != null)
			monitor.subTask(job_download_remark_2);
		main.getConfigHelper().saveRemarks();
		if(monitor != null)
			monitor.done();
    }

    @Override
    protected void OnQQEvent(QQEvent e) {
        switch(e.type) {
            case QQEvent.FRIEND_GET_REMARKS_OK:
                processBatchDownloadFriendRemarkSuccess(e);
            	break;
            case QQEvent.SYS_TIMEOUT:
                if(e.operation == QQ.QQ_CMD_FRIEND_DATA_OP)
                    processFriendDataOpTimeout(e);
                break;
        }
    }
    
    /**
     * 处理下载好友备注成功事件
     * @param e
     */
    private void processBatchDownloadFriendRemarkSuccess(QQEvent e) {
    	if(monitor != null) {
    		if(monitor.isCanceled())
    			wake();        
    		monitor.worked(10);    		
    	}
        
        FriendDataOpReplyPacket packet = (FriendDataOpReplyPacket)e.getSource();
        if(packet.hasRemark) {
			for(Integer qq : packet.remarks.keySet()) {
                FriendRemark fr = packet.remarks.get(qq);
                Remark remark = createRemarkElement(qq, fr);
    	        main.getConfigHelper().putRemark(remark);
    	        User u = ModelRegistry.getUser(remark.getQq());
    	        if(u != null)
    	        	u.remark = remark;
            }	        
            main.getClient().user_GetRemarks(page++);
        } else {
            wake();
        }
    }
    
    /**
     * 处理下载好友备注超时事件，重发一次
     * 
     * @param e
     * 		QQEvent
     */
    private void processFriendDataOpTimeout(QQEvent e) {
		wake();
    }
    
	/**
	 * 从FriendRemarkOpReplyPacket中创建Remark对象
	 * 
	 * @return
	 * 		Remark元素对象
	 */
	private Remark createRemarkElement(int qqNum, FriendRemark fr) {		
		Remark remark = RemarkFactory.eINSTANCE.createRemark();
		remark.setName(fr.name.trim());
		remark.setZipcode(fr.zipcode);
		remark.setTelephone(fr.telephone);
		remark.setMobile(fr.mobile);
		remark.setEmail(fr.email);
		remark.setAddress(fr.address);
		remark.setNote(fr.note);
		remark.setQq(qqNum);
		return remark;
	}

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.ui.jobs.IJob#isSuccess()
     */
    @Override
	public boolean isSuccess() {
        return errorMessage != null;
    }
}
