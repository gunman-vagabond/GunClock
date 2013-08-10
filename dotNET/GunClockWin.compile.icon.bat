set CFPath=C:\Program Files\Microsoft.NET\SDK\CompactFramework\v2.0\WindowsCE
set NETFRAMEWORK=C:\WINDOWS\Microsoft.NET\Framework\v2.0.50727

%NETFRAMEWORK%\csc.exe  /out:GunClockWinIcon.exe /target:winexe GunClockWin.cs /win32icon:icon1.ico /reference:GunClock.dll 
