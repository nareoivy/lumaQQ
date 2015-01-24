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
package edu.tsinghua.lumaqq.ui.config.sys;

import static edu.tsinghua.lumaqq.resource.Messages.*;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Text;

import edu.tsinghua.lumaqq.LumaQQ;
import edu.tsinghua.lumaqq.ecore.ProxyType;
import edu.tsinghua.lumaqq.ecore.proxy.HttpProxy;
import edu.tsinghua.lumaqq.ecore.proxy.Proxies;
import edu.tsinghua.lumaqq.ecore.proxy.ProxyFactory;
import edu.tsinghua.lumaqq.ecore.proxy.Socks5Proxy;
import edu.tsinghua.lumaqq.qq.QQ;
import edu.tsinghua.lumaqq.qq.Util;
import edu.tsinghua.lumaqq.qq.net.IProxyHandler;
import edu.tsinghua.lumaqq.resource.Colors;
import edu.tsinghua.lumaqq.resource.Resources;
import edu.tsinghua.lumaqq.ui.MainShell;
import edu.tsinghua.lumaqq.ui.config.AbstractPage;
import edu.tsinghua.lumaqq.ui.helper.OptionHelper;
import edu.tsinghua.lumaqq.ui.helper.UITool;
import edu.tsinghua.lumaqq.ui.listener.AroundBorderPaintListener;

/**
 * 登录设置页
 * 
 * @author luma
 */
public class LoginPage extends AbstractPage implements IProxyHandler {
    /**
     * 代理标签提供者
     * 
     * @author luma
     */
    private class ProxyLabelProvider extends LabelProvider {
        @Override
		public String getText(Object element) {
            if(element instanceof Socks5Proxy) {
                Socks5Proxy proxy = (Socks5Proxy)element;
                StringBuffer sb = new StringBuffer();
	            sb.append(proxy.getServer())
	            	.append(':')
	            	.append(proxy.getPort());
	            if(!proxy.getUsername().equals("")) {
	                sb.append(' ')
	                	.append('[')
	                	.append(proxy.getUsername())
	                	.append(':')
	                	.append(proxy.getPassword())
	                	.append(']');
	            }
	            return sb.toString();
            } else if(element instanceof HttpProxy) {
                HttpProxy proxy = (HttpProxy)element;
                StringBuffer sb = new StringBuffer();
	            sb.append(proxy.getServer())
	            	.append(':')
	            	.append(proxy.getPort());
	            if(!proxy.getUsername().equals("")) {
	                sb.append(' ')
	                	.append('[')
	                	.append(proxy.getUsername())
	                	.append(':')
	                	.append(proxy.getPassword())
	                	.append(']');
	            }
	            return sb.toString();
            } else
                return "";
        }
    }
    
    /**
     * 代理列表内容提供者
     * 
     * @author luma
     */
    private class ProxyContentProvider implements IStructuredContentProvider {
        /* (non-Javadoc)
         * @see org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java.lang.Object)
         */
        public Object[] getElements(Object inputElement) {
            if(comboProxyType.getSelectionIndex() == 0)
            	return getProxies().getSocks5Proxy().toArray();
            else
                return getProxies().getHttpProxy().toArray();
        }

        /* (non-Javadoc)
         * @see org.eclipse.jface.viewers.IContentProvider#dispose()
         */
        public void dispose() {
        }

        /* (non-Javadoc)
         * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
         */
        public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
        }
    }
    
    private Button rdoUdp, rdoTcp;
    private CCombo comboAddress, comboPort;
    private Button chkRandom;
    private Button chkUseProxy;
    private Group basicGroup, listGroup;
    private CCombo comboProxyType;
    private Text textProxyAddress, textProxyPort, textProxyUsername, textProxyPassword;
    private Button btnVerify;
    private ListViewer proxyViewer;
    private Button btnDelete;
    
    private PaintListener paintListener;
    
    private static final int USE_TCP = 1;
    private static final int LOGIN_SERVER = 2;
    private static final int LOGIN_PORT = 3;
    private static final int LOGIN_RANDOM = 4;
    private static final int PROXY_TYPE = 5;
    
    private MainShell main;
    
    /**
     * @param parent
     */
    public LoginPage(Composite parent, MainShell main) {
        super(parent);
        this.main = main;
        proxyViewer.setInput(this);
    }
    
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.shells.AbstractPage#initialVariable()
     */
	@Override
    protected void initialVariable() {
        paintListener = new AroundBorderPaintListener(new Class[] { CCombo.class, Text.class, List.class }, Colors.PAGE_CONTROL_BORDER);
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.ui.config.AbstractPage#createContent(org.eclipse.swt.widgets.Composite)
     */
	@Override
	@SuppressWarnings("unchecked")
    protected Control createContent(Composite parent) {
        Composite container = new Composite(parent, SWT.NONE);
        container.setBackground(Colors.PAGE_BACKGROUND);
        GridLayout layout = new GridLayout();
        layout.marginHeight = layout.marginWidth = 15;
        layout.verticalSpacing = 15;
        container.setLayout(layout);
        
        // 设置使用缺省背景色
        UITool.setDefaultBackground(null);
        
        // 登录方式组
        Group loginGroup = UITool.createGroup(container, sys_opt_group_login);
        
        // UDP方式
        rdoUdp = UITool.createRadio(loginGroup, sys_opt_login_method_udp);
        rdoUdp.addSelectionListener(new SelectionAdapter() {
            @Override
			public void widgetSelected(SelectionEvent e) {
                refreshControl();
                comboAddress.removeAll();
                fillUdpServers();
                if(!main.getOptionHelper().isUseTcp())
                    comboAddress.setText(main.getOptionHelper().getServer());
                else
                    comboAddress.select(0);
                makeDirty(USE_TCP);
                makeDirty(LOGIN_SERVER);
            }
        });
        // TCP方式
        rdoTcp = UITool.createRadio(loginGroup, sys_opt_login_method_tcp);
        rdoTcp.addSelectionListener(new SelectionAdapter() {
            @Override
			public void widgetSelected(SelectionEvent e) {
                refreshControl();
                comboAddress.removeAll();
                fillTcpServers();
                if(main.getOptionHelper().isUseTcp())
                    comboAddress.setText(main.getOptionHelper().getServer());
                else
                    comboAddress.select(0);
			    if(main.getOptionHelper().getTcpPort() == QQ.QQ_PORT_TCP)
			        comboPort.select(0);
			    else
			        comboPort.select(1);
                makeDirty(USE_TCP);
                makeDirty(LOGIN_SERVER);
            }
        });
        
        // 登录服务器组
        layout = new GridLayout(3, false);
        layout.marginHeight = layout.marginWidth = 8;
        Group serverGroup = UITool.createGroup(container, sys_opt_group_server, layout);
        serverGroup.addPaintListener(paintListener);
        
        // 地址标签
        UITool.createLabel(serverGroup, sys_opt_login_server);
        // 地址下拉框
        GridData gd = new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.VERTICAL_ALIGN_FILL);
        gd.widthHint = 200;
        comboAddress = UITool.createCCombo(serverGroup, gd, SWT.FLAT);
        comboAddress.addSelectionListener(new SelectionAdapter() {
            @Override
			public void widgetSelected(SelectionEvent e) {
                makeDirty(LOGIN_SERVER);
            }
        });
        comboAddress.addModifyListener(new ModifyListener() {
            public void modifyText(ModifyEvent e) {
                makeDirty(LOGIN_SERVER);
            }
        });
        // 随机选择
        chkRandom = UITool.createCheckbox(serverGroup, sys_opt_login_server_random, new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING));
        chkRandom.addSelectionListener(new SelectionAdapter() {
            @Override
			public void widgetSelected(SelectionEvent e) {
                makeDirty(LOGIN_RANDOM);
            }
        });
        
        // 端口标签
        UITool.createLabel(serverGroup, sys_opt_login_tcp_port);
        // 端口下拉框
        gd = new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.VERTICAL_ALIGN_FILL);
        gd.widthHint = 200;
        comboPort = UITool.createCCombo(serverGroup, gd);
		comboPort.add("80");
		comboPort.add("443");
		comboPort.addSelectionListener(new SelectionAdapter() {
            @Override
			public void widgetSelected(SelectionEvent e) {
                makeDirty(LOGIN_PORT);
            }
		});
        
        // 代理设置组
        Group proxyGroup = UITool.createGroup(container, sys_opt_group_proxy);
        
        // 使用代理服务器
        gd = new GridData();
        gd.horizontalSpan = 2;
        chkUseProxy = UITool.createCheckbox(proxyGroup, sys_opt_login_use_proxy, gd);
        chkUseProxy.addSelectionListener(new SelectionAdapter() {
            @Override
			public void widgetSelected(SelectionEvent e) {
                refreshControl();
                makeDirty(PROXY_TYPE);
            }
        });
        
        // 基本信息组
        basicGroup = UITool.createGroup(proxyGroup, sys_opt_group_proxy_basic, new GridData(GridData.FILL_BOTH), new GridLayout(2, false));
        basicGroup.addPaintListener(paintListener);
        
        // 代理类型
        UITool.createLabel(basicGroup, sys_opt_login_proxy_type);
        comboProxyType = UITool.createCCombo(basicGroup, new GridData(GridData.FILL_HORIZONTAL | GridData.VERTICAL_ALIGN_FILL));
        comboProxyType.add("Socks5");
        comboProxyType.add("Http");
        comboProxyType.addSelectionListener(new SelectionAdapter() {
            @Override
			public void widgetSelected(SelectionEvent e) {
                refreshControl();
                proxyViewer.refresh();
                makeDirty(PROXY_TYPE);
            }
        });
        // 代理地址
        UITool.createLabel(basicGroup, sys_opt_login_proxy_address);
        textProxyAddress = UITool.createSingleText(basicGroup, new GridData(GridData.FILL_HORIZONTAL | GridData.VERTICAL_ALIGN_FILL));
        ModifyListener ml = new ModifyListener() {
            public void modifyText(ModifyEvent e) {
                makeDirty(PROXY_TYPE);
            }
        };
        textProxyAddress.addModifyListener(ml);
        // 代理端口
        UITool.createLabel(basicGroup, sys_opt_login_proxy_port);
        textProxyPort = UITool.createSingleText(basicGroup, new GridData(GridData.FILL_HORIZONTAL | GridData.VERTICAL_ALIGN_FILL));
        textProxyPort.addModifyListener(ml);
        // 用户名
        UITool.createLabel(basicGroup, sys_opt_login_proxy_username);
        textProxyUsername = UITool.createSingleText(basicGroup, new GridData(GridData.FILL_HORIZONTAL | GridData.VERTICAL_ALIGN_FILL));
        textProxyUsername.addModifyListener(ml);
        // 密码
        UITool.createLabel(basicGroup, sys_opt_login_proxy_password);
        textProxyPassword = UITool.createSingleText(basicGroup, new GridData(GridData.FILL_HORIZONTAL | GridData.VERTICAL_ALIGN_FILL), SWT.SINGLE | SWT.PASSWORD);
        textProxyPassword.addModifyListener(ml);
        // 添加和验证按钮
        layout = new GridLayout(2, true);
        layout.marginHeight = layout.marginWidth = 0;
        gd = new GridData(GridData.FILL_HORIZONTAL);
        gd.horizontalSpan = 2;
        Composite c = UITool.createContainer(basicGroup, gd, layout);
        Button btnAddProxy = UITool.createButton(c, button_add, new GridData(GridData.HORIZONTAL_ALIGN_FILL));
        btnAddProxy.addSelectionListener(new SelectionAdapter() {
            @Override
			public void widgetSelected(SelectionEvent e) {
                if(checkProxy()) {
                    if(comboProxyType.getSelectionIndex() == 0) {
                        Socks5Proxy proxy = ProxyFactory.eINSTANCE.createSocks5Proxy();
                        proxy.setServer(textProxyAddress.getText());
                        proxy.setPort(textProxyPort.getText());
                        proxy.setUsername(textProxyUsername.getText());
                        proxy.setPassword(textProxyPassword.getText());
                        getProxies().getSocks5Proxy().add(proxy);
                        proxyViewer.refresh();
                    } else {
                        HttpProxy proxy = ProxyFactory.eINSTANCE.createHttpProxy();
                        proxy.setServer(textProxyAddress.getText());
                        proxy.setPort(textProxyPort.getText());
                        proxy.setUsername(textProxyUsername.getText());
                        proxy.setPassword(textProxyPassword.getText());
                        getProxies().getHttpProxy().add(proxy);
                        proxyViewer.refresh();
                    }
                }
            }
        });
        btnVerify = UITool.createButton(c, sys_opt_button_verify, new GridData(GridData.HORIZONTAL_ALIGN_FILL));
		btnVerify.addSelectionListener(new SelectionAdapter() {
            @Override
			public void widgetSelected(SelectionEvent e) {
                startVerify();
            }
		});

        // 列表信息组
        layout = new GridLayout();
        layout.marginHeight = layout.marginWidth = 8;
        listGroup = UITool.createGroup(proxyGroup, sys_opt_group_proxy_list, new GridData(GridData.FILL_BOTH), layout);
        listGroup.addPaintListener(paintListener);
        
        // 代理列表
        proxyViewer = new ListViewer(listGroup, SWT.SINGLE);
        proxyViewer.setContentProvider(new ProxyContentProvider());
        proxyViewer.setLabelProvider(new ProxyLabelProvider());
        proxyViewer.getList().setLayoutData(new GridData(GridData.FILL_BOTH));
        proxyViewer.addSelectionChangedListener(new ISelectionChangedListener() {
            public void selectionChanged(SelectionChangedEvent e) {
                IStructuredSelection selection = (IStructuredSelection)proxyViewer.getSelection();
                btnDelete.setEnabled(!selection.isEmpty());
                if(!selection.isEmpty()) {
                    Object obj = selection.getFirstElement();
                    if(obj instanceof Socks5Proxy) {
                        Socks5Proxy proxy = (Socks5Proxy)obj;
                        textProxyAddress.setText(proxy.getServer());
                        textProxyPort.setText(proxy.getPort());
                        textProxyUsername.setText(proxy.getUsername());
                        textProxyPassword.setText(proxy.getPassword());
                    } else if(obj instanceof HttpProxy) {
                        HttpProxy proxy = (HttpProxy)obj;
                        textProxyAddress.setText(proxy.getServer());
                        textProxyPort.setText(proxy.getPort());
                        textProxyUsername.setText(proxy.getUsername());
                        textProxyPassword.setText(proxy.getPassword());
                    }
                }
            }
        });
        // 删除
        btnDelete = UITool.createButton(listGroup, button_delete);
        btnDelete.setEnabled(false);
        btnDelete.addSelectionListener(new SelectionAdapter() {
            @Override
			public void widgetSelected(SelectionEvent e) {
                if(comboProxyType.getSelectionIndex() == 0)
                    getProxies().getSocks5Proxy().remove(proxyViewer.getList().getSelectionIndex());
                else
                    getProxies().getHttpProxy().remove(proxyViewer.getList().getSelectionIndex());
                proxyViewer.refresh();
            }
        });

        return container;
    }
    
    /**
	 * 检查代理服务器的地址和端口是否输入了，并且是否有效
	 * 
	 * @return 
	 * 		true表示有效，false则提示
	 */
	private boolean checkProxy() {
        if(textProxyAddress.getText().equals("")) {
            MessageDialog.openWarning(parentShell, message_box_common_warning_title, message_box_please_fill_proxy_address);
            return false;
        } else {
            int port = Util.getInt(textProxyPort.getText(), 0);
            if(port == 0) {
                MessageDialog.openWarning(parentShell, message_box_common_warning_title, message_box_please_fill_proxy_port);
	            return false;                
            }
        }
        return true;
	}
    
    /**
     * 刷新界面控件的状态
     */
    private void refreshControl() {
        comboPort.setEnabled(rdoTcp.getSelection());
        
        boolean useProxy = chkUseProxy.getSelection();
        if(basicGroup.isEnabled() != useProxy) {
	        basicGroup.setEnabled(useProxy);
	        setChildrenStatus(basicGroup, useProxy);            
        }
        
        if(listGroup.isEnabled() != useProxy) {
	        listGroup.setEnabled(useProxy);        
	        setChildrenStatus(listGroup, useProxy);
	        if(useProxy)
	            btnDelete.setEnabled(!proxyViewer.getSelection().isEmpty());
        }
        
        listGroup.setText(NLS.bind(sys_opt_group_proxy_list, comboProxyType.getText()));
    }
    
    /**
     * 递归的设置组件中所有孩子的状态
     * 
     * @param parent
     * 		父容器
     * @param enabled
     * 		true表示使能
     */
    private void setChildrenStatus(Composite parent, boolean enabled)  {
        Control[] children = parent.getChildren();
        for(int i = 0; i < children.length; i++) {
            children[i].setEnabled(enabled);
            if(children[i] instanceof Composite)
                setChildrenStatus((Composite)children[i], enabled);
        }
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.shells.AbstractPage#getImage()
     */
	@Override
    protected Image getImage() {
        return Resources.getInstance().getImage(Resources.icoSysOpt24);
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.shells.AbstractPage#getTitle()
     */
	@Override
    protected String getTitle(int page) {
        return sys_opt_button_login;
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.shells.AbstractPage#saveDirtyProperty(int)
     */
	@Override
    protected void saveDirtyProperty(int propertyId) {
        OptionHelper options = main.getOptionHelper();
        switch(propertyId) {
            case USE_TCP:
                options.setUseTcp(rdoTcp.getSelection());
                break;
            case LOGIN_SERVER:
                options.setServer(comboAddress.getText());
                break;
            case LOGIN_PORT:
                options.setTcpPort(comboPort.getText());
                break;
            case LOGIN_RANDOM:
                options.setAutoSelect(chkRandom.getSelection());
                break;
            case PROXY_TYPE:
                if(chkUseProxy.getSelection()) {
            		options.setProxyServer(textProxyAddress.getText().trim());
            		options.setProxyPort(Util.getInt(textProxyPort.getText().trim(), 1080));
            		options.setProxyUsername(textProxyUsername.getText());
            		options.setProxyPassword(textProxyPassword.getText());
            		options.setProxyType((comboProxyType.getSelectionIndex() == 0) ? ProxyType.SOCKS5_LITERAL : ProxyType.HTTP_LITERAL);
        		    if(comboProxyType.getSelectionIndex() == 1 && !options.isUseTcp()) {
        		        options.setUseTcp(true);
        		        options.setServer(LumaQQ.tcpServers[0]);		        
        		    }
                } else
                    options.setProxyType(ProxyType.NONE_LITERAL);
                main.getConfigHelper().saveProxies();
                break;
        }
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.shells.AbstractPage#initializeValues()
     */
	@Override
    protected void initializeValues() {
        OptionHelper options = main.getOptionHelper();
        
		chkRandom.setSelection(options.isAutoSelect());
		if(options.isUseTcp()) {
		    comboPort.setEnabled(true);
		    if(options.getTcpPort() == QQ.QQ_PORT_TCP)
		        comboPort.select(0);
		    else
		        comboPort.select(1);
			rdoTcp.setSelection(true);
			rdoUdp.setSelection(false);
			fillTcpServers();
		} else {
		    comboPort.setEnabled(false);
			rdoTcp.setSelection(false);
			rdoUdp.setSelection(true);
			fillUdpServers();
		}
		comboAddress.setText(options.getServer());
		chkUseProxy.setSelection(options.getProxyType() != ProxyType.NONE_LITERAL);
	    if(options.getProxyType() == ProxyType.HTTP_LITERAL)
	        comboProxyType.select(1);
	    else
	        comboProxyType.select(0);
        proxyViewer.refresh();
	    textProxyAddress.setText(options.getProxyServer());
	    textProxyPort.setText(String.valueOf(options.getProxyPort()));
	    textProxyUsername.setText(options.getProxyUsername());
	    textProxyPassword.setText(options.getProxyPassword());
	    refreshControl();
    }
    
	/**
	 * 填充TCP Server列表到下拉框
	 */
	private void fillTcpServers() {
		for(int i = 0; i < LumaQQ.tcpServers.length; i++)
			comboAddress.add(LumaQQ.tcpServers[i]);
		comboAddress.setVisibleItemCount(LumaQQ.tcpServers.length);
	}
	
	/**
	 * 填充UDP Server列表到下拉框
	 */
	private void fillUdpServers() {
		for(int i = 0; i < LumaQQ.udpServers.length; i++)
		    comboAddress.add(LumaQQ.udpServers[i]);
		comboAddress.setVisibleItemCount(LumaQQ.udpServers.length);
	}
	
	/**
     * 开始验证代理
     */
    protected synchronized void startVerify() {
        if(checkProxy()) {
            btnVerify.setEnabled(false);
            String proxyType = comboProxyType.getText();
            boolean udp = rdoUdp.getSelection();
            InetSocketAddress serverAddress = null;
            if(proxyType.equalsIgnoreCase("Socks5")) {
                // 得到一个随机服务器
                if(udp) {
                    String server = LumaQQ.udpServers[Util.random().nextInt(LumaQQ.udpServers.length)];
                    serverAddress = new InetSocketAddress(server, QQ.QQ_PORT_UDP);
                } else {
                    String server = LumaQQ.tcpServers[Util.random().nextInt(LumaQQ.tcpServers.length)];
                    serverAddress = new InetSocketAddress(server, QQ.QQ_PORT_TCP);
                }
            } else if(proxyType.equalsIgnoreCase("Http")) {
                String server = LumaQQ.tcpServers[Util.random().nextInt(LumaQQ.tcpServers.length - 1)];
                serverAddress = new InetSocketAddress(server, QQ.QQ_PORT_HTTP);
            }
            InetSocketAddress proxyAddress = new InetSocketAddress(textProxyAddress.getText(), Util.getInt(textProxyPort.getText(), 1080));
            if(!main.getClient().getConnectionPool().verifyProxy(this, proxyAddress, serverAddress, proxyType, udp, textProxyUsername.getText(), textProxyPassword.getText()))
            	btnVerify.setEnabled(true);
        }
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.IProxyHandler#proxyReady(java.net.InetSocketAddress)
     */
    public void proxyReady(InetSocketAddress bindAddress) throws IOException {
        parentShell.getDisplay().syncExec(new Runnable() {
            public void run() {
                btnVerify.setEnabled(true);
                MessageDialog.openInformation(parentShell, message_box_common_success_title, message_box_proxy_verify_ok);
            }
        });
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.IProxyHandler#proxyAuthFail()
     */
    public void proxyAuthFail() {
        parentShell.getDisplay().syncExec(new Runnable() {
            public void run() {
                btnVerify.setEnabled(true);
                MessageDialog.openError(parentShell, message_box_proxy_error_title, message_box_proxy_verify_fail);
            }
        });
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.net.IProxyHandler#proxyError(java.lang.String)
     */
    public synchronized void proxyError(final String err) {
        parentShell.getDisplay().syncExec(new Runnable() {
            public void run() {
                btnVerify.setEnabled(true);
                MessageDialog.openError(parentShell, message_box_proxy_error_title, err);
            }
        });            
    }

	/**
	 * @return
	 */
	private Proxies getProxies() {
		return main.getConfigHelper().getProxies();
	}
}
