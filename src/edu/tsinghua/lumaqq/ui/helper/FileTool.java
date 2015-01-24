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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 文件工具
 * 
 * @author luma
 */
public class FileTool {
	/**
	 * 从全路径中得到文件名
	 * 
	 * @param path
	 * @return
	 */
	public static String getFilename(String path) {
		int index = path.lastIndexOf(File.separatorChar);
		if(index == -1)
			return path;
		else
			return path.substring(index + 1);
	}
	
	/**
	 * 得到大小字符串
	 * 
	 * @param size
	 * @return
	 */
	public static String getSizeString(int size) {
		int value = size >> 10;
		if(value == 0)
			return String.valueOf(size) + " Bytes";
		
		size = value;
		value = size >> 10;
		if(value == 0)
			return String.valueOf(size) + " KB";
		
		size = value;
		value = size >> 10;
		if(value == 0)
			return String.valueOf(size) + " MB";
		else
			return String.valueOf(value) + " GB";
	}
	
    /**
     * 得到文件的扩展名
     * 
     * @param filename
     * 		文件名
     * @return
     * 		扩展名，如果没有扩展名，返回null
     */
    public static String getExtension(String filename) {
        int index = filename.lastIndexOf('.');
        if(index == -1)
            return "";
        else
            return filename.substring(index + 1);
    }
    
    /**
     * 删除一个文件
     * 
     * @param path
     * 		文件路径
     */
    public static void deleteFile(String path) {
        File file = new File(path);
        deleteFile(file);
    }
    
    /**
     * 删除文件
     * 
     * @param file
     */
    public static void deleteFile(File file) {
    	if(file.exists() && file.isFile())
    		file.delete();
    }
    
    /**
     * 删除目录
     * 
     * @param path
     */
    public static void deleteDirectory(String path) {
    	File dir = new File(path);
    	deleteDirectory(dir);
    }
    
    /**
     * 删除目录
     * 
     * @param dir
     */
    public static void deleteDirectory(File dir) {
    	if(dir.isDirectory()) {
    		File[] files = dir.listFiles();
    		for(File sub : files) {
    			if(sub.isDirectory())
    				deleteDirectory(sub);
    			else
    				deleteFile(sub);
    		}
    		dir.delete();
    	}
    }
    
    /**
     * 得到不包括扩展名在内的文件名
     * 
     * @param file
     * 		File对象
     * @return
     * 		文件名
     */
    public static String getNameExcludeExtension(File file) {
        return getNameExcludeExtension(file.getName());
    }
    
    /**
     * 得到不包括扩展名在内的文件名
     * 
     * @param filename
     * 		文件名
     * @return
     * 		文件名
     */
    public static String getNameExcludeExtension(String filename) {
        int index = filename.lastIndexOf('.');
        if(index != -1)
            filename = filename.substring(0, index);
        return filename;
    }
    
    /**
     * 把一段内容存为指定文件
     * 
     * @param b
     * 		字节数组
     * @param offset
     * 		起始偏移
     * @param length
     * 		保存的长度
     * @param dest
     * 		目标文件名
     * @return
     * 		true表示保存成功
     */
    public static boolean saveFile(byte[] b, int offset, int length, String dest) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(dest);
            fos.write(b, offset, length);
            fos.flush();
            return true;
        } catch (IOException e) {
            return false;
        } finally {
            try {
                if(fos != null)
                    fos.close();
            } catch (IOException e1) {
            }
        }        
    }
    
    /**
     * 把一个字符串保存到指定文件中
     * 
     * @param content
     * @param dest
     * @return
     */
    public static boolean saveFile(String content, String dest) {
        FileWriter fw = null;
        try {
            fw = new FileWriter(dest);
            fw.write(content);
            fw.flush();
            return true;
        } catch (IOException e) {
            return false;
        } finally {
            try {
                if(fw != null)
                    fw.close();
            } catch (IOException e1) {
            }
        }      
    }
    
    /**
     * 拷贝一个文件
     * 
     * @param src
     * 		源路径
     * @param dest
     * 		目的路径
     * @return
     * 		true表示拷贝成功
     */
    public static boolean copyFile(String src, String dest) {
        FileInputStream fis = null;        
        FileOutputStream fos = null;
        byte[] buffer = new byte[1024];
        try {
            fis = new FileInputStream(src);
            fos = new FileOutputStream(dest);
            for(int i = 0; i != -1; i = fis.read(buffer, 0, buffer.length))
                fos.write(buffer, 0, i);
            return true;
        } catch (IOException e) {
            return false;
        } finally {
            try {
                if(fis != null)
                    fis.close();
            } catch (IOException e1) {
            }
            try {
                if(fos != null)
                    fos.close();
            } catch (IOException e2) {
            }
            buffer = null;
        }
    }
    
    /**
     * 移动文件
     * 
     * @param src
     * 		源路径
     * @param dest
     * 		目的路径
     * @return
     * 		true表示移动成功
     */
    public static boolean moveFile(String src, String dest) {
    	if(!copyFile(src, dest))
    		return false;
    	deleteFile(src);
    	return true;
    }
    
    /**
     * 创建目录
     * 
     * @param path
     */
    public static void mkdirs(String path) {
    	File dir = new File(path);
    	if(!dir.exists())
    		dir.mkdirs();
    }
}
