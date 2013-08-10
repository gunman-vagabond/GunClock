#include <windows.h>

static double hourToRadian(int h, int m);
static double minuteToRadian(int m, int s);
static double DigitalRadian(int h, int m, int s);
static void dispChara(char **image, int x, int y, int w, int h);
static void makeGunClock(int clocksize);

__declspec(dllexport) void   __stdcall showGunClock(int clocksize);
__declspec(dllexport) char** __stdcall getGunClock(int clocksize);
