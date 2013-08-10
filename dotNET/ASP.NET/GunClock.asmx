<%@ WebService Language="C#" Class="GunClockWebService" %>

using System;
using System.Web.Services;
//using System.Globalization;

[WebService(Namespace="http://www.gunman.com/gunman/")]
public class GunClockWebService {

	[WebMethod]
	public String ClockImage(int size){
		GunClock gc  = new GunClock();
		gc.Newline   = "\n";
		gc.ClockSize = size;
		return gc.ClockImage;
	}
}
	