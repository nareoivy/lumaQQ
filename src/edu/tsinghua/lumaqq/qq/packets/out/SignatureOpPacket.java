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
import java.util.List;

import edu.tsinghua.lumaqq.qq.QQ;
import edu.tsinghua.lumaqq.qq.Util;
import edu.tsinghua.lumaqq.qq.beans.QQUser;
import edu.tsinghua.lumaqq.qq.beans.Signature;
import edu.tsinghua.lumaqq.qq.packets.BasicOutPacket;
import edu.tsinghua.lumaqq.qq.packets.PacketParseException;

/**
 * <pre>
 * 个性签名操作请求包
 * 1. 头部
 * 2. 子命令，1字节
 * 
 * 根据2部分的不同
 * 为0x01时：
 * 3. 未知1字节
 * 4. 个性签名的字节长度，1字节
 * 5. 个性签名
 * 6. 尾部
 * 
 * 为0x00时，无后续内容
 * 3. 尾部
 * 
 * 为0x02时
 * 3. 未知的1字节
 * 4. 需要得到个性签名的QQ号数量，1字节
 * 5. QQ号，4字节
 * 6. 本地的个性签名修改时间，4字节
 * 7. 如果有更多QQ号，重复5-6部分
 * 8. 尾部 
 * 
 * 在得到好友的个性签名时，QQ的做法是对所有的QQ号排个序，每次最多请求33个。
 * </pre>
 * 
 * @author luma
 */
public class SignatureOpPacket extends BasicOutPacket {
	private byte subCommand;
	private String signature;
	private List<Signature> signatures;
	
	public SignatureOpPacket(QQUser user) {
		super(QQ.QQ_CMD_SIGNATURE_OP, true, user);
		subCommand = QQ.QQ_SUB_CMD_MODIFY_SIGNATURE;
		signature = QQ.EMPTY_STRING;
	}

	public SignatureOpPacket(ByteBuffer buf, int length, QQUser user) throws PacketParseException {
		super(buf, length, user);
	}

	@Override
	public String getPacketName() {
		return "Signature Op Packet";
	}
	
	@Override
	protected void putBody(ByteBuffer buf) {
		buf.put(subCommand);
		switch(subCommand) {
			case QQ.QQ_SUB_CMD_MODIFY_SIGNATURE:
				buf.put((byte)0x01);
				byte[] b = Util.getBytes(signature);
				buf.put((byte)b.length);
				buf.put(b);		
				break;
			case QQ.QQ_SUB_CMD_GET_SIGNATURE:
				buf.put((byte)0);
				buf.put((byte)signatures.size());
				for(Signature sig : signatures) {
					buf.putInt(sig.qq);
					buf.putInt(sig.modifiedTime);
				}
				break;
		}
	}

	/**
	 * @return Returns the signature.
	 */
	public String getSignature() {
		return signature;
	}

	/**
	 * @param signature The signature to set.
	 */
	public void setSignature(String signature) {
		this.signature = signature;
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
	 * @return Returns the signatures.
	 */
	public List<Signature> getSignatures() {
		return signatures;
	}

	/**
	 * @param signatures The signatures to set.
	 */
	public void setSignatures(List<Signature> signatures) {
		this.signatures = signatures;
	}

}
