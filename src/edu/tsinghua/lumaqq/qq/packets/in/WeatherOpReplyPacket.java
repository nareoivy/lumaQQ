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
package edu.tsinghua.lumaqq.qq.packets.in;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import edu.tsinghua.lumaqq.qq.Util;
import edu.tsinghua.lumaqq.qq.beans.QQUser;
import edu.tsinghua.lumaqq.qq.beans.Weather;
import edu.tsinghua.lumaqq.qq.packets.BasicInPacket;
import edu.tsinghua.lumaqq.qq.packets.PacketParseException;

/**
 * <pre>
 * 天气操作回复包
 * 1. 头部
 * 2. 子命令，1字节
 * 3. 回复码，1字节
 * 4. 省名称字节长度，1字节
 * 5. 省
 * 6. 市名称字节长度，1字节
 * 7. 市
 * Note: 如果4部分为0，则可以认为腾讯无法找到你要的天气预报信息，不应再往下解析
 * 8. 未知的2字节
 * 9. 市(2)名称字节长度，1字节
 * 10. 市(2)
 * Note: 不明白为什么有两个市，这两个市有时候都有内容，有时候只有一个，要注意各种情况
 * 11. 预报的天数，1字节，如果72小时预报，这个就是0x03
 * 12. 时间，4字节，天气数据的开始时间
 * 13. 天气情况字节长度，1字节
 * 14. 天气情况
 * 15. 风向字节长度，1字节
 * 16. 风向
 * 17. 最低温度，2字节，单位是摄氏度
 * 18. 最高温度，2字节，单位是摄氏度
 * Note: 要注意温度为零下时，是负数，用java处理时要注意转换
 * 19. 未知的1字节
 * 20. 提示字节长度，1字节
 * 21. 提示
 * 22. 如果还有更多数据，重复12-21部分
 * 23. 未知的2字节
 * 24. 尾部
 * </pre>
 * 
 * @author luma
 */
public class WeatherOpReplyPacket extends BasicInPacket {
	public byte subCommand;
	public byte replyCode;
	public String province;
	public String city;
	public List<Weather> weathers;

	public WeatherOpReplyPacket(ByteBuffer buf, int length, QQUser user) throws PacketParseException {
		super(buf, length, user);
	}

	@Override
	protected void parseBody(ByteBuffer buf) throws PacketParseException {
		subCommand = buf.get();
		replyCode = buf.get();
		
		int len = buf.get() & 0xFF;
		if(len == 0)
			return;		
		province = Util.getString(buf, len);
		
		len = buf.get() & 0xFF;
		if(len > 0) {
			city = Util.getString(buf, len);
			buf.getChar();
			len = buf.get() & 0xFF;
			buf.position(buf.position() + len);
		} else {
			buf.getChar();
			len = buf.get() & 0xFF;
			city = Util.getString(buf, len);
		}
		int count = buf.get() & 0xFF;
		
		weathers = new ArrayList<Weather>();
		while(count-- > 0) {
			Weather w = new Weather();
			w.readBean(buf);
			weathers.add(w);
		}
	}
}
