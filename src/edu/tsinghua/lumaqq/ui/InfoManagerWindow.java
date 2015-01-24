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

import java.lang.reflect.InvocationTargetException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import edu.tsinghua.lumaqq.models.Cluster;
import edu.tsinghua.lumaqq.models.ClusterType;
import edu.tsinghua.lumaqq.models.Group;
import edu.tsinghua.lumaqq.models.ModelRegistry;
import edu.tsinghua.lumaqq.models.User;
import edu.tsinghua.lumaqq.record.IKeyConstants;
import edu.tsinghua.lumaqq.record.RecordEntry;
import edu.tsinghua.lumaqq.record.RecordManager;
import edu.tsinghua.lumaqq.resource.Colors;
import edu.tsinghua.lumaqq.resource.Resources;
import edu.tsinghua.lumaqq.ui.listener.AroundBorderPaintListener;
import edu.tsinghua.lumaqq.widgets.rich.RichBox;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.jface.window.Window;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.ViewForm;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.events.ShellListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Sash;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

/**
 * 信息管理器
 * 
 * @author luma
 */
public class InfoManagerWindow extends Window implements ShellListener {
	private class TreeContentProvider implements ITreeContentProvider {
		public Object[] getChildren(Object parentElement) {
			if(parentElement instanceof Integer) {
				Integer i = (Integer)parentElement;
				switch(i) {
					case 1:
						return otherGroups.toArray();
					case 2:
						return clusters.toArray();
					default:
						return EMPTY;
				}
			} else if(parentElement instanceof Group) {
				Group g = (Group)parentElement;
				return g.users.toArray();
			} else
				return EMPTY;
		}

		public Object getParent(Object element) {
			return null;
		}

		public boolean hasChildren(Object element) {
			if(element instanceof Integer) {
				Integer i = (Integer)element;
				return i != 3;
			} else if(element instanceof Cluster)
				return false;
			else if(element instanceof Group)
				return true;
			else
				return false;
		}

		public Object[] getElements(Object inputElement) {
			return root;
		}

		public void dispose() {
		}

		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}
	}
	
	private class TreeLabelProvider extends LabelProvider {
		@Override
		public String getText(Object element) {
			if(element instanceof Integer) {
				Integer i = (Integer)element;
				switch(i) {
					case 1:
						return info_groups;
					case 2:
						return info_cluster;
					case 3:
						return info_sys_msg;
					default:
						return "";
				}
			} else if(element instanceof Group) {
				Group g = (Group)element;
				return g.name + '(' + g.users.size() + ')';
			} else if(element instanceof Cluster) {
				Cluster c = (Cluster)element;
				return "" + c.externalId + '(' + c.name + ')'; 
			} else if(element instanceof User) {
				User u = (User)element;
				return "" + u.qq + '(' + u.displayName + ')';
			} else
				return "";
		}
		
		@Override
		public Image getImage(Object element) {
			if(element instanceof Integer) {
				Integer i = (Integer)element;
				switch(i) {
					case 1:
						return res.getSmallHead(main.getMyModel().headId);
					case 2:
						return res.getImage(Resources.icoFolder);
					case 3:
						return res.getImage(Resources.icoSysMsg);
					default:
						return null;
				}
			} else if(element instanceof Group)
				return res.getImage(Resources.icoFolder);
			else if(element instanceof Cluster)
				return res.getSmallClusterHead(((Cluster)element).headId);
			else if(element instanceof User)
				return res.getSmallHead(((User)element).headId);
			else
				return null;
		}
	}
	
	private class TreeSorter extends ViewerSorter {
		@Override
		public int compare(Viewer viewer, Object e1, Object e2) {
			if(e1 instanceof Integer && e2 instanceof Integer) {
				Integer i1 = (Integer)e1;
				Integer i2 = (Integer)e2;
				return i1 - i2;
			} else if(e1 instanceof Cluster && e2 instanceof Cluster) {
				Cluster c1 = (Cluster)e1;
				Cluster c2 = (Cluster)e2;
				return c1.externalId - c2.externalId;
			} else if(e1 instanceof User && e2 instanceof User) {
				User u1 = (User)e1;
				User u2 = (User)e2;
				return u1.qq - u2.qq;
			} else
				return 0;
		}
	}
	
	private class RecordContentProvider implements IStructuredContentProvider {
		public Object[] getElements(Object inputElement) {
			int pageSize = main.getRecordManager().getPageSize();
			return records.subList(pageNum * pageSize, Math.min(pageNum * pageSize + pageSize, records.size())).toArray();
		}

		public void dispose() {
		}

		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}		
	}
	
	private class RecordLabelProvider extends LabelProvider implements ITableLabelProvider {
		public Image getColumnImage(Object element, int columnIndex) {
			RecordEntry entry = (RecordEntry)element;
			if(columnIndex == 0) {
				switch(entry.type) {
					case IKeyConstants.SYSTEM:
						return res.getImage(Resources.icoSysMsg);
					case IKeyConstants.NORMAL:
					case IKeyConstants.CLUSTER:
						User u = ModelRegistry.getUser(entry.sender);
						return (u == null) ? res.getSmallHead(Resources.QQ_2005_FACE_MAX_INDEX) : res.getSmallHead(u.headId);
					default:
						return null;
				}
			} else
				return null;
		}

		public String getColumnText(Object element, int columnIndex) {
			RecordEntry entry = (RecordEntry)element;
			switch(columnIndex) {
				case 0:
					switch(entry.type) {
						case IKeyConstants.SYSTEM:
							return "10000";
						case IKeyConstants.NORMAL:
						case IKeyConstants.CLUSTER:
							User u = ModelRegistry.getUser(entry.sender);
							return (u == null) ? String.valueOf(entry.sender) : u.displayName;
						default:
							return "";
					}				
				case 1:
					date.setTime(entry.time);
					return dateFormat.format(date);
				case 2:
					date.setTime(entry.time);
					return timeFormat.format(date);
				case 3:
					return entry.message;
				default:
					return "";
			}
		}		
	}
	
	private MainShell main;
	private Resources res;
	private boolean active;
	
	private static final int SEARCH_RECORD = 0;
	@SuppressWarnings("unused")
	private static final int SEARCH_FRIEND = 1;
	private static final int SCOPE_ALL = 0;
	
	@SuppressWarnings("unused")
	private int searchMode;
	private int scope;
	private int pageNum;
	private int pageCount;
	private String keyword;
	private ViewForm vfRecord;
	private List<RecordEntry> records;
	private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	private static final DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
	private static final Date date = new Date();
	private TableViewer recordViewer;
	private List<Cluster> clusters;
	private List<Group> otherGroups;
	private List scopes;
	
	private static final Integer[] root = new Integer[] {
		1, 2, 3
	};
	private static final Object[] EMPTY = new Object[0];
	private TreeViewer treeViewer;
	private CCombo comboScope;
	private RichBox box;
	private Label lblPage;
	private ToolItem tiExport;
	private ToolItem tiDelete;
	private Label lblDate;
	private Label lblTime;
	private Label lblSender;
	private Label lblReceiver;
	private ToolItem tiSearch;
	private ToolItem tiFirst;
	private ToolItem tiPrev;
	private ToolItem tiNext;
	private ToolItem tiLast;
	
	public InfoManagerWindow(MainShell main) {
		super(main.getShell());
		this.main = main;
		res = Resources.getInstance();
		initializeVariables();
	}
	
	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText(info_title);
		newShell.setImage(res.getImage(Resources.icoMessageManage));
	}
	
	@Override
	protected Control createContents(Composite parent) {
		Composite control = (Composite)super.createContents(parent);
		control.setLayout(new GridLayout());
		control.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		// 工具条
		final ToolBar tb = new ToolBar(control, SWT.FLAT | SWT.RIGHT);
		tiExport = new ToolItem(tb, SWT.PUSH);
		tiExport.setImage(res.getImage(Resources.icoExport));
		tiExport.setText(button_export);
		tiExport.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				onExport();
			}
		});
		tiDelete = new ToolItem(tb, SWT.PUSH);
		tiDelete.setImage(res.getImage(Resources.icoDelete));
		tiDelete.setText(button_delete);
		tiDelete.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				onDelete();
			}
		});
		// separator
		new ToolItem(tb, SWT.SEPARATOR);
		// 其他控件的容器
		ToolItem tiControls = new ToolItem(tb, SWT.SEPARATOR);
		Composite controlContainer = new Composite(tb, SWT.NONE);
		GridLayout layout = new GridLayout(7, false);
		layout.marginHeight = layout.marginWidth = 1;
		controlContainer.setLayout(layout);		
		tiControls.setControl(controlContainer);		
		controlContainer.addPaintListener(new AroundBorderPaintListener(new Class[] { CCombo.class, Text.class }, Colors.PAGE_CONTROL_BORDER));
		// 查找
		Label lblSearch = new Label(controlContainer, SWT.LEFT);
		lblSearch.setText(info_search);
		final CCombo comboSearch = new CCombo(controlContainer, SWT.READ_ONLY | SWT.FLAT);
		GridData gd = new GridData(GridData.VERTICAL_ALIGN_CENTER);
		gd.widthHint = 100;
		comboSearch.setLayoutData(gd);
		comboSearch.setBackground(Colors.WHITE);
		comboSearch.add(info_record);
		comboSearch.select(0);
		comboSearch.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				searchMode = comboSearch.getSelectionIndex();
			}
		});
		// 范围
		Label lblScope = new Label(controlContainer, SWT.LEFT);
		lblScope.setText(info_scope);
		comboScope = new CCombo(controlContainer, SWT.READ_ONLY | SWT.FLAT);
		gd = new GridData(GridData.VERTICAL_ALIGN_CENTER);
		gd.widthHint = 150;
		comboScope.setLayoutData(gd);
		comboScope.setBackground(Colors.WHITE);
		comboScope.add(info_all);
		comboScope.select(0);
		comboScope.setVisibleItemCount(20);
		comboScope.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(comboScope.getSelectionIndex() == 0)
					scope = 0;
				else {
					Object obj = scopes.get(comboScope.getSelectionIndex() - 1);
					if(obj instanceof User)
						scope = ((User)obj).qq;
					else
						scope = ((Cluster)obj).clusterId;
				}
			}
		});
		// 关键字
		Label lblKeyword = new Label(controlContainer, SWT.LEFT);
		lblKeyword.setText(info_key);
		gd = new GridData();
		gd.horizontalIndent = 15;
		lblKeyword.setLayoutData(gd);
		Text textKeyword = new Text(controlContainer, SWT.SINGLE);
		gd = new GridData();
		gd.widthHint = 150;
		gd.heightHint = 18;
		textKeyword.setLayoutData(gd);
		textKeyword.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				Text t = (Text)e.getSource();
				keyword = t.getText().trim();
				if(keyword.equals(""))
					tiSearch.setEnabled(false);
				else
					tiSearch.setEnabled(true);
			}
		});
		textKeyword.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.character == SWT.CR && !keyword.equals(""))
					onSearch();
			}
		});
		tiSearch = new ToolItem(tb, SWT.PUSH);
		tiSearch.setImage(res.getImage(Resources.icoSearch));
		tiSearch.setText(button_search);
		tiSearch.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				onSearch();
			}
		});
		
		// 下部分的容器
		Composite bottomContainer = new Composite(control, SWT.NONE);
		layout = new GridLayout(3, false);
		layout.marginHeight = layout.marginWidth = 1;
		layout.horizontalSpacing = layout.verticalSpacing = 1;
		bottomContainer.setLayout(layout);
		bottomContainer.setLayoutData(new GridData(GridData.FILL_BOTH));
		bottomContainer.addPaintListener(new AroundBorderPaintListener(new Class[] { ViewForm.class }, Colors.PAGE_CONTROL_BORDER));
		
		// 树型图
		final ViewForm vfTree = new ViewForm(bottomContainer, SWT.FLAT);
		gd = new GridData(GridData.FILL_VERTICAL);
		gd.verticalSpan = 3;
		gd.widthHint = 200;
		vfTree.setLayoutData(gd);
		Composite topLeft = new Composite(vfTree, SWT.NONE);
		topLeft.setLayout(new GridLayout());
		Label lblName = new Label(topLeft, SWT.CENTER);
		lblName.setText(main.getMyModel().nick + '(' + main.getMyModel().qq + ')');
		vfTree.setTopLeft(topLeft);
		treeViewer = new TreeViewer(vfTree, SWT.SINGLE);
		treeViewer.setContentProvider(new TreeContentProvider());
		treeViewer.setLabelProvider(new TreeLabelProvider());
		treeViewer.setSorter(new TreeSorter());
		treeViewer.getTree().setLinesVisible(true);
		vfTree.setContent(treeViewer.getTree());
		treeViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				Object obj = getSelectedObject();
				if(obj != null) {
					onSelection(obj);
				}
			}
		});
		
		// sash
		Sash vSash = new Sash(bottomContainer, SWT.VERTICAL);
		gd = new GridData(GridData.FILL_VERTICAL);
		gd.verticalSpan = 3;
		vSash.setLayoutData(gd);
		vSash.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				GridData gd = (GridData)vfTree.getLayoutData();
				gd.widthHint = e.x;
				vfTree.getParent().layout();
				vfTree.getParent().redraw();
			}
		});
		
		// tab folder
		CTabFolder folder = new CTabFolder(bottomContainer, SWT.TOP | SWT.FLAT | SWT.BORDER);
		folder.setLayoutData(new GridData(GridData.FILL_BOTH));
		// 聊天记录tab
		CTabItem tiRecord = new CTabItem(folder, SWT.NONE);
		tiRecord.setText(info_record);
		tiRecord.setImage(res.getImage(Resources.icoMessage));
		// 聊天记录tab容器
		Composite recordContainer = new Composite(folder, SWT.NONE);
		layout = new GridLayout();
		layout.marginHeight = layout.marginWidth = layout.verticalSpacing = layout.horizontalSpacing = 0;
		recordContainer.setLayout(layout);
		recordViewer = new TableViewer(recordContainer, SWT.FULL_SELECTION | SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL);
		TableColumn tc = new TableColumn(recordViewer.getTable(), SWT.LEFT);
		tc.setText(info_sender);
		tc.setWidth(100);
		tc = new TableColumn(recordViewer.getTable(), SWT.LEFT);
		tc.setText(info_date);
		tc.setWidth(100);
		tc = new TableColumn(recordViewer.getTable(), SWT.LEFT);
		tc.setText(info_time);
		tc.setWidth(100);
		tc = new TableColumn(recordViewer.getTable(), SWT.LEFT);
		tc.setText(info_content);
		tc.setWidth(310);
		recordViewer.getTable().setHeaderVisible(true);
		recordViewer.getTable().setLinesVisible(true);
		recordViewer.getTable().setLayoutData(new GridData(GridData.FILL_BOTH));
		recordViewer.setContentProvider(new RecordContentProvider());
		recordViewer.setLabelProvider(new RecordLabelProvider());
		recordViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				onRecordSelection(event);
			}
		});
		recordViewer.addDoubleClickListener(new IDoubleClickListener() {
			public void doubleClick(DoubleClickEvent event) {
				onRecordDoubleClick(event);
			}
		});
		// 记录导航按钮条
		Composite navContainer = new Composite(recordContainer, SWT.NONE);
		layout = new GridLayout(2, false);
		layout.marginHeight = layout.marginWidth = 0;
		navContainer.setLayout(layout);
		navContainer.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		lblPage = new Label(navContainer, SWT.LEFT);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalIndent = 5;
		lblPage.setLayoutData(gd);
		// 导航工具条
		ToolBar tbNav = new ToolBar(navContainer, SWT.FLAT | SWT.RIGHT);
		tiFirst = new ToolItem(tbNav, SWT.PUSH);
		tiFirst.setImage(res.getImage(Resources.icoFirst));
		tiFirst.setText(button_first);
		tiFirst.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				pageNum = 0;
				navigateTo(pageNum);
			}
		});
		tiPrev = new ToolItem(tbNav, SWT.PUSH);
		tiPrev.setImage(res.getImage(Resources.icoPrevious));
		tiPrev.setText(button_previous);
		tiPrev.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				navigateTo(--pageNum);
			}
		});
		tiNext = new ToolItem(tbNav, SWT.PUSH);
		tiNext.setImage(res.getImage(Resources.icoNext));
		tiNext.setText(button_next);
		tiNext.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				navigateTo(++pageNum);
			}
		});
		tiLast = new ToolItem(tbNav, SWT.PUSH);
		tiLast.setImage(res.getImage(Resources.icoLast));
		tiLast.setText(button_last);
		tiLast.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				pageNum = pageCount - 1;
				navigateTo(pageNum);
			}
		});
		tiRecord.setControl(recordContainer);
		
		// horizontal sash
		Sash hSash = new Sash(bottomContainer, SWT.HORIZONTAL);
		hSash.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		hSash.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				GridData gd = (GridData)vfRecord.getLayoutData();
				gd.heightHint = vfRecord.getParent().getClientArea().height - e.y;
				vfRecord.getParent().layout();
				vfRecord.getParent().redraw();
			}
		});
		
		vfRecord = new ViewForm(bottomContainer, SWT.FLAT);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.heightHint = 100;
		vfRecord.setLayoutData(gd);
		topLeft = new Composite(vfRecord, SWT.NONE);
		layout = new GridLayout(4, true);
		topLeft.setLayout(layout);
		lblDate = new Label(topLeft, SWT.LEFT);
		lblDate.setLayoutData(new GridData(GridData.FILL_HORIZONTAL | GridData.VERTICAL_ALIGN_CENTER));
		lblDate.setText(info_date + ":");
		lblTime = new Label(topLeft, SWT.LEFT);
		lblTime.setLayoutData(new GridData(GridData.FILL_HORIZONTAL | GridData.VERTICAL_ALIGN_CENTER));
		lblTime.setText(info_time + ":");
		lblSender = new Label(topLeft, SWT.LEFT);
		lblSender.setLayoutData(new GridData(GridData.FILL_HORIZONTAL | GridData.VERTICAL_ALIGN_CENTER));
		lblSender.setText(info_sender + ":");
		lblReceiver = new Label(topLeft, SWT.LEFT);
		lblReceiver.setLayoutData(new GridData(GridData.FILL_HORIZONTAL | GridData.VERTICAL_ALIGN_CENTER));
		lblReceiver.setText(info_receiver + ":");
		vfRecord.setTopLeft(topLeft);
		box = new RichBox(vfRecord);
		box.setReadonly(true);
		box.setBackground(Colors.WHITE);
		vfRecord.setContent(box);
		
		controlContainer.setSize(controlContainer.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		tiControls.setWidth(controlContainer.getSize().x);
		folder.setSelection(0);		
		initializeControls();
		initializeViewers();
		return control;		
	}

	/**
	 * 处理记录双击事件
	 * 
	 * @param event
	 */
	protected void onRecordDoubleClick(DoubleClickEvent event) {
		IStructuredSelection s = (IStructuredSelection)event.getSelection();
		RecordEntry entry = (RecordEntry)s.getFirstElement();
		if(entry == null)
			return;
		main.getShellLauncher().openReceiveSystemMessageShell(entry);
	}
	
	/**
	 * 点击export按钮时调用此方法
	 */
	protected void onExport() {
		boolean onTree = treeViewer.getTree().isFocusControl();
		if(onTree) {
			Object obj = getSelectedObject();
			if(obj instanceof User) {
				User f = (User)obj;
				main.getExportHelper().exportMessage(f);
			} else if(obj instanceof Cluster) {
				Cluster c = (Cluster)obj;
				main.getExportHelper().exportMessage(c);
			} else if(obj instanceof Integer) {
				Integer i = (Integer)obj;
				if(i == 3)
					main.getExportHelper().exportSystemMessage();
			}
		} else {
			List<RecordEntry> entries = new ArrayList<RecordEntry>();
			int[] indices = recordViewer.getTable().getSelectionIndices();
			for(int i = indices.length - 1; i >= 0; i--) {
				TableItem ti = recordViewer.getTable().getItem(indices[i]);
				RecordEntry entry = (RecordEntry)ti.getData();
				entries.add(entry);
			}
			Object obj = getSelectedObject();
			if(obj instanceof User) {
				User f = (User)obj;
				main.getExportHelper().exportMessage(f, entries);
			} else if(obj instanceof Cluster) {
				Cluster c = (Cluster)obj;
				main.getExportHelper().exportMessage(c, entries);
			} else if(obj instanceof Integer) {
				Integer i = (Integer)obj;
				if(i == 3)
					main.getExportHelper().exportSystemMessage(entries);
			}
		}
	}

	protected void onDelete() {
		boolean onTree = treeViewer.getTree().isFocusControl();
		if(MessageDialog.openQuestion(getShell(), message_box_common_question_title, message_box_confirm_delete_message)) {
			if(onTree) {
				RecordManager rm = main.getRecordManager();
				Object obj = getSelectedObject();
				int owner = 0;
				if(obj instanceof User)
					owner = ((User)obj).qq;
				else if(obj instanceof Cluster) 
					owner = ((Cluster)obj).clusterId;
				else if(obj instanceof Integer) {
					Integer i = (Integer)obj;
					if(i == 3)
						owner = 10000;
					else						
						return;
				}
				
				rm.delete(owner, IKeyConstants.ALL, IKeyConstants.SUB_ALL);
				records.clear();
				pageCount = 0;
				pageNum = 0;
				recordViewer.refresh();
				resetNavigator();
				refreshRichBox(null);
			} else {
				// 删除
				RecordManager rm = main.getRecordManager();
				int base = pageNum * rm.getPageSize();
				int[] indices = recordViewer.getTable().getSelectionIndices();
				for(int i = indices.length - 1; i >= 0; i--) {
					TableItem ti = recordViewer.getTable().getItem(indices[i]);
					RecordEntry entry = (RecordEntry)ti.getData();
					rm.delete(entry);
					records.remove(base + indices[i]);
				}
				// 重新计算页数
				if(records.isEmpty())
					pageCount = 0;
				else
					pageCount = (records.size() + rm.getPageSize() - 1) / rm.getPageSize();
				if(pageNum >= pageCount)
					pageNum = Math.max(0, pageCount - 1);
				if(pageCount > 0)
					lblPage.setText(NLS.bind(info_page, String.valueOf(pageNum + 1), String.valueOf(pageCount)));
				else
					lblPage.setText("");
				// 刷新，同步
				recordViewer.refresh();
				resetNavigator();
				rm.sync();
			}
		}
	}

	protected void navigateTo(int page) {
		recordViewer.refresh();
		lblPage.setText(NLS.bind(info_page, String.valueOf(page + 1), String.valueOf(pageCount)));
		resetNavigator();
	}

	protected void onSearch() {
		records.clear();
		if(scope == 0) {
			// 搜索
			try {
				ProgressMonitorDialog dialog = new ProgressMonitorDialog(getShell());
				dialog.run(true, true, new IRunnableWithProgress() {
					public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
						RecordManager rm = main.getRecordManager();
						monitor.beginTask("Searching...", scopes.size());
						
						for(Object obj : scopes) {
							int scope;
							if(obj instanceof User)
								scope = ((User)obj).qq;
							else
								scope = ((Cluster)obj).clusterId;
							records.addAll(rm.search(scope, IKeyConstants.ALL, IKeyConstants.SUB_ALL, keyword, false, 0));
							monitor.worked(1);
							
							if(monitor.isCanceled())
								break;
						}
						
						monitor.done();
					}
				});
			} catch(InvocationTargetException e) {
			} catch(InterruptedException e) {
			}
		} else {
			records.clear();
			records.addAll(searchOne(scope));		
		}
		RecordManager rm = main.getRecordManager();
		if(records.isEmpty())
			pageCount = 0;
		else
			pageCount = (records.size() + rm.getPageSize() - 1) / rm.getPageSize();
		pageNum = 0;
		if(pageCount > 0)
			lblPage.setText(NLS.bind(info_page, String.valueOf(pageNum + 1), String.valueOf(pageCount)));
		else
			lblPage.setText("");
		recordViewer.refresh();
		refreshRichBox(null);
		resetNavigator();
	}
	
	protected List<RecordEntry> searchOne(int scope) {
		RecordManager rm = main.getRecordManager();
		boolean isCluster = ModelRegistry.getCluster(scope) != null;
		int type = isCluster ? IKeyConstants.CLUSTER : IKeyConstants.NORMAL;
		return rm.search(scope, type, IKeyConstants.SUB_ALL, keyword, false, 0);
	}

	protected void onRecordSelection(SelectionChangedEvent event) {
		IStructuredSelection s = (IStructuredSelection)event.getSelection();
		if(s.isEmpty()) {
			tiExport.setEnabled(false);
			tiDelete.setEnabled(false);
			refreshRichBox(null);
		} else {
			tiExport.setEnabled(true);
			tiDelete.setEnabled(true);
			
			RecordEntry first = (RecordEntry)s.getFirstElement();
			refreshRichBox(first);
		}
	}

	private void refreshRichBox(RecordEntry first) {
		if(first == null) {
			lblDate.setText(info_date + ':');
			lblTime.setText(info_time + ':');
			lblSender.setText(info_sender + ':');
			lblReceiver.setText(info_receiver + ':');
			box.clear();
		} else {
			box.setText(first.message);
			date.setTime(first.time);
			lblDate.setText(info_date + ": " + dateFormat.format(date));
			lblTime.setText(info_time + ": " + timeFormat.format(date));
			
			User sender = ModelRegistry.getUser(first.sender);
			if(sender == null)
				lblSender.setText(info_sender + ": " + first.sender);
			else
				lblSender.setText(info_sender + ": " + sender.displayName);
			
			if(first.type == IKeyConstants.NORMAL) {
				User receiver = ModelRegistry.getUser(first.receiver);
				if(receiver == null)
					lblReceiver.setText(info_receiver + ": " + first.receiver);
				else
					lblReceiver.setText(info_receiver + ": " + receiver.displayName);				
			} else
				lblReceiver.setText(info_receiver + ':');			
		}
	}

	protected void onSelection(Object obj) {
		if(obj instanceof Integer) {
			Integer i = (Integer)obj;
			if(i == 3) {
				/*
				 * 选择了系统消息
				 */
				reloadRecord(10000, IKeyConstants.SYSTEM, IKeyConstants.SUB_ALL);
			} else 
				resetAll();
		} else if(obj instanceof Group) {
			resetAll();
		} else if(obj instanceof User) {
			// 设置搜索范围
			User u = (User)obj;
			scope = u.qq;
			int index = scopes.indexOf(u);
			if(index != -1)
				comboScope.select(index + 1);
			
			// 刷新记录表
			reloadRecord(u.qq, IKeyConstants.NORMAL, IKeyConstants.SUB_ALL);
		} else if(obj instanceof Cluster) {
			Cluster c = (Cluster)obj;
			scope = c.clusterId;
			pageNum = 0;
			pageCount = 0;
			int index = scopes.indexOf(c);
			if(index != -1)
				comboScope.select(index + 1);
			
			// 刷新记录
			reloadRecord(c.clusterId, IKeyConstants.CLUSTER, IKeyConstants.SUB_ALL);
		}
	}

	private void reloadRecord(int owner, int type, int subType) {
		RecordManager rm = main.getRecordManager();
		records.clear();
		records.addAll(rm.getRecord(owner, type, subType));
		if(records.isEmpty())
			pageCount = 0;
		else
			pageCount = (records.size() + rm.getPageSize() - 1) / rm.getPageSize();
		pageNum = Math.max(0, pageCount - 1);
		if(pageCount > 0)
			lblPage.setText(NLS.bind(info_page, String.valueOf(pageNum + 1), String.valueOf(pageCount)));
		else 
			lblPage.setText("");
		recordViewer.refresh();
		tiExport.setEnabled(true);
		tiDelete.setEnabled(true);
		refreshRichBox(null);
		resetNavigator();
	}

	private void resetAll() {
		scope = 0;
		pageNum = 0;
		pageCount = 0;
		comboScope.select(0);
		lblPage.setText("");
		tiExport.setEnabled(false);
		tiDelete.setEnabled(false);
		records.clear();
		recordViewer.refresh();
		resetNavigator();
		refreshRichBox(null);
	}

	private void resetNavigator() {
		if(pageCount <= 1) {
			tiFirst.setEnabled(false);
			tiPrev.setEnabled(false);
			tiNext.setEnabled(false);
			tiLast.setEnabled(false);
		} else if(pageNum >= pageCount - 1) {
			tiFirst.setEnabled(true);
			tiPrev.setEnabled(true);
			tiNext.setEnabled(false);
			tiLast.setEnabled(false);
		} else if(pageNum <= 0) {
			tiFirst.setEnabled(false);
			tiPrev.setEnabled(false);
			tiNext.setEnabled(true);
			tiLast.setEnabled(true);
		} else {
			tiFirst.setEnabled(true);
			tiPrev.setEnabled(true);
			tiNext.setEnabled(true);
			tiLast.setEnabled(true);
		}
	}

	protected Object getSelectedObject() {
		IStructuredSelection s = (IStructuredSelection)treeViewer.getSelection();
		return s.getFirstElement();
	}

	private void initializeViewers() {
		recordViewer.setInput(this);
		treeViewer.setInput(this);
	}

	@SuppressWarnings("unchecked")
	private void initializeControls() {
		tiExport.setEnabled(false);
		tiDelete.setEnabled(false);
		tiSearch.setEnabled(false);
		resetNavigator();
		List<User> users = ModelRegistry.getSortedUserList();
		for(User u : users) 
			comboScope.add("" + u.qq + '(' + u.displayName + ')');
		scopes.addAll(users);
		for(Cluster c : clusters) 
			comboScope.add("" + c.externalId + '(' + c.name + ')');
		scopes.addAll(clusters);
	}

	private void initializeVariables() {
		active = false;
		searchMode = SEARCH_RECORD;
		scope = SCOPE_ALL;
		pageNum = 0;
		pageCount = 0;
		keyword = "";
		scopes = new ArrayList();
		records = new ArrayList<RecordEntry>();
		clusters = new ArrayList<Cluster>(main.getBlindHelper().getClusterGroup().clusters);
		otherGroups = main.getBlindHelper().getUserGroupList();
		for(Iterator<Cluster> i = clusters.iterator(); i.hasNext(); ) {
			if(i.next().clusterType != ClusterType.NORMAL) {
				i.remove();
			}
		}
	}
	
	@Override
	protected ShellListener getShellListener() {
		return this;
	}
	
	@Override
	protected Shell getParentShell() {
		return null;
	}
	
	@Override
	protected Point getInitialSize() {
		return new Point(800, 600);
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
     * 设置窗口激活
     */
    public void setActive() {
        getShell().setActive();
    }
    
    public boolean isActive() {
    	return active;
    }

	public void shellActivated(ShellEvent e) {
		active = true;
	}

	public void shellClosed(ShellEvent e) {
		main.getShellRegistry().deregisterInfoManagerWindow();
	}

	public void shellDeactivated(ShellEvent e) {
		active = false;
	}

	public void shellDeiconified(ShellEvent e) {
	}

	public void shellIconified(ShellEvent e) {
	}
}
