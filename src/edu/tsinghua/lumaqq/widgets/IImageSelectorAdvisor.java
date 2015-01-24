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
package edu.tsinghua.lumaqq.widgets;

import org.eclipse.swt.graphics.Image;

/**
 * 用来为图片选择窗口提供各种参数和内容
 * 
 * @author luma
 */
public interface IImageSelectorAdvisor {
    /**
     * @return
     * 		组个数
     */
    public int getGroupCount();
    
    /**
     * 得到组中图片数目
     * 
     * @param group
     * 		组序号
     * @return
     * 		图片数目
     */
    public int getImageCount(int group);
    
    /**
     * 得到图片的内码
     * 
     * @param group
     * 		组索引
     * @param sequence
     * 		图片组内序号
     * @return
     * 		图片内部表示代码
     */
    public int getImageCode(int group, int sequence);
    
    /**
     * 得到组名
     * 
     * @param group
     * 		组索引
     * @return
     * 		组名
     */
    public String getGroupName(int group);
    
    /**
     * 根据组索引和序号得到Image对象
     * 
     * @param group
     * 		组索引
     * @param sequence
     * 		序号
     * @return
     * 		Image，如果没有，返回null
     */
    public Image getImage(int group, int sequence);
    
    /**
     * 检查该组是否允许分页，不允许分页的组只显示1页
     * 
     * @param group
     * 		组索引
     * @return
     * 		true表示允许
     */
    public boolean canPagination(int group);
    
    /**
     * 检查该组是否允许跳到下一页
     * 
     * @param group
     * 		组索引
     * @param currentPage
     * 		当前页
     * @return
     * 		true表示允许
     */
    public boolean canNext(int group, int currentPage);
    
    /**
     * 检查该组是否允许回到上一页
     * 
     * @param group
     * 		组索引
     * @param currentPage
     * 		当前页
     * @return
     * 		true表示允许
     */
    public boolean canPrevious(int group, int currentPage);
    
    /**
     * @return
     * 		true表示显示附加的link组件
     */
    public boolean isAuxiliaryLinkVisible();
    
    /**
     * link组件被点击时调用此方法
     * 
     * @param fss
     * 		图像选择窗口
     */
    public void doLink(ImageSelector fss);
    
    /**
     * @return		
     * 		返回link组件的文本
     */
    public String getLinkLabel();
    
    /**
     * @return
     * 		返回网格大小
     */
    public int getGridSize();
    
    /**
     * @return
     * 		返回图像大小
     */
    public int getImageSize();
    
    /**
     * @return
     * 		行数
     */
    public int getRow();
    
    /**
     * @return
     * 		列数
     */
    public int getCol();
    
    /**
     * @return
     * 		边缘宽度
     */
    public int getMargin();
}
