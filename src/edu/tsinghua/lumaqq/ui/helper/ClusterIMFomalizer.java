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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.Assert;

import edu.tsinghua.lumaqq.qq.QQ;
import edu.tsinghua.lumaqq.qq.Util;

/**
 * 群消息正规式和非正规式转换器
 *
 * @author luma
 */
public class ClusterIMFomalizer {
	/**
	 * 将非正规式转换为正规式
	 * 
	 * @param msg
	 * 		非正规式消息
	 * @param faces
	 * 		已经发送的自定义表情
	 * @return
	 * 		正规式字节数组
	 */
	public static byte[] formalize(String msg, List<CustomFace> faces) {
		Map<Integer, CustomFace> idMap = new HashMap<Integer, CustomFace>();
		for(CustomFace f : faces)
			idMap.put(f.faceId, f);
		Map<Integer, Integer> existing = new HashMap<Integer, Integer>();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		
		int j = 0;
		int i = nextTag(msg, j);
		for(; i != -1; i = nextTag(msg, j)) {
			// 添加没有任何表情的内容
			try {
				String sub = msg.substring(j, i);
				baos.write(Util.getBytes(sub));
			} catch(IOException e) {
			}
			// 添加表情
			switch((byte)msg.charAt(i)) {
				case QQ.QQ_TAG_DEFAULT_FACE:
					baos.write(QQ.QQ_TAG_DEFAULT_FACE);
					baos.write(msg.charAt(i + 1));
					j = i + 2;
					break;
				case QQ.QQ_TAG_CUSTOM_FACE:
					int faceId = msg.charAt(i + 1);
					CustomFace face = idMap.get(faceId);
					Assert.isNotNull(face);
					if(existing.containsKey(faceId)) {
						/*
						 * 已存在
						 */
						baos.write(QQ.QQ_TAG_CUSTOM_FACE);
						baos.write(QQ.QQ_FORMAT_TAG_EXISTING_SERVER_SIDE_CUSTOM_SIDE);
						// 长度，14
						baos.write(' ');
						baos.write('1');
						baos.write('4');
						// id
						baos.write(face.id);
						// shortcut length, always write 0
						baos.write('A');
						// agent ip
						writeHex(baos, face.agentIp[3] & 0xFF, '0');
						writeHex(baos, face.agentIp[2] & 0xFF, '0');
						writeHex(baos, face.agentIp[1] & 0xFF, '0');
						writeHex(baos, face.agentIp[0] & 0xFF, '0');
						// unknown
						baos.write('A');
					} else {
						/*
						 * 新的
						 */
						baos.write(QQ.QQ_TAG_CUSTOM_FACE);
						baos.write(QQ.QQ_FORMAT_TAG_NEW_SERVER_SIDE_CUSTOM_FACE);
						// 长度，50 + 文件名长度，因为不支持快捷键
						String s = String.valueOf(50 + face.filename.length());
						if(s.length() < 3) {
							int len = 3 - s.length();
							for(int m = 0; m < len; m++)
								baos.write(' ');
							for(int m = 0; m < s.length(); m++)
								baos.write(s.charAt(m));
						}
						// id
						baos.write('e');
						// shortcut length
						baos.write('A');
						// 后面的内容开始一直到agent key之前的内容的长度，2字节，用16进制的字符串表示
						writeHex(baos, 24, '0');
						// session id
						writeHex(baos, (face.sessionId >>> 24) & 0xFF, ' ');
						writeHex(baos, (face.sessionId >>> 16) & 0xFF, ' ');
						writeHex(baos, (face.sessionId >>> 8) & 0xFF, ' ');
						writeHex(baos, face.sessionId & 0xFF, ' ');
						// agent ip
						writeHex(baos, face.agentIp[3] & 0xFF, '0');
						writeHex(baos, face.agentIp[2] & 0xFF, '0');
						writeHex(baos, face.agentIp[1] & 0xFF, '0');
						writeHex(baos, face.agentIp[0] & 0xFF, '0');
						// agent port
						writeHex(baos, (face.agentPort >>> 24) & 0xFF, ' ');
						writeHex(baos, (face.agentPort >>> 16) & 0xFF, ' ');
						writeHex(baos, (face.agentPort >>> 8) & 0xFF, ' ');
						writeHex(baos, face.agentPort & 0xFF, ' ');
						// agent key & file name
						try {
							baos.write(face.agentKey);
							baos.write(Util.getBytes(face.filename));
						} catch(IOException e) {
						}
						// unknown
						baos.write('A');
						// add it to existing map
						existing.put(faceId, faceId);
					}
					j = i + 2;
					break;
				default:
					j = i + 1;
					break;
			}
		}
		// 最后一段
		if(j < msg.length()) {
			try {
				String sub = msg.substring(j);
				baos.write(Util.getBytes(sub));
			} catch(IOException e) {
			}
		}
		
		return baos.toByteArray();
	}
	
	/**
	 * 把一个整数做为一个十六进制字符串写入，最大255
	 * 
	 * @param baos
	 * @param i
	 * @param fill
	 * 		不足时用什么填充
	 */
	private static void writeHex(ByteArrayOutputStream baos, int i, char fill) {
		String s = Integer.toHexString(i).toUpperCase();
		if(s.length() <= 0) {
			baos.write(fill);
			baos.write(fill);
		} else if(s.length() < 2) {
			baos.write(fill);
			baos.write(s.charAt(0));
		} else {
			baos.write(s.charAt(0));
			baos.write(s.charAt(1));
		}
	}
	
	/**
	 * 搜索下一个tag的位置
	 * 
	 * @param msg
	 * 		消息字符串
	 * @param from
	 * 		起始找寻点
	 * @return
	 * 		-1表示没有找到
	 */
	private static int nextTag(String msg, int from) {
		int length = msg.length();
		for(int i = from; i < length; i++) {
			switch((byte)msg.charAt(i)) {
				case QQ.QQ_TAG_DEFAULT_FACE:
				case QQ.QQ_TAG_CUSTOM_FACE:
					return i;
			}
		}
		return -1;
	}
}
