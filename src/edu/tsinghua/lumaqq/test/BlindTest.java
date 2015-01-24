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
import edu.tsinghua.lumaqq.widgets.qstyle.Blind;
import edu.tsinghua.lumaqq.widgets.qstyle.ISlatLabelProvider;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * @author luma
 */
public class BlindTest {
    private Shell shell;
    private Display display;
    
    public static void main(String[] args) {
        BlindTest t = new BlindTest();
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
	    
	    final Blind blind = new Blind(shell, SWT.BORDER);
	    GridData gd = new GridData(GridData.FILL_BOTH);
	    gd.horizontalIndent = 200;
	    blind.setLayoutData(gd);
	    blind.setLabelProvider(new ISlatLabelProvider() {
	    	public String getText(Control slatControl) {
	    		return "Hello" + slatControl.toString();
	    	}
	    });
	    
	    CLabel lblTemp = new CLabel(blind, SWT.CENTER);
	    lblTemp.setBackground(display.getSystemColor(SWT.COLOR_WHITE));
	    lblTemp.setText("111");
	    blind.addSlat(lblTemp);
	    
	    Button chk = new Button(blind, SWT.CHECK);
	    chk.setText("Use Proxy");
	    blind.addSlat(chk);
	    lblTemp = new CLabel(blind, SWT.CENTER);
	    lblTemp.setBackground(display.getSystemColor(SWT.COLOR_WHITE));
	    lblTemp.setText("222");
	    blind.addSlat(lblTemp);
	    
	    lblTemp = new CLabel(blind, SWT.CENTER);
	    lblTemp.setBackground(display.getSystemColor(SWT.COLOR_WHITE));
	    lblTemp.setText("333");
	    blind.addSlat(lblTemp);
	    
	    blind.setCurrentSlat(1);
	    
	    shell.addMouseListener(new MouseListener() {

			public void mouseDoubleClick(MouseEvent e) {
			}

			public void mouseDown(MouseEvent e) {
			}

			public void mouseUp(MouseEvent e) {
//				blind.removeSlat(blind.getCurrentSlatIndex());
				
//			    Button chk = new Button(blind, SWT.RADIO);
//			    chk.setBackground(display.getSystemColor(SWT.COLOR_CYAN));
//			    chk.setText("Fuck You");
//			    blind.addSlat(1, chk);
				
//				if(e.button == 1)
//					blind.hideSlat(blind.getCurrentSlatIndex());
//				else {
//					blind.showSlat(1);
//					blind.showSlat(3);
//				}
				
				if(e.button == 1)
					blind.startBlink(1, Resources.getInstance().getHead(45));
				else
					blind.stopBlink(1);
			}
	    	
	    });
	    shell.layout();
	    shell.open();
		while(!shell.isDisposed()) 
			if(!display.readAndDispatch()) 
			    display.sleep();
	}
}
