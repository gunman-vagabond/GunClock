<%@ Import Namespace="System" %>
<html>
<head>

<title>GunClock - ASP.NET</title>

<script language="C#" runat="server">
 void Submit_Click(Object sender, EventArgs e){
	GunClock gc     = new GunClock();
	gc.Newline      = "<br>";

	if(size.Value.Equals("")) gc.ClockSize = 20;
	else gc.ClockSize = Int32.Parse(size.Value);
	clockLabel.Text = gc.ClockImage;
 }
</script>
</head>
<body>
<h1>GunClock - ASP.NET</h1>
<form runat="server">
<input type="text" name="size" id="size" text="" runat="server"/>
<input type="submit" onserverclick="Submit_Click" value="reload" runat="server"/>
</form>
<pre>
<asp:label id="clockLabel" runat="server"/>
</pre>
</body>
</html>