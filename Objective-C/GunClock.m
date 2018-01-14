//
//  GunClock.m
//  HelloWorld
//
//  Created by 柳葉 光 on 17/02/25.
//  Copyright (c) 2017年 test. All rights reserved.
//

#include "GunClock.h"
#import "GunClock.h"

@implementation GunClock

- (NSString *)getGunClock : (int) gunClockSize {
//    int gunClockSize = 18;

    char ** gunClock = getGunClockC(gunClockSize);

    NSString * str = @" ";
    for (int i=0 ; i<gunClockSize; i++ ){
        NSString *line;
        line = [NSString stringWithCString: gunClock[i] encoding:NSUTF8StringEncoding];
        str = [NSString stringWithFormat:@"%@\n%@", str, line];
    }
    return str;
    //return @"GunClock\nGunClock\n";
}

@end
