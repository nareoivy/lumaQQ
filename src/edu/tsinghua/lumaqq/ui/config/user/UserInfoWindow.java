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
import java.util.Iterator;

import org.apache.commons.codec.binary.Base64;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;

import edu.tsinghua.lumaqq.LumaQQ;
import edu.tsinghua.lumaqq.ecore.login.Login;
import edu.tsinghua.lumaqq.ecore.login.Logins;
import edu.tsinghua.lumaqq.ecore.remark.Remark;
import edu.tsinghua.lumaqq.ecore.remark.RemarkFactory;
import edu.tsinghua.lumaqq.eutil.LoginUtil;
import edu.tsinghua.lumaqq.models.User;
import edu.tsinghua.lumaqq.qq.QQ;
import edu.tsinghua.lumaqq.qq.beans.Card;
import edu.tsinghua.lumaqq.qq.beans.ContactInfo;
import edu.tsinghua.lumaqq.qq.beans.FriendRemark;
import edu.tsinghua.lumaqq.qq.events.QQEvent;
import edu.tsinghua.lumaqq.qq.packets.in.ClusterCommandReplyPacket;
import edu.tsinghua.lumaqq.qq.packets.in.FriendDataOpReplyPacket;
import edu.tsinghua.lumaqq.qq.packets.in.SignatureOpReplyPacket;
import edu.tsinghua.lumaqq.qq.packets.out.ClusterModifyCardPacket;
import edu.tsinghua.lumaqq.qq.packets.out.FriendDataOpPacket;
import edu.tsinghua.lumaqq.qq.packets.out.ModifyInfoPacket;
import edu.tsinghua.lumaqq.resource.Resources;
import edu.tsinghua.lumaqq.ui.MainShell;
import edu.tsinghua.lumaqq.ui.config.AbstractConfigurationWindow;
import edu.tsinghua.lumaqq.ui.config.AbstractPage;
import edu.tsinghua.lumaqq.ui.config.IPacketFiller;

/**
 * 用户信息窗口
 * 
 * @author luma
 */
public class UserInfoWindow extends AbstractConfigurationWindow {
    private MainShell main;
    private User model;
    private ModifyInfoPacket mip;
    private ClusterModifyCardPacket cmcp;
    
    // true表示群名片已经自动请求过
    private boolean cardUpdated;
    
    // true表示个性签名要修改
    private boolean modifySignature;
    // true表示其他信息已经修改成功
    private boolean infoModified;
    private boolean sigModified;
    private char sigSequence;
    
    // style
    /** 用户资料可以编辑，对应于修改个人设置窗口 */
    public static final int EDITABLE = 1;
    /** 用户资料只读，对应于查看好友资料窗口 */
    public static final int READ_ONLY = 2;
    
    // 页ID
    public static final int PERSONAL_INFO = 0;
    public static final int QQ_SHOW = 1;
    @SuppressWarnings("unused")
	public static final int CONTACT = 2;
    public static final int SECURITY = 3;
    public static final int REMARK = 3;
    public static final int CARD = 4;
    
    /**
     * @param parent
     */
    public UserInfoWindow(MainShell main, User model, int style) {
        super(main.getShell(), style);
        this.model = model;
        this.main = main;
        cardUpdated = false;
        modifySignature = false;
        setOKButtonText(button_modify);
    }
    
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.shells.AbstractConfigurationWindow#initialPages()
     */
	@Override
    protected void initialPages() {
        addPage(new PersonalPage(getPageContainer(), model, style));        
        addPage(new QQShowPage(getPageContainer(), main, model, style));
        addPage(new ContactPage(getPageContainer(), model, style));      
        if(isEditable())
            addPage(new SecurityPage(getPageContainer(), model));
        if(isReadOnly())
            addPage(new RemarkPage(getPageContainer(), main, model));
        if(hasCluster())
        	addPage(new CardPage(getPageContainer(), model, style));
        
        main.getClient().addQQListener(this);
    }
	
	/**
	 * @return
	 * 		true表示这个用户在一个群中
	 */
	private boolean hasCluster() {
		return model.group != null && model.group.isCluster();
	}
	
	private boolean isEditable() {
		return (style & EDITABLE) != 0;
	}
	
	private boolean isReadOnly() {
		return (style & READ_ONLY) != 0;
	}
    
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.shells.AbstractConfigurationWindow#onOK()
     */
	@Override
    protected void onOK() {
        if(isEditable()) {
        	setOKButtonEnabled(false);
        	
        	if(getCurrentPageId() == CARD) {
        		cmcp = new ClusterModifyCardPacket(main.getClient().getUser());
        		Iterator<AbstractPage> i = getPageIterator();
        		while(i.hasNext()) {
        			AbstractPage page = i.next();
        			if(page instanceof IPacketFiller)
        				((IPacketFiller)page).fill(cmcp);
        		}
        		
        		main.getClient().sendPacket(cmcp);
        	} else {        		
        		// 检查旧密码是否输入正确
        		if(!((SecurityPage)getPage(SECURITY)).checkOldPassword()) {
        			MessageDialog.openError(parentShell, message_box_change_password_title, error_old_password_wrong);
        			setOKButtonEnabled(true);
        			return;
        		}
        		
        		// 检查新密码是否输入正确
        		if(!((SecurityPage)getPage(SECURITY)).checkNewPassword()) {
        			MessageDialog.openError(parentShell, message_box_change_password_title, error_two_password_differ);
        			setOKButtonEnabled(true);
        			return;
        		}
        		
        		// 组装包
        		mip = new ModifyInfoPacket(main.getClient().getUser());
        		mip.setContactInfo(new ContactInfo());
        		Iterator<AbstractPage> i = getPageIterator();
        		while(i.hasNext()) {
        			AbstractPage page = i.next();
        			if(page instanceof IPacketFiller)
        				((IPacketFiller)page).fill(mip);
        		}
        		
        		// 检查是否需要修改个性签名
        		PersonalPage pp = (PersonalPage)getPage(PERSONAL_INFO);
        		modifySignature = pp.isSignatureModified();
        		if(modifySignature) {
        			if(pp.getSignature().equals(""))
        				sigSequence = main.getClient().user_DeleteSignature();
        			else
        				sigSequence = main.getClient().user_ModifySignature(pp.getSignature());
        		}
        		
        		// 发送包
        		main.getClient().sendPacket(mip);       
        	}
        } else {
        	switch(getCurrentPageId()) {
        		case REMARK:
        			RemarkPage page = (RemarkPage)getPage(REMARK);
        			page.doSave();
        			break;
        		case CARD:
        			main.getClient().cluster_GetCard(model.cluster.clusterId, model.qq);
        			break;
        		default:
        			main.getClient().user_GetInfo(model.qq);
        			break;
        	}
        }
    }
    
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.shells.AbstractConfigurationWindow#pageChanged()
     */
	@Override
    protected void pageChanged() {		
        if(isReadOnly()) {
        	switch(getCurrentPageId()) {
        		case REMARK:
        			setOKButtonText(button_modify);
        			break;
        		default:
        			setOKButtonText(button_update);
        			break;        			
        	}
        } 
        
        if(cardUpdated == false && getCurrentPageId() == CARD) {
        	main.getClient().cluster_GetCard(model.cluster.clusterId, model.qq);
        	cardUpdated = true;
        }
    }
    
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.shells.AbstractConfigurationWindow#onShellClose()
     */
	@Override
    protected void onShellClose() {
        main.getShellRegistry().removeUserInfoWindow(model);
        main.getClient().removeQQListener(this);
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.shells.AbstractConfigurationWindow#getTitle()
     */
	@Override
    protected String getTitle() {
        return isEditable() ? user_info_title_modify : user_info_title_view;
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.shells.AbstractConfigurationWindow#getImage()
     */
	@Override
    protected Image getImage() {
        return Resources.getInstance().getImage(Resources.icoPersonInfo);
    }
    
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.shells.AbstractConfigurationWindow#getInitialSize()
     */
	@Override
    protected Point getInitialSize() {
        return new Point(600, 550);
    }
    
    /**
     * 设置好友model
     * 
     * @param model
     * 		FriendModel对象
     */
    public void setFriendModel(User model) {
    	if(this.model != model)
    		this.model.infoCopy(model);
        refreshPageModels(model);
        refreshPageValues();
    }

    /**
     * 设置QQ秀
     * 
     * @param showImage
     */
    public void setQQShow(Image showImage) {
        ((QQShowPage)getPage(QQ_SHOW)).setQQShow(showImage);
    }

    @Override
    protected void OnQQEvent(QQEvent e) {
        switch(e.type) {
			case QQEvent.FRIEND_UPLOAD_REMARKS_OK:
				processUploadFriendRemarkSuccess(e);
				break;
			case QQEvent.USER_MODIFY_INFO_OK:
			    processModifyInfoSuccess(e);
				break;
			case QQEvent.FRIEND_GET_REMARK_OK:
				processDownloadFriendRemarkSuccess(e);
				break;
			case QQEvent.CLUSTER_MODIFY_CARD_OK:
				processModifyCardSuccess(e);
				break;
			case QQEvent.CLUSTER_GET_CARD_OK:
				processGetCardSuccess(e);
				break;
			case QQEvent.USER_MODIFY_SIGNATURE_OK:
			case QQEvent.USER_DELETE_SIGNATURE_OK:
				processSignatureOpSuccess(e);
				break;
			case QQEvent.SYS_TIMEOUT:
				switch(e.operation) {
					case QQ.QQ_CMD_FRIEND_DATA_OP:
						processFriendRemarkOpTimeout(e);
						break;
				}
				break;
        }
    }

	/**
	 * 处理修改个性签名成功事件
	 * 
	 * @param e
	 */
	private void processSignatureOpSuccess(QQEvent e) {
		SignatureOpReplyPacket packet = (SignatureOpReplyPacket)e.getSource();
		if(packet.getSequence() == sigSequence) {
			sigModified = true;
			if(infoModified) {
				MessageDialog.openInformation(parentShell, message_box_success_modify_info_title, success_modify_info);	    				
				close();	
			}
		}
	}

	/**
	 * 处理得到群名片成功事件
	 * 
	 * @param e
	 */
	private void processGetCardSuccess(QQEvent e) {
		ClusterCommandReplyPacket packet = (ClusterCommandReplyPacket)e.getSource();
		if(packet.memberQQ == model.qq) {
			model.cardName = packet.card.name;
			model.cardEmail = packet.card.email;
			model.cardPhone = packet.card.phone;
			model.cardRemark = packet.card.remark;
			model.cardGenderIndex = packet.card.genderIndex;
			setFriendModel(model);
		}
	}

	/**
	 * 处理修改群名片成功事件
	 * 
	 * @param e
	 */
	private void processModifyCardSuccess(QQEvent e) {
	    if(cmcp == null || isReadOnly())
	        return;
	    
	    ClusterCommandReplyPacket reply = (ClusterCommandReplyPacket)e.getSource();
	    if(reply.getSequence() == cmcp.getSequence()) {
			setOKButtonEnabled(true);
			Card card = cmcp.getCard();
			model.cardName = card.name;
			model.cardEmail = card.email;
			model.cardPhone = card.phone;
			model.cardRemark = card.remark;
			model.cardGenderIndex = card.genderIndex;
			model.displayName = model.cardName;
			cmcp = null;
			main.getBlindHelper().refreshModel(model);
			MessageDialog.openInformation(shell, message_box_common_success_title, NLS.bind(message_box_card_modified, model.cluster.name));
	    }
	}

	/**
	 * 处理上传好友备注信息成功事件
	 * 
	 * @param e
	 */
	private void processUploadFriendRemarkSuccess(QQEvent e) {
		FriendDataOpPacket packet = (FriendDataOpPacket)e.getSource();
		if(packet.getQQ() == model.qq) {
            RemarkPage page = (RemarkPage)getPage(REMARK);
            page.setUploadRemarkEnable(true);
		    MessageDialog.openInformation(shell, message_box_common_success_title, message_box_upload_remark_success);		
		}
	}
    
	/**
	 * 处理备注操作超时事件
	 * 
	 * @param e
	 */
	private void processFriendRemarkOpTimeout(QQEvent e) {
		FriendDataOpPacket packet = (FriendDataOpPacket)e.getSource();
		if(packet.getQQ() == model.qq) {
			if(packet.getSubCommand() == QQ.QQ_SUB_CMD_DOWNLOAD_FRIEND_REMARK)
			    ((RemarkPage)getPage(REMARK)).setDownloadRemarkEnable(true);
			MessageDialog.openError(shell, message_box_common_fail_title, message_box_common_timeout);
		}
	}

	/**
	 * 处理更新用户信息成功事件，更新信息肯定是自己，因为只有自己能更新自己的
	 */
	private void processModifyInfoSuccess(QQEvent e) {
	    if(mip == null || isReadOnly())
	        return;
	    
	    if(mip.getSequence() == ((ModifyInfoPacket)e.getSource()).getSequence()) {
			// 刷新自己的信息
			ContactInfo info = mip.getContactInfo();
			User me = main.getMyModel();
			me.info = info;
			me.headId = getFaceId();
			me.nick = info.nick;
			me.displayName = me.nick;
			// 保存新密码，关闭资料窗口
			saveNewPassword();
			
			infoModified = true;
			if(!modifySignature || modifySignature && sigModified) {
				MessageDialog.openInformation(parentShell, message_box_success_modify_info_title, success_modify_info);	    				
				close();	    				
			}   	
	    }
	}
	
	/**
	 * 处理下载好友备注信息成功事件
	 * 
	 * @param e
	 */
	private void processDownloadFriendRemarkSuccess(QQEvent e) {
	    if(isEditable())
	        return;
	    
		FriendDataOpReplyPacket packet = (FriendDataOpReplyPacket)e.getSource();
		if(packet.hasRemark) {
			if(packet.qqNum == model.qq) {
				// 把下载到的信息保存到xml
				final Remark remark = createRemarkElement(packet.remark);
				((RemarkPage)getPage(REMARK)).saveToXML(remark);
				// 设置下载按钮为enable
			    ((RemarkPage)getPage(REMARK)).setDownloadRemarkEnable(true);
				// 设置各文本框内容
			    ((RemarkPage)getPage(REMARK)).setRemarkInfo(remark);		
			}		    
		} else {
		    // 好友没有备注，把按钮使能
			((RemarkPage)getPage(REMARK)).setDownloadRemarkEnable(true);
		}
	}
	
	/**
	 * 从FriendRemark中创建Remark对象
	 * @param fr
	 * @return
	 */
	private Remark createRemarkElement(FriendRemark fr) {
		Remark remark = RemarkFactory.eINSTANCE.createRemark();
		remark.setName(fr.name.trim());
		remark.setZipcode(fr.zipcode);
		remark.setTelephone(fr.telephone);
		remark.setMobile(fr.mobile);
		remark.setEmail(fr.email);
		remark.setAddress(fr.address);
		remark.setNote(fr.note);
		remark.setQq(model.qq);
		return remark;
	}
	
	/**
	 * @return
	 * 		头像ID
	 */
	private int getFaceId() {
	    return ((PersonalPage)getPage(PERSONAL_INFO)).getHeadId();
	}
	
	/**
	 * 如果用户修改了密码，且用户选择了记住密码，则把新密码保存到logins.xml中
	 */
	public void saveNewPassword() {
	    if(mip.getOldPassword() == null)
	        return;
	    
        // 读入登陆信息文件
	    File loginHistory = new File(LumaQQ.LOGIN_HISTORY);
    	Logins logins = LoginUtil.load(loginHistory);
    	if(logins == null)
    	    return;
    	// 得到自己的Login对象
    	Login login = LoginUtil.findLogin(logins, String.valueOf(main.getMyModel().qq));
    	if(login == null)
    	    return;
    	// 检查是否设置了记住密码
    	if(!login.isRememberPassword())
    	    return;
    	
    	// 保存新密码
    	login.setPassword(new String(new Base64().encode(md5(md5(mip.getNewPassword().getBytes())))));
        // 写入文件
    	LoginUtil.save(loginHistory, logins);
	}
}
