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
package edu.tsinghua.lumaqq.ui.jobs;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.BitSet;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;

import edu.tsinghua.lumaqq.LumaQQ;
import edu.tsinghua.lumaqq.ecore.face.FaceConstant;
import edu.tsinghua.lumaqq.models.ModelRegistry;
import edu.tsinghua.lumaqq.models.User;
import edu.tsinghua.lumaqq.qq.QQ;
import edu.tsinghua.lumaqq.qq.QQPort;
import edu.tsinghua.lumaqq.qq.Util;
import edu.tsinghua.lumaqq.qq.beans.CustomHead;
import edu.tsinghua.lumaqq.qq.events.QQEvent;
import edu.tsinghua.lumaqq.qq.net.IConnection;
import edu.tsinghua.lumaqq.qq.packets.in._03.GetCustomHeadDataReplyPacket;
import edu.tsinghua.lumaqq.ui.MainShell;
import edu.tsinghua.lumaqq.ui.helper.FaceRegistry;

/**
 * 下载自定义头像的任务
 *
 * @author luma
 */
public class DownloadCustomHeadJob extends AbstractJob {
    private static Log log = LogFactory.getLog(DownloadCustomHeadJob.class);
    
    private int finishCount;
	private List<CustomHead> headInfo;
	private IConnection conn;
	
	private BitSet fragmentChecker;
	private boolean downloading;
	private long latestFragmentTime;
	private int totalFragment;
	private int headLength;
	
	// 缓冲区
	private byte[] buf;

	private static final int DATA_TIMEOUT = 5000;
	
	private Timer timer;
	
	public DownloadCustomHeadJob() {
	}
	
	public DownloadCustomHeadJob(List<CustomHead> headInfo) {
		this.headInfo = headInfo;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public void setLinkArgument(Object arg) {
		headInfo = (List<CustomHead>)arg;
	}
	
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.ui.jobs.IJob#prepare(edu.tsinghua.lumaqq.ui.MainShell)
     */
    @Override
    public void prepare(MainShell m) {
        super.prepare(m);
        finishCount = -1;
        fragmentChecker = new BitSet(256);
        downloading = false;
        latestFragmentTime = System.currentTimeMillis();
        main.getClient().addQQListener(this);
    }

	@Override
	public void clear() {
		if(conn != null) {
			main.getClient().releaseConnection(conn.getId());
			conn = null;
		}
		main.getTimerHelper().dispose(timer);
        main.getClient().removeQQListener(this);
	}

	@Override
	public boolean isSuccess() {
		return finishCount == headInfo.size();
	}
	
	@Override
	protected boolean canRun() {
		// 检查是否为空
		boolean b = headInfo != null && !headInfo.isEmpty();
		if(!b)
			return false;
		else {
			// 创建一个port
			try {
				conn = QQPort.CUSTOM_HEAD_DATA.create(main.getClient(), new InetSocketAddress(QQ.QQ_SERVER_DOWNLOAD_CUSTOM_HEAD, 4000), null, true);
				return true;
			} catch(IOException e) {
				log.error("无法连接到自定义头像下载服务器");
				return false;
			} 
		}
	}
	
	@Override
	protected void preLoop() {				
		// 开始下载自定义图像
		getNextHead();
		
		// 申请一个定时器，用来监视自定义头像分片
		if(timer == null) {
			timer = main.getTimerHelper().newTimer();
			timer.schedule(new TimerTask() {
				@Override
				public void run() {
					checkHeadData();
				}
			}, DATA_TIMEOUT, DATA_TIMEOUT);
		}
	}
	
	@Override
	protected void postLoop() {
		// 刷新
		main.getDisplay().syncExec(new Runnable() {
			public void run() {
				main.getBlindHelper().refreshAll();
			}
		});
	}
	
	@Override
	protected void OnQQEvent(QQEvent e) {
		switch(e.type) {
			case QQEvent.USER_GET_CUSTOM_HEAD_DATA_OK:
				processGetCustomHeadDataSuccess(e);
				break;
			case QQEvent.SYS_TIMEOUT_03:
				switch(e.operation) {
					case QQ.QQ_03_CMD_GET_CUSTOM_HEAD_DATA:
						processGetCustomHeadDataTimeout(e);
						break;
				}
				break;
		}
	}

	/**
	 * 处理得到自定义头像数据超时事件
	 * 
	 * @param e
	 */
	private synchronized void processGetCustomHeadDataTimeout(QQEvent e) {		
		getNextHead();
	}

	/**
	 * 请求下一个自定义头像
	 */
	private synchronized void getNextHead() {
		// 超时的话，跳过这个好友，继续下一个
		finishCount++;
		if(finishCount == headInfo.size())
			wake();
		else {
			FaceRegistry reg = FaceRegistry.getInstance();
			CustomHead head = null;			
			for(; finishCount < headInfo.size(); finishCount++) {
				head = headInfo.get(finishCount);		
				User u = ModelRegistry.getUser(head.qq);
				if(u.customHeadTimestamp < head.timestamp || !reg.hasFace(reg.getMd5ById(u.customHeadId)))
					break;
				else
					head = null;
			}
			if(head == null)
				wake();
			else {
				main.getClient().customHead_GetData(head.qq, head.timestamp, conn.getId());
				fragmentChecker.clear();
				downloading = true;
				totalFragment = 0;				
			}
		}
	}
	
	private synchronized void checkHeadData() {
		if(System.currentTimeMillis() - latestFragmentTime > DATA_TIMEOUT)
			reconcilFragment();
	}

	private synchronized void processGetCustomHeadDataSuccess(QQEvent e) {
		GetCustomHeadDataReplyPacket packet = (GetCustomHeadDataReplyPacket)e.getSource();
		CustomHead head = headInfo.get(finishCount);
		if(packet.qq != head.qq)
			return;
		
		// 设置临时变量
		if(totalFragment < packet.totalFragment)
			totalFragment = packet.totalFragment;
		headLength = packet.headLength;
		
		log.debug("收到分片" + ((int)packet.currentFragment) + ", 总分片" + totalFragment + ", QQ" + packet.qq);		
		
		// 分配缓冲
		if(buf == null || buf.length < packet.headLength)
			buf = new byte[packet.headLength];
		
		// copy数据
		System.arraycopy(packet.data, 0, buf, packet.offset, packet.dataLength);
		
		// 检查是否所有分片都已经收到
		int bit = packet.offset / QQ.QQ_MAX_CUSTOM_HEAD_FRAGMENT_SIZE;
		fragmentChecker.set(bit);
		log.debug("置位: " + bit);
		latestFragmentTime = System.currentTimeMillis();
		if(isAllReceived(totalFragment)) {			
			log.debug("QQ: " + packet.qq + "， 头像已完成");
			// 保存自定义头像
			if(saveCustomHead(head, packet.headLength)) {
				String md5 = Util.convertByteToHexStringWithoutSpace(head.md5);
				FaceRegistry reg = FaceRegistry.getInstance();
				reg.addCustomHead(md5, md5 + ".bmp");
				User u = ModelRegistry.getUser(head.qq);
				int id = reg.getFaceId(md5);
				u.hasCustomHead = true;
				u.customHeadId = id;
				u.customHeadTimestamp = head.timestamp;
				if(u.qq == main.getMyModel().qq) {
					User me = main.getMyModel();
					me.hasCustomHead = true;
					me.customHeadId = id;
					me.customHeadTimestamp = head.timestamp;
				}
				main.getBlindHelper().synchronizeLatestDelayRefresh(u);
			}
			
			// 得到下一个头像
			getNextHead();
		} else
			downloading = false;
	}
	
	/**
	 * 检查分片标志，对没有收到的分片重新发送请求
	 */
	private synchronized void reconcilFragment() {
		if(finishCount < 0 || finishCount >= headInfo.size())
			return;		
		if(downloading)
			return;

		CustomHead head = headInfo.get(finishCount);
		int index = fragmentChecker.nextSetBit(0);
		if(index == -1) {
			main.getClient().customHead_GetData(head.qq, head.timestamp, conn.getId());
			log.debug("Reconcil: All");
			downloading = true;
		} else if(index > 0) {
			main.getClient().customHead_GetData(head.qq, head.timestamp, 0, index * QQ.QQ_MAX_CUSTOM_HEAD_FRAGMENT_SIZE, conn.getId());
			log.debug("Reconcil: 偏移: " + 0 + ", 长度: " + index * QQ.QQ_MAX_CUSTOM_HEAD_FRAGMENT_SIZE);
			downloading = true;
		} else if(index == 0) {
			index = fragmentChecker.nextClearBit(index + 1);
			int nextIndex = fragmentChecker.nextSetBit(index + 1);
			if(nextIndex == -1) {
				main.getClient().customHead_GetData(head.qq, head.timestamp, index * QQ.QQ_MAX_CUSTOM_HEAD_FRAGMENT_SIZE, headLength - index * QQ.QQ_MAX_CUSTOM_HEAD_FRAGMENT_SIZE, conn.getId());
				log.debug("Reconcil: 偏移: " + index * QQ.QQ_MAX_CUSTOM_HEAD_FRAGMENT_SIZE + ", 长度: " + (headLength - index * QQ.QQ_MAX_CUSTOM_HEAD_FRAGMENT_SIZE));
			} else {
				main.getClient().customHead_GetData(head.qq, head.timestamp, index * QQ.QQ_MAX_CUSTOM_HEAD_FRAGMENT_SIZE, (nextIndex - index) * QQ.QQ_MAX_CUSTOM_HEAD_FRAGMENT_SIZE, conn.getId());
				log.debug("Reconcil: 偏移: " + index * QQ.QQ_MAX_CUSTOM_HEAD_FRAGMENT_SIZE + ", 长度: " + ((nextIndex - index) * QQ.QQ_MAX_CUSTOM_HEAD_FRAGMENT_SIZE));
			}
			downloading = true;
		}
	}
	
	/**
	 * 检查是否所有分片已经收到
	 * 
	 * @param total
	 * 		全部分片数
	 * @return
	 * 		true表示所有分片已经收到
	 */
	private boolean isAllReceived(int total) {
		for(int i = 0; i < total; i++) {
			if(!fragmentChecker.get(i))
				return false;
		}
		return true;
	}
	
	/**
	 * 保存文件为自定义头像
	 * 
     * @return
     * 		true表示保存成功
	 */
	private boolean saveCustomHead(CustomHead head, int length) {
		try {
			// 生成ImageData
			ByteArrayInputStream bais = new ByteArrayInputStream(buf, 0, length);
			ImageData origin = new ImageData(bais);
			ImageData data = origin.scaledTo(40, 40);
			
			// save 40x40 bmp
			String md5 = Util.convertByteToHexStringWithoutSpace(head.md5);
			ImageLoader saveLoader = new ImageLoader();
			saveLoader.data = new ImageData[] { data };
			saveLoader.save(LumaQQ.CUSTOM_FACE_DIR + FaceConstant.CUSTOM_HEAD_GROUP_ID + '/' + md5 + ".bmp", SWT.IMAGE_BMP);
			
			// save 20x20 bmp
			data = origin.scaledTo(20, 20);
			saveLoader = new ImageLoader();
			saveLoader.data = new ImageData[] { data };
			saveLoader.save(LumaQQ.CUSTOM_FACE_DIR + FaceConstant.CUSTOM_HEAD_GROUP_ID + '/' + md5 + "fixed.bmp", SWT.IMAGE_BMP);
			
			return true;
		} catch(SWTException e) {
			return false;
		} 
	}
}
