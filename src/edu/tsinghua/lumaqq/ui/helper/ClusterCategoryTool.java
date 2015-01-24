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
import java.util.ArrayList;
import java.util.List;

import edu.tsinghua.lumaqq.LumaQQ;

/**
 * 读取群分类二进制文件的工具类
 * 
 * @author luma
 */
public class ClusterCategoryTool {
	public static final int ENTRY_COUNT = 10000;
	public static final int HEADER_LENGTH = 4;
	public static final int ENTRY_LENGTH = 12;
	
	private RandomAccessFile file;
	private static final List<String> sTemp = new ArrayList<String>();
	private static final List<Integer> iTemp = new ArrayList<Integer>();
	private static final String[] EMPTY_STRING_ARRAY = new String[0];
	private static final Integer[] EMPTY_INTEGER_ARRAY = new Integer[0];
	private static final StringBuilder sb = new StringBuilder();
	
	public void init() {
		try {
			file = new RandomAccessFile(LumaQQ.CLUSTER_CATEGORY_FILE, "r");
		} catch(IOException e) {
		}
	}
	
	public void dispose() {
		try {
			if(file != null)
				file.close();
		} catch(IOException e) {
		}
	}
	
	public String getName(int id) {
		if(id < 1 || id > ENTRY_COUNT || file == null)
			return "";
		else {
			try {
				file.seek(HEADER_LENGTH + ENTRY_LENGTH * id);
				file.seek(file.readInt() + 12);
				return file.readUTF();
			} catch(IOException e) {
				return "";
			}			
		}
	}
	
	public String[] getSubCategory(int parentId) {
		if(file == null)
			return EMPTY_STRING_ARRAY;
		
		sTemp.clear();
		
		try {
			int subId = getFirstSubId(parentId);
			while(subId > 0) {
				sTemp.add(getName(subId));
				subId = getNextSubId(subId);
			}
			return sTemp.toArray(new String[sTemp.size()]);
		} catch(IOException e) {
			return EMPTY_STRING_ARRAY;
		}
	}
	
	public Integer[] getSubCategoryId(int parentId) {
		if(file == null)
			return EMPTY_INTEGER_ARRAY;
		
		iTemp.clear();
		
		try {
			int subId = getFirstSubId(parentId);
			while(subId > 0) {
				iTemp.add(subId);
				subId = getNextSubId(subId);
			}
			return iTemp.toArray(new Integer[iTemp.size()]);
		} catch(IOException e) {
			return EMPTY_INTEGER_ARRAY;
		}
	}
	
	public String getCategoryPath(int id) {
		if(file == null)
			return "";
		
		try {
			sb.delete(0, sb.length());
			while(id > 0) {
				sb.insert(0, '-').insert(0, getName(id));
				id = getParentId(id);
			}
			if(sb.length() > 0)
				sb.deleteCharAt(sb.length() - 1);
			return sb.toString();
		} catch(IOException e) {
			return "";
		}
	}
	
	private int getParentId(int subId) throws IOException {
		file.seek(HEADER_LENGTH + ENTRY_LENGTH * subId);
		file.seek(file.readInt() + 4);
		return file.readInt();
	}
	
	public int getLevelId(int id, int level) {
		if(file == null)
			return 0;
		
		try {
			if(id == 0)
				return 0;
			
			int p1 = getParentId(id);
			int p2 = (p1 > 0) ? getParentId(p1) : 0;
			int idLevel = (p2 > 0) ? 2 : ((p1 > 0) ? 1 : 0);
			if(level < 0 || level > idLevel)
				return 0;
			else {
				switch(idLevel - level) {
					case 0:
						return id;
					case 1:
						return p1;
					case 2:
						return p2;
					default:
						return 0;
				}
			}
		} catch(IOException e) {
			return 0;
		}		
	}
	
	private int getFirstSubId(int parentId) throws IOException {
		file.seek(HEADER_LENGTH + ENTRY_LENGTH * parentId + 4);
		return file.readInt();
	}
	
	private int getNextSubId(int prevSubId) throws IOException {
		file.seek(HEADER_LENGTH + ENTRY_LENGTH * prevSubId);
		file.seek(file.readInt() + 8);
		return file.readInt();
	}
}
