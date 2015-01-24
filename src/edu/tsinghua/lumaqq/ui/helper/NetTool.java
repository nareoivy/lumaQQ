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

import java.net.InetAddress;
import java.net.UnknownHostException;

import edu.tsinghua.lumaqq.qq.Util;

/**
 * 网络相关工具类
 *
 * @author luma
 */
public class NetTool {
	private static String localIp;
	
	/**
	 * @return
	 * 		得到本地ip的字符串形式，排除127.0.0.1
	 */
	public static String getLocalIp() {
		if(localIp == null) {
			try {
				InetAddress local = InetAddress.getLocalHost();
				InetAddress[] ip = InetAddress.getAllByName(local.getHostName());
				for(InetAddress address : ip) {
					byte[] b = address.getAddress();
					if(!(b[0] == 127 && b[1] == 0 && b[2] == 0 && b[3] == 1)) {
						localIp = Util.convertIpToString(b);
						break;
					}
				}
			} catch(UnknownHostException e) {
			}			
		}
		if(localIp == null)
			localIp = "127.0.0.1";
		return localIp;
	}
}
