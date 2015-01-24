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

import edu.tsinghua.lumaqq.widgets.qstyle.Bubble;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * @author luma
 */
@SuppressWarnings("unused")
public class BubbleTest {
    private Shell shell;
    private Display display;
    private Image image;
    private Bubble bubble;
    
    public static void main(String[] args) {
        BubbleTest t = new BubbleTest();
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
	    
	    /*
	     * 支持样式:
		 * SWT.LEFT: 从左边出现
		 * SWT.RIGHT: 从右边出现
		 * SWT.TOP: 从上面出现
		 * SWT.BOTTOM: 从下面出现
		 * 
		 * LEFT和RIGHT，只能出现一个; TOP和BOTTOM，只能出现一个
		 * 
		 * 你可以调整bubble的enduringtime和framedelay来调整效果的平滑程度，
		 * 这两个参数的设置必须在open之前进行
	     */
	    final int style = SWT.BOTTOM;
	    bubble = new Bubble(shell, style);
	    
	    Button btn = new Button(shell, SWT.PUSH);
	    btn.setText("Close hint window");
	    btn.addSelectionListener(new SelectionAdapter() {
	    	@Override
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
	    		if(!bubble.isDisposed())
	    			bubble.close();
	    	}
	    });
	    shell.addMouseListener(new MouseListener() {

			public void mouseDoubleClick(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			public void mouseDown(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			public void mouseUp(MouseEvent e) {
				if(bubble.isDisposed())
					bubble = new Bubble(shell, style);				
				bubble.open();
			}
	    	
	    });
	    shell.layout();
	    shell.open();
		while(!shell.isDisposed()) 
			if(!display.readAndDispatch()) 
			    display.sleep();
	}
}
