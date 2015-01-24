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
package edu.tsinghua.lumaqq.ui.config.user;

import static edu.tsinghua.lumaqq.resource.Messages.*;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;

import edu.tsinghua.lumaqq.models.User;
import edu.tsinghua.lumaqq.resource.Colors;
import edu.tsinghua.lumaqq.resource.Resources;
import edu.tsinghua.lumaqq.ui.MainShell;
import edu.tsinghua.lumaqq.ui.config.AbstractPage;
import edu.tsinghua.lumaqq.ui.helper.UITool;
import edu.tsinghua.lumaqq.ui.listener.AroundBorderPaintListener;

/**
 * QQ秀页
 * 
 * @author luma
 */
public class QQShowPage extends AbstractPage {    
    private Label lblQQShow;
    
    private MainShell main;
    private Cursor handCursor;
    private User model;
    
    /**
     * @param parent
     */
    public QQShowPage(Composite parent, MainShell main, User model, int style) {
        super(parent, style);
        this.main = main;
        this.model = model;
    }
    
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.shells.AbstractPage#setModel(java.lang.Object)
     */
	@Override
    public void setModel(Object model) {
        if(model instanceof User)
            this.model = (User)model;
    }
    
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.shells.AbstractPage#initialVariable()
     */
	@Override
    protected void initialVariable() {
        handCursor = Display.getCurrent().getSystemCursor(SWT.CURSOR_HAND);
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.ui.config.AbstractPage#createContent(org.eclipse.swt.widgets.Composite)
     */
	@Override
    protected Control createContent(Composite parent) {
        Composite container = new Composite(parent, SWT.NONE);
        container.setBackground(Colors.PAGE_BACKGROUND);
        GridLayout layout = new GridLayout();
        layout.marginHeight = layout.marginWidth = 10;
        container.setLayout(layout);
        
        // 设置使用缺省背景色
        UITool.setDefaultBackground(null);
        
        // QQ秀组
        layout = new GridLayout(2, false);
        layout.marginHeight = layout.marginWidth = layout.horizontalSpacing = 15;
        Group qqshowGroup = UITool.createGroup(container, user_info_group_qqshow, layout);
        qqshowGroup.addPaintListener(new AroundBorderPaintListener(new Class[] { Label.class }));
        
        MouseListener ml = new MouseAdapter() {
            @Override
			public void mouseDown(MouseEvent e) {
                main.getShellLauncher().goQQShowMall();
            }
        };
        
        // QQ秀，QQ秀大小是140x226
        GridData gd = new GridData();        
        gd.widthHint = 142;
        gd.heightHint = 228;
        lblQQShow = UITool.createLabel(qqshowGroup, "", gd, SWT.CENTER);
        lblQQShow.setCursor(handCursor);
        lblQQShow.addMouseListener(ml);
        
        layout = new GridLayout();
        layout.verticalSpacing = 15;
        Composite c = UITool.createContainer(qqshowGroup, new GridData(GridData.VERTICAL_ALIGN_BEGINNING), layout);
        
        // 刷新QQ秀
        gd = new GridData(GridData.VERTICAL_ALIGN_END);  
        CLabel lblRefreshQQShow = UITool.createLink(c, user_info_label_refresh_qqshow, Resources.getInstance().getImage(Resources.icoRefresh), gd);        
        lblRefreshQQShow.addMouseListener(new MouseAdapter() {
            @Override
			public void mouseDown(MouseEvent e) {
                // 这里暂时去掉QQ Show的检查，目前的检查代码感觉并不正确，不启用
                // if(hasQQShow()) {
				setQQShow(Resources.getInstance().getImage(Resources.bmpDownloading));
				main.getQQShowManager().downloadQQShowImage(model.qq);		                    
                /* } else
                    MessageDialog.openInformation(parentShell, message.box.common.info.title"), message.box.no.qqshow"));*/
            }
        });
        
        if(isEditable()) {
	        // 去QQ秀商城
	        CLabel lblQQShowMall = UITool.createLink(c, user_info_label_go_qqshow_mall, Resources.getInstance().getImage(Resources.icoQQShowMall));
	        lblQQShowMall.addMouseListener(ml);
	        // 更换QQ秀
	        CLabel lblChangeQQShow = UITool.createLink(c, user_info_label_change_qqshow, Resources.getInstance().getImage(Resources.icoChangeQQShow));
	        lblChangeQQShow.addMouseListener(ml);
	        // QQ家园
	        CLabel lblQQHome = UITool.createLink(c, user_info_label_qqshow_home, Resources.getInstance().getImage(Resources.icoQQHome));
	        lblQQHome.addMouseListener(new MouseAdapter() {
	            @Override
				public void mouseDown(MouseEvent e) {
	                main.getShellLauncher().goQQHome();
	            }
	        });            
        }
       
        return container;
    }
	
	private boolean isEditable() {
		return (style & UserInfoWindow.EDITABLE) != 0;
	}

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.shells.AbstractPage#saveDirtyProperty(int)
     */
	@Override
    protected void saveDirtyProperty(int propertyId) {
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.shells.AbstractPage#initializeValues()
     */
	@Override
    protected void initializeValues() {
		// QQ秀
		if(main.getQQShowManager().isCached(model.qq)) 
			lblQQShow.setImage(main.getQQShowManager().getQQShowImage(model.qq));
		else 
			lblQQShow.setImage(Resources.getInstance().getImage(Resources.bmpDefaultQQShow));
		lblQQShow.redraw();
    }
    
    /**
     * 设置QQ秀
     * 
     * @param image
     */
    protected void setQQShow(Image image) {
        lblQQShow.setImage(image);
        lblQQShow.redraw();
    }
    
	/**
	 * @return true如果好友有QQ秀，false如果没有QQ秀
	 */
	/*private boolean hasQQShow() {
		ContactInfo info = (ContactInfo)model.getProperty(IQQNode.CONTACT);
		if(info == null)
		    return false;
		if(info.infos[info.qqShow].equals("0") || info.infos[info.qqShow].equals(""))
		    return false;
		else
		    return true;
	}*/

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.shells.AbstractPage#getImage()
     */
	@Override
    protected Image getImage() {
        if(isEditable())
            return Resources.getInstance().getImage(Resources.icoModifyPersonInfo24);
        else 
            return Resources.getInstance().getImage(Resources.icoViewPersonInfo24);
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.shells.AbstractPage#getTitle()
     */
	@Override
    protected String getTitle(int page) {
        return user_info_page_qqshow;
    }
}
