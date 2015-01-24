/*
* Shutter - Friend List Component
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
package edu.tsinghua.lumaqq.widgets.qstyle;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.custom.ControlEditor;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseTrackAdapter;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

import edu.tsinghua.lumaqq.widgets.BaseComposite;


/**
 * <pre>
 * Blind上的横条
 * 
 * <b>Style:</b>
 *      SWT.LEFT, SWT.RIGHT, SWT.CENTER
 * <b>Note:</b>
 * 		SWT.LEFT, SWT.RIGHT, 指定了SWT.CENTER属性
 *      相当于指定控件为Tab
 * </pre>
 *
 * @author luma
 */
public class Slat extends BaseComposite implements DisposeListener, PaintListener, ControlListener {	
    /**
     * 显示动画的线程
     * 
     * @author luma
     */
    public class Animate implements Runnable {
        private int frame;
        private Image[] frames;
        private volatile boolean stop;
        
        public void setFrames(Image[] images) {
            frame = 0;
            frames = images;
            stop = false;
        }        

        public void run() {
            try {                      
            	if(!stop) {
	                setImage(frames[frame]);
	                frame++;
	                if(frame >= frames.length)
	                    frame = 0;            		
            	}
                if(!stop)
                    getDisplay().timerExec(300, this);
            } catch (SWTException e) {         
                // 这个操作可能会抛出SWTException，如果组件已经dispose的话，
                // 所以我们需要捕捉这个异常，不然程序可能崩溃
            }
        }

		public void setStop(boolean stop) {
			this.stop = stop;
		}
    }

    /**
     * 闪烁图标的线程
     * 
     * @author luma
     */
    private class Blink implements Runnable {
        private Image blinkImage;
        private boolean flag;
        private volatile boolean stop;

        public void run() {
            try {                      
            	if(!stop) {
		            if(flag)
		                setImage(blinkImage);
		            else
		                setImage(null);
		            flag = !flag;            		
            	}
	            if(!stop)
	                getDisplay().timerExec(500, this);
            } catch (SWTException e) {         
                // 这个操作可能会抛出SWTException，如果组件已经dispose的话，
                // 所以我们需要捕捉这个异常，不然程序可能崩溃
            }
        }
        
        public void setStop(boolean stop) {
        	this.stop = stop;
        }
        
		public void setBlinkImage(Image blinkImage) {
			this.blinkImage = blinkImage;
            this.flag = true;
            this.stop = false;
		}
    }

    /**
     * 跳动图标的线程
     * 
     * @author luma
     */
    private class Bounce implements Runnable {
    	private volatile boolean stop;
        private int frame;
        private int[][] offset = new int[][] {
                {-1, 1}, {0, 0}, {1, 1}, {0, 0}
        };
        
        public void setBounceImage(Image bounceImage) {
            this.frame = 0;
            this.stop = false;
            setImage(bounceImage);
        }

        public void run() {
        	try {
	        	if(!stop) {
		            // 设置偏移
		            bX = offset[frame][0];
		            bY = offset[frame][1];
		            frame++;
		            if(frame >= offset.length)
		                frame = 0;
		            // 重画
		            redraw();        		
	        	}
	            if(!stop)
	                getDisplay().timerExec(300, this);        		
            } catch (SWTException e) {         
                // 这个操作可能会抛出SWTException，如果组件已经dispose的话，
                // 所以我们需要捕捉这个异常，不然程序可能崩溃
            }
        }

		public void setStop(boolean stop) {
			this.stop = stop;
		}
    }
    
    /**
     * 缺省的鼠标进入进出动作
     * 
     * @author luma
     */
    private class SlatMouseTrackListener extends MouseTrackAdapter {        
        @Override
		public void mouseEnter(MouseEvent e) {
            enter = true;
            refreshForeground();
            redraw();
        }

        @Override
		public void mouseExit(MouseEvent e) {
            enter = false;
            refreshForeground();
            redraw();
        }
    }
    
    /**
     * 缺省的鼠标动作
     * 
     * @author luma
     */
    private class SlatMouseListener extends MouseAdapter {
        @Override
		public void mouseDown(MouseEvent e) {
            up = false;
            redraw();        		
        }
        
        @Override
		public void mouseUp(MouseEvent e) {
            up = true;
            redraw(); 
        	forceFocus();
        }
    }
    
	// small and large image
	private Image image, imageBak;
	// image bound
	private Rectangle imageBound;
	// style
	private int style;
	// 是否按下
	private boolean up;
	// 鼠标是否进入控件区域
	private boolean enter;
	// 文字
	private String text;
	// 是否显示文字
	private boolean showText;
	// 文字的朝向
	private int textOrientation;
	// 文本编辑器
	private ControlEditor editor;
	// 事件监听器
	private List<ISlatListener> listeners;
	// 鼠标进入控件范围时显示的文字颜色
	private Color mouseTrackColor;
	// 表示是否正在闪烁或者跳动图标
	private boolean doEffect, blinking, bouncing, animating;
	// 跳动一个图标时，图标的偏移量
	private int bX, bY;
	// 图像和文字的外围矩形
	private Rectangle totalBound;
	// 客户区大小
	private Rectangle clientArea;
	// 老的前景色
	private Color oldForeground;
	// 特效任务
	private Blink blinkRunnable;
	private Bounce bounceRunnable;
	private Animate animateRunnable;
	
	private Color topColor;
	private Color bottomColor;
	private Color textColor;
	private Color borderColor;
	private Color underlayColor;
	private Color outBorderColor;
	private Color hoverTopColor;
	private Color hoverBottomColor;
	private Color downTopColor;
	private Color downBottomColor;

	public Slat(Composite parent) {
		this(parent, SWT.CENTER | SWT.NO_BACKGROUND | SWT.DOUBLE_BUFFERED);
	}
	
    /**
     * @param parent parent
     * @param style style
     */
    public Slat(Composite parent, int style) {
		super(parent, SWT.NONE | SWT.NO_BACKGROUND | SWT.DOUBLE_BUFFERED);
        init(style, null, null);
    }
    
	/**
	 * @param parent composite
	 * @param style style
	 * @param text text string
	 */
	public Slat(Composite parent, int style, String text) {
		super(parent, SWT.NONE | SWT.NO_BACKGROUND | SWT.DOUBLE_BUFFERED);
	    init(style, text, null);
	}
	
	/**
	 * @param parent parent
	 * @param style style
	 * @param text text string
	 * @param smallImage small image
	 * @param largeImage large image
	 */
	public Slat(Composite parent, int style, String text, Image image) {
		super(parent, SWT.NONE | SWT.NO_BACKGROUND | SWT.DOUBLE_BUFFERED);
		init(style, text, image);
	}

    /**
     * @param _style
     * @param _text
     * @param smallImage2
     * @param largeImage2
     */
    private void init(int _style, String _text, Image _image) {
		// 初始化image
        this.image = this.imageBak = _image;
        if(_image != null)
            imageBound = _image.getBounds();
        else 
        	imageBound = new Rectangle(0, 0, 0, 0);
		// 检查style
		checkStyle(_style);
		// 初始化变量
		up = true;
		enter = false;
		doEffect = blinking = bouncing = animating = false;
		showText = true;
		if(_text == null)
		    _text = "";
		this.text = _text;
		listeners = new ArrayList<ISlatListener>();
		bX = bY = 0;
		totalBound = new Rectangle(1, 1, 0, 0);
		refreshTotalBound();
		blinkRunnable = new Blink();
		bounceRunnable = new Bounce();
		animateRunnable = new Animate();
		topColor = new Color(getDisplay(), 0xD8, 0xF1, 0xFF);
		bottomColor = new Color(getDisplay(), 0xA8, 0xD8, 0xFF);
		textColor = new Color(getDisplay(), 0x1E, 0x3E, 0x93);
		borderColor = new Color(getDisplay(), 0x5E, 0x85, 0xB1);
		underlayColor = new Color(getDisplay(), 0xBB, 0xF4, 0xFF);
		outBorderColor = new Color(getDisplay(), 0xC6, 0xD4, 0xE3);
		hoverTopColor = new Color(getDisplay(), 0xF6, 0xFC, 0xFF);
		hoverBottomColor = new Color(getDisplay(), 0xC0, 0xE4, 0xFF);
		downTopColor = new Color(getDisplay(), 0x9B, 0xC8, 0xEE);
		downBottomColor = new Color(getDisplay(), 0xC4, 0xE3, 0xF4);
        // set layout
    	GridLayout layout = new GridLayout();
    	layout.horizontalSpacing = layout.verticalSpacing = 0;
    	layout.marginHeight = layout.marginWidth = 0;
        this.setLayout(layout);
        setBackground(getParent().getBackground());
		// 添加监听器
		addDisposeListener(this);
		addPaintListener(this);
		addControlListener(this);
		super.addMouseListener(new SlatMouseListener());
		super.addMouseTrackListener(new SlatMouseTrackListener());
    }
    
    /**
     * 处理编辑时焦点丢失事件
     * 
     * @param e
     */
    protected void onFocusLost(FocusEvent e) {
		// new a event object
		SlatEvent event = new SlatEvent(this);
		event.oldText = getText();
		if(event.oldText == null)
		    event.oldText = "";
		
		// set new text
		Text t = (Text)e.getSource();
		setText(t.getText());
		disposeEditor();
		
		// if text changed, fire event
		if(!event.oldText.equals(getText())) {
			event.newText = getText();
			for(ISlatListener listener : listeners)
				listener.textChanged(event);
		}
	}

	/**
     * 处理编辑时按键按下事件
     * 
     * @param e
     */
    protected void onKeyPressed(KeyEvent e) {
		if(e.keyCode == SWT.CR) {
			// new a event object
			SlatEvent event = new SlatEvent(this);
			event.oldText = getText();
			if(event.oldText == null)
			    event.oldText = "";
			
			// set new text
			Text t = (Text)e.getSource();
			setText(t.getText());
			disposeEditor();
			
			// if text changed, fire event
			if(!event.oldText.equals(getText())) {
				event.newText = getText();
				for(ISlatListener listener : listeners)
					listener.textChanged(event);
			}
		} else if(e.keyCode == SWT.ESC) {
			disposeEditor();
		}
	}
    
    /**
     * 释放编辑器资源
     */
    private void disposeEditor() {
    	if(editor != null) {    		
    		if(editor.getEditor() != null) 
    			editor.getEditor().dispose();
    		editor.dispose();
    		editor = null;
    	}
    }

	/**
     * 检查style
     * 
     * @param _style
     */
    private void checkStyle(int _style) {
		// 检查style，只允许下列值
		this.style = _style & (SWT.LEFT | SWT.RIGHT | SWT.CENTER);
		// 检查style看看文本位置设置了没有，没有则用缺省的right
	    textOrientation = this.style & (SWT.LEFT | SWT.RIGHT | SWT.CENTER);
	    if(textOrientation == 0) 
	    	textOrientation = SWT.CENTER;
    }
    
    /**
     * 添加一个listener
     * @param listener
     */
    public void addSlatListener(ISlatListener listener) {
        listeners.add(listener);
    }
    
    /**
     * 移除一个listener
     * @param listener
     */
    public void removeShutterLabelListener(ISlatListener listener) {
        listeners.remove(listener);
    }
    
    /**
     * 使编辑框显示出来，以便于编辑文本
     */
    public void editText() {
    	if(editor == null) {
    		editor = new ControlEditor(this);
    		editor.grabHorizontal = editor.grabVertical = true;
    	}
    	Text t = new Text(this, SWT.NONE);
    	t.setText(text);
    	t.selectAll();
    	t.addKeyListener(new KeyAdapter() {
    		@Override
			public void keyPressed(KeyEvent e) {
    			onKeyPressed(e);
    		}
    	});
    	t.addFocusListener(new FocusAdapter() {
    		@Override
			public void focusLost(FocusEvent e) {
    			onFocusLost(e);
    		}
    	});
    	editor.setEditor(t);
    	t.setFocus();
    }
    
    /**
     * 设置是否显示文字
     * @param show true表示显示文字
     */
    public void setShowText(boolean show) {
    	if(showText != show) {
    		showText = show;
    		redraw();
    	}
    }
    
    /**
     * 设置鼠标进入控件范围时显示的文字颜色
     * @param color 鼠标进入控件范围时显示的文字颜色
     */
    public void setMouseTrackColor(Color color) {
        this.mouseTrackColor = color;
    }
 
    /* (non-Javadoc)
	 * @see org.eclipse.swt.events.DisposeListener#widgetDisposed(org.eclipse.swt.events.DisposeEvent)
	 */
	public void widgetDisposed(DisposeEvent e) {
		disposeEditor();
		image = imageBak = null;
		imageBound = null;
		text = null;
		listeners = null;
		mouseTrackColor = null;
		totalBound = null;
		clientArea = null;
		oldForeground = null;
		blinkRunnable = null;
		bounceRunnable = null;
		animateRunnable = null;
		topColor.dispose();
		bottomColor.dispose();
		borderColor.dispose();
		textColor.dispose();
		outBorderColor.dispose();
		underlayColor.dispose();
		hoverTopColor.dispose();
		hoverBottomColor.dispose();
		downTopColor.dispose();
		downBottomColor.dispose();
	}

    /* (non-Javadoc)
     * @see org.eclipse.swt.events.PaintListener#paintControl(org.eclipse.swt.events.PaintEvent)
     */
    public void paintControl(PaintEvent event) {
        // 得到客户区大小
        if(clientArea.width == 0 || clientArea.height == 0)
            return;
        
        GC gc = event.gc;
        
        // 填充背景        
        gc.setBackground(getBackground());
        gc.fillRectangle(clientArea);
        
        // 画衬底
        gc.setBackground(underlayColor);
        gc.fillRectangle(1, 2, clientArea.width - 2, clientArea.height - 3);
        
        // 画主色
        if(!up) {
        	gc.setForeground(downTopColor);
        	gc.setBackground(downBottomColor);
        } else if(enter) {
        	gc.setForeground(hoverTopColor);
        	gc.setBackground(hoverBottomColor);
        } else {
        	gc.setForeground(topColor);
        	gc.setBackground(bottomColor);        	
        }
        gc.fillGradientRectangle(2, 
        		2, 
        		clientArea.width - 4, 
        		clientArea.height - 4, 
        		true);
        
        // 画外边框
        gc.setForeground(outBorderColor);
        gc.drawRoundRectangle(0, 
        		0, 
        		clientArea.width - 1, 
        		clientArea.height - 1, 
        		4, 
        		4);
        
        // 画内边框
        gc.setForeground(borderColor);        
        gc.drawRoundRectangle(0, 
        		0, 
        		clientArea.width - 1, 
        		clientArea.height - 1, 
        		6, 
        		6);
        
        // 画图标，先计算图标位置
        int availableHeight = getAvailableHeight();
        int x = 0, y = 0;
        // 不为null则画图标，这里画完后，x的值将为
        if(image != null) { 
        	switch(textOrientation) {
        		case SWT.RIGHT:
        		case SWT.CENTER:
        			x = 2;
        			y = 2;
        			break;
        		case SWT.LEFT:
        			x = clientArea.width - 2 - imageBound.width;
        			y = 2;
        			break;
        	}
        	int drawWidth = (imageBound.width > availableHeight) ? availableHeight : imageBound.width;
        	if(imageBound.height > availableHeight)
        		gc.drawImage(image, 
        				0, 
        				0, 
        				imageBound.width, 
        				imageBound.height, 
        				x + bX, 
        				y + bY, 
        				drawWidth, 
						availableHeight);
        	else
        		gc.drawImage(image, 
        				0, 
        				0, 
        				imageBound.width, 
        				imageBound.height, 
        				x + bX, 
        				y + bY + ((availableHeight - imageBound.height) >>> 1), 
        				drawWidth, 
        				imageBound.height);
            x = availableHeight;
        }
        
        // 画文字
        if(!showText)        	
        	return;
        
        // 计算可用宽度，如果宽度不够，截短文字
        boolean shorten = false;
        String t = text;
        int availableWidth = clientArea.width - 2;
        Point extent = getTotalSize();
        if(extent.x > availableWidth) 
            shorten = true;
        // 如果需要截短文字，得到截短后的文字
        if(shorten) 
            t = shortenText(gc, t, availableWidth);

        int h = gc.getFontMetrics().getHeight();
        int w = gc.textExtent(t, SWT.DRAW_TRANSPARENT).x;

        // 设置文本前景色，如果设置了鼠标hover时的文本色，则使用
        if(!isEnabled())
        	gc.setForeground(getDisplay().getSystemColor(SWT.COLOR_DARK_GRAY));
        else if(enter && mouseTrackColor != null)
        	gc.setForeground(mouseTrackColor);
        else
        	gc.setForeground(textColor);
        
        if(doEffect) 
        	x = imageBound.width;
        switch(textOrientation) {
        	case SWT.LEFT:
        		gc.drawString(t, clientArea.width - 1 - x - 5 - w, (clientArea.height - h) / 2, true);
        		break;
        	case SWT.RIGHT:
        		gc.drawString(t, 1 + x + 5, (clientArea.height - h) / 2, true);
        		break;
        	case SWT.CENTER:
        		gc.drawString(t, (clientArea.width - w) / 2, (clientArea.height - h) / 2, true);
        		break;
        }
    }
    
    /**
     * @return
     * 		可用高度
     */
    private int getAvailableHeight() {
    	if(clientArea == null)
    		clientArea = getClientArea();
    	return clientArea.height - 4;
	}

	/**
     * 计算截短的字符串，从中间开始不断减少，中间填省略号，直到宽度合适未知
     * 
     * @param gc
     * @param t
     * @param width
     * @return
     */
    protected String shortenText(GC gc, String t, int width) {
        if(t == null)
            return null;
        int w = gc.textExtent("...").x;
        int l = t.length();
        int pivot = l / 2;
        int s = pivot;
        for(int e = pivot + 1; s >= 0 && e < l; e++) {
            String s1 = t.substring(0, s);
            String s2 = t.substring(e, l);
            int l1 = gc.textExtent(s1).x;
            int l2 = gc.textExtent(s2).x;
            if(l1 + w + l2 < width) {
                t = s1 + "..." + s2;
                break;
            }
            s--;
        }

        return t;
    }
    
    /**
     * @return Returns the style.
     */
    @Override
	public int getStyle() {
        return style;
    }
    
    /* (non-Javadoc)
     * @see org.eclipse.swt.widgets.Control#computeSize(int, int, boolean)
     */
	@Override
    public Point computeSize(int wHint, int hHint, boolean changed) {
        Point e = getTotalSize();
        if(wHint == SWT.DEFAULT)
            e.x += 2;
        else
            e.x = wHint;
        if(hHint == SWT.DEFAULT)
            e.y += 2;
        else
            e.y = hHint;
        return e;
    }
    
    /**
     * 得到image和text的总大小
     * 
     * @return
     * 		组件大小
     */
    private Point getTotalSize() {
        Point size = new Point(0, 0);
        size.y = getAvailableHeight();
        size.x = (image == null) ? 0 : size.y;
        
        GC gc = new GC(this);
        if(text.length() > 0 && showText) {
            Point e = gc.textExtent(text, SWT.DRAW_TRANSPARENT);
            // 在文字两旁留5个象素的空隙
            size.x += e.x + 10;
        } 
        size.y = Math.max(size.y, gc.getFontMetrics().getHeight() + 4);
        gc.dispose();
        return size;
    }
    
    /**
     * @return Returns the text.
     */
    public String getText() {
        return text;
    }
    
    /**
     * @param text The text to set.
     */
    public void setText(String text) {
        if(text == null)
            text = "";
        else
        	this.text = text;
        if(showText) {
            // 重新设置image和text的总体外围矩形
            refreshTotalBound();
            // 重画
	        redraw();            
        }
    }
    
    /**
     * @return Returns the textOrientation.
     */
    public int getTextOrientation() {
        return textOrientation;
    }
    
    /**
     * @param textOrientation The textOrientation to set.
     */
    public void setTextOrientation(int textOrientation) {
        this.textOrientation = textOrientation;
        refreshTotalBound();
        redraw();
    }
    
    /**
     * @return Returns the image.
     */
    public Image getImage() {
        return image;
    }
    
    /**
     * @param image The image to set.
     */
    public void setImage(Image image) {
        this.image = image;
        if(image != null) 
        	this.imageBound = image.getBounds();
        
        // 重新设置image和text的总体外围矩形
        refreshTotalBound();
        // 重画
        redraw();
    }
    
    /**
     * 闪烁一个图标
     * @param blinkImage 要闪烁的图标
     */
    public void startBlinkImage(Image blinkImage) {
        if(doEffect || blinkImage == null) return;
        doEffect = blinking = true;
        this.getParent().layout();
        imageBak = image;
        blinkRunnable.setBlinkImage(blinkImage);
        getDisplay().timerExec(0, blinkRunnable);
    }
    
    /**
     * 停止闪烁图标
     */
    public void stopBlinkImage() {
        if(!blinking) return;
        blinkRunnable.setStop(true);
        doEffect = blinking = false;        
        setImage(imageBak);
        this.getParent().layout();
    }
    
    /**
     * 跳动一个图标
     * @param bounceImage 要跳动的图标
     */
    public void startBounceImage(Image bounceImage) {        
        if(doEffect || bounceImage == null) return;
        doEffect = bouncing = true;
        this.getParent().layout();
        imageBak = image;
        bounceRunnable.setBounceImage(bounceImage);
        getDisplay().timerExec(0, bounceRunnable);
    }
    
    /**
     * 停止跳动图标
     */
    public void stopBounceImage() {
        if(!bouncing) return;
        bounceRunnable.setStop(true);
        doEffect = bouncing = false;
        bX = bY = 0;
        setImage(imageBak);
        this.getParent().layout();
    }
    
    /**
     * 开始动画
     * @param images 动画帧
     */
    public void startAnimate(Image[] images) {
        if(doEffect || images == null) return;
        doEffect = animating = true;
        this.getParent().layout();
        imageBak = image;
        animateRunnable.setFrames(images);
        getDisplay().timerExec(0, animateRunnable);
    }
    
    /**
     * 停止动画
     */
    public void stopAnimate() {
        if(!animating) return;
        animateRunnable.setStop(true);
        doEffect = animating = false;
        setImage(imageBak);
        this.getParent().layout();
    }
    
    // 重新设置image和text的总体外围矩形
    private void refreshTotalBound() {
        Point extent = getTotalSize();
        totalBound.width = extent.x;
        totalBound.height = extent.y;
		clientArea = getClientArea();
		if(textOrientation == SWT.LEFT)
			totalBound.x = clientArea.width - totalBound.width;
		else
			totalBound.x = 2;
    }
    
    // 根据鼠标是否在图像和文字之上重新设置前景色
    private void refreshForeground() {
		if(mouseTrackColor != null) {
			if(oldForeground == null) oldForeground = getForeground();
			if(enter) 
				setForeground(mouseTrackColor);						
			else {
				setForeground(oldForeground);
				oldForeground = null;
			}
		}
    }
    
    /* (non-Javadoc)
     * @see org.eclipse.swt.widgets.Control#setEnabled(boolean)
     */
	@Override
    public void setEnabled(boolean enabled) {
    	super.setEnabled(enabled);
    	redraw();
    }
    
    /**
     * 设置选择状态
     * @param selection
     */
    public void setSelection(boolean selection) {
    	if(this.up == !selection) return;
    	this.up = !selection;
    	redraw();
    }
    
    /**
     * 返回当前选择状态
     * @return true如果被按下
     */
    public boolean getSelection() {
    	return !up;
    }
    
    /* (non-Javadoc)
	 * @see org.eclipse.swt.events.ControlListener#controlMoved(org.eclipse.swt.events.ControlEvent)
	 */
	public void controlMoved(ControlEvent e) {
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.events.ControlListener#controlResized(org.eclipse.swt.events.ControlEvent)
	 */
	public void controlResized(ControlEvent e) {
		refreshTotalBound();
	}
	
	/**
	 * @return Returns the animating.
	 */
	public boolean isAnimating() {
		return animating;
	}
	
	/**
	 * @return Returns the blinking.
	 */
	public boolean isBlinking() {
		return blinking;
	}
	
	/**
	 * @return Returns the bouncing.
	 */
	public boolean isBouncing() {
		return bouncing;
	}
	
	/**
	 * @return Returns the showText.
	 */
	public boolean isShowText() {
		return showText;
	}
}
