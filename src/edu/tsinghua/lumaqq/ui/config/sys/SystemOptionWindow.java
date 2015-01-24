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
package edu.tsinghua.lumaqq.ui.config.sys;

import static edu.tsinghua.lumaqq.resource.Messages.*;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;

import edu.tsinghua.lumaqq.resource.Resources;
import edu.tsinghua.lumaqq.ui.MainShell;
import edu.tsinghua.lumaqq.ui.config.AbstractConfigurationWindow;


/**
 * 系统设置窗口
 * 
 * @author luma
 */
public class SystemOptionWindow extends AbstractConfigurationWindow {
    // 配置页索引常量
	/** GUI配置页 */
	public static final int GUI = 0;
	/** 热键配置页 */
	public static final int KEY = 1;
	/** 消息配置页 */
	public static final int MESSAGE = 2;
	/** 回复配置页 */
	public static final int REPLY = 3;
	/** 登录配置页 */
	public static final int LOGIN = 4;
	/** 同步配置页 */
	public static final int SYNC = 5;
	/** 其他配置页 */
	public static final int OTHER = 6;
	
	private MainShell main;

    /**
     * @param parent
     */
    public SystemOptionWindow(MainShell main) {
        super(main.getShell());
        this.main = main;
        setCloseButtonText(button_cancel);
    }
    
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.shells.AbstractConfigurationWindow#initialPages()
     */
	@Override
    protected void initialPages() {
        addPage(new GUIPage(getPageContainer(), main));
        addPage(new KeyPage(getPageContainer(), main));
        addPage(new MessagePage(getPageContainer(), main));
        addPage(new ReplyPage(getPageContainer(), main));
        addPage(new LoginPage(getPageContainer(), main));
        addPage(new SyncPage(getPageContainer(), main));
        addPage(new OtherPage(getPageContainer(), main));
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.shells.AbstractConfigurationWindow#getTitle()
     */
	@Override
    protected String getTitle() {
        return sys_opt_title;
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.shells.AbstractConfigurationWindow#getImage()
     */
	@Override
    protected Image getImage() {
        return Resources.getInstance().getImage(Resources.icoSysOpt);
    }
    
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.shells.AbstractConfigurationWindow#configureShellSize()
     */
	@Override
    protected void configureShellSize() {
        shell.pack();
        Point size = shell.getSize();
		int width = shell.getDisplay().getClientArea().width;
		if(size.x > width)
			size.x = width;
        shell.setSize(size);
    }
    
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.shells.AbstractConfigurationWindow#onShellClose()
     */
	@Override
    protected void onShellClose() {
		main.getShellRegistry().deregisterSystemOptionWindow();
    }
    
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.shells.AbstractConfigurationWindow#onOK()
     */
	@Override
    protected void onOK() {
        saveAllAndClose();
        main.getOptionHelper().save();
    }
}
