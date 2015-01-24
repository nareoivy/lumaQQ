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

import static edu.tsinghua.lumaqq.resource.Messages.message_box_common_warning_title;
import static edu.tsinghua.lumaqq.resource.Messages.message_box_please_select_cluster;
import static edu.tsinghua.lumaqq.resource.Messages.search_cluster_result_creator;
import static edu.tsinghua.lumaqq.resource.Messages.search_cluster_result_id;
import static edu.tsinghua.lumaqq.resource.Messages.search_cluster_result_info;
import static edu.tsinghua.lumaqq.resource.Messages.search_cluster_result_message;
import static edu.tsinghua.lumaqq.resource.Messages.search_cluster_result_name;
import static edu.tsinghua.lumaqq.resource.Messages.search_cluster_result_title;

import java.util.List;

import edu.tsinghua.lumaqq.models.Cluster;
import edu.tsinghua.lumaqq.qq.beans.ClusterInfo;
import edu.tsinghua.lumaqq.ui.helper.UITool;
import edu.tsinghua.lumaqq.widgets.mac.Ring;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

/**
 * 群搜索结果页
 * 
 * @author luma
 */
public class SearchClusterResultWizardPage extends WizardPage {
    private SearchResult result;
    private TableViewer viewer;
    private SearchResultContentProvider provider;
    private SearchWizard wizard;
    private SearchWizardModel model;
    private CLabel lnkInfo;
    private Ring ring;
    
    /**
     * @param pageName
     */
    protected SearchClusterResultWizardPage(String pageName, SearchResult r) {
        super(pageName);
        result = r;
        setTitle(search_cluster_result_title);
        setMessage(search_cluster_result_message);
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
        viewer.setSorter(new SearchClusterResultSorter());
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
        tc.setText(search_cluster_result_id);
        tc.setWidth(80);
        tc.addSelectionListener(new SelectionAdapter() {
            @Override
			public void widgetSelected(SelectionEvent e) {
                sortOn(SearchClusterResultSorter.ID);
            }
        });
        tc = new TableColumn(table, SWT.LEFT);
        tc.setText(search_cluster_result_name);
        tc.setWidth(250);
        tc.addSelectionListener(new SelectionAdapter() {
            @Override
			public void widgetSelected(SelectionEvent e) {
                sortOn(SearchClusterResultSorter.NAME);
            }
        });
        tc = new TableColumn(table, SWT.CENTER);
        tc.setText(search_cluster_result_creator);
        tc.setWidth(100);
        tc.addSelectionListener(new SelectionAdapter() {
            @Override
			public void widgetSelected(SelectionEvent e) {
                sortOn(SearchClusterResultSorter.CREATOR);
            }
        });
        table.setHeaderVisible(true);
        table.addSelectionListener(new SelectionAdapter() {
            @Override
			public void widgetDefaultSelected(SelectionEvent e) {
                viewInfo();
            }
        });
        
        layout = new GridLayout(2, false);
        Composite c = UITool.createContainer(control, new GridData(GridData.FILL_HORIZONTAL), layout);
        
        // busy ring
        ring = UITool.createRing(c);
        // 查看资料
        GridData gd = new GridData(GridData.HORIZONTAL_ALIGN_END | GridData.HORIZONTAL_ALIGN_FILL);
        gd.grabExcessHorizontalSpace = true;
        lnkInfo = UITool.createLink(c, search_cluster_result_info, null, gd);
        lnkInfo.addMouseListener(new MouseAdapter() {
            @Override
			public void mouseDown(MouseEvent e) {
                viewInfo();
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
        SearchClusterResultSorter sorter = (SearchClusterResultSorter)viewer.getSorter();
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
     * 查看群组资料
     */
    private void viewInfo() {
        Object obj = getSelected();
        if(obj == null)
            MessageDialog.openInformation(getShell(), message_box_common_warning_title, message_box_please_select_cluster);
        else if(obj instanceof ClusterInfo) {
            ClusterInfo info = (ClusterInfo)obj;            
			Cluster c = new Cluster();
			c.clusterId = info.clusterId;
			c.name = info.name;
			c.description = info.description;
			c.notice = info.notice;
			c.authType = info.authType;
			c.category = info.category;
			c.oldCategory = info.oldCategory;
			c.externalId = info.externalId;
			c.creator = info.creator;			
			wizard.getMainShell().getShellLauncher().openClusterInfoWindow(c).setOKButtonEnabled(false);
        }
    }

    /* (non-Javadoc)
     * @see org.eclipse.jface.wizard.WizardPage#isPageComplete()
     */
    @Override
	public boolean isPageComplete() {
        return validatePage();
    }
    
    /**
     * @return
     */
    private boolean validatePage() {
        return !viewer.getSelection().isEmpty();
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
     * 情况result，刷新table
     */
    public void reset() {
        result.clear();
        provider.first();
        viewer.refresh();
    }
    
    /**
     * 添加一页，并显示这一页
     * 
     * @param page
     */
    public void addPage(List<? extends Object> page) {
        result.addPage(page);        
        provider.last();
        viewer.refresh();
        ring.stop();
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
	 * 发生错误时
	 */
	protected void onSearchClusterError() {
		ring.stop();
	}
}
