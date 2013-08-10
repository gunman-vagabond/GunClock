//import java.util.*;
//import java.io.Serializable;
using System;
using System.Text;

public class GunClock {

    // private values ////////////////////////////////////////////////////////
    private String         gunClock;
    static private String[]       stringGunClock;
    private int            clockSize = 20;
    private String         strNewline = "\n";


    // character images (String[]) ////////////////////////////////////////////

    static String[] strGunman =
                        { "*  __ *", 
                          " _|__|_",
                          "b (@@) ",
                          " V|~~|>",
                          "  //T| "
                        };

    static String[] strInu = 
                        { "__AA  * ",
                          "| 6 |__P",
                          "~~|    l",
                          " /_/~l_l"
                        };

    static String[] strLongHand = 
                        { "##" 
                        };
    static String[] strShortHand = 
                        { "::" 
                        };

    static String[] str3  = { "3" };
    static String[] str6  = { "6" };
    static String[] str9  = { "9" };
    static String[] str12 = { "12" };

    static String[] strWaku = { "+" };


    // Cast ////////////////////////////////////////////////////////////////////

    private Cast gunman ; 
    private Cast inu    ;
    private Cast longHand;
    private Cast shortHand;
    private Cast num3,num6,num9,num12;
    private Cast waku;

    // constructer //////////////////////////////////////////////////////////
    public GunClock(){

        ////////////////
        // create Cast 
        ////////////////
        gunman    = new Cast( strGunman );
        inu       = new Cast( strInu );
        longHand  = new Cast( strLongHand  );
        shortHand = new Cast( strShortHand );

        num3      = new Cast( str3  );
        num6      = new Cast( str6  );
        num9      = new Cast( str9  );
        num12     = new Cast( str12 );
        waku      = new Cast( strWaku ) ;

    }


    // Cast class : handling character //////////////////////////////////////
    public class Cast{

        /////////////////////
        // character image 
        /////////////////////
        String[] image;

        ////////////////
        // constructer
        ////////////////
        public Cast(String[] image){
            this.image = image;
        }

        //////////////////////
        // display character
        //////////////////////
        public void display(int x, int y) {

            /////////////////////////////
            // compute (x,y) for display
            /////////////////////////////
            int _x = (x*2) -(image[0].Length /2) ;
            int _y = y     -(image.Length    /2);
            
            /////////////////////////// 
            // display character image 
            /////////////////////////// 
            char c;
            for(int i=0; i<image.Length; i++){
                for(int j=0; j<image[i].Length; j++){

                    if( (c = image[i][j]) != '*' ) {
                             try{
					stringGunClock[_y+i] = stringGunClock[_y+i].Remove(_x+j,1);
					stringGunClock[_y+i] = stringGunClock[_y+i].Insert(_x+j,image[i].Substring(j,1));

                             } catch (Exception){ //System.ArgumentOutOfRangeException
                               /* Ignore it! */
                            }
                    }

                }
            }

        }

    }


    // tostring : ///////////////////////////////////////////////////////////
    //// from string-array to string : separated by indicated string //
    /////////////////////////////////////////////////////////////////////////
    private String ToString(String[] sa){

        String strbuf = "";

	strbuf = String.Join(strNewline,sa);

//        for(int i=0; i<sa.Length; i++){
//          strbuf.Join("",sa[i]);
//            strbuf.Join("",strNewline);
//        }

        return strbuf;
    }

    // makeGunClock /////////////////////////////////////////////////////
    private void makeGunClock(){

        /////////////////////////////////////
        // create stringBuffer[] for GunClock
        /////////////////////////////////////
        stringGunClock =  new String[clockSize];
        String strBase = "                                                                                                                                            "
                         .Substring(0,clockSize*2);
        for(int i=0;i<clockSize;i++){
//            strBufGunClock[i] = new string(strBase); 
            stringGunClock[i] = strBase; 
        }

        /////////////
        // get time
        /////////////
//        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("JST"), Locale.JAPAN);
//        int hour   = calendar.get(Calendar.HOUR);
//        int minute = calendar.get(Calendar.MINUTE);
//        int second = calendar.get(Calendar.SECOND);

        DateTime now = DateTime.Now;
        int hour   = now.Hour;
        int minute = now.Minute;
        int second = now.Second;

        ////////////////////////////////
        // compute character location
        ////////////////////////////////
        int centerX = (int)((clockSize)/2);
        int centerY = (int)((clockSize)/2);

        int gunmanX = centerX + (int)(Math.Cos(hourToRadian(hour,minute)) * (clockSize*2/3/2) );
        int gunmanY = centerY - (int)(Math.Sin(hourToRadian(hour,minute)) * (clockSize*2/3/2) );

        int inuX = centerX + (int)(Math.Cos(minuteToRadian(minute,second)) * (clockSize*4/5/2) );
        int inuY = centerY - (int)(Math.Sin(minuteToRadian(minute,second)) * (clockSize*4/5/2) );


        //////////////////////
        // display characters
        //////////////////////


        //// waku ////
        for(int i=0; i< 360; i+=30) {
            double radian = (i * 2 * Math.PI) / 360;

            int wakuX,wakuY; 

            double wakuXdiff = clockSize/2 * Math.Cos(radian);
            double wakuYdiff = clockSize/2 * Math.Sin(radian);

            if ( wakuXdiff >=0 ) {
                wakuX = centerX + (int)(wakuXdiff);
            } else {
                wakuX = centerX + (int)(wakuXdiff-0.5);
            }

            if ( wakuYdiff >=0 ) {
                wakuY = centerY + (int)(wakuYdiff);
            } else {
                wakuY = centerY + (int)(wakuYdiff-0.5);
            }

            waku.display(wakuX, wakuY);
        }


        //// num ////
        num3.display (clockSize -1 , centerY      );
        num6.display (centerX      , clockSize -1 );
        num9.display (0            , centerY      );
        num12.display(centerX      , 0            );

        //// longHand ////
        for(int i=0; i<clockSize*2/3/2; i++){
            int longHandX = centerX + ( ((inuX - centerX) * i) / (clockSize*2/3/2) );
            int longHandY = centerY + ( ((inuY - centerY) * i) / (clockSize*2/3/2) );

            longHand.display(longHandX, longHandY);
        }


        //// shortHand ////
        for(int i=0; i<clockSize*5/6/2; i++){
            int shortHandX = centerX + ( ((gunmanX - centerX) * i) / (clockSize*5/6/2) );
            int shortHandY = centerY + ( ((gunmanY - centerY) * i) / (clockSize*5/6/2) );

            shortHand.display(shortHandX, shortHandY);
        }


        //// inu ////
        inu.display(inuX, inuY );

        //// gunman ////
        gunman.display(gunmanX, gunmanY );


        ////////////////////////
        // display digital time 
        ////////////////////////

        StringBuilder strTime = new StringBuilder();

        if(hour<10) strTime.Append("0");
        strTime.Append(hour+":");
        if(minute<10) strTime.Append("0");
        strTime.Append(minute+":");
        if(second<10) strTime.Append("0");
        strTime.Append(second);

        String[] strDigital = new String[3];
        strDigital[0] = "____________";
        strDigital[1] = "| " + strTime + " |";
        strDigital[2] = "~~~~~~~~~~~~";

        Cast digital = new Cast(strDigital);

        double digitalRadian = DigitalRadian(hour,minute,second);

        digital.display(centerX + (int)(Math.Cos(digitalRadian) * clockSize/2 *1/2)
                      , centerY - (int)(Math.Sin(digitalRadian) * clockSize/2 *1/2)
        );



        ////////////////////////////////////////////
        // translate to string from stringBuffer[]
        ////////////////////////////////////////////
        gunClock = ToString(stringGunClock);

    }

    // tool : digitalRadian ///////////////////////////////////////////////////////
    private double DigitalRadian(int h, int m, int s) {

        double hRadian = hourToRadian(h,m);
        double mRadian = minuteToRadian(m,s);

        double aveRadian = (hRadian + mRadian) / 2;

        if ( ((hRadian > mRadian) && (hRadian - mRadian < Math.PI))
          || ((mRadian > hRadian) && (mRadian - hRadian < Math.PI))
        ) {
            return aveRadian + Math.PI; 
        } else {
            return aveRadian;
        }

    }

    // tool : hourToRadian ///////////////////////////////////////////////////////
    private double hourToRadian(int h, int m) {

        return Math.PI * (90.0 - ((h%12) + m/60.0) * 30.0) / 180.0;

    }

    // tool : minuteToRadian ///////////////////////////////////////////////////////
    private double minuteToRadian(int m, int s) {

        return Math.PI * (90.0 - (m + s/60.0) * 6.0) / 180.0;

    }


    // setter : setClockSize ///////////////////////////////////////////////////
    public void setClockSize(int clockSize){
        this.clockSize = clockSize;
    }
    // getter : getClockSize ///////////////////////////////////////////////////
    public int getClockSize() {
        return clockSize;
    }
    // property : ClockSize
    public int ClockSize {
	set{
//		if(value<5 || value>50)
//			throw new ArgumentOutOfRangeException("ClockSize");

		if(value<5)
			this.clockSize = 5;
		else if(value>50)
			this.clockSize = 50;
		else
			this.clockSize = value;
	}

	get{
		return this.clockSize;
	}
    }


    // setter : setStrNewline ///////////////////////////////////////////////////
    public void setStrNewline(String strNewline){
        this.strNewline = strNewline;
    }
    // getter : getStrNewline ///////////////////////////////////////////////////
    public String getStrNewline(){
        return strNewline;
    }

    // property : StrNewline
    public String Newline {
	set{
		this.strNewline = value;
	}
	get{
		return this.strNewline;
	}
    }

    // getter : getGunClock //////////////////////////////////////////////////
    public String getGunClock() {

        makeGunClock();

        return gunClock;
    } 

    // property : GunClock
    public String ClockImage{
	get{
		makeGunClock();
		return this.gunClock;
	}
    }

    public static void CUIsample(){
        GunClock gc = new GunClock();
        gc.setStrNewline("\n");
        Console.WriteLine(gc.getGunClock());
    }

    // main (for test)  ///////////////////////////////////////////////////////
    public static void Main(){
        CUIsample();
    }

}

