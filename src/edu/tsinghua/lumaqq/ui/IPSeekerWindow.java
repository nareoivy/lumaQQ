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

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;

import edu.tsinghua.lumaqq.IPEntry;
import edu.tsinghua.lumaqq.IPSeeker;
import edu.tsinghua.lumaqq.qq.events.IQQListener;
import edu.tsinghua.lumaqq.resource.Colors;
import edu.tsinghua.lumaqq.resource.Resources;
import edu.tsinghua.lumaqq.ui.helper.UITool;
import edu.tsinghua.lumaqq.ui.listener.AroundBorderPaintListener;
import edu.tsinghua.lumaqq.widgets.qstyle.Slat;

/**
 * IP查询窗口
 *
 * @author luma
 */
public class IPSeekerWindow extends BaseWindow {
	private Text textInput, textResult;
	private Slat btnAddr2Ip, btnIp2Addr;
	private IPSeeker seeker;

	public IPSeekerWindow(MainShell main) {
		super(main);
	}

	@Override
	protected String getTitle() {
		return ip_seeker_title;
	}

	@Override
	protected Image getImage() {
		return res.getImage(Resources.icoSearchIp);
	}

	@Override
	protected IQQListener getQQListener() {
		return null;
	}
	
	@Override
	protected void initializeVariables() {
		super.initializeVariables();
		seeker = IPSeeker.getInstance();
	}

	@Override
	protected Point getInitialSize() {
		return new Point(550, 400);
	}
	
	@Override
	protected Control createContents(Composite arg0) {
		Composite control = getContentContainer();
		
        Composite container = new Composite(control, SWT.NONE);
        container.setBackground(Colors.WHITE);
        container.setLayoutData(new GridData(GridData.FILL_BOTH));
        GridLayout layout = new GridLayout(4, false);   
        container.setLayout(layout);
        container.addPaintListener(new PaintListener() {
        	public void paintControl(PaintEvent e) {
        		Composite c = (Composite)e.getSource();
        		Rectangle rect = c.getClientArea();
        		rect.width--;
        		rect.height--;
        		e.gc.setForeground(Colors.MAINSHELL_BORDER_OUTMOST);
        		e.gc.drawRectangle(rect);
        	}
        });
        container.addPaintListener(new AroundBorderPaintListener(new Class[] { Text.class }, Colors.PAGE_CONTROL_BORDER));
        
        UITool.setDefaultBackground(Colors.WHITE);
        
        // 提示
        UITool.createLabel(container, ip_seeker_label_input);
        // 文本框
        GridData gd = new GridData(GridData.FILL_HORIZONTAL);
        gd.heightHint = 16;
        textInput = UITool.createSingleText(container, gd);
        // IP -> 地点
        btnIp2Addr = UITool.createSlat(container, ip_seeker_button_ip2addr);
        btnIp2Addr.setToolTipText(ip_seeker_tooltip_ip2addr);
        btnIp2Addr.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseUp(MouseEvent arg0) {
        		ip2Address();
        	}
        });
        // 地点 -> IP
        btnAddr2Ip = UITool.createSlat(container, ip_seeker_button_addr2ip);
        btnAddr2Ip.setToolTipText(ip_seeker_tooltip_addr2ip);
        btnAddr2Ip.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseUp(MouseEvent arg0) {
                btnAddr2Ip.setEnabled(false);
		        address2Ip();
        	}
        });        
        // 结果文本框
        gd = new GridData(GridData.FILL_BOTH);
        gd.horizontalSpan = 4;
        textResult = UITool.createMultiText(container, gd, SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL);

		return control;
	}
	
    /**
     * 从IP查询地点
     */
    protected void ip2Address() {
        textResult.setText("");
        String ip = textInput.getText().trim();
        StringBuffer sb = new StringBuffer();
        sb.append("IP:  ");
        sb.append(ip);
        sb.append(System.getProperty("line.separator"));
        sb.append(ip_seeker_text_address);
        sb.append(seeker.getCountry(ip));
        sb.append(' ');
        sb.append(seeker.getArea(ip));
        textResult.setText(sb.toString());
    }
    
    /**
     * 从地点查询IP
     */
    protected void address2Ip() {
        textResult.setText("");
        String s = textInput.getText().trim();
        if("".equals(s)) {
            textResult.append(ip_seeker_please_input);
            btnAddr2Ip.setEnabled(true);
            return;
        }
        String beginIp = ip_seeker_text_begin_ip;
        String endIp = ip_seeker_text_end_ip;
        String addr = ip_seeker_text_address;
        StringBuilder sb = new StringBuilder();
        List<IPEntry> ret = seeker.getIPEntries(s);
		for(IPEntry entry : ret) {
            sb.append(beginIp);
            sb.append(entry.beginIp);
            sb.append(' ');
            sb.append(endIp);
            sb.append(entry.endIp);
            sb.append(' ');
            sb.append(addr);
            sb.append(entry.country);
            sb.append(' ');
            sb.append(entry.area);
            sb.append(System.getProperty("line.separator"));
            textResult.append(sb.toString());
            sb.delete(0, sb.length());
        }
        btnAddr2Ip.setEnabled(true);
    }
}
