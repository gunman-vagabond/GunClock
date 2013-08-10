package gunman;

import java.awt.*;
import java.awt.event.*;


public class GunClockApp extends Frame implements Runnable,WindowListener {

    int clockSize = 20;
    GunClockBean gcBean;
    TextArea outText;

    Thread t;

    public GunClockApp(String title){
        super(title);
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addWindowListener(this);

        gcBean = new GunClockBean();
        gcBean.setStrNewline("\n");
        gcBean.setClockSize(clockSize);

        outText = new TextArea("",clockSize,clockSize*2,TextArea.SCROLLBARS_NONE);
//        outText = new JTextArea(clockSize,clockSize*2);
        outText.setEditable(false);
        add(outText);
//        getContentPane().add(outText);

        refleshClock();

        t = new Thread(this);
        t.start();

    }

    public void run(){

        while(t!=null){
            refleshClock();
            try{ t.sleep(60000); } catch (InterruptedException e){}
        }

    }

    public void refleshClock(){
        outText.setText(gcBean.getGunClock());
    }

    public void windowActivated(WindowEvent e){}
    public void windowClosed(WindowEvent e){}
    public void windowClosing(WindowEvent e){System.exit(0);}
    public void windowDeactivated(WindowEvent e){}
    public void windowDeiconified(WindowEvent e){}
    public void windowIconified(WindowEvent e){}
    public void windowOpened(WindowEvent e){}

    public static void main(String arg[]){
        GunClockApp gunClock = new GunClockApp("GunClockApp");
        gunClock.pack();
        gunClock.show();

    }
}
