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

import java.util.HashMap;
import java.util.Map;

import edu.tsinghua.lumaqq.LumaQQ;
import edu.tsinghua.lumaqq.models.Cluster;
import edu.tsinghua.lumaqq.models.Model;
import edu.tsinghua.lumaqq.models.User;
import edu.tsinghua.lumaqq.qq.packets.in.ReceiveIMPacket;
import edu.tsinghua.lumaqq.resource.Colors;
import edu.tsinghua.lumaqq.resource.Resources;
import edu.tsinghua.lumaqq.widgets.qstyle.Slat;
import edu.tsinghua.lumaqq.widgets.record.ClusterRecordProvider;
import edu.tsinghua.lumaqq.widgets.record.FriendRecordProvider;
import edu.tsinghua.lumaqq.widgets.record.IRecordProvider;
import edu.tsinghua.lumaqq.widgets.record.RecordViewer;

import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabFolder2Adapter;
import org.eclipse.swt.custom.CTabFolderEvent;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.MenuAdapter;
import org.eclipse.swt.events.MenuEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.graphics.Image;
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

/**
 * 标签页方式的消息发送窗口
 * 
 * @author luma
 */
public class SendIMTabWindow extends Window {
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
	            if(flag) {
	            	getShell().setImage(blinkImage);
	            	if(LumaQQ.IS_GTK)
		            	((BorderStyler)getShell().getData(BorderStyler.STYLER)).repaintTitleBar();
	            } else {
	            	getShell().setImage(res.getImage(Resources.icoBlank));
	            	if(LumaQQ.IS_GTK)
		            	((BorderStyler)getShell().getData(BorderStyler.STYLER)).repaintTitleBar();
	            }
	            flag = !flag;
	            if(!stop)
	                main.getDisplay().timerExec(500, this);
	            else {
	            	getShell().setImage(res.getImage(Resources.icoMessage));
	            	if(LumaQQ.IS_GTK)
		            	((BorderStyler)getShell().getData(BorderStyler.STYLER)).repaintTitleBar();
	            }
            } catch (Exception e) {         
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
    
	private MainShell main;
	private Resources res;
	private Menu enterMenu;
	private RecordViewer viewer;
	private CTabFolder folder;
	private IIMContainer activeContainer;
	private Blink blinkRunnable;
	private boolean active;
	
	// model到tab的注册表
	private Map<Model, CTabItem> model2tab;
	// model到container的注册表
	private Map<Model, IIMContainer> model2container;
	// id到model的注册表
	private Map<Integer, Model> id2model;
	
	// key advisor
	private IContainerKeyHandler keyHandler = new IContainerKeyHandler() {
		public void onKeyDown(int modifier, int keyChar) {
			if((modifier & SWT.MOD3) != 0) {
				switch(keyChar) {
					case 'H':
						showRecord();
						break;
					case 'C':
						closeActiveContainer();
						break;
					case 'Q':
						nextUnreadTab();
						break;
					case SWT.ARROW_RIGHT:
						nextTab();
						break;
					case SWT.ARROW_LEFT:
						previousTab();
						break;
				}				
			}
		}
	};
	
	/**
	 * 构造函数
	 * 
	 * @param main
	 * 		MainShell对象
	 */
	public SendIMTabWindow(MainShell main) {
		super(main.getShell());
		this.main = main;
		this.res = Resources.getInstance();
		model2tab = new HashMap<Model, CTabItem>();
		model2container = new HashMap<Model, IIMContainer>();
		id2model = new HashMap<Integer, Model>();
		blinkRunnable = new Blink();
		blinkRunnable.setBlinkImage(res.getImage(Resources.icoDialog));
		active = false;
		setBlockOnOpen(false);
	}

	/**
	 * 关闭当前tab
	 */
	protected void closeActiveContainer() {
		CTabItem tab = folder.getSelection();
		if(tab == null)
			return;
		
		IIMContainer container = (IIMContainer)tab.getControl();
		clearContainerRegistry(container);
		tab.dispose();
		
		if(folder.getItemCount() == 0) {      			
			closeWindow();
		}
	}

	/**
	 * 初始化使用Enter键的菜单
	 */
	private void initEnterMenu() {
		enterMenu = new Menu(getShell(), SWT.POP_UP);
		// 使用Enter键菜单
		final MenuItem miEnter = new MenuItem(enterMenu, SWT.RADIO);
		miEnter.setText(im_menu_use_enter);
		miEnter.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				main.getOptionHelper().setUseEnterInTalkMode(true);
				resetEnterKey(true);
			}
		});
		// 使用Ctrl + Enter键菜单
		final MenuItem miCtrlEnter = new MenuItem(enterMenu, SWT.RADIO);
		miCtrlEnter.setText(im_menu_use_ctrl_enter);
		miCtrlEnter.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				main.getOptionHelper().setUseEnterInTalkMode(false);
				resetEnterKey(false);
			}
		});
		// 添加菜单显示事件监听器
		enterMenu.addMenuListener(new MenuAdapter() {
			@Override
			public void menuShown(MenuEvent e) {
				boolean b = main.getOptionHelper().isUseEnterInTalkMode();
				miEnter.setSelection(b);
				miCtrlEnter.setSelection(!b);
			}
		});
	}
	
	/**
	 * 重设消息快捷键
	 * 
	 * @param b
	 * 		true表示使用Enter发送消息
	 */
	protected void resetEnterKey(boolean b) {
		for(IIMContainer container : model2container.values()) 
			container.setUseEnter(b);
	}

	@Override
	protected void configureShell(Shell newShell) {
		newShell.setText("LumaQQ - " + main.getMyModel().qq);
		newShell.setImage(res.getImage(Resources.icoMessage));
		newShell.addShellListener(new ShellAdapter() {
			@Override
			public void shellClosed(ShellEvent e) {
				main.getShellRegistry().deregisterSendIMTabWindow();
				for(IIMContainer container : model2container.values()) {
					main.getClient().removeQQListener(container.getQQListener());
					container.release();
				}
			}
			
			@Override
			public void shellDeactivated(ShellEvent e) {
				active = false;
			}
			
			@Override
			public void shellActivated(ShellEvent e) {
				active = true;
				stopBlinkImage();
			}
		});
		
        if(LumaQQ.IS_GTK) {
        	BorderStyler styler = new BorderStyler(main);
        	styler.setShowMaxButton(true);
        	styler.setHideWhenMinimize(false);
        	styler.decorateShell(newShell);        	
        }
	}
	
	@Override
	protected void constrainShellSize() {
		getShell().setSize(500, 470);
	}
	
	public void closeWindow() {
		getShell().close();
	}
	
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
        container.setLayout(layout);
        container.setBackground(Colors.MAINSHELL_BACKGROUND);
        
        folder = new CTabFolder(container, SWT.FLAT | SWT.TOP | SWT.CLOSE);
        folder.setLayoutData(new GridData(GridData.FILL_BOTH));
        folder.setBackground(Colors.MAINSHELL_BACKGROUND);
        folder.setSelectionBackground(Colors.LOGIN_BACKGROUND);
        folder.setUnselectedCloseVisible(false);
        folder.addCTabFolder2Listener(new CTabFolder2Adapter() {
        	@Override
        	public void close(CTabFolderEvent e) {
        		CTabItem tab = (CTabItem)e.item;
        		IIMContainer container = (IIMContainer)tab.getControl();
        		clearContainerRegistry(container);        		
        		
        		// 如果是最后一个tab，关闭窗口
        		if(folder.getItemCount() == 1) {
        			e.doit = false;        			
        			closeWindow();
        		}
        	}
        });
        folder.addSelectionListener(new SelectionAdapter() {
        	@Override
        	public void widgetSelected(SelectionEvent e) {
        		onTabSelection();
        	}
        });
        
        // 按钮区
        Composite buttonContainer = new Composite(container, SWT.NONE);
        buttonContainer.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
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
        
        Slat btnNextTab = new Slat(buttonContainer);
        btnNextTab.setText(button_next_tab);
        GridData gd = new GridData();
        gd.horizontalIndent = 3;
        btnNextTab.setLayoutData(gd);
        btnNextTab.addMouseListener(new MouseAdapter() {
            @Override
			public void mouseUp(MouseEvent e) {
				Slat temp = (Slat) e.getSource();
				if (e.x > temp.getSize().x || e.y > temp.getSize().y || e.x < 0 || e.y < 0) {
					return;
				}
                nextTab();
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
				getShell().close();
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
				IIMContainer container = getActiveContainer();
				if(container == null)
					return;
				container.send();
			}
		});
        
        Slat btnDropDown = new Slat(buttonContainer, SWT.CENTER);
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
		viewer = new RecordViewer(container, new FriendRecordProvider(null), main);
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
		viewer.addSelectionListener(
			new SelectionAdapter() {
				@Override
				public void widgetDefaultSelected(SelectionEvent e) {
					IIMContainer container = getActiveContainer();
					if(container != null)
						container.appendText(viewer.getSelectedText());
				}
			}
		);
		
		// 初始化
		initEnterMenu();
		
        return container;
	}
    
    /**
     * 	切换到下一个标签
     */
    protected void nextTab() {
    	int index = folder.getSelectionIndex();
    	if(index == -1 || index >= folder.getItemCount() - 1) {
    		if(folder.getItemCount() > 0) {
    			folder.setSelection(0);
    			onTabSelection();
    		}
    	} else if(folder.getItemCount() > index + 1) {
    		folder.setSelection(index + 1);
    		onTabSelection();
    	}
	}
    
    /**
     * 切换到上一标签
     */
    protected void previousTab() {
    	int index = folder.getSelectionIndex();
    	if(index == -1 || index == 0) {
    		if(folder.getItemCount() > 0) {
    			folder.setSelection(folder.getItemCount() - 1);
    			onTabSelection();
    		}
    	} else if(index > 0) {
    		folder.setSelection(index - 1);
    		onTabSelection();
    	} 
    }    
	
	/**
	 * 切换到下一个有消息未读的tab，如果没有，则保持不变
	 */
	protected void nextUnreadTab() {
		int count = folder.getItemCount();
    	int index = folder.getSelectionIndex();
    	if(index == -1 || index >= folder.getItemCount() - 1) {
  			for(int i = 0; i < count; i++) {
				if(getContainer(i).getUnreadCount() > 0) {
	    			folder.setSelection(i);
	    			onTabSelection();
	    			return;
				}
			}
    	} else if(count > index + 1) {
  			for(int i = index + 1; i < count; i++) {
				if(getContainer(i).getUnreadCount() > 0) {
	    			folder.setSelection(i);
	    			onTabSelection();
	    			return;
				}
			}
 			for(int i = 0; i < index; i++) {
				if(getContainer(i).getUnreadCount() > 0) {
	    			folder.setSelection(i);
	    			onTabSelection();
	    			return;
				}
			}
    	}
	}

	/**
     * @return
     * 		活动消息容器
     */
    protected IIMContainer getActiveContainer() {
		CTabItem tab = folder.getSelection();
		if(tab == null)
			return null;
		
		return (IIMContainer)tab.getControl();
	}
    
    /**
     * 得到第index个container
     * 
     * @param index
     * @return
     */
    private IIMContainer getContainer(int index) {
    	CTabItem tab = folder.getItem(index);
    	return (IIMContainer)tab.getControl();
    }

	/**
     * 清空相关数据
     * 
     * @param container
     */
    protected void clearContainerRegistry(IIMContainer container) {
		main.getClient().removeQQListener(container.getQQListener());
		model2container.remove(container.getModel());
		model2tab.remove(container.getModel());
		id2model.remove(container.getId());
		container.release();
	}

	/* (non-Javadoc)
     * @see org.eclipse.jface.window.Window#getInitialSize()
     */
	@Override
    protected Point getInitialSize() {
        return new Point(500, 470);
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
	
    /* (non-Javadoc)
     * @see org.eclipse.jface.window.Window#getInitialLocation(org.eclipse.swt.graphics.Point)
     */
    @Override
	protected Point getInitialLocation(Point initialSize) {
		// 缺省实现是把窗口居中
		Rectangle displayRect = main.getDisplay().getClientArea();
		int y = (displayRect.height - initialSize.y) / 2 - 100;
		if(y < 0)
			y = 0;
		return new Point((displayRect.width - initialSize.x) / 2, y);
    }

	/**
	 * 显示/隐藏聊天记录
	 */
	protected void showRecord() {
		IIMContainer container = getActiveContainer();
		if(container == null)
			return;
		
        GridData gd = (GridData)viewer.getLayoutData();
        Shell shell = getShell();
		if(gd.exclude) {
			gd.exclude = false;		
			refreshRecord(container);
		} else {
			gd.exclude = true;
		}
        if(!shell.getMaximized()) {
			Point p = shell.getSize();
			shell.setSize(p.x, p.y + (gd.exclude ? -205 : 205));						    
		}  
		viewer.setVisible(!gd.exclude);
        viewer.getParent().layout();
        shell.layout();
	}

	/**
	 * 刷新聊天记录
	 * 
	 * @param container
	 */
	private void refreshRecord(IIMContainer container) {
		IRecordProvider provider = viewer.getProvider();
		Model model = container.getModel();
		if(model instanceof User && !(provider instanceof FriendRecordProvider))
			viewer.setProvider(new FriendRecordProvider((User)model));
		else if(model instanceof Cluster && !(provider instanceof ClusterRecordProvider))
			viewer.setProvider(new ClusterRecordProvider((Cluster)model));
		else
			viewer.getProvider().setModel(model);
		viewer.refreshRecord();
	}
	
    /**
     * 设置窗口激活
     */
    public void setActive() {
        getShell().setActive();
    }
    
	public boolean isActive() {
		return active;
	}
    
    /**
     * 设置最小化状态
     * 
     * @param b
     */
    public void setMinimized(boolean b) {
        getShell().setMinimized(b);
    }

    /**
     * 设置焦点
     */
    public void setFocus() {
        getShell().setFocus();
    }
    
    /**
     * 添加标签页
     */
    public IIMContainer addTabIM(Model model) {
    	if(hasContainer(model))
    		return model2container.get(model);
    	
    	CTabItem tab = new CTabItem(folder, SWT.CLOSE);
    	IIMContainer container = null;    	
    	if(model instanceof User)
    		container = new NormalIMContainer(folder);
    	else
    		container = new ClusterIMContainer(folder);
    	container.setModel(model);
    	container.setMainShell(main);
    	container.setKeyHandler(keyHandler);
    	tab.setControl(container.getComposite());
    	tab.setImage(container.getHead());
    	tab.setText(container.getDisplayedName());
    	
    	model2tab.put(model, tab);
    	model2container.put(model, container);
    	id2model.put(container.getId(), model);

    	if(folder.getItemCount() == 1) {
    		folder.setSelection(tab);
    		onTabSelection();    		
    	}
    	
    	if(viewer.getVisible())
    		refreshRecord(container);
    	
    	container.setKeyboardFocus();
    	main.getClient().addQQListener(container.getQQListener());
    	container.init();
    	return container;
    }
    
    /**
     * @param id
     * 		Model的id
     * @return
     * 		true标明已经有了这个im容器
     */
    protected boolean hasContainer(int id) {
    	return id2model.containsKey(id);
    }
    
    /**
     * @param model
     * 		Model
     * @return
     * 		true标明已经有了这个im容器
     */
    public boolean hasContainer(Model model) {
    	return model2tab.containsKey(model);
    }
    
    /**
     * 刷新tab显示
     * 
     * @param model
     */
    public void refreshTab(Model model) {
    	CTabItem tab = model2tab.get(model);
    	if(tab == null)
    		return;
    	
    	IIMContainer container = model2container.get(model);
    	if(container == null)
    		return;
    	
    	container.setModel(model);
    	tab.setImage(container.getHead());
    	tab.setText(container.getDisplayedName());
    }
    
    /**
     * 激活一个容器
     * 
     * @param model
     * 		Model
     */
    public void activeContainer(Model model) {
    	IIMContainer container = model2container.get(model);
    	if(container == null)
    		return;
    	CTabItem newActiveTab = model2tab.get(model);
    	if(newActiveTab == null)
    		return;

    	folder.setSelection(newActiveTab);
    	((IIMContainer)newActiveTab.getControl()).setKeyboardFocus();
    }
    
    /**
     * 推入一个消息
     * 
     * @param model
     * 		Model
     * @param packet
     * 		消息包对象
     */
    public void putMessage(Model model, ReceiveIMPacket packet) {
    	IIMContainer container = model2container.get(model);
    	if(container == null)
    		container = addTabIM(model);
    	
    	container.putMessage(packet);
    	if(!container.isActiveContainer())
    		refreshTabText(container);
    }

    @Override
    protected Shell getParentShell() {
    	return null;
    }
    
	/**
	 * @param container
	 */
	private void refreshTabText(IIMContainer container) {
		Model m = container.getModel();
		CTabItem tab = model2tab.get(m);
		if(tab != null) {
			StringBuilder sb = new StringBuilder();
			if(container.getUnreadCount() > 0) {
				sb.append('(')
					.append(container.getUnreadCount())
					.append(')')				
					.append(' ');
			};
			sb.append(container.getDisplayedName());
			tab.setText(sb.toString());    			
		}
	}

	/**
	 * 当前tab改变后调用此方法
	 */
	private void onTabSelection() {
		IIMContainer container = getActiveContainer();
		if(activeContainer != null) {
			activeContainer.setActive(false);
			refreshTabText(activeContainer);
		}
		activeContainer = container;
		if(activeContainer != null) {
			activeContainer.setActive(true);
			refreshTabText(activeContainer);
		}
		
		container.setKeyboardFocus();
		if(viewer.getVisible()) {
			if(container != null && container.getModel() != viewer.getProvider().getModel())
				refreshRecord(container);
		}
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
        if(!blinkRunnable.isStop()) return;
        blinkRunnable.setStop(false);
        main.getDisplay().timerExec(0, blinkRunnable);
    }
}
