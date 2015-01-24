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

import edu.tsinghua.lumaqq.models.Group;
import edu.tsinghua.lumaqq.models.Model;
import edu.tsinghua.lumaqq.models.User;
import edu.tsinghua.lumaqq.ui.MainShell;
import edu.tsinghua.lumaqq.ui.helper.DragHelper;
import edu.tsinghua.lumaqq.widgets.qstyle.QItem;
import edu.tsinghua.lumaqq.widgets.qstyle.QTree;
import edu.tsinghua.lumaqq.widgets.qstyle.Slat;

import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.DropTargetListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Control;

/**
 * 处理放下一个好友的事件，这个可以在某个组上发生（用于树形模式），也可以在
 * 某个slat上发生（用于传统模式）
 * 
 * @author luma
 */
public class GroupDropTargetListener implements DropTargetListener {
	private MainShell main;
	
	public GroupDropTargetListener(MainShell main) {
		this.main = main;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.swt.dnd.DropTargetListener#dragEnter(org.eclipse.swt.dnd.DropTargetEvent)
	 */
	public void dragEnter(DropTargetEvent e) {
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.dnd.DropTargetListener#dragLeave(org.eclipse.swt.dnd.DropTargetEvent)
	 */
	public void dragLeave(DropTargetEvent e) {
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.dnd.DropTargetListener#dragOperationChanged(org.eclipse.swt.dnd.DropTargetEvent)
	 */
	public void dragOperationChanged(DropTargetEvent e) {
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.dnd.DropTargetListener#dragOver(org.eclipse.swt.dnd.DropTargetEvent)
	 */
	public void dragOver(DropTargetEvent e) {
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.dnd.DropTargetListener#drop(org.eclipse.swt.dnd.DropTargetEvent)
	 */
	public void drop(DropTargetEvent e) {
		Control control = ((DropTarget)e.getSource()).getControl();
		if(control instanceof QTree) {
			QTree tree = (QTree)control;
			Point loc = tree.toControl(e.x, e.y);
			QItem destItem = tree.getItem(loc.x, loc.y);	
			if(destItem == null)
				return;
			Model destModel = (Model)destItem.getData();
			QItem srcItem = DragHelper.getDraggedItem();
			Model srcModel = (Model)srcItem.getData();
			switch(destModel.type) {
				case GROUP:
					switch(srcModel.type) {
						case USER:
							main.getBlindHelper().moveUser((User)srcModel, (Group)destModel);
							break;
						default:
							break;
					}
					break;
				default:
					break;
			}
		} else if(control instanceof Slat) {
			Group g = main.getBlindHelper().getSlatGroup(control);
			if(g == null)
				return;
			
			QItem srcItem = DragHelper.getDraggedItem();
			Model srcModel = (Model)srcItem.getData();
			switch(srcModel.type) {
				case USER:
					main.getBlindHelper().moveUser((User)srcModel, g);
					break;
				default:
					break;
			}
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.dnd.DropTargetListener#dropAccept(org.eclipse.swt.dnd.DropTargetEvent)
	 */
	public void dropAccept(DropTargetEvent e) {
	}
}
