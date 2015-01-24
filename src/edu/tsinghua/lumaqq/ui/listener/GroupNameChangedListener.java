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

import org.eclipse.swt.widgets.Control;

import edu.tsinghua.lumaqq.models.Group;
import edu.tsinghua.lumaqq.ui.MainShell;
import edu.tsinghua.lumaqq.ui.helper.BlindHelper;
import edu.tsinghua.lumaqq.widgets.qstyle.ISlatListener;
import edu.tsinghua.lumaqq.widgets.qstyle.SlatEvent;

/**
 * 监视组名称改变事件 
 * 
 * @author luma
 */
public class GroupNameChangedListener implements ISlatListener {
	private MainShell main;
	
	public GroupNameChangedListener(MainShell main) {
		this.main = main;
	}
	
	public void textChanged(SlatEvent e) {
		BlindHelper blindHelper = main.getBlindHelper();
		Group g = blindHelper.getSlatGroup((Control)e.getSource());
		if(g == null)
			return;
		g.name = e.newText;
		blindHelper.refreshModel(g);
		if(!blindHelper.isTreeMode())
			main.getBlindHelper().refreshAll();
	}
}
