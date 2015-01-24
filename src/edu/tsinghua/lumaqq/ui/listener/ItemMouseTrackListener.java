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
package edu.tsinghua.lumaqq.ui.listener;

import edu.tsinghua.lumaqq.models.Cluster;
import edu.tsinghua.lumaqq.models.Model;
import edu.tsinghua.lumaqq.models.User;
import edu.tsinghua.lumaqq.ui.FriendTipShell;
import edu.tsinghua.lumaqq.ui.MainShell;
import edu.tsinghua.lumaqq.ui.helper.OptionHelper;
import edu.tsinghua.lumaqq.widgets.qstyle.QItem;
import edu.tsinghua.lumaqq.widgets.qstyle.QTree;

import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseTrackAdapter;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;

/**
 * 好友的鼠标跟踪事件，用于显示一个tip窗口
 * 
 * @author luma
 */
public class ItemMouseTrackListener extends MouseTrackAdapter {	
    private MainShell main;
	
	/**
	 * 构造函数
	 * @param main
	 */
	public ItemMouseTrackListener(MainShell main) {
	    this.main = main;
	}
		
	/* (non-Javadoc)
	 * @see org.eclipse.swt.events.MouseTrackListener#mouseExit(org.eclipse.swt.events.MouseEvent)
	 */
	@Override
	public void mouseExit(MouseEvent e) {
		// 如果当前设置了不显示tip，返回
		OptionHelper options = main.getOptionHelper();
		if(!options.isShowFriendTip()) return;
		// 设置在1.5秒后关闭提示窗口
		if(main.getTipHelper().isFriendTipShellVisible())
			main.getTipHelper().getFriendTipShell().setShouldClose(true);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.swt.events.MouseTrackListener#mouseHover(org.eclipse.swt.events.MouseEvent)
	 */
	@Override
	public void mouseHover(MouseEvent e) {
		QTree tree = (QTree)e.getSource();
		QItem item = tree.getItemUnderMouse();
		refreshTip(tree, item, e);
	}

	/**
	 * 刷新提示窗口
	 * 
	 * @param tree
	 * @param item
	 * @param e
	 */
	private void refreshTip(QTree tree, QItem item, MouseEvent e) {
		if(item == null) {
			closeAllTip();
			return;
		}
				
		Model model = (Model)item.getData();
		switch(model.type) {
			case USER:
				refreshFriendTip(tree, (User)model, e);
				break;
			case CLUSTER:
				refreshClusterTip(tree, (Cluster)model, e);
				break;
			default:
				closeAllTip();
				break;
		}
	}

	/**
	 * 刷新群提示窗口
	 * @param tree
	 * @param cluster
	 * @param e
	 */
	private void refreshClusterTip(QTree tree, Cluster cluster, MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * 关闭所有提示窗口
	 */
	private void closeAllTip() {
		main.getTipHelper().getFriendTipShell().setShouldClose(true);
	}

	/**
	 * 刷新好友提示窗口的信息
	 * 
	 * @param tree
	 * @param model
	 * @param e
	 */
	private void refreshFriendTip(QTree tree, User model, MouseEvent e) {
		// 如果当前设置了不显示tip，返回
		OptionHelper options = main.getOptionHelper();
		if(!options.isShowFriendTip()) return;
		// 得到好友提示窗口
		FriendTipShell tipShell = main.getTipHelper().getFriendTipShell();
		// 设置关闭标志为false
		tipShell.setShouldClose(false);
		// 得到好友model
		tipShell.setModel(model);
		// 添加model到tipShell的Data中
		tipShell.setData(model);
		// 调整窗口的位置，然后显示
		Point p = tree.toDisplay(e.x, e.y);
		Rectangle mainBound = main.getShell().getBounds();
		p.x = mainBound.x - 260;
		if(p.x < 0)
			p.x = mainBound.x + mainBound.width + 10;
		tipShell.setLocation(p);
		tipShell.setVisible(true);
		// 这也是为了满足Linux，必须layout
		tipShell.setSize(236, 156);
		tipShell.layout();
	}
}
