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


/**
 * 文本内容管理接口
 * 
 * @author luma
 */
public interface IRichContent {
    /** 缺省表情 */
    public static final char DEFAULT_FACE_TAG = 0x14;
    /** 自定义表情 */
    public static final char CUSTOM_FACE_TAG = 0x15;
    
    /** 缺省表情的索引是代码号，长度1个char */
    public static final int DEFAULT_FACE_INDEX_LENGTH = 1;
    /** 自定义表情的索引是md5的字符串形式，所以长度32个char */
    public static final int CUSTOM_FACE_INDEX_LENGTH = 32;
    
    /**
     * 得到行总数，如果没有行存在，应该返回1
     * <p>
     *
     * @return 行数，例如：
     * <ul>
     * <li>	text value ==> getLineCount		
     * <li>	null ==> 1		
     * <li>	"" ==> 1		
     * <li>	"a\n" ==> 2			
     * <li>	"\n\n" ==> 3			
     * </ul>
     */
    public int getLineCount();
    
    /**
     * 根据行号得到一行的字符串，不包括回车换行符
     * <p>
     *
     * @param lineIndex 
     * 		行号
     * @return 
     * 		行字符串
     */
    public String getLine(int lineIndex);
    
    /**
     * Return the line index at the given character offset.
     * <p>
     *
     * @param offset offset of the line to return. The first character of the 
     * 	document is at offset 0.  An offset of getLength() is valid and should 
     *	answer the number of lines. 
     * @return the line index. The first line is at index 0.  If the character 
     * 	at offset is a delimiter character, answer the line index of the line 
     * 	that is delimited. 
     * 	For example, if text = "\r\n\r\n", and delimiter = "\r\n", then:
     * <ul>
     * <li>getLineAtOffset(0) == 0
     * <li>getLineAtOffset(1) == 0
     * <li>getLineAtOffset(2) == 1
     * <li>getLineAtOffset(3) == 1
     * <li>getLineAtOffset(4) == 2
     * </ul>
     */
    public int getLineAtOffset(int offset);
    
    /**
     * Return the character offset of the first character of the given line.
     * <p>
     * <b>NOTE:</b> When there is no text (i.e., no lines), getOffsetAtLine(0) 
     * is a valid call that should return 0.
     * </p>
     *
     * @param lineIndex index of the line. The first line is at index 0.
     * @return offset offset of the first character of the line. The first 
     * 	character of the document is at offset 0.  The return value should 
     * 	include line delimiters.  
     * 	For example, if text = "\r\ntest\r\n" and delimiter = "\r\n", then:
     * <ul>
     * <li>getOffsetAtLine(0) == 0
     * <li>getOffsetAtLine(1) == 2
     * <li>getOffsetAtLine(2) == 8
     * </ul>
     */
    public int getLineStartOffset(int lineIndex);
    
    /**
     * Returns a string representing the content at the given range.
     * <p>
     *
     * @param start the start offset of the text to return. Offset 0 is the 
     * 	first character of the document.
     * @param length the length of the text to return
     * @return the text at the given range
     */
    public String getText(int start, int length);
    
    /**
     * @return
     * 		字符总数
     */
    public int getCharCount();
    
    /**
     * 把start和end之间的文本替换成text，不包括end
     * 
     * @param event
     * 		Event对象，event的可用字段包括start, end和text
     */
    public void replaceText(TextEvent event);
    
    /**
     * Set text to "text".
     * Implementors have to send a <code>TextChangedEvent</code> to the 
     * textSet method of the TextChangeListeners that were added using 
     * <code>addTextChangeListener</code>.
     * <p>
     *
     * @param text the new text
     * @see TextChangeListener
     */
    public void setText(String text);
    
    /**
     * 检测偏移i处的字符是否是分行符
     * 
     * @param i
     * 		偏移
     * @return
     * 		true表示是分行符号
     */
    public boolean isDelimit(int i);
    
    /**
     * 检测偏移i处的字符是否图片tag
     * 
     * @param i
     * 		偏移
     * @return
     * 		true表示是图片tag
     */
    public boolean isImageTag(int i);
    
    /**
     * @return
     * 		LineStyler对象
     */
    public ILineStyler getLineStyler();
    
    /**
     * @return
     * 		helper of content
     */
    public ContentHelper getContentHelper();
    
    /**
     * 设置某行的样式
     * 
     * @param lineIndex
     * 		硬行行号
     * @param style
     * 		样式
     */
    public void setLineStyle(int lineIndex, LineStyle style);
    
    /**
     * 设置缺省样式
     * 
     * @param style
     */
    public void setDefaultStyle(LineStyle style);
}
