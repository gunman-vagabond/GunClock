//
//  ViewController.m
//  HelloWorld
//
//  Created by 柳葉 光 on 17/02/25.
//  Copyright (c) 2017年 test. All rights reserved.
//

#import "ViewController.h"
#import "GunClock.h"

@interface ViewController ()

@property (weak, nonatomic) IBOutlet UILabel *label1;
@property (weak, nonatomic) IBOutlet UITextField *Text_Size;

@end

@implementation ViewController

NSTimer  *tm;
GunClock *gunClock;
int gunClockSize = 20;

- (IBAction)Text_DidEndOnExit:(UITextField *)sender {
//    [sender resignFirstResponder];
}

- (IBAction)buttonHandler:(UIButton *)sender {
    [_Text_Size endEditing: YES];
    gunClockSize = [_Text_Size.text intValue];
}

- (void)timeout: (NSTimer *)timer {
    _label1.text = [gunClock getGunClock:gunClockSize];
    _label1.numberOfLines = gunClockSize+2;
//    [_label1 sizeToFit];
}

- (void)viewDidLoad
{
    [super viewDidLoad];
	// Do any additional setup after loading the view, typically from a nib.
//  printf("Hello, World!");

    gunClock = [[GunClock alloc] init];

    tm= [NSTimer
         scheduledTimerWithTimeInterval:1.0f
         target:self
         selector:@selector(timeout:)
         userInfo:nil
         repeats:YES];

//    self.Text_Size.delegate = self;
}

//- (BOOL) textFieldShouldReturn:(UITextField *)textField {
//    [textField resignFirstResponder];
//    return YES;
//}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

@end
