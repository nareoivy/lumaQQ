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
package edu.tsinghua.lumaqq.resource;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

/**
 * 颜色常量
 * 
 * @author luma
 */
public class Colors {
    /** 配置页文本色 */
    public static Color PAGE_TITLE_FOREGROUND;
    /** 配置页标题栏背景 */
    public static Color PAGE_TITLE_BACKGROUND;
    /** 配置页背景 */
    public static Color PAGE_BACKGROUND;
    /** 页中控件的边框色 */
    public static Color PAGE_CONTROL_BORDER;
    /** 页中只读控件的背景色 */
    public static Color PAGE_READONLY_CONTROL_BACKGROUND;
    /** 设置组的前景色 */
    public static Color CONFIG_GROUP_FOREGROUND;
    /** 对话框背景 */
    public static Color DIALOG_BACKGROUND;
    /** 页菜单背景 */
    public static Color PAGE_LIST_BACKGROUND;
    /** 选中的页菜单项背景色 */
    public static Color PAGE_LIST_SELECTED;
    /** 悬停的页菜单项背景色 */
    public static Color PAGE_LIST_HOVERD;
    /** 页菜单项边框色 */
    public static Color PAGE_LIST_BORDER;
    /** 对话框按钮背景 */
    public static Color DIALOG_BUTTON_BACKGROUND;
    /** 白色 */
    public static Color WHITE;
    /** 蓝色 */
    public static Color BLUE;
    /** 红色 */
    public static Color RED;
    /** 黄色 */
    public static Color YELLOW;
    /** 灰色 */
    public static Color GRAY;
    /** 黑色 */
    public static Color BLACK;
    /** 深绿 */
    public static Color DARK_GREEN;
    /** 灰色 */
    public static Color GRAY_TEXT;
    /** 淡灰 */
    public static Color GRAY_LIGTHEST;
    /** 深灰 */
    public static Color GRAY_DARKER;
    /** 控件边框 */
    public static Color WIDGET_BORDER;
    /** view form 标题条的背景色 */
    public static Color VIEWFORM_BANNER_BACKGROUND;
    /** 登录对话框背景色 */
    public static Color LOGIN_BACKGROUND;
    /** 好友提示窗口背景色 */
    public static Color FRIEND_TIP_BACKGROUND;
    /** 淡蓝 */
    public static Color LIGHT_BLUE;
    /** 淡蓝2 */
    public static Color LIGHT_BLUE_2;
    /** 上线提示背景 */
    public static Color ONLINE_TIP_BACKGROUND;
    /** richbox只读时的背景 */
    public static Color READONLY_BACKGROUND;
    /** 聊天模式时，我的名字的颜色 */
    public static Color MY_HINT_COLOR;
    /** 表情选择窗口的边框色 */
    public static Color FACE_SELECT_SHELL_BORDER;
    /** 表情之间的分隔线颜色 */
    public static Color FACE_SEPARATOR_BORDER;
    /** 表情管理窗口，tableitem背景色 */
    public static Color TABLE_ITEM_BACKGROUND;
    /** 在线颜色 */
    public static Color ONLINE;
    /** 隐身颜色 */
    public static Color HIDDEN;
    /** 离开颜色 */
    public static Color AWAY;
    /** 提示背景色 */
    public static Color TOOLTIP_BACKGROUND;
    /** QQ秀边框 */
    public static Color QQ_SHOW_BORDER;
    /** 主窗口背景 */
    public static Color MAINSHELL_BACKGROUND;
    /** 主窗口边框 */
    public static Color[] MAINSHELL_BORDERS;
    public static Color MAINSHELL_BORDER_OUTMOST;
    /** 标题条顶端 */
    public static Color MAINSHELL_TITLE_TOP;
    /** 标题条底端 */
    public static Color MAINSHELL_TITLE_BOTTOM;
    
    public static Color MAINSHELL_TITLE_SEPARATOR_TOP;
    public static Color MAINSHELL_TITLE_SEPARATOR_BOTTOM;
    
    public static Color VIEWFORM_BANNER_TEXT;
    public static Color VIEWFORM_BANNER_TEXT_HOVER;
    
    /**
     * 初始化颜色
     */
    public static void init() {
        Display display = Display.getCurrent();
        WHITE = display.getSystemColor(SWT.COLOR_WHITE);
        BLUE = display.getSystemColor(SWT.COLOR_BLUE);
        RED = display.getSystemColor(SWT.COLOR_RED);
        GRAY = display.getSystemColor(SWT.COLOR_GRAY);
        BLACK = display.getSystemColor(SWT.COLOR_BLACK);
        YELLOW = display.getSystemColor(SWT.COLOR_YELLOW);
        DARK_GREEN = display.getSystemColor(SWT.COLOR_DARK_GREEN);
        WIDGET_BORDER = display.getSystemColor(SWT.COLOR_WIDGET_BORDER);
        TOOLTIP_BACKGROUND = display.getSystemColor(SWT.COLOR_INFO_BACKGROUND);

        CONFIG_GROUP_FOREGROUND 			= new Color(display, 0x00, 0x45, 0xD6);
        FACE_SELECT_SHELL_BORDER 			= new Color(display, 0x00, 0x49, 0x94);
        MY_HINT_COLOR 						= new Color(display, 0x00, 0x82, 0x42);
        QQ_SHOW_BORDER						= new Color(display, 0x1E, 0x3E, 0x93);
        MAINSHELL_TITLE_TOP 				= new Color(display, 0x37, 0x85, 0xE1);
        VIEWFORM_BANNER_BACKGROUND 			= new Color(display, 0xD3, 0xE6, 0xFA);
        VIEWFORM_BANNER_TEXT 				= new Color(display, 0x07, 0x1E, 0x81);
        VIEWFORM_BANNER_TEXT_HOVER 			= new Color(display, 0x63, 0x77, 0xBE);
        MAINSHELL_BACKGROUND				= new Color(display, 0x58, 0xB3, 0xFF);
        AWAY 								= new Color(display, 0x5A, 0x86, 0xFF);
        GRAY_TEXT 							= new Color(display, 0x66, 0x66, 0x66);
        PAGE_LIST_BORDER 					= new Color(display, 0x6B, 0x75, 0xE7);
        PAGE_TITLE_BACKGROUND 				= new Color(display, 0x6B, 0x92, 0xEF);
        ONLINE 								= new Color(display, 0x6B, 0xC7, 0x18);
        MAINSHELL_TITLE_BOTTOM 				= new Color(display, 0x79, 0xDF, 0xFF);
        LIGHT_BLUE 							= new Color(display, 0x7B, 0x92, 0xE7);
        LIGHT_BLUE_2						= new Color(display, 0xE0, 0xEC, 0xFA);
        PAGE_CONTROL_BORDER 				= new Color(display, 0x7B, 0x9E, 0xBD);
        GRAY_DARKER							= new Color(display, 0xAF, 0xAC, 0xA5);
        PAGE_LIST_SELECTED 					= new Color(display, 0xB5, 0xBE, 0xFF);
        PAGE_LIST_HOVERD 					= new Color(display, 0xBD, 0xD3, 0xFF);
        FRIEND_TIP_BACKGROUND 				= new Color(display, 0xDE, 0xE7, 0xF7);
        GRAY_LIGTHEST						= new Color(display, 0xE9, 0xF3, 0xF5);
        DIALOG_BACKGROUND 					= new Color(display, 0xEF, 0xEB, 0xD6);
        PAGE_READONLY_CONTROL_BACKGROUND 	= new Color(display, 0xEF, 0xEB, 0xEF);
        PAGE_LIST_BACKGROUND 				= new Color(display, 0xEF, 0xF7, 0xFF);
        READONLY_BACKGROUND 				= new Color(display, 0xBC, 0xBD, 0xC7);
        LOGIN_BACKGROUND 					= new Color(display, 0xF0, 0xF8, 0xFF);
        HIDDEN 								= new Color(display, 0xFF, 0xB6, 0x39);
        MAINSHELL_TITLE_SEPARATOR_TOP 		= new Color(display, 0x3B, 0x75, 0xBC);
        MAINSHELL_TITLE_SEPARATOR_BOTTOM	= MAINSHELL_BACKGROUND;
        
        MAINSHELL_BORDER_OUTMOST = QQ_SHOW_BORDER;
        MAINSHELL_BORDERS = new Color[] {
        		MAINSHELL_BORDER_OUTMOST,
        		new Color(display, 0x79, 0xDF, 0xFF),
        		new Color(display, 0x77, 0xCD, 0xFF),
        		new Color(display, 0x63, 0xC0, 0xFF),
        		new Color(display, 0x37, 0x85, 0xE1)
        };
        
        FACE_SEPARATOR_BORDER = FRIEND_TIP_BACKGROUND;
        DIALOG_BUTTON_BACKGROUND = PAGE_LIST_BACKGROUND;
        ONLINE_TIP_BACKGROUND = LOGIN_BACKGROUND;
        PAGE_BACKGROUND = WHITE;
        PAGE_TITLE_FOREGROUND = WHITE;
    }
    
    /**
     * 释放颜色
     */
    public static void dispose() {
        PAGE_TITLE_BACKGROUND.dispose();
        PAGE_CONTROL_BORDER.dispose();
        DIALOG_BACKGROUND.dispose();
        PAGE_READONLY_CONTROL_BACKGROUND.dispose();
        CONFIG_GROUP_FOREGROUND.dispose();
        PAGE_LIST_BACKGROUND.dispose();
        PAGE_LIST_SELECTED.dispose();
        PAGE_LIST_HOVERD.dispose();
        PAGE_LIST_BORDER.dispose();
        VIEWFORM_BANNER_BACKGROUND.dispose();
        VIEWFORM_BANNER_TEXT.dispose();
        VIEWFORM_BANNER_TEXT_HOVER.dispose();
        LOGIN_BACKGROUND.dispose();
        LIGHT_BLUE.dispose();
        LIGHT_BLUE_2.dispose();
        READONLY_BACKGROUND.dispose();
        MY_HINT_COLOR.dispose();
        FACE_SELECT_SHELL_BORDER.dispose();
        AWAY.dispose();
        ONLINE.dispose();
        HIDDEN.dispose();
        GRAY_TEXT.dispose();
        GRAY_LIGTHEST.dispose();
        GRAY_DARKER.dispose();
        MAINSHELL_BACKGROUND.dispose();
        MAINSHELL_TITLE_BOTTOM.dispose();
        MAINSHELL_TITLE_TOP.dispose();
        MAINSHELL_TITLE_SEPARATOR_TOP.dispose();
        for(Color c : MAINSHELL_BORDERS)
        	c.dispose();
    }
}
