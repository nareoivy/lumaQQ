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

import edu.tsinghua.lumaqq.models.User;
import edu.tsinghua.lumaqq.qq.beans.Card;
import edu.tsinghua.lumaqq.qq.packets.BasicOutPacket;
import edu.tsinghua.lumaqq.qq.packets.out.ClusterModifyCardPacket;
import edu.tsinghua.lumaqq.resource.Colors;
import edu.tsinghua.lumaqq.resource.Resources;
import edu.tsinghua.lumaqq.ui.config.AbstractPage;
import edu.tsinghua.lumaqq.ui.config.IPacketFiller;
import edu.tsinghua.lumaqq.ui.helper.UITool;
import edu.tsinghua.lumaqq.ui.listener.CenterBorderPaintListener;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;

public class CardPage extends AbstractPage implements IPacketFiller {
	private User model;
    private PaintListener paintListener;
    private Text textName, textPhone, textEmail, textRemark;
    private CCombo comboGender;
	
	public CardPage(Composite parent, User model, int style) {
        super(parent, style);
        this.model = model;
	}

	private boolean isEditable() {
		return (style & UserInfoWindow.EDITABLE) != 0;
	}
	
	@Override
	protected void initialVariable() {
		paintListener = new CenterBorderPaintListener(new Class[] { Text.class, CCombo.class }, 20, Colors.PAGE_CONTROL_BORDER);
	}
	
	@Override
	protected Control createContent(Composite parent) {
        Composite content = new Composite(parent, SWT.NONE);
        content.setBackground(Colors.PAGE_BACKGROUND);
        content.setLayout(new FormLayout());
        
        // 设置使用缺省背景色
        UITool.setDefaultBackground(null);        
		boolean enable = isEditable();
		
        Composite c = new Composite(content, SWT.NONE);
        FormData fd = new FormData();
        fd.top = fd.left = new FormAttachment(0, 0);
        fd.bottom = fd.right = new FormAttachment(100, -50);
        c.setLayoutData(fd);
        GridLayout layout = new GridLayout(2, false);
        layout.marginHeight = layout.horizontalSpacing = 8;
        layout.verticalSpacing = 14;
        layout.marginWidth = 15;
        c.setLayout(layout);
        c.setBackground(Colors.PAGE_BACKGROUND);
        c.addPaintListener(paintListener);
        // 姓名
        UITool.createLabel(c, user_info_card_name);
        textName = UITool.createSingleText(c, new GridData(GridData.FILL_HORIZONTAL));
		textName.setEnabled(enable);
		if(!enable)
			textName.setBackground(Colors.PAGE_READONLY_CONTROL_BACKGROUND);
        // 性别
        UITool.createLabel(c, user_info_card_gender);
        comboGender = UITool.createCCombo(c, new GridData(GridData.FILL_HORIZONTAL));
        comboGender.add(gender_gg);
        comboGender.add(gender_mm);
        comboGender.add("-");
		comboGender.setEnabled(enable);
		if(!enable)
			comboGender.setBackground(Colors.PAGE_READONLY_CONTROL_BACKGROUND);
        // 电话
        UITool.createLabel(c, user_info_card_phone);
        textPhone = UITool.createSingleText(c, new GridData(GridData.FILL_HORIZONTAL));
		textPhone.setEnabled(enable);
		if(!enable)
			textPhone.setBackground(Colors.PAGE_READONLY_CONTROL_BACKGROUND);
        // email
        UITool.createLabel(c, user_info_card_email);
        textEmail = UITool.createSingleText(c, new GridData(GridData.FILL_HORIZONTAL));
		textEmail.setEnabled(enable);
		if(!enable)
			textEmail.setBackground(Colors.PAGE_READONLY_CONTROL_BACKGROUND);
        // 备注
        UITool.createLabel(c, user_info_card_remark, new GridData(GridData.VERTICAL_ALIGN_BEGINNING));
        textRemark = UITool.createMultiText(c, new GridData(GridData.FILL_BOTH));
		textRemark.setEnabled(enable);
		if(!enable)
			textRemark.setBackground(Colors.PAGE_READONLY_CONTROL_BACKGROUND);
		
        return content;
	}

	@Override
	protected void saveDirtyProperty(int propertyId) {
	}
	
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.shells.AbstractPage#setModel(java.lang.Object)
     */
	@Override
    public void setModel(Object model) {
        if(model instanceof User)
            this.model = (User)model;
    }

	@Override
	protected void initializeValues() {
		textName.setText(model.cardName);
		textPhone.setText(model.cardPhone);
		textEmail.setText(model.cardEmail);
		textRemark.setText(model.cardRemark);
		comboGender.select(model.cardGenderIndex);
	}

	@Override
	protected Image getImage() {
        if(isEditable())
            return Resources.getInstance().getImage(Resources.icoModifyPersonInfo24);
        else 
            return Resources.getInstance().getImage(Resources.icoViewPersonInfo24);
	}

	@Override
	protected String getTitle(int page) {
		return user_info_page_card;
	}

	/* (non-Javadoc)
	 * @see edu.tsinghua.lumaqq.ui.config.IPacketFiller#fill(edu.tsinghua.lumaqq.qq.packets.BasicOutPacket)
	 */
	public void fill(BasicOutPacket p) {
		if(p instanceof ClusterModifyCardPacket) {
			ClusterModifyCardPacket packet = (ClusterModifyCardPacket)p;
			Card card = new Card();
			card.name = textName.getText();
			card.email = textEmail.getText();
			card.phone = textPhone.getText();
			card.remark = textRemark.getText();
			card.genderIndex = comboGender.getSelectionIndex();
			packet.setCard(card);
			packet.setClusterId(model.cluster.clusterId);
		}
	}
}
