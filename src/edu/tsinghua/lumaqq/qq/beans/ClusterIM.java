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
package edu.tsinghua.lumaqq.qq.beans;

import java.nio.ByteBuffer;

import edu.tsinghua.lumaqq.qq.QQ;

/**
 * <pre>
 * 群消息的信息封装bean，具体内容可以参见ReceiveIMPacket
 * 
 * 关于自定义表情的格式，参见NormalIM注释
 * </pre>
 * 
 * @see edu.tsinghua.lumaqq.qq.beans.NormalIM
 * @author luma
 */
public class ClusterIM {
    public char source;
    // 这个字段在收到临时群消息时表示父群ID，在固定群消息时表示群外部ID
	public int externalId;
	public byte type;
	public int sender;
	public char unknown1;
	public char sequence;
	public long sendTime;
	public int versionId;
	public char contentType;
	public int fragmentSequence;
	public int fragmentCount;
	public int messageId;	
    // 下面这些都是消息的属性，比如字体啦颜色啦，都是在fontAttribute里面的
	public boolean hasFontAttribute;
	public FontStyle fontStyle;
    
    // 临时群内部ID，仅用于临时群消息时
	public int clusterId;
	
	// 消息内容，在解析的时候只用byte[]，正式要显示到界面上时才会转为String，上层程序
	// 要负责这个事，这个类只负责把内容读入byte[]
	public byte[] messageBytes;
	public String message;
	
	// true表示这个消息中的自定义表情已经全部得到
	public boolean faceResolved;
    
    public ClusterIM(char source) {
        this.source = source;
        faceResolved = false;
        fontStyle = new FontStyle();
    }
    
    /**
     * 给定一个输入流，解析ClusterIM结构
     * @param buf
     */
    public void readBean(ByteBuffer buf) {
    	// 群外部ID或者父群ID
    	externalId = buf.getInt();
    	// 群类型，1字节
    	type = buf.get();
    	// 临时群内部ID
    	if(source == QQ.QQ_RECV_IM_TEMP_CLUSTER)
    	    clusterId = buf.getInt();
    	// 发送者
    	sender = buf.getInt();
    	// 未知1
    	unknown1 = buf.getChar();
    	// 消息序号
    	sequence = buf.getChar();
    	// 发送时间，记得乘1000才对
    	sendTime = buf.getInt() * 1000L;    	
    	// Member Version ID
    	versionId = buf.getInt();
    	// 后面的内容长度
    	buf.getChar();
    	// 一些扩展信息 
    	if(source != QQ.QQ_RECV_IM_UNKNOWN_CLUSTER) {
    	    // content type
    	    contentType = buf.getChar();
    	    // 分片数
    	    fragmentCount = buf.get() & 0xFF;
    	    // 分片序号
    	    fragmentSequence = buf.get() & 0xFF;
    	    // 2字节未知
    	    messageId = buf.getChar();
    	    // 4字节未知
    	    buf.getInt();
    	}
        // 消息正文，只有最后一个分片有字体属性
    	int remain = buf.remaining();
    	int fontAttributeLength = (fragmentSequence == fragmentCount - 1) ? (buf.get(buf.position() + remain - 1) & 0xFF) : 0;
    	messageBytes = new byte[remain - fontAttributeLength];
    	buf.get(messageBytes);
        // 只有最后一个分片有字体属性
        hasFontAttribute = fragmentSequence == fragmentCount - 1;
        // 这后面都是字体属性，这个和SendIMPacket里面的是一样的
        if(hasFontAttribute) 
        	fontStyle.readBean(buf);
    }
}
