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

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;

/**
 * 动画线程
 * 
 * @author luma
 */
public class AnimationThread extends Thread {
	private Runnable redrawRunnable = new Runnable() {
	    public void run() {
	        try {
	        	for(int i = 0; i < MAX; i++) {
	        		if(keys[i] == null)
	        			continue;
	        		
	        		// 如果下一帧的时间没到，跳过
	        		if(nextFrameTime[i] > time)
	        			continue;
	        		
	        		// 得到image data
	        		int tag = keys[i] >>> 16;
	        		int code = keys[i] & 0xFFFF;
	        		ImageLoader loader = resolver.getImageLoader(tag, code);
	        		Image image = resolver.getImage(tag, code);
	        		GC gc = new GC(image);
	        		ImageData frameData;
	        		
	        		if(frames[i] == -1) {
	        			frameData = loader.data[0];
	        			Image frame = new Image(richbox.getDisplay(), frameData);
	        			gc.drawImage(frame, 0,
	        					0,
	        					frameData.width,
	        					frameData.height,
	        					frameData.x,
	        					frameData.y,
	        					frameData.width,
	        					frameData.height);
	        			frames[i] = 0;
	        		} else {
	        			frameData = image.getImageData();  
	        			if(frameData.disposalMethod == SWT.DM_FILL_BACKGROUND) {
	        				gc.fillRectangle(frameData.x, 
	        						frameData.y,
	        						frameData.width,
	        						frameData.height);  								
	        			} else if(frameData.disposalMethod == SWT.DM_FILL_PREVIOUS) {
	        				Image frame = new Image(richbox.getDisplay(), frameData);								
	        				gc.drawImage(frame, 
	        						0, 
	        						0, 
	        						frameData.width, 
	        						frameData.height, 
	        						frameData.x, 
	        						frameData.y,
	        						frameData.width, 
	        						frameData.height);
	        				frame.dispose();
	        			} 
	        			
	        			frames[i] = (frames[i] + 1) % loader.data.length;	
	        			frameData = loader.data[frames[i]];	
	        			Image frame = new Image(richbox.getDisplay(), frameData);		
	        			gc.drawImage(frame, 
	        					0, 
	        					0, 
	        					frameData.width,
	        					frameData.height,
	        					frameData.x,
	        					frameData.y,
	        					frameData.width, 
	        					frameData.height);
	        			frame.dispose();			        			
	        		}
					gc.dispose();
					
					int ms = frameData.delayTime * 10;
					if(ms < 20)
						ms += 30;
					if(ms < 30)
						ms += 10;
	        		nextFrameTime[i] = time + ms;
	        	}
				richbox.redraw();
			} catch (Throwable e) {
			}
	    }
	};
	
    private RichBox richbox;
    private ImageResolver resolver;
    
    public static final int MAX = 5;
    
    private Integer[] keys;
    private int[] frames;
    private int[] nextFrameTime;
    private int time;
    
    private int pos;
    
    // 停止标志
    private volatile boolean stop;

    public AnimationThread(RichBox richbox, ImageResolver resolver) {
        this.richbox = richbox;
        this.resolver = resolver;
        keys = new Integer[MAX];
        frames = new int[MAX];
        nextFrameTime = new int[MAX];
        pos = 0;
        stop = false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Thread#run()
     */
    @Override
	public void run() {
        while(!isStop()) {            
            try {
                // wait frame
                synchronized(this) {
	                wait(200);                    
                }                
            } catch (InterruptedException e) {
                if(isStop())
                    break;
            }
            time += 200;

			try {
				richbox.getDisplay().asyncExec(redrawRunnable);
			} catch(Exception e) {
				break;
			}
        }
    }

    /**
     * 释放资源
     */
    public synchronized void dispose() {		
    	stop = true;
    	notify();
    }
    
    /**
     * @return Returns the stop.
     */
    public synchronized boolean isStop() {
        return stop;
    }

	/**
	 * 增加需要动画的表情key
	 * 
	 * @param key
	 */
	public synchronized void putImage(Integer key) {
		keys[pos] = key;
		frames[pos] = -1;
		nextFrameTime[pos] = 0;
		pos++;
		if(pos == MAX)
			pos = 0;
	}

	/**
	 * @param key
	 */
	public synchronized void remove(Integer key) {
		for(int i = 0; i < MAX; i++) {
			if(key.equals(keys[i])) {
				keys[i] = null;
				return;
			}
		}
	}
}