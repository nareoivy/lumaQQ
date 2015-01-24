/*
* LumaQQ - Java QQ Client
*
* Copyright (C) 2004 notXX
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
package edu.tsinghua.lumaqq.qq.net;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.tsinghua.lumaqq.qq.packets.PacketParseException;

/**
 * 发送接收线程
 * 
 * @author notxx
 */
public final class Porter extends Thread {
	/** Logger */
	private static final Log log = LogFactory.getLog(Porter.class);

	/** 线程是否结束的标志 */
	protected boolean shutdown = false;
	/** 端口选择器 */
	protected Selector selector;
	
	// port列表
	private List<IPort> ports;
	// proxy列表
	private List<IProxy> proxies;
	
	// 连接释放请求
	private Queue<Object> disposeQueue;
	
	// 新连接请求
	private List<Object> newConnections;
	
	/**
	 * 构造一个Porter.
	 */
	public Porter() {
	    ports = new ArrayList<IPort>();
	    proxies = new ArrayList<IProxy>();
	    newConnections = new Vector<Object>();
	    disposeQueue = new LinkedList<Object>();
		setName("Porter");
		setDaemon(true);
	    // 创建新的Selector
		try {
			selector = Selector.open();
		} catch (IOException e) {
			log.debug(e);
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 注册一个port到porter中
	 * 
	 * @param port
	 * 		IPort实现
	 * @throws ClosedChannelException
	 * 		如果注册失败
	 */
	public void register(IPort port) throws ClosedChannelException {
	    SelectableChannel channel = port.channel();
	    if(channel instanceof SocketChannel)
		    channel.register(selector, SelectionKey.OP_CONNECT, port.getNIOHandler());
	    else if(channel instanceof DatagramChannel)
		    channel.register(selector, SelectionKey.OP_READ, port.getNIOHandler());
	    if(!ports.contains(port))
	        ports.add(port);
	}
	
	/**
	 * 以指定的操作注册channel
	 * 
	 * @param port
	 * @param ops
	 * @throws ClosedChannelException
	 */
	public void register(IPort port, int ops) throws ClosedChannelException {
	    SelectableChannel channel = port.channel();
	    if(channel instanceof SocketChannel)
		    channel.register(selector, ops, port.getNIOHandler());
	    else if(channel instanceof DatagramChannel)
		    channel.register(selector, ops, port.getNIOHandler());
	    if(!ports.contains(port))
	        ports.add(port);
	}
	
	/**
	 * 注册一个代理，用在代理验证中
	 * 
	 * @param proxy
	 * 		IProxy实现类
	 * @throws ClosedChannelException
	 * 		如果注册失败
	 */
	public void register(IProxy proxy) throws ClosedChannelException {
	    SelectableChannel channel = proxy.channel();
	    if(channel instanceof SocketChannel)
		    channel.register(selector, SelectionKey.OP_CONNECT, proxy.getNIOHandler());
	    else if(channel instanceof DatagramChannel)
		    channel.register(selector, SelectionKey.OP_READ, proxy.getNIOHandler());
	    if(!proxies.contains(proxy))
	        proxies.add(proxy);
	}
	
	/**
	 * 删除一个port，这个port的channel将被关闭
	 * 
	 * @param port
	 * 		IPort实现
	 * @throws IOException
	 */
	private void deregister(IPort port) {
		if(port == null)
			return;
		
	    if(!ports.remove(port))
	    	return;
    	SelectionKey key = port.channel().keyFor(selector);
    	if(key != null)
    		key.cancel();
        port.dispose();
	}
	
	/**
	 * 删除一个proxy
	 * 
	 * @param proxy
	 */
	private void deregister(IProxy proxy) {
		if(proxy == null)
			return;
		
	    if(!proxies.remove(proxy))
	    	return;
    	SelectionKey key = proxy.channel().keyFor(selector);
    	if(key != null)
    		key.cancel();
        proxy.dispose();
	}
	
	/**
	 * 发送错误事件到所有port
	 * 
	 * @param e
	 * 		包含错误信息的Exception
	 */
	private void dispatchErrorToAll(Exception e) {
		for(IPort port : ports)
			port.getNIOHandler().processError(e);
	    for(IProxy proxy: proxies)
			proxy.getNIOHandler().processError(e);
	}
	
	/**
	 * 通知所有port发送包
	 * @throws IOException
	 */
	private void notifySend() {
	    int size = ports.size();
	    for(int i = 0; i < size; i++) {
	        INIOHandler handler = null;
	        try {
		        handler = (ports.get(i)).getNIOHandler();
                handler.processWrite();
            } catch (IOException e) {
	            log.error(e.getMessage());
	            handler.processError(e);
            } catch (IndexOutOfBoundsException e) {                
            }
	    }
	    
	    size = proxies.size();
	    for(int i = 0; i < size; i++) {
	        INIOHandler handler = null;
	        try {
		        handler = (proxies.get(i)).getNIOHandler();
                handler.processWrite();
            } catch (IOException e) {
	            log.error(e.getMessage());
	            handler.processError(e);
            } catch (IndexOutOfBoundsException e) {                
            }
	    }
	}
	
	/**
	 * 不断运转维护所有注册的IPort对象.
	 * 通过调用它们的几个函数分别做到清空发送队列/填充接收队列/维护队列的功能.
	 * @see IPort#send(ByteBuffer)
	 * @see IPort#receive(ByteBuffer)
	 * @see IPort#maintain()
	 */
	@Override
	public void run() {
		log.debug("Porter已经启动");		
		int n = 0;
	    while(!shutdown) {		    		    
	        // do select
            try {
                n = selector.select(3000);
                // 如果要shutdown，关闭selector退出
                if (shutdown) {
                    selector.close();
                	break;			        
                }
            } catch (IOException e) {
	            log.error(e.getMessage());
	            dispatchErrorToAll(e);
            } 
            
            // 处理连接释放请求
            processDisposeQueue();
            
		    // 如果select返回大于0，处理事件
		    if(n > 0) {
		        for (Iterator<SelectionKey> i = selector.selectedKeys().iterator(); i.hasNext();) {
					// 得到下一个Key
					SelectionKey sk = i.next();
					i.remove();
					// 检查其是否还有效
	                if(!sk.isValid())
	                    continue;

					// 处理
					INIOHandler handler = (INIOHandler)sk.attachment();
		            try {
                        if(sk.isConnectable())
                            handler.processConnect(sk);
                        else if (sk.isReadable())
                            handler.processRead(sk);
                    } catch (IOException e) {
        	            log.error(e.getMessage());
        	            handler.processError(e);
                    } catch (PacketParseException e) {
        	            log.debug("包解析错误: " + e.getMessage());
    	            } catch (RuntimeException e) {
    	                log.error(e.getMessage());
    	            }
		        }
		        
		        n = 0;
		    }
		    
		    checkNewConnection();		    
		    notifySend();		    
		}
	    
        selector = null;
        shutdown = false;
		log.debug("Porter已经退出");
	}
	
	/**
	 * 添加释放请求
	 * 
	 * @param p
	 */
	public void addDisposeRequest(IPort p) {
		synchronized(disposeQueue) {
			disposeQueue.offer(p);
		}
	}
	
	/**
	 * 添加释放请求
	 * 
	 * @param p
	 */
	public void addDisposeRequest(IProxy p) {
		synchronized(disposeQueue) {
			disposeQueue.offer(p);
		}
	}
	
    /**
     * 检查是否有新连接要加入
     */
    private void checkNewConnection() {	 
        while(!newConnections.isEmpty()) {
            Object handler = newConnections.remove(0);
	        if(handler instanceof IProxy) {
		        try {
	                register((IProxy)handler);
	            } catch (ClosedChannelException e1) {
	            }
	        } else if(handler instanceof IPort) {
		        try {
	                register((IPort)handler);
	            } catch (ClosedChannelException e1) {
	            }
	        }            
        }
    }
    
    /**
     * 处理连接释放请求
     */
    private void processDisposeQueue() {
    	synchronized(disposeQueue) {
    		while(!disposeQueue.isEmpty()) {
    			Object obj = disposeQueue.poll();
    			if(obj instanceof IPort)
    				deregister((IPort)obj);
    			else if(obj instanceof IProxy)
    				deregister((IProxy)obj);
    		}
    	}
    }

    /**
     * 关闭porter
     */
    public void shutdown() {
	    if(selector != null) {
		    shutdown = true;
	        selector.wakeup();	        
	    }
    }
    
    /**
     * 唤醒selector
     */
    public void wakeup() {
        selector.wakeup();
    }
    
    /**
     * 唤醒selector然后注册这个proxy
     * 
     * @param proxy
     */
    public void wakeup(Object handler) {
        newConnections.add(handler);
        selector.wakeup();
    }
}