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

import edu.tsinghua.lumaqq.qq.beans.AdvancedUserInfo;
import edu.tsinghua.lumaqq.qq.beans.UserInfo;

/**
 * 搜索结果的排序类，根据指定的属性排序
 * 
 * @author luma
 */
public class SearchUserResultSorter extends ViewerSorter {
    public static final int QQ_NUMBER = 0;
    public static final int NICK = 1;
    public static final int GENDER = 2; 
    public static final int AGE = 3;
    public static final int FROM = 4;
    public static final int STATUS = 5;
    
    private static Collator collator = Collator.getInstance(Locale.CHINESE);
    
    private int flag;
    private int sortOn;
    
    public SearchUserResultSorter() {
        flag = 1;
        sortOn = QQ_NUMBER;
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
        if(e1 instanceof UserInfo && e2 instanceof UserInfo)
            return compareUserInfo((UserInfo)e1, (UserInfo)e2);
        else if(e1 instanceof AdvancedUserInfo && e2 instanceof AdvancedUserInfo)
            return compareAdvancedUserInfo((AdvancedUserInfo)e1, (AdvancedUserInfo)e2);
        else
            return 0;
    }

    /**
     * @param user1
     * @param user2
     * @return
     */
    private int compareAdvancedUserInfo(AdvancedUserInfo user1, AdvancedUserInfo user2) {
        switch(sortOn) {
            case NICK:
                return collator.compare(user1.nick, user2.nick) * flag;
            case GENDER:
                return (user1.genderIndex - user2.genderIndex) * flag;
            case AGE:
                return (user1.age - user2.age) * flag;
            case FROM:
                int r = user1.provinceIndex - user1.provinceIndex;
                if(r == 0)
                    r = user1.cityIndex - user1.cityIndex;
                return r * flag;
            case STATUS:
                if(user1.online == user2.online)
                    return (user1.qqNum - user2.qqNum) * flag;
                else
                    return (user1.online ? -1 : 1) * flag;
            default:
                return (user1.qqNum - user2.qqNum) * flag;
        }
    }

    /**
     * @param user1
     * @param user2
     * @return
     */
    private int compareUserInfo(UserInfo user1, UserInfo user2) {
        switch(sortOn) {
            case NICK:
                return collator.compare(user1.nick, user2.nick) * flag;
            case FROM:
                return collator.compare(user1.province, user2.province) * flag;
            default:
                return (user1.qqNum - user2.qqNum) * flag;
        }
    }
    
    /**
     * @param sortOn The sortOn to set.
     */
    public void setSortOn(int sortOn) {
        this.sortOn = sortOn;
    }
    
    /**
     * @return Returns the sortOn.
     */
    public int getSortOn() {
        return sortOn;
    }
}
