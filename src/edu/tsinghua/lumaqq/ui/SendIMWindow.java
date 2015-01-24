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
import org.eclipse.jface.window.Window;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.ViewForm;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.MenuAdapter;
import org.eclipse.swt.events.MenuEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseTrackAdapter;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.events.ShellListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.ColorDialog;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.FontDialog;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Sash;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import edu.tsinghua.lumaqq.LumaQQ;
import edu.tsinghua.lumaqq.events.IFaceSelectionListener;
import edu.tsinghua.lumaqq.models.User;
import edu.tsinghua.lumaqq.qq.Util;
import edu.tsinghua.lumaqq.qq.beans.FontStyle;
import edu.tsinghua.lumaqq.qq.beans.NormalIM;
import edu.tsinghua.lumaqq.qq.beans.NormalIMHeader;
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
import edu.tsinghua.lumaqq.widgets.qstyle.Slat;
import edu.tsinghua.lumaqq.widgets.record.FriendRecordProvider;
import edu.tsinghua.lumaqq.widgets.record.RecordViewer;
import edu.tsinghua.lumaqq.widgets.rich.IRichContent;
import edu.tsinghua.lumaqq.widgets.rich.LineStyle;
import edu.tsinghua.lumaqq.widgets.rich.RichBox;

/**
 * 发送普通消息窗口
 * 
 * @author luma
 */
public class SendIMWindow extends Window implements ShellListener, IFaceSelectionListener {
	/**
	 * <pre>
	 * 闪烁图标
	 * </pre>
	 * 
	 * @author luma
	 */
	private class Blink implements Runnable {
		private boolean flag;
		private volatile boolean stop;
		private Image blinkImage;


		public Blink() {
			stop = true;
		}

		public void setBlinkImage(Image image) {
			blinkImage = image;
			stop = false;
			flag = true;
		}

		public void run() {
			try {
				if (flag) {
					getShell().setImage(blinkImage);
					if (LumaQQ.IS_GTK)
						((BorderStyler) getShell().getData(BorderStyler.STYLER)).repaintTitleBar();
				} else {
					getShell().setImage(res.getImage(Resources.icoBlank));
					if (LumaQQ.IS_GTK)
						((BorderStyler) getShell().getData(BorderStyler.STYLER)).repaintTitleBar();
				}
				flag = !flag;
				if (!stop)
					main.getDisplay().timerExec(500, this);
				else {
					getShell().setImage(res.getImage(Resources.icoMessage));
					if (LumaQQ.IS_GTK)
						((BorderStyler) getShell().getData(BorderStyler.STYLER)).repaintTitleBar();
				}
			} catch (SWTException e) {
				// 这个操作可能会抛出SWTException，如果组件已经dispose的话，
				// 所以我们需要捕捉这个异常，不然程序可能崩溃
			}
		}

		public void setStop(boolean stop) {
			this.stop = stop;
		}

		public boolean isStop() {
			return stop;
		}
	}


	private User model;
	private MainShell main;
	private Resources res;

	// 表示当前窗口是否是active的
	private boolean active;

	private CLabel lblName, lblIp;
	private RichBox outputBox, inputBox;
	private RecordViewer viewer;
	private Menu enterMenu, fileMenu;
	private Cursor handCursor;
	private Slat btnMode;
	private Sash sash;

	private Blink blinkRunnable;

	private ViewForm inputForm, outputForm;

	private String ip, place;

	// style样式表缓冲区
	private List<LineStyle> styleCache;

	// 发送消息的参数
	private String message;

	// 缺省的用户名称提示样式
	private static final LineStyle myStyle = new LineStyle(Colors.MY_HINT_COLOR, null, "宋体", SWT.NORMAL, 9);
	private static final LineStyle otherStyle = new LineStyle(Colors.BLUE, null, "宋体", SWT.NORMAL, 9);

	// 关闭窗口action
	private Runnable closeAction = new Runnable() {
		public void run() {
			getShell().close();
		}
	};
	// 查看记录的action
	private Runnable showRecordAction = new Runnable() {
		public void run() {
			showRecord();
		}
	};
	// 发送消息的action
	private Runnable sendAction = new Runnable() {
		public void run() {
			if (!inputBox.isReadonly())
				sendMessage(inputBox.getText());
		}
	};
	// 切换模式
	private Runnable modeAction = new Runnable() {
		public void run() {
			setTalkMode(!model.talkMode);
		}
	};

	// 系统平台
	private static boolean IS_GTK;
	private static boolean IS_MOTIF;
	private ImageSelector fss;
	static {
		String platform = SWT.getPlatform();
		IS_GTK = "gtk".equals(platform);
		IS_MOTIF = "motif".equals(platform);
	}


	/**
	 * @param parentShell
	 */
	public SendIMWindow(MainShell main, User f) {
		super(main.getShell());
		this.main = main;
		this.model = f;
		this.res = Resources.getInstance();
		blinkRunnable = new Blink();
		blinkRunnable.setBlinkImage(HeadFactory.getSmallHeadByStatus(f));
		styleCache = new ArrayList<LineStyle>();
		handCursor = main.getDisplay().getSystemCursor(SWT.CURSOR_HAND);

		resolveIPLocation(f);

		setBlockOnOpen(false);
	}

	/**
	 * 解析IP地址的地点
	 * 
	 * @param f
	 *            User对象
	 */
	private void resolveIPLocation(User f) {
		// 设置IP信息
		if (f.ip == null || Util.isIpZero(f.ip)) {
			ip = "";
			place = unknown_ip;
		} else {
			String ipStr = Util.getIpStringFromBytes(f.ip);
			String port = String.valueOf(f.port);
			String country = main.getIPSeeker().getCountry(f.ip);
			String area = main.getIPSeeker().getArea(f.ip);
			if (area.endsWith("CZ88.NET"))
				area = "";
			if (country != bad_ip_file)
				place = country + area;
			else
				place = bad_ip_file;
			ip = ipStr + ":" + port;
		}
	}

	public String getIPText() {
		if (ip.equals(""))
			return place;
		else
			return ip + " - " + place;
	}

	/**
	 * 刷新
	 * 
	 * @param f
	 */
	public void refresh(User f) {
		model = f;
		setFriendNameLabel();
	}

	/**
	 * 得到行样式
	 * 
	 * @param fs
	 * @return
	 */
	private LineStyle getLineStyle(FontStyle fs) {
		int fontStyle = 0;
		if (fs.isBold())
			fontStyle |= SWT.BOLD;
		if (fs.isItalic())
			fontStyle |= SWT.ITALIC;
		if (fontStyle == 0)
			fontStyle = SWT.NORMAL;

		return getLineStyle(fs.getFontName(), fontStyle, fs.getFontSize(), fs.getRed(), fs.getGreen(), fs.getBlue());
	}

	/**
	 * 根据一个现有的style，在cache里面查找一个相同的style，没有则新建一个
	 * 
	 * @param style
	 * @return
	 */
	private LineStyle getLineStyle(LineStyle ls) {
		int size = styleCache.size();
		for (int i = 0; i < size; i++) {
			LineStyle style = styleCache.get(i);
			if (style.equals(ls))
				return style;
		}

		LineStyle style = (LineStyle) ls.clone();
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
		for (int i = 0; i < size; i++) {
			LineStyle style = styleCache.get(i);
			if (!style.fontName.equals(fontName))
				continue;
			if (style.fontSize != fontSize)
				continue;
			if (style.fontStyle != fontStyle)
				continue;
			// TODO add underline here
			if (style.foreground.getRed() != red)
				continue;
			if (style.foreground.getGreen() != green)
				continue;
			if (style.foreground.getBlue() != blue)
				continue;
			return style;
		}

		LineStyle style = new LineStyle(new Color(main.getDisplay(), red, green, blue), null, fontName, fontStyle, fontSize);
		styleCache.add(style);
		return style;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.window.Window#getParentShell()
	 */
	@Override
	protected Shell getParentShell() {
		return null;
	}

	/**
	 * 初始化使用Enter键的菜单
	 */
	private void initEnterMenu() {
		enterMenu = new Menu(getShell(), SWT.POP_UP);
		// 使用Enter键菜单
		MenuItem mi = new MenuItem(enterMenu, SWT.RADIO);
		mi.setText(im_menu_use_enter);
		mi.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				inputBox.removeUserKeyAction(SWT.MOD1 | SWT.CR);
				inputBox.setUserKeyAction(SWT.CR, sendAction);
				inputBox.setKeyBinding(SWT.MOD1 | SWT.CR, RichBox.NEW_LINE);
				if (model.talkMode)
					main.getOptionHelper().setUseEnterInTalkMode(true);
				else
					main.getOptionHelper().setUseEnterInMessageMode(true);
			}
		});
		// 使用Ctrl + Enter键菜单
		mi = new MenuItem(enterMenu, SWT.RADIO);
		mi.setText(im_menu_use_ctrl_enter);
		mi.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				inputBox.removeUserKeyAction(SWT.CR);
				inputBox.setUserKeyAction(SWT.MOD1 | SWT.CR, sendAction);
				inputBox.setKeyBinding(SWT.CR, RichBox.NEW_LINE);
				if (model.talkMode)
					main.getOptionHelper().setUseEnterInTalkMode(false);
				else
					main.getOptionHelper().setUseEnterInMessageMode(false);
			}
		});
		// 添加菜单显示事件监听器
		enterMenu.addMenuListener(new MenuAdapter() {
			@Override
			public void menuShown(MenuEvent e) {
				refreshEnterMenu();
			}
		});
	}

	/**
	 * 根据当前选项设置菜单项状态
	 */
	private void refreshEnterMenu() {
		boolean b = model.talkMode ? main.getOptionHelper().isUseEnterInTalkMode() : main.getOptionHelper().isUseEnterInMessageMode();
		enterMenu.getItem(0).setSelection(b);
		enterMenu.getItem(1).setSelection(!b);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.window.Window#configureShell(org.eclipse.swt.widgets.Shell)
	 */
	@Override
	protected void configureShell(Shell newShell) {
		newShell.setText(NLS.bind(im_title, model.displayName));
		newShell.setImage(res.getImage(Resources.icoMessage));
		newShell.addControlListener(new ControlAdapter() {
			@Override
			public void controlMoved(ControlEvent e) {
				// 保存自己的位置，以便下次打开窗口时在同样的位置
				Point loc = ((Shell) e.getSource()).getLocation();
				model.windowX = loc.x;
				model.windowY = loc.y;
			}
		});
		newShell.addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				for (LineStyle style : styleCache) {
					if (style.foreground != null)
						style.foreground.dispose();
					if (style.background != null)
						style.background.dispose();
				}
			}
		});
		main.getShell().addShellListener(new ShellAdapter() {
			@Override
			public void shellClosed(ShellEvent e) {
				inputBox.dispose();
				outputBox.dispose();
			}
		});

		if (LumaQQ.IS_GTK) {
			BorderStyler styler = new BorderStyler(main);
			styler.setShowMaxButton(true);
			styler.setHideWhenMinimize(false);
			styler.decorateShell(newShell);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.window.Window#getShellListener()
	 */
	@Override
	protected ShellListener getShellListener() {
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.swt.events.ShellListener#shellActivated(org.eclipse.swt.events.ShellEvent)
	 */
	public void shellActivated(ShellEvent e) {
		stopBlinkImage();
		active = true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.swt.events.ShellListener#shellDeactivated(org.eclipse.swt.events.ShellEvent)
	 */
	public void shellDeactivated(ShellEvent e) {
		active = false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.swt.events.ShellAdapter#shellClosed(org.eclipse.swt.events.ShellEvent)
	 */
	public void shellClosed(ShellEvent e) {
		main.getShellRegistry().removeSendIMWindow(model);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.swt.events.ShellListener#shellDeiconified(org.eclipse.swt.events.ShellEvent)
	 */
	public void shellDeiconified(ShellEvent e) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.swt.events.ShellListener#shellIconified(org.eclipse.swt.events.ShellEvent)
	 */
	public void shellIconified(ShellEvent e) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.window.Window#createContents(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Control createContents(Composite parent) {
		Composite container = null;
		if (LumaQQ.IS_GTK) {
			container = ((BorderStyler) getShell().getData(BorderStyler.STYLER)).getCenter();
		} else {
			parent.setLayout(new FillLayout());
			container = (Composite) super.createContents(parent);
		}
		GridLayout layout = new GridLayout();
		layout.horizontalSpacing = 0;
		container.setLayout(layout);
		container.setBackground(Colors.MAINSHELL_BACKGROUND);

		// 聊天区
		Composite chatContainer = new Composite(container, SWT.NONE);
		chatContainer.setLayoutData(new GridData(GridData.FILL_BOTH));
		layout = new GridLayout();
		layout.marginHeight = layout.marginWidth = 1;
		layout.horizontalSpacing = layout.verticalSpacing = 0;
		chatContainer.setLayout(layout);
		chatContainer.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent e) {
				Composite c = (Composite) e.getSource();
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
		// 设置ip标签
		lblIp = new CLabel(left, SWT.RIGHT);
		lblIp.setText(getIPText());
		lblIp.setToolTipText(lblIp.getText());
		lblIp.setLayoutData(new GridData(GridData.FILL_BOTH));
		lblIp.setBackground(Colors.VIEWFORM_BANNER_BACKGROUND);
		// set left control
		outputForm.setTopLeft(left);
		// set content
		outputBox = new RichBox(outputForm);
		outputBox.setReadonly(true);
		outputBox.setBackground(Colors.WHITE);
		outputForm.setContent(outputBox);

		sash = new Sash(chatContainer, SWT.HORIZONTAL);
		sash.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		sash.setBackground(Colors.READONLY_BACKGROUND);
		sash.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				GridData data = (GridData) inputForm.getLayoutData();
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
				FontDialog dialog = new FontDialog(getShell());
				if (main.getDefaultStyle() != null)
					dialog.setFontList(main.getDefaultStyle().createFontData());
				if (main.getFontColor() != null)
					dialog.setRGB(main.getFontColor().getRGB());
				dialog.open();
				FontData[] fontList = dialog.getFontList();
				if (fontList == null)
					return;
				RGB rgb = dialog.getRGB();
				if (main.getFontColor() != null)
					main.getFontColor().dispose();
				main.getDefaultStyle().fontName = fontList[0].getName();
				main.getDefaultStyle().fontSize = fontList[0].getHeight();
				main.getDefaultStyle().fontStyle = fontList[0].getStyle();
				if (rgb != null) {
					main.setFontColor(new Color(main.getDisplay(), rgb));
					main.getDefaultStyle().foreground = main.getFontColor();
				}
				inputBox.setDefaultStyle(main.getDefaultStyle());
			}
		});
		// 颜色，在有些平台下需要
		if (IS_GTK || IS_MOTIF) {
			ToolItem tiColor = new ToolItem(tb, SWT.NONE);
			tiColor.setImage(res.getImage(Resources.icoColor));
			tiColor.setToolTipText(tooltip_button_color);
			tiColor.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					ColorDialog dialog = new ColorDialog(getShell());
					if (main.getFontColor() != null)
						dialog.setRGB(main.getFontColor().getRGB());
					dialog.open();
					RGB rgb = dialog.getRGB();
					if (rgb != null) {
						if (main.getFontColor() != null)
							main.getFontColor().dispose();
						if (rgb != null) {
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
				ToolBar toolbar = ((ToolItem) e.widget).getParent();
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
				ToolItem item = (ToolItem) e.widget;
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

		// 按钮区
		Composite buttonContainer = new Composite(container, SWT.NONE);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		buttonContainer.setLayoutData(gd);
		layout = new GridLayout(5, false);
		layout.marginHeight = layout.marginWidth = layout.verticalSpacing = layout.horizontalSpacing = 0;
		buttonContainer.setLayout(layout);
		buttonContainer.setBackground(container.getBackground());

		Slat btnRecord = new Slat(buttonContainer);
		btnRecord.setText(button_record_accel);
		btnRecord.setLayoutData(new GridData());
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

		btnMode = new Slat(buttonContainer);
		btnMode.setText(im_button_mode_message);
		gd = new GridData();
		gd.horizontalIndent = 3;
		btnMode.setLayoutData(gd);
		btnMode.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				Slat temp = (Slat) e.getSource();
				if (e.x > temp.getSize().x || e.y > temp.getSize().y || e.x < 0 || e.y < 0) {
					return;
				}
				setTalkMode(!model.talkMode);
			}
		});

		Slat btnClose = new Slat(buttonContainer);
		btnClose.setText(button_close_accel);
		gd = new GridData(GridData.HORIZONTAL_ALIGN_END);
		gd.grabExcessHorizontalSpace = true;
		btnClose.setLayoutData(gd);
		btnClose.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				Slat temp = (Slat) e.getSource();
				if (e.x > temp.getSize().x || e.y > temp.getSize().y || e.x < 0 || e.y < 0) {
					return;
				}
				closeWindow();
			}
		});

		final Slat btnSend = new Slat(buttonContainer);
		btnSend.setText(button_send_accel);
		gd = new GridData();
		gd.horizontalIndent = 3;
		btnSend.setLayoutData(gd);
		btnSend.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				Slat temp = (Slat) e.getSource();
				if (e.x > temp.getSize().x || e.y > temp.getSize().y || e.x < 0 || e.y < 0) {
					return;
				}
				sendMessage(inputBox.getText());
			}
		});

		Slat btnDropDown = new Slat(buttonContainer);
		btnDropDown.setText("↓");
		gd = new GridData();
		gd.horizontalIndent = 1;
		btnDropDown.setLayoutData(gd);
		btnDropDown.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				Slat temp = (Slat) e.getSource();
				if (e.x > temp.getSize().x || e.y > temp.getSize().y || e.x < 0 || e.y < 0) {
					return;
				}
				// 设置菜单位置
				Rectangle bound = btnSend.getBounds();
				enterMenu.setLocation(btnSend.getParent().toDisplay(bound.x, bound.y + bound.height));
				// 显示菜单
				enterMenu.setVisible(true);
			}
		});

		// 聊天记录面板
		viewer = new RecordViewer(container, new FriendRecordProvider(model), main);
		viewer.setFont(res.getDefaultFont());
		viewer.setVisible(false);
		viewer.setRecordManager(main.getRecordManager());
		viewer.setBackground(container.getBackground());
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 2;
		gd.heightHint = 200;
		gd.exclude = true;
		viewer.setLayoutData(gd);
		// tableitem双击事件监听器
		viewer.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				inputBox.appendText(viewer.getSelectedFullText(), main.getDefaultStyle());
			}
		});

		// 初始化菜单
		initEnterMenu();
		initFileMenu();

		// 初始化快捷键设置
		initKeyAction();

		setTalkMode(model.talkMode);

		return container;
	}

	/**
	 * 初始化快捷键设置
	 */
	private void initKeyAction() {
		// mod3 -> alt
		inputBox.setUserKeyAction('C' | SWT.MOD3, closeAction);
		inputBox.setUserKeyAction('S' | SWT.MOD3, sendAction);
		inputBox.setUserKeyAction('H' | SWT.MOD3, showRecordAction);
		inputBox.setUserKeyAction('T' | SWT.MOD3, modeAction);

		// 发送消息的快捷键
		reinstallSendKey(model.talkMode ? main.getOptionHelper().isUseEnterInTalkMode() : main.getOptionHelper().isUseEnterInMessageMode());
	}

	/**
	 * 刷新发送快捷键设置
	 * 
	 * @param useEnter
	 */
	private void reinstallSendKey(boolean useEnter) {
		if (useEnter) {
			inputBox.removeUserKeyAction(SWT.MOD1 | SWT.CR);
			inputBox.setUserKeyAction(SWT.CR, sendAction);
			inputBox.setKeyBinding(SWT.MOD1 | SWT.CR, RichBox.NEW_LINE);
		} else {
			inputBox.removeUserKeyAction(SWT.CR);
			inputBox.setUserKeyAction(SWT.MOD1 | SWT.CR, sendAction);
			inputBox.setKeyBinding(SWT.CR, RichBox.NEW_LINE);
		}
	}

	/**
	 * 刷新群名称标签
	 */
	private void setFriendNameLabel() {
		FaceRegistry reg = FaceRegistry.getInstance();
		if (model.hasCustomHead && reg.hasFace(reg.getMd5ById(model.customHeadId)))
			lblName.setImage(res.getSmallCustomHead(model.customHeadId, false));
		else
			lblName.setImage(HeadFactory.getSmallHeadByStatus(model));
		lblName.setText(model.displayName + " (" + model.qq + ')');
	}

	/**
	 * 停止闪烁图标
	 */
	protected void stopBlinkImage() {
		blinkRunnable.setStop(true);
	}

	/**
	 * 开始闪烁图标
	 */
	public void startBlinkImage() {
		if (!blinkRunnable.isStop())
			return;
		blinkRunnable.setStop(false);
		main.getDisplay().timerExec(0, blinkRunnable);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.window.Window#getShellStyle()
	 */
	@Override
	protected int getShellStyle() {
		int onTop = main.getOptionHelper().isIMOnTop() ? SWT.ON_TOP : SWT.NONE;
		if (LumaQQ.IS_GTK)
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.window.Window#open()
	 */
	@Override
	public int open() {
		int ret = super.open();
		// 设置输入框获得焦点，设置输入框的字体和颜色
		inputBox.setFocus();
		if (main.getDefaultStyle() != null)
			inputBox.setDefaultStyle(main.getDefaultStyle());
		return ret;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.window.Window#constrainShellSize()
	 */
	@Override
	protected void constrainShellSize() {
		if (model.talkMode)
			getShell().setSize(470, 430);
		else
			getShell().setSize(440, 340);

		Point loc = new Point(50, 50);
		int x = model.windowX;
		int y = model.windowY;
		if (x > 0)
			loc.x = x;
		if (y > 0)
			loc.y = y;
		getShell().setLocation(loc);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.window.Window#getInitialLocation(org.eclipse.swt.graphics.Point)
	 */
	@Override
	protected Point getInitialLocation(Point initialSize) {
		Point loc = new Point(50, 50);
		int x = model.windowX;
		int y = model.windowY;
		if (x > 0)
			loc.x = x;
		if (y > 0)
			loc.y = y;
		return loc;
	}

	/**
	 * 追加消息到输出框
	 * 
	 * @param f
	 * @param im
	 * @param header
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
	 *            RecordEntry
	 */
	private void appendMessage(RecordEntry entry) {
		outputBox.appendText('(' + DefaultFace.escapeFaces(main.getMyModel().displayName) + ")  " + DateTool.format(entry.time), myStyle);
		outputBox.appendText(entry.message, getLineStyle(main.getDefaultStyle()));
	}

	/**
	 * 在输出框中添加提示信息
	 * 
	 * @param hint
	 * @param style
	 */
	@SuppressWarnings("unused")
	public void appendHint(String hint, LineStyle style) {
		outputBox.appendText(hint, style);
	}

	/**
	 * @return true表示当前窗口是活动窗口
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * 发送群消息
	 * 
	 * @param string
	 */
	protected void sendMessage(String s) {
		// 检查消息是否为空
		if (s.length() == 0) {
			MessageDialog.openWarning(getShell(), message_box_common_warning_title, message_box_cannot_send_empty_message);
			return;
		}
		// 判断用户是否登录
		if (main.getClient().getUser().isLoggedIn()) {
			message = s;
			// 保存到聊天记录中
			RecordEntry key = new RecordEntry();
			key.owner = model.qq;
			key.sender = main.getMyModel().qq;
			key.senderParent = 0;
			key.receiver = model.qq;
			key.time = System.currentTimeMillis();
			key.type = IKeyConstants.NORMAL;
			key.message = message;
			main.getRecordManager().addRecord(key);

			// 把发送的消息显示到输出框中
			if (model.talkMode)
				appendMessage(key);

			if (model.talkMode)
				inputBox.clear();
			else {
				/*
				 * 如果是消息模式
				 */
				// 设置输入框不可写
				inputBox.setReadonly(true);
				inputBox.setBackground(Colors.READONLY_BACKGROUND);
				// 窗口最小化
				getShell().setMinimized(true);
			}

			// TODO 检查是否包含自定义表情，转换发送消息到QQ格式，启动自定义表情发送任务

			// 启动普通消息发送任务
			main.getIMJobQueue().addJob(new SendNormalIMJob(message, model));
		} else {
			MessageDialog.openWarning(getShell(), message_box_common_warning_title, message_box_please_login_first);
		}
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

	/**
	 * 关闭窗口
	 */
	public void closeWindow() {
		getShell().close();
	}

	/**
	 * 设置消息发送窗口为聊天模式或者消息模式
	 * 
	 * @param b
	 *            true表示为聊天模式
	 */
	public void setTalkMode(boolean b) {
		if (b) {
			model.talkMode = true;
			btnMode.setText(im_button_mode_message);
			if (main.getOptionHelper().isUseEnterInMessageMode() != main.getOptionHelper().isUseEnterInTalkMode())
				reinstallSendKey(main.getOptionHelper().isUseEnterInTalkMode());
			sash.setVisible(true);
			outputForm.setLayoutData(new GridData(GridData.FILL_BOTH));
			GridData gd = new GridData(GridData.FILL_HORIZONTAL);
			gd.heightHint = 100;
			inputForm.setLayoutData(gd);
			gd = (GridData) sash.getLayoutData();
			gd.heightHint = SWT.DEFAULT;
			if (model.talkMode)
				getShell().setSize(470, viewer.isVisible() ? 630 : 430);
			else
				getShell().setSize(440, viewer.isVisible() ? 540 : 340);
			inputForm.getParent().layout();
		} else {
			outputBox.clear();
			model.talkMode = false;
			btnMode.setText(im_button_mode_talk);
			if (main.getOptionHelper().isUseEnterInMessageMode() != main.getOptionHelper().isUseEnterInTalkMode())
				reinstallSendKey(main.getOptionHelper().isUseEnterInMessageMode());
			sash.setVisible(false);
			GridData gd = new GridData(GridData.FILL_HORIZONTAL);
			gd.heightHint = outputForm.getTopLeft().getSize().y + 2;
			outputForm.setLayoutData(gd);
			inputForm.setLayoutData(new GridData(GridData.FILL_BOTH));
			gd = (GridData) sash.getLayoutData();
			gd.heightHint = 0;
			if (model.talkMode)
				getShell().setSize(470, viewer.isVisible() ? 630 : 430);
			else
				getShell().setSize(440, viewer.isVisible() ? 540 : 340);
			inputForm.getParent().layout();
		}
	}

	/**
	 * 初始化当前文件传输任务菜单
	 */
	private void initFileMenu() {
		fileMenu = new Menu(getShell(), SWT.POP_UP);
		// 缺省，无任务进行中
		MenuItem mi = new MenuItem(fileMenu, SWT.PUSH);
		mi.setText(menu_file_none);
	}

	/**
	 * 设置焦点
	 */
	public void setFocus() {
		getShell().setFocus();
	}

	/**
	 * @return true表示聊天模式
	 */
	public boolean isTalkMode() {
		return model.talkMode;
	}

	/**
	 * 打开表情选择窗口
	 */
	private void openImageSelectionShell(Point loc) {
		if (fss != null && !fss.isDisposed())
			fss.getShell().close();

		fss = new ImageSelector(getShell(), new FaceImageAdvisor(main));
		fss.setListener(this);
		fss.setLocation(loc);
		fss.open();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.tsinghua.lumaqq.events.IFaceSelectionListener#faceSelected(edu.tsinghua.lumaqq.ui.IImageProvider, int, int)
	 */
	public void faceSelected(IImageSelectorAdvisor provider, int group, int sequence) {
		if (group == 0) {
			int index = res.getFaceCode(sequence);
			if (index != -1)
				inputBox.insertImage(IRichContent.DEFAULT_FACE_TAG, index);
		} else {
			MessageDialog.openInformation(getShell(), message_box_common_info_title, message_box_face_to_user);
			/*
			 * 暂时还不支持向单个用户发送自定义表情，注释掉这些代码
			 */
			// FaceUtil util = FaceUtil.getInstance();
			// Face face = util.getFace(group - 1, sequence);
			// if(face == null)
			// return;
			// int id = Util.getInt(face.getId(), -1);
			// if(id == -1)
			// return;
			// inputBox.insertImage(IRichContent.CUSTOM_FACE_TAG, id);
		}
	}

	/**
	 * @return the otherStyle
	 */
	public static LineStyle getOtherStyle() {
		return otherStyle;
	}

	/**
	 * @return the inputBox
	 */
	public RichBox getInputBox() {
		return inputBox;
	}
}
