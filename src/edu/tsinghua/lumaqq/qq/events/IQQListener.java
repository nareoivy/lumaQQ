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
package edu.tsinghua.lumaqq.qq.events;


/**
 * QQ事件监听器，比如登陆成功，登陆失败等等等等，这些事件常量
 * 定义在QQEvent中，用户如果对某个事件感兴趣可以实现这个接口，
 * 然后判断事件常量即可
 *
 * @author luma
 */
public interface IQQListener {	
    /**
     * QQ事件，用户可以获取type变量得到具体的事件类型
     * @param e QQEvent
     */
    public void qqEvent(QQEvent e);
}
