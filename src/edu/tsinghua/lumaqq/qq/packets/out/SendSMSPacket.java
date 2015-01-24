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
import java.util.List;

import edu.tsinghua.lumaqq.qq.QQ;
import edu.tsinghua.lumaqq.qq.Util;
import edu.tsinghua.lumaqq.qq.beans.QQUser;
import edu.tsinghua.lumaqq.qq.packets.BasicOutPacket;
import edu.tsinghua.lumaqq.qq.packets.PacketParseException;

/**
 * <pre>
 * 发送短消息的请求包，格式为：
 * 1. 包头
 * 2. 消息序号，2字节，从0开始，用在拆分发送中
 * 3. 未知2字节，全0
 * 4. 未知4字节，全0
 * 5. 发送者昵称，最长13个字节，如果不足，则后面为0
 * 6. 未知的1字节，0x01
 * 7. 如果是免提短信，0x20，其他情况，0x00
 * 8. 短消息内容类型，1字节
 * 9. 短消息内容类型编号，4字节
 * 10. 未知的1字节，0x01
 * 11. 接受者中的手机号码个数，1字节
 * 12. 手机号码，18字节，不足的为0
 * 13. 未知的2字节
 * 14. 未知的1字节
 * 15. 如果有更多手机号，重复12-14部分
 * 注：12-15部分只在11部分不为0时存在
 * 16. 接受者中的QQ号码个数，1字节
 * 17. QQ号码，4字节
 * 18. 如果有更多QQ号码，重复17部分
 * 注：17-18部分只有在16部分不为0时存在
 * 19. 未知1字节，一般是0x03
 * 20. 短消息字节长度，2字节，如果8部分不为0，则此部分0x0000
 * 注：QQ的短信和发送者昵称加起来不能超过58个字符（英文和汉字都算是一个字符），
 * 昵称最长是13字节，所以最短也应该能发43个字符，所以可以考虑不按照QQ的做法，
 * 我们可以尽量发满86个字节。
 * 21. 短消息字节数组，消息的格式如下：
 * 		如果是普通的消息，则就是平常的字节数组而已
 *      如果有些字符有闪烁，则那些字节要用0x01括起来
 *      如果这条消息是一条长消息拆分而成的部分，则在消息字节数组前面要加一部分内容，这部分内容是
 *      [消息序号的字符串形式，从1开始] [0x2F] [总消息条数的字符串形式] [0x0A]
 * 注：21部分只有当20部分部位0时存在
 * 22. 尾部
 * 
 * 调用这个包时，message的内容必须是已经组装好的
 * </pre>
 * 
 * @author luma
 */
public class SendSMSPacket extends BasicOutPacket {    
    private char messageSequence;
    private byte[] message;
    private byte contentType;
    private int contentId;
    private byte sendMode;
    private String senderName;
    private List<String> receiverMobile;
    private List<Integer> receiverQQ;
    
    /**
     * 创建SendSMSPacket
     */
    public SendSMSPacket(QQUser user) {
        super(QQ.QQ_CMD_SEND_SMS, true, user);
        messageSequence = 0;
        contentType = QQ.QQ_SMS_CONTENT_NORMAL;
        contentId = 0;
        sendMode = QQ.QQ_SMS_MODE_NORMAL;
    }
    
    /**
     * @param buf
     * @param length
     * @throws PacketParseException
     */
    public SendSMSPacket(ByteBuffer buf, int length, QQUser user)
            throws PacketParseException {
        super(buf, length, user);
    }
    
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.packets.OutPacket#getPacketName()
     */
	@Override
    public String getPacketName() {
        return "Send SMS Packet";
    }
    
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.packets.OutPacket#putBody(java.nio.ByteBuffer)
     */
	@Override
    protected void putBody(ByteBuffer buf) {                
        // 短消息序号
        buf.putChar(messageSequence);
        // 未知2字节
        buf.putChar((char)0);
        // 未知4字节
        buf.putInt(0);
        // 发送者昵称
        byte[] b = Util.getBytes(senderName);
        if(b.length > QQ.QQ_MAX_SMS_SENDER_NAME) {
            buf.put(b, 0, QQ.QQ_MAX_SMS_SENDER_NAME);
        } else {
            buf.put(b);
            int stuff = QQ.QQ_MAX_SMS_SENDER_NAME - b.length;
            while(stuff-- > 0)
                buf.put((byte)0);
        }
        // 未知1字节
        buf.put((byte)0x01);
        // 发送模式
        buf.put(sendMode);
        // 内容类型
        buf.put(contentType);
        // 内容编号
        buf.putInt(contentId);
        // 未知1字节
        buf.put((byte)0x01);
        // 手机个数
        if(receiverMobile == null)
        	buf.put((byte)0);
        else {
        	buf.put((byte)receiverMobile.size());
        	for(String mobile : receiverMobile) {
                b = Util.getBytes(mobile);
                if(b.length > QQ.QQ_MAX_SMS_MOBILE_LENGTH) {
                    buf.put(b, 0, QQ.QQ_MAX_SMS_MOBILE_LENGTH);
                } else {
                    buf.put(b);
                    int stuff = QQ.QQ_MAX_SMS_MOBILE_LENGTH - b.length;
                    while(stuff-- > 0)
                        buf.put((byte)0);
                }
                // 未知的2字节
                buf.putChar((char)0);
                // 未知的1字节
                buf.put((byte)0);
        	}
        }
        // QQ号码个数
        if(receiverQQ == null)
        	buf.put((byte)0);
        else {
        	buf.put((byte)receiverQQ.size());
        	for(Integer qq : receiverQQ) 
        		buf.putInt(qq);
        }
        // 未知1字节
        buf.put((byte)0x03);
        // 消息
        if(message == null)
        	buf.putChar((char)0);
        else {
        	buf.putChar((char)message.length);
        	// 消息
        	buf.put(message);        	
        }
    }
    
    /**
     * @return Returns the contentId.
     */
    public int getContentId() {
        return contentId;
    }
    
    /**
     * @param contentId The contentId to set.
     */
    public void setContentId(int contentId) {
        this.contentId = contentId;
    }
    
    /**
     * @return Returns the contentType.
     */
    public byte getContentType() {
        return contentType;
    }
    
    /**
     * @param contentType The contentType to set.
     */
    public void setContentType(byte contentType) {
        this.contentType = contentType;
    }
    
    /**
     * @return Returns the message.
     */
    public byte[] getMessage() {
        return message;
    }
    
    /**
     * @param message The message to set.
     */
    public void setMessage(byte[] message) {
        this.message = message;
    }
    
    /**
     * @return Returns the messageSequence.
     */
    public char getMessageSequence() {
        return messageSequence;
    }
    
    /**
     * @param messageSequence The messageSequence to set.
     */
    public void setMessageSequence(char messageSequence) {
        this.messageSequence = messageSequence;
    }
    
    /**
     * @return Returns the senderName.
     */
    public String getSenderName() {
        return senderName;
    }
    
    /**
     * @param senderName The senderName to set.
     */
    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }
    
    /**
     * @return Returns the sendMode.
     */
    public byte getSendMode() {
        return sendMode;
    }
    
    /**
     * @param sendMode The sendMode to set.
     */
    public void setSendMode(byte sendMode) {
        this.sendMode = sendMode;
    }

	/**
	 * @return Returns the receiverMobile.
	 */
    public List<String> getReceiverMobile() {
		return receiverMobile;
	}

	/**
	 * @param receiverMobile The receiverMobile to set.
	 */
	public void setReceiverMobile(List<String> receiverMobile) {
		this.receiverMobile = receiverMobile;
	}

	/**
	 * @return Returns the receiverQQ.
	 */
	public List<Integer> getReceiverQQ() {
		return receiverQQ;
	}

	/**
	 * @param receiverQQ The receiverQQ to set.
	 */
	public void setReceiverQQ(List<Integer> receiverQQ) {
		this.receiverQQ = receiverQQ;
	}
}
