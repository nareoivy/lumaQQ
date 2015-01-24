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

import static edu.tsinghua.lumaqq.resource.Messages.default_font;
import static edu.tsinghua.lumaqq.resource.Messages.error_login_fail;
import static edu.tsinghua.lumaqq.resource.Messages.hint_login;
import static edu.tsinghua.lumaqq.resource.Messages.message_box_common_fail_title;
import static edu.tsinghua.lumaqq.resource.Messages.message_box_common_question_title;
import static edu.tsinghua.lumaqq.resource.Messages.message_box_login_fail_title;
import static edu.tsinghua.lumaqq.resource.Messages.message_box_upload_group;
import static edu.tsinghua.lumaqq.resource.Messages.search_current_online_unknown;
import static edu.tsinghua.lumaqq.resource.Messages.tooltip_button_search;
import static edu.tsinghua.lumaqq.resource.Messages.tooltip_button_sms;
import static edu.tsinghua.lumaqq.resource.Messages.tooltip_button_sysmenu;
import static edu.tsinghua.lumaqq.resource.Messages.tooltip_button_sysmsg;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseTrackAdapter;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tray;
import org.eclipse.swt.widgets.TrayItem;

import edu.tsinghua.lumaqq.IPSeeker;
import edu.tsinghua.lumaqq.LumaQQ;
import edu.tsinghua.lumaqq.MessageQueue;
import edu.tsinghua.lumaqq.QQShowManager;
import edu.tsinghua.lumaqq.Sounder;
import edu.tsinghua.lumaqq.ecore.option.OpType;
import edu.tsinghua.lumaqq.events.IQQShowListener;
import edu.tsinghua.lumaqq.hotkey.IHotkeyListener;
import edu.tsinghua.lumaqq.models.Group;
import edu.tsinghua.lumaqq.models.ModelRegistry;
import edu.tsinghua.lumaqq.models.User;
import edu.tsinghua.lumaqq.qq.QQ;
import edu.tsinghua.lumaqq.qq.QQClient;
import edu.tsinghua.lumaqq.qq.Util;
import edu.tsinghua.lumaqq.qq.beans.QQUser;
import edu.tsinghua.lumaqq.qq.events.QQEvent;
import edu.tsinghua.lumaqq.qq.packets.ErrorPacket;
import edu.tsinghua.lumaqq.qq.packets.InPacket;
import edu.tsinghua.lumaqq.qq.packets.in.LoginReplyPacket;
import edu.tsinghua.lumaqq.qq.packets.in.ReceiveIMPacket;
import edu.tsinghua.lumaqq.qq.packets.in.SystemNotificationPacket;
import edu.tsinghua.lumaqq.record.RecordManager;
import edu.tsinghua.lumaqq.resource.Colors;
import edu.tsinghua.lumaqq.resource.Resources;
import edu.tsinghua.lumaqq.ui.config.user.UserInfoWindow;
import edu.tsinghua.lumaqq.ui.dialogs.CrashDialog;
import edu.tsinghua.lumaqq.ui.dialogs.LoginDialog;
import edu.tsinghua.lumaqq.ui.helper.BlindHelper;
import edu.tsinghua.lumaqq.ui.helper.ClusterCategoryTool;
import edu.tsinghua.lumaqq.ui.helper.ConfigHelper;
import edu.tsinghua.lumaqq.ui.helper.ExportHelper;
import edu.tsinghua.lumaqq.ui.helper.FaceRegistry;
import edu.tsinghua.lumaqq.ui.helper.MenuHelper;
import edu.tsinghua.lumaqq.ui.helper.MessageHelper;
import edu.tsinghua.lumaqq.ui.helper.OptionHelper;
import edu.tsinghua.lumaqq.ui.helper.ShellLauncher;
import edu.tsinghua.lumaqq.ui.helper.ShellRegistry;
import edu.tsinghua.lumaqq.ui.helper.TimerHelper;
import edu.tsinghua.lumaqq.ui.helper.TipHelper;
import edu.tsinghua.lumaqq.ui.helper.UIHelper;
import edu.tsinghua.lumaqq.ui.jobs.BackgroundJobExecutor;
import edu.tsinghua.lumaqq.ui.jobs.DialogJobExecutor;
import edu.tsinghua.lumaqq.ui.jobs.IExecutor;
import edu.tsinghua.lumaqq.ui.jobs.UploadGroupJob;
import edu.tsinghua.lumaqq.widgets.mac.Ring;
import edu.tsinghua.lumaqq.widgets.qstyle.Blind;
import edu.tsinghua.lumaqq.widgets.rich.LineStyle;
import edu.tsinghua.lumaqq.widgets.rich2.Animator;

/**
 * @author luma
 */
public class MainShell implements IQQShowListener {        
    private Shell shell;
    private Display display;
    private TrayItem item;
    private Blind blind;
    private Label btnSysMsg;
    private Label btnSysMenu;
    private Ring statusRing;
    private QQClient client;
    private Resources res;
    
    // 群贴图接收器
    private ClusterCustomFaceReceiver faceReceiver;
    // QQ事件处理器
    private QQEventProcessor processor;
    // 声音播放精灵线程
    private Sounder sounder;
    // 网络硬盘任务线程
    private IExecutor diskJobQueue;
    // 发送消息的任务线程
    private IExecutor imJobQueue;
    // 用户自己的model
    private User myModel;
    // 当前在线人数的字符串表示
    private String currentOnlineNumber;
    // 当前消息字体和颜色，null为缺省
    private Font font;
    private LineStyle defaultStyle;
    // 组背景颜色，如果为null，为缺省颜色
    private Color groupColor;
    // ip查找类引用
    private IPSeeker seeker;
    // 当前在线好友
    private List<User> currentOnlines;
    // 聊天记录管理器
    private RecordManager rm;
    // QQ秀管理器
    private QQShowManager sm;
    // 参数工具类实例
    private OptionHelper optionHelper;
    // 消息队列
    private MessageQueue mq;
    // 窗口位置备份
    private Point shellLocation;

	// 分组信息是否已经被修改
	private boolean groupDirty;
	
    // 删除请求哈希表，这个哈希表说明了如果某个用户删除成功就要把这个用户弄到哪个组去
    private Map<Integer, Group> deleteToMap;
    // 窗口注册表
    private ShellRegistry shellRegistry;
    // 窗口发射器
    private ShellLauncher shellLauncher;
    
    // 配置文件帮助类
    private ConfigHelper configHelper;
    // blind帮助类
    private BlindHelper blindHelper;
    // UI帮助类
    private UIHelper uiHelper;
    // 导出帮助类
    private ExportHelper exportHelper;
    // 消息处理帮助类
    private MessageHelper messageHelper;
    // 菜单帮助类
    private MenuHelper menuHelper;
    // 提示窗口帮助类
    private TipHelper tipHelper;
    // 定时器帮助类
    private TimerHelper timerHelper;
    
    private ClusterCategoryTool ccu;
    
    private IHotkeyListener messageKeyListener;
    private Listener falseMessageKeyListener;
	private Label btnSMS;
	
	// 面板数组
	private Composite[] panels;
	
	public static final int PANEL_MAIN = 0;
	public static final int PANEL_WAITING = 1;
	private Label waitingPanelHint;
	private Animator waitingPanelAnimator;
	private int currentPanel;
	
	// true表示鼠标位于主面板bar之上
	private boolean mouseOnBar;
	
	// view
	private Control[] views;
	public static final int VIEW_FRIEND = 0;
	private Composite viewContainer;
	
	/**
	 * 初始化所有
	 */
	private void initialize() {
		this.display = Display.getCurrent();
		res = Resources.getInstance();
		
		// 初始化组件
		initLayout();

		initVariables();
		
		// 初始化菜单
		menuHelper = new MenuHelper(this);
		menuHelper.initMenu();
	}

	/**
	 * 初始化变量
	 */
	private void initVariables() {
		mq = new MessageQueue();
		seeker = IPSeeker.getInstance();
		processor = new QQEventProcessor(this);
		faceReceiver = new ClusterCustomFaceReceiver(this);
		deleteToMap = new Hashtable<Integer, Group>();
		shellRegistry = new ShellRegistry();
		shellLauncher = new ShellLauncher(this);
		uiHelper = new UIHelper(this);
		exportHelper = new ExportHelper(this);
		messageHelper = new MessageHelper(this);
		blindHelper = new BlindHelper(this);
		tipHelper = new TipHelper(this);
		timerHelper = new TimerHelper();
		currentOnlineNumber = search_current_online_unknown;
		sounder = new Sounder();
		diskJobQueue = new BackgroundJobExecutor(this);
		diskJobQueue.setExitOnEmpty(false);
		imJobQueue = new BackgroundJobExecutor(this);
		imJobQueue.setExitOnEmpty(false);
		currentOnlines = new ArrayList<User>();
		mouseOnBar = false;
		ccu = new ClusterCategoryTool();
		ccu.init();
		messageKeyListener = new IHotkeyListener() {
			public void keyPressed() {
				onMessageKey();
			}
		};
		falseMessageKeyListener = new Listener() {
			public void handleEvent(Event e) {
				if(e.keyCode == 'z' && (e.stateMask & SWT.CTRL) != 0 && (e.stateMask & SWT.ALT) != 0) {
					onMessageKey();
				}
			}			
		};
	}      
	
	/**
	 * 切换面板
	 * 
	 * @param panelId
	 */
	public void switchPanel(int panelId) {
		BorderStyler styler = (BorderStyler)shell.getData(BorderStyler.STYLER);
		StackLayout layout = (StackLayout)styler.getCenter().getLayout();
		layout.topControl = panels[panelId];
		currentPanel = panelId;
		styler.getCenter().layout();
	}
	
	/**
	 * 切换视图
	 * 
	 * @param viewId
	 */
	public void switchView(int viewId) {
		StackLayout layout = (StackLayout)viewContainer.getLayout();
		layout.topControl = views[viewId];
		viewContainer.layout();
	}
	
	/**
	 * 检查分组是否修改过，根据系统设置判断是否上传
	 */
	public void checkGroupDirty() {
	    // 如果分组被修改了，检查系统设置
	    if(isGroupDirty() && client.getUser().isLoggedIn()) {
	        if(optionHelper.getAutoUploadGroup() == OpType.PROMPT_LITERAL) {
		        if(MessageDialog.openQuestion(shell, 
		                message_box_common_question_title, 
		                message_box_upload_group)) {
		            uploadGroup();
		        }
	        } else if(optionHelper.getAutoUploadGroup() == OpType.ALWAYS_LITERAL) 
	            uploadGroup();
	    }
	}    
	
	/**
	 * 上传分组
	 */
	private void uploadGroup() {
	    IExecutor executor = new DialogJobExecutor(this);
	    executor.addJob(new UploadGroupJob());
	    executor.execute();
	}
	
	public boolean isGroupDirty() {
		return groupDirty;
	}
	
	/**
	 * @return
	 * 		主Shell对象
	 */
	public Shell getShell() {
		return shell;
	}
	
    /**
     * @return
     * 		好友列表控件
     */
    public Blind getBlind() {
        return blind;
    }

	/**
	 * 关闭主shell
	 */
	public void close() {
		if(shell != null && !shell.isDisposed())
			shell.close();
	}

	public void setClient(QQClient client) {
		this.client = client;
		
		// 初始化系统设置
		if(configHelper == null)
			configHelper = new ConfigHelper(this);
		if(optionHelper == null)
			optionHelper = new OptionHelper();
		configHelper.initSystemOptions();
		// 初始化自己的model
		configHelper.saveSelf();
		configHelper.initSelf();
		myModel = configHelper.getMyself();		
		// 初始化窗口，只初始化一次
		if(display == null)
			initialize();
		// 后初始化
		configHelper.postInitSystemOptions();
		// 设置窗口标题
		setTitle(String.valueOf(myModel.qq));
		// 初始化自动停靠
		initAutoDock();
		// 初始化备注信息
		configHelper.initRemarks();
		// 初始化快捷回复
		configHelper.initReplies();
		// 初始化代理列表
		configHelper.initProxies();
		// 初始化自定义表情
		configHelper.initFaces();
		// 初始化model
        blindHelper.initModels();
        blindHelper.setTreeMode(optionHelper.isTreeMode());
		// 初始化聊天记录管理器
		initMessageManager(myModel.qq);
		// 初始化QQ秀管理器
		initQQShowManager(myModel.qq);
		
		// 设置声音精灵线程的使能性
		sounder.setEnable(optionHelper.isEnableSound());
		// 设置其他Helper参数
		menuHelper.setClient(client);
	}	

	/**
	 * 设置标题条
	 * 
	 * @param string
	 */
	private void setTitle(String string) {
		shell.setText(string);
		BorderStyler styler = (BorderStyler)shell.getData(BorderStyler.STYLER);
		styler.repaintTitleBar();
	}

	/**
	 * 为shell加上自动停靠
	 */
	private void initAutoDock() {
		if(shell.getData(AutoDockManager.DOCK_MANAGER) == null) {
			BorderStyler styler = (BorderStyler)shell.getData(BorderStyler.STYLER);
			new AutoDockManager(this).addDockSupport(shell, styler);
		}
	}

	/**
	 * 初始化聊天记录管理器
	 */
	private void initMessageManager(int qqNum) {
		rm = new RecordManager(LumaQQ.RECORD_DIRECTORY);
	}
	
    /**
	 * 初始化QQ秀管理器
	 * @param qq
	 */
	private void initQQShowManager(int qqNum) {
		if(sm != null)
			sm.removeQQShowListener(this);
		sm = QQShowManager.getInstance(LumaQQ.INSTALL_DIR + "/" + String.valueOf(qqNum));
		sm.addQQShowListener(this);
	}
	
	/**
	 * 打开shell，开始事件循环
	 */
	public void open()	{
		sounder.start();
		diskJobQueue.execute();
		imJobQueue.execute();
		// 初始化系统Tray Icon
		initTray();

		// 设置窗口位置和大小
		Rectangle bound = new Rectangle(optionHelper.getLocationX(), optionHelper.getLocationY(), 0, 0);
		bound.width = optionHelper.getWidth();
		bound.height = optionHelper.getHeight();
		if(bound.width == -1) bound.width = 200;
		if(bound.height == -1) bound.height = 500;
		shell.setSize(bound.width, bound.height);
		shell.setLocation(bound.x, bound.y);
		// 保存窗口位置，本来这个位置是在controlMoved事件里面保存的，但是有可能有的系统
		// 一开始没有这个事件，从而造成空指针，所以还是要在这里保存一下
		shellLocation = shell.getLocation();
		// 打开shell，开始事件循环
		shell.layout();
		shell.open();
		// 登陆
		checkLogin(false, false);
		// 开始事件循环
		while(!shell.isDisposed()) 	{
			if(!display.readAndDispatch()) 
				display.sleep();
		}
	}

	/**
	 * 检查登陆状态，如果正在登录或者已经登录，直接返回，如果尚未登录则执行登录操作
	 * 
	 * @param forceRandom
	 * 		true表示强制随机选择服务器
	 * @return
	 * 		如果当前正在登陆，则返回false，否则返回true
	 */
	public boolean checkLogin(boolean forceRandom, boolean forceTcp) {
		// 如果正在登陆，不执行动作
		if(client.isLogging()) return false;
		// 万一用户还没有登陆，需要先登陆
		if(!client.getUser().isLoggedIn()) {
			// 重设界面
			if(getCurrentPanel() != PANEL_WAITING) {
				switchPanel(PANEL_WAITING);
				setWaitingPanelHint(hint_login);
				resumeWaitingPanelAnimation();
			}
			// 如果设置了强制tcp方式，则不管设置什么都用tcp
			boolean useTcp = forceTcp ? true : optionHelper.isUseTcp();
			// 设置登陆的服务器和登陆方式
			if(optionHelper.isAutoSelect() || forceRandom) {
				String[] servers = useTcp ? LumaQQ.tcpServers : LumaQQ.udpServers;
				client.setLoginServer(servers[Util.random().nextInt(servers.length)]);
			} else {
			    if(forceTcp && optionHelper.isUseTcp() != forceTcp)
			        client.setLoginServer(LumaQQ.tcpServers[0]);
			    else 
			        client.setLoginServer(optionHelper.getServer());
			}
			// 如果是TCP方式登录，设置TCP服务器的端口和登录模式
			if(useTcp) {
			    client.getUser().setUdp(false);
			    client.setTcpLoginPort(optionHelper.getTcpPort());
			} else
			    client.getUser().setUdp(true);
			// 设置代理服务器的类型，地址和验证用户名密码
			try {
                client.setProxy(new InetSocketAddress(optionHelper.getProxyServer(), optionHelper.getProxyPort()));
				client.setProxyType(optionHelper.getProxyType().getName());
				client.setProxyUsername(optionHelper.getProxyUsername());
				client.setProxyPassword(optionHelper.getProxyPassword());
            } catch (IllegalArgumentException e) {
                client.setProxyType("None");
            }
            // 设置是否显示虚拟摄像头
            client.getUser().setShowFakeCam(optionHelper.isShowFakeCam());
			// 开始登陆
            uiHelper.startStatusAnimation();
			processor.clear();
			client.addQQListener(processor);
			client.addQQListener(faceReceiver);
			try {
			    client.login();
			} catch (Exception e) {
                client.getUser().setStatus(QQ.QQ_STATUS_OFFLINE);
			    logout();
			    MessageDialog.openError(shell, message_box_common_fail_title, e.getMessage());
			}
			return false;
		}
		return true;
	}

	/**
	 * 使自己处于下线状态
	 */
	public void logout() {
		// 注销
		client.logout();
		// 停止所有动画
		uiHelper.stopStatusAnimation();
		uiHelper.stopBlinkSystemMessageIcon();
		uiHelper.stopBlinkImage();
		blindHelper.stopAllEffectOnBlind();
		// 释放定时器
		timerHelper.dispose();
		// 重设图标状态和相关变量，把所有好友设为下线模式
		uiHelper.setTrayIconByStatus();
		uiHelper.setAllFriendOffline();
		mq.clear();
	}		
    
	/**
     * 添加程序图标到系统托盘中
     */
    private void initTray() {
    	// 创建Tray Icon
    	Tray tray = display.getSystemTray();
    	if(tray == null)
    		return;
    	item = new TrayItem(tray, SWT.NONE);
    	// 设置图标
    	item.setImage(res.getImage(Resources.icoOffline));
    	// 添加listener
    	// 鼠标单击事件
    	item.addListener (SWT.Selection, new Listener () {
    		public void handleEvent (Event event) {
    			if(mq.hasNext()) {
    				populateMessage();
    			} else {
    				menuHelper.setStatusMenuVisible(false);
    				if(shell.getMinimized()) {
    					shell.setVisible(true);														    
    					shell.setMinimized(false);
    				} else if(isAutoDockEnabled()) {
    					if(isDocking())
        					pend();
        				else if(!isPending()) {
        					tipHelper.closeFriendTipShell();
        				    shell.setMinimized(true);
        				    shell.setVisible(false);
        				}
    				} else {
    					shell.setMinimized(true);
    					if(optionHelper.isHideWhenMinimize())
    						shell.setVisible(false);
    				}
    			}
    		}
    	});
    	// 鼠标右键点击事件
    	item.addListener (SWT.MenuDetect, new Listener () {
    		public void handleEvent (Event event) {
    			if(menuHelper.isSystemMenuVisible())
    				menuHelper.setSystemMenuVisible(false);
    			else {
    				menuHelper.setSystemMenuData(1);
    				menuHelper.setSystemMenuLocation(display.getCursorLocation());
    				menuHelper.setSystemMenuVisible(true);    				
    			}
    		}
    	});
		item.setToolTipText("LumaQQ " + String.valueOf(client.getUser().getQQ()));
    }

	/**
	 * 解析下一个消息，弹出相应的界面
	 */
	protected void populateMessage() {
		switch(mq.nextMessageSource()) {
			case QQ.QQ_IM_FROM_SYS:
				InPacket in = mq.getSystemMessage();
				shellLauncher.openReceiveSystemMessageShell(in);
				if(in instanceof SystemNotificationPacket) {
					SystemNotificationPacket packet = (SystemNotificationPacket)in;
					if(packet.type == QQ.QQ_SYS_ADD_FRIEND_APPROVED ||
							packet.type == QQ.QQ_SYS_ADD_FRIEND_APPROVED_AND_ADD)
						blindHelper.addFriend(packet.from);    					
				}
				break;
			case QQ.QQ_IM_FROM_SMS:
				onSMS();
				break;
			case QQ.QQ_IM_FROM_TEMP_SESSION:
				shellLauncher.openTempSessionIMWindow(mq.nextSender());
				break;
			default:
				shellLauncher.openIMShell(mq.nextSender());	
				break;
		}
	}

	/**
	 * 使主界面处于悬挂状态
	 */
	protected void pend() {
		((AutoDockManager)getShell().getData(AutoDockManager.DOCK_MANAGER)).pend();
	}

	/**
	 * @return
	 * 		QQClient对象
	 */
	public QQClient getClient() {
		return client;
	}

	public MenuHelper getMenuHelper() {
		return menuHelper;
	}

	public Display getDisplay() {
		return display;
	}

	/**
	 * 创建字体
	 */
	public void createDefaultStyle() {
		String fontName = optionHelper.getFontName();
		if(fontName.equals(""))
		    fontName = default_font;
		int style = SWT.NORMAL;
		if(optionHelper.getBold()) style |= SWT.BOLD;
		if(optionHelper.getItalic()) style |= SWT.ITALIC;
		
		int c = optionHelper.getFontColor();
		defaultStyle = new LineStyle(new Color(display, (c >>> 16) & 0xFF, (c >>> 8) & 0xFF, c & 0xFF), null, fontName, style, optionHelper.getFontSize());		
	}
    
    /**
     * 设置组背景色
     * 
     * @param color
     * 		背景色
     */
    public void setGroupColor(Color color) {
        groupColor = color;
        blind.setContentBackground(groupColor);
    }

    /**
     * 初始化界面布局
     */
    private void initLayout() {
    	Composite body = initShell();
    	body.setLayout(new StackLayout());
		
    	panels = new Composite[2];
    	panels[PANEL_MAIN] = initMainPanel(body);
    	panels[PANEL_WAITING] = initWaitingPanel(body);
    	
    	switchView(VIEW_FRIEND);
    	switchPanel(PANEL_WAITING);
    }

	/**
	 * 初始化等待面板
	 * 
	 * @param body
	 */
	private Composite initWaitingPanel(Composite body) {
		Composite panel = new Composite(body, SWT.NONE | SWT.NO_BACKGROUND);
		panel.setLayout(new FormLayout());
		panel.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent e) {
				Color blue = new Color(display, 0xD5, 0xEF, 0xFF);
				Composite panel = (Composite)e.getSource();
				Rectangle rect = panel.getClientArea();
				int height = rect.height >> 2;
				e.gc.setForeground(blue);
				e.gc.setBackground(display.getSystemColor(SWT.COLOR_WHITE));
				e.gc.fillGradientRectangle(0, 0, rect.width, height, true);
				e.gc.setForeground(display.getSystemColor(SWT.COLOR_WHITE));
				e.gc.setBackground(blue);
				e.gc.fillGradientRectangle(0, rect.height - height, rect.width, height, true);
				e.gc.setBackground(display.getSystemColor(SWT.COLOR_WHITE));
				e.gc.fillRectangle(0, height, rect.width, rect.height - height - height);
				blue.dispose();
			}
		});
		// logo
		final Label lblLogo = new Label(panel, SWT.CENTER);
		lblLogo.setImage(res.getImage(Resources.bmpLumaQQ));
		FormData fd = new FormData();
		fd.left = new FormAttachment(0, 0);
		fd.top = new FormAttachment(0, 0);
		fd.height = 47;
		fd.width = 160;
		lblLogo.setLayoutData(fd);
		// 进度条动画
		waitingPanelAnimator = new Animator(panel);
		waitingPanelAnimator.setLoader(res.getImageLoader(Resources.bmpProgress));
		fd = new FormData();
		fd.left = new FormAttachment(0, 0);
		fd.top = new FormAttachment(0, 0);
		fd.height = 19;
		fd.width = 108;
		waitingPanelAnimator.setLayoutData(fd);
		// 提示
		waitingPanelHint = new Label(panel, SWT.CENTER);
		waitingPanelHint.setBackground(Colors.WHITE);
		waitingPanelHint.setText(hint_login);
		fd = new FormData();
		fd.left = new FormAttachment(0, 0);
		fd.top = new FormAttachment(0, 0);
		fd.width = 160;
		waitingPanelHint.setLayoutData(fd);
		// 取消登录
		final Label lblCancel = new Label(panel, SWT.CENTER);
		lblCancel.setImage(res.getImage(Resources.bmpCancelLoginNormal));
		fd = new FormData();
		fd.left = new FormAttachment(0, 0);
		fd.top = new FormAttachment(0, 0);
		fd.height = 26;
		fd.width = 90;
		lblCancel.setLayoutData(fd);
		lblCancel.addMouseTrackListener(new MouseTrackAdapter() {
			@Override
			public void mouseEnter(MouseEvent e) {
				lblCancel.setImage(res.getImage(Resources.bmpCancelLoginHover));
			}
			
			@Override
			public void mouseExit(MouseEvent e) {
				lblCancel.setImage(res.getImage(Resources.bmpCancelLoginNormal));
			}
		});
		lblCancel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				lblCancel.setImage(res.getImage(Resources.bmpCancelLoginDown));
			}
			
			@Override
			public void mouseUp(MouseEvent e) {
				lblCancel.setImage(res.getImage(Resources.bmpCancelLoginNormal));
				restartLogin(null);
			}
		});
		
		panel.addControlListener(new ControlAdapter() {
			@Override
			public void controlResized(ControlEvent e) {
				Composite panel = (Composite)e.getSource();
				Rectangle rect = panel.getClientArea();
				
				int childrenHeight = 47 + 5 + 19 + 5 + 20 + 10 + 26;
				int reference = (rect.height - childrenHeight) >> 1;
				FormData fd = (FormData)lblLogo.getLayoutData();
				fd.left.offset = (rect.width - 160) >> 1;
				fd.top.offset = reference;
				
				fd = (FormData)waitingPanelAnimator.getLayoutData();
				fd.left.offset = (rect.width - 108) >> 1;
				fd.top.offset = reference + 52;
				
				fd = (FormData)waitingPanelHint.getLayoutData();
				fd.left.offset = (rect.width - 160) >> 1;
				fd.top.offset = reference + 47 + 5 + 19 + 5;
				
				fd = (FormData)lblCancel.getLayoutData();
				fd.left.offset = (rect.width - 90) >> 1;
				fd.top.offset = reference + 47 + 5 + 19 + 5 + 20 + 10;
				
				panel.layout();
			}
		});
		
		return panel;
	}
	
	/**
	 * 设置等待面板的提示
	 * 
	 * @param text
	 */
	public void setWaitingPanelHint(String text) {
		waitingPanelHint.setText(text);
	}
	
	/**
	 * 停止等待面板的动画
	 */
	public void stopWaitingPanelAnimation() {
		waitingPanelAnimator.stopAnimation();
	}
	
	/**
	 * 回复等待面板的动画
	 */
	public void resumeWaitingPanelAnimation() {
		waitingPanelAnimator.resumeAnimation();
	}

	/**
	 * 初始化主面板
	 * 
	 * @param body
	 */
	private Composite initMainPanel(Composite body) {
		Composite panel = new Composite(body, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.marginHeight = layout.marginWidth = layout.horizontalSpacing = layout.verticalSpacing = 0;
		panel.setLayout(layout);
		
		viewContainer = new Composite(panel, SWT.NONE);
		viewContainer.setLayoutData(new GridData(GridData.FILL_BOTH));
		viewContainer.setLayout(new StackLayout());		
		viewContainer.setBackground(Colors.MAINSHELL_BACKGROUND);
		
		// 初始化视图
		views = new Control[2];
		initFriendView();
        
        // expand bar
        Label lblBar = new Label(panel, SWT.LEFT);
        GridData gd = new GridData(GridData.FILL_HORIZONTAL);
        gd.heightHint = 4;
        lblBar.setLayoutData(gd);
        lblBar.addPaintListener(new PaintListener() {
        	public void paintControl(PaintEvent e) {
        		boolean expanded = optionHelper.isBarExpanded();
        		Color color = null;
        		if(mouseOnBar)
        			color = new Color(display, 0xA2, 0xD3, 0x75);
        		else
        			color = new Color(display, 0x5E, 0xB6, 0xF6);
        		Label lbl = (Label)e.getSource();
        		Rectangle rect = lbl.getBounds();
        		rect.x = rect.y = 0;
        		e.gc.setBackground(color);
        		e.gc.fillRectangle(rect);
        		color.dispose();
        		
        		int middle = rect.width >> 1;
        		color = new Color(display, 0x39, 0x69, 0xB9);
        		e.gc.setForeground(color);
        		if(expanded) {
        			e.gc.drawLine(middle - 3, 0, middle + 2, 0);
        			e.gc.drawLine(middle - 2, 1, middle + 1, 1);
        			e.gc.drawLine(middle - 1, 2, middle, 2);
        		} else {
        			e.gc.drawLine(middle - 1, 1, middle, 1);
        			e.gc.drawLine(middle - 2, 2, middle + 1, 2);
        			e.gc.drawLine(middle - 3, 3, middle + 2, 3);        			
        		}
        		color.dispose();
        	}
        });
        lblBar.addMouseTrackListener(new MouseTrackAdapter() {
        	@Override
        	public void mouseEnter(MouseEvent e) {
        		mouseOnBar = true;
        		((Label)e.getSource()).redraw();
        	}
        	@Override
        	public void mouseExit(MouseEvent e) {
        		mouseOnBar = false;
        		((Label)e.getSource()).redraw();
        	}
        });
        lblBar.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseUp(MouseEvent e) {
        		optionHelper.setBarExpanded(!optionHelper.isBarExpanded());
        		((Label)e.getSource()).redraw();
        	}
        });
        
        /* 按钮区 */        
        // 按钮容器
        Composite buttonContainer = new Composite(panel, SWT.NONE);
        gd = new GridData(GridData.FILL_HORIZONTAL);
        gd.heightHint = 24;
        buttonContainer.setLayoutData(gd);
        layout = new GridLayout(5, false);
        layout.marginHeight = layout.marginWidth = layout.verticalSpacing = 0;
        layout.horizontalSpacing = 6;
        buttonContainer.setLayout(layout);  
        buttonContainer.addPaintListener(new PaintListener() {
        	public void paintControl(PaintEvent e) {        		
        		paintBackground(e, ((Composite)e.getSource()).getClientArea().width, res.getImage(Resources.bmpToolbarBackground));
        	}
        });
        // 系统菜单
        btnSysMenu = new Label(buttonContainer, SWT.LEFT);
        btnSysMenu.setImage(res.getImage(Resources.bmpMenuNormal));
        gd = new GridData(GridData.FILL_VERTICAL);
        btnSysMenu.setLayoutData(gd);
        btnSysMenu.setToolTipText(tooltip_button_sysmenu);
        btnSysMenu.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseDown(MouseEvent e) {
        		btnSysMenu.setImage(res.getImage(Resources.bmpMenuDown));
        	}
    		@Override
			public void mouseUp(MouseEvent e) {     
        		btnSysMenu.setImage(res.getImage(Resources.bmpMenuHover));
        		if(menuHelper.isSystemMenuVisible())
        			menuHelper.setSystemMenuVisible(false);
        		else {
        			menuHelper.setSystemMenuData(0);
        			menuHelper.setSystemMenuLocation(btnSysMenu.toDisplay(btnSysMenu.getLocation()));
        			menuHelper.setSystemMenuVisible(true);        			
        		}
    		}
    	});
        btnSysMenu.addMouseTrackListener(new MouseTrackAdapter() {
        	@Override
        	public void mouseEnter(MouseEvent e) {
        		btnSysMenu.setImage(res.getImage(Resources.bmpMenuHover));
        	}
        	@Override
        	public void mouseExit(MouseEvent e) {
        		btnSysMenu.setImage(res.getImage(Resources.bmpMenuNormal));
        	}
        });
        // 系统消息按钮
        btnSysMsg = new Label(buttonContainer, SWT.CENTER);
        btnSysMsg.setImage(res.getImage(Resources.icoSysMsg));
        btnSysMsg.setToolTipText(tooltip_button_sysmsg);
        btnSysMsg.setLayoutData(new GridData(GridData.FILL_VERTICAL));
        btnSysMsg.setCursor(display.getSystemCursor(SWT.CURSOR_HAND));
        btnSysMsg.addMouseListener(new MouseAdapter() {
    		@Override
			public void mouseUp(MouseEvent e) {     
    			if(mq.hasSystemMessage()) {
    				InPacket in = mq.getSystemMessage();
    				shellLauncher.openReceiveSystemMessageShell(in);
    				if(in instanceof SystemNotificationPacket) {
    					SystemNotificationPacket packet = (SystemNotificationPacket)in;
    					if(packet.type == QQ.QQ_SYS_ADD_FRIEND_APPROVED ||
    							packet.type == QQ.QQ_SYS_ADD_FRIEND_APPROVED_AND_ADD)
    						blindHelper.addFriend(packet.from);    					
    				}
    			} else
    				shellLauncher.openSystemMessageListWindow();
    		}
    	});
        btnSysMsg.addPaintListener(new PaintListener() {
        	public void paintControl(PaintEvent e) {
        		Label label = (Label)e.getSource();
        		Rectangle bound = label.getBounds();
        		paintBackground(e, bound.width, res.getImage(Resources.bmpToolbarBackground));
        		if(label.getImage() != null)
        			e.gc.drawImage(label.getImage(), 0, (bound.height - label.getImage().getBounds().height) >>> 1);
        	}
        });
        // 添加查找按钮
        Label btnFind = new Label(buttonContainer, SWT.CENTER);
        btnFind.setImage(res.getImage(Resources.icoSearch));
        btnFind.setToolTipText(tooltip_button_search);
        btnFind.setLayoutData(new GridData(GridData.FILL_VERTICAL));
        btnFind.setCursor(display.getSystemCursor(SWT.CURSOR_HAND));
        btnFind.addMouseListener(new MouseAdapter() {
    		@Override
			public void mouseUp(MouseEvent e) {
    			if(client.getUser().isLoggedIn())
    				shellLauncher.openSearchWizard();
    		}
    	});
        btnFind.addPaintListener(new PaintListener() {
        	public void paintControl(PaintEvent e) {
        		Label label = (Label)e.getSource();
        		Rectangle bound = label.getBounds();
        		paintBackground(e, bound.width, res.getImage(Resources.bmpToolbarBackground));
        		e.gc.drawImage(label.getImage(), 0, (bound.height - label.getImage().getBounds().height) >>> 1);
        	}
        });
        // 短信按钮
        btnSMS = new Label(buttonContainer, SWT.CENTER);
		btnSMS.setImage(res.getImage(Resources.icoMobile));
        btnSMS.setToolTipText(tooltip_button_sms);
        btnSMS.setCursor(display.getSystemCursor(SWT.CURSOR_HAND));
        btnSMS.setLayoutData(new GridData(GridData.FILL_VERTICAL));
        btnSMS.addMouseListener(new MouseAdapter() {
    		@Override
			public void mouseUp(MouseEvent e) {     
    		    onSMS();
    		}
    	});
        btnSMS.addPaintListener(new PaintListener() {
        	public void paintControl(PaintEvent e) {
        		Label label = (Label)e.getSource();
        		Rectangle bound = label.getBounds();
        		paintBackground(e, bound.width, res.getImage(Resources.bmpToolbarBackground));
        		if(label.getImage() != null)
        			e.gc.drawImage(label.getImage(), 0, (bound.height - label.getImage().getBounds().height) >>> 1);
        	}
        });
        // 状态指示
        statusRing = new Ring(buttonContainer);
        gd = new GridData(GridData.FILL_BOTH);
        statusRing.setLayoutData(gd);
        statusRing.setMinWidth(18);
        statusRing.setBackground(Colors.MAINSHELL_BACKGROUND);
        statusRing.setTiledBackground(res.getImage(Resources.bmpToolbarBackground));
        statusRing.setMaxAvailableHeight(18);
        statusRing.setCursor(display.getSystemCursor(SWT.CURSOR_HAND));
        statusRing.addMouseListener(new MouseAdapter() {
    		@Override
			public void mouseUp(MouseEvent e) {        			
    			menuHelper.setStatusMenuLocation(statusRing.toDisplay(e.x, e.y));
    			menuHelper.setStatusMenuVisible(true);
    		}
    	});
        
        return panel;
	}

	/**
	 * 初始化好友视图
	 */
	private void initFriendView() {
        // 好友视图 
        blind = new Blind(viewContainer, SWT.BORDER);
        views[VIEW_FRIEND] = blind;
	}

	/**
	 * 初始化shell和边框，等等
	 */
	private Composite initShell() {
		int shellStyle = SWT.NO_TRIM | SWT.NO_BACKGROUND;
		if(optionHelper.isOnTop())
			shellStyle |= SWT.ON_TOP;
		shell = new Shell(display, shellStyle);
		shell.setImage(res.getImage(Resources.icoLumaQQ));
		// 添加事件监听器
		shell.addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
		    	if(font != null)
		    		font.dispose();
		    	if(defaultStyle != null)
		    		if(defaultStyle.foreground != null) 
		    			defaultStyle.foreground.dispose();
		    	if(groupColor != null) 
		    		groupColor.dispose();
		    	Colors.dispose();
			}
		});
		shell.addShellListener(new ShellAdapter() {
			@Override
			public void shellIconified(ShellEvent e) {		
				if(item != null && !item.getVisible()) {
					initTray();
					uiHelper.setTrayIconByStatus();
				}
				menuHelper.hideAllMenu();
			}
			
			@Override
			public void shellClosed(ShellEvent e) {
			    // 上传分组
			    checkGroupDirty();
			    
			    // 关闭Client
			    if(processor != null && client != null) 
			    	client.removeQQListener(processor);
			    // 注销
				if(client != null) {
					client.logout();
					client.release();
				}
				// 关闭定时器
				timerHelper.dispose();
				// 保存用户信息文件
				configHelper.saveSelf();
				// 保存好友分组信息
				blindHelper.saveModel();
				// 释放Tray Icon
				if(item != null) 
					item.dispose();
				// 停止声音线程
				if(sounder != null) 
					sounder.setStop(true);
				// 保存当前设置
				if(optionHelper.getOptionsModel() != null) {
				    // 保存窗口位置大小
					if(isOff()) {
						Rectangle bound = shell.getBounds();
						optionHelper.setLocationX(bound.x);
						optionHelper.setLocationY(bound.y);
						optionHelper.setWidth(bound.width);
						optionHelper.setHeight(bound.height);						
					}
					// 保存字体信息
					if(defaultStyle != null) {
					    optionHelper.setFontName(defaultStyle.fontName);
					    optionHelper.setFontSize(defaultStyle.fontSize);
					    optionHelper.setBold((defaultStyle.fontStyle & SWT.BOLD) != 0);
					    optionHelper.setItalic((defaultStyle.fontStyle & SWT.ITALIC) != 0);			    
					}
					// 保存字体颜色
					if(defaultStyle.foreground != null) {
						RGB rgb = defaultStyle.foreground.getRGB();
					    optionHelper.setFontColor(rgb);
					}
					optionHelper.save();			
				}
				// 保存表情文件
				FaceRegistry.getInstance().save();
				// 关闭消息队列
				if(rm != null) 
					rm.dispose();
				// 关闭群分类文件
				if(ccu != null)
					ccu.dispose();
			}
		});
		shell.addControlListener(new ControlAdapter() {
			@Override
			public void controlMoved(ControlEvent e) {
				shellLocation = shell.getLocation();
			}
		});

		BorderStyler styler = new BorderStyler(this);
		styler.setCheckMinimizeWhenClose(true);
		if(!optionHelper.isOnTop())
			styler.setHideWhenMinimize(optionHelper.isHideWhenMinimize());
		styler.setMaximizeWhenDoubleClick(false);

		styler.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				menuHelper.hideAllMenu();
			}
		});
		return styler.decorateShell(shell);
	}

	/**
	 * 画背景
	 * 
	 * @param e
	 * @param clientWidth
	 */
	protected void paintBackground(PaintEvent e, int clientWidth, Image tile) {
		int backWidth = tile.getBounds().width;
		int x = 0;
		while(x < clientWidth) {
			e.gc.drawImage(tile, x, 0);
			x += backWidth;
		}
	}

	/**
	 * @return Returns the configHelper.
	 */
	public ConfigHelper getConfigHelper() {
		return configHelper;
	}

	public MessageHelper getMessageHelper() {
		return messageHelper;
	}

	public User getMyModel() {
		return myModel;
	}

	public List<User> getCurrentOnlines() {
		return currentOnlines;
	}

	public TipHelper getTipHelper() {
		return tipHelper;
	}

	public UIHelper getUIHelper() {
		return uiHelper;
	}

    /**
     * 设置QQ在线人数
     * 
     * @param currentOnlineNumber
     * 		当前QQ在线人数
     */
    public void setCurrentOnlineNumber(String currentOnlineNumber) {
        this.currentOnlineNumber = currentOnlineNumber;
    }

	/**
	 * 删除一个成功或者失败的删除好友请求
	 * 
	 * @param key
	 * 			好友的QQ号
	 */
	public Group removeDeleteToMap(int key) {
		return deleteToMap.remove(key);
	}

	public void setGroupDirty(boolean groupDirty) {
		this.groupDirty = groupDirty;
	}

	/**
	 * 设置窗口可见状态
	 * 
	 * @param b
	 * 			true表示窗口可见
	 */
	public void setVisible(boolean b) {
	    shell.setVisible(b);
	}

	public BlindHelper getBlindHelper() {
		return blindHelper;
	}

	public void qqShowDownloaded(final int qq) {
		if(shellRegistry.hasUserInfoWindow(qq) && sm.isCached(qq)) {
			display.syncExec(new Runnable() {
				public void run() {					
				    UserInfoWindow uis = shellRegistry.getUserInfoWindow(qq);
				    if(uis != null)
				        uis.setQQShow(sm.getQQShowImage(qq));						
				}
			});
		}
	}

	/**
	 * @return Returns the groupColor.
	 */
	public Color getGroupColor() {
		return groupColor;
	}

	public QQShowManager getQQShowManager() {
		return sm;
	}

    /**
     * @return
     * 		ShellLauncher对象
     */
    public ShellLauncher getShellLauncher() {
        return shellLauncher;
    }

    /**
     * @return
     * 		窗口注册表
     */
    public ShellRegistry getShellRegistry() {
        return shellRegistry;
    }

	public Sounder getSounder() {
		return sounder;
	}

	public ExportHelper getExportHelper() {
		return exportHelper;
	}

	public String getCurrentOnlineNumber() {
		return currentOnlineNumber;
	}

	public MessageQueue getMessageQueue() {
		return mq;
	}

	public IPSeeker getIPSeeker() {
		return seeker;
	}

	public LineStyle getDefaultStyle() {
		return defaultStyle;
	}

	public Color getFontColor() {
		return defaultStyle.foreground;
	}
	
    /**
     * 设置字体颜色
     * 
     * @param fontColor
     * 		字体颜色
     */
    public void setFontColor(Color fontColor) {
        defaultStyle.foreground = fontColor;
    }

	public RecordManager getRecordManager() {
		return rm;
	}
    
	/**
	 * 删除一个好友
	 * 
	 * @param f
	 * 			User
	 * @param directly
	 * 			表示是直接删除还是从服务器删除
	 * @param removeSelf
	 * 			是否把自己从对方的好友列表中删除
	 */
	public void deleteFriend(User f, boolean directly, boolean removeSelf) {
		if(directly && f != null) {
			Group g = f.group;
			g.removeUser(f);
			if(g.users.size() == 0)
				blindHelper.collapseGroup(g);
			blindHelper.refreshGroup(g);	
		} else {
			// 请求服务器删除该好友
			deleteFriendFromServer(f.qq, true, removeSelf, null);
		}
	}
	
    /**
	 * 从服务器上删除一个好友
	 * 
	 * @param qqNum
	 * 			要删除的QQ号，为0表示不执行删除动作
	 * @param doDelete
	 * 			表示是否从向服务器发请求要求删除这个好友，如果这个QQ号不在我好友列表里面就不需要
	 * @param removeSelf
	 * 			表示是否把自己也从这个好友的列表中删除
	 * @param g
	 * 			表示这个好友删除后把它放置到哪个组，如果为null则忽略
	 */
	public void deleteFriendFromServer(int qqNum, boolean doDelete, boolean removeSelf, Group g) {
		// 如果删除请求包发送成功，则加入到删除请求哈希表中
		if(doDelete)
			client.user_Delete(qqNum);
		if(removeSelf)
			client.user_RemoveSelfFrom(qqNum);
		if(g != null)
			deleteToMap.put(qqNum, g);
	}
	
	public Label getSystemMenuButton() {
		return btnSysMenu;
	}
	
	public Label getSMSButton() {
		return btnSMS;
	}
	
	/**
	 * 处理
	 * @param e
	 */
	public void handleRuntimeError(QQEvent e) {
	    // 隐藏窗口
		stopWaitingPanelAnimation();
		setVisible(false);
		// 退出登录
		getClient().getUser().setStatus(QQ.QQ_STATUS_OFFLINE);
		logout();
		// 打开报告窗口
		CrashDialog dialog = new CrashDialog(this, ((ErrorPacket)e.getSource()).errorMessage);
		dialog.open();
	}
	
	/**
	 * 重新开始登录流程
	 * 
	 * @param e
	 */
	public void restartLogin(QQEvent e) {
	    // 隐藏窗口
		stopWaitingPanelAnimation();
		setVisible(false);
		// 退出登录
		getClient().getUser().setStatus(QQ.QQ_STATUS_OFFLINE);
		logout();
	    // 显示错误提示框
		if(e != null) {
			String msg = "";
			if(e.getSource() instanceof LoginReplyPacket)
				msg = ((LoginReplyPacket)e.getSource()).replyMessage;
			else if(e.getSource() instanceof ErrorPacket)
				msg = ((ErrorPacket)e.getSource()).errorMessage;
			MessageDialog.openError(getShell(), 
					message_box_login_fail_title, 
					(msg == null || msg.trim().equals("")) ? error_login_fail : msg);			
		}
		relaunchLoginDialog();
	}
	
	/**
	 * 重新打开登录对话框
	 */
	public void relaunchLoginDialog() {
		// 再次显示登录对话框
		LoginDialog login = new LoginDialog(getShell(), true);
		if(login.open()) {
			// 创建QQ用户对象
			QQUser me = new QQUser(login.getQQ(), login.getMd5Password());
			if(login.isLoginHidden()) {
				me.setLoginMode(QQ.QQ_LOGIN_MODE_HIDDEN);
				me.setStatus(QQ.QQ_STATUS_HIDDEN);
			} else {
				me.setLoginMode(QQ.QQ_LOGIN_MODE_NORMAL);
				me.setStatus(QQ.QQ_STATUS_ONLINE);
			}
			// 设置用户对象
			getClient().setUser(me);
			// 初始化用户文件路径
			LumaQQ.initUserFilePath(me);
			// 设置client，相当于刷新各种数据
			setClient(getClient());
			// 检查是否使用登录框的网络设置
			if(login.isUseNetworkSetting()) 
				LumaQQ.syncLoginOption(login.getLogins().getNetwork(), optionHelper);
			// relayout
			getBlind().layout();
			// 打开主界面
			setVisible(true);
			setWaitingPanelHint(hint_login);
			resumeWaitingPanelAnimation();
			checkLogin(false, false);
		} else
			close();
	}

	/**
	 * 改变当前用户
	 */
	public void changeUser() {
		LoginDialog login = new LoginDialog(shell, true);
		if(login.open()) {
			int qqNum = login.getQQ();
			if(myModel.qq != qqNum) {
				// 重设界面
				switchPanel(PANEL_WAITING);
				setWaitingPanelHint(hint_login);
				resumeWaitingPanelAnimation();
				// 登出
				client.logout();
				uiHelper.setTrayIconByStatus();
				// 创建QQ用户对象
				QQUser me = new QQUser(qqNum, login.getMd5Password());
				if(login.isLoginHidden()) {
					me.setLoginMode(QQ.QQ_LOGIN_MODE_HIDDEN);
					me.setStatus(QQ.QQ_STATUS_HIDDEN);
				} else {
					me.setLoginMode(QQ.QQ_LOGIN_MODE_NORMAL);
					me.setStatus(QQ.QQ_STATUS_ONLINE);
				}
				// 创建QQ客户端对象
				client.setUser(me);
				// 初始化用户文件路径
				LumaQQ.initUserFilePath(me);
				// 设置client
				setClient(this.client);
				// 检查是否使用登录框的网络设置
				if(login.isUseNetworkSetting()) 
					LumaQQ.syncLoginOption(login.getLogins().getNetwork(), optionHelper);
				// 设置tray的tooltip
				if(item != null)
					item.setToolTipText("LumaQQ " + String.valueOf(myModel.qq));
				// 登陆
				checkLogin(false, false);
			}							
		}	
	}

	public ClusterCustomFaceReceiver getFaceReceiver() {
		return faceReceiver;
	}

	/**
	 * @param f
	 */
	public void addOnline(User f) {
		currentOnlines.add(f);
	}
	
	public Label getSystemMessageButton() {
		return btnSysMsg;
	}

	public TrayItem getTrayItem() {
		return item;
	}

	public Ring getStatusRing() {
		return statusRing;
	}

	/**
	 * @return Returns the optionHelper.
	 */
	public OptionHelper getOptionHelper() {
		return optionHelper;
	}
	
	public ClusterCategoryTool getClusterCategoryUtility() {
		return ccu;
	}

	/**
	 * 收到提取消息按键时调用此方法
	 */
	public void onMessageKey() {
		if(mq.hasNext()) {
			populateMessage();
		} else {
			menuHelper.setStatusMenuVisible(false);
			if(shell.getMinimized()) {
				shell.setLocation(shellLocation);
				shell.setMinimized(false);
				shell.setVisible(true);				
			} else if(isAutoDockEnabled() && isDocking())
				pend();
		}
	}

	public IHotkeyListener getMessageKeyListener() {
		return messageKeyListener;
	}

	public Listener getFalseMessageKeyListener() {
		return falseMessageKeyListener;
	}
	
	public void off() {
		((AutoDockManager)getShell().getData(AutoDockManager.DOCK_MANAGER)).off();
	}
	
	private boolean isAutoDockEnabled() {
		return getShell().getData(AutoDockManager.DOCK_MANAGER) != null;
	}
	
	/**
	 * 主窗口是否停靠状态
	 * 
	 * @return
	 */
	private boolean isDocking() {
		return ((AutoDockManager)getShell().getData(AutoDockManager.DOCK_MANAGER)).isDocking();
	}
	
	/**
	 * 主窗口是否在屏幕边缘
	 * 
	 * @return
	 */
	private boolean isPending() {
		return ((AutoDockManager)getShell().getData(AutoDockManager.DOCK_MANAGER)).isPending();
	}
	
	/**
	 * 主窗口是否没有dock，也没有pending
	 * 
	 * @return
	 */
	public boolean isOff() {
		AutoDockManager manager = (AutoDockManager)getShell().getData(AutoDockManager.DOCK_MANAGER);
		if(manager == null)
			return true;
		else
			return manager.isOff();
	}

	/**
	 * 按了短信按钮
	 */
	private void onSMS() {
		ReceiveIMPacket packet = (ReceiveIMPacket)mq.getSMS();
		if(packet == null)
			shellLauncher.openSMSWindow();
		else {
			if(packet.sms.sender == 10000) {
				/*
				 * 打开普通手机消息
				 */
				String mobile = packet.sms.senderName;
				SMSWindow window = shellLauncher.openSMSWindow(mobile);
				window.putSMS(packet);
				packet = (ReceiveIMPacket)mq.getSMS(mobile);
				while(packet != null) {
					window.putSMS(packet);
					packet = (ReceiveIMPacket)mq.getSMS(mobile);					
				}
			} else{
				/*
				 * 打开移动QQ和绑定手机用户消息
				 */
				User f = ModelRegistry.getUser(packet.sms.sender);
				if(f == null) {
					f = new User();
					f.qq = packet.sms.sender;
				}
				SMSWindow window = shellLauncher.openSMSWindow(f);
				window.putSMS(packet);
				packet = (ReceiveIMPacket)mq.getSMS(f.qq);
				while(packet != null) {
					window.putSMS(packet);
					packet = (ReceiveIMPacket)mq.getSMS(f.qq);					
				}
			}
			uiHelper.stopBlinkSMSIcon();
			uiHelper.resetTrayImageEffect();
		}
		
	}

	public TimerHelper getTimerHelper() {
		return timerHelper;
	}

	public void setTimerHelper(TimerHelper timerHelper) {
		this.timerHelper = timerHelper;
	}

	/**
	 * @return the currentPanel
	 */
	public int getCurrentPanel() {
		return currentPanel;
	}

	/**
	 * @return the jobQueue
	 */
	public IExecutor getDiskJobQueue() {
		return diskJobQueue;
	}

	/**
	 * @return the longTimeJobQueue
	 */
	public IExecutor getIMJobQueue() {
		return imJobQueue;
	}
}
