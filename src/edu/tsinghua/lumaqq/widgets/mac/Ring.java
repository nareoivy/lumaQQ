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
package edu.tsinghua.lumaqq.widgets.mac;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;

import edu.tsinghua.lumaqq.widgets.BaseComposite;

/**
 * 苹果风格busy指示器
 * 
 * @author luma
 */
public class Ring extends BaseComposite {
	// 圆形标志
	public static final int SIGN_CIRCLE = 0;
	
	// 圆形边界	
	public static final int BORDER_CIRCLE = 0;
	
	// 原点位置坐标
	private Point origin;
	
	// 标志个数，缺省8个
	private int signCount;
	// true表示按照顺时针旋转
	private boolean clockwise;
	// 当前的标志，根据旋转顺序，序号从0开始
	private int currentSign;
	// true表示当前正在旋转中
	private volatile boolean rotating;
	// 动画间隔
	private int interval;
	// true表示显示轨迹
	private boolean showTrack;
	// 轨迹的标志数
	private int trackSignCount;
	// 标志形状的外接圆半径
	private int signRadius;
	
	// 最大高度和宽度
	private int minHeight;
	private int minWidth;
	
	private int maxAvailableHeight;
	
	private Image tiledBackground;
	
	// 边界绘制接口
	private IBorderPainter borderPainter;
	// 标志绘制接口
	private ISignPainter signPainter;
	// 活动标志的颜色
	private Color activeForeground;
	
	// 缺省前景色和活动标志前景色，缺省背景
	private Color defaultForeground;
	private Color defaultActiveForeground;
	private Color defaultBackground;
	
	private Runnable redrawRunnable;
	
	public Ring(Composite parent) {
		super(parent, SWT.NO_BACKGROUND | SWT.NO_FOCUS | SWT.NO_REDRAW_RESIZE | SWT.DOUBLE_BUFFERED);
		
		// 初始化变量
		signCount = 8;
		clockwise = true;
		currentSign = 0;
		rotating = false;
		interval = 100;
		showTrack = true;
		signRadius = 2;
		minHeight = minWidth = -1;
		maxAvailableHeight = -1;
		trackSignCount = signCount - 2;
		defaultForeground = new Color(getDisplay(), 206, 206, 202);
		defaultActiveForeground = new Color(getDisplay(), 117, 117, 113);
		defaultBackground = getDisplay().getSystemColor(SWT.COLOR_WHITE);
		redrawRunnable = new Runnable() {
			public void run() {
				try {
					redraw();
				} catch (RuntimeException e) {
				}
			}
		};
		
		// 初始化监听器
		addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent e) {
				handlePaint(e);
			}
		});
		addControlListener(new ControlAdapter() {
			@Override
			public void controlResized(ControlEvent e) {
				handleResized(e);
			}
		});
		
		setSignShape(SIGN_CIRCLE);
		setBorderShape(BORDER_CIRCLE);
		setForeground(defaultForeground);
		setActiveSignForeground(defaultActiveForeground);
		setBackground(defaultBackground);
	}
	
	@Override
	public Point computeSize(int wHint, int hHint, boolean changed) {
		Point size = super.computeSize(wHint, hHint, changed);
		if(minHeight != -1 && size.y < minHeight)
			size.y = minHeight;
		if(minWidth != -1 && size.x < minWidth)
			size.x = minWidth;
		return size;
	}
	
	/**
	 * 开始旋转
	 */
	public synchronized void rotate() {
		rotate(null);
	}
	
	/**
	 * 开始旋转
	 * 
	 * @param tooltip
	 * 		提示文本
	 */
	public synchronized void rotate(String tooltip) {
		if(rotating)
			return;
		setToolTipText(tooltip);
		rotating = true;
		currentSign = 0;
		getDisplay().timerExec(0, redrawRunnable);
	}
	
	/**
	 * 停止旋转
	 */
	public synchronized void stop() {
		rotating = false;
	}
	
	/**
	 * 处理大小改变事件
	 * 
	 * @param e
	 */
	protected void handleResized(ControlEvent e) {
		if(origin == null)
			origin = new Point(0, 0);
		
		Point size = getSize();
		origin.x = size.x >> 1;
		origin.y = size.y >> 1;
		redraw();
	}

	/**
	 * 处理重画事件
	 * 
	 * @param e
	 */
	protected void handlePaint(PaintEvent e) {
		// Check if there is work to do
		if (e.height <= 0)
			return;
		
		// 得到gc
		GC gc = e.gc;
		
		// 画背景
		if(tiledBackground != null) {
			Rectangle imgBound = tiledBackground.getBounds();
			int x = e.x;
			while(x < e.x + e.width) {
				gc.drawImage(tiledBackground, 
						0,
						e.y,
						imgBound.width,
						imgBound.height - e.y,
						x,
						e.y,
						imgBound.width,
						imgBound.height - e.y);
				x += imgBound.width;
			}
		} else {
			gc.fillRectangle(getClientArea());
		}
		
		// 画
		borderPainter.draw(gc, signPainter, this);
		
		// 如果当前正在旋转，定时画
		synchronized(this) {
			if(rotating) {
				// 根据旋转方向调整活动标志序号
				if(clockwise) {
					currentSign--;
					if(currentSign < 0)
						currentSign = signCount - 1;
				} else
					currentSign = (currentSign + 1) % signCount;
				// 定时画
				getDisplay().timerExec(interval, redrawRunnable);
			}
		}
	}
	
	/**
	 * 设置边界形状
	 * 
	 * @param shape
	 * 		形状常量
	 */
	public void setBorderShape(int shape) {
		switch(shape) {
			case BORDER_CIRCLE:
				borderPainter = new CircleBorderPainter();
				break;
		}
	}

	/**
	 * 设置标志的形状
	 * 
	 * @param shape
	 * 		形状常量
	 */
	public void setSignShape(int shape) {
		switch(shape) {
			case SIGN_CIRCLE:
				signPainter = new CircleSignPainter();
				break;
		}
	}

	/**
	 * @return
	 * 		标志个数
	 */
	public int getSignCount() {
		return signCount;
	}

	/**
	 * 设置标志个数
	 * 
	 * @param count
	 * 		个数
	 */
	public void setSignCount(int count) {
		signCount = Math.max(0, count);
		trackSignCount = signCount - 2;
	}

	/**
	 * @return
	 * 		原点坐标
	 */
	public Point getOrigin() {
		return origin;
	}

	/**
	 * @return
	 * 		true表示顺时针
	 */
	public boolean isClockwise() {
		return clockwise;
	}

	/**
	 * 设置旋转方向
	 * 
	 * @param clockwise
	 * 		true表示顺时针
	 */
	public void setClockwise(boolean clockwise) {
		this.clockwise = clockwise;
	}

	/**
	 * @return Returns the currentSign.
	 */
	public int getCurrentSign() {
		return currentSign;
	}

	/**
	 * @return Returns the rotating.
	 */
	public synchronized boolean isRotating() {
		return rotating;
	}

	/**
	 * @return Returns the showTrack.
	 */
	public boolean isShowTrack() {
		return showTrack;
	}

	/**
	 * @param showTrack The showTrack to set.
	 */
	public void setShowTrack(boolean showTrack) {
		this.showTrack = showTrack;
	}

	/**
	 * @return Returns the trackSignCount.
	 */
	public int getTrackSignCount() {
		return trackSignCount;
	}

	/**
	 * @param count The trackSignCount to set.
	 */
	public void setTrackSignCount(int count) {
		trackSignCount = Math.min(signCount - 2, count);
		trackSignCount = Math.max(0, trackSignCount);
	}

	/**
	 * @return Returns the activeForeground.
	 */
	public Color getActiveSignForeground() {
		return activeForeground;
	}

	/**
	 * @param activeForeground The activeForeground to set.
	 */
	public void setActiveSignForeground(Color activeForeground) {
		this.activeForeground = activeForeground;
	}

	/**
	 * @return Returns the signRadius.
	 */
	public int getSignRadius() {
		return signRadius;
	}

	/**
	 * @param signRadius The signRadius to set.
	 */
	public void setSignRadius(int signRadius) {
		this.signRadius = signRadius;
	}

	/**
	 * @return Returns the interval.
	 */
	public int getInterval() {
		return interval;
	}

	/**
	 * @param interval The interval to set.
	 */
	public void setInterval(int interval) {
		this.interval = interval;
	}

	/**
	 * @return Returns the defaultActiveForeground.
	 */
	public Color getDefaultActiveForeground() {
		return defaultActiveForeground;
	}

	/**
	 * @return Returns the defaultBackground.
	 */
	public Color getDefaultBackground() {
		return defaultBackground;
	}

	/**
	 * @return Returns the defaultForeground.
	 */
	public Color getDefaultForeground() {
		return defaultForeground;
	}

	/**
	 * @return Returns the minHeight.
	 */
	public int getMinHeight() {
		return minHeight;
	}

	/**
	 * @param minHeight The minHeight to set.
	 */
	public void setMinHeight(int minHeight) {
		this.minHeight = minHeight;
	}

	/**
	 * @return Returns the minWidth.
	 */
	public int getMinWidth() {
		return minWidth;
	}

	/**
	 * @param minWidth The minWidth to set.
	 */
	public void setMinWidth(int minWidth) {
		this.minWidth = minWidth;
	}

	/**
	 * @return Returns the tiledBackground.
	 */
	public Image getTiledBackground() {
		return tiledBackground;
	}

	/**
	 * @param tiledBackground The tiledBackground to set.
	 */
	public void setTiledBackground(Image tiledBackground) {
		this.tiledBackground = tiledBackground;
	}

	/**
	 * @return Returns the maxBorderHeight.
	 */
	public int getMaxAvailableHeight() {
		return maxAvailableHeight;
	}

	/**
	 * @param maxBorderHeight The maxBorderHeight to set.
	 */
	public void setMaxAvailableHeight(int maxBorderHeight) {
		this.maxAvailableHeight = maxBorderHeight;
	}
}
