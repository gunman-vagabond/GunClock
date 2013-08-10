package gunman;

import java.util.*;
import java.io.Serializable;

public class GunClockBean implements Serializable {

    // private values ////////////////////////////////////////////////////////
    private String         gunClock;
    private StringBuffer[] strBufGunClock;
    private int clockSize = 20;
//    private String         strNewline = "<br>\n";
    private String         strNewline = "\n";


    // character images (String[]) ////////////////////////////////////////////

    static final String[] strGunman = 
                        { "*  __ *", 
                          " _|__|_",
                          "b (@@) ",
                          " V|~~|>",
                          "  //T| "
                        };

    static final String[] strInu = 
                        { "__AA  * ",
                          "| 6 |__P",
                          "~~|    l",
                          " /_/~l_l"
                        };

    static final String[] strLongHand = 
                        { "##" 
                        };
    static final String[] strShortHand = 
                        { "::" 
                        };

    static final String[] str3  = { "3" };
    static final String[] str6  = { "6" };
    static final String[] str9  = { "9" };
    static final String[] str12 = { "12" };

    static final String[] strWaku = { "+" };


    // Cast ////////////////////////////////////////////////////////////////////

    private Cast gunman ; 
    private Cast inu    ;
    private Cast longHand;
    private Cast shortHand;
    private Cast num3,num6,num9,num12;
    private Cast waku;

    // constructer //////////////////////////////////////////////////////////
    public GunClockBean(){

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
    private class Cast implements Serializable {

        /////////////////////
        // character image 
        /////////////////////
        String[] image;

        ////////////////
        // constructer
        ////////////////
        private Cast(String[] image){
            this.image = image;
        }

        //////////////////////
        // display character
        //////////////////////
        private void display(int x, int y) {

            /////////////////////////////
            // compute (x,y) for display
            /////////////////////////////
            int _x = (x*2) -(image[0].length() /2) ;
            int _y = y     -(image.length      /2);

            
            /////////////////////////// 
            // display character image 
            /////////////////////////// 
            char c;
            for(int i=0; i<image.length; i++){
                for(int j=0; j<image[i].length(); j++){

                    if( (c = image[i].charAt(j)) != '*' ) {
                             try{
                        strBufGunClock[_y+i].setCharAt(_x+j,image[i].charAt(j));
                             } catch (Exception StringIndexOutOfBoundsException){
                                 /* Ignore it! */
                             }
                    }

                }
            }

        }

    }


    // toString : ///////////////////////////////////////////////////////////
    //// from StringBuffer-array to String : separated by indicated string //
    /////////////////////////////////////////////////////////////////////////
    private String toString(StringBuffer[] sb){

        StringBuffer strbuf = new StringBuffer();


        for(int i=0; i<sb.length; i++){
            strbuf.append(sb[i]);
            strbuf.append(strNewline);
        }

        return strbuf.toString();

    }

    // makeGunClock /////////////////////////////////////////////////////
    private void makeGunClock(){

        /////////////////////////////////////
        // create StringBuffer[] for GunClock
        /////////////////////////////////////
        strBufGunClock =  new StringBuffer[clockSize];
        String strBase = "                                                                                                                                            "
                         .substring(0,clockSize*2);
        for(int i=0;i<clockSize;i++){
            strBufGunClock[i] = new StringBuffer(strBase); 
        }

        /////////////
        // get time
        /////////////
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("JST"), Locale.JAPAN);
        int hour   = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);

        ////////////////////////////////
        // compute character location
        ////////////////////////////////
        int centerX = (int)((clockSize)/2);
        int centerY = (int)((clockSize)/2);

        int gunmanX = centerX + (int)(Math.cos(hourToRadian(hour,minute)) * (clockSize*2/3/2) );
        int gunmanY = centerY - (int)(Math.sin(hourToRadian(hour,minute)) * (clockSize*2/3/2) );

        int inuX = centerX + (int)(Math.cos(minuteToRadian(minute,second)) * (clockSize*4/5/2) );
        int inuY = centerY - (int)(Math.sin(minuteToRadian(minute,second)) * (clockSize*4/5/2) );


        //////////////////////
        // display characters
        //////////////////////


        //// waku ////
        for(int i=0; i< 360; i+=30) {
            double radian = (i * 2 * Math.PI) / 360;

            int wakuX,wakuY; 

            double wakuXdiff = clockSize/2 * Math.cos(radian);
            double wakuYdiff = clockSize/2 * Math.sin(radian);

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

        StringBuffer strBufTime = new StringBuffer();

        if(hour<10) strBufTime.append("0");
        strBufTime.append(hour+":");
        if(minute<10) strBufTime.append("0");
        strBufTime.append(minute);

        String[] strDigital = new String[3];
        strDigital[0] = "_________";
        strDigital[1] = "| " + strBufTime + " |";
        strDigital[2] = "~~~~~~~~~";

        Cast digital = new Cast(strDigital);

        double digitalRadian = digitalRadian(hour,minute,second);

        digital.display(centerX + (int)(Math.cos(digitalRadian) * clockSize/2 *1/2)
                      , centerY - (int)(Math.sin(digitalRadian) * clockSize/2 *1/2)
        );



        ////////////////////////////////////////////
        // translate to String from StringBuffer[]
        ////////////////////////////////////////////
        gunClock = toString(strBufGunClock);

    }

    // tool : digitalRadian ///////////////////////////////////////////////////////
    private double digitalRadian(int h, int m, int s) {

        double hRadian = hourToRadian(h,m);
        double mRadian = minuteToRadian(m,s);

        double aveRadian = (hRadian + mRadian) / 2;

        if ( ((hRadian >= mRadian) && (hRadian - mRadian < Math.PI))
          || ((mRadian >= hRadian) && (mRadian - hRadian < Math.PI))
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

    // setter : setStrNewline ///////////////////////////////////////////////////
    public void setStrNewline(String strNewline){
        this.strNewline = strNewline;
    }

    // getter : getStrNewline ///////////////////////////////////////////////////
    public String getStrNewline(){
        return strNewline;
    }

    // getter : getGunClock //////////////////////////////////////////////////
    public String getGunClock() {

        makeGunClock();

        return gunClock;
    } 


    public static void GUIsample(){
        GunClockBean gcb = new GunClockBean();
        gcb.setStrNewline("\n");
        System.out.println(gcb.getGunClock());
    }

    // main (for test)  ///////////////////////////////////////////////////////
    public static void main(String[] argv){
        GUIsample();
    }

}
