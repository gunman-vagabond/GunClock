import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;

import gunman.*;

public class GunClockApplet extends Applet implements ActionListener,Runnable {

    GunClockBean gcBean;
    TextArea outText;

    Button b;
    Thread t;

    int clockSize = 20;

    boolean running = false;

    public void init(){

        setBackground(Color.white);
        
        gcBean = new GunClockBean();
        gcBean.setStrNewline("\n");
        gcBean.setClockSize(clockSize);

        outText = new TextArea("",clockSize,clockSize*2,TextArea.SCROLLBARS_NONE);
        outText.setEditable(false);
        outText.setBackground(Color.white);
        add(outText);

        b = new Button("What time is it?");
        b.addActionListener(this);
//        add(b);


    }


    public void start(){
        refleshClock();

        if ( t == null ) {
            t = new Thread(this);
            t.start();
        }
    } 

    public void stop() {
        if ( t != null ) {
//            t.stop();
            t = null;
        }
    }

    public void destroy() {
        gcBean = null;
    }

    public void refleshClock(){
        outText.setText(gcBean.getGunClock());
    }

    public void actionPerformed(ActionEvent evt){
        refleshClock();
    }

    public void run() {
        while( t != null ) {

            refleshClock();
            try{ t.sleep(60000); } catch (InterruptedException e) {}

        }
    }

}
