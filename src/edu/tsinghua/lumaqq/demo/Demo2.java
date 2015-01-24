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
package edu.tsinghua.lumaqq.demo;

import java.awt.Button;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JApplet;

import edu.tsinghua.lumaqq.qq.QQ;
import edu.tsinghua.lumaqq.qq.QQClient;
import edu.tsinghua.lumaqq.qq.Util;
import edu.tsinghua.lumaqq.qq.beans.QQUser;
import edu.tsinghua.lumaqq.qq.events.QQEvent;
import edu.tsinghua.lumaqq.qq.events.IQQListener;
import edu.tsinghua.lumaqq.qq.net.PortGateFactory;

/**
 * <pre>
 * LumaQQ的简单Applet包装，主要演示事件的使用和处理。
 * </pre>
 * 
 * @author luma
 */
public class Demo2 extends JApplet implements IQQListener {    
	private static final long serialVersionUID = 3258417235386053939L;
	
	private TextField textQQ, textPassword, textFriendQQ;
    private TextArea textMessage;
    private Label lblHint;
    private int friendQQ;
    private QQClient client;
    
    /* (non-Javadoc)
     * @see java.applet.Applet#init()
     */
    @Override
	public void init() {
        /* 创建界面 */
        Container content = getContentPane();
        content.setLayout(new GridLayout(6, 2));
        Label label = new Label("Your QQ:");
        content.add(label);        
        textQQ = new TextField();
        content.add(textQQ);
        
        label = new Label("Your Password:");
        content.add(label);
        textPassword = new TextField();
        textPassword.setEchoChar('*');
        content.add(textPassword);
        
        label = new Label("Friend QQ:");
        content.add(label);        
        textFriendQQ = new TextField();
        content.add(textFriendQQ);
        
        label = new Label("Message");
        content.add(label);        
        textMessage = new TextArea();
        content.add(textMessage);
        
        Button btnSend = new Button("Send");
        btnSend.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                send();
            }
        });
        content.add(btnSend);
        
        label = new Label("");
        content.add(label);
        
        lblHint = new Label("Ready");
        lblHint.setForeground(Color.RED);
        content.add(lblHint);
    }

    /**
     * 发送一条消息
     */
    protected void send() {
        // 得到所有参数
        int yourQQ = 0;
        try {
            yourQQ = Integer.parseInt(textQQ.getText());
            friendQQ = Integer.parseInt(textFriendQQ.getText());
        } catch (NumberFormatException e) {
            lblHint.setText("The format of QQ number is invalid.");
            return;
        }        
        String yourPassword = textPassword.getText();
        
        // 开始创建各种对象，登录，然后发送消息，登出，注意我们不再使用sleep来等待操作完成
        // 创建QQClient和QQUser
        client = new QQClient();
        client.setConnectionPoolFactory(new PortGateFactory());
        QQUser user = new QQUser(yourQQ, yourPassword);
        // 把自己添加成为QQListener
        client.addQQListener(this);
        // 设置参数
        user.setUdp(true);
        client.setUser(user);
        client.setLoginServer("sz.tencent.com");

        try {
            // 登录
            lblHint.setText("Logining...");
            client.login();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.qq.events.QQListener#qqEvent(edu.tsinghua.lumaqq.qq.events.QQEvent)
     */
    public void qqEvent(QQEvent e) {
        switch(e.type) {
			case QQEvent.LOGIN_OK:
				processLoginSuccess();
				break;
			case QQEvent.LOGIN_FAIL:
				processLoginFail(e);
				break;
			case QQEvent.LOGIN_UNKNOWN_ERROR:
				processLoginUnknownError();
				break;
			case QQEvent.IM_SEND_OK:
			    processSendIMSuccess();
				break;
			case QQEvent.USER_STATUS_CHANGE_OK:
			    processChangeStatusSuccess();
				break;
			case QQEvent.USER_STATUS_CHANGE_FAIL:
			    processChangeStatusFail();
				break;
			case QQEvent.SYS_TIMEOUT:
			    if(e.operation == QQ.QQ_CMD_SEND_IM)
			        processSendIMFail();
			    break;
        }
    }
    
	/**
     * 初始改变状态失败事件
     */
    private void processChangeStatusFail() {
        lblHint.setText("Failed to change status, message can't be sent, client logout");
        client.logout();
        client.release();
    }
	
	/**
	 * 处理改变状态成功事件
	 */
	private void processChangeStatusSuccess() {
	    lblHint.setText("Status changed successfully, begin to send message...");
	    client.im_Send(friendQQ, Util.getBytes(textMessage.getText()));
	}
	
    /**
     * 处理发送消息失败事件
     */
    private void processSendIMFail() {
        lblHint.setText("Failed to send message, client logout.");
        client.logout();
        client.release();
    }

    /**
     * 处理发送消息成功事件
     */
    private void processSendIMSuccess() {
        lblHint.setText("Message is sent successfully, client now logout.");
        client.logout();
        client.release();
    }

    /**
	 * 处理登陆成功事件
	 */
	private void processLoginSuccess() {
	    if(!client.getUser().isLoggedIn()) {
	        lblHint.setText("Login successful, wait for status changed...");	        
	    }
	}	

	/**
	 * 处理登陆密码错误事件
	 * @param e 
	 */
	private void processLoginFail(QQEvent e) {
	    lblHint.setText("Password error, login failed!");
	}
	
	/**
	 * 处理登陆未知错误事件
	 */
	private void processLoginUnknownError() {
	    lblHint.setText("Unknown error, login failed!");
	}
}
