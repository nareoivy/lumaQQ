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
package edu.tsinghua.lumaqq.widgets;

import static edu.tsinghua.lumaqq.resource.Messages.*;

import org.eclipse.swt.graphics.Image;

import edu.tsinghua.lumaqq.resource.Resources;

/**
 * 头像图片提供器，用来为图片选择窗口提供头像选择的能力
 * 
 * @author luma
 */
public class HeadImageAdvisor implements IImageSelectorAdvisor {
	private static final int SIZE = 40;
	private static final int MARGIN = 3;
	private static final int GRID = MARGIN * 2 + SIZE;
	private static final int ROW = 5;
	private static final int COL = 8;
	
	private static final int[][] headMap = new int[][] {
	        // 注意序号从0开始
	        // 男士头像
	        {
	            4,  6,  9,  13, 14, 15, 16, 27,
	            35, 36, 42, 43, 45, 49, 51, 52,
	            53, 59, 60, 62, 67, 71, 73, 76,
	            78, 79, 81, 84, 93, 94
            },
            
            // 女士头像
            {
                5,  8,  11, 19, 28, 29, 33, 37,
                39, 44, 46, 48, 50, 54, 56, 57,
                61, 66, 69, 74, 77, 80, 82, 83, 
                85, 86, 87, 88, 89
            },
            
            // 宠物头像
            {
                0,  1,  2,  3,  7,  10, 12, 16,
                17, 18, 20, 21, 22, 23, 24, 25,
                30, 31, 32, 34, 38, 40, 41, 47,
                55, 58, 63, 64, 65, 68, 70, 72,
                75, 90, 91, 92
            },
            
            // QQ堂头像
            {
                95, 96, 97, 98, 99
            }
	};
	
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.ui.IImageProvider#getGroupCount()
     */
    public int getGroupCount() {
        return 4;
    }
    
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.ui.IImageProvider#getImageCount(int)
     */
    public int getImageCount(int group) {
        return headMap[group].length;
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.ui.IImageProvider#getGroupName(int)
     */
    public String getGroupName(int group) {
        switch(group) {
            case 0:
                return image_group_man;
            case 1:
                return image_group_woman;
            case 2:
                return image_group_pet;
            case 3:
                return image_group_qqtang;
            default:
                return "";
        }
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.ui.IImageProvider#getImage(int, int)
     */
    public Image getImage(int group, int sequence) {
        if(sequence < 0 || sequence >= headMap[group].length)
            return null;
        int code = headMap[group][sequence] * 3;
        return Resources.getInstance().getHead(code);
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.ui.IImageProvider#getImageCode(int, int)
     */
    public int getImageCode(int group, int sequence) {
        if(sequence < 0 || sequence >= headMap[group].length)
            return -1;
        return headMap[group][sequence] * 3;
    }
    
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.ui.IImageProvider#canPagination(int)
     */
    public boolean canPagination(int group) {
        return false;
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.ui.IImageProvider#canNext(int, int)
     */
    public boolean canNext(int group, int currentPage) {
        return false;
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.ui.IImageProvider#canPrevious(int, int)
     */
    public boolean canPrevious(int group, int currentPage) {
        return false;
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.widgets.IImageSelectorAdvisor#isAuxiliaryLinkVisible()
     */
    public boolean isAuxiliaryLinkVisible() {
        return false;
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.ui.IImageProvider#doLink(edu.tsinghua.lumaqq.ui.FaceSelectShell)
     */
    public void doLink(ImageSelector fss) {
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.ui.IImageProvider#getLinkLabel()
     */
    public String getLinkLabel() {
        return "";
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.ui.IImageProvider#getGridSize()
     */
    public int getGridSize() {
        return GRID;
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.ui.IImageProvider#getImageSize()
     */
    public int getImageSize() {
        return SIZE;
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.ui.IImageProvider#getRow()
     */
    public int getRow() {
        return ROW;
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.ui.IImageProvider#getCol()
     */
    public int getCol() {
        return COL;
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.ui.IImageProvider#getMargin()
     */
    public int getMargin() {
        return MARGIN;
    }
}
