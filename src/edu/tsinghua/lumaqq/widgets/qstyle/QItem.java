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

import static edu.tsinghua.lumaqq.widgets.qstyle.QTree.*;
import static java.lang.Math.max;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Item;

/**
 * QTree的item
 * 
 * @author luma
 */
public class QItem extends Item {	
    static final String ELLIPSIS = "...";
	static final int MAX_ATTACHMENT = 4;
	static final int ATTACHMENT_SPACING = 3;
	static final Rectangle DUMMY_BOUND = new Rectangle(0, 0, 0, 0);
	
	private QTree parent;
	private QItem parentItem;
	
	private QItem[] items;
	private int itemCount;
	
	private boolean expanded;
	private int level;
	private Color foreground;
	
	private boolean mouseOnIcon;
	private int mouseOnAttachment;
	private boolean mouseOnText;
	private int textWidth;
	
	boolean needRedraw;
	
	boolean animating;
	IEffect effect;
	
	// 装饰图标，装饰图标是指位于原始图标边框范围之内，一般放置于右下角的小图片
	private Image decorationImage;
	// 附件，附件不和原始图标重合，而是显示在其他地方，做为对原始图标的补充
	private Image[] attachment;
	private Rectangle[] attachmentBound;
	// 前缀，放在图标的前面，可以用来放置如加号减号表示展开收起
	private Image prefix;
	// 主图标边框
	Rectangle imageBound;
	
	/**
	 * 创建一个新的QTree根节点
	 * 
	 * @param parent
	 * 		QTree
	 * @param style
	 * 		样式
	 */
	public QItem(QTree parent, int style) {
		super(parent, style);
		this.parent = parent;
		this.parentItem = null;
		this.level = 0;
		this.itemCount = 0;
		this.items = new QItem[16];
		this.expanded = false;
		this.attachment = new Image[MAX_ATTACHMENT];
		this.attachmentBound = new Rectangle[MAX_ATTACHMENT];
		this.prefix = null;
		this.mouseOnIcon = false;
		this.needRedraw = false;
		this.mouseOnAttachment = -1;
		this.mouseOnText = false;
		this.animating = false;
		this.textWidth = 0;
		this.imageBound = (getImage() == null) ? DUMMY_BOUND : getImage().getBounds();
		
		parent.addItem(this);
	}
	
	/**
	 * 创建一个新的QTree子项
	 * 
	 * @param parentItem
	 * 		父item
	 * @param style
	 * 		样式
	 */
	public QItem(QItem parentItem, int style) {
		super(checkNull(parentItem).parent, style);
		this.parent = parentItem.parent;
		this.parentItem = parentItem;
		this.level = parentItem.getLevel() + 1;
		this.itemCount = 0;
		this.items = new QItem[16];
		this.expanded = false;
		this.attachment = new Image[MAX_ATTACHMENT];
		this.attachmentBound = new Rectangle[MAX_ATTACHMENT];
		this.prefix = null;
		this.mouseOnIcon = false;
		this.mouseOnAttachment = -1;
		this.animating = false;
		this.imageBound = (getImage() == null) ? DUMMY_BOUND : getImage().getBounds();
		
		parentItem.addItem(this);		
	}
	
	/**
	 * 添加一个子item
	 * 
	 * @param item
	 */
	private void addItem(QItem item) {
		internalAddItem(item);
	}

	/**
	 * 检查是否为空
	 * 
	 * @param item
	 * 		QItem
	 * @return
	 * 		如果不为空，返回被检查的对象
	 * @exception SWTException <ul>
	 * <li>ERROR_NULL_ARGUMENT - if the parent is null</li>
	 * </ul>
	 */
	static QItem checkNull(QItem item) {
		if (item == null) 
			SWT.error(SWT.ERROR_NULL_ARGUMENT);
		return item;
	}
	
	/**
	 * 画item
	 * 
	 * @param gc
	 * 		GC对象
	 * @param y
	 * 		item左上角y坐标，相对于父控件来说
	 * @param frame
	 * 		帧数，动画状态时才有意义
	 */
	void onPaint(GC gc, int y, int frame) {		
		textWidth = gc.textExtent(getText()).x;
		ItemLayout layout = parent.getLevelLayout(level);
		
		if(animating && effect != null)
			effect.onPaint(this, gc, layout, y, frame);
		else {
			switch(layout) {
				case HORIZONTAL:	
					paintHorizontal(gc, y);
					break;
				case VERTICAL:
					paintVertical(gc, y);
					break;
				default:
					SWT.error(SWT.ERROR_INVALID_RANGE);
					break;
			}			
		}		
	}
	
	/**
	 * 画垂直样式的item
	 * 
	 * @param gc
	 * 		GC
	 * @param y 
	 * 		item边框左上角y坐标 
	 */
	void paintVertical(GC gc, int y) {
		int size = parent.getLevelImageSize(level);
		int x = parent.getItemIndent(level);
		int itemHeight = parent.getItemHeight(level);
		int itemWidth = parent.getItemWidth(level);
		int imageX = x + (max(0, itemWidth - size) >>> 1);
		int imageY = y + ITEM_TOP_MARGIN;
		int prefixX = 0;
		int prefixY = 0;
		if(prefix != null) {
			Rectangle prefixBound = prefix.getBounds();
			prefixX = imageX - PREFIX_ICON_SPACING - prefixBound.width;
			prefixY = imageY + ((size - prefixBound.height) >> 1);
		}
		
		// 画前缀
		if(prefix != null) 
			paintPrefix(gc, getPrefix(), prefixX, prefixY);
		
		// 画图标
		paintIcon(gc, getImage(), imageBound, imageX, imageY, size);
		
		// 画文本
		paintVerticalText(gc, x, y, itemWidth, itemHeight, foreground);

		// 画附件
		paintVerticalAttachment(gc, x, y, itemWidth, itemHeight, imageX, imageY, size);
	}

	/**
	 * 画水平样式的item
	 * 
	 * @param gc
	 * 		GC
	 * @param y 
	 * 		item边框左上角y坐标 
	 */
	void paintHorizontal(GC gc, int y) {
		int size = parent.getLevelImageSize(level);
		int x = parent.getItemIndent(level);
		int itemHeight = parent.getItemHeight(level);
		int itemWidth = parent.getItemWidth(level);
		int imageX = x + ITEM_LEFT_MARING;
		int imageY = y + (max(0, itemHeight - size) >>> 1);
		int prefixX = 0;
		int prefixY = 0;
		if(prefix != null) {
			Rectangle prefixBound = prefix.getBounds();
			prefixX = imageX;
			prefixY = imageY + ((itemHeight - prefixBound.height) >> 1);
			imageX += prefixBound.width + PREFIX_ICON_SPACING;
		}
		
		// 画前缀
		if(prefix != null)
			paintPrefix(gc, getPrefix(), prefixX, prefixY);
		
		// 画图标
		paintIcon(gc, getImage(), imageBound, imageX, imageY, size);	
		
		// 画文本
		paintHorizontalText(gc, x, y, itemWidth, itemHeight, foreground);
		
		// 画附件
		paintHorizontalAttachment(gc, x, y, itemWidth, itemHeight, imageX, imageY, size);
	}		

	/**
	 * 画前缀图标
	 * 
	 * @param gc
	 * @param image
	 * @param prefixX
	 * @param prefixY
	 */
	void paintPrefix(GC gc, Image image, int prefixX, int prefixY) {
		if(image != null)
			gc.drawImage(image, prefixX, prefixY);
	}

	/**
	 * 画垂直模式时的文本
	 * 
	 * @param gc
	 * 		GC
	 * @param x
	 * 		item左上角x坐标
	 * @param y
	 * 		item左上角y坐标
	 * @param itemWidth
	 * 		item宽度
	 * @param itemHeight
	 * 		item高度
	 * @param fg
	 * 		前景色
	 */
	void paintVerticalText(GC gc, int x, int y, int itemWidth, int itemHeight, Color fg) {
		if(getText() != null) {
			// 调整文本的宽度
			String text = getText();
			Color oldForeground = gc.getForeground();
			if(fg != null)
				gc.setForeground(fg);
			gc.drawString(text,
					Math.max(5, (itemWidth - textWidth) >> 1),
					y + ITEM_TOP_MARGIN + parent.getLevelImageSize(level) + ITEM_IMAGE_TEXT_SPACING,
					true);
			gc.setForeground(oldForeground);			
		}
	}
	
	/**
	 * 画水平模式时的文本
	 * 
	 * @param gc
	 * 		GC
	 * @param x
	 * 		item左上角x坐标
	 * @param y
	 * 		item左上角y坐标
	 * @param itemWidth
	 * 		item宽度
	 * @param itemHeight
	 * 		item高度
	 * @param fg
	 * 		前景色
	 */
	void paintHorizontalText(GC gc, int x, int y, int itemWidth, int itemHeight, Color fg) {
		if(getText() != null) {
			// 调整文本的宽度
			int textX = x + ITEM_LEFT_MARING + parent.getLevelImageSize(level) + ITEM_IMAGE_TEXT_SPACING;
			if(prefix != null)
				textX += PREFIX_ICON_SPACING + prefix.getBounds().width;
			String text = getText();
			Color oldForeground = gc.getForeground();
			if(fg != null)
				gc.setForeground(fg);
			gc.drawString(text, 
					textX,
					y + ((itemHeight - parent.fontHeight) >>> 1),
					true);
			gc.setForeground(oldForeground);			
		}
	}

	/**
	 * 画主图标
	 * 
	 * @param gc
	 * 		GC
	 * @param imgBound	
	 * 		主图标矩形
	 * @param imageX
	 * 		主图标左上角
	 * @param imageY
	 * 		主图标右上角
	 * @param size
	 * 		图标实际尺寸
	 */
	 void paintIcon(GC gc, Image image, Rectangle imgBound, int imageX, int imageY, int size) {
		// 画图标
		if(image != null) {
			gc.drawImage(image, 
					0, 
					0, 
					imgBound.width, 
					imgBound.height, 
					imageX, 
					imageY, 
					size, 
					size);
			paintDecoration(gc, imgBound, imageX, imageY, size);			
		}
		// 画图标边框
		if(parent.isEnableIconHover() && mouseOnIcon)
			paintHoverBorder(gc, imageX - 1, imageY - 1, size + 1, size + 1);
	}

	/**
	 * 画附件图标
	 * 
	 * @param gc
	 * 		GC
	 * @param x	
	 * 		item的左上角x坐标
	 * @param y
	 * 		item的左上角y坐标
	 * @param itemWidth
	 * 		item的宽度
	 * @param itemHeight
	 * 		item的高度
	 * @param imageX
	 * 		图标的x位置
	 * @param imageY
	 * 		图标的y位置
	 * @param size
	 * 		最终的图标大小
	 */
	void paintHorizontalAttachment(GC gc, int x, int y, int itemWidth, int itemHeight, int imageX, int imageY, int size) {
		int attX = x + itemWidth - ITEM_RIGHT_MARGIN;
		for(int i = 0; i < MAX_ATTACHMENT; i++) {
			if(attachment[i] == null)
				continue;
			
			// 计算附件图标的位置
			int attY = y + ((itemHeight - attachmentBound[i].height) >>> 1);
			attX -= attachmentBound[i].width;
			// 如果附件图标和原始图标出现了重叠，不再画
			if(attX < imageX + size)
				break;
			
			// 画附件
			gc.drawImage(attachment[i], 
					0, 
					0, 
					attachmentBound[i].width,
					attachmentBound[i].height,
					attX,
					attY,
					attachmentBound[i].width,
					attachmentBound[i].height);
			// 画边框
			if(mouseOnAttachment == i)
				paintHoverBorder(gc, attX - 1, attY - 1, attachmentBound[i].width + 1, attachmentBound[i].height + 1);
			
			attX -= ATTACHMENT_SPACING;
		}
	}	

	/**
	 * 画附件图标
	 * 
	 * @param gc
	 * 		GC
	 * @param x	
	 * 		item的左上角x坐标
	 * @param y
	 * 		item的左上角y坐标
	 * @param itemWidth
	 * 		item的宽度
	 * @param itemHeight
	 * 		item的高度
	 * @param imageX
	 * 		图标的x位置
	 * @param imageY
	 * 		图标的y位置
	 * @param size
	 * 		最终的图标大小
	 */
	void paintVerticalAttachment(GC gc, int x, int y, int itemWidth, int itemHeight, int imageX, int imageY, int size) {
		int evenX = imageX - ATTACHMENT_SPACING;
		int oddX = imageX + size + ATTACHMENT_SPACING;
		for(int i = 0; i < MAX_ATTACHMENT; i++) {
			if(attachment[i] == null)
				continue;
			
			// 计算附件图标的位置
			int attX, attY;
			if(i % 2 == 0) {
				evenX -= attachmentBound[i].width;
				attX = evenX;
			} else {
				attX = oddX;
				oddX += attachmentBound[i].width + ATTACHMENT_SPACING;
			}
			attY = imageY + size - attachmentBound[i].height;
			
			// 画附件
			gc.drawImage(attachment[i], 
					0, 
					0, 
					attachmentBound[i].width,
					attachmentBound[i].height,
					attX,
					attY,
					attachmentBound[i].width,
					attachmentBound[i].height);
			// 画边框
			if(mouseOnAttachment == i)
				paintHoverBorder(gc, attX - 1, attY - 1, attachmentBound[i].width + 1, attachmentBound[i].height + 1);
		}
	}

	/**
	 * 画装饰图标
	 * 
	 * @param gc
	 * 		GC
	 * @param imgBound
	 * 		原始图标尺寸
	 * @param imageX
	 * 		实际图标x位置
	 * @param imageY
	 * 		实际图标y位置
	 * @param size
	 * 		实际图标尺寸
	 */
	void paintDecoration(GC gc, Rectangle imgBound, int imageX, int imageY, int size) {
		if(decorationImage == null)
			return;
		
		Rectangle decorationBound = decorationImage.getBounds();
		int visualWidth, visualHeight;
		if(decorationBound.width > size || decorationBound.height > size) {
			visualWidth = decorationBound.width * size / imgBound.width;
			visualHeight = decorationBound.height * size / imgBound.height;			
		} else {
			visualWidth = decorationBound.width;
			visualHeight = decorationBound.height;
		}
		int decorationX = imageX + size - visualWidth;
		int decorationY = imageY + size - visualHeight;
		gc.drawImage(decorationImage, 
				0, 
				0, 
				decorationBound.width, 
				decorationBound.height,
				decorationX,
				decorationY,
				visualWidth,
				visualHeight);
	}

	/**
	 * 得到某个子item的索引
	 * 
	 * @param item
	 * 		子item对象
	 * @return
	 * 		索引
	 */
	public int indexOf(QItem item) {
		checkWidget();		
		for(int i = 0; i < itemCount; i++) {
			if(items[i] == item)
				return i;
		}
		return -1;
	}
	
	/**
	 * @return Returns the parent.
	 */
	public QTree getParent() {
		return parent;
	}

	/**
	 * @return Returns the parentItem.
	 */
	public QItem getParentItem() {
		return parentItem;
	}

	/**
	 * @return
	 * 		true表示这个item处于一个可见状态，可见状态也就是指它的
	 * 		所有父节点都是展开的
	 */
	boolean isLogicalVisible() {
		QItem item = parentItem;
		while(item != null) {
			if(!item.isExpanded())
				return false;
			item = item.getParentItem();
		}
		return true;
	}
	
	/**
	 * @return Returns the expanded.
	 */
	public boolean isExpanded() {
		checkWidget();
		return expanded;
	}

	/**
	 * 改变item的展开状态
	 * 
	 * @param expanded
	 * 		true表示展开item
	 */
	public void setExpanded(boolean expanded) {
		setExpanded(expanded, true);
	}
	
	/**
	 * 改变item的展开状态
	 * 
	 * @param expanded
	 * 		true表示展开item
	 * @param redraw
	 * 		true表示立刻重画
	 */
	public void setExpanded(boolean expanded, boolean redraw) {
		checkWidget();
		if(this.expanded != expanded) {
			this.expanded = expanded;
			if(redraw)
				parent.updateExpanded(this);
		}		
	}

	/**
	 * @return Returns the level.
	 */
	public int getLevel() {
		return level;
	}
	
	/**
	 * 扩展item数组大小
	 */
	private void expandItemArray() {
		QItem[] newItems = new QItem[items.length << 1];
		System.arraycopy(items, 0, newItems, 0, itemCount);
		items = newItems;
	}
	
	/**
	 * 向item数组中添加一个item到数组中
	 * 
	 * @param item
	 * 		要添加的QItem对象
	 */
	private void internalAddItem(QItem item) {
		if(itemCount >= items.length)
			expandItemArray();
		
		items[itemCount++] = item;
	}
	
	/**
	 * 从item数组中删除一个item，不校验index的合法性
	 * 
	 * @param index
	 * 		item的index
	 */
	private void internalRemoveItem(int index) {
		System.arraycopy(items, index + 1, items, index, itemCount - index - 1);
		items[itemCount - 1] = null;
	}

	/**
	 * @return Returns the itemCount.
	 */
	public int getItemCount() {
		return itemCount;
	}

	/**
	 * @return Returns the items.
	 */
	public QItem[] getItems() {
		checkWidget();
		QItem[] ret = new QItem[itemCount];
		System.arraycopy(items, 0, ret, 0, itemCount);
		return ret;
	}
	
	/**
	 * 得到第index个子item
	 * 
	 * @param index
	 * 		子item索引 
	 * @return
	 * 		QItem对象
	 */
	public QItem getItem(int index) {
		checkWidget();
		checkIndex(index);
		return items[index];
	}

	/**
	 * 检查index是否符合范围
	 * 
	 * @param index		
	 * 		子item索引
	 */
	private void checkIndex(int index) {
		if(index < 0 || index >= itemCount)
			SWT.error(SWT.ERROR_INVALID_ARGUMENT);
	}		
	
	@Override
	public void setText(String string) {
		setText(string, true);
	}
	
	/**
	 * 设置文本
	 * 
	 * @param string
	 * 		新文本
	 * @param redraw
	 * 		true表示立刻重画
	 */
	public void setText(String string, boolean redraw) {
		super.setText(string);
		if(redraw)
			parent.updateItem(this);
	}
	
	@Override
	public void setImage(Image image) {
		setImage(image, true);
	}
	
	/**
	 * 设置图标 
	 * 
	 * @param image
	 * 		图标
	 * @param redraw
	 * 		true表示立刻重画
	 */
	public void setImage(Image image, boolean redraw) {
		super.setImage(image);
		imageBound = (image == null) ? DUMMY_BOUND : image.getBounds();
		if(redraw)
			parent.updateItem(this);
	}
	
	/**
	 * 设置前景色
	 * 
	 * @param color
	 * 		前景色
	 */
	public void setForeground(Color color) {
		setForeground(color, true);
	}
	
	/**
	 * 设置前景色
	 * 
	 * @param color
	 * 		前景色
	 * @param redraw
	 * 		true表示立刻重画
	 */
	public void setForeground(Color color, boolean redraw) {
		checkWidget();
		if(color != null && color.isDisposed())
			SWT.error (SWT.ERROR_INVALID_ARGUMENT);
		foreground = color;
		if(redraw)
			parent.updateItem(this);
	}
	
	/**
	 * @return
	 * 		前景色，如果没有设置，返回父控件的前景色
	 */
	public Color getForeground() {
		return (foreground == null) ? parent.getForeground() : foreground;
	}

	/**
	 * @param decorationImage The decorationImage to set.
	 */
	public void setDecorationImage(Image decorationImage) {
		setDecorationImage(decorationImage, true);
	}
	
	/**
	 * 这是装饰图标
	 * 
	 * @param decorationImage
	 * 		装饰图标对象 
	 * @param redraw
	 * 		true表示立刻重画
	 */
	public void setDecorationImage(Image decorationImage, boolean redraw) {
		checkWidget();
		if(decorationImage != null && decorationImage.isDisposed())
			SWT.error(SWT.ERROR_INVALID_ARGUMENT);
		this.decorationImage = decorationImage;
		if(redraw)
			parent.updateItem(this);
	}
	
	/**
	 * 添加一个附件图标
	 * 
	 * @param index
	 * 		附件图标索引
	 * @param att
	 * 		附件图标
	 */
	public void addAttachment(int index, Image att) {
		addAttachment(index, att, true);
	}
	
	/**
	 * 添加一个附件图标
	 * 
	 * @param index
	 * 		附件图标索引
	 * @param att
	 * 		附件图标
	 * @param redraw
	 * 		true表示立刻重画
	 */
	public void addAttachment(int index, Image att, boolean redraw) {
		if(index < 0 || index >= MAX_ATTACHMENT)
			SWT.error(SWT.ERROR_INVALID_RANGE);
		if(att != null && att.isDisposed())
			SWT.error(SWT.ERROR_INVALID_ARGUMENT);
		if(attachment[index] == att)
			return;
		
		attachment[index] = att;
		if(att == null)
			attachmentBound[index] = null;
		else
			attachmentBound[index] = att.getBounds();
		if(redraw)
			parent.updateItem(this);
	}
	
	/**
	 * 删除一个附件图标
	 * 
	 * @param index
	 * 		附件索引
	 */
	public void removeAttachment(int index) {
		removeAttachment(index, true);
	}
	
	/**
	 * 删除一个item
	 * 
	 * @param index
	 * 		item的索引
	 */
	public void removeItem(int index) {
		checkIndex(index);
		internalRemoveItem(index);
	}
	
	/**
	 * 删除从index开始的后面所有item
	 * 
	 * @param index
	 */
	void removeItemFrom(int index) {
		checkIndex(index);
		for(int i = index; i < itemCount; i++)
			items[index] = null;
		itemCount = index;
	}
	
	/**
	 * 删除一个附件图标
	 * 
	 * @param index
	 * 		附件索引
	 * @param redraw
	 * 		true表示立刻重画
	 */
	public void removeAttachment(int index, boolean redraw) {
		if(index < 0 || index >= MAX_ATTACHMENT)
			SWT.error(SWT.ERROR_INVALID_RANGE);
		if(attachment[index] == null)
			return;
		attachment[index] = null;
		attachmentBound[index] = null;
		if(redraw)
			parent.updateItem(this);
	}
	
	/**
	 * 情况所有鼠标位置相关标志
	 */
	void clearHitTestFlag() {
		mouseOnIcon = false;
	}
	
	/**
	 * 对坐标值进行一些检测，在tree收到鼠标移动事件时调用此方法
	 * 
	 * @param x
	 * 		相对于item的x坐标 
	 * @param y
	 * 		相对于item的y坐标
	 */
	void mouseTest(int x, int y) {
		boolean b = isOnIcon(x, y);
		if(b != mouseOnIcon) {
			mouseOnIcon = b;
			needRedraw = true;
		}
		int i = isOnAttachment(x, y);
		if(i != mouseOnAttachment) {
			mouseOnAttachment = i;
			needRedraw= true;
		}
		mouseOnText = isOnText(x, y);
	}
	
	/**
	 * 判断坐标是否落在某个附件图标内
	 * 
	 * @param x
	 * 		x坐标，这个坐标是相对于item的坐标
	 * @param y
	 * 		y坐标，这个坐标是相对于item的坐标
	 * @return
	 * 		true表示是
	 */
	private int isOnAttachment(int x, int y) {
		int attX, attY;
		int itemWidth = parent.getItemWidth(level);
		int itemHeight = parent.getItemHeight(level);
		switch(parent.getLevelLayout(level)) {
			case HORIZONTAL:
				attX = itemWidth - ITEM_RIGHT_MARGIN;
				attY = 0;
				for(int i = 0; i < MAX_ATTACHMENT; i++) {
					if(attachment[i] == null)
						continue;
					
					// 计算附件图标的位置
					attY = (itemHeight - attachmentBound[i].height) >>> 1;
					attX -= attachmentBound[i].width;
					
					if(containsPoint(attX, attY, attachmentBound[i].width, attachmentBound[i].height, x, y))
						return i;
					
					attX -= ATTACHMENT_SPACING;
				}
				return -1;
			case VERTICAL:
				int size = parent.getLevelImageSize(level);
				int imageX = (itemWidth - size) >>> 1;
				int imageY = ITEM_TOP_MARGIN;
				int evenX = imageX - ATTACHMENT_SPACING;
				int oddX = imageX + size + ATTACHMENT_SPACING;
				for(int i = 0; i < MAX_ATTACHMENT; i++) {
					if(attachment[i] == null)
						continue;
					
					// 计算附件图标的位置
					if(i % 2 == 0) {
						evenX -= attachmentBound[i].width;
						attX = evenX;
					} else {
						attX = oddX;
						oddX += attachmentBound[i].width + ATTACHMENT_SPACING;
					}
					attY = imageY + size - attachmentBound[i].height;
					
					if(containsPoint(attX, attY, attachmentBound[i].width, attachmentBound[i].height, x, y))
						return i;
				}
				return -1;
			default:
				SWT.error(SWT.ERROR_INVALID_RANGE);
				return -1;
		}
	}
	
	/**
	 * 测试pX, pY是否落在前面四个参数指定的矩形中
	 * 
	 * @param x
	 * 		矩形左上角x坐标
	 * @param y
	 * 		矩形左上角y坐标
	 * @param w
	 * 		矩形宽度
	 * @param h
	 * 		矩形高度
	 * @param pX
	 * 		点x坐标
	 * @param pY
	 * 		点y坐标
	 * @return
	 * 		true表示包含
	 */
	private boolean containsPoint(int x, int y, int w, int h, int pX, int pY) {
		return pX >= x && pX < x + w && pY >= y && pY < y + h; 
	}

	/**
	 * 测试x，y表示的坐标是否在icon上方
	 * 
	 * @param x
	 * 		x坐标，这个坐标是相对于item的坐标
	 * @param y
	 * 		y坐标，这个坐标是相对于item的坐标
	 * @return
	 * 		true表示是
	 */
	private boolean isOnIcon(int x, int y) {
		int size = parent.getLevelImageSize(level);
		switch(parent.getLevelLayout(level)) {
			case HORIZONTAL:				
				if(x < ITEM_LEFT_MARING || x >= ITEM_LEFT_MARING + size)
					return false;
				if(y < ITEM_TOP_MARGIN || y >= ITEM_TOP_MARGIN + size)
					return false;
				return true;
			case VERTICAL:
				int width = parent.getItemWidth(level);
				int imageX = (width - size) >>> 1;
				if(x < imageX || x >= imageX + size)
					return false;
				if(y < ITEM_TOP_MARGIN || y >= ITEM_TOP_MARGIN + size)
					return false;
				return true;
			default:
				SWT.error(SWT.ERROR_INVALID_RANGE);
				return false;
		}
	}
	
	/**
	 * 检查鼠标是否在item的文本上
	 * 
	 * @param x
	 * 		x坐标，这个坐标是相对于item的坐标
	 * @param y
	 * 		y坐标，这个坐标是相对于item的坐标
	 * @return
	 * 		true表示是
	 */
	private boolean isOnText(int x, int y) {
		if(mouseOnAttachment != -1 || mouseOnIcon)
			return false;
		
		int size = parent.getLevelImageSize(level);
		switch(parent.getLevelLayout(level)) {
			case HORIZONTAL:				
				int left = ITEM_LEFT_MARING + size + ITEM_IMAGE_TEXT_SPACING;
				int top = (parent.getItemHeight(level) - parent.fontHeight) >>> 1;
				if(x < left || x >= left + textWidth)
					return false;
				if(y < top || y >= top + parent.fontHeight)
					return false;
				return true;
			case VERTICAL:
				left = Math.max(0, (parent.getItemWidth(level) - textWidth) >> 1);
				top = ITEM_TOP_MARGIN + size + ITEM_IMAGE_TEXT_SPACING;
				if(x < left || x >= left + textWidth)
					return false;
				if(y < top || y >= top + parent.fontHeight)
					return false;
				return true;
			default:
				SWT.error(SWT.ERROR_INVALID_RANGE);
				return false;
		}
	}

    /**
     * 画Hover边界，就是鼠标移动到上面显示的边界
     * 
     * @param gc
     * 		GC
     * @param x
     * 		矩形左上角x坐标
     * @param y
     * 		矩形左上角y坐标
     * @param w
     * 		矩形宽度
     * @param h
     * 		矩形高度
     */
    void paintHoverBorder(GC gc, int x, int y, int w, int h) {
        Display disp = getDisplay();
        Color c1 = null;
        Color c2 = null;
        c1 = disp.getSystemColor(SWT.COLOR_DARK_YELLOW);
        c2 = disp.getSystemColor(SWT.COLOR_DARK_YELLOW);
        if(c1 != null && c2 != null)
            paintBevelRect(gc, x, y, w, h, c1, c2);
    }

    /**
     * 画一个Bevel矩形
     * 
     * @param gc
     * 		GC
     * @param x
     * 		矩形左上角x坐标
     * @param y
     * 		矩形左上角y坐标
     * @param w
     * 		矩形宽度
     * @param h
     * 		矩形高度
     * @param topleft
     * 		上边缘颜色
     * @param bottomright
     * 		下边缘颜色
     */
    void paintBevelRect(GC gc, int x, int y, int w, int h, Color topleft, Color bottomright) {
        Color old = gc.getForeground();
        gc.setForeground(bottomright);
        gc.drawLine(x + w, y, x + w, y + h);
        gc.drawLine(x, y + h, x + w, y + h);
        gc.setForeground(topleft);
        gc.drawLine(x, y, (x + w) - 1, y);
        gc.drawLine(x, y, x, (y + h) - 1);
        gc.setForeground(old);
    }

	/**
	 * @return Returns the mouseOnIcon.
	 */
	public boolean isMouseOnIcon() {
		return mouseOnIcon;
	}

	/**
	 * @return Returns the mouseOnAttachment.
	 */
	public int getMouseOnAttachment() {
		return mouseOnAttachment;
	}
	
	/**
	 * @return
	 * 		true表示鼠标在空白区域
	 */
	public boolean isMouseOnBlank() {
		return !isMouseOnIcon() && getMouseOnAttachment() == -1 && !isMouseOnText();
	}
	
	/**
	 * @return
	 * 		文字所占的矩形区域，相对于item左上角来说
	 */
	Rectangle getTextBound() {
		if(getText() == null)
			return DUMMY_BOUND;
		
		int itemWidth = parent.getItemWidth(level);
		int itemHeight = parent.getItemHeight(level);
		int x = parent.getItemIndent(level);
		int max;
		Point size;
		String text = getText();
		Rectangle rect = new Rectangle(0, 0, 0, 0);
		GC gc = new GC(parent);
		switch(parent.getLevelLayout(level)) {
			case HORIZONTAL:
				rect.x = ITEM_LEFT_MARING + parent.getLevelImageSize(level) + ITEM_IMAGE_TEXT_SPACING;
				if(prefix != null)
					rect.x += PREFIX_ICON_SPACING + prefix.getBounds().width;
				int textX = x + rect.x;
				max = itemWidth - textX;
				size = gc.textExtent(text);
				rect.width = Math.min(max, size.x);
				rect.height = size.y;
				rect.y = (itemHeight - parent.fontHeight) >>> 1;
				break;
			case VERTICAL:
				size = gc.textExtent(text);
				rect.x = Math.max(0, (itemWidth - size.x) >> 1);
				rect.y = ITEM_TOP_MARGIN + parent.getLevelImageSize(level) + ITEM_IMAGE_TEXT_SPACING;
				rect.width = Math.min(itemWidth, size.x);
				rect.height = size.y;
				break;
			default:
				SWT.error(SWT.ERROR_INVALID_RANGE);
				break;
		}
		gc.dispose();
		return rect;
	}

	/**
	 * @return Returns the prefix.
	 */
	public Image getPrefix() {
		return prefix;
	}

	/**
	 * @param prefix The prefix to set.
	 */
	public void setPrefix(Image prefix) {
		setPrefix(prefix, true);
	}
	
	public void setPrefix(Image prefix, boolean redraw) {
		this.prefix = prefix;
		if(redraw)
			parent.updateItem(this);
	}

	public boolean isMouseOnText() {
		return mouseOnText;
	}
}
