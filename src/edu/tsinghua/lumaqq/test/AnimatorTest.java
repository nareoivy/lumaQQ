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
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import edu.tsinghua.lumaqq.widgets.rich2.Animator;

/**
 * @author luma
 */
public class AnimatorTest {	
    private Shell shell;
    private Display display;
    private boolean stop;
    
    public static void main(String[] args) {
        AnimatorTest t = new AnimatorTest();
        t.open();
    }
    
	/**
	 * 打开对话框
	 */
	public void open()	{		
		// event loop
		stop = false;
	    shell = new Shell(new Display());
	    display = shell.getDisplay();
	    shell.setLayout(new GridLayout(10, false));
	    shell.setSize(600, 300);
	    shell.setBackground(display.getSystemColor(SWT.COLOR_WHITE));	    

	    final Animator[] ani = new Animator[40];
	    for(int i = 0; i < 40; i++) {
	    	ani[i] = new Animator(shell);
	    	GridData gd = new GridData();
	    	gd.widthHint = gd.heightHint = 32;
	    	ani[i].setLayoutData(gd);
	    	
	    	ImageLoader loader = new ImageLoader();
	    	loader.load("src/edu/tsinghua/lumaqq/resource/face/" + i + ".gif");
	    	ani[i].setLoader(loader);
	    	ani[i].setBackground(shell.getBackground());	    	
	    }

	    
	    shell.addMouseListener(new MouseAdapter() {
	    	@Override
	    	public void mouseUp(MouseEvent e) {
	    		if(stop) {
	    			for(Animator a : ani)
	    				a.resumeAnimation();
	    		} else {
	    			for(Animator a : ani)
	    				a.stopAnimation();
	    		}
	    		stop = !stop;	
	    	}
	    });
	    
	    shell.layout();
	    shell.open();
		while(!shell.isDisposed()) 
			if(!display.readAndDispatch()) 
			    display.sleep();
	}
}
