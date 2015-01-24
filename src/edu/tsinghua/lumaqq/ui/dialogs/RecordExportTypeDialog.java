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
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import edu.tsinghua.lumaqq.ui.provider.ListContentProvider;
import edu.tsinghua.lumaqq.widgets.record.IRecordExporterFactory;

/**
 * 聊天记录导出格式选择对话框
 *
 * @author luma
 */
public class RecordExportTypeDialog extends Dialog {
	private IRecordExporterFactory factory;
	private String fileTypeName;
	private ListViewer typeViewer;
	
	public RecordExportTypeDialog(Shell parent, IRecordExporterFactory factory) {
		super(parent);
		this.factory = factory;
	}

	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText(record_export_title);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	protected Control createDialogArea(Composite parent) {
		Composite control = (Composite)super.createDialogArea(parent);
		
		Label lblHint = new Label(control, SWT.LEFT);
		lblHint.setLayoutData(new GridData());
		lblHint.setText(record_export_filetype);
		
		typeViewer = new ListViewer(control, SWT.BORDER);
		typeViewer.getList().setLayoutData(new GridData(GridData.FILL_BOTH));
		typeViewer.setContentProvider(new ListContentProvider(factory.getExporterNameList()));
		typeViewer.setLabelProvider(new LabelProvider());
		typeViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent e) {
				IStructuredSelection s = (IStructuredSelection)e.getSelection();
				Object obj = s.getFirstElement();
				if(obj != null)
					fileTypeName = (String)obj;
			}
		});
		
		initializeControls();
		
		return control;
	}

	private void initializeControls() {
		typeViewer.setInput(this);
		if(typeViewer.getList().getItemCount() > 0) {
			typeViewer.getList().setSelection(0);
			fileTypeName = typeViewer.getList().getItem(0);
		}
	}

	/**
	 * @return Returns the fileTypeName.
	 */
	public String getFileTypeName() {
		return fileTypeName;
	}
}
