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
package edu.tsinghua.lumaqq.widgets.record;

import static edu.tsinghua.lumaqq.resource.Messages.*;

import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.MenuAdapter;
import org.eclipse.swt.events.MenuEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Spinner;

import edu.tsinghua.lumaqq.models.Cluster;
import edu.tsinghua.lumaqq.models.User;
import edu.tsinghua.lumaqq.record.IKeyConstants;
import edu.tsinghua.lumaqq.record.RecordEntry;
import edu.tsinghua.lumaqq.record.RecordManager;
import edu.tsinghua.lumaqq.resource.Resources;
import edu.tsinghua.lumaqq.ui.MainShell;

/**
 * 聊天记录查看器控件，做成控件比较方便维护
 * 
 * @author luma
 */
public class RecordViewer extends Composite implements DisposeListener {    
	// 界面控件
	private Button chkDisplayAll;
	private Waterfall waterfall;
	private Menu recordMenu;
	// 年月日
	private int year, month, day;
	// 消息管理器
	private RecordManager rm;
	// 消息提供者
	private IRecordProvider provider;
	// IconHolder实例
	private Resources res;
	// 剪贴板实例
	private Clipboard clipboard;
	// MainShell
	private MainShell main;
	
	// 消息过滤器
	private IRecordEntryFilter filter;
	
	/**
	 * @param parent
	 * @param style
	 */
	public RecordViewer(Composite parent, IRecordProvider provider, MainShell main) {
		super(parent, SWT.NONE);		
		
		// 初始化变量
		this.provider = provider;
		this.main = main;
		clipboard = new Clipboard(getDisplay());
		res = Resources.getInstance();
		Calendar c = Calendar.getInstance();
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH) + 1;
		day = c.get(Calendar.DAY_OF_MONTH);
		// 初始化窗口布局
		initLayout();
		// 初始化弹出菜单
		initPopupMenu();
		// 添加事件监听器
		addDisposeListener(this);
	}
	
	@Override
	public void setFont(Font font) {
		super.setFont(font);
		waterfall.setFont(font);
	}

	/**
	 * 初始化弹出菜单
	 */
	private void initPopupMenu() {
		recordMenu = new Menu(this);
		// 拷贝消息到剪贴板
		MenuItem mi = new MenuItem(recordMenu, SWT.PUSH);
		mi.setText(record_viewer_menu_copy);
		mi.setImage(res.getImage(Resources.icoCopy));
		mi.addSelectionListener(
			new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {			
					RecordEntry entry = getSelectedMessageEntry();
					if(entry != null) {
						String copyString = provider.copyToClipboard(entry);
						if(copyString != null)
							clipboard.setContents(new Object[] { copyString }, new Transfer[] { TextTransfer.getInstance() });

					}
				}
			}
		);
		// 导出表中所有信息
		mi = new MenuItem(recordMenu, SWT.PUSH);
		mi.setText(record_viewer_menu_export);
		mi.setImage(res.getImage(Resources.icoTxtFile));
		mi.addSelectionListener(
			new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					switch(provider.getExportType()) {
						case IRecordExporter.EXPORT_FRIEND:
							main.getExportHelper().exportMessage((User)provider.getModel(), provider.getRecords());
							break;
						case IRecordExporter.EXPORT_CLUSTER:
							main.getExportHelper().exportMessage((Cluster)provider.getModel(), provider.getRecords());
							break;
						case IRecordExporter.EXPORT_SMS:
							main.getExportHelper().exportMessage((String)provider.getModel(), provider.getRecords());
							break;
						case IRecordExporter.EXPORT_SYSTEM:
							main.getExportHelper().exportSystemMessage(provider.getRecords());
							break;
					}
				}
			}
		);
		// 添加菜单显示事件监听器
		recordMenu.addMenuListener(
			new MenuAdapter() {
				@Override
				public void menuShown(MenuEvent e) {
					recordMenu.getItem(0).setEnabled(waterfall.getSelection() != null);
				}
			}
		);
	}

	/**
	 * 初始化界面布局
	 */
	private void initLayout() {
		GridLayout layout = new GridLayout(8, false);
		layout.marginHeight = layout.marginWidth = 0;
		layout.horizontalSpacing = 0;		
		setLayout(layout);
		// 显示标签
		Label lblDisplay = new Label(this, SWT.NONE);		
		lblDisplay.setText(record_viewer_label_display);
		lblDisplay.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_CENTER));
		// 年spinner
		GridData gd = new GridData(GridData.VERTICAL_ALIGN_CENTER);
		gd.horizontalIndent = 5;
		Spinner spinYear = new Spinner(this, SWT.READ_ONLY);
		spinYear.setLayoutData(gd);
		spinYear.setMinimum(1970);
		spinYear.setMaximum(3000);
		spinYear.setSelection(year);
		spinYear.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Spinner spin = (Spinner)e.getSource();
				year = spin.getSelection();
				if(!chkDisplayAll.getSelection())
					refreshRecord();	
			}
		});
		// 年标签
		gd = new GridData(GridData.VERTICAL_ALIGN_CENTER);
		gd.horizontalIndent = 1;
		Label lblYearChar = new Label(this, SWT.READ_ONLY);
		lblYearChar.setText(record_viewer_label_year);
		lblYearChar.setLayoutData(gd);
		// 月Spinner
		gd = new GridData(GridData.VERTICAL_ALIGN_CENTER);
		gd.horizontalIndent = 5;
		Spinner spinMonth = new Spinner(this, SWT.NONE);
		spinMonth.setLayoutData(gd);
		spinMonth.setMinimum(1);
		spinMonth.setMaximum(12);
		spinMonth.setSelection(month);
		spinMonth.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Spinner spin = (Spinner)e.getSource();
				month = spin.getSelection();
				if(!chkDisplayAll.getSelection())
					refreshRecord();	
			}
		});
		// 月标签
		gd = new GridData(GridData.VERTICAL_ALIGN_CENTER);
		gd.horizontalIndent = 1;
		Label lblMonthChar = new Label(this, SWT.NONE);
		lblMonthChar.setText(record_viewer_label_month);
		lblMonthChar.setLayoutData(gd);
		// 日Spinner
		gd = new GridData(GridData.VERTICAL_ALIGN_CENTER);
		gd.horizontalIndent = 5;
		Spinner spinDay = new Spinner(this, SWT.READ_ONLY);
		spinDay.setLayoutData(gd);
		spinDay.setMinimum(1);
		spinDay.setMaximum(31);
		spinDay.setSelection(day);
		spinDay.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Spinner spin = (Spinner)e.getSource();
				day = spin.getSelection();
				if(!chkDisplayAll.getSelection())
					refreshRecord();	
			}
		});
		// 日标签
		gd = new GridData(GridData.VERTICAL_ALIGN_CENTER);
		gd.horizontalIndent = 1;
		Label lblDayChar = new Label(this, SWT.NONE);
		lblDayChar.setText(record_viewer_label_day);
		lblDayChar.setLayoutData(gd);
		// 显示所有check box
		gd = new GridData(GridData.VERTICAL_ALIGN_CENTER);
		gd.horizontalIndent = 10;
		chkDisplayAll = new Button(this, SWT.CHECK | SWT.FLAT);
		chkDisplayAll.setText(record_viewer_button_display_all);
		chkDisplayAll.setLayoutData(gd);
		chkDisplayAll.addSelectionListener(
			new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					refreshRecord();
				}
			}
		);
		// 聊天记录表
		waterfall = new Waterfall(this);
		waterfall.setContentProvider(provider.getRecordContentProvider());
		waterfall.setLabelProvider(provider.getRecordLabelProvider());
		waterfall.setSorter(provider.getRecordSorter());
		gd = new GridData(GridData.FILL_BOTH);
		gd.horizontalSpan = layout.numColumns;
		waterfall.setLayoutData(gd);
		// 添加表格的鼠标事件
		waterfall.addMouseListener(
			new MouseAdapter() {
				@Override
				public void mouseUp(MouseEvent e) {
					if((e.stateMask & SWT.BUTTON3) != 0) 
						popupMenu(e);
				}
			}
		);
		waterfall.setInput(this);
		// layout， do it for linux
		layout();
	}
	
	/**
	 * 弹出菜单
	 * @param e
	 */
	private void popupMenu(MouseEvent e) {
		recordMenu.setLocation(waterfall.toDisplay(e.x, e.y));
		recordMenu.setVisible(true);
	}
	
	/**
	 * 设置聊天记录表中的内容，显示的是当前年月日的记录，如果设置了显示
	 * 所有，则显示所有记录
	 * 
	 * @param displayAll 
	 * 		true时表示显示所有
	 */
	public void refreshRecord() {
		if(rm == null)
			return;
		// 清除所有
		List<RecordEntry> list = null;
		// 得到消息列表
		if(chkDisplayAll.getSelection())
			list = rm.getRecord(provider.getId(), IKeyConstants.ALL, IKeyConstants.SUB_ALL);
		else 
			list = rm.getRecords(provider.getId(), IKeyConstants.ALL, IKeyConstants.SUB_ALL, year, month, day, year, month, day + 1);
		// 刷新列表
		filter(list);
		provider.setRecords(list);
		waterfall.refresh();
	}
	
	/**
	 * 过滤消息记录
	 * 
	 * @param list
	 */
	private void filter(List<RecordEntry> list) {
		if(filter == null)
			return;
		for(Iterator<RecordEntry> i = list.iterator(); i.hasNext(); ) {
			if(!filter.select(i.next()))
				i.remove();
		}
	}

	/**
	 * @return Returns the mm.
	 */
	public RecordManager getRecordManager() {
		return rm;
	}
	
	/**
	 * @param mm The mm to set.
	 */
	public void setRecordManager(RecordManager rm) {
		this.rm = rm;
	}
	
	/**
	 * @return 当前选择的项的相关的MessageEntry对象，如果当前没有Item被选，返回null
	 */
	public RecordEntry getSelectedMessageEntry() {
		Object obj = waterfall.getSelection();
		return (obj == null) ? null : ((RecordEntry)obj);
	}
	
	/**
	 * @return
	 * 		返回当前选择的聊天记录内容
	 */
	public String getSelectedText() {
		RecordEntry entry = getSelectedMessageEntry();
		return (entry == null) ? "" : entry.message;
	}
	
	/**
	 * @return
	 * 		当前选择记录的全文，即包括时间和发送者
	 */
	public String getSelectedFullText() {
		RecordEntry entry = getSelectedMessageEntry();
		return provider.getRecordLabelProvider().getText(entry);
	}
	
	/**
	 * 添加TableItem选择事件监听器
	 * 
	 * @param listener
	 */
	public void addSelectionListener(SelectionListener listener) {
		waterfall.addSelectionListener(listener);
	}
	
	/**
	 * 除去TableItem选择事件监听器
	 * 
	 * @param listener
	 */
	public void removeSelectionListener(SelectionListener listener) {
		waterfall.removeSelectionListener(listener);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.events.DisposeListener#widgetDisposed(org.eclipse.swt.events.DisposeEvent)
	 */
	public void widgetDisposed(DisposeEvent e) {
		clipboard.dispose();
	}	
	
	/* (non-Javadoc)
     * @see org.eclipse.swt.widgets.Control#setBackground(org.eclipse.swt.graphics.Color)
     */
    @Override
	public void setBackground(Color color) {
        super.setBackground(color);
        Control[] children = getChildren();
        for(int i = 0; i < children.length; i++) {
            children[i].setBackground(color);
        }
    }

	public IRecordProvider getProvider() {
		return provider;
	}

	public void setProvider(IRecordProvider provider) {
		this.provider = provider;
		waterfall.setContentProvider(provider.getRecordContentProvider());
		waterfall.setLabelProvider(provider.getRecordLabelProvider());
		waterfall.setSorter(provider.getRecordSorter());
	}

	/**
	 * @return Returns the filter.
	 */
	public IRecordEntryFilter getFilter() {
		return filter;
	}

	/**
	 * @param filter The filter to set.
	 */
	public void setFilter(IRecordEntryFilter filter) {
		this.filter = filter;
	}
}
