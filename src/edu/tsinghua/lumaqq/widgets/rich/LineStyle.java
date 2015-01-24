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
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.widgets.Display;

/**
 * 行的样式，为了简化编程，这里规定style只应用于一行
 * 
 * @author luma
 */
public class LineStyle {
    public static final LineStyle INSTANCE = new LineStyle();

    public Color foreground;
    public Color background;

    // MAY BE SWT.NORMAL OR SWT.BOLD
    public int fontStyle = SWT.NORMAL;
    // 缺省字体大小
    public int fontSize = 9;
    // 字体名称
    public String fontName = "宋体";
    
    // 为了照顾不同平台之间的字体不一致问题，在这里得到缺省字体大小，对于
    // 小于系统缺省字体大小的，一律设置为缺省大小
    private static int DEFAULT_FONT_SIZE;
    static {
        DEFAULT_FONT_SIZE = JFaceResources.getDefaultFont().getFontData()[0].getHeight();
    }

    /**
     * 创建一个缺省的行样式
     */
    public LineStyle() {
        fontStyle = SWT.NORMAL;
        fontSize = (9 < DEFAULT_FONT_SIZE) ? DEFAULT_FONT_SIZE : 9;
        fontName = "宋体";        
    }

    /**
     * 创建一个新的行样式
     * <p>
     * 
     * @param foreground
     *      行前景色
     * @param background
     *      行背景色
     */
    public LineStyle(Color foreground, Color background) {
        this();
        this.foreground = foreground;
        this.background = background;
    }

    /**
     * 创建一个新的行样式
     * <p>
     * 
     * @param foreground
     *      行前景色
     * @param background
     *      行背景色
     * @param fontName
     * 		字体名称
     * @param fontStyle
     * 		字体样式
     * @param fontSize
     * 		字体大小
     */
    public LineStyle(Color foreground,
            Color background, String fontName, int fontStyle, int fontSize) {
        this.foreground = foreground;
        this.background = background;
        this.fontName = fontName;
        this.fontStyle = fontStyle;
        this.fontSize = (fontSize < DEFAULT_FONT_SIZE) ? DEFAULT_FONT_SIZE : fontSize;
    }

    /**
     * Compare the specified object to this StyleRange and answer if the two are
     * equal. The object must be an instance of StyleRange and have the same
     * field values.
     * <p>
     * 
     * @param object
     *            the object to compare with this object
     * @return true if the objects are equal, false otherwise
     */
    @Override
	public boolean equals(Object object) {
        LineStyle style;
        if (object == this)
            return true;
        if (object instanceof LineStyle)
            style = (LineStyle) object;
        else
            return false;
        if (this.foreground != null) {
            if (!this.foreground.equals(style.foreground))
                return false;
        } else if (style.foreground != null)
            return false;
        if (this.background != null) {
            if (!this.background.equals(style.background))
                return false;
        } else if (style.background != null)
            return false;
        if(!this.fontName.equals(style.fontName))
            return false;
        if (this.fontStyle != style.fontStyle)
            return false;
        if(this.fontSize != style.fontSize)
            return false;
        return true;
    }

    /**
     * Returns an integer hash code for the receiver. Objects which are equal
     * answer the same value for this method.
     * <p>
     * 
     * @return the receiver's hash
     */
    @Override
	public int hashCode() {
        int code = 0;
        if (foreground != null)
            code += foreground.hashCode();
        if (background != null)
            code += background.hashCode();
        code += fontName.hashCode();
        code += fontSize;
        return code + fontStyle;
    }

    /**
     * Returns whether or not the receiver is unstyled (i.e., does not have any
     * style attributes specified).
     * <p>
     * 
     * @return true if the receiver is unstyled, false otherwise.
     */
    public boolean isUnstyled() {
        if (this.foreground != null)
            return false;
        if (this.background != null)
            return false;
        if (this.fontStyle != SWT.NORMAL)
            return false;
        if(!this.fontName.equals("宋体"))
            return false;
        if(this.fontSize != 9)
            return false;
        return true;
    }

    /**
     * Compares the specified object to this StyleRange and answer if the two
     * are similar. The object must be an instance of StyleRange and have the
     * same field values for except for start and length.
     * <p>
     * 
     * @param object
     *            the object to compare with this object
     * @return true if the objects are similar, false otherwise
     */
    public boolean similarTo(LineStyle style) {
        if (this.foreground != null) {
            if (!this.foreground.equals(style.foreground))
                return false;
        } else if (style.foreground != null)
            return false;
        if (this.background != null) {
            if (!this.background.equals(style.background))
                return false;
        } else if (style.background != null)
            return false;
        if(!this.fontName.equals(style.fontName))
            return false;
        if (this.fontStyle != style.fontStyle)
            return false;
        if(this.fontSize != style.fontSize)
            return false;
        return true;
    }

    /**
     * Answers a new StyleRange with the same values as this StyleRange.
     * <p>
     * 
     * @return a shallow copy of this StyleRange
     */
    @Override
	public Object clone() {
        Color fg = (foreground == null) ? null : new Color(Display.getCurrent(), foreground.getRGB());        
        Color bg = (background == null) ? null : new Color(Display.getCurrent(), foreground.getRGB());        
        LineStyle style = new LineStyle(fg, bg, fontName, fontStyle, fontSize);
        return style;
    }
    
    /**
     * @return
     * 		字体字符串
     */
    public String getFontString() {
        StringBuffer buf = new StringBuffer();
        buf.append(fontName + '_');
        buf.append(fontSize);
        if((fontStyle & SWT.BOLD) != 0)
            buf.append("_bold");
        if((fontStyle & SWT.ITALIC) != 0)
            buf.append("_italic");
        return buf.toString();
    }
    
    /**
     * @return
     * 		字体
     */
    public FontData[] createFontData() {
        FontData fd = new FontData();
        if(fontName == null) {
        	fontName = "宋体";
        	fontSize = 9;
        	fontStyle = SWT.NORMAL;
        }
        fd.setName(fontName);
        fd.setHeight(fontSize);
        fd.setStyle(fontStyle);
        return new FontData[] { fd };
    }
}