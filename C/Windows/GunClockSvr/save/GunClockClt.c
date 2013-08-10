#include <stdio.h>
#include <WinSock2.h>

#define GUNCLOCKPORT   7777

main(int argc, char *argv[]){
	
    int sock;
    int newsock;
    struct sockaddr_in svraddr;
    struct sockaddr_in cltaddr;
    int svraddrlen;
    char **gunclock;
    WSADATA wsaData;
    int ret;
    char buf[64];
    int clocksize;
    int sendlen;
    int i;
    HOSTENT *phe;

    if ((ret = WSAStartup(MAKEWORD(2, 2), &wsaData)) != 0) {
        printf("WSAStartup failed: 0x%x", ret);
        return -1;
    }

    sock = socket(AF_INET, SOCK_STREAM, IPPROTO_TCP);

    printf("socket created, soc=0x%x\n", sock);



/*
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


    if ( (newsock = accept(sock, (struct sockaddr *)&cltaddr, &cltaddrlen)) == -1){
        printf("accept error! code=%x\n",newsock);
        return -1;
    } 
*/

    svraddr.sin_family = AF_INET;
    svraddr.sin_port   = htons(GUNCLOCKPORT);
//    svraddr.sin_port   = GUNCLOCKPORT;

    phe = gethostbyname(argv[1]);
    memcpy(&svraddr.sin_addr, phe->h_addr, phe->h_length);
//    svraddr.sin_addr.s_addr = argv[1];
    if ( (newsock = connect(sock, (struct sockaddr *)&svraddr, sizeof(svraddr))) == -1){
        printf("connect error! code=%x\n",newsock);
        return -1;
    } 

    printf("connect completed: %s(%d:%d)\n",inet_ntoa(svraddr.sin_addr), svraddr.sin_port, GUNCLOCKPORT);


//    gunclock = getGunClock(20);

    clocksize = atoi(argv[2]);
    printf("clocksize=%i\n", clocksize);
//    sendlen = send(newsock, argv[2], strlen(argv[2]), 0);
	buf[0] = '1';
	buf[1] = '2';

	sendlen = send(newsock, buf, 2, 0);
printf("sendlen = %x\n", sendlen);

    for (i=0; i<clocksize; i++){
        int recvlen;
        recvlen = recv(newsock, buf, 256, 0);
        printf("[%d] %s", i, buf);
    }

}
