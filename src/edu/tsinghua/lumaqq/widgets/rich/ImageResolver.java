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
package edu.tsinghua.lumaqq.widgets.rich;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageLoader;

import edu.tsinghua.lumaqq.resource.Resources;
import edu.tsinghua.lumaqq.ui.helper.FaceRegistry;

/**
 * 维护动画：载入，释放，等等
 * 
 * @author luma
 */
public class ImageResolver implements DisposeListener {
    // 图片哈希，key是图片tag和图片索引的组合，value是ImageLoader对象
    private Map<Integer, ImageLoader> loaders;
    
    // Image对象缓冲区，用来保存Image的引用，释放资源用
    private Map<Integer, Image> imagePool;
    
    private RichBox richbox;
    
    public ImageResolver(RichBox richbox) {
        this.richbox = richbox;
        imagePool = new HashMap<Integer, Image>();
        loaders = new HashMap<Integer, ImageLoader>();
    }
    
    /**
     * 得到图片的Image对象
     * 
     * @param tag
     * @param code
     * @return
     */
    public Image getImage(int tag, int code) {
        Image image = imagePool.get(getKey(tag, code));
        if(image != null)
            return image;
            
        ImageLoader loader = getImageLoader(tag, code);
        if(loader == null) {
            return Resources.getInstance().getImage(Resources.icoNotFound);            
        }
        Image ret = null;
        if(loader.logicalScreenHeight <= 0 || loader.logicalScreenWidth <= 0)
        	ret = new Image(richbox.getDisplay(), loader.data[0]);
        else {
        	ret = new Image(richbox.getDisplay(), loader.logicalScreenWidth, loader.logicalScreenHeight);
        	GC gc = new GC(ret);
        	Image img = new Image(richbox.getDisplay(), loader.data[0]);
        	gc.drawImage(img, 0,
        			0,
        			loader.data[0].width,
        			loader.data[0].height,
        			loader.data[0].x,
        			loader.data[0].y,
        			loader.data[0].width,
        			loader.data[0].height);
        	img.dispose();
        	gc.dispose();
        }
        imagePool.put(getKey(tag, code), ret);
        return ret;
    }
    
    /**
     * 得到ImageLoader
     * 
     * @param tag
     * @param code
     * @return
     */
    public ImageLoader getImageLoader(int tag, int code) {
        int key = getKey(tag, code);
        ImageLoader loader = loaders.get(key);
        if(loader == null) {
            if(tag == IRichContent.DEFAULT_FACE_TAG) {
            	String path = Resources.getInstance().getFacePathByCode(code);
            	if(path == null)
            		return null;
            	
	            loader = new ImageLoader();
                loader.load(this.getClass().getResourceAsStream(path));
                loaders.put(key, loader);
            } else if(tag == IRichContent.CUSTOM_FACE_TAG){
            	String path = FaceRegistry.getInstance().getFacePath(code);
            	if(path == null)
            	    return null;
            	
            	loader = new ImageLoader();      
            	try {
                    loader.load(path);
                } catch (Exception e) {
                    return null;
                }
            	loaders.put(key, loader);
            } else
            	return null;
        }
        return loader;
    }
    
    /**
     * 检查某个图片是否是动画
     * 
     * @param tag
     * @param code
     * @return
     * 		true表示是动画
     */
    public boolean isAnimate(int tag, int code) {
        ImageLoader loader = getImageLoader(tag, code);
        if(loader == null)
            return false;
        return loader.data.length > 1;
    }
    
    /**
     * 得到图像的key
     * 
     * @param tag
     * @param code
     * @return
     */
    private int getKey(int tag, int code) {
        return ((tag & 0xFFFF) << 16) | (code & 0xFFFF);
    }

    /* (non-Javadoc)
     * @see org.eclipse.swt.events.DisposeListener#widgetDisposed(org.eclipse.swt.events.DisposeEvent)
     */
    public void widgetDisposed(DisposeEvent e) {
		for(Image image : imagePool.values())
			image.dispose();
    }
}
