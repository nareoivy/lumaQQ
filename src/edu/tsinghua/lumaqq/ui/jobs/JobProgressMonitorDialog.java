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

import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;

/**
 * 任务进度对话框，扩展自ProgressMonitorDialog，提供了设置模态和非模态的功能
 * 
 * @author luma
 */
public class JobProgressMonitorDialog extends ProgressMonitorDialog {
    /**
     * 创建一个任务进度对话框
     * 
     * @param parent
     * 		父窗口
     */
    public JobProgressMonitorDialog(Shell parent, boolean modeless) {
        super(parent);
        if(modeless)
            setShellStyle(SWT.DIALOG_TRIM);
    }
}
