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

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

/**
 * 用搜索结果做为Content Provider
 * 
 * @author luma
 */
public class SearchResultContentProvider implements IStructuredContentProvider {
    private SearchResult result;
    private int cursor;
    private boolean showAll;
    
    public SearchResultContentProvider(SearchResult r) {
        result = r;
        cursor = 0;
    }
    
    /**
     * @return
     * 		true如果还有下一页
     */
    public boolean hasNext() {
        return cursor < result.getPageCount() - 1;
    }
    
    /**
     * @return
     * 		true如果还有上一页
     */
    public boolean hasPrevious() {
        return cursor > 0;
    }
    
    /**
     * @return
     * 		当前页号
     */
    public int getCurrentPageId() {
        return cursor;
    }
    
    /**
     * 到最后一页
     */
    public void last() {
        cursor = result.getPageCount() - 1;
        if(cursor < 0)
            cursor = 0;
    }
    
    /**
     * 到第一页
     */
    public void first() {
        cursor = 0;
    }
    
    /**
     * 移动到上一页 
     */
    public void next() {
        showAll = false;
        cursor++;
    }
    
    /**
     * 移动到上一页
     */
    public void previous() {
        showAll = false;
        cursor--;
    }

    /* (non-Javadoc)
     * @see org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java.lang.Object)
     */
    public Object[] getElements(Object inputElement) {
        if(showAll)
            return result.getAll().toArray();
        else
            return result.getPage(cursor).toArray();
    }

    /* (non-Javadoc)
     * @see org.eclipse.jface.viewers.IContentProvider#dispose()
     */
    public void dispose() {
    }

    /* (non-Javadoc)
     * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
     */
    public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
    }
    
    /**
     * @param showAll The showAll to set.
     */
    public void setShowAll(boolean showAll) {
        this.showAll = showAll;
    }
    /**
     * @return Returns the showAll.
     */
    public boolean isShowAll() {
        return showAll;
    }
}
