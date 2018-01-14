package gunClock_plugin.views;


import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.part.*;
import org.eclipse.jface.viewers.*;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.jface.action.*;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.*;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import gunman.*;

public class GunClockView extends ViewPart implements Runnable, PaintListener {

	Label label;
	GunClockBean gunClockBean = new GunClockBean();

	/**
	 * The constructor.
	 */
	public GunClockView() {
	}

	/**
	 * This is a callback that will allow us
	 * to create the viewer and initialize it.
	 */
	public void createPartControl(Composite parent) {

		
		gunClockBean.setClockSize(15);
		label = new Label(parent, SWT.CENTER);
		Font font = new Font(Display.getCurrent(), "Courier New", 8, SWT.NORMAL);
		label.setFont(font);

//		label.setText(gunClockBean.getGunClock());

		label.addPaintListener(this);
		Thread t = new Thread(this);
		t.start();
	}
		
	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
		label.setText(gunClockBean.getGunClock());
	}

	public void run() {
		try {
			Display display = label.getDisplay();
			while(true){
                // displayÇ…redraw()Çé¿çsÇµÇƒÇ‡ÇÁÇ§
                display.asyncExec(new Runnable() {
                    public void run() {
                    	if(!label.isDisposed()) label.redraw();
                    }
                });

                Thread.sleep(60000);
			}
		} catch (InterruptedException e) {
		}
	}

	public void paintControl(PaintEvent e) {
		label.setText(gunClockBean.getGunClock());
	}
}