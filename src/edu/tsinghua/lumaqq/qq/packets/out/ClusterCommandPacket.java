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
import edu.tsinghua.lumaqq.qq.beans.QQUser;
import edu.tsinghua.lumaqq.qq.packets.BasicOutPacket;
import edu.tsinghua.lumaqq.qq.packets.PacketParseException;

/**
 * <pre>
 * 群操作包的基类，其包含了一些群操作包的公共字段，比如子命令类型
 * </pre>
 * 
 * @author luma
 */
public class ClusterCommandPacket extends BasicOutPacket {
	protected byte subCommand;
	protected int clusterId;
	
    /** 字体属性 */
	protected static final byte NONE = 0x00;
	protected static final byte BOLD = 0x20;
	protected static final byte ITALIC = 0x40;
	protected static final byte UNDERLINE = (byte)0x80;
    
    /**
     * 构造函数
     */
    public ClusterCommandPacket(QQUser user) {
        super(QQ.QQ_CMD_CLUSTER_CMD, true, user);
    }	
    
    /**
     * @param buf
     * @param length
     * @throws PacketParseException
     */
    public ClusterCommandPacket(ByteBuffer buf, int length, QQUser user) throws PacketParseException {
        super(buf, length, user);
    }
    
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.packets.OutPacket#parseBody(java.nio.ByteBuffer)
     */
	@Override
    protected void parseBody(ByteBuffer buf) throws PacketParseException {
        subCommand = buf.get();
    }
    
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.packets.BasicOutPacket#getPacketName()
     */
	@Override
    public String getPacketName() {
	    switch(subCommand) {
	        case QQ.QQ_CLUSTER_CMD_ACTIVATE_CLUSTER:
	            return "Cluster Activate Packet";
	        case QQ.QQ_CLUSTER_CMD_MODIFY_MEMBER:
	        	return "Cluster Modify Member Packet";
	        case QQ.QQ_CLUSTER_CMD_CREATE_CLUSTER:
	            return "Cluster Create Packet";
	        case QQ.QQ_CLUSTER_CMD_EXIT_CLUSTER:
	            return "Cluster Exit Packet";
	        case QQ.QQ_CLUSTER_CMD_GET_CLUSTER_INFO:
	            return "Cluster Get Info Packet";
	        case QQ.QQ_CLUSTER_CMD_GET_MEMBER_INFO:
	            return "Cluster Get Member Info Packet";
	        case QQ.QQ_CLUSTER_CMD_GET_ONLINE_MEMBER:
	            return "Cluster Get Online Member Packet";
	        case QQ.QQ_CLUSTER_CMD_JOIN_CLUSTER:
	            return "Cluster Join Packet";
	        case QQ.QQ_CLUSTER_CMD_JOIN_CLUSTER_AUTH:
	            return "Cluster Auth Packet";
	        case QQ.QQ_CLUSTER_CMD_MODIFY_CLUSTER_INFO:
	            return "Cluster Modify Info Packet";
	        case QQ.QQ_CLUSTER_CMD_SEARCH_CLUSTER:
	            return "Cluster Search Packet";
	        case QQ.QQ_CLUSTER_CMD_SEND_IM_EX:
	            return "Cluster Send IM Ex Packet";
	        case QQ.QQ_CLUSTER_CMD_MODIFY_TEMP_MEMBER:
	            return "Cluster Modify Temp Cluster Member Packet";
	        case QQ.QQ_CLUSTER_CMD_GET_TEMP_INFO:
	            return "Cluster Get Temp Cluster Info Packet";
	        case QQ.QQ_CLUSTER_CMD_ACTIVATE_TEMP:
	            return "Cluster Activate Temp Cluster Packet";
	        case QQ.QQ_CLUSTER_CMD_EXIT_TEMP:
	            return "Cluster Exit Temp Cluster Packet";
	        case QQ.QQ_CLUSTER_CMD_CREATE_TEMP:
	            return "Cluster Create Temp Cluster Packet";
	        default:
	            return "Unknown Cluster Command Packet";
	    }
    }
    
	/**
	 * @return Returns the subCommand.
	 */
	public byte getSubCommand() {
		return subCommand;
	}
	/**
	 * @param subCommand The subCommand to set.
	 */
	public void setSubCommand(byte subCommand) {
		this.subCommand = subCommand;
	}
	
	/**
	 * @return Returns the clusterId.
	 */
	public int getClusterId() {
		return clusterId;
	}
	
	/**
	 * @param clusterId The clusterId to set.
	 */
	public void setClusterId(int clusterId) {
		this.clusterId = clusterId;
	}

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.packets.Packet#putBody(java.nio.ByteBuffer)
     */
	@Override
    protected void putBody(ByteBuffer buf) {
    }
}
