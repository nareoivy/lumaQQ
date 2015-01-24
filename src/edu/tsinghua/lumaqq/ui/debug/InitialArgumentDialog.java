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
package edu.tsinghua.lumaqq.ui.debug;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import edu.tsinghua.lumaqq.qq.Util;
import edu.tsinghua.lumaqq.qq.debug.InitialArgument;

/**
 * <pre>
 * 调试参数对话框
 * </pre>
 * 
 * @author luma
 */
class InitialArgumentDialog extends Dialog {
    private InitialArgument arg;
    
    // 界面控件
    private Text textQQ, textPassword, textLoginReply;
    private Font font;
    
    /**
     * @param parent
     */
    public InitialArgumentDialog(Shell parent) {
        super(parent);
		font = new Font(parent.getDisplay(), "Courier New", 9, SWT.NORMAL);
    }
    
    @Override
    protected void configureShell(Shell newShell) {
    	super.configureShell(newShell);
		newShell.setText("Input Debug Argument");
		newShell.addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				font.dispose();
			}
		});
    }
    
    @Override
    protected Point getInitialSize() {
    	return new Point(400, 350);
    }
    
    @Override
    protected Control createDialogArea(Composite parent) {
    	Composite control = (Composite)super.createDialogArea(parent);
    	
    	Composite container = new Composite(control, SWT.NONE);
        GridLayout layout = new GridLayout();
        layout.numColumns = 2;
        layout.marginHeight = layout.marginWidth = 0;
        container.setLayout(layout);
        container.setLayoutData(new GridData(GridData.FILL_BOTH));
        
        // QQ号
        Label lblQQ = new Label(container, SWT.RIGHT);
        lblQQ.setText("QQ Number:");
        lblQQ.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL));
        textQQ = new Text(container, SWT.SINGLE | SWT.BORDER);
        textQQ.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        textQQ.addKeyListener(new KeyAdapter() {
            @Override
			public void keyPressed(KeyEvent e) {
                if(!Character.isDigit(e.character) && !Character.isISOControl(e.character))
                    e.doit = false;
            }
        });
        
        // 密码
        Label lblPassword = new Label(container, SWT.RIGHT);
        lblPassword.setText("Password:");
        lblPassword.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL));
        textPassword = new Text(container, SWT.SINGLE | SWT.BORDER | SWT.PASSWORD);
        textPassword.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        
        // 登录回复包
        Label lblLoginReply = new Label(container, SWT.RIGHT);
        lblLoginReply.setText("Login Reply:");
        lblLoginReply.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.VERTICAL_ALIGN_BEGINNING));
        textLoginReply = new Text(container, SWT.MULTI | SWT.WRAP | SWT.BORDER);
        textLoginReply.setLayoutData(new GridData(GridData.FILL_BOTH));
        textLoginReply.setFont(font);
        
        // composite
        Composite comp = new Composite(container, SWT.NONE);
        layout = new GridLayout();
        layout.numColumns = 2;
        layout.horizontalSpacing = 20;
        layout.makeColumnsEqualWidth = true;
        comp.setLayout(layout);
        GridData gd = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
        gd.horizontalSpan = 2;
        comp.setLayoutData(gd);
        
    	return control;
    }
    
    @Override
    protected void okPressed() {
        arg = createDebugArgument();
        if(arg == null)
            MessageDialog.openWarning(getShell(), "Warning", "Some errors exist in your input, try again!");
        else
        	super.okPressed();
    }

    /**
     * 创建调试参数bean
     * 
     * @return
     * 			如果失败，返回null
     */
    protected InitialArgument createDebugArgument() {
        if(textQQ.getText().trim().equals(""))
            return null;
        
        byte[] reply = Util.convertHexStringToByte(textLoginReply.getText());
        if(reply == null)
            return null;
        
        InitialArgument da = new InitialArgument();
        da.setQQ(Integer.parseInt(textQQ.getText().trim()));
        da.setPassword(textPassword.getText());
        da.setLoginReply(reply);
        return da;
    }

	/**
	 * @return the arg
	 */
	public InitialArgument getArg() {
		return arg;
	}
}
