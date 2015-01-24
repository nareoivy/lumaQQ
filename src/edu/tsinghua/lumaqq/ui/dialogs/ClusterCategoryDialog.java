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

import edu.tsinghua.lumaqq.resource.Resources;
import edu.tsinghua.lumaqq.ui.MainShell;
import edu.tsinghua.lumaqq.ui.helper.ClusterCategoryTool;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

/**
 * 选择群分类的对话框
 * 
 * @author luma
 */
public class ClusterCategoryDialog extends Dialog {
	private int categoryId;
	private Combo comboOne;
	private Combo comboTwo;
	private Combo comboThree;
	private ClusterCategoryTool util;
	private Integer[] iOne, iTwo, iThree;
	private int idLevel0, idLevel1, idLevel2;
	
	public ClusterCategoryDialog(Shell parentShell, MainShell main) {
		super(parentShell);
		categoryId = 0;
		util = main.getClusterCategoryUtility();
	}
	
	@Override
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		shell.setText(cluster_category_title);
		shell.setImage(Resources.getInstance().getImage(Resources.icoLumaQQ));
	}
	
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite control = (Composite)super.createDialogArea(parent);

		comboOne = new Combo(control, SWT.READ_ONLY);
		comboOne.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
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
			}
		});
		
		comboTwo = new Combo(control, SWT.READ_ONLY);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.verticalIndent = 30;
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
			}
		});
		
		comboThree = new Combo(control, SWT.READ_ONLY);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.verticalIndent = 30;
		comboThree.setLayoutData(gd);
		comboThree.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				idLevel2 = iThree[comboThree.getSelectionIndex()];
				categoryId = idLevel2;
			}
		});
		
		comboOne.setItems(util.getSubCategory(0));
		comboOne.setText("");
		iOne = util.getSubCategoryId(0);
		idLevel0 = idLevel1 = idLevel2 = 0;
		refreshCombo(categoryId);
		return control;
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
	 * @return Returns the categoryId.
	 */
	public int getCategoryId() {
		return categoryId;
	}

	/**
	 * @param categoryId The categoryId to set.
	 */
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
}
