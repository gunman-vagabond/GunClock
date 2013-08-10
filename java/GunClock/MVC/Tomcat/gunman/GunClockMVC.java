package gunman;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import gunman.*;

public class GunClockMVC extends HttpServlet {

    GunClockBean gcBean = new GunClockBean();

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();

        gcBean.setStrNewline("\n");

        if(request.getParameter("clockSize") != null ) {
            gcBean.setClockSize(Integer.parseInt(request.getParameter("clockSize")) );
        }

//        request.setAttribute("gcBean",gcBean);
        session.setAttribute("gcBean",gcBean);
        RequestDispatcher rd = request.getRequestDispatcher("/GunClockMVC.jsp");
        rd.forward(request,response);

    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
