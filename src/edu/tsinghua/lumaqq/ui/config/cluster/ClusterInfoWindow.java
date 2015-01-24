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
package edu.tsinghua.lumaqq.ui.config.cluster;

import static edu.tsinghua.lumaqq.resource.Messages.button_modify;
import static edu.tsinghua.lumaqq.resource.Messages.button_update;
import static edu.tsinghua.lumaqq.resource.Messages.cluster_info_title_modify;
import static edu.tsinghua.lumaqq.resource.Messages.cluster_info_title_view;
import static edu.tsinghua.lumaqq.resource.Messages.message_box_cluster_message_option_modified;
import static edu.tsinghua.lumaqq.resource.Messages.message_box_common_fail_title;
import static edu.tsinghua.lumaqq.resource.Messages.message_box_common_info_title;
import static edu.tsinghua.lumaqq.resource.Messages.message_box_common_timeout;
import static edu.tsinghua.lumaqq.resource.Messages.success_modify_info;

import edu.tsinghua.lumaqq.models.Cluster;
import edu.tsinghua.lumaqq.qq.QQ;
import edu.tsinghua.lumaqq.qq.events.QQEvent;
import edu.tsinghua.lumaqq.qq.packets.in.ClusterCommandReplyPacket;
import edu.tsinghua.lumaqq.qq.packets.out.ClusterCommandPacket;
import edu.tsinghua.lumaqq.resource.Resources;
import edu.tsinghua.lumaqq.ui.MainShell;
import edu.tsinghua.lumaqq.ui.config.AbstractConfigurationWindow;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;

/**
 * @author luma
 */
public class ClusterInfoWindow extends AbstractConfigurationWindow {
    private MainShell main;
    private Cluster model;
    
    // style
    /** 我是创建者 */
    public static final int EDITABLE_CREATOR = 1;
    /** 群资料只读，对应于查看群资料窗口 */
    public static final int READ_ONLY = 2;
    /** 我是管理员，管理员不能设置管理员，不能转让身份 */
    public static final int EDITABLE_ADMIN = 3;
    
    // 页ID
    private static final int CLUSTER_INFO = 0;
    private static final int MEMBERS = 1;
    private static final int MESSAGE_OPTION = 2;
    
    // 修改成员列表时的包序号，检查这些序号判断回复包是否属于这个窗口
    // 这里也用来判断修改是否完成，如果三者都为0，表示修改已经完成
    private char removeMemberSequence;
    private char addMemberSequence;
    private char modifyInfoSequence;
    
	/**
	 * Create a cluster info window
	 * 
	 * @param main
	 * 		MainShell
	 * @param c
	 * 		Cluster model
	 * @param style
	 * 		window style
	 */
	public ClusterInfoWindow(MainShell main, Cluster c, int style) {
		super(main.getShell(), style);
		this.main = main;
		this.model = c;
		
		setPageListWidth(90);
		
        if(style == READ_ONLY)
        	setOKButtonText(button_update);
        else
        	setOKButtonText(button_modify);
	}
	
	/* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.ui.config.AbstractConfigurationWindow#initializeVariables()
     */
	@Override
    protected void initializeVariables() {
        super.initializeVariables();
        addMemberSequence = removeMemberSequence = modifyInfoSequence = 0;
    }
    
    /**
     * @return
     * 		true表示修改已经完成
     */
    private boolean isFinished() {
        return addMemberSequence == 0 && removeMemberSequence == 0 && modifyInfoSequence == 0;
    }
	
	/* (non-Javadoc)
	 * @see edu.tsinghua.lumaqq.ui.config.AbstractConfigurationWindow#initialPages()
	 */
	@Override
	protected void initialPages() {
		addPage(new ClusterPage(getPageContainer(), main, model, style));
		addPage(new MemberPage(getPageContainer(), main, model, style));
		addPage(new MessagePage(getPageContainer(), model));
		
		main.getClient().addQQListener(this);
	}
	
	/* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.ui.config.AbstractConfigurationWindow#configureShellSize()
     */
	@Override
    protected void configureShellSize() {
        shell.pack();
        Point size = shell.getSize();
        if(size.x > 800)
            size.x = 800;
        if(size.y > 600)
            size.y = 600;
        shell.setSize(size);        
    }
    
	/* (non-Javadoc)
	 * @see edu.tsinghua.lumaqq.ui.config.AbstractConfigurationWindow#onShellClose()
	 */
	@Override
	protected void onShellClose() {
	    saveAll();
		main.getShellRegistry().removeClusterInfoWindow(model);
		main.getClient().removeQQListener(this);
	}
	
	/**
	 * 设置cluster model
	 * 
	 * @param c
	 */
	public void setClusterModel(Cluster c) {
        this.model = c;
        refreshPageModels(model);
        refreshPageValues();
	}
	
	/* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.ui.config.AbstractConfigurationWindow#onOK()
     */
	@Override
    protected void onOK() {
        if(getCurrentPageId() == MESSAGE_OPTION) {
            ((MessagePage)getPage(MESSAGE_OPTION)).doSave();
            MessageDialog.openInformation(getShell(), message_box_common_info_title, message_box_cluster_message_option_modified);
        } else {
            setOKButtonEnabled(false);
            if(style == READ_ONLY)
            	getClusterInfo();
            else
            	modifyCluster();                
        }
    }
	
	/**
     * 发送修改群信息的请求包
     */
    private void modifyCluster() {
        modifyInfoSequence = ((ClusterPage)getPage(CLUSTER_INFO)).doModifyClusterInfo();
        MemberPage page = (MemberPage)getPage(MEMBERS);
        removeMemberSequence = page.doRemoveMember();
        addMemberSequence = page.doAddMember();
    }

    /**
     * 请求得到群信息
     */
    private void getClusterInfo() {
        main.getClient().cluster_GetInfo(model.clusterId);
    }

    /* (non-Javadoc)
	 * @see edu.tsinghua.lumaqq.ui.config.AbstractConfigurationWindow#pageChanged()
	 */
	@Override
	protected void pageChanged() {
		if(style == READ_ONLY) {
			if(getCurrentPageId() == MESSAGE_OPTION) 
				setOKButtonText(button_modify);
			else
				setOKButtonText(button_update);
		}
	}
	
	/* (non-Javadoc)
	 * @see edu.tsinghua.lumaqq.ui.config.AbstractConfigurationWindow#getTitle()
	 */
	@Override
	protected String getTitle() {
        return (style == READ_ONLY) ? cluster_info_title_view : cluster_info_title_modify;
	}

	/* (non-Javadoc)
	 * @see edu.tsinghua.lumaqq.ui.config.AbstractConfigurationWindow#getImage()
	 */
	@Override
	protected Image getImage() {
		return Resources.getInstance().getImage(Resources.icoCluster);
	}
	
	/**
	 * 如果修改已经完成，关闭窗口
	 */
	private void checkFinish() {
        if(isFinished()) {
            getClusterInfo();
            MessageDialog.openInformation(main.getShell(), message_box_common_info_title, success_modify_info);
            close();
        }
	}

	@Override
	protected void OnQQEvent(QQEvent e) {
	    switch(e.type) {
			case QQEvent.CLUSTER_GET_INFO_FAIL:
			case QQEvent.CLUSTER_GET_TEMP_INFO_FAIL:
			case QQEvent.CLUSTER_MODIFY_INFO_FAIL:
				processClusterCommandFail(e);
				break;
			case QQEvent.CLUSTER_GET_TEMP_INFO_OK:
			case QQEvent.CLUSTER_GET_INFO_OK:
			    processGetClusterInfoSuccess(e);
				break;
			case QQEvent.CLUSTER_MODIFY_INFO_OK:
			    processModifyClusterInfoSuccess(e);
				break;
			case QQEvent.CLUSTER_MODIFY_MEMBER_OK:
			    processModifyClusterMemberSuccess(e);
				break;
			case QQEvent.SYS_TIMEOUT:
			    if(e.operation == QQ.QQ_CMD_CLUSTER_CMD)
			        processClusterCommandTimeout(e);
			    break;
	    }
	}

    /**
     * @param e
     */
    private void processModifyClusterMemberSuccess(QQEvent e) {
        ClusterCommandReplyPacket packet = (ClusterCommandReplyPacket)e.getSource();
        if(packet.getSequence() == addMemberSequence) 
            addMemberSequence = 0;
        else if(packet.getSequence() == removeMemberSequence) 
            removeMemberSequence = 0;
        
        checkFinish();
    }

    /**
     * @param e
     */
    private void processClusterCommandTimeout(QQEvent e) {
        ClusterCommandPacket packet = (ClusterCommandPacket)e.getSource();
        if(packet.getClusterId() == model.clusterId) {
            MessageDialog.openError(main.getShell(), message_box_common_fail_title, message_box_common_timeout);
            close();
        }
    }

    /**
     * @param e
     */
    private void processModifyClusterInfoSuccess(QQEvent e) {
        ClusterCommandReplyPacket packet = (ClusterCommandReplyPacket)e.getSource();
        if(packet.getSequence() != modifyInfoSequence)
            return;
        
        modifyInfoSequence = 0;
        checkFinish();
    }

    /**
	 * 处理得到临时群信息成功事件
	 * 
     * @param e
     */
    private void processGetClusterInfoSuccess(QQEvent e) {
		ClusterCommandReplyPacket packet = (ClusterCommandReplyPacket)e.getSource();
		if(packet.clusterId == model.clusterId)
		    setOKButtonEnabled(true);	
    }

    /**
	 * 处理群命令失败事件，如果错误信息表示自己已经不是群成员，则删除这个群
	 * 
	 * @param e
	 */
	private void processClusterCommandFail(QQEvent e) {
		ClusterCommandReplyPacket packet = (ClusterCommandReplyPacket)e.getSource();
		if(packet.clusterId == model.clusterId)
		    MessageDialog.openError(main.getShell(), message_box_common_fail_title, packet.errorMessage);	
	}
}
