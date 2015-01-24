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
package edu.tsinghua.lumaqq.ui.helper;


import static java.lang.Math.max;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseTrackAdapter;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.FontMetrics;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.Text;

import edu.tsinghua.lumaqq.resource.Colors;
import edu.tsinghua.lumaqq.widgets.mac.Ring;
import edu.tsinghua.lumaqq.widgets.qstyle.Slat;

/**
 * 一些用在page创建时的工具方法
 * 
 * @author luma
 */
public class UITool {
    private static Color DEFAULT_BACKGROUND;
    
    private static class LinkMouseTrackListener extends MouseTrackAdapter {
        private Color fg;
        private Color hoverColor;
        
        public LinkMouseTrackListener(Color fg, Color hoverColor) {
            this.fg = fg;
            this.hoverColor = hoverColor;
        }
        
        /* (non-Javadoc)
         * @see org.eclipse.swt.events.MouseTrackAdapter#mouseEnter(org.eclipse.swt.events.MouseEvent)
         */
        @Override
		public void mouseEnter(MouseEvent e) {
            ((Control)e.getSource()).setForeground(hoverColor);
        }
        
        /* (non-Javadoc)
         * @see org.eclipse.swt.events.MouseTrackAdapter#mouseExit(org.eclipse.swt.events.MouseEvent)
         */
        @Override
		public void mouseExit(MouseEvent e) {
            ((Control)e.getSource()).setForeground(fg);
        }
    }
    
    /**
     * 创建一个Group控件
     * 
     * @param container
     * 		父容器
     * @param text
     * 		控件标签
     * @return
     * 		Group
     */
    public static Group createGroup(Composite container, String text) {
        return createGroup(container, text, new GridData(GridData.FILL_HORIZONTAL));
    }
    
    /**
     * 创建一个Group控件
     * 
     * @param container
     * 		父容器 
     * @param text
     * 		标签
     * @param layoutData
     * 		布局数据
     * @return
     * 		Group对象
     */
    public static Group createGroup(Composite container, String text, Object layoutData) {
        GridLayout layout = new GridLayout(2, true);
        layout.marginHeight = layout.marginWidth = 8;
        return createGroup(container, text, layoutData, layout);
    }
    
    /**
     * 创建一个Group控件
     * 
     * @param container
     * 		父容器 
     * @param text
     * 		标签
     * @param layout
     * 		布局
     * @return
     * 		Group对象
     */
    public static Group createGroup(Composite container, String text, Layout layout) {
        return createGroup(container, text, new GridData(GridData.FILL_HORIZONTAL), layout);
    }
    
    /**
     * 创建一个Group控件
     * 
     * @param container
     * 		父容器 
     * @param text
     * 		标签
     * @param layoutData
     * 		布局数据
     * @param layout
     * 		布局
     * @return
     * 		Group对象
     */
    public static Group createGroup(Composite container, String text, Object layoutData, Layout layout) {
        Group group = new Group(container, SWT.SHADOW_ETCHED_OUT);
        group.setText(text);
        group.setLayoutData(layoutData);
        group.setBackground(getDefaultBackground());
        group.setForeground(Colors.CONFIG_GROUP_FOREGROUND);
        group.setLayout(layout);
        return group;
    }
    
    /**
     * 创建一个checkbox，缺省使用页背景色和缺省layout data
     * 
     * @param parent
     * 		父容器
     * @param text
     * 		标签
     * @return
     * 		Button对象
     */
    public static Button createCheckbox(Composite parent, String text) {
        return createCheckbox(parent, text, new GridData());
    }
    
    /**
     * 创建一个checkbox，缺省使用页背景色和缺省layout data
     * 
     * @param parent
     * 		父容器
     * @param text
     * 		标签
     * @param layoutData
     * 		布局数据
     * @return
     * 		Button对象
     */
    public static Button createCheckbox(Composite parent, String text, Object layoutData) {
		Button checkbox = new Button(parent, SWT.CHECK | SWT.FLAT);
		checkbox.setBackground(getDefaultBackground());
		checkbox.setText(text);
		checkbox.setLayoutData(layoutData);		
		return checkbox;
    }
    
    /**
     * 创建一个radio button，缺省使用页背景色和缺省layout data
     * 
     * @param parent
     * 		父容器
     * @param text
     * 		标签
     * @return
     * 		Button对象
     */
    public static Button createRadio(Composite parent, String text) {
        return createRadio(parent, text, SWT.RADIO | SWT.FLAT);
    }
    
    /**
     * 创建一个radio button，缺省使用页背景色和缺省layout data
     * 
     * @param parent
     * 		父容器
     * @param text
     * 		标签
     * @param style
     * 		样式
     * @return
     * 		Button对象
     */
    public static Button createRadio(Composite parent, String text, int style) {
        Button radio = new Button(parent, style);
        radio.setBackground(getDefaultBackground());
        radio.setText(text);
        radio.setLayoutData(new GridData());
        return radio;
    }
    
    /**
     * 创建一个Flat按钮
     * 
     * @param parent
     * 		父容器
     * @param text
     * 		文本
     * @return
     * 		Button对象
     */
    public static Button createButton(Composite parent, String text) {
        return createButton(parent, text, new GridData());
    }
    
    /**
     * 创建一个Flat按钮
     * 
     * @param parent
     * 		父容器
     * @param text
     * 		文本
     * @param layoutData
     * 		布局数据
     * @return
     * 		Button对象
     */
    public static Button createButton(Composite parent, String text, Object layoutData) {
        return createButton(parent, text, layoutData, SWT.FLAT);
    }
    
    /**
     * 创建一个Flat按钮
     * 
     * @param parent
     * 		父容器
     * @param text
     * 		文本
     * @param style
     * 		样式 
     * @return
     * 		Button对象
     */
    public static Button createButton(Composite parent, String text, int style) {
        return createButton(parent, text, new GridData(), style);
    }
    
    /**
     * 创建一个Flat按钮
     * 
     * @param parent
     * 		父容器
     * @param text
     * 		文本
     * @param layoutData
     * 		布局数据
     * @param style
     * 		样式
     * @return
     * 		Button对象
     */
    public static Button createButton(Composite parent, String text, Object layoutData, int style) {
        Button button = new Button(parent, style);
        button.setText(text);
        button.setLayoutData(layoutData);
        return button;
    }
    
    /**
     * 创建一个标签控件
     * 
     * @param parent
     * 		父容器
     * @param text
     * 		文本
     * @return
     * 		Label对象
     */
    public static Label createLabel(Composite parent, String text) {
        return createLabel(parent, text, new GridData());
    }
    
    /**
     * 创建一个标签控件
     * 
     * @param parent
     * 		父容器
     * @param text
     * 		文本
     * @param layoutData
     * 		布局数据
     * @return
     * 		Label对象
     */
    public static Label createLabel(Composite parent, String text, Object layoutData) {
        return createLabel(parent, text, layoutData, SWT.LEFT);
    }
    
    /**
     * 创建一个Ring组件
     * 
     * @param parent
     * 		父容器
     * @param layoutData
     * 		布局数据
     * @return
     * 		Ring
     */
    public static Ring createRing(Composite parent, Object layoutData) {
    	Ring ring = new Ring(parent);
    	ring.setLayoutData(layoutData);
    	ring.setBackground(parent.getBackground());
    	return ring;
    }
    
    /**
     * 创建一个Ring组件
     * 
     * @param parent	
     * 		父容器
     * @return
     * 		Ring
     */
    public static Ring createRing(Composite parent) {
        GridData gd = new GridData();
        gd.widthHint = gd.heightHint = 18;
        return createRing(parent, gd);
    }
    
    /**
     * 创建一个标签控件
     * 
     * @param parent
     * 		父容器
     * @param text
     * 		文本
     * @param style
     * 		样式
     * @return
     * 		Label对象
     */
    public static Label createLabel(Composite parent, String text, int style) {
        return createLabel(parent, text, new GridData(), style);
    }
    
    /**
     * 创建一个标签控件
     * 
     * @param parent
     * 		父容器
     * @param text
     * 		文本
     * @param layoutData
     * 		布局数据
     * @param style
     * 		样式
     * @return
     * 		Label对象
     */
    public static Label createLabel(Composite parent, String text, Object layoutData, int style) {
        Label label = new Label(parent, style);
        label.setText(text);
        label.setBackground(getDefaultBackground());
        label.setLayoutData(layoutData);
        return label;
    }
    
    /**
     * 创建一个CCombo控件
     * 
     * @param parent
     * 		父容器
     * @return
     * 		CCombo
     */
    public static CCombo createCCombo(Composite parent) {
        return createCCombo(parent, new GridData(), SWT.FLAT | SWT.READ_ONLY);
    }
    
    /**
     * 创建一个CCombo控件
     * 
     * @param parent
     * 		父容器
     * @param layoutData
     * 		布局数据
     * @return
     * 		CCombo
     */
    public static CCombo createCCombo(Composite parent, Object layoutData) {
        return createCCombo(parent, layoutData, SWT.FLAT | SWT.READ_ONLY);
    }
    
    /**
     * 创建一个CCombo控件
     * 
     * @param parent
     * 		父容器
     * @param layoutData
     * 		布局数据
     * @param style
     * 		样式
     * @return
     * 		CCombo
     */
    public static CCombo createCCombo(Composite parent, Object layoutData, int style) {
        CCombo combo = new CCombo(parent, style);
        combo.setBackground(getDefaultBackground());
        combo.setLayoutData(layoutData);
        return combo;
    }
    
    /**
     * 创建一个单行文本框
     * 
     * @param parent
     * 		父容器
     * @return
     * 		Text
     */
    public static Text createSingleText(Composite parent) {
        return createSingleText(parent, new GridData(), SWT.SINGLE);
    }
    
    /**
     * 创建一个单行文本框
     * 
     * @param parent
     * 		父容器
     * @param style
     * 		样式
     * @return
     * 		Text
     */
    public static Text createSingleText(Composite parent, int style) {
        return createSingleText(parent, new GridData(), style);
    }
    
    /**
     * 创建一个单行文本框
     * 
     * @param parent
     * 		父容器
     * @param layoutData
     * 		布局数据
     * @return
     * 		Text
     */
    public static Text createSingleText(Composite parent, Object layoutData) {
        return createSingleText(parent, layoutData, SWT.SINGLE);
    }
    
    /**
     * 创建一个单行文本框
     * 
     * @param parent
     * 		父容器
     * @param layoutData
     * 		布局数据
     * @param style
     * 		样式
     * @return
     * 		Text
     */
    public static Text createSingleText(Composite parent, Object layoutData, int style) {
        Text text = new Text(parent, style);
        text.setBackground(getDefaultBackground());
        text.setLayoutData(layoutData);
        return text;
    }
    
    /**
     * 创建一个多行文本框
     * 
     * @param parent
     * 		父容器
     * @param layoutData
     * 		布局数据
     * @return
     * 		Text
     */
    public static Text createMultiText(Composite parent, Object layoutData) {
        return createMultiText(parent, layoutData, SWT.MULTI | SWT.WRAP | SWT.V_SCROLL);
    }
    
    /**
     * 创建一个多行文本框
     * 
     * @param parent
     * 		父容器
     * @param layoutData
     * 		布局数据
     * @param style
     * 		样式
     * @return
     * 		Text
     */
    public static Text createMultiText(Composite parent, Object layoutData, int style) {
        Text text = new Text(parent, style);
        text.setBackground(getDefaultBackground());
        text.setLayoutData(layoutData);
        return text;
    }    
    
    /**
     * 创建一个容器
     * 
     * @param parent
     * 		父容器
     * @param layoutData
     * 		布局数据
     * @param layout
     * 		布局
     * @return
     * 		Composite
     */
    public static Composite createContainer(Composite parent, Object layoutData, Layout layout) {
    	return createContainer(parent, layoutData, layout, SWT.NONE);
    }
    
    /**
     * 创建一个容器
     * 
     * @param parent
     * 		父容器
     * @param layoutData
     * 		布局数据
     * @param layout
     * 		布局
     * @param style
     * 		样式
     * @return
     * 		Composite
     */
    public static Composite createContainer(Composite parent, Object layoutData, Layout layout, int style) {
        Composite c = new Composite(parent, style);
        c.setBackground(getDefaultBackground());
        c.setLayoutData(layoutData);
        c.setLayout(layout);
        return c;
    }
    
    /**
     * 创建一个只支持文字的link
     * 
     * @param parent
     * @param text
     * @return
     */
    public static Label createSimpleLink(Composite parent, String text) {
        return createSimpleLink(parent, text, new GridData());
    }
    
    /**
     * 创建一个只支持文字的link
     * 
     * @param parent
     * @param text
     * @param layoutData
     * @return
     */
    public static Label createSimpleLink(Composite parent, String text, Object layoutData) {
        return createSimpleLink(parent, text, Colors.BLUE, Colors.RED);
    }
    
    /**
     * 创建一个只支持文字的link
     * 
     * @param parent
     * @param text
     * @param fg
     * @param hg
     * @return
     */
    public static Label createSimpleLink(Composite parent, String text, Color fg, Color hg) {
        return createSimpleLink(parent, text, fg, hg, new GridData());
    }
    
    /**
     * 创建一个只支持文字的link
     * 
     * @param parent
     * @param text
     * @param fg
     * @param hg
     * @param layoutData
     * @return
     */
    public static Label createSimpleLink(Composite parent, String text, Color fg, Color hg, Object layoutData) {
        Label link = new Label(parent, SWT.LEFT);
        link.setBackground(getDefaultBackground());
        link.setText(text);
        link.setCursor(Display.getCurrent().getSystemCursor(SWT.CURSOR_HAND));
        link.addMouseTrackListener(new LinkMouseTrackListener(fg, hg));
        link.setForeground(fg);
        return link;
    }
    
    /**
     * 创建一个link样式的label
     * 
     * @param parent
     * 		父容器
     * @param text
     * 		文本
     * @param image
     * 		图标
     * @return
     * 		CLabel
     */
    public static CLabel createLink(Composite parent, String text, Image image) {
        return createLink(parent, text, image, new GridData());
    }
    
    /**
     * 创建一个link样式的label
     * 
     * @param parent
     * 		父容器
     * @param text
     * 		文本
     * @param image
     * 		图标
     * @param layoutData
     * 		布局数据
     * @return
     * 		CLabel
     */
    public static CLabel createLink(Composite parent, String text, Image image, Object layoutData) {
        return createLink(parent, text, image, Colors.BLUE, Colors.RED, layoutData);
    }
    
    /**
     * 创建一个link样式的label
     * 
     * @param parent
     * @param text
     * @param image
     * @param fg
     * @param hg
     * @return
     */
    public static CLabel createLink(Composite parent, String text, Image image, Color fg, Color hg) {
        return createLink(parent, text, image, fg, hg, new GridData());
    }
    
    /**
     * @param parent
     * @param text
     * @param image
     * @param fg
     * @param hg
     * @param layoutData
     * @return
     */
    public static CLabel createLink(Composite parent, String text, Image image, Color fg, Color hg, Object layoutData) {
        CLabel link = new CLabel(parent, SWT.LEFT);
        link.setLayoutData(layoutData);
        link.setForeground(Display.getCurrent().getSystemColor(SWT.COLOR_BLUE));
        link.setImage(image);
        link.setText(text);
        link.setCursor(Display.getCurrent().getSystemCursor(SWT.CURSOR_HAND));
        link.setBackground(getDefaultBackground());
        link.addMouseTrackListener(new LinkMouseTrackListener(fg, hg));
        return link;
    }
    
    public static Slat createSlat(Composite parent, String text) {
        return createSlat(parent, text, new GridData());
    }
    
    public static Slat createSlat(Composite parent, String text, GridData gd) {
        return createSlat(parent, text, gd, SWT.CENTER);
    }
    
    public static Slat createSlat(Composite parent, String text, GridData gd, int style) {
    	Slat button = new Slat(parent, SWT.CENTER);
        button.setText(text);
        button.setLayoutData(gd);
        return button;
    }
    
    /**
     * 设置缺省背景色
     * 
     * @param c
     */
    public static void setDefaultBackground(Color c) {
        DEFAULT_BACKGROUND = c;
    }
    
    /**
     * @return
     * 		缺省背景色
     */
    public static Color getDefaultBackground() {
        if(DEFAULT_BACKGROUND == null)
            return Colors.PAGE_BACKGROUND;
        else
            return DEFAULT_BACKGROUND;
    }
    
	/**
	 * 计算字体高度
	 * 
	 * @return
	 * 		字体高度象素
	 */
	public static int calculateDefaultFontHeight(GC gc) {
		Font originalFont = gc.getFont();
		FontData[] fontDatas = originalFont.getFontData();	
		FontMetrics metrics = gc.getFontMetrics();
		
		int ascent = metrics.getAscent() + metrics.getLeading();
		int descent = metrics.getDescent();
		
		getFontData(fontDatas, SWT.BOLD);
		Font boldFont = new Font(getDisplay(), fontDatas);
		gc.setFont(boldFont);
		metrics = gc.getFontMetrics();
		ascent = max(ascent, metrics.getAscent() + metrics.getLeading());
		descent = max(descent, metrics.getDescent());
		boldFont.dispose();
		
		getFontData(fontDatas, SWT.ITALIC);
		Font italicFont = new Font(getDisplay(), fontDatas);
		gc.setFont(italicFont);
		metrics = gc.getFontMetrics();
		ascent = max(ascent, metrics.getAscent() + metrics.getLeading());
		descent = max(descent, metrics.getDescent());
		italicFont.dispose();
		
		getFontData(fontDatas, SWT.BOLD | SWT.ITALIC);
		Font boldItalicFont = new Font(getDisplay(), fontDatas);
		gc.setFont(boldItalicFont);
		metrics = gc.getFontMetrics();
		ascent = max(ascent, metrics.getAscent() + metrics.getLeading());
		descent = max(descent, metrics.getDescent());
		boldItalicFont.dispose();
		
		gc.setFont(originalFont);
		
		return ascent + descent;
	}
	
	private static Display getDisplay() {
		return Display.getCurrent();
	}

	/**
	 * 设置font data为某种样式
	 * 
	 * @param fontDatas
	 * 		FontData数组
	 * @param style
	 * 		样式
	 */
	private static void getFontData(FontData[] fontDatas, int style) {
		for(FontData fd : fontDatas)
			fd.setStyle(style);
	}
}