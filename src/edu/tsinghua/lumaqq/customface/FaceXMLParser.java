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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

/**
 * 用来从EIP的Faces.xml中解析一些信息
 * 
 * @author luma
 */
public class FaceXMLParser {
	private static final Log log = LogFactory.getLog(FaceXMLParser.class);
	
	// element
	private static final String CUSTOM_FACE_GROUP = "CUSTOMFACEGROUP";
	private static final String FACE = "FACE";
	private static final String FACE_ORG = "FILE_ORG";
	private static final String FILE_FIXED = "FILE_FIXED";
	private static final String CUSTOM_FACE = "CUSTOMFACE";
	private static final String ORIGINAL_FACE_ORG = "FILE ORG";
	private static final String ORIGINAL_FILE_FIXED = "FILE FIXED";
	
	// property
	private static final String FACE_INDEX = "FileIndex";
	private static final String FACE_MD5 = "id";
	private static final String FACE_SHORTCUT = "shortcut";	
	private static final String FACE_NAME = "tip";
	
	private Map<String, FaceEntry> entries;
	
	/**
	 * 创建一个表情配置文件解析器
	 * 
	 * @param is
	 * 		输入流
	 */
	@SuppressWarnings("unchecked")
	public FaceXMLParser(InputStream is) {
		entries = new HashMap<String, FaceEntry>();
		
		SAXBuilder builder = new SAXBuilder(false);
		Document doc;
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			byte[] buf = new byte[8192];
			for(int i = 0; i != -1; i = is.read(buf, 0, buf.length))
				baos.write(buf, 0, i);
			String s = new String(baos.toByteArray());
			s = s.replaceAll(ORIGINAL_FACE_ORG, FACE_ORG);
			s = s.replaceAll(ORIGINAL_FILE_FIXED, FILE_FIXED);
			
			doc = builder.build(new StringReader(s));
			Element root = doc.getRootElement();
			
			List<Element> groups = root.getChildren(CUSTOM_FACE_GROUP);
			for(Element group : groups) {
				List<Element> faces = group.getChildren(FACE);
				parseFaceList(faces);
			}
			List<Element> customfaces = root.getChildren(CUSTOM_FACE);
			for(Element customface : customfaces) {
				List<Element> faces = customface.getChildren(FACE);
				parseFaceList(faces);
			}
		} catch(JDOMException e) {
			log.error(e.getMessage());
		} catch(IOException e) {
			log.error(e.getMessage());
		}			
	}
	
	/**
	 * 分析Face列表
	 * 
	 * @param faces
	 */
	private void parseFaceList(List<Element> faces) {
		for(Element face : faces) {
			FaceEntry entry = new FaceEntry();
			entry.md5 = face.getAttributeValue(FACE_MD5);
			entry.filename = face.getChildText(FACE_ORG);
			entry.name = face.getAttributeValue(FACE_NAME);
			entry.shortcut = face.getAttributeValue(FACE_SHORTCUT);
			String index = face.getAttributeValue(FACE_INDEX);
			entries.put(index, entry);
		}
	}

	/**
	 * 得到face entry
	 * 
	 * @param index
	 * 		自定义表情的序号
	 * @return
	 * 		FaceEntry对象
	 */
	public FaceEntry getEntry(String index) {
		return entries.get(index);
	}
}
