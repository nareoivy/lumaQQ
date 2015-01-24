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

import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

import edu.tsinghua.lumaqq.resource.Colors;
import edu.tsinghua.lumaqq.ui.helper.BeanHelper;
import edu.tsinghua.lumaqq.ui.helper.UITool;
import edu.tsinghua.lumaqq.ui.listener.AroundBorderPaintListener;

/**
 * 高级搜索页
 * 
 * @author luma
 */
public class SearchUserAdvancedWizardPage extends WizardPage {
    private Button chkOnline, chkWithCam;
    private CCombo comboProvince, comboCity, comboAge, comboGender;
    private SearchWizard wizard;
    private SearchWizardModel model;
    
    /**
     * @param pageName
     */
    public SearchUserAdvancedWizardPage(String pageName) {
        super(pageName);
        setTitle(search_user_advanced_title);
        setMessage(search_user_advanced_message);
    }

    /* (non-Javadoc)
     * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
     */
    public void createControl(Composite parent) {
        Composite control = new Composite(parent, SWT.NONE);
        control.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        GridLayout layout = new GridLayout(2, true);
        layout.marginHeight = layout.marginWidth = 15;
        layout.verticalSpacing = 8;
        layout.horizontalSpacing = 30;
        control.setLayout(layout);      

        UITool.setDefaultBackground(control.getBackground());
        
        // 在线用户        
        chkOnline = UITool.createCheckbox(control, search_user_advanced_online, new GridData(GridData.HORIZONTAL_ALIGN_END));
        chkOnline.setSelection(true);
        chkOnline.addSelectionListener(new SelectionAdapter() {
            @Override
			public void widgetSelected(SelectionEvent e) {
                if(!chkOnline.getSelection())
                    chkWithCam.setSelection(false);
                model.setWithCam(chkWithCam.getSelection());
                model.setOnline(chkOnline.getSelection());
            }
        });
        // 有摄像头
        chkWithCam = UITool.createCheckbox(control, search_user_advanced_cam, new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING));
        chkWithCam.addSelectionListener(new SelectionAdapter() {
            @Override
			public void widgetSelected(SelectionEvent e) {
                chkOnline.setSelection(chkWithCam.getSelection());
                model.setWithCam(chkWithCam.getSelection());
                model.setOnline(chkOnline.getSelection());
            }
        });
        
        // 基本条件组
        layout = new GridLayout(2, false);
        layout.marginHeight = layout.marginWidth = 15;        
        GridData gd = new GridData(GridData.FILL_HORIZONTAL);
        gd.horizontalSpan = 2;
        Group basicGroup = UITool.createGroup(control, search_user_advanced_basic, gd, layout);
        basicGroup.addPaintListener(new AroundBorderPaintListener(new Class[] { CCombo.class }));
        
        // 省份
        UITool.createLabel(basicGroup, search_user_advanced_province);
        gd = new GridData();
        gd.widthHint = 150;
        comboProvince = UITool.createCCombo(basicGroup, gd);    
        comboProvince.setBackground(Colors.WHITE);        
        add(comboProvince, BeanHelper.PROVINCE);
        comboProvince.select(0);
        comboProvince.setVisibleItemCount(13);
        comboProvince.addSelectionListener(new SelectionAdapter() {
            @Override
			public void widgetSelected(SelectionEvent e) {
                comboCity.removeAll();
                int index = comboProvince.getSelectionIndex();
                add(comboCity, BeanHelper.getCitys(index));
                comboCity.select(0);
                
                model.setProvince(index);
                model.setCity(0);
            }
        });
        // 城市
        UITool.createLabel(basicGroup, search_user_advanced_city);
        gd = new GridData();
        gd.widthHint = 150;
        comboCity = UITool.createCCombo(basicGroup, gd);
        comboCity.setBackground(Colors.WHITE);
        add(comboCity, BeanHelper.getCitys(0));
        comboCity.setVisibleItemCount(11);
        comboCity.select(0);
        comboCity.addSelectionListener(new SelectionAdapter() {
        	@Override
        	public void widgetSelected(SelectionEvent e) {
        		model.setCity(comboCity.getSelectionIndex());
        	}
        });
        // 年龄
        UITool.createLabel(basicGroup, search_user_advanced_age);
        gd = new GridData();
        gd.widthHint = 150;
        comboAge = UITool.createCCombo(basicGroup, gd);
        comboAge.setBackground(Colors.WHITE);
        add(comboAge, BeanHelper.AGE);
        comboAge.setVisibleItemCount(6);
        comboAge.select(0);
        comboAge.addSelectionListener(new SelectionAdapter() {
        	@Override
        	public void widgetSelected(SelectionEvent e) {
        		model.setAge(comboAge.getSelectionIndex());
        	}
        });
        // 性别
        UITool.createLabel(basicGroup, search_user_advanced_gender);
        gd = new GridData();
        gd.widthHint = 150;
        comboGender = UITool.createCCombo(basicGroup, gd);
        comboGender.setBackground(Colors.WHITE);
        add(comboGender, BeanHelper.GENDER);
        comboGender.setVisibleItemCount(3);
        comboGender.select(0);
        comboGender.addSelectionListener(new SelectionAdapter() {
        	@Override
        	public void widgetSelected(SelectionEvent e) {
        		model.setGender(comboGender.getSelectionIndex());
        	}
        });
        
        setControl(control);
    }
    
    /**
     * 往combo里面添加item
     * 
     * @param combo
     * @param items
     */
    private void add(CCombo combo, String[] items) {
		for(String s : items)
			combo.add(s);
    }
    
    /* (non-Javadoc)
     * @see org.eclipse.jface.dialogs.IDialogPage#setVisible(boolean)
     */
    @Override
	public void setVisible(boolean visible) {
        super.setVisible(visible);
        if(visible) {
        	model.setEnterResultPage(true);
        	
        	chkOnline.setSelection(model.isOnline());
        	chkWithCam.setSelection(model.isWithCam());
        	comboProvince.select(model.getProvince());
        	comboCity.select(model.getCity());
        	comboAge.select(model.getAge());
        	comboGender.select(model.getGender());
        }
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
     * @see org.eclipse.jface.wizard.WizardPage#isPageComplete()
     */
    @Override
	public boolean isPageComplete() {
        return true;
    }
    
    /**
     * 高级搜索
     * 
     * @param page
     * 		页号
     * @return
     * 		包序号
     */
    public char doSearch(int page) {
        /*
         * Note: 省份里面的"不限"是0，但是不限之后的北京却是2。1是国外，但是在
         * 腾讯里面，国外是排在最后一个的，所以我疑惑了好一阵为啥北京不是1。直到
         * 有人说搜索国外时有问题才搞明白。LumaQQ的省份列表把国外放到了前面正确
         * 的位置，没有采用腾讯的方案
         */
        return wizard.getMainShell().getClient().user_AdvancedSearch(
                page,
                model.isOnline(),
                model.isWithCam(),
                model.getProvince(),
                model.getCity(),
                model.getAge(),
                model.getGender());
    }
}
