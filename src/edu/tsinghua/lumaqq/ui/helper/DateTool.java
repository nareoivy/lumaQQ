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
package edu.tsinghua.lumaqq.ui.helper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * <pre>
 * 日期工具类函数
 * </pre>
 * 
 * @author luma
 */
public class DateTool {
    private static DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
    /**
     * @return 现在的日期字符串
     */
    public static String getCurrentDateTimeString() {
        return df.format(System.currentTimeMillis());
    }
    
    public static String format(long time) {
    	return df.format(time);
    }
}
