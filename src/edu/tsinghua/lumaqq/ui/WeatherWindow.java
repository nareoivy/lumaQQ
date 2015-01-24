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

import edu.tsinghua.lumaqq.events.BaseQQListener;
import edu.tsinghua.lumaqq.qq.QQ;
import edu.tsinghua.lumaqq.qq.Util;
import edu.tsinghua.lumaqq.qq.beans.Weather;
import edu.tsinghua.lumaqq.qq.events.IQQListener;
import edu.tsinghua.lumaqq.qq.events.QQEvent;
import edu.tsinghua.lumaqq.qq.packets.Packet;
import edu.tsinghua.lumaqq.qq.packets.in.WeatherOpReplyPacket;
import edu.tsinghua.lumaqq.qq.packets.out.WeatherOpPacket;
import edu.tsinghua.lumaqq.resource.Colors;
import edu.tsinghua.lumaqq.resource.Resources;
import edu.tsinghua.lumaqq.ui.helper.UITool;
import edu.tsinghua.lumaqq.ui.listener.AroundBorderPaintListener;
import edu.tsinghua.lumaqq.widgets.qstyle.Slat;

import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;

/**
 * 天气预报窗口
 * 
 * @author luma
 */
public class WeatherWindow extends BaseWindow {
	private Text textResult;
	private Slat btnQuery;
	private Text textIp;
	private char expect;
	private boolean querying;
	
	private BaseQQListener qqListener = new BaseQQListener() {
		@Override
		protected void OnQQEvent(QQEvent e) {
			switch(e.type) {
				case QQEvent.USER_WEATHER_GET_OK:
					processGetWeatherSuccess(e);
					break;
				case QQEvent.USER_WEATHER_GET_FAIL:
					processGetWeatherFail(e);
					break;
				case QQEvent.SYS_TIMEOUT:
					if(e.operation == QQ.QQ_CMD_WEATHER_OP) {
						WeatherOpPacket packet = (WeatherOpPacket)e.getSource();
						if(packet.getSubCommand() == QQ.QQ_SUB_CMD_GET_WEATHER)
							processGetWeatherFail(e);
					}
					break;
			}
		}
	};

	public WeatherWindow(MainShell main) {
		super(main);
	}

	@Override
	protected String getTitle() {
		return weather_title;
	}

	@Override
	protected Image getImage() {
		return res.getImage(Resources.icoLumaQQ);
	}
	
	@Override
	protected IQQListener getQQListener() {
		return qqListener;
	}
	
	@Override
	protected void initializeVariables() {
		super.initializeVariables();
		expect = 0;
		querying = false;
	}

	@Override
	public void shellClosed(ShellEvent e) {
		super.shellClosed(e);
		main.getShellRegistry().deregisterWeatherWindow();
	}
	
	@Override
	protected Point getInitialSize() {
		return new Point(400, 350);
	}
	
	@Override
	protected void onOpen() {
		textIp.setText(Util.getIpStringFromBytes(main.getClient().getUser().getIp()));
		onQuery();
	}
	
	@Override
	protected Control createContents(Composite parent) {
		Composite control = getContentContainer();
		
        Composite container = new Composite(control, SWT.NONE);
        container.setBackground(Colors.WHITE);
        container.setLayoutData(new GridData(GridData.FILL_BOTH));
        GridLayout layout = new GridLayout(3, false);        
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
        
        // 输入IP
        UITool.createLabel(container, weather_ip);
        GridData gd = new GridData(GridData.FILL_HORIZONTAL);
        gd.heightHint = 16;
        textIp = UITool.createSingleText(container, gd);
        gd = new GridData();
        gd.widthHint = 70;
		btnQuery = UITool.createSlat(container, weather_query, gd);
        btnQuery.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseUp(MouseEvent e) {
        		onQuery();
        	}
        });
        
		// 结果
        gd = new GridData(GridData.FILL_BOTH);
        gd.horizontalSpan = 3;
        textResult = UITool.createMultiText(container, gd, SWT.MULTI | SWT.WRAP | SWT.READ_ONLY | SWT.V_SCROLL);
		return control;
	}
	
	/**
	 * 查询天气
	 */
	protected void onQuery() {		
		if(querying) {
			querying = false;
			btnQuery.setText(weather_query);
			expect = 0;
		} else {
			querying = true;
			btnQuery.setText(weather_stop);
			textResult.setText("");
			byte[] ip = Util.getIpByteArrayFromString(textIp.getText().trim());
			expect = main.getClient().weather_Get(ip);			
		}
	}

	/**
	 * 添加天气信息
	 * 
	 * @param weather
	 */
	private void appendWeather(Weather weather) {
		textResult.append(String.valueOf(weather.year));
		textResult.append("-");
		textResult.append(String.valueOf(weather.month));
		textResult.append("-");
		textResult.append(String.valueOf(weather.day));
		textResult.append(System.getProperty("line.separator"));
		textResult.append(weather.shortDesc);
		textResult.append(System.getProperty("line.separator"));
		textResult.append(NLS.bind(weather_temperature, String.valueOf(weather.lowTemperature), String.valueOf(weather.highTemperature)));
		textResult.append(System.getProperty("line.separator"));
		textResult.append(NLS.bind(weather_wind, weather.wind));
		textResult.append(System.getProperty("line.separator"));
		textResult.append(System.getProperty("line.separator"));
		textResult.append("--------------------------------");
		textResult.append(System.getProperty("line.separator"));
	}

	/**
	 * 处理请求天气信息失败或超时
	 * 
	 * @param e
	 */
	private void processGetWeatherFail(QQEvent e) {
		Packet p = (Packet)e.getSource();
		if(p.getSequence() == expect) {
			textResult.setText(weather_fail);
			btnQuery.setText(weather_query);
			querying = false;		
		}
	}

	/**
	 * 处理请求天气信息成功
	 * 
	 * @param e
	 */
	private void processGetWeatherSuccess(QQEvent e) {
		WeatherOpReplyPacket packet = (WeatherOpReplyPacket)e.getSource();
		if(packet.getSequence() == expect) {
			textResult.setText("");
			if(packet.province != null)
				textResult.append(packet.province);
			textResult.append("-");
			if(packet.city != null)
				textResult.append(packet.city);
			textResult.append(System.getProperty("line.separator"));
			textResult.append("--------------------------------");
			textResult.append(System.getProperty("line.separator"));
			for(Weather w : packet.weathers) {
				appendWeather(w);
			}
			btnQuery.setText(weather_query);
			querying = false;	
		}
	}
}
