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


/**
 * <pre>
 * 普通消息的本体，其在NormalIMHeader之后
 * 
 * 普通消息中可能内嵌一些图片信息，除了普通的文本之外，图片的信息格式为：
 * 一. 缺省表情，缺省表情的前导字节是0x14，0x14之后的一个字节表示缺省表情的索引值
 * 二. 自定义表情，自定义表情的前导字节是0x15，0x15之后的格式为:
 * 	  1. 存在性字节，如果这个表情第一次出现，则为0x33，如果已经出现过，则为0x34，当为0x33时，后面的内容是
 * 		 i.   扩展名长度，1字节，以'0'为基准，'2'则表示长度为3
 *       ii.  表情图片的文件名，其文件名由md5的字符串形式和扩展名构成，因此这个长度应该是32 + 1 + 3(一般是GIF)
 *       iii. 表情的shortcut长度，以'A'为基准，如果长度是2，则这个字节是'C'
 *       iv.  表情的shortcut
 *    2. 如果为0x34时，则后面的内容为：
 * 		 i.   1字节索引值，假如这个自定义表情出现在第一个位置，则这个字节为'A'   
 *    3. 如果为0x36时，群内自定义表情
 * 		 i. 自定义表情协议块的长度的10进制字符串形式，3字节，不足者前部填为空格，比如为了表示这个自定义表情用了
 *          88个字节，那么这个字段就是" 88"，呵呵，晕吧，注意这个长度是从0x15开始算起，一直到结束。注意这个长度
 *          是字节长度
 *       ii. 表情标识，1字节，标识这个表情是新的，还是已经出现过的，如果是新的，用'e'表示。如果是已经出现过的，
 *           用一个大写字母表示，第一个新表情代号是A，第二个是B，以此类推
 *       iii. 表情的快捷键字节长度，1字节，用一个大写字母表示，比如A表示长度为0，依次类推
 *       iv. 后面的内容开始一直到agent key之前的内容的长度，2字节，用16进制的字符串表示
 *       v. session id的16进制字符串形式，8字节，不足者前面是空格
 *       vi. 中转服务器IP的16进制字符串形式，注意是little-endian，那么ipv4的话自然就是8个字节了
 *       vii. 中转服务器端口号的16进制字符串形式，8个字节
 *       viii. file agent key，16字节
 *       ix. 图片的文件名，文件名的形式是MD5的字符串形式加上点加上后缀名而成，所以一般是36个字节，但是
 *           我想最好还是根据前面的长度减去其他字段的长度来判断好些
 *       x.  快捷键，长度前面已经说了
 *       xi. 一个字节，'A'，可能是用来分界用的
 *    4. 如果为0x37时，群内自定义表情
 *       0x37表示这个表情已经在前面出现过，参见0x36时的格式，0x37缺少0x36的iv, v, vii, viii, ix部分，
 *       其他部分均相同
 * </pre>
 * 
 * @author luma
 */
public class NormalIM {
    public boolean hasFontAttribute; // 是一个字节，为了方便，我弄成boolean表示
    public int totalFragments;
    public int fragmentSequence;
    public int messageId;
    public byte replyType;
    // 字体属性
    public FontStyle fontStyle;
    
	// 消息内容，在解析的时候只用byte[]，正式要显示到界面上时才会转为String，上层程序
	// 要负责这个事，这个类只负责把内容读入byte[]
	public byte[] messageBytes;
	public String message;
    
    /**
     * 给定一个输入流，解析NormalIM结构
     * @param buf
     */
    public void readBean(ByteBuffer buf) {
    	fontStyle = new FontStyle();
        // 是否有字体属性
        hasFontAttribute = buf.getInt() != 0;
        // 分片数
        totalFragments = buf.get() & 0xFF;
        // 分片序号
        fragmentSequence = buf.get() & 0xFF;
        // 消息id
        messageId = buf.getChar();
        // 消息类型，这里的类型表示是正常回复还是自动回复之类的信息
        replyType = buf.get();
        // 消息正文，长度=剩余字节数 - 包尾字体属性长度
        int remain = buf.remaining();
        int fontAttributeLength = hasFontAttribute ? (buf.get(buf.position() + remain - 1) & 0xFF) : 0;
        messageBytes = new byte[remain - fontAttributeLength];
        buf.get(messageBytes);
        // 这后面都是字体属性，这个和SendIMPacket里面的是一样的
        if(hasFontAttribute) {
            if(buf.hasRemaining()) 
            	fontStyle.readBean(buf);
            else
                hasFontAttribute = false;
        } 
    }

	public void readBeanEx(ByteBuffer buf) {
    	fontStyle = new FontStyle();
        // 是否有字体属性
        hasFontAttribute = buf.getInt() != 0;
        // 未知8字节
        buf.getLong();
        // 分片数
        totalFragments = buf.get() & 0xFF;
        // 分片序号
        fragmentSequence = buf.get() & 0xFF;
        // 消息id
        messageId = buf.getChar();
        // 消息类型，这里的类型表示是正常回复还是自动回复之类的信息
        replyType = buf.get();
        // 消息正文，长度=剩余字节数 - 包尾字体属性长度
        int remain = buf.remaining();
        int fontAttributeLength = hasFontAttribute ? (buf.get(buf.position() + remain - 1) & 0xFF) : 0;
        messageBytes = new byte[remain - fontAttributeLength];
        buf.get(messageBytes);
        // 这后面都是字体属性，这个和SendIMPacket里面的是一样的
        if(hasFontAttribute) {
            if(buf.hasRemaining()) 
            	fontStyle.readBean(buf);
            else
                hasFontAttribute = false;
        } 
	}
}
