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

import static edu.tsinghua.lumaqq.resource.Resources.*;
import static edu.tsinghua.lumaqq.resource.Messages.*;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import edu.tsinghua.lumaqq.MessageQueue;
import edu.tsinghua.lumaqq.models.Cluster;
import edu.tsinghua.lumaqq.models.ModelRegistry;
import edu.tsinghua.lumaqq.models.Status;
import edu.tsinghua.lumaqq.models.User;
import edu.tsinghua.lumaqq.qq.QQ;
import edu.tsinghua.lumaqq.resource.Colors;
import edu.tsinghua.lumaqq.resource.Resources;
import edu.tsinghua.lumaqq.ui.MainShell;
import edu.tsinghua.lumaqq.widgets.mac.Ring;
import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

/**
 * 由于MainShell类比较庞大，所以隔离一部分代码到这个类中，这些方法主要执行
 * 一些界面相关的操作
 * 
 * @author luma
 */
public class UIHelper {	
    /**
     * 闪烁图标
     * 
     * @author luma
     */
    private class Blink implements Runnable {
        private Image blinkImage;
        private boolean flag;
        private volatile boolean stop;
        
        public Blink() {
        	stop = true;
        }
        
        public void setBlinkImage(Image blinkImage) {
            this.blinkImage = blinkImage;
            this.flag = true;
            this.stop = false;
        }

        public void run() {
            try {                      
	            if(flag)
	                setTrayItemImage(blinkImage);
	            else
	                setTrayItemImage(res.getImage(icoBlank));
	            flag = !flag;
	            if(!stop)
	                display.timerExec(500, this);
	            else
	                setTrayIconByStatus();
            } catch (SWTException e) {         
                // 这个操作可能会抛出SWTException，如果组件已经dispose的话，
                //     所以我们需要捕捉这个异常，不然程序可能崩溃
            }
        }

		public void setStop(boolean stop) {
			this.stop = stop;
		}

		public boolean isStop() {
			return stop;
		}
    }
    
    /**
     * 闪烁图标
     * 
     * @author luma
     */
    private class BlinkSysMsg implements Runnable {
        private boolean flag;
        private volatile boolean stop;
        
        public BlinkSysMsg() {
        	stop = true;
        }
        
        public void init() {
            this.flag = true;
            this.stop = false;
        }

        public void run() {
            try {                      
	            if(flag)
	                main.getSystemMessageButton().setImage(res.getImage(icoSysMsg));
	            else
	                main.getSystemMessageButton().setImage(null);
	            flag = !flag;
	            if(!stop)
	                display.timerExec(500, this);
	            else
	            	main.getSystemMessageButton().setImage(res.getImage(icoSysMsg));
            } catch (SWTException e) {         
                // 这个操作可能会抛出SWTException，如果组件已经dispose的话，
                //     所以我们需要捕捉这个异常，不然程序可能崩溃
            }
        }

		public void setStop(boolean stop) {
			this.stop = stop;
		}

		public boolean isStop() {
			return stop;
		}
    }
    
    /**
     * 闪烁图标
     * 
     * @author luma
     */
    private class BlinkSMS implements Runnable {
        private boolean flag;
        private volatile boolean stop;
        
        public BlinkSMS() {
        	stop = true;
        }
        
        public void init() {
            this.flag = true;
            this.stop = false;
        }

        public void run() {
            try {                      
	            if(flag)
	                main.getSMSButton().setImage(res.getImage(icoMobile));
	            else
	                main.getSMSButton().setImage(null);
	            flag = !flag;
	            if(!stop)
	                display.timerExec(500, this);
	            else
	            	main.getSMSButton().setImage(res.getImage(icoMobile));
            } catch (SWTException e) {         
                // 这个操作可能会抛出SWTException，如果组件已经dispose的话，
                //     所以我们需要捕捉这个异常，不然程序可能崩溃
            }
        }

		public void setStop(boolean stop) {
			this.stop = stop;
		}

		public boolean isStop() {
			return stop;
		}
    }
    
    private MainShell main;
    private Display display;
    private Resources res;
    private Blink blinkRunnable;
    private BlinkSysMsg blinkSysMsgRunnable;
    private BlinkSMS blinkSMSRunnable;
    
    // 这个哈希表保存了需要自动打开的讨论组序号，我们在一个组织上右键时，会
    // 有一个发起讨论菜单，选择了之后，如果没有一个相关的讨论组存在，我们就要
    // 自动创建一个，所以这个哈希表保存了创建讨论组的包序号，创建完毕后，自动
    // 打开窗口
    private Map<Integer, Integer> discussPromoting;
    
    public UIHelper(MainShell main) {
        this.main = main;
        display = main.getDisplay();
        res = Resources.getInstance();
		blinkRunnable = new Blink();
		blinkSysMsgRunnable = new BlinkSysMsg();
		blinkSMSRunnable = new BlinkSMS();
		discussPromoting = new HashMap<Integer, Integer>();
    }        
    
    /**
     * 添加一个需要自动打开的讨论组序号
     * 
     * @param sequence
     */
    public void addPromotingDiscuss(int sequence) {
    	discussPromoting.put(sequence, sequence);
    }
    
    public boolean hasPromotingDiscuss(int sequence) {
    	return discussPromoting.containsKey(sequence);
    }
    
    public void removePromotingDiscuss(int sequence) {
    	discussPromoting.remove(sequence);
    }
	
    /**
     * 开始闪烁图标
     * 
     * @param blinkImage
     * 		要闪烁的图标
     */
    public void startBlinkImage(Image blinkImage) {
        if(!blinkRunnable.isStop()) return;
        blinkRunnable.setBlinkImage(blinkImage);
        display.timerExec(0, blinkRunnable);
    }
    
    /**
     * 停止闪烁
     */
    public void stopBlinkImage() {
        blinkRunnable.setStop(true);
    }
	
	/**
	 * 这个方法检查总消息队列，根据检查的结果，重新设置系统托盘的图标特效状态
	 */
	public void resetTrayImageEffect() {
	    MessageQueue mq = main.getMessageQueue();
	    
		// 检查总队列还有没有消息，如果没有就停止闪烁，有就闪烁下一个图标，注意这里可能出现
		// 系统消息，系统消息的图标的得到和头像的得到是不一样的
		stopBlinkImage();
		if(mq.hasNext()) {			
			int source = mq.nextMessageSource();
			int sender = mq.nextSender();
			switch(source) {
				case QQ.QQ_IM_FROM_USER:
					User f = ModelRegistry.getUser(sender);	
					if(f != null) {
						if(f.isTM())
							startBlinkImage(res.getImage(f.female ? icoTMFemale16 : icoTMMale16));
						else
							startBlinkImage(HeadFactory.getOnlineSmallHead(f));		
					}
					break;
				case QQ.QQ_IM_FROM_TEMP_SESSION:
					startBlinkImage(res.getSmallHead(0));
					break;
				case QQ.QQ_IM_FROM_CLUSTER:
					Cluster c = ModelRegistry.getCluster(sender);
					if(c != null) {
						switch(c.clusterType) {
							case NORMAL:
								startBlinkImage(res.getSmallClusterHead(c.headId));
								break;
							case SUBJECT:
							case DIALOG:
								startBlinkImage(res.getImage(icoDialog));
								break;
							default:
								SWT.error(SWT.ERROR_INVALID_ARGUMENT);
							break;
						}
					}
					break;
				case QQ.QQ_IM_FROM_SMS:
					startBlinkImage(res.getImage(icoMobile));
					break;
				default:
					startBlinkImage(res.getImage(icoSysMsg));					
			}
		}
	}

	/**
	 * 设置所有的好友状态在下线
	 */
	public void setAllFriendOffline() {
		for(Iterator<User> i = ModelRegistry.getUserIterator(); i.hasNext(); ) {
			i.next().status = Status.OFFLINE;			
		}
		main.getBlindHelper().refreshAll();
	}
    
    /**
     * 设置系统托盘图标
     * 
     * @param image
     * 		Image对象
     */
    public void setTrayItemImage(Image image) {
    	if(main.getTrayItem() != null)
    		main.getTrayItem().setImage(image);
    }
	
	/**
	 * 设置tray icon和状态按钮的图标文字符合用户的当前状态
	 */
	public void setTrayIconByStatus() {
		Ring ring = main.getStatusRing();
		switch(main.getClient().getUser().getStatus()) {
			case QQ.QQ_STATUS_ONLINE:
				setTrayItemImage(res.getImage(icoOnline));
				ring.setToolTipText(status_online);
				ring.setForeground(Colors.ONLINE);
				break;
			case QQ.QQ_STATUS_AWAY:
				setTrayItemImage(res.getImage(icoAway));
				ring.setToolTipText(status_away);
				ring.setForeground(Colors.AWAY);
				break;
			case QQ.QQ_STATUS_HIDDEN:
				setTrayItemImage(res.getImage(icoHidden));
				ring.setToolTipText(status_hidden);
				ring.setForeground(Colors.HIDDEN);
				break;
			case QQ.QQ_STATUS_OFFLINE:
				setTrayItemImage(res.getImage(icoOffline));
				ring.setToolTipText(status_offline);
				ring.setForeground(ring.getDefaultForeground());
				break;
		}
	}
	
    /**
     * 开始闪烁系统消息图标
     */
    public void startBlinkSystemMessageIcon() {
    	if(!blinkSysMsgRunnable.isStop())
    		return;
    	blinkSysMsgRunnable.init();
    	display.timerExec(0, blinkSysMsgRunnable);
    }
    
    /**
     * 停止系统消息按钮上的闪烁效果
     */
    public void stopBlinkSystemMessageIcon() {
    	blinkSysMsgRunnable.setStop(true);
    }
    
    /**
     * 开始闪烁系统消息图标
     */
    public void startBlinkSMSIcon() {
    	if(!blinkSMSRunnable.isStop())
    		return;
    	blinkSMSRunnable.init();
    	display.timerExec(0, blinkSMSRunnable);
    }
    
    /**
     * 停止系统消息按钮上的闪烁效果
     */
    public void stopBlinkSMSIcon() {
    	blinkSMSRunnable.setStop(true);
    }

	/**
	 * 开始状态动画
	 */
	public void startStatusAnimation() {
		main.getStatusRing().rotate();
	}
	
	/**
	 * 停止登录动画
	 */
	public void stopStatusAnimation() {
		main.getStatusRing().stop();
	}
}
