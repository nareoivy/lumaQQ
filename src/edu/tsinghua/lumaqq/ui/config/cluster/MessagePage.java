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

import static edu.tsinghua.lumaqq.resource.Messages.cluster_info_message_accept;
import static edu.tsinghua.lumaqq.resource.Messages.cluster_info_message_block;
import static edu.tsinghua.lumaqq.resource.Messages.cluster_info_message_eject;
import static edu.tsinghua.lumaqq.resource.Messages.cluster_info_message_group;
import static edu.tsinghua.lumaqq.resource.Messages.cluster_info_message_record;
import static edu.tsinghua.lumaqq.resource.Messages.cluster_info_message_show_number;
import static edu.tsinghua.lumaqq.resource.Messages.cluster_info_page_message;

import edu.tsinghua.lumaqq.models.Cluster;
import edu.tsinghua.lumaqq.models.MessageSetting;
import edu.tsinghua.lumaqq.resource.Colors;
import edu.tsinghua.lumaqq.resource.Resources;
import edu.tsinghua.lumaqq.ui.config.AbstractPage;
import edu.tsinghua.lumaqq.ui.helper.UITool;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;

/**
 * 消息设置页
 * 
 * @author luma
 */
public class MessagePage extends AbstractPage {
    private Button rdoAccept, rdoEject, rdoCounter, rdoRecord, rdoBlock;
    private Cluster model;

	/**
	 * @param parent
	 */
	public MessagePage(Composite parent, Cluster model) {
		super(parent);
		this.model = model;
	}
	
	@Override
	public void setModel(Object model) {
		this.model = (Cluster)model;
	}

	/* (non-Javadoc)
	 * @see edu.tsinghua.lumaqq.ui.config.AbstractPage#createContent(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Control createContent(Composite parent) {
        Composite content = new Composite(parent, SWT.NONE);
        content.setBackground(Colors.PAGE_BACKGROUND);
        GridLayout layout = new GridLayout();
        layout.marginHeight = layout.marginWidth = 15;
        content.setLayout(layout);
        
        // 设置使用缺省背景色
        UITool.setDefaultBackground(null);
        
        Group msgGroup = UITool.createGroup(content, cluster_info_message_group, new GridData(GridData.FILL_HORIZONTAL), new GridLayout());
        
        // 接收并提示消息
        rdoAccept = UITool.createRadio(msgGroup, cluster_info_message_accept);
        // 自动弹出消息
        rdoEject = UITool.createRadio(msgGroup, cluster_info_message_eject);
        // 消息来时只显示消息数目
        rdoCounter = UITool.createRadio(msgGroup, cluster_info_message_show_number);
        // 接收但不提示消息(只保存在聊天记录中)
        rdoRecord = UITool.createRadio(msgGroup, cluster_info_message_record);
        // 阻止一切该群的消息
        rdoBlock = UITool.createRadio(msgGroup, cluster_info_message_block);
        
        return content;
	}

	/* (non-Javadoc)
	 * @see edu.tsinghua.lumaqq.ui.config.AbstractPage#saveDirtyProperty(int)
	 */
	@Override
	protected void saveDirtyProperty(int propertyId) {
	}

	/**
     * @return
     * 		消息设置
     */
    private MessageSetting getMessageSetting() {
        if(rdoAccept.getSelection())
            return MessageSetting.ACCEPT;
        else if(rdoEject.getSelection())
            return MessageSetting.EJECT;
        else if(rdoCounter.getSelection())
            return MessageSetting.COUNTER;
        else if(rdoRecord.getSelection())
            return MessageSetting.RECORD;
        else if(rdoBlock.getSelection())
            return MessageSetting.BLOCK;
        else
            return MessageSetting.ACCEPT;
    }

    /* (non-Javadoc)
	 * @see edu.tsinghua.lumaqq.ui.config.AbstractPage#initializeValues()
	 */
	@Override
	protected void initializeValues() {
	    rdoAccept.setSelection(false);
	    rdoEject.setSelection(false);
	    rdoRecord.setSelection(false);
	    rdoCounter.setSelection(false);
	    rdoBlock.setSelection(false);
	    
	    switch(model.messageSetting) {
	    	case EJECT:
	    		rdoEject.setSelection(true);
	    		break;
	    	case COUNTER:
	    		rdoCounter.setSelection(true);
	    		break;
	    	case RECORD:
	    		rdoRecord.setSelection(true);
	    		break;
	    	case BLOCK:
	    		rdoBlock.setSelection(true);
	    		break;
	    	default:
	    		rdoAccept.setSelection(true);
	    		break;
	    }
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
		return cluster_info_page_message;
	}

    /**
     * 保存消息设置
     */
    public void doSave() {
    	model.messageSetting = getMessageSetting();
    }
}
