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
package edu.tsinghua.lumaqq.ui.helper;

import edu.tsinghua.lumaqq.models.User;
import edu.tsinghua.lumaqq.resource.Resources;

import org.eclipse.swt.graphics.Image;

/**
 * 根据model的参数得到正确的头像
 * 
 * @author luma
 */
public class HeadFactory {
    private static Resources res = Resources.getInstance();
    	
	/**
	 * 得到好友的在线头像，如果没有face属性，则返回缺省头像
	 * 
	 * @param f
	 * 		好友的model
	 * @return
	 * 		头像的Image对象
	 */
	public static Image getOnlineHead(User f) {
		return res.getHead(f.headId);
	}
	
	/**
	 * 得到好友的在线小头像，这个是因为linux下面居然不会自动缩放图像，没办法
	 * 
	 * @param f
	 * 		好友的model
	 * @return
	 * 		小头像的Image对象
	 */
	public static Image getOnlineSmallHead(User f) {
		return res.getSmallHead(f.headId);
	}
	
	/**
	 * 根据状态得到小头像
	 * 
	 * @param f
	 * 		好友model
	 * @return
	 * 		头像Image对象
	 */
	public static Image getSmallHeadByStatus(User f) {
		if(f.isOnline())
			return res.getSmallHead(f.headId);
		else if(f.isAway())
			return res.getSmallHead(f.headId + 2);
		else
			return res.getSmallHead(f.headId + 1);
	}
}
