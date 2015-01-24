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

import edu.tsinghua.lumaqq.qq.QQ;
import edu.tsinghua.lumaqq.qq.Util;

/**
 * 普通消息形式化工具类。用于在消息的显示格式和QQ协议之间转换。称QQ协议格式为正式。
 *
 * @author luma
 */
public class NormalIMFormalizer {
	/**
	 * 将协议格式转换为显示格式，为非正式化
	 * 
	 * @param b
	 * @return
	 */
	public static String deformalize(byte[] b) {
		StringBuilder sb = new StringBuilder();
		
		int j = 0;
		int i = nextTag(b, j);
		for(; i != -1; i = nextTag(b, j)) {
			sb.append(Util.getString(b, j, i - j));
			switch(b[i]) {
				case QQ.QQ_TAG_DEFAULT_FACE:
					sb.append((char)QQ.QQ_TAG_DEFAULT_FACE);
					sb.append((char)(b[i + 1] & 0xFF));
					j = i + 2;
					break;
				case QQ.QQ_TAG_CUSTOM_FACE:
					// TODO 暂时没处理
					j = i + 1;
					break;
			}
		}
		if(j < b.length) {
			sb.append(Util.getString(b, j, b.length - j));
		}
		
		return sb.toString();
	}
	
	/**
	 * 接受一个显示格式的消息，将其转换为正规形式的字节数组。将显示格式转换为
	 * 协议格式，为正式化
	 * 
	 * @param msg
	 * @return
	 */
	public static byte[] formalize(String msg) {
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
					// TODO 现在是简单的跳过
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
	
	/**
	 * 搜索下一个tag的位置
	 * 
	 * @param b
	 * @param from
	 * @return
	 */
	private static int nextTag(byte[] b, int from) {
		int length = b.length;
		for(int i = from; i < length; i++) {
			switch(b[i]) {
				case QQ.QQ_TAG_DEFAULT_FACE:
				case QQ.QQ_TAG_CUSTOM_FACE:
					return i;
			}
		}
		return -1;
	}
}
