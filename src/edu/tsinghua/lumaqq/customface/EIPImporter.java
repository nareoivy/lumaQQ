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
package edu.tsinghua.lumaqq.customface;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.poifs.filesystem.DirectoryEntry;
import org.apache.poi.poifs.filesystem.DocumentEntry;
import org.apache.poi.poifs.filesystem.DocumentInputStream;
import org.apache.poi.poifs.filesystem.Entry;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;

import edu.tsinghua.lumaqq.ecore.face.FaceConstant;
import edu.tsinghua.lumaqq.ecore.face.FaceGroup;

/**
 * 这是EIP格式表情文件的导入器，EIP文件和腾讯的其他的一些文件都是基于POIFS的，关于
 * POIFS的格式，参见http://jakarta.apache.org/poi/poifs/fileformat.html
 * 
 * 对于EIP文件，它的目录组织形式是
 * 根目录下有两个目录
 * 1. config, 配置文件目录 		
 * 2. Files, 表情文件目录
 * 
 * config下存放的是Face.xml文件，是自定义表情的描述文件
 * Files下面又有子目录，子目录的名字就是自定义表情的组名
 * 比如有个组叫word，那么就有一个word的子目录
 * 然后word下面才是真正的表情文件，它的文件用数字做文件名，从0开始，缩略图用数字
 * 加上Fixed做文件名，比如0Fixed，表情更详细的信息要到Faces.xml里面去查
 * 
 * @author notXX
 * @author luma
 */
public class EIPImporter {
	private static final String CONFIG_DIRECTORY = "config";
	private static final String FILES_DIRECTORY = "files";
	private static final String CONFIG_FILE = "face.xml";
	private static final String THUMB_SUFFIX = "fixed";
	
    private byte[] buffer;
    
    private FaceEntry entry;
    private String destDir;
    
    private FileInputStream eipStream;
    private FaceXMLParser parser;
    private Iterator<Entry> groupIterator;
    private Iterator<Entry> faceIterator;
    private DirectoryEntry currentDir;
    private DocumentEntry currentFace;
    
	@SuppressWarnings("unchecked")
    public EIPImporter(String file, String destDir) {
        this.destDir = destDir;       
    	buffer = new byte[8192];
    	
    	POIFSFileSystem eipSystem;
    	try {
    		// 打开eip文件
			eipStream = new FileInputStream(file);
			eipSystem = new POIFSFileSystem(eipStream);			
			
			// 找到对应的目录
			DirectoryEntry configDir = null, fileDir = null;
			DirectoryEntry root = eipSystem.getRoot();
			Iterator<Entry> i = root.getEntries();
			while(i.hasNext()) {
				Entry e = i.next();
				if(e.isDirectoryEntry()) {
					if(CONFIG_DIRECTORY.equals(e.getName().toLowerCase()))
						configDir = (DirectoryEntry)e;
					else if(FILES_DIRECTORY.equals(e.getName().toLowerCase()))
						fileDir = (DirectoryEntry)e;
				}
			}
			
			// 检查是否目录齐全
			if(configDir == null || fileDir == null)
				throw new IOException("Can't find correct directories");
			
			// 解析face.xml文件
			i = configDir.getEntries();
			while(i.hasNext()) {
				Entry e = i.next();
				if(e.isDocumentEntry() && CONFIG_FILE.equals(e.getName().toLowerCase())) {
					DocumentInputStream dis = new DocumentInputStream((DocumentEntry)e);
					parser = new FaceXMLParser(dis);
					dis.close();
					break;
				}
			}
			
			// 检查是否解析了face.xml
			if(parser == null)
				throw new IOException("Can't find " + CONFIG_FILE);
			
			// 得到表情组iterator
			groupIterator = fileDir.getEntries();
			currentDir = fileDir;
			faceIterator = currentDir.getEntries();
		} catch(IOException e) {
			eipSystem = null;
			try {
				if(eipStream != null) {
					eipStream.close();
					eipStream = null;
				}
			} catch(IOException e1) {
			}
		}
    }
    
    /**
     * 关闭文件
     */
    public void dispose() {
    	try {
    		if(eipStream != null)
    			eipStream.close();
		} catch (IOException e) {
		}
    }
    
    /**
     * @return
     * 		下一个表情项内容，如果为null，表示没有更多项了。返回的表情项对象中，如果组名是文件目录，则设为"."
     */
    @SuppressWarnings("unchecked")
    public FaceEntry getNextEntry() {
    	if(currentDir == null)
    		return null;
    	
    	while(true) {
    		currentFace = getNextDocument();
    		if(currentFace == null) {
    			currentDir = getNextDir();
    			if(currentDir == null)
    				return null;
    			faceIterator = currentDir.getEntries();
    		} else if(currentFace.getName().toLowerCase().endsWith(THUMB_SUFFIX))
    			continue;
    		else    
    			break;
    	}
    	
    	entry = parser.getEntry(currentFace.getName());
    	entry.groupName = currentDir.getName();
    	if(entry.groupName.toLowerCase().equals(FILES_DIRECTORY))
    		entry.groupName = ".";
    	return entry;
    }
    
    /**
     * 保存一个表情文件
     * 
     * @param g
     * 		组
     * @return
     * 		true表示成功
     */
    public boolean saveEntry(FaceGroup g) {
    	if(g.getId() == FaceConstant.CUSTOM_HEAD_GROUP_ID) 
    		return saveCustomHead(g);
    	else {
    		FileOutputStream fos = null;
    		DocumentInputStream dis = null;
    		try {
    			// 保存原始表情
    			String filename = destDir + g.getId() + '/' + entry.filename;
    			fos = new FileOutputStream(filename);        
    			dis = new DocumentInputStream(currentFace);
    			for(int i = 0; i != -1; i = dis.read(buffer, 0, buffer.length))
    				fos.write(buffer, 0, i);
    			
    			// 保存缩略图
    			try {
    				ImageLoader loader = new ImageLoader();
    				loader.load(filename);
    				ImageData data = loader.data[0].scaledTo(20, 20);
    				loader = new ImageLoader();
    				loader.data = new ImageData[] { data };
    				loader.save(destDir + g.getId() + '/' + entry.md5 + "fixed.bmp", SWT.IMAGE_BMP);
    			} catch (SWTException e) {
    				return false;
    			}    
    			
    			return true;
    		} catch(IOException e) {
    			return false;
    		} finally {
    			try {
    				if(fos != null)
    					fos.close();
    				if(dis != null)
    					dis.close();
    			} catch(IOException e) {				
    			}
    		}    		
    	}
    }
    
	/**
	 * 保存文件为自定义头像
	 * 
     * @return
     * 		true表示保存成功
	 */
	private boolean saveCustomHead(FaceGroup g) {
		DocumentInputStream dis = null;
		try {
			// 读取图片内容
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			dis = new DocumentInputStream(currentFace);
			for(int i = 0; i != -1; i = dis.read(buffer, 0, buffer.length))
				baos.write(buffer, 0, i);
			
			// 生成ImageData
			ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
			ImageData origin = new ImageData(bais);
			ImageData data = origin.scaledTo(40, 40);
			
			// save 40x40 bmp
			ImageLoader saveLoader = new ImageLoader();
			saveLoader.data = new ImageData[] { data };
			saveLoader.save(destDir + g.getId() + '/' + entry.md5 + ".bmp", SWT.IMAGE_BMP);
			
			// save 20x20 bmp
			data = origin.scaledTo(20, 20);
			saveLoader = new ImageLoader();
			saveLoader.data = new ImageData[] { data };
			saveLoader.save(destDir + g.getId() + '/' + entry.md5 + "fixed.bmp", SWT.IMAGE_BMP);
			
			return true;
		} catch(SWTException e) {
			return false;
		} catch(IOException e) {
			return false;
		} finally {
			try {
				if(dis != null)
					dis.close();
			} catch(IOException e) {				
			}
		}    		
	}
    
    /**
     * @return
     * 		当前目录的下一个document entry
     */
    private DocumentEntry getNextDocument() {
    	DocumentEntry de = null;
    	while(faceIterator.hasNext()) {
    		Entry e = faceIterator.next();
    		if(e.isDocumentEntry()) {
    			de = (DocumentEntry)e;
    			break;
    		}
    	}
    	return de;
    }
    
    /**
     * @return
     * 		下一次表情组目录对象
     */
    private DirectoryEntry getNextDir() {
    	DirectoryEntry dir = null;
    	while(groupIterator.hasNext()) {
    		Entry e = groupIterator.next();
    		if(e.isDirectoryEntry()) {
    			dir = (DirectoryEntry)e;
    			break;
    		}
    	}
    	return dir;
    }
}
