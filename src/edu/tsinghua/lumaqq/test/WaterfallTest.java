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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import edu.tsinghua.lumaqq.record.IKeyConstants;
import edu.tsinghua.lumaqq.record.RecordEntry;
import edu.tsinghua.lumaqq.widgets.record.NormalClusterIMLabelProvider;
import edu.tsinghua.lumaqq.widgets.record.RecordEntryListContentProvider;
import edu.tsinghua.lumaqq.widgets.record.Waterfall;

/**
 * @author luma
 */
public class WaterfallTest {	
    private Shell shell;
    private Display display;
    
    public static void main(String[] args) {  	
        WaterfallTest t = new WaterfallTest();
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
	    shell.setBackground(display.getSystemColor(SWT.COLOR_BLUE));	
	    
	    List<RecordEntry> list = new ArrayList<RecordEntry>();
	    for(int i = 0; i < 6; i++) {
	    	RecordEntry key = new RecordEntry();
	    	key.owner = 77777;
	    	key.sender = 8422190;
	    	key.senderParent = 0;
	    	key.receiver = 555555;
	    	key.time = System.currentTimeMillis();
	    	key.type = IKeyConstants.NORMAL;
	    	key.message = "你好\r\n哈哈哈，你好" + i;
	    	list.add(key);	    	
	    }
	    
	    Waterfall wf = new Waterfall(shell);
	    wf.setContentProvider(new RecordEntryListContentProvider(list));
	    wf.setLabelProvider(new NormalClusterIMLabelProvider());
	    wf.setInput(this);
	    wf.setLayoutData(new GridData(GridData.FILL_BOTH));

	    shell.layout();
	    shell.open();
		while(!shell.isDisposed()) 
			if(!display.readAndDispatch()) 
			    display.sleep();
	}
}
