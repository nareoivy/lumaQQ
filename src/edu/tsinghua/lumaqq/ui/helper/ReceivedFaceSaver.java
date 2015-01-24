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

import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;


/**
 * 字节数组导入
 *
 * @author luma
 */
public class ReceivedFaceSaver {
	private byte[] buffer;
	private int offset, length;
	private String md5, filename;
	
	public ReceivedFaceSaver(byte[] buffer, int offset, int length, String md5, String filename) {
		this.buffer = buffer;
		this.offset = offset;
		this.length = length;
		this.md5 = md5;
		this.filename = filename;
	}
	
	/**
	 * 保存接收到的表情
	 * 
	 * @return
	 */
	public boolean saveEntry() {
		String path = FaceRegistry.getInstance().getReceivedFacePath(filename);
		if(!FileTool.saveFile(buffer, offset, length, path))
			return false;
		
		// 保存缩略图
		try {              
			ImageLoader loader = new ImageLoader();
			loader.load(path);
			ImageData data = loader.data[0].scaledTo(20, 20);
			loader = new ImageLoader();
			loader.data = new ImageData[] { data };
			loader.save(FaceRegistry.getInstance().getReceivedFaceThumbPath(md5), SWT.IMAGE_BMP);
		} catch (SWTException e) {
			return false;
		}    
		return true; 
	}
}
