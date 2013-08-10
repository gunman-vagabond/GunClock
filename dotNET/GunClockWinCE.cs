using System;
using System.Windows.Forms;
//using Microsoft.WindowsCE.Forms;
using System.Drawing;

public class GunClockWin : Form {

	Label l;
	GunClock gc;
	int   clockSize=20;

	public static void Main(){
		Application.Run(new GunClockWin());
	}
    
	GunClockWin(){

		this.Text = "GunClockWin";
		this.Size = new Size(clockSize*15,clockSize*15);

		this.gc = new GunClock();
		this.gc.Newline = "\n";
		this.gc.ClockSize = clockSize;

		this.l = new Label();
		this.l.Size = this.Size; //new Size(clockSize*15,clockSize*15);
//		this.l.Font = new Font("Courier new",7); //.NET compact Framework Ç…ÇÕé¿ëïÇ≥ÇÍÇƒÇ¢Ç»Ç¢ÅB

		this.Controls.Add(l);

		Timer timer;
		timer = new Timer();
		timer.Tick += new EventHandler(ShowGunClockWin);
		timer.Interval = 1000;
//		timer.Start();    //.NET compact Framework Ç…ÇÕé¿ëïÇ≥ÇÍÇƒÇ¢Ç»Ç¢ÅBEnabled=trueÇ∆ìØã`
		timer.Enabled = true;

	}

	public void ShowGunClockWin(object obj, EventArgs ea){
		this.l.Size = this.Size;

		this.clockSize = Math.Min(this.Size.Width/15, this.Size.Height/15);

		this.gc.ClockSize = clockSize;
		this.l.Text = this.gc.ClockImage;
	}

}