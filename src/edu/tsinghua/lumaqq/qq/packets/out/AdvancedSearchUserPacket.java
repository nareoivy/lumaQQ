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
package edu.tsinghua.lumaqq.qq.packets.out;

import java.nio.ByteBuffer;

import edu.tsinghua.lumaqq.qq.QQ;
import edu.tsinghua.lumaqq.qq.beans.QQUser;
import edu.tsinghua.lumaqq.qq.packets.BasicOutPacket;
import edu.tsinghua.lumaqq.qq.packets.PacketParseException;

/**
 * <pre>
 * 高级搜索用户的请求包：
 * 
 * *********** 格式1 **************
 * 头部
 * --------- 加密开始（会话密钥）------------
 * 用户类型，1字节，0x01表示普通用户
 * 页数，从0开始，2字节
 * 在线与否，1字节，0x01表示在线，0x00表示不在线
 * 是否有摄像头，1字节，0x01表示有，0x00表示无，TX QQ 2004中的处理是如果要查找有摄像头的用户，则必须查找在线用户，不知道不这样行不行
 * 年龄，1字节，表示在下拉框中的索引
 * 性别，1字节，表示在下拉框中的索引
 * 省份，2字节，表示在下拉框中的索引
 * 城市，2字节，表示在下拉框中的索引
 * --------- 加密结束 -------------
 * 尾部
 * </pre>
 * 
 * @author luma
 */
public class AdvancedSearchUserPacket extends BasicOutPacket {
	private byte userType;
    private boolean searchOnline;
    private boolean hasCam;
    private char page;
    private byte ageIndex;
    private byte genderIndex;
    private char provinceIndex;
    private char cityIndex;
    
    /**
     * @param buf
     * @param length
     * @throws PacketParseException
     */
    public AdvancedSearchUserPacket(ByteBuffer buf, int length, QQUser user)
            throws PacketParseException {
        super(buf, length, user);
    }
    
    /**
     * 构造一个缺省包，缺省包查找在线用户，其他条件都不限
     */
    public AdvancedSearchUserPacket(QQUser user) {
        super(QQ.QQ_CMD_ADVANCED_SEARCH, true, user);
        searchOnline = true;
        hasCam = false;
        page = provinceIndex = cityIndex = 0;
        ageIndex = genderIndex = 0;
        userType = QQ.QQ_USER_TYPE_NORMAL;
    }
    
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.packets.OutPacket#getPacketName()
     */
	@Override
    public String getPacketName() {
        return "Advanced Search Packet";
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.packets.OutPacket#putBody(java.nio.ByteBuffer)
     */
	@Override
    protected void putBody(ByteBuffer buf) {
		// 用户类型
		buf.put(userType);
        // 2. 页数，从0开始
        buf.putChar(page);
        // 3. 在线与否，1字节，0x01表示在线，0x00表示不在线
        buf.put(searchOnline ? (byte)0x01 : (byte)0x00);
        // 4. 是否有摄像头，1字节，0x01表示有，0x00表示无，TX QQ 2004中的处理是如果要查找
        //   有摄像头的用户，则必须查找在线用户，不知道不这样行不行
        buf.put(hasCam ? (byte)0x01 : (byte)0x00);
        // 5. 年龄，1字节，表示在下拉框中的索引
        buf.put(ageIndex);
        // 6. 性别，1字节，表示在下拉框中的索引
        buf.put(genderIndex);
        // 7. 省份，2字节，表示在下拉框中的索引
        buf.putChar(provinceIndex);
        // 8. 城市，2字节，表示在下拉框中的索引
        buf.putChar(cityIndex);
    }
    
    /**
     * @return Returns the ageIndex.
     */
    public byte getAgeIndex() {
        return ageIndex;
    }
    /**
     * @param ageIndex The ageIndex to set.
     */
    public void setAgeIndex(byte ageIndex) {
        this.ageIndex = ageIndex;
    }
    /**
     * @return Returns the genderIndex.
     */
    public byte getGenderIndex() {
        return genderIndex;
    }
    /**
     * @param genderIndex The genderIndex to set.
     */
    public void setGenderIndex(byte genderIndex) {
        this.genderIndex = genderIndex;
    }
    /**
     * @return Returns the hasCam.
     */
    public boolean isHasCam() {
        return hasCam;
    }
    /**
     * @param hasCam The hasCam to set.
     */
    public void setHasCam(boolean hasCam) {
        this.hasCam = hasCam;
    }
    /**
     * @return Returns the page.
     */
    public char getPage() {
        return page;
    }
    /**
     * @param page The page to set.
     */
    public void setPage(char page) {
        this.page = page;
    }
    /**
     * @return Returns the searchOnline.
     */
    public boolean isSearchOnline() {
        return searchOnline;
    }
    /**
     * @param searchOnline The searchOnline to set.
     */
    public void setSearchOnline(boolean searchOnline) {
        this.searchOnline = searchOnline;
    }
    /**
     * @return Returns the cityIndex.
     */
    public char getCityIndex() {
        return cityIndex;
    }
    /**
     * @param cityIndex The cityIndex to set.
     */
    public void setCityIndex(char cityIndex) {
        this.cityIndex = cityIndex;
    }
    /**
     * @return Returns the provinceIndex.
     */
    public char getProvinceIndex() {
        return provinceIndex;
    }
    /**
     * @param provinceIndex The provinceIndex to set.
     */
    public void setProvinceIndex(char provinceIndex) {
        this.provinceIndex = provinceIndex;
    }

	public byte getUserType() {
		return userType;
	}

	public void setUserType(byte userType) {
		this.userType = userType;
	}
}
