package edu.tsinghua.lumaqq.ui;

import static edu.tsinghua.lumaqq.resource.Messages.*;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;

import edu.tsinghua.lumaqq.qq.events.IQQListener;
import edu.tsinghua.lumaqq.record.RecordEntry;
import edu.tsinghua.lumaqq.resource.Colors;
import edu.tsinghua.lumaqq.resource.Resources;
import edu.tsinghua.lumaqq.ui.helper.UITool;
import edu.tsinghua.lumaqq.ui.listener.AroundBorderPaintListener;
import edu.tsinghua.lumaqq.widgets.record.RecordViewer;
import edu.tsinghua.lumaqq.widgets.record.SystemRecordProvider;

/**
 * 系统消息记录列表窗口
 *
 * @author luma
 */
public class SystemMessageListWindow extends BaseWindow {
	// 界面控件
	private RecordViewer viewer;
	
	public SystemMessageListWindow(MainShell main) {
		super(main);
	}

	@Override
	protected String getTitle() {
		return sys_msg_list_title;
	}

	@Override
	protected Image getImage() {
		return res.getImage(Resources.icoLumaQQ);
	}

	@Override
	protected IQQListener getQQListener() {
		return null;
	}
	
	@Override
	public void shellClosed(ShellEvent e) {
		super.shellClosed(e);
		main.getShellRegistry().deregisterSystemMessageListWindow();
	}

	@Override
	protected Control createContents(Composite parent) {
		Composite control = getContentContainer();
		
        Composite container = new Composite(control, SWT.NONE);
        container.setBackground(Colors.WHITE);
        container.setLayoutData(new GridData(GridData.FILL_BOTH));
        GridLayout layout = new GridLayout();        
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
        
		viewer = new RecordViewer(container, new SystemRecordProvider(), main);
		viewer.setFont(res.getDefaultFont());
		viewer.setRecordManager(main.getRecordManager());
		viewer.setLayoutData(new GridData(GridData.FILL_BOTH));
		viewer.setBackground(Colors.LOGIN_BACKGROUND);
		// tableitem双击事件监听器
		viewer.addSelectionListener(
			new SelectionAdapter() {
				@Override
				public void widgetDefaultSelected(SelectionEvent e) {
					RecordEntry entry = viewer.getSelectedMessageEntry();
					if(entry == null)
						return;
					main.getShellLauncher().openReceiveSystemMessageShell(entry);
				}
			}
		);
        
        return control;
	}
	
	@Override
	public int open() {
		int ret = super.open();
		viewer.refreshRecord();
		return ret;
	}
	
	@Override
	protected Point getInitialSize() {
		return new Point(400, 250);
	}
}
