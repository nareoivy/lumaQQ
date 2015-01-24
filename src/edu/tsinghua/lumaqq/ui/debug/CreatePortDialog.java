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

import java.net.InetSocketAddress;
import java.nio.channels.UnresolvedAddressException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import edu.tsinghua.lumaqq.qq.QQ;
import edu.tsinghua.lumaqq.qq.QQClient;
import edu.tsinghua.lumaqq.qq.Util;
import edu.tsinghua.lumaqq.qq.net.IConnection;
import edu.tsinghua.lumaqq.qq.net.IConnectionPolicy;
import edu.tsinghua.lumaqq.ui.provider.ListContentProvider;

/**
 * 创建新port的对话框
 *
 * @author luma
 */
public class CreatePortDialog extends TitleAreaDialog {
	private Text textPortName;
	private List<Integer> family;
	private QQClient client;
	private ListViewer familyList;
	private Button chkUdp;
	private CCombo comboProxyType;
	private Text textProxyAddress;
	private Text textProxyPort;
	private Text textServerAddress;
	private Text textServerPort;
	private Text textUsername;
	private Text textPassword;

	protected CreatePortDialog(Shell parentShell, QQClient client) {
		super(parentShell);
		this.client = client;
		family = new ArrayList<Integer>();
		family.add(QQ.QQ_PROTOCOL_FAMILY_03);
		family.add(QQ.QQ_PROTOCOL_FAMILY_05);
		family.add(QQ.QQ_PROTOCOL_FAMILY_BASIC);
	}

	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("Create Port");
	}
	
	@Override
	protected Control createDialogArea(Composite parent) {
    	Composite control = (Composite)super.createDialogArea(parent);
    	
    	Composite container = new Composite(control, SWT.NONE);
        GridLayout layout = new GridLayout();
        container.setLayout(layout);
        container.setLayoutData(new GridData(GridData.FILL_BOTH));
        
        // port name
        Label lblTemp = new Label(container, SWT.LEFT);
        lblTemp.setText("Port Name:");
        textPortName = new Text(container, SWT.SINGLE | SWT.BORDER);
		textPortName.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        textPortName.addModifyListener(new ModifyListener() {
        	public void modifyText(ModifyEvent e) {
        		validate();
        	}
        });
        
        // port family
        lblTemp = new Label(container, SWT.LEFT);
        lblTemp.setText("Port Family:");
        familyList = new ListViewer(container);
        familyList.getList().setLayoutData(new GridData(GridData.FILL_BOTH));
		familyList.setContentProvider(new ListContentProvider<Integer>(family));
        familyList.setLabelProvider(new LabelProvider() {
        	@Override
        	public String getText(Object element) {
        		return Util.getProtocolString((Integer)element);
        	}
        });
        familyList.addSelectionChangedListener(new ISelectionChangedListener() {
        	public void selectionChanged(SelectionChangedEvent event) {
        		validate();
        	}
        });
        
        // server address
        lblTemp = new Label(container, SWT.LEFT);
        lblTemp.setText("Server Address");
        chkUdp = new Button(container, SWT.CHECK);
		chkUdp.setText("UDP Server");
		chkUdp.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				validate();
			}
		});
		Composite comp = new Composite(container, SWT.NONE);
		comp.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		layout = new GridLayout(4, false);
		layout.marginHeight = layout.marginWidth = 0;
		comp.setLayout(layout);
		lblTemp = new Label(comp, SWT.LEFT);
		lblTemp.setText("Server Address:");
		textServerAddress = new Text(comp, SWT.SINGLE | SWT.BORDER);
		textServerAddress.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		textServerAddress.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				validate();
			}
		});
		lblTemp = new Label(comp, SWT.LEFT);
		lblTemp.setText("Server Port:");
		textServerPort = new Text(comp, SWT.SINGLE | SWT.BORDER);
		GridData gd = new GridData();
		gd.widthHint = 80;
		textServerPort.setLayoutData(gd);
		textServerPort.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				validate();
			}
		});
		
		// proxy type
		lblTemp = new Label(container, SWT.LEFT);
		lblTemp.setText("Proxy Type:");
		comboProxyType = new CCombo(container, SWT.DROP_DOWN | SWT.BORDER);
		comboProxyType.add("None");
		comboProxyType.add("Http");
		comboProxyType.add("Socks");
		comboProxyType.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				validate();
			}
		});
		comboProxyType.select(0);
		
		// proxy address
        lblTemp = new Label(container, SWT.LEFT);
        lblTemp.setText("Proxy:");
		comp = new Composite(container, SWT.NONE);
		comp.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		layout = new GridLayout(4, false);
		layout.marginHeight = layout.marginWidth = 0;
		comp.setLayout(layout);
		lblTemp = new Label(comp, SWT.LEFT);
		lblTemp.setText("Proxy Address:");
		textProxyAddress = new Text(comp, SWT.SINGLE | SWT.BORDER);
		textProxyAddress.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		textProxyAddress.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				validate();
			}
		});
		lblTemp = new Label(comp, SWT.LEFT);
		lblTemp.setText("Proxy Port:");
		textProxyPort = new Text(comp, SWT.SINGLE | SWT.BORDER);
		gd = new GridData();
		gd.widthHint = 80;
		textProxyPort.setLayoutData(gd);
		textProxyPort.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				validate();
			}
		});
		
		// proxy username and password
		comp = new Composite(container, SWT.NONE);
		comp.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		layout = new GridLayout(4, false);
		layout.marginHeight = layout.marginWidth = 0;
		comp.setLayout(layout);
		lblTemp = new Label(comp, SWT.LEFT);
		lblTemp.setText("Proxy Username:");
		textUsername = new Text(comp, SWT.SINGLE | SWT.BORDER);
		textUsername.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		lblTemp = new Label(comp, SWT.LEFT);
		lblTemp.setText("Proxy Password:");
		textPassword = new Text(comp, SWT.SINGLE | SWT.BORDER | SWT.PASSWORD);
		gd = new GridData();
		gd.widthHint = 80;
		textPassword.setLayoutData(gd);
        
        initializeViewer();
        
        setMessage("Before create a port, some arguments must be decided");
		setTitle("Select port argument");
        
        return control;
	}
	
	@Override
	protected void constrainShellSize() {
		super.constrainShellSize();
		validate();
	}

	private void initializeViewer() {
		familyList.setInput(this);
	}

	protected void validate() {
		boolean fail = true;
		if(textPortName.getText().trim().equals(""))
			setErrorMessage("Connection name can't be empty");
		else if(client.getConnectionPool().hasConnection(textPortName.getText().trim()))
			setErrorMessage("Connection name has already been used by other port");
		else if(familyList.getSelection().isEmpty())
			setErrorMessage("Should select at least one family");
		else if(textServerAddress.getText().trim().equals(""))
			setErrorMessage("Server address can't be empty");
		else if(Util.getInt(textServerPort.getText().trim(), 0) == 0)
			setErrorMessage("The server port format is incorrect");
		else if(chkUdp.getSelection() && comboProxyType.getSelectionIndex() == 1) 
			setErrorMessage("Can't use Http proxy with UDP mode");
		else if(comboProxyType.getSelectionIndex() != 0 && textProxyAddress.getText().trim().equals(""))
			setErrorMessage("Proxy address can't be empty");
		else if(comboProxyType.getSelectionIndex() != 0 && Util.getInt(textProxyPort.getText().trim(), 0) == 0)
			setErrorMessage("The proxy port format is incorrect");
		else {
			setErrorMessage(null);
			fail = false;
		}
		
		getButton(IDialogConstants.OK_ID).setEnabled(!fail);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	protected void okPressed() {
		String portName = textPortName.getText().trim();
		int supportedFamily = 0;
		for(Iterator<Integer> i = ((IStructuredSelection)familyList.getSelection()).iterator(); i.hasNext(); )
			supportedFamily |= i.next();
		
		IConnection conn = null;
		
		IConnectionPolicy policy = client.getConnectionPolicyFactory().createPolicy(client, portName, supportedFamily, (Integer)((IStructuredSelection)familyList.getSelection()).getFirstElement(), null, null, null);
		try {
			if(chkUdp.getSelection()) {
				switch(comboProxyType.getSelectionIndex()) {
					case 0:
						conn = client.getConnectionPool().newUDPConnection(policy, 
								new InetSocketAddress(textServerAddress.getText().trim(), Util.getInt(textServerPort.getText().trim(), 0)), 
								true);
						break;
					case 2:
						policy.setProxy(new InetSocketAddress(textProxyAddress.getText().trim(), Util.getInt(textProxyPort.getText().trim(), 0)));
						policy.setProxyUsername(textUsername.getText().trim());
						policy.setProxyPassword(textPassword.getText());
						conn = client.getConnectionPool().newUDPSocks5Connection(policy, 
								new InetSocketAddress(textServerAddress.getText().trim(), Util.getInt(textServerPort.getText().trim(), 0)),
								true);
						break;
				}
			} else {
				switch(comboProxyType.getSelectionIndex()) {
					case 0:
						conn = client.getConnectionPool().newTCPConnection(policy, 
								new InetSocketAddress(textServerAddress.getText().trim(), Util.getInt(textServerPort.getText().trim(), 0)), 
								true);
						break;
					case 1:
						policy.setProxy(new InetSocketAddress(textProxyAddress.getText().trim(), Util.getInt(textProxyPort.getText().trim(), 0)));
						policy.setProxyUsername(textUsername.getText().trim());
						policy.setProxyPassword(textPassword.getText());
						conn = client.getConnectionPool().newTCPHttpConnection(policy,
								new InetSocketAddress(textServerAddress.getText().trim(), Util.getInt(textServerPort.getText().trim(), 0)),
								true);
						break;
					case 2:
						policy.setProxy(new InetSocketAddress(textProxyAddress.getText().trim(), Util.getInt(textProxyPort.getText().trim(), 0)));
						policy.setProxyUsername(textUsername.getText().trim());
						policy.setProxyPassword(textPassword.getText());
						conn = client.getConnectionPool().newTCPSocks5Connection(policy,
								new InetSocketAddress(textServerAddress.getText().trim(), Util.getInt(textServerPort.getText().trim(), 0)),
								true);
						break;
				}
			}
		} catch(UnresolvedAddressException e) {
			conn = null;
		}
		
		if(conn == null)
			MessageDialog.openError(getShell(), "Failed", "Create connection failed");
		else
			super.okPressed();
	}
}