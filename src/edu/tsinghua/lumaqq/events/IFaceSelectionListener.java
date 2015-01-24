/*
* LumaQQ - Java QQ Client
*
* Copyright (C) 2004 luma <stubma@163.com>
* 					 糊涂虫 <jtdeng518@163.com>
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
package edu.tsinghua.lumaqq.events;

import edu.tsinghua.lumaqq.widgets.IImageSelectorAdvisor;

/**
 * 当用户点击了一个face时，调用此接口定义的方法
 * 
 * @author luma
 */
public interface IFaceSelectionListener {
    /**
     * 选择了一个表情时调用此方法
     * 
     * @param provider
     * 		图片提供者
     * @param group
     * 		表情所属的组号，如果是0，表示是缺省表情
     * @param sequence
     * 		表情的序号，从0开始，从左到右从上到下一路下来
     */
    public void faceSelected(IImageSelectorAdvisor provider, int group, int sequence);
}
