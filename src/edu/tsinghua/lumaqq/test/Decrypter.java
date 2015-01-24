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

import edu.tsinghua.lumaqq.qq.Crypter;
import edu.tsinghua.lumaqq.qq.Util;

public class Decrypter {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		byte[] enc = Util.convertHexStringToByte("55 90 81 39 D1 A2 3E 3E 76 11 8A A4 D8 34 B2 E6 D1 63 39 A4 4E BC BE CC 5D B8 3B 93 D2 2F 01 AC");
		byte[] key = new byte[16];
		
		
		Crypter crypter = new Crypter();
		for(int i = 0xFF; i >= 0; i--) {
			for(int j = 0; j < 16; j++)
				key[j] = (byte)i;
			byte[] b = crypter.decrypt(enc, key);
			if(b != null) {
				System.out.println("key: " + Util.convertByteToHexString(key));
				System.out.println("content: " + Util.convertByteToHexString(b));
			}
		}
		
		long count = 0;
		while(true) {
			if(increase(key) > 16)
				break;
			
			count++;
			if(count % 1000000 == 0)
				System.out.println(count);
			byte[] b = crypter.decrypt(enc, key);
			if(b != null) {
				System.out.println("key: " + Util.convertByteToHexString(key));
				System.out.println("content: " + Util.convertByteToHexString(b));
			}
		}
	}
	
	private static int increase(byte[] key) {
		int i = 0;
		key[i]++;
		while(i < 16 && (key[i] & 0xFF) == 0xFF) {
			key[i] = 0;
			i++;
			key[i]++;
		}
		return i;
	}
}
