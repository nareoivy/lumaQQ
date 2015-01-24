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

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * QQ秀图片的下载线程，在下载完毕后，将会调用QQShowManager的回调方法
 * 
 * @author luma
 */
public class QQShowDownloadThread extends Thread {
    // Log对象
    private static Log log = LogFactory.getLog(QQShowDownloadThread.class);
    
    // QQShowManager实例
	private QQShowManager sm;
	// 好友QQ号
	private int qqNum;
	// 输入流
	private BufferedInputStream bis;
	
	
	/**
	 * 构造函数
	 * @param qqNum 好友的QQ号
	 */
	public QQShowDownloadThread(int qqNum) {
		this.qqNum = qqNum;
		setDaemon(true);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		sm = QQShowManager.getInstance();
		// 得到URL，如果有错误，则返回
		String urlString = sm.getQQShowUrlString(qqNum);
		URL url = null;
		try {
			url = new URL(urlString);
		} catch (MalformedURLException e) {
			sm.threadCallback(qqNum);
			return;
		}
		File tempFile = null;
		FileOutputStream fos = null;
		try {
			log.debug("开始下载QQ秀，地址" + urlString);
			// 临时文件
			tempFile = new File(QQShowManager.QQ_SHOW_CACHE_DIR + qqNum + "-temp.gif");
			if(!tempFile.exists())
				tempFile.createNewFile();
			// 打开临时文件输出流
			fos = new FileOutputStream(tempFile);
			// 开始下载，同时数据写入到临时文件中
			byte[] buf = new byte[1024];
			bis = new BufferedInputStream(url.openStream());
			log.debug("成功打开输入流");
			for(int i = bis.read(buf, 0, 1024); i != -1; i = bis.read(buf, 0, 1024))
				fos.write(buf, 0, i);
			// 写入完成，把临时文件改名，但是要先关闭流，不然改不了的
			fos.close();
			fos = null;
			File file = new File(QQShowManager.QQ_SHOW_CACHE_DIR + qqNum + ".gif");
			log.debug("改名" + (tempFile.renameTo(file)?"成功":"失败"));
			log.debug("下载成功");
		} catch (IOException e) {
			log.error("下载QQ秀图片文件时发生错误或者用户退出程序导致线程强行结束");
			// 如果出现错误，则删除临时文件
			if(fos != null) {
				try {
					fos.close();
					fos = null;
				} catch (IOException e1) {
				    log.error(e.getMessage());
				}
			}
			if(tempFile != null && tempFile.exists())
				tempFile.delete();
		} finally {
			try {
				if(fos != null) fos.close();
				if(bis != null) bis.close();
			} catch (IOException e1) {
			    log.error(e1.getMessage());
			}
			sm.threadCallback(qqNum);
			log.debug("" + qqNum + " QQ秀下载线程退出");
		}
	}
}
