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
package edu.tsinghua.lumaqq.qq.beans;

import static org.apache.commons.codec.digest.DigestUtils.*;
import edu.tsinghua.lumaqq.qq.QQ;
import edu.tsinghua.lumaqq.qq.Util;


/**
 * 封装QQ用户的信息
 *
 * @author luma
 */
public class QQUser {
	// Double MD5处理的用户密码
    private byte[] md5pwd;
    // 初始密钥
    private byte[] iniKey;
    // 请求登录令牌时的随机密钥
    private byte[] loginTokenRandomKey;
    // 登录令牌
    private byte[] loginToken;
    // 会话密钥
    private byte[] sessionKey;
    // 文件传输会话密钥
    private byte[] fileSessionKey;
    // 文件中转服务器通讯密钥，来自0x001D - 0x4
    private byte[] fileAgentKey;
    // 客户端key
    private byte[] clientKey;
    // 未知用途密钥，来自0x001D
    private byte[] unknown03Key;
    private byte[] unknown06Key;
    private byte[] unknown07Key;
    private byte[] unknown08Key;
    // 文件中转认证令牌
    private byte[] fileAgentToken;
    // 未知令牌
    private byte[] unknown03Token;
    private byte[] unknown06Token;
    private byte[] unknown07Token;
    private byte[] unknown08Token;
    // 认证令牌
    private byte[] authToken;
    // QQ号
    private int qq;
    // 本地IP
    private byte[] ip;
    // 本地端口，在QQ中其实只有两字节
    private int port;
    // 服务器IP
    private byte[] serverIp;
    // 服务器端口，在QQ中其实只有两字节
    private int serverPort;
    // 本次登陆时间
    private long loginTime;
    // 上一次登陆IP
    private byte[] lastLoginIp;
    // 上一次登陆时间，在QQ中其实只有4字节
    private long lastLoginTime;
    // 当前登陆状态，为true表示已经登陆
    private boolean loggedIn;
    // 登陆模式，隐身还是非隐身
    private byte loginMode;
    // 设置登陆服务器的方式是UDP还是TCP
    private boolean udp;
    // 当前的状态，比如在线，隐身等等
    private byte status;
    // ContactInfo
    private ContactInfo contactInfo;
    
    // 是否显示虚拟摄像头
    private boolean showFakeCam;
    
    /**
     * @param qq QQ号
     * @param pwd 密码
     */
    public QQUser(int qqNum, String pwd) {
        this.qq = qqNum;
        setPassword(pwd);
        ip = new byte[4];
        serverIp = new byte[4];
        lastLoginIp = new byte[4];
        loggedIn = false;
        loginMode = QQ.QQ_LOGIN_MODE_NORMAL;
        udp = true;
        contactInfo = new ContactInfo();
        showFakeCam = false;
        iniKey = Util.randomKey();
        loginTokenRandomKey = Util.randomKey();
    }
    
    /**
     * @param qq QQ号
     * @param md5pwd 经过Double MD5的密码字节数组
     */
    public QQUser(int qqNum, byte[] md5pwd) {
        this.qq = qqNum;
        this.md5pwd = md5pwd;
        ip = new byte[4];
        serverIp = new byte[4];
        lastLoginIp = new byte[4];
        loggedIn = false;
        loginMode = QQ.QQ_LOGIN_MODE_NORMAL;
        udp = true;
        contactInfo = new ContactInfo();
        showFakeCam = false;
        iniKey = Util.randomKey();
        loginTokenRandomKey = Util.randomKey();
    }
    
    /**
     * @return Returns the qq.
     */
    public int getQQ() {
        return qq;
    }
    
    /**
     * @param qq The qq to set.
     */
    public void setQQ(int qqNum) {
        this.qq = qqNum;
    }
    
    /**
     * @return Returns the sessionKey.
     */
    public byte[] getSessionKey() {
        return sessionKey;
    }
    
    /**
     * @param sessionKey The sessionKey to set.
     */
    public void setSessionKey(byte[] sessionKey) {
        this.sessionKey = sessionKey;
    }
    
    /**
     * 设置用户的密码，不会保存明文形式的密码，立刻用Double MD5算法加密
     * @param pwd 明文形式的密码
     */
    public void setPassword(String pwd) {
        md5pwd = md5(md5(pwd.getBytes()));
    }
    
    /**
     * @return 被Double MD5处理过的密码
     */
    public byte[] getPasswordKey() {
        return md5pwd;
    }
    
    /**
     * @return Returns the ip.
     */
    public byte[] getIp() {
        return ip;
    }
    
    /**
     * @param ip The ip to set.
     */
    public void setIp(byte[] ip) {
        this.ip = ip;
    }
    
    /**
     * @return Returns the lastLoginIp.
     */
    public byte[] getLastLoginIp() {
        return lastLoginIp;
    }
    
    /**
     * @param lastLoginIp The lastLoginIp to set.
     */
    public void setLastLoginIp(byte[] lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }
    
    /**
     * @return Returns the port.
     */
    public int getPort() {
        return port;
    }
    
    /**
     * @param port The port to set.
     */
    public void setPort(int port) {
        this.port = port;
    }
    
    /**
     * @return Returns the serverIp.
     */
    public byte[] getServerIp() {
        return serverIp;
    }
    
    /**
     * @param serverIp The serverIp to set.
     */
    public void setServerIp(byte[] serverIp) {
        this.serverIp = serverIp;
    }
    
    /**
     * @return Returns the serverPort.
     */
    public int getServerPort() {
        return serverPort;
    }
    
    /**
     * @param serverPort The serverPort to set.
     */
    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }
    
	/**
	 * @return Returns the lastLoginTime.
	 */
	public long getLastLoginTime() {
		return lastLoginTime;
	}
	
	/**
	 * @param lastLoginTime The lastLoginTime to set.
	 */
	public void setLastLoginTime(long lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	
	/**
	 * @return Returns the loginTime.
	 */
	public long getLoginTime() {
		return loginTime;
	}
	
	/**
	 * @param loginTime The loginTime to set.
	 */
	public void setLoginTime(long loginTime) {
		this.loginTime = loginTime;
	}    
	
	/**
	 * @return Returns the loggedIn.
	 */
	public boolean isLoggedIn() {
		return loggedIn;
	}
	
	/**
	 * @param loggedIn The loggedIn to set.
	 */
	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}	

    /**
     * @param loginMode
     *            The loginMode to set.
     */
    public void setLoginMode(byte loginMode) {
        this.loginMode = loginMode;
    }    

    /**
     * @return Returns the loginMode.
     */
    public byte getLoginMode() {
        return loginMode;
    }

	/**
	 * @return Returns the udp.
	 */
	public boolean isUdp() {
		return udp;
	}
	
	/**
	 * @param udp The udp to set.
	 */
	public void setUdp(boolean udp) {
		this.udp = udp;
	}
	
	/**
	 * @return Returns the status.
	 */
	public byte getStatus() {
		return status;
	}
	
	/**
	 * @param status The status to set.
	 */
	public void setStatus(byte status) {
		this.status = status;
	}
	
    /**
     * @return Returns the contactInfo.
     */
    public ContactInfo getContactInfo() {
        return contactInfo;
    }
    
    /**
     * @param contactInfo The contactInfo to set.
     */
    public void setContactInfo(ContactInfo contactInfo) {
        this.contactInfo = contactInfo;
    }
    
	/**
	 * @return Returns the fileKey.
	 */
	public byte[] getFileSessionKey() {
		return fileSessionKey;
	}
	
	/**
	 * @param fileKey The fileKey to set.
	 */
	public void setFileSessionKey(byte[] fileKey) {
		this.fileSessionKey = fileKey;
	}
	
    /**
     * @return Returns the fileAgentKey.
     */
    public byte[] getFileAgentKey() {
        return fileAgentKey;
    }
    
    /**
     * @param fileAgentKey The fileAgentKey to set.
     */
    public void setFileAgentKey(byte[] fileAgentKey) {
        this.fileAgentKey = fileAgentKey;
    }
    
    /**
     * @return Returns the unknownKey.
     */
    public byte[] getUnknown03Key() {
        return unknown03Key;
    }
    
    /**
     * @param unknownKey The unknownKey to set.
     */
    public void setUnknown03Key(byte[] unknownKey) {
        this.unknown03Key = unknownKey;
    }
    
    /**
     * @return Returns the fileAgentToken.
     */
    public byte[] getFileAgentToken() {
        return fileAgentToken;
    }
    
    /**
     * @param fileAgentToken The fileAgentToken to set.
     */
    public void setFileAgentToken(byte[] fileAgentToken) {
        this.fileAgentToken = fileAgentToken;
    }
    
    /**
     * @return Returns the unknownToken.
     */
    public byte[] getUnknown03Token() {
        return unknown03Token;
    }
    
    /**
     * @param unknownToken The unknownToken to set.
     */
    public void setUnknown03Token(byte[] unknownToken) {
        this.unknown03Token = unknownToken;
    }
    
    /**
     * @return Returns the showFakeCam.
     */
    public boolean isShowFakeCam() {
        return showFakeCam;
    }
    
    /**
     * @param showFakeCam The showFakeCam to set.
     */
    public void setShowFakeCam(boolean showFakeCam) {
        this.showFakeCam = showFakeCam;
    }
    
    /**
     * @return
     * 		client key
     */
    public byte[] getClientKey() {
        return clientKey;
    }
    
    /**
     * @param clientKey
     * 		client key
     */
    public void setClientKey(byte[] clientKey) {
        this.clientKey = clientKey;
    }
    
    /**
     * @return
     * 		初始密钥
     */
    public byte[] getInitKey() {
        return iniKey;
    }
    
    /**
     * @return Returns the loginToken.
     */
    public byte[] getLoginToken() {
        return loginToken;
    }
    
    /**
     * @param loginToken The loginToken to set.
     */
    public void setLoginToken(byte[] loginToken) {
        this.loginToken = loginToken;
    }

	/**
	 * @return the unknown06Key
	 */
	public byte[] getUnknown06Key() {
		return unknown06Key;
	}

	/**
	 * @param unknown06Key the unknown06Key to set
	 */
	public void setUnknown06Key(byte[] unknown06Key) {
		this.unknown06Key = unknown06Key;
	}

	/**
	 * @return the unknown07Key
	 */
	public byte[] getUnknown07Key() {
		return unknown07Key;
	}

	/**
	 * @param unknown07Key the unknown07Key to set
	 */
	public void setUnknown07Key(byte[] unknown07Key) {
		this.unknown07Key = unknown07Key;
	}

	/**
	 * @return the unknown08Key
	 */
	public byte[] getUnknown08Key() {
		return unknown08Key;
	}

	/**
	 * @param unknown08Key the unknown08Key to set
	 */
	public void setUnknown08Key(byte[] unknown08Key) {
		this.unknown08Key = unknown08Key;
	}

	/**
	 * @return the unknown06Token
	 */
	public byte[] getUnknown06Token() {
		return unknown06Token;
	}

	/**
	 * @param unknown06Token the unknown06Token to set
	 */
	public void setUnknown06Token(byte[] unknown06Token) {
		this.unknown06Token = unknown06Token;
	}

	/**
	 * @return the unknown07Token
	 */
	public byte[] getUnknown07Token() {
		return unknown07Token;
	}

	/**
	 * @param unknown07Token the unknown07Token to set
	 */
	public void setUnknown07Token(byte[] unknown07Token) {
		this.unknown07Token = unknown07Token;
	}

	/**
	 * @return the unknown08Token
	 */
	public byte[] getUnknown08Token() {
		return unknown08Token;
	}

	/**
	 * @param unknown08Token the unknown08Token to set
	 */
	public void setUnknown08Token(byte[] unknown08Token) {
		this.unknown08Token = unknown08Token;
	}

	/**
	 * @return the authToken
	 */
	public byte[] getAuthToken() {
		return authToken;
	}

	/**
	 * @param authToken the authToken to set
	 */
	public void setAuthToken(byte[] authToken) {
		this.authToken = authToken;
	}

	public byte[] getLoginTokenRandomKey() {
		return loginTokenRandomKey;
	}
}
