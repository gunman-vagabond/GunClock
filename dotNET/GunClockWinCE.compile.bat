rem set CFPath=C:\Program Files\Microsoft.NET\SDK\CompactFramework\v2.0\WindowsCE
set CFPath=C:\Program Files\Microsoft.NET\SDK\CompactFramework\v3.5\WindowsCE
set NETFRAMEWORK=C:\WINDOWS\Microsoft.NET\Framework\v2.0.50727

%NETFRAMEWORK%\csc.exe  /out:GunClockWinCE.exe /target:winexe GunClockWinCE.cs /win32icon:icon1.ico /d:DEBUG /noconfig /nostdlib /lib:"%CFPath%" /r:"%CFPath%\system.dll";"%CFPath%\system.drawing.dll";"%CFPath%\system.windows.forms.dll";"%CFPath%\mscorlib.dll" /reference:GunClock.dll 
