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
import edu.tsinghua.lumaqq.qq.beans.Signature;
import edu.tsinghua.lumaqq.qq.events.QQEvent;
import edu.tsinghua.lumaqq.qq.packets.in.SignatureOpReplyPacket;
import edu.tsinghua.lumaqq.ui.MainShell;

/**
 * 下载个性签名的任务
 * 
 * @author luma
 */
public class DownloadSignatureJob extends AbstractJob {	
	private List<User> users;
	private List<Signature> sigs;
	private static final int MAX = 30;

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
        sigs = new ArrayList<Signature>();
        users = new ArrayList<User>();        
        Iterator<User> i = ModelRegistry.getUserIterator();
        while(i.hasNext()) {
        	User u = i.next();
        	if(u.hasSignature)
        		users.add(u);
        }
        Collections.sort(users, new Comparator<User>() {
        	public int compare(User o1, User o2) {
        		return o1.qq - o2.qq;
        	}
        });
        request(0);
    }
    
    @Override
    protected void postLoop() {
		main.getDisplay().syncExec(new Runnable() {
			public void run() {
				main.getBlindHelper().refreshAll();
			}
		});
    }

    /**
     * 请求某页的个性签名
     * 
     * @param next 
     */
    private void request(int next) {
    	sigs.clear();
    	int start = findStart(next);
    	if(start == -1)
    		wake();
    	else {
    		int end = Math.min(start + MAX, users.size());
    		for(int i = start; i < end; i++) {
    			User u = users.get(i);
    			Signature s = new Signature();
    			s.modifiedTime = u.signatureModifiedTime;
    			s.qq = u.qq;
    			sigs.add(s);
    		}
    		main.getClient().user_GetSignature(sigs);    		
    	}
	}
    
    private int findStart(int next) {
    	int i = 0;
    	for(User u : users) {
    		if(u.qq >= next)
    			return i;
    		else
    			i++;
    	}
    	return -1;
    }
    
    @Override
    protected void OnQQEvent(QQEvent e) {
        switch(e.type) {
            case QQEvent.USER_GET_SIGNATURE_OK:
                processGetSignatureSuccess(e);
            	break;
            case QQEvent.SYS_TIMEOUT:
                if(e.operation == QQ.QQ_CMD_SIGNATURE_OP)
                    processSignatureOpTimeout(e);
                break;
        }
    }
    
    /**
     * 处理下载好友备注成功事件
     * @param e
     */
    private void processGetSignatureSuccess(QQEvent e) {        
        SignatureOpReplyPacket packet = (SignatureOpReplyPacket)e.getSource();
        for(Signature sig : packet.signatures) {
        	User u = ModelRegistry.getUser(sig.qq);
        	if(u != null) {
        		u.signature = sig.sig;
        		if(u.qq == main.getMyModel().qq)
        			main.getMyModel().signature = sig.sig;
        	}
        }
        
      	request(packet.nextQQ);
    }
    
    /**
     * 处理下载好友备注超时事件，重发一次
     * 
     * @param e
     * 		QQEvent
     */
    private void processSignatureOpTimeout(QQEvent e) {
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
