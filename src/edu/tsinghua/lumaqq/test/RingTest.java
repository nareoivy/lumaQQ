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

import edu.tsinghua.lumaqq.widgets.mac.Ring;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

/**
 * @author luma
 */
public class RingTest {
    private Shell shell;
    private Display display;
    
    public static void main(String[] args) {
        RingTest t = new RingTest();
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
	    
	    new Label(shell, SWT.CENTER).setText("鼠标按下开始旋转，松开停止旋转");
	    
	    // 如果需要更多的样式，可以实现IBorderPainter和ISignPainter接口
	    // 目前只提供了圆形边界和圆形标志实现
	    
	    final Ring ring = new Ring(shell);
	    GridData gd = new GridData();
	    gd.widthHint = 18;
	    gd.heightHint = 18;
	    ring.setLayoutData(gd);
	    
	    shell.addMouseListener(new MouseAdapter() {
	    	@Override
	    	public void mouseDown(MouseEvent e) {
//	    		ring.setClockwise(false);
//	    		ring.setTrackSignCount(2);
//	    		ring.setShowTrack(false);
//	    		ring.setSignCount(6);
//	    		ring.setActiveForeground(display.getSystemColor(SWT.COLOR_BLUE));
	    		
	    		// 如果要改变标志外接圆半径到5，那么相应改变上面的griddata到50x50，不然标志太大了
//	    		ring.setSignRadius(5);
//	    		ring.setForeground(display.getSystemColor(SWT.COLOR_DARK_GREEN));
	    		
	    		ring.rotate("哈哈");
	    	}
	    	@Override
	    	public void mouseUp(MouseEvent e) {
	    		//ring.stop();
	    	}
	    });
	    shell.layout();
	    shell.open();
		while(!shell.isDisposed()) 
			if(!display.readAndDispatch()) 
			    display.sleep();
	}
}
