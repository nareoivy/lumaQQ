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

import java.io.IOException;
import java.nio.ByteBuffer;

import edu.tsinghua.lumaqq.qq.QQ;
import edu.tsinghua.lumaqq.qq.beans.FontStyle;
import edu.tsinghua.lumaqq.qq.beans.QQUser;
import edu.tsinghua.lumaqq.qq.packets.BasicOutPacket;
import edu.tsinghua.lumaqq.qq.packets.PacketParseException;


/**
 * <pre>
 * 发送消息的包，格式为
 * 头部
 * --------- 加密开始（会话密钥）-----------
 * 发送者QQ号，4个字节
 * 接收者的QQ号，4个字节
 * 发送者QQ版本，2字节
 * 发送者QQ号，4字节
 * 接收者QQ号，4个字节（奇怪，为什么要搞两个在里面）
 * 发送者QQ号和session key合在一起用md5处理一次的结果，16字节
 * 消息类型，2字节
 * 会话ID，2字节，如果是一个操作需要发送多个包才能完成，则这个id必须一致
 * 发送时间，4字节
 * 发送者头像，2字节
 * 字体信息，4字节，设成0x00000001吧，不懂具体意思
 * 消息分片数，1字节，如果消息比较长，这里要置一个分片值，QQ缺省是700字节一个分片，这个700字节是纯消息，不包含其他部分
 * 分片序号，1字节，从0开始
 * 消息的id，2字节，同一条消息的不同分片id相同
 * 消息方式，是发送的，还是自动回复的，1字节
 * 消息内容
 * 空格，1字节，这个空格只有在最后一个分片才有，用来表示消息的结束
 * ---------- FontStyle Start ---------
 * 字体属性，2字节，已知的位是
 * 		i.   bit0-bit4用来表示字体大小，所以最大是32
 *      ii.  bit5表示是否bold
 *      iii. bit6表示是否italic
 *      iv.  bit7表示是否underline
 * 颜色Red，1字节
 * 颜色Green，1字节
 * 颜色Blue，1字节
 * 1个未知字节，置0先
 * 消息编码，2字节，0x8602为GB，0x0000为EN，其他未知，好像可以自定义，因为服务器好像不干涉
 * 字体名，比如0xcb, 0xce, 0xcc, 0xe5表示宋体 
 * 1字节，表示18和19部分的字节长度
 * ---------- FontStyle End ---------
 * -------- 加密结束 ---------
 * 尾部
 *
 * 请求传送文件的包，这是这个包的另一种用法，其格式为
 * 1  - 14. 1到14部分均与发送消息包相同，只有第8部分不同，对于UDP的请求，8部分是0x0035，对于TCP，是0x0001
 * 15 - 17. 怀疑也和发送消息包相同，但是在这种情况中，这部分没有使用，为全0，一共11个0字节
 * 18. 传输类型，1字节，表示是传文件还是传表情
 * 19. 连接方式字节，UDP是0， TCP是3
 * 20. 4个字节的发送者外部ip地址（也就是可能为代理地址）
 * 21. 2个字节的发送者端口
 * 22. 2个字节的端口，第一个监听端口，TCP没有这个部分
 * 23. 4个字节的地址，真实IP
 * 24. 2个字节的端口，第二个而监听端口
 * 25. 空格符号做为上述信息的结束，一个字节，0x20
 * 26. 分隔符0x1F
 * 27. 要传送的文件名
 * 28. 分隔符0x1F
 * 29. 字节数的字符串形式后跟" 字节"，比如文件大小3字节的话，就是"3 字节"这个字符串的编码形式
 * 30. 尾部 
 * 
 * 同意传送文件的包，格式为
 * 1  - 24. 除了8部分，其他均与发送消息包相同。对于UDP的情况，8部分是0x0037，TCP是0x0003。
 *          UDP时，最后的本地ip和端口都是0；TCP时没有22部分
 * 25. 尾部
 * 
 * 拒绝接收文件的包，格式为
 * 1 - 19. 除了8部分，均与同意传送文件包相同。对于UDP的情况，8部分是0x0039，对于TCP，是0x0005
 * 20. 尾部
 * 
 * 通知我的IP信息，格式为
 * 1 - 24. 除了8部分，均与请求传送文件包相同。8部分是0x003B
 * 25. 尾部
 * 
 * 取消传送文件，格式为
 * 1 - 18. 除了8部分，均与请求传送文件包相同。8部分是0x0049
 * 19. 尾部
 * 
 * 要求别人主动连接我的包，格式为
 * 1 - 18. 除了8部分，均与请求传送文件包相同。8部分是0x003F
 * 19. 尾部
 * </pre>
 * 
 * @author luma
 */
public class SendIMPacket extends BasicOutPacket {
	// 下面为发送普通消息需要设置的变量
	private FontStyle fontStyle;
    private int receiver;
    private byte[] message;
    private char messageType;
    private byte replyType;
    private int totalFragments;
    private int fragmentSequence;
    private char messageId;
    
    // 下面为发送文件时需要设置的变量
    private String fileName;
    private String fileSize;
    private char directPort;
    private char localPort;
    private byte[] localIp;
    private char sessionId;
    private byte transferType;
    
    // true时表示发送一个假IP，用在如来神掌中，免得泄漏自己的IP
    private boolean fakeIp;
    
    private static final byte DELIMIT = 0x1F;
    
    /**
     * 构造函数
     */
    public SendIMPacket(QQUser user) {
        super(QQ.QQ_CMD_SEND_IM, true, user);
        fontStyle = new FontStyle();
        message = null;
        messageType = QQ.QQ_IM_TYPE_TEXT;
        replyType = QQ.QQ_IM_NORMAL_REPLY;
        transferType = QQ.QQ_TRANSFER_FILE;
        fakeIp = false;
        totalFragments = 1;
        fragmentSequence = 0;
    }

    /**
     * @param buf
     * @param length
     * @throws PacketParseException
     */
    public SendIMPacket(ByteBuffer buf, int length, QQUser user) throws PacketParseException {
        super(buf, length, user);
    }
    
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.packets.OutPacket#getPacketName()
     */
	@Override
    public String getPacketName() {
        return "Send IM Packet";
    }
    
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.packets.OutPacket#putBody(java.nio.ByteBuffer)
     */
	@Override
    protected void putBody(ByteBuffer buf) {
	    // 发送者QQ号
	    buf.putInt(user.getQQ());
	    // 接收者QQ号
	    buf.putInt(receiver);
	    // 发送者QQ版本
	    buf.putChar(source);
	    // 发送者QQ号
	    buf.putInt(user.getQQ());
	    // 接收者QQ号
	    buf.putInt(receiver);
	    // 文件传输会话密钥
	    buf.put(user.getFileSessionKey());
	    // 消息类型
	    buf.putChar(messageType);
	    // 顺序号
	    if(sessionId == 0)
	    	buf.putChar(sequence);
	    else
	    	buf.putChar(sessionId);
	    // 发送时间
	    int time = (int)(System.currentTimeMillis() / 1000);
	    buf.putInt(time);
	    // 发送者头像
	    char face = (char)user.getContactInfo().head;
	    buf.putChar(face);
	    // 字体信息，设成1
	    buf.putInt(1);
	    // 暂时为如来神掌做的设置
	    if(fakeIp)
		    buf.putInt(0);
	    else {
		    // 分片数
		    buf.put((byte)totalFragments);
		    // 分片序号
		    buf.put((byte)fragmentSequence);
		    // 消息id
		    buf.putChar(messageId);	        
	    }
	    
	    // 判断消息类型
	    switch(messageType) {
		    case QQ.QQ_IM_TYPE_TEXT:
		    	initTextContent(buf);
		    	break;
		    case QQ.QQ_IM_TYPE_UDP_REQUEST:
		    	initSendFileContent(buf);
		    	break;
		    case QQ.QQ_IM_TYPE_ACCEPT_UDP_REQUEST:
		    	initSendFileAcceptContent(buf);
		    	break;
		    case QQ.QQ_IM_TYPE_REJECT_UDP_REQUEST:
		    case QQ.QQ_IM_TYPE_REJECT_TCP_REQUEST:
		        initSendFileRejectContent(buf);
		    	break;
		    case QQ.QQ_IM_TYPE_NOTIFY_IP:
		        initNotifyFilePortUDP(buf);
		    	break;
		    case QQ.QQ_IM_TYPE_REQUEST_CANCELED:
		        initConnectionCanceled(buf);
		    	break;
		    case QQ.QQ_IM_TYPE_ARE_YOU_BEHIND_FIREWALL:
		        initPleaseConnectMe(buf);
		    	break;
	    }
    } 
    
    /**
     * 初始化请求对方主动连接的包
     * @param buf
     */
    private void initPleaseConnectMe(ByteBuffer buf) {
	    // 17 - 19. 怀疑也和发送消息包相同，但是在这种情况中，这部分没有使用，为全0，一共11个0字节
        buf.putLong(0);
        buf.putChar((char)0);
        buf.put((byte)0);
	    // 我们先尝试UDP方式
        buf.put(transferType);
        buf.put((byte)0x0);
	    // 四个字节的发送者IP，这是外部IP
        buf.put(user.getIp());
	    // 发送者端口
        buf.putChar((char)user.getPort());
	    // 监听端口，含义未知，为连接服务器的端口，先随便写一个值
        buf.putChar(directPort);
	    // 后面全0
        buf.putInt(0);
        buf.putChar((char)0);
    }

    /**
     * 初始化取消发送文件包
     * @param buf
     */
    private void initConnectionCanceled(ByteBuffer buf) {
	    // 17 - 19. 怀疑也和发送消息包相同，但是在这种情况中，这部分没有使用，为全0，一共11个0字节
	    buf.putLong(0);
	    buf.putChar((char)0);
	    buf.put((byte)0);
	    // 传输类型
	    buf.put(transferType);
    }

    /**
     * 初始化IP信息通知包
     * @param buf
     */
    private void initNotifyFilePortUDP(ByteBuffer buf) {
	    // 17 - 19. 怀疑也和发送消息包相同，但是在这种情况中，这部分没有使用，为全0，一共11个0字节
        buf.putLong(0);
        buf.putChar((char)0);
        buf.put((byte)0);
	    // 我们先尝试UDP方式
        buf.put(transferType);
        buf.put((byte)0x0);
	    // 四个字节的发送者IP，这是外部IP
        buf.put(user.getIp());
	    // 发送者端口
        buf.putChar((char)user.getPort());
	    // 监听端口，含义未知，为连接服务器的端口，先随便写一个值
        buf.putChar(directPort);
	    // 真实IP和第二个端口
        buf.put(localIp);
        buf.putChar(localPort);
    }

    /**
     * 初始化拒绝接收文件包的其余部分
     * @param buf
     */
    private void initSendFileRejectContent(ByteBuffer buf) {
	    // 17 - 19. 怀疑也和发送消息包相同，但是在这种情况中，这部分没有使用，为全0，一共11个0字节
        buf.putLong(0);
        buf.putChar((char)0);
        buf.put((byte)0);
	    // 我们先尝试UDP方式
        buf.put(transferType);
    }

 	/**
 	 * 初始化同意接收文件包的其余部分
	 * @param buf
	 * @throws IOException
	 */
	private void initSendFileAcceptContent(ByteBuffer buf) {
	    // 17 - 19. 怀疑也和发送消息包相同，但是在这种情况中，这部分没有使用，为全0，一共11个0字节
	    buf.putLong(0);
	    buf.putChar((char)0);
	    buf.put((byte)0);
	    // 我们先尝试UDP方式
	    buf.put(transferType);
	    buf.put((byte)0x0);
	    // 四个字节的发送者IP，这是外部IP
	    buf.put(user.getIp());
	    // 发送者端口
	    buf.putChar((char)user.getPort());
	    // 监听端口，含义未知，为连接服务器的端口，先随便写一个值
	    buf.putChar(directPort);
	    // 后面全0
	    buf.putInt(0);
	    buf.putChar((char)0);
	}

	/**
	 * 初始化请求发送文件包的其余部分
	 * @param buf
	 */
	private void initSendFileContent(ByteBuffer buf) {
	    // 17 - 19. 怀疑也和发送消息包相同，但是在这种情况中，这部分没有使用，为全0，一共11个0字节
	    buf.putLong(0);
	    buf.putChar((char)0);
	    buf.put((byte)0);
	    // 我们先尝试UDP方式
	    buf.put(transferType);
	    buf.put((byte)0x0);
	    if(fakeIp) {
	        buf.putInt(0);
	        buf.putChar((char)0);
	    } else {
		    // 四个字节的发送者IP，这是外部IP
		    buf.put(user.getIp());
		    // 发送者端口
		    buf.putChar((char)user.getPort());	        
	    }
	    // 直接端口
	    buf.putChar(directPort);
	    buf.putInt(0);
	    buf.putChar((char)0);
	    buf.put((byte)0x20);
	    buf.put(DELIMIT);
	    buf.put(fileName.getBytes());
	    buf.put(DELIMIT);
	    buf.put(fileSize.getBytes());
	}

	/**
	 * 初始化普通消息包的其余部分
	 * @param buf
	 */
	private void initTextContent(ByteBuffer buf) {
	    // 消息方式，是发送的，还是自动回复的，1字节
	    buf.put(replyType);
	    // 写入消息正文字节数组
	    if(message != null)
	    	buf.put(message);
	    // 最后一个分片时追加空格
	    if(fragmentSequence == totalFragments - 1)
	    	buf.put((byte)0x20);
	    // 消息尾部，字体修饰属性
	    fontStyle.writeBean(buf);
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
    
    /**
     * @return Returns the receiver.
     */
    public int getReceiver() {
        return receiver;
    }
    
    /**
     * @param receiver The receiver to set.
     */
    public void setReceiver(int receiver) {
        this.receiver = receiver;
    }
    
    /**
     * @return Returns the messageType.
     */
    public char getMessageType() {
        return messageType;
    }
    
    /**
     * @param messageType The messageType to set.
     */
    public void setMessageType(char messageType) {
        this.messageType = messageType;
    }
    
    /**
     * @return Returns the replyType.
     */
    public byte getReplyType() {
        return replyType;
    }
    
    /**
     * @param replyType The replyType to set.
     */
    public void setReplyType(byte replyType) {
        this.replyType = replyType;
    }  
    
	/**
	 * @param filePath The filePath to set.
	 */
	public void setFileName(String filePath) {
		this.fileName = filePath;
	}
	
	/**
	 * @param fileSize The fileSize to set.
	 */
	public void setFileSize(int size) {
		this.fileSize = String.valueOf(size) + " 字节";
	}
	
	/**
	 * @param port The port to set.
	 */
	public void setDirectPort(int port) {
		this.directPort = (char)port;
	}
    
	/**
	 * @return Returns the requestSequence.
	 */
	public char getSessionId() {
		return sessionId;
	}
	
	/**
	 * @param requestSequence The requestSequence to set.
	 */
	public void setSessionId(char requestSequence) {
		this.sessionId = requestSequence;
	}
	
    /**
     * @return Returns the localIp.
     */
    public byte[] getLocalIp() {
        return localIp;
    }
    
    /**
     * @param localIp The localIp to set.
     */
    public void setLocalIp(byte[] localIp) {
        this.localIp = localIp;
    }
    
    /**
     * @return Returns the localPort.
     */
    public char getLocalPort() {
        return localPort;
    }
    
    /**
     * @param localPort The localPort to set.
     */
    public void setLocalPort(int localPort) {
        this.localPort = (char)localPort;
    }
    
    public byte getTransferType() {
        return transferType;
    }
    
    public void setTransferType(byte transferType) {
        this.transferType = transferType;
    }
    
    public boolean isFakeIp() {
        return fakeIp;
    }
    
    public void setFakeIp(boolean fakeIp) {
        this.fakeIp = fakeIp;
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
	 * @return the message
	 */
	public byte[] getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(byte[] message) {
		this.message = message;
	}
}
