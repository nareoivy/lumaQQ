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
package edu.tsinghua.lumaqq.widgets.rich2;

import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

/**
 * 显示动画的组件
 *
 * @author luma
 */
public class Animator extends Composite implements IReplicate {
	private Listener listener;
	
	private ImageLoader loader;
	private int repeatCount;
	private ImageData currentData;
	private Image image;
	private int dataIndex;
	private Image bufferImage;
	
	// true表示停止动画
	private boolean stop;
	
	// true表示是第一次开始画
	private boolean firstRepeat;
	
	// 重画动作
	private Runnable redrawAction = new Runnable() {
		public void run() {
			try {
				redraw();
			} catch(SWTException e) {
			}
		}
	};

	private boolean useGIFBackground;
	
	/**
	 * 构造函数
	 * 
	 * @param parent
	 */
	public Animator(Composite parent) {
		super(parent, SWT.NONE | SWT.NO_MERGE_PAINTS);
		
		installListener();
	}

	/**
	 * 初始化监听器
	 */
	private void installListener() {
		listener = new Listener() {
			public void handleEvent(Event event) {
				switch(event.type) {
					case SWT.Dispose:
						handleDispose(event);
						break;
					case SWT.Resize:
						handleResize(event);
						break;
					case SWT.Paint:
						handlePaint(event);
						break;
				}
			}
		};
		addListener(SWT.Dispose, listener);
		addListener(SWT.Resize, listener);
		addListener(SWT.Paint, listener);
	}

	protected void handleResize(Event event) {
		Rectangle rect = getClientArea();
		if(bufferImage != null && !bufferImage.isDisposed())
			bufferImage.dispose();
		if(rect.height > 0 && rect.width > 0) {
			bufferImage = new Image(getDisplay(), rect.width, rect.height);
			redraw();			
		}
	}

	protected void handlePaint(Event e) {
		if(e.width <= 0 || e.height <= 0 || loader == null || repeatCount == 0)
			return;
		
		if(loader.data.length <= 1) {
			image = new Image(getDisplay(), loader.data[0]);
			e.gc.drawImage(image, 0, 0);
			image.dispose();
			return;
		}
		
		boolean b = isStop();
		
		Rectangle rect = getClientArea();
		int offsetX = (rect.width - loader.logicalScreenWidth) >> 1;
		int offsetY = (rect.height - loader.logicalScreenHeight) >> 1;
		
		GC gc = new GC(bufferImage);
		
		if(firstRepeat) {
			firstRepeat = false;
			releaseImage();
			image = new Image(getDisplay(), currentData);
			gc.drawImage(image, 
					0,
					0,
					currentData.width,
					currentData.height,
					offsetX + currentData.x,
					offsetY + currentData.y,
					currentData.width,
					currentData.height);
		} else if(!b) {
			// 消除上一帧
			switch(currentData.disposalMethod) {
				case SWT.DM_FILL_BACKGROUND:
					/*
					 * Fill with the background color
					 * before drawing.
					 */
					Color bgColor = null;
					if(useGIFBackground && loader.backgroundPixel != -1) {
						bgColor = new Color(getDisplay(), currentData.palette.getRGB(loader.backgroundPixel));
					}
					gc.setBackground(bgColor != null ? bgColor : getBackground());
					gc.fillRectangle(offsetX + currentData.x, offsetY + currentData.y, currentData.width, currentData.height);
					if(bgColor != null)
						bgColor.dispose();
					break;
				case SWT.DM_FILL_PREVIOUS:
					/*
					 * Restore the previous image before
					 * drawing.
					 */
					gc.drawImage(image, 
							0, 
							0, 
							currentData.width, 
							currentData.height,
							offsetX + currentData.x, 
							offsetY + currentData.y,
							currentData.width, 
							currentData.height);
					break;
			}
			
			// 画下一帧
			dataIndex = (dataIndex + 1) % loader.data.length;
			currentData = loader.data[dataIndex];
			releaseImage();
			image = new Image(getDisplay(), currentData);
			gc.drawImage(image, 
					0, 
					0, 
					currentData.width, 
					currentData.height,
					offsetX + currentData.x, 
					offsetY + currentData.y,
					currentData.width, 
					currentData.height);
		}
		
		// 减少重复次数
		if(!b) {
			if(dataIndex == loader.data.length - 1)
				repeatCount--;			
		}
		
		e.gc.drawImage(bufferImage, 0, 0);
		gc.dispose();
		
		// 设定下一次重画的时间
		if(!b)
			resetTimer();
	}
	
	/**
	 * 停止动画
	 */
	public void stopAnimation() {
		setStop(true);
	}
	
	/**
	 * 恢复动画
	 */
	public void resumeAnimation() {
		if(isStop()) {
			setStop(false);
			redraw();			
		}
	}

	protected void handleDispose(Event event) {
		removeListener(SWT.Dispose, listener);
		if(bufferImage != null && !bufferImage.isDisposed())
			bufferImage.dispose();
	}
	
	private void releaseImage() {
		if(image != null && !image.isDisposed()) {
			image.dispose();
			image = null;
		}
	}
	
	/**
	 * 设定下一次重画的时间
	 */
	private void resetTimer() {
		if(!isStop()) {
			int ms = currentData.delayTime * 10;
			if(ms < 20)
				ms += 30;
			if(ms < 30)
				ms += 10;
			getDisplay().timerExec(ms, redrawAction);			
		}
	}

	/**
	 * @return the loader
	 */
	public ImageLoader getLoader() {
		return loader;
	}

	/**
	 * @param loader the loader to set
	 */
	public void setLoader(ImageLoader loader) {
		this.loader = loader;
		repeatCount = loader.repeatCount;
		if(repeatCount == 0)
			repeatCount = -1;
		dataIndex = 0;
		currentData = loader.data[dataIndex];
		firstRepeat = true;
		useGIFBackground = false;
	}

	/* (non-Javadoc)
	 * @see edu.tsinghua.lumaqq.widgets.rich2.IReplicate#replicate()
	 */
	public Object replicate() {
		Animator ani = new Animator(getParent());
		ani.setBackground(getBackground());
		ani.setLoader(loader);
		ani.setSize(getSize());
		return ani;
	}

	/**
	 * @return the stop
	 */
	public synchronized boolean isStop() {
		return stop;
	}

	/**
	 * @param stop the stop to set
	 */
	public synchronized void setStop(boolean stop) {
		this.stop = stop;
	}
}
