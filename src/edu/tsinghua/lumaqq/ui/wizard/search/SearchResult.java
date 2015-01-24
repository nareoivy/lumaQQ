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

import java.util.ArrayList;
import java.util.List;

/**
 * 管理搜索结果，负责管理分页
 * 
 * @author luma
 */
public class SearchResult {
    // 分页，每个对象又是一个List，包含了实际的数据
    public List<List<? extends Object>> pages;
    
    private static List<Object> EMPTY = new ArrayList<Object>();    
    
    public SearchResult() {
        pages = new ArrayList<List<? extends Object>>();
    }
    
    /**
     * 得到一页
     * 
     * @param index
     * 		页索引，从0开始，0表示第一页
     * @return
     * 		List
     */
    public List<?> getPage(int index) {
        if(index < 0 || index >= getPageCount())
            return EMPTY;
        return pages.get(index);
    }
    
    /**
     * 添加一页
     * 
     * @param page
     * 		List
     */
    public void addPage(List<? extends Object> page) {
        pages.add(page);
    }
    
    /**
     * 所有的数据放到一个List中返回
     * 
     * @return
     * 		List
     */
    public List<Object> getAll() {
       List<Object> all = new ArrayList<Object>();
	   for(List<? extends Object> c : pages)
		   all.addAll(c);
       return all;
    }
    
    /**
     * @return
     * 		页数
     */
    public int getPageCount() {
        return pages.size();
    }
    
    /**
     * 清楚所有数据
     */
    public void clear() {
        pages.clear();
    }
}
