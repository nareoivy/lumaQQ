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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.eclipse.jface.window.Window;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.ViewForm;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseTrackAdapter;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.events.ShellListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;

import edu.tsinghua.lumaqq.LumaQQ;
import edu.tsinghua.lumaqq.models.User;
import edu.tsinghua.lumaqq.qq.Util;
import edu.tsinghua.lumaqq.qq.beans.NormalIM;
import edu.tsinghua.lumaqq.qq.beans.NormalIMHeader;
import edu.tsinghua.lumaqq.qq.packets.in.ReceiveIMPacket;
import edu.tsinghua.lumaqq.resource.Colors;
import edu.tsinghua.lumaqq.resource.DefaultFace;
import edu.tsinghua.lumaqq.resource.Resources;
import edu.tsinghua.lumaqq.ui.config.sys.SystemOptionWindow;
import edu.tsinghua.lumaqq.ui.config.user.UserInfoWindow;
import edu.tsinghua.lumaqq.ui.helper.HeadFactory;
import edu.tsinghua.lumaqq.ui.helper.ShellFactory;
import edu.tsinghua.lumaqq.widgets.qstyle.Slat;
import edu.tsinghua.lumaqq.widgets.rich.LineStyle;
import edu.tsinghua.lumaqq.widgets.rich.RichBox;

/**
 * 接收消息窗口，用来查看收到的消息
 * 
 * @author luma
 */
public class ReceiveIMWindow extends Window implements ShellListener {    
    private User model;
    private MainShell main;
    private Resources res;
    
	// 表示当前窗口是否是active的
	private boolean active;
	
	private CLabel lblName;
	private RichBox outputBox;
	private Cursor handCursor;
	private Slat btnNext, btnQuickReply;
	private Menu quickReplyMenu;
	private CLabel lblIp;
	
	private String ip, place;
	
	// 用作临时用途
	private Date date = new Date();
	private DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	// style样式表缓冲区
	private List<LineStyle> styleCache;	
	
	// 缺省的用户名称提示样式
	private static final LineStyle myStyle = new LineStyle(Colors.MY_HINT_COLOR, null, "宋体", SWT.NORMAL, 9);
	
	// 关闭窗口action
	private Runnable closeAction = new Runnable() {
        public void run() {
            getShell().close();
        }
	};
	// 回复
	private Runnable replyAction = new Runnable() {
        public void run() {
            openSendIMWindow();
            getShell().close();
        }
	};
	// 快捷回复
	private Runnable quickReplyAction = new Runnable() {
        public void run() {
            quickReply();
        }
	};
	// 下一条
	private Runnable nextAction = new Runnable() {
        public void run() {
        	if(btnNext.isEnabled()) {
	            main.getShellLauncher().openNormalIMWindow(model);
	            if(!main.getMessageQueue().hasMessage(model.qq))
	            	setNextButtonEnabled(false);        		
        	}
        }
	};
    
    /**
     * @param parentShell
     */
    public ReceiveIMWindow(MainShell main, User f) {
        super(main.getShell());
        this.main = main;
        this.model = f;
        this.res = Resources.getInstance();
		styleCache = new ArrayList<LineStyle>();
		handCursor = main.getDisplay().getSystemCursor(SWT.CURSOR_HAND);
		
		// 设置ip地址信息
		if(f.ip == null || Util.isIpZero(f.ip)) {
			ip = "";
			place = unknown_ip;
		} else {
			String ipStr = Util.getIpStringFromBytes(f.ip);
			String port = String.valueOf(f.port);
			String country = main.getIPSeeker().getCountry(f.ip);
			String area = main.getIPSeeker().getArea(f.ip);
			if(area.endsWith("CZ88.NET"))
				area = "";
			place = country + area;
			ip = ipStr + ":" + port;
		}
		
        setBlockOnOpen(false);
    }    
    
	public String getIPText() {
		if(ip.equals(""))
			return place;
		else
			return ip + " - " + place;
	}

	/**
	 * 初始化快捷回复菜单
	 */
	@SuppressWarnings("unchecked")
	private void initQuickReplyMenu() {
		quickReplyMenu = new Menu(getShell(), SWT.POP_UP);
		// 循环初始化菜单项，有多少条快捷回复消息就创建多少菜单项，最后加上一个自定义项
		List<String> quickReplies = main.getConfigHelper().getReplies().getQuickReply();
		int size = quickReplies.size();
		for(int i = 0; i < size; i++) {
			final MenuItem mi = new MenuItem(quickReplyMenu, SWT.RADIO);
			mi.setData(i);
			mi.setText(quickReplies.get(i));
			mi.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {					
					int index = (Integer)((MenuItem)e.getSource()).getData();
					if(mi.getSelection()) {
						main.getConfigHelper().getReplies().setCurrentQuickReply(index);
						quickReply();							
					}
				}
			});			
		}
		new MenuItem(quickReplyMenu, SWT.SEPARATOR);
		// 自定义
		MenuItem mi = new MenuItem(quickReplyMenu, SWT.PUSH);
		mi.setImage(res.getImage(Resources.icoReply));
		mi.setText(menu_quick_reply_addnew);
		mi.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				main.getShellLauncher().openSystemOptionWindow().setCurrentPage(SystemOptionWindow.REPLY);
			}
		});
		// 设置菜单项的选择状态
		quickReplyMenu.getItem(main.getConfigHelper().getReplies().getCurrentQuickReply()).setSelection(true);
	}	
	
	/**
	 * 快速回复
	 */
	protected void quickReply() {
	    SendIMWindow sms = openSendIMWindow();
		getShell().close();
		sms.sendMessage(main.getConfigHelper().getCurrentQuickReplyString());
	}

	/**
	 * 打开回复消息窗口
	 */
	private SendIMWindow openSendIMWindow() {
		SendIMWindow sms = main.getShellRegistry().getSendIMWindow(model);
		if(sms == null) {
			sms = ShellFactory.createSendIMWindow(main, model);
			sms.open();
		} else {
			sms.setMinimized(false);
			sms.setActive();
			sms.setFocus();
		}
		return sms;
	}
	
    /**
     * 得到行样式
     * 
     * @param im
     * @return
     */
    private LineStyle getLineStyle(NormalIM im) {
        int fontStyle = 0;
        if(im.fontStyle.isBold())
            fontStyle |= SWT.BOLD;
        if(im.fontStyle.isItalic())
            fontStyle |= SWT.ITALIC;
        if(fontStyle == 0)
            fontStyle = SWT.NORMAL;
        
        return getLineStyle(im.fontStyle.getFontName(), 
        		fontStyle, 
        		im.fontStyle.getFontSize(), 
        		im.fontStyle.getRed(), 
        		im.fontStyle.getGreen(), 
        		im.fontStyle.getBlue());
    }
    
    /**
     * 根据具体的样式信息查找style
     * 
     * @param fontName
     * @param fontStyle
     * @param fontSize
     * @param red
     * @param green
     * @param blue
     * @return
     */
    private LineStyle getLineStyle(String fontName, int fontStyle, int fontSize, int red, int green, int blue) {        
        int size = styleCache.size();
        for(int i = 0; i < size; i++) {
            LineStyle style = styleCache.get(i);
            if(!style.fontName.equals(fontName))
                continue;
            if(style.fontSize != fontSize)
                continue;            
            if(style.fontStyle != fontStyle)
                continue;
            // TODO add underline here
            if(style.foreground.getRed() != red)
                continue;
            if(style.foreground.getGreen() != green)
                continue;
            if(style.foreground.getBlue() != blue)
                continue;
            return style;
        }
        
        LineStyle style = new LineStyle(new Color(main.getDisplay(), red, green, blue), null, fontName, fontStyle, fontSize);
        styleCache.add(style);
        return style;
    }
    
    /* (non-Javadoc)
     * @see org.eclipse.jface.window.Window#getParentShell()
     */
	@Override
    protected Shell getParentShell() {
        return null;
    }
    
	/* (non-Javadoc)
     * @see org.eclipse.jface.window.Window#configureShell(org.eclipse.swt.widgets.Shell)
     */
	@Override
    protected void configureShell(Shell newShell) {
        newShell.setText(NLS.bind(receive_im_title, model.displayName)); 
		newShell.setImage(res.getImage(Resources.icoMessage));
        newShell.addControlListener(new ControlAdapter() {
            @Override
			public void controlMoved(ControlEvent e) {
        		// 保存自己的位置，以便下次打开窗口时在同样的位置
        		Point loc = ((Shell)e.getSource()).getLocation();
        		model.windowX = loc.x;
        		model.windowY = loc.y;
            }
        });
        newShell.addDisposeListener(new DisposeListener() {
            public void widgetDisposed(DisposeEvent e) {
				for(LineStyle style : styleCache) {
                    if(style.foreground != null)
                        style.foreground.dispose();
                    if(style.background != null)
                        style.background.dispose();
                }
            }
        });
        
        if(LumaQQ.IS_GTK) {
        	BorderStyler styler = new BorderStyler(main);
        	styler.setShowMaxButton(true);
        	styler.setHideWhenMinimize(false);
        	styler.decorateShell(newShell);        	
        }
    }
    
    /* (non-Javadoc)
     * @see org.eclipse.jface.window.Window#getShellListener()
     */
	@Override
    protected ShellListener getShellListener() {
        return this;
    }
    
    /* (non-Javadoc)
     * @see org.eclipse.swt.events.ShellListener#shellActivated(org.eclipse.swt.events.ShellEvent)
     */
    public void shellActivated(ShellEvent e) {
        active = true;
    }
    
    /* (non-Javadoc)
     * @see org.eclipse.swt.events.ShellListener#shellDeactivated(org.eclipse.swt.events.ShellEvent)
     */
    public void shellDeactivated(ShellEvent e) {
        active = false;
    }
    
    /* (non-Javadoc)
     * @see org.eclipse.swt.events.ShellAdapter#shellClosed(org.eclipse.swt.events.ShellEvent)
     */
    public void shellClosed(ShellEvent e) {
		main.getShellRegistry().removeReceiveIMWindow(model);
    }
    
    /* (non-Javadoc)
     * @see org.eclipse.swt.events.ShellListener#shellDeiconified(org.eclipse.swt.events.ShellEvent)
     */
    public void shellDeiconified(ShellEvent e) {
    }

    /* (non-Javadoc)
     * @see org.eclipse.swt.events.ShellListener#shellIconified(org.eclipse.swt.events.ShellEvent)
     */
    public void shellIconified(ShellEvent e) {
    }
    
    /* (non-Javadoc)
     * @see org.eclipse.jface.window.Window#createContents(org.eclipse.swt.widgets.Composite)
     */
	@Override
    protected Control createContents(Composite parent) {
		Composite container = null;
		if(LumaQQ.IS_GTK) {
			container = ((BorderStyler)getShell().getData(BorderStyler.STYLER)).getCenter();
		} else {
			parent.setLayout(new FillLayout());
			container = (Composite)super.createContents(parent);			
		}
        GridLayout layout = new GridLayout();
        layout.horizontalSpacing = 0;
        container.setLayout(layout);
        container.setBackground(Colors.MAINSHELL_BACKGROUND);
        
        // 记录区
        Composite chatContainer = new Composite(container, SWT.NONE);
        chatContainer.setLayoutData(new GridData(GridData.FILL_BOTH));
        layout = new GridLayout();        
        layout.marginHeight = layout.marginWidth = 1;
        layout.horizontalSpacing = layout.verticalSpacing = 0;
        chatContainer.setLayout(layout);
        chatContainer.addPaintListener(new PaintListener() {
            public void paintControl(PaintEvent e) {
                Composite c = (Composite)e.getSource();
                Rectangle rect = c.getClientArea();
                rect.width--;
                rect.height--;
                e.gc.setForeground(Colors.WIDGET_BORDER);
                e.gc.drawRectangle(rect);
            }
        });
        
        ViewForm outputForm = new ViewForm(chatContainer, SWT.FLAT);
        outputForm.setLayoutData(new GridData(GridData.FILL_BOTH));
        outputForm.setBackground(Colors.VIEWFORM_BANNER_BACKGROUND);
        lblName = new CLabel(outputForm, SWT.LEFT);
        GridData gd = new GridData(GridData.FILL_HORIZONTAL);
        gd.horizontalSpan = 2;
        lblName.setLayoutData(gd);
        lblName.setBackground(Colors.VIEWFORM_BANNER_BACKGROUND);
        setFriendNameLabel();
        lblName.setCursor(handCursor);
        lblName.setForeground(Colors.VIEWFORM_BANNER_TEXT);
        lblName.addMouseListener(new MouseAdapter() {
            @Override
			public void mouseUp(MouseEvent e) {
                main.getShellLauncher().openUserInfoWindow(model, UserInfoWindow.READ_ONLY);
            }
        });
        lblName.addMouseTrackListener(new MouseTrackAdapter() {
            @Override
			public void mouseEnter(MouseEvent e) {
                lblName.setForeground(Colors.VIEWFORM_BANNER_TEXT_HOVER);
            }
            
            @Override
			public void mouseExit(MouseEvent e) {
                lblName.setForeground(Colors.VIEWFORM_BANNER_TEXT);
            }
        });
        outputForm.setTopLeft(lblName);
        lblIp = new CLabel(outputForm, SWT.LEFT);
        lblIp.setBackground(Colors.VIEWFORM_BANNER_BACKGROUND);
        lblIp.setLayoutData(new GridData());
        lblIp.setText(getIPText());
        outputForm.setTopRight(lblIp);        
        outputBox = new RichBox(outputForm);
        outputBox.setReadonly(true);
        outputBox.setBackground(Colors.READONLY_BACKGROUND);
        outputForm.setContent(outputBox);    
        
        // 按钮区
        Composite buttonContainer = new Composite(container, SWT.NONE);
        gd = new GridData(GridData.FILL_HORIZONTAL);
        buttonContainer.setLayoutData(gd);
        layout = new GridLayout(5, false);
        layout.marginHeight = layout.marginWidth = layout.verticalSpacing = layout.horizontalSpacing = 0;
        buttonContainer.setLayout(layout);
        buttonContainer.setBackground(container.getBackground());

		// 回复消息按钮
		Slat btnReply = new Slat(buttonContainer);
		btnReply.setText(receive_im_button_reply);
		btnReply.setLayoutData(new GridData());
		btnReply.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
			    replyAction.run();
			}
		});
		// 快捷回复按钮
		btnQuickReply = new Slat(buttonContainer);
		btnQuickReply.setText(receive_im_button_quickreply);
		gd = new GridData();
		gd.horizontalIndent = 3;
		btnQuickReply.setLayoutData(gd);
		btnQuickReply.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				quickReplyAction.run();
			}
		});
		// 快捷回复旁边的下拉箭头按钮
		Slat btnArrow = new Slat(buttonContainer, SWT.CENTER);
		btnArrow.setText("↓");
		btnArrow.setLayoutData(new GridData());
		btnArrow.addMouseListener(new MouseAdapter() {
            @Override
			public void mouseUp(MouseEvent e) {
				// 设置菜单位置
				Rectangle bound = btnQuickReply.getBounds();
				quickReplyMenu.setLocation(btnQuickReply.getParent().toDisplay(bound.x, bound.y + bound.height));	
				// 显示菜单
				quickReplyMenu.setVisible(true);
            }
		});
        
		// 关闭按钮
        Slat btnClose = new Slat(buttonContainer);
        btnClose.setText(button_close_accel);
        gd = new GridData(GridData.HORIZONTAL_ALIGN_END);
        gd.grabExcessHorizontalSpace = true;
        btnClose.setLayoutData(gd);
		btnClose.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				closeAction.run();
			}
		});
		
		// 下一条消息按钮
		btnNext = new Slat(buttonContainer);		
		btnNext.setText(button_next_accel);
		gd = new GridData();
		gd.horizontalIndent = 3;
		btnNext.setLayoutData(gd);
		btnNext.setEnabled(false);
		btnNext.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
			    nextAction.run();
			}
		});
		
		// 初始化菜单
		initQuickReplyMenu();
		
		// 初始化快捷键设置
		initKeyAction();
		
        return container;
    }
    
    /**
     * 初始化快捷键设置
     */
    private void initKeyAction() {
        // mod3 -> alt
        outputBox.setUserKeyAction('C' | SWT.MOD3, closeAction);
        outputBox.setUserKeyAction('R' | SWT.MOD3, replyAction);
        outputBox.setUserKeyAction('S' | SWT.MOD3, quickReplyAction);
        outputBox.setUserKeyAction('N' | SWT.MOD3, nextAction);
        outputBox.setUserKeyAction(SWT.CR, replyAction);
    }

    /* (non-Javadoc)
     * @see org.eclipse.jface.window.Window#getShellStyle()
     */
	@Override
    protected int getShellStyle() {
		int onTop = main.getOptionHelper().isIMOnTop() ? SWT.ON_TOP : SWT.NONE;
		if(LumaQQ.IS_GTK)
			return SWT.NO_TRIM | SWT.NO_BACKGROUND | onTop;
		else
			return SWT.SHELL_TRIM | onTop;
    }
    
    /**
     * 设置窗口激活
     */
    public void setActive() {
        getShell().setActive();
    }
    
    /**
     * 设置最小化状态
     * 
     * @param b
     */
    public void setMinimized(boolean b) {
        getShell().setMinimized(b);
    }
    
    /* (non-Javadoc)
     * @see org.eclipse.jface.window.Window#open()
     */
	@Override
    public int open() {
        int ret = super.open();
		outputBox.setFocus();
        return ret;
    }
    
    /* (non-Javadoc)
     * @see org.eclipse.jface.window.Window#constrainShellSize()
     */
	@Override
    protected void constrainShellSize() {
        getShell().setSize(440, 340);
        
		Point loc = new Point(150, 150);
		int x = model.windowX;
		int y = model.windowY;
		if(x > 0) loc.x = x;
		if(y > 0) loc.y = y;
		getShell().setLocation(loc);
    }
    
    /* (non-Javadoc)
     * @see org.eclipse.jface.window.Window#getInitialLocation(org.eclipse.swt.graphics.Point)
     */
	@Override
    protected Point getInitialLocation(Point initialSize) {
		Point loc = new Point(150, 150);
		int x = model.windowX;
		int y = model.windowY;
		if(x > 0) loc.x = x;
		if(y > 0) loc.y = y;
		return loc;
    }
    
    /**
     * @param c
     * 		群model
     * @param im
     * 		群消息结构
     * @param header
     * @param me
     * 		true表示是我自己
     */
    public void appendMessage(User f, NormalIM im, NormalIMHeader header) {
        date.setTime(header.sendTime);
		outputBox.appendText('(' + DefaultFace.escapeFaces(f.displayName) + ")  " + df.format(date), myStyle);
		LineStyle style = getLineStyle(im);
		outputBox.appendText(im.message, style);
    }

    /**
     * 刷新群名称标签
     */
    private void setFriendNameLabel() {
        lblName.setImage(HeadFactory.getSmallHeadByStatus(model));
        lblName.setText(model.displayName + " (" + model.qq + ')');
    }

    /**
     * @return
     * 		true表示当前窗口是活动窗口
     */
    public boolean isActive() {
        return active;
    }
	
	/**
	 * 设置下一条按钮的使能状态
	 * 
	 * @param b		
	 */
	public void setNextButtonEnabled(boolean b) {
		btnNext.setEnabled(b);
	}
	
	/**
     * @param packet
     */
    public void setMessage(ReceiveIMPacket packet) {
        outputBox.clear();
        appendMessage(model, packet.normalIM, packet.normalHeader);
    }
}
