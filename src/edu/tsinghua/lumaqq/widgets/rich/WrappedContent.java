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

import org.eclipse.swt.graphics.GC;

/**
 * 包含一个rich content，使其具有wrap功能
 * 
 * @author luma
 */
public class WrappedContent implements IWrappedRichContent {    
    private RichBox richbox;
    private IRichContent unwrappedContent;
    private ContentHelper unwrappedHelper;
    private WrappedLineStyler styler;
    private ContentHelper helper;
    
    // 存放硬行wrap之后各个硬行的软行偏移
    private int[] wrapped;
    // 软行总数
    private int softLineCount;
    // 软行偏移和长度数组
    private int[][] softLines;
	
	/**
	 * 创建content
	 */
	public WrappedContent(RichBox box, IRichContent content, ImageResolver resolver) {
	    richbox = box;
	    unwrappedContent = content;
	    unwrappedHelper = content.getContentHelper();
	    styler = new WrappedLineStyler(this, content.getLineStyler());
	    helper = new ContentHelper(this, resolver);
	    int lineCount = content.getLineCount();
	    wrapped = new int[lineCount];
	    softLines = new int[10][2];
	}
	
	/**
	 * 删除wrap偏移数据
	 * 
	 * @param start
	 * @param length
	 */
	private void removeWrapped(int start, int length) {
        if(start + length < wrapped.length)
            System.arraycopy(wrapped, start + length, wrapped, start, wrapped.length - start - length);
	}
	
	/**
	 * 插入空间到wrap数组
	 * 
	 * @param startIndex
	 * @param numLines
	 * @param oldLineCount
	 */
	private void insertWrapped(int startIndex, int numLines, int oldLineCount) {
        while(oldLineCount + numLines > wrapped.length)
            expandWrappedDouble();
        System.arraycopy(wrapped, startIndex, wrapped, startIndex + numLines, oldLineCount - startIndex);
	}
	
	/**
	 * 插入空间到行偏移长度数组
	 * 
	 * @param startIndex
	 * @param numLines
	 * @param oldLineCount
	 */
	private void insertSoftLines(int startIndex, int numLines, int oldLineCount) {
        while(oldLineCount + numLines > softLines.length)
            expandLineDouble();
        System.arraycopy(softLines, startIndex, softLines, startIndex + numLines, oldLineCount - startIndex);
	}
	
	/**
	 * 删除行
	 * 
	 * @param start
	 * @param length
	 */
	private void removeSoftLines(int start, int length) {
        if(start + length < softLines.length)
            System.arraycopy(softLines, start + length, softLines, start, softLines.length - start - length);
	}
	
	/**
	 * 扩展wrap数组
	 */
	private void expandWrappedDouble() {
        int[] newArray = new int[wrapped.length << 1];
        System.arraycopy(wrapped, 0, newArray, 0, wrapped.length);
        wrapped = newArray;	  
	}
	
	/**
	 * 保证wrap数组能满足一个容量的需求
	 * 
	 * @param minimum
	 */
	private void ensureWrappedSize(int minimum) {
	    if(minimum > wrapped.length) {
	        int[] newArray = new int[minimum + 10];
	        System.arraycopy(wrapped, 0, newArray, 0, wrapped.length);
	        wrapped = newArray;	        
	    }
	}

    /**
     * 成倍增加行数
     */
    private void expandLineDouble() {
        int[][] newLines = new int[softLines.length << 1][];
        System.arraycopy(softLines, 0, newLines, 0, softLines.length);
        softLines = newLines;
    }
    
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.widgets.IRichContent#getLineCount()
     */
    public int getLineCount() {
        return softLineCount;
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.widgets.IRichContent#getLine(int)
     */
    public String getLine(int lineIndex) {
        int start = softLines[lineIndex][0];
        int length = softLines[lineIndex][1];
        while (length - 1 >= 0 && isDelimit(start + length - 1) )
            length--;
        return unwrappedContent.getText(start, length);
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.widgets.IRichContent#getLineAtOffset(int)
     */
    public int getLineAtOffset(int offset) {
        int low = 0;
        int high = softLineCount - 1;
        int middle = (low + high) >>> 1;
        for(; low < high; middle = (low + high) >>> 1) {
            if(offset >= softLines[middle][0])
                low = (middle == low) ? (low + 1) : middle;
            else
                high = (middle == high) ? (high - 1) : middle;
        }
        
        if(offset >= softLines[low][0])
            return low;
        else
            return low - 1;
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.widgets.IRichContent#getLineStartOffset(int)
     */
    public int getLineStartOffset(int lineIndex) {
        if(lineIndex < softLineCount)
            return softLines[lineIndex][0];
        else
            return getCharCount();
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.widgets.IRichContent#getText(int, int)
     */
    public String getText(int start, int length) {
        return unwrappedContent.getText(start, length);
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.widgets.IRichContent#getCharCount()
     */
    public int getCharCount() {
        return unwrappedContent.getCharCount();
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.widgets.IRichContent#setText(java.lang.String)
     */
    public void setText(String text) {
        unwrappedContent.setText(text);
        wrap(richbox.getClientArea().width - richbox.getLeftMargin() - richbox.getRightMargin());
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.widgets.IRichContent#isDelimit(int)
     */
    public boolean isDelimit(int i) {
        return unwrappedContent.isDelimit(i);
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.widgets.IRichContent#isImageTag(int)
     */
    public boolean isImageTag(int i) {
        return unwrappedContent.isImageTag(i);
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.widgets.IWrappedContent#wrap(int)
     */
    public void wrap(int width) {        
        reset();
        
        if(width <= 0)
            width = 100;
        
        GC gc = richbox.getGC();
        softLineCount = 0;
        int hardLineCount = unwrappedContent.getLineCount();
        ensureWrappedSize(hardLineCount);
        for(int i = 0; i < hardLineCount; i++) {
            wrapped[i] = softLineCount;
            
            int[] offsets = unwrappedHelper.splitLine(gc, width, i);
            softLineCount += offsets.length;
            int m = 0;
            for(int j = wrapped[i]; j < softLineCount - 1; j++, m++) 
                setSoftLine(j, offsets[m], offsets[m + 1] - offsets[m]);
            setSoftLine(softLineCount - 1, offsets[m], unwrappedContent.getLineStartOffset(i + 1) - offsets[m]);
        }
        richbox.releaseGC();
    }
    
    /**
     * 初始化各个变量的值
     */
    private void reset() {
        softLineCount = 1;
        softLines[0][0] = 0;
        softLines[0][1] = 0;
        wrapped[0] = 0;
        helper.clearLineHeightCache();
    }
    
    /**
     * 保存软行的起始和长度
     * 
     * @param index
     * 		行号
     * @param start
     * 		起始偏移
     * @param length
     * 		长度
     */
    private void setSoftLine(int index, int start, int length) {
        while(index >= softLines.length)
            expandLineDouble();
        
        softLines[index] = new int[2];
        softLines[index][0] = start;
        softLines[index][1] = length;
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.widgets.IRichContent#getLineStyler()
     */
    public ILineStyler getLineStyler() {
        return styler;
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.widgets.IWrappedRichContent#getHardLineIndex(int)
     */
    public int getHardLineIndex(int softLineIndex) {
        int low = 0;
        int high = getHardLineCount() - 1;
        int middle = (low + high) >>> 1;
        for(; low < high; middle = (low + high) >>> 1) {
            if(softLineIndex >= wrapped[middle])
                low = (middle == low) ? (low + 1) : middle;
            else
                high = (middle == high) ? (high - 1) : middle;
        }
        
        if(softLineIndex >= wrapped[low])
            return low;
        else
            return low - 1;
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
        int oldHardLineCount = unwrappedContent.getLineCount();
        unwrappedContent.replaceText(event);
        
        int width = richbox.getClientArea().width - richbox.getLeftMargin() - richbox.getRightMargin();   
        if(width <= 0)
            width = 100;
        
        // 调整空间
        int startSoftLine = wrapped[event.startLine];      
        int removedSoftLineCount;
        if(event.startLine + event.replaceLineCount + 1 >= oldHardLineCount)
            removedSoftLineCount = softLineCount - wrapped[event.startLine];
        else
            removedSoftLineCount = wrapped[event.startLine + event.replaceLineCount + 1] - wrapped[event.startLine];            
        removeWrapped(event.startLine + 1, event.replaceLineCount);
        oldHardLineCount -= event.replaceLineCount;
        insertWrapped(event.startLine + 1, event.newLineCount, oldHardLineCount);
        
        // 计算软行的增减
        GC gc = richbox.getGC();     
        int addedSoftLineCount = 0;            
        for(int i = event.startLine; i <= event.startLine + event.newLineCount; i++) {
            int[] offsets = unwrappedHelper.splitLine(gc, width, i);
            addedSoftLineCount += offsets.length;
        }
        int softLineDelta = addedSoftLineCount - removedSoftLineCount;
        
        // 调整软行的空间和其他空间
        removeSoftLines(startSoftLine, removedSoftLineCount);
        insertSoftLines(startSoftLine, addedSoftLineCount, softLineCount);
        helper.removeLineHeight(startSoftLine, removedSoftLineCount);
        helper.insertLineHeight(startSoftLine, addedSoftLineCount, softLineCount);                
        softLineCount += softLineDelta;
        
        // 调整编辑范围之内的软行
        for(int i = event.startLine; i <= event.startLine + event.newLineCount; i++) {
            int[] offsets = unwrappedHelper.splitLine(gc, width, i);
            wrapped[i] = startSoftLine;
            startSoftLine += offsets.length;
            int m = 0;
            for(int j = wrapped[i]; j < startSoftLine - 1; j++, m++) 
                setSoftLine(j, offsets[m], offsets[m + 1] - offsets[m]);
            setSoftLine(startSoftLine - 1, offsets[m], unwrappedContent.getLineStartOffset(i + 1) - offsets[m]);
        }            
        
        // 调整编辑范围之外的硬行分行数
        for(int i = event.startLine + event.newLineCount + 1; i < unwrappedContent.getLineCount(); i++)
            wrapped[i] += softLineDelta;
        // 调整编辑范围之外的软行
        int charDelta = event.newCharCount - event.replaceCharCount;
        for(int i = startSoftLine; i < softLineCount; i++)
            softLines[i][0] += charDelta;
        
        richbox.releaseGC();
        richbox.redrawFromLine(wrapped[event.startLine]);
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.widgets.IRichContent#setLineStyle(int, edu.tsinghua.lumaqq.widgets.LineStyle)
     */
    public void setLineStyle(int lineIndex, LineStyle style) {
        int hardLineIndex = lineIndex;
        unwrappedContent.getLineStyler().addLineStyle(hardLineIndex, style);
        unwrappedHelper.clearLineHeightCache(hardLineIndex);
        
        int hardLineCount = unwrappedContent.getLineCount();
        
        // 调整空间
        int startSoftLine = wrapped[hardLineIndex];
        int removedSoftLineCount;
        if(hardLineIndex >= hardLineCount - 1)
            removedSoftLineCount = softLineCount - startSoftLine;
        else
            removedSoftLineCount = wrapped[hardLineIndex + 1] - startSoftLine;            
        
        // 计算软行的增减
        GC gc = richbox.getGC();
        int width = richbox.getClientArea().width - richbox.getLeftMargin() - richbox.getRightMargin();
        if(width <= 0)
            width = 100;
        int addedSoftLineCount = 0;            
        int[] offsets = unwrappedHelper.splitLine(gc, width, hardLineIndex);
        addedSoftLineCount += offsets.length;
        int softLineDelta = addedSoftLineCount - removedSoftLineCount;
        
        // 调整软行的空间和其他空间
        removeSoftLines(startSoftLine, removedSoftLineCount);
        insertSoftLines(startSoftLine, addedSoftLineCount, softLineCount);
        helper.removeLineHeight(startSoftLine, removedSoftLineCount);
        helper.insertLineHeight(startSoftLine, addedSoftLineCount, softLineCount);                
        softLineCount += softLineDelta;
        
        // 调整编辑范围之内的软行
        startSoftLine += offsets.length;
        int m = 0;
        for(int j = wrapped[hardLineIndex]; j < startSoftLine - 1; j++, m++) 
            setSoftLine(j, offsets[m], offsets[m + 1] - offsets[m]);
        setSoftLine(startSoftLine - 1, offsets[m], unwrappedContent.getLineStartOffset(hardLineIndex + 1) - offsets[m]);   
        
        // 调整编辑范围之外的硬行分行数
        for(int i = hardLineIndex + 1; i < unwrappedContent.getLineCount(); i++)
            wrapped[i] += softLineDelta;
        
        richbox.releaseGC();
        richbox.redrawFromLine(wrapped[hardLineIndex]);
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.widgets.IWrappedRichContent#getHardLineCount()
     */
    public int getHardLineCount() {
        return unwrappedContent.getLineCount();
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.widgets.IRichContent#setDefaultStyle(edu.tsinghua.lumaqq.widgets.LineStyle)
     */
    public void setDefaultStyle(LineStyle style) {
        unwrappedContent.getLineStyler().setDefaultStyle(style);
        wrap(richbox.getClientArea().width - richbox.getLeftMargin() - richbox.getRightMargin());
        richbox.calculateTopLine();
        richbox.redraw();
    }
}
