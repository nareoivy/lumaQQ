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
package edu.tsinghua.lumaqq.ui.wizard.search;

import static edu.tsinghua.lumaqq.resource.Messages.button_next;
import static edu.tsinghua.lumaqq.resource.Messages.button_previous;
import static edu.tsinghua.lumaqq.resource.Messages.message_box_common_warning_title;
import static edu.tsinghua.lumaqq.resource.Messages.message_box_please_select_user;
import static edu.tsinghua.lumaqq.resource.Messages.search_user_result_age;
import static edu.tsinghua.lumaqq.resource.Messages.search_user_result_all;
import static edu.tsinghua.lumaqq.resource.Messages.search_user_result_from;
import static edu.tsinghua.lumaqq.resource.Messages.search_user_result_gender;
import static edu.tsinghua.lumaqq.resource.Messages.search_user_result_info;
import static edu.tsinghua.lumaqq.resource.Messages.search_user_result_message;
import static edu.tsinghua.lumaqq.resource.Messages.search_user_result_nick;
import static edu.tsinghua.lumaqq.resource.Messages.search_user_result_page;
import static edu.tsinghua.lumaqq.resource.Messages.search_user_result_qq;
import static edu.tsinghua.lumaqq.resource.Messages.search_user_result_showall;
import static edu.tsinghua.lumaqq.resource.Messages.search_user_result_status;
import static edu.tsinghua.lumaqq.resource.Messages.search_user_result_title;

import java.util.List;

import edu.tsinghua.lumaqq.models.User;
import edu.tsinghua.lumaqq.qq.beans.AdvancedUserInfo;
import edu.tsinghua.lumaqq.qq.beans.UserInfo;
import edu.tsinghua.lumaqq.resource.Resources;
import edu.tsinghua.lumaqq.ui.MainShell;
import edu.tsinghua.lumaqq.ui.config.user.UserInfoWindow;
import edu.tsinghua.lumaqq.ui.helper.UITool;
import edu.tsinghua.lumaqq.widgets.mac.Ring;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

/**
 * 用户搜索结果页
 * 
 * @author luma
 */
public class SearchUserResultWizardPage extends WizardPage {
    private SearchResult result;
    private Label lblPage;
    private CLabel lnkAll, lnkInfo, lnkNext, lnkPrevious;
    private Ring ring;
    private TableViewer viewer;
    private SearchResultContentProvider provider;
    private SearchWizard wizard;
    private SearchWizardModel model;
    
    /**
     * @param pageName
     */
    public SearchUserResultWizardPage(String pageName, SearchResult r) {
        super(pageName);
        result = r;
        setTitle(search_user_result_title);
        setMessage(search_user_result_message);
        provider = new SearchResultContentProvider(result);        
    }

    /* (non-Javadoc)
     * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
     */
    public void createControl(Composite parent) {
        Composite control = new Composite(parent, SWT.NONE);
        control.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        GridLayout layout = new GridLayout();
        control.setLayout(layout);        
 
        UITool.setDefaultBackground(control.getBackground());
        
        // table viewer
        viewer = new TableViewer(control, SWT.FULL_SELECTION | SWT.V_SCROLL | SWT.H_SCROLL | SWT.SINGLE | SWT.BORDER);
        viewer.setContentProvider(provider);
        viewer.setLabelProvider(new SearchResultLabelProvider());
        viewer.setSorter(new SearchUserResultSorter());
        viewer.setInput(this);
        viewer.addSelectionChangedListener(new ISelectionChangedListener() {
            public void selectionChanged(SelectionChangedEvent event) {
                model.setSelectedModel(getSelected());
                setPageComplete(validatePage());
            }
        });
        
        Table table = viewer.getTable();
        table.setLayoutData(new GridData(GridData.FILL_BOTH));
        TableColumn tc = new TableColumn(table, SWT.LEFT);
        tc.setText(search_user_result_qq);
        tc.setWidth(80);
        tc.addSelectionListener(new SelectionAdapter() {
            @Override
			public void widgetSelected(SelectionEvent e) {
                sortOn(SearchUserResultSorter.QQ_NUMBER);
            }
        });
        tc = new TableColumn(table, SWT.LEFT);
        tc.setText(search_user_result_nick);
        tc.setWidth(100);
        tc.addSelectionListener(new SelectionAdapter() {
            @Override
			public void widgetSelected(SelectionEvent e) {
                sortOn(SearchUserResultSorter.NICK);
            }
        });
        tc = new TableColumn(table, SWT.CENTER);
        tc.setText(search_user_result_gender);
        tc.setWidth(40);
        tc.addSelectionListener(new SelectionAdapter() {
            @Override
			public void widgetSelected(SelectionEvent e) {
                sortOn(SearchUserResultSorter.GENDER);
            }
        });
        tc = new TableColumn(table, SWT.CENTER);
        tc.setText(search_user_result_age);
        tc.setWidth(40);
        tc.addSelectionListener(new SelectionAdapter() {
            @Override
			public void widgetSelected(SelectionEvent e) {
                sortOn(SearchUserResultSorter.AGE);
            }
        });
        tc = new TableColumn(table, SWT.LEFT);
        tc.setText(search_user_result_from);
        tc.setWidth(100);
        tc.addSelectionListener(new SelectionAdapter() {
            @Override
			public void widgetSelected(SelectionEvent e) {
                sortOn(SearchUserResultSorter.FROM);
            }
        });
        tc = new TableColumn(table, SWT.LEFT);
        tc.setText(search_user_result_status);
        tc.setWidth(50);        
        tc.addSelectionListener(new SelectionAdapter() {
            @Override
			public void widgetSelected(SelectionEvent e) {
                sortOn(SearchUserResultSorter.STATUS);
            }
        });
        table.setHeaderVisible(true);
        table.addSelectionListener(new SelectionAdapter() {
            @Override
			public void widgetDefaultSelected(SelectionEvent e) {
                viewInfo();
            }
        });
        
        layout = new GridLayout(6, false);
        layout.horizontalSpacing = 15;
        Composite c = UITool.createContainer(control, new GridData(GridData.FILL_HORIZONTAL), layout);
        
        // busy ring
        ring = UITool.createRing(c);
        // 当前页
        lblPage = UITool.createLabel(c, "", new GridData(GridData.FILL_HORIZONTAL));
        // 全部
        lnkAll = UITool.createLink(c, search_user_result_all, null);
        lnkAll.addMouseListener(new MouseAdapter() {
            @Override
			public void mouseDown(MouseEvent e) {
                if(wizard.isOperating())
                    return;
                
                provider.setShowAll(true);
                viewer.refresh();
                update();
            }
        });
        // 查看资料
        lnkInfo = UITool.createLink(c, search_user_result_info, null);
        lnkInfo.addMouseListener(new MouseAdapter() {
            @Override
			public void mouseDown(MouseEvent e) {
                viewInfo();
            }
        });
        // 上一页
        lnkPrevious = UITool.createLink(c, button_previous, Resources.getInstance().getImage(Resources.icoPrevious));
        lnkPrevious.addMouseListener(new MouseAdapter() {
            @Override
			public void mouseDown(MouseEvent e) {
                if(wizard.isOperating())
                    return;
                
                provider.previous();
                viewer.refresh();
                update();
            }
        });
        // 下一页
        lnkNext = UITool.createLink(c, button_next, Resources.getInstance().getImage(Resources.icoNext));
        lnkNext.addMouseListener(new MouseAdapter() {
            @Override
			public void mouseDown(MouseEvent e) {
                if(wizard.isOperating())
                    return;
                
                if(provider.hasNext()) {
	                provider.next();
	                viewer.refresh();
	                update();
                } else {             
                	ring.rotate();
	                provider.next();
	                viewer.refresh();	                
	                wizard.doSearch(provider.getCurrentPageId());                
                }
            }
        });
        
        setControl(control);
    }
    
    /**
     * 设置在什么字段上排序
     * 
     * @param sortOn
     * 		排序字段ID
     */
    protected void sortOn(int sortOn) {
        SearchUserResultSorter sorter = (SearchUserResultSorter)viewer.getSorter();
        if(sorter.getSortOn() == sortOn)
            sorter.reverse();
        else
            sorter.setSortOn(sortOn);        
        viewer.refresh();
    }

    /**
     * @return
     * 		当前选择的对象
     */
    private Object getSelected() {
        IStructuredSelection selection = (IStructuredSelection)viewer.getSelection();
        return selection.getFirstElement();
    }
    
    /**
     * 搜索发生错误时
     */
    protected void onSearchUserError() {
    	ring.stop();
    }
    
    /**
     * 查看用户资料
     */
    private void viewInfo() {
        Object obj = getSelected();
        if(obj == null)
            MessageDialog.openInformation(getShell(), message_box_common_warning_title, message_box_please_select_user);
        else if(obj instanceof UserInfo) {
            UserInfo user = (UserInfo)obj;
            MainShell main = wizard.getMainShell();
			User f = new User();
			f.qq = user.qqNum;
			f.nick = user.nick;
			f.displayName = f.nick;
			f.headId = user.face;
			main.getShellLauncher().openUserInfoWindow(f, UserInfoWindow.READ_ONLY);
			main.getClient().user_GetInfo(user.qqNum);
        } else if(obj instanceof AdvancedUserInfo) {
            AdvancedUserInfo user = (AdvancedUserInfo)obj;
            MainShell main = wizard.getMainShell();
            User f = new User();
			f.qq = user.qqNum;
			f.nick = user.nick;
			f.displayName = f.nick;
			f.headId = user.face;
			main.getShellLauncher().openUserInfoWindow(f, UserInfoWindow.READ_ONLY);
			main.getClient().user_GetInfo(user.qqNum);
        }
    }
    
    /* (non-Javadoc)
     * @see org.eclipse.jface.wizard.WizardPage#isPageComplete()
     */
    @Override
	public boolean isPageComplete() {
        return validatePage();
    }
    
    /* (non-Javadoc)
     * @see org.eclipse.jface.wizard.WizardPage#setWizard(org.eclipse.jface.wizard.IWizard)
     */
    @Override
	public void setWizard(IWizard newWizard) {
        super.setWizard(newWizard);
        wizard = (SearchWizard)newWizard;
        model = (SearchWizardModel)wizard.getModel();
    }
    
    /**
     * @return	
     *  	true 如果可以继续
     */
    private boolean validatePage() {
        return !viewer.getSelection().isEmpty();
    }
    
    /* (non-Javadoc)
     * @see org.eclipse.jface.dialogs.IDialogPage#setVisible(boolean)
     */
    @Override
	public void setVisible(boolean visible) {
        super.setVisible(visible);
        if(visible && model.isEnterResultPage()) {
        	ring.rotate();
            reset();
            wizard.setEnd(false);
            wizard.doSearch(0);
        }
    }
    
    /**
     * 情况result，刷新table
     */
    public void reset() {
        result.clear();
        provider.first();
        viewer.refresh();
        lblPage.setText("");
        lnkPrevious.setVisible(false);
        lnkNext.setVisible(true);
    }
    
    /**
     * 更新组件状态
     */
    public void update() {
        if(provider.isShowAll())
            lblPage.setText(search_user_result_showall);
        else
            lblPage.setText(NLS.bind(search_user_result_page, String.valueOf(provider.getCurrentPageId() + 1)));
        lnkNext.setVisible(provider.hasNext() || !wizard.isEnd());
        lnkPrevious.setVisible(provider.hasPrevious());
    }
    
    /**
     * 添加一页，并显示这一页
     * 
     * @param page
     */
    public void addPage(List<? extends Object> page) {
        result.addPage(page);        
        lastPage();
        ring.stop();
    }
    
    /**
     * 在搜索结束时由wizard调用
     */
    public void onSearchEnd() {
        update();
        lastPage();
        ring.stop();
    }
    
    /**
     * 显示最后一页
     */
    private void lastPage() {
        provider.last();
        viewer.refresh();
        lblPage.setText(NLS.bind(search_user_result_page, String.valueOf(provider.getCurrentPageId() + 1)));
        lnkNext.setVisible(provider.hasNext() || !wizard.isEnd());
        lnkPrevious.setVisible(provider.hasPrevious());
    }
}
