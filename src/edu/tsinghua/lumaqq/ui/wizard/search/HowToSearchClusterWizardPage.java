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

import static edu.tsinghua.lumaqq.resource.Messages.*;

import edu.tsinghua.lumaqq.qq.Util;
import edu.tsinghua.lumaqq.resource.Colors;
import edu.tsinghua.lumaqq.ui.MainShell;
import edu.tsinghua.lumaqq.ui.helper.ClusterCategoryTool;
import edu.tsinghua.lumaqq.ui.helper.UITool;
import edu.tsinghua.lumaqq.ui.listener.AroundBorderPaintListener;

import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
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
import org.eclipse.swt.widgets.Text;

/**
 * 查找群的方式页
 * 
 * @author luma
 */
public class HowToSearchClusterWizardPage extends WizardPage {    
    private Button rdoDemo, rdoById, rdoByCategory;
    private Text textId;
    private SearchWizard wizard;
    private SearchWizardModel model;
	private CCombo comboOne, comboTwo, comboThree;
	private int idLevel0, idLevel1, idLevel2;
	private ClusterCategoryTool util;
	private Integer[] iOne, iTwo, iThree;
	private int categoryId;
    
    /**
     * @param pageName
     */
    public HowToSearchClusterWizardPage(MainShell main, String pageName) {
        super(pageName);
        setTitle(how_to_search_cluster_title);
        setMessage(how_to_search_cluster_message);
		util = main.getClusterCategoryUtility();
		categoryId = 0;
    }
    
    @Override
    public IWizardPage getPreviousPage() {
    	return wizard.getPage(SearchWizardModel.PAGE_SEARCH_WHAT);
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

    /* (non-Javadoc)
     * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
     */
    public void createControl(Composite parent) {
        Composite control = new Composite(parent, SWT.NONE);
        GridData gd = new GridData(GridData.FILL_HORIZONTAL);
        control.setLayoutData(gd);
        GridLayout layout = new GridLayout();
        layout.marginHeight = layout.marginWidth = 15;
        layout.verticalSpacing = 8;
        control.setLayout(layout);
        control.addPaintListener(new AroundBorderPaintListener(new Class[] { Text.class, CCombo.class }));
        
        UITool.setDefaultBackground(control.getBackground());
        
        // 查找示范群
        rdoDemo = UITool.createRadio(control, how_to_search_demo_cluster);
        rdoDemo.setSelection(true);
        rdoDemo.addSelectionListener(new SelectionAdapter() {
            @Override
			public void widgetSelected(SelectionEvent e) {
            	if(rdoDemo.getSelection())
            		onSelection(SearchWizardModel.DEMO_CLUSTER);
            }
        });
        // 通过群ID
        rdoById = UITool.createRadio(control, how_to_search_cluster_by_id);
        rdoById.addSelectionListener(new SelectionAdapter() {
            @Override
			public void widgetSelected(SelectionEvent e) {
            	if(rdoById.getSelection())
            		onSelection(SearchWizardModel.BY_CLUSTER_ID);
            }
        });
        // 群ID输入框
        gd = new GridData();
        gd.widthHint = 160;
        gd.heightHint = 18;
        gd.horizontalIndent = 20;
        textId = UITool.createSingleText(control, gd);
        textId.addModifyListener(new ModifyListener() {
            public void modifyText(ModifyEvent e) {
                setPageComplete(validatePage());
            }
        });
        // 通过群分类
        rdoByCategory = UITool.createRadio(control, how_to_search_cluster_by_category);
        rdoByCategory.addSelectionListener(new SelectionAdapter() {
        	@Override
        	public void widgetSelected(SelectionEvent e) {
        		if(rdoByCategory.getSelection())
        			onSelection(SearchWizardModel.BY_CATEGORY);
        	}
        });
        // 群分类，Level 1
        comboOne = UITool.createCCombo(control);
        gd = new GridData();
        gd.widthHint = 160;
        gd.heightHint = 18;
        gd.horizontalIndent = 20;
        comboOne.setLayoutData(gd);
		comboOne.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				idLevel0 = iOne[comboOne.getSelectionIndex()];
				comboTwo.setItems(util.getSubCategory(idLevel0));
				iTwo = util.getSubCategoryId(idLevel0);
				comboTwo.setText("");
				comboTwo.setEnabled(true);
				comboThree.removeAll();
				comboThree.setEnabled(false);
				idLevel1 = 0;
				idLevel2 = 0;
				categoryId = idLevel0;
				setPageComplete(validatePage());
			}
		});
        // 群分类，Level 2
        comboTwo = UITool.createCCombo(control);
        gd = new GridData();
        gd.widthHint = 160;
        gd.heightHint = 18;
        gd.horizontalIndent = 20;
        comboTwo.setLayoutData(gd);
		comboTwo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				idLevel1 = iTwo[comboTwo.getSelectionIndex()];
				iThree = util.getSubCategoryId(idLevel1);
				comboThree.setEnabled(true);
				comboThree.setItems(util.getSubCategory(idLevel1));
				comboThree.setText("");
				idLevel2 = 0;
				categoryId = idLevel1;
				setPageComplete(validatePage());
			}
		});
        // 群分类，Level 3
        comboThree = UITool.createCCombo(control);
        gd = new GridData();
        gd.widthHint = 160;
        gd.heightHint = 18;
        gd.horizontalIndent = 20;
        comboThree.setLayoutData(gd);
		comboThree.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				idLevel2 = iThree[comboThree.getSelectionIndex()];
				categoryId = idLevel2;
				setPageComplete(validatePage());
			}
		});
        
        setControl(control);
		comboOne.setItems(util.getSubCategory(0));
		comboOne.setText("");
		iOne = util.getSubCategoryId(0);
		idLevel0 = idLevel1 = idLevel2 = 0;
		refreshCombo(categoryId);
        refreshControlStatus();
    }
    
	/**
	 * 刷新combo控件状态和内容
	 * 
	 * @param id
	 */
	private void refreshCombo(int id) {
		if(id == 0) {
			comboTwo.setText("");
			comboTwo.setEnabled(false);
			comboThree.setText("");
			comboThree.setEnabled(false);
		} else {
			if(id > 0) {
				idLevel0 = util.getLevelId(id, 0);
				idLevel1 = util.getLevelId(id, 1);
				idLevel2 = util.getLevelId(id, 2);
				comboOne.setText(util.getName(idLevel0));
				if(idLevel0 > 0) {
					iTwo = util.getSubCategoryId(idLevel0);
					comboTwo.setItems(util.getSubCategory(idLevel0));
					comboTwo.setEnabled(true);
					comboTwo.setText(util.getName(idLevel1));
				} else {
					comboTwo.setText("");
					comboTwo.setEnabled(false);
				}
				if(idLevel1 > 0) {
					iThree = util.getSubCategoryId(idLevel1);
					comboThree.setItems(util.getSubCategory(idLevel1));
					comboThree.setEnabled(true);
					comboThree.setText(util.getName(idLevel2));
				} else {
					comboThree.setText("");
					comboThree.setEnabled(false);
				}
			}
		}
	}
    
    /**
     * radio button被选择时调用此方法
     * 
     * @param searchMode
     */
    private void onSelection(int searchMode) {
		model.setClusterSearchMode(searchMode);
		refreshControlStatus();
		setPageComplete(validatePage());
    }
    
    /**
     * 刷新控件状态
     */
    private void refreshControlStatus() {
        if(rdoDemo.getSelection()) {
        	disableIdInput();
        	disableCategoryInput();
        } else if(rdoById.getSelection()) {
        	enableIdInput();
        	disableCategoryInput();
        } else if(rdoByCategory.getSelection()) {
        	disableIdInput();
        	enableCategoryInput();
        }
    }
    
    private void enableCategoryInput() {
    	comboOne.setEnabled(true);
    	comboTwo.setEnabled(true);
    	comboThree.setEnabled(true);
    	comboOne.setBackground(Colors.WHITE);
    	comboTwo.setBackground(Colors.WHITE);
    	comboThree.setBackground(Colors.WHITE);
    }
    
    private void disableCategoryInput() {
    	comboOne.setEnabled(false);
    	comboTwo.setEnabled(false);
    	comboThree.setEnabled(false);
    	comboOne.setBackground(getControl().getBackground());
    	comboTwo.setBackground(getControl().getBackground());
    	comboThree.setBackground(getControl().getBackground());
    }
    
    private void enableIdInput() {
        textId.setEnabled(true);
        textId.setBackground(Colors.WHITE);
    }
    
    private void disableIdInput() {
        textId.setEnabled(false);
        textId.setBackground(getControl().getBackground());   
    }
    
    /* (non-Javadoc)
     * @see org.eclipse.jface.wizard.WizardPage#isPageComplete()
     */
    @Override
	public boolean isPageComplete() {
        return validatePage();
    }
    
    /* (non-Javadoc)
     * @see org.eclipse.jface.dialogs.IDialogPage#setVisible(boolean)
     */
    @Override
	public void setVisible(boolean visible) {
        super.setVisible(visible);
        if(visible)
        	model.setEnterResultPage(true);
    }    

    /**
     * @return
     * 		true表示输入有效
     */
    private boolean validatePage() {        
        if(rdoById.getSelection()) {
        	String s = textId.getText().trim();
            if(!Util.isInt(s)) {
                setErrorMessage(error_invalid_cluster_id);
                return false;
            } else
            	model.setClusterId(Util.getInt(s, 0));
        } else if(rdoByCategory.getSelection()) {
        	if(categoryId == 0) {
        		setErrorMessage(error_select_category);
        		return false;
        	} else
        		model.setCategoryId(categoryId);
        }
        
        setErrorMessage(null);
        return true;
    }
    
    /**
     * @return
     * 		包序号
     */
    public char doSearch() {
        if(rdoDemo.getSelection())
            return wizard.getMainShell().getClient().cluster_SearchDemo();
        else
            return wizard.getMainShell().getClient().cluster_SearchById(model.getClusterId());
    }
}
