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

import static edu.tsinghua.lumaqq.models.Status.*;

import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;

import edu.tsinghua.lumaqq.models.Cluster;
import edu.tsinghua.lumaqq.models.ClusterType;
import edu.tsinghua.lumaqq.models.Model;
import edu.tsinghua.lumaqq.models.Organization;
import edu.tsinghua.lumaqq.models.Status;
import edu.tsinghua.lumaqq.models.User;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;


/**
 * <pre>
 * 用于对群组排序，群组和好友组是不同的，因为其有一个父子结构，所以排序的策略是：
 * 1. 如果都是群，则先按照类型排，固定群排在上面，然后相同类型的按照名称排
 * 2. 如果一个是群，一个是成员，如果这个成员属于这个群，那肯定成员排在后面，如果
 *    这个成员不属于这个群，则和这个成员的父群比较
 * 3. 如果两个都是成员且两个都在一个群中，按照好友那么排；如果两个在不同的群中，
 *    比较他们的父群
 * 
 * 好友排序的方法，原则是上线的在最前面，离开的在后面，离线或隐身的在最后，如果某个好友有消息
 * 来了，那么把他当作上线状态对待，其次按照昵称排序。
 * </pre>
 * @author luma
 */
public class ModelSorter extends ViewerSorter implements Comparator<Model> {
	public static final ModelSorter INSTANCE = new ModelSorter();
	
    static Collator collator = Collator.getInstance(Locale.CHINESE);
    
    /* (non-Javadoc)
     * @see org.eclipse.jface.viewers.ViewerSorter#compare(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
     */
    @Override
	public int compare(Viewer viewer, Object e1, Object e2) {
        return compare((Model)e1, (Model)e2);
    }
    
	/* (non-Javadoc)
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	public int compare(Model m1, Model m2) {
		switch(m1.type) {
			case GROUP:
				return 0;
			case CLUSTER:
				switch(m2.type) {
					case CLUSTER:
						Cluster c1 = (Cluster)m1;
						Cluster c2 = (Cluster)m2;
						return compareClusterModel(c1, c2);
					default:
						return 0;
				}
			case ORGANIZATION:
				switch(m2.type) {
					case USER:
						return 1;
					case ORGANIZATION:
						return compareChineseString(((Organization)m1).name, ((Organization)m2).name);
					default:
						return 0;
				}
			case USER:
				switch(m2.type) {
					case USER:
						return compareUserModel((User)m1, (User)m2);
					case ORGANIZATION:
						return -1;
					default:
						return 0;
				}
			default:
				return 0;
		}
	}	
	
	/**
	 * 比较两个FriendModel

	 * @param f1
	 * 			好友1的model
	 * @param f2
	 * 			好友2的model
	 * @return
	 * 			根据排序位置返回1,0或者-1
	 */
	private int compareUserModel(User f1, User f2) {
		// 先检查他们是否被pin
		if(f1.pinned && f2.pinned)
			return compareChineseString(f1.displayName, f2.displayName);
		else if(f1.pinned)
			return -1;
		else if(f2.pinned)
			return 1;
		
		// 得到好友状态，如果当前好友有消息未读，则也当成在线状态看待，所以
	    // 免得好友变成隐身之后就下去了，还要去找是哪个好友发了消息
		Status s1 = f1.status;
		Status s2 = f2.status;
		if(f1.hasMessage)
		    s1 = ONLINE;
		if(f2.hasMessage)
		    s2 = ONLINE;
		// 得到显示名
		String _n1 = f1.displayName;
		String _n2 = f2.displayName;
		// 比较
        if(s1 == s2)
        	return compareChineseString(_n1, _n2);
        else if(s1 == ONLINE)
        	return -1;
        else if(s2 == ONLINE)
        	return 1;
        else if(s1 == AWAY)
        	return -1;
        else if(s2 == AWAY)
        	return 1;
        else if(s1 == HIDDEN)
            return -1;
        else if(s2 == HIDDEN)
            return 1;
        else
        	return compareChineseString(_n1, _n2);
	}

	/**
	 * 比较两个ClusterModel
	 * 
	 * @param c1
	 * @param c2
	 * @return
	 */
	private int compareClusterModel(Cluster c1, Cluster c2) {
		if(c1.clusterType == ClusterType.DIALOG_CONTAINER)
			return 1;
		if(c2.clusterType == ClusterType.DIALOG_CONTAINER)
			return -1;
		return compareChineseString(c1.name, c2.name);
	}
	
	/**
	 * 比较中文字符串，比较后的结果按照拼音方式排序
	 * 
	 * @param _n1
	 * 			source
	 * @param _n2
	 * 			dest
	 * @return source大于dest返回1，等于返回0，小于返回-1
	 */
	private int compareChineseString(String _n1, String _n2) {
	    return collator.compare(_n1, _n2);
	}
}
