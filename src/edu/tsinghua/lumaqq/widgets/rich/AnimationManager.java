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
package edu.tsinghua.lumaqq.widgets.rich;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;

/**
 * 负责管理动画的启动，停止，释放资源
 * 
 * @author luma
 */
public class AnimationManager implements DisposeListener {    
    // richbox
    private RichBox richbox;
    
    // 动画的key，key由tag和code组成
    // value是当前这种表情的个数
    private Map<Integer, Integer> keys;
    
    private AnimationThread thread;
    
    /**
     * 创建一个manager
     * 
     * @param threshold
     * 		最大动画数阈值
     */
    public AnimationManager(int threshold, RichBox richbox) {
        this.richbox = richbox;
        keys = new HashMap<Integer, Integer>();
    }
    
    /**
     * @param tag
     * @param code
     */
    public void startAnimation(int tag, int code) {
        ImageResolver resolver = richbox.getImageResolver();
        if(!resolver.isAnimate(tag, code))
            return;        
        
        int key = getKey(tag, code);
        if(keys.containsKey(key))
        	return;        
        
        resolver.getImage(tag, code);
        keys.put(key, key);
        if(keys.size() == 1) {
	        thread = new AnimationThread(richbox, resolver);	        
	        thread.start();
        }
        thread.putImage(key);
    }
    
    /**
     * 停止这个动画
     * 
     * @param tag
     * @param code
     */
    public void stopAnimation(int tag, int code) {
    	int key = getKey(tag, code);
    	if(!keys.containsKey(key))
    		return;
    	
    	keys.remove(key);
    	if(keys.isEmpty()) {
    		thread.dispose();
    		thread = null;
    	} else {
    		thread.remove(key);
    	}
    }
    
    /**
     * 停止所有动画
     */
    public void stopAll() {
    	keys.clear();
    	if(thread != null) {
    		thread.dispose();
    		thread = null;
    	}
    }

    /**
	 * @param tag
	 * @param code
	 * @return
	 */
	private int getKey(int tag, int code) {
		return ((tag & 0xFFFF) << 16) | (code & 0xFFFF);
	}
	
    /* (non-Javadoc)
     * @see org.eclipse.swt.events.DisposeListener#widgetDisposed(org.eclipse.swt.events.DisposeEvent)
     */
    public void widgetDisposed(DisposeEvent e) {
    	if(thread != null) {    	    
    		thread.dispose();
    		thread = null;
    	}
    }
}
