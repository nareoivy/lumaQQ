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
package edu.tsinghua.lumaqq.ui.wizard.cluster;

import static edu.tsinghua.lumaqq.resource.Messages.*;
import static edu.tsinghua.lumaqq.ui.wizard.cluster.ClusterWizardModel.*;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;

import edu.tsinghua.lumaqq.events.BaseQQListener;
import edu.tsinghua.lumaqq.qq.QQ;
import edu.tsinghua.lumaqq.qq.events.QQEvent;
import edu.tsinghua.lumaqq.qq.packets.in.ClusterCommandReplyPacket;
import edu.tsinghua.lumaqq.qq.packets.out.ClusterCommandPacket;
import edu.tsinghua.lumaqq.resource.Resources;
import edu.tsinghua.lumaqq.ui.MainShell;
import edu.tsinghua.lumaqq.ui.wizard.IModelBasedWizard;

/**
 * 创建群的wizard
 * 
 * @author luma
 */
public class ClusterWizard extends Wizard implements IModelBasedWizard, INewWizard {
    private MainShell main;
    private ClusterWizardModel model;
    
    private boolean operating;
    private String errorMessage;
    private int clusterId;
    
    private BaseQQListener qqListener = new BaseQQListener() {
    	@Override
    	protected void OnQQEvent(QQEvent e) {
    		switch(e.type) {
    			case QQEvent.CLUSTER_CREATE_OK:
    			case QQEvent.CLUSTER_CREATE_TEMP_OK:
    				processClusterCreateSuccess(e);
    				break;
    			case QQEvent.CLUSTER_CREATE_FAIL:
    			case QQEvent.CLUSTER_CREATE_TEMP_FAIL:
    				processClusterCreateFail(e);
    				break;
    			case QQEvent.SYS_TIMEOUT:
    				if(e.operation == QQ.QQ_CMD_CLUSTER_CMD)
    					processClusterCommandTimeout(e);
    				break;
    		}
    	}
    };
    
    /**
     * 构造函数
     */
    public ClusterWizard() {
    	model = new ClusterWizardModel();
    }
    
    /* (non-Javadoc)
     * @see org.eclipse.jface.wizard.IWizard#getStartingPage()
     */
    @Override
    public IWizardPage getStartingPage() {    	
    	return getPage(model.getStartingPage());
    }
    
    /* (non-Javadoc)
     * @see org.eclipse.jface.wizard.IWizard#addPages()
     */
    @Override
	public void addPages() {
        addPage(new CreateWhatWizardPage(PAGE_CREATE_WHAT));
        addPage(new PermanentClusterInfoWizardPage(PAGE_PERMANENT_CLUSTER_INFO, main));
        addPage(new TempClusterInfoWizardPage(PAGE_TEMP_CLUSTER_INFO));
        addPage(new MemberSelectWizardPage(PAGE_MEMBER_SELECT));
        addPage(new CreateWizardPage(PAGE_CREATE));

        getShell().setImage(Resources.getInstance().getImage(Resources.icoCluster));
        hookListener();
    }
    
    /* (non-Javadoc)
     * @see org.eclipse.jface.wizard.IWizard#performFinish()
     */
    @Override
	public boolean performFinish() {
        unhookListener();
        main.getShellRegistry().deregisterClusterWizard();
        return true;
    }
    
    /* (non-Javadoc)
     * @see org.eclipse.jface.wizard.IWizard#performCancel()
     */
    @Override
	public boolean performCancel() {
        unhookListener();
        main.getShellRegistry().deregisterClusterWizard();
        return true;
    }
    
    /* (non-Javadoc)
     * @see org.eclipse.jface.wizard.IWizard#getNextPage(org.eclipse.jface.wizard.IWizardPage)
     */
    @Override
	public IWizardPage getNextPage(IWizardPage p) {
        String name = p.getName();
        if(PAGE_CREATE_WHAT.equals(name)) {
            switch(model.getClusterType()) {
                case ClusterWizardModel.PERMANENT_CLUSTER:
                    return getPage(PAGE_PERMANENT_CLUSTER_INFO);
                case ClusterWizardModel.DIALOG:
                case ClusterWizardModel.SUBJECT:
                	return getPage(PAGE_TEMP_CLUSTER_INFO);
                default:
                    return null;
            }
        } else if(PAGE_PERMANENT_CLUSTER_INFO.equals(name) || PAGE_TEMP_CLUSTER_INFO.equals(name)) {
            return getPage(PAGE_MEMBER_SELECT);
        } else if(PAGE_MEMBER_SELECT.equals(name)) {
            return getPage(PAGE_CREATE);
        } else
            return null;
    }

    /* (non-Javadoc)
     * @see org.eclipse.ui.IWorkbenchWizard#init(org.eclipse.ui.IWorkbench, org.eclipse.jface.viewers.IStructuredSelection)
     */
    public void init(IWorkbench arg0, IStructuredSelection arg1) {
    }
    
    /**
     * @return
     * 		MainShell
     */
    public MainShell getMainShell() {
        return main;
    }
    
    /**
     * 初始化，传递MainShell引用
     * 
     * @param main
     */
    public void init(MainShell m) {
        this.main = m;
        setWindowTitle(cluster_title);       
        setDefaultPageImageDescriptor(Resources.getInstance().getImageDescriptor(Resources.icoClusterWizard));
    }
    
    /**
     * 把自己加为QQ listener
     */
    private void hookListener() {
        main.getClient().addQQListener(qqListener);
    }

    /**
     * 删除自己做为qq listener
     */
    private void unhookListener() {
        main.getClient().removeQQListener(qqListener);
    }
    
    /* (non-Javadoc)
     * @see org.eclipse.jface.wizard.IWizard#canFinish()
     */
    @Override
	public boolean canFinish() {
        return ((CreateWizardPage)getPage(PAGE_CREATE)).isPageComplete();
    }
    
    /**
     * 创建群
     */
    public void doCreate() {
        if(isOperating())
            return;
        setOperating(true);
        
        switch(model.getClusterType()) {
            case ClusterWizardModel.PERMANENT_CLUSTER:
                ((PermanentClusterInfoWizardPage)getPage(PAGE_PERMANENT_CLUSTER_INFO)).doCreate();
            	break;
            default:
                ((TempClusterInfoWizardPage)getPage(PAGE_TEMP_CLUSTER_INFO)).doCreate();
            	break;
        }
        setCreatePageStatus(CreateWizardPage.CREATING);
    }
    
    /**
     * 设置创建页面的状态
     * 
     * @param status
     */
    public void setCreatePageStatus(int status) {
        ((CreateWizardPage)getPage(PAGE_CREATE)).setStatus(status);
    }
    
    /**
     * @return Returns the operating.
     */
    public boolean isOperating() {
        return operating;
    }
    
    /**
     * @param operating The operating to set.
     */
    public void setOperating(boolean operating) {
        this.operating = operating;
    }

    /**
     * @return Returns the clusterId.
     */
    public int getClusterId() {
        return clusterId;
    }
    
    /**
     * @return Returns the errorMessage.
     */
    public String getErrorMessage() {
        return errorMessage;
    }

	/**
	 * 处理群操作超时事件
	 * 
	 * @param e
	 */
	private void processClusterCommandTimeout(QQEvent e) {
		ClusterCommandPacket packet = (ClusterCommandPacket)e.getSource();
		if(packet.getSubCommand() == QQ.QQ_CLUSTER_CMD_CREATE_CLUSTER) {
		    setCreatePageStatus(CreateWizardPage.TIMEOUT);
		    setOperating(false);
		}
	}

	/**
	 * 处理创建群失败事件
	 * 
	 * @param e
	 */
	private void processClusterCreateFail(QQEvent e) {		
		ClusterCommandReplyPacket packet = (ClusterCommandReplyPacket)e.getSource();
	    errorMessage = packet.errorMessage;
	    setCreatePageStatus(CreateWizardPage.FAILED);
	    setOperating(false);
	}

	/**
	 * 处理创建群成功事件
	 * 
	 * @param e
	 */
	private void processClusterCreateSuccess(QQEvent e) {
		ClusterCommandReplyPacket packet = (ClusterCommandReplyPacket)e.getSource();
	    clusterId = packet.clusterId;
	    setCreatePageStatus(CreateWizardPage.CREATED);
	    setOperating(false);
	}

	public Object getModel() {
		return model;
	}

	/* (non-Javadoc)
	 * @see edu.tsinghua.lumaqq.ui.wizard.IModelBasedWizard#preNext()
	 */
	public void preNext() {
	}
}
