<%@ page import="gunman.GunClockBean" contentType="text/html; charset=EUC-JP" %>

<%-- for useBean --%>
<jsp:useBean id="GunClock" scope="session" class="gunman.GunClockBean" />
<jsp:setProperty name="GunClock" property="clockSize" value="20"/>
<jsp:setProperty name="GunClock" property="strNewline" value="<br>"/>

<%-- for script --%>
<%
    GunClockBean gcBean = new GunClockBean();
    gcBean.setClockSize(15);
    gcBean.setStrNewline("\n");
%>

<html>
<head>
<META HTTP-EQUIV='refresh' CONTENT='60;URL=./GunClock.jsp'> 
<title>GunClock (JSP)</title>
</head>

<body>

<h1>GunClock (JSP)</h1>
<hr>

■ useBean 
<center>
<pre>
<jsp:getProperty name="GunClock" property="gunClock" />
</pre>
</center>
<hr>

■ スクリプト
<center>
<pre>
<%= gcBean.getGunClock() %>
</pre>
</center>

</body>
</html>
