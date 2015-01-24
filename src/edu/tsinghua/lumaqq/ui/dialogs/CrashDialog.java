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
package edu.tsinghua.lumaqq.ui.dialogs;

import static edu.tsinghua.lumaqq.resource.Messages.*;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import edu.tsinghua.lumaqq.resource.Resources;
import edu.tsinghua.lumaqq.ui.MainShell;
import edu.tsinghua.lumaqq.ui.helper.UITool;

/**
 * 崩溃报告对话框
 * 
 * @author luma
 */
public class CrashDialog extends Dialog {
	private MainShell main;
	private String errorMessage;
	
	private static final int COPY = 0;
	private static final int CLOSE = 1;
	private static final int RESTART = 2;
	
	public CrashDialog(MainShell main, String errorMessage) {
		super(main.getShell());
		this.main = main;
		this.errorMessage = errorMessage;
	}
	
	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setImage(Resources.getInstance().getImage(Resources.icoLumaQQ));
		newShell.setText(crash_title);
	}
	
	@Override
	protected Point getInitialSize() {
		return new Point(500, 400);
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite control = (Composite)super.createDialogArea(parent);
		
		UITool.setDefaultBackground(control.getBackground());
		
		CLabel lblHint = new CLabel(control, SWT.LEFT);
		lblHint.setText(crash_hint);
		lblHint.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		Text textReport = new Text(control, SWT.MULTI | SWT.WRAP | SWT.BORDER | SWT.V_SCROLL | SWT.READ_ONLY);
		textReport.setLayoutData(new GridData(GridData.FILL_BOTH));
		textReport.setText(errorMessage);
		
		return control;
	}
	
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, COPY, crash_copy, false);
		createButton(parent, CLOSE, crash_close, false);
		createButton(parent, RESTART, crash_restart, true);
	}
	
	@Override
	protected void buttonPressed(int buttonId) {
		switch(buttonId) {
			case COPY:
				Clipboard clip = new Clipboard(getShell().getDisplay());
				clip.setContents(new Object[] { errorMessage }, new Transfer[] { TextTransfer.getInstance() });
				clip.dispose();
				break;
			case CLOSE:
				main.close();
				break;
			case RESTART:
				close();
				main.relaunchLoginDialog();
				break;
		}
	}
}
