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
package edu.tsinghua.lumaqq.record;

import java.util.Comparator;

import com.sleepycat.je.DatabaseEntry;

/**
 * 消息关键字比较
 * 
 * @author luma
 */
public class KeyComparator implements Comparator {
	private static final DatabaseEntry valueEntry = new DatabaseEntry();
	private static final KeyBinding keyBinding = new KeyBinding();
	
	public int compare(Object o1, Object o2) {
		valueEntry.setData((byte[])o1);
		RecordEntry k1 = (RecordEntry)keyBinding.entryToObject(valueEntry);
		valueEntry.setData((byte[])o2);
		RecordEntry k2 = (RecordEntry)keyBinding.entryToObject(valueEntry);
		
		int result = k1.owner - k2.owner;
		if(result != 0)
			return result;
		
		if(k1.time - k2.time != 0)
			return (k1.time - k2.time > 0) ? 1 : -1;
		else 
			return 0;
	}
}
