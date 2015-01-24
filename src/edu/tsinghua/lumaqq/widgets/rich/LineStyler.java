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

import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;


/**
 * 管理行样式
 * 
 * @author luma
 */
public class LineStyler implements ILineStyler {
    private LineStyle[] styles;
    private LineStyle defaultStyle;
    
    public LineStyler() {
        styles = new LineStyle[10];
        defaultStyle = LineStyle.INSTANCE;
    }

    /**
     * 扩展数组大小
     */
    private void expandStylesDouble() {
        LineStyle[] newStyles = new LineStyle[styles.length << 1];
        System.arraycopy(styles, 0, newStyles, 0, styles.length);
        styles = newStyles;  
    }
    
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.widgets.ILineStyler#removeLineStyle(int, int)
     */
    public void removeLineStyle(int start, int length) {
        if(start + length < styles.length) 
            System.arraycopy(styles, start + length, styles, start, styles.length - start - length);
    }
    
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.widgets.ILineStyler#insertLineStyle(int, int, int)
     */
    public void insertLineStyle(int startIndex, int numLines, int oldLineCount) {
        while(oldLineCount + numLines > styles.length)
            expandStylesDouble();
        System.arraycopy(styles, startIndex, styles, startIndex + numLines, oldLineCount - startIndex);
        for(int i = startIndex; i < startIndex + numLines; i++)
            styles[i] = null;
    }
    
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.widgets.ILineStyler#getLineStyle(int)
     */
    public LineStyle getLineStyle(int lineIndex) {     
        if(lineIndex >= styles.length)
            expandStylesDouble();       

        LineStyle style = styles[lineIndex];
        if(style == null)
            style = defaultStyle;
        return style;
    }
    
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.widgets.ILineStyler#getLineFont(int)
     */
    public Font getLineFont(int lineIndex) {
        LineStyle style = getLineStyle(lineIndex);
        String symbolic = style.getFontString();
        Font f = JFaceResources.getFont(symbolic);
        if(f == JFaceResources.getDefaultFont()) {
            FontData[] fd = style.createFontData();
            JFaceResources.getFontRegistry().put(symbolic, fd);
        }
        return JFaceResources.getFont(symbolic);
    }
    
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.widgets.ILineStyler#clearLineStyle(int)
     */
    public void clearLineStyle(int lineIndex) {
        styles[lineIndex] = null;
    }
    
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.widgets.ILineStyler#addLineStyle(int, edu.tsinghua.lumaqq.widgets.LineStyle)
     */
    public void addLineStyle(int lineIndex, LineStyle style) {
        while(lineIndex > styles.length)
            expandStylesDouble();
        styles[lineIndex] = style;
    }
    
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.widgets.ILineStyler#clearAll()
     */
    public void clearAll() {
        for(int i = 0; i < styles.length; i++)
            styles[i] = null;
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.widgets.ILineStyler#getDefaultStyle()
     */
    public LineStyle getDefaultStyle() {
        return defaultStyle;
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.widgets.ILineStyler#setDefaultStyle(edu.tsinghua.lumaqq.widgets.LineStyle)
     */
    public void setDefaultStyle(LineStyle style) {
        this.defaultStyle = style;
    }
}
