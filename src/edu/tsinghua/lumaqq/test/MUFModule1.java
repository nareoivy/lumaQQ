package edu.tsinghua.lumaqq.test;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

import edu.tsinghua.lumaqq.ui.muf.MUFModel;
import edu.tsinghua.lumaqq.ui.muf.MUFModule;

public class MUFModule1 extends MUFModule {
	public static int i = 0;
	public MUFModule1(MUFModel model, String id) {
		super(model, id);
	}
	
	@Override
	public void createContent(Composite parent) {
		Button btn = new Button(parent, SWT.PUSH);
		GridData gd = new GridData();
		gd.widthHint = 200;
		btn.setLayoutData(gd);
		btn.setText("Add a new module");
		btn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			    MUFModule module = new MUFModule2(new MUFModel(), "testmm" + i++);
			    ImageLoader loader = new ImageLoader();
			    loader.load("src/edu/tsinghua/lumaqq/resource/image/QQMM.png");
			    module.setImage(new Image(Display.getCurrent(), loader.data[0]));
			    container.add(module);
			}
		});
	}
}
