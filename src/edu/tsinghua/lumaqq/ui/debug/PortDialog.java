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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import edu.tsinghua.lumaqq.qq.QQ;
import edu.tsinghua.lumaqq.qq.QQClient;
import edu.tsinghua.lumaqq.qq.Util;
import edu.tsinghua.lumaqq.qq.net.IConnection;
import edu.tsinghua.lumaqq.ui.provider.ListContentProvider;

/**
 * 端口选择对话框
 *
 * @author luma
 */
public class PortDialog extends Dialog {
	private QQClient client;
	private ListViewer connList;
	private List<Integer> family;
	
	private int supportedFamily;
	private ListViewer protocolList;
	
	private int selectedFamily;
	private String connectionId;
	
	private static final int CREATE_ID = 9999;
	
	protected PortDialog(Shell parentShell, QQClient client) {
		super(parentShell);
		this.client = client;
		family = new ArrayList<Integer>();
		family.add(QQ.QQ_PROTOCOL_FAMILY_03);
		family.add(QQ.QQ_PROTOCOL_FAMILY_05);
		family.add(QQ.QQ_PROTOCOL_FAMILY_BASIC);
		supportedFamily = 0;
		selectedFamily = 0;
	}
	
	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("Port Selection");
	}
	
	@Override
	protected Control createDialogArea(Composite parent) {
    	Composite control = (Composite)super.createDialogArea(parent);
    	
    	Composite container = new Composite(control, SWT.NONE);
        GridLayout layout = new GridLayout();
        layout.marginHeight = layout.marginWidth = 0;
        container.setLayout(layout);
        container.setLayoutData(new GridData(GridData.FILL_BOTH));
        
        // port list
        Label lblTemp = new Label(container, SWT.LEFT);
        lblTemp.setText("Existing Port List");
        connList = new ListViewer(container, SWT.SINGLE | SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
		connList.getList().setLayoutData(new GridData(GridData.FILL_BOTH));
		ListContentProvider<IConnection> provider = new ListContentProvider<IConnection>(new ArrayList<IConnection>());
		if(client.getConnectionPool() != null)
			provider.setSource(client.getConnectionPool().getConnections());
        connList.setContentProvider(provider);
        connList.setLabelProvider(new LabelProvider() {
        	@Override
        	public String getText(Object element) {
        		return ((IConnection)element).getId();
        	}
        });
        connList.addSelectionChangedListener(new ISelectionChangedListener() {
        	public void selectionChanged(SelectionChangedEvent event) {
        		IStructuredSelection s = (IStructuredSelection)event.getSelection();
        		if(s.isEmpty()) {
        			supportedFamily = 0;
        			connectionId = null;
        		} else {
        			IConnection port = (IConnection)s.getFirstElement();
        			supportedFamily = port.getPolicy().getSupportedFamily();
        			connectionId = port.getId();
        		}
        		protocolList.refresh();
        	}
        });
        
        // supported protocol
        lblTemp = new Label(container, SWT.LEFT);
        lblTemp.setText("Available Protocol Family");
        protocolList = new ListViewer(container, SWT.SINGLE | SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
		protocolList.getList().setLayoutData(new GridData(GridData.FILL_BOTH));
        protocolList.setContentProvider(new ListContentProvider<Integer>(family));
        protocolList.setLabelProvider(new LabelProvider() {
        	@Override
        	public String getText(Object element) {
        		return Util.getProtocolString((Integer)element);
        	}
        });
        protocolList.addFilter(new ViewerFilter() {
        	@Override
        	public boolean select(Viewer viewer, Object parentElement, Object element) {
        		return (supportedFamily & (Integer)element) != 0;
        	}
        });
        protocolList.addSelectionChangedListener(new ISelectionChangedListener() {
        	public void selectionChanged(SelectionChangedEvent event) {
        		IStructuredSelection s = (IStructuredSelection)event.getSelection();
        		Integer i = (Integer)s.getFirstElement();
        		if(i == null)
        			selectedFamily = 0;
        		else
        			selectedFamily = i;
        	}
        });
        
        initializeViewers();
        
        return control;
	}
	
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, CREATE_ID, "Create New Port", false);
		super.createButtonsForButtonBar(parent);
	}
	
	@Override
	protected void buttonPressed(int buttonId) {
		if(buttonId == CREATE_ID) 
			createPressed();
		else
			super.buttonPressed(buttonId);
	}

	private void createPressed() {
		CreatePortDialog dialog = new CreatePortDialog(getShell(), client);
		if(dialog.open() == IDialogConstants.OK_ID) {
			ListContentProvider<IConnection> provider = (ListContentProvider<IConnection>)connList.getContentProvider();
			provider.setSource(client.getConnectionPool().getConnections());
			connList.refresh();
		}
	}

	private void initializeViewers() {
		connList.setInput(this);
		protocolList.setInput(this);
	}

	/**
	 * @return the portName
	 */
	public String getConnectionId() {
		return connectionId;
	}

	/**
	 * @return the selectedFamily
	 */
	public int getSelectedFamily() {
		return selectedFamily;
	}
}
