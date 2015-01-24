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

import static edu.tsinghua.lumaqq.resource.Messages.*;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import edu.tsinghua.lumaqq.qq.beans.AdvancedUserInfo;
import edu.tsinghua.lumaqq.qq.beans.ClusterInfo;
import edu.tsinghua.lumaqq.qq.beans.UserInfo;
import edu.tsinghua.lumaqq.resource.Resources;
import edu.tsinghua.lumaqq.ui.helper.BeanHelper;

/**
 * 搜索结构的label provider
 * 
 * @author luma
 */
public class SearchResultLabelProvider extends LabelProvider implements
        ITableLabelProvider {
    /* (non-Javadoc)
     * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnImage(java.lang.Object, int)
     */
    public Image getColumnImage(Object element, int columnIndex) {
        if(element instanceof UserInfo) {
            UserInfo user = (UserInfo)element;
            if(columnIndex == 0)
                return Resources.getInstance().getSmallHead(user.face);
            else
                return null;
        } else if(element instanceof AdvancedUserInfo) {
            AdvancedUserInfo user = (AdvancedUserInfo)element;
            if(columnIndex == 0)
                return Resources.getInstance().getSmallHead(user.face);
            else
                return null;
        } else if(element instanceof ClusterInfo) {
            if(columnIndex == 0)
                return Resources.getInstance().getSmallClusterHead(4);
            else
                return null;
        } else
            return null;
    }

    /* (non-Javadoc)
     * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnText(java.lang.Object, int)
     */
    public String getColumnText(Object element, int columnIndex) {
        if(element instanceof UserInfo) {
            UserInfo user = (UserInfo)element;
            switch(columnIndex) {
                case 0:
                    return String.valueOf(user.qqNum);
                case 1:
                    return user.nick;
                case 4:
                    return user.province;
                default:
                    return "";
            }
        } else if(element instanceof AdvancedUserInfo) {
            AdvancedUserInfo user = (AdvancedUserInfo)element;
            switch(columnIndex) {
                case 0:
                    return String.valueOf(user.qqNum);
                case 1:
                    return user.nick;
                case 2:
                    return BeanHelper.getGender(user.genderIndex);
                case 3:
                    return String.valueOf(user.age);
                case 4:
                    return BeanHelper.getProvince(user.provinceIndex) + ' ' + BeanHelper.getCity(user.provinceIndex, user.cityIndex);
                case 5:
                    return user.online ? status_online : status_offline;
                default:
                    return "";
            }
        } else if(element instanceof ClusterInfo) {
            ClusterInfo cluster = (ClusterInfo)element;
            switch(columnIndex) {
                case 0:
                    return String.valueOf(cluster.externalId);
                case 1:
                    return cluster.name;
                case 2:
                    return String.valueOf(cluster.creator);
                default:
                    return "";
            }
        } else
            return "";
    }
}
