package edu.tsinghua.lumaqq.ui.muf;

import java.util.HashMap;

public class MUFModel {
	private HashMap<String, Object> properties = new HashMap<String, Object>();
	
	public void setProperty(String key, Object value) {
		properties.put(key, value);
	}
	
	public Object getProperty(String key) {
		return properties.get(key);
	}
}
