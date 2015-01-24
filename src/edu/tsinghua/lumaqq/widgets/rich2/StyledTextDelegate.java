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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.PaintObjectEvent;
import org.eclipse.swt.custom.PaintObjectListener;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.GlyphMetrics;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import edu.tsinghua.lumaqq.widgets.rich.LineStyle;

/**
 * StyledText的代理类，封装一些方法
 *
 * @author luma
 */
public class StyledTextDelegate {
	public static final String DEFAULT_FACE_TAG = "\u0014";
	public static final String CUSTOM_HEAD_TAG = "\u0016";
	public static final String CUSTOM_FACE_TAG = "\u0015";
	
	private StyledText st;
	private Map<Point, StyleObject> objects;
	private List<Entry<Point, StyleObject>> temp;
	private List<StyleObject> copyObject;
	private String copyString;
	private List<Integer> objectOffset;
	private List<StyleObject> deferredDispose;
	private Clipboard clipboard;
	
	private Font font;
	
	// 用户自定义按键的映射表，key是按键值，vlaue是Runnable对象
	private Map<Integer, Runnable> userActionMap;
	
	// 垂直卷滚条的上一次位置
	private Map<StyleObject, Integer> objectY;
	private Composite container;
	
	/**
	 * 构造函数
	 */
	public StyledTextDelegate() {
		objects = new HashMap<Point, StyleObject>();
		temp = new ArrayList<Entry<Point,StyleObject>>();
		copyObject = new ArrayList<StyleObject>();
		objectOffset = new ArrayList<Integer>();
		deferredDispose = new ArrayList<StyleObject>();
		userActionMap = new HashMap<Integer, Runnable>();
		clipboard = new Clipboard(Display.getCurrent());
		objectY = new HashMap<StyleObject, Integer>();
	}
	
	/**
	 * @return
	 * 		StyledText对象
	 */
	public StyledText getStyledText() {
		return st;
	}
	
	/**
	 * @return
	 * 		一个可发送的字符串
	 */
	public String getText() {
		return st.getText();
	}
	
	/**
	 * @return
	 * 		st的容器
	 */
	public Composite getContainer() {
		return container;
	}
	
	/**
	 * 创建StyledText
	 * 
	 * @param parent
	 * 		父容器
	 * @param style
	 * 		样式
	 * @param layoutData
	 * 		布局
	 */
	public void createStyledText(Composite parent, int style, Object layoutData) {
		container = new Composite(parent, SWT.NONE);
		if(layoutData != null)
			container.setLayoutData(layoutData);
		container.setLayout(new GridLayout());
		st = new StyledText(container, style);
		st.setLayoutData(new GridData(GridData.FILL_BOTH));
		st.addVerifyListener(new VerifyListener() {
			public void verifyText(VerifyEvent e) {
				int start = e.start;
				int replaceCharCount = e.end - e.start;
				int newCharCount = e.text.length();
				temp.clear();
				for(Iterator<Entry<Point, StyleObject>> i = objects.entrySet().iterator(); i.hasNext(); ) {
					Entry<Point, StyleObject> entry = i.next();
					Point range = entry.getKey();
					if(start + replaceCharCount <= range.x) {
						i.remove();
						range.x += newCharCount - replaceCharCount;
						temp.add(entry);
					} else if(start < range.x + range.y) {
						StyleObject so = entry.getValue();
						if(so != null && !so.isDisposed()) {
							// 如果这个对象正在拷贝缓冲中，延迟对他的释放
							if(copyObject.contains(so)) {
								deferredDispose.add(so);
								so.hide();
							} else
								so.dispose();
						}
						
						// 调整偏移
						if(start > range.x) {
							e.start = range.x;
							replaceCharCount = e.end - e.start;
						}
						i.remove();
					}
				}
				for(Entry<Point, StyleObject> entry : temp)
					objects.put(entry.getKey(), entry.getValue());
			}
		});
		st.addPaintObjectListener(new PaintObjectListener() {
			public void paintObject(PaintObjectEvent event) {
				GC gc = event.gc;
				StyleRange style = event.style;
				int start = style.start;
				for(Point range : objects.keySet()) {
					if(start == range.x) {
						StyleObject so = objects.get(range);
						switch(so.objectType) {
							case StyleObject.IMAGE:
								Image image = (Image)so.object;
								int x = event.x;
								int y = event.y + event.ascent - style.metrics.ascent;
								gc.drawImage(image, x, y);
								break;
							case StyleObject.CONTROL:
								Control control = (Control)so.object;
								so.show();
								y = event.y + event.ascent - so.getBounds().height;
								control.setLocation(event.x, y);
								objectY.put(so, st.getVerticalBar().getSelection());
								break;
						}
					}
				}
			}
		});
//		st.getVerticalBar().addSelectionListener(new SelectionAdapter() {
//			@Override
//			public void widgetSelected(SelectionEvent e) {
//				ScrollBar vBar = st.getVerticalBar();
//				for(StyleObject so : objects.values()) {
//					Integer oldY = objectY.get(so);
//					if(oldY == null)
//						continue;
//					Rectangle rect = so.getBounds();
//					if(vBar.getSelection() + vBar.getThumb() <= oldY + rect.y - 5)
//						so.hide();
//					else if(vBar.getSelection() >= oldY + rect.y + rect.height - 5)
//						so.hide();
//				}
//			}
//		});
		st.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				int key;
				if (e.keyCode != 0) {
					// special key pressed (e.g., F1)
				    key = e.keyCode | e.stateMask;
				} else {
					// character key pressed
				    key = e.character | e.stateMask;
				}
				Runnable action = getUserKeyAction(key);
				if(action != null)
					action.run();
			}
		});
		st.addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				if(font != null)
					font.dispose();
				clipboard.dispose();
				disposeStyleObjects();
			}
		});
		
		// 重定向一些按键
		st.setKeyBinding(SWT.MOD1 | 'C', SWT.NULL);
		st.setKeyBinding(SWT.MOD1 | 'V', SWT.NULL);
		st.setKeyBinding(SWT.MOD1 | 'X', SWT.NULL);
		st.setKeyBinding(SWT.MOD1 | 'A', SWT.NULL);
		setUserKeyAction(SWT.MOD1 | 'C', new Runnable() {
			public void run() {
				copy();
			}
		});
		setUserKeyAction(SWT.MOD1 | 'V', new Runnable() {
			public void run() {
				paste();
			}
		});
		setUserKeyAction(SWT.MOD1 | 'X', new Runnable() {
			public void run() {
				cut();
			}
		});
		setUserKeyAction(SWT.MOD1 | 'A', new Runnable() {
			public void run() {
				st.selectAll();
			}
		});
		
		container.setBackground(st.getBackground());
	}
	
	/**
	 * 设置背景色
	 * 
	 * @param c
	 */
	public void setBackground(Color c) {
		st.setBackground(c);
		container.setBackground(c);
	}
	
	/**
	 * 释放资源
	 */
	public void dispose() {
		st.dispose();
	}
	
	/**
	 * 添加一个内嵌对象
	 * 
	 * @param so
	 * 		内嵌对象描述类
	 * @param offset
	 * 		偏移
	 * @param length
	 * 		长度
	 */
	void addObject(StyleObject so, int offset, int length) {
		Point range = new Point(offset, length);
		objects.put(range, so);
		StyleRange style = new StyleRange();
		style.start = offset;
		style.length = length;
		Rectangle rect = so.getBounds();
		style.metrics = new GlyphMetrics(rect.height, 0, rect.width);
		st.setStyleRange(style);
	}
	
	/**
	 * 在当前位置插入一个图像
	 * 
	 * @param image
	 * 		图像
	 * @param placeholder
	 * 		占位内容
	 * @param disposable
	 * 		true表示这个图像被删除时是否dispose掉
	 */
	public void insertImage(Image image, String placeholder, boolean disposable) {
		if(image == null)
			return;
		StyleObject so = new StyleObject(StyleObject.IMAGE, image, disposable);
		insertObject(so, placeholder);
	}
	
	/**
	 * 在文本末尾追加一个图像
	 * 
	 * @param image
	 * 		图像
	 * @param placeholder
	 * 		占位内容
	 * @param disposable
	 * 		true表示这个图像被删除时是否dispose掉
	 */
	public void appendImage(Image image, String placeholder, boolean disposable) {
		StyleObject so = new StyleObject(StyleObject.IMAGE, image, disposable);
		appendObject(so, placeholder);
	}
	
	/**
	 * 追加一个对象 
	 * 
	 * @param so
	 * @param placeholder
	 */
	private void appendObject(StyleObject so, String placeholder) {
		int offset = st.getCharCount();
		st.replaceTextRange(offset, 0, placeholder);
		addObject(so, offset, placeholder.length());
	}

	/**
	 * 插入动画
	 * 
	 * @param loader
	 * @param placeholder
	 * @param disposable
	 */
	public void insertImage(ImageLoader loader, String placeholder, boolean disposable) {
		if(loader == null)
			return;
		final Animator ani = new Animator(st);
		ani.setLoader(loader);
		ani.setSize(loader.logicalScreenWidth, loader.logicalScreenHeight);
		ani.setBackground(st.getBackground());
		insertControl(ani, placeholder, disposable);
	}
	
	/**
	 * 追加动画
	 * 
	 * @param loader
	 * @param placeholder
	 * @param disposable
	 */
	public void appendImage(ImageLoader loader, String placeholder, boolean disposable) {
		if(loader == null)
			return;
		Animator ani = new Animator(st);
		ani.setLoader(loader);
		ani.setSize(loader.logicalScreenWidth, loader.logicalScreenHeight);
		ani.setBackground(st.getBackground());
		appendControl(ani, placeholder, disposable);
	}
	
	/**
	 * 插入控件
	 * 
	 * @param control
	 * @param placeholder
	 * @param disposable
	 */
	public void insertControl(Control control, String placeholder, boolean disposable) {
		StyleObject so = new StyleObject(StyleObject.CONTROL, control, disposable);
		insertObject(so, placeholder);
	}
	
	/**
	 * 追加控件
	 * 
	 * @param control
	 * @param placeholder
	 * @param disposable
	 */
	public void appendControl(Control control, String placeholder, boolean disposable) {
		StyleObject so = new StyleObject(StyleObject.CONTROL, control, disposable);
		appendObject(so, placeholder);
	}
	
	/**
	 * 插入一个对象
	 * 
	 * @param so
	 * @param placeholder
	 */
	private void insertObject(StyleObject so, String placeholder) {
		if(st.getSelectionCount() > 0) {
			Point range = st.getSelectionRange();
			st.replaceTextRange(range.x, range.y, placeholder);
			addObject(so, range.x, placeholder.length());
		} else {
			st.replaceTextRange(st.getCaretOffset(), 0, placeholder);
			addObject(so, st.getCaretOffset(), placeholder.length());
		}
		st.setCaretOffset(st.getCaretOffset() + placeholder.length());
	}

	/**
	 * 刷新拷贝缓冲
	 */
	private void refreshCopyCache() {
		// 检查延迟释放缓冲中的对象
		releaseObject();
		
		// 如果选择为空，返回
		if(st.getSelectionCount() <= 0)
			return;
		
		// copy的内容中是否有对象，有则保存到cache里面
		Point selection = st.getSelection();
		for(Entry<Point, StyleObject> entry : objects.entrySet()) {
			Point range = entry.getKey();
			if(selection.x <= range.x && range.x < selection.y) {
				copyObject.add(entry.getValue());
				objectOffset.add(range.x - selection.x);
			}
		}
	}

	/**
	 * 清空缓冲，释放一些对象
	 */
	private void releaseObject() {
		for(StyleObject so : copyObject) {
			if(deferredDispose.contains(so))
				so.dispose();
		}
		copyObject.clear();
		objectOffset.clear();
		deferredDispose.clear();
	}
	
	/**
	 * copy
	 */
	public void copy() {
		refreshCopyCache();
		st.copy();
		copyString = (String)clipboard.getContents(TextTransfer.getInstance());
		if(copyString == null)
			copyString = "";
	}	

	/**
	 * paste
	 */
	public void paste() {
		int offset = (st.getSelectionCount() > 0) ? st.getSelectionRange().x : st.getCaretOffset();
		st.paste();
		if(copyObject.isEmpty())
			return;
		
		String str = (String)clipboard.getContents(TextTransfer.getInstance());
		if(copyString.equals(str)) {
			int size = copyObject.size();
			for(int i = 0 ; i < size; i++) {
				addObject(StyleObjectReplicator.replicate(copyObject.get(i)), objectOffset.get(i) + offset, 1);
			}			
		} else
			releaseObject();
	}
	
	/**
	 * cut
	 */
	public void cut() {
		refreshCopyCache();
		st.cut();
		copyString = (String)clipboard.getContents(TextTransfer.getInstance());
		if(copyString == null)
			copyString = "";
	}
	
	/**
	 * 添加一个用户自定义按键事件
	 * 
	 * @param key
	 * @param action
	 */
	public void setUserKeyAction(int key, Runnable action) {	    
		int keyValue = key & SWT.KEY_MASK;
		int modifierValue = key & SWT.MODIFIER_MASK;
		char keyChar = (char)keyValue;

		if (Character.isLetter(keyChar)) {
			// 添加key的大写形式
			char ch = Character.toUpperCase(keyChar);
			int newKey = ch | modifierValue;
			if (action == null)
			    userActionMap.remove(newKey);
			else
			    userActionMap.put(newKey, action);
			// 添加key的小写形式
			ch = Character.toLowerCase(keyChar);
			newKey = ch | modifierValue;
			if (action == null)
			    userActionMap.remove(newKey);
			else
			    userActionMap.put(newKey, action);
		} else {
		    // 不是字符形式的按键则直接添加
			if (action == null)
			    userActionMap.remove(key);
			else
			    userActionMap.put(key, action);
		}       
	}
	
	/**
	 * 删除用户按键绑定
	 * 
	 * @param key
	 */
	public void removeUserKeyAction(int key) {
		userActionMap.remove(key);
	}
	
	/**
	 * 得到用户自定义动作对象
	 * 
	 * @param key
	 * 		按键值
	 * @return
	 * 		Runnable对象
	 */
	public Runnable getUserKeyAction(int key) {
	    return userActionMap.get(key);
	}

	public void setDefaultStyle(LineStyle defaultStyle) {
		st.setForeground(defaultStyle.foreground);
		if(font != null)
			font.dispose();
		FontData fd = new FontData(defaultStyle.fontName, defaultStyle.fontSize, defaultStyle.fontStyle);
		font = new Font(st.getDisplay(), fd);
		st.setFont(font);
	}

	public void setForbiddenAnimation(boolean selection) {
		for(StyleObject so : objects.values()) {
			if(so.objectType == StyleObject.CONTROL) {
				if(so.object instanceof Animator) {
					if(selection)
						((Animator)so.object).stopAnimation();
					else
						((Animator)so.object).resumeAnimation();
				}
			}
		}
	}

	public void clear() {
		st.setText("");
		disposeStyleObjects();
	}

	private void disposeStyleObjects() {
		for(StyleObject so : objects.values())
			so.dispose();
		for(StyleObject so : copyObject)
			so.dispose();
		for(StyleObject so : deferredDispose)
			so.dispose();
		objects.clear();
		copyObject.clear();
		deferredDispose.clear();
		objectOffset.clear();
		objectY.clear();
		temp.clear();
	}

	public boolean isReadonly() {
		return !st.getEditable();
	}

	public void appendText(String text) {
		st.append(text);
	}

	public void setFocus() {
		st.setFocus();
	}

	public void setReadonly(boolean b) {
		st.setEditable(!b);
	}

	public boolean isDisposed() {
		return st.isDisposed();
	}

	public void setText(String text) {
		clear();
		st.setText(text);
	}	
}
