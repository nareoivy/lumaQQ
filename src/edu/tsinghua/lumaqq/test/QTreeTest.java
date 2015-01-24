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
import edu.tsinghua.lumaqq.widgets.qstyle.IQTreeListener;
import edu.tsinghua.lumaqq.widgets.qstyle.ItemLayout;
import edu.tsinghua.lumaqq.widgets.qstyle.QItem;
import edu.tsinghua.lumaqq.widgets.qstyle.QTree;
import edu.tsinghua.lumaqq.widgets.qstyle.QTreeEvent;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * @author luma
 */
public class QTreeTest {
    private Shell shell;
    private Display display;
    
    public static void main(String[] args) {
        QTreeTest t = new QTreeTest();
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
	    
	    Button btn = new Button(shell, SWT.PUSH);
	    btn.setText("Hello World");
	    final QTree tree = new QTree(shell);
		tree.setBackground(display.getSystemColor(SWT.COLOR_WHITE));
		tree.setLevelImageSize(1, 32);		
		tree.setLevelLayout(1, ItemLayout.VERTICAL);
		final QItem root1 = new QItem(tree, SWT.NONE);
		root1.setText("Hello");
		root1.setImage(Resources.getInstance().getImage(Resources.icoCluster), false);
		
		QItem sub = new QItem(root1, SWT.NONE);		
		sub.setText("卖女孩的小货舱");
		sub.setImage(Resources.getInstance().getHead(93), false);
		
		sub = new QItem(root1, SWT.NONE);		
		sub.setText("Mr. J 2nd");
		sub.setImage(Resources.getInstance().getHead(90), false);
		
		sub = new QItem(root1, SWT.NONE);		
		sub.setText("Steve");
		sub.setImage(Resources.getInstance().getHead(87), false);
		
		QItem root2 = new QItem(tree, SWT.NONE);
		root2.setText("LumaQQ");
		root2.setImage(Resources.getInstance().getImage(Resources.icoCluster), false);
		
		for(int i = 0; i < 10; i++) {
			sub = new QItem(root1, SWT.NONE);		
			sub.setText(String.valueOf(i));
			sub.setImage(Resources.getInstance().getHead(84), false);
		}		
		root1.setExpanded(true, false);
		
	    //StyledText st = new StyledText(shell, SWT.V_SCROLL | SWT.WRAP);
	    GridData gd = new GridData(GridData.FILL_BOTH);
	    gd.horizontalIndent = 100;
	    tree.setLayoutData(gd);
	    tree.addQTreeListener(new IQTreeListener() {
	    	public void treeCollapsed(QTreeEvent e) {
	    		// TODO Auto-generated method stub
	    		e.item.setImage(Resources.getInstance().getImage(Resources.icoCluster));
	    		
	    	}
	    	public void treeExpanded(QTreeEvent e) {
	    		e.item.setImage(Resources.getInstance().getImage(Resources.icoAbout));
	    	}
			public void itemTextChanged(QTreeEvent e) {
				// TODO Auto-generated method stub
				
			}
	    });
		shell.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
//				//root1.setExpanded(!root1.isExpanded());
//				root1.getItem(0).setDecorationImage(IconHolder.getInstance().getImage(IconHolder.icoNA));
//				root1.getItem(1).addAttachment(1, IconHolder.getInstance().getImage(IconHolder.icoBindMobileUser));
//				root1.getItem(1).addAttachment(2, IconHolder.getInstance().getImage(IconHolder.icoCam));
//				root1.getItem(1).setForeground(display.getSystemColor(SWT.COLOR_RED));
//				tree.startAnimation(root1.getItem(1), Animation.TEXT_BLINK);
			}
			@Override
			public void mouseUp(MouseEvent e) {
//				tree.stopAnimation(root1.getItem(1));
//				root1.setExpanded(!root1.isExpanded());
//				root1.getItem(0).setDecorationImage(IconHolder.getInstance().getImage(IconHolder.icoNA));
//				root1.getItem(1).addAttachment(1, IconHolder.getInstance().getImage(IconHolder.icoBindMobileUser));
//				root1.getItem(1).addAttachment(2, IconHolder.getInstance().getImage(IconHolder.icoCam));
//				root1.getItem(1).setForeground(display.getSystemColor(SWT.COLOR_RED));
//				tree.startAnimation(root1.getItem(1), AnimationType.ICON_BOUNCE);
//				tree.startAnimation(root1.getItem(2), AnimationType.ICON_BLINK);
//				tree.startAnimation(root1, AnimationType.ICON_BLINK);
//				tree.revealItem(root1.getItem(10), true);
//				tree.setLevelIndent(0);
//				tree.redraw();
				tree.editItemText(root1.getItem(1));
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
