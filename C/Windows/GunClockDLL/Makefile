DLL=GunClock.dll
DLL_SRC=GunClock.c
DLL_LIB=GunClock.lib

TARGET=GunClock.exe
TARGET_SRC=main.c

all: $(TARGET) $(DLL)

$(TARGET): $(TARGET_SRC)
	$(CC) -o $(TARGET) $(TARGET_SRC) $(DLL_LIB)

$(DLL):
	$(CC) /LD $(DLL_SRC)

clean:
	rm *.obj *.exe
