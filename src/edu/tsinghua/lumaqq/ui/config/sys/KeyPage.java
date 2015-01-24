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
package edu.tsinghua.lumaqq.ui.config.sys;

import static edu.tsinghua.lumaqq.resource.Messages.*;

import edu.tsinghua.lumaqq.resource.Colors;
import edu.tsinghua.lumaqq.resource.Resources;
import edu.tsinghua.lumaqq.ui.MainShell;
import edu.tsinghua.lumaqq.ui.config.AbstractPage;
import edu.tsinghua.lumaqq.ui.helper.UITool;
import edu.tsinghua.lumaqq.ui.listener.CenterBorderPaintListener;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

/**
 * 热键设置页
 * 
 * @author luma
 */
public class KeyPage extends AbstractPage {
	private MainShell main;
	
	private static final int MESSAGE_KEY = 0;

	private Text textKey;

	public KeyPage(Composite parent, MainShell main) {
		super(parent);
		this.main = main;
	}

	@Override
	protected Control createContent(Composite parent) {
        Composite container = new Composite(parent, SWT.NONE);
        container.setBackground(Colors.PAGE_BACKGROUND);
        GridLayout layout = new GridLayout();
        layout.marginHeight = layout.marginWidth = 15;
        layout.verticalSpacing = 15;
        container.setLayout(layout);
        
        // 设置使用缺省背景色
        UITool.setDefaultBackground(null);
        
        // 提取消息设置组
        layout = new GridLayout(2, false);
        layout.marginHeight = layout.marginWidth = 8;
        layout.horizontalSpacing = 15;
        Group msgGroup = UITool.createGroup(container, sys_opt_group_message_key, layout);
        msgGroup.addPaintListener(new CenterBorderPaintListener(new Class[] { Text.class }, 20, Colors.PAGE_CONTROL_BORDER));
        
        // 提取消息
        UITool.createLabel(msgGroup, sys_opt_key_use_key);
        textKey = UITool.createSingleText(msgGroup, new GridData(GridData.FILL_HORIZONTAL));
        textKey.addKeyListener(new KeyAdapter() {
        	@Override
        	public void keyPressed(KeyEvent e) {
        		int key = 0;
        		if(e.keyCode == 0) 
        			key = e.character | e.stateMask;
        		else
        			key = e.keyCode | e.stateMask;
        		
        		int keyValue = key & SWT.KEY_MASK;
        		int modifierValue = key & SWT.MODIFIER_MASK;
        		char keyChar = (char)keyValue;
        		
        		StringBuilder sb = new StringBuilder();
        		if((modifierValue & SWT.MOD1) != 0)
        			sb.append("<Ctrl>");
        		if((modifierValue & SWT.MOD2) != 0)
        			sb.append("<Shift>");
        		if((modifierValue & SWT.MOD3) != 0)
        			sb.append("<Alt>");
        		
        		if(Character.isLetter(keyChar)) {
        			sb.append(Character.toUpperCase(keyChar));
        		} 
        		textKey.setText(sb.toString());
        		e.doit = false;
        		
        		makeDirty(MESSAGE_KEY);
        	}
        });
		
		// 提示信息
		Label lblTemp = UITool.createLabel(container, sys_opt_key_for_platform);
		lblTemp.setForeground(Colors.RED);
		
		return container;
	}

	@Override
	protected void saveDirtyProperty(int propertyId) {
		switch(propertyId) {
			case MESSAGE_KEY:				
				String old = main.getOptionHelper().getMessageKey();
				main.getOptionHelper().setMessageKey(textKey.getText());
				if(!main.getConfigHelper().initHotkeys()) {
					main.getOptionHelper().setMessageKey(old);
					main.getConfigHelper().initHotkeys();
					MessageDialog.openInformation(main.getShell(),
							"Error",
							"Hotkey binding failed");
				}
				break;
		}
	}

	@Override
	protected void initializeValues() {
		textKey.setText(main.getOptionHelper().getMessageKey());
	}

	@Override
	protected Image getImage() {
        return Resources.getInstance().getImage(Resources.icoSysOpt24);
	}

	@Override
	protected String getTitle(int page) {
		return sys_opt_button_key;
	}
}
