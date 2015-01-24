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

import java.util.List;

import edu.tsinghua.lumaqq.record.IKeyConstants;
import edu.tsinghua.lumaqq.record.RecordEntry;
import edu.tsinghua.lumaqq.record.RecordManager;

@SuppressWarnings("unused")
public class RecordTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		RecordTest t = new RecordTest();
		t.testGetRecords();
	}

	private void testSearch() {
		RecordManager rm = new RecordManager("d:\\temp\\Download");
		long start = System.currentTimeMillis();
		rm.setPageSize(10);
		List<RecordEntry> msg = rm.search(10001, IKeyConstants.SYSTEM, IKeyConstants.SUB_ALL, "12", true, 0);
		for(RecordEntry s : msg)
			System.out.println(s.message);
		System.out.println("---------------------------");
		long nextStart = msg.isEmpty() ? 0 : msg.get(msg.size() - 1).time + 1;
		msg.clear();
		msg = rm.search(10001, IKeyConstants.SYSTEM, IKeyConstants.SUB_ALL, "12", true, nextStart);
		for(RecordEntry s : msg)
			System.out.println(s.message);
		System.out.println(System.currentTimeMillis() - start);
		rm.dispose();
	}
	
	private void testGetNextPage() {
		RecordManager rm = new RecordManager("d:\\temp\\Download");
		long start = System.currentTimeMillis();
		rm.setPageSize(0);
		List<RecordEntry> msg = rm.getNextPage(10001, IKeyConstants.SYSTEM, IKeyConstants.SUB_ALL, 0);
		for(RecordEntry s : msg)
			System.out.println(s.message);
		System.out.println("---------------------------");
		msg.clear();
		long nextStart = msg.isEmpty() ? 0 : msg.get(msg.size() - 1).time + 1;
		msg = rm.getNextPage(10001, IKeyConstants.SYSTEM, IKeyConstants.SUB_ALL, nextStart);
		for(RecordEntry s : msg)
			System.out.println(s.message);
		System.out.println(System.currentTimeMillis() - start);
		rm.dispose();
	}
	
	private void testGetRecords() {
		RecordManager rm = new RecordManager("d:\\temp\\Download");
		long start = System.currentTimeMillis();
		List<RecordEntry> msg = rm.getRecords(10000, IKeyConstants.SYSTEM, IKeyConstants.SUB_ALL, 2005, 12, 3, 2005, 12, 5);
		for(RecordEntry s : msg)
			System.out.println(s.message);
		System.out.println(System.currentTimeMillis() - start);
		rm.dispose();
	}
}
