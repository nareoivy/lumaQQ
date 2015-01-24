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
package edu.tsinghua.lumaqq.test;

import edu.tsinghua.lumaqq.widgets.qstyle.Bubble;

import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * @author luma
 */
@SuppressWarnings("unused")
public class AnimationTest {
    private Shell shell;
    private Display display;
    private ImageLoader loader;
    private Bubble bubble;
    private boolean animate;
    private Thread animateThread;
    private Color canvasBackground;
    private int imageDataIndex;
    private GC offScreenImageGC;
    private Composite comp;
    private Image buffer;
     
    public static void main(String[] args) {
        AnimationTest t = new AnimationTest();
        t.open();
    }
    
	/**
	 * 打开对话框
	 */
	public void open()	{		
		// event loop
	    shell = new Shell(new Display());
	    display = shell.getDisplay();
	    shell.setLayout(new GridLayout());
	    shell.setSize(400, 300);
	    shell.setBackground(display.getSystemColor(SWT.COLOR_WHITE));
	    
	    loader = new ImageLoader();
		loader.load("src/edu/tsinghua/lumaqq/face/0.gif");
		animate = false;
		imageDataIndex = 0;
		buffer = new Image(display, 20, 20);
		offScreenImageGC = new GC(buffer);
		canvasBackground = shell.getBackground();
		offScreenImageGC.setBackground(canvasBackground);
		
		comp = new Composite(shell, SWT.NO_BACKGROUND);
		comp.setLayoutData(new GridData(GridData.FILL_BOTH));		
		
		shell.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				animate();
			}
		});
		
		comp.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent e) {
				e.gc.fillRectangle(comp.getClientArea());
				if(buffer != null)
					e.gc.drawImage(buffer, 10, 10);
			}
		});
	    shell.layout();
	    shell.open();
		while(!shell.isDisposed()) 
			if(!display.readAndDispatch()) 
			    display.sleep();
	}
	
	void animate() {
		animate = !animate;
		if (animate ) {
			animateThread = new Thread() {
				@Override
				public void run() {					
					// Animate.
					try {
						animateLoop();
					} catch (SWTException e) {
						System.out.print(e);
					}
				}
			};
			animateThread.setDaemon(true);
			animateThread.start();
		}
	}
	
	void animateLoop() {

		while(true) {
			ImageData imageData = loader.data[imageDataIndex];	
			ImageData previous = (imageDataIndex > 0) ? loader.data[imageDataIndex - 1] : loader.data[loader.data.length - 1];
			Image frame = new Image(display, imageData);								
			if(previous.disposalMethod == SWT.DM_FILL_BACKGROUND) {
				offScreenImageGC.fillRectangle(previous.x, 
						previous.y,
						previous.width,
						previous.height);  								
			} else if(previous.disposalMethod == SWT.DM_FILL_PREVIOUS) {
				Image previousImage = new Image(display, previous);
				offScreenImageGC.drawImage(previousImage, 
						0, 
						0, 
						previous.width, 
						previous.height, 
						previous.x, 
						previous.y,
						previous.width, 
						previous.height);
				previousImage.dispose();
			} else if(imageDataIndex == 0)
				offScreenImageGC.fillRectangle(frame.getBounds());
			
			offScreenImageGC.drawImage(frame, 
					0, 
					0, 
					imageData.width,
					imageData.height,
					imageData.x,
					imageData.y,
					imageData.width, 
					imageData.height);
			frame.dispose();	
				
			// Draw the off-screen image to the screen.
			display.syncExec(new Runnable() {
				public void run() {
					comp.redraw();					
				}
			});
			
			imageDataIndex++;
			imageDataIndex %= loader.data.length;
			
			// Sleep for the specified delay time before drawing again.
			try {
				Thread.sleep(visibleDelay(imageData.delayTime * 10));
			} catch (InterruptedException e) {
			}
			
		}
	}
	
	static int visibleDelay(int ms) {
		if (ms < 20) return ms + 30;
		if (ms < 30) return ms + 10;
		return ms;
	}
}
