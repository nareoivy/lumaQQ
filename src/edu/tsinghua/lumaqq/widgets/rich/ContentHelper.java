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
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;

/**
 * 提供一些帮助方法
 * 
 * @author luma
 */
public class ContentHelper {
    private IRichContent content;
    private ILineStyler styler;
    private ImageResolver resolver;
    
    // 行高数组
    private int[] lineHeight;
    
    public ContentHelper(IRichContent content, ImageResolver resolver) {
        this.resolver = resolver;
        this.content = content;
        this.styler = content.getLineStyler();
        lineHeight = new int[10];
    }
        
    /**
     * 成倍扩展行高数组
     */
    public void expandLineHeightDouble() {
        int[] newLineHeight = new int[lineHeight.length << 1];
        System.arraycopy(lineHeight, 0, newLineHeight, 0, lineHeight.length);
        lineHeight = newLineHeight;
    }
    
    /**
     * 给定一行的行号和行内偏移，计算这个偏移的X坐标，比如这个偏移是1，那么就是这一行
     * 第一个字符的宽度
     * 
     * @param gc
     * 		GC
     * @param lineIndex
     * 		行号
     * @param offsetInLine
     * 		行内偏移
     * @return
     */
    public int getXAtOffset(GC gc, int lineIndex, int offsetInLine) {
        StringBuffer sb = new StringBuffer(content.getLine(lineIndex).substring(0, offsetInLine));
        return getWidth(gc, sb, lineIndex);
    }    
    
    /**
     * 得到一个字符串的宽度，使用lineIndex行的style来检查
     * 
     * @param gc
     * 		GC
     * @param line
     * 		字符串缓冲区
     * @param lineIndex
     * 		行号
     * @return
     * 		宽度
     */
    public int getWidth(GC gc, StringBuffer line, int lineIndex) {
        int width = 0;
		int i = 0;
		int j = findNextImage(line, 0);
		for (; j != -1; i = j, j = findNextImage(line, i)) {
			Image image = resolver.getImage(line.charAt(j), line.charAt(j + 1));
			width += image.getBounds().width;
			line.delete(j, j + 2);
		}
		Font oldFont = gc.getFont();
		gc.setFont(styler.getLineFont(lineIndex));
		width += gc.textExtent(line.toString()).x;
		gc.setFont(oldFont);
		return width;
    }
    
    /**
     * 得到x象素位置在某行中的偏移，然后再得到绝对偏移
     * 
     * @param gc
     * 		GC
     * @param lineIndex
     * 		行号
     * @param x
     * 		x象素位置
     * @return
     * 		字符绝对偏移
     */
    public int getOffsetAtX(GC gc, int lineIndex, int x) {
        int lineOffset = content.getLineStartOffset(lineIndex);
        // 先检查x是否小于0
        if(x < 0)
            return lineOffset;
        else {
            x++;
            
            // 得到行长度，如果x大于行长，直接返回
            char[] c = content.getLine(lineIndex).toCharArray();
            StringBuffer sb = new StringBuffer();
            sb.append(c);
            int len = getWidth(gc, sb, lineIndex);
            if(x >= len)
                return lineOffset + c.length;
            else {
                // 先模糊定位到一个偏移附近再查找
                int referenceOffset = c.length * x / len;
                if(referenceOffset > 0)
                    referenceOffset--;
                if(content.isImageTag(lineOffset + referenceOffset - 1))
                    referenceOffset++;
                sb.delete(0, sb.length());
                sb.append(c, 0, referenceOffset);
                len = getWidth(gc, sb, lineIndex);
                boolean iniFlag = x > len;
                boolean flag = iniFlag;
                while(flag == iniFlag) {
                    if(x > len) {
                        if(content.isImageTag(lineOffset + referenceOffset))
                            referenceOffset += 2;
                        else
                            referenceOffset++;
                    } else {
                        if(content.isImageTag(lineOffset + referenceOffset - 2))
                            referenceOffset -= 2;
                        else
                            referenceOffset--;
                    }
                    sb.delete(0, sb.length());
                    sb.append(c, 0, referenceOffset);
                    len = getWidth(gc, sb, lineIndex);
                    flag = x > len;
                }
                if(iniFlag) {
                    referenceOffset--;
                    if(content.isImageTag(lineOffset + referenceOffset - 1))
                        referenceOffset--;
                }
                return lineOffset + referenceOffset;
            }
        }
    }
    
	/**
	 * 找寻下一个图片tag的开始位置
	 * 
	 * @param line
	 * 		行
	 * @param fromIndex
	 * 		从何处开始搜索
	 * @return
	 * 		tag索引位置，或者-1表示没有了
	 */
	public int findNextImage(StringBuffer line, int fromIndex) {
	    int len = line.length();
	    for(int i = fromIndex; i < len; i++) {
	        char c = line.charAt(i);
	        if(c == IRichContent.DEFAULT_FACE_TAG ||
	                c == IRichContent.CUSTOM_FACE_TAG)
	            return i;
	    }
	    return -1;
	}
	
	/**
	 * 找寻下一个图片tag的开始位置
	 * 
	 * @param line
	 * 		行
	 * @param fromIndex
	 * 		从何处开始搜索
	 * @return
	 * 		tag索引位置，或者-1表示没有了
	 */
	public int findNextImage(String line, int fromIndex) {
	    int len = line.length();
	    for(int i = fromIndex; i < len; i++) {
	        char c = line.charAt(i);
	        if(c == IRichContent.DEFAULT_FACE_TAG ||
	                c == IRichContent.CUSTOM_FACE_TAG)
	            return i;
	    }
	    return -1;
	}
	
	/**
	 * 找寻上一个图片tag的开始位置
	 * 
	 * @param line
	 * @param fromIndex
	 * @return
	 */
	public int findPreviousImage(String line, int fromIndex) {
	    for(int i = fromIndex; i >= 0; i--) {
	        char c = line.charAt(i);
	        if(c == IRichContent.DEFAULT_FACE_TAG ||
	                c == IRichContent.CUSTOM_FACE_TAG)
	            return i;
	    }
	    return -1;
	}
    
    /**
     * 清空行高缓存数据
     */
    public void clearLineHeightCache() {
        for(int i = 0; i < lineHeight.length; i++)
            lineHeight[i] = 0;
    }
    
    /**
     * 清楚某一行的行高缓存
     * 
     * @param lineIndex
     * 		行号
     */
    public void clearLineHeightCache(int lineIndex) {
        lineHeight[lineIndex] = 0;
    }
    
    /**
     * 清楚start行到end行的行高数据，用在行删除时
     * 
     * @param start
     * 		开始行号
     * @param length
     * 		要删除的行数
     */
    public void removeLineHeight(int start, int length) {
        if(start + length < lineHeight.length)
            System.arraycopy(lineHeight, start + length, lineHeight, start, lineHeight.length - start - length);
    }
    
    /**
     * 插入一些行高数据空间
     * 
     * @param startIndex
     * 		插入的起始位置
     * @param numLines
     * 		要插入的行数
     * @param oldLineCount
     * 		没插入前的行数
     */
    public void insertLineHeight(int startIndex, int numLines, int oldLineCount) {
        while(oldLineCount + numLines > lineHeight.length)
            expandLineHeightDouble();
        System.arraycopy(lineHeight, startIndex, lineHeight, startIndex + numLines, oldLineCount - startIndex);
        for(int i = startIndex; i < startIndex + numLines; i++)
            lineHeight[i] = 0;
    }
    
    /**
     * 给出绝对y偏移，得到y所在的行数
     * 
     * @param gc
     * 		GC
     * @param y
     * 		y偏移
     * @return
     * 		行号
     */
    public int getLineAtY(GC gc, int y) {
        int i;
        int lineCount = content.getLineCount();
        for(i = 0; i < lineCount && y > 0; i++)
            y -= getLineHeight(gc, i);       
        return (i == 0) ? 0 : (i - 1);
    }

    /**
     * 得到从startLine开始，endLine结束的行的总高度（不包括endLine）
     * 
     * @param gc
     * 		GC
     * @param startLine
     * 		开始行号
     * @param endLine
     * 		结束行号
     * @return
     * 		总高度
     */
    public int getHeightOfLines(GC gc, int startLine, int endLine) {
        int h = 0;
        for(int i = startLine; i < endLine; i++)
            h += getLineHeight(gc, i);
        return h;
    }
    
    /**
     * 得到某行的起始绝对Y偏移
     * 
     * @param gc
     * 		GC
     * @param lineIndex
     * 		行号
     * @return
     * 		绝对Y
     */
    public int getYOfLine(GC gc, int lineIndex) {
        if(lineIndex > content.getLineCount())
            lineIndex = content.getLineCount();
        int y = 0;
        for(int i = 0; i < lineIndex; i++)
            y += getLineHeight(gc, i);
        return y;
    }
    
    /**
     * 得到某行的高度
     * 
     * @param gc
     * 		GC
     * @param lineIndex
     * 		行号
     * @return
     * 		高度, in pixel
     */
    public int getLineHeight(GC gc, int lineIndex) {
        while(lineIndex >= lineHeight.length)
            expandLineHeightDouble();
        
        if(lineHeight[lineIndex] == 0) {
	        String line = content.getLine(lineIndex);
	        Font oldFont = gc.getFont();
	        Font lineFont = styler.getLineFont(lineIndex);
	        gc.setFont(lineFont);
	        int faceHeight = getMaxFaceHeight(IRichContent.CUSTOM_FACE_TAG, line);
            faceHeight = Math.max(faceHeight, getMaxFaceHeight(IRichContent.DEFAULT_FACE_TAG, line));
	        int h = Math.max(faceHeight, gc.textExtent(line).y);
	        gc.setFont(oldFont);
	        lineHeight[lineIndex] = h;
	        return h;            
        } else
            return lineHeight[lineIndex];
    }
        
    /**
     * 在一行中寻找图片的最大高度
     * 
     * @param line
     * @return
     */
    public int getMaxFaceHeight(int tag, String line) {
        int maxHeight = 0;
        int i = line.indexOf(tag);
        for(; i != -1; i = line.indexOf(tag, i + 1)) {
            int fIndex = line.charAt(i + 1);
            Image face = resolver.getImage(tag, fIndex);
            if(face != null)
                maxHeight = Math.max(maxHeight, face.getBounds().height);
        }
        return maxHeight;
    }

    /**
     * 根据一个最大宽度把一行划分成多行
     * 
     * @param gc
     * 		GC
     * @param width
     * 		最大行宽
     * @param lineIndex
     * 		要划分的行号
     * @return
     * 		各个行的绝对起始偏移
     */
    public int[] splitLine(GC gc, int width, int lineIndex) {
        int lineStartOffset = content.getLineStartOffset(lineIndex);
        int lineWidth = getLineWidth(gc, lineIndex);
        if(width >= lineWidth)
            return new int[] { lineStartOffset };
        
        int offsetCount = 0;
        int[] offsets = new int[lineWidth / width + 1];
        offsets[offsetCount++] = lineStartOffset;
        int w = width;
        while(w < lineWidth) {
            int offset = getOffsetAtX(gc, lineIndex, w);
            if(offset <= offsets[offsetCount - 1]) {
                if(content.isImageTag(offset))
	                offset++;
                offset++;
            }
            if(offsetCount >= offsets.length)
                offsets = expandIntArray(offsets);
            offsets[offsetCount++] = offset;
            w = getXAtOffset(gc, lineIndex, offset - lineStartOffset) + width;
        }
        
        if(offsetCount == offsets.length)
            return offsets;
        else {
            int[] ret = new int[offsetCount];
            System.arraycopy(offsets, 0, ret, 0, offsetCount);
            return ret;
        }
    }
    
    /**
     * 扩展行数组
     * 
     * @param array
     * @return
     */
    private int[] expandIntArray(int[] array) {
        int[] newArray;
        if(array.length > 5)
            newArray = new int[array.length + 5];
        else
            newArray = new int[array.length << 1];
        System.arraycopy(array, 0, newArray, 0, array.length);
        return newArray;
    }
    
    /**
     * 得到某一行的宽度
     * 
     * @param gc
     * 		GC
     * @param lineIndex
     * 		行号
     * @return
     * 		宽度
     */
    public int getLineWidth(GC gc, int lineIndex) {
        StringBuffer sb = new StringBuffer(content.getLine(lineIndex));
        return getWidth(gc, sb, lineIndex);
    }
}
