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
package edu.tsinghua.lumaqq.test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import edu.tsinghua.lumaqq.qq.Util;
import edu.tsinghua.lumaqq.resource.Resources;
import edu.tsinghua.lumaqq.ui.muf.MUFContainer;
import edu.tsinghua.lumaqq.ui.muf.MUFModel;
import edu.tsinghua.lumaqq.ui.muf.MUFModule;
import edu.tsinghua.lumaqq.widgets.qstyle.DieAway;

/**
 * @author luma
 */
@SuppressWarnings("unused")
public class DieAwayTest {
    private Shell shell;
    private Display display;
    private Image image;
    private DieAway die;
    
    public static void main(String[] args) throws IOException {
        DieAwayTest t = new DieAwayTest();
        t.open();
    }
    
	/**
	 * 打开对话框
	 * @throws IOException 
	 */
	public void open() throws IOException	{		
		// event loop
	    shell = new Shell(new Display(), SWT.NO_TRIM | SWT.NO_BACKGROUND);
	    display = shell.getDisplay();
	    shell.setLayout(new FillLayout());
	    shell.setSize(400, 300);
	    shell.setBackground(display.getSystemColor(SWT.COLOR_WHITE));
	    
	    MUFContainer container = new MUFContainer(shell);
	    
	    MUFModule module = new MUFModule1(new MUFModel(), "test");
	    ImageLoader loader = new ImageLoader();
	    loader.load("src/edu/tsinghua/lumaqq/resource/image/QQGG.png");
	    module.setImage(new Image(shell.getDisplay(), loader.data[0]));
	    container.add(module);
	    
//	    module = new MUFModule(new MUFModel(), "testmm");
//	    loader = new ImageLoader();
//	    loader.load("src/edu/tsinghua/lumaqq/resource/image/QQMM.png");
//	    module.setImage(new Image(shell.getDisplay(), loader.data[0]));
//	    container.add(module);
	    
	    shell.layout();
	    shell.pack();
	    shell.open();
		while(!shell.isDisposed()) 
			if(!display.readAndDispatch()) 
			    display.sleep();
	}
}
