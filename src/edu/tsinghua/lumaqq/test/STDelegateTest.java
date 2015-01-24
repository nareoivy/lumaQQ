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

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;

import edu.tsinghua.lumaqq.widgets.rich2.StyledTextDelegate;

public class STDelegateTest {
    private Shell shell;
    private Display display;

    public static void main(String[] args) {
    	STDelegateTest t = new STDelegateTest();
        t.open();
    }
    
	/**
	 * 打开对话框
	 */
	public void open()	{		
		// event loop
	    shell = new Shell(new Display());
	    display = shell.getDisplay();
	    shell.setLayout(new GridLayout(2, false));
	    shell.setSize(400, 300);
	    shell.setBackground(display.getSystemColor(SWT.COLOR_WHITE));
	    
	    final StyledTextDelegate delegate = new StyledTextDelegate();
	    delegate.createStyledText(shell, SWT.V_SCROLL | SWT.WRAP, new GridData(GridData.FILL_BOTH));
	    delegate.getStyledText().setText("This is a test");
	    
	    Composite comp = new Composite(shell, SWT.NONE);
	    comp.setLayout(new GridLayout());
	    comp.setLayoutData(new GridData(GridData.FILL_VERTICAL));
	    
	    Button btn = new Button(comp, SWT.PUSH);
	    btn.setText("Insert Image/Animation");
	    btn.addSelectionListener(new SelectionAdapter() {
	    	@Override
	    	public void widgetSelected(SelectionEvent e) {
	    		FileDialog dialog = new FileDialog(shell, SWT.OPEN);
	    		String filename = dialog.open();
				try {
					ImageLoader loader = new ImageLoader();
					loader.load(filename);
					delegate.insertImage(loader, "\u0014", true);
				} catch(RuntimeException e1) {
					MessageDialog.openError(shell, "Error", "Not an image");
				}
	    	}
	    });
	    
	    btn = new Button(comp, SWT.PUSH);
	    btn.setText("Insert a button");
	    btn.addSelectionListener(new SelectionAdapter() {
	    	@Override
	    	public void widgetSelected(SelectionEvent e) {
	    		Button btn = new Button(delegate.getStyledText(), SWT.PUSH);
	    		btn.setText("Button");
	    		btn.setCursor(shell.getDisplay().getSystemCursor(SWT.CURSOR_ARROW));
	    		btn.setSize(btn.computeSize(SWT.DEFAULT, SWT.DEFAULT));
	    		btn.addSelectionListener(new SelectionAdapter() {
	    			@Override
					public void widgetSelected(SelectionEvent e) {
						MessageDialog.openInformation(shell, "Hi", "Hello world!");
	    			}
	    		});
	    		delegate.insertControl(btn, "\u0014", true);
	    	}
	    });

	    shell.layout();
	    shell.open();
		while(!shell.isDisposed()) 
			if(!display.readAndDispatch()) 
			    display.sleep();
	}

}
