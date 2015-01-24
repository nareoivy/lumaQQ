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

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.ViewForm;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseTrackAdapter;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.ColorDialog;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FontDialog;
import org.eclipse.swt.widgets.Sash;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import edu.tsinghua.lumaqq.events.BaseQQListener;
import edu.tsinghua.lumaqq.events.IFaceSelectionListener;
import edu.tsinghua.lumaqq.models.Model;
import edu.tsinghua.lumaqq.models.User;
import edu.tsinghua.lumaqq.qq.Util;
import edu.tsinghua.lumaqq.qq.beans.FontStyle;
import edu.tsinghua.lumaqq.qq.beans.NormalIM;
import edu.tsinghua.lumaqq.qq.beans.NormalIMHeader;
import edu.tsinghua.lumaqq.qq.events.IQQListener;
import edu.tsinghua.lumaqq.qq.packets.in.ReceiveIMPacket;
import edu.tsinghua.lumaqq.record.IKeyConstants;
import edu.tsinghua.lumaqq.record.RecordEntry;
import edu.tsinghua.lumaqq.resource.Colors;
import edu.tsinghua.lumaqq.resource.DefaultFace;
import edu.tsinghua.lumaqq.resource.Resources;
import edu.tsinghua.lumaqq.ui.config.user.UserInfoWindow;
import edu.tsinghua.lumaqq.ui.helper.DateTool;
import edu.tsinghua.lumaqq.ui.helper.FaceRegistry;
import edu.tsinghua.lumaqq.ui.helper.HeadFactory;
import edu.tsinghua.lumaqq.ui.jobs.SendNormalIMJob;
import edu.tsinghua.lumaqq.widgets.FaceImageAdvisor;
import edu.tsinghua.lumaqq.widgets.IImageSelectorAdvisor;
import edu.tsinghua.lumaqq.widgets.ImageSelector;
import edu.tsinghua.lumaqq.widgets.rich.IRichContent;
import edu.tsinghua.lumaqq.widgets.rich.LineStyle;
import edu.tsinghua.lumaqq.widgets.rich.RichBox;

/**
 * 普通聊天容器
 * 
 * @author luma
 */
public class NormalIMContainer extends Composite implements IIMContainer {
	private Composite parent;
	private Resources res;
	private IContainerKeyHandler keyHandler;
	
	private CLabel lblName, lblIp;
	private RichBox outputBox, inputBox;
	private ViewForm inputForm, outputForm;
	
	private MainShell main;
	private String ip, place;
	private User user;
	
	private int unread;
	private boolean active;
	
	// 发送消息的参数
	private String message;
	
	// style样式表缓冲区
	private List<LineStyle> styleCache;	
	// 缺省的用户名称提示样式
	private static final LineStyle myStyle = new LineStyle(Colors.MY_HINT_COLOR, null, "宋体", SWT.NORMAL, 9);
	private static final LineStyle otherStyle = new LineStyle(Colors.BLUE, null, "宋体", SWT.NORMAL, 9);
	
    private BaseQQListener qqListener = new BaseQQListener();
    
	// 系统平台
    private static boolean IS_GTK;
    private static boolean IS_MOTIF;
    static {
        String platform = SWT.getPlatform();
        IS_GTK = "gtk".equals(platform);
        IS_MOTIF = "motif".equals(platform);
    }
    
	// 关闭窗口action
	private Runnable closeAction = new Runnable() {
        public void run() {
        	if(keyHandler != null)
        		keyHandler.onKeyDown(SWT.MOD3, 'C');
        }
	};
	// 查看记录的action
	private Runnable showRecordAction = new Runnable() {
        public void run() {
        	if(keyHandler != null)
        		keyHandler.onKeyDown(SWT.MOD3, 'H');
        }
	};
	// 发送消息的action
	private Runnable sendAction = new Runnable() {
        public void run() {
            if(!inputBox.isReadonly())
                send();
        }
	};
	// 切换下一个未读tab
	private Runnable nextUnreadTabAction = new Runnable() {
        public void run() {
        	if(keyHandler != null)
        		keyHandler.onKeyDown(SWT.MOD3, 'Q');
        }
	};
	// 切换下一个tab
	private Runnable nextTabAction = new Runnable() {
        public void run() {
        	if(keyHandler != null)
        		keyHandler.onKeyDown(SWT.MOD3, SWT.ARROW_RIGHT);
        }
	};
	// 切换上一个tab
	private Runnable prevTabAction = new Runnable() {
        public void run() {
        	if(keyHandler != null)
        		keyHandler.onKeyDown(SWT.MOD3, SWT.ARROW_LEFT);
        }
	};
	
	public NormalIMContainer(Composite parent) {
		super(parent, SWT.NONE);
		this.parent = parent;
		res = Resources.getInstance();
		unread = 0;
		active = false;
		styleCache = new ArrayList<LineStyle>();
		initLayout();
	}
	
	/**
	 * 解析IP地址的地点
	 * 
	 * @param f
	 * 		User对象
	 */
	private void resolveIPLocation(User f) {
		// 设置IP信息
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
			if(country != bad_ip_file)
				place = country + area;
			else
				place = bad_ip_file;
			ip = ipStr + ":" + port;
		}
	}
	
    /**
     * 初始化快捷键设置
     */
    private void initKeyAction() {
        // mod3 -> alt
        inputBox.setUserKeyAction('C' | SWT.MOD3, closeAction);
        inputBox.setUserKeyAction('S' | SWT.MOD3, sendAction);
        inputBox.setUserKeyAction('H' | SWT.MOD3, showRecordAction);
        inputBox.setUserKeyAction('Q' | SWT.MOD3, nextUnreadTabAction);
        inputBox.setUserKeyAction(SWT.MOD3 | SWT.ARROW_RIGHT, nextTabAction);
        inputBox.setUserKeyAction(SWT.MOD3 | SWT.ARROW_LEFT, prevTabAction);
    }
	
	/**
	 * 初始化布局
	 */
	private void initLayout() {
        GridLayout layout = new GridLayout();
        layout.horizontalSpacing = layout.verticalSpacing = layout.marginHeight = layout.marginWidth = 0;
        setLayout(layout);
        setBackground(Colors.MAINSHELL_BACKGROUND);
        
        // 聊天区
        Composite chatContainer = new Composite(this, SWT.NONE);
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
        
        // output form
        outputForm = new ViewForm(chatContainer, SWT.FLAT);
        outputForm.setLayoutData(new GridData(GridData.FILL_BOTH));
        outputForm.setBackground(Colors.VIEWFORM_BANNER_BACKGROUND);
        // output form left control
        Composite left = new Composite(outputForm, SWT.NONE);
        layout = new GridLayout(2, false);
        layout.marginHeight = layout.marginWidth = 0;
        left.setLayout(layout);
        left.setBackground(Colors.VIEWFORM_BANNER_BACKGROUND);
        // 设置名称标签
        lblName = new CLabel(left, SWT.LEFT);
        lblName.setLayoutData(new GridData(GridData.FILL_VERTICAL));
        lblName.setBackground(Colors.VIEWFORM_BANNER_BACKGROUND);
        lblName.setCursor(getDisplay().getSystemCursor(SWT.CURSOR_HAND));
        lblName.setForeground(Colors.VIEWFORM_BANNER_TEXT);
        lblName.addMouseListener(new MouseAdapter() {
            @Override
			public void mouseUp(MouseEvent e) {
                main.getShellLauncher().openUserInfoWindow(user, UserInfoWindow.READ_ONLY);
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
        // 设置ip标签
        lblIp = new CLabel(left, SWT.RIGHT);
        lblIp.setLayoutData(new GridData(GridData.FILL_BOTH));
        lblIp.setBackground(Colors.VIEWFORM_BANNER_BACKGROUND);
        // set left control
        outputForm.setTopLeft(left);
        // set content
        outputBox = new RichBox(outputForm);
        outputBox.setReadonly(true);
        outputBox.setBackground(Colors.WHITE);
        outputForm.setContent(outputBox);     
        
        Sash sash = new Sash(chatContainer, SWT.HORIZONTAL);
        sash.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        sash.setBackground(Colors.READONLY_BACKGROUND);
        sash.addSelectionListener(new SelectionAdapter() {
            @Override
			public void widgetSelected(SelectionEvent e) {
                GridData data = (GridData)inputForm.getLayoutData();
                data.heightHint = inputForm.getParent().getClientArea().height - e.y;
                inputForm.getParent().layout();
            }
        });
        
        inputForm = new ViewForm(chatContainer, SWT.FLAT);
        GridData gd = new GridData(GridData.FILL_HORIZONTAL);
        gd.heightHint = 100;
        inputForm.setLayoutData(gd);
        ToolBar tb = new ToolBar(inputForm, SWT.FLAT);
        tb.setBackground(Colors.VIEWFORM_BANNER_BACKGROUND);
        // 字体
        ToolItem ti = new ToolItem(tb, SWT.NONE);        
        ti.setImage(res.getImage(Resources.icoFont));
        ti.setToolTipText(tooltip_button_font);
        ti.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				FontDialog dialog = new FontDialog(parent.getShell());
				if(main.getDefaultStyle() != null) 
					dialog.setFontList(main.getDefaultStyle().createFontData());
				if(main.getFontColor() != null)
					dialog.setRGB(main.getFontColor().getRGB());
				dialog.open();
				FontData[] fontList = dialog.getFontList();
				if(fontList == null) return;
				RGB rgb = dialog.getRGB();
				if(main.getFontColor() != null) main.getFontColor().dispose();
				main.getDefaultStyle().fontName = fontList[0].getName();
				main.getDefaultStyle().fontSize = fontList[0].getHeight();
				main.getDefaultStyle().fontStyle = fontList[0].getStyle();
				if(rgb != null) {
					main.setFontColor(new Color(main.getDisplay(), rgb));
					main.getDefaultStyle().foreground = main.getFontColor();
				}
				inputBox.setDefaultStyle(main.getDefaultStyle());
			}
		});
        // 颜色，在有些平台下需要
        if(IS_GTK || IS_MOTIF) {
	        ToolItem tiColor = new ToolItem(tb, SWT.NONE);
	        tiColor.setImage(res.getImage(Resources.icoColor));
	        tiColor.setToolTipText(tooltip_button_color);
	        tiColor.addSelectionListener(new SelectionAdapter() {
	            @Override
				public void widgetSelected(SelectionEvent e) {
	                ColorDialog dialog = new ColorDialog(parent.getShell());
					if(main.getFontColor() != null)
						dialog.setRGB(main.getFontColor().getRGB());
					dialog.open();
					RGB rgb = dialog.getRGB();
					if(rgb != null) {
						if(main.getFontColor() != null) 
						    main.getFontColor().dispose();
						if(rgb != null) {
							main.setFontColor(new Color(main.getDisplay(), rgb));
							main.getDefaultStyle().foreground = main.getFontColor();
						}
					}
					inputBox.setDefaultStyle(main.getDefaultStyle());
	            }
	        });            
        }
        // 表情
        ti = new ToolItem(tb, SWT.NONE);
        ti.setImage(res.getImage(Resources.icoSmiley));
        ti.setToolTipText(tooltip_button_face);
        ti.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ToolBar toolbar = ((ToolItem)e.widget).getParent();
				Rectangle bound = toolbar.getBounds();
				openImageSelectionShell(toolbar.getParent().toDisplay(bound.x, bound.y + bound.height));					
			}
		});
        // 动画状态
        ti = new ToolItem(tb, SWT.CHECK);
        ti.setImage(res.getImage(Resources.icoStop));
        ti.setToolTipText(tooltip_button_disable_animation);
        ti.addSelectionListener(new SelectionAdapter() {
            @Override
			public void widgetSelected(SelectionEvent e) {
                ToolItem item = (ToolItem)e.widget;
                outputBox.setForbiddenAnimation(item.getSelection());
                inputBox.setForbiddenAnimation(item.getSelection());                
                item.setToolTipText(item.getSelection() ? tooltip_button_animation : tooltip_button_disable_animation);
            }
        });
		// input form
        inputForm.setTopLeft(tb);
        inputForm.setBackground(Colors.VIEWFORM_BANNER_BACKGROUND);
        inputBox = new RichBox(inputForm);
        inputBox.setBackground(Colors.WHITE);
        inputForm.setContent(inputBox);
        
        addDisposeListener(new DisposeListener() {
            public void widgetDisposed(DisposeEvent e) {
				for(LineStyle style : styleCache) {
                    if(style.foreground != null)
                        style.foreground.dispose();
                    if(style.background != null)
                        style.background.dispose();
                }
            }
        });
	}

	public Image getHead() {
    	FaceRegistry reg = FaceRegistry.getInstance();
    	if(user.hasCustomHead && reg.hasFace(reg.getMd5ById(user.customHeadId)))
    		return res.getSmallCustomHead(user.customHeadId, false);
    	else
    		return res.getSmallHead(user.headId);
	}

	public int getUnreadCount() {
		return unread;
	}

	public String getDisplayedName() {
		return user.displayName;
	}

	public int getId() {
		return user.qq;
	}

	public Model getModel() {
		return user;
	}

	public void putMessage(ReceiveIMPacket packet) {
		appendMessage(user, packet.normalIM, packet.normalHeader);
		if(!active)
			unread++;
	}
	
	public String getIPText() {
		if(ip.equals(""))
			return place;
		else
			return ip + " - " + place;
	}

	public void setModel(Model model) {
		user = (User)model;
		if(main != null) {
			resolveIPLocation(user);
			lblIp.setText(getIPText());	
	        lblIp.setToolTipText(lblIp.getText());
		}
    	FaceRegistry reg = FaceRegistry.getInstance();
    	if(user.hasCustomHead && reg.hasFace(reg.getMd5ById(user.customHeadId)))
    		lblName.setImage(res.getSmallCustomHead(user.customHeadId, false));
    	else
    		lblName.setImage(HeadFactory.getSmallHeadByStatus(user));
        lblName.setText(user.displayName + " (" + user.qq + ')');
        lblName.getParent().layout();
	}

	public void setMainShell(MainShell main) {
		this.main = main;
		if(ip == null) {
			resolveIPLocation(user);
	        lblIp.setText(getIPText());
	        lblIp.setToolTipText(lblIp.getText());
		}
        // 初始化按键事件
        initKeyAction();
        setUseEnter(main.getOptionHelper().isUseEnterInTalkMode());
	}
	
	/**
     * 打开表情选择窗口
     */
    private void openImageSelectionShell(Point loc) {
    	ImageSelector fss = new ImageSelector(parent.getShell(), new FaceImageAdvisor(main));
    	fss.setListener(new IFaceSelectionListener() {
    		public void faceSelected(IImageSelectorAdvisor provider, int group, int sequence) {
    	        if(group == 0) {
    	            int index = res.getFaceCode(sequence);
    	            if(index != -1)
    	                inputBox.insertImage(IRichContent.DEFAULT_FACE_TAG, index);
    	        } else {
    	            MessageDialog.openInformation(parent.getShell(), message_box_common_info_title, message_box_face_to_user);
    	            /*
    	             * 暂时还不支持向单个用户发送自定义表情，注释掉这些代码
    	             */
//    	            FaceUtil util = FaceUtil.getInstance();
//    	            Face face = util.getFace(group - 1, sequence);
//    	            if(face == null)
//    	                return;
//    	            int id = Util.getInt(face.getId(), -1);
//    	            if(id == -1)
//    	                return;
//    	            inputBox.insertImage(IRichContent.CUSTOM_FACE_TAG, id);
    	        }
    		}
    	});
		fss.setLocation(loc);
		fss.open();	
    }

	public void setActive(boolean active) {
		this.active = active;
		if(active)
			unread = 0;
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
		outputBox.appendText('(' + DefaultFace.escapeFaces(f.displayName) + ")  " + DateTool.format(header.sendTime), otherStyle);
		LineStyle style = getLineStyle(im.fontStyle);
		outputBox.appendText(im.message, style);
    }
    
    /**
     * 往输出框追加消息
     * 
     * @param entry
     * 		RecordEntry
     */
    private void appendMessage(RecordEntry entry) {
		outputBox.appendText('(' + DefaultFace.escapeFaces(main.getMyModel().displayName) + ")  " + DateTool.format(entry.time), myStyle);		
		outputBox.appendText(entry.message, getLineStyle(main.getDefaultStyle()));
    }
    
    /**
     * 得到行样式
     * 
     * @param fs
     * @return
     */
    private LineStyle getLineStyle(FontStyle fs) {
        int fontStyle = 0;
        if(fs.isBold())
            fontStyle |= SWT.BOLD;
        if(fs.isItalic())
            fontStyle |= SWT.ITALIC;
        if(fontStyle == 0)
            fontStyle = SWT.NORMAL;
        
        return getLineStyle(fs.getFontName(), 
        		fontStyle, 
        		fs.getFontSize(), 
        		fs.getRed(), 
        		fs.getGreen(), 
        		fs.getBlue());
    }
    
    /**
     * 根据一个现有的style，在cache里面查找一个相同的style，没有则新建一个
     * 
     * @param style
     * @return
     */
    private LineStyle getLineStyle(LineStyle ls) {
        int size = styleCache.size();
        for(int i = 0; i < size; i++) {
            LineStyle style = styleCache.get(i);
            if(style.equals(ls))
                return style;
        }
        
        LineStyle style = (LineStyle)ls.clone();
        styleCache.add(style);
        return style;
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
	 * @see edu.tsinghua.lumaqq.ui.IIMContainer#send()
	 */
	public void send() {		
	    // 检查消息是否为空
		String s = inputBox.getText();
	    if(s.length() == 0) {
	        MessageDialog.openWarning(getShell(), message_box_common_warning_title, message_box_cannot_send_empty_message);
	        return;
	    }
	    // 判断用户是否登录
		if(main.getClient().getUser().isLoggedIn()) {
		    message = s;
			// 保存到聊天记录中
		    RecordEntry key = new RecordEntry();
		    key.owner = user.qq;
		    key.sender = main.getMyModel().qq;
		    key.senderParent = 0;
		    key.receiver = user.qq;
		    key.time = System.currentTimeMillis();
		    key.type = IKeyConstants.NORMAL;
		    key.message = message;
			main.getRecordManager().addRecord(key);

			// 把发送的消息显示到输出框中
		    appendMessage(key);
			// 清空发送框
		    inputBox.clear();
		    
		    // TODO 检查是否包含自定义表情，转换发送消息到QQ格式，启动自定义表情发送任务
		    
		    // 启动普通消息发送任务
		    main.getIMJobQueue().addJob(new SendNormalIMJob(message, (User)getModel()));
		} else {
		    MessageDialog.openWarning(getShell(), message_box_common_warning_title, message_box_please_login_first);
		}
	}

	/* (non-Javadoc)
	 * @see edu.tsinghua.lumaqq.ui.IIMContainer#setUseEnter(boolean)
	 */
	public void setUseEnter(boolean useEnter) {
		if(useEnter) {
		    inputBox.removeUserKeyAction(SWT.MOD1 | SWT.CR);
		    inputBox.setUserKeyAction(SWT.CR, sendAction);
		    inputBox.setKeyBinding(SWT.MOD1 | SWT.CR, RichBox.NEW_LINE);
		} else {
		    inputBox.removeUserKeyAction(SWT.CR);
		    inputBox.setUserKeyAction(SWT.MOD1 | SWT.CR, sendAction);
		    inputBox.setKeyBinding(SWT.CR, RichBox.NEW_LINE);
		}
	}

	/* (non-Javadoc)
	 * @see edu.tsinghua.lumaqq.ui.IIMContainer#getComposite()
	 */
	public Composite getComposite() {
		return this;
	}

	public void setKeyHandler(IContainerKeyHandler ka) {
		keyHandler = ka;
	}

	public void setText(String text) {
		inputBox.setText(text);
	}

	public void release() {
		dispose();
	}

	public void setKeyboardFocus() {
		inputBox.setFocus();
	}

	public void init() {
		inputBox.setDefaultStyle(main.getDefaultStyle());
	}

	public boolean isActiveContainer() {
		return active;
	}

	public void appendText(String text) {
		inputBox.appendText(text, main.getDefaultStyle());
	}

	public IQQListener getQQListener() {
		return qqListener;
	}
}
