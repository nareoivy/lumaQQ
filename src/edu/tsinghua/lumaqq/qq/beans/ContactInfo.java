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
package edu.tsinghua.lumaqq.qq.beans;

import java.nio.ByteBuffer;

import edu.tsinghua.lumaqq.qq.QQ;
import edu.tsinghua.lumaqq.qq.Util;


/**
 * 一个用户的详细信息，全部都是字符串形式，按照QQ请求用户信息应答包中的顺序排列，一共37项
 *
 * @author luma
 * @see edu.tsinghua.lumaqq.qq.packets.in.GetUserInfoReplyPacket
 */
public class ContactInfo {
	/**
	 * 分割符(char)30.
	 */
	public static final String DIVIDER = Character.toString((char) 30);
	
    // 1. QQ号
    private static final int QQ_NUM = 0;
    // 2. 昵称
    private static final int NICK = 1;
    // 3. 国家
    private static final int COUNTRY = 2;
    // 4. 省
    private static final int PROVINCE = 3;
    // 5. 邮政编码
    private static final int ZIPCODE = 4;
    // 6. 地址
    private static final int ADDRESS = 5;
    // 7. 电话
    private static final int TELEPHONE = 6;
    // 8. 年龄
    private static final int AGE = 7;
    // 9. 性别
    private static final int GENDER = 8;
    // 10. 姓名
    private static final int NAME = 9;
    // 11. Email
    private static final int EMAIL = 10;
    // 12. 寻呼机sn，（sn是什么玩意，我也不知道）
    private static final int PAGER_SN = 11;
    // 13. 寻呼机号
    private static final int PAGER = 12;
    // 14. 寻呼机服务提供商
    private static final int PAGER_SP = 13;
    // 15. 寻呼机base num（也不清楚这是什么）
    private static final int PAGER_BASE_NUM = 14;
    // 16. 寻呼机类型
    private static final int PAGER_TYPE = 15;
    // 17. 职业
    private static final int OCCUPATION = 16;
    // 18. 主页
    private static final int HOMEPAGE = 17;
    // 19. 认证类型（应该是被人加自己为好友的时候的认证类型把）
    private static final int AUTH_TYPE = 18;
    // 20. unknown 1
    private static final int UNKNOWN_1 = 19;
    // 21. unknown 2
    private static final int UNKNOWN_2 = 20;
    // 22. 头像，头像是用一个数代表的，比如27, 因为QQ目录下的头像是从1开始编号的，
    //     但是这个头像的数字却是从0开始计数的。并且注意，QQ的目录下面每种头像都
    //     有3个bmp，所以按数字这么一排，27应该是10-1.bmp
    private static final int HEAD = 21;
    // 23. 手机号
    private static final int MOBILE = 22;
    // 24. 手机类型 
    private static final int MOBILE_TYPE = 23;
    // 25. 介绍
    private static final int INTRO = 24;
    // 26. 城市
    private static final int CITY = 25;
    // 27. unknown 3
    private static final int UNKNOWN_3 = 26;
    // 28. unknown 4
    private static final int UNKNOWN_4 = 27;
    // 29. unknown 5
    private static final int UNKNOWN_5 = 28;
    // 30. is_open_hp
    private static final int OPEN_HP = 29;
    // 31. is_open_contact（通讯方式是否对其他人可见）
    private static final int OPEN_CONTACT = 30;
    // 32. 学校
    private static final int COLLEGE = 31;
    // 33. 星座
    private static final int HOROSCOPE = 32;
    // 34. 生肖
    private static final int ZODIAC = 33;
    // 35. 血型
    private static final int BLODD = 34;
    // 36. UserFlag
    private static final int USER_FLAG = 35;
    // 37. unknown 6，总是0x2D
    private static final int UNKNOWN_6 = 36;
	
    // 1. QQ号
    public int qq;
    // 2. 昵称
    public String nick;
    // 3. 国家
    public String country;
    // 4. 省
    public String province;
    // 5. 邮政编码
    public String zipcode;
    // 6. 地址
    public String address;
    // 7. 电话
    public String telephone;
    // 8. 年龄
    public int age;
    // 9. 性别
    public String gender;
    // 10. 姓名
    public String name;
    // 11. Email
    public String email;
    // 17. 职业
    public String occupation;
    // 18. 主页
    public String homepage;
    // 19. 认证类型（应该是被人加自己为好友的时候的认证类型把）
    public int authType;
    // 22. 头像，头像是用一个数代表的，比如27, 因为QQ目录下的头像是从1开始编号的，
    //     但是这个头像的数字却是从0开始计数的。并且注意，QQ的目录下面每种头像都
    //     有3个bmp，所以按数字这么一排，27应该是10-1.bmp
    public int head;
    // 23. 手机号
    public String mobile;
    // 25. 介绍
    public String intro;
    // 26. 城市
    public String city;
    // 31. is_open_contact（通讯方式是否对其他人可见）
    public int openContact;
    // 32. 学校
    public String college;
    // 33. 星座
    public int horoscope;
    // 34. 生肖
    public int zodiac;
    // 35. 血型
    public int blood;
    // 36. UserFlag
    public int userFlag;
    
    // 字段数
    public int fieldCount;
    
    // 原始信息数组
    private String[] infos;
    
    /**
     * 构造函数
     */
    public ContactInfo() {
    	nick = "";
    	country = "";
    	province = "";
    	zipcode = "";
    	address = "";
    	telephone = "";
    	name = "";
    	email = "";
    	occupation = "";
    	homepage = "";
    	mobile = "";
    	intro = "";
    	city = "";
    	college = "";
    	gender = "";
    	authType = QQ.QQ_AUTH_NEED;
    	openContact = QQ.QQ_CONTACT_ONLY_FRIENDS;
    	fieldCount = QQ.QQ_COUNT_GET_USER_INFO_FIELD;
    }
    
    /**
     * 构造函数
     */
    public ContactInfo(ByteBuffer buf)  {
        String s = Util.getString(buf);;
        infos = s.split(DIVIDER);
        fieldCount = infos.length;
    	
        // 1. QQ号
        qq = Util.getInt(infos[QQ_NUM], 0);
        // 2. 昵称
        nick = Util.filterUnprintableCharacter(infos[NICK]);
         // 3. 国家
        country = infos[COUNTRY];
        // 4. 省
        province = infos[PROVINCE];
        // 5. 邮政编码
        zipcode = infos[ZIPCODE];
        // 6. 地址
        address = infos[ADDRESS];
        // 7. 电话
        telephone = infos[TELEPHONE];
        // 8. 年龄
        age = Util.getInt(infos[AGE], 0);
        // 9. 性别
        gender = infos[GENDER];
        // 10. 姓名
        name = infos[NAME];
        // 11. Email
        email = infos[EMAIL];
        // 17. 职业
        occupation = infos[OCCUPATION];
        // 18. 主页
        homepage = infos[HOMEPAGE];
        // 19. 认证类型（应该是被人加自己为好友的时候的认证类型把）
        authType = Util.getInt(infos[AUTH_TYPE], QQ.QQ_AUTH_NEED);
        // 22. 头像，头像是用一个数代表的，比如27, 因为QQ目录下的头像是从1开始编号的，
        //     但是这个头像的数字却是从0开始计数的。并且注意，QQ的目录下面每种头像都
        //     有3个bmp，所以按数字这么一排，27应该是10-1.bmp
        head = Util.getInt(infos[HEAD], 0);
        // 23. 手机号
        mobile = infos[MOBILE];
        // 25. 介绍
        intro = infos[INTRO];
        // 26. 城市
        city = infos[CITY];
        // 31. is_open_contact（通讯方式是否对其他人可见）
        openContact = Util.getInt(infos[OPEN_CONTACT], QQ.QQ_CONTACT_ONLY_FRIENDS);
        // 32. 学校
        college = infos[COLLEGE];
        // 33. 星座
        horoscope = Util.getInt(infos[HOROSCOPE], 0);
        // 34. 生肖
        zodiac = Util.getInt(infos[ZODIAC], 0);
        // 35. 血型
        blood = Util.getInt(infos[BLODD], 0);
        // 36. UserFlag
        userFlag = Util.getInt(infos[USER_FLAG], 0);
    }
    
    /**
     * @return
     * 		得到信息字符串数组
     */
    public String[] getInfoArray() {
    	if(infos == null)
    		createInfoArray();
    	return infos;
    }

	private void createInfoArray() {
		infos = new String[QQ.QQ_COUNT_GET_USER_INFO_FIELD];
	    // 1. QQ号
		infos[QQ_NUM] = String.valueOf(qq);
	    // 2. 昵称
		infos[NICK] = nick;
	    // 3. 国家
		infos[COUNTRY] = country;
	    // 4. 省
		infos[PROVINCE] = province;
	    // 5. 邮政编码
		infos[ZIPCODE] = zipcode;
	    // 6. 地址
		infos[ADDRESS] = address;
	    // 7. 电话
		infos[TELEPHONE] = telephone;
	    // 8. 年龄
		infos[AGE] = String.valueOf(age);
	    // 9. 性别
		infos[GENDER] = gender;
	    // 10. 姓名
		infos[NAME] = name;
	    // 11. Email
		infos[EMAIL] = email;
	    // 12. 寻呼机sn，（sn是什么玩意，我也不知道）
		infos[PAGER_SN] = "";
	    // 13. 寻呼机号
		infos[PAGER] = "";
	    // 14. 寻呼机服务提供商
		infos[PAGER_SP] = "";
	    // 15. 寻呼机base num（也不清楚这是什么）
		infos[PAGER_BASE_NUM] = "";
	    // 16. 寻呼机类型
		infos[PAGER_TYPE] = "";
	    // 17. 职业
		infos[OCCUPATION] = occupation;
	    // 18. 主页
		infos[HOMEPAGE] = homepage;
	    // 19. 认证类型（应该是被人加自己为好友的时候的认证类型把）
		infos[AUTH_TYPE] = String.valueOf(authType);
	    // 20. unknown 1
		infos[UNKNOWN_1] = "";
	    // 21. unknown 2
		infos[UNKNOWN_2] = "";
	    // 22. 头像，头像是用一个数代表的，比如27, 因为QQ目录下的头像是从1开始编号的，
	    //     但是这个头像的数字却是从0开始计数的。并且注意，QQ的目录下面每种头像都
	    //     有3个bmp，所以按数字这么一排，27应该是10-1.bmp
		infos[HEAD] = String.valueOf(head);
	    // 23. 手机号
		infos[MOBILE] = mobile;
	    // 24. 手机类型 
		infos[MOBILE_TYPE] = "";
	    // 25. 介绍
		infos[INTRO] = intro;
	    // 26. 城市
		infos[CITY] = city;
	    // 27. unknown 3
		infos[UNKNOWN_3] = "";
	    // 28. unknown 4
		infos[UNKNOWN_4] = "";
	    // 29. unknown 5
		infos[UNKNOWN_5] = "";
	    // 30. is_open_hp
		infos[OPEN_HP] = "";
	    // 31. is_open_contact（通讯方式是否对其他人可见）
		infos[OPEN_CONTACT] = String.valueOf(openContact);
	    // 32. 学校
		infos[COLLEGE] = college;
	    // 33. 星座
		infos[HOROSCOPE] = String.valueOf(horoscope);
	    // 34. 生肖
		infos[ZODIAC] = String.valueOf(zodiac);
	    // 35. 血型
		infos[BLODD] = String.valueOf(blood);
	    // 36. UserFlag
		infos[USER_FLAG] = String.valueOf(userFlag);
	    // 37. unknown 6，总是0x2D
		infos[UNKNOWN_6] = "";
	}
}
