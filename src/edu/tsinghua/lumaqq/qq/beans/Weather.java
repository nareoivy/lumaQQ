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

import java.nio.ByteBuffer;
import java.util.Calendar;

import edu.tsinghua.lumaqq.qq.Util;

/**
 * 天气情况数据Bean
 * 
 * @author luma
 * @see edu.tsinghua.lumaqq.qq.packets.in.WeatherOpReplyPacket
 */
public class Weather {
	public int time;
	public int year, month, day;
	public String shortDesc;
	public String wind;
	public int lowTemperature, highTemperature;
	public String hint;
	
	public void readBean(ByteBuffer buf) {
		time = buf.getInt();
		int len = buf.get() & 0xFF;
		shortDesc = Util.getString(buf, len);
		len = buf.get() & 0xFF;
		wind = Util.getString(buf, len);
		lowTemperature = (short)buf.getChar();
		highTemperature = (short)buf.getChar();
		buf.get();
		len = buf.get() & 0xFF;
		hint = Util.getString(buf, len);
		
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(time * 1000L);
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH) + 1;
		day = c.get(Calendar.DAY_OF_MONTH);
	}
}
