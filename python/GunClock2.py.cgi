#!/usr/local/bin/python

import cgi

form = cgi.FieldStorage()
if ( "clockSize" in form ) :
  clockSize = int(form["clockSize"].value)
else :
  clockSize = 20 

from GunClock import GunClock
gunclock = GunClock(clockSize)


html = '''Content-Type: text/html

<html>
<head>
  <META HTTP-EQUIV='refresh' CONTENT='60;URL=%s'>
  <title>GunClock (python)</title>
</head>
<body>
<h1>GunClock (python)</h1>
<hr>

<form method="POST" action="%s">
clock size : 
<input type="text"
 name="clockSize"
 value="%d"
 size="4">
<input type="submit" value="display">
</form>
<pre>
%s
</pre>
<hr>
%s<br>
<textarea cols=80 rows=50>
%s
</textarea>
<hr>
%s<br>
<textarea cols=80 rows=80>
%s
</textarea>
<hr>
</body>
</html>
'''

gunClockString = gunclock.toString()

#thisScript = os.path.basename(__file__)
thisScript = "GunClock2.py.cgi"
pythonSrc = open(thisScript, "r")
pythonSrcList = ""
for line in pythonSrc:
  pythonSrcList += line.replace("&","&amp;").replace("<","&lt;")
pythonSrc.close()

pythonScript = "GunClock.py"
pythonSrc2 = open(pythonScript, "r")
pythonSrcList2 = ""
for line in pythonSrc2:
  pythonSrcList2 += line.replace("&","&amp;").replace("<","&lt;")
pythonSrc2.close()

print html % (thisScript, thisScript, clockSize, gunClockString, thisScript, pythonSrcList, pythonScript, pythonSrcList2)
