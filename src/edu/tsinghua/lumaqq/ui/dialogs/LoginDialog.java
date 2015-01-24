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
package edu.tsinghua.lumaqq.ui.dialogs;

import static edu.tsinghua.lumaqq.resource.Messages.*;
import static org.apache.commons.codec.digest.DigestUtils.md5;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
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
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import edu.tsinghua.lumaqq.LumaQQ;
import edu.tsinghua.lumaqq.ecore.LoginOption;
import edu.tsinghua.lumaqq.ecore.ProxyType;
import edu.tsinghua.lumaqq.ecore.login.Login;
import edu.tsinghua.lumaqq.ecore.login.LoginFactory;
import edu.tsinghua.lumaqq.ecore.login.Logins;
import edu.tsinghua.lumaqq.eutil.LoginUtil;
import edu.tsinghua.lumaqq.models.ModelUtils;
import edu.tsinghua.lumaqq.qq.Util;
import edu.tsinghua.lumaqq.resource.Colors;
import edu.tsinghua.lumaqq.resource.Resources;
import edu.tsinghua.lumaqq.ui.BorderStyler;
import edu.tsinghua.lumaqq.ui.helper.UITool;
import edu.tsinghua.lumaqq.ui.listener.AroundBorderPaintListener;
import edu.tsinghua.lumaqq.widgets.qstyle.Slat;


/**
 * 登陆对话框
 *
 * @author luma
 * @author starboy
 */
public class LoginDialog extends Dialog {
    // Log对象
    protected static Log log = LogFactory.getLog(LoginDialog.class);
    // Shell
    private Shell dialog;
    // 是否记住密码
    private boolean rememberPassword;
    // 是否隐身登陆
    private boolean loginHidden;
    // 是否自动登录
    private boolean autoLogin;
    // 是否忽略自动登录选项
    private boolean ignoreAuto;
    // 用户QQ号
    private int qqNum;
    // 密码的MD5形式
    private byte[] md5pwd;
    // 所有组件
    private Slat btnLogin, btnCancel;
    private Button chkRemember, chkHidden, chkAuto;
    private Text text;
    private CCombo combo;
    // 用户是否点了登陆按钮
    private boolean ok;
    // 登陆历史信息文件根元素对象
    private Logins logins;
    // 用户这次是否修改了密码
    private boolean changed;
    // 是否使用登录框的网络设置
    private boolean useNetworkSetting;
    // IconHolder实例
    private Resources res = Resources.getInstance();
    // Base64 codec
    private Base64 codec = new Base64();
	private Composite networkContainer;
	private CCombo comboServer;
	private CCombo comboPort;
	private Button chkAutoSelect;
	private Button rdoUdp;
	private Button rdoTcp;
	private Text textProxyServer;
	private Text textProxyPort;
	private Text textUsername;
	private Text textPassword;
	private CCombo comboProxyType;
	
	// 没有显示网络设置时的大小
	private Rectangle smallRect;
    
    /**
     * 构造函数
     * 
     * @param shell
     * 		父窗口
     * @param ignoreAuto
     * 		true表示忽略掉自动登录选项，不要自动返回
     */
    public LoginDialog(Shell shell, boolean ignoreAuto) {
        super(shell, SWT.NO_TRIM | SWT.NO_BACKGROUND);
        this.ignoreAuto = ignoreAuto;
    }

    /**
     * @param dialog
     */
    private void initLayout() {  
    	BorderStyler styler = new BorderStyler();
    	styler.setHideWhenMinimize(false);
    	styler.setResizable(false);
    	Composite center = styler.decorateShell(dialog);
    	
        GridLayout layout = new GridLayout();
        layout.marginHeight = layout.marginWidth = layout.verticalSpacing = layout.horizontalSpacing = 0;
        center.setLayout(layout);
        center.setBackground(Colors.LOGIN_BACKGROUND);

        UITool.setDefaultBackground(Colors.LOGIN_BACKGROUND);
        
        //logo
        Label lblLogo = UITool.createLabel(center, "", SWT.CENTER);
        setLogo(lblLogo);
        
        layout = new GridLayout(4, false);
        layout.marginHeight = layout.marginWidth = 25;
        layout.verticalSpacing = 14;
        layout.horizontalSpacing = 10;        
        Composite c = UITool.createContainer(center, new GridData(GridData.FILL_BOTH), layout);
        c.addPaintListener(new PaintListener() {
            public void paintControl(PaintEvent e) {
                Rectangle rect = ((Composite)e.getSource()).getClientArea();
                rect.x = rect.y = 5;
                rect.width -= 10;
                rect.height -= 10;
                e.gc.setForeground(Colors.PAGE_CONTROL_BORDER);
                e.gc.drawRectangle(rect);
            }
        });
        c.addPaintListener(new AroundBorderPaintListener(new Class[] { Text.class, CCombo.class }, Colors.PAGE_CONTROL_BORDER));

        // 号码
        UITool.createLabel(c, login_qq_number);
        // 号码下拉框
        GridData gd = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
        gd.horizontalSpan = 2;
        gd.heightHint = 18;
        combo = UITool.createCCombo(c, gd, SWT.FLAT);
        combo.setBackground(Colors.WHITE);
        // placeholder
        UITool.createLabel(c, "");
        
        // 密码
        UITool.createLabel(c, login_qq_password);
        // 密码框
        gd = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
        gd.horizontalSpan = 2;
        gd.heightHint = 18;
        text = UITool.createSingleText(c, gd, SWT.SINGLE | SWT.PASSWORD);
        text.setBackground(Colors.WHITE);
        text.setEchoChar('*');
        // placeholder
        UITool.createLabel(c, "");
        
        // placeholder
        UITool.createLabel(c, "");        
        // 记住密码
        chkRemember = UITool.createCheckbox(c, login_remember_password);
        // 隐身登陆
        chkHidden = UITool.createCheckbox(c, login_hidden, new GridData(GridData.HORIZONTAL_ALIGN_END));
        // 自动登录
        chkAuto = UITool.createCheckbox(c, login_auto);
        chkAuto.addSelectionListener(new SelectionAdapter() {
        	@Override
        	public void widgetSelected(SelectionEvent e) {
        		if(chkAuto.getSelection())
        			chkRemember.setSelection(true);
        		chkRemember.setEnabled(!chkAuto.getSelection());
        	}
        });
        
        // 按钮区容器
        layout = new GridLayout(3, false);
        layout.horizontalSpacing = 10;
        c = UITool.createContainer(center, new GridData(GridData.FILL_HORIZONTAL), layout);
        
        // 网络设置
        gd = new GridData();
        gd.widthHint = 80;
        Slat btnNetwork = UITool.createSlat(c, login_network, gd);
        btnNetwork.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseUp(MouseEvent e) {
        		switchNetworkSetting(); 
        	}
        });
        // 登录
        gd = new GridData(GridData.HORIZONTAL_ALIGN_END);
        gd.grabExcessHorizontalSpace = true;
        gd.widthHint = 70;
        btnLogin = UITool.createSlat(c, button_login, gd);
        // 取消
        gd = new GridData();
        gd.widthHint = 70;
        btnCancel = UITool.createSlat(c, button_cancel, gd); 
        
        gd = new GridData(GridData.FILL_HORIZONTAL);
        gd.exclude = true;
        networkContainer = UITool.createContainer(center, gd, new GridLayout());
		// 网络设置组
        Group netGroup = UITool.createGroup(networkContainer, login_network, new GridLayout());

        layout = new GridLayout(4, false);
        layout.marginWidth = layout.marginHeight = 0;
        Composite comp = UITool.createContainer(netGroup, new GridData(GridData.FILL_HORIZONTAL), layout);
        // 登录方式
        UITool.createLabel(comp, sys_opt_group_login);
        rdoUdp = UITool.createRadio(comp, sys_opt_login_method_udp);
		rdoTcp = UITool.createRadio(comp, sys_opt_login_method_tcp);
		chkAutoSelect = UITool.createCheckbox(comp, sys_opt_login_server_random);
        chkAutoSelect.setSelection(true);
		rdoTcp.setLayoutData(gd);
        rdoTcp.setSelection(true);
        rdoUdp.addSelectionListener(new SelectionAdapter() {
        	@Override
        	public void widgetSelected(SelectionEvent e) {
        		switchMethod(true);
        	}
        });
        rdoTcp.addSelectionListener(new SelectionAdapter() {
        	@Override
        	public void widgetSelected(SelectionEvent e) {
        		switchMethod(false);
        	}
        });
        
        layout = new GridLayout(4, false);
        layout.marginHeight = 1;
        layout.marginWidth = 0;
        layout.marginRight = 1;
        layout.verticalSpacing = 7;
        comp = UITool.createContainer(netGroup, new GridData(GridData.FILL_HORIZONTAL), layout);        
        comp.addPaintListener(new AroundBorderPaintListener(new Class[] { CCombo.class, Text.class }, Colors.PAGE_CONTROL_BORDER));
        // 服务器地址
        UITool.createLabel(comp, sys_opt_group_server);
        comboServer = UITool.createCCombo(comp, new GridData(GridData.FILL_HORIZONTAL), SWT.FLAT);
		// 端口
        UITool.createLabel(comp, sys_opt_login_proxy_port);
        comboPort = UITool.createCCombo(comp, new GridData(GridData.FILL_HORIZONTAL));
        comboPort.add("80");
        comboPort.add("443");
        comboPort.select(0);
        // 代理服务器类型
        UITool.createLabel(comp, login_proxy_type);
        gd = new GridData(GridData.FILL_HORIZONTAL);
        gd.horizontalSpan = 3;
        comboProxyType = UITool.createCCombo(comp, gd);
		comboProxyType.add(login_no_proxy);
		comboProxyType.add(login_http);
        comboProxyType.add(login_socks5);
        comboProxyType.addSelectionListener(new SelectionAdapter() {
        	@Override
        	public void widgetSelected(SelectionEvent e) {
        		selectProxy(comboProxyType.getSelectionIndex());
        	}
        });
        // 服务器地址
        UITool.createLabel(comp, login_proxy_server);
        textProxyServer = UITool.createSingleText(comp, new GridData(GridData.FILL_HORIZONTAL));
		// 服务器端口
        UITool.createLabel(comp, login_proxy_port);
        textProxyPort = UITool.createSingleText(comp, new GridData(GridData.FILL_HORIZONTAL));
		// 服务器用户名
        UITool.createLabel(comp, login_proxy_username);
        textUsername = UITool.createSingleText(comp, new GridData(GridData.FILL_HORIZONTAL));
		// 服务器密码
        UITool.createLabel(comp, login_proxy_password);
        textPassword = UITool.createSingleText(comp, new GridData(GridData.FILL_HORIZONTAL), SWT.SINGLE | SWT.PASSWORD);
        
		switchMethod(false);
    }    
    
    /**
     * 选择代理类型
     * 
     * @param i
     */
    private void selectProxy(int i) {
    	switch(i) {
    		case 0:
    			logins.getNetwork().setProxyType(ProxyType.NONE_LITERAL);
    			textProxyServer.setEnabled(false);
    			textProxyPort.setEnabled(false);
    			textUsername.setEnabled(false);
    			textPassword.setEnabled(false);
    			Color color = dialog.getDisplay().getSystemColor(SWT.COLOR_WIDGET_BACKGROUND);
    			textProxyServer.setBackground(color);
    			textProxyPort.setBackground(color);
    			textUsername.setBackground(color);
    			textPassword.setBackground(color);
    			break;
    		case 1:
    			logins.getNetwork().setProxyType(ProxyType.HTTP_LITERAL);
    			textProxyServer.setEnabled(true);
    			textProxyPort.setEnabled(true);
    			textUsername.setEnabled(true);
    			textPassword.setEnabled(true);
    			textProxyServer.setBackground(Colors.LOGIN_BACKGROUND);
    			textProxyPort.setBackground(Colors.LOGIN_BACKGROUND);
    			textUsername.setBackground(Colors.LOGIN_BACKGROUND);
    			textPassword.setBackground(Colors.LOGIN_BACKGROUND);
    			break;
    		case 2:
    			logins.getNetwork().setProxyType(ProxyType.SOCKS5_LITERAL);
    			textProxyServer.setEnabled(true);
    			textProxyPort.setEnabled(true);
    			textUsername.setEnabled(true);
    			textPassword.setEnabled(true);
    			textProxyServer.setBackground(Colors.LOGIN_BACKGROUND);
    			textProxyPort.setBackground(Colors.LOGIN_BACKGROUND);
    			textUsername.setBackground(Colors.LOGIN_BACKGROUND);
    			textPassword.setBackground(Colors.LOGIN_BACKGROUND);
    			break;
    	}
	}

	/**
     * 切换登录方式
     * 
     * @param udp
     */
    protected void switchMethod(boolean udp) {
    	fillServers(udp);
    	comboServer.select(0);
    	comboPort.setEnabled(!udp);
	}

	/**
     * 填充服务器
     * 
     * @param udp
     */
    private void fillServers(boolean udp) {
    	comboServer.removeAll();
    	String[] servers = udp ? LumaQQ.udpServers : LumaQQ.tcpServers;
    	for(String s : servers)
    		comboServer.add(s);
    }
	
    /**
     * 初始化监听器
     */
    private void initListeners() {
        // 取消按钮鼠标事件
        btnCancel.addMouseListener(new MouseAdapter() {
            @Override
			public void mouseUp(MouseEvent e) {
                ok = false;
                dialog.close();
            }
        });
        // 登陆按钮鼠标事件
        btnLogin.addMouseListener(new MouseAdapter() {
            @Override
			public void mouseUp(MouseEvent e) {
                doOk();
            }
        });
        // qq号输入框选择事件
        combo.addSelectionListener(new SelectionAdapter() {
            @Override
			public void widgetSelected(SelectionEvent e) {
                // 查找选择的号码的信息，重新设置控件的value
                Login login = LoginUtil.findLogin(logins, combo.getText());
                if(login == null) {
                    log.error("登陆历史信息文件有错误");
                    return;
                }
                setInitValue(login);
            }
        });        
        // 密码框的输入事件
        text.addKeyListener(new KeyAdapter() {
            @Override
			public void keyPressed(KeyEvent e) {
                if(e.character == SWT.CR) {
                    doOk();
                }
            }
        });
        // 密码框的改变事件
        text.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				changed = true;
			}        		
    	});        
    }
    
    private byte[] getTrianglePasswordKey(byte[] passwordKey) {
        return md5(new String(codec.encode(passwordKey)).getBytes());
    }
    
    /**
     * 用户按了登录按钮时调用这个方法
     */
	@SuppressWarnings("unchecked")
    private void doOk() {
    	String msg = validate();
    	msg = null;
    	if(msg != null) {
    		
    		MessageDialog.openError(dialog, "", msg);
    		return;
    	}
    	
        ok = true;
        // 保存当前输入
        saveInput();
        // 得到登陆历史信息文件对象，如果不存在，创建一个
        File loginHistory = new File(LumaQQ.LOGIN_HISTORY);
        if(logins == null)
        	logins = ModelUtils.createDefaultLogins();
        // 检查是否当前登陆是新的记录
        Login login = LoginUtil.findLogin(logins, combo.getText());
        if(login == null) {
	        // 如果没有，把新的登陆信息加入
	        login = LoginFactory.eINSTANCE.createLogin();
	        // 设置登陆信息
	        setLoginValue(login);
	        // 添加login
	        logins.getLogin().add(login);	      
	        
	        /*
	         * 这里新建了一个临时列表来排序，因为一个很奇怪的bug
	         * 使用logins.getLogin()得到的列表，排序时会出错误，
	         * 确实没有道理，也许是EMF的bug
	         */
	        List<Login> temp = new ArrayList<Login>(logins.getLogin());
	        // 排序所有的登陆信息，按照QQ号从小到大排
	        Collections.sort(temp, new Comparator<Login>() {
	        	public int compare(Login l1, Login l2) {
	                try {
	        	        int qq1 = Integer.parseInt(l1.getQq());
	        	        int qq2 = Integer.parseInt(l2.getQq());
	        	        return qq1 - qq2;
	                } catch (NumberFormatException e) {
	                    return 0;
	                }
	        	}
	        });    
	        logins.getLogin().clear();
	        logins.getLogin().addAll(temp);
        } else {
        	setLoginValue(login);
        }
        // 修改最后一次登陆的qq号信息
        logins.setLastLogin(combo.getText());
        // 写入文件
        LoginUtil.save(loginHistory, logins);
        
        dialog.close();
    }
    
    /**
     * 把当前信息存入Login对象中
     * @param login Login对象
     */
    private void setLoginValue(Login login) {
        /*
         * 目前我采用了一个临时的办法，对于没有选择记住密码的人，把它的md5key做了base64之后
         * 再做一次md5，再来一次base64，这样的话应该就安全了，即时它把字符串用base64解密
         * 也没用了，但是对于我比较密码还是有用，就是在修改个人信息的时候那个密码，就是
         * 那个地方还要比较一下密码。
         * 不过，对付暴力破解并没有用，QQ是做了几十万次的MD5来保存密码，我不想做那么多
         * 次，Java做不来那么多次，写JNI又懒的动手，就这样吧 
         */
        login.setQq(combo.getText());
        login.setRememberPassword(autoLogin || rememberPassword);
        login.setLoginHidden(loginHidden);
        login.setAutoLogin(autoLogin);
        if(rememberPassword)
            login.setPassword(new String(codec.encode(md5pwd)));
        else
        	login.setPassword(new String(codec.encode(getTrianglePasswordKey(md5pwd))));
    }
    
    /**
     * 初始化控件的值
     */
	@SuppressWarnings("unchecked")
    private void initValue() {
    	// 检查配置文件，如果不存在则创建一个
    	File loginHistory = new File(LumaQQ.LOGIN_HISTORY);
        // 读入登陆信息文件
    	logins = LoginUtil.load(loginHistory);
    	if(logins == null)
    	    logins = ModelUtils.createDefaultLogins();
        // 得到上一次登陆的人的历史信息
        Login login = LoginUtil.findLogin(logins, logins.getLastLogin());
        // 把所有的QQ号都加到combo里面
        addAllQQ();
        // 检查login，不应该为null，如果为null了，那文件有问题，采用第一项login
        if(login == null) {
            List<Login> list = logins.getLogin();
            if(list == null || list.size() == 0) return;
            login = list.get(0);
        }
        // 设置初始值
        setInitValue(login);
    }    
    
    /**
     * 把所有的QQ号都加到combo里面
     */
	@SuppressWarnings("unchecked")
    private void addAllQQ() {
		for(Login login : (List<Login>)logins.getLogin())
            combo.add(login.getQq());
    }
    
    /**
     * 设置初始值
     * @param login Login对象
     */
    private void setInitValue(Login login) {
        combo.setText(login.getQq());
        chkRemember.setSelection(login.isAutoLogin() || login.isRememberPassword());
        chkHidden.setSelection(login.isLoginHidden());
        chkAuto.setSelection(login.isAutoLogin());
       	chkRemember.setEnabled(!login.isAutoLogin());
        if(chkRemember.getSelection()) {
            text.setText("xxxxxxxx");
            md5pwd = codec.decode(login.getPassword().getBytes());
        } else {
            text.setText("");
            md5pwd = new byte[16];
        }
        changed = false;
        
		LoginOption lo = logins.getNetwork();
		comboProxyType.select(lo.getProxyType().getValue());
		selectProxy(lo.getProxyType().getValue());
		textProxyServer.setText(lo.getProxyServer());
		textProxyPort.setText(String.valueOf(lo.getProxyPort()));
		textUsername.setText(lo.getProxyUsername());
		textPassword.setText(lo.getProxyPassword());
		rdoTcp.setSelection(lo.isUseTcp());
		rdoUdp.setSelection(!lo.isUseTcp());
		chkAutoSelect.setSelection(lo.isAutoSelect());
		if(lo.getProxyPort() == 443)
			comboPort.select(1);
		else
			comboPort.select(0);
		comboServer.setText(lo.getServer());
    }

    /**
     * 校验用户输入
     * 
     * @return
     * 		错误信息，如果没有错误，返回null
     */
    private String validate() {
    	if(Util.getInt(combo.getText(), -1) == -1)
    		return message_box_wrong_qq;
    	else
    		return null;
    }
    
    /**
     * 保存输入结果
     */
    private void saveInput() {
    	autoLogin = chkAuto.getSelection();
        rememberPassword = autoLogin || chkRemember.getSelection();
        loginHidden = chkHidden.getSelection();
        qqNum = Util.getInt(combo.getText(), -1);
        combo.setText(String.valueOf(qqNum));
        if(changed)
        	md5pwd = md5(md5(text.getText().getBytes()));
        else if(md5pwd == null)
            md5pwd = md5(md5("".getBytes()));
        
        LoginOption lo = logins.getNetwork();
        lo.setUseTcp(rdoTcp.getSelection());
        lo.setAutoSelect(chkAutoSelect.getSelection());
        lo.setServer(comboServer.getText());
        lo.setTcpPort(Util.getInt(comboPort.getText(), 80));
        switch(comboProxyType.getSelectionIndex()) {
        	case 0:
        		lo.setProxyType(ProxyType.NONE_LITERAL);
        		break;
        	case 1:
        		lo.setProxyType(ProxyType.HTTP_LITERAL);
        		break;
        	case 2:
        		lo.setProxyType(ProxyType.SOCKS5_LITERAL);
        		break;
        }
        lo.setProxyServer(textProxyServer.getText());
        lo.setProxyPort(Util.getInt(textProxyPort.getText(), 8000));
        lo.setProxyUsername(textUsername.getText());
        lo.setProxyPassword(textPassword.getText());
    }
    
	/**
	 * 打开对话框
	 */
	public boolean open()	{
	    // create dialog
	    Shell parent = getParent();
	    Display display = parent.getDisplay();
		dialog = new Shell(parent, getStyle());
		// init child controls
		initLayout();
		// 初始化控件的值
		initValue();
		if(!ignoreAuto && chkAuto.getSelection()) {
			saveInput();
			return true;
		}
		// init event listeners
		initListeners();
		// set title and image
		dialog.setImage(res.getImage(Resources.icoLumaQQ));
		dialog.setText(login_title);
		// set dialog to center of screen
		dialog.pack();
		smallRect = dialog.getBounds();
		Rectangle displayRect = display.getBounds();
		dialog.setLocation((displayRect.width - smallRect.width) / 2, (displayRect.height - smallRect.height) / 2);
		// 设置密码框获得焦点
		text.setFocus();
		combo.setSelection(new Point(0, 0));
		// event loop
		dialog.open();
		while(!dialog.isDisposed()) 
			if(!display.readAndDispatch()) 
			    display.sleep();
			
		return ok;
	}

    /**
     * @return Returns the loginHidden.
     */
    public boolean isLoginHidden() {
        return loginHidden;
    }
    
    /**
     * @return Returns the md5pwd.
     */
    public byte[] getMd5Password() {
        return md5pwd;
    }
    
    /**
     * @return Returns the qqNum.
     */
    public int getQQ() {
        return qqNum;
    }
    
    /**
     * 设置logo
     * 
     * @param lblLogo
     */
    private void setLogo(Label lblLogo) {
        Calendar c = Calendar.getInstance();
        if(c.get(Calendar.MONTH) == Calendar.MARCH){
        	//System.out.println(c.get(Calendar.MONTH)+"\tEEE:"+Calendar.MARCH);
            lblLogo.setImage(Resources.getInstance().getImage(Resources.bmpSmth));
        }
        else if(c.get(Calendar.HOUR_OF_DAY) >= 20){
        	//System.out.println(c.get(Calendar.MONTH)+"\tDDD:"+Calendar.MARCH);
            lblLogo.setImage(Resources.getInstance().getImage(Resources.bmpLogin2));
        }
        else{
            lblLogo.setImage(Resources.getInstance().getImage(Resources.bmpLogin));
      //  System.out.println(c.get(Calendar.MONTH)+"\tGGGGGGG:"+Calendar.MARCH);
        }
    }
    
    /**
     * @return Returns the rememberPassword.
     */
    public boolean isRememberPassword() {
        return rememberPassword;
    }

	/**
	 * 切换网络设置面板的显示
	 */
	private void switchNetworkSetting() {
		GridData gd = (GridData)networkContainer.getLayoutData();
		gd.exclude = !gd.exclude;
		networkContainer.getParent().layout();
		dialog.layout();
		if(gd.exclude)
			dialog.setSize(smallRect.width, smallRect.height);
		else
			dialog.setSize(smallRect.width, smallRect.height + networkContainer.getSize().y);
		Rectangle dialogRect = dialog.getBounds();
		Rectangle displayRect = dialog.getDisplay().getClientArea();
		dialog.setLocation((displayRect.width - dialogRect.width) >> 1, (displayRect.height - dialogRect.height) >> 1);
		useNetworkSetting = !gd.exclude;
	}

	/**
	 * @return the logins
	 */
	public Logins getLogins() {
		return logins;
	}

	/**
	 * @return the useNetworkSetting
	 */
	public boolean isUseNetworkSetting() {
		return useNetworkSetting;
	}
}
