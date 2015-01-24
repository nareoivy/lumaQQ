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

import static edu.tsinghua.lumaqq.resource.Messages.*;

import java.lang.reflect.Field;

import edu.tsinghua.lumaqq.qq.Util;
import edu.tsinghua.lumaqq.resource.Messages;

/**
 * 保存一些固定的数据
 * 
 * @author luma
 */
public class BeanHelper {
    private static String[] EMPTY = new String[0];
    
    public static String[] GENDER = new String[] {
        unlimited,
        gender_gg,
        gender_mm
    };
    
    public static String[] AGE;    
    public static String[] PROVINCE;
    public static Object[] CITY;
	public static String[] ZODIAC;		
	public static String[] OCCUPATION;
	public static String[] HOROSCOPE;
	public static String[] BLOOD;
    
    static {
        try {
			// 载入年龄
			int n = Util.getInt(user_info_basic_age_number, 0);
			AGE = new String[n + 1];
			AGE[0] = unlimited;
			for(int i = 1; i <= n; i++) {
				Field f = Messages.class.getField("user_info_basic_age_" + i);
			    AGE[i] = (String)f.get(null);			
			}
			// 载入省名称列表
			n = Util.getInt(user_info_contact_province_number, 0);
			PROVINCE = new String[n + 1];
			CITY = new Object[n + 1];
			CITY[0] = new String[] { unlimited };
			PROVINCE[0] = unlimited;
			for(int i = 1; i <= n; i++) {
				Field f = Messages.class.getField("user_info_contact_province_" + i);
			    PROVINCE[i] = (String)f.get(null);
			    
			    // 载入城市列表
			    String prefix = "user_info_contact_city_" + i + "_";
				f = Messages.class.getField(prefix + "number");
			    int cityNum = Util.getInt((String)f.get(null), 0);
			    String[] c = new String[cityNum + 1];
			    c[0] = unlimited;
			    for(int j = 1; j <= cityNum; j++) {
					f = Messages.class.getField(prefix + j);
			        c[j] = (String)f.get(null);		
			    }
			    CITY[i] = c;
			}
			
			// 载入星座
			ZODIAC = new String[13];
			for(int i = 0; i <= 12; i++) {
				Field f = Messages.class.getField("user_info_basic_zodiac_" + i);
				ZODIAC[i] = (String)f.get(null);
			}
			
			// 载入职业
			n = Util.getInt(user_info_basic_occupation_number, 0);
			OCCUPATION = new String[n];
			for(int i = 1; i <= n; i++) {
				Field f = Messages.class.getField("user_info_basic_occupation_" + i);
				OCCUPATION[i - 1] = (String)f.get(null); 
			}
			
			// 载入生肖
			HOROSCOPE = new String[13];
			for(int i = 0; i <= 12; i++) {
				Field f = Messages.class.getField("user_info_basic_horoscope_" + i);
				HOROSCOPE[i] = (String)f.get(null);
			}
			
			// 载入血型
			BLOOD = new String[6];
			for(int i = 0; i <= 5; i++) {
				Field f = Messages.class.getField("user_info_basic_blood_" + i);
				BLOOD[i] = (String)f.get(null);
			}
		} catch(Exception e) {
		}
    }
    
    /**
     * 得到性别字符串
     * 
     * @param index
     * @return
     */
    public static String getGender(int index) {
        if(index < 0 || index >= GENDER.length)
            return "";
        return GENDER[index];
    }
    
    /**
     * 得到省份名称
     * 
     * @param index
     * 		省份索引
     * @return
     * 		省份名称
     */
    public static String getProvince(int index) {
        if(index < 0 || index >= PROVINCE.length)
            return "";
        return PROVINCE[index];
    }
    
    /**
     * 得到城市名称
     * 
     * @param provinceIndex
     * 		省份索引
     * @param cityIndex
     * 		城市索引
     * @return
     * 		城市名称
     */
    public static String getCity(int provinceIndex, int cityIndex) {
        String[] c = getCitys(provinceIndex);
        if(cityIndex < 0 || cityIndex >= c.length)
            return "";
        return c[cityIndex];
    }
    
    /**
     * 得到城市列表
     * 
     * @param provinceIndex
     * @return
     */
    public static String[] getCitys(int provinceIndex) {
        if(provinceIndex < 0 || provinceIndex >= PROVINCE.length)
            return EMPTY;
        
        return (String[])CITY[provinceIndex];
    }
}
