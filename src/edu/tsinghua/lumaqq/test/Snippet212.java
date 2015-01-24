package edu.tsinghua.lumaqq.test;

import org.eclipse.swt.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.custom.*;

import edu.tsinghua.lumaqq.widgets.qstyle.Slat;
import edu.tsinghua.lumaqq.widgets.rich2.Animator;
import edu.tsinghua.lumaqq.widgets.rich2.StyleObject;

/*
 * SWT StyledText snippet: embed images in a StyledText.
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 * 
 * @since 3.2
 */
public class Snippet212 {

	StyledText styledText;

	String text = "\u0014\u0014This snippet shows how to embed images in a StyledText.\n" + "Here is one:, and here is another: ." + "Use the add button\u0014 to add an image from your filesystem to the StyledText at the current caret offset.";

	int[] offsets;

	private StyleObject[] objects;

	void addObject(StyleObject so, int offset) {
		StyleRange style = new StyleRange();
		style.start = offset;
		style.length = 1;
		Rectangle rect = so.getBounds();
		style.metrics = new GlyphMetrics(rect.height, 0, rect.width);
		styledText.setStyleRange(style);
	}
	
	public static void main(String[] args) {
		Snippet212 t = new Snippet212();
		t.run();
	}

	public void run() {
		final Display display = new Display();
		final Shell shell = new Shell(display);
		shell.setLayout(new GridLayout());
		styledText = new StyledText(shell, SWT.WRAP | SWT.BORDER | SWT.V_SCROLL);
		styledText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		styledText.setText(text);
		
		Slat button = new Slat(styledText);
		button.setText("Add Image");
		button.setCursor(display.getSystemCursor(SWT.CURSOR_ARROW));
		button.setSize(button.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		
		button.addListener(SWT.MouseUp, new Listener() {
			public void handleEvent(Event event) {
				FileDialog dialog = new FileDialog(shell);
				String filename = dialog.open();
				if(filename != null) {
					try {
//						Image image = new Image(display, filename);
						
//						Slat image = new Slat(styledText);
//						image.setText("Button");
//						image.setCursor(display.getSystemCursor(SWT.CURSOR_ARROW));
//						image.setSize(image.computeSize(SWT.DEFAULT, SWT.DEFAULT));
						
				    	Animator image = new Animator(styledText);
				    	image.setSize(20, 20);
				    	
				    	ImageLoader loader = new ImageLoader();
				    	loader.load(filename);
				    	image.setLoader(loader);
				    	image.setBackground(shell.getBackground());	    
						
						int offset = styledText.getCaretOffset();
						styledText.replaceTextRange(offset, 0, "\u0014");
						int index = 0;
						while(index < offsets.length) {
							if(offsets[index] == -1 && objects[index] == null)
								break;
							index++;
						}
						if(index == offsets.length) {
							int[] tmpOffsets = new int[index + 1];
							System.arraycopy(offsets, 0, tmpOffsets, 0, offsets.length);
							offsets = tmpOffsets;
							StyleObject[] tempObjects = new StyleObject[index + 1];
							System.arraycopy(objects, 0, tempObjects, 0, objects.length);
							objects = tempObjects;
						}
						offsets[index] = offset;
						objects[index] = new StyleObject(StyleObject.CONTROL, image, true);
						addObject(objects[index], offset);
					} catch(Exception e) {
						e.printStackTrace();
					}
				}
			}
		});
		
		objects = new StyleObject[] {
			new StyleObject(StyleObject.IMAGE, display.getSystemImage(SWT.ICON_QUESTION), true),
			new StyleObject(StyleObject.IMAGE, display.getSystemImage(SWT.ICON_INFORMATION), true),
			new StyleObject(StyleObject.CONTROL, button, true)
		};
		offsets = new int[objects.length];
		int lastOffset = 0;
		for(int i = 0; i < objects.length; i++) {
			int offset = text.indexOf("\u0014", lastOffset);
			offsets[i] = offset;
			addObject(objects[i], offset);
			lastOffset = offset + 1;
		}

		// use a verify listener to keep the offsets up to date
		styledText.addVerifyListener(new VerifyListener() {
			public void verifyText(VerifyEvent e) {
				int start = e.start;
				int replaceCharCount = e.end - e.start;
				int newCharCount = e.text.length();
				for(int i = 0; i < offsets.length; i++) {
					int offset = offsets[i];
					if(start <= offset && offset < start + replaceCharCount) {
						// this image is being deleted from the text
						if(objects[i] != null && !objects[i].isDisposed()) {
							objects[i].dispose();
							objects[i] = null;
						}
						offset = -1;
					}
					if(offset != -1 && offset >= start)
						offset += newCharCount - replaceCharCount;
					offsets[i] = offset;
				}
			}
		});
		styledText.addPaintObjectListener(new PaintObjectListener() {
			public void paintObject(PaintObjectEvent event) {
				GC gc = event.gc;
				StyleRange style = event.style;
				int start = style.start;
				for(int i = 0; i < offsets.length; i++) {
					int offset = offsets[i];
					if(start == offset) {
						switch(objects[i].objectType) {
							case StyleObject.IMAGE:
								Image image = (Image)objects[i].object;
								int x = event.x;
								int y = event.y + event.ascent - style.metrics.ascent;
								gc.drawImage(image, x, y);
								System.out.println(y);
								break;
							case StyleObject.CONTROL:
								Control control = (Control)objects[i].object;
								y = event.y + event.ascent - objects[i].getBounds().height;
								control.setLocation(event.x, y);
								break;
						}
					}
				}
			}
		});

		shell.setSize(400, 400);
		shell.open();
		while(!shell.isDisposed()) {
			if(!display.readAndDispatch())
				display.sleep();
		}
		for(int i = 0; i < objects.length; i++) {
			if(objects[i] != null && !objects[i].isDisposed()) {
				objects[i].dispose();
			}
		}
		display.dispose();
	}
}
