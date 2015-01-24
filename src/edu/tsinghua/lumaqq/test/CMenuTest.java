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

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import edu.tsinghua.lumaqq.resource.Resources;
import edu.tsinghua.lumaqq.widgets.menu.CMenu;
import edu.tsinghua.lumaqq.widgets.menu.CMenuItem;

/**
 * @author luma
 */
public class CMenuTest {	
    private Shell shell;
    private Display display;
    
    public static void main(String[] args) {   	
        CMenuTest t = new CMenuTest();
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
	    
	    final CMenu menu = new CMenu();
	    menu.setImageSize(32);
	    
	    CMenuItem item = new CMenuItem(menu, SWT.PUSH);
	    item.setText("MenuItem 1");
	    item.setImage(Resources.getInstance().getImage(Resources.icoMessage));
	    item.setEnabled(false);
	    
	    CMenuItem cas = new CMenuItem(menu, SWT.CASCADE);
	    cas.setText("MenuItem 2");
	    cas.setImage(Resources.getInstance().getImage(Resources.icoMessage));
	    
	    item = new CMenuItem(menu, SWT.SEPARATOR);
	    
	    item = new CMenuItem(menu, SWT.PUSH);
	    item.setText("哈哈，第三个");
	    item.setImage(Resources.getInstance().getImage(Resources.icoSysOpt));
	    
	    item = new CMenuItem(menu, SWT.CHECK);
	    item.setSelected(true);
	    item.setText("系统参数");
	    item.setImage(Resources.getInstance().getImage(Resources.icoSysOpt));
	    
	    item = new CMenuItem(menu, SWT.RADIO);
	    item.setSelected(true);
	    item.setText("如来神掌");
	    item.setImage(Resources.getInstance().getImage(Resources.icoSysOpt));
	    
	    CMenu sub = new CMenu(cas);
	    item = new CMenuItem(sub, SWT.PUSH);
	    item.setText("Sub 1");
	    item.setImage(Resources.getInstance().getImage(Resources.icoSysOpt));
	    item = new CMenuItem(sub, SWT.PUSH);
	    item.setText("Sub 2");
	    item.setImage(Resources.getInstance().getImage(Resources.icoClusterCard));
	    
	    shell.addMouseListener(new MouseListener() {
	    	public void mouseDoubleClick(MouseEvent arg0) {
	    		// TODO Auto-generated method stub
	    		
	    	}
	    	public void mouseDown(MouseEvent arg0) {
	    		// TODO Auto-generated method stub
	    		
	    	}
	    	public void mouseUp(MouseEvent e) {
	    		menu.setLocation(shell.toDisplay(e.x, e.y));
	    		menu.setVisible(true);
	    	}
	    });

	    shell.layout();
	    shell.open();
		while(!shell.isDisposed()) 
			if(!display.readAndDispatch()) 
			    display.sleep();
	}
}
