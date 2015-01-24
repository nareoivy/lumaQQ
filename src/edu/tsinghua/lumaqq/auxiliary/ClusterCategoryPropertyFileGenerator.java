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

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

/**
 * 从cluster_category.xml中读取群分类，然后生成一个properties文件
 *
 * @author luma
 */
public class ClusterCategoryPropertyFileGenerator {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ClusterCategoryPropertyFileGenerator gen = new ClusterCategoryPropertyFileGenerator();
		gen.generate();
	}

	/**
	 * 产生二进制文件
	 */
	@SuppressWarnings("unchecked")
	private void generate() {
		try {
			StringBuilder sb = new StringBuilder();
			String CRLF = System.getProperty("line.separator");
			
			// 读取所有元素
			SAXBuilder builder = new SAXBuilder(false);
			Document doc = builder.build("cluster_category.xml");
			Element root = doc.getRootElement();
			List<Element> l1_list = root.getChildren("L1");
			
			sb.append("Level1.count = ");
			sb.append(l1_list.size());
			sb.append(CRLF);
			
			int l1_count = 0;
			for(Element l1 : l1_list) {
				int l1_id = Integer.parseInt(l1.getAttributeValue("id"));
				
				// id
				sb.append("Level1.");
				sb.append(l1_count);
				sb.append(".id = ");
				sb.append(l1_id);
				sb.append(CRLF);
				
				// value
				sb.append("Level1.");
				sb.append(l1_count);
				sb.append(".value = ");
				sb.append(l1.getAttributeValue("value"));
				sb.append(CRLF);
				
				List<Element> l2_list = l1.getChildren("L2");
				
				sb.append("Level2.");
				sb.append(l1_count);
				sb.append(".count = ");
				sb.append(l2_list.size());
				sb.append(CRLF);
				
				int l2_count = 0;
				for(Element l2 : l2_list) {
					int l2_id = Integer.parseInt(l2.getAttributeValue("id"));
					
					// id
					sb.append("Level2.");
					sb.append(l1_count);
					sb.append(".");
					sb.append(l2_count);
					sb.append(".id = ");
					sb.append(l2_id);
					sb.append(CRLF);
					
					// value
					sb.append("Level2.");
					sb.append(l1_count);
					sb.append(".");
					sb.append(l2_count);
					sb.append(".value = ");
					sb.append(l2.getAttributeValue("value"));
					sb.append(CRLF);
					
					List<Element> l3_list = l2.getChildren("L3");
					
					sb.append("Level3.");
					sb.append(l1_count);
					sb.append(".");
					sb.append(l2_count);
					sb.append(".count = ");
					sb.append(l3_list.size());
					sb.append(CRLF);
					
					int l3_count = 0;					
					for(Element l3 : l3_list) {
						int l3_id = Integer.parseInt(l3.getAttributeValue("id"));
						
						// id
						sb.append("Level3.");
						sb.append(l1_count);
						sb.append(".");
						sb.append(l2_count);
						sb.append(".");
						sb.append(l3_count);
						sb.append(".id = ");
						sb.append(l3_id);
						sb.append(CRLF);
						
						// value
						sb.append("Level2.");
						sb.append(l1_count);
						sb.append(".");
						sb.append(l2_count);
						sb.append(".");
						sb.append(l3_count);
						sb.append(".value = ");
						sb.append(l3.getAttributeValue("value"));
						sb.append(CRLF);
						
						l3_count++;
					}
					l2_count++;
				}
				l1_count++;
			}
			
			FileOutputStream fos = new FileOutputStream("cluster_category.properties");
			OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
			osw.write(sb.toString());
			osw.close();
		} catch(Exception e) {
			e.printStackTrace();
		} 
	}
}
