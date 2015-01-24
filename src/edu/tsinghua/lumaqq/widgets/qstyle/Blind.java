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
package edu.tsinghua.lumaqq.widgets.qstyle;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DragSource;
import org.eclipse.swt.dnd.DragSourceListener;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.DropTargetListener;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

/**
 * 新的百叶窗控件，尽量的简单化了
 * 
 * 可接受的样式为
 * SWT.BORDER
 * 
 * @author luma
 */
public class Blind extends Composite {
	private Color borderColor;
	private Color contentBackground;
	private int borderWidth;
	private ISlatLabelProvider labelProvider;
	
	private int previousSlat;
	private int currentSlat;
	private int visibleSlatCount;
	
	// 子控件列表，大小是偶数，偶数位置是coolbutton，奇数位置是任意控件
	private List<Control> children;
	
	private MouseListener slatMouseListener = new MouseAdapter() {
		@Override
		public void mouseUp(MouseEvent e) {
			Slat slat = (Slat)e.getSource();
			int absoluteIndex = children.indexOf(slat);
			if(absoluteIndex == -1)
				return;
			
			setCurrentSlat(absoluteIndex >>> 1);			
		}
	};
	
	/**
	 * 创建一个百叶窗
	 * 
	 * @param parent
	 * 		父容器
	 */
	public Blind(Composite parent) {
		this(parent, SWT.NONE);
	}
	
	/**
	 * 创建一个百叶窗
	 * 
	 * @param parent
	 * 		父容器
	 * @param style
	 * 		样式
	 */
	public Blind(Composite parent, int style) {
		super(parent, SWT.NONE);
		
		checkStyle(style);
		
		// 初始化变量
		children = new ArrayList<Control>();
		borderWidth = 1;
		borderColor = new Color(getDisplay(), 0x29, 0x5D, 0xA5);
		contentBackground = getDisplay().getSystemColor(SWT.COLOR_WHITE);
		currentSlat = -1;
		previousSlat = -1;
		visibleSlatCount = 0;
		
		// 添加监听器
		GridLayout layout = new GridLayout();
		layout.horizontalSpacing = 0;
		layout.verticalSpacing = 1;
		layout.marginHeight = layout.marginWidth = 1;
		if((style & SWT.BORDER) != 0) {
			layout.marginHeight = layout.marginWidth = borderWidth + 1;
			addPaintListener(new PaintListener() {
				public void paintControl(PaintEvent e) {
					Rectangle rect = getClientArea();
					rect.x += borderWidth - 1;
					rect.y += borderWidth - 1;
					rect.width -= borderWidth;
					rect.height -= borderWidth;
					e.gc.setForeground(borderColor);
					e.gc.drawRectangle(rect);
				}
			});
		} 
		
		addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				borderColor.dispose();
			}
		});
		
		// 设置layout
		setLayout(layout);
		// 设置背景
		setBackground(contentBackground);
	}
	
	/**
	 * 删除指定位置的slat
	 * 
	 * @param index
	 * 		slat索引
	 */
	public void removeSlat(int index) {
		internalRemoveSlat(index);
	}
	
	/**
	 * 删除所有的slat
	 */
	public void removeAll() {
		while(children.size() > 0)
			removeSlat(0);
	}
	
	/**
	 * 刷新slat文本
	 * 
	 * @param slatControl
	 */
	public void refreshSlat(Control slatControl) {
		checkWidget();
		int index = children.indexOf(slatControl);
		index >>>= 1;
		if(!checkIndex(index))
			return;
		
		Slat slat = (Slat)children.get(index << 1);
		slat.setText(labelProvider.getText(slatControl));
	}

	/**
	 * 删除一个slat
	 * 
	 * @param slatControl
	 * 		slat绑定控件
	 */
	public void removeSlat(Control slatControl) {
		int index = children.indexOf(slatControl);
		if(index == -1)
			return;
		
		internalRemoveSlat(index >>> 1);
	}
	
	/**
	 * 实际的删除操作
	 * 
	 * @param index
	 * 		slat索引
	 */
	private void internalRemoveSlat(int index) {
		checkWidget();
		if(!checkIndex(index))
			return;
		
		adjustCurrentSlat(index);
		if(isSlatVisible(index))
			visibleSlatCount--;
		int temp = index << 1;
		Slat slat = (Slat)children.remove(temp);
		slat.removeMouseListener(slatMouseListener);
		slat.dispose();
		children.remove(temp).dispose();	
		if(index < currentSlat)
			currentSlat--;
		layout();
	}
	
	/**
	 * 隐藏一个slat
	 * 
	 * @param index
	 * 		索引
	 */
	public void hideSlat(int index) {
		internalHideSlat(index);
	}
	
	/**
	 * 隐藏一个slat
	 * 
	 * @param slatControl
	 * 		slat绑定的控件
	 */
	public void hideSlat(Control slatControl) {
		int index = children.indexOf(slatControl);
		if(index == -1)
			return;
		
		internalHideSlat(index >>> 1);
	}
	
	/**
	 * 执行实际的隐藏操作
	 * 
	 * @param index
	 * 		索引
	 */
	private void internalHideSlat(int index) {
		checkWidget();
		if(!checkIndex(index))
			return;
		if(!isSlatVisible(index))
			return;
		
		adjustCurrentSlat(index);
		Control slat = children.get(index << 1);
		GridData gd = (GridData)slat.getLayoutData();
		gd.exclude = true;		
		slat.setVisible(false);
		visibleSlatCount--;
		layout();
	}
	
	/**
	 * 显示一个slat
	 * 
	 * @param index
	 * 		索引
	 */
	public void showSlat(int index) {
		internalShowSlat(index);
	}
	
	/**
	 * 显示一个slat
	 * 
	 * @param slatControl
	 * 		slat绑定控件
	 */
	public void showSlat(Control slatControl) {
		int index = children.indexOf(slatControl);
		if(index == -1)
			return;
		
		internalShowSlat(index >>> 1);
	}
	
	/**
	 * 执行实际的显示slat的工作
	 * 
	 * @param index
	 * 		索引
	 */
	private void internalShowSlat(int index) {
		checkWidget();
		if(!checkIndex(index))
			return;
		if(isSlatVisible(index))
			return;
		
		Control slat = children.get(index << 1);
		GridData gd = (GridData)slat.getLayoutData();
		gd.exclude = false;
		slat.setVisible(true);
		visibleSlatCount++;
		if(currentSlat == -1)
			setCurrentSlat(index);
		layout();
	}
	
	/**
	 * 调整当前slat
	 * 
	 * @param deleted
	 * 		将要被删除的slat
	 */
	private void adjustCurrentSlat(int toBeDeleted) {
		if(toBeDeleted != currentSlat)
			return;
		
		if(visibleSlatCount > 1) {			
			int candidate = currentSlat;
			while(!isSlatVisible((candidate + 1) % getSlatCount()))
				candidate++;
			setCurrentSlat((candidate + 1) % getSlatCount());
		} else {
			setCurrentSlat(-1);
		}
	}
	
	/**
	 * 检查一个slat是否可见
	 * 
	 * @param index
	 * 		索引
	 * @return
	 * 		true表示可见
	 */
	public boolean isSlatVisible(int index) {
		checkWidget();
		if(!checkIndex(index))
			SWT.error(SWT.ERROR_INVALID_ARGUMENT);
		return ((GridData)children.get(index << 1).getLayoutData()).exclude == false;
	}
	
	/**
	 * 设置当前slat
	 * 
	 * @param index
	 * 		slat索引
	 */
	public void setCurrentSlat(int index) {
		checkWidget();
		if(getSlatCount() == 0 || index == currentSlat) {
			previousSlat = currentSlat;
			return;
		}
		
		// 去掉当前slat
		if(currentSlat != -1) {
			Control c = children.get((currentSlat << 1) + 1);
			GridData gd = (GridData)c.getLayoutData();
			gd.exclude = true;			
			c.setVisible(false);
		}
		
		// 设置前一个slat
		if(previousSlat == -1 && currentSlat == -1)
			previousSlat = index;
		else
			previousSlat = currentSlat;
		
		// 设置新的当前slat
		if(checkIndex(index)) {
			Control c = children.get((index << 1) + 1);
			GridData gd = (GridData)c.getLayoutData();
			gd.exclude = false;
			c.setVisible(true);
			currentSlat = index;
		} else
			currentSlat = -1;
		
		layout();			
	}
	
	/**
	 * 在slat上闪烁一个图标
	 * 
	 * @param index
	 * 		序号
	 * @param image
	 * 		要闪烁的图标
	 */
	public void startBlink(int index, Image image) {
		checkWidget();
		if(!checkIndex(index))
			return;
		((Slat)children.get(index << 1)).startBlinkImage(image);
	}
	
	/**
	 * 停止闪烁一个slat
	 * 
	 * @param index
	 * 		序号
	 */
	public void stopBlink(int index) {
		checkWidget();
		if(!checkIndex(index))
			return;
		((Slat)children.get(index << 1)).stopBlinkImage();
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Composite#getChildren()
	 */
	@Override
	public Control[] getChildren() {
		checkWidget();
		return children.toArray(new Control[children.size()]);
	}
	
	/**
	 * 添加一个slat
	 * 
	 * @param slatControl
	 * 		slat所绑定的控件
	 * @return
	 * 		新建的slat
	 */
	public Slat addSlat(Control slatControl) {
		return internalAddSlat(children.size() >>> 1, slatControl);
	}
	
	/**
	 * 添加一个slat到指定位置
	 * 
	 * @param index
	 * 		slat的索引，不包括slat的控件
	 * @param slatControl
	 * 		slat所绑定的控件
	 * @return
	 * 		新建的slat
	 */
	public Slat addSlat(int index, Control slatControl) {
		return internalAddSlat(index, slatControl);
	}
	
	/**
	 * 添加一个slat到指定位置
	 * 
	 * @param index
	 * 		slat的索引，相对于所有控件来说
	 * @param slatControl
	 * 		slat所绑定的控件
	 * @return
	 * 		新建的slat
	 */
	private Slat internalAddSlat(int index, Control slatControl) {
		checkWidget();
		if(labelProvider == null)
			return null;
		if(index < 0 || index > getSlatCount())
			SWT.error(SWT.ERROR_INVALID_ARGUMENT);
		int temp = index << 1;
		
		// 添加slat控件
		Slat slat = new Slat(this, SWT.CENTER, labelProvider.getText(slatControl), null);
		slat.setBackground(getDisplay().getSystemColor(SWT.COLOR_WHITE));
		slat.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		children.add(temp, slat);
		// 添加slat绑定控件
		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.exclude = true;
		slatControl.setVisible(false);
		slatControl.setLayoutData(gd);
		slatControl.setBackground(contentBackground);
		children.add(temp + 1, slatControl);
		
		visibleSlatCount++;
		slat.addMouseListener(slatMouseListener);
		if(currentSlat == -1)
			setCurrentSlat(0);
		else if(index <= currentSlat) {
			currentSlat++;
			layout();
		} else
			layout();
		return slat;
	}

	/**
	 * 检查索引范围
	 * 
	 * @param index
	 * 		索引
	 * @return
	 * 		true表示索引范围正确
	 */
	private boolean checkIndex(int index) {
		return index >= 0 && index < getSlatCount();
	}

	/**
	 * 检查样式是否合法
	 * 
	 * @param style
	 * 		样式常量
	 */
	private void checkStyle(int style) {
		style &= SWT.BORDER;
	}

	/**
	 * @return Returns the labelProvider.
	 */
	public ISlatLabelProvider getLabelProvider() {
		return labelProvider;
	}

	/**
	 * @param labelProvider The labelProvider to set.
	 */
	public void setLabelProvider(ISlatLabelProvider labelProvider) {
		this.labelProvider = labelProvider;
	}

	/**
	 * @return Returns the currentSlat.
	 */
	public int getCurrentSlatIndex() {
		return currentSlat;
	}
	
	/**
	 * 得到这个控件所在的slat索引
	 * 
	 * @param slatControl
	 * @return
	 */
	public int indexOf(Control slatControl) {
		int index = children.indexOf(slatControl);
		return index >>> 1;
	}
	
	/**
	 * 得到slat绑定控件
	 * 
	 * @param index
	 * 		slat索引
	 * @return
	 * 		Control对象
	 */
	public Control getSlatControl(int index) {
		checkWidget();
		if(!checkIndex(index))
			return null;
		return children.get((index << 1) + 1);
	}
	
	/**
	 * @return
	 * 		slat总数
	 */
	public int getSlatCount() {
		return children.size() >>> 1;
	}
	
	/**
	 * 为Slat添加拖动支持
	 * 
	 * @param index
	 * 		slat序号
	 * @param operations
	 *      a bitwise OR of the supported drag and drop operation types (
	 *      <code>DROP_COPY</code>,<code>DROP_LINK</code>, and
	 *      <code>DROP_MOVE</code>)
	 * @param transferTypes
	 *      the transfer types that are supported by the drag operation
	 * @param listener
	 *      the callback that will be invoked to set the drag data and to
	 * 		cleanup after the drag and drop operation finishes
	 * @see org.eclipse.swt.dnd.DND
	 */
	public void addSlatDragSupport(int index, int operations, Transfer[] transferTypes, DragSourceListener listener) {
		checkWidget();
		if(!checkIndex(index))
			SWT.error(SWT.ERROR_INVALID_ARGUMENT);
		DragSource dragSource = new DragSource(children.get(index << 1), operations);
		dragSource.setTransfer(transferTypes);
		dragSource.addDragListener(listener);
	}
	
	/**
	 * 为Slat添加拖放支持
	 * 
	 * @param index
	 * 		slat序号
	 * @param operations
	 *            a bitwise OR of the supported drag and drop operation types (
	 *            <code>DROP_COPY</code>,<code>DROP_LINK</code>, and
	 *            <code>DROP_MOVE</code>)
	 * @param transferTypes
	 *            the transfer types that are supported by the drop operation
	 * @param listener
	 *            the callback that will be invoked after the drag and drop
	 *            operation finishes
	 * @see org.eclipse.swt.dnd.DND
	 */
	public void addSlatDropSupport(int index, int operations, Transfer[] transferTypes, DropTargetListener listener) {
		checkWidget();
		if(!checkIndex(index))
			SWT.error(SWT.ERROR_INVALID_ARGUMENT);
		DropTarget dropTarget = new DropTarget(children.get(index << 1), operations);
		dropTarget.setTransfer(transferTypes);
		dropTarget.addDropListener(listener);
	}
	
	/**
	 * 为Slat添加一个拖放支持，使用slat或者slat绑定控件定位
	 * 
	 * @param c
	 * @param operations
	 * @param transferTypes
	 * @param listener
	 */
	public void addSlatDropSupport(Control c, int operations, Transfer[] transferTypes, DropTargetListener listener) {
		int index = indexOf(c);
		addSlatDropSupport(index, operations, transferTypes, listener);
	}
	
	/**
	 * 为Slat绑定控件添加拖动支持
	 * 
	 * @param index
	 * 		slat序号
	 * @param operations
	 *      a bitwise OR of the supported drag and drop operation types (
	 *      <code>DROP_COPY</code>,<code>DROP_LINK</code>, and
	 *      <code>DROP_MOVE</code>)
	 * @param transferTypes
	 *      the transfer types that are supported by the drag operation
	 * @param listener
	 *      the callback that will be invoked to set the drag data and to
	 * 		cleanup after the drag and drop operation finishes
	 * @see org.eclipse.swt.dnd.DND
	 */
	public void addSlatControlDragSupport(int index, int operations, Transfer[] transferTypes, DragSourceListener listener) {
		checkWidget();
		if(!checkIndex(index))
			SWT.error(SWT.ERROR_INVALID_ARGUMENT);
		DragSource dragSource = new DragSource(children.get((index << 1) + 1), operations);
		dragSource.setTransfer(transferTypes);
		dragSource.addDragListener(listener);
	}
	
	/**
	 * 为Slat绑定控件添加拖放支持
	 * 
	 * @param index
	 * 		slat序号
	 * @param operations
	 *            a bitwise OR of the supported drag and drop operation types (
	 *            <code>DROP_COPY</code>,<code>DROP_LINK</code>, and
	 *            <code>DROP_MOVE</code>)
	 * @param transferTypes
	 *            the transfer types that are supported by the drop operation
	 * @param listener
	 *            the callback that will be invoked after the drag and drop
	 *            operation finishes
	 * @see org.eclipse.swt.dnd.DND
	 */
	public void addSlatControlDropSupport(int index, int operations, Transfer[] transferTypes, DropTargetListener listener) {
		checkWidget();
		if(!checkIndex(index))
			SWT.error(SWT.ERROR_INVALID_ARGUMENT);
		DropTarget dropTarget = new DropTarget(children.get((index << 1) + 1), operations);
		dropTarget.setTransfer(transferTypes);
		dropTarget.addDropListener(listener);
	}
	
	/**
	 * 得到指定位置的slat
	 * 
	 * @param index
	 * 		slat索引
	 * @return
	 * 		Slat对象
	 */
	public Slat getSlat(int index) {
		checkWidget();
		if(!checkIndex(index))
			return null;
		return (Slat)children.get(index << 1);
	}

	/**
	 * 设置slat绑定控件背景色
	 * 
	 * @param bg
	 * 		背景色
	 */
	public void setContentBackground(Color bg) {
		checkWidget();
		contentBackground = bg;
		int size = children.size();
		for(int i = 1; i < size; i += 2)
			children.get(i).setBackground(bg);
		setBackground(contentBackground);
	}

	/**
	 * @return
	 * 		当前slat控件
	 */
	public Slat getCurrentSlat() {
		return getSlat(getCurrentSlatIndex());
	}
	
	/**
	 * @return
	 * 		当前slat绑定控件
	 */
	public Control getCurrentSlatControl() {
		return getSlatControl(getCurrentSlatIndex());
	}
	
	/**
	 * 停止所有Slat上的动画
	 */
	public void stopAllAnimation() {
		int size = children.size();
		for(int i = 0; i < size; i += 2) {
			Slat slat = (Slat)children.get(i);
			slat.stopAnimate();
			slat.stopBlinkImage();
			slat.stopBounceImage();
		}
	}
	
	/**
	 * @param index
	 * @return
	 * 		true表示slat上有一个动画
	 */
	public boolean hasSlatAnimation(int index) {
		checkWidget();
		if(!checkIndex(index))
			SWT.error(SWT.ERROR_INVALID_ARGUMENT);
		Slat slat = getSlat(index);
		return slat.isAnimating() || slat.isBlinking() || slat.isBouncing();
	}

	/**
	 * @return Returns the previousSlat.
	 */
	public int getPreviousSlat() {
		return previousSlat;
	}
}
