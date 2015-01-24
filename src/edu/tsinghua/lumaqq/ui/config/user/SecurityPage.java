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
import static org.apache.commons.codec.digest.DigestUtils.md5;

import java.io.File;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
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

import edu.tsinghua.lumaqq.LumaQQ;
import edu.tsinghua.lumaqq.ecore.login.Login;
import edu.tsinghua.lumaqq.ecore.login.Logins;
import edu.tsinghua.lumaqq.eutil.LoginUtil;
import edu.tsinghua.lumaqq.models.User;
import edu.tsinghua.lumaqq.qq.QQ;
import edu.tsinghua.lumaqq.qq.beans.ContactInfo;
import edu.tsinghua.lumaqq.qq.packets.BasicOutPacket;
import edu.tsinghua.lumaqq.qq.packets.out.ModifyInfoPacket;
import edu.tsinghua.lumaqq.resource.Colors;
import edu.tsinghua.lumaqq.resource.Resources;
import edu.tsinghua.lumaqq.ui.config.AbstractPage;
import edu.tsinghua.lumaqq.ui.config.IPacketFiller;
import edu.tsinghua.lumaqq.ui.helper.UITool;
import edu.tsinghua.lumaqq.ui.listener.CenterBorderPaintListener;

/**
 * 网络安全配置页
 * 
 * @author luma
 */
public class SecurityPage extends AbstractPage implements IPacketFiller {    
    private User model;
    private PaintListener paintListener;
    
	private Button radioAnyOne, radioAuth, radioNever, chkChangePassword;
	private Text textOldPassword, textNewPassword, textConfirmPassword;
    
    /**
     * @param parent
     */
    public SecurityPage(Composite parent, User model) {
        super(parent);
        this.model = model;
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
     * @see edu.tsinghua.lumaqq.shells.AbstractPage#initialVariable()
     */
	@Override
    protected void initialVariable() {
        paintListener = new CenterBorderPaintListener(new Class[] { Text.class }, 20, Colors.PAGE_CONTROL_BORDER);
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
        fd.bottom = new FormAttachment(100, -30);
        fd.right = new FormAttachment(100, -100);
        container.setLayoutData(fd);
        GridLayout layout = new GridLayout(3, false);
        layout.marginHeight = layout.horizontalSpacing = 8;
        layout.verticalSpacing = 14;
        layout.marginWidth = 15;
        container.setLayout(layout);
        container.setBackground(Colors.PAGE_BACKGROUND);
        container.addPaintListener(paintListener);
        
        // 设置使用缺省背景色
        UITool.setDefaultBackground(null);
        
        // 修改密码
        GridData gd = new GridData();
        gd.horizontalSpan = 3;
        chkChangePassword = UITool.createCheckbox(container, user_info_security_changepassword, gd);
        chkChangePassword.addSelectionListener(new SelectionAdapter() {
            @Override
			public void widgetSelected(SelectionEvent e) {
                refreshControlStatus();                
            }
        });
        // 旧密码
        gd = new GridData();
        gd.horizontalIndent = 20;
        UITool.createLabel(container, user_info_security_oldpassword, gd);
        textOldPassword = UITool.createSingleText(container, new GridData(GridData.FILL_HORIZONTAL | GridData.VERTICAL_ALIGN_CENTER), SWT.PASSWORD | SWT.SINGLE);
        textOldPassword.setTextLimit(16);
        UITool.createLabel(container, user_info_security_changepassword_hint);
        // 新密码
        gd = new GridData();
        gd.horizontalIndent = 20;
        UITool.createLabel(container, user_info_security_newpassword, gd);
        textNewPassword = UITool.createSingleText(container, new GridData(GridData.FILL_HORIZONTAL | GridData.VERTICAL_ALIGN_CENTER), SWT.PASSWORD | SWT.SINGLE);
        textNewPassword.setTextLimit(16);
        UITool.createLabel(container, user_info_security_changepassword_hint);
        // 新密码确认
        gd = new GridData();
        gd.horizontalIndent = 20;
        UITool.createLabel(container, user_info_security_confirmpassword, gd);
        textConfirmPassword = UITool.createSingleText(container, new GridData(GridData.FILL_HORIZONTAL | GridData.VERTICAL_ALIGN_CENTER), SWT.PASSWORD | SWT.SINGLE);
        textConfirmPassword.setTextLimit(16);
        UITool.createLabel(container, "");
        
        // 身份验证组
        layout = new GridLayout();
        layout.marginHeight = layout.marginWidth = 8;
        gd = new GridData(GridData.FILL_HORIZONTAL);
        gd.horizontalSpan = 3;
        Group authGroup = UITool.createGroup(container, user_info_group_auth, gd, layout);
        
        // 允许任何人加
        radioAnyOne = UITool.createRadio(authGroup, user_info_security_openhp_anyone);
        radioAuth = UITool.createRadio(authGroup, user_info_security_openhp_auth);
        radioNever = UITool.createRadio(authGroup, user_info_security_openhp_never);
        
        return content;
    }
    
    /**
     * 刷新密码框状态
     */
    private void refreshControlStatus() {
        boolean enable = chkChangePassword.getSelection();
        textOldPassword.setEnabled(enable);
        textNewPassword.setEnabled(enable);
        textConfirmPassword.setEnabled(enable);
        if(enable) {
            textOldPassword.setBackground(Colors.PAGE_BACKGROUND);
            textNewPassword.setBackground(Colors.PAGE_BACKGROUND);
            textConfirmPassword.setBackground(Colors.PAGE_BACKGROUND);
        } else {
            textOldPassword.setBackground(Colors.PAGE_READONLY_CONTROL_BACKGROUND);
            textNewPassword.setBackground(Colors.PAGE_READONLY_CONTROL_BACKGROUND);
            textConfirmPassword.setBackground(Colors.PAGE_READONLY_CONTROL_BACKGROUND);            
        }
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
		// 好友认证设置
		switch(info.authType) {
			case QQ.QQ_AUTH_NO:
				radioAnyOne.setSelection(true);
				radioAuth.setSelection(false);
				radioNever.setSelection(false);
				break;
			case QQ.QQ_AUTH_NEED:
				radioAnyOne.setSelection(false);			
				radioAuth.setSelection(true);
				radioNever.setSelection(false);			
				break;
			default:
				radioAnyOne.setSelection(false);			
				radioAuth.setSelection(false);			
				radioNever.setSelection(true);
				break;
		}
		chkChangePassword.setSelection(false);
		refreshControlStatus();
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.shells.AbstractPage#getImage()
     */
	@Override
    protected Image getImage() {
        return Resources.getInstance().getImage(Resources.icoModifyPersonInfo24);
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.shells.AbstractPage#getTitle()
     */
	@Override
    protected String getTitle(int page) {
        return user_info_page_security;
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.shells.IPacketFiller#fill(edu.tsinghua.lumaqq.qq.packets.OutPacket)
     */
    public void fill(BasicOutPacket packet) {
        if(packet instanceof ModifyInfoPacket) {
            ModifyInfoPacket mip = (ModifyInfoPacket)packet;
            ContactInfo info = mip.getContactInfo();
            if(chkChangePassword.getSelection()) {
                mip.setOldPassword(textOldPassword.getText());
                mip.setNewPassword(textNewPassword.getText());
            } else {
                mip.setOldPassword(null);
                mip.setNewPassword(null);
            }
    		if(radioAnyOne.getSelection())
    			info.authType = QQ.QQ_AUTH_NO;
    		else if(radioAuth.getSelection())
    			info.authType = QQ.QQ_AUTH_NEED;
    		else
    			info.authType = QQ.QQ_AUTH_REJECT;
        }
    }

	/**
	 * 检查新密码
	 * 
	 * @return
	 * 		新密码输入无误返回true，否则返回false
	 */
	protected boolean checkNewPassword() {
		if(chkChangePassword.getSelection())
			return textNewPassword.getText().equals(textConfirmPassword.getText());
		else
			return true;
	}
	
	/**
	 * 检查旧密码
	 * 
	 * @return
	 * 		输入正确返回true，错误返回false
	 */
	@SuppressWarnings("unchecked")
	protected boolean checkOldPassword() {
		// 加密消息
		if(chkChangePassword.getSelection()) {
			Logins logins = LoginUtil.load(new File(LumaQQ.LOGIN_HISTORY));
			if(logins == null) {
				log.error("无法读取登陆历史信息文件");
				return false;
			}
			List<Login> list = logins.getLogin();
			for(Login login : list) {
				if(login.getQq().equals(String.valueOf(model.qq))) {
					// 哈，这里就是要人看着晕～
					String s = "";
					Base64 codec = new Base64();
					if(login.isRememberPassword()) 
						s = new String(codec.encode(md5(md5(textOldPassword.getText().getBytes()))));
					else
						s = new String(codec.encode(md5(new String(codec.encode(md5(md5(textOldPassword.getText().getBytes())))).getBytes())));
					if(login.getPassword().equals(s))
						return true;
					else
						return false;
				}
			}
			return false;
		} else
			return true;
	}
}
