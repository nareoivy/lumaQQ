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

import edu.tsinghua.lumaqq.ui.dialogs.SnapshotDialog;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * @author luma
 */
public class SnapshotTest {
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
        SnapshotTest t = new SnapshotTest();
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
	    
	    shell.addMouseListener(new MouseAdapter() {
	    	@Override
	    	public void mouseUp(MouseEvent e) {
	    		SnapshotDialog dialog = new SnapshotDialog(shell);
	    		dialog.open();
	    	}
	    });

	    shell.layout();
	    shell.open();
		while(!shell.isDisposed()) 
			if(!display.readAndDispatch()) 
			    display.sleep();
	}
}
