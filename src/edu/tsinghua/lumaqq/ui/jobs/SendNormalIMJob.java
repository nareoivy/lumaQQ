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

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;

import edu.tsinghua.lumaqq.models.User;
import edu.tsinghua.lumaqq.qq.QQ;
import edu.tsinghua.lumaqq.qq.events.QQEvent;
import edu.tsinghua.lumaqq.resource.Colors;
import edu.tsinghua.lumaqq.ui.MainShell;
import edu.tsinghua.lumaqq.ui.SendIMWindow;
import edu.tsinghua.lumaqq.ui.helper.MessageIDGenerator;
import edu.tsinghua.lumaqq.ui.helper.NormalIMFormalizer;
import edu.tsinghua.lumaqq.widgets.rich.LineStyle;

/**
 * 发送普通消息的任务
 *
 * @author luma
 */
public class SendNormalIMJob extends AbstractJob {
	private String msg;
	private byte[] bytes;
	private User friend;
	private int totalFragments;
	private int currentFragment;
	private char messageId;
	
	public SendNormalIMJob(String msg, User u) {
		this.msg = msg;
		this.bytes = NormalIMFormalizer.formalize(msg);
		this.friend = u;
	    // 计算分片数
	    totalFragments = bytes.length / QQ.QQ_MAX_SEND_IM + 1;
	    // 初始化其他变量
	    currentFragment = -1;
	    messageId = MessageIDGenerator.getNext();
	}
	
	@Override
	public void prepare(MainShell m) {
		super.prepare(m);
		main.getClient().addQQListener(this);
	}

	@Override
	public void clear() {
		main.getClient().removeQQListener(this);
	}
	
	@Override
	protected boolean canRun() {
		return bytes.length > 0;
	}
	
	@Override
	protected void preLoop() {
		sendNextFragment();
	}
	
	@Override
	protected void postLoop() {
		if(exitCode == SUCCESS) {
			if(!friend.talkMode) {
				// 关闭聊天窗口
				main.getDisplay().asyncExec(new Runnable() {
					public void run() {
						SendIMWindow win = main.getShellRegistry().getSendIMWindow(friend);
						if(win != null)
							win.closeWindow();
					}
				});
			}
		} else if(exitCode == TIMEOUT) {
			if(friend.talkMode) {
				// 追加失败消息到输出框
				main.getDisplay().syncExec(new Runnable() {
					public void run() {
						SendIMWindow win = main.getShellRegistry().getSendIMWindow(friend);
						if(win != null)
							win.appendHint(NLS.bind(cluster_im_hint_timemout, msg), SendIMWindow.getOtherStyle());
					}						
				});	
			} else {
				// 解除输入框的不可写状态，显示提示框
				main.getDisplay().syncExec(new Runnable() {
					public void run() {
						SendIMWindow win = main.getShellRegistry().getSendIMWindow(friend);
						if(win != null) {
							win.getInputBox().setReadonly(false);
							win.getInputBox().setBackground(Colors.WHITE);
							win.setMinimized(false);
							win.setActive();
							MessageDialog.openError(win.getShell(), message_box_common_fail_title, message_box_send_message_timeout);							
						}
					}						
				});	
			}
		}
	}
	
	/**
	 * 发送下一个分片
	 */
	private void sendNextFragment() {
		currentFragment++;
		if(currentFragment >= totalFragments) {
			exitCode = SUCCESS;
			wake();
		} else {
		    LineStyle style = main.getDefaultStyle();
		    int start = currentFragment * QQ.QQ_MAX_SEND_IM;
		    byte[] b = new byte[Math.min(QQ.QQ_MAX_SEND_IM, bytes.length - start)];
		    System.arraycopy(bytes, start, b, 0, b.length);
		    main.getClient().im_Send(
		            friend.qq,
		            b,
		            messageId, 
		            totalFragments,
		            currentFragment,
		            style.fontName,
		            (style.fontStyle & SWT.BOLD) != 0,
		            (style.fontStyle & SWT.ITALIC) != 0,
		            false,
		            style.fontSize,
		            style.foreground.getRed(),
		            style.foreground.getGreen(),
		            style.foreground.getBlue(),
		            QQ.QQ_IM_NORMAL_REPLY);
		}
	}
	
	@Override
	protected void OnQQEvent(QQEvent e) {
		switch(e.type) {
			case QQEvent.IM_SEND_OK:
				processSendIMSuccess(e);
		        break;
			case QQEvent.SYS_TIMEOUT:
				if(e.operation == QQ.QQ_CMD_SEND_IM)
					processSendIMTimeout(e);
				break;
		}
	}

	private void processSendIMTimeout(QQEvent e) {
		exitCode = TIMEOUT;
		wake();
	}

	private void processSendIMSuccess(QQEvent e) {
		sendNextFragment();
	}
}
