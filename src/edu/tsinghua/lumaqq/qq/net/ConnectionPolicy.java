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

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;

import edu.tsinghua.lumaqq.qq.QQClient;
import edu.tsinghua.lumaqq.qq.packets.ErrorPacket;
import edu.tsinghua.lumaqq.qq.packets.InPacket;
import edu.tsinghua.lumaqq.qq.packets.OutPacket;
import edu.tsinghua.lumaqq.qq.packets.PacketHelper;
import edu.tsinghua.lumaqq.qq.packets.PacketParseException;

/**
 * 缺省的连接策略实现
 *
 * @author luma
 */
public class ConnectionPolicy implements IConnectionPolicy {
	QQClient client;
	int supportedFamily;
	int relocateFamily;
	PacketHelper helper;
	String id;
	private InetSocketAddress proxy;
	private String proxyUsername;
	private String proxyPassword;
	
	public ConnectionPolicy(QQClient client, String id, int supportedFamily, int relocateFamily) {
		this.client = client;
		this.id = id;
		this.supportedFamily = supportedFamily;
		this.relocateFamily = relocateFamily;
		helper = new PacketHelper(supportedFamily);
	}
	
	public boolean relocate(ByteBuffer buf) {
		return helper.relocate(relocateFamily, buf);
	}
	
	public ErrorPacket createErrorPacket(int errorCode, String portName) {
	    ErrorPacket packet = new ErrorPacket(errorCode, client.getUser());
	    packet.setFamily(supportedFamily);
	    packet.connectionId = portName;
	    return packet;
	}

	public void pushResend(OutPacket out, String portName) {
		client.addResendPacket(out, portName);
	}
	
	public void flush() {
		client.getConnectionPool().flush();
	}
	
	public void pushIn(InPacket in) {
		client.addIncomingPacket(in, id);
	}
	
	public InPacket parseIn(ByteBuffer buf, boolean debug) throws PacketParseException {
		return helper.parseIn(supportedFamily, buf, client.getUser(), debug);
	}
	
	public void putSent(OutPacket packet) {
		helper.putSent(packet);
	}
	
	public boolean isReplied(OutPacket packet, boolean add) {
		return helper.isReplied(packet, add);
	}
	
	public OutPacket retrieveSent(InPacket in) {
		return helper.retrieveSent(in);
	}

	public int getSupportedFamily() {
		return supportedFamily;
	}

	public int getRelocateFamily() {
		return relocateFamily;
	}

	public String getConnectionId() {
		return id;
	}

	public InetSocketAddress getProxy() {
		return proxy;
	}

	public String getProxyUsername() {
		return proxyUsername;
	}

	public String getProxyPassword() {
		return proxyPassword;
	}

	/**
	 * @param proxy the proxy to set
	 */
	public void setProxy(InetSocketAddress proxy) {
		this.proxy = proxy;
	}

	/**
	 * @param proxyPassword the proxyPassword to set
	 */
	public void setProxyPassword(String proxyPassword) {
		this.proxyPassword = proxyPassword;
	}

	/**
	 * @param proxyUsername the proxyUsername to set
	 */
	public void setProxyUsername(String proxyUsername) {
		this.proxyUsername = proxyUsername;
	}
}
