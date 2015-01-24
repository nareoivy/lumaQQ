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

import edu.tsinghua.lumaqq.qq.QQClient;
import edu.tsinghua.lumaqq.qq.Util;
import edu.tsinghua.lumaqq.qq.beans.QQUser;
import edu.tsinghua.lumaqq.qq.net.PortGateFactory;

/**
 * <pre>
 * 非常简单的LumaQQ演示程序，其功能是登录，然后发送消息，然后登出。
 * </pre>
 * 
 * @author luma
 */
public class Demo1 {
    public static void main(String[] args) {
        /* 在下面的参数中填入你自己的QQ号和密码 */
        // 创建QQClient和QQUser
        QQClient client = new QQClient();
        client.setConnectionPoolFactory(new PortGateFactory());
        QQUser user = new QQUser(12345678, "YourPassword");
        // 设置参数
        user.setUdp(true);
        client.setUser(user);
        client.setLoginServer("sz.tencent.com");

        try {
            // 登录
            client.login();
            // 等待登录完成
            Thread.sleep(10000);
            // 发送消息
            /* 在sendMessage中填入你自己想发的消息和对方的QQ号 */
            client.im_Send(87654321, Util.getBytes("Hello"));
            // 等待消息发送完成
            Thread.sleep(6000);
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        // 登出
        client.logout();
        // 释放，不再想使用此client实例时必须调用此方法
        client.release();
    }
}