#include <stdio.h>
#include <WinSock2.h>

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
    WSADATA wsaData;
    int ret;
    char buf[64];
    int clocksize;
    int recvlen;
    int i;
    int optval,optlen;


    if ((ret = WSAStartup(MAKEWORD(2, 2), &wsaData)) != 0) {
        printf("WSAStartup failed: 0x%x", ret);
        return -1;
    }

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


    printf("connect accepted: %s(%d)\n",inet_ntoa(cltaddr.sin_addr), cltaddr.sin_port);

//    optval=1;optlen=1;
//    setsockopt(newsock, SOL_SOCKET, SO_REUSEADDR, (void*)&optval, optlen);

    recvlen = recv(newsock, buf, 64, 0);

printf("recvlen=%d\n", recvlen);

    clocksize = atoi(buf);

clocksize=20;
    printf("clocksize=", clocksize);

    gunclock = getGunClock(clocksize);

    for (i=0; i<clocksize; i++){
        int sendlen;
printf("%S\n",gunclock[i]);
        sendlen = send(newsock, gunclock[i], strlen(gunclock[i]), 0);
printf("sendlen=%d",sendlen);
    }

}
