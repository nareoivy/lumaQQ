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
package edu.tsinghua.lumaqq.widgets.rich;

import org.eclipse.swt.graphics.Font;

/**
 * 行样式管理器
 * 
 * @author luma
 */
public interface ILineStyler {
    /**
     * 设置缺省style
     * 
     * @param style
     */
    public void setDefaultStyle(LineStyle style);
    
    /**
     * @return
     * 		缺省行样式
     */
    public LineStyle getDefaultStyle();
    
    /**
     * 得到行样式
     * 
     * @param lineIndex
     * 		行号
     * @return
     * 		LineStyle
     */
    public LineStyle getLineStyle(int lineIndex);

    /**
     * 某行的字体
     * 
     * @param lineIndex
     * 		行号
     * @return
     * 		行字体
     */
    public Font getLineFont(int lineIndex);

    /**
     * 清楚某行的样式
     * 
     * @param lineIndex
     * 		行号
     */
    public void clearLineStyle(int lineIndex);

    /**
     * 添加一个行样式
     * 
     * @param lineIndex
     * 		行号
     * @param style
     * 		LineStyle
     */
    public void addLineStyle(int lineIndex, LineStyle style);
    
    /**
     * 清除start开始的length长度行样式数据
     * 
     * @param start
     * 		起始行号
     * @param length
     * 		要清楚的行数
     */
    public void removeLineStyle(int start, int length);
    
    /**
     * 插入一些空间
     * 
     * @param startIndex
     * 		开始索引
     * @param numLines
     * 		要插入的空间数
     * @param oldLineCount
     * 		没插入前的行数
     */
    public void insertLineStyle(int startIndex, int numLines, int oldLineCount);

    /**
     * 清除所有行样式
     */
    public void clearAll();
}