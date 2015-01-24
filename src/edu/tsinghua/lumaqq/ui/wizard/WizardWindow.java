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
package edu.tsinghua.lumaqq.ui.wizard;

import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.widgets.Shell;

import edu.tsinghua.lumaqq.ui.MainShell;

/**
 * 在独立的窗口中显示一个wizard，jface提供的只能在一个对话框中显示，所以这里
 * 用了一些trick
 * 
 * @author luma
 */
public class WizardWindow extends WizardDialog {
    private MainShell main;
    private IModelBasedWizard wizard;
    
    /**
     * @param parentShell
     * @param newWizard
     */
    public WizardWindow(MainShell m, IModelBasedWizard newWizard) {
        super(m.getShell(), newWizard);         
        setBlockOnOpen(false);
        main = m;
        wizard = newWizard;
    }
    
    /**
     * 显示起始页
     */
    public void showStartingPage() {
    	showPage(wizard.getStartingPage());
    }
    
    @Override
    protected void nextPressed() {
    	wizard.preNext();
    	super.nextPressed();
    }
    
    /**
     * @return
     * 		model对象
     */
    public Object getModel() {
    	return ((IModelBasedWizard)getWizard()).getModel();
    }
    
    /* (non-Javadoc)
     * @see org.eclipse.jface.window.Window#getShellStyle()
     */
    @Override
	protected int getShellStyle() {
        return SWT.SHELL_TRIM;
    }
    
    /* (non-Javadoc)
     * @see org.eclipse.jface.window.Window#getParentShell()
     */
    @Override
	protected Shell getParentShell() {
        // 我们这里返回一个null，使SWT用当前display创建一个窗口，使用null值
        // 不被SWT推荐，但是没有更简单的办法了
        return null;
    }
    
    /* (non-Javadoc)
     * @see org.eclipse.jface.wizard.WizardDialog#configureShell(org.eclipse.swt.widgets.Shell)
     */
    @Override
	protected void configureShell(final Shell newShell) {
    	super.configureShell(newShell);
        main.getShell().addShellListener(new ShellAdapter() {
            @Override
			public void shellClosed(ShellEvent e) {
                if(newShell != null && !newShell.isDisposed())
                    newShell.close();
            }
        });
    }

    /**
     * @param b
     */
    public void setMinimized(boolean b) {
        getShell().setMaximized(b);
    }

    /**
     * 激活窗口
     */
    public void setActive() {
        getShell().setActive();
    }
}
