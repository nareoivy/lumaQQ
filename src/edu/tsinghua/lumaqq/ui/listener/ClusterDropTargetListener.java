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

import java.util.ArrayList;
import java.util.List;

import edu.tsinghua.lumaqq.models.Cluster;
import edu.tsinghua.lumaqq.models.Dummy;
import edu.tsinghua.lumaqq.models.DummyType;
import edu.tsinghua.lumaqq.models.Model;
import edu.tsinghua.lumaqq.models.Organization;
import edu.tsinghua.lumaqq.models.User;
import edu.tsinghua.lumaqq.qq.beans.Member;
import edu.tsinghua.lumaqq.ui.MainShell;
import edu.tsinghua.lumaqq.ui.helper.DragHelper;
import edu.tsinghua.lumaqq.widgets.qstyle.QItem;
import edu.tsinghua.lumaqq.widgets.qstyle.QTree;

import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.DropTargetListener;
import org.eclipse.swt.graphics.Point;

/**
 * 群组的拖动目标事件处理器
 * 
 * @author luma
 */
public class ClusterDropTargetListener implements DropTargetListener {
	private MainShell main;
	
	public ClusterDropTargetListener(MainShell m) {
		main = m;
	}
	
	public void dragEnter(DropTargetEvent event) {
	}

	public void dragLeave(DropTargetEvent event) {
	}

	public void dragOperationChanged(DropTargetEvent event) {
	}

	public void dragOver(DropTargetEvent event) {
	}

	public void drop(DropTargetEvent e) {
		QTree tree = (QTree)((DropTarget)e.getSource()).getControl();
		Point loc = tree.toControl(e.x, e.y);
		QItem destItem = tree.getItem(loc.x, loc.y);	
		if(destItem == null)
			return;
		Model destModel = (Model)destItem.getData();
		QItem srcItem = DragHelper.getDraggedItem();
		User member = (User)srcItem.getData();
		switch(destModel.type) {
			case ORGANIZATION:
				Organization o = (Organization)destModel;
				if(member.cluster == o.cluster) {
					if(o.id != member.organizationId) 
						moveMember(o.id, o.cluster, member);					
				}
				break;
			case DUMMY:
				Dummy dummy = (Dummy)destModel;
				if(member.cluster == dummy.cluster && dummy.dummyType == DummyType.CLUSTER_ORGANIZATION) {
					if(member.organizationId != 0)
						moveMember(0, dummy.cluster, member);					
				}
				break;
		}
	}
	
	/**
	 * 移动member
	 * 
	 * @param orgId
	 * 		组织id
	 * @param c
	 * 		群
	 * @param member
	 * 		成员
	 */
	private void moveMember(int orgId, Cluster c, User member) {
		List<Member> temp = new ArrayList<Member>();
		member.organizationId = orgId;
		Member m = new Member();
		m.qq = member.qq;
		m.organization = member.organizationId;
		temp.add(m);
		main.getClient().cluster_CommitMemberOrganization(c.clusterId, temp);
		main.getBlindHelper().refreshGroup(c.group);
	}

	public void dropAccept(DropTargetEvent event) {
	}
}
