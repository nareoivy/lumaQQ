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
 * 组织
 * 
 * @author luma
 */
public class Organization extends Model {
	public int id;
	public int path;
	public String name;
	public Cluster cluster;
	
	// level mask
	private static final int[] MASK = new int[] {
		0xFF000000,
		0x00FC0000,
		0x0003F000,
		0x00000FC0
	};
	
	// level shift
	private static final int[] SHIFT = new int[] {
		24, 
		18,
		12,
		6
	};
	
	public Organization() {
		super(Type.ORGANIZATION);
		initializeValues();
	}
	
	/**
	 * 初始化字段
	 */
	private void initializeValues() {
		id = 0;
		path = 0;
		name = "";
		cluster = null;
	}
	
	@Override
	public boolean equals(Object arg0) {
		if(arg0 instanceof Organization) {
			Organization o = (Organization)arg0;
			return o.id == id;
		} else
			return false;
	}
	
	@Override
	public int hashCode() {
		return id;
	}
	
	/**
	 * 检查自己是否是org的子组织
	 * 
	 * @param org
	 * 		可能的父组织
	 * @return
	 * 		true表示是
	 */
	public boolean isChildOf(Organization org) {
		return org != this && 
			org.getLevel() + 1 == getLevel() &&
			org.xor(path) == getMaskLevelId();
	}
	
	/**
	 * 检查自己是否是org的父组织，非直接父子关系也返回true
	 * 
	 * @param org
	 * @return
	 */
	public boolean isPrefixOf(Organization org) {
		int level = getLevel();
		return level < org.getLevel() &&
			(xor(org.path) >>> SHIFT[level]) == 0;
	}
	
	/**
	 * 检查自己是否是org的父组织
	 * 
	 * @param org
	 * 		可能的子组织
	 * @return
	 * 		true表示是
	 */
	public boolean isParentOf(Organization org) {
		return org.isChildOf(this);
	}
	
	/**
	 * @return
	 * 		得到自己的父组织，如果没有，返回null
	 */
	public Organization getParent() {
		// 如果群为null，返回
		if(cluster == null)
			return null;
		
		// 得到自己的level数和mask值
		int maskLevel = getMaskLevelId();
		int level = getLevel() - 1;
		if(level == -1)
			return null;
		
		// 逐个查找
		for(Organization org : cluster.organizations.values()) {
			if(org.getLevel() == level) {
				if(org.xor(path) == maskLevel)
					return org;
			}
		}
		return null;
	}
	
	/**
	 * 和另一个组织的path做一个异或
	 * 
	 * @param otherPath
	 * 		其他组织的path值
	 * @return
	 * 		异或值
	 */
	public int xor(int otherPath) {
		return path ^ otherPath; 
	}
	
	/**
	 * @return
	 * 		组织的路径和相应层数的mask相与的结果
	 */
	public int getMaskLevelId() {
		return path & MASK[getLevel()];
	}
	
	/**
	 * @return
	 * 		组织的层数，从0开始
	 */
	public int getLevel() {
		for(int i = MASK.length - 1; i >= 0; i--) {
			if((path & MASK[i]) != 0)
				return i;
		}
		return -1;
	}
	
	/**
	 * @return
	 * 		组织的路径与mask相与，然后移位，其值表明了这个组织是其父组织的第几个孩子
	 */
	public int getShiftMaskLevelId() {
		int level = getLevel();
		return (path & MASK[level]) >>> SHIFT[level];
	}
	
	/**
	 * 减少自己的level id，比如把自己从第三个子组织变成第二个子组织
	 */
	public void decreaseLevelId() {
		decreaseLevelId(getLevel());
	}
	
	/**
	 * 增加自己的level id，比如把自己从第二个子组织变成第三个子组织 
	 */
	public void increaseLevelId() {
		increaseLevelId(getLevel());
	}
	
	/**
	 * 减少某一层的level id
	 * 
	 * @param level
	 */
	public void decreaseLevelId(int level) {
		path -= 1 << SHIFT[level];
	}
	
	/**
	 * 增加某一层的level id
	 * 
	 * @param level
	 */
	public void increaseLevelId(int level) {
		path += 1 << SHIFT[level];
	}
	
	/**
	 * 设置某一层的id
	 * 
	 * @param level
	 * @param value
	 */
	public void setLevelId(int level, int value) {
		path &= ~MASK[level];
		path |= value << SHIFT[level];
	}
	
	/**
	 * 设置本层的id
	 * 
	 * @param value
	 */
	public void setLevelId(int value) {
		setLevelId(getLevel(), value);
	}
}
