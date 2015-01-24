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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.tsinghua.lumaqq.qq.packets.OutPacket;

/**
 * 管理所有IPort对象，一个QQClient一个Porter，一个Porter上面可以有
 * 多个IPort，PortGate用来集中管理这些IPort。一个Port代表了一个连接，
 * 这个连接可能被多个地方使用，所以port有一个引用计数，计数为0时才真正释放
 * port
 * 
 * @author luma
 */
public class PortGate implements IConnectionPool {
	// IPort映射器，key是名称，value是IPort对象
	private Map<String, IPort> registry;
	// 引用计数
	private Map<IConnection, Integer> references;
	// Porter
	private Porter porter;
	
	/**
	 * 构造函数
	 */
	public PortGate() {
		porter = new Porter();
		registry = new HashMap<String, IPort>();
		references = new HashMap<IConnection, Integer>();
	}
	
	public void start() {
		porter.start();
	}
	
	public synchronized List<IConnection> getConnections() {
		List<IConnection> ret = new ArrayList<IConnection>();
		ret.addAll(registry.values());
		return ret;
	}
	
	public synchronized boolean hasConnection(String id) {
		return registry.containsKey(id);
	}
	
	public synchronized void release(IConnection con) {
		if(con == null)
			return;
		
		Integer reference = references.get(con);
		if(reference == null)
			return;
		reference--;
		if(reference <= 0) {
			references.remove(con);
			registry.remove(con.getId());
			porter.addDisposeRequest((IPort)con);
		} else
			references.put(con, reference);
	}
	
	public synchronized void release(String id) {
		IConnection con = getConnection(id);
		release(con);
	}
	
	public synchronized IConnection getConnection(String id) {
		return registry.get(id);
	}
	
	/**
	 * 检查是否存在一个到指定远程地址的连接
	 * 
	 * @param address
	 * @return
	 */
	public synchronized IConnection getConnection(InetSocketAddress address) {
		for(IConnection port : registry.values()) {
	        if(port.getRemoteAddress().equals(address))
	            return port;
		}
	    return null;
	}
	
	/**
	 * 增加连接的引用计数
	 * 
	 * @param id
	 */
	private synchronized void increaseReference(IConnection con) {
		Integer i = references.get(con);
		if(i != null) {
			i++;
			references.put(con, i);			
		}
	}

	/**
	 * 使用指定的port发送一个包
	 * 
	 * @param name
	 * 		port name
	 * @param packet
	 * 		OutPacket子类
	 * @param keepSent
	 * 		true表示保存发出的包，这种需要是因为有些协议的返回包没有什么可用信息，需要使用发出包来
	 *      触发事件
	 * @return
	 * 		true表示包发送成功，false表示失败
	 */
	public void send(String id, OutPacket packet, boolean keepSent) {
		IConnection port = getConnection(id);
		if(port != null) {
		    if(keepSent)
		        port.getPolicy().putSent(packet);
			port.add(packet);
		} 
	}

	public synchronized IConnection newUDPConnection(IConnectionPolicy policy, InetSocketAddress server, boolean start) {
        if(hasConnection(policy.getConnectionId())) {
        	IConnection con = getConnection(policy.getConnectionId());
        	increaseReference(con);
        }
        
        UDPPort port;
		try {
			port = new UDPPort(policy, server);
		} catch(IOException e) {
			return null;
		}
		port.setPool(this);
        registry.put(policy.getConnectionId(), port);
        references.put(port, 1);
        porter.wakeup(port);
        if(start)
            port.start();
        return port;
	}

	public synchronized IConnection newUDPSocks5Connection(IConnectionPolicy policy, InetSocketAddress server, boolean start) {
        if(hasConnection(policy.getConnectionId())) {
        	IConnection con = getConnection(policy.getConnectionId());
        	increaseReference(con);
        }
        
        UDPSocks5Port port;
		try {
			port = new UDPSocks5Port(policy, server);
		} catch(IOException e) {
			return null;
		}
		port.setPool(this);
        registry.put(policy.getConnectionId(), port);
        references.put(port, 1);
        porter.wakeup(port);
        if(start)
            port.start();
        return port;
	}

	public synchronized IConnection newTCPConnection(IConnectionPolicy policy, InetSocketAddress server, boolean start) {
        if(hasConnection(policy.getConnectionId())) {
        	IConnection con = getConnection(policy.getConnectionId());
        	increaseReference(con);
        }
        
        TCPPort port;
		try {
			port = new TCPPort(policy, server);
		} catch(IOException e) {
			return null;
		}
		port.setPool(this);
        registry.put(policy.getConnectionId(), port);
        references.put(port, 1);
        porter.wakeup(port);
        if(start)
            port.start();
        return port;
	}

	public synchronized IConnection newTCPSocks5Connection(IConnectionPolicy policy, InetSocketAddress server, boolean start) {
        if(hasConnection(policy.getConnectionId())) {
        	IConnection con = getConnection(policy.getConnectionId());
        	increaseReference(con);
        }
        
        TCPSocks5Port port;
		try {
			port = new TCPSocks5Port(policy, server);
		} catch(IOException e) {
			return null;
		}
		port.setPool(this);
        registry.put(policy.getConnectionId(), port);
        references.put(port, 1);
        porter.wakeup(port);
        if(start)
            port.start();
        return port;
	}

	public synchronized IConnection newTCPHttpConnection(IConnectionPolicy policy, InetSocketAddress server, boolean start) {
        if(hasConnection(policy.getConnectionId())) {
        	IConnection con = getConnection(policy.getConnectionId());
        	increaseReference(con);
        }
        
        TCPHttpPort port;
		try {
			port = new TCPHttpPort(policy, server);
		} catch(IOException e) {
			return null;
		}
		port.setPool(this);
        registry.put(policy.getConnectionId(), port);
        references.put(port, 1);        
        porter.wakeup(port);
        if(start)
            port.start();
        return port;
	}

	/* (non-Javadoc)
	 * @see edu.tsinghua.lumaqq.qq.net.IConnectionPool#dispose()
	 */
	public synchronized void dispose() {
		new Thread() {
			@Override
			public void run() {
				porter.shutdown();
				registry.clear();
				references.clear();
				porter = null;
				registry = null;
				references = null;
			}
		}.start();
	}

	public void flush() {
		porter.wakeup();
	}

	/**
	 * @return the porter
	 */
	public Porter getPorter() {
		return porter;
	}

	public boolean verifyProxy(IProxyHandler handler, InetSocketAddress proxyAddress, InetSocketAddress serverAddress, String type, boolean udp, String user, String pass) {
		ProxyVerifier verifier = null;
		try {
			verifier = new ProxyVerifier(handler, proxyAddress, serverAddress, type, udp, user, pass);
			verifier.start();
			return true;
		} catch(IOException e) {
			if(verifier != null)
				verifier.dispose();
			return false;
		}
	}
}
