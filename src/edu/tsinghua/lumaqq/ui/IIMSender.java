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
package edu.tsinghua.lumaqq.ui;

/**
 * 消息发送者接口，这个接口做为IMWorkflow的回调接口，用来发送一条消息。
 * 为了使IMWorkflow不关系具体的发送参数，只关心具体的发送内容，所以具体
 * 的发送工作由这个接口的实现类完成
 * 
 * @author luma
 */
public interface IIMSender {
    /**
     * 发送一条消息
     * 
     * @param msg
     * 		已经处理好的消息内容，直接发送即可
     */
    public void send(String msg);
    
    /**
     * @return
     * 		发送者的id，群可以返回群id，普通聊天窗口可以返回qq号
     */
    public int getSenderId();
    
    /**
     * 消息发送结束时回调此方法，消息发送结束是指一个消息的分片全部发送完成
     */
    public void notifyOver();
    
    /**
     * 在开始发送消息前调用此方法，消息发送前是指第一个分片发送前
     * 
     * @param toBeSent
     * 		将要发送的消息
     */
    public void notifyStart(String toBeSent);
    
    /**
     * 消息发送失败时调用此方法
     * 
     * @param msg
     * 		失败消息
     */
    public void notifyFail(String msg);
    
    /**
     * 发送超时
     * 
     * @param failToSent
     * 		发送失败的消息
     */
    public void notifyTimeout(String failToSent);
}
