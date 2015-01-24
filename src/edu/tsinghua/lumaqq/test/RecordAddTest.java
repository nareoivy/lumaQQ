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
package edu.tsinghua.lumaqq.test;

import java.util.Calendar;
import java.util.Locale;

import edu.tsinghua.lumaqq.record.IKeyConstants;
import edu.tsinghua.lumaqq.record.RecordEntry;
import edu.tsinghua.lumaqq.record.RecordManager;

@SuppressWarnings("unused")
public class RecordAddTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		RecordManager rm = new RecordManager("d:\\temp\\Download");
//		Calendar c = Calendar.getInstance(Locale.getDefault());
//		RecordEntry key = new RecordEntry();
//		int count = 0;
//		for(int m = 0; m < 12; m++) {
//			for(int d = 1; d < 29; d++) {
//				for(int h = 0; h < 24; h++) {
//					for(int min = 0; min < 60; min++) {
//						c.set(2005, m, d, h, min, 0);
//						key.type = IKeyConstants.SYSTEM;
//						key.owner = 10000 + min;
//						key.sender = 8422190;
//						key.receiver = 375149072;
//						key.time = (int)(c.getTimeInMillis() / 1000L);
//						key.message = "Hello" + (m + 1) + ":" + d + ":" + h + ":" + min;
//						rm.addRecord(key);										
//					}
//				}
//			}
//		}
		Calendar c = Calendar.getInstance(Locale.getDefault());
		RecordEntry key = new RecordEntry();
		int count = 0;
		int time = (int)(System.currentTimeMillis() / 1000L);
		for(int m = 0; m < 12; m++) {
			for(int d = 1; d < 29; d++) {
				c.set(2005, m, d, 0, 0, 0);
				key.type = IKeyConstants.SYSTEM;
				key.owner = 10000;
				key.sender = 8422190;
				key.receiver = 375149072;
				key.time = time;
				key.message = "Hello" + (m + 1) + ":" + d;
				rm.addRecord(key);					
			}
		}
		rm.dispose();
	}

}
