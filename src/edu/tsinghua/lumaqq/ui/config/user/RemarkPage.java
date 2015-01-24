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
package edu.tsinghua.lumaqq.ui.config.user;

import static edu.tsinghua.lumaqq.resource.Messages.*;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;

import edu.tsinghua.lumaqq.ecore.remark.Remark;
import edu.tsinghua.lumaqq.ecore.remark.RemarkFactory;
import edu.tsinghua.lumaqq.models.User;
import edu.tsinghua.lumaqq.qq.beans.FriendRemark;
import edu.tsinghua.lumaqq.resource.Colors;
import edu.tsinghua.lumaqq.resource.Resources;
import edu.tsinghua.lumaqq.ui.MainShell;
import edu.tsinghua.lumaqq.ui.config.AbstractPage;
import edu.tsinghua.lumaqq.ui.helper.UITool;
import edu.tsinghua.lumaqq.ui.listener.CenterBorderPaintListener;

/**
 * 备注配置页
 * 
 * @author luma
 */
public class RemarkPage extends AbstractPage {    
    private User model;
    private PaintListener paintListener;
    private MainShell main;
    
    private Button btnDownload, btnUpload;
	private Text textRemarkName, textRemarkZipcode, textRemarkTelephone, textRemarkMobile, textRemarkEmail, textRemarkAddress, textRemarkNote;
	private Button chkAutoUpload;
    
    /**
     * @param parent
     */
    public RemarkPage(Composite parent, MainShell main, User model) {
        super(parent);
        this.main = main;
        this.model = model;
    }
    
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.shells.AbstractPage#initialVariable()
     */
	@Override
    protected void initialVariable() {
        paintListener = new CenterBorderPaintListener(new Class[] { Text.class }, 20, Colors.PAGE_CONTROL_BORDER);
    }
    
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.shells.AbstractPage#setModel(java.lang.Object)
     */
	@Override
    public void setModel(Object model) {
        if(model instanceof User)
            this.model = (User)model;
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.ui.config.AbstractPage#createContent(org.eclipse.swt.widgets.Composite)
     */
	@Override
    protected Control createContent(Composite parent) {
        Composite content = new Composite(parent, SWT.NONE);
        content.setBackground(Colors.PAGE_BACKGROUND);
        content.setLayout(new FormLayout());
        
        Composite container = new Composite(content, SWT.NONE);
        FormData fd = new FormData();
        fd.top = fd.left = new FormAttachment(0, 0);
        fd.bottom = new FormAttachment(100, -50);
        fd.right = new FormAttachment(100, -70);
        container.setLayoutData(fd);
        GridLayout layout = new GridLayout(4, false);
        layout.marginHeight = 14;
        layout.horizontalSpacing = 8;
        layout.verticalSpacing = 14;
        layout.marginWidth = 15;
        container.setLayout(layout);
        container.setBackground(Colors.PAGE_BACKGROUND);
        container.addPaintListener(paintListener);
        
        // 设置使用缺省背景色
        UITool.setDefaultBackground(null);
        
        // 用户名称:
        UITool.createLabel(container, user_info_remark_name);
        textRemarkName = UITool.createSingleText(container, new GridData(GridData.FILL_HORIZONTAL | GridData.VERTICAL_ALIGN_CENTER));
        // 邮政编码:
        UITool.createLabel(container, user_info_remark_zipcode);
        textRemarkZipcode = UITool.createSingleText(container, new GridData(GridData.FILL_HORIZONTAL | GridData.VERTICAL_ALIGN_CENTER));
        // 常用电话:
        UITool.createLabel(container, user_info_remark_telephone);
        textRemarkTelephone = UITool.createSingleText(container, new GridData(GridData.FILL_HORIZONTAL | GridData.VERTICAL_ALIGN_CENTER));
        // 手机号码:
        UITool.createLabel(container, user_info_remark_mobile);
        textRemarkMobile = UITool.createSingleText(container, new GridData(GridData.FILL_HORIZONTAL | GridData.VERTICAL_ALIGN_CENTER));
        // 电子邮箱:
        UITool.createLabel(container, user_info_remark_email);
        GridData gd = new GridData(GridData.FILL_HORIZONTAL | GridData.VERTICAL_ALIGN_CENTER);
        gd.horizontalSpan = 3;
        textRemarkEmail = UITool.createSingleText(container, gd);
        // 联系地址:
        UITool.createLabel(container, user_info_remark_address);
        gd = new GridData(GridData.FILL_HORIZONTAL | GridData.VERTICAL_ALIGN_CENTER);
        gd.horizontalSpan = 3;
        textRemarkAddress = UITool.createSingleText(container, gd);
        // 备注说明:
        UITool.createLabel(container, user_info_remark_note, new GridData(GridData.VERTICAL_ALIGN_BEGINNING));
        gd = new GridData(GridData.FILL_BOTH);
        gd.horizontalSpan = 3;
        textRemarkNote = UITool.createSingleText(container, gd);
        
        // 上传下载组
        gd = new GridData(GridData.FILL_HORIZONTAL);
        gd.horizontalSpan = 4;
        layout = new GridLayout(3, false);
        layout.marginHeight = layout.marginWidth = layout.horizontalSpacing = 8;
        Group updownGroup = UITool.createGroup(container, user_info_group_up_down, gd, layout);
        
        // 上传备注
        btnUpload = UITool.createButton(updownGroup, user_info_remark_button_upload);
        btnUpload.addSelectionListener(new SelectionAdapter() {
            @Override
			public void widgetSelected(SelectionEvent e) {
                uploadFriendRemark();
            }
        });
        // 下载备注
        btnDownload = UITool.createButton(updownGroup, user_info_remark_button_download);
        btnDownload.addSelectionListener(new SelectionAdapter() {
            @Override
			public void widgetSelected(SelectionEvent e) {
				btnDownload.setEnabled(false);
				main.getClient().user_GetRemark(model.qq);
            }
        });
        // 自动上传备注
        chkAutoUpload = UITool.createCheckbox(updownGroup, user_info_remark_auto_upload);
        chkAutoUpload.setSelection(true);        
        
        return content;
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.shells.AbstractPage#saveDirtyProperty(int)
     */
	@Override
    protected void saveDirtyProperty(int propertyId) {
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.shells.AbstractPage#initializeValues()
     */
	@Override
    protected void initializeValues() {
		Remark remark = main.getConfigHelper().getRemark(model.qq);
		if(remark != null) {
			setRemarkInfo(remark);
		}
    }
    
	/**
	 * 设置各文本框的内容，remark不能为null，也不做检查
	 * @param remark
	 */
	protected void setRemarkInfo(Remark remark) {
		textRemarkName.setText(remark.getName());
		textRemarkZipcode.setText(remark.getZipcode());
		textRemarkTelephone.setText(remark.getTelephone());
		textRemarkMobile.setText(remark.getMobile());
		textRemarkEmail.setText(remark.getEmail());
		textRemarkAddress.setText(remark.getAddress());
		textRemarkNote.setText(remark.getNote());
	}
	
	/**
	 * 设置下载备注按钮的使能状态
	 * 
	 * @param b
	 */
	protected void setDownloadRemarkEnable(boolean b) {
	    btnDownload.setEnabled(b);
	}
	
	/**
	 * 设置上传备注按钮的使能状态
	 * 
	 * @param b
	 */
	protected void setUploadRemarkEnable(boolean b) {
	    btnUpload.setEnabled(b);
	}

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.shells.AbstractPage#getImage()
     */
	@Override
    protected Image getImage() {
        return Resources.getInstance().getImage(Resources.icoViewPersonInfo24);
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.shells.AbstractPage#getTitle()
     */
	@Override
    protected String getTitle(int page) {
        return user_info_page_remark;
    }
    
	/**
	 * 上传好友备注信息，上传的同时会保存到xml中
	 */
	protected void uploadFriendRemark() {
	    setUploadRemarkEnable(false);
		Remark remark = createRemarkElement();
		FriendRemark fr = createFriendRemarkObject(remark);
		// 保存到xml
		saveToXML(remark);
		// 上传
		main.getClient().user_UploadRemark(model.qq, fr);
	}
	
	/**
	 * 创建FriendRemark对象
	 * 
	 * @param remark
	 * 		Remark元素对象
	 * @return
	 * 		FriendRemark对象
	 */
	protected FriendRemark createFriendRemarkObject(Remark remark) {
		FriendRemark fr = new FriendRemark();
		fr.name = remark.getName();
		fr.zipcode = remark.getZipcode();
		fr.telephone = remark.getTelephone();
		fr.mobile = remark.getMobile();
		fr.email = remark.getEmail();
		fr.address = remark.getAddress();
		fr.note = remark.getNote();		
		return fr;
	}
	
	/**
	 * 创建Remark元素对象
	 */
	protected Remark createRemarkElement() {
		// 创建Remark元素对象
		Remark remark = RemarkFactory.eINSTANCE.createRemark();
		remark.setName(textRemarkName.getText());
		remark.setZipcode(textRemarkZipcode.getText());
		remark.setTelephone(textRemarkTelephone.getText());
		remark.setMobile(textRemarkMobile.getText());
		remark.setEmail(textRemarkEmail.getText());
		remark.setAddress(textRemarkAddress.getText());
		remark.setNote(textRemarkNote.getText());
		remark.setQq(model.qq);
		return remark;
	}
	
	/**
	 * 保存备注
	 */
	public void doSave() {
		// 保存到xml
		Remark remark = createRemarkElement();
		saveToXML(remark);
		// 上传
		if(chkAutoUpload.getSelection()) {
		    setUploadRemarkEnable(false);
			FriendRemark fr = createFriendRemarkObject(remark);
			main.getClient().user_UploadRemark(model.qq, fr);
		}
	}
	
	/**
	 * 保存备注信息到xml文件
	 * 
	 * @param remark2
	 * 			Remark
	 */
	@SuppressWarnings("unchecked")
	protected void saveToXML(final Remark remark) {
	    // 保存备注
		main.getConfigHelper().getRemarks().getRemark().add(remark);
		main.getConfigHelper().saveRemarks();
		// 修改model的realName属性，如果现在好友列表正处于显示备注名称模式，则还要修改name属性
		main.getDisplay().syncExec(new Runnable() {
		    public void run() {
		    	model.remark = remark;
				if(!main.getOptionHelper().isShowNick()) {
					model.displayName = remark.getName();
					main.getBlindHelper().refreshModel(model);
				}
			}
		});
	}
}
