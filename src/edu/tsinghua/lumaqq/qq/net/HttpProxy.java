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
package edu.tsinghua.lumaqq.qq.net;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.tsinghua.lumaqq.qq.QQ;
import edu.tsinghua.lumaqq.qq.packets.PacketParseException;

/**
 * <pre>
 * Http代理实现类。如果程序使用HTTP代理，则只能采用TCP方式登录。并且在请求中
 * 需要连接TCP服务器的443端口
 * </pre>
 * 
 * @see RFC 2616
 * @see RFC 2617
 * @author luma
 */
public class HttpProxy extends AbstractProxy {
    /** Log类 */
	private static final Log log = LogFactory.getLog(HttpProxy.class);
	
	/** 回复码 - 成功 */
	public static final String SUCCESS = "200";
	/** 回复码 - 需要验证 */
	public static final String NEED_AUTH = "407";
	
    /** 状态 - 无动作 */
    public static final int STATUS_NONE = 0;
    /** 状态 - 最初请求 */
    public static final int STATUS_INIT = 1;
    /** 状态 - 验证 */
    public static final int STATUS_AUTH = 2;
    /** 状态 - Ready */
    public static final int STATUS_READY = 3;
    
    /** CRLF */
    private static final byte[] CRLF = "\r\n".getBytes();
    /** CONNECT 的前面部分 */
    private static final byte[] CONNECT_BEGIN = "CONNECT ".getBytes();
    /** CONNECT 的后面部分 */
    private static final byte[] CONNECT_END = " HTTP/1.1\r\n".getBytes();
    /** Accept Header */
    private static final byte[] ACCEPT = "Accept: */*\r\n".getBytes();
    /** Content-Type Header */
    private static final byte[] CONTENT_TYPE = "Content-Type: text/html\r\n".getBytes();
    /** Proxy-Connection Header */
    private static final byte[] PROXY_CONNECTION = "Proxy-Connection: Keep-Alive\r\n".getBytes();
    /** Content-lenght Header */
    private static final byte[] CONTENT_LENGTH = "Content-length: 0\r\n".getBytes();
    /** Basic Proxy-Authorization Header */
    private static final byte[] PROXY_AUTHORIZATION = "Proxy-Authorization: Basic ".getBytes();
    
    /** base64 认证参数 */
    private static byte[] authParam;
    
    /** 远程主机地址，这个可能是ip，也可能是域名 */
    protected byte[] remoteAddress;
    
    /**
     * 构造函数
     * 
     * @throws IOException
     */
    public HttpProxy(IProxyHandler handler) throws IOException {
        super(handler);
        status = STATUS_NONE;
    }
    
    /**
     * 构造函数，带验证参数
     * 
     * @param u 用户名
     * @param p 密码
     * @throws IOException
     */
    public HttpProxy(IProxyHandler handler, String u, String p) throws IOException {
        this(handler);
        username = u;
        password = p;
        if(u == null || "".equals(u.trim()) || p == null)
            authParam = null;
        else {
        	Base64 codec = new Base64();
        	authParam = new String(codec.encode((u + ":" + p).getBytes())).getBytes();
        }
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.IProxy#init()
     */
    public void init() {
        log.trace("HttpProxy init");
        // 构造请求连接包
        buffer.clear();       
        buffer.put(CONNECT_BEGIN)
        	.put(remoteAddress)
        	.put(CONNECT_END)
        	.put(ACCEPT)
        	.put(CONTENT_TYPE)
        	.put(PROXY_CONNECTION)
        	.put(CONTENT_LENGTH)
        	.put(CRLF);
        // 发送
        buffer.flip();
        send();
        status = STATUS_INIT;     
    }   

    /**
     * 验证 
     */
    private void auth() {
        log.trace("HttpProxy auth");
        // 构造请求连接包
        buffer.clear();       
        buffer.put(CONNECT_BEGIN)
        	.put(remoteAddress)
        	.put(CONNECT_END)
        	.put(PROXY_AUTHORIZATION)
        	.put(authParam)
        	.put(CRLF)
        	.put(ACCEPT)
        	.put(CONTENT_TYPE)
        	.put(PROXY_CONNECTION)
        	.put(CONTENT_LENGTH)
        	.put(CRLF);
        // 发送
        buffer.flip();
        send();
        status = STATUS_AUTH;   
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.INIOHandler#processRead(java.nio.channels.SelectionKey)
     */
    public void processRead(SelectionKey sk) throws IOException, PacketParseException {
        log.trace("HttpProxy NIOHandler processRead");
        // 接收数据
        receive();
        // 检查头部
        byte[] b = new byte[buffer.limit()];
        buffer.get(b);
        String response = new String(b);
        log.debug(response);
        if(!response.startsWith("HTTP/1."))
            return;
        // 判断状态
        String replyCode = response.substring(9, 12);
        switch(status) {
            case STATUS_INIT:
                if(SUCCESS.equals(replyCode)) {
                    /* 成功 */
                    log.debug("连接成功");
                    status = STATUS_READY;
                    handler.proxyReady(null);
                } else if(NEED_AUTH.equals(replyCode)) {
                    /* 需要验证 */
                    log.debug("需要验证，但是未提供用户名密码");
                    handler.proxyError("Proxy Need Auth");
                } else {
                    log.debug("未知的回复码");
                    handler.proxyError("Unknown Reply Code");
                }
                break;
            case STATUS_AUTH:
                if(SUCCESS.equals(replyCode)) {
                    /* 成功 */
                    log.debug("连接成功");
                    status = STATUS_READY;
                    handler.proxyReady(null);
                } else if(NEED_AUTH.equals(replyCode)) {
                    /* 验证了之后又通知验证，说明验证可能出错 */
                    log.debug("验证出错，可能用户名和密码错误");
                    handler.proxyAuthFail();
                } else {
                    log.debug("未知的回复码");
                    handler.proxyError("Unknown Reply Code");
                }
                break;
            default:
                break;
        }
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.INIOHandler#processWrite()
     */
    public void processWrite() throws IOException {
        log.trace("HttpProxy processWrite");
        if(connected) {
            if(authParam == null)
                init();
            else
                auth();
        }
    }
    
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.IProxy#setServerAddress(java.net.InetSocketAddress)
     */
    @Override
	public void setRemoteAddress(InetSocketAddress serverAddress) {
        super.setRemoteAddress(serverAddress);
        String s = serverAddress.getHostName() + ':' + QQ.QQ_PORT_HTTP;
        this.remoteAddress = s.getBytes();
    }
}
