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

import java.nio.ByteBuffer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.tsinghua.lumaqq.qq.QQ;
import edu.tsinghua.lumaqq.qq.beans.QQUser;

/**
 * @author notXX
 * @author luma
 */
public final class PacketHelper {
    // Log对象
    static Log log = LogFactory.getLog(PacketHelper.class);
    // 当前使用的parser
    private IParser parser;
    
    private static final int PARSER_COUNT = 3;
    private int[] family;
    private IParser[] parsers;
    
    public PacketHelper(int supportedFamily) {    	
    	family = new int[] {
    		QQ.QQ_PROTOCOL_FAMILY_BASIC,
    		QQ.QQ_PROTOCOL_FAMILY_03,
    		QQ.QQ_PROTOCOL_FAMILY_05,
    	};
    	parsers = new IParser[] {
    		(supportedFamily * QQ.QQ_PROTOCOL_FAMILY_BASIC) != 0 ? new BasicFamilyParser() : null,
    		(supportedFamily * QQ.QQ_PROTOCOL_FAMILY_03) != 0 ? new _03FamilyParser() : null,
    		(supportedFamily * QQ.QQ_PROTOCOL_FAMILY_05) != 0 ? new _05FamilyParser() : null,
    	};
    }
    
    /**
     * 通过回复包获得请求包
     * 
     * @param in
     * 		回复包
     * @return
     * 		OutPacket对象，如果没有找到，返回null
     */
    public OutPacket retrieveSent(InPacket in) {
    	for(int i = 0; i < PARSER_COUNT; i++) {
    		if(in.getFamily() == family[i]) {
    			PacketHistory history = parsers[i].getHistory();
    			if(history == null)
    				return null;
    			else
    				return history.retrieveSent(in);
    		}
    	}
    	return null;
    }
    
    /**
     * 缓存输出包
     * 
     * @param out
     */
    public void putSent(OutPacket out) {
    	for(int i = 0; i < PARSER_COUNT; i++) {
    		if(out.getFamily() == family[i]) {
    			PacketHistory history = parsers[i].getHistory();
    			if(history != null)
    				history.putSent(out);
    			break;
    		}
    	}
    }    

    /**
     * 这个方法检查包是否已收到，要注意的是检查是针对这个包的hash值进行的，
     * 并不是对packet这个对象，hash值的计算是在packet的hashCode中完成的，
     * 如果两个packet的序号或者命令有不同，则hash值肯定不同。
     * 
     * @param packet
     * 		要检查的包
     * @param add
     * 		如果为true，则当这个包不存在时，添加这个包的hash，否则不添加
     * @return
     * 		true如果这个包已经收到，否则false
     * 
     * @see Packet#hashCode()
     * @see Packet#equals(Object);
     */
	public boolean isReplied(OutPacket packet, boolean add) {
    	for(int i = 0; i < PARSER_COUNT; i++) {
    		if(packet.getFamily() == family[i]) {
    			PacketHistory history = parsers[i].getHistory();
    			if(history != null)
    				return history.check(packet, add);
    			else
    				return false;
    		}
    	}
    	return false;
	}
	
	/**
	 * 检查包是否重复收到
	 * 
	 * @param packet
	 * 		InPacket子类
	 * @return
	 * 		true表示重复
	 */
	public boolean isDuplicated(InPacket packet) {
    	for(int i = 0; i < PARSER_COUNT; i++) {
    		if(packet.getFamily() == family[i]) {
    			return parsers[i].isDuplicate(packet);
    		}
    	}
    	return false;
	}
    
	/**
	 * 把ByteBuffer中的内容解析成一个InPacket子类，从buf的当前位置开始解析，直到limit为止
	 * 不论解析成功或者失败，要把buf的position置于length后
	 * 
	 * @param supportedFamily
	 * 		支持的协议族
	 * @param buf 
	 * 		ByteBuffer对象
	 * @param debug
	 * 		true表示调试模式，调试模式下会忽略重复包
	 * @return InPacket
	 * 		如果解析失败，或者缓冲区中的内容构不成一个包，返回null
	 * @throws PacketParseException
	 * 		如果包格式不对
	 */
	public InPacket parseIn(int supportedFamily, ByteBuffer buf, QQUser user, boolean debug) throws PacketParseException {
		if(!findParser(supportedFamily, buf))
			return null;
    	return parseIn(buf, parser.getLength(buf), user, debug);
	}
	
	/**
	 * 查找一个能解析buf中内容的parser
	 * 
	 * @param supportedFamily 
	 * 		支持的协议族
	 * @param buf
	 * 		ByteBuffer
	 * @return
	 * 		true表示找到，false表示没有找到
	 */
	private boolean findParser(int supportedFamily, ByteBuffer buf) {
    	if(parser == null) {
    		for(int i = 0; i < PARSER_COUNT; i++) {
    			if((supportedFamily & family[i]) != 0) {
    				if(parsers[i].accept(buf)) {
    					parser = parsers[i];
    					break;
    				}    				
    			}
    		}
    	} 
    	return parser != null;
	}
	
	/**
	 * 把ByteBuffer中的内容解析成一个InPacket子类，从buf的当前位置开始解析length字节
	 * 不论解析成功或者失败，buf的position将位于length后
	 * 
	 * @param buf
	 * 				ByteBuffer对象
	 * @param type
	 * 				包类型
	 * @param length
	 * 				包长度
	 * @param debug
	 * @return InPacket
	 * @throws PacketParseException
	 * 					如果包格式不对
	 */
	private InPacket parseIn(ByteBuffer buf, int length, QQUser user, boolean debug) throws PacketParseException {
	    // 保存当前位置
	    int offset = buf.position();

	    // 解析包
        try {
            InPacket ret = parser.parseIncoming(buf, length, user);
            boolean duplicated = isDuplicated(ret);
    	    boolean needReply = parser.isDuplicatedNeedReply(ret);
    	    if(duplicated && !needReply && !debug)
    	    	return null;
    	    else {
    	    	ret.setDuplicated(duplicated);
    	    	return ret;    	    	
    	    }
        } catch (PacketParseException e) {
            throw e;
        } finally {
	        buf.position(offset + length);
	        parser = null;
        }
	}
	
	/**
	 * 把ByteBuffer中的内容解析成一个InPacket子类，从buf的当前位置开始解析，直到limit为止
	 * 不论解析成功或者失败，要把buf的position置于length后
	 * 
	 * @param buf
	 * @param type
	 * @return
	 * @throws PacketParseException
	 */
	public OutPacket parseOut(int supportedFamily, ByteBuffer buf, QQUser user) throws PacketParseException {
		if(!findParser(supportedFamily, buf))
			return null;
	    return parseOut(buf, parser.getLength(buf), user);
	}

    /**
	 * 把ByteBuffer中的内容解析成一个InPacket子类，从buf的当前位置开始解析，直到limit为止
	 * 不论解析成功或者失败，要把buf的position置于length后
	 * 
     * @param buf
     * @param length
     * @param debug
     * @return
     * @throws PacketParseException
     */
    private OutPacket parseOut(ByteBuffer buf, int length, QQUser user) throws PacketParseException {
        int pos = buf.position();		
	    try {
            OutPacket ret = parser.parseOutcoming(buf, length, user);
            return ret;
        } catch (PacketParseException e) {
            throw e;
        } finally {
            buf.position(pos + length);
            parser = null;
        }
    }

	/**
	 * 把position设置到下一个包的起始位置处。一般当某段数据没有parser
	 * 可以时，调用此方法跳过这段数据
	 * 
	 * @param relocateFamily
	 * 		重定位使用的协议族
	 * @param buf
	 * 		缓冲区
	 * @return
	 * 		true表示重定位成功，false表示失败或者推迟重定位
	 */
	public boolean relocate(int relocateFamily, ByteBuffer buf) {
		int offset = buf.position();
		for(int i = 0; i < PARSER_COUNT; i++) {
			if((relocateFamily & family[i]) != 0) {
				int relocated = parsers[i].relocate(buf);
				if(relocated > offset) {
					buf.position(relocated);
					return true;
				} else
					return false;
			}
		}
		return false;
	}
}
