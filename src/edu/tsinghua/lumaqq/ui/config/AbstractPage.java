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
package edu.tsinghua.lumaqq.ui.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.ViewForm;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

import edu.tsinghua.lumaqq.resource.Colors;

/**
 * 配置页基类，配置页用在一些资料窗口，比如系统设置窗口，个人信息窗口中用于显示一个配置页面
 * 
 * @author luma
 */
public abstract class AbstractPage {
    protected static Log log = LogFactory.getLog(AbstractPage.class);
    
    protected ViewForm vf;
    protected Shell parentShell;
    protected int style;
    
    // 存放脏属性标识，用户需要对每个属性注册一个
    private Map<Integer, Integer> dirtyMap;
    
    /**
     * 创建一个配置页面
     * 
     * @param parent
     * 		父容器
     */
    public AbstractPage(Composite parent) {
        this(parent, 0);
    }
    
    /**
     * 创建一个配置页面
     * 
     * @param parent
     * 		父容器
     * @param style
     * 		样式
     */
    public AbstractPage(Composite parent, int style) {
        parentShell = parent.getShell();
        parentShell.addShellListener(new ShellAdapter() {
            @Override
			public void shellClosed(ShellEvent e) {
                onParentShellClose();
            }
        });
        this.style = style;
        initialVariable();
        initialLayout(parent);
    }
    
    /**
     * 如果page的内容来源于某个model对象，子类可以覆盖这个方法
     * 
     * @param model
     * 		model对象
     */
    public void setModel(Object model) {        
    }
    
    /**
     * 刷新值，缺省实现是调用initializeValues方法，子类如果需要其他的逻辑，可以覆盖这个方法
     */
    public void refreshValues() {
        initializeValues();
    }
    
    /**
     * @return
     * 		content
     */
    protected Composite getContent() {
        return vf;
    }
    
    /**
     * 父窗口关闭时调用这个方法
     */
    protected void onParentShellClose() {        
    }
    
    /**
     * 初始化变量
     */
    protected void initialVariable() {        
    }
    
    /**
     * 初始化页面
     * 
     * @param parent
     * 		父容器
     */
    protected void initialLayout(Composite parent) {
        // 创建view form
        vf = new ViewForm(parent, SWT.FLAT);
        
        // 设置top-left
        CLabel label = new CLabel(vf, SWT.NONE);
        label.setText(getTitle(0));
        label.setImage(getImage());
        label.setForeground(Colors.PAGE_TITLE_FOREGROUND);
        label.setBackground(Colors.PAGE_TITLE_BACKGROUND);
        vf.setTopLeft(label);       
        
        // 创建content
        vf.setContent(createContent(vf));
    }
    
    /**
     * @param data
     * 		这是布局参数
     */
    public void setLayoutData(Object data) {
        vf.setLayoutData(data);
    }
    
    /**
     * 设置form的可见性
     * 
     * @param b
     * 		true为可见
     */
    public void setVisible(boolean b) {
        vf.setVisible(b);
    }
    
    /**
     * @return
     * 		Content的control对象
     */
    protected abstract Control createContent(Composite parent);
    
    /**
     * 保存更改
     */
    protected void save() {
        // 如果没有属性改变，返回
        if(dirtyMap == null || !isDirty()) 
            return;
        
        // 保存所有的脏属性
		for(Integer propertyId : dirtyMap.keySet())
			saveDirtyProperty(propertyId);
        
        // 清空脏列表
        dirtyMap.clear();
    }
    
    /**
     * 保存脏属性
     * 
     * @param propertyId
     * 		属性的ID
     */
    protected abstract void saveDirtyProperty(int propertyId);
    
    /**
     * 把属性ID添加到脏属性列表中
     * 
     * @param propertyId
     * 		属性ID
     */
    protected void makeDirty(int propertyId) {
        if(dirtyMap == null)
            dirtyMap = new HashMap<Integer, Integer>();
        dirtyMap.put(propertyId, propertyId);
    }

    /**
     * 初始化值 
     */
    protected abstract void initializeValues();
    
    /**
     * @return
     * 		页面图标
     */
    protected abstract Image getImage();
    
    /**
     * @param page 
     * @return
     * 		页面标题
     */
    protected abstract String getTitle(int page);
    
    /**
     * @return
     * 		true表示设置已改动
     */
    public boolean isDirty() {
        return !dirtyMap.isEmpty();
    }
}
