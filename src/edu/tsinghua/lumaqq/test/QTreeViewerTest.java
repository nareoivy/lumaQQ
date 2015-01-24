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
package edu.tsinghua.lumaqq.test;

import edu.tsinghua.lumaqq.resource.Resources;
import edu.tsinghua.lumaqq.widgets.qstyle.IQTreeContentProvider;
import edu.tsinghua.lumaqq.widgets.qstyle.IQTreeLabelProvider;
import edu.tsinghua.lumaqq.widgets.qstyle.ItemLayout;
import edu.tsinghua.lumaqq.widgets.qstyle.QItem;
import edu.tsinghua.lumaqq.widgets.qstyle.QTree;
import edu.tsinghua.lumaqq.widgets.qstyle.QTreeViewer;
import edu.tsinghua.lumaqq.widgets.qstyle.Slat;

import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSource;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.DragSourceListener;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.DropTargetListener;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * @author luma
 */
public class QTreeViewerTest {
	private class Model {
		public int faceId;
		public String text;
		public Model(int f, String t) {
			faceId = f;
			text = t;
		}
	};
	
    Model[] roots = new Model[] {
    		new Model(42, "Hello"),
    		new Model(45, "心软俱乐部"),
    		new Model(48, "哇哇")
    };
	
    private Shell shell;
    private Display display;
    
    public static void main(String[] args) {
        QTreeViewerTest t = new QTreeViewerTest();
        t.open();
    }
    
	/**
	 * 打开对话框
	 */
	public void open()	{		
		// event loop
	    shell = new Shell(new Display());
	    display = shell.getDisplay();
	    shell.setLayout(new GridLayout());
	    shell.setSize(400, 300);
	    shell.setBackground(display.getSystemColor(SWT.COLOR_WHITE));	    

	    final Slat slat = new Slat(shell, SWT.FLAT | SWT.CENTER, "Hello");
	    slat.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
	    
	    final QTreeViewer<Model> viewer = new QTreeViewer<Model>(shell);
	    QTree tree = viewer.getQTree();
	    viewer.setContentProvider(new IQTreeContentProvider<Model>() {
			public Model[] getElements(Object inputElement) {
				return roots;
			}

			public Model[] getChildren(Model parent) {
				if(parent == roots[0])
					return new Model[] { roots[1], roots[roots.length - 1]};
				return new Model[0];
			}

			public Model getParent(Model child) {
				return null;
			}

			public boolean hasChildren(Model parent) {
				return false;
			}
	    	
	    });
	    viewer.setLabelProvider(new IQTreeLabelProvider<Model>() {
			public String getText(Model element) {
				return element.text;
			}

			public Image getImage(Model element) {
				return Resources.getInstance().getHead(element.faceId);
			}

			public Image getDecoration(Model element) {
				return Resources.getInstance().getImage(Resources.icoAwayDecoration);
			}

			public Image getAttachment(Model element, int index) {
				// TODO Auto-generated method stub
				return null;
			}

			public Color getForeground(Model element) {
				// TODO Auto-generated method stub
				return null;
			}

			public boolean isExpaned(Model element) {
				// TODO Auto-generated method stub
				return false;
			}

			public void setExpanded(Model element, boolean exp) {
				// TODO Auto-generated method stub
				
			}

			public Image getPrefix(Model element) {
				return Resources.getInstance().getImage(Resources.icoExpanded9);
			}
	    	
	    });
	    viewer.setInput(this);
		tree.setBackground(display.getSystemColor(SWT.COLOR_WHITE));
		tree.setLevelImageSize(1, 32);		
		tree.setLevelLayout(1, ItemLayout.VERTICAL);
	    
	    GridData gd = new GridData(GridData.FILL_BOTH);
	    gd.horizontalIndent = 100;
	    tree.setLayoutData(gd);
	    
	    viewer.addDragSupport(DND.DROP_MOVE, new Transfer[] { TextTransfer.getInstance() }, new DragSourceListener() {

			public void dragStart(DragSourceEvent event) {
				QTree tree = (QTree)((DragSource)event.getSource()).getControl();
				QItem item = tree.getItemUnderMouse();
				if(item == null || item.getParentItem() == null)
					event.doit = false;
				else {
					tree.saveExpandStatus();
					tree.collapseAll();
				}
			}

			public void dragSetData(DragSourceEvent event) {
				QTree tree = (QTree)((DragSource)event.getSource()).getControl();
				QItem item = tree.getItemUnderMouse();
				event.data = item.getText();
			}

			public void dragFinished(DragSourceEvent event) {
				QTree tree = (QTree)((DragSource)event.getSource()).getControl();
				tree.restoreExpandStatus();
			}
	    	
	    });
	    viewer.addDropSupport(DND.DROP_MOVE, new Transfer[] { TextTransfer.getInstance() }, new DropTargetListener() {

			public void dragEnter(DropTargetEvent event) {
				// TODO Auto-generated method stub
				
			}

			public void dragLeave(DropTargetEvent event) {
				// TODO Auto-generated method stub
				
			}

			public void dragOperationChanged(DropTargetEvent event) {
				// TODO Auto-generated method stub
				
			}

			public void dragOver(DropTargetEvent event) {
			}

			public void drop(DropTargetEvent event) {
				QTree tree = (QTree)((DropTarget)event.getSource()).getControl();
				Point loc = tree.toControl(event.x, event.y);
				QItem item = tree.getItem(loc.x, loc.y);				
				System.out.println(event.data);
				if(item != null)
					System.out.println(item.getText());				
			}

			public void dropAccept(DropTargetEvent event) {
				// TODO Auto-generated method stub
				
			}
	    	
	    });
	    
		shell.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
//				roots = new Model[200];
//				for(int i = 0; i < roots.length; i++) {
//					roots[i] = new Model(i % 255, String.valueOf(i));
//				}
//			    viewer.refresh();
//				if(e.button == 3)
//					slat.editText();
				slat.setShowText(e.button == 1);
			}
			@Override
			public void mouseUp(MouseEvent e) {
			}
			@Override
			public void mouseDoubleClick(MouseEvent e) {
			}
		});

	    shell.layout();
	    shell.open();
		while(!shell.isDisposed()) 
			if(!display.readAndDispatch()) 
			    display.sleep();
	}
}
