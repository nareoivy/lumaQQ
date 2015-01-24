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
package edu.tsinghua.lumaqq;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.widgets.Display;

import edu.tsinghua.lumaqq.events.IQQShowListener;

/**
 * 管理QQ show的类
 * 
 * @author luma
 */
public class QQShowManager {
    // Log
    private static Log log = LogFactory.getLog(QQShowManager.class);
    
	protected static final String QQ_SHOW_SERVER = "http://qqshow-user.tencent.com/";
	protected static final String QQ_SHOW_PATH = "/10/00/00.gif";
	protected static String QQ_SHOW_CACHE_DIR;
	private static QQShowManager instance;
	// QQ秀的图片缓冲区
	private Map<Integer, Image> cache;
	// 当前下载线程列表
	private Map<Integer, QQShowDownloadThread> threads;
	// QQ秀事件监听器，主要用来通知对方QQ秀已经下载完毕
	private List<IQQShowListener> listeners;
	
	/**
	 * 私有构造函数
	 * @param userDir 用户目录
	 */
	private QQShowManager(String userDir) {
		QQ_SHOW_CACHE_DIR = userDir + "/qqshow/";
		File file = new File(QQ_SHOW_CACHE_DIR);
		file.mkdirs();
		cache = new Hashtable<Integer, Image>();
		threads = new Hashtable<Integer, QQShowDownloadThread>();
		listeners = new ArrayList<IQQShowListener>();
	}
	
	/**
	 * 得到单一实例
	 * @param userDir 用户目录
	 * @return
	 */
	public static QQShowManager getInstance(String userDir) {
		if(instance != null)
			instance.close();
		instance = new QQShowManager(userDir);
		return instance;
	}
	
	/**
	 * @return 单一实例，这个实例可能为null
	 */
	public static QQShowManager getInstance() {
		return instance;
	}
	
	/**
	 * 关闭当前manager，释放资源
	 */
	private void close() {
		// 释放已经载入的图像
		for(Image image : cache.values())
			image.dispose();
	}

	/**
	 * 得到好友的QQ Show图像文件URL字符串
	 * 
	 * @param qqNum
	 * 		好友QQ号
	 * @return
	 * 		QQ秀地址
	 */
	protected String getQQShowUrlString(int qqNum) {
		return QQ_SHOW_SERVER + qqNum + QQ_SHOW_PATH;
	}
	
	/**
	 * 判断好友的QQ秀是否已经下载到了本地缓冲
	 * 
	 * @param qqNum
	 * 		好友的QQ号
	 * @return
	 * 		true如果已经下载到了本地缓冲
	 */
	public boolean isCached(int qqNum) {
		if(cache.containsKey(qqNum))
			return true;
		else {
			File file = new File(QQ_SHOW_CACHE_DIR + qqNum + ".gif");
			if(file.exists()) {	
				// 如果存在，但是哈希表中没有，则载入
				Image image = createStockImage(Display.getCurrent(), file);
				if(image == null)
					return false;
				else {
					cache.put(qqNum, image);
					return true;
				}
			} else
				return false;
		}
	}
	
	/**
	 * 得到QQ秀的图片
	 * 
	 * @param qqNum 
	 * 		好友的QQ号
	 * @return
	 * 		返回QQ秀图片对象
	 */
	public Image getQQShowImage(int qqNum) {
		return cache.get(qqNum);
	}
	
	/**
	 * 下载QQ秀，这个方法开启一个下载线程，直到下载完成为止
	 * 
	 * @param qqNum
	 * 		好友的QQ号
	 */
	public void downloadQQShowImage(int qqNum) {
		if(threads.containsKey(qqNum)) return;
		// 创建线程
		QQShowDownloadThread thread = new QQShowDownloadThread(qqNum);
		// 加入到哈希表
		threads.put(qqNum, thread);
		// 删除旧的QQ秀文件，如果有的话
		if(cache.containsKey(qqNum)) {
			cache.remove(qqNum).dispose();
			File file = new File(QQ_SHOW_CACHE_DIR + qqNum + ".gif");
			file.delete();
		}
		// 启动线程
		thread.start();
	}
	
	/**
	 * 线程下载完成时调用的回调函数，可能下载出错，也可能下载成功
	 * 
	 * @param qqNum 
	 * 		好友的QQ号
	 */
	protected synchronized void threadCallback(int qqNum) {
		// 删除这个线程
		threads.remove(qqNum);
		// 触发事件
		fireQQShowEvent(qqNum);
	}
	
    /**
     * 触发QQ秀事件
     * 
	 * @param qqNum
	 * 		已经下载完QQ秀的好友QQ号
	 */
	private void fireQQShowEvent(int qqNum) {
		for(IQQShowListener listener : listeners)
			listener.qqShowDownloaded(qqNum);
	}

	/**
     * @param display
     *            the display
     * @param path
     *            the relative path to the icon
     * 
     * 从文件创建Image对象
     */
    private Image createStockImage(Display display, File file) {
    	InputStream stream = null;
        try {
            stream = new FileInputStream(file);
            if (stream != null) {
                ImageData imageData = new ImageData(stream);
                if (imageData != null) {
                    ImageData mask = imageData.getTransparencyMask();
                    return new Image(display, imageData, mask);
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
        	try {
				if(stream != null) 
					stream.close();
			} catch (IOException e1) {
			    log.error(e1.getMessage());
			}
        }
        return null;
    }
    
    /**
     * 添加QQ秀事件监听器
     * 
     * @param listener
     * 		IQQShowListener实现类
     */
    public void addQQShowListener(IQQShowListener listener) {
    	listeners.add(listener);
    }
    
    /**
     * 删除QQ秀事件监听器
     * 
     * @param listener
     * 		IQQShowListener实现类
     */
    public void removeQQShowListener(IQQShowListener listener) {
    	listeners.remove(listener);
    }
}
