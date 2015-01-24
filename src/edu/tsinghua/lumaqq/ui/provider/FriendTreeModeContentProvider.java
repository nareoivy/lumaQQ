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
package edu.tsinghua.lumaqq.ui.provider;

import java.util.ArrayList;
import java.util.List;

import edu.tsinghua.lumaqq.models.Group;
import edu.tsinghua.lumaqq.models.Model;
import edu.tsinghua.lumaqq.models.Type;
import edu.tsinghua.lumaqq.models.User;
import edu.tsinghua.lumaqq.ui.MainShell;
import edu.tsinghua.lumaqq.ui.helper.BlindHelper;
import edu.tsinghua.lumaqq.widgets.qstyle.IQTreeContentProvider;

/**
 * 树形模式时的好友内容提供者
 * 
 * @author luma
 */
public class FriendTreeModeContentProvider implements IQTreeContentProvider<Model> {	
	private List<Model> temp;
	private static final Model[] EMPTY = new Model[0];
	private MainShell main;
	
	public FriendTreeModeContentProvider(MainShell main) {
		this.main = main;
		temp = new ArrayList<Model>();
	}
	
	public Model[] getElements(Object inputElement) {
		if(!(inputElement instanceof BlindHelper))
			return EMPTY;
		
		BlindHelper helper = (BlindHelper)inputElement;
		temp.clear();
		temp.add(helper.getMyFriendGroup());
		temp.addAll(helper.getNormalGroups());
		temp.add(helper.getStrangerGroup());
		if(main.getOptionHelper().isShowBlacklist())
			temp.add(helper.getBlacklistGroup());
		return temp.toArray(EMPTY);
	}

	public Model[] getChildren(Model parent) {
		if(parent.type == Type.GROUP)
			return ((Group)parent).users.toArray(EMPTY);
		else
			return EMPTY;
	}

	public Model getParent(Model child) {
		if(child.type == Type.USER)
			return ((User)child).group;
		else
			return null;
	}

	public boolean hasChildren(Model parent) {
		return parent.type == Type.GROUP;
	}
}
