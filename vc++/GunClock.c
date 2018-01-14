#include <math.h>
#include <stdio.h>
#include <time.h>
//#include <windows.h>

double hourToRadian(int h, int m);
double minuteToRadian(int m, int s);
double DigitalRadian(int h, int m, int s);
	
    static char strGunman[5][8] =
                        { "*  __ *", 
                          " _|__|_",
                          "b (@@) ",
                          " V|~~|>",
                          "  //T| "
                        };

    static char strInu[4][9] = 
                        { "__AA  * ",
                          "| 6 |__P",
                          "~~|    l",
                          " /_/~l_l"
                        };

    static char strLongHand[1][3] = 
                        { "##" 
                        };
    static char strShortHand[1][3] = 
                        { "::" 
                        };

    static char str3[1][2]  = { "3" };
    static char str6[1][2]  = { "6" };
    static char str9[1][2]  = { "9" };
    static char str12[1][3] = { "12" };

    static char strWaku[1][2] = { "+" };

#define GUNCLOCKSIZE 20
char strGunClock[GUNCLOCKSIZE][GUNCLOCKSIZE*2+1]=
//char *strGunClock[GUNCLOCKSIZE]=
{
"                                        ",
"                                        ",
"                                        ",
"                                        ",
"                                        ",
"                                        ",
"                                        ",
"                                        ",
"                                        ",
"                                        ",
"                                        ",
"                                        ",
"                                        ",
"                                        ",
"                                        ",
"                                        ",
"                                        ",
"                                        ",
"                                        ",
"                                        "
};

static void dispChara(char **image, int x, int y, int w, int h){

//printf("aaa");
	/////////////////////////////
	// compute (x,y) for display
	/////////////////////////////
	int _x = (x*2) -((w-1)/2) ;
	int _y = y     -(h/2);
	/////////////////////////// 
	// display character image 
	/////////////////////////// 
	char c;
	int i,j;

	char *tmp = (char *) image;
	for(j=0; j<h; j++){
		for(i=0; i<w; i++){
			c = *(tmp + (w*j) + i);
			if( c!='*' && c!='\0' ) {
				if ( _x+i >= 0
				  && _x+i < GUNCLOCKSIZE*2
				  && _y+j >= 0
				  && _y+j < GUNCLOCKSIZE 
				 ) {
					strGunClock[_y+j][_x+i] = c;
				}
			}
		}
	}
	return;
}

static void makeGunClock(){

        int hour;
        int minute;
        int second;
		int centerX;
		int centerY;
		int gunmanX;
		int gunmanY;
		int inuX;
		int inuY;
		int i;
		char strDigital[3][13] = {
			"____________",
			"| xx:xx:xx |",
			"~~~~~~~~~~~~"
		};
		double digitalRadian;

        struct tm *newtime;
        time_t long_time;

		/*----------*/
		/* get time */
		/*----------*/
		time( &long_time );                /* long ®”‚Æ‚µ‚ÄŽž‚ðŽæ“¾ */
		newtime = localtime( &long_time ); /* Œ»’nŽž‚É•ÏŠ· */

		hour = newtime->tm_hour;
		minute = newtime->tm_min;
		second = newtime->tm_sec;
       
        ////////////////////////////////
        // compute character location
        ////////////////////////////////
		centerX = (int)((GUNCLOCKSIZE)/2);
		centerY = (int)((GUNCLOCKSIZE)/2);

		gunmanX = centerX + (int)(cos(hourToRadian(hour,minute)) * (GUNCLOCKSIZE*2/3/2) );
		gunmanY = centerY - (int)(sin(hourToRadian(hour,minute)) * (GUNCLOCKSIZE*2/3/2) );

		inuX = centerX + (int)(cos(minuteToRadian(minute,second)) * (GUNCLOCKSIZE*4/5/2) );
		inuY = centerY - (int)(sin(minuteToRadian(minute,second)) * (GUNCLOCKSIZE*4/5/2) );
		
		//////////////////////
        // display characters
        //////////////////////
        //// waku ////
        for(i=0; i< 360; i+=30) {
            double radian = (i * 2 * 3.14) / 360;

            int wakuX,wakuY; 

            double wakuXdiff = GUNCLOCKSIZE/2 * cos(radian);
            double wakuYdiff = GUNCLOCKSIZE/2 * sin(radian);

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

            dispChara(strWaku, wakuX, wakuY, 2,1);
        }


        //// num ////
        dispChara(str3 , GUNCLOCKSIZE -1 , centerY      ,2,1);
        dispChara(str6 , centerX      , GUNCLOCKSIZE -1 ,2,1);
        dispChara(str9 , 0            , centerY         ,2,1);
        dispChara(str12, centerX      , 0               ,3,1);

        //// longHand ////
        for(i=0; i<GUNCLOCKSIZE*2/3/2; i++){
            int longHandX = centerX + ( ((inuX - centerX) * i) / (GUNCLOCKSIZE*2/3/2) );
            int longHandY = centerY + ( ((inuY - centerY) * i) / (GUNCLOCKSIZE*2/3/2) );

            dispChara(strLongHand, longHandX, longHandY ,3,1);
        }


        //// shortHand ////
        for(i=0; i<GUNCLOCKSIZE*5/6/2; i++){
            int shortHandX = centerX + ( ((gunmanX - centerX) * i) / (GUNCLOCKSIZE*5/6/2) );
            int shortHandY = centerY + ( ((gunmanY - centerY) * i) / (GUNCLOCKSIZE*5/6/2) );

            dispChara(strShortHand, shortHandX, shortHandY ,3,1);
        }


        //// inu ////
        dispChara(strInu, inuX, inuY ,9,4);

        //// gunman ////
        dispChara(strGunman, gunmanX, gunmanY ,8,5 );


        ////////////////////////
        // display digital time 
        ////////////////////////
		sprintf(strDigital[1], "| %02d:%02d:%02d |", hour, minute, second);
        digitalRadian = DigitalRadian(hour,minute,second);
        dispChara(strDigital
                      , centerX + (int)(cos(digitalRadian) * GUNCLOCKSIZE/2 *1/2)
                      , centerY - (int)(sin(digitalRadian) * GUNCLOCKSIZE/2 *1/2)
                      , 13,3
        );

	return;
}

    // tool : digitalRadian ///////////////////////////////////////////////////////
    double DigitalRadian(int h, int m, int s) {

        double hRadian = hourToRadian(h,m);
        double mRadian = minuteToRadian(m,s);

        double aveRadian = (hRadian + mRadian) / 2;

        if ( ((hRadian > mRadian) && (hRadian - mRadian < 3.14))
          || ((mRadian > hRadian) && (mRadian - hRadian < 3.14))
        ) {
            return aveRadian + 3.14; 
        } else {
            return aveRadian;
        }

    }

    // tool : hourToRadian ///////////////////////////////////////////////////////
    double hourToRadian(int h, int m) {

        return 3.14 * (90.0 - ((h%12) + m/60.0) * 30.0) / 180.0;

    }

    // tool : minuteToRadian ///////////////////////////////////////////////////////
    double minuteToRadian(int m, int s) {

        return 3.14 * (90.0 - (m + s/60.0) * 6.0) / 180.0;

    }



static void showGunClock(){
	int i;
	for(i=0; i<GUNCLOCKSIZE; i++){
		printf("%s\n", strGunClock[i]);
	}
	return;
}

int main(){

	makeGunClock();
	showGunClock();
}
