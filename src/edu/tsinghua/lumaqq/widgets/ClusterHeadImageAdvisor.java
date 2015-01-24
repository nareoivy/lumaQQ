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

import static edu.tsinghua.lumaqq.resource.Messages.image_group_cluster;

import org.eclipse.swt.graphics.Image;

import edu.tsinghua.lumaqq.resource.Resources;

/**
 * 为图片选择窗口提供选择群头像的能力
 * 
 * @author luma
 */
public class ClusterHeadImageAdvisor implements IImageSelectorAdvisor {
	private static final int SIZE = 32;
	private static final int MARGIN = 3;
	private static final int GRID = MARGIN * 2 + SIZE;
	private static final int ROW = 5;
	private static final int COL = 8;
	
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.ui.IImageProvider#getGroupCount()
     */
    public int getGroupCount() {
        return 1;
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.ui.IImageProvider#getImageCount(int)
     */
    public int getImageCount(int group) {
        switch(group) {
            default:
                return 6;
        }
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.ui.IImageProvider#getImageCode(int, int)
     */
    public int getImageCode(int group, int sequence) {
        if(sequence < 0 || sequence > 5)
            return -1;
        return sequence + 1;
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.ui.IImageProvider#getGroupName(int)
     */
    public String getGroupName(int group) {
        return image_group_cluster;
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.ui.IImageProvider#getImage(int, int)
     */
    public Image getImage(int group, int sequence) {
        if(sequence < 0 || sequence > 5)
            return null;
        return Resources.getInstance().getClusterHead(sequence + 1);
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
