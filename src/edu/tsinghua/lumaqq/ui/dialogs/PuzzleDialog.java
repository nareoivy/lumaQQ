package edu.tsinghua.lumaqq.ui.dialogs;

import static edu.tsinghua.lumaqq.resource.Messages.*;

import java.io.ByteArrayInputStream;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import edu.tsinghua.lumaqq.ui.MainShell;

public class PuzzleDialog extends Dialog {	
	private String verifyCode;
	private byte[] puzzleData;
	private byte[] puzzleToken;
	private Image puzzleImage;
	private MainShell main;
	private Label lblVerifyCode;
	
	public PuzzleDialog(MainShell main, byte[] puzzleData, byte[] puzzleToken) {
		super(main.getShell());
		this.main = main;
		this.puzzleData = puzzleData;
		this.puzzleToken = puzzleToken;
		puzzleImage = null;
	}

	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText(verify_title);
		newShell.addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				if(puzzleImage != null && !puzzleImage.isDisposed())
					puzzleImage.dispose();
			}
		});
	}
	
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite control = (Composite)super.createDialogArea(parent);
		
		GridLayout layout = new GridLayout();
		layout.numColumns = 3;
		control.setLayout(layout);
		
		Label lblTemp = new Label(control, SWT.RIGHT);
		lblTemp.setText(verify_code_image);
		lblTemp.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL));
		
		// create image
		try {
			ImageLoader loader = new ImageLoader();
			loader.load(new ByteArrayInputStream(puzzleData));
			puzzleImage = new Image(parent.getDisplay(), loader.data[0]);
		} catch (RuntimeException e1) {
			puzzleImage = null;
		}
		
		lblVerifyCode = new Label(control, SWT.CENTER);
		GridData gd = new GridData();
		if(puzzleImage != null) {
			gd.widthHint = puzzleImage.getBounds().width;
			gd.heightHint = puzzleImage.getBounds().height;			
		}
		lblVerifyCode.setLayoutData(gd);
		lblVerifyCode.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent e) {
				if(puzzleImage != null)
					e.gc.drawImage(puzzleImage, 0, 0);
			}
		});
		
		Link lnkRefresh = new Link(control, SWT.NONE);
		lnkRefresh.setText("<a>" + verify_refresh + "</a>");
		lnkRefresh.setLayoutData(new GridData());
		lnkRefresh.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				main.getClient().login_RefreshPuzzle(puzzleToken);
			}
		});
		
		lblTemp = new Label(control, SWT.RIGHT);
		lblTemp.setText(verify_input);
		lblTemp.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL));
		
		Text txtVerifyCode = new Text(control, SWT.SINGLE | SWT.BORDER);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.horizontalSpan = 2;
		txtVerifyCode.setLayoutData(gd);
		txtVerifyCode.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				verifyCode = ((Text)e.getSource()).getText();
			}
		});
		
		return control;
	}
	
	@Override
	protected int getShellStyle() {
		return SWT.DIALOG_TRIM;
	}
	
	@Override
	protected void okPressed() {
		super.okPressed();
	}

	public String getVerifyCode() {
		return verifyCode;
	}
	
	public void refreshPuzzleImage(byte[] token, byte[] puzzleData) {
		this.puzzleData = puzzleData;
		this.puzzleToken = token;
		
		// release image
		if(puzzleImage != null && !puzzleImage.isDisposed())
			puzzleImage.dispose();
		
		// create image
		try {
			ImageLoader loader = new ImageLoader();
			loader.load(new ByteArrayInputStream(puzzleData));
			puzzleImage = new Image(getShell().getDisplay(), loader.data[0]);
		} catch (RuntimeException e1) {
			puzzleImage = null;
		}
		
		// relayout and redraw
		if(puzzleImage != null) {
			GridData gd = (GridData)lblVerifyCode.getLayoutData();
			gd.widthHint = puzzleImage.getBounds().width;
			gd.heightHint = puzzleImage.getBounds().height;
			lblVerifyCode.setLayoutData(gd);
			lblVerifyCode.getParent().layout();
			
			lblVerifyCode.redraw();
		}
	}

	public byte[] getPuzzleToken() {
		return puzzleToken;
	}
}
