#include <stdio.h>
#include <WinSock2.h>

#define GUNCLOCKPORT   7777

main(int argc, char *argv[]){
	
    int sock;
    struct sockaddr_in svraddr;
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

    svraddr.sin_family = AF_INET;
    svraddr.sin_port   = htons(GUNCLOCKPORT);

    phe = gethostbyname(argv[1]);
    memcpy(&svraddr.sin_addr, phe->h_addr, phe->h_length);
    if ( (ret = connect(sock, (struct sockaddr *)&svraddr, sizeof(svraddr))) == -1){
        printf("connect error! code=%x\n",ret);
        return ret;
    } 

    printf("connect completed: %s(%d:%d), sock=%d\n",inet_ntoa(svraddr.sin_addr), svraddr.sin_port, GUNCLOCKPORT, sock);


    clocksize = atoi(argv[2]);
    printf("clocksize=%i\n", clocksize);
//    sendlen = send(sock, argv[2], strlen(argv[2]), 0);
    sendlen = send(sock, argv[2], 4, 0);

    for (i=0; i<clocksize; i++){
        int recvlen;
        recvlen = recv(sock, buf, clocksize*2+1, 0);
        printf("%s\n", buf);
    }

}
