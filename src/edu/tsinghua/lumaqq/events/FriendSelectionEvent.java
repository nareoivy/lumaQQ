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
package edu.tsinghua.lumaqq.events;

import java.util.List;

import edu.tsinghua.lumaqq.models.Model;

/**
 * <pre>
 * 这个是好友选择事件的事件类，其source是一个List对象，其元素为FriendModel或者MobileModel
 * </pre>
 * 
 * @author luma
 */
public class FriendSelectionEvent {
	private List<Model> models;
	
	/**
	 * @param source
	 */
	public FriendSelectionEvent(List<Model> source) {
		models = source;
	}
	
	public List<Model> getModels() {
		return models;
	}
}
