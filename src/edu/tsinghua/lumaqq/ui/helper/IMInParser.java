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

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

import edu.tsinghua.lumaqq.qq.QQ;
import edu.tsinghua.lumaqq.qq.Util;
import edu.tsinghua.lumaqq.qq.beans.ClusterIM;
import edu.tsinghua.lumaqq.widgets.rich.IRichContent;

/**
 * 自定义表情工具类，用来分析一条接收消息，提取出自定义表情的信息
 * 
 * @author luma
 */
public class IMInParser {
    private List<Integer> offsets;
    private List<Integer> lengths;
    private List<String> names;
    
    // agents保存表情的中转服务器地址，不包含重复的表情
    private List<InetSocketAddress> agents;
    // sessionIds保存session id，不包含重复的表情
    private List<Integer> sessionIds;
    // 保存表情的文件名，不包含重复的表情
    private List<String> distinctNames;
    
    private ClusterIM im;
    private byte[] agentKey;
    
    public IMInParser() {
        offsets = new ArrayList<Integer>();
        lengths = new ArrayList<Integer>();
        names = new ArrayList<String>();
        agents = new ArrayList<InetSocketAddress>();
        sessionIds = new ArrayList<Integer>();
        distinctNames = new ArrayList<String>();
    }
    
    /**
     * 得到session id
     * 
     * @param index
     * 		索引，不考虑重复的
     * @return
     * 		会话id
     */
    public int getSessionId(int index) {
        if(index < 0 || index >= sessionIds.size())
            return 0;
        return sessionIds.get(index);
    }
    
    public InetSocketAddress getAgentAddress(int index) {
        return agents.get(index);
    }
    
    public int getDistinctFaceCount() {
        return agents.size();
    }
    
    public byte[] getAgentKey() {
        return agentKey;
    }
    
    /**
     * 得到表情文件名，得到的文件名的扩展名是小写的
     * 
     * @param index
     * @return
     */
    public String getFaceFileName(int index) {
        if(index < 0 || index >= distinctNames.size())
            return null;
        return distinctNames.get(index);
    }
    
    /**
     * 分析接收到的信息
     * 
     * @param msg
     */
    public void parseInMessage(ClusterIM clusterIM) {
    	im = clusterIM;
        lengths.clear();
        offsets.clear();
        names.clear();
        distinctNames.clear();
        sessionIds.clear();
        agents.clear();
        agentKey = null;
        
        int i = Util.findByteOffset(im.messageBytes, QQ.QQ_TAG_CUSTOM_FACE);
        while(i != -1) {
            offsets.add(i);
            int length = Util.getInt(new String(im.messageBytes, i + 2, 3).trim(), 5);
            byte existence = im.messageBytes[i + 1];
            int shortcutLen = (im.messageBytes[i + 6] & 0xFF) - 'A';
            int index = (im.messageBytes[i + 5] & 0xFF) - 'A';
            lengths.add(length);
            switch(existence) {
                case QQ.QQ_FORMAT_TAG_NEW_SERVER_SIDE_CUSTOM_FACE:
                    // session id
                    int sessionId = Util.getInt(new String(im.messageBytes, i + 9, 8).trim(), 16, -1);
                    sessionIds.add(sessionId);
                    // ip
                    byte[] ip = Util.convertHexStringToByteNoSpace(new String(im.messageBytes, i + 17, 8));
                    byte b = ip[0];
                    ip[0] = ip[3];
                    ip[3] = b;
                    b = ip[1];
                    ip[1] = ip[2];
                    ip[2] = b;
                    String ipStr = Util.convertIpToString(ip);
                    // port
                    int port = Util.getInt(new String(im.messageBytes, i + 25, 8).trim(), 16, 443);
                    // generate address
                    InetSocketAddress address = new InetSocketAddress(ipStr, port);
                    agents.add(address);
                    // get agent key
		            if(agentKey == null)
		                agentKey = new String(im.messageBytes, i + 33, 16).getBytes();
                    // 得到文件名
		            String name = new String(im.messageBytes, i + 49, length - shortcutLen - 50);
		            name = FileTool.getNameExcludeExtension(name) + '.' + FileTool.getExtension(name).toLowerCase();
                    names.add(name);
                    distinctNames.add(name);
                    break;
                case QQ.QQ_FORMAT_TAG_EXISTING_SERVER_SIDE_CUSTOM_SIDE:
                    names.add(distinctNames.get(index));   
                    break;
            }
            
            // 查找下一个
            i = Util.findByteOffset(im.messageBytes, QQ.QQ_TAG_CUSTOM_FACE, i + length);
        }
    }
    
    /**
     * @return
     * 		得到表情个数
     */
    public int getFaceCount() {
        return agents.size();
    }
    
    /**
     * 把输入消息转换为能在richbox中显示的格式
     * 
     * @return
     * 		转换后的消息
     */
    public String toDisplayFormat() {
    	// 没有自定义表情就跳过
    	if(offsets.isEmpty())
    		return im.message;
    	
    	// 否则生成RichBox可以理解的形式
        FaceRegistry util = FaceRegistry.getInstance();
        StringBuilder sb = new StringBuilder();
        int size = offsets.size();
        int lastOffset = 0;
        for(int i = 0; i < size; i++) {
            int offset = offsets.get(i);
            sb.append(convertBytes(im.messageBytes, lastOffset, offset));
            lastOffset = offset + lengths.get(i);
            
            String md5 = FileTool.getNameExcludeExtension(names.get(i));
            char id = (char)util.getFaceId(md5);
            if(id == 0)
                id = (char)util.getFaceId(md5);
            sb.append(IRichContent.CUSTOM_FACE_TAG).append(id);
        }
        if(lastOffset < im.messageBytes.length)
            sb.append(convertBytes(im.messageBytes, lastOffset, im.messageBytes.length));
        return sb.toString();
    }
    
    /**
     * 把字节数组转换为String，它为我们处理缺省表情的问题
     * 
     * @param b
     * 		消息字节数组
     * @return
     * 		String
     */
    private String convertBytes(byte[] b, int from, int end) {
        StringBuffer sb = new StringBuffer();
        int offset = from;
        int length = 0;
        for(int i = from; i < end; i++) {
            if(b[i] == QQ.QQ_TAG_DEFAULT_FACE) {
                sb.append(Util.getString(b, offset, length));                
                sb.append((char)b[i]).append((char)(b[i + 1] & 0xFF));
                i++;
                offset = i + 1;
                length = 0;
            } else
                length++;
        }
        if(length > 0)
            sb.append(Util.getString(b, offset, length));
        return sb.toString();
    }
}
