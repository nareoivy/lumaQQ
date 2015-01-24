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

import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;

import edu.tsinghua.lumaqq.ecore.face.FaceConstant;
import edu.tsinghua.lumaqq.ecore.face.FaceGroup;
import edu.tsinghua.lumaqq.qq.Util;
import edu.tsinghua.lumaqq.ui.helper.FileTool;

/**
 * 单个图片导入器
 * 
 * @author luma
 */
public class SingleFaceImporter {
    private String file, destDir;
    private FaceEntry entry;
    private FaceGroup group;
    
    public SingleFaceImporter(String file, String destDir, FaceGroup g) {
        this.file = file;
        this.destDir = destDir;
        this.group = g;
        entry = new FaceEntry();
    }
    
    /**
     * @return
     * 		得到这个图片的entry对象
     */
    public FaceEntry getEntry() {
        entry.md5 = Util.getFileMD5String(file);
        entry.filename = entry.md5 + '.' + FileTool.getExtension(file);
        return entry;
    }
    
    /**
     * 保存这个图片到指定目录。如果是保存自定义头像，则需要把图片缩放一下
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
    		if(!FileTool.copyFile(file, filename))
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
			ImageLoader loader = new ImageLoader();
			loader.load(file);
			ImageData data = loader.data[0].scaledTo(40, 40);
			
			// save 40x40 bmp
			ImageLoader saveLoader = new ImageLoader();
			saveLoader.data = new ImageData[] { data };
			saveLoader.save(destDir + group.getId() + '/' + entry.md5 + ".bmp", SWT.IMAGE_BMP);
			
			// save 20x20 bmp
			data = loader.data[0].scaledTo(20, 20);
			saveLoader = new ImageLoader();
			saveLoader.data = new ImageData[] { data };
			saveLoader.save(destDir + group.getId() + '/' + entry.md5 + "fixed.bmp", SWT.IMAGE_BMP);
			
			return true;
		} catch(SWTException e) {
			return false;
		}
	}
}
