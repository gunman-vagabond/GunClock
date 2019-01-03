package com.gunman;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.io.*;

//import com.gunman.*;

//@WebServlet(name="GunClock",urlPatterns={"/GC"})
@WebServlet("/gunclock")
public class GunClock extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        GunClockBean gcBean = new GunClockBean();

        gcBean.setStrNewline("\n");

        if(request.getParameter("clockSize") != null ) {
            gcBean.setClockSize(Integer.parseInt(request.getParameter("clockSize")) );
        } else {
            gcBean.setClockSize(20);
        }

        response.setContentType("text/html");
        response.setIntHeader("Refresh",60);

        PrintWriter out = response.getWriter();

        out.println("<html><head><title>GunClock</title></head>\n"
                  + "<body><h1>GunClock</h1><hr>\n"
                  + "<center><pre>\n"
                  + gcBean.getGunClock()
                  + "</pre></center></body></html>\n"
        );

    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}

