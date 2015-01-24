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
import java.util.ArrayList;
import java.util.List;

import edu.tsinghua.lumaqq.qq.QQ;
import edu.tsinghua.lumaqq.qq.beans.QQUser;
import edu.tsinghua.lumaqq.qq.packets.PacketParseException;

/**
 * <pre>
 * 得到群中成员信息的请求包，格式为：
 * 头部
 * -------- 加密开始(会话密钥) ----------
 * 命令类型，得到成员信息是0x0C
 * 群内部ID，4字节
 * ------- QQ Number Start (Repeat) ---------
 * 需要得到信息的成员QQ号，4字节
 * ------- QQ Number End ---------
 * ------- 加密结束 ---------
 * 尾部
 * 
 * 注意：一次最多只能得到61个成员信息，而实际操作中我们按照30个一组来得到
 * </pre>
 * 
 * @author luma
 */
public class ClusterGetMemberInfoPacket extends ClusterCommandPacket {
	private List<Integer> members;
	
	/**
	 * 构造函数
	 */
	public ClusterGetMemberInfoPacket(QQUser user) {
		super(user);
		subCommand = QQ.QQ_CLUSTER_CMD_GET_MEMBER_INFO;
		members = new ArrayList<Integer>();		
	}

    /**
     * @param buf
     * @param length
     * @throws PacketParseException
     */
    public ClusterGetMemberInfoPacket(ByteBuffer buf, int length, QQUser user)
            throws PacketParseException {
        super(buf, length, user);
    }
    
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.packets.OutPacket#getPacketName()
     */
	@Override
    public String getPacketName() {
        return "Cluster Get Member Info Packet";
    }
    
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.packets.OutPacket#putBody(java.nio.ByteBuffer)
     */
	@Override
    protected void putBody(ByteBuffer buf) {
		// 命令类型
		buf.put(subCommand);
		// 内部ID
		buf.putInt(clusterId);
		// 需要得到信息的成员QQ号列表
		for(Integer qq : members)
			buf.putInt(qq);
    }
    
    /**
	 * 添加一个需要得到信息的成员QQ号
	 * @param qqNum
	 */
	public void addMember(int qqNum) {
		members.add(new Integer(qqNum));
	}
	
	/**
	 * 添加一个需要得到信息的成员QQ号
	 * @param qqNum
	 */
	public void addMember(Integer qqNum) {
		members.add(qqNum);
	}
	
	/**
	 * 一次添加多个成员
	 * @param m
	 */
	public void addMembers(int[] m) {
		for(int i : m)
			members.add(i);
	}
	
	/**
	 * 设置需要得到信息的成员列表
	 * @param members
	 */
	public void setMembers(List<Integer> members) {
		this.members = members;
	}
	
	/**
	 * 得到指定位置的成员QQ号
	 * 
	 * @param index
	 * 			成员索引
	 * @return 成员QQ号
	 */
	public int getMember(int index) {
	    return members.get(index);
	}
}
