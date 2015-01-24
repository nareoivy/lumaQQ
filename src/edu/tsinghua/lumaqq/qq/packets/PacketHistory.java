/*
* LumaQQ - Java QQ Client
*
* Copyright (C) 2004 luma <stubma@163.com>
*          			 notXX
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
package edu.tsinghua.lumaqq.qq.packets;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;



/**
 * 包监视器.
 * 这个类使用一个hashtable和一个linked list来管理收到的包，其他人可以调用他的
 * 方法来检查一个包是否收到，比如重发线程通过它来检查是否一个包的应答已经到达，
 * 如果是，则不需要重发。还有接收线程，一旦收到一个包，就把这个包的hash值存入这
 * 个类，还有包处理器，也要通过来检查是否一个包是重复的。当然这种方法还不能完美
 * 的避免任何不必要的重发，但是至少有点帮助，-_-!....
 * 另外这个包监视器还要管理所有的请求，因为这些包的应答包里面没有什么有用的信息，
 * 信息都在请求包里，所以...
 * <br>(edit by notxx)
 * 改变成使用LinkedHashSet和HashMap来管理. 速度应该快一些.
 * 不需要创建一个Integer对象作为key了, 直接使用Packet本身即可.
 * 
 * @see notxx.lumaqq.qq.packet.Packet#hashCode()
 * @see notxx.lumaqq.qq.packet.Packet#equals(Object)
 * 
 * @author luma
 * @author notXX
 */
public class PacketHistory {
    /**
     *  用于重复包检测的链接哈希表
     */
    private LinkedHashSet<Integer> hash;
    /**
     *  用于请求的哈希表
     */
    private Map<OutPacket, OutPacket> sent;
    /**
     *  阈值，超过时清理hash中的数据
     */
    private static final int THRESHOLD = 100;
    
    /**
     * 构造函数
     */
    public PacketHistory() {
    	hash = new LinkedHashSet<Integer>();
    	sent = new Hashtable<OutPacket, OutPacket>();
    }
 
    /**
     * 这个方法检查包是否已收到，要注意的是检查是针对这个包的hash值进行的，
     * 并不是对packet这个对象，hash值的计算是在packet的hashCode中完成的，
     * 如果两个packet的序号或者命令有不同，则hash值肯定不同。
     * 
     * @param packet
     * 		要检查的包
     * @param add
     * 		如果为true，则当这个包不存在时，添加这个包的hash，否则不添加
     * @return
     * 		true如果这个包已经收到，否则false
     * 
     * @see Packet#hashCode()
     * @see Packet#equals(Object);
     */
    public boolean check(Packet packet, boolean add) {
        // 检查
        return check(packet.hashCode(), add);
    }
    
    /**
     * 检查指定的hash值是否已经存在
     * 
     * @param hashValue
     * 		哈希值
     * @param add
     * 		true表示如果不存在则添加这个哈希值
     * @return
     * 		true表示已经存在
     */
    public boolean check(int hashValue, boolean add) {
        // 检查是否已经有了
        if(hash.contains(hashValue))
            return true;
        else{
            // 如果add标志为false，不添加
            if(add)	
				hash.add(hashValue);
            else 
				return false;
        }
        // 检查是否超过了阈值
        if(hash.size() >= THRESHOLD) {
            // 清理掉一半
        	Iterator<Integer> it = hash.iterator();
            for(int i = 0; i < (THRESHOLD / 2); i++) {
                it.next();
                it.remove();
            }
        }
        
        return false;
    }
    
    /**
     * 这个方法检查包是否已收到，要注意的是检查是针对这个包的hash值进行的，
     * 并不是对packet这个对象，hash值的计算是在packet的hashCode中完成的，
     * 如果两个packet的序号或者命令有不同，则hash值肯定不同。
     * 
     * @param packet
     * 		要检查的包
     * @param add
     * 		如果为true，则当这个包不存在时，添加这个包的hash，否则不添加
     * @return
     * 		true表示这个包已经收到，否则false
     */
    public boolean check(Object packet, boolean add) {
    	return check((Packet)packet, add);
    }
    
    /**
     * 把请求推入哈希表
     * 
     * @param packet
     * 		请求包
     */
    public void putSent(OutPacket packet) {
        sent.put(packet, packet);
    }
    
    /**
     * 返回这个回复包对应的请求包
     * 
     * @param packet
     * 		InPacket
     * @return
     * 		你的请求包
     */
    public OutPacket retrieveSent(InPacket packet) {
        return sent.remove(packet);
    }
    
    /**
     * 清空包监视缓冲区
     */
    public void clear() {
        hash.clear();
        sent.clear();
    }
}
