package gunman;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.server.JSONP;

@Path("/gunclock/")
public class REST_GunClock {
	
	static String getGunClock(String newline, int clockSize){
		GunClockBean gcb = new GunClockBean();
		gcb.setStrNewline(newline);
		gcb.setClockSize(clockSize);
		String gunclock = gcb.getGunClock();
		return gunclock;
	}

@Path("text/{clocksize}")
	// This method is called if TEXT_PLAIN is request
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getGunClockText(@PathParam("clocksize") final int pClocksize) {
		String gunclock = getGunClock("\n", pClocksize);
		return gunclock;
	}
	

	// This method is called if HTML is request   ★ブラウザアクセスは、こちら（この記述がなければ、TEXT_PLAIN
@Path("html/{clocksize}")
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String getGunClockHtmlWithClocksize(@PathParam("clocksize") final int pClocksize) {
		String gunclock = getGunClock("\n", pClocksize);
		return "<html> " + "<title>" + "GunClock-REST(html)" + "</title>"
				+ "<body><pre>" + gunclock + "</body></pre>" + "</html> ";
	}  

@Path("json/{clocksize}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, String> getGunClockJSON(@PathParam("clocksize") final int pClocksize) {
		String gunclock = getGunClock("\n", pClocksize);
		String line[] = gunclock.split("\n");

		Map<String, String> map = new HashMap<>();
		String s = "";
		for ( int i=0; i<line.length; i++){
			if ( i != 0 ) {
				s += ",";
			}
			s += line[i];
		}
		map.put("message", s);
		return map;
	}

@Path("json2/{clocksize}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<String> getGunClockJSON2(@PathParam("clocksize") final int pClocksize) {
		String gunclock = getGunClock("\n", pClocksize);
		String line[] = gunclock.split("\n");
		
		List<String> list = new ArrayList<String>();
		for ( int i=0; i<line.length; i++){
			list.add(line[i]);
		}
		return list;
	}


	public class JaxbBean {
		private String message;
		public JaxbBean() {}
		public JaxbBean(final String message) {
			this.message = message;
		}
		public String getMessage() {
			return message;
		}
		public void setMessage(final String message) {
			this.message = message;
		}
	}

@Path("jsonp/{clocksize}")
	@GET
	@JSONP(callback = "callback", queryParam = "callback")
	@Produces({"application/javascript"})
	//public JaxbBean getGunClockJSON(@PathParam("clocksize") final int pClocksize, @QueryParam("callback") @DefaultValue("callback") String callback) {
	public JaxbBean getGunClockJSONP(@PathParam("clocksize") final int pClocksize) {
		String gunclock = getGunClock("\n", pClocksize);
		String line[] = gunclock.split("\n");

		String s = "";
		for ( int i=0; i<line.length; i++){
			if ( i != 0 ) {
				s += ",";
			}
			s += line[i];
		}

		return new JaxbBean(s);
	}

}