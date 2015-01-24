package edu.tsinghua.lumaqq.ui.muf;

import java.util.Iterator;

public class NotificationCenter {
	private MUFContainer container;
	
	public NotificationCenter(MUFContainer container) {
		this.container = container;
	}
	
	public void dispatchNofiication(Notification n) {
		Iterator<MUFModule> iter = container.getModuleIterator();
		while(iter.hasNext()) {
			MUFModule module = iter.next();
			if(module != n.module)
				module.notifyChanged(n);
		}
	}
}
