package edu.tsinghua.lumaqq.ui.muf;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;

/**
 * 界面模块
 *
 * @author luma
 */
public class MUFModule {
	protected static final int MARGIN = 7;
	protected static final int IMAGE_SIZE = 64;
	
	// attribute
	protected String id;
	protected Image image;
	protected String title;
	protected boolean selected;
	
	// mouse
	private boolean mouseDown;
	private int mouseX;
	private int mouseY;
	private MouseListener focusML = new MouseAdapter() {
		@Override
		public void mouseDown(MouseEvent e) {
			select();
		}
	};
	
	// layout
	protected Composite self;
	protected Composite content;
	protected MUFContainer container;
	private Label lblImage;
	private Label lblTitle;
	
	// model
	protected MUFModel model;
	
	public MUFModule(MUFModel model, String id) {
		// init members
		this.model = model;
		setId(id);
		title = "Untitled";
		selected = false;
		
		// get sub class a chance to init themselves
		initialize();
	}
	
	protected void initialize() {
	}

	public void create(MUFContainer container) {
		// save container
		this.container = container;
		
		// create self
		self = new Composite(container.getContent(), SWT.NONE);
		self.setLayoutData(new GridData(GridData.FILL_HORIZONTAL | GridData.HORIZONTAL_ALIGN_BEGINNING));
		
		// create layout
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		layout.marginHeight = layout.marginWidth = 7;
		layout.verticalSpacing = 3;
		self.setLayout(layout);
		
		// create image label
		GridData data = new GridData(GridData.VERTICAL_ALIGN_BEGINNING);
		data.verticalSpan = 3;
		data.widthHint = image == null ? IMAGE_SIZE : image.getBounds().width;
		data.heightHint = image == null ? IMAGE_SIZE : image.getBounds().height;
		lblImage = new Label(self, SWT.CENTER);
		lblImage.setLayoutData(data);
		lblImage.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent e) {
				paintImage(e);
			}
		});
		
		// create title label
		lblTitle = new Label(self, SWT.LEFT);
		lblTitle.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		lblTitle.setText(getTitle());
		
		// create separator
		data = new GridData(GridData.FILL_HORIZONTAL);
		data.heightHint = 4;
		Label lblSep = new Label(self, SWT.SEPARATOR | SWT.HORIZONTAL);
		lblSep.setLayoutData(data);
		
		// create content
		content = new Composite(self, SWT.NONE);
		content.setLayoutData(new GridData(GridData.FILL_BOTH));
		content.setLayout(new GridLayout());
		
		// create custom
		createContent(content);
		
		// add listener
		self.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent e) {
				paintSelf(e);
			}
		});
		lblImage.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				mouseDown = true;
				mouseX = e.x;
				mouseY = e.y;
			}
			
			@Override
			public void mouseUp(MouseEvent e) {
				mouseDown = false;
			}
		});
		lblImage.addMouseMoveListener(new MouseMoveListener() {
			public void mouseMove(MouseEvent e) {
				if(mouseDown) {
					// get delta
					int dx = e.x - mouseX;
					int dy = e.y - mouseY;
					
					// get old bound
					Rectangle bound = self.getParent().getParent().getBounds();
					
					// set new location
					self.getParent().getParent().setLocation(bound.x + dx, bound.y + dy);
				}
			}
		});
		addSelectionMouseListener(self);
	}
	
	private void addSelectionMouseListener(Control control) {
		control.addMouseListener(focusML);
		if(control instanceof Composite) {
			Control[] children = ((Composite)control).getChildren();
			for(Control c : children)
				addSelectionMouseListener(c);
		}
	}

	protected void paintImage(PaintEvent e) {
		if(image == null)
			return;
		
		Rectangle bound = image.getBounds();
		e.gc.drawImage(image, 0, 0, bound.width, bound.height, 0, 0, lblImage.getBounds().width, lblImage.getBounds().height);
	}

	public void createContent(Composite parent) {		
	}
	
	public void dispose() {
	}
	
	protected void paintSelf(PaintEvent e) {
		// get bound
		Composite comp = (Composite)e.getSource();
		Rectangle bound = comp.getClientArea();
		bound.width--;
		bound.height--;
		
		// draw border
		Color c = new Color(e.display, 0x80, 0x80, 0x80);
		e.gc.setForeground(c);
		e.gc.drawRectangle(bound);
		c.dispose();
		
		// draw selected border
		if(selected) {
			c = new Color(e.display, 0xA6, 0xCA, 0xF0);
			e.gc.setForeground(c);
			
			bound.x++;
			bound.y++;
			bound.width -= 2;
			bound.height -= 2;
			e.gc.drawRectangle(bound);
			bound.x++;
			bound.y++;
			bound.width -= 2;
			bound.height -= 2;
			e.gc.drawRectangle(bound);
			c.dispose();
		}
	}
	
	public Point getPreferredSize() {
		return self.computeSize(SWT.DEFAULT, SWT.DEFAULT);
	}
	
	public Image getImage() {
		return image;
	}
	
	public void setImage(Image img) {
		image = img;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}

	public void notifyChanged(Notification n) {
		switch(n.notificationId) {
			case Notification.N_SELECTION_CHANGE_TO:
				if(selected) {
					selected = false;
					self.redraw();
				}
				break;
		}
	}

	private void select() {
		if(!selected) {
			selected = true;
			
			// send notification
			Notification n = new Notification();
			n.module = this;
			n.notificationId = Notification.N_SELECTION_CHANGE_TO;
			container.dispatchNotification(n);
			
			// redraw
			self.redraw();
		}
	}
}
