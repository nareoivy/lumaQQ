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
package edu.tsinghua.lumaqq.ui.wizard.search;

import java.text.Collator;
import java.util.Locale;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;

import edu.tsinghua.lumaqq.qq.beans.ClusterInfo;

/**
 * 对群搜索结果排序
 * 
 * @author luma
 */
public class SearchClusterResultSorter extends ViewerSorter {
    public static final int ID = 0;
    public static final int NAME = 1;
    public static final int CREATOR = 2;
    
    private static Collator collator = Collator.getInstance(Locale.CHINESE);
    
    private int flag;
    private int sortOn;
    
    public SearchClusterResultSorter() {
        flag = 1;
        sortOn = ID;
    }
    
    /**
     * 翻转排序顺序
     */
    public void reverse() {
        flag = -flag;
    }
    
    /* (non-Javadoc)
     * @see org.eclipse.jface.viewers.ViewerSorter#compare(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
     */
    @Override
	public int compare(Viewer viewer, Object e1, Object e2) {
        ClusterInfo cluster1 = (ClusterInfo)e1;
        ClusterInfo cluster2 = (ClusterInfo)e2;
        
        switch(sortOn) {
            case NAME:
                return collator.compare(cluster1.name, cluster2.name) * flag;
            case CREATOR:
                return (cluster1.creator - cluster2.creator) * flag;
            default:
                return (cluster1.externalId - cluster2.externalId) * flag;
        }
    }
    
    /**
     * @return Returns the sortOn.
     */
    public int getSortOn() {
        return sortOn;
    }
    
    /**
     * @param sortOn The sortOn to set.
     */
    public void setSortOn(int sortOn) {
        this.sortOn = sortOn;
    }
}
