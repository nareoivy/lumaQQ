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
package edu.tsinghua.lumaqq.widgets.qstyle;

import org.eclipse.swt.SWT;

/**
 * 动画类型
 * 
 * @author luma
 */
public enum Animation {
	ICON_BLINK, ICON_BOUNCE, TEXT_BLINK;
	
	/**
	 * @return
	 * 		特效的具体实现类
	 */
	public IEffect getEffector() {
		switch(this) {
			case ICON_BLINK:
				return IconBlinkEffect.INSTANCE;
			case ICON_BOUNCE:
				return IconBounceEffect.INSTANCE;
			case TEXT_BLINK:
				return TextBlinkEffect.INSTANCE;
			default:
				SWT.error(SWT.ERROR_INVALID_RANGE);
				return null;
		}
	}
	
	public static Animation valueOf(IEffect effect) {
		Class klass = effect.getClass();
		if(klass == IconBlinkEffect.class)
			return ICON_BLINK;
		else if(klass == IconBounceEffect.class)
			return ICON_BOUNCE;
		else if(klass == TextBlinkEffect.class)
			return TEXT_BLINK;
		else {
			SWT.error(SWT.ERROR_INVALID_RANGE);
			return null;			
		}
	}
}
