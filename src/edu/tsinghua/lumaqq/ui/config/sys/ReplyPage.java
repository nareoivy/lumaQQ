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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import edu.tsinghua.lumaqq.ecore.reply.Replies;
import edu.tsinghua.lumaqq.resource.Colors;
import edu.tsinghua.lumaqq.resource.Resources;
import edu.tsinghua.lumaqq.ui.MainShell;
import edu.tsinghua.lumaqq.ui.config.AbstractPage;
import edu.tsinghua.lumaqq.ui.helper.UITool;
import edu.tsinghua.lumaqq.ui.listener.AroundBorderPaintListener;
import edu.tsinghua.lumaqq.ui.provider.ListContentProvider;

/**
 * 回复设置页
 * 
 * @author luma
 */
public class ReplyPage extends AbstractPage {    
    /**
     * 为table viewer提供label
     * 
     * @author luma
     */
    private class ReplyContentProvider extends LabelProvider implements
            ITableLabelProvider {
        /* (non-Javadoc)
         * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnImage(java.lang.Object, int)
         */
        public Image getColumnImage(Object element, int columnIndex) {
            return null;
        }

        /* (non-Javadoc)
         * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnText(java.lang.Object, int)
         */
        public String getColumnText(Object element, int columnIndex) {
            return (String)element;
        }
    }
    
    private static final int AUTO_REPLY = 1;
    private static final int QUICK_REPLY = 2;
    
    private List<String> autoReplies;
    private List<String> quickReplies;
    private ILabelProvider labelProvider;
    private PaintListener paintListener;
    private TableItem currentAutoReply, currentQuickReply;
    
    private Button btnDeleteAuto, btnDeleteQuick;
    private Text textEdit;
    private Button btnAddAuto, btnAddQuick;
    private TableViewer autoViewer, quickViewer;
    private MainShell main;
    
    /**
     * @param parent
     */
    public ReplyPage(Composite parent, MainShell main) {
        super(parent);
        this.main = main;
    }
    
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.shells.AbstractPage#initialVariable()
     */
	@Override
    protected void initialVariable() {
        autoReplies = new ArrayList<String>();
        quickReplies = new ArrayList<String>();
        labelProvider = new ReplyContentProvider();
        paintListener = new AroundBorderPaintListener(new Class[] { Table.class, Text.class }, Colors.PAGE_CONTROL_BORDER);
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.ui.config.AbstractPage#createContent(org.eclipse.swt.widgets.Composite)
     */
	@Override
    protected Control createContent(Composite parent) {
        Composite container = new Composite(parent, SWT.NONE);
        container.setBackground(Colors.PAGE_BACKGROUND);
        GridLayout layout = new GridLayout(2, true);
        layout.marginHeight = layout.marginWidth = 15;
        layout.verticalSpacing = 15;
        container.setLayout(layout);
        
        // 设置使用缺省背景色
        UITool.setDefaultBackground(null);
        
        // 自动回复组
        layout = new GridLayout();
        layout.marginHeight = layout.marginWidth = 8;
        GridData gd = new GridData(GridData.FILL_BOTH);
        Group autoGroup = UITool.createGroup(container, sys_opt_group_auto_reply, gd, layout);
        autoGroup.addPaintListener(paintListener);
        
        // 删除自动回复按钮
        btnDeleteAuto = UITool.createButton(autoGroup, button_delete);
        btnDeleteAuto.setEnabled(false);
        btnDeleteAuto.addSelectionListener(new SelectionAdapter() {
            @Override
			public void widgetSelected(SelectionEvent e) {
				btnDeleteAuto.setEnabled(false);
				
                Table t = autoViewer.getTable();
				TableItem ti = t.getItem(t.getSelectionIndex());
				boolean checked = ti.getChecked();
				if(checked)
				    ti.setChecked(false);
				autoReplies.remove(t.getSelectionIndex());
				autoViewer.refresh();
				
				if(checked) {
					if(autoReplies.size() > 0) {
					    currentAutoReply = t.getItem(0);
						currentAutoReply.setChecked(true);						
					} else
					    currentAutoReply = null;
				}				
				
                makeDirty(AUTO_REPLY);
            }
        });
        
        // 自动回复的table viewer
        autoViewer = new TableViewer(autoGroup, SWT.SINGLE | SWT.CHECK | SWT.V_SCROLL | SWT.FULL_SELECTION);
        autoViewer.setContentProvider(new ListContentProvider<String>(autoReplies));
        autoViewer.setLabelProvider(labelProvider);
        new TableColumn(autoViewer.getTable(), SWT.LEFT);
        autoViewer.getTable().setHeaderVisible(false);
        autoViewer.getTable().setLinesVisible(false);
        autoViewer.getTable().setBackground(Colors.PAGE_BACKGROUND);
        gd = new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.FILL_VERTICAL);
        gd.widthHint = 150;
        autoViewer.getTable().setLayoutData(gd);
        autoViewer.getTable().addControlListener(new ControlAdapter() {
            @Override
			public void controlResized(ControlEvent e) {
                Table t = (Table)e.getSource();
                t.getColumn(0).setWidth(t.getClientArea().width);
            }
        });
        autoViewer.getTable().addSelectionListener(new SelectionAdapter() {
            @Override
			public void widgetSelected(SelectionEvent e) {
                Table t = (Table)e.getSource();
                
                if(e.detail == SWT.CHECK) {
					TableItem ti = (TableItem)e.item;
					if(ti.getChecked()) {				
					    if(currentAutoReply != null)
					        currentAutoReply.setChecked(false);
						currentAutoReply = ti;
						
						makeDirty(AUTO_REPLY);
					} else
						ti.setChecked(true);
                } else {
					if(t.getSelectionIndex() != -1) {
						btnDeleteAuto.setEnabled(true);
						textEdit.setText(t.getSelection()[0].getText());
					} else {
						btnDeleteAuto.setEnabled(false);
					}                    
                }                
            }
        });
        autoViewer.setInput(this);
        
        // 快捷回复组
        layout = new GridLayout();
        layout.marginHeight = layout.marginWidth = 8;
        gd = new GridData(GridData.FILL_BOTH);
        Group quickGroup = UITool.createGroup(container, sys_opt_group_quick_reply, gd, layout);
        quickGroup.addPaintListener(paintListener);
        
        // 删除快捷回复按钮
        btnDeleteQuick = UITool.createButton(quickGroup, button_delete);
        btnDeleteQuick.setEnabled(false);
        btnDeleteQuick.addSelectionListener(new SelectionAdapter() {
            @Override
			public void widgetSelected(SelectionEvent e) {
				btnDeleteQuick.setEnabled(false);
				
                Table t = quickViewer.getTable();
				TableItem ti = t.getItem(t.getSelectionIndex());
				boolean checked = ti.getChecked();
				if(checked)
				    ti.setChecked(false);
				quickReplies.remove(t.getSelectionIndex());
				quickViewer.refresh();
				
				if(checked) {
					if(quickReplies.size() > 0) {
					    currentQuickReply = t.getItem(0);					    
						currentQuickReply.setChecked(true);
					} else
					    currentQuickReply = null;
				}
				
                makeDirty(QUICK_REPLY);
            }
        });

        // 快捷回复的table viewer
        quickViewer = new TableViewer(quickGroup, SWT.SINGLE | SWT.CHECK | SWT.V_SCROLL | SWT.FULL_SELECTION);
        quickViewer.setContentProvider(new ListContentProvider<String>(quickReplies));
        quickViewer.setLabelProvider(labelProvider);
        new TableColumn(quickViewer.getTable(), SWT.LEFT);
        quickViewer.getTable().setHeaderVisible(false);
        quickViewer.getTable().setLinesVisible(false);
        quickViewer.getTable().setBackground(Colors.PAGE_BACKGROUND);
        gd = new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.FILL_VERTICAL);
        gd.widthHint = 150;
        quickViewer.getTable().setLayoutData(gd);
        quickViewer.getTable().addControlListener(new ControlAdapter() {
            @Override
			public void controlResized(ControlEvent e) {
                Table t = (Table)e.getSource();
                t.getColumn(0).setWidth(t.getClientArea().width);
            }
        });
        quickViewer.getTable().addSelectionListener(new SelectionAdapter() {
            @Override
			public void widgetSelected(SelectionEvent e) {
                Table t = (Table)e.getSource();
                
                if(e.detail == SWT.CHECK) {
					TableItem ti = (TableItem)e.item;
					if(ti.getChecked()) {		
					    if(currentQuickReply != null)
					        currentQuickReply.setChecked(false);
						currentQuickReply = ti;
						
						makeDirty(QUICK_REPLY);
					} else
						ti.setChecked(true);
                } else {
					if(t.getSelectionIndex() != -1) {
						btnDeleteQuick.setEnabled(true);
						textEdit.setText(t.getSelection()[0].getText());
					} else {
					    btnDeleteQuick.setEnabled(false);
					}                    
                }
                
            }
        });
        quickViewer.setInput(this);
        
        // 编辑组
        gd = new GridData(GridData.FILL_HORIZONTAL);
        gd.horizontalSpan = 2;
        layout = new GridLayout(2, false);
        Group editGroup = UITool.createGroup(container, sys_opt_group_edit_reply, gd, layout);
        editGroup.addPaintListener(paintListener);
        
        // 编辑框
        gd = new GridData(GridData.FILL_BOTH);
        gd.verticalSpan = 2;
        textEdit = UITool.createMultiText(editGroup, gd);
        textEdit.addModifyListener(new ModifyListener() {
            public void modifyText(ModifyEvent e) {
                boolean empty = textEdit.getText().trim().equals("");
                btnAddAuto.setEnabled(!empty);
                btnAddQuick.setEnabled(!empty);
            }
        });
        // 添加为自动回复按钮
        btnAddAuto = UITool.createButton(editGroup, sys_opt_button_add_auto_reply, new GridData(GridData.VERTICAL_ALIGN_BEGINNING | GridData.HORIZONTAL_ALIGN_FILL));
        btnAddAuto.setEnabled(false);
        btnAddAuto.addSelectionListener(new SelectionAdapter() {
            @Override
			public void widgetSelected(SelectionEvent e) {
                autoReplies.add(textEdit.getText().trim());
                autoViewer.refresh();
                makeDirty(AUTO_REPLY);
            }
        });
        // 添加为快捷回复按钮
        btnAddQuick = UITool.createButton(editGroup, sys_opt_button_add_quick_reply, new GridData(GridData.VERTICAL_ALIGN_BEGINNING | GridData.HORIZONTAL_ALIGN_FILL));
        btnAddQuick.setEnabled(false);
        btnAddQuick.addSelectionListener(new SelectionAdapter() {
            @Override
			public void widgetSelected(SelectionEvent e) {
                quickReplies.add(textEdit.getText().trim());
                quickViewer.refresh();
                makeDirty(QUICK_REPLY);
            }
        });
        
        return container;
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.shells.AbstractPage#getImage()
     */
	@Override
    protected Image getImage() {
        return Resources.getInstance().getImage(Resources.icoSysOpt24);
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.shells.AbstractPage#getTitle()
     */
	@Override
    protected String getTitle(int page) {
        return sys_opt_button_reply;
    }
    
    /**
     * @return
     * 		选中的Auto Reply索引
     */
    private int getCheckedAutoReplyIndex() {
        TableItem[] item = autoViewer.getTable().getItems();
        for(int i = 0; i < item.length; i++) {
            if(item[i].getChecked())
                return i;
        }
        return 0;
    }
    
    /**
     * @return
     * 		选中的Quick Reply索引
     */
    private int getCheckedQuickReplyIndex() {
        TableItem[] item = quickViewer.getTable().getItems();
        for(int i = 0; i < item.length; i++) {
            if(item[i].getChecked())
                return i;
        }
        return 0;
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.shells.AbstractPage#saveDirtyProperty(int)
     */
	@Override
	@SuppressWarnings("unchecked")
    protected void saveDirtyProperty(int propertyId) {
		Replies replies = main.getConfigHelper().getReplies();
        switch(propertyId) {
            case AUTO_REPLY:
                replies.getAutoReply().clear();
                replies.getAutoReply().addAll(autoReplies);
                replies.setCurrentAutoReply(getCheckedAutoReplyIndex());
                main.getConfigHelper().saveReplies();
        		main.getMenuHelper().renewAawyMenu();
                break;
            case QUICK_REPLY:
                replies.getQuickReply().clear();
                replies.getQuickReply().addAll(quickReplies);
                replies.setCurrentQuickReply(getCheckedQuickReplyIndex());
                main.getConfigHelper().saveReplies();
                main.getMenuHelper().renewAawyMenu();
                break;
        }
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.shells.AbstractPage#initializeValues()
     */
	@Override
	@SuppressWarnings("unchecked")
    protected void initializeValues() {
		Replies replies = main.getConfigHelper().getReplies();
        autoReplies.addAll(replies.getAutoReply());
        quickReplies.addAll(replies.getQuickReply());
        autoViewer.refresh();
        quickViewer.refresh();
        if(replies.getAutoReply().size() > 0) {
	        currentAutoReply = autoViewer.getTable().getItem(replies.getCurrentAutoReply()); 
	        currentAutoReply.setChecked(true);            
        }
        if(replies.getQuickReply().size() > 0) {
	        currentQuickReply = quickViewer.getTable().getItem(replies.getCurrentQuickReply()); 
	        currentQuickReply.setChecked(true);            
        }
    }
}
