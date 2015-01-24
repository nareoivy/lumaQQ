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
 * Wrap content的行样式管理器
 * 
 * @author luma
 */
public class WrappedLineStyler implements ILineStyler {
    private ILineStyler unwrappedStyler;
    private IWrappedRichContent content;

    public WrappedLineStyler(IWrappedRichContent content, ILineStyler unwrappedStyler) {
        this.content = content;
        this.unwrappedStyler = unwrappedStyler;
    }
    
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.widgets.ILineStyler#getLineStyle(int)
     */
    public LineStyle getLineStyle(int lineIndex) {
        return unwrappedStyler.getLineStyle(content.getHardLineIndex(lineIndex));
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.widgets.ILineStyler#getLineFont(int)
     */
    public Font getLineFont(int lineIndex) {
        return unwrappedStyler.getLineFont(content.getHardLineIndex(lineIndex));
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.widgets.ILineStyler#clearLineStyle(int)
     */
    public void clearLineStyle(int lineIndex) {
        unwrappedStyler.clearLineStyle(content.getHardLineIndex(lineIndex));
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.widgets.ILineStyler#addLineStyle(int, edu.tsinghua.lumaqq.widgets.LineStyle)
     */
    public void addLineStyle(int lineIndex, LineStyle style) {
        unwrappedStyler.addLineStyle(content.getHardLineIndex(lineIndex), style);
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.widgets.ILineStyler#clearAll()
     */
    public void clearAll() {
        unwrappedStyler.clearAll();
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.widgets.ILineStyler#removeLineStyle(int, int)
     */
    public void removeLineStyle(int start, int length) {
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.widgets.ILineStyler#insertLineStyle(int, int, int)
     */
    public void insertLineStyle(int startIndex, int numLines, int oldLineCount) {
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.widgets.ILineStyler#setDefaultStyle(edu.tsinghua.lumaqq.widgets.LineStyle)
     */
    public void setDefaultStyle(LineStyle style) {
        unwrappedStyler.setDefaultStyle(style);
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.widgets.ILineStyler#getDefaultStyle()
     */
    public LineStyle getDefaultStyle() {
        return unwrappedStyler.getDefaultStyle();
    }
}
