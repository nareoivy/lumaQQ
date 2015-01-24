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
package edu.tsinghua.lumaqq.qq.debug;

import java.util.Vector;

/**
 * <pre>
 * Debug切换类，单一实例，控制debug状态的设置和管理Debug客户端，其负责向客户端转发
 * Debug事件。所以可以有多个调试客户端同时存在。
 * </pre>
 * 
 * @author luma
 */
public class DebugSwitch {
    // DebugListener列表
    private Vector<IDebugListener> listeners;
    // 是否打开调试功能，是否打开调试功能是由目前有没有调试事件监听器的个数决定的
    private boolean debug;
    
    // 单一实例
    private static DebugSwitch instance = new DebugSwitch();
    
    /**
     * Private constructor
     */
    private DebugSwitch() {        
        listeners = new Vector<IDebugListener>();
        debug = false;
    }
    
    /**
     * @return 单一实例
     */
    public static DebugSwitch getInstance() {
        return instance;
    }
    
    /**
     * 添加一个调试事件监听器
     * 
     * @param dl
     * 			IDebugListener
     */
    public void addDebugListener(IDebugListener dl) {
        if(dl != null)
            listeners.add(dl);
        
        // 如果有至少一个调试事件监听器，打开调试模式
        if(listeners.size() > 0)
            debug = true;
    }
    
    /**
     * 删除一个调试事件监听器
     * 
     * @param dl
     * 			IDebugListener
     */
    public void removeDebugListener(IDebugListener dl) {
        if(dl != null)
            listeners.remove(dl);
        
        // 如果一个调试事件监听器也没有，关闭调试模式
        if(listeners.size() <= 0)
            debug = false;
    }
    
    /**
     * 转发包调试事件
     * 
     * @param e
     * 			IDebugObject对象
     */
    public void deliverDebugObject(IDebugObject obj) {
        int size = listeners.size();
        for(int i = 0; i < size; i++)
            (listeners.get(i)).deliverDebugObject(obj);
    }
    
    /**
     * @return Returns the debug.
     */
    public boolean isDebug() {
        return debug;
    }
    
    /**
     * @param debug The debug to set.
     */
    public void setDebug(boolean debug) {
        this.debug = debug;
    }
}
