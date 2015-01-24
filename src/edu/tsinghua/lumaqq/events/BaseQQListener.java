package edu.tsinghua.lumaqq.events;

import java.util.LinkedList;
import java.util.Queue;

import org.eclipse.swt.widgets.Display;

import edu.tsinghua.lumaqq.qq.events.IQQListener;
import edu.tsinghua.lumaqq.qq.events.QQEvent;

public class BaseQQListener implements IQQListener {
	private Queue<QQEvent> queue;
	
	public BaseQQListener() {
		queue = new LinkedList<QQEvent>();
	}
	
	private Runnable eventRunnable = new Runnable() {
		public void run() {
			QQEvent e = getEvent();
			OnQQEvent(e);
		}
	};
	
	private QQEvent getEvent() {
		synchronized (queue) {
			return queue.poll();
		}
	}
	
	protected void OnQQEvent(QQEvent e) {
	}

	private void addEvent(QQEvent e) {
		synchronized(queue) {
			queue.offer(e);
		}
	}
	
	public void qqEvent(QQEvent e) {
		addEvent(e);
		Display.getDefault().asyncExec(eventRunnable);
	}
}
