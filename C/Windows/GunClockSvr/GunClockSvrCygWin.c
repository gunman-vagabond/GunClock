#include <stdio.h>
#include <sys/socket.h>
#include <netinet/in.h>

#define GUNCLOCKPORT   7777

__declspec(dllimport) 
void   __stdcall showGunClock(int clocksize);

__declspec(dllimport)
char** __stdcall getGunClock(int clocksize);

main(){
	
    int sock;
    int newsock;
    struct sockaddr_in svraddr;
    struct sockaddr_in cltaddr;
    int cltaddrlen;
    char **gunclock;

    sock = socket(AF_INET, SOCK_STREAM, IPPROTO_TCP);

    svraddr.sin_family = AF_INET;
    svraddr.sin_port   = htons(GUNCLOCKPORT);
    svraddr.sin_addr.s_addr = INADDR_ANY;
    if ( bind(sock, (struct sockaddr *)&svraddr, sizeof(svraddr)) ){
        printf("bind error!\n");
        return -1;
    }

    if ( listen(sock, 1) ){
        printf("listen error!\n");
        return -1;
    }

//    for(;;){printf(".");}

    if ( (newsock = accept(sock, (struct sockaddr *)&cltaddr, &cltaddrlen)) == -1){
        printf("accept error! code=%x\n",newsock);
        return -1;
    } 


    printf("connect accepted: %s(%i)\n",inet_ntoa(cltaddr.sin_addr), cltaddr.sin_port);

    gunclock = getGunClock(20);

}
