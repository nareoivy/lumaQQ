/*
* LumaQQ - Java QQ Client
*
* Copyright (C) 2004 notXX
*                    luma <stubma@163.com>
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
package edu.tsinghua.lumaqq.qq.packets;

import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.tsinghua.lumaqq.qq.Crypter;
import edu.tsinghua.lumaqq.qq.QQ;
import edu.tsinghua.lumaqq.qq.beans.QQUser;
import edu.tsinghua.lumaqq.qq.debug.DebugSwitch;
import edu.tsinghua.lumaqq.qq.debug.IDebugObject;
import edu.tsinghua.lumaqq.qq.debug.PacketDO;


/**
 * QQ所有包对象的基类
 * 
 * @author notxx
 * @author luma
 */
public abstract class Packet {
	/**
	 * logger.
	 */
	protected static Log log = LogFactory.getLog(Packet.class);
	/**
	 * 解密者.
	 */
	protected static final Crypter crypter = new Crypter();
	/** 
	 * 包体缓冲区，有back array，用来存放未加密时的包体，子类应该在putBody方法中
	 * 使用这个缓冲区。使用之前先执行clear() 
	 */
	protected static final ByteBuffer bodyBuf = ByteBuffer.allocate(QQ.QQ_MAX_PACKET_SIZE);
	
	/** 调试模式开关 */
	protected static DebugSwitch ds = DebugSwitch.getInstance();
	
	/** 解密密钥 */
	protected byte[] decryptKey;
	
	/** 第一次解密失败后再次尝试的密钥 */
	protected byte[] fallbackDecryptKey;
	
	/** 加密密钥 */
	protected byte[] encryptKey;
	
	/**
	 * 包命令, 0x03~0x04.
	 */
	protected char command;

	/**
	 * 源标志, 0x01~0x02.
	 */
	protected char source;

	/**
	 * 包序号, 0x05~0x06.
	 */
	protected char sequence;
	
	/** 包头字节 */
	protected byte header;
	
	/**
	 * QQUser
	 * 为了支持一个JVM中创建多个QQClient，包中需要保持一个QQUser的引用以
	 * 确定包的用户相关字段如何填写
	 */
	protected QQUser user;
	
	/**
	 * true表示这个包是一个重复包，重复包本来是不需要处理的，但是由于LumaQQ较常发生
	 * 消息确认包丢失的问题，所以，这里加一个字段来表示到来的消息包是重复的。目前这个
	 * 字段只对消息有效，姑且算个解决办法吧，虽然不是太好看
	 */
	protected boolean duplicated;
	
	/** 明文包体 */
	protected byte[] bodyDecrypted;
	
	/**
	 * 构造一个指定参数的包
	 * 
	 * @param header
	 * 		包头
	 * @param source
	 * 		包源
	 * @param command
	 * 		包命令 
	 * @param sequence
	 * 		包序号 
	 * @param user	
	 * 		QQ用户对象
	 */
	public Packet(byte header, char source, char command, char sequence, QQUser user) {
	    this.user = user;
		this.source = source;
		this.command = command;
		this.sequence = sequence;
		this.duplicated = false;
		this.header = header;
	}
	
	/**
	 * 从buf中构造一个OutPacket，用于调试。这个buf里面可能包含了抓包软件抓来的数据
	 * 
	 * @param buf
	 * 			ByteBuffer
	 * @throws PacketParseException
	 * 			解析出错
	 */
	protected Packet(ByteBuffer buf, QQUser user) throws PacketParseException {
	    this(buf, buf.limit() - buf.position(), user);
	}

	/**
	 * 从buf中构造一个OutPacket，用于调试。这个buf里面可能包含了抓包软件抓来的数据
	 * 
	 * @param buf
	 * 			ByteBuffer
	 * @param length
	 * 			要解析的内容长度
	 * @throws PacketParseException
	 * 			如果解析出错
	 */
	protected Packet(ByteBuffer buf, int length, QQUser user) throws PacketParseException {
	    this.user = user;
	    // 解析头部
	    parseHeader(buf);
	    // 检查QQ号
	    if(!validateHeader())
	        throw new PacketParseException("包头有误，抛弃该包: " + toString());
	    // 得到包体
	    byte[] body = getBodyBytes(buf, length);
	    bodyDecrypted = decryptBody(body);
		if(bodyDecrypted == null)
	        throw new PacketParseException("包内容解析出错，抛弃该包: " + toString());
	    // 包装到ByteBuffer
	    ByteBuffer tempBuf = ByteBuffer.wrap(bodyDecrypted);
	    try {
		    // 解析
		    parseBody(tempBuf);	        
	    } catch (BufferUnderflowException e) {
	        throw new PacketParseException(e.getMessage());
	    }
	    parseTail(buf);
	    // 查看是否打开了调试模式，如果有，则触发调试事件
	    if(ds.isDebug()) {	        
	        byte[] debugContent = dump();
	        IDebugObject obj = new PacketDO(getPacketName(), debugContent, this instanceof InPacket, getHeadLength(), debugContent.length - getTailLength());
	        ds.deliverDebugObject(obj);
	    }
	}		
	
	/**
	 * 导出包的全部内容到一个字节数组中，主要用于调试
	 * 
	 * @return
	 * 		包的字节数组
	 */
	public byte[] dump() {
		if(bodyDecrypted == null)
			return new byte[0];
		else {
			byte[] debugContent = new byte[getLength(bodyDecrypted.length)];
			ByteBuffer debugBuf = ByteBuffer.wrap(debugContent);
			putHead(debugBuf);
			debugBuf.put(bodyDecrypted);
			putTail(debugBuf);
			debugBuf = null;
			return debugContent;
		}
	}
	
	/**
	 * 构造一个包对象，什么字段也不填，仅限于子类使用
	 */
	protected Packet() {	    
	}	
    
    /**
     * 得到UDP形式包的总长度，不考虑TCP形式
     * 
     * @param bodyLength
     * 		包体长度
     * @return
     * 		包长度
     */
    protected abstract int getLength(int bodyLength);
    
    /**
     * 校验头部
     *
     * @return
     * 		true表示头部有效
     */
    protected abstract boolean validateHeader();
    
    /**
     * @return
     * 		包头长度
     */
    protected abstract int getHeadLength();
    
    /**
     * @return
     * 		包尾长度
     */
    protected abstract int getTailLength();
    
	/**
	 * 将包头部转化为字节流, 写入指定的ByteBuffer对象.
	 * 
	 * @param buf
	 *                   写入的ByteBuffer对象.
	 */
	protected abstract void putHead(ByteBuffer buf);
	
	/**
	 * 初始化包体
	 * 
	 * @param buf
	 * 			ByteBuffer
	 */
	protected abstract void putBody(ByteBuffer buf);
	
    /**
     * 得到包体的字节数组
     * 
     * @param buf
     * 		ByteBuffer
     * @param length
     * 		包总长度
     * @return
     * 		包体字节数组
     */
    protected abstract byte[] getBodyBytes(ByteBuffer buf, int length);
    
    /**
     * @return
     * 		标识这个包属于哪个协议族
     */
    public abstract int getFamily();
	
	/**
	 * 将包尾部转化为字节流, 写入指定的ByteBuffer对象.
	 * 
	 * @param buf
	 * 		写入的ByteBuffer对象.
	 */
	protected abstract void putTail(ByteBuffer buf);	
	
	/**
	 * 加密包体
	 * 
	 * @param b 
	 * 		未加密的字节数组
	 * @return
	 * 		加密的包体
	 */
	protected byte[] encryptBody(byte[] b) {
		// get start
		int start = getEncryptStart();
		if(start == -1)
			return b;
		
		// get length
		int length = getEncryptLength();
		if(length == -1)
			length = b.length - start;
		
		// encrypt
		byte[] enc = crypter.encrypt(b, start, length, getEncryptKey(b));
		
		// 出错?
		if(enc == null)
			return b;
		
		// 组装返回结果
		byte[] ret = enc;
		if(b.length - length > 0) {
			ret = new byte[b.length - length + enc.length];
			System.arraycopy(b, 0, ret, 0, start);
			System.arraycopy(enc, 0, ret, start, enc.length);
			System.arraycopy(b, start + length, ret, start + enc.length, b.length - start - length);
		}
		
		return ret;
	}
	
	/**
	 * 解密包体
	 * 
     * @param body
     * 			包体字节数组
     * @return 解密的包体字节数组
     */
	protected byte[] decryptBody(byte[] body) {
		// 得到解密起始
	    int start = getDecryptStart();
	    if(start == -1) // 没有加密
	    	return body;
	    
	    // 得到解密长度
	    int length = getDecryptLength();
	    if(length == -1)
	    	length = body.length - start;
	    
	    // 第一次解密
	    byte[] dec = crypter.decrypt(body, start, length, getDecryptKey(body));
	    
	    // 第二次解密
	    if(dec == null)
	    	dec = crypter.decrypt(body, start, length, getFallbackDecryptKey(body));
	    
	    // 出错?
	    if(dec == null)
	    	return null;
	    
	    // 如果start大于0
	    byte[] ret = dec;
	    if(body.length - length > 0) {
	    	ret = new byte[dec.length + body.length - length];
	    	System.arraycopy(body, 0, ret, 0, start);
	    	System.arraycopy(dec, 0, ret, start, dec.length);
	    	System.arraycopy(body, start + length, ret, start + dec.length, body.length - start - length);
	    }
	    
	    return ret;
	}
	
    /**
     * @return
     * 		加密的起始位置，这个位置是相对于包体的第一个字节来说的，-1表示此包不需要加密
     */
    protected int getEncryptStart() {
    	return 0;
    }
    
    /**
     * @return
     * 		需要加密的明文长度，如果为-1，表示加密到包体结束为止
     */
    protected int getEncryptLength() {
    	return -1;
    }
    
    /**
     * @return
     * 		解密的起始位置，这个位置相对于包体来说，-1表示此包没有加密
     */
    protected int getDecryptStart() {
    	return 0;
    }
    
    /**
     * @return
     * 		解密的密文长度，-1表示一直到包体结束
     */
    protected int getDecryptLength() {
    	return -1;
    }
	
	/**
	 * 解析包体，从buf的开头位置解析起
	 * 
	 * @param buf
	 * 			ByteBuffer
	 * @throws PacketParseException
	 * 			如果解析出错
	 */
	protected abstract void parseBody(ByteBuffer buf) throws PacketParseException;
	
	/**
	 * 从buf的当前位置解析包头
	 * 
	 * @param buf
	 * 		ByteBuffer
	 * @throws PacketParseException
	 * 		如果解析出错
	 */
	protected abstract void parseHeader(ByteBuffer buf) throws PacketParseException;
	
	/**
	 * 从buf的当前未知解析包尾
	 * 
	 * @param buf
	 * 		ByteBuffer
	 * @throws PacketParseException
	 * 		如果解析出错
	 */
	protected abstract void parseTail(ByteBuffer buf) throws PacketParseException;
	
	@Override
	public String toString() {
		return "包名: " + getPacketName() + " 包序号: " + (int)sequence;
	}
	
	public String toDebugString() {
		return "toDebugString not implemented!";
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Packet) {
			Packet packet = (Packet)obj;
			return header == packet.header && 
				command == packet.command && 
				sequence == packet.sequence;
		} else
			return super.equals(obj);
	}

	/**
	 * 把序列号和命令拼起来作为哈希码. 为了避免不同header的包有相同的命令，Header也参与进来
	 */
	@Override
	public int hashCode() {
		return hash(sequence, command);
	}
	
	/**
	 * 得到hash值
	 * 
	 * @param header
	 * @param sequence
	 * @param command
	 * @return
	 */
	public static int hash(char sequence, char command) {
		return (sequence << 16) | command;
	}
	
    /**
     * @return Returns the command.
     */
    public char getCommand() {
        return command;
    }
    
    /**
     * @return Returns the sequence.
     */
    public char getSequence() {
        return sequence;
    }
    
    /**
     * @param sequence The sequence to set.
     */
    public void setSequence(char sequence) {
        this.sequence = sequence;
    }
    
    /**
     * @return
     * 		包的描述性名称
     */
    public String getPacketName() {
        return "Unknown Packet";
    }
    
    /**
     * @return Returns the source.
     */
    public char getSource() {
        return source;
    }
    
    /**
     * @return Returns the duplicated.
     */
    public boolean isDuplicated() {
        return duplicated;
    }
    
    /**
     * @param duplicated The duplicated to set.
     */
    public void setDuplicated(boolean duplicated) {
        this.duplicated = duplicated;
    }
    
    /**
     * @return Returns the header.
     */
    public byte getHeader() {
        return header;
    }
    
    /**
     * @param header The header to set.
     */
    public void setHeader(byte header) {
        this.header = header;
    }

	public byte[] getDecryptKey(byte[] body) {
		return decryptKey;
	}

	public void setDecryptKey(byte[] decryptKey) {
		this.decryptKey = decryptKey;
	}

	public byte[] getEncryptKey(byte[] body) {
		return encryptKey;
	}

	public void setEncryptKey(byte[] encryptKey) {
		this.encryptKey = encryptKey;
	}

	public byte[] getFallbackDecryptKey(byte[] body) {
		return fallbackDecryptKey;
	}

	public void setFallbackDecryptKey(byte[] fallbackDecryptKey) {
		this.fallbackDecryptKey = fallbackDecryptKey;
	}
}