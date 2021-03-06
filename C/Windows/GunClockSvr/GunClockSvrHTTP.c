#include <stdio.h>
#include <WinSock2.h>

#define GUNCLOCKPORT   7777

__declspec(dllimport) 
void   __stdcall showGunClock(int clocksize);

__declspec(dllimport)
char** __stdcall getGunClock(int clocksize);

main(int argc, char *argv[]){
	
    int sock;
    int newsock;
    struct sockaddr_in svraddr;
    struct sockaddr_in cltaddr;
    int cltaddrlen;
    char **gunclock;
    WSADATA wsaData;
    int ret;
    char buf[1024];
    int clocksize;
    int recvlen;
    int i;
    int optval,optlen;
    int port;

    if ((ret = WSAStartup(MAKEWORD(2, 2), &wsaData)) != 0) {
        printf("WSAStartup failed: 0x%x", ret);
        return -1;
    }

    sock = socket(AF_INET, SOCK_STREAM, IPPROTO_TCP);

    svraddr.sin_family = AF_INET;
//    if(argc==1) {
//        svraddr.sin_port = htons(atoi(argv[1]));
//    } else {
        svraddr.sin_port   = htons(GUNCLOCKPORT);
//    }
    svraddr.sin_addr.s_addr = INADDR_ANY;
    if ( bind(sock, (struct sockaddr *)&svraddr, sizeof(svraddr)) ){
        printf("bind error!\n");
        return -1;
    }

    if ( listen(sock, 1) ){
        printf("listen error!\n");
        return -1;
    }

    while(1){

	cltaddrlen = sizeof(cltaddr);
        if ( (newsock = accept(sock, (struct sockaddr *)&cltaddr, &cltaddrlen)) == -1){
            printf("accept error! code=%x\n",newsock);
            return -1;
        } 

        printf("connect accepted: %s(%d), newsock=%d\n"
               ,inet_ntoa(cltaddr.sin_addr) 
               ,cltaddr.sin_port, newsock);

        recvlen = recv(newsock, buf, 1024, 0);

        printf("recvlen=%d\n", recvlen);

	buf[recvlen] = '\0';
	printf("%s\n", buf);

//        clocksize = atoi(buf);
	clocksize = 20;
        printf("clocksize=%d\n", clocksize);

        gunclock = getGunClock(clocksize);

	sprintf(buf,"HTTP/1.0 200 Document follows\nContent-length: 857\nContent-type: text/html\n\n<HTML><BODY><pre>");
//	sprintf(buf,"HTTP/1.0 200 Document follows\nContent-length: 800\nContent-type: text/html\n\n<HTML><BODY><pre>");
//	send(newsock, buf, strlen(buf) + 1 , 0);
	send(newsock, buf, strlen(buf) , 0);

        for (i=0; i<clocksize; i++){
            int sendlen;
//            sendlen = send(newsock, gunclock[i], strlen(gunclock[i])+1, 0);
            sendlen = send(newsock, gunclock[i], strlen(gunclock[i]), 0);
            printf("sendlen=%d, '%s'\n",sendlen, gunclock[i]);
            sendlen = send(newsock, "\n", 1, 0);
        }
	sprintf(buf,"</pre></BODY></HTML>");
	send(newsock, buf, strlen(buf) , 0);

        close(newsock);
	printf("socket closed.");
    }

}
