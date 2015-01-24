package edu.tsinghua.lumaqq.ui.muf;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

/**
 * 模块化界面容器
 *
 * @author luma
 */
public class MUFContainer {
	private Composite content;
	private HashMap<String, MUFModule> modules;
	private NotificationCenter center;
	private Point oldSize;
	
	// animation
	private static final int FRAMES = 10;
	private static final int INTERVAL = 10;
	private volatile boolean playing;
	private int frame;
	private int dx, dy;
	private MUFModule moduleToBeRemoved;
	private boolean adding;
	
	// delay add array
	private Queue<MUFModule> delayAdd, delayRemove;
	// delay dispose
	private Queue<Composite> delayDispose;
	
	private Runnable runnable = new Runnable() {
		public void run() {
			if(frame < FRAMES) {
				Point size = content.getParent().getSize();
				content.getParent().setSize(size.x + dx, size.y + dy);	
				frame++;
				Display.getCurrent().timerExec(INTERVAL, this);
			} else {				
				// set size
				oldSize = content.computeSize(SWT.DEFAULT, SWT.DEFAULT);
				content.getParent().setSize(content.getParent().computeSize(SWT.DEFAULT, SWT.DEFAULT));
				
				playing = false;
				if(delayDispose.peek() != null)
					delayDispose.poll().dispose();
				if(!adding)
					delayDispose.offer(moduleToBeRemoved.self);
				if(!delayAdd.isEmpty())
					add(delayAdd.poll());
				else if(!delayRemove.isEmpty())
					remove(delayRemove.poll());
			}
		}
	};

	public MUFContainer(Composite parent) {
		content = new Composite(parent, SWT.NONE);
		
		// set layout
		GridLayout layout = new GridLayout();
		layout.verticalSpacing = layout.horizontalSpacing = 3;
		layout.marginHeight = layout.marginWidth = 0;
		content.setLayout(layout);
		
		// init member
		modules = new HashMap<String, MUFModule>();
		center = new NotificationCenter(this);
		delayAdd = new LinkedList<MUFModule>();
		delayRemove = new LinkedList<MUFModule>();
		delayDispose = new LinkedList<Composite>();
		
		// get init size
		playing = false;
		oldSize = content.computeSize(SWT.DEFAULT, SWT.DEFAULT);
	}
	
	public void setLayoutData(Object data) {
		content.setLayoutData(data);
	}

	public Composite getContent() {
		return content;
	}
	
	public void remove(MUFModule module) {
		if(playing) {
			delayRemove.offer(module);
			return;
		}
		
		// set to true
		playing = true;
		adding = false;
		moduleToBeRemoved = module;
		
		// exclude from layout
		modules.remove(module.getId());
		GridData data = (GridData)module.self.getLayoutData();
		data.exclude = true;
		module.self.setLayoutData(data);
		module.self.setVisible(false);
		
		if(content.isVisible()) {
			// get destination size
			frame = 1;
			Point newSize = content.computeSize(SWT.DEFAULT, SWT.DEFAULT);
			dx = Math.min(0, (newSize.x - oldSize.x) / FRAMES);
			dy = Math.min(0, (newSize.y - oldSize.y) / FRAMES);
			Display.getCurrent().timerExec(0, runnable);
		} else {
			oldSize = content.computeSize(SWT.DEFAULT, SWT.DEFAULT);
			playing = false;
			delayDispose.offer(module.self);
		}
		module.dispose();
	}
	
	public void add(MUFModule module) {
		if(playing) {
			delayAdd.offer(module);
			return;
		}			
		
		// set to true
		playing = true;
		adding = true;
		
		// initialize module and add it
		module.create(this);
		modules.put(module.getId(), module);
		
		if(content.isVisible()) {
			// play a animation
			frame = 1;
			Point moduleSize = module.getPreferredSize();
			dx = Math.max(0, (moduleSize.x - oldSize.x) / FRAMES);
			dy = Math.max(0, moduleSize.y / FRAMES);
			content.pack();
			Display.getCurrent().timerExec(0, runnable);
		} else {
			oldSize = content.computeSize(SWT.DEFAULT, SWT.DEFAULT);
			playing = false;
		}
	}
	
	public MUFModule getModule(String id) {
		return modules.get(id);
	}
	
	public Iterator<MUFModule> getModuleIterator() {
		return modules.values().iterator();
	}
	
	public void dispatchNotification(Notification n) {
		center.dispatchNofiication(n);
	}
}
