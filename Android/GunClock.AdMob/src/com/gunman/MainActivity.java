package com.gunman;

import java.util.Timer;
import java.util.TimerTask;

import com.gunman.R;
import com.gunman.R.id;
import com.gunman.R.layout;
import com.gunman.R.menu;
import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.text.Layout;
import android.util.FloatMath;
import android.view.Display;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.Typeface;

public class MainActivity extends Activity {

	TextView textViewGunClock;
	LinearLayout layout;
	float gunClockTextSize;
	float gunClockLeft, gunClockTop;
	GunClockBean gcb;
	Handler handler; 
	
	Timer   mTimer   = null;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WindowManager wm = (WindowManager)getSystemService(WINDOW_SERVICE);
        Display disp = wm.getDefaultDisplay();
        Point size = new Point();
        disp.getSize(size);
        int displayWidth = size.x;
        int displayHeight = size.y;
                
        gcb = new GunClockBean();
        gcb.setStrNewline("\n");
        
        gunClockTextSize = (displayWidth>displayHeight)?
                         displayHeight/gcb.getClockSize()/2.2f:
                         displayWidth/gcb.getClockSize()/2.2f;
                         
        gunClockLeft = 100;
        gunClockTop = 100;
        textViewGunClock = (TextView)this.findViewById(R.id.textViewGunClock);
        layout = (LinearLayout)this.findViewById(R.id.a);

//        textViewGunClock.setLeft(gunClockLeft);
//        textViewGunClock.setTop(gunClockTop);
        


        //Timerのスレッドでは、textView を直接触れないので、handler経由でメッセージング
        handler = new Handler(){
        	public void handleMessage(Message msg){
        		layout.setX(gunClockLeft);
        		layout.setY(gunClockTop);
//                layout.layout(
 //       				0,0,gunClockLeft,gunClockTop
   //     				);
//                layout.setLeft(
//       				gunClockLeft
//        		);
//                layout.setTop(
//        				gunClockTop
//        		);
        		
//                textViewGunClock.setLeft(gunClockLeft);
//                textViewGunClock.setTop(gunClockTop);
                textViewGunClock.setTypeface(Typeface.MONOSPACE);
                textViewGunClock.setTextSize(gunClockTextSize);
                textViewGunClock.setHorizontallyScrolling(true);
                textViewGunClock.setText(gcb.getGunClock());
        	}
        };
        
       	TimerTask timerTask = new TimerTask() {
			@Override
       		public void run(){
				//textView を直接触れない、handler 経由でメッセージング
       			handler.sendMessage(new Message());
       		}
       	};
       	
       	//定期タイマ：1000マイクロ秒ごとに timerTask を呼び出す
       	mTimer = new Timer(true);
       	mTimer.scheduleAtFixedRate(timerTask, 0, 1000);
       	
        // adView を作成する
//       	final String MY_AD_UNIT_ID = "a151fbf6300b25a";
//        AdView adView = new AdView(this, AdSize.BANNER, MY_AD_UNIT_ID);
        
        // 属性 android:id="@+id/mainLayout" が与えられているものとして
        // LinearLayout をルックアップする
//        LinearLayout layoutB = (LinearLayout)findViewById(R.id.b);

        // adView を追加
//        layoutB.addView(adView);

        // 一般的なリクエストを行って広告を読み込む
//        adView.loadAd(new AdRequest());

        
        // Look up the AdView as a resource and load a request.
        AdView adView2 = (AdView)this.findViewById(R.id.adView);
        adView2.loadAd(new AdRequest());
//        LinearLayout layoutB = (LinearLayout)this.findViewById(R.id.b);
////        layoutB.addView(adView2);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

	private static final int MODE_NONE = 0;
	private static final int MODE_DRAG = 1;
	private static final int MODE_ZOOM = 2;

    int mode = MODE_NONE;
    
    /** Zoom開始時の二点間距離 */
	private float initDistance = 1;
	private float initLeft,initTop;
	private static final float MIN_LENGTH = 30f;
	/** MatrixのgetValues用 */
	private float[] values = new float[9];
	/** マトリックス */
	private Matrix matrix = new Matrix();
    
    @Override
    public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()){
		
		case MotionEvent.ACTION_DOWN:
			mode = MODE_DRAG;
			initLeft = event.getX(0);
			initTop = event.getY(0);
			
			break;
		case MotionEvent.ACTION_POINTER_2_UP:
		case MotionEvent.ACTION_UP:
			mode = MODE_NONE;
			
	        AnimationSet set = new AnimationSet(true);
	        RotateAnimation rotate = new RotateAnimation(0, 360, (textViewGunClock.getTextSize()*(gcb.getClockSize()+2))/2, (textViewGunClock.getTextSize()*(gcb.getClockSize()+2))/2);
	        set.addAnimation(rotate);
	        set.setDuration(3000); // 3000msかけてアニメーションする
	        textViewGunClock.startAnimation(set); // アニメーション適用

			break;
		case MotionEvent.ACTION_POINTER_2_DOWN:
			initDistance = getDistance(event);
			if (initDistance > MIN_LENGTH) {
				mode = MODE_ZOOM;
			}
			break;
		case MotionEvent.ACTION_MOVE:
			switch (mode) {
			case MODE_DRAG:
				float currentLeft = event.getX(0);
				float currentTop = event.getY(0);
				if ( currentLeft - initLeft > 20 || currentLeft - initLeft < -20 
			      || currentTop - initTop > 20 || currentTop - initTop < -20 ) {
					gunClockLeft = currentLeft -  (textViewGunClock.getTextSize()*(gcb.getClockSize()+2))/2;
					gunClockTop  = currentTop -  (textViewGunClock.getTextSize()*(gcb.getClockSize()+2))/2; 
//					gunClockLeft += currentLeft - initLeft;
//					gunClockTop  += currentTop - initTop;
					handler.sendMessage(new Message());
				}
				break;
			case MODE_ZOOM:
				if ( event.getPointerCount() == 2 ) { 
					float currentDistance= getDistance(event);
//					gunClockTextSize *= (currentLength / initLength );
					if ( currentDistance > initDistance ) {
						gunClockTextSize *= 1.05;
					} else {
						gunClockTextSize /= 1.05;
					}
					if (gunClockTextSize < 5) gunClockTextSize = 5;
					if (gunClockTextSize > 100) gunClockTextSize = 100;
					handler.sendMessage(new Message());
				}
				break;
			}
			break;
		}
		return false;
    }

	/**
	 * 比率を計算
	 * @param x
	 * @param y
	 * @return
	 */
	private float getDistance(MotionEvent e) {
		float xx = e.getX(1) - e.getX(0);
		float yy = e.getY(1) - e.getY(0);
		return FloatMath.sqrt(xx * xx + yy * yy);
	}
	
}
