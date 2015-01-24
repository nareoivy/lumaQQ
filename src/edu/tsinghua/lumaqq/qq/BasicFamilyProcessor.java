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

import static org.apache.commons.codec.digest.DigestUtils.md5;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.tsinghua.lumaqq.qq.beans.QQUser;
import edu.tsinghua.lumaqq.qq.events.IPacketListener;
import edu.tsinghua.lumaqq.qq.events.PacketEvent;
import edu.tsinghua.lumaqq.qq.events.QQEvent;
import edu.tsinghua.lumaqq.qq.net.IConnectionPolicy;
import edu.tsinghua.lumaqq.qq.packets.BasicInPacket;
import edu.tsinghua.lumaqq.qq.packets.ErrorPacket;
import edu.tsinghua.lumaqq.qq.packets.InPacket;
import edu.tsinghua.lumaqq.qq.packets.OutPacket;
import edu.tsinghua.lumaqq.qq.packets.PacketParseException;
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
import edu.tsinghua.lumaqq.qq.packets.out.ClusterCommandPacket;
import edu.tsinghua.lumaqq.qq.packets.out.ModifyInfoPacket;
import edu.tsinghua.lumaqq.qq.packets.out.SendIMPacket;


/**
 * 这个类处理基本协议族包事件
 *
 * @author luma
 */
class BasicFamilyProcessor implements IPacketListener {
    // Log对象
    static Log log = LogFactory.getLog(BasicFamilyProcessor.class);

    // QQ客户端
    private QQClient client;
    // QQ用户，我们将在收到登陆包后设置user不为null，所以如果user为null意味着尚未登陆
    private QQUser user;
    
    private IConnectionPolicy policy;
    
    public BasicFamilyProcessor(QQClient client) {
        this.client = client;
        this.user = null;   
    }    
    
    // 处理登陆应答
    private void processLoginReply(BasicInPacket in) {
    	QQEvent e;
    	LoginReplyPacket packet = (LoginReplyPacket)in;
    	// 开始判断应答内容
    	switch(packet.replyCode) {
    		case QQ.QQ_REPLY_OK:
    			log.debug("登陆成功，用户IP为: " + Util.getIpStringFromBytes(packet.ip) + " 端口: " + packet.port);
    			// 登陆成功的话我们就设置用户的一些信息，然后触发事件
    			user.setSessionKey(packet.sessionKey);
    			user.setIp(packet.ip);
    			user.setServerIp(packet.serverIp);
    			user.setLastLoginIp(packet.lastLoginIp);
    			user.setPort(packet.port);
    			user.setServerPort(packet.serverPort);
    			user.setLoginTime(packet.loginTime);
    			user.setLastLoginTime(packet.lastLoginTime);
    			user.setClientKey(packet.clientKey);
    			user.setAuthToken(packet.authToken);
    			// 得到文件传输密钥
    		    byte[] b = new byte[4 + QQ.QQ_LENGTH_KEY];
    		    b[0] = (byte)((user.getQQ() >>> 24) & 0xFF);
    		    b[1] = (byte)((user.getQQ() >>> 16) & 0xFF);
    		    b[2] = (byte)((user.getQQ() >>> 8) & 0xFF);
    		    b[3] = (byte)(user.getQQ() & 0xFF);
    		    System.arraycopy(user.getSessionKey(), 0, b, 4, QQ.QQ_LENGTH_KEY);
    		    user.setFileSessionKey(md5(b));
    		    // 触发事件 
    			e = new QQEvent(packet);
    			e.type = QQEvent.LOGIN_OK;
    			client.fireQQEvent(e);
    			break;
    		case QQ.QQ_REPLY_LOGIN_FAIL:
    			log.debug("登录失败: " + packet.replyMessage);
    			// 如果是密码错误，触发事件    			
    			e = new QQEvent(packet);
    			e.type = QQEvent.LOGIN_FAIL;
    			client.fireQQEvent(e);
    			break;
    		case QQ.QQ_REPLY_LOGIN_REDIRECT:
    			log.debug("登陆重定向");
    			if(Util.isIpZero(packet.redirectIp)) {
    			    log.debug("地址重定向到0，随机选取其他服务器再登录");
    			    e = new QQEvent(packet);
    			    e.type = QQEvent.LOGIN_REDIRECT_NULL;
    			    client.fireQQEvent(e);
    			} else {
	    			// 如果是登陆重定向，继续登陆
	    			client.setLoginRedirect(true);
	    			try {
	                    client.login(Util.getIpStringFromBytes(packet.redirectIp), packet.redirectPort);
	                } catch (Exception e1) {
	                    log.error(e1.getMessage());
	                }    			    
    			}
    			break;
    		default:
    			log.debug("未知登陆错误");
    			// 如果是其他未知错误，触发事件
    			e = new QQEvent(packet);
    			e.type = QQEvent.LOGIN_UNKNOWN_ERROR;
    			client.fireQQEvent(e);
    			break;
    	}
    }
    
    /**
     * 判断包是否时登录前可以出现的包，有些包是在登录前可以处理的，如果不是，应该缓存起来等
     * 到登录成功后再处理，不过但是在实际中观察发现，登录之前收到的东西基本没用，可以不管
     * 
     * @param cmd
     * @return
     */
    private boolean isPreLoginPacket(BasicInPacket in) {
    	switch(in.getCommand()) {
    		case QQ.QQ_CMD_GET_LOGIN_TOKEN:
    		case QQ.QQ_CMD_LOGIN:
    			return true;
    		case QQ.QQ_CMD_UNKNOWN:
    			return in instanceof ErrorPacket;
    		default:
    			return false;
    	}
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.protocol.qq.PacketListener#packetArrived(edu.tsinghua.lumaqq.protocol.qq.PacketEvent)
     */
    public void packetArrived(PacketEvent e) {
        BasicInPacket in = (BasicInPacket) e.getSource();
        if(in instanceof UnknownInPacket) {
            log.debug("收到一个未知格式包");
            return;
        }
        
        // 显示调试信息
        if(in instanceof ErrorPacket)
        	log.debug("开始处理错误通知包，错误类型：" + in);
        else
        	log.debug("开始处理" + in.toString());
        
        // 检查目前是否已经登录
        if(user == null || !user.isLoggedIn()) {
            /*
             * 按理说应该把在登陆之前时收到的包缓存起来，但是在实际中观察发现，登录之前收到的
             * 东西基本没用，所以在这里不做任何事情，简单的抛弃这个包
             */
        	if(!isPreLoginPacket(in))
        		return;
        }
      
        // 这里检查是不是服务器发回的确认包
        // 为什么要检查这个呢，因为有些包是服务器主动发出的，对于这些包我们是不需要
        // 从超时发送队列中移除的，如果是服务器的确认包，那么我们就需要把超时发送队列
        // 中包移除
        switch (in.getCommand()) {
        	// 这三种包是服务器先发出的，我们要确认
        	case QQ.QQ_CMD_RECV_IM:
        	case QQ.QQ_CMD_RECV_MSG_SYS:
        	case QQ.QQ_CMD_RECV_MSG_FRIEND_CHANGE_STATUS:
        		break;
        	// 未知命令忽略掉
        	case QQ.QQ_CMD_UNKNOWN:
        		break;
        	// 其他情况我们删除超时队列中的包
        	default:
        	    client.removeResendPacket(in);
        		log.debug("包" + (int)in.getSequence() + "的确认已经收到，将不再发送");
        		break;
        }
        
        // 现在开始判断包的类型，作出相应的处理
		policy = client.getConnectionPolicy(in);
		if(policy == null)
			return;
        switch (in.getCommand()) {
            case QQ.QQ_CMD_GET_LOGIN_TOKEN:
            	this.user = client.getUser();
            	processGetLoginTokenReply(in);
            	break;
        	case QQ.QQ_CMD_KEEP_ALIVE:
        		processKeepAliveReply(in);
        		break;
        	case QQ.QQ_CMD_MODIFY_INFO:
        	    processModifyInfoReply(in);
        		break;
        	case QQ.QQ_CMD_SEARCH_USER:
        		processSearchUserReply(in);
        		break;
        	case QQ.QQ_CMD_ADD_FRIEND_EX:
        	    processAddFriendExReply(in);
        		break;
        	case QQ.QQ_CMD_AUTH_INFO_OP:
        		processAuthInfoOpReply(in);
        		break;
        	case QQ.QQ_CMD_AUTH_QUESTION_OP:
        		processAuthQuestionOpReply(in);
        		break;
        	case QQ.QQ_CMD_DELETE_FRIEND:
        	    processDeleteFriendReply(in);
        		break;
        	case QQ.QQ_CMD_REMOVE_SELF:
        	    processRemoveSelfReply(in);
        		break;
        	case QQ.QQ_CMD_ADD_FRIEND_AUTH:
        	    processAddFriendAuthReply(in);
        		break;
        	case QQ.QQ_CMD_GET_USER_INFO:
        		processGetUserInfoReply(in);
        		break;
        	case QQ.QQ_CMD_CHANGE_STATUS:
        		processChangeStatusReply(in);
        		break;
        	case QQ.QQ_CMD_SEND_IM:
        		processSendIMReply(in);
        		break;
        	case QQ.QQ_CMD_RECV_IM:
        	    processReceiveIM(in);
        		break;
        	case QQ.QQ_CMD_SEND_SMS:
        		processSendSMSReply(in);
        		break;
        	case QQ.QQ_CMD_LOGIN:
        		processLoginReply(in);
        		break;
        	case QQ.QQ_CMD_GET_FRIEND_LIST:
        		processGetFriendListReply(in);
        		break;
        	case QQ.QQ_CMD_GET_ONLINE_OP:
        		processGetFriendOnlineReply(in);
        		break;
        	case QQ.QQ_CMD_RECV_MSG_SYS:
        	    processSystemNotification(in);
        		break;
        	case QQ.QQ_CMD_RECV_MSG_FRIEND_CHANGE_STATUS:
        		processFriendChangeStatus(in);
        		break;
        	case QQ.QQ_CMD_UPLOAD_GROUP_FRIEND:
        		processUploadGroupFriendReply(in);
        		break;
        	case QQ.QQ_CMD_DOWNLOAD_GROUP_FRIEND:
        		processDownloadGroupFriendReply(in);
        		break;
        	case QQ.QQ_CMD_GROUP_DATA_OP:
        		processGroupNameOpReply(in);
        		break;
        	case QQ.QQ_CMD_FRIEND_DATA_OP:
        		processFriendDataOpReply(in);
        		break;
        	case QQ.QQ_CMD_CLUSTER_CMD:
        		processClusterCommandReply(in);
        		break;
        	case QQ.QQ_CMD_REQUEST_KEY:
        		processRequestKeyReply(in);
        		break;
        	case QQ.QQ_CMD_ADVANCED_SEARCH:
        	    processAdvancedSearchReply(in);
        		break;
        	case QQ.QQ_CMD_AUTHORIZE:
        		processAuthorizeReply(in);
        		break;
        	case QQ.QQ_CMD_SIGNATURE_OP:
        		processSignatureOpReply(in);
        		break;
        	case QQ.QQ_CMD_USER_PROPERTY_OP:
        		processUserPropertyOpReply(in);
        		break;
        	case QQ.QQ_CMD_FRIEND_LEVEL_OP:
        		processFriendLevelOpReply(in);
        		break;
        	case QQ.QQ_CMD_WEATHER_OP:
        		processWeatherOpReply(in);
        		break;
        	case QQ.QQ_CMD_TEMP_SESSION_OP:
        		processTempSessionOpReply(in);
        		break;
        	case QQ.QQ_CMD_PRIVACY_DATA_OP:
        		processPrivacyDataOpReply(in);
        		break;
        	case QQ.QQ_CMD_UNKNOWN:
        	    processUnknown(in);
        		break;
        	default:
        		break;
        }        	
    }

    private void processAuthQuestionOpReply(BasicInPacket in) {
    	QQEvent e;
    	AuthQuestionOpReplyPacket packet = (AuthQuestionOpReplyPacket)in;
    	switch(packet.subCommand) {
    		case QQ.QQ_SUB_CMD_GET_QUESTION:
    			e = new QQEvent(packet);
    			e.type = packet.replyCode == QQ.QQ_REPLY_OK ? QQEvent.FRIEND_GET_AUTH_QUESTION_OK : QQEvent.FRIEND_GET_AUTH_QUESTION_FAIL;
    			client.fireQQEvent(e);
    			break;
    		case QQ.QQ_SUB_CMD_ANSWER_QUESTION:
    			e = new QQEvent(packet);
    			e.type = packet.replyCode == QQ.QQ_REPLY_OK ? QQEvent.FRIEND_RIGHT_ANSWER : QQEvent.FRIEND_WRONG_ANSWER;
    			client.fireQQEvent(e);
    			break;
    	}
	}

	private void processAuthInfoOpReply(BasicInPacket in) {
		QQEvent e;
		AuthInfoOpReplyPacket packet = (AuthInfoOpReplyPacket)in;
		switch(packet.subCommand) {
			case QQ.QQ_SUB_CMD_GET_AUTH_INFO:
				e = new QQEvent(packet);
				e.type = packet.replyCode == QQ.QQ_REPLY_GET_PUZZLE_FROM_URL ? QQEvent.FRIEND_GET_AUTH_INFO_FROM_URL : QQEvent.FRIEND_GET_AUTH_INFO_OK;
				client.fireQQEvent(e);
				break;
			case QQ.QQ_SUB_CMD_SUBMIT_AUTH_INFO:
				e = new QQEvent(packet);
				e.type = packet.replyCode == QQ.QQ_REPLY_OK ? QQEvent.FRIEND_SUBMIT_AUTO_INFO_OK : QQEvent.FRIEND_SUBMIT_AUTO_INFO_FAIL;
				client.fireQQEvent(e);
				break;
		}
	}

	/**
     * 处理隐私选项操作回复包
     * 
     * @param in
     */
    private void processPrivacyDataOpReply(BasicInPacket in) {
    	QQEvent e;
    	PrivacyDataOpReplyPacket packet = (PrivacyDataOpReplyPacket)in;
    	switch(packet.replyCode) {
    		case QQ.QQ_REPLY_OK:
    			e = new QQEvent(packet);
    			e.type = QQEvent.USER_PRIVACY_DATA_OP_OK;
    			client.fireQQEvent(e);
    			break;
    		default:
    			e = new QQEvent(packet);
				e.type = QQEvent.USER_PRIVACY_DATA_OP_FAIL;
				client.fireQQEvent(e);
				break;
    	}
	}

	/**
     * 处理临时会话回复包
     * 
     * @param in
     */
    private void processTempSessionOpReply(BasicInPacket in) {
    	QQEvent e;
    	TempSessionOpReplyPacket packet = (TempSessionOpReplyPacket)in;
    	switch(packet.replyCode) {
    		case QQ.QQ_REPLY_OK:
    		case QQ.QQ_REPLY_MAYBE_OFFLINE:
    			e = new QQEvent(packet);
    			e.type = QQEvent.IM_TEMP_SESSION_SEND_OK;
    			client.fireQQEvent(e);
    			break;
    		default:
    			e = new QQEvent(packet);
    			e.type = QQEvent.IM_TEMP_SESSION_SEND_FAIL;
    			client.fireQQEvent(e);
    			break;
    	}
	}

	/**
     * 处理天气预报操作回复包
     * 
     * @param in
     */
    private void processWeatherOpReply(BasicInPacket in) {
    	WeatherOpReplyPacket packet = (WeatherOpReplyPacket)in;
    	QQEvent e;
    	switch(packet.replyCode) {
    		case QQ.QQ_REPLY_OK:
    			e = new QQEvent(packet);
    			if(packet.province == null)
    				e.type = QQEvent.USER_WEATHER_GET_FAIL;
    			else 
    				e.type = QQEvent.USER_WEATHER_GET_OK;
    			client.fireQQEvent(e);
    			break;
    		default:
       			e = new QQEvent(packet);
				e.type = QQEvent.USER_WEATHER_GET_FAIL;
				client.fireQQEvent(e);
				break;
    	}
	}

	/**
     * 处理发送短信回复事件
     * 
     * @param in
     */
    private void processSendSMSReply(BasicInPacket in) {
    	SendSMSReplyPacket packet = (SendSMSReplyPacket)in;
    	QQEvent e = new QQEvent(packet);
    	e.type = QQEvent.SMS_SEND_OK;
    	client.fireQQEvent(e);
	}

	/**
     * 处理好友等级回复包
     * 
     * @param in
     */
    private void processFriendLevelOpReply(BasicInPacket in) {
    	FriendLevelOpReplyPacket packet = (FriendLevelOpReplyPacket)in;
    	QQEvent e = new QQEvent(packet);
    	e.type = QQEvent.FRIEND_GET_LEVEL_OK;
    	client.fireQQEvent(e);
	}

	/**
     * 处理用户属性回复包
     * 
     * @param in
     */
    private void processUserPropertyOpReply(BasicInPacket in) {
    	UserPropertyOpReplyPacket packet = (UserPropertyOpReplyPacket)in;
    	QQEvent e = new QQEvent(packet);
    	e.type = QQEvent.USER_GET_PROPERTY_OK;
    	client.fireQQEvent(e);
	}

	/**
     * 处理个性签名操作回复包
     * 
     * @param in
     */
    private void processSignatureOpReply(BasicInPacket in) {
    	QQEvent e;
    	SignatureOpReplyPacket packet = (SignatureOpReplyPacket)in;
    	if(packet.replyCode == QQ.QQ_REPLY_OK) {
    		switch(packet.subCommand) {
    			case QQ.QQ_SUB_CMD_MODIFY_SIGNATURE:
    				e = new QQEvent(packet);
    				e.type = QQEvent.USER_MODIFY_SIGNATURE_OK;
    				client.fireQQEvent(e);
    				break;
    			case QQ.QQ_SUB_CMD_DELETE_SIGNATURE:
    				e = new QQEvent(packet);
    				e.type = QQEvent.USER_DELETE_SIGNATURE_OK;
    				client.fireQQEvent(e);
    				break;
    			case QQ.QQ_SUB_CMD_GET_SIGNATURE:
      				e = new QQEvent(packet);
    				e.type = QQEvent.USER_GET_SIGNATURE_OK;
    				client.fireQQEvent(e);
    				break;
    		} 
    	} else {
    		switch(packet.subCommand) {
    			case QQ.QQ_SUB_CMD_MODIFY_SIGNATURE:
    				e = new QQEvent(packet);
    				e.type = QQEvent.USER_MODIFY_SIGNATURE_FAIL;
    				client.fireQQEvent(e);
    				break;
    			case QQ.QQ_SUB_CMD_DELETE_SIGNATURE:
    				e = new QQEvent(packet);
    				e.type = QQEvent.USER_DELETE_SIGNATURE_FAIL;
    				client.fireQQEvent(e);
    				break;
    			case QQ.QQ_SUB_CMD_GET_SIGNATURE:
      				e = new QQEvent(packet);
    				e.type = QQEvent.USER_GET_SIGNATURE_FAIL;
    				client.fireQQEvent(e);
    				break;
    		} 
    	}
	}

	/**
     * 处理认证消息发送包
     * 
     * @param in
     */
    private void processAuthorizeReply(BasicInPacket in) {
    	QQEvent e;
    	AuthorizeReplyPacket packet = (AuthorizeReplyPacket)in;
    	switch(packet.replyCode) {
    		case QQ.QQ_REPLY_OK:
    			e = new QQEvent(packet);
    			e.type = QQEvent.FRIEND_AUTHORIZE_SEND_OK;
    			client.fireQQEvent(e);
    			break;
    		default:
    			e = new QQEvent(packet);
				e.type = QQEvent.FRIEND_AUTHORIZE_SEND_FAIL;
				client.fireQQEvent(e);
				break;
    	}
	}

	/**
     * 处理未知命令包，有些和协议无关的包也使用这个命令，比如ErrorPacket
     * 
     * @param in
     */
    private void processUnknown(BasicInPacket in) {
        QQEvent e = null;
        if(in instanceof ErrorPacket) {
            ErrorPacket error = (ErrorPacket)in;
            switch(error.errorCode) {
                case ErrorPacket.ERROR_CONNECTION_BROKEN:
                    e = new QQEvent(error);
                	e.type = QQEvent.ERROR_CONNECTION_BROKEN;
                	break;
                case ErrorPacket.ERROR_TIMEOUT:
                	OutPacket packet = error.timeoutPacket;
                    switch(packet.getCommand()) {
                    	case QQ.QQ_CMD_KEEP_ALIVE:
                    		// 说明连接已经丢失
                    		e = new QQEvent(packet);
                    		e.type = QQEvent.USER_KEEP_ALIVE_FAIL;
                    		break;
                    	default:
                        	e = new QQEvent(packet);
                    		e.type = QQEvent.SYS_TIMEOUT;
                    		e.operation = packet.getCommand();
                    		break;
                    }
                    break;
                case ErrorPacket.ERROR_PROXY:
                	e = new QQEvent(error);
                	e.type = QQEvent.ERROR_PROXY;
                	break;
                case ErrorPacket.ERROR_NETWORK:
                	e = new QQEvent(error);
                	e.type = QQEvent.ERROR_NETWORK;
                	break;
                case ErrorPacket.RUNTIME_ERROR:
                	e = new QQEvent(error);
                	e.type = QQEvent.ERROR_RUNTIME;
                	break;
            }
        }
        
        if(e != null)
        	client.fireQQEvent(e);
    }

    /**
     * 处理高级搜索的回复包
     * 
     * @param in
     */
    private void processAdvancedSearchReply(BasicInPacket in) {
        QQEvent e;
        AdvancedSearchUserReplyPacket packet = (AdvancedSearchUserReplyPacket)in;
        
        if(packet.replyCode == QQ.QQ_REPLY_OK && !packet.finished) {
            log.debug("高级搜索成功");
            e = new QQEvent(packet);
            e.type = QQEvent.USER_ADVANCED_SEARCH_OK;
            client.fireQQEvent(e);
        } else if(packet.replyCode == QQ.QQ_REPLY_ADVANCED_SEARCH_END || packet.finished) {
            log.debug("高级搜索完毕");
            e = new QQEvent(packet);
            e.type = QQEvent.USER_ADVANCED_SEARCH_END;
            client.fireQQEvent(e);
        } else
            log.debug("高级搜索返回未知回复码");
    }
    
    /**
     * 处理请求登录令牌的回复包
     * 
     * @param in
     */
    private void processGetLoginTokenReply(BasicInPacket in) {
        QQEvent e;
        GetLoginTokenReplyPacket packet = (GetLoginTokenReplyPacket)in;
        
        switch(packet.replyCode) {
        	case QQ.QQ_REPLY_OK:
                log.debug("得到登录令牌成功");
                user.setLoginToken(packet.token);
                e = new QQEvent(packet);
                e.type = QQEvent.LOGIN_GET_TOKEN_OK;
                client.fireQQEvent(e);
                break;
        	case QQ.QQ_REPLY_LOGIN_NEED_VERIFY:
        		log.debug("需要输入验证码");
        		e = new QQEvent(packet);
        		e.type = QQEvent.LOGIN_NEED_VERIFY;
        		client.fireQQEvent(e);
        		break;
        }
    }

	/**
	 * 处理群命令的回复包
	 * 
	 * @param in
	 * @throws PacketParseException
	 */
	private void processClusterCommandReply(BasicInPacket in) {
		ClusterCommandReplyPacket packet = (ClusterCommandReplyPacket)in;

		switch(packet.subCommand) {
			case QQ.QQ_CLUSTER_CMD_CREATE_CLUSTER:
				processClusterCreateReply(packet);
				break;
			case QQ.QQ_CLUSTER_CMD_ACTIVATE_CLUSTER:
				processClusterActivateReply(packet);
				break;
			case QQ.QQ_CLUSTER_CMD_ACTIVATE_TEMP:
				processClusterActivateTempReply(packet);
				break;
			case QQ.QQ_CLUSTER_CMD_MODIFY_MEMBER:
				processClusterModifyMember(packet);
				break;
			case QQ.QQ_CLUSTER_CMD_GET_CLUSTER_INFO:
				processClusterGetInfoReply(packet);
				break;
			case QQ.QQ_CLUSTER_CMD_GET_ONLINE_MEMBER:
				processClusterGetOnlineMemberReply(packet);
				break;
			case QQ.QQ_CLUSTER_CMD_GET_MEMBER_INFO:
				processClusterGetMemberInfoReply(packet);
				break;
			case QQ.QQ_CLUSTER_CMD_EXIT_CLUSTER:
				processClusterExitReply(packet);
				break;
			case QQ.QQ_CLUSTER_CMD_DISMISS_CLUSTER:
				processClusterDismissReply(packet);
				break;
			case QQ.QQ_CLUSTER_CMD_JOIN_CLUSTER:
				processClusterJoinReply(packet);
				break;
			case QQ.QQ_CLUSTER_CMD_JOIN_CLUSTER_AUTH:
				processClusterJoinAuthReply(packet);
				break;
			case QQ.QQ_CLUSTER_CMD_MODIFY_CLUSTER_INFO:
				processClusterModifyInfoReply(packet);
				break;
			case QQ.QQ_CLUSTER_CMD_SEARCH_CLUSTER:
				processClusterSearchReply(packet);
				break;
			// 这个命令已经过时
//			case QQ.QQ_CLUSTER_CMD_SEND_IM:
//				processClusterSendIMReply(packet);
//				break;
			case QQ.QQ_CLUSTER_CMD_SEND_IM_EX:
			    processClusterSendIMExReply(packet);
				break;
			case QQ.QQ_CLUSTER_CMD_CREATE_TEMP:
			    processClusterCreateTempClusterReply(packet);
				break;
			case QQ.QQ_CLUSTER_CMD_SEND_TEMP_IM:
			    processClusterSendTempClusterIMReply(packet);
				break;
			case QQ.QQ_CLUSTER_CMD_EXIT_TEMP:
			    processClusterExitTempClusterReply(packet);	
				break;
			case QQ.QQ_CLUSTER_CMD_GET_TEMP_INFO:
			    processClusterGetTempClusterInfoReply(packet);
				break;
			case QQ.QQ_CLUSTER_CMD_MODIFY_TEMP_MEMBER:
			    processClusterModifyTempClusterMemberReply(packet);	
				break;
			case QQ.QQ_CLUSTER_CMD_SUB_CLUSTER_OP:
				processClusterSubClusterOpReply(packet);
				break;
			case QQ.QQ_CLUSTER_CMD_UPDATE_ORGANIZATION:
				processUpdateOrganizationReply(packet);
				break;
			case QQ.QQ_CLUSTER_CMD_COMMIT_ORGANIZATION:
				processCommitOrganizationReply(packet);
				break;
			case QQ.QQ_CLUSTER_CMD_COMMIT_MEMBER_ORGANIZATION:
				processCommitMemberOrganizationReply(packet);
				break;
			case QQ.QQ_CLUSTER_CMD_MODIFY_TEMP_INFO:
				processModifyTempClusterInfoReply(packet);
				break;
			case QQ.QQ_CLUSTER_CMD_GET_CARD_BATCH:
				processGetCardBatchReply(packet);
				break;
			case QQ.QQ_CLUSTER_CMD_GET_CARD:
				processGetCardReply(packet);
				break;
			case QQ.QQ_CLUSTER_CMD_MODIFY_CARD:
				processModifyCardReply(packet);
				break;
			case QQ.QQ_CLUSTER_CMD_SET_ROLE:
				processSetRoleReply(packet);
				break;
			case QQ.QQ_CLUSTER_CMD_TRANSFER_ROLE:
				processTransferRoleReply(packet);
				break;
		}
	}

    /**
     * 处理激活临时群回复包
     * 
     * @param packet
     */
    private void processClusterActivateTempReply(ClusterCommandReplyPacket packet) {
		QQEvent e;
		if(packet.replyCode == QQ.QQ_REPLY_OK) {
			log.debug("临时群激活成功");
			e = new QQEvent(packet);
			e.type = QQEvent.CLUSTER_ACTIVATE_TEMP_OK;
			client.fireQQEvent(e);
		} else {
			log.debug("群激活失败");
			ClusterCommandPacket ccp = (ClusterCommandPacket)policy.retrieveSent(packet);
			packet.clusterId = ccp.getClusterId();
			e = new QQEvent(packet);
			e.type = QQEvent.CLUSTER_ACTIVATE_TEMP_FAIL;
			client.fireQQEvent(e);
		}
	}

	/**
     * 处理解散群回复包
     * 
     * @param packet
     */
    private void processClusterDismissReply(ClusterCommandReplyPacket packet) {
    	QQEvent e;
    	if(packet.replyCode == QQ.QQ_REPLY_OK) {
    		e = new QQEvent(packet);
    		e.type = QQEvent.CLUSTER_DISMISS_OK;
    		client.fireQQEvent(e);
    	} else {
    		e = new QQEvent(packet);
    		e.type = QQEvent.CLUSTER_DISMISS_FAIL;
    		client.fireQQEvent(e);
    	}
	}

	/**
     * 处理转让身份回复包
     * 
     * @param packet
     */
    private void processTransferRoleReply(ClusterCommandReplyPacket packet) {
    	QQEvent e;
    	if(packet.replyCode == QQ.QQ_REPLY_OK) {
    		e = new QQEvent(packet);
    		e.type = QQEvent.CLUSTER_TRANSFER_ROLE_OK;
    		client.fireQQEvent(e);
    	} else {
    		e = new QQEvent(packet);
    		e.type = QQEvent.CLUSTER_TRANSFER_ROLE_FAIL;
    		client.fireQQEvent(e);
    	}
	}

	/**
     * 处理设置成员角色回复包
     * 
     * @param packet
     */
    private void processSetRoleReply(ClusterCommandReplyPacket packet) {
    	QQEvent e;
    	if(packet.replyCode == QQ.QQ_REPLY_OK) {
    		e = new QQEvent(packet);
    		e.type = QQEvent.CLUSTER_SET_ROLE_OK;
    		client.fireQQEvent(e);
    	} else {
    		e = new QQEvent(packet);
    		e.type = QQEvent.CLUSTER_SET_ROLE_FAIL;
    		client.fireQQEvent(e);
    	}
	}

	/**
     * 处理修改群名片回复包
     * 
     * @param packet
     */
    private void processModifyCardReply(ClusterCommandReplyPacket packet) {
    	QQEvent e;
    	if(packet.replyCode == QQ.QQ_REPLY_OK) {
    		e = new QQEvent(packet);
    		e.type = QQEvent.CLUSTER_MODIFY_CARD_OK;
    		client.fireQQEvent(e);
    	} else {
			ClusterCommandPacket ccp = (ClusterCommandPacket)policy.retrieveSent(packet);
			packet.clusterId = ccp.getClusterId();
    		e = new QQEvent(packet);
    		e.type = QQEvent.CLUSTER_MODIFY_CARD_FAIL;
    		client.fireQQEvent(e);
    	}
	}

	/**
     * 处理得到单个成员群名片信息回复包
     * 
     * @param packet
     */
    private void processGetCardReply(ClusterCommandReplyPacket packet) {
    	QQEvent e;
    	if(packet.replyCode == QQ.QQ_REPLY_OK) {
    		e = new QQEvent(packet);
    		e.type = QQEvent.CLUSTER_GET_CARD_OK;
    		client.fireQQEvent(e);
    	} else {
			ClusterCommandPacket ccp = (ClusterCommandPacket)policy.retrieveSent(packet);
			packet.clusterId = ccp.getClusterId();
    		e = new QQEvent(packet);
    		e.type = QQEvent.CLUSTER_GET_CARD_FAIL;
    		client.fireQQEvent(e);
    	}
	}

	/**
     * 处理批量得到群名片回复包
     * 
     * @param packet
     */
    private void processGetCardBatchReply(ClusterCommandReplyPacket packet) {
    	QQEvent e;
    	if(packet.replyCode == QQ.QQ_REPLY_OK) {
    		e = new QQEvent(packet);
    		e.type = QQEvent.CLUSTER_GET_CARDS_OK;
    		client.fireQQEvent(e);
    	} else {
			ClusterCommandPacket ccp = (ClusterCommandPacket)policy.retrieveSent(packet);
			packet.clusterId = ccp.getClusterId();
    		e = new QQEvent(packet);
    		e.type = QQEvent.CLUSTER_GET_CARDS_FAIL;
    		client.fireQQEvent(e);
    	}
	}

	/**
     * 处理修改临时群信息回复包
     * 
     * @param packet
     */
    private void processModifyTempClusterInfoReply(ClusterCommandReplyPacket packet) {
    	QQEvent e;
    	if(packet.replyCode == QQ.QQ_REPLY_OK) {
    		e = new QQEvent(packet);
    		e.type = QQEvent.CLUSTER_MODIFY_TEMP_INFO_OK;
    		client.fireQQEvent(e);
    	} else {
			ClusterCommandPacket ccp = (ClusterCommandPacket)policy.retrieveSent(packet);
			packet.clusterId = ccp.getClusterId();
    		e = new QQEvent(packet);
    		e.type = QQEvent.CLUSTER_MODIFY_TEMP_INFO_FAIL;
    		client.fireQQEvent(e);
    	}
	}

	/**
     * 处理提交成员分组情况回复包
     * 
     * @param packet
     */
    private void processCommitMemberOrganizationReply(ClusterCommandReplyPacket packet) {
    	QQEvent e;
    	if(packet.replyCode == QQ.QQ_REPLY_OK) {
    		e = new QQEvent(packet);
    		e.type = QQEvent.CLUSTER_COMMIT_MEMBER_ORG_OK;
    		client.fireQQEvent(e);
    	} else {
			ClusterCommandPacket ccp = (ClusterCommandPacket)policy.retrieveSent(packet);
			packet.clusterId = ccp.getClusterId();
    		e = new QQEvent(packet);
    		e.type = QQEvent.CLUSTER_COMMIT_MEMBER_ORG_FAIL;
    		client.fireQQEvent(e);
    	}
	}

	/**
     * 处理提交组织架构回复包
     * 
     * @param packet
     */
    private void processCommitOrganizationReply(ClusterCommandReplyPacket packet) {
    	QQEvent e;
    	if(packet.replyCode == QQ.QQ_REPLY_OK) {
    		e = new QQEvent(packet);
    		e.type = QQEvent.CLUSTER_COMMIT_ORG_OK;
    		client.fireQQEvent(e);
    	} else {
			ClusterCommandPacket ccp = (ClusterCommandPacket)policy.retrieveSent(packet);
			packet.clusterId = ccp.getClusterId();
    		e = new QQEvent(packet);
    		e.type = QQEvent.CLUSTER_COMMIT_ORG_FAIL;
    		client.fireQQEvent(e);
    	}
	}

	/**
     * 处理更新组织架构回复包
     * 
     * @param packet
     */
    private void processUpdateOrganizationReply(ClusterCommandReplyPacket packet) {
    	QQEvent e;
    	if(packet.replyCode == QQ.QQ_REPLY_OK) {
    		e = new QQEvent(packet);
    		e.type = QQEvent.CLUSTER_UPDATE_ORG_OK;
    		client.fireQQEvent(e);
    	} else {
			ClusterCommandPacket ccp = (ClusterCommandPacket)policy.retrieveSent(packet);
			packet.clusterId = ccp.getClusterId();
    		e = new QQEvent(packet);
    		e.type = QQEvent.CLUSTER_UPDATE_ORG_FAIL;
    		client.fireQQEvent(e);
    	}
	}

	/**
     * 处理讨论组操作回复包
     * 
     * @param packet
     */
    private void processClusterSubClusterOpReply(ClusterCommandReplyPacket packet) {
    	QQEvent e;
    	if(packet.replyCode == QQ.QQ_REPLY_OK) {
    		e = new QQEvent(packet);
    		e.type = QQEvent.CLUSTER_SUB_OP_OK;
    		client.fireQQEvent(e);
    	} else {
    		ClusterCommandPacket ccp = (ClusterCommandPacket)policy.retrieveSent(packet);
    		packet.clusterId = ccp.getClusterId();
    		e = new QQEvent(packet);
    		e.type = QQEvent.CLUSTER_SUB_OP_FAIL;
    		client.fireQQEvent(e);
    	}
	}

	/**
     * 处理修改临时群成员的回复包
     * 
     * @param packet
     */
    private void processClusterModifyTempClusterMemberReply(ClusterCommandReplyPacket packet) {
		QQEvent e;
		ClusterCommandPacket ccp = (ClusterCommandPacket)policy.retrieveSent(packet);
		packet.clusterId = ccp.getClusterId();
		if(packet.replyCode == QQ.QQ_REPLY_OK) {
		    log.debug("临时群成员修改成功，群ID：" + packet.clusterId + " 父ID：" + packet.parentClusterId);
			e = new QQEvent(packet);
			e.type = QQEvent.CLUSTER_MODIFY_TEMP_MEMBER_OK;
			client.fireQQEvent(e);
		} else {
			log.debug("临时群成员修改失败");
			e = new QQEvent(packet);
			e.type = QQEvent.CLUSTER_MODIFY_TEMP_MEMBER_FAIL;
			client.fireQQEvent(e);
		}
    }

    /**
     * 处理创建临时群的回复包
     * 
     * @param packet
     */
    private void processClusterCreateTempClusterReply(ClusterCommandReplyPacket packet) {
		QQEvent e;
		if(packet.replyCode == QQ.QQ_REPLY_OK) {
			log.debug("临时群创建成功，群ID：" + packet.clusterId + " 父ID：" + packet.parentClusterId);
			e = new QQEvent(packet);
			e.type = QQEvent.CLUSTER_CREATE_TEMP_OK;
			client.fireQQEvent(e);
		} else {
			log.debug("临时群创建失败");
			e = new QQEvent(packet);
			e.type = QQEvent.CLUSTER_CREATE_TEMP_FAIL;
			client.fireQQEvent(e);
		}
    }

    /**
	 * 处理修改群成员的回复包
	 * 
	 * @param packet
	 * 		ClusterCommandReplyPacket
	 */
	private void processClusterModifyMember(ClusterCommandReplyPacket packet) {
		QQEvent e;
		if(packet.replyCode == QQ.QQ_REPLY_OK) {
			log.debug("群成员修改成功");
			e = new QQEvent(packet);
			e.type = QQEvent.CLUSTER_MODIFY_MEMBER_OK;
			client.fireQQEvent(e);
		} else {
			log.debug("群成员修改失败");
			ClusterCommandPacket ccp = (ClusterCommandPacket)policy.retrieveSent(packet);
			packet.clusterId = ccp.getClusterId();
			e = new QQEvent(packet);
			e.type = QQEvent.CLUSTER_MODIFY_MEMBER_FAIL;
			client.fireQQEvent(e);
		}
	}

    /**
     * @param packet
     */
    private void processClusterSendTempClusterIMReply(ClusterCommandReplyPacket packet) {
		QQEvent e;
		ClusterCommandPacket ccp = (ClusterCommandPacket)policy.retrieveSent(packet);
		packet.clusterId = ccp.getClusterId();
		if(packet.replyCode == QQ.QQ_REPLY_OK) {
			log.debug("临时群消息发送成功");
			e = new QQEvent(packet);
			e.type = QQEvent.IM_TEMP_CLUSTER_SEND_OK;
			client.fireQQEvent(e);
		} else {
			log.debug("临时群消息发送失败");
			e = new QQEvent(packet);
			e.type = QQEvent.IM_TEMP_CLUSTER_SEND_FAIL;
			client.fireQQEvent(e);
		}
    }

    /**
     * 处理扩展群消息回复包
     * 
     * @param packet
     */
    private void processClusterSendIMExReply(ClusterCommandReplyPacket packet) {
		QQEvent e;
		if(packet.replyCode == QQ.QQ_REPLY_OK) {
			log.debug("群消息发送成功");
			e = new QQEvent(packet);
			e.type = QQEvent.IM_CLUSTER_SEND_EX_OK;
			client.fireQQEvent(e);
		} else {
			log.debug("群消息发送失败");
			ClusterCommandPacket ccp = (ClusterCommandPacket)policy.retrieveSent(packet);
			packet.clusterId = ccp.getClusterId();
			e = new QQEvent(packet);
			e.type = QQEvent.IM_CLUSTER_SEND_EX_FAIL;
			client.fireQQEvent(e);
		}
    }

	/**
	 * 处理搜索群的回复包
	 * 
	 * @param packet
	 */
	private void processClusterSearchReply(ClusterCommandReplyPacket packet) {
		QQEvent e;
		if(packet.replyCode == QQ.QQ_REPLY_OK) {
			log.debug("群搜索成功");
			e = new QQEvent(packet);
			e.type = QQEvent.CLUSTER_SEARCH_OK;
			client.fireQQEvent(e);
		} else {
			log.debug("搜索群失败");
			e = new QQEvent(packet);
			e.type = QQEvent.CLUSTER_SEARCH_FAIL;
			client.fireQQEvent(e);
		}
	}

	// 处理修改群信息的回复包
	private void processClusterModifyInfoReply(ClusterCommandReplyPacket packet) {
		QQEvent e;
		if(packet.replyCode == QQ.QQ_REPLY_OK) {
			log.debug("群信息修改成功");
			e = new QQEvent(packet);
			e.type = QQEvent.CLUSTER_MODIFY_INFO_OK;
			client.fireQQEvent(e);
		} else {
			log.debug("群信息修改失败");
			ClusterCommandPacket ccp = (ClusterCommandPacket)policy.retrieveSent(packet);
			packet.clusterId = ccp.getClusterId();
			e = new QQEvent(packet);
			e.type = QQEvent.CLUSTER_MODIFY_INFO_FAIL;
			client.fireQQEvent(e);
		}
	}

	// 处理认证信息发送的回复包
	private void processClusterJoinAuthReply(ClusterCommandReplyPacket packet) {
		QQEvent e;
		if(packet.replyCode == QQ.QQ_REPLY_OK) {
			log.debug("认证信息发送成功");
			e = new QQEvent(packet);
			e.type = QQEvent.CLUSTER_AUTH_SEND_OK;
			client.fireQQEvent(e);
		} else {
			log.debug("认证信息发送失败");
			ClusterCommandPacket ccp = (ClusterCommandPacket)policy.retrieveSent(packet);
			packet.clusterId = ccp.getClusterId();
			e = new QQEvent(packet);
			e.type = QQEvent.CLUSTER_AUTH_SEND_FAIL;
			client.fireQQEvent(e);
		}
	}

	// 处理加入群的回复包
	private void processClusterJoinReply(ClusterCommandReplyPacket packet) {
		QQEvent e;
		if(packet.replyCode == QQ.QQ_REPLY_OK) {
		    switch(packet.joinReply) {
		        case QQ.QQ_CLUSTER_JOIN_OK:
					log.debug("加入群成功");
					e = new QQEvent(packet);
					e.type = QQEvent.CLUSTER_JOIN_OK;
					client.fireQQEvent(e);
					break;
				case QQ.QQ_CLUSTER_JOIN_NEED_AUTH:
					log.debug("对方需要认证");
					e = new QQEvent(packet);
					e.type = QQEvent.CLUSTER_JOIN_NEED_AUTH;
					client.fireQQEvent(e);
					break;
				case QQ.QQ_CLUSTER_JOIN_DENIED:
				    log.debug("对方禁止加入成员");
					e = new QQEvent(packet);
					e.type = QQEvent.CLUSTER_JOIN_DENY;
					client.fireQQEvent(e);
					break;
				default:
				    log.debug("收到加入群的回复，但是回复码未知，抛弃该包");
					break;
		    }
		} else {
			log.debug("加入群失败");
			ClusterCommandPacket ccp = (ClusterCommandPacket)policy.retrieveSent(packet);
			packet.clusterId = ccp.getClusterId();
			e = new QQEvent(packet);
			e.type = QQEvent.CLUSTER_JOIN_FAIL;
			client.fireQQEvent(e);
		}
	}	

    /**
     * @param packet
     */
    private void processClusterExitTempClusterReply(ClusterCommandReplyPacket packet) {
		QQEvent e;
		ClusterCommandPacket ccp = (ClusterCommandPacket)policy.retrieveSent(packet);
		packet.clusterId = ccp.getClusterId();
		if(packet.replyCode == QQ.QQ_REPLY_OK) {
			log.debug("退出群成功");
			e = new QQEvent(packet);
			e.type = QQEvent.CLUSTER_EXIT_TEMP_OK;
			client.fireQQEvent(e);
		} else {
			log.debug("退出群失败");
			e = new QQEvent(packet);
			e.type = QQEvent.CLUSTER_EXIT_TEMP_FAIL;
			client.fireQQEvent(e);
		}
    }

	/**
	 * 处理退出群的回复包
	 * 
	 * @param packet
	 */
	private void processClusterExitReply(ClusterCommandReplyPacket packet) {
		QQEvent e;
		if(packet.replyCode == QQ.QQ_REPLY_OK) {
			log.debug("退出群成功");
			e = new QQEvent(packet);
			e.type = QQEvent.CLUSTER_EXIT_OK;
			client.fireQQEvent(e);
		} else {
			log.debug("退出群失败");
			ClusterCommandPacket ccp = (ClusterCommandPacket)policy.retrieveSent(packet);
			packet.clusterId = ccp.getClusterId();
			e = new QQEvent(packet);
			e.type = QQEvent.CLUSTER_EXIT_FAIL;
			client.fireQQEvent(e);
		}
	}

	// 处理得到群成员信息的回复包
	private void processClusterGetMemberInfoReply(ClusterCommandReplyPacket packet) {
		QQEvent e;
		if(packet.replyCode == QQ.QQ_REPLY_OK) {
			log.debug("得到群成员信息成功");
			e = new QQEvent(packet);
			e.type = QQEvent.CLUSTER_GET_MEMBER_INFO_OK;
			client.fireQQEvent(e);
		} else {
			log.debug("得到群成员信息失败");
			ClusterCommandPacket ccp = (ClusterCommandPacket)policy.retrieveSent(packet);
			packet.clusterId = ccp.getClusterId();
			e = new QQEvent(packet);
			e.type = QQEvent.CLUSTER_GET_ONLINE_MEMBER_FAIL;
			client.fireQQEvent(e);
		}
	}

	// 处理得到在线群成员的回复包
	private void processClusterGetOnlineMemberReply(ClusterCommandReplyPacket packet) {
		QQEvent e;
		if(packet.replyCode == QQ.QQ_REPLY_OK) {
			log.debug("得到在线成员列表成功");
			e = new QQEvent(packet);
			e.type = QQEvent.CLUSTER_GET_ONLINE_MEMBER_OK;
			client.fireQQEvent(e);
		} else {
			log.debug("得到在线成员列表失败");
			ClusterCommandPacket ccp = (ClusterCommandPacket)policy.retrieveSent(packet);
			packet.clusterId = ccp.getClusterId();
			e = new QQEvent(packet);
			e.type = QQEvent.CLUSTER_GET_ONLINE_MEMBER_FAIL;
			client.fireQQEvent(e);
		}
	}

	/**
	 * 处理得到群信息回复包
	 * 
	 * @param packet
	 */
	private void processClusterGetInfoReply(ClusterCommandReplyPacket packet) {
		QQEvent e;
		if(packet.replyCode == QQ.QQ_REPLY_OK) {
			log.debug("成功得到群信息，群内部ID：" + packet.clusterId + " 群名称：" + packet.info.name);
			e = new QQEvent(packet);
			e.type = QQEvent.CLUSTER_GET_INFO_OK;
			client.fireQQEvent(e);
		} else {
			log.debug("得到群信息失败");
			ClusterCommandPacket ccp = (ClusterCommandPacket)policy.retrieveSent(packet);
			packet.clusterId = ccp.getClusterId();
			e = new QQEvent(packet);
			e.type = QQEvent.CLUSTER_GET_INFO_FAIL;
			client.fireQQEvent(e);
		}
	}

	/**
	 * 处理得到临时群信息回复包
	 * 
     * @param packet
     */
    private void processClusterGetTempClusterInfoReply(ClusterCommandReplyPacket packet) {
		QQEvent e;
		if(packet.replyCode == QQ.QQ_REPLY_OK) {
			log.debug("成功得到群信息，群内部ID：" + packet.clusterId + " 群名称：" + packet.info.name);
			e = new QQEvent(packet);
			e.type = QQEvent.CLUSTER_GET_TEMP_INFO_OK;
			client.fireQQEvent(e);
		} else {
			log.debug("得到群信息失败");
			ClusterCommandPacket ccp = (ClusterCommandPacket)policy.retrieveSent(packet);
			packet.clusterId = ccp.getClusterId();
			e = new QQEvent(packet);
			e.type = QQEvent.CLUSTER_GET_TEMP_INFO_FAIL;
			client.fireQQEvent(e);
		}
    }

	/**
	 * 处理激活群回复包
	 * 
	 * @param packet
	 */
	private void processClusterActivateReply(ClusterCommandReplyPacket packet) {
		QQEvent e;
		if(packet.replyCode == QQ.QQ_REPLY_OK) {
			log.debug("群激活成功");
			e = new QQEvent(packet);
			e.type = QQEvent.CLUSTER_ACTIVATE_OK;
			client.fireQQEvent(e);
		} else {
			log.debug("群激活失败");
			ClusterCommandPacket ccp = (ClusterCommandPacket)policy.retrieveSent(packet);
			packet.clusterId = ccp.getClusterId();
			e = new QQEvent(packet);
			e.type = QQEvent.CLUSTER_ACTIVATE_FAIL;
			client.fireQQEvent(e);
		}
	}

	// 处理创建群回复包
	private void processClusterCreateReply(ClusterCommandReplyPacket packet) {
		QQEvent e;
		if(packet.replyCode == QQ.QQ_REPLY_OK) {
			log.debug("群创建成功，内部ID：" + packet.clusterId + " 外部ID：" + packet.externalId);
			e = new QQEvent(packet);
			e.type = QQEvent.CLUSTER_CREATE_OK;
			client.fireQQEvent(e);
		} else {
			log.debug("群创建失败");
			e = new QQEvent(packet);
			e.type = QQEvent.CLUSTER_CREATE_FAIL;
			client.fireQQEvent(e);
		}
	}

	/**
	 * 处理上传下载备注的回复包
	 * 
	 * @param in
	 */
	private void processFriendDataOpReply(BasicInPacket in) {
		QQEvent e;
		FriendDataOpReplyPacket packet = (FriendDataOpReplyPacket)in;
		// 判断操作类型
		if(packet.replyCode == QQ.QQ_REPLY_OK) {
			switch(packet.subCommand) {
			    case QQ.QQ_SUB_CMD_BATCH_DOWNLOAD_FRIEND_REMARK:
					log.debug("批量下载好友备注信息成功");
					e = new QQEvent(packet);
					e.type = QQEvent.FRIEND_GET_REMARKS_OK;
					client.fireQQEvent(e);
					break;
			    case QQ.QQ_SUB_CMD_UPLOAD_FRIEND_REMARK:
					log.debug("上传好友备注信息成功");
					e = new QQEvent(policy.retrieveSent(packet));
					e.type = QQEvent.FRIEND_UPLOAD_REMARKS_OK;
					client.fireQQEvent(e);
					break;
				case QQ.QQ_SUB_CMD_REMOVE_FRIEND_FROM_LIST:
				    log.debug("更新服务器端好友列表成功");
				    e = new QQEvent(policy.retrieveSent(packet));
					e.type = QQEvent.FRIEND_REMOVE_FROM_LIST_OK;
					client.fireQQEvent(e);
					break;
				case QQ.QQ_SUB_CMD_DOWNLOAD_FRIEND_REMARK:
					log.debug("下载好友备注信息成功");
					e = new QQEvent(packet);
					e.type = QQEvent.FRIEND_GET_REMARK_OK;
					client.fireQQEvent(e);
					break;
			}		    
		}
	}

	/**
	 * 处理下载分组好友列表回复包
	 * 
	 * @param in
	 */
	private void processDownloadGroupFriendReply(BasicInPacket in) {
		QQEvent e;
		DownloadGroupFriendReplyPacket packet = (DownloadGroupFriendReplyPacket)in;
		policy.retrieveSent(packet);
		// 触发事件 
		if(packet.replyCode == QQ.QQ_REPLY_OK) {
			log.debug("下载分组好友列表成功");
			e = new QQEvent(packet);
			e.type = QQEvent.FRIEND_DOWNLOAD_GROUPS_OK;
			client.fireQQEvent(e);			
		} else {
			log.debug("下载分组好友失败");
			e = new QQEvent(packet);
			e.type = QQEvent.FRIEND_DOWNLOAD_GROUPS_FAIL;
			client.fireQQEvent(e);		
		}
	}

	/**
	 * 处理上传分组名称回复包
	 * 
	 * @param in
	 */
	private void processGroupNameOpReply(BasicInPacket in) {
		QQEvent e;
		GroupDataOpReplyPacket packet = (GroupDataOpReplyPacket)in;
		// 触发事件
		policy.retrieveSent(packet);
		if(packet.subCommand == QQ.QQ_SUB_CMD_UPLOAD_GROUP_NAME) {
			log.debug("上传分组名称成功");
			e = new QQEvent(packet);
			e.type = QQEvent.FRIEND_UPDATE_GROUP_NAMES_OK;
			client.fireQQEvent(e);			
		} else if(packet.subCommand == QQ.QQ_SUB_CMD_DOWNLOAD_GROUP_NAME) {
			log.debug("下载分组名称成功");
			e = new QQEvent(packet);
			e.type = QQEvent.FRIEND_GET_GROUP_NAMES_OK;
			client.fireQQEvent(e);
		} else {
			log.debug("未知的分组名称操作类型，忽略该包");
		}
	}

	/**
	 * 处理上传分组好友列表回复包
	 * 
	 * @param in
	 */
	private void processUploadGroupFriendReply(BasicInPacket in) {
		QQEvent e;
		UploadGroupFriendReplyPacket packet = (UploadGroupFriendReplyPacket)in;
		// 触发事件
		policy.retrieveSent(packet);
		if(packet.replyCode == QQ.QQ_REPLY_OK) {
			log.debug("上传分组好友列表成功");
			e = new QQEvent(packet);
			e.type = QQEvent.FRIEND_UPLOAD_GROUPS_OK;
			client.fireQQEvent(e);		
		} else {
			log.debug("上传分组好友列表失败");
			e = new QQEvent(packet);
			e.type = QQEvent.FRIEND_UPLOAD_GROUPS_FAIL;
			client.fireQQEvent(e);	
		}
	}

	/**
	 * 处理搜索在线用户的回复包
	 * 
	 * @param in
	 */
	private void processSearchUserReply(BasicInPacket in) {
		QQEvent e;
		SearchUserReplyPacket packet = (SearchUserReplyPacket)in;
		
		// 触发事件
		if(packet.replyCode == QQ.QQ_REPLY_OK) {
			e = new QQEvent(packet);
			e.type = QQEvent.USER_SEARCH_OK;
			client.fireQQEvent(e);			
		}
	}

    /**
     * 处理删除自己的回复包
     * 
     * @param in
     */
    private void processRemoveSelfReply(BasicInPacket in) {
        QQEvent e;
        RemoveSelfReplyPacket packet = (RemoveSelfReplyPacket)in;
        if(packet.replyCode == QQ.QQ_REPLY_OK) {
            log.debug("已经从对方好友列表中删除了自己");
            e = new QQEvent(packet);
            e.type = QQEvent.FRIEND_REMOVE_SELF_OK;
            client.fireQQEvent(e);
        } else {
            log.debug("从对方好友列表中删除自己失败");
            e = new QQEvent(packet);
            e.type = QQEvent.FRIEND_REMOVE_SELF_FAIL;
            client.fireQQEvent(e);
        }
    }

    /**
     * 处理修改个人信息的回复包
     * 
     * @param in
     */
    private void processModifyInfoReply(BasicInPacket in) {
        QQEvent e;
        ModifyInfoReplyPacket packet = (ModifyInfoReplyPacket)in;
        if(packet.success) {
            log.debug("修改信息成功");
            // 设置用户的新的信息
            ModifyInfoPacket miPacket = (ModifyInfoPacket)policy.retrieveSent(packet);
            user.setContactInfo(miPacket.getContactInfo());
            e = new QQEvent(miPacket);
            e.type = QQEvent.USER_MODIFY_INFO_OK;
            client.fireQQEvent(e);
        } else {
            log.debug("修改信息失败");
            e = new QQEvent(policy.retrieveSent(packet));
            e.type = QQEvent.USER_MODIFY_INFO_FAIL;
            client.fireQQEvent(e);
        }
    }

    /**
     * 处理发送认证信息的回复包
     * 
     * @param in
     * @throws PacketParseException
     */
    private void processAddFriendAuthReply(BasicInPacket in) {
        QQEvent e;
        AddFriendAuthResponseReplyPacket packet = (AddFriendAuthResponseReplyPacket)in;
        if(packet.replyCode != QQ.QQ_REPLY_ADD_FRIEND_AUTH_OK) {
            log.debug("认证信息发送失败");
            e = new QQEvent(policy.retrieveSent(packet));
            e.type = QQEvent.FRIEND_AUTH_SEND_FAIL;
            client.fireQQEvent(e);
        } else {
            log.debug("认证信息发送成功，等待对方回复");
            e = new QQEvent(policy.retrieveSent(packet));
            e.type = QQEvent.FRIEND_AUTH_SEND_OK;
            client.fireQQEvent(e);
        }
    }

    /**
     * 处理删除好友的回复包
     * 
     * @param in
     */
    private void processDeleteFriendReply(BasicInPacket in) {
        QQEvent e;
        DeleteFriendReplyPacket packet = (DeleteFriendReplyPacket)in;
        if(packet.replyCode != QQ.QQ_REPLY_OK) {
            log.debug("好友删除失败");
            e = new QQEvent(policy.retrieveSent(packet));
            e.type = QQEvent.FRIEND_DELETE_FAIL;
            client.fireQQEvent(e);
        } else {
            log.debug("好友删除成功");
            e = new QQEvent(policy.retrieveSent(packet));
            e.type = QQEvent.FRIEND_DELETE_OK;
            client.fireQQEvent(e);
        }
    }

    /**
     * 处理请求加一个好友的回复包
     * 
     * @param in
     */
    private void processAddFriendExReply(BasicInPacket in) {
        QQEvent e;
        AddFriendExReplyPacket packet = (AddFriendExReplyPacket)in;
        if(packet.replyCode == QQ.QQ_REPLY_OK) {
        	switch(packet.authCode) {
        		case QQ.QQ_AUTH_NO:
                    log.debug("好友添加成功");
                    e = new QQEvent(packet);
                    e.type = QQEvent.FRIEND_ADD_OK;
                    client.fireQQEvent(e);
                    break;
        		case QQ.QQ_AUTH_NEED:
                    log.debug("对方要求认证");
                    e = new QQEvent(packet);
                    e.type = QQEvent.FRIEND_ADD_NEED_AUTH;
                    client.fireQQEvent(e);
                    break;
        		case QQ.QQ_AUTH_REJECT:
                    log.debug("对方禁止别人加他为好友");
                    e = new QQEvent(packet);
                    e.type = QQEvent.FRIEND_ADD_DENY;
                    client.fireQQEvent(e);
                    break;
        	}
        } else if(packet.replyCode == QQ.QQ_REPLY_ADD_FRIEND_ALREADY) {
            log.debug("对方已经是自己的好友");
            e = new QQEvent(packet);
            e.type = QQEvent.FRIEND_ADD_ALREADY;
            client.fireQQEvent(e);
        } else {
            log.debug("添加好友失败");
            e = new QQEvent(packet);
            e.type = QQEvent.FRIEND_ADD_FAIL;
            client.fireQQEvent(e);
        }
    }

    /**
     * 处理系统消息，比如谁谁谁添加你为好友
     * 
     * @param in
     */
    private void processSystemNotification(BasicInPacket in) {
        QQEvent e = null;
        SystemNotificationPacket packet = (SystemNotificationPacket)in;
        // 判断类型
        switch(packet.type) {
        	case QQ.QQ_SYS_BEING_ADDED:
    	        e = new QQEvent(packet);
                e.type = QQEvent.SYS_ADDED_BY_OTHERS;   
                break;
        	case QQ.QQ_SYS_ADD_FRIEND_REQUEST:
                e = new QQEvent(packet);
                e.type = QQEvent.SYS_REQUEST_ADD;
                break;
        	case QQ.QQ_SYS_ADD_FRIEND_APPROVED:
                e = new QQEvent(packet);
                e.type = QQEvent.SYS_APPROVE_ADD;
                break;
        	case QQ.QQ_SYS_ADD_FRIEND_REJECTED:
                e = new QQEvent(packet);
                e.type = QQEvent.SYS_REJECT_ADD;
                break;
        	case QQ.QQ_SYS_BEING_ADDED_EX:
    	        e = new QQEvent(packet);
                e.type = QQEvent.SYS_ADDED_BY_OTHERS_EX;   
                break;
        	case QQ.QQ_SYS_ADD_FRIEND_REQUEST_EX:
            	e = new QQEvent(packet);
            	e.type = QQEvent.SYS_REQUEST_ADD_EX;
            	break;
        	case QQ.QQ_SYS_ADD_FRIEND_APPROVED_AND_ADD:
        		e = new QQEvent(packet);
        		e.type = QQEvent.SYS_APPROVE_ADD_BIDI;
        		break;
            default:
            	log.debug("未知类型的系统通知，忽略");
            	break;
        }
        // 触发事件
        if(e != null)
            client.fireQQEvent(e);
    }

    /**
     * 处理未知命令，没有实际操作，简单打印内容
     * 
     * @param in
     */
    private void processRequestKeyReply(BasicInPacket in) {
        GetKeyReplyPacket packet = (GetKeyReplyPacket)in;
        QQEvent e;
        if(packet.replyCode == QQ.QQ_REPLY_OK) {
	        e = new QQEvent(packet);            
            e.type = QQEvent.USER_REQUEST_KEY_OK;
	        client.fireQQEvent(e);
        } else {
	        e = new QQEvent(policy.retrieveSent(packet));            
            e.type = QQEvent.USER_REQUEST_KEY_FAIL;
	        client.fireQQEvent(e);            
        }
    }

    /**
     * 处理收到的消息
     * 
     * @param in
     */
    private void processReceiveIM(BasicInPacket in) {
        QQEvent e = null;
        if(!(in instanceof ReceiveIMPacket))
            return;
        
        ReceiveIMPacket packet = (ReceiveIMPacket)in;
        if(packet.isDuplicated()) {
            log.debug("收到一条重复消息，发送确认");
            e = new QQEvent(packet);
            e.type = QQEvent.IM_DUPLICATED;
            client.fireQQEvent(e);
            return;            
        }
        
        // 开始处理
        switch(packet.header.type) {
        	case QQ.QQ_RECV_IM_FRIEND:
        	case QQ.QQ_RECV_IM_STRANGER:
        	case QQ.QQ_RECV_IM_FRIEND_EX:
                e = new QQEvent(packet);
            	switch(packet.normalHeader.type) {
            		case QQ.QQ_IM_TYPE_TEXT:
    		            e.type = QQEvent.IM_RECEIVED;
    		            break;
    		        case QQ.QQ_IM_TYPE_UDP_REQUEST:
    	        		log.debug("对方请求传送文件，协议UDP" + " 传送文件端口：" + packet.fileArgs.majorPort);
    	        		e.type = QQEvent.FILE_REQUEST_SEND_TO_ME;
    	        		break;
    	        	case QQ.QQ_IM_TYPE_REJECT_UDP_REQUEST:
    	            	log.debug("对方拒绝了传送文件的请求");
    	            	e.type = QQEvent.FILE_REJECT;
    	            	break;
    	            case QQ.QQ_IM_TYPE_ACCEPT_UDP_REQUEST:
    	            	log.debug("对方接受了传送文件的请求");
    	            	e.type = QQEvent.FILE_ACCEPT;
    	            	break;
    	            case QQ.QQ_IM_TYPE_NOTIFY_IP:
    	            	log.debug("对方告诉我文件发送的IP和端口，其外部IP为" + Util.getIpStringFromBytes(packet.fileArgs.internetIp) +
    	            			" 其外部端口为" + packet.fileArgs.internetPort + " 其主端口为" + packet.fileArgs.majorPort +
    							" 其真实IP为" + Util.getIpStringFromBytes(packet.fileArgs.localIp) +
    							" 其副端口为" + packet.fileArgs.minorPort);
    	            	e.type = QQEvent.FILE_NOTIFY_ARGS;
    	            	break;
    	            case QQ.QQ_IM_TYPE_ARE_YOU_BEHIND_FIREWALL:
    	            	log.debug("对方请求自己主动连接他");
    	            	e.type = QQEvent.FILE_REQUEST_ME_CONNECT;
    	            	break;
    	            case QQ.QQ_IM_TYPE_REQUEST_CANCELED:
    	            	log.debug("对方取消了文件传送");
    	            	e.type = QQEvent.FILE_CANCEL;
    	            	break;
    	            default:
    	        		log.debug("接受到一条非文本消息，目前无法处理，忽略该消息");
    	        		e.type = QQEvent.IM_UNKNOWN_TYPE_RECEIVED;
    	        		break;
            	}
            	break;
        	case QQ.QQ_RECV_IM_SYS_MESSAGE:
                log.debug("接收到一条系统消息: " + packet.sysMessage);
                if(packet.systemMessageType == QQ.QQ_RECV_IM_KICK_OUT) {
                	e = new QQEvent(packet);
                	e.type = QQEvent.SYS_KICKED;
                } else {
    	            e = new QQEvent(packet);	
    	            e.type = QQEvent.SYS_BROADCAST_RECEIVED;          	
                }
                break;
        	case QQ.QQ_RECV_IM_CLUSTER:
            	e = new QQEvent(packet);
            	e.type = QQEvent.IM_CLUSTER_RECEIVED;
            	break;
        	case QQ.QQ_RECV_IM_TEMP_CLUSTER:
            	e = new QQEvent(packet);
            	e.type = QQEvent.IM_TEMP_CLUSTER_RECEIVED;
            	break;
        	case QQ.QQ_RECV_IM_UNKNOWN_CLUSTER:
               	e = new QQEvent(packet);
            	e.type = QQEvent.IM_UNKNOWN_CLUSTER_TYPE_RECEIVED;
            	break;
        	case QQ.QQ_RECV_IM_BIND_USER:
        	case QQ.QQ_RECV_IM_MOBILE_QQ:
        	case QQ.QQ_RECV_IM_MOBILE_QQ_2:
        	case QQ.QQ_RECV_IM_MOBILE:
        		log.debug("收到一条短信: " + packet.sms.message);
        		e = new QQEvent(packet);
        		e.type = QQEvent.SMS_RECEIVED;
        		break;
        	case QQ.QQ_RECV_IM_TEMP_SESSION:
        		log.debug("收到一条临时会话消息: " + packet.tempSessionIM.message);
        		e = new QQEvent(packet);
        		e.type = QQEvent.IM_TEMP_SESSION_RECEIVED;
        		break;
        	case QQ.QQ_RECV_IM_CREATE_CLUSTER:
        	case QQ.QQ_RECV_IM_ADDED_TO_CLUSTER:
            	log.debug("收到群创建通知");
            	e = new QQEvent(packet);
            	e.type = QQEvent.CLUSTER_ADDED_TO;
            	break;
        	case QQ.QQ_RECV_IM_DELETED_FROM_CLUSTER:
            	log.debug("我被创建者从群中删除");
            	e = new QQEvent(packet);
            	e.type = QQEvent.CLUSTER_REMOVED_FROM;
            	break;
        	case QQ.QQ_RECV_IM_APPROVE_JOIN_CLUSTER:
    			log.debug("对方同意我加入群，附加消息为：" + packet.message);
    			e = new QQEvent(packet);
    			e.type = QQEvent.CLUSTER_APPROVE_JOIN;
    			break;
        	case QQ.QQ_RECV_IM_REJECT_JOIN_CLUSTER:
    			log.debug("对方拒绝我加入群，附加消息为：" + packet.message);
    			e = new QQEvent(packet);
    			e.type = QQEvent.CLUSTER_REJECT_JOIN;
    			break;
        	case QQ.QQ_RECV_IM_REQUEST_JOIN_CLUSTER:
    			log.debug("对方请求加入我的群，附加消息为：" + packet.message);
    			e = new QQEvent(packet);
    			e.type = QQEvent.CLUSTER_JOIN;
    			break;
        	case QQ.QQ_RECV_IM_CLUSTER_NOTIFICATION:
        		switch(packet.opCode) {
        			case QQ.QQ_CLUSTER_OP_SET_ADMIN:
        				e = new QQEvent(packet);
        				e.type = QQEvent.CLUSTER_ADMIN_ENTITLED;
        				break;
        			case QQ.QQ_CLUSTER_OP_UNSET_ADMIN:
        				e = new QQEvent(packet);
        				e.type = QQEvent.CLUSTER_ADMIN_WITHDRAWED;
        				break;
        		}
        		break;
        	case QQ.QQ_RECV_IM_SIGNATURE_CHANGE:
        		log.debug("好友" + packet.signatureOwner + "的个性签名改变了");
        		e = new QQEvent(packet);
        		e.type = QQEvent.FRIEND_SIGNATURE_CHANGED;
        		break;
        	case QQ.QQ_RECV_IM_MEMBER_LOGIN_HINT:
        		log.debug("收到会员登录提示");
        		e = new QQEvent(packet);
        		e.type = QQEvent.USER_MEMBER_LOGIN_HINT_RECEIVED;
        		break;
        	case QQ.QQ_RECV_IM_PROPERTY_CHANGE:
        		log.debug("收到好友属性变化通知");
        		e = new QQEvent(packet);
        		e.type = QQEvent.FRIEND_PROPERTY_CHANGED;
        		break;
        	case QQ.QQ_RECV_IM_CUSTOM_HEAD_CHANGE:
        		log.debug("收到好友自定义头像改变通知");
        		e = new QQEvent(packet);
        		e.type = QQEvent.FRIEND_CUSTOM_HEAD_CHANGED;
        		break;
    		default:
                log.debug("接收到一条未知类型消息: " + ((int)packet.header.type) + "，目前无法处理，忽略");
    			break;
        }
        
        // fire event
        if(e != null)
        	client.fireQQEvent(e);
    }

    /**
     * 处理Keep Alive的回复事件
     * 
     * @param in
     */
    private void processKeepAliveReply(BasicInPacket in) {
        QQEvent e;
        KeepAliveReplyPacket packet = (KeepAliveReplyPacket)in;
        if(log.isDebugEnabled()) {
            log.debug("Keep Alive: 在线数: " + packet.onlines + "我的IP: " + Util.convertIpToString(packet.ip));
        }
        
        // 触发事件
        if(packet.replyCode == QQ.QQ_REPLY_OK) {
        	e = new QQEvent(packet);
        	e.type = QQEvent.USER_KEEP_ALIVE_OK;
        	client.fireQQEvent(e);       	
        }
    }

    /**
     * 处理发送消息回复事件
     * 
     * @param in
     */
    private void processSendIMReply(BasicInPacket in) {
        QQEvent e;
        SendIMReplyPacket packet = (SendIMReplyPacket)in;
        if(packet.replyCode == QQ.QQ_REPLY_OK) {
        	SendIMPacket srcPacket = (SendIMPacket)policy.retrieveSent(packet);
        	char messageType = srcPacket.getMessageType();
        	if(messageType == QQ.QQ_IM_TYPE_TEXT) {
	            log.debug("消息发送成功");
	            e = new QQEvent(srcPacket);
	            e.type = QQEvent.IM_SEND_OK;
	            client.fireQQEvent(e);        		
        	} else if(messageType == QQ.QQ_IM_TYPE_UDP_REQUEST) {
        		log.debug("传送文件请求已发送，等待对方接受");
        		e = new QQEvent(srcPacket);
        		e.type = QQEvent.FILE_SEND_REQUEST_OK;
        		client.fireQQEvent(e);
        	} else if(messageType == QQ.QQ_IM_TYPE_NOTIFY_IP) {
        	    log.debug("IP信息已发送，等待对方返回确认");
        	}
        }
    }

    /**
     * 处理得到在线好友回复事件
     * 
     * @param in
     */
    private void processGetFriendOnlineReply(BasicInPacket in) {
        QQEvent e;
        GetOnlineOpReplyPacket packet = (GetOnlineOpReplyPacket)in;
        
        /*if(log.isDebugEnabled()) {
            Iterator iter = packet.onlineFriends.iterator();
            while(iter.hasNext()) {
                FriendStatus fs = ((FriendOnlineEntry)iter.next()).status;
                log.debug("好友" + fs.qqNum + "在线状态" + Utils.getStatusString(fs.status));
            }
        }*/
        
        e = new QQEvent(packet);
        e.type = QQEvent.FRIEND_GET_ONLINE_OK;
        client.fireQQEvent(e);            
    }

    /**
     * 处理得到好友列表回复事件
     * 
     * @param in
     */
    private void processGetFriendListReply(BasicInPacket in) {
        QQEvent e;
        GetFriendListReplyPacket packet = (GetFriendListReplyPacket)in;
        /*if(log.isDebugEnabled()) {
            Iterator iter = packet.friends.iterator();
            while(iter.hasNext()) {
                QQFriend friend = (QQFriend)iter.next();
                log.debug("得到好友" + friend.qqNum + ", 昵称: " + friend.nick);
            }
        }*/
        
        // 触发事件
        e = new QQEvent(packet);
        e.type = QQEvent.FRIEND_GET_LIST_OK;
        client.fireQQEvent(e);            
    }

    /**
     * 处理改变状态回复事件
     * 
     * @param in
     */
    private void processChangeStatusReply(BasicInPacket in) {
        QQEvent e;
        ChangeStatusReplyPacket packet = (ChangeStatusReplyPacket)in;
        
        // 检查应答码
        if(packet.replyCode == QQ.QQ_REPLY_CHANGE_STATUS_OK) {
            log.debug("改变状态成功");
            e = new QQEvent(packet);
            e.type = QQEvent.USER_STATUS_CHANGE_OK;
            client.fireQQEvent(e);
        } else {
        	log.debug("改变状态失败");
            e = new QQEvent(packet);
            e.type = QQEvent.USER_STATUS_CHANGE_FAIL;
            client.fireQQEvent(e);            
        }
    }

    /**
     * 处理得到用户信息回复事件 
     * 
     * @param in
     */
    private void processGetUserInfoReply(BasicInPacket in) {
        QQEvent e;
        GetUserInfoReplyPacket packet = (GetUserInfoReplyPacket)in;
        /*if(log.isDebugEnabled()) {
            for(int i = 0; i < QQ.QQ_CONTACT_FIELDS; i++)
                log.debug("用户信息字段" + i + ": " + packet.contactInfo.infos[i]);
        }*/
        // 触发成功事件
        e = new QQEvent(packet);
        e.type = QQEvent.USER_GET_INFO_OK;
        client.fireQQEvent(e);
    }

	/**
	 * 处理好友状态改变事件
	 * 
	 * @param in
	 */
	private void processFriendChangeStatus(BasicInPacket in) {
		QQEvent e;
		FriendChangeStatusPacket packet = (FriendChangeStatusPacket)in;
		if(packet.myQQ == 0) {
			log.warn("好友状态改变包中数据有误，忽略该包");
		} else {
			// 触发事件
			e = new QQEvent(packet);
			e.type = QQEvent.FRIEND_STATUS_CHANGED;
			client.fireQQEvent(e);
		}
	}
	
	/* (non-Javadoc)
	 * @see edu.tsinghua.lumaqq.qq.events.IPacketListener#accept(edu.tsinghua.lumaqq.qq.packets.InPacket)
	 */
	public boolean accept(InPacket in) {
		return (in.getFamily() & QQ.QQ_PROTOCOL_FAMILY_BASIC) != 0;
	}
}
