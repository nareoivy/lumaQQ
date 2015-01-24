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
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
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
import org.eclipse.swt.events.MouseTrackListener;
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
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.ColorDialog;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.FontDialog;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Sash;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import edu.tsinghua.lumaqq.ecore.face.Face;
import edu.tsinghua.lumaqq.events.BaseQQListener;
import edu.tsinghua.lumaqq.events.IFaceSelectionListener;
import edu.tsinghua.lumaqq.models.Cluster;
import edu.tsinghua.lumaqq.models.ClusterType;
import edu.tsinghua.lumaqq.models.GroupType;
import edu.tsinghua.lumaqq.models.Model;
import edu.tsinghua.lumaqq.models.ModelRegistry;
import edu.tsinghua.lumaqq.models.User;
import edu.tsinghua.lumaqq.qq.QQ;
import edu.tsinghua.lumaqq.qq.beans.ClusterIM;
import edu.tsinghua.lumaqq.qq.events.IQQListener;
import edu.tsinghua.lumaqq.qq.events.QQEvent;
import edu.tsinghua.lumaqq.qq.packets.in.ClusterCommandReplyPacket;
import edu.tsinghua.lumaqq.qq.packets.in.ReceiveIMPacket;
import edu.tsinghua.lumaqq.record.IKeyConstants;
import edu.tsinghua.lumaqq.record.RecordEntry;
import edu.tsinghua.lumaqq.resource.Colors;
import edu.tsinghua.lumaqq.resource.DefaultFace;
import edu.tsinghua.lumaqq.resource.Resources;
import edu.tsinghua.lumaqq.ui.config.user.UserInfoWindow;
import edu.tsinghua.lumaqq.ui.helper.DateTool;
import edu.tsinghua.lumaqq.ui.helper.FaceRegistry;
import edu.tsinghua.lumaqq.ui.provider.ClusterMemberLabelProvider;
import edu.tsinghua.lumaqq.ui.provider.MapValueContentProvider;
import edu.tsinghua.lumaqq.ui.sorter.ModelSorter;
import edu.tsinghua.lumaqq.widgets.FaceImageAdvisor;
import edu.tsinghua.lumaqq.widgets.IImageSelectorAdvisor;
import edu.tsinghua.lumaqq.widgets.ImageSelector;
import edu.tsinghua.lumaqq.widgets.rich.IRichContent;
import edu.tsinghua.lumaqq.widgets.rich.LineStyle;
import edu.tsinghua.lumaqq.widgets.rich.RichBox;

/**
 * 群聊天容器
 * 
 * @author luma
 */
public class ClusterIMContainer extends Composite implements IIMContainer, IFaceSelectionListener, IIMSender {   
    //设置输入框为可输入
	private Runnable enableRunnable = new Runnable() {
        public void run() {
        	if(inputBox.isDisposed())
        		return;
            inputBox.setReadonly(false);
            inputBox.setBackground(Colors.WHITE);
        }
    };
    
    /**
     * 往输出框追加提示信息
     * 
     * @author luma
     */
    private class AppendRunnable implements Runnable {
        public String hint;
        
        /* (non-Javadoc)
         * @see java.lang.Runnable#run()
         */
        public void run() {
        	if(inputBox.isDisposed())
        		return;
            appendHint('\n' + hint + "\n\n", hintStyle);
        }
    }
    
    private BaseQQListener qqListener = new BaseQQListener() {
    	@Override
    	protected void OnQQEvent(QQEvent e) {
    		switch(e.type) {
    			case QQEvent.CLUSTER_GET_INFO_FAIL:
    			case QQEvent.CLUSTER_GET_MEMBER_INFO_FAIL:
    			case QQEvent.CLUSTER_GET_ONLINE_MEMBER_FAIL:
    			case QQEvent.CLUSTER_GET_TEMP_INFO_FAIL:
    				processClusterCommandFail(e);
    				break;
    			default:
    		        workflow.delegateQQEvent(e);
    				break;
    		}
    	}
    };
    
	private Resources res;
	private MainShell main;
	
	private IContainerKeyHandler keyHandler;
	private int unread;
	private boolean active;
	private Cluster cluster;
	
	// 消息流程封装类
	private ClusterIMDelegate workflow;
	
	private AppendRunnable appendRunnable;
	
	private CLabel lblName;
	private CLabel lblList;
	private RichBox outputBox, inputBox;
	private Composite sideContainer, chatContainer, noticeContainer;
	private TableViewer listViewer;
	private Sash vSash;
	private Text textNotice;
	private ToolBar infoBar;
	private Menu memberMenu;
	
	// style样式表缓冲区
	private List<LineStyle> styleCache;
	// 提示信息的样式
	private static final LineStyle hintStyle = new LineStyle(null, null, "宋体", SWT.NORMAL, 9);
	// 缺省的用户名称提示样式
	private static final LineStyle myStyle = new LineStyle(Colors.MY_HINT_COLOR, null, "宋体", SWT.NORMAL, 9);
	private static final LineStyle otherStyle = new LineStyle(Colors.BLUE, null, "宋体", SWT.NORMAL, 9);
	
	private ViewForm inputForm, infoForm;

	// true表示群栏目收起
	private boolean infoFormExpanded;
	
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
	
    // 系统平台
    private static boolean IS_GTK;
    private static boolean IS_MOTIF;
    static {
        String platform = SWT.getPlatform();
        IS_GTK = "gtk".equals(platform);
        IS_MOTIF = "motif".equals(platform);
    }
	
	public ClusterIMContainer(Composite parent) {
		super(parent, SWT.NONE);
		res = Resources.getInstance();
		unread = 0;
		active = false;
		styleCache = new ArrayList<LineStyle>();
		initLayout();
	}
	
	private void initLayout() {
        GridLayout layout = new GridLayout(3, false);
        layout.horizontalSpacing = layout.verticalSpacing = layout.marginHeight = layout.marginWidth = 0;
        setLayout(layout);
        setBackground(Colors.MAINSHELL_BACKGROUND);
        
        // 聊天区
        chatContainer = new Composite(this, SWT.NONE);
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
        lblName = new CLabel(outputForm, SWT.LEFT);
        lblName.setBackground(Colors.VIEWFORM_BANNER_BACKGROUND);
        lblName.setCursor(getDisplay().getSystemCursor(SWT.CURSOR_HAND));
        lblName.setForeground(Colors.VIEWFORM_BANNER_TEXT);
        lblName.addMouseListener(new MouseAdapter() {
            @Override
			public void mouseUp(MouseEvent e) {
                main.getShellLauncher().openClusterInfoWindow(cluster);
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
                GridData gd = (GridData)inputForm.getLayoutData();
                gd.heightHint = inputForm.getParent().getClientArea().height - e.y;
                inputForm.getParent().layout();
            }
        });
        
        inputForm = new ViewForm(chatContainer, SWT.FLAT);
        inputForm.setBackground(Colors.VIEWFORM_BANNER_BACKGROUND);
        GridData gd = new GridData(GridData.FILL_HORIZONTAL);
        gd.heightHint = 100;
        inputForm.setLayoutData(gd);
        final ToolBar tb = new ToolBar(inputForm, SWT.FLAT);
        tb.setBackground(Colors.VIEWFORM_BANNER_BACKGROUND);
        // 字体
        ToolItem tiFont = new ToolItem(tb, SWT.NONE);        
        tiFont.setImage(res.getImage(Resources.icoFont));
        tiFont.setToolTipText(tooltip_button_font);
        tiFont.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				FontDialog dialog = new FontDialog(getShell());
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
	                ColorDialog dialog = new ColorDialog(getShell());
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
        ToolItem tiFace = new ToolItem(tb, SWT.NONE);
        tiFace.setImage(res.getImage(Resources.icoSmiley));
        tiFace.setToolTipText(tooltip_button_face);
        tiFace.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Rectangle bound = tb.getBounds();
			    openImageSelectionShell(tb.getParent().toDisplay(bound.x, bound.y + bound.height));
			}
		});
        // 动画状态
        ToolItem tiAnimation = new ToolItem(tb, SWT.CHECK);
        tiAnimation.setImage(res.getImage(Resources.icoStop));
        tiAnimation.setToolTipText(tooltip_button_disable_animation);
        tiAnimation.addSelectionListener(new SelectionAdapter() {
            @Override
			public void widgetSelected(SelectionEvent e) {
                ToolItem ti = (ToolItem)e.widget;
                outputBox.setForbiddenAnimation(ti.getSelection());
                inputBox.setForbiddenAnimation(ti.getSelection());                
                ti.setToolTipText(ti.getSelection() ? tooltip_button_animation : tooltip_button_disable_animation);
            }
        });
        inputForm.setTopLeft(tb);
        ToolBar tbSwitch = new ToolBar(inputForm, SWT.FLAT);
        tbSwitch.setBackground(Colors.VIEWFORM_BANNER_BACKGROUND);
        ToolItem tiSwitch = new ToolItem(tbSwitch, SWT.NONE);
        tiSwitch.setImage(res.getImage(Resources.icoRightArrow));
        tiSwitch.setToolTipText(tooltip_button_hide);
        inputForm.setTopRight(tbSwitch);
        inputBox = new RichBox(inputForm);
        inputBox.setBackground(Colors.WHITE);
        inputForm.setContent(inputBox);
        tiSwitch.addSelectionListener(new SelectionAdapter() {
            @Override
			public void widgetSelected(SelectionEvent e) {
				ToolItem item = (ToolItem)e.getSource();
                GridData d = (GridData)sideContainer.getLayoutData();
				d.exclude = !d.exclude;
				d = (GridData)vSash.getLayoutData();
				d.exclude = !d.exclude;
				if(d.exclude) {
					sideContainer.setVisible(false);
					((GridData)chatContainer.getLayoutData()).horizontalSpan = 3;
                    item.setImage(res.getImage(Resources.icoLeftArrow));
                    item.setToolTipText(tooltip_button_show);
				} else {
					sideContainer.setVisible(true);
					((GridData)chatContainer.getLayoutData()).horizontalSpan = 1;
                    item.setImage(res.getImage(Resources.icoRightArrow));
                    item.setToolTipText(tooltip_button_hide);
				}
				
                sideContainer.getParent().layout();
            }
        });
        
        // 垂直sash
        vSash = new Sash(this, SWT.VERTICAL);
        gd = new GridData(GridData.FILL_VERTICAL);
        gd.verticalSpan = 2;
		vSash.setLayoutData(gd);
		vSash.setBackground(Colors.MAINSHELL_BACKGROUND);
		vSash.addSelectionListener(new SelectionAdapter() {
            @Override
			public void widgetSelected(SelectionEvent e) {
                GridData d = (GridData)sideContainer.getLayoutData();   
                GridLayout l = (GridLayout)sideContainer.getParent().getLayout();
                d.widthHint = sideContainer.getParent().getClientArea().width - e.x - (l.marginWidth << 1);
                sideContainer.getParent().layout();
            }
        });
        
        // 成员列表区
        sideContainer = new Composite(this, SWT.NONE);
        gd = new GridData(GridData.FILL_VERTICAL);
        gd.verticalSpan = 2;
        gd.widthHint = 140;
        sideContainer.setLayoutData(gd);
        layout = new GridLayout();
        layout.marginHeight = layout.marginWidth = 1;
        layout.horizontalSpacing = layout.verticalSpacing = 0;
        sideContainer.setLayout(layout);
        sideContainer.addPaintListener(new PaintListener() {
            public void paintControl(PaintEvent e) {
                Composite c = (Composite)e.getSource();
                Rectangle rect = c.getClientArea();
                rect.width--;
                rect.height--;
                if(rect.width > 0 && rect.height > 0) {
	                e.gc.setForeground(Colors.WIDGET_BORDER);
	                e.gc.drawRectangle(rect);                    
                }
            }
        });
        
        // 群栏目
        MouseTrackListener mtl = new MouseTrackAdapter() {
        	@Override
        	public void mouseEnter(MouseEvent e) {
        		if(!infoBar.isVisible())
        			infoBar.setVisible(true);
        	}
        	@Override
        	public void mouseExit(MouseEvent e) {
        		Control control = main.getDisplay().getCursorControl();
        		if(control == null)
        			infoBar.setVisible(false);
        		else if(control == infoBar ||
        				control == noticeContainer ||
        				control.getParent() == noticeContainer)
        			return;
        		else
        			infoBar.setVisible(false);
        	}
        };        

    	infoForm = new ViewForm(sideContainer, SWT.FLAT);
    	gd = new GridData(GridData.FILL_HORIZONTAL);
    	gd.heightHint = 140; 
    	infoForm.setLayoutData(gd);
    	final CLabel lblExpand = new CLabel(infoForm, SWT.LEFT);
    	lblExpand.setImage(res.getImage(Resources.icoFlatCollapse));
    	lblExpand.setText(cluster_im_list_info);
    	lblExpand.setBackground(Colors.VIEWFORM_BANNER_BACKGROUND);
    	lblExpand.setForeground(Colors.VIEWFORM_BANNER_TEXT);
    	infoForm.setTopLeft(lblExpand);
    	lblExpand.addMouseListener(new MouseAdapter() {
    		@Override
    		public void mouseUp(MouseEvent e) {
    			infoFormExpanded = !infoFormExpanded;
    			if(infoFormExpanded) {
    				lblExpand.setImage(res.getImage(Resources.icoFlatCollapse));
    				GridData gd = (GridData)infoForm.getLayoutData();
    				gd.heightHint = 140;
    				sideContainer.layout();
    			} else {
    				lblExpand.setImage(res.getImage(Resources.icoFlatExpand));
    				GridData gd = (GridData)infoForm.getLayoutData();
    				gd.heightHint = infoForm.getTopLeft().getSize().y;
    				sideContainer.layout();
    			}
    		}
    	});
    	Composite content = new Composite(infoForm, SWT.NONE);
    	content.setLayout(new FormLayout());
    	FormData fd = new FormData();
    	fd.left = fd.top = new FormAttachment(0, 0);
    	fd.bottom = fd.right = new FormAttachment(100, 0);
    	noticeContainer = new Composite(content, SWT.NONE);
    	noticeContainer.setBackground(Colors.TOOLTIP_BACKGROUND);
    	noticeContainer.setLayout(new GridLayout());
    	noticeContainer.setLayoutData(fd);
    	CLabel lblNotice = new CLabel(noticeContainer, SWT.LEFT);
    	lblNotice.setImage(res.getImage(Resources.icoSysMsg));
    	lblNotice.setText(cluster_im_label_notice);
    	lblNotice.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_CENTER));
    	lblNotice.setBackground(noticeContainer.getBackground());
    	lblNotice.addMouseTrackListener(mtl);
    	textNotice = new Text(noticeContainer, SWT.MULTI | SWT.WRAP | SWT.READ_ONLY);
    	textNotice.setBackground(noticeContainer.getBackground());
    	textNotice.setLayoutData(new GridData(GridData.FILL_BOTH));
    	textNotice.addMouseTrackListener(mtl);
    	infoBar = new ToolBar(content, SWT.FLAT);
    	infoBar.setBackground(Colors.WHITE);
    	ToolItem tiInfo = new ToolItem(infoBar, SWT.PUSH);
    	tiInfo.setImage(res.getImage(Resources.icoClusterInfo));
    	tiInfo.setToolTipText(cluster_im_button_info);
    	tiInfo.addSelectionListener(new SelectionAdapter() {
    		@Override
    		public void widgetSelected(SelectionEvent e) {
				main.getShellLauncher().openClusterInfoWindow(cluster);
    		}
    	});
    	ToolItem tiCard = new ToolItem(infoBar, SWT.PUSH);
    	tiCard.setImage(res.getImage(Resources.icoClusterCard));
    	tiCard.setToolTipText(cluster_im_button_card);
    	tiCard.addSelectionListener(new SelectionAdapter() {
    		@Override
    		public void widgetSelected(SelectionEvent e) {
				User u = cluster.getMember(main.getMyModel().qq);
				if(u == null)
					return;
				u.info = main.getMyModel().info;
				UserInfoWindow uiw = main.getShellLauncher().openUserInfoWindow(u, UserInfoWindow.EDITABLE);
				uiw.setCurrentPage(UserInfoWindow.CARD);
    		}
    	});
    	ToolItem tiUpdate = new ToolItem(infoBar, SWT.PUSH);
    	tiUpdate.setImage(res.getImage(Resources.icoRefresh));
    	tiUpdate.setToolTipText(cluster_im_button_update);
    	tiUpdate.addSelectionListener(new SelectionAdapter() {
    		@Override
    		public void widgetSelected(SelectionEvent e) {
    			main.getClient().cluster_GetInfo(cluster.clusterId);
    			cluster.cardUpdated = false;
				main.getClient().cluster_GetCardBatch(cluster.clusterId, 0);
    		}
    	});
    	fd = new FormData();
    	fd.right = fd.bottom = new FormAttachment(100, 0);
    	infoBar.setLayoutData(fd);
    	infoBar.moveAbove(noticeContainer);
    	infoBar.setVisible(false);
    	infoBar.addMouseTrackListener(mtl);
    	infoBar.addPaintListener(new PaintListener() {
    		public void paintControl(PaintEvent e) {
    			Rectangle rect = infoBar.getClientArea();
    			e.gc.setForeground(Colors.PAGE_CONTROL_BORDER);
    			rect.height--;
    			rect.width--;
    			e.gc.drawRectangle(rect);
    		}
    	});
    	noticeContainer.addMouseTrackListener(mtl);
    	infoForm.setContent(content);    	
        
        // 群成员列表
        ViewForm listForm = new ViewForm(sideContainer, SWT.FLAT);
        listForm.setLayoutData(new GridData(GridData.FILL_BOTH));
        lblList = new CLabel(listForm, SWT.LEFT);
        lblList.setImage(res.getSmallClusterHead(6));
        lblList.setBackground(Colors.VIEWFORM_BANNER_BACKGROUND);
        lblList.setForeground(Colors.VIEWFORM_BANNER_TEXT);
        listForm.setTopLeft(lblList);
        listViewer = new TableViewer(listForm, SWT.SINGLE | SWT.V_SCROLL | SWT.FULL_SELECTION);
        listViewer.setLabelProvider(new ClusterMemberLabelProvider());
        listViewer.setSorter(new ModelSorter());
        Table t = listViewer.getTable();
        new TableColumn(t, SWT.LEFT);
        new TableColumn(t, SWT.LEFT);
        t.addControlListener(new ControlAdapter() {
            @Override
			public void controlResized(ControlEvent e) {
                Table table = (Table)e.getSource();
                table.getColumn(0).setWidth(20);
                table.getColumn(1).setWidth(table.getClientArea().width - 20);
            }
        });
        t.addMouseListener(new MouseAdapter() {
            @Override
			public void mouseUp(MouseEvent e) {
                if(e.button == 3) {        
                    Table table = (Table)e.getSource();
                    memberMenu.setLocation(table.toDisplay(e.x, e.y));
                    memberMenu.setVisible(true);
                }
            }
        });
        t.addSelectionListener(new SelectionAdapter() {
            @Override
			public void widgetDefaultSelected(SelectionEvent e) {
			    IStructuredSelection selection = (IStructuredSelection)listViewer.getSelection();	    
				if(!selection.isEmpty()) {
					User f = (User)selection.getFirstElement();
					main.getShellLauncher().openUserInfoWindow(f, UserInfoWindow.READ_ONLY);
					main.getClient().user_GetInfo(f.qq);
				}
            }
        });
        t.setHeaderVisible(false);
        t.setLinesVisible(false);        
        listForm.setContent(t);           

		
		// 初始化菜单
		initMemberMenu();
		
        addDisposeListener(new DisposeListener() {
            public void widgetDisposed(DisposeEvent e) {
                int size = styleCache.size();
                for(int i = 0; i < size; i++) {
                    LineStyle style = styleCache.get(i);
                    if(style.foreground != null)
                        style.foreground.dispose();
                    if(style.background != null)
                        style.background.dispose();
                }
                workflow.dispose();
            }
        });
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
        
        // 发送消息的快捷键
        if(main.getOptionHelper().isUseEnterInTalkMode()) {
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
     * 打开表情选择窗口
     */
    private void openImageSelectionShell(Point loc) {
    	ImageSelector fss = new ImageSelector(getShell(), new FaceImageAdvisor(main));
    	fss.setListener(this);
		fss.setLocation(loc);
		fss.open();	
    }

	public void setKeyHandler(IContainerKeyHandler ka) {
		keyHandler = ka;
	}

	public Image getHead() {
		if(cluster.isPermanent())
			return res.getSmallClusterHead(cluster.headId);
		else
			return res.getImage(Resources.icoDialog);
	}

	public int getUnreadCount() {
		return unread;
	}

	public void setActive(boolean active) {
		this.active = active;
		if(active)
			unread = 0;
	}

	public String getDisplayedName() {
		return cluster.name;
	}

	public int getId() {
		return cluster.clusterId;
	}

	public Model getModel() {
		return cluster;
	}

	public void setModel(Model model) {
		this.cluster = (Cluster)model;
		GridData gd = (GridData)infoForm.getLayoutData();
		gd.exclude = !cluster.isPermanent();
		infoForm.getParent().layout();
		if(!gd.exclude)
	    	setNotice(cluster.notice);    
		
        setListLabel(cluster.members.size(), 0);
        setClusterNameLabel();
        listViewer.setContentProvider(new MapValueContentProvider(cluster.members));
        listViewer.setInput(this);
	}
	
    /**
     * 刷新群名称标签
     */
    private void setClusterNameLabel() {
    	if(cluster.clusterType == ClusterType.NORMAL)
    		lblName.setImage(res.getSmallClusterHead(cluster.headId));
    	else
    		lblName.setImage(res.getImage(Resources.icoDialog));
    	
        if(cluster.isPermanent())
            lblName.setText(cluster.name + " (" + cluster.externalId + ')');
        else 
            lblName.setText(cluster.name);
    }

	public void setMainShell(MainShell main) {
		this.main = main;
		workflow = new ClusterIMDelegate(main.getClient());
		workflow.setSender(this);
		// 初始化快捷键设置
		initKeyAction();
	}

	public void send() {
		String s = inputBox.getText();
	    // 检查消息是否为空
	    if(s.length() == 0) {
	        MessageDialog.openWarning(getShell(), message_box_common_warning_title, message_box_cannot_send_empty_message);
	        return;
	    }
	    // 判断用户是否登录
		if(main.getClient().getUser().isLoggedIn()) {
		    workflow.setOriginalMessage(s);
		    workflow.start();
		} else {
		    MessageDialog.openWarning(getShell(), message_box_common_warning_title, message_box_please_login_first);
		}
	}

	public void putMessage(ReceiveIMPacket packet) {
		appendMessage(cluster, packet.clusterIM, false);
		if(!active)
			unread++;
	}

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

	public Composite getComposite() {
		return this;
	}

	public void setText(String text) {
		inputBox.setText(text);
	}
	
    /**
	 * <pre>
	 * 处理群命令失败事件：失败时，弹出一个对话框提示，如果错误类型表示自己已经不是这个群的
	 * 成员，则移除这个群
	 * </pre>
	 * @param e
	 */
	private void processClusterCommandFail(QQEvent e) {
		final ClusterCommandReplyPacket packet = (ClusterCommandReplyPacket)e.getSource();
		if(packet.clusterId == cluster.clusterId)
		    MessageDialog.openError(main.getShell(), message_box_common_fail_title, packet.errorMessage);
	}
	
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.events.IFaceSelectionListener#faceSelected(edu.tsinghua.lumaqq.ui.IImageProvider, int, int)
     */
    public void faceSelected(IImageSelectorAdvisor provider, int group, int sequence) {
        if(group == 0) {
            int index = res.getFaceCode(sequence);
            if(index != -1)
                inputBox.insertImage(IRichContent.DEFAULT_FACE_TAG, index);
        } else {
            FaceRegistry util = FaceRegistry.getInstance();
            Face face = util.getFace(group - 1, sequence);
            if(face == null)
                return;
            int id = face.getId();
            inputBox.insertImage(IRichContent.CUSTOM_FACE_TAG, id);
        }
    }
    
    /**
     * 设置群公告
     * 
     * @param notice
     */
    private void setNotice(String notice) {
    	if(notice != null && infoForm != null)
    		textNotice.setText(notice);
	}
    
    /**
     * 设置列表提示文本
     * 
     * @param total
     * 		群内人数
     * @param online
     * 		群内在线数
     */
    private void setListLabel(int total, int online) {
        lblList.setText(NLS.bind(cluster_im_list_label, String.valueOf(online), String.valueOf(total)));
    }
    
	/**
	 * 初始化成员菜单
	 */
	private void initMemberMenu() {
		memberMenu = new Menu(getShell());
		// 收发消息
		final MenuItem miSend = new MenuItem(memberMenu, SWT.PUSH);
		miSend.setText(cluster_im_menu_member_send);
		miSend.setImage(res.getImage(Resources.icoSendReceiveMessage));
		miSend.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				openMemberMessageShell();
			}
		});
		// 临时会话
		MenuItem mi = new MenuItem(memberMenu, SWT.PUSH);
		mi.setImage(res.getImage(Resources.icoTempSessionIM));
		mi.setText(menu_friend_temp_session);
		mi.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			    IStructuredSelection selection = (IStructuredSelection)listViewer.getSelection();
				if(!selection.isEmpty()) {
					User u = (User)selection.getFirstElement();
					main.getShellLauncher().openTempSessionIMWindow(u.qq);					
				}
			}
		});
		new MenuItem(memberMenu, SWT.SEPARATOR);
		// 加为好友
		final MenuItem miAdd = new MenuItem(memberMenu, SWT.PUSH);
		miAdd.setText(cluster_im_menu_member_add);
		miAdd.setImage(res.getImage(Resources.icoAddFriend));
		miAdd.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			    IStructuredSelection selection = (IStructuredSelection)listViewer.getSelection();	    
				if(!selection.isEmpty()) {
					User f = (User)selection.getFirstElement();
					main.getShellLauncher().openAddReceiveSystemMessageShell(f);
					main.getClient().user_Add(f.qq);																	
				}
			}
		});		
		// 查看资料
		mi = new MenuItem(memberMenu, SWT.PUSH);
		mi.setText(cluster_im_menu_member_viewinfo);
		mi.setImage(res.getImage(Resources.icoPersonInfo));
		mi.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			    IStructuredSelection selection = (IStructuredSelection)listViewer.getSelection();	    
				if(!selection.isEmpty()) {
					User f = (User)selection.getFirstElement();
					main.getShellLauncher().openUserInfoWindow(f, UserInfoWindow.READ_ONLY);
				}
			}
		});
		// separator
		mi = new MenuItem(memberMenu, SWT.SEPARATOR);
		// 消息管理菜单
		mi = new MenuItem(memberMenu, SWT.CASCADE);
		mi.setText(cluster_im_menu_member_message);
		mi.setImage(res.getImage(Resources.icoMessageManage));
		Menu msgMenu = new Menu(mi);
		mi.setMenu(msgMenu);
		// 导出聊天记录菜单
		mi = new MenuItem(msgMenu, SWT.PUSH);
		mi.setText(cluster_im_menu_member_export);
		mi.setImage(res.getImage(Resources.icoTxtFile));
		mi.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			    IStructuredSelection selection = (IStructuredSelection)listViewer.getSelection();	    
				if(!selection.isEmpty()) {
					User f = (User)selection.getFirstElement();
					// 导出
					main.getExportHelper().exportMessage(f);
				}
			}
		});
		// 添加菜单显示事件监听器
		memberMenu.addMenuListener(new MenuAdapter() {
			@Override
			public void menuShown(MenuEvent e) {
			    IStructuredSelection selection = (IStructuredSelection)listViewer.getSelection();	    
				if(selection.isEmpty())
					memberMenu.setVisible(false);
				else {
					User f = (User)selection.getFirstElement();
					// 设置菜单的使能状态，对于好友，可以发消息，不是好友不能发
					boolean b = f.isFriend();
					miSend.setEnabled(b);
					miAdd.setEnabled(!b);
				}
			}
		});
	}
	
    /**
	 * 打开与某个成员的聊天窗口
	 * 
	 * @return 
	 * 		打开的窗口对象，可能为ReceiveMessageShell, 也可能为SendMessageShell
	 */
	protected void openMemberMessageShell() {
	    IStructuredSelection selection = (IStructuredSelection)listViewer.getSelection();	    
		if(!selection.isEmpty()) {
			User f = (User)selection.getFirstElement();
			// 在现在的好友中查找这个model
			User f2 = ModelRegistry.getUser(f.qq);
			// 如果找到model为null，说明这个人不是我的好友了，为了界面上的一致
			// 性，把这个人添加到陌生人再打开窗口。否则，这个人肯定已经在model中
			// ，直接打开窗口
			if(f2 == null) {
				/* 否则这个人还不是我的好友 */
				f2 = f;
				main.getBlindHelper().addUser(f2, GroupType.STRANGER_GROUP);
				main.getBlindHelper().refreshGroup(f2.group);
			}
			main.getShellLauncher().openNormalIMWindow(f2);
		}
	}

	public void send(String msg) {
	    // 发送	    
	    LineStyle style = main.getDefaultStyle();
		if(cluster.isPermanent())
		    main.getClient().im_SendCluster(
		    		cluster.clusterId,
		            msg,
		            workflow.getMessageId(), 
		            workflow.getTotalFragments(), 
		            workflow.getCurrentFragment(),
		            style.fontName,
		            (style.fontStyle & SWT.BOLD) != 0,
		            (style.fontStyle & SWT.ITALIC) != 0,
		            false,
		            style.fontSize,
		            style.foreground.getRed(),
		            style.foreground.getGreen(),
		            style.foreground.getBlue());
		else
		    main.getClient().im_SendTempCluster(
		    		cluster.clusterType.toQQConstant(),
		    		cluster.clusterId, 
		    		cluster.parentClusterId, 
		            msg, 
		            workflow.getMessageId(), 
		            workflow.getTotalFragments(), 
		            workflow.getCurrentFragment(),
		            style.fontName,
		            (style.fontStyle & SWT.BOLD) != 0,
		            (style.fontStyle & SWT.ITALIC) != 0,
		            false,
		            style.fontSize,
		            style.foreground.getRed(),
		            style.foreground.getGreen(),
		            style.foreground.getBlue());
	}

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.ui.IIMSender#notifyOver()
     */
    public void notifyOver() {
        main.getDisplay().syncExec(enableRunnable);
    }
    
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.ui.IIMSender#notifyStart(java.lang.String)
     */
    public void notifyStart(String toBeSent) {
	    // 设置输入框暂时不可写
	    inputBox.setReadonly(true);
	    inputBox.setBackground(Colors.READONLY_BACKGROUND);	    
		// 保存到聊天记录中
	    RecordEntry key = new RecordEntry();
	    key.owner = cluster.clusterId;
	    key.sender = main.getMyModel().qq;
	    key.senderParent = cluster.clusterId;
	    key.receiver = cluster.clusterId;
	    key.time = System.currentTimeMillis();
	    key.type = IKeyConstants.CLUSTER;
	    key.message = toBeSent;
		main.getRecordManager().addRecord(key);
		// 清空发送框
		inputBox.clear();
		// 把发送的消息显示到输出框中
		appendMessage(key);
    }
    
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.ui.IIMSender#notifyFail(java.lang.String)
     */
    public void notifyFail(String msg) {
	    main.getDisplay().syncExec(enableRunnable);
		appendRunnable.hint = msg;
	    main.getDisplay().syncExec(appendRunnable);
    }
    
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.ui.IIMSender#notifyTimeout(java.lang.String)
     */
    public void notifyTimeout(String failToSent) {
	    main.getDisplay().syncExec(enableRunnable);
		appendRunnable.hint = NLS.bind(cluster_im_hint_timemout, failToSent);
	    main.getDisplay().syncExec(appendRunnable);
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.ui.IIMSender#getSenderId()
     */
    public int getSenderId() {
        return cluster.clusterId;
    }
    
	/**
	 * 在输出框中添加提示信息
	 * 
	 * @param hint
	 * @param style
	 */
	private void appendHint(String hint, LineStyle style) {
	    outputBox.appendText(hint, style);
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
     * @param c
     * 		群model
     * @param im
     * 		群消息结构
     * @param me
     * 		true表示是我自己
     */
    public void appendMessage(Cluster c, ClusterIM im, boolean me) {
        User f = c.getMember(im.sender);
        String name = (f == null) ? String.valueOf(im.sender) : DefaultFace.escapeFaces(f.displayName);
		outputBox.appendText('(' + name + ")  " + DateTool.format(im.sendTime), me ? myStyle : otherStyle);
		LineStyle style = getLineStyle(im);
		outputBox.appendText(im.message, style);
    }
	
    /**
     * 得到行样式
     * 
     * @param im
     * @return
     */
    private LineStyle getLineStyle(ClusterIM im) {
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

	public void release() {
		dispose();
	}

	public void setKeyboardFocus() {
		inputBox.setFocus();
	}

	public void init() {
		inputBox.setDefaultStyle(main.getDefaultStyle());
		main.getClient().cluster_GetInfo(cluster.clusterId);
	}

	public boolean isActiveContainer() {
		return true;
	}

	public void appendText(String text) {
		inputBox.appendText(text, main.getDefaultStyle());
	}
	
	public IQQListener getQQListener() {
		return qqListener;
	}
}
