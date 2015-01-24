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

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Sash;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import edu.tsinghua.lumaqq.events.BaseQQListener;
import edu.tsinghua.lumaqq.events.FriendSelectionEvent;
import edu.tsinghua.lumaqq.events.IFriendSelectionListener;
import edu.tsinghua.lumaqq.models.Model;
import edu.tsinghua.lumaqq.models.ModelRegistry;
import edu.tsinghua.lumaqq.models.User;
import edu.tsinghua.lumaqq.qq.QQ;
import edu.tsinghua.lumaqq.qq.Util;
import edu.tsinghua.lumaqq.qq.beans.SMSReply;
import edu.tsinghua.lumaqq.qq.events.IQQListener;
import edu.tsinghua.lumaqq.qq.events.QQEvent;
import edu.tsinghua.lumaqq.qq.packets.in.ReceiveIMPacket;
import edu.tsinghua.lumaqq.qq.packets.in.SendSMSReplyPacket;
import edu.tsinghua.lumaqq.qq.packets.out.SendSMSPacket;
import edu.tsinghua.lumaqq.record.IKeyConstants;
import edu.tsinghua.lumaqq.record.RecordEntry;
import edu.tsinghua.lumaqq.resource.Colors;
import edu.tsinghua.lumaqq.resource.DefaultFace;
import edu.tsinghua.lumaqq.resource.Resources;
import edu.tsinghua.lumaqq.ui.helper.DateTool;
import edu.tsinghua.lumaqq.ui.helper.UITool;
import edu.tsinghua.lumaqq.ui.listener.CenterBorderPaintListener;
import edu.tsinghua.lumaqq.widgets.qstyle.Slat;
import edu.tsinghua.lumaqq.widgets.record.RecordViewer;
import edu.tsinghua.lumaqq.widgets.record.SMSFilter;
import edu.tsinghua.lumaqq.widgets.record.SMSRecordProvider;
import edu.tsinghua.lumaqq.widgets.rich.LineStyle;
import edu.tsinghua.lumaqq.widgets.rich.RichBox;

/**
 * 短信发送接收窗口
 * 
 * @author luma
 */
public class SMSWindow extends BaseWindow implements IFriendSelectionListener {
	/**
	 * 闪烁图标
	 * 
	 * @author luma
	 */
	private class Blink implements Runnable {
		private boolean flag;
		private volatile boolean stop;


		public Blink() {
			stop = true;
		}

		public void init() {
			this.flag = true;
			this.stop = false;
		}

		public void run() {
			try {
				if (flag) {
					getShell().setImage(res.getImage(Resources.icoMobile));
					((BorderStyler) getShell().getData(BorderStyler.STYLER)).repaintTitleBar();
				} else {
					getShell().setImage(null);
					((BorderStyler) getShell().getData(BorderStyler.STYLER)).repaintTitleBar();
				}
				flag = !flag;
				if (!stop)
					getShell().getDisplay().timerExec(500, this);
				else {
					getShell().setImage(res.getImage(Resources.icoMobile));
					((BorderStyler) getShell().getData(BorderStyler.STYLER)).repaintTitleBar();
				}
			} catch (SWTException e) {
				// 这个操作可能会抛出SWTException，如果组件已经dispose的话，
				//     所以我们需要捕捉这个异常，不然程序可能崩溃
			}
		}

		public void setStop(boolean stop) {
			this.stop = stop;
		}

		public boolean isStop() {
			return stop;
		}
	}

	private BaseQQListener qqListener = new BaseQQListener() {
		@Override
		protected void OnQQEvent(QQEvent e) {
			switch (e.type) {
				case QQEvent.SMS_SEND_OK:
					processSMSSent(e);
					break;
				case QQEvent.SYS_TIMEOUT:
					switch (e.operation) {
						case QQ.QQ_CMD_SEND_SMS:
							processSMSTimeout(e);
							break;
					}
					break;
			}
		}
	};

	private FriendSelectionShell fss;

	private String receiver;
	private RichBox inputBox;
	private Text textSender;
	private RecordViewer viewer;

	// true表示窗口处于一对一发送接收模式，这种模式下不能选择收件人，需要预设，可以发送和接收
	// false表示窗口处于可以多人发送模式，这种模式下可以选择1到多个收件人，只能发送
	private boolean singleMode;

	private boolean isUser;
	private User friend;
	private String mobile;

	// 消息要发送到的手机和好友QQ号列表
	private List<String> mobiles;
	private List<Integer> friends;
	// 短信的分片相关
	private int fragmentCount;
	private int nextFragment;
	private int maxFragmentLength;
	private byte[] fragmentCache;
	// 要发送的消息
	private String textCache;
	// 是否停止发送
	private boolean stop;
	// 当前是否有发送在进行中
	private boolean sending;

	private char expected;

	private Blink blink;

	private static final LineStyle myStyle = new LineStyle(Colors.MY_HINT_COLOR, null, "宋体", SWT.NORMAL, 9);
	private static final LineStyle otherStyle = new LineStyle(Colors.BLUE, null, "宋体", SWT.NORMAL, 9);
	private static final String CRLF = System.getProperty("line.separator");

	private Runnable sendAction = new Runnable() {
		public void run() {
			textCache = inputBox.getText();
			send();
		}
	};
	private Runnable recordAction = new Runnable() {
		public void run() {
			showRecord();
		}
	};
	private RichBox outputBox;
	private Slat btnSend;
	private Text textReceiver;
	private Label lblHint;


	public SMSWindow(MainShell main, String mobile) {
		super(main);
		this.mobile = mobile;
		isUser = false;
		singleMode = true;
	}

	public SMSWindow(MainShell main, User friend) {
		super(main);
		this.friend = friend;
		isUser = true;
		singleMode = true;
	}

	public SMSWindow(MainShell main) {
		super(main);
		singleMode = false;
	}

	@Override
	protected void initializeVariables() {
		super.initializeVariables();
		mobiles = new ArrayList<String>();
		friends = new ArrayList<Integer>();
		maxFragmentLength = QQ.QQ_MAX_SMS_LENGTH - QQ.QQ_MAX_SMS_SENDER_NAME;
		fragmentCount = 1;
		nextFragment = 0;
		stop = true;
		sending = false;
		blink = new Blink();
		receiver = "";
		expected = 0;
	}

	/**
	 * 开始闪烁系统消息图标
	 */
	public void startBlink() {
		if (!blink.isStop())
			return;
		blink.init();
		getShell().getDisplay().timerExec(0, blink);
	}

	/**
	 * 停止系统消息按钮上的闪烁效果
	 */
	public void stopBlink() {
		blink.setStop(true);
	}

	@Override
	protected String getTitle() {
		User me = main.getMyModel();
		if (me.isMobile())
			return NLS.bind(sms_title_mobile_qq, String.valueOf(me.qq));
		else if (me.isBind())
			return NLS.bind(sms_title_bind_user, String.valueOf(me.qq));
		else
			return NLS.bind(sms_title_no_bind, String.valueOf(me.qq));
	}

	@Override
	protected Image getImage() {
		return res.getImage(Resources.icoMobile);
	}

	@Override
	protected IQQListener getQQListener() {
		return qqListener;
	}

	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		initializeFriendSelectionShell(newShell);
	}

	private void initializeFriendSelectionShell(Shell newShell) {
		fss = new FriendSelectionShell(newShell, false);
		fss.addFriendSelectionListener(this);
		fss.setModel(main.getBlindHelper().getSMSReceivableGroupList());
	}

	@Override
	protected Point getInitialSize() {
		return new Point(500, 450);
	}

	@Override
	protected Control createContents(Composite parent) {
		Composite control = getContentContainer();

		Composite container = new Composite(control, SWT.NONE);
		container.setBackground(Colors.WHITE);
		container.setLayoutData(new GridData(GridData.FILL_BOTH));
		container.setLayout(new GridLayout());
		container.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent e) {
				Composite c = (Composite) e.getSource();
				Rectangle rect = c.getClientArea();
				rect.width--;
				rect.height--;
				e.gc.setForeground(Colors.MAINSHELL_BORDER_OUTMOST);
				e.gc.drawRectangle(rect);
			}
		});

		UITool.setDefaultBackground(Colors.WHITE);

		GridLayout layout = new GridLayout(2, false);
		layout.marginHeight = 1;
		Composite top = UITool.createContainer(container, new GridData(GridData.FILL_HORIZONTAL), layout);
		top.addPaintListener(new CenterBorderPaintListener(new Class[] { Text.class }, 16, Colors.PAGE_CONTROL_BORDER));

		// 收件人
		Slat btnReceiver = new Slat(top);
		btnReceiver.setText(sms_to);
		btnReceiver.setLayoutData(new GridData());
		btnReceiver.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				if (fss.isVisible())
					fss.setVisible(false);
				else {
					if (fss == null || fss.isDisposed())
						initializeFriendSelectionShell(getShell());
					fss.setVisible(true);
				}
			}
		});
		btnReceiver.setEnabled(!singleMode);

		textReceiver = new Text(top, SWT.SINGLE | (singleMode ? SWT.READ_ONLY : SWT.NONE));
		textReceiver.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		layout = new GridLayout();
		layout.marginHeight = layout.marginWidth = 3;
		layout.verticalSpacing = 0;
		Composite center = UITool.createContainer(container, new GridData(GridData.FILL_BOTH), layout);
		center.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent e) {
				Composite c = (Composite) e.getSource();
				Rectangle rect = c.getClientArea();
				rect.width--;
				rect.height--;
				e.gc.setForeground(Colors.MAINSHELL_BORDER_OUTMOST);
				e.gc.drawRectangle(rect);
			}
		});

		outputBox = new RichBox(center);
		outputBox.setLayoutData(new GridData(GridData.FILL_BOTH));
		outputBox.setReadonly(true);
		outputBox.setBackground(Colors.WHITE);
		outputBox.setDefaultStyle(myStyle);

		// sash
		Sash sash = new Sash(center, SWT.HORIZONTAL);
		sash.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		sash.setBackground(Colors.READONLY_BACKGROUND);
		sash.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				GridData data = (GridData) inputBox.getLayoutData();
				data.heightHint = inputBox.getParent().getClientArea().height - e.y - 23;
				inputBox.getParent().layout();
			}
		});

		// 提示条
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.heightHint = 20;
		Composite hintContainer = UITool.createContainer(center, gd, new GridLayout());
		hintContainer.setBackground(Colors.VIEWFORM_BANNER_BACKGROUND);

		lblHint = UITool.createLabel(hintContainer, NLS.bind(sms_will_send, String.valueOf(1)), new GridData(GridData.FILL_HORIZONTAL
				| GridData.VERTICAL_ALIGN_CENTER));
		lblHint.setBackground(lblHint.getParent().getBackground());

		inputBox = new RichBox(center);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.heightHint = 100;
		inputBox.setLayoutData(gd);
		inputBox.setBackground(Colors.WHITE);
		inputBox.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				calculateFragmentCount();
				refreshHint();
			}
		});

		layout = new GridLayout(4, false);
		layout.marginHeight = layout.marginWidth = 1;
		Composite bottom = UITool.createContainer(container, new GridData(GridData.FILL_HORIZONTAL), layout);
		bottom.addPaintListener(new CenterBorderPaintListener(new Class[] { Text.class }, 16, Colors.PAGE_CONTROL_BORDER));

		// 聊天记录
		gd = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
		gd.grabExcessHorizontalSpace = true;
		Slat btnRecord = UITool.createSlat(bottom, button_record_accel, gd);
		btnRecord.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				Slat temp = (Slat) e.getSource();
				if (e.x > temp.getSize().x || e.y > temp.getSize().y || e.x < 0 || e.y < 0) {
					return;
				}
				showRecord();
			}
		});
		// 签名        
		UITool.createLabel(bottom, sms_sender, new GridData(GridData.HORIZONTAL_ALIGN_END));
		gd = new GridData(GridData.HORIZONTAL_ALIGN_END);
		gd.widthHint = 100;
		textSender = UITool.createSingleText(bottom, gd);
		textSender.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (Character.isISOControl(e.character))
					return;
				String text = textSender.getText();
				byte[] b = Util.getBytes(text);
				int addLen = Util.getBytes(Character.toString(e.character)).length;
				if (b.length + addLen > QQ.QQ_MAX_SMS_SENDER_NAME)
					e.doit = false;
			}
		});
		textSender.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				maxFragmentLength = QQ.QQ_MAX_SMS_LENGTH - textSender.getText().length();
				calculateFragmentCount();
				refreshHint();
			}
		});
		btnSend = new Slat(bottom);
		btnSend.setText(button_send_accel);
		btnSend.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_END));
		btnSend.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				Slat temp = (Slat) e.getSource();
				if (e.x > temp.getSize().x || e.y > temp.getSize().y || e.x < 0 || e.y < 0) {
					return;
				}
				sendAction.run();
			}
		});

		// 聊天记录控件
		if (singleMode) {
			if (isUser)
				viewer = new RecordViewer(container, new SMSRecordProvider(friend), main);
			else
				viewer = new RecordViewer(container, new SMSRecordProvider(mobile), main);
			viewer.setFont(res.getDefaultFont());
			viewer.setRecordManager(main.getRecordManager());
			viewer.setBackground(Colors.LOGIN_BACKGROUND);
			gd = new GridData(GridData.FILL_HORIZONTAL);
			gd.horizontalSpan = 2;
			gd.heightHint = 200;
			gd.exclude = true;
			viewer.setLayoutData(gd);

			if (!isUser)
				viewer.setFilter(new SMSFilter(mobile));
		}
		btnRecord.setVisible(singleMode);

		initializeControl();
		initializeKeyAction();
		return control;
	}

	/**
	 * 显示记录控件
	 */
	private void showRecord() {
		GridData gd = (GridData) viewer.getLayoutData();
		Shell shell = getShell();
		if (gd.exclude) {
			gd.exclude = false;
			viewer.refreshRecord();
		} else {
			gd.exclude = true;
		}
		if (!shell.getMaximized()) {
			Point p = shell.getSize();
			shell.setSize(p.x, p.y + (gd.exclude ? -205 : 205));
		}
		viewer.setVisible(!gd.exclude);
		viewer.getParent().layout();
		shell.layout();
	}

	protected void refreshHint() {
		lblHint.setText(NLS.bind(sms_will_send, String.valueOf(fragmentCount)));
	}

	private void initializeKeyAction() {
		// mod3 -> alt
		inputBox.setUserKeyAction('S' | SWT.MOD3, sendAction);
		if (singleMode)
			inputBox.setUserKeyAction('H' | SWT.MOD3, recordAction);
	}

	private void initializeControl() {
		textSender.setText(main.getMyModel().displayName);
		if (receiver != null)
			textReceiver.setText(receiver);
	}

	public void append(String text, LineStyle style) {
		outputBox.appendText(text, style);
		outputBox.appendText(CRLF, style);
	}
	
	/**
	 * 处理发送超时事件
	 * 
	 * @param e
	 */
	private void processSMSTimeout(QQEvent e) {
		SendSMSPacket packet = (SendSMSPacket) e.getSource();
		if (expected != packet.getSequence())
			return;
		append(sms_timeout, otherStyle);
		reset();
	}

	/**
	 * 推入一个短信包
	 * 
	 * @param packet
	 */
	public void putSMS(ReceiveIMPacket packet) {
		outputBox.appendText('(' + DefaultFace.escapeFaces(packet.sms.senderName) + ")  " + DateTool.format(packet.sms.time), otherStyle);
		outputBox.appendText(packet.sms.message, otherStyle);
		if (receiver == null || receiver.trim().equals("")) {
			if (packet.sms.sender == 10000)
				setReceiver(packet.sms.senderName);
			else
				setReceiver(String.valueOf(packet.sms.sender));
		}
	}

	/**
	 * 处理发送完毕事件
	 * 
	 * @param e
	 */
	private void processSMSSent(QQEvent e) {
		SendSMSReplyPacket packet = (SendSMSReplyPacket) e.getSource();
		if (expected != packet.getSequence())
			return;
		// 显示回复信息，如果发送成功，则保存到聊天记录
		for (SMSReply reply : packet.replies) {
			switch (reply.replyCode) {
				case QQ.QQ_REPLY_SMS_FAIL:
					append(NLS.bind(sms_fail, reply.message, reply.isQQ ? String.valueOf(reply.qq) : reply.mobile), otherStyle);
					stop = true;
					break;
				case QQ.QQ_REPLY_SMS_OK:
					if (nextFragment == fragmentCount - 1)
						saveMessage(reply);
					append(NLS.bind(sms_success, reply.message, reply.isQQ ? String.valueOf(reply.qq) : reply.mobile), otherStyle);
					break;
			}
		}
		append(packet.message, otherStyle);

		// 继续发送
		if (stop)
			reset();
		else {
			if (nextFragment < fragmentCount - 1) {
				/*
				 * 还有分片没发完
				 */
				nextFragment++;
				send(getNextFragment());
			} else {
				/*
				 * 检查是否还有其他接收者
				 */
				rebuildReceiverList(packet.replies);
				if (isReceiverEmpty())
					reset();
				else {
					nextFragment = 0;
					send(getNextFragment());
				}
			}
		}
	}

	protected void saveMessage(SMSReply reply) {
		RecordEntry entry = new RecordEntry();
		if (reply.isQQ)
			entry.owner = reply.qq;
		else
			entry.owner = 9999;
		entry.sender = main.getMyModel().qq;
		entry.receiver = 0;
		entry.senderParent = 0;
		entry.type = IKeyConstants.NORMAL;
		entry.time = System.currentTimeMillis();
		if (reply.isQQ)
			entry.message = textCache;
		else
			entry.message = reply.mobile + '|' + textCache;
		main.getRecordManager().addRecord(entry);
	}

	protected void rebuildReceiverList(List<SMSReply> replies) {
		mobiles.clear();
		friends.clear();
		for (SMSReply reply : replies) {
			if (reply.replyCode == QQ.QQ_REPLY_SMS_QUEUED) {
				if (reply.isQQ)
					friends.add(reply.qq);
				else
					mobiles.add(reply.mobile);
			}
		}
	}

	protected boolean isReceiverEmpty() {
		return mobiles.isEmpty() && friends.isEmpty();
	}

	protected void reset() {
		btnSend.setText(button_send_accel);
		btnSend.setEnabled(true);
		stop = true;
		sending = false;
		nextFragment = 0;
		expected = 0;
	}

	/**
	 * @return Returns the receiver.
	 */
	public String getReceiver() {
		return receiver;
	}

	/**
	 * @param receiver The receiver to set.
	 */
	public void setReceiver(String receiver) {
		this.receiver = receiver;
		textReceiver.setText(receiver);
	}

	/**
	 * @param receiver
	 */
	private void rebuildReceiverList(String receiver) {
		friends.clear();
		mobiles.clear();
		StringTokenizer st = new StringTokenizer(receiver, ",");
		while (st.hasMoreTokens()) {
			String rec = st.nextToken().trim();
			int qq = Util.getInt(rec, 0);
			if (qq != 0 && ModelRegistry.hasUser(qq) && !friends.contains(qq)) {
				friends.add(qq);
			} else if (!mobiles.contains(rec)) {
				mobiles.add(rec);
			}
		}
	}

	@Override
	public void shellActivated(ShellEvent e) {
		super.shellActivated(e);
		stopBlink();
	}

	@Override
	public void shellClosed(ShellEvent e) {
		super.shellClosed(e);
		if (singleMode) {
			if (isUser)
				main.getShellRegistry().removeSMSWindow(friend);
			else
				main.getShellRegistry().removeSMSWindow(mobile);
		} else
			main.getShellRegistry().deregisterSMSWindow();
	}

	/**
	 * 计算分片数
	 */
	private void calculateFragmentCount() {
		textCache = inputBox.getText();
		int length = textCache.length();
		if (length > maxFragmentLength) {
			fragmentCount = length / (maxFragmentLength - 4) + 1;
		} else
			fragmentCount = 1;
	}

	/**
	 * 发送短消息
	 */
	protected void send() {
		if (stop) {
			calculateFragmentCount();
			receiver = textReceiver.getText();
			rebuildReceiverList(receiver);
			if (textCache.length() == 0)
				MessageDialog.openWarning(getShell(), message_box_common_warning_title, message_box_sms_empty);
			else if (fragmentCount > 9)
				MessageDialog.openWarning(getShell(), message_box_common_warning_title, message_box_too_long_sms);
			else if (mobiles.isEmpty() && friends.isEmpty())
				MessageDialog.openWarning(getShell(), message_box_common_warning_title, message_box_sms_no_receiver);
			else {
				btnSend.setText(button_stop);
				send(getNextFragment());
				stop = false;
			}
		} else {
			btnSend.setText(button_send_accel);
			stop = true;
			if (sending)
				btnSend.setEnabled(false);
		}
	}

	/**
	 * 发送一个分片
	 */
	private void send(byte[] msg) {
		append(NLS.bind(sms_sending, new Object[] { Util.getString(msg), String.valueOf(nextFragment + 1), String.valueOf(fragmentCount) }), myStyle);
		expected = main.getClient().sms_Send(mobiles, friends, textSender.getText(), msg, (char) nextFragment);
		sending = true;
	}

	/**
	 * @return
	 * 		下一个分片的字节数组
	 */
	protected byte[] getNextFragment() {
		if (nextFragment >= fragmentCount)
			return null;
		else if (fragmentCount == 1) {
			fragmentCache = Util.getBytes(inputBox.getText());
			return fragmentCache;
		} else {
			String text = inputBox.getText();
			StringBuilder sb = new StringBuilder();
			sb.append(nextFragment + 1);
			sb.append('/');
			sb.append(fragmentCount);
			sb.append((char) 0x0A);
			sb.append(text.substring(nextFragment * (maxFragmentLength - 4), Math.min((nextFragment + 1) * (maxFragmentLength - 4), text.length())));
			fragmentCache = Util.getBytes(sb.toString());
			return fragmentCache;
		}
	}

	public void friendSelected(FriendSelectionEvent e) {
		for (Model model : e.getModels()) {
			if (model instanceof User) {
				User f = (User) model;
				friends.add(f.qq);
			}
		}
		synchronizeList();
	}

	public void friendDeselected(FriendSelectionEvent e) {
		for (Model model : e.getModels()) {
			if (model instanceof User) {
				User f = (User) model;
				friends.remove(new Integer(f.qq));
			}
		}
		synchronizeList();
	}

	private void synchronizeList() {
		StringBuilder sb = new StringBuilder();
		for (String mobile : mobiles)
			sb.append(mobile).append(',');
		for (Integer qq : friends)
			sb.append(qq).append(',');
		if (sb.length() > 0)
			sb.deleteCharAt(sb.length() - 1);
		receiver = sb.toString();
		textReceiver.setText(receiver);
	}
}
