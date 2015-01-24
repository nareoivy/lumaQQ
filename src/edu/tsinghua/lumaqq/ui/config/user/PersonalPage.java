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

import edu.tsinghua.lumaqq.events.IFaceSelectionListener;
import edu.tsinghua.lumaqq.models.User;
import edu.tsinghua.lumaqq.qq.Util;
import edu.tsinghua.lumaqq.qq.beans.ContactInfo;
import edu.tsinghua.lumaqq.qq.packets.BasicOutPacket;
import edu.tsinghua.lumaqq.qq.packets.out.ModifyInfoPacket;
import edu.tsinghua.lumaqq.resource.Colors;
import edu.tsinghua.lumaqq.resource.Resources;
import edu.tsinghua.lumaqq.ui.config.AbstractPage;
import edu.tsinghua.lumaqq.ui.config.IPacketFiller;
import edu.tsinghua.lumaqq.ui.helper.BeanHelper;
import edu.tsinghua.lumaqq.ui.helper.FaceRegistry;
import edu.tsinghua.lumaqq.ui.helper.HeadFactory;
import edu.tsinghua.lumaqq.ui.helper.UITool;
import edu.tsinghua.lumaqq.ui.listener.AroundBorderPaintListener;
import edu.tsinghua.lumaqq.ui.listener.CenterBorderPaintListener;
import edu.tsinghua.lumaqq.widgets.HeadImageAdvisor;
import edu.tsinghua.lumaqq.widgets.IImageSelectorAdvisor;
import edu.tsinghua.lumaqq.widgets.ImageSelector;
import edu.tsinghua.lumaqq.widgets.qstyle.LevelBar;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Text;

/**
 * 个人资料配置页
 * 
 * @author luma
 */
public class PersonalPage extends AbstractPage implements IPacketFiller, IFaceSelectionListener {	
    private User model;
    private PaintListener centerBorderPaintListener, aroundBorderPaintListener;
	private int headId;
    
    private Text textQQ, textNick, textRealName, textCollege, textHomePage, textAge, textIntro;
    private CCombo comboGender, comboOccupation, comboZodiac, comboHoroscope, comboBlood;
    private CLabel btnFace;
    private Cursor handCursor;
	private Text textSignature;
	private LevelBar levelBar;
    
    /**
     * @param parent
     */
    public PersonalPage(Composite parent, User model, int style) {
        super(parent, style);
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
        centerBorderPaintListener = new CenterBorderPaintListener(new Class[] { Text.class, CCombo.class }, 20, Colors.PAGE_CONTROL_BORDER);
        aroundBorderPaintListener = new AroundBorderPaintListener(new Class[] { CLabel.class }, Colors.PAGE_CONTROL_BORDER);
        handCursor = Display.getCurrent().getSystemCursor(SWT.CURSOR_HAND);
        headId = 0;
    }

	private boolean isEditable() {
		return (style & UserInfoWindow.EDITABLE) != 0;
	}
	
    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.ui.config.AbstractPage#createContent(org.eclipse.swt.widgets.Composite)
     */
	@Override
    protected Control createContent(Composite parent) {
        Composite content = new Composite(parent, SWT.NONE);
        content.setBackground(Colors.PAGE_BACKGROUND);
        content.setLayout(new FormLayout());
        
        Composite container = new Composite(content, SWT.NONE);
        FormData fd = new FormData();
        fd.top = fd.left = new FormAttachment(0, 0);
        fd.bottom = new FormAttachment(100, -20);
        fd.right = new FormAttachment(100, -50);
        container.setLayoutData(fd);
        GridLayout layout = new GridLayout(4, false);
        layout.marginHeight = layout.horizontalSpacing = 8;
        layout.verticalSpacing = 14;
        layout.marginWidth = 15;
        container.setLayout(layout);
        container.setBackground(Colors.PAGE_BACKGROUND);
        container.addPaintListener(centerBorderPaintListener);
        container.addPaintListener(aroundBorderPaintListener);
        
        // 设置使用缺省背景色
        UITool.setDefaultBackground(null);
        
        // 用户号码
        UITool.createLabel(container, user_info_basic_qq);
        GridData gd = new GridData(GridData.FILL_HORIZONTAL | GridData.VERTICAL_ALIGN_CENTER);
        gd.widthHint = 155;
        textQQ = UITool.createSingleText(container, gd, SWT.SINGLE | SWT.READ_ONLY);
        textQQ.setBackground(Colors.PAGE_READONLY_CONTROL_BACKGROUND);
        // 头像按钮
        btnFace = new CLabel(container, SWT.CENTER);
        gd = new GridData(GridData.HORIZONTAL_ALIGN_CENTER | GridData.VERTICAL_ALIGN_BEGINNING);
        gd.heightHint = gd.widthHint = 48;
        gd.horizontalSpan = 2;
        gd.verticalSpan = 3;
        gd.horizontalIndent = 30;
        btnFace.setLayoutData(gd);
        btnFace.setCursor(handCursor);
        btnFace.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				if(isEditable()) {
					Rectangle bound = btnFace.getBounds();
					bound.height++;					
					openImageSelectionShell(btnFace.getParent().toDisplay(bound.x, bound.y + bound.height));
				}
			}
        });
        // 用户昵称
        UITool.createLabel(container, user_info_basic_nick);
        gd = new GridData(GridData.FILL_HORIZONTAL | GridData.VERTICAL_ALIGN_CENTER);
        gd.widthHint = 155;
        textNick = UITool.createSingleText(container, gd);
        // 性别
        UITool.createLabel(container, user_info_basic_gender);
        gd = new GridData(GridData.FILL_HORIZONTAL | GridData.VERTICAL_ALIGN_CENTER);
        gd.widthHint = 155;
        comboGender = UITool.createCCombo(container, gd, SWT.FLAT);
        comboGender.setTextLimit(6);
		comboGender.add(user_info_basic_gender_male);
		comboGender.add(user_info_basic_gender_female);
		comboGender.add(user_info_basic_gender_ladyman);
		comboGender.add(user_info_basic_gender_both);
		comboGender.add(user_info_basic_gender_null);
		comboGender.setVisibleItemCount(5);
		// 个性签名
		gd = new GridData(GridData.VERTICAL_ALIGN_BEGINNING);
		UITool.createLabel(container, user_info_basic_signature, gd);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 3;
		gd.heightHint = 40;
		textSignature = UITool.createMultiText(container, gd, SWT.MULTI | SWT.WRAP);
		textSignature.setTextLimit(100);
		// 等级
		UITool.createLabel(container, user_info_basic_level);
		levelBar = new LevelBar(container);
		levelBar.setBackground(container.getBackground());
		gd = new GridData(GridData.FILL_HORIZONTAL | GridData.VERTICAL_ALIGN_CENTER);
		gd.horizontalSpan = 3;
		gd.heightHint = 16;
		levelBar.setLayoutData(gd);
		levelBar.setGlyphDeepth(3);
		levelBar.setGlyphWeights(new int[] { 16, 4, 1});
		levelBar.setGlyphs(new Image[] { 
				Resources.getInstance().getImage(Resources.icoSun),
				Resources.getInstance().getImage(Resources.icoMoon),
				Resources.getInstance().getImage(Resources.icoStar)
		});
        // 真实姓名
        UITool.createLabel(container, user_info_basic_name);
        gd = new GridData(GridData.FILL_HORIZONTAL | GridData.VERTICAL_ALIGN_CENTER);
        gd.widthHint = 155;
        textRealName = UITool.createSingleText(container, gd);
        // 年龄
        gd = new GridData();
        gd.horizontalIndent = 30;
        UITool.createLabel(container, user_info_basic_age, gd);
        gd = new GridData(GridData.FILL_HORIZONTAL | GridData.VERTICAL_ALIGN_CENTER);
        gd.widthHint = 70;
        textAge = UITool.createSingleText(container, gd);
        textAge.setTextLimit(3);
        // 毕业院校
        UITool.createLabel(container, user_info_basic_college);
        gd = new GridData(GridData.FILL_HORIZONTAL | GridData.VERTICAL_ALIGN_CENTER);
        gd.widthHint = 155;
        textCollege = UITool.createSingleText(container, gd);
        // 生肖
        gd = new GridData();
        gd.horizontalIndent = 30;
        UITool.createLabel(container, user_info_basic_zodiac, gd);
        gd = new GridData(GridData.FILL_HORIZONTAL | GridData.VERTICAL_ALIGN_CENTER);
        gd.widthHint = 70;
        comboZodiac = UITool.createCCombo(container, gd);
		for(String s : BeanHelper.ZODIAC)
			comboZodiac.add(s);
		comboZodiac.setVisibleItemCount(BeanHelper.ZODIAC.length);
        // 职业
        UITool.createLabel(container, user_info_basic_occupation);
        gd = new GridData(GridData.FILL_HORIZONTAL | GridData.VERTICAL_ALIGN_CENTER);
        gd.widthHint = 155;
        comboOccupation = UITool.createCCombo(container, gd, SWT.FLAT);
		for(String s : BeanHelper.OCCUPATION)
			comboOccupation.add(s);
		comboOccupation.setVisibleItemCount(BeanHelper.OCCUPATION.length);
		// 星座
        gd = new GridData();
        gd.horizontalIndent = 30;
		UITool.createLabel(container, user_info_basic_horoscope, gd);
        gd = new GridData(GridData.FILL_HORIZONTAL | GridData.VERTICAL_ALIGN_CENTER);
        gd.widthHint = 50;
		comboHoroscope = UITool.createCCombo(container, gd);
		for(String s : BeanHelper.HOROSCOPE)
			comboHoroscope.add(s);
		comboHoroscope.setVisibleItemCount(BeanHelper.HOROSCOPE.length);
		// 个人主页
		UITool.createLabel(container, user_info_basic_homepage);
        gd = new GridData(GridData.FILL_HORIZONTAL | GridData.VERTICAL_ALIGN_CENTER);
        gd.widthHint = 155;
		textHomePage = UITool.createSingleText(container, gd);     
		// 血型
        gd = new GridData();
        gd.horizontalIndent = 30;
		UITool.createLabel(container, user_info_basic_blood, gd);
        gd = new GridData(GridData.FILL_HORIZONTAL | GridData.VERTICAL_ALIGN_CENTER);
        gd.widthHint = 70;
		comboBlood = UITool.createCCombo(container, gd);
		for(String s : BeanHelper.BLOOD)
			comboBlood.add(s);
		comboBlood.setVisibleItemCount(BeanHelper.BLOOD.length);
		// 个人说明
		gd = new GridData(GridData.VERTICAL_ALIGN_BEGINNING);
		UITool.createLabel(container, user_info_basic_intro, gd);
		gd = new GridData(GridData.FILL_BOTH);
		gd.horizontalSpan = 3;
		textIntro = UITool.createMultiText(container, gd, SWT.MULTI | SWT.WRAP);
		textIntro.setTextLimit(131);
        
        return content;
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
		// 昵称和头像和QQ号
		textNick.setText(model.nick);
		textQQ.setText(String.valueOf(model.qq));
		FaceRegistry reg = FaceRegistry.getInstance();
		if(model.hasCustomHead && reg.hasFace(reg.getMd5ById(model.customHeadId))) {
			headId = model.customHeadId;
			btnFace.setImage(Resources.getInstance().getCustomHead(headId, false));
		} else {
			headId = model.headId;
			btnFace.setImage(HeadFactory.getOnlineHead(model));			
		}
		// 开始设置其他联系信息
		ContactInfo info = model.info;
		if(info == null) return;
		// 年龄
		textAge.setText(String.valueOf(info.age));
		// 性别
		comboGender.setText(info.gender);
		// 真实姓名
		textRealName.setText(info.name);
		// 毕业学校
		textCollege.setText(info.college);
		// 职业
		comboOccupation.setText(info.occupation);
		// 生肖
		int i = info.zodiac;
		if(i <= 12 && i >=0)
			comboZodiac.setText(comboZodiac.getItem(i));
		// 血型
		i = info.blood;
		if(i <= 5 && i >= 0)
			comboBlood.setText(comboBlood.getItem(i));
		// 星座
		i = info.horoscope;
		if(i <= 12 && i >= 0 )
			comboHoroscope.setText(comboHoroscope.getItem(i));
		// 主页
		textHomePage.setText(info.homepage);
		// 个人说明
		textIntro.setText(info.intro);
		// 等级
		levelBar.setValue(model.level);
		levelBar.redraw();
		// 个性签名
		textSignature.setText(model.signature);
    }

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
        return user_info_page_basic;
    }

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.shells.IPacketFiller#fill(edu.tsinghua.lumaqq.qq.packets.OutPacket)
     */
    public void fill(BasicOutPacket packet) {
        if(packet instanceof ModifyInfoPacket) {
            ContactInfo info = ((ModifyInfoPacket)packet).getContactInfo();
    		info.head = headId;
            info.qq = Util.getInt(textQQ.getText(), 0);
            info.nick = textNick.getText();
            info.gender = comboGender.getText();
            info.name = textRealName.getText();
            info.college = textCollege.getText();
            info.occupation = comboOccupation.getText();
            info.homepage = textHomePage.getText();
            info.age = Util.getInt(textAge.getText(), 0);
    		info.zodiac = comboZodiac.indexOf(comboZodiac.getText());
    		info.blood = comboBlood.indexOf(comboBlood.getText());
    		info.horoscope = comboHoroscope.indexOf(comboHoroscope.getText());
            info.intro = textIntro.getText();
        }
    }
    
    /**
     * 打开图片选择窗口
     */
    private void openImageSelectionShell(Point loc) {
    	ImageSelector fss = new ImageSelector(parentShell, new HeadImageAdvisor());
    	fss.setListener(this);
		fss.setLocation(loc);
		fss.open();	
    }
    
	/**
	 * @return Returns the faceId.
	 */
	protected int getHeadId() {
		return headId;
	}

    /* (non-Javadoc)
     * @see edu.tsinghua.lumaqq.events.IFaceSelectionListener#faceSelected(edu.tsinghua.lumaqq.ui.IImageProvider, int, int)
     */
    public void faceSelected(IImageSelectorAdvisor provider, int group, int sequence) {
        int code = provider.getImageCode(group, sequence);
        if(code != -1) {
            headId = code;
	        btnFace.setImage(provider.getImage(group, sequence));
        }
    }
    
    boolean isSignatureModified() {
    	return !textSignature.getText().equals(model.signature);
    }
    
    String getSignature() {
    	return textSignature.getText();
    }
}
