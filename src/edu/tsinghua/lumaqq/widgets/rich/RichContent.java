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

import org.eclipse.swt.SWT;

/**
 * IRichContent的缺省实现
 * 
 * @author luma
 */
public class RichContent implements IRichContent {   
    
    private ILineStyler styler;
    private ContentHelper helper;
    
    // 缓冲区
    // 对于一个图像，其会占用两个char，前一个是tag，后一个是索引
    private StringBuffer buffer;
    // 行的起始偏移和长度
    private int[][] lines;
    // 当前行数
    private int lineCount;
    
    /**
     * 创建一个RichContent对象
     */
    public RichContent(ImageResolver resolver) {
        buffer = new StringBuffer();
        lines = new int[10][2];
        lines[0][0] = 0;
        lines[0][1] = 0;
        lineCount = 1;
        styler = new LineStyler();
        helper = new ContentHelper(this, resolver);
    }
    
    /**
     * 成倍增加行数
     */
    public void expandLineDouble() {
        int[][] newLines = new int[lines.length << 1][];
        System.arraycopy(lines, 0, newLines, 0, lines.length);
        lines = newLines;
    }
    
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.widgets.IRichContent#isDelimit(int)
     */
    public boolean isDelimit(int i) {
        if(i < 0)
            return false;
        return buffer.charAt(i) == '\r' || buffer.charAt(i) == '\n';
    }
        
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.widgets.IRichContent#isImageTag(int)
     */
    public boolean isImageTag(int i) {
        if(i < 0)
            return false;
        return buffer.charAt(i) == DEFAULT_FACE_TAG || buffer.charAt(i) == CUSTOM_FACE_TAG;
    }
    
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.widgets.IRichContent#getLineCount()
     */
    public int getLineCount() {
        return lineCount;
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.widgets.IRichContent#getLine(int)
     */
    public String getLine(int lineIndex) {
        int start = lines[lineIndex][0];
        int length = lines[lineIndex][1];
        while (length - 1 >= 0 && isDelimit(start + length - 1) )
            length--;
        return buffer.substring(start, start + length);
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.widgets.IRichContent#getLineAtOffset(int)
     */
    public int getLineAtOffset(int offset) {
        int low = 0;
        int high = lineCount - 1;
        int middle = (low + high) >>> 1;
        for(; low < high; middle = (low + high) >>> 1) {
            if(offset >= lines[middle][0])
                low = (middle == low) ? (low + 1) : middle;
            else
                high = (middle == high) ? (high - 1) : middle;
        }
        
        if(offset >= lines[low][0])
            return low;
        else
            return low - 1;
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.widgets.IRichContent#getLineStartOffset(int)
     */
    public int getLineStartOffset(int lineIndex) {
        if(lineIndex < lineCount)
            return lines[lineIndex][0];
        else
            return getCharCount();
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.widgets.IRichContent#getText(int, int)
     */
    public String getText(int start, int length) {
        return buffer.substring(start, start + length);
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.widgets.IRichContent#setText(java.lang.String)
     */
    public void setText(String text) {
        buffer = new StringBuffer(text);
        indexLines();
        styler.clearAll();        
    }
    
    /**
     * Calculates the indexes of each line in the text store. Assumes no gap
     * exists. Optimized to do less checking.
     */
    private void indexLines() {
        int start = 0;
        lineCount = 0;
        int textLength = buffer.length();
        int i;
        for (i = start; i < textLength; i++) {
            char ch = buffer.charAt(i);
            if (ch == SWT.CR) {
                // see if the next character is a LF
                if (i + 1 < textLength) {
                    ch = buffer.charAt(i + 1);
                    if (ch == SWT.LF) {
                        i++;
                    }
                }
                addLineIndex(start, i - start + 1);
                start = i + 1;
            } else if (ch == SWT.LF) {
                addLineIndex(start, i - start + 1);
                start = i + 1;
            }
        }
        addLineIndex(start, i - start);
    }
    
    /**
     * Adds a line to the end of the line indexes array. Increases the size of
     * the array if necessary. <code>lineCount</code> is updated to reflect
     * the new entry.
     * <p>
     * 
     * @param start
     *            the start of the line
     * @param length
     *            the length of the line
     */
    private void addLineIndex(int start, int length) {
        int size = lines.length;
        if (lineCount == size) 
            expandLineDouble();
        int[] range = new int[] { start, length };
        lines[lineCount++] = range;
    }
    
    /**
     * 删除行的偏移长度数据
     * 
     * @param start
     * 		开始删除的行号
     * @param length
     * 		删除的行数
     */
    private void removeLines(int start, int length) {
        if(start + length < lines.length)
            System.arraycopy(lines, start + length, lines, start, lines.length - start - length);
    }
    
    /**
     * 插入行数组
     * 
     * @param startIndex
     * @param numLines
     * @param oldLineCount
     */
    public void insertLines(int startIndex, int numLines, int oldLineCount) {
        while(oldLineCount + numLines > lines.length)
            expandLineDouble();
        System.arraycopy(lines, startIndex, lines, startIndex + numLines, oldLineCount - startIndex);
        for(int i = startIndex; i < startIndex + numLines; i++) {
            lines[i] = new int[2];
            lines[i][0] = lines[i][1] = 0;            
        }
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.widgets.IRichContent#getCharCount()
     */
    public int getCharCount() {
        return buffer.length();
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.widgets.IRichContent#getLineStyler()
     */
    public ILineStyler getLineStyler() {
        return styler;
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.widgets.IRichContent#getContentHelper()
     */
    public ContentHelper getContentHelper() {
        return helper;
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.widgets.IRichContent#replaceText(edu.tsinghua.lumaqq.widgets.TextEvent)
     */
    public void replaceText(TextEvent event) {
        int startLine = getLineAtOffset(event.start);
        int endLine = getLineAtOffset(event.end);
        int replaceLineCount = lineCount(event.start, event.end);
        int newLineCount = lineCount(event.text);
        int replaceCharCount = event.end - event.start;
        int newCharCount = event.text.length();
        event.startLine = startLine;
        event.endLine = endLine;
        event.replaceCharCount = replaceCharCount;
        event.replaceLineCount = replaceLineCount;
        event.newCharCount = newCharCount;
        event.newLineCount = newLineCount;
        
        // remove char, and clear some styles and heights
        if(replaceLineCount > 0) {
            // 对于删除掉的行，清楚他们的style和行高数据
	        styler.removeLineStyle(startLine + 1, replaceLineCount);
            helper.removeLineHeight(startLine, replaceLineCount + 1);
            
            // 调整行的位置和长度
            lines[startLine][1] = getLineStartOffset(startLine + replaceLineCount + 1) - getLineStartOffset(startLine) - replaceCharCount;
            removeLines(startLine + 1, replaceLineCount);
	        lineCount -= replaceLineCount;
            for(int i = startLine + 1; i < lineCount; i++)
                lines[i][0] -= replaceCharCount;
        } else if(replaceCharCount > 0) {
            // 调整行的位置和长度
            lines[startLine][1] -= replaceCharCount;
            for(int i = startLine + 1; i < lineCount; i++)
                lines[i][0] -= replaceCharCount;
        }
        if(replaceCharCount > 0)
            buffer.delete(event.start, event.end);
        
        // insert
        if(newLineCount > 0) {
	        int lenBeforeFirstLF = getLengthBeforeFirstLF(event.text); // 包括了第一个换行符
	        int lenAfterLastLF = getLengthAfterLastLF(event.text); // 不包括最后一个换行符号
	        int lenBeforeLastLF = newCharCount - lenAfterLastLF; // 包括了最后一个换行符
	        int startLineOffset = getLineStartOffset(startLine);
	        int nextLineOffset = getLineStartOffset(startLine + 1);
	        
            // 插入相应的行样式和行高空间
	        if(event.start == startLineOffset) {
	            styler.insertLineStyle(startLine, newLineCount, lineCount);
		        helper.insertLineHeight(startLine, newLineCount, lineCount);	            
	        } else {
		        styler.insertLineStyle(startLine + 1, newLineCount, lineCount);
		        helper.insertLineHeight(startLine + 1, newLineCount, lineCount);	            
	        }
	        
	        // 调整行的位置和长度
	        if(event.start == startLineOffset)
		        insertLines(startLine, newLineCount, lineCount);
	        else
	            insertLines(startLine + 1, newLineCount, lineCount);
	        lines[startLine][0] = startLineOffset;
	        lines[startLine][1] = event.start - startLineOffset + lenBeforeFirstLF;
	        lines[startLine + newLineCount][0] = event.start + lenBeforeLastLF;
	        lines[startLine + newLineCount][1] = lenAfterLastLF + nextLineOffset - event.start;
	        lineCount += newLineCount;
	        for(int i = startLine + newLineCount + 1; i < lineCount; i++)
	            lines[i][0] += newCharCount;
	        
	        // 调整中间的新行
	        int startOffset = lines[startLine][0] + lines[startLine][1];
	        int from = lenBeforeFirstLF;
	        int index = event.text.indexOf(SWT.LF, from);
	        for(int i = startLine + 1; i < startLine + newLineCount; i++) {
	            lines[i][0] = startOffset;
	            lines[i][1] = index - from + 1;
	            from = index + 1;
	            startOffset += lines[i][1];
	            index = event.text.indexOf(SWT.LF, from);
	        }
        } else if(newCharCount > 0) {
            lines[startLine][1] += newCharCount;
            for(int i = startLine + 1; i < lineCount; i++)
                lines[i][0] += newCharCount;
        }
        if(newCharCount > 0)
            buffer.insert(event.start, event.text);
    }

    /**
     * 返回字符串中第一个换行符之前的长度，包括换行符号
     * 
     * @param text
     * @return
     */
    private int getLengthBeforeFirstLF(String text) {
        int index = text.indexOf(SWT.LF);
        if(index == -1)
            return text.length();
        else
            return index + 1;
    }
    
    /**
     * 返回字符串中最后一个换行符号之后的长度，不包括换行符
     * 
     * @param text
     * @return
     */
    private int getLengthAfterLastLF(String text) {
        int index = text.lastIndexOf(SWT.LF);
        if(index == -1)
            return text.length();
        else
            return text.length() - index - 1;
    }

    /**
     * 在start和end之间查找换行数
     * 
     * @param start
     * 		起始偏移
     * @param end
     * 		结束偏移，exclusive
     * @return
     * 		换行数
     */
    private int lineCount(int start, int end) {
        int count = 0;
        for(int i = start; i < end; i++) {
            if(buffer.charAt(i) == SWT.LF)
                count++;
        }
        return count;
    }
    
    /**
     * 得到字符串中的换行数
     * 
     * @param text
     * @return
     * 		换行数
     */
    private int lineCount(String text) {
        int count = 0;
        int len = text.length();
        for(int i = 0; i < len; i++) {
            if(text.charAt(i) == SWT.LF)
                count++;
        }
        return count;
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.widgets.IRichContent#setLineStyle(int, edu.tsinghua.lumaqq.widgets.LineStyle)
     */
    public void setLineStyle(int lineIndex, LineStyle style) {
        styler.addLineStyle(lineIndex, style);
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.widgets.IRichContent#setDefaultStyle(edu.tsinghua.lumaqq.widgets.LineStyle)
     */
    public void setDefaultStyle(LineStyle style) {
        styler.setDefaultStyle(style);
    }
}
