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
package edu.tsinghua.lumaqq.models;

/**
 * 不表示什么，用来表示一些和QQ内容无关且不携带什么信息的model
 * 
 * @author luma
 */
public class Dummy extends Model {
	public String name;
	public DummyType dummyType;
	public Cluster cluster;
	
	public Dummy() {
		super(Type.DUMMY);
		initializeValues();
	}
	
	/**
	 * 初始化字段
	 */
	private void initializeValues() {
		name = EMPTY_STRING;
		dummyType = DummyType.CLUSTER_ORGANIZATION;
		cluster = null;
	}
}
