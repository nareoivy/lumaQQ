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
package edu.tsinghua.lumaqq.ui.config.cluster;

import static edu.tsinghua.lumaqq.resource.Messages.*;

import edu.tsinghua.lumaqq.events.IFaceSelectionListener;
import edu.tsinghua.lumaqq.models.Cluster;
import edu.tsinghua.lumaqq.qq.QQ;
import edu.tsinghua.lumaqq.resource.Colors;
import edu.tsinghua.lumaqq.resource.Resources;
import edu.tsinghua.lumaqq.ui.MainShell;
import edu.tsinghua.lumaqq.ui.config.AbstractPage;
import edu.tsinghua.lumaqq.ui.dialogs.ClusterCategoryDialog;
import edu.tsinghua.lumaqq.ui.helper.ClusterCategoryTool;
import edu.tsinghua.lumaqq.ui.helper.UITool;
import edu.tsinghua.lumaqq.ui.listener.AroundBorderPaintListener;
import edu.tsinghua.lumaqq.ui.listener.CenterBorderPaintListener;
import edu.tsinghua.lumaqq.widgets.ClusterHeadImageAdvisor;
import edu.tsinghua.lumaqq.widgets.IImageSelectorAdvisor;
import edu.tsinghua.lumaqq.widgets.ImageSelector;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;

/**
 * 群基本资料页
 * 
 * @author luma
 */
public class ClusterPage extends AbstractPage implements IFaceSelectionListener {
    private Text textId, textCreator, textName, textNotice, textDescription;
    private Text textCategory;
    private Group authGroup;
    private Button rdoNoAuth, rdoNeedAuth, rdoNoAdd;
    private CLabel lblFace;
    private Cursor handCursor;
    private Cluster model;
    private int headId;
    private int categoryId;
    private MainShell main;
    private ClusterCategoryTool utility;
    
    private static final int FACE = 0;
    
	/**
	 * @param parent
	 */
	public ClusterPage(Composite parent, MainShell main, Cluster model, int style) {
		super(parent, style);
		this.model = model;
		this.main = main;
		utility = main.getClusterCategoryUtility();
	}
	
	@Override
	public void setModel(Object model) {
		this.model = (Cluster)model;
		categoryId = this.model.category;
	}
	
	/* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.ui.config.AbstractPage#initialVariable()
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
        final Composite content = new Composite(parent, SWT.NONE);
        content.setBackground(Colors.PAGE_BACKGROUND);
        GridLayout layout = new GridLayout(4, false);
        layout.marginWidth = 15;
        layout.marginHeight = 8;
        content.setLayout(layout);
        content.addPaintListener(new CenterBorderPaintListener(new Class[] { Text.class, CCombo.class }, 18, Colors.PAGE_CONTROL_BORDER));
        content.addPaintListener(new AroundBorderPaintListener(new Class[] { CLabel.class }, Colors.PAGE_CONTROL_BORDER));
        
        // 设置使用缺省背景色
        UITool.setDefaultBackground(null);
        
        // 号码
        UITool.createLabel(content, cluster_info_basic_id);
        GridData gd = new GridData();
        gd.widthHint = 120;
        textId = UITool.createSingleText(content, gd, SWT.SINGLE | SWT.READ_ONLY);        
        textId.setBackground(Colors.PAGE_READONLY_CONTROL_BACKGROUND);
        // 头像
        lblFace = new CLabel(content, SWT.CENTER);
        gd = new GridData(GridData.HORIZONTAL_ALIGN_CENTER | GridData.VERTICAL_ALIGN_BEGINNING);
        gd.heightHint = gd.widthHint = 40;
        gd.horizontalSpan = 2;
        gd.verticalSpan = 2;
        gd.horizontalIndent = 12;
        lblFace.setLayoutData(gd);
        lblFace.setCursor(handCursor);
        lblFace.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				Rectangle bound = lblFace.getBounds();
				openImageSelectionShell(lblFace.getParent().toDisplay(bound.x, bound.y + bound.height + 1));											
			}
		});
        // 创建人
        UITool.createLabel(content, cluster_info_basic_creator);
        gd = new GridData();
        gd.widthHint = 120;
        textCreator = UITool.createSingleText(content, gd, SWT.SINGLE | SWT.READ_ONLY);
        textCreator.setBackground(Colors.PAGE_READONLY_CONTROL_BACKGROUND);
        // 名称
        UITool.createLabel(content, cluster_info_basic_name);
        gd = new GridData();
        gd.widthHint = 120;        
        textName = UITool.createSingleText(content, gd, (style == ClusterInfoWindow.READ_ONLY) ? (SWT.READ_ONLY | SWT.SINGLE) : SWT.SINGLE);
        // 分类
        gd = new GridData();
        gd.horizontalIndent = 12;
        UITool.createLabel(content, cluster_info_basic_category, gd);
        layout = new GridLayout(2, false);
        layout.marginHeight = 1;
        layout.marginWidth = 3;
        Composite temp = UITool.createContainer(content, new GridData(GridData.FILL_HORIZONTAL), layout);
        temp.addPaintListener(new CenterBorderPaintListener(new Class[] { Text.class }, 18, Colors.PAGE_CONTROL_BORDER));
        textCategory = UITool.createSingleText(temp, new GridData(GridData.FILL_HORIZONTAL), SWT.SINGLE | SWT.READ_ONLY);
        Button btnSetting = UITool.createButton(temp, button_setting);
        btnSetting.addSelectionListener(new SelectionAdapter() {
        	@Override
        	public void widgetSelected(SelectionEvent e) {
        		ClusterCategoryDialog dialog = new ClusterCategoryDialog(content.getShell(), main);
        		dialog.setCategoryId(model.category);
        		if(IDialogConstants.OK_ID == dialog.open()) {
        			categoryId = dialog.getCategoryId();
        			textCategory.setText(utility.getCategoryPath(categoryId));
        		}
        	}
        });
        // 群内公告
        gd = new GridData();
        gd.horizontalSpan = 4;
        UITool.createLabel(content, cluster_info_basic_notice, gd);
        gd = new GridData(GridData.FILL_HORIZONTAL);
        gd.horizontalSpan = 4;
        gd.heightHint = 40;
        textNotice = UITool.createMultiText(content, gd, (style == ClusterInfoWindow.READ_ONLY) ? (SWT.READ_ONLY | SWT.MULTI | SWT.WRAP) : (SWT.MULTI | SWT.WRAP));
        // 群的简介
        gd = new GridData();
        gd.horizontalSpan = 4;
        UITool.createLabel(content, cluster_info_basic_description, gd);
        gd = new GridData(GridData.FILL_HORIZONTAL);
        gd.horizontalSpan = 4;
        gd.heightHint = 40;
        textDescription = UITool.createMultiText(content, gd, (style == ClusterInfoWindow.READ_ONLY) ? (SWT.READ_ONLY | SWT.MULTI | SWT.WRAP) : (SWT.MULTI | SWT.WRAP));
        // 验证组
        gd = new GridData(GridData.FILL_HORIZONTAL);
        gd.horizontalSpan = 4;
        authGroup = UITool.createGroup(content, cluster_info_basic_auth, gd, new GridLayout());
        rdoNoAuth = UITool.createRadio(authGroup, cluster_info_basic_no_auth);
        rdoNeedAuth = UITool.createRadio(authGroup, cluster_info_basic_need_auth);
        rdoNoAdd = UITool.createRadio(authGroup, cluster_info_basic_no_add);
        
        return content;
	}
    
	/* (non-Javadoc)
	 * @see edu.tsinghua.lumaqq.ui.config.AbstractPage#saveDirtyProperty(int)
	 */
	@Override
	protected void saveDirtyProperty(int propertyId) {
	    switch(propertyId) {
	        case FACE:
	        	model.headId = headId;
	        	main.getBlindHelper().refreshModel(model);
	            break;
	    }
	}

	/* (non-Javadoc)
	 * @see edu.tsinghua.lumaqq.ui.config.AbstractPage#initializeValues()
	 */
	@Override
	protected void initializeValues() {
	    setOtherInfo();
	    setClusterFace();
	    disableControls();
	}
	
	/**
	 * 设置控件使能状态，如果是临时群或者只读方式，禁止修改
	 */
	private void disableControls() {
	    boolean disable = !model.isPermanent() || style == ClusterInfoWindow.READ_ONLY;	    
	    if(disable) {
		    textName.setBackground(Colors.PAGE_READONLY_CONTROL_BACKGROUND);
		    textNotice.setBackground(Colors.PAGE_READONLY_CONTROL_BACKGROUND);
		    textDescription.setBackground(Colors.PAGE_READONLY_CONTROL_BACKGROUND);	 
		    textCategory.setEnabled(false);
		    textCategory.setBackground(Colors.PAGE_READONLY_CONTROL_BACKGROUND);
		    authGroup.setEnabled(false);
		    rdoNeedAuth.setEnabled(false);
		    rdoNoAdd.setEnabled(false);
		    rdoNoAuth.setEnabled(false);
	    } else {
		    textName.setBackground(Colors.WHITE);
		    textNotice.setBackground(Colors.WHITE);
		    textDescription.setBackground(Colors.WHITE);	
	    }
	}
	
	/* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.ui.config.AbstractPage#refreshValues()
     */
	@Override
    public void refreshValues() {
        setOtherInfo();
    }
	
	/**
	 * 设置其他信息
	 */
	private void setOtherInfo() {
	    if(model.isPermanent())
	        textId.setText(String.valueOf(model.externalId));
	    else
	        textId.setText(String.valueOf(model.clusterId));
	    textCreator.setText(String.valueOf(model.creator));
	    textName.setText(model.name);
        headId = model.headId;
        textCategory.setText(utility.getCategoryPath(model.category));
        textNotice.setText(model.notice);
        textDescription.setText(model.description);
        switch(model.authType) {
            case QQ.QQ_AUTH_CLUSTER_NO:
                rdoNoAuth.setSelection(true);
            	break;
            case QQ.QQ_AUTH_CLUSTER_NEED:
                rdoNeedAuth.setSelection(true);
            	break;
            case QQ.QQ_AUTH_CLUSTER_REJECT:
                rdoNoAdd.setSelection(true);
            	break;
        }
	}
	
	/**
	 * 设置群头像
	 */
	private void setClusterFace() {
        lblFace.setImage(Resources.getInstance().getClusterHead(model.headId));
	}

	/* (non-Javadoc)
	 * @see edu.tsinghua.lumaqq.ui.config.AbstractPage#getImage()
	 */
	@Override
	protected Image getImage() {
        return Resources.getInstance().getImage(Resources.icoViewPersonInfo24);
	}

	/* (non-Javadoc)
	 * @see edu.tsinghua.lumaqq.ui.config.AbstractPage#getTitle()
	 */
	@Override
	protected String getTitle(int page) {
		return cluster_info_page_basic;
	}
	
	/**
	 * @return
	 * 		验证类型常量
	 */
	private byte getAuthType() {
	    if(rdoNoAuth.getSelection())
	        return QQ.QQ_AUTH_CLUSTER_NO;
	    else if(rdoNoAdd.getSelection())
	        return QQ.QQ_AUTH_CLUSTER_REJECT;
	    else
	        return QQ.QQ_AUTH_CLUSTER_NEED;	        
	}
	
	/**
	 * 发送修改群信息请求包
	 * 
	 * @return
	 * 		包序号
	 */
	public char doModifyClusterInfo() {
	    return main.getClient().cluster_ModifyInfo(model.clusterId,
	            textName.getText(),
	            textNotice.getText(),
	            textDescription.getText(),
	            model.oldCategory,
	            categoryId,
	            getAuthType());
	}
	
    
    /**
     * 打开图片选择窗口
     */
    private void openImageSelectionShell(Point loc) {
    	ImageSelector fss = new ImageSelector(parentShell, new ClusterHeadImageAdvisor());
    	fss.setListener(this);
		fss.setLocation(loc);
		fss.open();	
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.events.IFaceSelectionListener#faceSelected(edu.tsinghua.lumaqq.ui.IImageProvider, int, int)
     */
    public void faceSelected(IImageSelectorAdvisor provider, int group, int sequence) {
        int code = provider.getImageCode(group, sequence);
        if(code != -1) {
            headId = code;
	        lblFace.setImage(provider.getImage(group, sequence));
	        makeDirty(FACE);
        }
    }
}
