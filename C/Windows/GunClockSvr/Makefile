#CC = gcc

DLL_LIB=GunClock.lib ws2_32.lib

TARGET=GunClockSvr.exe
TARGET_SRC=GunClockSvr.c

TARGET2=GunClockClt.exe
TARGET2_SRC=GunClockClt.c

TARGET3=GunClockSvrHTTP.exe
TARGET3_SRC=GunClockSvrHTTP.c

all: $(TARGET) $(TARGET2) $(TARGET3)

$(TARGET): $(TARGET_SRC)
	$(CC) -o $(TARGET) $(TARGET_SRC) $(DLL_LIB)


$(TARGET2): $(TARGET2_SRC)
	$(CC) -o $(TARGET2) $(TARGET2_SRC) $(DLL_LIB)

$(TARGET3): $(TARGET3_SRC)
	$(CC) -o $(TARGET3) $(TARGET3_SRC) $(DLL_LIB)

clean:
	rm *.obj *.exe
