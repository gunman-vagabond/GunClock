#include <stdio.h>
#include "GunClock.h"

void main(int argc, char *argv[]){
	char **gunclock;
	int clocksize;
	int i;

	if (argc == 2){
		clocksize = atoi(argv[1]);
	} else {
		clocksize = 20;
	}

//	showGunClock(clocksize);

	gunclock = getGunClock(clocksize);

	for (i=0; i<clocksize; i++){
		printf("%s\n", gunclock[i]);
	}

}
