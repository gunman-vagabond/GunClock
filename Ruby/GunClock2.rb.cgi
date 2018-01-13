#!/usr/local/bin/ruby

require File.dirname(__FILE__) + "/GunClock"

require "cgi"
cgi = CGI.new

if ( cgi.has_key?("clockSize") ) then
  $gunClockSize = cgi["clockSize"].to_i
else
  $gunClockSize = 15
end

gunClock = GunClock.new($gunClockSize)
gunClockString = gunClock.toString()

print <<"__EOF__"
Content-Type: text/html

<html>
<head>
  <META HTTP-EQUIV='refresh' CONTENT='60;URL=#{$0}'>
  <title>GunClock (Ruby)</title>
</head>
<body>
<h1>GunClock (Ruby)</h1>
<hr>

<form method="POST" action="#{$0}">
clock size :
<input type="text"
 name="clockSize"
 value="#{$gunClockSize}"
 size="4">
<input type="submit" value="display">
</form>
<pre>
#{gunClockString}
</pre>
<hr>
#{$0}<br>
<textarea cols=80 rows=50>
__EOF__

File.open("#{$0}",  "r") do |f|
  while line = f.gets
    puts line.gsub(/&/, "&amp;").gsub(/</, "&lt;")
  end
end

print <<"__EOF__"
</textarea>
<hr>
GunClock.rb<br>
<textarea cols=80 rows=80>
__EOF__

File.open("GunClock.rb",  "r") do |f|
  while line = f.gets
    puts line.gsub(/&/, "&amp;").gsub(/</, "&lt;")
  end
end

print <<"__EOF__"
</textarea>
<hr>
</body>
</html>
__EOF__

