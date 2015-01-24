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
package edu.tsinghua.lumaqq.qq.beans;

import java.nio.ByteBuffer;

import edu.tsinghua.lumaqq.qq.QQ;
import edu.tsinghua.lumaqq.qq.Util;

/**
 * 字体信息Bean
 * 
 * @author luma
 * @see edu.tsinghua.lumaqq.qq.packets.out.SendIMPacket
 */
public class FontStyle {
    /** 字体属性 */
    private static final byte NONE = 0x00;
    private static final byte BOLD = 0x20;
    private static final byte ITALIC = 0x40;
    private static final byte UNDERLINE = (byte)0x80;

    private int red, green, blue;
    private String fontName;
    private boolean bold, italic, underline;
    private int fontSize;
    private char fontFlag; // 用来表示bold, italic, underline, fontSize的组合结果
    private String encoding;
    private char encodingCode;
    
    public FontStyle() {
        encodingCode = QQ.QQ_CHARSET_GB;
        encoding = "GBK";
        fontName = "宋体";
        red = green = blue = 0;
        bold = italic = underline = false;
        fontSize = 0x9;
        fontFlag = 0x9;
    }

	public void writeBean(ByteBuffer buf) {
	    buf.putChar(fontFlag);
	    // 字体颜色红绿篮
	    buf.put((byte)red);
	    buf.put((byte)green);
	    buf.put((byte)blue);
	    // 一个未知字节
	    buf.put((byte)0);
	    // 消息编码
	    buf.putChar(encodingCode);
	    // 字体
	    byte[] fontBytes = Util.getBytes(fontName);
	    buf.put(fontBytes);
	    // 字体属性长度（包括本字节）
	    buf.put((byte)(fontBytes.length + 9));   
	}
	
	public void readBean(ByteBuffer buf) {
        fontFlag = buf.getChar();
        // 分析字体属性到具体的变量
        // 字体大小
        fontSize = fontFlag & 0x1F;
        // 组体，斜体，下画线
        bold = (fontFlag & 0x20) != 0;
        italic = (fontFlag & 0x40) != 0;
        underline = (fontFlag & 0x80) != 0;
        // 字体颜色rgb
        red = buf.get() & 0xFF;
        green = buf.get() & 0xFF;
        blue = buf.get() & 0xFF;
        // 1个未知字节
        buf.get();
        // 消息编码，这个据Gaim QQ的注释，这个字段用处不大，说是如果在一个英文windows
        // 里面输入了中文，那么编码是英文的，按照这个encoding来解码就不行了
        // 不过我们还是得到这个字段吧，后面我们采用先缺省GBK解码，不行就这个encoding
        // 解码，再不行就ISO-8859-1的方式
        encodingCode = buf.getChar();
        encoding = Util.getEncodingString(encodingCode);
        // 字体名称，字体名称也有中文的也有英文的，所以。。先来试试缺省的
        fontName = Util.getString(buf, buf.remaining() - 1);   
	}
	
    /**
     * @return Returns the blue.
     */
    public int getBlue() {
        return blue;
    }
    
    /**
     * @param blue The blue to set.
     */
    public void setBlue(int blue) {
        this.blue = blue;
    }
    
    /**
     * @return Returns the bold.
     */
    public boolean isBold() {
        return bold;
    }
    
    /**
     * @param bold The bold to set.
     */
    public void setBold(boolean bold) {
        this.bold = bold;
        fontFlag &= 0xDF;
        fontFlag |= bold ? BOLD : NONE;
    }
    
    /**
     * @return Returns the encoding.
     */
    public String getEncoding() {
        return encoding;
    }
    
    /**
     * @return Returns the fontName.
     */
    public String getFontName() {
        return fontName;
    }
    
    /**
     * @param fontName The fontName to set.
     */
    public void setFontName(String fontName) {
        this.fontName = fontName;
    }
    
    /**
     * @return Returns the green.
     */
    public int getGreen() {
        return green;
    }
    
    /**
     * @param green The green to set.
     */
    public void setGreen(int green) {
        this.green = green;
    }
    
    /**
     * @return Returns the italic.
     */
    public boolean isItalic() {
        return italic;
    }
    
    /**
     * @param italic The italic to set.
     */
    public void setItalic(boolean italic) {
        this.italic = italic;
        fontFlag &= 0xBF;
        fontFlag |= italic ? ITALIC : NONE;
    }
    
    /**
     * @return Returns the red.
     */
    public int getRed() {
        return red;
    }
    
    /**
     * @param red The red to set.
     */
    public void setRed(int red) {
        this.red = red;
    }
    
    /**
     * @return Returns the underline.
     */
    public boolean isUnderline() {
        return underline;
    }
    
    /**
     * @param underline The underline to set.
     */
    public void setUnderline(boolean underline) {
        this.underline = underline;
        fontFlag &= 0x7F;
        fontFlag |= underline ? UNDERLINE : NONE;
    }
    
    /**
     * @return Returns the fontSize.
     */
    public int getFontSize() {
        return fontSize;
    }
    
    /**
     * @param fontSize The fontSize to set.
     */
    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
        fontSize &= 0x1F;
        fontFlag &= 0xE0;
        fontFlag |= fontSize;
    }

	/**
	 * @return Returns the encodingCode.
	 */
	public char getEncodingCode() {
		return encodingCode;
	}

	/**
	 * @param encodingCode The encodingCode to set.
	 */
	public void setEncodingCode(char encodingCode) {
		this.encodingCode = encodingCode;
	}
}
