/*
 * OWL Editor Plugin for Eclipse
 * IBM China Research Lab
 * Information & Knowledge Management Group
 * 
 * Copyright 2004, IBM Corporation, All right reserved
 * 
 * Created on 2005-1-6
 */
package edu.tsinghua.lumaqq.ui.helper;

import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.events.ShellListener;

import edu.tsinghua.lumaqq.ui.FriendTipShell;
import edu.tsinghua.lumaqq.ui.MainShell;
import edu.tsinghua.lumaqq.ui.OnlineTipShell;

/**
 * 提示窗口管理类
 * 
 * @author luma
 */
public class TipHelper {
	private MainShell main;
	private FriendTipShell friendTipShell;
	private OnlineTipShell onlineTipShell;
	
    // 是否是第一次得到好友列表，如果是，不需要弹出上线提示，对于后面的，需要
	private boolean firstTime;
	
	private ShellListener mainShellListener = new ShellAdapter() {
		@Override
		public void shellDeactivated(ShellEvent e) {
			closeFriendTipShell();
		}
	};
	
	public TipHelper(MainShell m) {
		main = m;
		firstTime = true;
		main.getShell().addShellListener(mainShellListener);
	}
	
	/**
	 * @return
	 * 		好友提示窗口，lazy initialization
	 */
	public FriendTipShell getFriendTipShell() {
		if(friendTipShell == null || friendTipShell.isDisposed())
			friendTipShell = new FriendTipShell(main);
		return friendTipShell;	
	}
	
	/**
	 * @return
	 * 		好友提示窗口，如果好像提示窗口不存在，则不创建新的
	 */
	private FriendTipShell getFriendTipShellNoCreate() {
		if(friendTipShell == null || friendTipShell.isDisposed())
			return null;
		else
			return friendTipShell;
	}
	
	/**
	 * @return
	 * 		true表示好友提示窗口可见
	 */
	public boolean isFriendTipShellVisible() {
		FriendTipShell shell = getFriendTipShellNoCreate();
		if(shell == null)
			return false;
		else
			return shell.isVisible();
	}
		
	/**
	 * 立刻关闭提示窗口
	 */
	public void closeFriendTipShell() {
		FriendTipShell shell = getFriendTipShellNoCreate();
		if(shell != null && shell.isVisible())
			shell.closeNow();
	}
	
	/**
	 * @return
	 * 		OnlineTipShell对象
	 */
	public OnlineTipShell getOnlineTipShell() {
	    if(onlineTipShell == null || onlineTipShell.isDisposed())
	        onlineTipShell = new OnlineTipShell(main);
	    return onlineTipShell;
	}
	    
    /**
     * @param firstTime The firstTime to set.
     */
    public void setFirstTime(boolean firstTime) {
        this.firstTime = firstTime;
    }
    
    /**
     * @return Returns the firstTime.
     */
    public boolean isFirstTime() {
        return firstTime;
    }
}
