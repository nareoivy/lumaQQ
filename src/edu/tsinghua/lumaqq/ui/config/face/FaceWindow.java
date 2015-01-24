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
package edu.tsinghua.lumaqq.ui.config.face;

import static edu.tsinghua.lumaqq.resource.Messages.*;

import java.io.File;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import edu.tsinghua.lumaqq.LumaQQ;
import edu.tsinghua.lumaqq.ecore.face.FaceConstant;
import edu.tsinghua.lumaqq.ecore.face.FaceGroup;
import edu.tsinghua.lumaqq.resource.Resources;
import edu.tsinghua.lumaqq.ui.MainShell;
import edu.tsinghua.lumaqq.ui.config.AbstractConfigurationWindow;
import edu.tsinghua.lumaqq.ui.helper.FaceRegistry;
import edu.tsinghua.lumaqq.ui.jobs.DialogJobExecutor;
import edu.tsinghua.lumaqq.ui.jobs.IExecutor;
import edu.tsinghua.lumaqq.ui.jobs.ImportCFCJob;
import edu.tsinghua.lumaqq.ui.jobs.ImportEIPJob;

/**
 * 自定义表情管理窗口
 * 
 * @author luma
 */
public class FaceWindow extends AbstractConfigurationWindow {    
    private FaceRegistry util;
    private MainShell main;
    private FaceManagePage facePage;
    
    /**
     * @param parent
     */
    public FaceWindow(MainShell main) {
        super(main.getShell());
        this.main = main;
        setPageListWidth(90);
    }
    
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.ui.config.AbstractConfigurationWindow#initializeVariables()
     */
	@Override
    protected void initializeVariables() {
        super.initializeVariables();
        util = FaceRegistry.getInstance();
    }
    
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.ui.config.AbstractConfigurationWindow#showOKButton()
     */
	@Override
    protected boolean showOKButton() {
        return false;
    }
    
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.ui.config.AbstractConfigurationWindow#pageChanged()
     */
	@Override
    protected void pageChanged() {
        int page = getCurrentPageId();
        facePage.setCurrentPage(page);
        facePage.refreshViewer(util.getFaceGroup(page));
    }
    
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.ui.config.AbstractConfigurationWindow#pageRemoved(int)
     */
	@Override
    protected void pageRemoved(int index) {
        if(index < facePage.getCurrentPage())
            facePage.setCurrentPage(facePage.getCurrentPage() - 1);
        else if(index == facePage.getCurrentPage())
            pageChanged();
    }
    
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.ui.config.AbstractConfigurationWindow#onShellClose()
     */
	@Override
    protected void onShellClose() {
        main.getShellRegistry().deregisterFaceWindow();
        util.save();
    }
    
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.ui.config.AbstractConfigurationWindow#getInitialSize()
     */
	@Override
    protected Point getInitialSize() {
        return new Point(525, 391);
    }
    
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.ui.config.AbstractConfigurationWindow#initialPages()
     */
	@Override
    protected void initialPages() {
        if(facePage == null)
            facePage = new FaceManagePage(getPageContainer());
        
        int count = util.getGroupCount();
        for(int i = 0; i < count; i++) 
            addPage(facePage);
    }
    
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.ui.config.AbstractConfigurationWindow#createTopControl(org.eclipse.swt.widgets.Composite)
     */
	@Override
	@SuppressWarnings("unchecked")
    protected void createTopControl(Composite parent) {
        ToolBar tb = new ToolBar(parent, SWT.FLAT | SWT.RIGHT);
        tb.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        
        final Resources res = Resources.getInstance();
        
        // 导入
        ToolItem ti = new ToolItem(tb, SWT.PUSH);
        ti.setText(button_import);
        ti.setImage(res.getImage(Resources.icoImport24));
        ti.addSelectionListener(new SelectionAdapter() {
            @Override
			public void widgetSelected(SelectionEvent e) {
                importFace();
            }
        });
        
        // separator
        ti = new ToolItem(tb, SWT.SEPARATOR);
        
        // 添加分组
        ti = new ToolItem(tb, SWT.PUSH);
        ti.setText(face_add_group);
        ti.setImage(res.getImage(Resources.icoAddFaceGroup24));
        ti.addSelectionListener(new SelectionAdapter() {
            @Override
			public void widgetSelected(SelectionEvent e) {
                InputDialog dialog = new InputDialog(getShell(), face_new_group_title, face_new_group_message, null, null);
                if(dialog.open() == IDialogConstants.OK_ID) {
                    String name = dialog.getValue();
                    if(name != null && !name.equals("") && !FaceRegistry.getInstance().hasGroup(name)) {
                    	addGroup(name);
                    }                    
                }
            }
        });
        
        // 修改分组
        ti = new ToolItem(tb, SWT.PUSH);
        ti.setText(face_modify_group);
        ti.setImage(res.getImage(Resources.icoModifyFaceGroup24));
        ti.addSelectionListener(new SelectionAdapter() {
            @Override
			public void widgetSelected(SelectionEvent e) {
            	FaceGroup g = util.getFaceGroup(getCurrentPageId());
            	switch(g.getId()) {
            		case FaceConstant.DEFAULT_GROUP_ID:
            		case FaceConstant.RECEIVED_GROUP_ID:
            		case FaceConstant.CUSTOM_HEAD_GROUP_ID:
            			MessageDialog.openWarning(getShell(), 
            					message_box_common_warning_title, 
            					message_box_cannot_change_default_group);
            			break;
            		default:
                        InputDialog dialog = new InputDialog(getShell(), face_new_group_title, face_new_group_message, null, null);
	                    if(dialog.open() == IDialogConstants.OK_ID) {
	                        String name = dialog.getValue();
	                        if(name != null && !name.equals("")) {
	                            FaceRegistry.getInstance().setGroupName(getCurrentPageId(), name);
	                            getCurrentPageLabel().setText(name);
	                        }                    
	                    }
	            		break;
            	}
            }
        });
        
        // 删除分组
        ti = new ToolItem(tb, SWT.PUSH);
        ti.setText(face_delete_group);
        ti.setImage(res.getImage(Resources.icoDeleteFaceGroup24));
        ti.addSelectionListener(new SelectionAdapter() {
            @Override
			public void widgetSelected(SelectionEvent e) {
            	FaceGroup g = util.getFaceGroup(getCurrentPageId());
            	switch(g.getId()) {
            		case FaceConstant.DEFAULT_GROUP_ID:
            		case FaceConstant.RECEIVED_GROUP_ID:
            		case FaceConstant.CUSTOM_HEAD_GROUP_ID:
            			MessageDialog.openWarning(getShell(), 
            					message_box_common_warning_title, 
            					message_box_cannot_delete_default_group);
            			break;
            		default:
            			util.removeGroup(getCurrentPageId());
	            		removeCurrentPage();
	            		break;
            	}
            }
        });
    }

    /**
     * 添加一个组
     * 
     * @param name
     * 		组名
     */
    public void addGroup(String name) {
        // 添加新的group元素
        FaceRegistry.getInstance().addGroup(name);
        // 添加一页
        addPage(facePage);  
        getPageListContainer().layout();
        // 设置新页为当前页
        setCurrentPage(getPageCount() - 1);
	}

	/**
     * 导入表情文件，目前只支持CFC文件
     */
    private void importFace() {
        // 如果当前没有分组，则返回
        if(getPageCount() == 0) {
            MessageDialog.openWarning(getShell(), message_box_common_warning_title, message_box_need_face_group);
            return;
        }
            
        FileDialog dialog = new FileDialog(getShell(), SWT.OPEN);
        dialog.setFilterExtensions(new String[] { "*.eip", "*.cfc" });
        dialog.setFilterNames(new String[] { face_eip, face_cfc });
        dialog.open();
        
        String filename = dialog.getFileName();
        if(filename == null)
            return;
        String path = dialog.getFilterPath();
        if(!path.endsWith(File.separator))
            path += File.separatorChar;
        
        FaceGroup g = util.getFaceGroup(getCurrentPageId());
        if(filename.endsWith(".cfc")) {
        	IExecutor executor = new DialogJobExecutor(main);
        	executor.addJob(new ImportCFCJob(path + filename, LumaQQ.CUSTOM_FACE_DIR, g));
        	executor.execute();        	
        } else {
        	IExecutor executor = new DialogJobExecutor(main);
        	executor.addJob(new ImportEIPJob(this, path + filename, LumaQQ.CUSTOM_FACE_DIR, g));
        	executor.execute();        	
        }
        g = util.getFaceGroup(getCurrentPageId());
        facePage.refreshViewer(g);
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.ui.config.AbstractConfigurationWindow#getTitle()
     */
	@Override
    protected String getTitle() {
        return face_title;
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.ui.config.AbstractConfigurationWindow#getImage()
     */
	@Override
    protected Image getImage() {
        return Resources.getInstance().getImage(Resources.icoLumaQQ);
    }
}
