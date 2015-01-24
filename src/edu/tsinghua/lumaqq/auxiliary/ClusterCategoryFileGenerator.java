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
package edu.tsinghua.lumaqq.auxiliary;

import static edu.tsinghua.lumaqq.ui.helper.ClusterCategoryTool.*;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

/**
 * 从cluster_category.xml中读取群分类，然后生成一个二进制的文件
 * 
 * 格式:
 * 1. 文件头，4字节，种类的个数
 * 2. 索引区，索引项格式为：
 * 		i. 种类记录的绝对偏移，4字节
 * 		ii. 第一个子类的ID号，4字节
 *		iii. 最后一个子类的ID号，4字节
 * 索引区包含ENTRY_COUNT个索引项，但是实际用上的数目是有文件头指明的
 * 3. 记录区，记录项的格式为
 * 		i. 种类的ID，4字节
 * 		ii. 父类的ID，4字节，如果没有父类，为0，4字节
 * 		iii. 下一个兄弟类的ID，4字节
 * 		iv. 种类名称，UTF字符串
 * 
 * 全部为Big-endian
 * @author luma
 */
public class ClusterCategoryFileGenerator {
	private RandomAccessFile file;
	private int count;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ClusterCategoryFileGenerator gen = new ClusterCategoryFileGenerator();
		gen.generate();
	}

	/**
	 * 产生二进制文件
	 */
	@SuppressWarnings("unchecked")
	private void generate() {
		try {
			// 打开目标文件
			file = new RandomAccessFile("cluster_category.db", "rw");
			file.setLength(0);
			generateHeader();
			generateIndex();
			count = 0;
			
			// 读取所有元素
			SAXBuilder builder = new SAXBuilder(false);
			Document doc = builder.build("cluster_category.xml");
			Element root = doc.getRootElement();
			List<Element> l1_list = root.getChildren("L1");
			for(Element l1 : l1_list) {
				System.out.println("Process " + l1.getAttributeValue("id") + " " + l1.getAttributeValue("value"));
				int l1_id = Integer.parseInt(l1.getAttributeValue("id"));
				addEntry(l1_id, 0, l1.getAttributeValue("value"));
				
				List<Element> l2_list = l1.getChildren("L2");
				for(Element l2 : l2_list) {
					System.out.println("Process " + l2.getAttributeValue("id") + " " + l2.getAttributeValue("value"));
					int l2_id = Integer.parseInt(l2.getAttributeValue("id"));
					addEntry(l2_id, l1_id, l2.getAttributeValue("value"));
					
					List<Element> l3_list = l2.getChildren("L3");
					for(Element l3 : l3_list) {
						System.out.println("Process " + l3.getAttributeValue("id") + " " + l3.getAttributeValue("value"));
						int l3_id = Integer.parseInt(l3.getAttributeValue("id"));
						addEntry(l3_id, l2_id, l3.getAttributeValue("value"));
					}
				}
			}

			// 修改entry数目
			updateCount();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(file != null)
					file.close();
			} catch(IOException e) {
			}
		}
	}

	private void updateCount() throws IOException {
		file.seek(0);
		file.writeInt(count);
	}

	private void addEntry(int id, int parentId, String name) throws IOException {
		int start = appendEntry(id, parentId, name);
		writeIndex(id, start);
		updateParent(parentId, id);
		count++;
	}

	private void updateParent(int parentId, int id) throws IOException {
		int start = HEADER_LENGTH + ENTRY_LENGTH * parentId + 4;
		file.seek(start);
		int firstId = file.readInt();
		if(firstId == 0) {
			file.seek(start);
			file.writeInt(id);
		}
		int lastId = file.readInt();
		if(lastId != 0) 
			updateLast(lastId, id);
		file.seek(start + 4);
		file.writeInt(id);
	}

	private void updateLast(int lastId, int id) throws IOException {
		int offset = getOffset(lastId);
		file.seek(offset + 8);
		file.writeInt(id);
	}

	private int getOffset(int id) throws IOException {
		file.seek(HEADER_LENGTH + ENTRY_LENGTH * id);
		return file.readInt();
	}

	private int appendEntry(int id, int parentId, String name) throws IOException {
		int start = (int)file.length();
		file.seek(start);
		file.writeInt(id);
		file.writeInt(parentId);
		file.writeInt(0);
		file.writeUTF(name);
		return start;
	}

	private void writeIndex(int id, int start) throws IOException {
		file.seek(HEADER_LENGTH + ENTRY_LENGTH * id);
		file.writeInt(start);
	}

	private void generateIndex() throws IOException {
		for(int i = 0; i < ENTRY_COUNT; i++) {
			file.writeInt(0);
			file.writeInt(0);
			file.writeInt(0);
		}
	}

	private void generateHeader() throws IOException {
		file.writeInt(0);
	}
}
