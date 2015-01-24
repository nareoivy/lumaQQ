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
import edu.tsinghua.lumaqq.qq.packets._03InPacket;
import edu.tsinghua.lumaqq.qq.packets.OutPacket;
import edu.tsinghua.lumaqq.qq.packets.in._03.Unknown03InPacket;

/**
 * FTP协议族包处理器
 *
 * @author luma
 */
class _03FamilyProcessor implements IPacketListener {
    // Log对象
    static Log log = LogFactory.getLog(_03FamilyProcessor.class);
    
    private QQClient client;
    
    public _03FamilyProcessor(QQClient client) {
    	this.client = client;
    }
    
	public void packetArrived(PacketEvent e) {
    	if(e.getSource() instanceof ErrorPacket) {
    		processError((ErrorPacket)e.getSource());
    		return;
    	}
    	
    	_03InPacket in = (_03InPacket)e.getSource();
    	if(in instanceof Unknown03InPacket) {
            log.debug("收到一个FTP Family未知格式包");
            return;
    	}
    	
        // 清楚重发包
        client.removeResendPacket(in);
		log.debug("包" + (int)in.getSequence() + "的确认已经收到，将不再发送");
		
		// 判断包命令，处理
		switch(in.getCommand()) {
			case QQ.QQ_03_CMD_GET_CUSTOM_HEAD_DATA:
				processGetCustomHeadDataReply(in);
				break;
			case QQ.QQ_03_CMD_GET_CUSTOM_HEAD_INFO:
				processGetCustomHeadInfoReply(in);
				break;
		}
	}

	private void processGetCustomHeadInfoReply(_03InPacket in) {
		log.debug("得到自定义头像信息成功");
		QQEvent e = new QQEvent(in);
		e.type = QQEvent.USER_GET_CUSTOM_HEAD_INFO_OK;
		client.fireQQEvent(e);
	}

	private void processGetCustomHeadDataReply(_03InPacket in) {
		QQEvent e = new QQEvent(in);
		e.type = QQEvent.USER_GET_CUSTOM_HEAD_DATA_OK;
		client.fireQQEvent(e);
	}

	private void processError(ErrorPacket error) {
    	QQEvent e = null;
		switch(error.errorCode) {
			case ErrorPacket.ERROR_TIMEOUT:
				OutPacket packet = error.timeoutPacket;
				e = new QQEvent(packet);
				e.type = QQEvent.SYS_TIMEOUT_03;
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
	
	/* (non-Javadoc)
	 * @see edu.tsinghua.lumaqq.qq.events.IPacketListener#accept(edu.tsinghua.lumaqq.qq.packets.InPacket)
	 */
	public boolean accept(InPacket in) {
		return (in.getFamily() & QQ.QQ_PROTOCOL_FAMILY_03) != 0;
	}
}
