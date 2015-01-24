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
package edu.tsinghua.lumaqq.ui.helper;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * 文件分片器
 * 
 * @author luma
 */
public class FileSegmentor {
    private RandomAccessFile file;
    private int totalFragments;
    private int fragmentSize;
    
    /**
     * 创建一个文件分片器
     * 
     * @param path
     * 		文件的路径
     * @param fragmentSize
     * 		分片大小
     */
    public FileSegmentor(String path, int fragmentSize) {
        try {
            this.fragmentSize = fragmentSize;
            file = new RandomAccessFile(path, "r");
	        totalFragments = (int)(file.length() - 1) / fragmentSize + 1;
        } catch (IOException e) {
            totalFragments = 0;
        }        
    }
    
    /**
     * @return
     * 		true表示这个文件成功打开
     */
    public boolean isLoadSuccess() {
        return totalFragments > 0;
    }
    
    /**
     * 得到分片数据
     * 
     * @param index
     * 		分片号
     * @return
     * 		数据字节数组
     */
    public byte[] getFragment(int index) {
        if(index >= totalFragments)
            return null;
        
        try {
            byte[] ret = null;
            if(index < totalFragments - 1)
                ret = new byte[fragmentSize];
            else
                ret = new byte[(int)file.length() % fragmentSize];
            
            file.seek(index * fragmentSize);
            file.readFully(ret);
            return ret;
        } catch (IOException e) {
            return null;
        }
    }
    
    /**
     * 关闭打开的文件
     */
    public void close() {
        try {
            file.close();
        } catch (IOException e) {
        }
    }
    
    /**
     * @return Returns the totalFragments.
     */
    public int getTotalFragments() {
        return totalFragments;
    }
}
