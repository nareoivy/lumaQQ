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
import java.util.Iterator;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MenuAdapter;
import org.eclipse.swt.events.MenuEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

import edu.tsinghua.lumaqq.LumaQQ;
import edu.tsinghua.lumaqq.customface.FaceEntry;
import edu.tsinghua.lumaqq.customface.SingleFaceImporter;
import edu.tsinghua.lumaqq.ecore.face.Face;
import edu.tsinghua.lumaqq.ecore.face.FaceGroup;
import edu.tsinghua.lumaqq.resource.Colors;
import edu.tsinghua.lumaqq.ui.config.AbstractPage;
import edu.tsinghua.lumaqq.ui.helper.FaceRegistry;
import edu.tsinghua.lumaqq.ui.helper.FileTool;
import edu.tsinghua.lumaqq.widgets.qstyle.Slat;

/**
 * 图片管理页
 * 
 * @author luma
 */
public class FaceManagePage extends AbstractPage {
    private Composite content;
    private TableViewer faceViewer;
    private int currentPage; 
    private Menu moveMenu;
    private Slat btnMove;
    private SelectionListener sl;
    
    /**
     * @param parent
     */
    public FaceManagePage(Composite parent) {
        super(parent);
    }
    
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.ui.config.AbstractPage#initialLayout(org.eclipse.swt.widgets.Composite)
     */
	@Override
    protected void initialLayout(Composite parent) {
        createContent(parent);
    }
    
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.ui.config.AbstractPage#initialVariable()
     */
	@Override
    protected void initialVariable() {
        currentPage = -1;
        sl = new SelectionAdapter() {
            @Override
			public void widgetSelected(SelectionEvent e) {
                MenuItem mi = (MenuItem)e.getSource();
                int dest = (Integer)mi.getData();
                if(dest == getCurrentPage())
                    return;
                
                FaceRegistry util = FaceRegistry.getInstance();
                IStructuredSelection selection = (IStructuredSelection)faceViewer.getSelection();
                Iterator<?> i = selection.iterator();
                while(i.hasNext())
                    util.moveFace((Face)i.next(), dest);
                faceViewer.refresh();
            }
        };
    }
    
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.ui.config.AbstractPage#getContent()
     */
	@Override
    protected Composite getContent() {
        return content;
    }
    
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.ui.config.AbstractPage#createContent(org.eclipse.swt.widgets.Composite)
     */
    @Override
	protected Control createContent(Composite parent) {
        content = new Composite(parent, SWT.NONE);
        GridLayout layout = new GridLayout(2, false);
        content.setLayout(layout);
        content.setBackground(Colors.WHITE);
	    FormData fd = new FormData();
	    fd.top = fd.left = new FormAttachment(0, 1);
	    fd.bottom = fd.right = new FormAttachment(100, -1);
	    setLayoutData(fd);	        
        
        // viewer
        faceViewer = new TableViewer(content, SWT.FULL_SELECTION | SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL | SWT.MULTI);
        faceViewer.setContentProvider(new FaceContentProvider());
        faceViewer.setLabelProvider(new FaceLabelProvider());
        Table t = faceViewer.getTable();
        GridData gd = new GridData(GridData.FILL_BOTH);
        gd.verticalSpan = 5;
        t.setLayoutData(gd);
        TableColumn tc = new TableColumn(t, SWT.LEFT);
        tc.setText(face_id);
        tc.setWidth(70);
        tc = new TableColumn(t, SWT.LEFT);
        tc.setText(face_image);
        tc.setWidth(100);
        t.setLinesVisible(true);
        t.setHeaderVisible(true);
        
        // 添加
        Slat btnAdd = new Slat(content, SWT.NONE);
        btnAdd.setText(button_add_dot);
        gd = new GridData(GridData.VERTICAL_ALIGN_BEGINNING | GridData.HORIZONTAL_ALIGN_BEGINNING);
        gd.widthHint = 100;
        btnAdd.setLayoutData(gd);
        btnAdd.addMouseListener(new MouseAdapter() {
            @Override
			public void mouseUp(MouseEvent e) {
                if(currentPage == -1) {
                    MessageDialog.openWarning(parentShell, message_box_common_warning_title, message_box_need_face_group);
                    return;
                }
                
                FileDialog dialog = new FileDialog(parentShell, SWT.OPEN);
                dialog.setFilterExtensions(new String[] { "*.*", "*.gif", "*.jpg", "*.bmp" });
                dialog.setFilterNames(new String[] { "All Files(*.*)", "GIF Files(*.gif)", "JPEG Files(*.jpg)", "Bitmap Files(*.bmp)" });
                dialog.open();
                
                String filename = dialog.getFileName();      
                if(filename == null)
                    return;
                    
                String path = dialog.getFilterPath();
                if(!path.endsWith(File.separator))
                    path += File.separatorChar;
                
                // 如果已经有了这个图片，返回
                FaceRegistry util = FaceRegistry.getInstance();
                FaceGroup g = util.getFaceGroup(getCurrentPage());
                SingleFaceImporter importer = new SingleFaceImporter(path + filename, LumaQQ.CUSTOM_FACE_DIR, g);
                FaceEntry entry = importer.getEntry();
                if(util.hasFace(entry.md5))
                    return;
                // 如果保存失败，返回
                if(!importer.saveEntry())
                    return;
                // 添加表情，刷新table
                util.addFace(entry, g);
                refreshViewer(g);
            }
        });
        // 删除
        Slat btnDelete = new Slat(content, SWT.NONE);
        btnDelete.setText(button_delete);
        gd = new GridData(GridData.VERTICAL_ALIGN_BEGINNING | GridData.HORIZONTAL_ALIGN_BEGINNING);
        gd.widthHint = 100;
        btnDelete.setLayoutData(gd);
        btnDelete.addMouseListener(new MouseAdapter() {
            @Override
			public void mouseUp(MouseEvent e) {
                FaceRegistry util = FaceRegistry.getInstance();
                IStructuredSelection selection = (IStructuredSelection)faceViewer.getSelection();
                Iterator<?> i = selection.iterator();
                while(i.hasNext()) {
                    Face face = (Face)i.next();
                    FileTool.deleteFile(util.getFacePath(face));
                    FileTool.deleteFile(util.getSmallFacePath(face));
                    util.removeFace(face.getMd5());
                }
                
                faceViewer.refresh();
            }
        });
        // 上移
        Slat btnUp = new Slat(content, SWT.NONE);
        btnUp.setText(button_up);
        gd = new GridData(GridData.VERTICAL_ALIGN_BEGINNING | GridData.HORIZONTAL_ALIGN_BEGINNING);
        gd.widthHint = 100;
        btnUp.setLayoutData(gd);
        btnUp.addMouseListener(new MouseAdapter() {
            @Override
			public void mouseUp(MouseEvent e) {
                FaceRegistry util = FaceRegistry.getInstance();
                Table table = faceViewer.getTable();
                int[] indices = table.getSelectionIndices();
                for(int i = 0; i < indices.length; i++) 
                    util.upFace(currentPage, indices[i]);
                
                faceViewer.refresh();
            }
        });
        // 下移
        Slat btnDown = new Slat(content, SWT.NONE);
        btnDown.setText(button_down);
        gd = new GridData(GridData.VERTICAL_ALIGN_BEGINNING | GridData.HORIZONTAL_ALIGN_BEGINNING);
        gd.widthHint = 100;
        btnDown.setLayoutData(gd);
        btnDown.addMouseListener(new MouseAdapter() {
            @Override
			public void mouseUp(MouseEvent e) {
                FaceRegistry util = FaceRegistry.getInstance();
                Table table = faceViewer.getTable();
                int[] indices = table.getSelectionIndices();
                for(int i = 0; i < indices.length; i++) 
                    util.downFace(currentPage, indices[i]);
                
                faceViewer.refresh();
            }
        });
        // 移动
        btnMove = new Slat(content, SWT.NONE);
        btnMove.setText(button_move_dot);
        gd = new GridData(GridData.VERTICAL_ALIGN_BEGINNING | GridData.HORIZONTAL_ALIGN_BEGINNING);
        gd.widthHint = 100;
        btnMove.setLayoutData(gd);
        btnMove.addMouseListener(new MouseAdapter() {
            @Override
			public void mouseUp(MouseEvent e) {
                Rectangle bound = btnMove.getBounds();
                moveMenu.setLocation(btnMove.getParent().toDisplay(bound.x, bound.y + bound.height));
                moveMenu.setVisible(true);
            }
        });
        
        // 初始化移动到菜单
        initMoveMenu();
        
        return content;
    }
    
    /**
     * 初始化移动到菜单
     */
    private void initMoveMenu() {
        moveMenu = new Menu(btnMove);
        moveMenu.addMenuListener(new MenuAdapter() {
            @Override
			public void menuShown(MenuEvent e) {
                MenuItem[] mi = moveMenu.getItems();
                for(int i = 0; i < mi.length; i++)
                    mi[i].dispose();
                fillMoveMenu();                
            }
        });
    }

    /**
     * 填充移动到菜单
     */
    private void fillMoveMenu() {
        FaceRegistry util = FaceRegistry.getInstance();
        int count = util.getGroupCount();
        for(int i = 0; i < count; i++) {
            MenuItem mi = new MenuItem(moveMenu, SWT.PUSH);
            mi.setData(new Integer(i));
            FaceGroup g = util.getFaceGroup(i);
            mi.setText(g.getName());           
            mi.addSelectionListener(sl);
        }
    }

    /**
     * 刷新表情列表
     * 
     * @param g
     */
    public void refreshViewer(FaceGroup g) {
        faceViewer.setInput(g);
        faceViewer.refresh();
    }
    
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.ui.config.AbstractPage#setVisible(boolean)
     */
	@Override
    public void setVisible(boolean b) {
    }
    
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.ui.config.AbstractPage#setLayoutData(java.lang.Object)
     */
	@Override
    public void setLayoutData(Object data) {
        content.setLayoutData(data);
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.ui.config.AbstractPage#saveDirtyProperty(int)
     */
	@Override
    protected void saveDirtyProperty(int propertyId) {
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.ui.config.AbstractPage#initializeValues()
     */
	@Override
    protected void initializeValues() {
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.ui.config.AbstractPage#getImage()
     */
	@Override
    protected Image getImage() {
        return null;
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.ui.config.AbstractPage#getTitle()
     */
	@Override
    protected String getTitle(int page) {
        FaceGroup g = FaceRegistry.getInstance().getFaceGroup(page);
        if(g == null)
            return null;
        else
            return g.getName();
    }
    
    /**
     * 设置当前页
     * 
     * @param currentPage
     * 		当前页号
     */
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
    
    /**
     * @return
     * 		当前页号
     */
    public int getCurrentPage() {
        return currentPage;
    }
}
