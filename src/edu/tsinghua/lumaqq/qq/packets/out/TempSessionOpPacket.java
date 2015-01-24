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
import edu.tsinghua.lumaqq.qq.Util;
import edu.tsinghua.lumaqq.qq.beans.FontStyle;
import edu.tsinghua.lumaqq.qq.beans.QQUser;
import edu.tsinghua.lumaqq.qq.packets.BasicOutPacket;
import edu.tsinghua.lumaqq.qq.packets.PacketParseException;

/**
 * <pre>
 * 临时会话操作请求包，格式为
 * 1. 头部
 * 2. 子命令，1字节
 * 当2部分为0x01时，表示发送临时会话消息，格式为
 * 3. 接收者QQ号，4字节
 * 4. 未知的4字节
 * 5. 发送者昵称长度，1字节
 * 6. 发送者昵称
 * 7. Site名称长度，1字节
 * 8. Site名称
 * Note: 所谓Site就是这个临时会话发源的地点，如果用户从某个群中开始一个
 * 临时会话，Site就是群名称，这个域可以指定为任意值，没有什么影响
 * 9. 未知的1字节
 * Note: 测试发现，9部分只有为0x01或者0x02时，才能使对方收到消息
 * 10. 未知的4字节
 * 11. 后面的内容长度，2字节，exclusive
 * 12. 消息内容，结尾追加空格
 * 13. 字体属性，参加edu.tsinghua.lumaqq.qq.beans.FontStyle
 * 
 * Note: 临时会话消息在QQ中是限制发送长度的，而且不支持多条发送
 * </pre>
 * 
 * @author luma
 */
public class TempSessionOpPacket extends BasicOutPacket {
	private byte subCommand;
	
	// 用于发送临时会话消息时
	private int receiver;
	private String nick;
	private String site;
	private String message;
	private FontStyle fontStyle;
	
	public TempSessionOpPacket(QQUser user) {
        super(QQ.QQ_CMD_TEMP_SESSION_OP, true, user);
        site = nick = message = "";
        fontStyle = new FontStyle();
	}

	public TempSessionOpPacket(ByteBuffer buf, int length, QQUser user) throws PacketParseException {
		super(buf, length, user);
	}
	
	@Override
	public String getPacketName() {
		switch(subCommand) {
			case QQ.QQ_SUB_CMD_SEND_TEMP_SESSION_IM:
				return "Send Temp Session IM Packet";
			default:
				return "Unknown Temp Session Op Packet";
		}
	}

	@Override
	protected void putBody(ByteBuffer buf) {
		buf.put(subCommand);
		switch(subCommand) {
			case QQ.QQ_SUB_CMD_SEND_TEMP_SESSION_IM:
				// 接收者
				buf.putInt(receiver);
				// 未知
				buf.putInt(0);
				// nick
				byte[] b = Util.getBytes(nick);
				buf.put((byte)b.length);
				buf.put(b);
				// site name
				b = Util.getBytes(site);
				buf.put((byte)b.length);
				buf.put(b);
				// 未知
				buf.put((byte)1);
				// 未知
				buf.putInt(0);
				// 长度，最后再填
				int pos = buf.position();
				buf.putChar((char)0);
				// 消息内容
				b = Util.getBytes(message);
				buf.put(b);
				buf.put((byte)0x20);
				// 字体属性
				fontStyle.writeBean(buf);
				// 回填长度
				buf.putChar(pos, (char)(buf.position() - pos - 2));
				break;
		}
	}

    /**
     * @return Returns the blue.
     */
    public int getBlue() {
        return fontStyle.getBlue();
    }
    
    /**
     * @param blue The blue to set.
     */
    public void setBlue(int blue) {
        fontStyle.setBlue(blue);
    }
    
    /**
     * @return Returns the bold.
     */
    public boolean isBold() {
        return fontStyle.isBold();
    }
    
    /**
     * @param bold The bold to set.
     */
    public void setBold(boolean bold) {
    	fontStyle.setBold(bold);
    }
    
    /**
     * @return Returns the encoding.
     */
    public char getEncoding() {
        return fontStyle.getEncodingCode();
    }
    
    /**
     * @param encoding The encoding to set.
     */
    public void setEncoding(char encoding) {
        fontStyle.setEncodingCode(encoding);
    }
    
    /**
     * @return Returns the fontName.
     */
    public String getFontName() {
        return fontStyle.getFontName();
    }
    
    /**
     * @param fontName The fontName to set.
     */
    public void setFontName(String fontName) {
        fontStyle.setFontName(fontName);
    }
    
    /**
     * @return Returns the green.
     */
    public int getGreen() {
        return fontStyle.getGreen();
    }
    
    /**
     * @param green The green to set.
     */
    public void setGreen(int green) {
        fontStyle.setGreen(green);
    }
    
    /**
     * @return Returns the italic.
     */
    public boolean isItalic() {
        return fontStyle.isItalic();
    }
    
    /**
     * @param italic The italic to set.
     */
    public void setItalic(boolean italic) {
        fontStyle.setItalic(italic);
    }
    
    /**
     * @return Returns the red.
     */
    public int getRed() {
        return fontStyle.getRed();
    }
    
    /**
     * @param red The red to set.
     */
    public void setRed(int red) {
        fontStyle.setRed(red);
    }
    
    /**
     * @return Returns the underline.
     */
    public boolean isUnderline() {
        return fontStyle.isUnderline();
    }
    
    /**
     * @param underline The underline to set.
     */
    public void setUnderline(boolean underline) {
    	fontStyle.setUnderline(underline);
    }
    
    /**
     * @return Returns the fontSize.
     */
    public int getFontSize() {
        return fontStyle.getFontSize();
    }
    
    /**
     * @param fontSize The fontSize to set.
     */
    public void setFontSize(int fontSize) {
    	fontStyle.setFontSize(fontSize);
    }

	/**
	 * @return Returns the message.
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message The message to set.
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return Returns the nick.
	 */
	public String getNick() {
		return nick;
	}

	/**
	 * @param nick The nick to set.
	 */
	public void setNick(String nick) {
		this.nick = nick;
	}

	/**
	 * @return Returns the receiver.
	 */
	public int getReceiver() {
		return receiver;
	}

	/**
	 * @param receiver The receiver to set.
	 */
	public void setReceiver(int receiver) {
		this.receiver = receiver;
	}

	/**
	 * @return Returns the site.
	 */
	public String getSite() {
		return site;
	}

	/**
	 * @param site The site to set.
	 */
	public void setSite(String site) {
		this.site = site;
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
}
