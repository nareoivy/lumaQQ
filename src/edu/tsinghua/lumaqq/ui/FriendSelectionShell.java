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

import java.util.ArrayList;
import java.util.List;

import edu.tsinghua.lumaqq.events.FriendSelectionEvent;
import edu.tsinghua.lumaqq.events.IFriendSelectionListener;
import edu.tsinghua.lumaqq.models.Cluster;
import edu.tsinghua.lumaqq.models.Group;
import edu.tsinghua.lumaqq.models.Model;
import edu.tsinghua.lumaqq.models.User;
import edu.tsinghua.lumaqq.resource.Resources;
import edu.tsinghua.lumaqq.ui.sorter.ModelSorter;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TreeItem;

/**
 * <pre>
 * 把好友显示在树状图中供用户选择的窗口，每当一个好友被选择时，其会
 * 触发一个好友选择事件，事件类是FriendSelectionEvent
 * </pre>
 * 
 * @author luma
 */
public class FriendSelectionShell implements ControlListener {
	private Shell shell;
	private Shell parent;
	private Resources res;
	// 界面控件
	private TreeViewer viewer;
	// 事件监听器
	private List<IFriendSelectionListener> listeners;
	// 是否单选模式
	private boolean single;
	
	private List<Model> roots;
	
	private static final Object[] EMPTY = new Object[0];
	private ILabelProvider lableProvider = new LabelProvider() {
		@Override
		public String getText(Object arg0) {
			Model model = (Model)arg0;
			switch(model.type) {
				case GROUP:
					return ((Group)arg0).name;
				case CLUSTER:
					return ((Cluster)arg0).name;
				case USER:
					User u = (User)arg0;
					return u.displayName + '(' + u.qq + ')';
				default:
					return "";
			}
		}
		
		@Override
		public Image getImage(Object arg0) {
			Model model = (Model)arg0;
			switch(model.type) {
				case GROUP:
					Group g = (Group)arg0;
					switch(g.groupType) {
						default:
							return res.getImage(Resources.icoFolder);
					}
				case CLUSTER:
					return res.getSmallClusterHead(((Cluster)arg0).headId);
				case USER:
					return res.getSmallHead(((User)arg0).headId);
				default:
					return null;
			}
		}
	};
	private ITreeContentProvider contentProvider = new ITreeContentProvider() {		
		public Object[] getChildren(Object arg0) {
			Model model = (Model)arg0;
			switch(model.type) {
				case GROUP:
					Group g = (Group)arg0;
					switch(g.groupType) {
						default:
							return g.users.toArray();
					}
				case CLUSTER:
					return ((Cluster)arg0).members.values().toArray();
				default:
					return EMPTY;
			}
		}

		public Object getParent(Object arg0) {
			Model model = (Model)arg0;
			switch(model.type) {
				case USER:
					User u = (User)model;
					switch(u.group.groupType) {
						case CLUSTER_GROUP:
							return u.cluster;
						default:
							return u.group;
					}
				default:
					return null;
			}
		}

		public boolean hasChildren(Object arg0) {
			Model model = (Model)arg0;
			switch(model.type) {
				case GROUP:
				case CLUSTER:
					return true;
				default:
					return false;
			}
		}

		public Object[] getElements(Object arg0) {
			return roots.toArray();
		}

		public void dispose() {	
		}

		public void inputChanged(Viewer arg0, Object arg1, Object arg2) {
		}	
	};
	
	/**
	 * 构造函数
	 * 
	 * @param parent
	 * 		父窗口
	 * @param single
	 * 		true表示单选模式
	 */
	public FriendSelectionShell(Shell parent, boolean single) {
		this.parent = parent;
		shell = new Shell(parent.getDisplay(), SWT.BORDER | SWT.RESIZE);
		// 添加自己为parent的监听器以能够在parent改变位置的时候也改变位置
		parent.addControlListener(this);
		
		// 初始化变量
		res = Resources.getInstance();
		listeners = new ArrayList<IFriendSelectionListener>();
		this.single = single;
		roots = new ArrayList<Model>();
		
		// 初始化窗口布局
		initLayout();
	}

	/**
	 * 设置窗口的位置，使其附着在父窗口旁
	 */
	private void setLocation() {
		Rectangle bound = parent.getBounds();
		shell.setLocation(bound.x + bound.width, bound.y);
	}
	
	/**
	 * 设置窗口的大小
	 */
	private void setSize() {
		Point parentSize = parent.getSize();
		shell.setSize(200, parentSize.y);
	}
	
	/**
	 * 多选择模式时的选择事件处理方法
	 * 
	 * @param e
	 */
	private void multiSelectedMode(SelectionEvent e) {
		// 产生List对象
		List<Model> models = new ArrayList<Model>();
		// 得到被选择的model
		TreeItem ti = (TreeItem)e.item;
		Model modelObj = (Model)ti.getData();
		// 检查model的类型
		if(modelObj instanceof User) {
			models.add(modelObj);
			FriendSelectionEvent event = new FriendSelectionEvent(models);
			if(ti.getChecked())
				fireFriendSelectedEvent(event);
			else
				fireFriendDeselectedEvent(event);
		} else {
			if(ti.getChecked()) {
				// 把它的孩子都设为check状态
				TreeItem[] children = ti.getItems();
				if(children != null) {
					// 没有check的，我们才添加到list中
					for(int i = 0; i < children.length; i++) {
						if(!children[i].getChecked()) {
							children[i].setChecked(true);
							models.add((Model)children[i].getData());
						}
					}									
					// 触发事件
					FriendSelectionEvent event = new FriendSelectionEvent(models);
					fireFriendSelectedEvent(event);
				}
			} else {
				// 把它的孩子都设为uncheck状态
				TreeItem[] children = ti.getItems();
				if(children != null) {
					// 已经check的，我们才添加到list中
					for(int i = 0; i < children.length; i++) {
						if(children[i].getChecked()) {
							children[i].setChecked(false);
							models.add((Model)children[i].getData());
						}
					}
					// 触发事件
					FriendSelectionEvent event = new FriendSelectionEvent(models);
					fireFriendDeselectedEvent(event);
				}
			}
		}							
	}
	
	/**
	 * 初始化窗口布局
	 */
	private void initLayout() {
		// 好友树
	    shell.setLayout(new FillLayout());
	    viewer = new TreeViewer(shell, SWT.CHECK | SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL);
	    viewer.setContentProvider(contentProvider);
	    viewer.setLabelProvider(lableProvider);
	    viewer.setSorter(new ModelSorter());
		viewer.getTree().addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(e.detail == SWT.CHECK) {
				    if(single)
				        singleSelectedMode(e);
				    else
				        multiSelectedMode(e);
				}
			}
		});
		viewer.setInput(this);
		shell.layout();
	}
	
	/**
	 * 单选模式时的选择事件处理方法
	 * 
     * @param e
     */
    private void singleSelectedMode(SelectionEvent e) {
		// 得到被选择的model
		TreeItem ti = (TreeItem)e.item;
		Model modelObj = (Model)ti.getData();
		// 检查model的类型
		if(modelObj instanceof User) {
			// 取消先前被选择的item
			TreeItem[] items = viewer.getTree().getItems();
			for(int i = 0; i < items.length; i++) {
			    if(items[i] != ti)
			        items[i].setChecked(false);
			    
			    TreeItem[] children = items[i].getItems();
			    for(int j = 0; j < children.length; j++)
			        if(children[j] != ti)
			            children[j].setChecked(false);
			}
			
			// 触发事件
			List<Model> models = new ArrayList<Model>();
			models.add(modelObj);
			FriendSelectionEvent event = new FriendSelectionEvent(models);
			if(ti.getChecked())
				fireFriendSelectedEvent(event);
			else
				fireFriendDeselectedEvent(event);
		} else {
		    ti.setChecked(false);
		}		
    }

    /**
	 * 设置Model，这将会用model中的数据重画树
	 * @param model
	 */
	public void setModel(List<Group> model) {
		roots.addAll(model);
		viewer.refresh();
		viewer.expandAll();
	}
	
	/**
	 * 设置群model，把群里面的成员添加到树中
	 * @param c
	 */
	public void setClusterModel(Cluster c) {
		roots.add(c);
		viewer.refresh();
		viewer.expandAll();
	}
	
	/**
	 * 添加好友选择事件监听器
	 * @param listener
	 */
	public void addFriendSelectionListener(IFriendSelectionListener listener) {
		listeners.add(listener);
	}
	
	/**
	 * 除去好友选择事件监听器
	 * @param listener
	 */
	public void removeFriendSelectionListener(IFriendSelectionListener listener) {
		listeners.remove(listener);
	}
	
	/**
	 * 触发一个好友被选择的事件，事件的source是个FriendModel
	 * @param e
	 */
	private void fireFriendSelectedEvent(FriendSelectionEvent e) {
		for(IFriendSelectionListener listener : listeners)
			listener.friendSelected(e);
	}
	
	/**
	 * 触发一个好友被取消选择事件，source是一个FriendModel
	 * @param e
	 */
	private void fireFriendDeselectedEvent(FriendSelectionEvent e) {
		for(IFriendSelectionListener listener : listeners)
			listener.friendDeselected(e);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.events.ControlListener#controlMoved(org.eclipse.swt.events.ControlEvent)
	 */
	public void controlMoved(ControlEvent e) {
		if(shell != null && !shell.isDisposed())
			setLocation();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.events.ControlListener#controlResized(org.eclipse.swt.events.ControlEvent)
	 */
	public void controlResized(ControlEvent e) {
		if(shell != null && !shell.isDisposed())
			setLocation();
	}
	
	/**
	 * 隐藏或者显示窗口
	 * @param b
	 */
	public void setVisible(boolean b) {
		if(b) {
			setSize();
			setLocation();
		}
		setLocation();
		shell.setVisible(b);
	}
	
	/**
	 * @return true表示当前窗口可见
	 */
	public boolean isVisible() {
		return shell.isVisible();
	}

	/**
	 * 把f代表的对象置为选择状态
	 * 
	 * @param f
	 */
	public void select(Object f) {
	    if(f == null) return;
	    
		for(TreeItem root : viewer.getTree().getItems()) {
			for(TreeItem leaf : root.getItems()) {
				if(f.equals(leaf.getData())) {
					leaf.setChecked(true);
					return;
				}
			}
		}
	}
	
	public void deselect(Object f) {
		if(f == null)
			return;
		
		for(TreeItem root : viewer.getTree().getItems()) {
			for(TreeItem leaf : root.getItems()) {
				if(f.equals(leaf.getData())) {
					leaf.setChecked(false);
					return;
				}
			}
		}
	}
	
	/**
	 * 选择一个根下所有的model
	 * 
	 * @param obj
	 */
	public void selectFromRoot(Object obj) {
	    if(obj == null) return;
	    
		for(TreeItem root : viewer.getTree().getItems()) {
		    if(obj == root.getData()) {
		        root.setChecked(true);
				for(TreeItem leaf : root.getItems())
					leaf.setChecked(true);
				return;
		    }
		}
	}
	
	/**
	 * @return true如果窗口已经disposed
	 */
	public boolean isDisposed() {
		return shell.isDisposed();
	}
}
