/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.System.out;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Sanya
 */
@WebServlet(name = "LogOut", urlPatterns = {"/LogOut"})
public class LogOut extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       
                     response.setContentType("text/html;charset=UTF-8");
                     PrintWriter out = response.getWriter();
                     HttpSession session=request.getSession();
                     session.invalidate();
                     out.println("<script type=\"text/javascript\">");
                     out.println("alert('Successfully logged out!');");
                     out.println("location='Home.html';");
                     out.println("</script>");
             
       
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request,response);
       //request.getSession().invalidate();
        //response.sendRedirect(request.getContextPath() + "/Home.html");
    }

   
}
