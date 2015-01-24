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
package edu.tsinghua.lumaqq.ui.dialogs;

import static edu.tsinghua.lumaqq.resource.Messages.about_group_contributor;
import static edu.tsinghua.lumaqq.resource.Messages.about_group_thanks;
import static edu.tsinghua.lumaqq.resource.Messages.about_message;
import static edu.tsinghua.lumaqq.resource.Messages.about_title;
import static edu.tsinghua.lumaqq.resource.Messages.about_version;
import static edu.tsinghua.lumaqq.resource.Messages.update_time;

import edu.tsinghua.lumaqq.resource.Colors;
import edu.tsinghua.lumaqq.resource.Resources;
import edu.tsinghua.lumaqq.ui.helper.UITool;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;

/**
 * 关于对话框
 * 
 * @author luma
 */
public class AboutDialog extends TitleAreaDialog {
    /**
     * @param parentShell
     */
    public AboutDialog(Shell parentShell) {
        super(parentShell);        
    }
    
    /* (non-Javadoc)
     * @see org.eclipse.jface.window.Window#configureShell(org.eclipse.swt.widgets.Shell)
     */
    @Override
	protected void configureShell(Shell newShell) {
    	super.configureShell(newShell);
        newShell.setText(about_title);
        newShell.setImage(Resources.getInstance().getImage(Resources.icoAbout));
    }
    
    @Override
    protected Point getInitialSize() {
    	return new Point(500, 500);
    }
    
    /* (non-Javadoc)
     * @see org.eclipse.jface.dialogs.TitleAreaDialog#createDialogArea(org.eclipse.swt.widgets.Composite)
     */
    @Override
	protected Control createDialogArea(Composite parent) {
        Composite composite = (Composite)super.createDialogArea(parent);        
        
        UITool.setDefaultBackground(Colors.WHITE);
        
        Composite c = UITool.createContainer(composite, new GridData(GridData.FILL_BOTH), new GridLayout(2, true));        
        
        Group contriGroup = UITool.createGroup(c, about_group_contributor, new GridData(GridData.FILL_BOTH), new GridLayout());
        UITool.createLabel(contriGroup, "三三");
        UITool.createLabel(contriGroup, "notXX@smth");
        UITool.createLabel(contriGroup, "Gavin@smth");
        UITool.createLabel(contriGroup, "gnulinux@smth");
        UITool.createLabel(contriGroup, "quhw@smth");
        UITool.createLabel(contriGroup, "sdumjr@smth");
        UITool.createLabel(contriGroup, "civet@linuxsir");
        UITool.createLabel(contriGroup, "e-xiaobin@X-SAiRE");
        UITool.createLabel(contriGroup, "zening@gmail");
        UITool.createLabel(contriGroup, "lxleaves_zhang");
        UITool.createLabel(contriGroup, "starboy@freecity");
        
        Group thankGroup = UITool.createGroup(c, about_group_thanks, new GridData(GridData.FILL_BOTH), new GridLayout());
        UITool.createLabel(thankGroup, "Chicyu@smth");
        UITool.createLabel(thankGroup, "hongjq@smth");
        UITool.createLabel(thankGroup, "wqfox@smth");
        UITool.createLabel(thankGroup, "j4me@smth");
        UITool.createLabel(thankGroup, "henryouly@linuxsir");
        UITool.createLabel(thankGroup, "jeff_yecn@linuxsir");
        UITool.createLabel(thankGroup, "puzzlebird@linuxsir");
        UITool.createLabel(thankGroup, "webzi@linuxsir");
        UITool.createLabel(thankGroup, "Yuking@linuxsir");
        UITool.createLabel(thankGroup, "Winnie@qq");
        UITool.createLabel(thankGroup, "OpenQ Team");
        UITool.createLabel(thankGroup, "isQ Team");
        
        setTitle(NLS.bind(about_version, update_time));
        setMessage(about_message);
        setTitleImage(Resources.getInstance().getImage(Resources.icoAboutTitleImage));

        return composite;
    }
    
    /* (non-Javadoc)
     * @see org.eclipse.jface.dialogs.Dialog#createButtonsForButtonBar(org.eclipse.swt.widgets.Composite)
     */
    @Override
	protected void createButtonsForButtonBar(Composite parent) {
        createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, true);
    }
    
    /* (non-Javadoc)
     * @see org.eclipse.jface.window.Window#getInitialLocation(org.eclipse.swt.graphics.Point)
     */
    @Override
	protected Point getInitialLocation(Point initialSize) {
		// 窗口居中
		Rectangle displayRect = getShell().getDisplay().getClientArea();
		return new Point((displayRect.width - initialSize.x) / 2, (displayRect.height - initialSize.y) / 2);
    }
}
