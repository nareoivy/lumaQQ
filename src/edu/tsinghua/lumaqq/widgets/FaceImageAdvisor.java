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

import edu.tsinghua.lumaqq.ecore.face.Face;
import edu.tsinghua.lumaqq.resource.Resources;
import edu.tsinghua.lumaqq.ui.MainShell;
import edu.tsinghua.lumaqq.ui.helper.FaceRegistry;

/**
 * 用在聊天窗口中，提供表情选择功能
 * 
 * @author luma
 */
public class FaceImageAdvisor implements IImageSelectorAdvisor {
    private FaceRegistry util;
    private Resources res;
    private MainShell main;
     
	private static final int SIZE = 20;
	private static final int MARGIN = 3;
	private static final int GRID = MARGIN * 2 + SIZE;
	private static final int ROW = 8;
	private static final int COL = 15;
    
    public FaceImageAdvisor(MainShell m) {
    	main = m;
    	util = FaceRegistry.getInstance();
        res = Resources.getInstance();
    }
    
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.ui.IImageProvider#getGroupCount()
     */
    public int getGroupCount() {
        return util.getGroupCount() + 1;
    }
    
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.ui.IImageProvider#getImageCount(int)
     */
    public int getImageCount(int group) {
        switch(group) {
            case 0:
                return 96;
            default:
                return util.getFaceCount(group - 1);
        }
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.ui.IImageProvider#getGroupName(int)
     */
    public String getGroupName(int group) {
        switch(group) {
            case 0:
                return image_group_default;
            default:
                return util.getFaceGroup(group - 1).getName();
        }
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.ui.IImageProvider#getImage(int, int)
     */
    public Image getImage(int group, int sequence) {
        switch(group) {
            case 0:
                return res.getFaceBySequence(sequence);
            default:
                Face face = util.getFace(group - 1, sequence);
	            if(face == null)
	                return null;
	            return res.getSmallCustomFace(face.getMd5());
        }
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.ui.IImageProvider#getImageCode(int, int)
     */
    public int getImageCode(int group, int sequence) {
        switch(group) {
            case 0:
                return res.getFaceCode(sequence);
            default:
                Face face = util.getFace(group - 1, sequence);
	            if(face == null)
	                return 0;
	            return face.getId();
        }
    }
    
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.ui.IImageProvider#canPagination(int)
     */
    public boolean canPagination(int group) {
        return group != 0;
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.widgets.IImageSelectorAdvisor#isAuxiliaryLinkVisible()
     */
    public boolean isAuxiliaryLinkVisible() {
        return true;
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.ui.IImageProvider#getLinkLabel()
     */
    public String getLinkLabel() {
        return image_link_add;
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.ui.IImageProvider#canNext(int, int)
     */
    public boolean canNext(int group, int currentPage) {
        if(group == 0)
            return false;
        
        int total = getImageCount(group) / (ROW * COL) + 1;
        return currentPage < total - 1;
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.ui.IImageProvider#canPrevious(int, int)
     */
    public boolean canPrevious(int group, int currentPage) {
        if(group == 0)
            return false;
        return currentPage > 0;
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.ui.IImageProvider#doLink(edu.tsinghua.lumaqq.ui.FaceSelectShell)
     */
    public void doLink(ImageSelector fss) {
        fss.getShell().close();
        main.getShellLauncher().openFaceWindow();
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
