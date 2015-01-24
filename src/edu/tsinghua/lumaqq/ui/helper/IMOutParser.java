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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.tsinghua.lumaqq.ecore.face.Face;
import edu.tsinghua.lumaqq.widgets.rich.IRichContent;

/**
 * 自定义表情工具类，用来分析一条发送消息，提取出里面的自定义表情信息
 * 
 * @author luma
 */
public class IMOutParser {
    private List<Integer> ids;    
    private List<Integer> offsets;    
    private char[] flag;
    private boolean[] referred;
    private String message;
    
    public IMOutParser() {   
        ids = new ArrayList<Integer>();
        offsets = new ArrayList<Integer>();
    }
    
    /**
     * 分析一条输出消息中的所有自定义表情
     * 
     * @param msg
     */
    public void parseOutMessage(String msg) {
        this.message = msg;
        ids.clear();
        offsets.clear();
        
        int i = msg.indexOf(IRichContent.CUSTOM_FACE_TAG);
        while(i != -1) {
            offsets.add(i);
            ids.add((int)message.charAt(i + 1));
            i = msg.indexOf(IRichContent.CUSTOM_FACE_TAG, i + 2);
        }
        
        if(ids.isEmpty())
            return;

        int size = ids.size();
        flag = new char[size];
        referred = new boolean[size];
        
        Map<Integer, Character> temp = new HashMap<Integer, Character>();        
        char c = 'A';
        for(i = 0; i < size; i++) {
            Integer id = ids.get(i);
            if(temp.containsKey(id)) {
                referred[i] = true;
                flag[i] = temp.get(id);
            } else {
                temp.put(id, c);
                referred[i] = false;
                flag[i] = c++;
            }
        }
    }
    
    /**
     * @return
     * 		消息中的自定义表情数目
     */
    public int getFaceCount() {
        return ids.size();
    }
    
    /**
     * @return
     * 		消息中的自定义表情数目，不包括重复的在内
     */
    public int getDistinctFaceCount() {
        int count = 0;
		for(boolean refer : referred) {
            if(refer)
                count++;
        }
        return count;
    }
    
    /**
     * 把要发送的消息转换为协议格式
     * 
     * @return
     */
    public String toProtocolFormat(int[] sessionId, InetSocketAddress agentAddress, byte[] fileAgentKey) {
        StringBuffer sb = new StringBuffer();
        int size = ids.size();
        int lastOffset = 0;
        for(int i = 0; i < size; i++) {
            int offset = offsets.get(i);
            sb.append(message.subSequence(lastOffset, offset));
            lastOffset = offset + 2;
            
            if(isReferred(i))
                appendExistFace(sb, i);
            else
                appendNewFace(sb, i, sessionId, agentAddress, fileAgentKey);
        }
        if(lastOffset < message.length())
            sb.append(message.substring(lastOffset));
        return sb.toString();
    }
    
    /**
     * 生成一个已经存在表情的协议内容
     * 
     * @param sb
     * @param i
     */
    private void appendExistFace(StringBuffer sb, int i) {
        // 0x15
        sb.append(IRichContent.CUSTOM_FACE_TAG);
        // 已有表情
        sb.append('7');
        // 总长度，因为我们没有快捷方式，设置8就是了
        sb.append("  8");
        // 标志
        sb.append(getFlag(i));
        // 快捷键长度
        sb.append('A');
        // 分界符号
        sb.append('A');
    }

    /**
     * 生成一个新表情的协议内容
     * 
     * @param sb
     * @param index
     * @param agentAddress
     * @param sessionId
     * @param fileAgentKey
     */
    private void appendNewFace(StringBuffer sb, int index, int[] sessionId, InetSocketAddress agentAddress, byte[] fileAgentKey) {
        Face face = FaceRegistry.getInstance().getFace(getId(index));
        String filename = face.getFilename();
        int length = 50 + filename.length();
        String lengthStr = String.valueOf(length);
        
        // 0x15
        sb.append(IRichContent.CUSTOM_FACE_TAG);
        // 新的表情
        sb.append('6');
        // 总长度，因为我们目前没有快捷方式，设成86个字节就是了
        int spaceLen = 3 - lengthStr.length();
        for(int i = 0; i < spaceLen; i++)
            sb.append(' ');
        sb.append(lengthStr);
        // 新的表情
        sb.append('e');
        // 快捷键长度
        sb.append('A');
        // 后面的长度，用1A就是了
        sb.append("1A");

        // session id 的16进制字符串
        String sessionIdStr = Integer.toHexString(sessionId[index]);
        spaceLen = 8 - sessionIdStr.length();
        for(int i = 0; i < spaceLen; i++)
            sb.append(' ');
        sb.append(sessionIdStr);
        
        // 添加ip
        byte[] ip = agentAddress.getAddress().getAddress();
        for(int i = ip.length - 1; i >= 0; i--)
            sb.append(Integer.toHexString(ip[i] & 0xFF));
        
        // 添加端口
        String portStr = Integer.toHexString(agentAddress.getPort());
        spaceLen = 8 - portStr.length();
        for(int i = 0; i < spaceLen; i++)
            sb.append(' ');
        sb.append(portStr);
        
        // agent key
        sb.append(new String(fileAgentKey));
        
        // 文件名
        sb.append(face.getFilename());
        // 快捷方式，没有，暂时不管
        // 分界符
        sb.append('A');
    }
    
    /**
     * 检查自定义表情是否是引用其他的，意思就是是否这个表情以前出现过
     * 
     * @param index
     * 		表情在消息中出现的序号
     * @return
     * 		true表示这个表情以前出现过
     */
    public boolean isReferred(int index) {
        return referred[index];
    }
    
    /**
     * 得到表情的标识字母
     * 
     * @param index
     * @return
     */
    public char getFlag(int index) {
        return flag[index];
    }
    
    /**
     * @return
     * 		true表示消息中包含自定义表情
     */
    public boolean hasCustomFace() {
        return ids.size() > 0;
    }
    
    /**
     * 得到表情的id
     * 
     * @param index
     * @return
     */
    public int getId(int index) {
        return ids.get(index);
    }
    
    /**
     * 返回表情在消息中的偏移
     * 
     * @param index
     * @return
     */
    public int getOffsets(int index) {
        return offsets.get(index);
    }
}
