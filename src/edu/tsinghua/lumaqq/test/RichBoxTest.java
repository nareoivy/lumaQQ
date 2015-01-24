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
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import edu.tsinghua.lumaqq.widgets.rich.RichBox;

/**
 * @author luma
 */
public class RichBoxTest {
    private Shell shell;
    private Display display;
    
    public static void main(String[] args) {
        RichBoxTest t = new RichBoxTest();
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
	    
	    final RichBox st = new RichBox(shell);
	    st.setBackground(display.getSystemColor(SWT.COLOR_WHITE));
	    //StyledText st = new StyledText(shell, SWT.V_SCROLL | SWT.WRAP);
	    GridData gd = new GridData(GridData.FILL_BOTH);
	    gd.horizontalIndent = 100;
	    st.setLayoutData(gd);
	    st.setText("哈，娃\r\n\r阿瓦");
	    st.setFont(new Font(display, new FontData("宋体", 9, SWT.NORMAL)));
	    shell.addMouseListener(new MouseAdapter() {
	        /* (non-Javadoc)
             * @see org.eclipse.swt.events.MouseAdapter#mouseDown(org.eclipse.swt.events.MouseEvent)
             */
            @Override
			public void mouseDown(MouseEvent e) {
                 st.forceFocus();
            }
	    });
	    shell.layout();
	    shell.open();
		while(!shell.isDisposed()) 
			if(!display.readAndDispatch()) 
			    display.sleep();
	}
}
