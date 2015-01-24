/*
* LumaQQ - Java QQ Client
*
* Copyright (C) 2004 notXX
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
package edu.tsinghua.lumaqq.customface;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;

import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;

import edu.tsinghua.lumaqq.ecore.face.FaceConstant;
import edu.tsinghua.lumaqq.ecore.face.FaceGroup;
import edu.tsinghua.lumaqq.qq.Util;
import edu.tsinghua.lumaqq.ui.helper.FileTool;

/**
 * QQ自定义表情CFC格式文件的导入器，CFC文件是一个比较简单的文件格式，它比EIP要简单很多，
 * 缺点是CFC文件不能支持表情分组。
 * <p>
 * CFC文件的格式是一系列连续的块，每个块是一个GIF和BMP文件，BMP文件是GIF的微缩图，我们
 * 知道QQ在选择自定义表情的时候都会把它们的微缩图显示出来给我们选。LumaQQ只关心GIF，BMP
 * 暂且用不上。
 * </p>
 * <p>
 * CFC文件没有文件头，也没有文件尾，它就是一系列连续的块组成的，所以下面介绍块的格式，我们
 * 要注意的是，这个文件里面的整数都是little-endian格式.
 * </p>
 * <p>
 * 1. md5的字符串形式长度，4个字节，这个一般都是0x00000020，因为MD5是16字节
 * 2. 快捷键长度，4字节
 * 3. 表情名称长度，4字节
 * 4. 表情文件名长度，4字节，加上".GIF"，所以这个字段一般都是0x00000024
 * 5. 表情文件长度，4字节
 * 6. 微缩图文件名长度，4字节，因为微缩图一般都是MD5 + "fixed.bmp"，所以这个字段一般都是0x00000029
 * 7. 微缩文件长度，4字节
 * 8. 表情文件帧数，4字节，因为表情文件可能是动画，动画自然是多帧的
 * 9. 图片md5的字符串形式
 * 10. 快捷键
 * 11. 表情名称
 * 12. 表情文件名
 * 13. 微缩图文件名
 * 14. 表情文件内容
 * 15. 微缩图内容
 * </p>
 * 
 * @author luma
 */
public class CFCImporter {
    private FaceEntry entry;
    private String destDir;
    private FaceGroup group;
    
    private RandomAccessFile cfcFile;
    
    private long nextEntryOffset;
    private long cfcFileLength;
    
    private byte[] buffer;
    
    private int md5Length;
    private int shortcutLength;
    private int nameLength;
    private int fileNameLength;
    private int fileLength;
    private int thumbFileNameLength;
    private int thumbFileLength;
    
    /**
     * 创建一个CFC文件导入器
     * 
     * @param file
     * 		CFC文件路径
     * @param destDir
     * 		保存图片的目的路径
     * @param g
     * 		保存到的组
     */
    public CFCImporter(String file, String destDir, FaceGroup g) {
        this.destDir = destDir;        
        nextEntryOffset = 0;
        buffer = new byte[10000];
        entry = new FaceEntry();
        group = g;
        
        try {
            cfcFile = new RandomAccessFile(file, "r");
            cfcFileLength = cfcFile.length();
        } catch (IOException e) {
            cfcFileLength = -1;
        }
    }
    
    /**
     * 释放资源
     */
    public void dispose() {
        buffer = null;
        try {
            cfcFile.close();
        } catch (IOException e) {
        }
    }
    
    /**
     * @return
     * 		下一个表情项内容，如果为null，表示没有更多项了
     */
    public FaceEntry getNextEntry() {
        if(nextEntryOffset >= cfcFileLength)
            return null;
        
        boolean success = readEntry();
        while(nextEntryOffset < cfcFileLength && !success)
            success = readEntry();
        
        return success ? entry : null;
    }
    
	/**
	 * 从offset位置读取4个字节为一个long，因为java为big-endian格式，所以没办法
	 * 用了这么一个函数来做转换
	 * @param offset
	 * @return 读取的long值，返回-1表示读取文件失败
	 */
	private int readInt4(long offset) {
	    int ret = 0;
		try {
		    cfcFile.seek(offset);
			ret |= (cfcFile.readByte() & 0xFF);
			ret |= ((cfcFile.readByte() << 8) & 0xFF00);
			ret |= ((cfcFile.readByte() << 16) & 0xFF0000);
			ret |= ((cfcFile.readByte() << 24) & 0xFF000000);
			return ret;
		} catch (IOException e) {
			return -1;
		}
	}
	
	/**
	 * 从指定位置读取一个指定长度的字符串
	 * 
	 * @param offset
	 * 		起始偏移
	 * @param length
	 * 		字符串长度
	 * @return
	 * 		字符串，失败返回null
	 */
	private String readString(long offset, int length) {
	    try {
            byte[] buf = new byte[length];
            cfcFile.seek(offset);
            cfcFile.readFully(buf);
            return Util.getString(buf);
        } catch (IOException e) {
            return null;
        }
	}
	
	/**
	 * 从指定位置读取一段内容
	 * 
	 * @param offset
	 * 		起始偏移
	 * @param length
	 * 		读取长度
	 * @return
	 * 		true表示成功
	 */
	private boolean readBytes(long offset, int length) {
	    if(length > buffer.length)
	        expandBuffer(length);
	    
	    try {
            cfcFile.seek(offset);
            cfcFile.read(buffer, 0, length);
            return true;
        } catch (IOException e) {
            return false;
        }
	}
	
	/**
	 * 读取下一个face entry。读取完毕后，nextEntryOffset将被置为下一个entry的偏移
	 * 
	 * @return
	 * 		true表示读取成功
	 */
	private boolean readEntry() {
	    long offset = nextEntryOffset;
	    
	    md5Length = readInt4(offset);
	    offset += 4;
	    
	    shortcutLength = readInt4(offset);
	    offset += 4;
	    
	    nameLength = readInt4(offset);
	    offset += 4;
	    
	    fileNameLength = readInt4(offset);
	    offset += 4;
	    
	    fileLength = readInt4(offset);
	    offset += 4;
	    
	    thumbFileNameLength = readInt4(offset);
	    offset += 4;
	    
	    thumbFileLength = readInt4(offset);
	    offset += 4;
	    
	    // 读取md5
	    offset += 4;	    
	    entry.md5 = readString(offset, md5Length);
	    offset += md5Length;
	    // shortcut
	    entry.shortcut = readString(offset, shortcutLength);
	    offset += shortcutLength;
	    // 名称
	    entry.name = readString(offset, nameLength);
	    offset += nameLength;
	    // 文件名
	    entry.filename = readString(offset, fileNameLength);
	    offset += fileNameLength;
	    
	    // 读取文件内容
	    offset += thumbFileNameLength;
	    if(!readBytes(offset, fileLength)) {
	        nextEntryOffset = offset + fileLength + thumbFileLength;
	        return false;
	    }
	    
	    nextEntryOffset = offset + fileLength + thumbFileLength;
	    return true;
	}

    /**
     * 保存这个块中的图片文件到目标目录
     * 
     * @return
     * 		true表示保存成功
     */
    public boolean saveEntry() {
    	if(group.getId() == FaceConstant.CUSTOM_HEAD_GROUP_ID) 
    		return saveCustomHead();
    	else {
    		// 保存表情文件
    		String filename = destDir + group.getId() + '/' + entry.filename;
    		if(!FileTool.saveFile(buffer, 0, fileLength, filename))
    			return false;
    		
    		// 保存缩略图
    		try {
    			ImageLoader loader = new ImageLoader();
    			loader.load(filename);
    			ImageData data = loader.data[0].scaledTo(20, 20);
    			loader = new ImageLoader();
    			loader.data = new ImageData[] { data };
    			loader.save(destDir + group.getId() + '/' + entry.md5 + "fixed.bmp", SWT.IMAGE_BMP);
    		} catch (SWTException e) {
    			return false;
    		}    
    		return true;    		
    	}
    }

	/**
	 * 保存文件为自定义头像
	 * 
     * @return
     * 		true表示保存成功
	 */
	private boolean saveCustomHead() {
		try {
			// 生成ImageData
			ByteArrayInputStream bais = new ByteArrayInputStream(buffer, 0, fileLength);
			ImageData origin = new ImageData(bais);
			ImageData data = origin.scaledTo(40, 40);
			
			// save 40x40 bmp
			ImageLoader saveLoader = new ImageLoader();
			saveLoader.data = new ImageData[] { data };
			saveLoader.save(destDir + group.getId() + '/' + entry.md5 + ".bmp", SWT.IMAGE_BMP);
			
			// save 20x20 bmp
			data = origin.scaledTo(20, 20);
			saveLoader = new ImageLoader();
			saveLoader.data = new ImageData[] { data };
			saveLoader.save(destDir + group.getId() + '/' + entry.md5 + "fixed.bmp", SWT.IMAGE_BMP);
			
			return true;
		} catch(SWTException e) {
			return false;
		}
	}

	/**
     * 扩展缓冲区
     */
    private void expandBuffer(int length) {
        buffer = new byte[length];
    }
}
