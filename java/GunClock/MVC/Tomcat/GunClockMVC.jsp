<%@ page import="gunman.GunClockBean" contentType="text/html; charset=EUC-JP" %>

<%-- for useBean --%>
<%--  comment out -----
<jsp:useBean id="gcBean"   scope="request" class="gunman.GunClockBean" />
------------------- --%>
<jsp:useBean id="gcBean" scope="session" class="gunman.GunClockBean" />

<html>
<head>
<META HTTP-EQUIV='refresh' CONTENT='60;URL=/GunClockMVC.jsp'> 
<title>GunClock (JSP)</title>
</head>

<body>

<h1>GunClock (MVC model)</h1>
<hr>

<!--
<form method="POST" action="http://www.mycgiserver.com/servlet/gunman.GunClockMVC">
-->

<form method="POST" action="http://127.0.0.1:8080/GunClock/servlet/gunman.GunClockMVC">
clock size : <input type="text" name="clockSize" value=<jsp:getProperty name="gcBean" property="clockSize"/> size="4">
<input type="submit" value="Resize">
</form>

<center>
<pre>
<%-- if (gcBean != null ) { --%>
    <jsp:getProperty name="gcBean" property="gunClock" />
<%-- } --%>
</pre>
</center>
<hr>


</body>
</html>
