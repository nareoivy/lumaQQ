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

import java.util.List;

import edu.tsinghua.lumaqq.models.Group;
import edu.tsinghua.lumaqq.widgets.qstyle.Slat;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

/**
 * <pre>
 * 组选择窗口，用在添加一个好友时，选择把这个好友放到哪个组
 * </pre>
 * 
 * @author luma
 */
public class SelectGroupDialog extends Dialog {    
    // 显示组名的列表
    private Table table;
    // model
    private List<Group> model;
    // selection
    private int selection;
    
    /**
     * @param parent
     */
    public SelectGroupDialog(Shell parent) {
        super(parent);
        selection = -1;
    }
    
    /**
     * 设置model，缺省选择第一项，即“我的好友”
     * @param model
     */
    public void setModel(List<Group> model) {
        this.model = model;
    }
    
	/**
	 * 打开对话框
	 * @return 选择的组索引
	 */
	public Group open() {
	    // 创建shell
		Shell parent = getParent();
		Shell shell = new Shell(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
		shell.setText(select_group_title);
		shell.setSize(120, 200);

		// 初始化界面布局
		initLayout(shell);
		
		// 初始化组名表内容
		initTable();
		
		// 打开对话框
		shell.layout();
		shell.open();
		Display display = parent.getDisplay();
		// set dialog to center of screen
		Rectangle dialogRect = shell.getBounds();
		Rectangle displayRect = display.getBounds();
		shell.setLocation((displayRect.width-dialogRect.width)/2, (displayRect.height-dialogRect.height)/2);
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) display.sleep();
		}
		// 如果用户直接关闭，返回我的好友组，如果没有找到我的好友组，返回第一个找到的好友组
		if(selection == -1) {
			for(Group g : model) {
				if(g.isDefaultFriend())
					return g;
			}			
			for(Group g : model) {
				if(g.isFriendly())
					return g;
			}
			return null;
		} else
			return model.get(selection);
	}

	/**
	 * 在表中添加组名
	 */
	private void initTable() {
	    if(model == null) 
	    	return;
	    int i = 0;
	    for(Group g : model) {
            TableItem ti = new TableItem(table, SWT.NONE);
            ti.setText(g.name);
            ti.setData(new Integer(i++));
        }
	}
	
    /**
     * 初始化界面布局
     * @param shell
     */
    private void initLayout(final Shell shell) {
        // 设置对话框layout
        GridLayout layout = new GridLayout();
        layout.marginHeight = layout.marginWidth = layout.horizontalSpacing = layout.verticalSpacing = 0;
        shell.setLayout(layout);
        // 提示标签
        Label lblHint = new Label(shell, SWT.CENTER);
        lblHint.setText(select_group_label_hint);
        lblHint.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        // 组名table
        table = new Table(shell, SWT.SINGLE | SWT.FULL_SELECTION | SWT.BORDER);
        table.setLayoutData(new GridData(GridData.FILL_BOTH));
        table.addControlListener(new ControlListener() {
            public void controlResized(ControlEvent e) {
                table.getColumn(0).setWidth(table.getClientArea().width);
            }
            public void controlMoved(ControlEvent e) {
                // 没什么要做的
            }
        });
        table.addSelectionListener(new SelectionAdapter() {
            @Override
			public void widgetSelected(SelectionEvent e) {
                selection = table.getSelectionIndex();
                if(selection != -1)
                    selection = (Integer)table.getItem(selection).getData();
            }
        });
        // 表头
        new TableColumn(table, SWT.CENTER);
        table.setHeaderVisible(false);
        // 确定按钮
        Slat btnOK = new Slat(shell, SWT.NONE);
        btnOK.setText(button_ok);
        btnOK.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        btnOK.addMouseListener(new MouseAdapter() {
            @Override
			public void mouseUp(MouseEvent e) {
                if(selection == -1) 
                    MessageDialog.openWarning(shell, message_box_common_warning_title, message_box_please_select_group);                 
                else
	                shell.close();                    
            }
        });
    }	
}
