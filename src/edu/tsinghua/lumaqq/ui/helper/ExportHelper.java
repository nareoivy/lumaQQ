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

import static edu.tsinghua.lumaqq.resource.Messages.dir_dialog_common_title;
import static edu.tsinghua.lumaqq.resource.Messages.dir_dialog_export_message;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.FileDialog;

import edu.tsinghua.lumaqq.models.Cluster;
import edu.tsinghua.lumaqq.models.User;
import edu.tsinghua.lumaqq.record.IKeyConstants;
import edu.tsinghua.lumaqq.record.RecordEntry;
import edu.tsinghua.lumaqq.template.RecordExporterFactory;
import edu.tsinghua.lumaqq.ui.MainShell;
import edu.tsinghua.lumaqq.ui.dialogs.RecordExportTypeDialog;
import edu.tsinghua.lumaqq.ui.jobs.DialogJobExecutor;
import edu.tsinghua.lumaqq.ui.jobs.ExportAllRecordJob;
import edu.tsinghua.lumaqq.ui.jobs.IExecutor;
import edu.tsinghua.lumaqq.widgets.record.IRecordExporter;
import edu.tsinghua.lumaqq.widgets.record.IRecordExporterFactory;

/**
 * 记录导出帮助类
 * 
 * @author luma
 */
public class ExportHelper {
    private static Log log = LogFactory.getLog(ExportHelper.class);
    
    private IRecordExporterFactory factory;
    private Map<String, Object> arg;
    private MainShell main;
    
    public ExportHelper(MainShell main) {
        this.main = main;
        factory = new RecordExporterFactory();
        arg = new HashMap<String, Object>();
    }

    /**
     * 导出实际内容到文件中
     * 
     * @param exporter
     * 		导出器
     * @param argument
     * 		参数
     * @param filename
     * 		文件名
     */
    private void exportToFile(IRecordExporter exporter, Object argument, String filename) {
		// 如果filename不等于null则导出
		if(filename != null) {
			BufferedWriter writer = null;
			try {
				// 创建文本文件
				File file = new File(filename);
				if(!file.exists())
					file.createNewFile();

				writer = new BufferedWriter(new FileWriter(file));
				writer.write(exporter.generate(argument));
				writer.flush();
			} catch (IOException ex) {
				log.error("导出聊天记录失败");
			} finally {
				try {
					if(writer != null)
						writer.close();
				} catch (IOException e1) {
				    log.error(e1.getMessage());
				}
			}
		}
    }
    
    /**
     * 导出聊天记录
     * 
     * @param arg
     * 		参数
     * @param defaultFileName
     * 		缺省文件名
     */
    public void exportMessage(Map<String, Object> arg, String defaultFileName) {
    	// 创建导出文件类型对话框
    	RecordExportTypeDialog dialog = new RecordExportTypeDialog(main.getShell(), factory);
    	if(dialog.open() == IDialogConstants.CANCEL_ID)
    		return;

		String fileTypeName = dialog.getFileTypeName();
		IRecordExporter exporter = factory.getExporter(fileTypeName);
		if(exporter == null)
			return;
		
		// 创建文件对话框，设置参数，打开
		FileDialog fd = new FileDialog(main.getShell(), SWT.SAVE);
		fd.setFilterExtensions(new String[] { '*' + factory.getFilterExtension(fileTypeName), "*.*" } );
		fd.setFilterNames(new String[] { factory.getFilterName(fileTypeName), "All Files" });
		fd.setFileName(defaultFileName + factory.getFilterExtension(fileTypeName));
		String filename = fd.open();
		if(filename == null)
			return;
		
		exportToFile(exporter, arg, filename);
    }
    
    /**
     * 导出聊天记录
     * 
     * @param fileTypeName
     * 		导出类型名，如果为null，使用缺省值
     * @param arg
     * 		参数
     * @param defaultFileName
     * 		缺省文件名
     * @param showFileDialog
     * 		true表示显示文件名对话框
     */
    public void exportMessage(String fileTypeName, Map<String, Object> arg, String defaultFileName, boolean showFileDialog) {
    	if(fileTypeName == null)
    		fileTypeName = factory.getDefaultExporterName();    	
		IRecordExporter exporter = factory.getExporter(fileTypeName);
		if(exporter == null)
			return;
		
		// 创建文件对话框，设置参数，打开
		String filename = defaultFileName;
		if(showFileDialog) {
			FileDialog fd = new FileDialog(main.getShell(), SWT.SAVE);
			fd.setFilterExtensions(new String[] { '*' + factory.getFilterExtension(fileTypeName), "*.*" } );
			fd.setFilterNames(new String[] { factory.getFilterName(fileTypeName), "All Files" });
			fd.setFileName(defaultFileName + factory.getFilterExtension(fileTypeName));
			filename = fd.open();
		}
		if(filename == null)
			return;			
		
		exportToFile(exporter, arg, filename);	
    }
    
	/**
     * 导出所有的聊天记录，每个好友，每个群分别生成一个聊天记录文件
     */
    public void exportAllMessage() {
    	// 创建导出文件类型对话框
    	RecordExportTypeDialog d = new RecordExportTypeDialog(main.getShell(), factory);
    	if(d.open() == IDialogConstants.CANCEL_ID)
    		return;
		String fileTypeName = d.getFileTypeName();
		
        // 选择目录
		DirectoryDialog dialog = new DirectoryDialog(main.getShell(), SWT.OPEN);		
		dialog.setMessage(dir_dialog_export_message);
		dialog.setText(dir_dialog_common_title);
		String dir = dialog.open();
		// 导出
		if(dir != null) {
		    // 给目录名后面添加分隔符
		    if(!dir.endsWith(File.separator))
		        dir += File.separator;
		    
		    IExecutor executor = new DialogJobExecutor(main);
		    executor.addJob(new ExportAllRecordJob(dir, fileTypeName, factory));
		    executor.execute();
		}
    }
    
    /**
     * 导出短信记录
     * 
     * @param mobile
     */
    public void exportMessage(String mobile) {
    	exportMessage(mobile, null);
    }
    
    /**
     * 导出好友聊天记录
     * 
     * @param f
     */
    public void exportMessage(User f) {
    	exportMessage(f, null);
    }
    
    /**
     * 导出群聊天记录
     * 
     * @param c
     * 		群对象
     */
    public void exportMessage(Cluster c) {
    	exportMessage(c, null);
    }
    
    /**
     * 导出群聊天记录
     * 
     * @param c
     * @param entries
     */
    public void exportMessage(Cluster c, List<RecordEntry> entries) {
    	// 创建导出文件类型对话框
    	RecordExportTypeDialog dialog = new RecordExportTypeDialog(main.getShell(), factory);
    	if(dialog.open() == IDialogConstants.CANCEL_ID)
    		return;
		String fileTypeName = dialog.getFileTypeName();
		
		// 创建文件对话框，设置参数，打开
		FileDialog fd = new FileDialog(main.getShell(), SWT.SAVE);
		fd.setFilterExtensions(new String[] { '*' + factory.getFilterExtension(fileTypeName), "*.*" } );
		fd.setFilterNames(new String[] { factory.getFilterName(fileTypeName), "All Files" });
		fd.setFileName('(' + String.valueOf(c.externalId) + ')' + c.name + factory.getFilterExtension(fileTypeName));
		String filename = fd.open();
		if(filename == null)
			return;
		
		exportMessage(c, fileTypeName, filename, entries);
    }
    
    /**
     * 导出好友记录
     * 
     * @param f
     * @param entries
     */
    public void exportMessage(User f, List<RecordEntry> entries) {
    	// 创建导出文件类型对话框
    	RecordExportTypeDialog dialog = new RecordExportTypeDialog(main.getShell(), factory);
    	if(dialog.open() == IDialogConstants.CANCEL_ID)
    		return;
		String fileTypeName = dialog.getFileTypeName();
		
		// 创建文件对话框，设置参数，打开
		FileDialog fd = new FileDialog(main.getShell(), SWT.SAVE);
		fd.setFilterExtensions(new String[] { '*' + factory.getFilterExtension(fileTypeName), "*.*" } );
		fd.setFilterNames(new String[] { factory.getFilterName(fileTypeName), "All Files" });
		fd.setFileName(String.valueOf(f.qq) + factory.getFilterExtension(fileTypeName));
		String filename = fd.open();
		if(filename == null)
			return;
		
		exportMessage(f, fileTypeName, filename, entries);
    }
    
    /**
     * 导出短信记录
     * 
     * @param mobile
     * @param entries
     */
    public void exportMessage(String mobile, List<RecordEntry> entries) {
    	// 创建导出文件类型对话框
    	RecordExportTypeDialog dialog = new RecordExportTypeDialog(main.getShell(), factory);
    	if(dialog.open() == IDialogConstants.CANCEL_ID)
    		return;
		String fileTypeName = dialog.getFileTypeName();
		
		// 创建文件对话框，设置参数，打开
		FileDialog fd = new FileDialog(main.getShell(), SWT.SAVE);
		fd.setFilterExtensions(new String[] { '*' + factory.getFilterExtension(fileTypeName), "*.*" } );
		fd.setFilterNames(new String[] { factory.getFilterName(fileTypeName), "All Files" });
		fd.setFileName(mobile + factory.getFilterExtension(fileTypeName));
		String filename = fd.open();
		if(filename == null)
			return;
		
		exportMessage(mobile, fileTypeName, filename, entries);
    }
    
    /**
     * 导出系统消息
     */
    public void exportSystemMessage() {
    	exportSystemMessage(null);
    }
    
    /**
     * 导出系统消息
     * 
     * @param entries
     */
    public void exportSystemMessage(List<RecordEntry> entries) {
    	// 创建导出文件类型对话框
    	RecordExportTypeDialog dialog = new RecordExportTypeDialog(main.getShell(), factory);
    	if(dialog.open() == IDialogConstants.CANCEL_ID)
    		return;
		String fileTypeName = dialog.getFileTypeName();
		
		// 创建文件对话框，设置参数，打开
		FileDialog fd = new FileDialog(main.getShell(), SWT.SAVE);
		fd.setFilterExtensions(new String[] { '*' + factory.getFilterExtension(fileTypeName), "*.*" } );
		fd.setFilterNames(new String[] { factory.getFilterName(fileTypeName), "All Files" });
		fd.setFileName("system" + factory.getFilterExtension(fileTypeName));
		String filename = fd.open();
		if(filename == null)
			return;
		
		exportSystemMessge(fileTypeName, filename, entries);
    }
    
    /**
     * 导出短信记录
     * 
     * @param mobile
     * 		手机号
     * @param fileTypeName
     * 		导出类型
     * @param filename
     * 		文件名
     */
    public void exportMessage(String mobile, String fileTypeName, String filename, List<RecordEntry> entries) {
		arg.clear();
		arg.put(IRecordExporter.EXPORT_TYPE, IRecordExporter.EXPORT_SMS);
		arg.put(IRecordExporter.MOBILE_NUMBER, mobile);
		arg.put(IRecordExporter.MY_MODEL, main.getMyModel());
		if(entries == null) {
			List<RecordEntry> temp = main.getRecordManager().getRecord(9999, IKeyConstants.ALL, IKeyConstants.SUB_ALL);
			entries = new ArrayList<RecordEntry>();
			for(RecordEntry entry : temp) {
				if(entry.message.startsWith(mobile))
					entries.add(entry);
			}			
		}
		arg.put(IRecordExporter.RECORD_ENTRIES, entries);
		exportMessage(fileTypeName, arg, filename, false);
    }
    
    /**
     * 导出群聊天记录
     * 
     * @param c
     * 		群对象
     * @param fileTypeName
     * 		导出类型
     * @param filename
     * 		文件名
     */
    public void exportMessage(Cluster c, String fileTypeName, String filename, List<RecordEntry> entries) {
		arg.clear();
		arg.put(IRecordExporter.EXPORT_TYPE, IRecordExporter.EXPORT_CLUSTER);
		arg.put(IRecordExporter.CLUSTER_MODEL, c);
		arg.put(IRecordExporter.RECORD_ENTRIES, (entries == null) ? main.getRecordManager().getRecord(c.clusterId, IKeyConstants.ALL, IKeyConstants.SUB_ALL) : entries);
		exportMessage(fileTypeName, arg, filename, false);                		
    }
    
    /**
     * 导出好友聊天记录
     * 
     * @param f
     * 		好友对象
     * @param fileTypeName
     * 		文件类型名称
     * @param filename
     * 		文件名
     */
    public void exportMessage(User f, String fileTypeName, String filename, List<RecordEntry> entries) {
   		arg.clear();
		arg.put(IRecordExporter.EXPORT_TYPE, IRecordExporter.EXPORT_FRIEND);
		arg.put(IRecordExporter.MY_MODEL, main.getMyModel());
		arg.put(IRecordExporter.FRIEND_MODEL, f);
		arg.put(IRecordExporter.RECORD_ENTRIES, (entries == null) ? main.getRecordManager().getRecord(f.qq, IKeyConstants.ALL, IKeyConstants.SUB_ALL) : entries);
		exportMessage(fileTypeName, arg, filename, false);             		
    }
    
    /**
     * 导出系统消息
     * 
     * @param fileTypeName
     * @param filename
     * @param entries
     */
    public void exportSystemMessge(String fileTypeName, String filename, List<RecordEntry> entries) {
   		arg.clear();
		arg.put(IRecordExporter.EXPORT_TYPE, IRecordExporter.EXPORT_SYSTEM);
		arg.put(IRecordExporter.RECORD_ENTRIES, (entries == null) ? main.getRecordManager().getRecord(10000, IKeyConstants.ALL, IKeyConstants.SUB_ALL) : entries);
		exportMessage(fileTypeName, arg, filename, false);     
    }
}
