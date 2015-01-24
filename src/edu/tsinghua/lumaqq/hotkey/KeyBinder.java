/*
* LumaQQ - Java QQ Client
*
* Copyright (C) 2004 quhw@smth
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
package edu.tsinghua.lumaqq.hotkey;

import java.util.HashMap;

/**
 * KeyBinder is used to maintain the key bindings.
 * This is a singleton class. 
 * 
 * @author quhw
 */
public class KeyBinder {
	private static KeyBinder _instance = null;
	
	private HashMap<String, IHotkeyListener> _bindings;
	
	public static KeyBinder getInstance() {
		if(_instance == null)
			_instance = new KeyBinder();
		return _instance;
	}
	
	public static boolean initOk;
	
	static {
		try {
			System.loadLibrary("keybinder");
			initOk = true;
		} catch (Throwable e) {
			initOk = false;
		}
	}
	
	/**
	 * 初始化热键库
	 */
	private native void init();
	
	/**
	 * 绑定热键
	 * 
	 * @param keystring
	 * 		热键的描述字符串，比如<Ctrl><Alt>Z
	 * @return
	 * 		true表示绑定成功
	 */
	private native boolean bind(String keystring);
	
	/**
	 * 取消热键绑定
	 * 
	 * @param keystring
	 * 		热键的描述字符串，比如<Ctrl><Alt>Z
	 */
	private native void unbind(String keystring);

	/**
	 * 初始化
	 */
	protected KeyBinder() {
		if(initOk)
			init();
    }

	/**
	 * 回调函数
	 * 
	 * @param keystring
	 * 		热键的描述字符串，比如<Ctrl><Alt>Z
	 */
	protected void callback(String keystring) {
		if(_bindings == null)
			return;
		
		IHotkeyListener handler = _bindings.get(keystring);
		
		if(handler != null) {
			handler.keyPressed();
		}
	}

    /**
     * 绑定热键
     * 
     * @param keystring
     * 		热键描述字符串，如<Ctrl><Alt>Z
     * @param handler
     * 		事件对象
     * @return
     * 		true如果绑定成功
     */
    public boolean register(String keystring, IHotkeyListener handler) {     
    	if(!initOk)
    		return false;
    	
        boolean success = bind(keystring);
        if(success) {
        	if(_bindings == null)
        		_bindings = new HashMap<String, IHotkeyListener>();
        	_bindings.put(keystring, handler);        	
        }
        return success;
    }

    /**
     * 删除热键绑定
     * 
     * @param keystring
     * 		热键描述字符串，如<Ctrl><Alt>Z
     */
    public void deregister(String keystring) {
    	if(!initOk)
    		return;
    	
        _bindings.remove(keystring);        
        unbind(keystring);
    }    
    
    /**
     * 删除当前所有绑定
     */
    public void deregisterAll() {
    	if(_bindings == null)
    		return;
    	for(String key : _bindings.keySet())
    		deregister(key);
    	_bindings.clear();
    }
}
