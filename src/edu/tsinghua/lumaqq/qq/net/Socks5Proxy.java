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
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.tsinghua.lumaqq.qq.Util;
import edu.tsinghua.lumaqq.qq.packets.PacketParseException;


/**
 * <pre>
 * Socks5 代理服务器的处理类。
 * </pre>
 * 
 * @see RFC 1928
 * @see RFC 1929
 * @author luma
 */
public class Socks5Proxy extends AbstractProxy {
    /** Log类 */
	private static final Log log = LogFactory.getLog(Socks5Proxy.class);
    /** Socks版本号 */
    public static final byte VER_5 = 0x5;
    /** 验证类型 - 不需验证 */
    public static final byte AUTH_NONE = 0x0;
    /** 验证类型 - 用户名/密码 */
    public static final byte AUTH_USERNAME_PASSWORD = 0x2;
    /** 请求类型 - 连接 */
    public static final byte REQ_CONNECT = 0x1;
    /** 请求类型 - UDP绑定 */
    public static final byte REQ_UDP_ASSOCIATE = 0x3;
    /** 地址类型 - ipv4 */
    public static final byte ATYP_IPV4 = 0x1;
    /** 地址类型 - 域名 */
    public static final byte ATYP_DOMAIN_NAME = 0x3;
    /** 地址类型 - IPV6 */
    public static final byte ATYP_IPV6 = 0x4;
    /** 回复码 - 成功 */
    public static final byte REPLY_SUCCESS = 0x0;
    
    /** 状态 - 无动作 */
    public static final int STATUS_NONE = 0;
    /** 状态 - 最初请求 */
    public static final int STATUS_INIT = 1;
    /** 状态 - 验证 */
    public static final int STATUS_AUTH = 2;
    /** 状态 - CONNECT */
    public static final int STATUS_CONNECT = 3;
    /** 状态 - UDP绑定 */
    public static final int STATUS_UDP_ASSOCIATE = 4;
    /** 状态 - Ready */
    public static final int STATUS_READY = 5;

    /** 远程主机地址字节数组，这个数组里面可能是ip，也可能是域名 */
    protected byte[] remoteAddress;
    /** 远程主机端口 */
    protected int remotePort;
    /** 地址是IP还是域名 */
    protected boolean isIp;
    /**是否需要验证？
     * socks5 协议比较诡异，
     * 还是根据是否传入了用户名和密码来决定是否验证
     * 如果数据发送的是05 02 00 02
     * 代理服务器返回的就会是05 02
     * 然后就会出错
     */
    protected boolean NeedVerify;

    /**
     * 构造函数
     * 
     * @param handler
     * @throws IOException
     */
    public Socks5Proxy(IProxyHandler handler, String u, String p, DatagramChannel channel) throws IOException {
        super(handler, channel);
        status = STATUS_NONE;
        NeedVerify = false;
        if(u != null){
            NeedVerify = true;
            username = u;
        }
        if(p != null)
            password = p;
    }
    
    /**
     * 构造函数，带验证参数
     * 
     * @param handler
     * @param u 用户名
     * @param p 密码
     * @throws IOException
     */
    public Socks5Proxy(IProxyHandler handler, String u, String p) throws IOException {
        super(handler);
        status = STATUS_NONE;
        NeedVerify = false;
        if(u != null){
            NeedVerify = true;
            username = u;
        }
        if(p != null)
            password = p;
    }

    /**
     * 发送初始请求包，其格式为：
     * +----+----------+----------+
     * |VER | NMETHODS | METHODS  |
     * +----+----------+----------+
     * | 1  |    1     | 1 to 255 |
     * +----+----------+----------+
     * 
     * @see edu.tsinghua.lumaqq.qq.net.IProxy#init()
     */
    public void init() {
        log.trace("Socks5 init");
        // 构造初始请求包
        buffer.clear();
        buffer.put(VER_5)
        	.put((byte)0x1)
        	.put(NeedVerify?AUTH_USERNAME_PASSWORD:AUTH_NONE);
        // 发送
        buffer.flip();
        send();
        status = STATUS_INIT;           
    }
    
    /**
     * 发送验证包
     * Socks5 验证包的格式是
     * +----+------+----------+------+----------+
     * |VER | ULEN |  UNAME   | PLEN |  PASSWD  |
     * +----+------+----------+------+----------+
     * | 1  |  1   | 1 to 255 |  1   | 1 to 255 |
     * +----+------+----------+------+----------+
     */
    protected void auth() {
        log.trace("Socks5 auth");
        // 发送验证包，如果用户名密码为null，则置为空
        buffer.clear();
        buffer.put(VER_5)
        	.put((byte)(username.length() & 0xFF))
        	.put(username.getBytes())
        	.put((byte)(password.length() & 0xFF))
        	.put(password.getBytes());
        // 发送
        buffer.flip();
        send();
        status = STATUS_AUTH;            
    }
    
    /**
     * 连接一个远程主机，Socks命令的格式为
     * +----+-----+-------+------+----------+----------+
     * |VER | CMD |  RSV  | ATYP | DST.ADDR | DST.PORT |
     * +----+-----+-------+------+----------+----------+
     * | 1  |  1  | X'00' |  1   | Variable |    2     |
     * +----+-----+-------+------+----------+----------+
     */
    protected void connect() {
        log.trace("Socks5 connect");
        // 构造请求包
        buffer.clear();
        buffer.put(VER_5)
        	.put(REQ_CONNECT)
        	.put((byte)0x0)
        	.put(isIp ? ATYP_IPV4 : ATYP_DOMAIN_NAME)
        	.put(remoteAddress)
        	.putChar((char)remotePort);
        // 发送
        buffer.flip();
        send();
        status = STATUS_CONNECT;            
    }

    /**
     * 发送UDP绑定命令，命令格式为
     * +----+-----+-------+------+----------+----------+
     * |VER | CMD |  RSV  | ATYP | DST.ADDR | DST.PORT |
     * +----+-----+-------+------+----------+----------+
     * | 1  |  1  | X'00' |  1   | Variable |    2     |
     * +----+-----+-------+------+----------+----------+
     */
    protected void associate() {
        log.trace("Socks5 associate");
        // 构造请求包
        buffer.clear();
        buffer.put(VER_5)
        	.put(REQ_UDP_ASSOCIATE)
        	.put((byte)0x0)
        	.put(ATYP_IPV4)
        	.putInt(0)
        	.putChar((char)clientPort);
        // 发送
        buffer.flip();
        send();
        status = STATUS_UDP_ASSOCIATE;            
    }    

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.IHandler#processRead(java.nio.channels.SelectionKey)
     */
    public void processRead(SelectionKey sk) throws IOException, PacketParseException {
        // 接收数据
        receive();
        // 检查数据长度
        if(!buffer.hasRemaining()) return;
        // 检查版本号，如果版本号不是5，返回
        if(buffer.get(0) != VER_5)
            return;
        // 根据当前状态做出反应
        switch(status) {
            case STATUS_INIT:
                /* 当前正在初始化，如果服务器需要认证，就认证，不需要认证，就可以连了 */
                if(buffer.get(1) == AUTH_NONE) {
                    log.debug("代理服务器连接成功，不需要认证");
                    if(udp)
                        associate();
                    else
                        connect();
                } else if(buffer.get(1) == AUTH_USERNAME_PASSWORD) {
                    log.debug("代理服务器连接成功，需要认证");
                    auth();
                } else {
                    log.debug("不支持的验证方法");
                    handler.proxyAuthFail();
                }
                break;
            case STATUS_AUTH:
                /* 如果验证通过了，就继续连接；否则返回 */
                if(buffer.get(1) == REPLY_SUCCESS) {
                    log.debug("验证通过");
                    if(udp)
                        associate();
                    else
                        connect();                    
                } else {
                    log.debug("验证失败");
                    handler.proxyAuthFail();
                }
                break;
            case STATUS_CONNECT:
                /* 如果连接成功了，置当前状态为ready */
                if(buffer.get(1) == REPLY_SUCCESS)    {      
                    log.debug("CONNECT命令完成");
                    status = STATUS_READY;
                    handler.proxyReady(null);
                } else {
                    /* 命令失败 */
                    log.debug("代理服务器连接失败，代理不可用");
                    handler.proxyError("代理服务器连接失败，代理不可用");
                }
                break;
            case STATUS_UDP_ASSOCIATE:
                /*
                 * 如果连接成功了，置当前状态为ready。目前我们直接连接bind address，但是
                 * 实际上，这个bind address可能不是Socks5代理的外部IP，因为可能连接不上。
                 * 对于这种情况，没有处理。拒猜测，QQ会首先发出0x0031命令来试探这个bind 
                 * address是否可用，如果不可用，它会自动切换到TCP登录方式 
                 */
                if(buffer.get(1) == REPLY_SUCCESS) {
                    // 得到地址类型
                    byte addressType = buffer.get(3);                    
                    // 得到bind address的字节数组
                    byte[] a = new byte[buffer.limit() - 6];
                    buffer.position(4);
                    buffer.get(a);
                    // 得到bind port
                    int p = buffer.getChar();
                    // 根据地址类型转换成字符串
                    String addressStr = null;
                    if(addressType == ATYP_IPV4)
                        addressStr = Util.getIpStringFromBytes(a);
                    else if(addressType == ATYP_DOMAIN_NAME)
                        addressStr = new String(a);
                    else {
                        log.debug("不支持IPV6");
                        return;
                    }
                    // 生成地址
                    InetSocketAddress address = new InetSocketAddress(addressStr, p);
                    // log
                    log.debug("UDP绑定已完成，BND.ADDR: " + addressStr + " BND.PORT: " + p);
                    // 设置状态为已准备好
                    status = STATUS_READY;    
                    // 对于UDP Socks5，代理的事件不再有用处，注销
                    sk.cancel();
                    // 回调
                    handler.proxyReady(address);
                } else  {
                    /* 命令失败 */
                    log.debug("代理服务器连接失败，代理不可用");
                    handler.proxyError("代理服务器连接失败，代理不可用");
                }
                break;
            default:
                break;
        }
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.IHandler#processWrite()
     */
    public void processWrite() throws IOException {      
        log.trace("Socks5Proxy processWrite");
        if(connected && status == STATUS_NONE)
            init();
    }
        
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.IProxy#setServerAddress(java.net.InetSocketAddress)
     */
    @Override
	public void setRemoteAddress(InetSocketAddress serverAddress) {
        super.setRemoteAddress(serverAddress);
		// 解析远程主机的地址和端口
        if(serverAddress.isUnresolved()) {
            /* 没有解析，是域名 */
            isIp = false;
		    byte[] b = serverAddress.getHostName().getBytes();
		    remoteAddress = new byte[b.length + 1];
		    remoteAddress[0] = (byte)(b.length & 0xFF);
		    System.arraycopy(b, 0, remoteAddress, 1, b.length);
        } else {
            /* 已经解析，可以得到IP。有的Socks5服务器的实现可能不支持域名，能得到ip就用ip */
            isIp = true;
            remoteAddress = serverAddress.getAddress().getAddress();            
        }
		remotePort = serverAddress.getPort();
    }
    
    /**
     * @param clientPort The clientPort to set.
     */
    @Override
	public void setClientPort(int clientPort) {
        this.clientPort = clientPort;
    }
}
