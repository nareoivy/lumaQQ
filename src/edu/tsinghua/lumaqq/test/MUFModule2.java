package edu.tsinghua.lumaqq.test;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

import edu.tsinghua.lumaqq.ui.muf.MUFModel;
import edu.tsinghua.lumaqq.ui.muf.MUFModule;

public class MUFModule2 extends MUFModule {
	public MUFModule2(MUFModel model, String id) {
		super(model, id);
	}
	
	@Override
	public void createContent(Composite parent) {
		Button btn = new Button(parent, SWT.PUSH);
		GridData gd = new GridData();
		gd.widthHint = 500;
		btn.setLayoutData(gd);
		btn.setText("Remove Module");
		btn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				container.remove(getThis());
			}
		});
	}
	
	public MUFModule getThis() {
		return this;
	}
}
