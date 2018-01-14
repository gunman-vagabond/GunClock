//
//  GunClockC.h
//  HelloWorld
//
//  Created by 柳葉 光 on 17/02/25.
//  Copyright (c) 2017年 test. All rights reserved.
//

#ifndef HelloWorld_GunClockC_h
#define HelloWorld_GunClockC_h

static double hourToRadian(int h, int m);
static double minuteToRadian(int m, int s);
static double DigitalRadian(int h, int m, int s);
static void dispChara(char **image, int x, int y, int w, int h);
static void makeGunClock(int clocksize);

void showGunClock(int clocksize);
char **getGunClockC(int clocksize);

#endif
