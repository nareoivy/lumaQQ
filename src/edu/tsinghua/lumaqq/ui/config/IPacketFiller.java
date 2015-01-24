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
package edu.tsinghua.lumaqq.ui.config;

import edu.tsinghua.lumaqq.qq.packets.BasicOutPacket;

/**
 * 包填充接口，用在配置窗口中，有些配置窗口不光是需要修改本地的信息，也需要发送网络包把
 * 修改提交给服务器。而配置页本身是独立的，这个接口用在一些需要各个配置页合作完成的任务时
 * 
 * @author luma
 */
public interface IPacketFiller {
    /**
     * 填充内容到包中
     * 
     * @param packet
     * 		发送包对象
     */
    public void fill(BasicOutPacket packet);
}
