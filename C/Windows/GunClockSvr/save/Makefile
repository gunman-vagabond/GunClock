#CC = gcc

DLL_LIB=GunClock.lib ws2_32.lib

TARGET=GunClockSvr.exe
TARGET_SRC=GunClockSvr.c

TARGET2=GunClockClt.exe
TARGET2_SRC=GunClockClt.c

all: $(TARGET) $(TARGET2)

$(TARGET): $(TARGET_SRC)
	$(CC) -o $(TARGET) $(TARGET_SRC) $(DLL_LIB)


$(TARGET2): $(TARGET2_SRC)
	$(CC) -o $(TARGET2) $(TARGET2_SRC) $(DLL_LIB)

clean:
	rm *.obj *.exe
