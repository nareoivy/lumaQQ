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
package edu.tsinghua.lumaqq.qq.packets;

import java.nio.ByteBuffer;

import edu.tsinghua.lumaqq.qq.QQ;
import edu.tsinghua.lumaqq.qq.beans.QQUser;
import edu.tsinghua.lumaqq.qq.packets.in.AddFriendAuthResponseReplyPacket;
import edu.tsinghua.lumaqq.qq.packets.in.AddFriendExReplyPacket;
import edu.tsinghua.lumaqq.qq.packets.in.AdvancedSearchUserReplyPacket;
import edu.tsinghua.lumaqq.qq.packets.in.AuthInfoOpReplyPacket;
import edu.tsinghua.lumaqq.qq.packets.in.AuthQuestionOpReplyPacket;
import edu.tsinghua.lumaqq.qq.packets.in.AuthorizeReplyPacket;
import edu.tsinghua.lumaqq.qq.packets.in.ChangeStatusReplyPacket;
import edu.tsinghua.lumaqq.qq.packets.in.ClusterCommandReplyPacket;
import edu.tsinghua.lumaqq.qq.packets.in.DeleteFriendReplyPacket;
import edu.tsinghua.lumaqq.qq.packets.in.DownloadGroupFriendReplyPacket;
import edu.tsinghua.lumaqq.qq.packets.in.FriendChangeStatusPacket;
import edu.tsinghua.lumaqq.qq.packets.in.FriendDataOpReplyPacket;
import edu.tsinghua.lumaqq.qq.packets.in.FriendLevelOpReplyPacket;
import edu.tsinghua.lumaqq.qq.packets.in.GetFriendListReplyPacket;
import edu.tsinghua.lumaqq.qq.packets.in.GetOnlineOpReplyPacket;
import edu.tsinghua.lumaqq.qq.packets.in.GetTempClusterOnlineMemberReplyPacket;
import edu.tsinghua.lumaqq.qq.packets.in.GetUserInfoReplyPacket;
import edu.tsinghua.lumaqq.qq.packets.in.GroupDataOpReplyPacket;
import edu.tsinghua.lumaqq.qq.packets.in.KeepAliveReplyPacket;
import edu.tsinghua.lumaqq.qq.packets.in.LoginReplyPacket;
import edu.tsinghua.lumaqq.qq.packets.in.ModifyInfoReplyPacket;
import edu.tsinghua.lumaqq.qq.packets.in.PrivacyDataOpReplyPacket;
import edu.tsinghua.lumaqq.qq.packets.in.ReceiveIMPacket;
import edu.tsinghua.lumaqq.qq.packets.in.RemoveSelfReplyPacket;
import edu.tsinghua.lumaqq.qq.packets.in.GetKeyReplyPacket;
import edu.tsinghua.lumaqq.qq.packets.in.GetLoginTokenReplyPacket;
import edu.tsinghua.lumaqq.qq.packets.in.SearchUserReplyPacket;
import edu.tsinghua.lumaqq.qq.packets.in.SendIMReplyPacket;
import edu.tsinghua.lumaqq.qq.packets.in.SendSMSReplyPacket;
import edu.tsinghua.lumaqq.qq.packets.in.SignatureOpReplyPacket;
import edu.tsinghua.lumaqq.qq.packets.in.SystemNotificationPacket;
import edu.tsinghua.lumaqq.qq.packets.in.TempSessionOpReplyPacket;
import edu.tsinghua.lumaqq.qq.packets.in.UnknownInPacket;
import edu.tsinghua.lumaqq.qq.packets.in.UploadGroupFriendReplyPacket;
import edu.tsinghua.lumaqq.qq.packets.in.UserPropertyOpReplyPacket;
import edu.tsinghua.lumaqq.qq.packets.in.WeatherOpReplyPacket;
import edu.tsinghua.lumaqq.qq.packets.out.AddFriendAuthResponsePacket;
import edu.tsinghua.lumaqq.qq.packets.out.AddFriendExPacket;
import edu.tsinghua.lumaqq.qq.packets.out.AdvancedSearchUserPacket;
import edu.tsinghua.lumaqq.qq.packets.out.AuthInfoOpPacket;
import edu.tsinghua.lumaqq.qq.packets.out.AuthQuestionOpPacket;
import edu.tsinghua.lumaqq.qq.packets.out.AuthorizePacket;
import edu.tsinghua.lumaqq.qq.packets.out.ChangeStatusPacket;
import edu.tsinghua.lumaqq.qq.packets.out.ClusterCommandPacket;
import edu.tsinghua.lumaqq.qq.packets.out.DeleteFriendPacket;
import edu.tsinghua.lumaqq.qq.packets.out.DownloadGroupFriendPacket;
import edu.tsinghua.lumaqq.qq.packets.out.FriendDataOpPacket;
import edu.tsinghua.lumaqq.qq.packets.out.FriendLevelOpPacket;
import edu.tsinghua.lumaqq.qq.packets.out.GetFriendListPacket;
import edu.tsinghua.lumaqq.qq.packets.out.GetOnlineOpPacket;
import edu.tsinghua.lumaqq.qq.packets.out.GetUserInfoPacket;
import edu.tsinghua.lumaqq.qq.packets.out.GroupDataOpPacket;
import edu.tsinghua.lumaqq.qq.packets.out.KeepAlivePacket;
import edu.tsinghua.lumaqq.qq.packets.out.LoginPacket;
import edu.tsinghua.lumaqq.qq.packets.out.LogoutPacket;
import edu.tsinghua.lumaqq.qq.packets.out.ModifyInfoPacket;
import edu.tsinghua.lumaqq.qq.packets.out.PrivacyDataOpPacket;
import edu.tsinghua.lumaqq.qq.packets.out.ReceiveIMReplyPacket;
import edu.tsinghua.lumaqq.qq.packets.out.RemoveSelfPacket;
import edu.tsinghua.lumaqq.qq.packets.out.GetKeyPacket;
import edu.tsinghua.lumaqq.qq.packets.out.GetLoginTokenPacket;
import edu.tsinghua.lumaqq.qq.packets.out.SearchUserPacket;
import edu.tsinghua.lumaqq.qq.packets.out.SendIMPacket;
import edu.tsinghua.lumaqq.qq.packets.out.SendSMSPacket;
import edu.tsinghua.lumaqq.qq.packets.out.SignatureOpPacket;
import edu.tsinghua.lumaqq.qq.packets.out.TempSessionOpPacket;
import edu.tsinghua.lumaqq.qq.packets.out.UnknownOutPacket;
import edu.tsinghua.lumaqq.qq.packets.out.UploadGroupFriendPacket;
import edu.tsinghua.lumaqq.qq.packets.out.UserPropertyOpPacket;
import edu.tsinghua.lumaqq.qq.packets.out.WeatherOpPacket;

/**
 * 基本协议族解析器
 * 
 * @author luma
 */
public class BasicFamilyParser implements IParser {    
	private int offset;
	private int length;
	
	private PacketHistory history;

	public BasicFamilyParser() {
		history = new PacketHistory();
	}
    
	/* (non-Javadoc)
	 * @see edu.tsinghua.lumaqq.qq.packets.IParser#accept(java.nio.ByteBuffer)
	 */
	public boolean accept(ByteBuffer buf) {
		// 保存偏移
		offset = buf.position();
		int bufferLength = buf.limit() - buf.position();
		if(bufferLength <= 0)
		    return false;
		
		boolean accept = checkTcp(buf);
		if(!accept)
		    accept = checkUdp(buf);
		
		return accept;
	}
	
	/**
	 * 检查一个包是否是udp包
	 * 
	 * @param buf
	 * 		ByteBuffer
	 * @return
	 * 		true表示是，false表示否
	 */
	private boolean checkUdp(ByteBuffer buf) {
		if(buf.get(offset) == QQ.QQ_HEADER_BASIC_FAMILY) {
			// 首先检查是否UDP方式
		    length = buf.limit() - buf.position();
		    if(buf.get(offset + length - 1) == QQ.QQ_TAIL_BASIC_FAMILY) 
		    	return true;
		}
		return false;
	}
	
	/**
	 * 检查一个包是否是tcp包
	 * 
	 * @param buf
	 * 		ByteBuffer
	 * @return
	 * 		true表示是
	 */
	private boolean checkTcp(ByteBuffer buf) {
	    // buffer length不大于2则连个长度字段都没有
		int bufferLength = buf.limit() - buf.position();
		if(bufferLength < 2)
		    return false;
		
		// 如果可读内容小于包长，则这个包还没收完
        length = buf.getChar(offset);
        if(length <= 0 || length > bufferLength)
            return false;
                    
        // 再检查包头包尾
        if(buf.get(offset + 2) == QQ.QQ_HEADER_BASIC_FAMILY) {
            if(buf.get(offset + length - 1) == QQ.QQ_TAIL_BASIC_FAMILY) 
            	return true;
        }	        
        
        return false;
	}
	
    public int getLength(ByteBuffer buf) {
        return length;
    }
    
	public boolean isDuplicate(InPacket in) {
		return history.check(in, true);
	}

	/**
     * 得到包的命令和序号
     * 
     * @param buf
     */
    private char getCommand(ByteBuffer buf, QQUser user) {
	    if(!user.isUdp()) {
	        return buf.getChar(offset + 5);
	    } else {
	        return buf.getChar(offset + 3);
	    }
    }

	/* (non-Javadoc)
	 * @see edu.tsinghua.lumaqq.qq.packets.IParser#parseIncoming(java.nio.ByteBuffer, int, edu.tsinghua.lumaqq.qq.beans.QQUser)
	 */
	public InPacket parseIncoming(ByteBuffer buf, int len, QQUser user) throws PacketParseException {
	    try {
            switch(getCommand(buf, user)) {
                case QQ.QQ_CMD_GET_LOGIN_TOKEN:
                	return new GetLoginTokenReplyPacket(buf, len, user);
            	case QQ.QQ_CMD_KEEP_ALIVE:
            	    return new KeepAliveReplyPacket(buf, len, user);
            	case QQ.QQ_CMD_MODIFY_INFO:
            	    return new ModifyInfoReplyPacket(buf, len, user);
            	case QQ.QQ_CMD_ADD_FRIEND_EX:
            		return new AddFriendExReplyPacket(buf, len, user);
            	case QQ.QQ_CMD_AUTH_QUESTION_OP:
            		return new AuthQuestionOpReplyPacket(buf, len, user);
            	case QQ.QQ_CMD_AUTH_INFO_OP:
            		return new AuthInfoOpReplyPacket(buf, len, user);
            	case QQ.QQ_CMD_SEARCH_USER:
            	    return new SearchUserReplyPacket(buf, len, user);
            	case QQ.QQ_CMD_DELETE_FRIEND:
            	    return new DeleteFriendReplyPacket(buf, len, user);
            	case QQ.QQ_CMD_REMOVE_SELF:
            	    return new RemoveSelfReplyPacket(buf, len, user);
            	case QQ.QQ_CMD_ADD_FRIEND_AUTH:
            	    return new AddFriendAuthResponseReplyPacket(buf, len, user);
            	case QQ.QQ_CMD_GET_USER_INFO:
            		return new GetUserInfoReplyPacket(buf, len, user);
            	case QQ.QQ_CMD_CHANGE_STATUS:
            		return new ChangeStatusReplyPacket(buf, len, user);
            	case QQ.QQ_CMD_SEND_IM:
            		return new SendIMReplyPacket(buf, len, user);
            	case QQ.QQ_CMD_RECV_IM:
            	    return new ReceiveIMPacket(buf, len, user);
            	case QQ.QQ_CMD_LOGIN:
            		return new LoginReplyPacket(buf, len, user);
            	case QQ.QQ_CMD_GET_FRIEND_LIST:
            		return new GetFriendListReplyPacket(buf, len, user);
            	case QQ.QQ_CMD_GET_ONLINE_OP:
            		return new GetOnlineOpReplyPacket(buf, len, user);
            	case QQ.QQ_CMD_RECV_MSG_SYS:
            	    return new SystemNotificationPacket(buf, len, user);
            	case QQ.QQ_CMD_RECV_MSG_FRIEND_CHANGE_STATUS:
            		return new FriendChangeStatusPacket(buf, len, user);
            	case QQ.QQ_CMD_UPLOAD_GROUP_FRIEND:
            		return new UploadGroupFriendReplyPacket(buf, len, user);
            	case QQ.QQ_CMD_DOWNLOAD_GROUP_FRIEND:
            		return new DownloadGroupFriendReplyPacket(buf, len, user);
            	case QQ.QQ_CMD_GROUP_DATA_OP:
            		return new GroupDataOpReplyPacket(buf, len, user);
            	case QQ.QQ_CMD_FRIEND_DATA_OP:
            		return new FriendDataOpReplyPacket(buf, len, user);
            	case QQ.QQ_CMD_CLUSTER_CMD:
            		return new ClusterCommandReplyPacket(buf, len, user);
            	case QQ.QQ_CMD_REQUEST_KEY:
            		return new GetKeyReplyPacket(buf, len, user);
            	case QQ.QQ_CMD_ADVANCED_SEARCH:
            	    return new AdvancedSearchUserReplyPacket(buf, len, user);
            	case QQ.QQ_CMD_CLUSTER_DATA_OP:
            	    return new GetTempClusterOnlineMemberReplyPacket(buf, len, user);
            	case QQ.QQ_CMD_AUTHORIZE:
            		return new AuthorizeReplyPacket(buf, len, user);
            	case QQ.QQ_CMD_SIGNATURE_OP:
            		return new SignatureOpReplyPacket(buf, len, user);
            	case QQ.QQ_CMD_WEATHER_OP:
            		return new WeatherOpReplyPacket(buf, len, user);
            	case QQ.QQ_CMD_USER_PROPERTY_OP:
            		return new UserPropertyOpReplyPacket(buf, len, user);
            	case QQ.QQ_CMD_FRIEND_LEVEL_OP:
            		return new FriendLevelOpReplyPacket(buf, len, user);
            	case QQ.QQ_CMD_SEND_SMS:
            		return new SendSMSReplyPacket(buf, len, user);
            	case QQ.QQ_CMD_TEMP_SESSION_OP:
            		return new TempSessionOpReplyPacket(buf, len, user);
            	case QQ.QQ_CMD_PRIVACY_DATA_OP:
            		return new PrivacyDataOpReplyPacket(buf, len, user);
            	default:
            		return new UnknownInPacket(buf, len, user);
            }
        } catch (PacketParseException e) {
            // 如果解析失败，返回null
            buf.position(offset);
            return new UnknownInPacket(buf, len, user);
        }
	}

	/* (non-Javadoc)
	 * @see edu.tsinghua.lumaqq.qq.packets.IParser#parseOutcoming(java.nio.ByteBuffer, int, edu.tsinghua.lumaqq.qq.beans.QQUser)
	 */
	public OutPacket parseOutcoming(ByteBuffer buf, int len, QQUser user) throws PacketParseException {
	    try {
            switch(getCommand(buf, user)) {
                case QQ.QQ_CMD_GET_LOGIN_TOKEN:
                	return new GetLoginTokenPacket(buf, len, user);
            	case QQ.QQ_CMD_KEEP_ALIVE:
            	    return new KeepAlivePacket(buf, len, user);
            	case QQ.QQ_CMD_MODIFY_INFO:
            	    return new ModifyInfoPacket(buf, len, user);
            	case QQ.QQ_CMD_SEARCH_USER:
            	    return new SearchUserPacket(buf, len, user);
            	case QQ.QQ_CMD_ADD_FRIEND_EX:
            		return new AddFriendExPacket(buf, len, user);
            	case QQ.QQ_CMD_AUTH_INFO_OP:
            		return new AuthInfoOpPacket(buf, len, user);
            	case QQ.QQ_CMD_AUTH_QUESTION_OP:
            		return new AuthQuestionOpPacket(buf, len, user);
            	case QQ.QQ_CMD_DELETE_FRIEND:
            	    return new DeleteFriendPacket(buf, len, user);
            	case QQ.QQ_CMD_REMOVE_SELF:
            	    return new RemoveSelfPacket(buf, len, user);
            	case QQ.QQ_CMD_ADD_FRIEND_AUTH:
            	    return new AddFriendAuthResponsePacket(buf, len, user);
            	case QQ.QQ_CMD_GET_USER_INFO:
            		return new GetUserInfoPacket(buf, len, user);
            	case QQ.QQ_CMD_CHANGE_STATUS:
            		return new ChangeStatusPacket(buf, len, user);
            	case QQ.QQ_CMD_SEND_IM:
            		return new SendIMPacket(buf, len, user);
            	case QQ.QQ_CMD_RECV_IM:
            	    return new ReceiveIMReplyPacket(buf, len, user);
            	case QQ.QQ_CMD_LOGIN:
            		return new LoginPacket(buf, len, user);
            	case QQ.QQ_CMD_GET_FRIEND_LIST:
            		return new GetFriendListPacket(buf, len, user);
            	case QQ.QQ_CMD_GET_ONLINE_OP:
            		return new GetOnlineOpPacket(buf, len, user);
            	case QQ.QQ_CMD_UPLOAD_GROUP_FRIEND:
            		return new UploadGroupFriendPacket(buf, len, user);
            	case QQ.QQ_CMD_DOWNLOAD_GROUP_FRIEND:
            		return new DownloadGroupFriendPacket(buf, len, user);
            	case QQ.QQ_CMD_GROUP_DATA_OP:
            		return new GroupDataOpPacket(buf, len, user);
            	case QQ.QQ_CMD_FRIEND_DATA_OP:
            		return new FriendDataOpPacket(buf, len, user);
            	case QQ.QQ_CMD_ADVANCED_SEARCH:
            	    return new AdvancedSearchUserPacket(buf, len, user);
            	case QQ.QQ_CMD_CLUSTER_CMD:    		
            	    return new ClusterCommandPacket(buf, len, user);
            	case QQ.QQ_CMD_REQUEST_KEY:
            		return new GetKeyPacket(buf, len, user);
            	case QQ.QQ_CMD_LOGOUT:
            	    return new LogoutPacket(buf, len, user);
            	case QQ.QQ_CMD_AUTHORIZE:
            		return new AuthorizePacket(buf, len, user);
            	case QQ.QQ_CMD_SIGNATURE_OP:
            		return new SignatureOpPacket(buf, len, user);
            	case QQ.QQ_CMD_WEATHER_OP:
            		return new WeatherOpPacket(buf, len, user);
            	case QQ.QQ_CMD_USER_PROPERTY_OP:
            		return new UserPropertyOpPacket(buf, len, user);
            	case QQ.QQ_CMD_FRIEND_LEVEL_OP:
            		return new FriendLevelOpPacket(buf, len, user);
            	case QQ.QQ_CMD_SEND_SMS:
            		return new SendSMSPacket(buf, len, user);
            	case QQ.QQ_CMD_TEMP_SESSION_OP:
            		return new TempSessionOpPacket(buf, len, user);
            	case QQ.QQ_CMD_PRIVACY_DATA_OP:
            		return new PrivacyDataOpPacket(buf, len, user);
            	default:
            		return new UnknownOutPacket(buf, len, user);
            }
        } catch (PacketParseException e) {
            // 如果解析失败，返回一个未知包
            buf.position(offset);
            return new UnknownOutPacket(buf, len, user);
        }
	}

	/* (non-Javadoc)
	 * @see edu.tsinghua.lumaqq.qq.packets.IParser#isDuplicatedNeedReply(edu.tsinghua.lumaqq.qq.packets.InPacket)
	 */
	public boolean isDuplicatedNeedReply(InPacket in) {
		return in.getCommand() == QQ.QQ_CMD_RECV_IM;
	}

	/* (non-Javadoc)
	 * @see edu.tsinghua.lumaqq.qq.packets.IParser#relocate(java.nio.ByteBuffer)
	 */
	public int relocate(ByteBuffer buf) {
		int offset = buf.position();
		if(buf.remaining() < 2)
			return offset;
		int len = buf.getChar(offset);
		if(len <= 0 || offset + len > buf.limit())
			return offset;
		else
			return offset + len; 
	}

	/* (non-Javadoc)
	 * @see edu.tsinghua.lumaqq.qq.packets.IParser#getHistory()
	 */
	public PacketHistory getHistory() {
		return history;
	}
}
