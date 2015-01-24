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
package edu.tsinghua.lumaqq.qq.packets.out;

import java.nio.ByteBuffer;

import edu.tsinghua.lumaqq.qq.QQ;
import edu.tsinghua.lumaqq.qq.Util;
import edu.tsinghua.lumaqq.qq.beans.FontStyle;
import edu.tsinghua.lumaqq.qq.beans.QQUser;
import edu.tsinghua.lumaqq.qq.packets.PacketParseException;

/**
 * <pre>
 * 扩展固定群消息发送包，相对于已经有的固定群消息发送包来说，这个多了一些标志，
 * 同时旧的群消息发送包为了兼容性的考虑TX依然保留：
 * 1.  头部
 * 2.  子命令，1字节，0x1A
 * 3.  群内部ID，4字节
 * 4.  后面的数据的总长度，2字节
 * 5.  Content Type, 2字节，0x0001表示纯文件，0x0002表示有自定义表情
 * 6.  消息分片数，1字节。群消息分片也是最大700字节，但是这个和普通消息不一样的是：这个700包含了这些控制信息
 * 7.  分片序号，1字节，从0开始
 * 8.  消息id，2字节，同一条消息的不同分片id相同
 * 9.  4字节，未知
 * 10. 消息内容，最后一个分片追加空格
 * Note: 结尾处的空格是必须的，如果不追加空格，会导致有些缺省表情显示为乱码
 * 11. 消息的尾部，包含一些消息的参数，比如字体颜色啦，等等等等，顺序是
 *     1. 字体修饰属性，bold，italic之类的，2字节，具体的设置是
 *         i.   bit0-bit4用来表示字体大小，所以最大是32
 *         ii.  bit5表示是否bold
 *         iii. bit6表示是否italic
 *         iv.  bit7表示是否underline
 *     2. 颜色Red，1字节
 *     3. 颜色Green，1字节
 *     4. 颜色Blue，1字节
 *     5. 1个未知字节，置0先
 *     6. 消息编码，2字节，0x8602为GB，0x0000为EN，其他未知，好像可以自定义，因为服务器好像不干涉
 *     7. 可变长度的一段信息，字体名后面跟一个回车符，比如0xcb, 0xce, 0xcc, 0xe5,表示宋体
 * 12. 1字节，表示11和12部分的字节长度
 * 13. 尾部
 * 
 * 注意：只有最后一个分片有11, 12部分
 * </pre>
 * 
 * @author luma
 */
public class ClusterSendIMExPacket extends ClusterCommandPacket {
	// 字体属性
	protected FontStyle fontStyle;
    // 消息内容
    protected String message;
    
    protected int totalFragments;
    protected int fragmentSequence;
    protected char messageId;
    
    /**
     * @param user
     */
    public ClusterSendIMExPacket(QQUser user) {
        super(user);
        subCommand = QQ.QQ_CLUSTER_CMD_SEND_IM_EX;
        totalFragments = 1;
        fragmentSequence = 0;
        messageId = (char)Util.random().nextInt();
		fontStyle = new FontStyle();
        message = "";
    }

    /**
     * @param buf
     * @param length
     * @param user
     * @throws PacketParseException
     */
    public ClusterSendIMExPacket(ByteBuffer buf, int length, QQUser user)
            throws PacketParseException {
        super(buf, length, user);
    }
    
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.packets.out.ClusterSendIMPacket#getPacketName()
     */
	@Override
    public String getPacketName() {
        return "Cluster Send IM Ex Packet";
    }
    
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.packets.OutPacket#putBody(java.nio.ByteBuffer)
     */
	@Override
    protected void putBody(ByteBuffer buf) {
		// 命令类型
		buf.put(subCommand);
		// 群内部ID
		buf.putInt(clusterId);
	    // 后面数据的长度，这个长度需要根据后面的长度计算才能知道，
		// 所以先占个位置		
		int pos = buf.position();
		buf.putChar((char)0);
		// 未知的2字节
		buf.putChar((char)1);
		// 分片数
		buf.put((byte)totalFragments);
		// 分片序号
		buf.put((byte)fragmentSequence);
		// 消息id
		buf.putChar(messageId);
		// 未知4字节
		buf.putInt(0);
	    // 以0结束的消息，首先我们要根据用户设置的message，解析出一个网络可发送的格式
	    // 这一步比较麻烦，暂时想不到好的办法
		byte[] msgBytes = null;
	    int j, i = 0;
	    while((j = message.indexOf((char)QQ.QQ_TAG_DEFAULT_FACE, i)) != -1) {
	    	String sub = message.substring(i, j);
	    	if(!sub.equals("")) {
	    	    msgBytes = Util.getBytes(sub);
			    buf.put(msgBytes);
	    	}
		    buf.put(QQ.QQ_TAG_DEFAULT_FACE);
		    buf.put((byte)(message.charAt(j + 1) & 0xFF));
		    i = j + 2;
	    }
	    if(i < message.length()) {
	    	String sub = message.substring(i);
	    	msgBytes = Util.getBytes(sub);		
		    buf.put(msgBytes);	    	
	    }
	    
		// 只有最后一个分片有空格和字体属性
		if(fragmentSequence == totalFragments - 1) {
			buf.put((byte)0x20);
			fontStyle.writeBean(buf);
		}
        // 写入长度
	    int cur = buf.position();
	    buf.position(pos);
	    buf.putChar((char)(cur - pos - 2));
	    buf.position(cur);
    }

    /**
     * @return Returns the fragmentSequence.
     */
    public int getFragmentSequence() {
        return fragmentSequence;
    }
    /**
     * @param fragmentSequence The fragmentSequence to set.
     */
    public void setFragmentSequence(int fragmentSequence) {
        this.fragmentSequence = fragmentSequence;
    }
    /**
     * @return Returns the totalFragments.
     */
    public int getTotalFragments() {
        return totalFragments;
    }
    /**
     * @param totalFragments The totalFragments to set.
     */
    public void setTotalFragments(int totalFragments) {
        this.totalFragments = totalFragments;
    }
    /**
     * @return Returns the messageId.
     */
    public char getMessageId() {
        return messageId;
    }
    /**
     * @param messageId The messageId to set.
     */
    public void setMessageId(char messageId) {
        this.messageId = messageId;
    }
    
    /**
     * @return Returns the message.
     */
    public String getMessage() {
        return message;
    }
    
    /**
     * @param message The message to set.
     */
    public void setMessage(String message) {
        this.message = message;
    }
    
    /**
     * @return Returns the blue.
     */
    public int getBlue() {
        return fontStyle.getBlue();
    }
    
    /**
     * @param blue The blue to set.
     */
    public void setBlue(int blue) {
        fontStyle.setBlue(blue);
    }
    
    /**
     * @return Returns the bold.
     */
    public boolean isBold() {
        return fontStyle.isBold();
    }
    
    /**
     * @param bold The bold to set.
     */
    public void setBold(boolean bold) {
    	fontStyle.setBold(bold);
    }
    
    /**
     * @return Returns the encoding.
     */
    public char getEncoding() {
        return fontStyle.getEncodingCode();
    }
    
    /**
     * @param encoding The encoding to set.
     */
    public void setEncoding(char encoding) {
        fontStyle.setEncodingCode(encoding);
    }
    
    /**
     * @return Returns the fontName.
     */
    public String getFontName() {
        return fontStyle.getFontName();
    }
    
    /**
     * @param fontName The fontName to set.
     */
    public void setFontName(String fontName) {
        fontStyle.setFontName(fontName);
    }
    
    /**
     * @return Returns the green.
     */
    public int getGreen() {
        return fontStyle.getGreen();
    }
    
    /**
     * @param green The green to set.
     */
    public void setGreen(int green) {
        fontStyle.setGreen(green);
    }
    
    /**
     * @return Returns the italic.
     */
    public boolean isItalic() {
        return fontStyle.isItalic();
    }
    
    /**
     * @param italic The italic to set.
     */
    public void setItalic(boolean italic) {
        fontStyle.setItalic(italic);
    }
    
    /**
     * @return Returns the red.
     */
    public int getRed() {
        return fontStyle.getRed();
    }
    
    /**
     * @param red The red to set.
     */
    public void setRed(int red) {
        fontStyle.setRed(red);
    }
    
    /**
     * @return Returns the underline.
     */
    public boolean isUnderline() {
        return fontStyle.isUnderline();
    }
    
    /**
     * @param underline The underline to set.
     */
    public void setUnderline(boolean underline) {
    	fontStyle.setUnderline(underline);
    }
    
    /**
     * @return Returns the fontSize.
     */
    public int getFontSize() {
        return fontStyle.getFontSize();
    }
    
    /**
     * @param fontSize The fontSize to set.
     */
    public void setFontSize(int fontSize) {
    	fontStyle.setFontSize(fontSize);
    }
}
