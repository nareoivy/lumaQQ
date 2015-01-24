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

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import com.sleepycat.je.Cursor;
import com.sleepycat.je.Database;
import com.sleepycat.je.DatabaseConfig;
import com.sleepycat.je.DatabaseEntry;
import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;
import com.sleepycat.je.LockMode;
import com.sleepycat.je.OperationStatus;

import edu.tsinghua.lumaqq.qq.Util;

/**
 * 消息管理器
 * 
 * @author luma
 */
public class RecordManager {	
	private Environment env;
	private Database db;
	private KeyBinding keyBinding;
	private DatabaseEntry keyEntry;
	private DatabaseEntry valueEntry;
	private RecordEntry searchKey;
	private int pageSize;
	
	private static final List<RecordEntry> EMPTY = new ArrayList<RecordEntry>();
	
	/**
	 * 构造函数
	 * 
	 * @param baseDir
	 * 		起始目录
	 */
	public RecordManager(String baseDir) {
		File dir = new File(baseDir);
		if(!dir.exists())
			dir.mkdirs();
		EnvironmentConfig config = new EnvironmentConfig();
		config.setAllowCreate(true);
		try {
			env = new Environment(dir, config);
			
			// 创建数据库
			DatabaseConfig dbConfig = new DatabaseConfig();
			dbConfig.setAllowCreate(true);
			dbConfig.setSortedDuplicates(true);
			dbConfig.setBtreeComparator(KeyComparator.class);
			db = env.openDatabase(null, "record", dbConfig);
			
			keyBinding = new KeyBinding();
			keyEntry = new DatabaseEntry();
			valueEntry = new DatabaseEntry();
			searchKey = new RecordEntry();
			pageSize = 30;
		} catch(DatabaseException e) {
			env = null;
			db = null;
		}
	}
	
	/**
	 * 删除一条记录
	 * 
	 * @param entry
	 */
	public void delete(RecordEntry entry) {
		if(db == null)
			return;
		
		searchKey.owner = entry.owner;
		searchKey.time = entry.time;
		keyBinding.objectToEntry(searchKey, keyEntry);
		try {
			db.delete(null, keyEntry);
		} catch(DatabaseException e) {
		}
	}
	
	/**
	 * 同步
	 */
	public void sync() {
		try {
			env.sync();
		} catch(DatabaseException e) {
		}
	}
	
	/**
	 * 删除某个owner的某类型消息
	 * 
	 * @param owner
	 * @param type
	 * @param subType
	 */
	public void delete(int owner, int type, int subType) {
		if(db == null)
			return;
		
		searchKey.owner = owner;
		searchKey.time = 0;
		keyBinding.objectToEntry(searchKey, keyEntry);
		
		Cursor cursor = null;
		try {
			cursor = db.openCursor(null, null);
		} catch(DatabaseException e) {
			return;
		}
		
		try {
			OperationStatus status = cursor.getSearchKeyRange(keyEntry, valueEntry, LockMode.DEFAULT);
			while(status == OperationStatus.SUCCESS) {
				// 检查owner是否匹配
				RecordEntry entry = (RecordEntry)keyBinding.entryToObject(keyEntry);
				if(entry.owner != owner || 
						(type != IKeyConstants.ALL && entry.type != type) ||
						(subType != IKeyConstants.SUB_ALL && entry.subType != subType))
					break;
				
				// 删除
				cursor.delete();
				
				// 查找下一个
				searchKey.time = entry.time + 1;
				keyBinding.objectToEntry(searchKey, keyEntry);
				status = cursor.getSearchKeyRange(keyEntry, valueEntry, LockMode.DEFAULT);
			}
			
			env.sync();
		} catch(DatabaseException e) {
		} finally {
			try {
				if(cursor != null)
					cursor.close();
			} catch(DatabaseException e) {
			}
		}	
	}
	
	/**
	 * 搜索关键字
	 * 
	 * @param owner
	 * 		消息拥有者
	 * @param type
	 * 		消息类型
	 * @param subType
	 * 		消息子类型
	 * @param keySearched
	 * 		要搜索的关键字
	 * @param pagination
	 * 		是否分页
	 * @param start
	 * 		起始时间
	 * @return
	 * 		聊天记录列表
	 */
	public List<RecordEntry> search(int owner, int type, int subType, String keySearched, boolean pagination, long start) {
		if(db == null)
			return EMPTY;
		
		searchKey.owner = owner;
		searchKey.time = start;
		keyBinding.objectToEntry(searchKey, keyEntry);
		
		Cursor cursor = null;
		try {
			cursor = db.openCursor(null, null);
		} catch(DatabaseException e) {
			return EMPTY;
		}
		
		List<RecordEntry> ret = new ArrayList<RecordEntry>();
		try {
			int count = 0;
			OperationStatus status = cursor.getSearchKeyRange(keyEntry, valueEntry, LockMode.DEFAULT);
			while(status == OperationStatus.SUCCESS) {
				// 检查owner是否匹配
				RecordEntry entry = (RecordEntry)keyBinding.entryToObject(keyEntry);
				if(entry.owner != owner || 
						(type != IKeyConstants.ALL && entry.type != type) ||
						(subType != IKeyConstants.SUB_ALL && entry.subType != subType))
					break;
				
				// 添加记录
				String msg = getString();
				if(msg.indexOf(keySearched) != -1) {
					if(pagination && ++count > pageSize) {			
						break;
					}
					entry.message = msg;
					ret.add(entry);
				}
				
				// 查找下一个
				searchKey.time = entry.time + 1;
				keyBinding.objectToEntry(searchKey, keyEntry);
				status = cursor.getSearchKeyRange(keyEntry, valueEntry, LockMode.DEFAULT);
			}
		} catch(DatabaseException e) {
		} finally {
			try {
				if(cursor != null)
					cursor.close();
			} catch(DatabaseException e) {
			}
		}
		return ret;
	}
	
	/**
	 * 得到下一页记录
	 * 
	 * @param owner
	 * 		消息拥有者
	 * @param type
	 * 		消息类型
	 * @param subType
	 * 		消息子类型
	 * @param start
	 * 		起始时间
	 * @return
	 * 		聊天记录列表
	 */
	public List<RecordEntry> getNextPage(int owner, int type, int subType, long start) {
		if(db == null)
			return EMPTY;
		
		searchKey.owner = owner;
		searchKey.time = start;
		keyBinding.objectToEntry(searchKey, keyEntry);
		
		Cursor cursor = null;
		try {
			cursor = db.openCursor(null, null);
		} catch(DatabaseException e) {
			return EMPTY;
		}
		
		List<RecordEntry> ret = new ArrayList<RecordEntry>();
		try {
			int count = 0;
			OperationStatus status = cursor.getSearchKeyRange(keyEntry, valueEntry, LockMode.DEFAULT);
			while(status == OperationStatus.SUCCESS) {
				// 检查owner是否匹配
				RecordEntry entry = (RecordEntry)keyBinding.entryToObject(keyEntry);
				if(entry.owner != owner || 
						(type != IKeyConstants.ALL && entry.type != type) ||
						(subType != IKeyConstants.SUB_ALL && entry.subType != subType))
					break;
				
				// 添加记录
				if(++count > pageSize) {			
					break;
				}
				entry.message = getString();
				ret.add(entry);		
				
				// 查找下一个
				searchKey.time = entry.time + 1;
				keyBinding.objectToEntry(searchKey, keyEntry);
				status = cursor.getSearchKeyRange(keyEntry, valueEntry, LockMode.DEFAULT);
			}
		} catch(DatabaseException e) {
		} finally {
			try {
				if(cursor != null)
					cursor.close();
			} catch(DatabaseException e) {
			}
		}	
		return ret;
	}
	
	/**
	 * 得到某个owner的全部聊天记录
	 * 
	 * @param owner
	 * 		消息拥有者
	 * @param type
	 * 		消息类型
	 * @param subType
	 * 		消息子类型
	 * @return
	 * 		记录列表
	 */
	public List<RecordEntry> getRecord(int owner, int type, int subType) {
		if(db == null)
			return EMPTY;
		
		// get search key
		searchKey.time = 0;
		searchKey.owner = owner;
		keyBinding.objectToEntry(searchKey, keyEntry);
		
		Cursor cursor = null;
		try {
			cursor = db.openCursor(null, null);
		} catch(DatabaseException e) {
			return EMPTY;
		}
		
		List<RecordEntry> ret = new ArrayList<RecordEntry>();
		try {
			OperationStatus status = cursor.getSearchKeyRange(keyEntry, valueEntry, LockMode.DEFAULT);
			while(status == OperationStatus.SUCCESS) {
				RecordEntry entry = (RecordEntry)keyBinding.entryToObject(keyEntry);
				if(entry.owner != owner || 
						(type != IKeyConstants.ALL && entry.type != type) ||
						(subType != IKeyConstants.SUB_ALL && entry.subType != subType))
					break;
				
				entry.message = getString();
				ret.add(entry);
				searchKey.time = entry.time + 1;
				keyBinding.objectToEntry(searchKey, keyEntry);
				status = cursor.getSearchKeyRange(keyEntry, valueEntry, LockMode.DEFAULT);
			}
		} catch(DatabaseException e) {
		} finally {
			try {
				if(cursor != null)
					cursor.close();
			} catch(DatabaseException e) {
			}
		}
		return ret;
	}
	
	/**
	 * 得到某个时间段内的聊天记录
	 * 
	 * @param owner
	 * 		消息拥有者
	 * @param type
	 * 		消息类型
	 * @param subType
	 * 		消息子类型
	 * @param fromYear
	 * 		起始年
	 * @param fromMonth
	 * 		起始月
	 * @param fromDay
	 * 		起始日
	 * @param toYear
	 * 		结束年
	 * @param toMonth
	 * 		结束月
	 * @param toDay
	 * 		结束日
	 * @return
	 * 		聊天记录列表
	 */
	public List<RecordEntry> getRecords(int owner, int type, int subType, int fromYear, int fromMonth, int fromDay, int toYear, int toMonth, int toDay) {
		if(db == null)
			return EMPTY;
		
		Calendar c = Calendar.getInstance(Locale.getDefault());
		c.set(fromYear, fromMonth - 1, fromDay, 0, 0, 0);
		
		// get search key
		searchKey.time = c.getTimeInMillis();
		searchKey.owner = owner;
		keyBinding.objectToEntry(searchKey, keyEntry);
		
		// get end time
		c.set(toYear, toMonth - 1, toDay, 0, 0, 0);
		long endTime = c.getTimeInMillis();
		
		Cursor cursor = null;
		try {
			cursor = db.openCursor(null, null);
		} catch(DatabaseException e) {
			return EMPTY;
		}
		
		List<RecordEntry> ret = new ArrayList<RecordEntry>();
		try {
			OperationStatus status = cursor.getSearchKeyRange(keyEntry, valueEntry, LockMode.DEFAULT);
			while(status == OperationStatus.SUCCESS) {
				RecordEntry entry = (RecordEntry)keyBinding.entryToObject(keyEntry);
				if(entry.owner != owner || 
						(type != IKeyConstants.ALL && entry.type != type) ||
						(subType != IKeyConstants.SUB_ALL && entry.subType != subType) ||
						entry.time > endTime)
					break;
				
				entry.message = getString();
				ret.add(entry);
				searchKey.time = entry.time + 1;
				keyBinding.objectToEntry(searchKey, keyEntry);
				status = cursor.getSearchKeyRange(keyEntry, valueEntry, LockMode.DEFAULT);
			}
		} catch(DatabaseException e) {
		} finally {
			try {
				if(cursor != null)
					cursor.close();
			} catch(DatabaseException e) {
			}
		}
		return ret;
	}
	
	/**
	 * 查询记录
	 * 
	 * @param key
	 * 		记录关键字
	 * @return
	 * 		String，如果没找到，返回null
	 */
	public String getRecord(RecordEntry key) {
		keyBinding.objectToEntry(key, keyEntry);
		try {
			db.get(null, keyEntry, valueEntry, LockMode.DEFAULT);
			if(valueEntry.getData() == null)
				return null;
			return getString();
		} catch(DatabaseException e) {
			return null;
		}
	}

	/**
	 * @return
	 */
	private String getString() {
		return Util.getString(valueEntry.getData(), "UTF-8");
	}
	
	/**
	 * 添加消息记录
	 * 
	 * @param key
	 * 		消息记录
	 */
	public void addRecord(RecordEntry key) {
		if(db == null)
			return;
		
		keyBinding.objectToEntry(key, keyEntry);		
		valueEntry.setData(Util.getBytes(key.message, "UTF-8"));
		try {
			db.put(null, keyEntry, valueEntry);
			env.sync();
		} catch(DatabaseException e) {
		}
	}
	
	/**
	 * 释放资源
	 */
	public void dispose() {
		try {
			if(env != null) {
				if(db != null)
					db.close();
				env.cleanLog();
				env.close();
			}
		} catch(DatabaseException e) {			
		}
	}

	/**
	 * @return Returns the pageSize.
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * @param pageSize The pageSize to set.
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
}
