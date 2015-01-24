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
package edu.tsinghua.lumaqq.ui.wizard.search;

import static edu.tsinghua.lumaqq.resource.Messages.*;
import static edu.tsinghua.lumaqq.ui.wizard.search.SearchWizardModel.*;

import java.util.List;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;

import edu.tsinghua.lumaqq.events.BaseQQListener;
import edu.tsinghua.lumaqq.qq.QQ;
import edu.tsinghua.lumaqq.qq.beans.AdvancedUserInfo;
import edu.tsinghua.lumaqq.qq.beans.ClusterInfo;
import edu.tsinghua.lumaqq.qq.beans.UserInfo;
import edu.tsinghua.lumaqq.qq.events.QQEvent;
import edu.tsinghua.lumaqq.qq.packets.Packet;
import edu.tsinghua.lumaqq.qq.packets.in.AddFriendExReplyPacket;
import edu.tsinghua.lumaqq.qq.packets.in.AdvancedSearchUserReplyPacket;
import edu.tsinghua.lumaqq.qq.packets.in.ClusterCommandReplyPacket;
import edu.tsinghua.lumaqq.qq.packets.in.SearchUserReplyPacket;
import edu.tsinghua.lumaqq.qq.packets.out.AdvancedSearchUserPacket;
import edu.tsinghua.lumaqq.qq.packets.out.ClusterCommandPacket;
import edu.tsinghua.lumaqq.qq.packets.out.SearchUserPacket;
import edu.tsinghua.lumaqq.resource.Resources;
import edu.tsinghua.lumaqq.ui.MainShell;
import edu.tsinghua.lumaqq.ui.wizard.IModelBasedWizard;


/**
 * 搜索的wizard
 * 
 * @author luma
 */
public class SearchWizard extends Wizard implements IModelBasedWizard, INewWizard {    
    private MainShell main;
    private SearchResult userResult;
    private SearchResult clusterResult;
    private char expected;
    private boolean end;
    private boolean operating;
    private SearchWizardModel model;
    
    private int preNextFlag;
    
    private static final int PRE_NEXT_NONE = 0;
    private static final int PRE_NEXT_SEARCH_CLUSTER_BY_CATEGORY = 1;
    
    private BaseQQListener qqListener = new BaseQQListener() {
    	@Override
    	protected void OnQQEvent(QQEvent e) {
    		switch(e.type) {
    			case QQEvent.USER_SEARCH_OK:
    				processSearchUserSuccess(e);
    				break;		
    			case QQEvent.USER_ADVANCED_SEARCH_OK:
    			    processAdvancedSearchUserSuccess(e);
    				break;
    		    case QQEvent.USER_ADVANCED_SEARCH_END:
    		        processAdvancedSearchUserEnd(e);
    		    	break;
    			case QQEvent.CLUSTER_SEARCH_OK:
    				processSearchClusterSuccess(e);
    				break;
    			case QQEvent.CLUSTER_SEARCH_FAIL:
    				processSearchClusterFail(e);
    				break;
    			case QQEvent.FRIEND_ADD_OK:
    			case QQEvent.FRIEND_ADD_ALREADY:
    				processAddFriendSuccess(e);
    				break;
    			case QQEvent.FRIEND_ADD_FAIL:
    				processAddFriendFail(e);
    				break;
    		    case QQEvent.FRIEND_ADD_DENY:
    		        processAddFriendDeny(e);
    		    	break;
    			case QQEvent.FRIEND_ADD_NEED_AUTH:
    				processAddFriendNeedAuth(e);
    				break;
    			case QQEvent.FRIEND_AUTH_SEND_OK:
    			case QQEvent.FRIEND_AUTHORIZE_SEND_OK:
    				processAddFriendAuthSendSuccess(e);
    				break;
    			case QQEvent.FRIEND_AUTH_SEND_FAIL:
    			case QQEvent.FRIEND_AUTHORIZE_SEND_FAIL:
    				processAddFriendAuthSendFail(e);
    				break;
    			case QQEvent.CLUSTER_JOIN_OK:
    				processJoinClusterSuccess(e);
    				break;
    			case QQEvent.CLUSTER_JOIN_FAIL:
    				processJoinClusterFail(e);
    				break;
    			case QQEvent.CLUSTER_JOIN_DENY:
    			    processJoinClusterDenied(e);
    				break;
    			case QQEvent.CLUSTER_JOIN_NEED_AUTH:
    				processJoinClusterNeedAuth(e);
    				break;
    			case QQEvent.CLUSTER_AUTH_SEND_FAIL:
    				processJoinClusterAuthSendFail(e);
    				break;
    			case QQEvent.CLUSTER_AUTH_SEND_OK:
    			    processJoinClusterAuthSendSuccess(e);
    				break;
    		    case QQEvent.SYS_TIMEOUT:
    		        switch(e.operation) {
    		            case QQ.QQ_CMD_SEARCH_USER:
    		                processSearchUserTimeout(e);
    		            	break;
    		            case QQ.QQ_CMD_ADVANCED_SEARCH:
    		                processAdvancedSearchUserTimeout(e);
    		            	break;
    					case QQ.QQ_CMD_CLUSTER_CMD:
    						processClusterCommandTimeout(e);
    						break;
    					case QQ.QQ_CMD_ADD_FRIEND_AUTH:
    						processAddFriendAuthSendFail(e);
    						break;
    					case QQ.QQ_CMD_ADD_FRIEND_EX:
    						processAddFriendExTimeout(e);
    						break;
    		        }
    		}
    	}
    };

    public SearchWizard() {
    	model = new SearchWizardModel();
    }
    
    /* (non-Javadoc)
     * @see org.eclipse.jface.wizard.IWizard#getStartingPage()
     */
    @Override
	public IWizardPage getStartingPage() {
        return getPage(model.getStartingPage());
    }
    
    /* (non-Javadoc)
     * @see org.eclipse.jface.wizard.IWizard#performFinish()
     */
    @Override
	public boolean performFinish() {
        unhookListener();
        main.getShellRegistry().deregisterSearchWizard();
        return true;
    }
    
    /* (non-Javadoc)
     * @see org.eclipse.jface.wizard.IWizard#addPages()
     */
    @Override
	public void addPages() {
        userResult = new SearchResult();
        clusterResult = new SearchResult();
        addPage(new SearchWhatWizardPage(PAGE_SEARCH_WHAT));
        addPage(new HowToSearchUserWizardPage(PAGE_HOW_TO_SEARCH_USER));
        addPage(new HowToSearchClusterWizardPage(main, PAGE_HOW_TO_SEARCH_CLUSTER));
        addPage(new SearchUserAccurateWizardPage(PAGE_SEARCH_USER_ACCURATE));
        addPage(new SearchUserAdvancedWizardPage(PAGE_SEARCH_USER_ADVANCED));
        addPage(new SearchUserResultWizardPage(PAGE_SEARCH_USER_RESULT, userResult));
        addPage(new SearchClusterResultWizardPage(PAGE_SEARCH_CLUSTER_RESULT, clusterResult));
        addPage(new AddFriendClusterWizardPage(PAGE_ADD));
        
        getShell().setImage(Resources.getInstance().getImage(Resources.icoSearch));
        hookListener();
        end = false;
        operating = false;
        preNextFlag = PRE_NEXT_NONE;
    }
    
    /* (non-Javadoc)
     * @see org.eclipse.jface.wizard.IWizard#getNextPage(org.eclipse.jface.wizard.IWizardPage)
     */
    @Override
	public IWizardPage getNextPage(IWizardPage page) {
    	preNextFlag = PRE_NEXT_NONE;
        String name = page.getName();
        if(PAGE_SEARCH_WHAT.equals(name)) {
            switch(model.getSearchWhat()) {
                case SearchWizardModel.USER:
                    return getPage(PAGE_HOW_TO_SEARCH_USER);
                case SearchWizardModel.CLUSTER:
                    return getPage(PAGE_HOW_TO_SEARCH_CLUSTER);
                default:
                    return null;
            }
        } else if(PAGE_HOW_TO_SEARCH_USER.equals(name)) {   
            switch(model.getUserSearchMode()) {
                case ONLINE:
                    return getPage(PAGE_SEARCH_USER_RESULT);
                case ACCURATE:
                    return getPage(PAGE_SEARCH_USER_ACCURATE);
                case ADVANCED:
                    return getPage(PAGE_SEARCH_USER_ADVANCED);
                default:
                    return null;
            }
        } else if(PAGE_HOW_TO_SEARCH_CLUSTER.equals(name)) {
        	switch(model.getClusterSearchMode()) {
        		case BY_CATEGORY:      
        			preNextFlag = PRE_NEXT_SEARCH_CLUSTER_BY_CATEGORY;
        			return page;
        		default:
        			return getPage(PAGE_SEARCH_CLUSTER_RESULT);
        	}
        } else if(PAGE_SEARCH_USER_ACCURATE.equals(name)) {
            return getPage(PAGE_SEARCH_USER_RESULT);
        } else if(PAGE_SEARCH_USER_ADVANCED.equals(name)) {
            return getPage(PAGE_SEARCH_USER_RESULT);
        } else if(PAGE_SEARCH_USER_RESULT.equals(name)) {
            return getPage(PAGE_ADD);
        } else if(PAGE_SEARCH_CLUSTER_RESULT.equals(name)) {
            return getPage(PAGE_ADD);
        } else
            return null;
    }

    /* (non-Javadoc)
     * @see org.eclipse.ui.IWorkbenchWizard#init(org.eclipse.ui.IWorkbench, org.eclipse.jface.viewers.IStructuredSelection)
     */
    public void init(IWorkbench workbench, IStructuredSelection selection) {
    }
    
    /**
     * 初始化，传递MainShell引用
     * 
     * @param m
     */
    public void init(MainShell m) {
        this.main = m;
        setWindowTitle(search_title);       
        setDefaultPageImageDescriptor(Resources.getInstance().getImageDescriptor(Resources.icoSearchWizard));
    }    
    
    public MainShell getMainShell() {
        return main;
    }
    
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.ui.wizard.IModelBasedWizard#preNext()
     */
    public void preNext() {
    	switch(preNextFlag) {
    		case PRE_NEXT_SEARCH_CLUSTER_BY_CATEGORY:
    			getMainShell().getShellLauncher().searchCluster(model.getCategoryId());
    			break;
    	}
    }
    
	/**
	 * 处理添加好友超时事件
	 * 
	 * @param e
	 */
	private void processAddFriendExTimeout(QQEvent e) {
	    Packet packet = (Packet)e.getSource();
	    if(expected != packet.getSequence())
	        return;
	    operating = false;
	    expected = 0;
	    setAddPageStatus(ADD_TIMEOUT);
	}

	/**
	 * 初始对方禁止添加好友事件
	 * 
     * @param e
     */
    private void processAddFriendDeny(QQEvent e) {
	    Packet packet = (Packet)e.getSource();
	    if(expected != packet.getSequence())
	        return;
	    operating = false;
	    expected = 0;
	    setAddPageStatus(ADD_DENY);
    }

    /**
	 * 处理认证消息发送失败事件
	 */
	private void processJoinClusterAuthSendFail(QQEvent e) {
	    Packet packet = (Packet)e.getSource();
	    if(expected != packet.getSequence())
	        return;
	    operating = false;
	    expected = 0;
	    setAddPageStatus(AUTH_TIMEOUT);
	}
    
	/**
	 * 处理认证消息发送成功事件
	 */
	private void processJoinClusterAuthSendSuccess(QQEvent e) {
	    Packet packet = (Packet)e.getSource();
	    if(expected != packet.getSequence())
	        return;
	    operating = false;
	    expected = 0;
	    setAddPageStatus(AUTH_SENT);
	}
	
    /**
     * 处理加入群需要认证事件
     */
    private void processJoinClusterNeedAuth(QQEvent e) {
	    Packet packet = (Packet)e.getSource();
	    if(expected != packet.getSequence())
	        return;
	    operating = false;
	    expected = 0;
	    setAddPageStatus(AUTH_INPUTING);
    }
    
	/**
     * 处理禁止加入群事件
     */
    private void processJoinClusterDenied(QQEvent e) {
	    Packet packet = (Packet)e.getSource();
	    if(expected != packet.getSequence())
	        return;
	    operating = false;
	    expected = 0;
	    setAddPageStatus(JOIN_DENY);
    }
    
    /**
	 * 初始加入群失败事件
	 */
	private void processJoinClusterFail(QQEvent e) {
	    Packet packet = (Packet)e.getSource();
	    if(expected != packet.getSequence())
	        return;
	    operating = false;
	    expected = 0;
	    setAddPageStatus(JOIN_TIMEOUT);
	}
    
	/**
	 * 处理加入群成功事件
	 * @param e
	 */
	private void processJoinClusterSuccess(QQEvent e) {
		ClusterCommandReplyPacket packet = (ClusterCommandReplyPacket)e.getSource();
		if(expected != packet.getSequence())
		    return;
		
		final int id = model.getSelectedModelId();
		if(id == packet.clusterId) {
		    operating = false;
		    expected = 0;
		    setAddPageStatus(JOIN_FINISHED);
			main.getBlindHelper().addCluster(id, false);
		}
	}
    
	/**
	 * 处理认证信息发送失败事件
	 */
	private void processAddFriendAuthSendFail(QQEvent e) {
	    Packet packet = (Packet)e.getSource();
	    if(expected != packet.getSequence())
	        return;
	    operating = false;
	    expected = 0;
	    setAddPageStatus(AUTH_TIMEOUT);
	}
    
	/**
	 * 处理验证发送成功事件
	 */
	private void processAddFriendAuthSendSuccess(QQEvent e) {
	    Packet packet = (Packet)e.getSource();
	    if(expected != packet.getSequence())
	        return;
	    operating = false;
	    expected = 0;
	    setAddPageStatus(AUTH_SENT);	
	}
    
	/**
	 * 处理添加好友需要验证事件
	 */
	private void processAddFriendNeedAuth(QQEvent e) {
	    Packet packet = (Packet)e.getSource();
	    if(expected != packet.getSequence())
	        return;
	    operating = false;
	    expected = 0;
	    setAddPageStatus(AUTH_INPUTING);
	}
	
	/**
	 * 处理添加好友失败事件
	 */
	private void processAddFriendFail(QQEvent e) {
	    Packet packet = (Packet)e.getSource();
	    if(expected != packet.getSequence())
	        return;
	    operating = false;
	    expected = 0;
	    setAddPageStatus(ADD_DENY);
	}
    
	/**
	 * 处理添加好友成功事件
	 * 
	 * @param e
	 */
	private void processAddFriendSuccess(QQEvent e) {
		AddFriendExReplyPacket packet = (AddFriendExReplyPacket)e.getSource();
		if(expected != packet.getSequence())
		    return;
		final int id = model.getSelectedModelId();
		if(id == packet.friendQQ) {
		    operating = false;
		    expected = 0;
		    setAddPageStatus(ADD_FINISHED);
		}
	}
    
	/**
	 * 处理群命令超时事件
	 * 
	 * @param e
	 */
	private void processClusterCommandTimeout(QQEvent e) {
		final ClusterCommandPacket packet = (ClusterCommandPacket)e.getSource();
	    if(expected != packet.getSequence())
	        return;
		switch(packet.getSubCommand()) {
		    case QQ.QQ_CLUSTER_CMD_SEARCH_CLUSTER: 
				processSearchClusterTimeout(e);
		    	break;
			case QQ.QQ_CLUSTER_CMD_JOIN_CLUSTER_AUTH:
				processJoinClusterAuthSendFail(e);
				break;
			case QQ.QQ_CLUSTER_CMD_JOIN_CLUSTER:
				processJoinClusterFail(e);
				break;
		}
	}
	
	/**
	 * 处理搜索群命令超时事件
	 * 
	 * @param e
	 */
	private void processSearchClusterTimeout(QQEvent e) {
	    ClusterCommandPacket packet = (ClusterCommandPacket)e.getSource();
	    if(expected != packet.getSequence())
	        return;
	    operating = false;
	    expected = 0;
	    onSearchClusterError();
		openError(message_box_common_timeout);
	}
	
	/**
	 * 搜索群发生错误时
	 */
	private void onSearchClusterError() {
        SearchClusterResultWizardPage page = (SearchClusterResultWizardPage)getPage(PAGE_SEARCH_CLUSTER_RESULT);
        page.onSearchClusterError();
	}

	/**
	 * 处理搜索群失败事件
	 * 
	 * @param e
	 */
	private void processSearchClusterFail(QQEvent e) {
		final ClusterCommandReplyPacket packet = (ClusterCommandReplyPacket)e.getSource();
	    if(expected != packet.getSequence())
	        return;
	    operating = false;
	    expected = 0;
	    onSearchClusterError();
		openError(packet.errorMessage);
	}

	/**
	 * 处理搜索群成功事件
	 * 
	 * @param e
	 */
	private void processSearchClusterSuccess(QQEvent e) {
		final ClusterCommandReplyPacket packet = (ClusterCommandReplyPacket)e.getSource();
	    if(expected != packet.getSequence())
	        return;
	    operating = false;
	    expected = 0;
	    showClusterPage(packet.clusters);
	}

	/**
     * @param e
     */
    private void processAdvancedSearchUserTimeout(QQEvent e) {
	    AdvancedSearchUserPacket packet = (AdvancedSearchUserPacket)e.getSource();
	    if(expected != packet.getSequence())
	        return;
	    operating = false;
	    expected = 0;
	    onSearchUserError();
	    openError(error_timeout);
    }

    /**
     * @param e
     */
    private void processAdvancedSearchUserEnd(QQEvent e) {
	    AdvancedSearchUserReplyPacket packet = (AdvancedSearchUserReplyPacket)e.getSource();
	    if(expected != packet.getSequence())
	        return;
	    operating = false;
	    end = true;
	    expected = 0;
	    onSearchUserEnd();
    }

    /**
     * @param e
     */
    private void processAdvancedSearchUserSuccess(QQEvent e) {
	    AdvancedSearchUserReplyPacket packet = (AdvancedSearchUserReplyPacket)e.getSource();
	    if(expected != packet.getSequence())
	        return;
	    end = false;
	    operating = false;
	    expected = 0;
	    showUserPage(packet.users);
    }
    
	/**
	 * 初始搜索用户超时事件
     * @param e
     */
    private void processSearchUserTimeout(QQEvent e) {
	    SearchUserPacket packet = (SearchUserPacket)e.getSource();
	    if(expected != packet.getSequence())
	        return;
	    operating = false;
	    expected = 0;
	    onSearchUserError();
	    openError(error_timeout);
    }    

	/**
	 * 搜索发生错误时
	 */
	private void onSearchUserError() {
        SearchUserResultWizardPage page = (SearchUserResultWizardPage)getPage(PAGE_SEARCH_USER_RESULT);
        page.onSearchUserError();
	}	
    
    /**
     * 打开错误提示框
     * 
     * @param message
     * 		错误消息
     */
    private void openError(String message) {
	    MessageDialog.openError(getShell(), message_box_common_fail_title, message);
    }

    /**
	 * 处理搜索用户成功事件
	 * 
	 * @param e
	 */
	private void processSearchUserSuccess(QQEvent e) {
	    SearchUserReplyPacket packet = (SearchUserReplyPacket)e.getSource();
	    if(expected != packet.getSequence())
	        return;
	    end = false;
	    operating = false;
	    expected = 0;
	    showUserPage(packet.users);
	}
    
	/**
	 * 显示一页结果
	 * 
	 * @param page
	 */
	private void showUserPage(List<? extends Object> p) {
        SearchUserResultWizardPage page = (SearchUserResultWizardPage)getPage(PAGE_SEARCH_USER_RESULT);
        page.addPage(p);
	}
	
	/**
	 * 显示一页结果
	 * 
	 * @param p
	 */
	private void showClusterPage(List<? extends Object> p) {
        SearchClusterResultWizardPage page = (SearchClusterResultWizardPage)getPage(PAGE_SEARCH_CLUSTER_RESULT);
        page.addPage(p);
	}
    
    /**
     * 在搜索用户结束时调用
     */
    private void onSearchUserEnd() {
        SearchUserResultWizardPage page = (SearchUserResultWizardPage)getPage(PAGE_SEARCH_USER_RESULT);
        page.onSearchEnd();
    }
    
    /**
     * 设置添加页面的状态
     * 
     * @param status
     */
    private void setAddPageStatus(int status) {
    	model.setStatus(status);
        AddFriendClusterWizardPage page = (AddFriendClusterWizardPage)getPage(PAGE_ADD);
        page.refresh();
    }
    
    /* (non-Javadoc)
     * @see org.eclipse.jface.wizard.IWizard#performCancel()
     */
    @Override
	public boolean performCancel() {
        unhookListener();
        main.getShellRegistry().deregisterSearchWizard();
        return true;
    }      
    
    /**
     * 根据搜索方式搜索
     * 
     * @param page
     */
    public void doSearch(int page) {
        if(operating)
            return;
        operating = true;
        
        if(model.getSearchWhat() == USER) {
            switch(model.getUserSearchMode()) {
                case ONLINE:
                    searchOnline(page);
                    break;
                case ACCURATE:
                    searchAccurate(page);
                    break;
                case ADVANCED:
                    searchAdvanced(page);
                    break;
                default:
                    operating = false;
                	break;
            }        	
        } else if(model.getSearchWhat() == CLUSTER) {
        	switch(model.getClusterSearchMode()) {
                case DEMO_CLUSTER:
                    searchDemoCluster();
                    break;
                case BY_CLUSTER_ID:
                    searchClusterById();
                    break;
                default:
                    operating = false;
                	break;
        	}
        }
    }
    
    /**
     * 发送认证信息
     * 
     * @param message
     */
    public void doAuth(String message) {
        if(operating)
            return;
        operating = true;
        
        Object selectedModel = model.getSelectedModel();
        if(selectedModel == null)
            return;
        
        if(selectedModel instanceof UserInfo) {
            UserInfo user = (UserInfo)selectedModel;
            expected = main.getClient().user_SendAuth(user.qqNum, message);
            setAddPageStatus(AUTH_SENDING);
        } else if(selectedModel instanceof AdvancedUserInfo) {
            AdvancedUserInfo user = (AdvancedUserInfo)selectedModel;
            expected = main.getClient().user_SendAuth(user.qqNum, message);
            setAddPageStatus(AUTH_SENDING);
        } else if(selectedModel instanceof ClusterInfo) {
            ClusterInfo cluster = (ClusterInfo)selectedModel;
            expected = main.getClient().cluster_RequestJoin(cluster.clusterId, message);
            setAddPageStatus(AUTH_SENDING);
        } else
            operating = false;
    }
    
    /**
     * 添加好友或群
     */
    public void doAdd() {
        if(operating)
            return;
        operating = true;
        
        Object selectedModel = model.getSelectedModel();
        if(selectedModel == null)
            return;
        
        if(selectedModel instanceof UserInfo) {
            UserInfo user = (UserInfo)selectedModel;
            expected = main.getClient().user_Add(user.qqNum);
            setAddPageStatus(ADDING);
        } else if(selectedModel instanceof AdvancedUserInfo) {
            AdvancedUserInfo user = (AdvancedUserInfo)selectedModel;
            expected = main.getClient().user_Add(user.qqNum);
            setAddPageStatus(ADDING);
        } else if(selectedModel instanceof ClusterInfo) {
            ClusterInfo cluster = (ClusterInfo)selectedModel;
            expected = main.getClient().cluster_Join(cluster.clusterId);
            setAddPageStatus(JOINING);
        } else
            operating = false;
    }
    
    /**
     * 搜索示范群
     */
    public void searchDemoCluster() {
        expected = ((HowToSearchClusterWizardPage)getPage(PAGE_HOW_TO_SEARCH_CLUSTER)).doSearch();
    }
    
    /**
     * 根据群ID搜索
     */
    public void searchClusterById() {
        expected = ((HowToSearchClusterWizardPage)getPage(PAGE_HOW_TO_SEARCH_CLUSTER)).doSearch();
    }
    
    /**
     * 高级搜索
     * 
     * @param page
     */
    public void searchAdvanced(int page) {
        expected = ((SearchUserAdvancedWizardPage)getPage(PAGE_SEARCH_USER_ADVANCED)).doSearch(page);
    }
    
    /**
     * 搜索用户
     * 
     * @param page
     * 		页号
     */
    public void searchOnline(int page) {
        expected = main.getClient().user_Search(page);
    }
    
    /**
     * 精确查找
     * 
     * @param page
     * 		页号
     */
    public void searchAccurate(int page) {
        expected = ((SearchUserAccurateWizardPage)getPage(PAGE_SEARCH_USER_ACCURATE)).doSearch(page);
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
    
    /**
     * @return Returns the end.
     */
    public boolean isEnd() {
        return end;
    }
    
    /**
     * @return Returns the operating.
     */
    public boolean isOperating() {
        return operating;
    }
    
    /**
     * @param end The end to set.
     */
    public void setEnd(boolean end) {
        this.end = end;
    }
    
    /* (non-Javadoc)
     * @see org.eclipse.jface.wizard.IWizard#canFinish()
     */
    @Override
	public boolean canFinish() {
        return ((AddFriendClusterWizardPage)getPage(PAGE_ADD)).isPageComplete();
    }

	public Object getModel() {
		return model;
	}
}
