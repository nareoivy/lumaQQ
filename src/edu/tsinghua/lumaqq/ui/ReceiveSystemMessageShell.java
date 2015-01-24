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

import static edu.tsinghua.lumaqq.resource.Messages.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import edu.tsinghua.lumaqq.events.BaseQQListener;
import edu.tsinghua.lumaqq.models.Cluster;
import edu.tsinghua.lumaqq.models.ModelRegistry;
import edu.tsinghua.lumaqq.models.User;
import edu.tsinghua.lumaqq.qq.QQ;
import edu.tsinghua.lumaqq.qq.events.QQEvent;
import edu.tsinghua.lumaqq.qq.packets.InPacket;
import edu.tsinghua.lumaqq.qq.packets.in.AddFriendExReplyPacket;
import edu.tsinghua.lumaqq.qq.packets.in.AuthorizeReplyPacket;
import edu.tsinghua.lumaqq.qq.packets.in.ClusterCommandReplyPacket;
import edu.tsinghua.lumaqq.qq.packets.in.ReceiveIMPacket;
import edu.tsinghua.lumaqq.qq.packets.in.SystemNotificationPacket;
import edu.tsinghua.lumaqq.qq.packets.out.AddFriendAuthResponsePacket;
import edu.tsinghua.lumaqq.qq.packets.out.ClusterCommandPacket;
import edu.tsinghua.lumaqq.record.IKeyConstants;
import edu.tsinghua.lumaqq.record.RecordEntry;
import edu.tsinghua.lumaqq.resource.Resources;
import edu.tsinghua.lumaqq.ui.config.user.UserInfoWindow;
import edu.tsinghua.lumaqq.widgets.qstyle.Slat;

/**
 * 查看系统消息窗口，这个窗口不光要查看系统的消息，还要负责查看群的通知消息，但是其
 * 基本结构还是差不多，都要是验证，拒绝之类的，不过由于消息的类型可能不同，一些控制
 * 逻辑是不可避免的，也许看起来有点晕。
 * 
 * @author luma
 */
public class ReceiveSystemMessageShell extends BaseQQListener {
	// Log对象
    protected static Log log = LogFactory.getLog(ReceiveSystemMessageShell.class);
    // 窗口的打开模式，有查看模式，删除模式和添加模式
    public static final int VIEW_MODE = 0;
    public static final int DELETE_MODE = 1;
    public static final int ADD_MODE = 2;
    
    private MainShell main;
	private Shell shell;
	private Display display;
	private Label lblHint;
	private Text textQQ, textNick, textMsg;
	private Slat btnApprove, btnReject, btnAdd;
	private Slat btnUserInfo;
	private Resources res = Resources.getInstance();
	// 头像id
	private int headId;
	// 查看的号码和发送认证消息的目的号码
	private int infoQQ, authQQ;
	// qqNum是用户还是群的标志
	private boolean isCluster;
	// 发送用户验证还是群验证
	private boolean isClusterAuth;
	// 系统消息类型
	private byte type;
	// 是否在拒绝请求的第二步
	private boolean rejectSecondStep;
	// 是否在添加好友的第二步
	private boolean addSecondStep;
	// 动画帧
	private Image[] frames;
	
	public ReceiveSystemMessageShell(MainShell main) {
		this.main = main;
		this.display = main.getDisplay();
		shell = new Shell(display, SWT.TITLE | SWT.CLOSE | SWT.MIN);
		shell.setText(receive_system_message_title);
		shell.setImage(res.getImage(Resources.icoLumaQQ));
		// 添加事件监听器
		shell.addShellListener(new ShellAdapter() {
			@Override
			public void shellClosed(ShellEvent e) {
				OnShellClosed(e);
			}
		});
		
		// 初始化变量
		rejectSecondStep = false;
		addSecondStep = false;
		isCluster = false;
		isClusterAuth = false;
		initAnimateFrames(0);
		
		initLayout();
	}
	
	protected void OnShellClosed(ShellEvent e) {
		main.getClient().removeQQListener(this);
	}

	// 初始化其他控件
	private void initLayout() {
		shell.setLayout(new GridLayout());
		
		Group from = new Group(shell, SWT.SHADOW_ETCHED_IN);
		from.setText(receive_system_message_from);
		from.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		GridLayout layout = new GridLayout(6, false);
		layout.marginHeight = 0;
		layout.marginWidth = 3;
		from.setLayout(layout);
		// qq号标签
		Label lblQQ = new Label(from, SWT.NONE);
		lblQQ.setText(receive_system_message_from_qq);
		lblQQ.setLayoutData(new GridData());
		// qq号文本框
		textQQ = new Text(from, SWT.SINGLE | SWT.READ_ONLY | SWT.BORDER);
		textQQ.setBackground(from.getBackground());
		GridData gd = new GridData();
		gd.widthHint = 80;
		textQQ.setLayoutData(gd);
		// 昵称标签
		Label lblNick = new Label(from, SWT.NONE);
		lblNick.setText(receive_system_message_from_nick);
		lblNick.setLayoutData(new GridData());
		// 昵称文本框
		textNick = new Text(from, SWT.SINGLE | SWT.READ_ONLY | SWT.BORDER);
		textNick.setBackground(from.getBackground());
		gd = new GridData();
		gd.widthHint = 80;
		textNick.setLayoutData(gd);
		// 查看详细资料标签
		Label lblSeeDetail = new Label(from, SWT.NONE);
		lblSeeDetail.setText(receive_system_message_from_seedetail);
		lblSeeDetail.setLayoutData(new GridData());
		// 查看用户资料按钮
		btnUserInfo = new Slat(from, SWT.CENTER | SWT.FLAT, null, res.getHead(0));
		gd = new GridData();
		gd.widthHint = gd.heightHint = 48;
		btnUserInfo.setLayoutData(gd);
		btnUserInfo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				if(isCluster) {
					Cluster c = ModelRegistry.getCluster(infoQQ);
					if(c == null) {
						c = new Cluster();
						c.clusterId = infoQQ;						
					}
					main.getShellLauncher().openClusterInfoWindow(c);
					main.getClient().cluster_GetInfo(infoQQ);
				} else {
					User f = new User();
					f.qq = infoQQ;
					main.getShellLauncher().openUserInfoWindow(f, UserInfoWindow.READ_ONLY);
					main.getClient().user_GetInfo(infoQQ);						
				}
			}
		});
		// 消息内容标签或者拒绝理由提示标签
		lblHint = new Label(shell, SWT.NONE);
		lblHint.setText(receive_system_message_content_label);
		lblHint.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		// 消息内容文本框
		textMsg = new Text(shell, SWT.MULTI | SWT.READ_ONLY | SWT.BORDER | SWT.WRAP);
		textMsg.setBackground(shell.getBackground());
		textMsg.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		// 按钮容器
		Composite buttonComposite = new Composite(shell, SWT.NONE);
		layout = new GridLayout(4, false);
		layout.marginHeight = layout.marginWidth = 0;
		layout.horizontalSpacing = 3;
		buttonComposite.setLayout(layout);
		buttonComposite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		// 通过验证按钮
		btnApprove = new Slat(buttonComposite, SWT.NONE);
		btnApprove.setText(receive_system_message_button_approve);
		gd = new GridData();
		gd.widthHint = 80;		
		btnApprove.setLayoutData(gd);
		btnApprove.addMouseListener(
			new MouseAdapter() {
				@Override
				public void mouseUp(MouseEvent e) {
					textMsg.setEditable(false);
					textMsg.setBackground(shell.getBackground());
					btnApprove.setEnabled(false);
					btnReject.setEnabled(false);
					btnAdd.setEnabled(false);
					btnUserInfo.startAnimate(frames);
					if(isClusterAuth)
						main.getClient().cluster_ApproveJoin(authQQ, infoQQ);
					else
						main.getClient().user_ApproveAdd(authQQ);
				}
			}
		);
		// 拒绝请求按钮
		btnReject = new Slat(buttonComposite, SWT.NONE);
		btnReject.setText(receive_system_message_button_reject);
		gd = new GridData();
		gd.widthHint = 80;		
		btnReject.setLayoutData(gd);
		btnReject.addMouseListener(
			new MouseAdapter() {
				@Override
				public void mouseUp(MouseEvent e) {
					if(rejectSecondStep) {
						/* 拒绝的第二步是发送拒绝的信息 */
						textMsg.setEditable(false);
						textMsg.setBackground(shell.getBackground());
						btnApprove.setEnabled(false);
						btnReject.setEnabled(false);
						btnAdd.setEnabled(false);
						btnUserInfo.startAnimate(frames);
						if(isClusterAuth)
							main.getClient().cluster_RejectJoin(authQQ, infoQQ, textMsg.getText());
						else
							main.getClient().user_RejectAdd(authQQ, textMsg.getText());
					} else {
						/* 拒绝的第一步是提示用户输入拒绝理由 */
						textMsg.setEditable(true);
						textMsg.setBackground(main.getDisplay().getSystemColor(SWT.COLOR_WHITE));
						textMsg.setText("");
						textMsg.setFocus();
						lblHint.setText(receive_system_message_reject_label);						
						rejectSecondStep = true;
						addSecondStep = false;
					}
				}
			}
		);
		// 加为好友按钮
		btnAdd = new Slat(buttonComposite, SWT.NONE);
		btnAdd.setText(receive_system_message_button_add);
		gd = new GridData(GridData.HORIZONTAL_ALIGN_END);
		gd.grabExcessHorizontalSpace = true;
		gd.widthHint = 80;		
		btnAdd.setLayoutData(gd);
		btnAdd.addMouseListener(
			new MouseAdapter() {
				@Override
				public void mouseUp(MouseEvent e) {
					if(addSecondStep) {
						/* 第二步表示发送验证信息，这一步只会在对方需要验证时出现 */
						btnApprove.setEnabled(false);
						btnReject.setEnabled(false);
						btnAdd.setEnabled(false);
						textMsg.setEditable(false);
						textMsg.setBackground(shell.getBackground());
						main.getClient().user_SendAuth(authQQ, textMsg.getText());
					} else {
						/* 第一步是直接发送添加请求，如果对方不需要验证，则直接成功 */
						textMsg.setEditable(false);
						textMsg.setBackground(shell.getBackground());
						textMsg.setText(NLS.bind(receive_system_message_adding, String.valueOf(authQQ)));
						btnApprove.setEnabled(false);
						btnReject.setEnabled(false);						
						btnAdd.setEnabled(false);
						main.getClient().user_Add(authQQ);						
					}
					btnUserInfo.startAnimate(frames);
				}
			}
		);
		// 关闭按钮
		Slat btnClose = new Slat(buttonComposite, SWT.NONE);
		btnClose.setText(button_close);
		gd = new GridData();
		gd.widthHint = 80;		
		btnClose.setLayoutData(gd);
		btnClose.addMouseListener(
			new MouseAdapter() {
				@Override
				public void mouseUp(MouseEvent e) {
					close();
				}
			}
		);
	}
	
	/**
	 * 设置当前窗口运行在删除好友模式
	 */
	private void setDeleteMode() {
		btnAdd.setVisible(false);
		btnApprove.setVisible(false);
		btnReject.setVisible(false);
		textMsg.setText(receive_system_message_deleting);
		btnUserInfo.startAnimate(frames);
	}
	
	/**
	 * 设置当前窗口运行在添加好友模式
	 */
	private void setAddMode() {
		btnAdd.setVisible(false);
		btnApprove.setVisible(false);
		btnReject.setVisible(false);
		textMsg.setText(NLS.bind(receive_system_message_adding, String.valueOf(authQQ)));
		btnUserInfo.startAnimate(frames);
	}
	
	/**
	 * 初始化动画帧
	 * @param head
	 */
	private void initAnimateFrames(int head) {
		frames = new Image[] { res.getHead(head), res.getHead(head + 2) };
	}
	
	/**
	 * 设置头像号
	 * @param headId
	 */
	public void setHeadId(int h) {
		headId = h - h % 3;
		btnUserInfo.setImage(res.getHead(headId));
		initAnimateFrames(headId);
	}
	
	/**
	 * 设置好友的model
	 * @param f
	 */
	public void setFriendModel(User f) {
		setQQ(f.qq, f.qq, String.valueOf(f.qq));
		setHeadId(f.headId);
		setNick(f.displayName);
	}
	
	/**
	 * 缺省打开为查看消息模式
	 */
	public void open() {
		open(VIEW_MODE);
	}

	// 打开shell
	public void open(int mode)	{
		// 设置打开模式
		if(mode == DELETE_MODE)
			setDeleteMode();
		else if(mode == ADD_MODE)
			setAddMode();
		
		// 打开shell
		shell.pack();
		Point size = shell.getSize();
		size.x = Math.max(400, size.x);
		size.y = Math.max(250, size.y);
		shell.setSize(size);
		shell.open();
		main.getClient().addQQListener(this);
	}
	
	/**
	 * 是当前窗口激活
	 */
	public void setActive() {
		shell.setActive();
	}	
	
	/**
	 * 代理方法，设置窗口的最小化状态
	 * @param b
	 */
	public void setMinimized(boolean b) {
		shell.setMinimized(b);
	}
	
	/**
	 * 设置要显示的系统消息
	 * @param in
	 */
	public void setSystemMessage(InPacket in) {
		if(in instanceof SystemNotificationPacket)
			setSystemMessage((SystemNotificationPacket)in);
		else if(in instanceof ReceiveIMPacket)
			setSystemMessage((ReceiveIMPacket)in);
	}
	
	private void setSystemMessage(ReceiveIMPacket packet) {
		switch(packet.header.type) {
			case QQ.QQ_RECV_IM_SYS_MESSAGE:
				setSystemMessage(10000, 
						0, 
						packet.sysMessage,
						false,
						false,
						false,
						false,
						false);
				break;
			case QQ.QQ_RECV_IM_ADDED_TO_CLUSTER:
			case QQ.QQ_RECV_IM_CREATE_CLUSTER:
			case QQ.QQ_RECV_IM_APPROVE_JOIN_CLUSTER:
			case QQ.QQ_RECV_IM_REJECT_JOIN_CLUSTER:
			case QQ.QQ_RECV_IM_CLUSTER_NOTIFICATION:
				setSystemMessage(packet.header.sender,
						0,
						packet.message,
						true,
						false,
						false,
						false,
						false);
				break;
			case QQ.QQ_RECV_IM_DELETED_FROM_CLUSTER:
				if(packet.sender == main.getMyModel().qq) 
					setSystemMessage(packet.header.sender,
							0,
							packet.message,
							true,
							false,
							false,
							false,
							false);
				else
					setSystemMessage(packet.sender,
							0,
							packet.message,
							false,
							false,
							false,
							false,
							false);
				break;
			case QQ.QQ_RECV_IM_REQUEST_JOIN_CLUSTER:
				setSystemMessage(packet.sender,
						packet.header.sender,
						packet.message,
						false,
						true,
						true,
						true,
						false);
				break;
		}
	}
	
	private void setSystemMessage(SystemNotificationPacket packet) {
		switch(packet.type) {
			case QQ.QQ_SYS_BEING_ADDED:
			case QQ.QQ_SYS_BEING_ADDED_EX:
				setSystemMessage(packet.from, 
						packet.from,
						packet.message,
						false,
						false,
						false,
						false,
						true);
				break;
			case QQ.QQ_SYS_ADD_FRIEND_REQUEST:
			case QQ.QQ_SYS_ADD_FRIEND_REQUEST_EX:
				setSystemMessage(packet.from,
						packet.from,
						packet.message,
						false,
						false,
						true,
						true,
						true);
				break;
			case QQ.QQ_SYS_ADD_FRIEND_APPROVED:
			case QQ.QQ_SYS_ADD_FRIEND_REJECTED:
			case QQ.QQ_SYS_ADD_FRIEND_APPROVED_AND_ADD:
				setSystemMessage(packet.from,
						packet.from,
						packet.message,
						false,
						false,
						false,
						false,
						false);
				break;
		}
	}
	
	/**
	 * 设置要显示的系统消息
	 * 
	 * @param entry
	 * 		记录内容
	 */
	public void setSystemMessage(RecordEntry entry) {
		switch(entry.subType) {
			case IKeyConstants.SUB_BEING_ADDED:
			case IKeyConstants.SUB_BEING_ADDED_EX:
				setSystemMessage(entry.sender, 
						0,
						entry.message,
						false,
						false,
						false,
						false,
						true);
				break;
			case IKeyConstants.SUB_ADD_FRIEND_REQUEST:
			case IKeyConstants.SUB_ADD_FRIEND_REQUEST_EX:
				setSystemMessage(entry.sender,
						entry.sender,
						entry.message,
						false,
						false,
						true,
						true,
						true);
				break;
			case IKeyConstants.SUB_ADD_FRIEND_APPROVED:
			case IKeyConstants.SUB_ADD_FRIEND_REJECTED:
			case IKeyConstants.SUB_ADD_FRIEND_APPROVED_AND_ADD:
				setSystemMessage(entry.sender,
						0,
						entry.message,
						false,
						false,
						false,
						false,
						false);
				break;
			case IKeyConstants.SUB_ADDED_TO_CLUSTER:
			case IKeyConstants.SUB_CREATE_CLUSTER:
			case IKeyConstants.SUB_APPROVE_JOIN_CLUSTER:
			case IKeyConstants.SUB_REJECT_JOIN_CLUSTER:
			case IKeyConstants.SUB_CLUSTER_NOTIFICATION_SET_ADMIN:
			case IKeyConstants.SUB_CLUSTER_NOTIFICATION_UNSET_ADMIN:
				setSystemMessage(entry.sender,
						0,
						entry.message,
						true,
						false,
						false,
						false,
						false);
				break;
			case IKeyConstants.SUB_DELETED_FROM_CLUSTER:
				if(entry.receiver == main.getMyModel().qq) 
					setSystemMessage(entry.sender,
							0, 
							entry.message,
							true,
							false,
							false,
							false,
							false);
				else
					setSystemMessage(entry.senderParent,
							0,
							entry.message,
							false,
							false,
							false,
							false,
							false);
				break;
			case IKeyConstants.SUB_REQUEST_JOIN_CLUSTER:		
				setSystemMessage(entry.sender,
						entry.senderParent,
						entry.message,
						false,
						true,
						true,
						true,
						false);
				break;
		}
	}
	
	/**
	 * 设置系统消息参数
	 * 
	 * @param infoFrom
	 * 		应该查看谁的消息
	 * @param authTo
	 * 		验证消息应该发送给谁
	 * @param message
	 * 		消息的内容
	 * @param isCluster
	 * 		infoFrom是不是群
	 * @param isClusterAuth
	 * 		是不是群验证
	 * @param approveVisible
	 * 		通过验证按钮的可见性
	 * @param rejectVisible
	 * 		拒绝验证按钮的可见性
	 * @param addVisible
	 * 		添加好友按钮的可见性
	 */
	public void setSystemMessage(int infoFrom, 
			int authTo, 
			String message, 
			boolean isCluster,
			boolean isClusterAuth,
			boolean approveVisible,
			boolean rejectVisible,
			boolean addVisible) {
		setQQ(infoFrom, authTo, String.valueOf(infoFrom));
		if(isCluster) {
			Cluster c = ModelRegistry.getCluster(infoFrom);
			if(c == null || c.name.equals(""))
				setNick(String.valueOf(infoFrom));
			else
				setNick(c.name);
			if(c != null && c.externalId != 0)
				textQQ.setText(String.valueOf(c.externalId));
		} else {
			User u = ModelRegistry.getUser(infoFrom);
			if(u == null || u.displayName.equals(""))
				setNick(String.valueOf(infoFrom));
			else
				setNick(u.displayName);
		}
		this.isCluster = isCluster;
		this.isClusterAuth = isClusterAuth;
		btnApprove.setVisible(approveVisible);
		btnReject.setVisible(rejectVisible);
		btnAdd.setVisible(addVisible);
		textMsg.setText(message);
	}
	
	/**
	 * 设置QQ号
	 */
	public void setQQ(int infoQQ, int authQQ, String qq) {
		this.infoQQ = infoQQ;
		this.authQQ = authQQ;
		textQQ.setText(qq);
	}
	
	/**
	 * 设置昵称
	 * @param nick
	 */
	public void setNick(String nick) {
		textNick.setText(nick);
	}
	
	/**
	 * 关闭窗口
	 */
	public void close() {
		btnUserInfo.stopAnimate();
		shell.close();
	}
	
	@Override
	protected void OnQQEvent(QQEvent e) {
		switch(e.type) {
			case QQEvent.FRIEND_AUTH_SEND_OK:
				processAddFriendAuthSendSuccess(e);
				break;			
			case QQEvent.FRIEND_AUTH_SEND_FAIL:
				processAddFriendAuthSendFail(e);
				break;
			case QQEvent.FRIEND_AUTHORIZE_SEND_OK:
				processAuthorizeSendSuccess(e);
				break;
			case QQEvent.FRIEND_AUTHORIZE_SEND_FAIL:
				processAuthorizeSendFail(e);
				break;
			case QQEvent.FRIEND_ADD_OK:
			case QQEvent.FRIEND_ADD_ALREADY:
				processAddFriendSuccess(e);
				break;
			case QQEvent.FRIEND_ADD_NEED_AUTH:
				processAddFriendNeedAuth(e);
				break;
			case QQEvent.FRIEND_ADD_DENY:
				processAddFriendForbidden(e);
				break;
			case QQEvent.FRIEND_DELETE_OK:
			case QQEvent.FRIEND_DELETE_FAIL:
				processDeleteFriendSuccess(e);
				break;
			case QQEvent.CLUSTER_AUTH_SEND_OK:
				processJoinClusterAuthSendSuccess(e);
				break;
			case QQEvent.CLUSTER_AUTH_SEND_FAIL:
				processJoinClusterAuthSendFail(e);
				break;
			case QQEvent.SYS_TIMEOUT:
				if(e.operation == QQ.QQ_CMD_ADD_FRIEND_AUTH)
					processAddFriendAuthSendFail(e);
				else if(e.operation == QQ.QQ_CMD_DELETE_FRIEND)
					processDeleteFriendTimeout();
				else if(e.operation == QQ.QQ_CMD_CLUSTER_CMD)
					processClusterCommandTimeout(e);
				break;
		}
	}

	/**
	 * 处理认证消息发送失败事件
	 * 
	 * @param e
	 */
	private void processAuthorizeSendFail(QQEvent e) {
		AuthorizeReplyPacket packet = (AuthorizeReplyPacket)e.getSource();
		if(authQQ == packet.to) {
			textMsg.setEditable(true);
			textMsg.setBackground(main.getDisplay().getSystemColor(SWT.COLOR_WHITE));							
			btnApprove.setEnabled(true);
			btnReject.setEnabled(true);
			btnAdd.setEnabled(true);
			lblHint.setText(receive_system_message_fail_label);
			textMsg.setText(receive_system_message_send_fail);
			btnUserInfo.stopAnimate();		
		}
	}

	/**
	 * 处理认证消息发送成功事件
	 * 
	 * @param e
	 */
	private void processAuthorizeSendSuccess(QQEvent e) {
		AuthorizeReplyPacket packet = (AuthorizeReplyPacket)e.getSource();
		if(authQQ == packet.to) {
			btnApprove.setEnabled(true);
			btnReject.setEnabled(true);
			btnAdd.setEnabled(true);
			lblHint.setText(receive_system_message_success_label);
			textMsg.setText(receive_system_message_submited);
			btnUserInfo.stopAnimate();		
		}
	}

	/**
	 * 处理群操作超时事件
	 * @param e
	 */
	private void processClusterCommandTimeout(QQEvent e) {
		ClusterCommandPacket packet = (ClusterCommandPacket)e.getSource();
		if(packet.getSubCommand() == QQ.QQ_CLUSTER_CMD_JOIN_CLUSTER_AUTH)
			processJoinClusterAuthSendFail(e);
	}

	/**
	 * 处理群认证消息发送失败事件
	 * @param e
	 */
	private void processJoinClusterAuthSendFail(QQEvent e) {
		ClusterCommandReplyPacket packet = (ClusterCommandReplyPacket)e.getSource();
		if(authQQ == packet.clusterId) {
			textMsg.setEditable(true);
			textMsg.setBackground(main.getDisplay().getSystemColor(SWT.COLOR_WHITE));							
			btnApprove.setEnabled(true);
			btnReject.setEnabled(true);
			lblHint.setText(receive_system_message_fail_label);
			textMsg.setText(receive_system_message_send_fail);
			btnUserInfo.stopAnimate();		
		}
	}

	/**
	 * 处理群认证消息发送成功事件
	 * @param e
	 */
	private void processJoinClusterAuthSendSuccess(QQEvent e) {
		ClusterCommandReplyPacket packet = (ClusterCommandReplyPacket)e.getSource();
		if(authQQ == packet.clusterId) {
			btnApprove.setVisible(false);
			btnReject.setVisible(false);
			lblHint.setText(receive_system_message_success_label);
			textMsg.setText(receive_system_message_submited);
			btnUserInfo.stopAnimate();
			// 请求得到群信息
			main.getClient().cluster_GetInfo(authQQ);		
		}
	}

	/**
	 * 处理删除好友超时事件
	 */
	private void processDeleteFriendTimeout() {
		textMsg.setText(receive_system_message_timeout);
	}

	/**
	 * 处理删除好友成功事件
	 * 
	 * @param e
	 */
	private void processDeleteFriendSuccess(QQEvent e) {
		close();
	}

	/**
	 * 处理对方禁止添加好友事件
	 * 
	 * @param e
	 */
	private void processAddFriendForbidden(QQEvent e) {
		AddFriendExReplyPacket packet = (AddFriendExReplyPacket)e.getSource();
		if(authQQ == packet.friendQQ) {
			textMsg.setText(receive_system_message_forbidden);
			btnUserInfo.stopAnimate();
		}
	}

	/**
	 * 处理添加好友需要认证事件
	 * 
	 * @param e
	 */
	private void processAddFriendNeedAuth(QQEvent e) {
		AddFriendExReplyPacket packet = (AddFriendExReplyPacket)e.getSource();
		if(authQQ == packet.friendQQ) {
			btnApprove.setEnabled(true);
			btnReject.setEnabled(true);
			btnAdd.setEnabled(true);
			btnAdd.setVisible(true);
			textMsg.setEditable(true);
			textMsg.setBackground(main.getDisplay().getSystemColor(SWT.COLOR_WHITE));
			textMsg.setText("");
			btnAdd.setEnabled(true);
			lblHint.setText(receive_system_message_auth_label);
			addSecondStep = true;
			rejectSecondStep = false;
			btnUserInfo.stopAnimate();
		}
	}

	/**
	 * 处理添加好友成功事件
	 * 
	 * @param e
	 */
	private void processAddFriendSuccess(QQEvent e) {
		AddFriendExReplyPacket packet = (AddFriendExReplyPacket)e.getSource();
		if(authQQ == packet.friendQQ) {
			btnAdd.setVisible(false);
			textMsg.setText(receive_system_message_add_success);
			btnUserInfo.stopAnimate();
		}
	}

	/**
	 * 处理认证消息发送失败事件
	 * 
	 * @param e
	 */
	private void processAddFriendAuthSendFail(QQEvent e) {
		AddFriendAuthResponsePacket packet = (AddFriendAuthResponsePacket)e.getSource();
		type = packet.getAction();
		if(authQQ == packet.getTo()) {
			if(type == QQ.QQ_MY_AUTH_REQUEST || type == QQ.QQ_MY_AUTH_REJECT) {
				textMsg.setEditable(true);
				textMsg.setBackground(main.getDisplay().getSystemColor(SWT.COLOR_WHITE));							
			} 
			btnApprove.setEnabled(true);
			btnReject.setEnabled(true);
			btnAdd.setEnabled(true);
			lblHint.setText(receive_system_message_fail_label);
			textMsg.setText(receive_system_message_send_fail);
			btnUserInfo.stopAnimate();			
		}
	}

	/**
	 * 处理认证消息送出成功事件
	 * 
	 * @param e
	 */
	private void processAddFriendAuthSendSuccess(QQEvent e) {
		AddFriendAuthResponsePacket packet = (AddFriendAuthResponsePacket)e.getSource();
		type = packet.getAction();
		if(authQQ == packet.getTo()) {
			if(type == QQ.QQ_MY_AUTH_APPROVE || type == QQ.QQ_MY_AUTH_REJECT) {
				btnApprove.setVisible(false);
				btnReject.setVisible(false);
			}
			btnApprove.setEnabled(true);
			btnReject.setEnabled(true);
			btnAdd.setEnabled(true);
			lblHint.setText(receive_system_message_success_label);
			textMsg.setText(receive_system_message_submited);
			btnUserInfo.stopAnimate();		
		}
	}
}
