/*
* LumaQQ - Java QQ Client
*
* Copyright (C) 2004 luma <stubma@163.com>
*                    notXX
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

import java.nio.ByteBuffer;

import edu.tsinghua.lumaqq.qq.QQ;
import edu.tsinghua.lumaqq.qq.Util;
import edu.tsinghua.lumaqq.qq.beans.QQUser;
import edu.tsinghua.lumaqq.qq.debug.IDebugObject;
import edu.tsinghua.lumaqq.qq.debug.PacketDO;

/**
 * 所有输出包基类，这个基类定义了输出包的基本框架
 * 
 * @author luma
 */
public abstract class OutPacket extends Packet {
	/** 包起始序列号 */
	protected static char seq = (char) Util.random().nextInt();
	/** 是否需要回应 */
	protected boolean ack;
	/** 重发计数器 */
	protected int resendCountDown;
	/** 超时截止时间，单位ms */
	protected long timeout;
	/** 发送次数，只在包是不需要ack时有效，比如logout包是发4次，但是其他可能只发一次 */
	protected int sendCount;
   
    /**
     * 创建一个基本输出包
     * 
     * @param command
     * 		包命令
     * @param ack
     * 		包是否需要回复
     * @param user
     * 		QQ用户对象
     */
    public OutPacket(byte header, char command, boolean ack, QQUser user) {
		super(header, QQ.QQ_CLIENT_VERSION, command, getNextSeq(), user);
		this.ack = ack;
		this.resendCountDown = QQ.QQ_SEND_TIME_NOACK_PACKET;
		this.sendCount = 1;
    }    
    
	/**
	 * 从buf中构造一个OutPacket，用于调试。这个buf里面可能包含了抓包软件抓来的数据
	 * 
	 * @param buf
	 * 			ByteBuffer
	 * @throws PacketParseException
	 * 			解析出错
	 */
	protected OutPacket(ByteBuffer buf, QQUser user) throws PacketParseException {
	    super(buf, user);
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
	protected OutPacket(ByteBuffer buf, int length, QQUser user) throws PacketParseException {
	    super(buf, length, user);
	}		
	
	/* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.packets.Packet#parseBody(java.nio.ByteBuffer)
     */
	@Override
    protected void parseBody(ByteBuffer buf) throws PacketParseException {
    }
    
	/**
	 * 回填，有些字段必须填完整个包才能确定其内容，比如长度字段，那么这个方法将在
	 * 尾部填充之后调用
	 * 
	 * @param buf
	 * 		ByteBuffer
	 * @param startPos
	 * 		包起始位置
	 */
	protected abstract void postFill(ByteBuffer buf, int startPos);
	
    /**
	 * 将整个包转化为字节流, 并写入指定的ByteBuffer对象.
	 * 一般而言, 前后分别需要写入包头部和包尾部.
	 * 
	 * @param buf
	 * 		将包写入的ByteBuffer对象.
	 */
	public void fill(ByteBuffer buf) {
	    // 保存当前pos
	    int pos = buf.position();
	    // 填充头部
	    putHead(buf);
	    // 填充包体
	    bodyBuf.clear();
	    putBody(bodyBuf);
	    bodyBuf.flip();
	    // 加密包体
	    bodyDecrypted = new byte[bodyBuf.limit()];
	    System.arraycopy(bodyBuf.array(), 0, bodyDecrypted, 0, bodyDecrypted.length);
	    byte[] enc = encryptBody(bodyDecrypted);
	    // 加密内容写入最终buf
	    buf.put(enc);
	    // 填充尾部
	    putTail(buf);
	    // 回填
	    postFill(buf, pos);
	    // 查看是否打开了调试模式，如果有，则触发调试事件
	    if(ds.isDebug()) {
	        byte[] debugContent = dump();
	        IDebugObject obj = new PacketDO(getPacketName(), debugContent, false, getHeadLength(), debugContent.length - getTailLength());
	        ds.deliverDebugObject(obj);
	    }
	}
	
	@Override
	public byte[] dump() {
		if(bodyDecrypted == null)
			return new byte[0];
		else {
			byte[] debugContent = new byte[getLength(bodyDecrypted.length)];
			ByteBuffer debugBuf = ByteBuffer.wrap(debugContent);
			putHead(debugBuf);
			debugBuf.put(bodyDecrypted);
			putTail(debugBuf);
			postFill(debugBuf, 0);
			debugBuf = null;
			return debugContent;
		}
	}
	
    /**
	 * @return
	 * 		下一个可用的序列号
	 */
	protected static char getNextSeq() {
	    seq++;
	    // 为了兼容iQQ
	    // iQQ把序列号的高位都为0，如果为1，它可能会拒绝，wqfox称是因为TX是这样做的
	    seq &= 0x7FFF;
	    if(seq == 0)
	        seq++;
	    return seq;
	}	
	
	/* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.packets.Packet#getPacketName()
     */
	@Override
    public String getPacketName() {
        return "Unknown Outcoming Packet";
    }
	
	/**
	 * 是否需要重发.
	 * 
	 * @return 需要重发返回true, 否则返回false.
	 */
	public final boolean needResend() {
		return (resendCountDown--) > 0;
	}
	
	/**
	 * @return true表示包需要回复
	 */
	public final boolean needAck() {
	    return ack;
	}
	
    /**
     * @return Returns the timeout.
     */
    public final long getTimeout() {
        return timeout;
    }
    
    /**
     * @param timeout The timeout to set.
     */
    public final void setTimeout(long timeout) {
        this.timeout = timeout;
    }
    
    /**
     * @param sendCount The sendCount to set.
     */
    public final void setSendCount(int sendCount) {
        this.sendCount = sendCount;
    }
    
    /**
     * @return Returns the sendCount.
     */
    public final int getSendCount() {
        return sendCount;
    }
}
