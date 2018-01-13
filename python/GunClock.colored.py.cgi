#!/usr/local/bin/python

import cgi

form = cgi.FieldStorage()
if ( "clockSize" in form ) :
  clockSize = int(form["clockSize"].value)
else :
  clockSize = 20 

if ( "clockColor" in form ) : 
  clockColor = form["clockColor"].value
else : 
  clockColor = "#ffffff"

from GunClock import GunClock
gunclock = GunClock(clockSize)
gunClockString = gunclock.toString()

html = '''Content-Type: text/html

<html>
<head>
  <title>GunClock</title>
</head>
<body>

<table>
<tr><td bgcolor=%s>
<pre>%s</pre>
</td></tr>
</table>

</body>
</html>
'''

print html % (clockColor, gunClockString)
