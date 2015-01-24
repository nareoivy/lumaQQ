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
package edu.tsinghua.lumaqq.ui.sorter;

import java.util.Comparator;

import edu.tsinghua.lumaqq.models.Cluster;
import edu.tsinghua.lumaqq.models.Model;
import edu.tsinghua.lumaqq.models.User;

/**
 * 排序最近联系人
 * 
 * @author luma
 */
public class LatestSorter implements Comparator<Model> {
	public static final LatestSorter INSTANCE = new LatestSorter();

	public int compare(Model o1, Model o2) {
		long time1 = 0, time2 = 0;
		if(o1 instanceof User)
			time1 = ((User)o1).lastMessageTime;
		else if(o1 instanceof Cluster)
			time1 = ((Cluster)o1).lastMessageTime;
		
		if(o2 instanceof User)
			time2 = ((User)o2).lastMessageTime;
		else if(o2 instanceof Cluster)
			time2 = ((Cluster)o2).lastMessageTime;
		
		long delta = time1 - time2;
		if(delta == 0)
			return 0;
		else
			return (delta > 0) ? -1 : 1;
	}

}
