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

import edu.tsinghua.lumaqq.models.Model;
import edu.tsinghua.lumaqq.ui.helper.DragHelper;
import edu.tsinghua.lumaqq.widgets.qstyle.QItem;
import edu.tsinghua.lumaqq.widgets.qstyle.QTree;

import org.eclipse.swt.dnd.DragSource;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.DragSourceListener;

/**
 * 群组中的拖动源事件处理器，群组里面主要要处理成员在不同组织之间的拖动
 * 
 * @author luma
 */
public class ClusterDragSourceListener implements DragSourceListener {
	public void dragStart(DragSourceEvent e) {
		QTree tree = (QTree)((DragSource)e.getSource()).getControl();
		QItem item = tree.getItemUnderMouse();
		if(item == null)
			e.doit = false;
		else {
			Model model = (Model)item.getData();
			switch(model.type) {
				case USER:
					DragHelper.setDraggedItem(item);
					break;
				default:
					e.doit = false;
					break;
			}
		}
	}

	public void dragSetData(DragSourceEvent e) {
		e.data = "dummy";
	}

	public void dragFinished(DragSourceEvent event) {
	}
}
