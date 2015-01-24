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
package edu.tsinghua.lumaqq.widgets.rich2;

/**
 * 复制StyleObject
 *
 * @author luma
 */
public class StyleObjectReplicator {
	/**
	 * @param source
	 * @return
	 */
	public static StyleObject replicate(StyleObject source) {
		switch(source.objectType) {
			case StyleObject.IMAGE:
				return source;
			case StyleObject.CONTROL:
				if(source.object instanceof IReplicate) {
					StyleObject so = new StyleObject(source.objectType, ((IReplicate)source.object).replicate(), source.disposable);
					return so;
				} else
					return source;
			default:
				return source;
		}
	}
}
