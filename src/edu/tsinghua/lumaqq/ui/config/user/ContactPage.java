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
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.PaintListener;
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

import edu.tsinghua.lumaqq.models.User;
import edu.tsinghua.lumaqq.qq.QQ;
import edu.tsinghua.lumaqq.qq.beans.ContactInfo;
import edu.tsinghua.lumaqq.qq.packets.BasicOutPacket;
import edu.tsinghua.lumaqq.qq.packets.out.ModifyInfoPacket;
import edu.tsinghua.lumaqq.resource.Colors;
import edu.tsinghua.lumaqq.resource.Resources;
import edu.tsinghua.lumaqq.ui.config.AbstractPage;
import edu.tsinghua.lumaqq.ui.config.IPacketFiller;
import edu.tsinghua.lumaqq.ui.helper.BeanHelper;
import edu.tsinghua.lumaqq.ui.helper.UITool;
import edu.tsinghua.lumaqq.ui.listener.CenterBorderPaintListener;

/**
 * 联系地址配置页
 * 
 * @author luma
 */
public class ContactPage extends AbstractPage implements IPacketFiller {    
    private User model;
    private PaintListener paintListener;
    
    private Text textCountry, textCity, textZipcode, textEmail, textAddress, textMobile, textTelephone;
	private Button radioOpen, radioOnlyFriend, radioClose;
	private CCombo comboProvince;
    
    /**
     * @param parent
     */
    public ContactPage(Composite parent, User model, int style) {
        super(parent, style);
        this.model = model;
    }
    
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.shells.AbstractPage#initialVariable()
     */
	@Override
    protected void initialVariable() {
        paintListener = new CenterBorderPaintListener(new Class[] { Text.class, CCombo.class }, 20, Colors.PAGE_CONTROL_BORDER);
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
        
        // 设置使用缺省背景色
        UITool.setDefaultBackground(null);
        
        Composite container = new Composite(content, SWT.NONE);
        FormData fd = new FormData();
        fd.top = fd.left = new FormAttachment(0, 0);
        fd.right = new FormAttachment(100, -50);
        container.setLayoutData(fd);
        GridLayout layout = new GridLayout();
        layout.marginHeight = layout.marginWidth = layout.horizontalSpacing = layout.verticalSpacing = 0;
        container.setLayout(layout);
        container.setBackground(Colors.PAGE_BACKGROUND);
        
        GridData gd = new GridData();
        gd.widthHint = 250;
        layout = new GridLayout(2, false);
        layout.marginHeight = layout.horizontalSpacing = 8;
        layout.verticalSpacing = 14;
        layout.marginWidth = 15;
        Composite c = UITool.createContainer(container, gd, layout);
        c.addPaintListener(paintListener);
        // 国家/地区
        UITool.createLabel(c, user_info_contact_country);
        textCountry = UITool.createSingleText(c, new GridData(GridData.VERTICAL_ALIGN_CENTER | GridData.FILL_HORIZONTAL));
        textCountry.setTextLimit(17);
		// 省份
		UITool.createLabel(c, user_info_contact_province);
        comboProvince = UITool.createCCombo(c, new GridData(GridData.VERTICAL_ALIGN_CENTER | GridData.FILL_HORIZONTAL), SWT.FLAT);
        comboProvince.setTextLimit(18);
        comboProvince.setVisibleItemCount(18);        
        for(int i = 2; i < BeanHelper.PROVINCE.length; i++)
            comboProvince.add(BeanHelper.PROVINCE[i]);
        // 城市
        UITool.createLabel(c, user_info_contact_city);
        textCity = UITool.createSingleText(c, new GridData(GridData.VERTICAL_ALIGN_CENTER | GridData.FILL_HORIZONTAL));
        textCity.setTextLimit(8);
        // 邮政编码
        UITool.createLabel(c, user_info_contact_zipcode);
        textZipcode = UITool.createSingleText(c, new GridData(GridData.VERTICAL_ALIGN_CENTER | GridData.FILL_HORIZONTAL));
        textZipcode.setTextLimit(10);
        
        layout = new GridLayout();
        layout.marginHeight = layout.marginWidth = 8;
        c = UITool.createContainer(container, new GridData(GridData.FILL_HORIZONTAL), layout);
        // 私人联系方式组
        layout = new GridLayout(4, false);
        layout.marginHeight = 8;
        layout.marginWidth = 10;
        layout.verticalSpacing = 14;
        gd = new GridData(GridData.FILL_BOTH);
        gd.horizontalSpan = 2;
        Group contactGroup = UITool.createGroup(c, user_info_group_contact, gd, layout);
        contactGroup.addPaintListener(paintListener);
        
        // 以下资料
        UITool.createLabel(contactGroup, user_info_contact_opencontact_label);
        // 完全公开
        radioOpen = UITool.createRadio(contactGroup, user_info_contact_opencontact_open);
        // 仅好友可见
        radioOnlyFriend = UITool.createRadio(contactGroup, user_info_contact_opencontact_onlyfriend);
        // 完全保密
        radioClose = UITool.createRadio(contactGroup, user_info_contact_opencontact_close);
        
        // 电子邮件
        UITool.createLabel(contactGroup, user_info_contact_email);
        gd = new GridData(GridData.VERTICAL_ALIGN_CENTER);
        gd.widthHint = 150;
        gd.horizontalSpan = 3;
        textEmail = UITool.createSingleText(contactGroup, gd);
        // 联系地址
        UITool.createLabel(contactGroup, user_info_contact_address);
        gd = new GridData(GridData.VERTICAL_ALIGN_CENTER);
        gd.widthHint = 150;
        gd.horizontalSpan = 3;
        textAddress = UITool.createSingleText(contactGroup, gd);
        // 手机号码
        UITool.createLabel(contactGroup, user_info_contact_mobile);
        gd = new GridData(GridData.VERTICAL_ALIGN_CENTER);
        gd.widthHint = 150;
        gd.horizontalSpan = 3;
        textMobile = UITool.createSingleText(contactGroup, gd);
        // 电话号码
        UITool.createLabel(contactGroup, user_info_contact_telephone);
        gd = new GridData(GridData.VERTICAL_ALIGN_CENTER);
        gd.widthHint = 150;
        gd.horizontalSpan = 3;
        textTelephone = UITool.createSingleText(contactGroup, gd);
        
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
		ContactInfo info = model.info;
		if(info == null)
		    return;
		// 国家/地区
		textCountry.setText(info.country);
		// 省份
		comboProvince.setText(info.province);
		// 城市
		textCity.setText(info.city);
		// email
		textEmail.setText(info.email);
		// 地址
		textAddress.setText(info.address);
		// 邮政编码
		textZipcode.setText(info.zipcode);
		// 电话
		textTelephone.setText(info.telephone);
		// 通讯信息可见性
		if(info.openContact == QQ.QQ_CONTACT_OPEN) {
			radioOpen.setSelection(true);
			radioOnlyFriend.setSelection(false);
			radioClose.setSelection(false);
		} else if(info.openContact == QQ.QQ_CONTACT_ONLY_FRIENDS) {
			radioOpen.setSelection(false);
			radioOnlyFriend.setSelection(true);
			radioClose.setSelection(false);
		} else {
			radioOpen.setSelection(false);
			radioOnlyFriend.setSelection(false);
			radioClose.setSelection(true);			
		}
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.shells.AbstractPage#getImage()
     */
	@Override
    protected Image getImage() {
        if(isEditable())
            return Resources.getInstance().getImage(Resources.icoModifyPersonInfo24);
        else 
            return Resources.getInstance().getImage(Resources.icoViewPersonInfo24);
    }
	
	private boolean isEditable() {
		return (style & UserInfoWindow.EDITABLE) != 0;
	}

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.shells.AbstractPage#getTitle()
     */
	@Override
    protected String getTitle(int page) {
        return user_info_page_contact;
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.shells.IPacketFiller#fill(edu.tsinghua.lumaqq.qq.packets.OutPacket)
     */
    public void fill(BasicOutPacket packet) {
        if(packet instanceof ModifyInfoPacket) {
            ContactInfo info = ((ModifyInfoPacket)packet).getContactInfo();
            info.country = textCountry.getText();
            info.province = comboProvince.getText();
            info.city = textCity.getText();
            info.zipcode = textZipcode.getText();
    		info.email = textEmail.getText();
    		info.address = textAddress.getText();
    		info.telephone = textTelephone.getText();
    		info.mobile = textMobile.getText();
    		if(radioOpen.getSelection())
    			info.openContact = QQ.QQ_CONTACT_OPEN;
    		else if(radioOnlyFriend.getSelection())
    			info.openContact = QQ.QQ_CONTACT_ONLY_FRIENDS;
    		else
    			info.openContact = QQ.QQ_CONTACT_CLOSE;
        }
    }
}
