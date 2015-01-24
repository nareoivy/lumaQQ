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
package edu.tsinghua.lumaqq.ui.helper;

import static edu.tsinghua.lumaqq.resource.Messages.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import edu.tsinghua.lumaqq.ui.MainShell;

import org.eclipse.jface.dialogs.MessageDialog;

/**
 * 检测更新的线程
 * 
 * @author luma
 */
public class CheckUpdateThread extends Thread {	
	private MainShell main;
	
	public CheckUpdateThread(MainShell m) {
		main = m;
		setName("Check Update");
		setDaemon(true);
	}
	
	@Override
	public void run() {
		boolean latest = true;
		
        // 得到当前版本时间，包含了日月年小时分
        String timeTag = update_time;
        BufferedReader br = null;
        try {
            // 打开输入流，读取服务器上的文件
            URL url = new URL(url_update);
            br = new BufferedReader(new InputStreamReader(url.openStream(), "GBK"));
            // 读取最新版本修改时间
            String latestTag = br.readLine();
            // 比较
            latest = timeTag.compareTo(latestTag) >= 0;  
        } catch (Exception e) {  
        	latest = true;
        } finally {
            try {
                if(br != null)
                    br.close();
            } catch (IOException e1) {                
            }
        }
        
        if(!latest) {
        	main.getDisplay().asyncExec(new Runnable() {
        		public void run() {
        			MessageDialog.openInformation(main.getShell(), message_box_common_info_title, check_update_lable_old);
        		}
        	});
        }
	}
}
