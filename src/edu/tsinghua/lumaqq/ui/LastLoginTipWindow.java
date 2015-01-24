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
package edu.tsinghua.lumaqq.ui;

import static edu.tsinghua.lumaqq.resource.Messages.*;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.graphics.Image;

import edu.tsinghua.lumaqq.qq.QQClient;
import edu.tsinghua.lumaqq.qq.Util;
import edu.tsinghua.lumaqq.qq.beans.QQUser;
import edu.tsinghua.lumaqq.resource.Resources;
import edu.tsinghua.lumaqq.ui.config.sys.SystemOptionWindow;
import edu.tsinghua.lumaqq.ui.helper.DateTool;

/**
 * 上次登录信息提示窗口
 *
 * @author luma
 */
public class LastLoginTipWindow extends BaseTipWindow {
	public LastLoginTipWindow(MainShell main) {
		super(main);
	}

	@Override
	protected String getTitle() {
		return last_login_title;
	}

	@Override
	protected Image getImage() {
		return res.getImage(Resources.icoLumaQQ);
	}

	@Override
	protected String getButtonLabel() {
		return last_login_setting;
	}
	
	@Override
	protected void onButton() {
		main.getShellLauncher().openSystemOptionWindow().setCurrentPage(SystemOptionWindow.GUI);
	}

	@Override
	protected String getTip() {
		QQClient client = main.getClient();
		if(client == null)
			return "";
		QQUser user = client.getUser();
		if(user == null)
			return "";
		
		return NLS.bind(last_login_tip, DateTool.format(user.getLastLoginTime()), Util.getIpStringFromBytes(user.getLastLoginIp()));
	}
}
