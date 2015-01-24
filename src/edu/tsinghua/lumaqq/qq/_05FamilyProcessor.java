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
package edu.tsinghua.lumaqq.qq;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.tsinghua.lumaqq.qq.events.IPacketListener;
import edu.tsinghua.lumaqq.qq.events.PacketEvent;
import edu.tsinghua.lumaqq.qq.events.QQEvent;
import edu.tsinghua.lumaqq.qq.packets.ErrorPacket;
import edu.tsinghua.lumaqq.qq.packets.InPacket;
import edu.tsinghua.lumaqq.qq.packets.OutPacket;
import edu.tsinghua.lumaqq.qq.packets._05InPacket;
import edu.tsinghua.lumaqq.qq.packets.in._05.RequestAgentReplyPacket;
import edu.tsinghua.lumaqq.qq.packets.in._05.Unknown05InPacket;

/**
 * 05系列包处理器
 * 
 * @author luma
 */
class _05FamilyProcessor implements IPacketListener {
    // Log对象
    static Log log = LogFactory.getLog(_05FamilyProcessor.class);
    
    // QQ客户端
    private QQClient client;
    
    public _05FamilyProcessor(QQClient client) {
        this.client = client;
    }
    
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.events.IPacketListener#packetArrived(edu.tsinghua.lumaqq.qq.events.PacketEvent)
     */
    public void packetArrived(PacketEvent e) {
    	if(e.getSource() instanceof ErrorPacket) {
    		processError((ErrorPacket)e.getSource());
    		return;
    	}
    	
        _05InPacket in = (_05InPacket) e.getSource();
        if(in instanceof Unknown05InPacket) {
            log.debug("收到一个未知格式包");
            return;
        }
        
        // 清楚重发包
        client.removeResendPacket(in);
		log.debug("包" + (int)in.getSequence() + "的确认已经收到，将不再发送");
		
		// 判断包命令，处理
		switch(in.getCommand()) {
			case QQ.QQ_05_CMD_REQUEST_AGENT:
				processRequestAgentReply(in);
				break;
			case QQ.QQ_05_CMD_REQUEST_FACE:
			    processRequestFaceReply(in);
				break;
			case QQ.QQ_05_CMD_REQUEST_BEGIN:
			    processRequestBeginReply(in);
				break;
			case QQ.QQ_05_CMD_TRANSFER:
			    processTransferReply(in);
				break;
		}
    }

    /**
     * 处理错误事件 
     * 
     * @param packet
     */
    private void processError(ErrorPacket error) {
    	QQEvent e = null;
		switch(error.errorCode) {
			case ErrorPacket.ERROR_TIMEOUT:
				OutPacket packet = error.timeoutPacket;
				e = new QQEvent(packet);
				e.type = QQEvent.SYS_TIMEOUT_05;
				e.operation = packet.getCommand();
				break;
            case ErrorPacket.ERROR_PROXY:
            	e = new QQEvent(error);
            	e.type = QQEvent.ERROR_PROXY;
            	break;
            case ErrorPacket.ERROR_NETWORK:
            	e = new QQEvent(error);
            	e.type = QQEvent.ERROR_NETWORK;
            	break;
		}
		
		if(e != null)
			client.fireQQEvent(e);
	}

	/**
     * 处理请求自定义表情回复包
     * 
     * @param in
     */
    private void processRequestFaceReply(_05InPacket in) {
        QQEvent e;
        
        e = new QQEvent(in);
        e.type = QQEvent.FILE_REQUEST_FACE_OK;
        client.fireQQEvent(e);
    }

    /**
     * 处理传送回复包
     * 
     * @param in
     */
    private void processTransferReply(_05InPacket in) {
        QQEvent e;
        
        if(in.getSource() == QQ.QQ_CLIENT_VERSION) {
	        e = new QQEvent(in);
	        e.type = QQEvent.FILE_TRANSFER_FACE_OK;
	        client.fireQQEvent(e);           
        } else {
            if(in.getSequence() == 0) {
	            e = new QQEvent(in);
	            e.type = QQEvent.FILE_FACE_INFO_RECEIVED;
	            client.fireQQEvent(e);                
            } else {
                e = new QQEvent(in);
                e.type = QQEvent.FILE_FACE_DATA_RECEIVED;
                client.fireQQEvent(e);
            }
        }
    }

    /**
     * 处理请求开始回复包
     * 
     * @param in
     */
    private void processRequestBeginReply(_05InPacket in) {
        QQEvent e;
        
        log.debug("服务器同意开始发送");
        e = new QQEvent(in);
        e.type = QQEvent.FILE_REQUEST_BEGIN_OK;
        client.fireQQEvent(e);
    }

    /**
     * 处理请求中转服务器请求包
     * 
	 * @param in
	 */
	private void processRequestAgentReply(_05InPacket in) {
		QQEvent e;
		RequestAgentReplyPacket packet = (RequestAgentReplyPacket)in;
		
		switch(packet.replyCode) {
			case QQ.QQ_REPLY_REQUEST_AGENT_REDIRECT:
				log.debug("请求中转服务器重定向， to " + Util.convertIpToString(packet.redirectIp));
				e = new QQEvent(packet);
				e.type = QQEvent.FILE_REQUEST_AGENT_REDIRECT;
				client.fireQQEvent(e);
				break;
			case QQ.QQ_REPLY_REQUEST_AGENT_OK:
				log.debug("请求中转服务器成功");
				e = new QQEvent(packet);
				e.type = QQEvent.FILE_REQUEST_AGENT_OK;
				client.fireQQEvent(e);
				break;
			case QQ.QQ_REPLY_REQUEST_AGENT_TOO_LONG:
			    log.debug(packet.message);
				e = new QQEvent(packet);
				e.type = QQEvent.FILE_REQUEST_AGENT_FAIL;
				client.fireQQEvent(e);
				break;
			default:
				log.debug("未知的Request Agent回复码: " + (int)packet.replyCode);
				break;
		}
	}
	
	/* (non-Javadoc)
	 * @see edu.tsinghua.lumaqq.qq.events.IPacketListener#accept(edu.tsinghua.lumaqq.qq.packets.InPacket)
	 */
	public boolean accept(InPacket in) {
		return (in.getFamily() & QQ.QQ_PROTOCOL_FAMILY_05) != 0;
	}
}
