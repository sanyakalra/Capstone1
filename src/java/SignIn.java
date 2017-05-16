/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
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
@WebServlet(urlPatterns = {"/SignIn"})
public class SignIn extends HttpServlet {

     @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
      PrintWriter out = response.getWriter();
        Connection conn = null;      
        String branch ;
        String userName = "root";
        String password = "root";
        String url = "jdbc:mysql://localhost:3306/FileTrack1";
        HttpSession session=request.getSession();
         try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection(url, userName, password);
            Statement s = conn.createStatement();
             
             
           // Executive
           if(request.getParameter("ue").equals("Executive/Teacher"))
           {
           s.executeQuery("SELECT * FROM executive where username = '"+request.getParameter("username")+"'");
            ResultSet rs;
             rs = s.getResultSet();
             if(!rs.next())
            {
                     out.println("<script type=\"text/javascript\">");
                     out.println("alert('Username does not exist');");
                     out.println("location='SignIn.html';");
                     out.println("</script>");
            }
             s.executeQuery("SELECT * FROM executive where username = '"+request.getParameter("username")+"'");
             rs = s.getResultSet();
             while(rs.next()){
                String pass= rs.getString("Password");
                String fname = rs.getString("Fname");
                String lname = rs.getString("Lname");
                 String eid=rs.getString("EID");
                 String lev= rs.getString("level");
                if(pass.equals(request.getParameter("password")))
                       {
                            if(lev==null){
                     session.setAttribute("ID", eid);
                     
                     
                     out.println("<script type=\"text/javascript\">");
                     out.println("alert('Welcome ');");
                     out.println("</script>");
                     ServletContext context= getServletContext();
                     RequestDispatcher rd= context.getRequestDispatcher("/U_Checkstatus");
                     rd.forward(request, response);
                    
                            }
                            else{
                             session.setAttribute("ID", eid);
                             session.setAttribute("LEV",lev);
                             ServletContext context2= getServletContext();
                             RequestDispatcher rd2;
                             if(lev.equals("1") || lev.equals("2")){
                                 out.println("Entered");
                              rd2= context2.getRequestDispatcher("/E_dept_regis");
                                rd2.forward(request, response);
                             }
                             else if(lev.equals("3@6@8") || lev.equals("4@9")){
                              rd2= context2.getRequestDispatcher("/E_acfa_subhash");
                                rd2.forward(request, response);
                             }
                             else if(lev.equals("5") || lev.equals("7") || lev.equals("10")){
                              rd2= context2.getRequestDispatcher("/E_acfa_subhash");
                                rd2.forward(request, response);
                             }
                            }
                       }
                else
              {
                  out.println("<script type=\"text/javascript\">");
                     out.println("alert('Username and password does not match');");
                     out.println("location='SignIn.html';");
                     out.println("</script>");
              }
                
           } 
           }
           
           //Admin
           else if(request.getParameter("ue").equals("Administrator"))
           {
                s.executeQuery("SELECT * FROM admin where username = '"+request.getParameter("username")+"'");
                 ResultSet rs;
                rs = s.getResultSet();
                if(!rs.next())
                {
                     out.println("<script type=\"text/javascript\">");
                     out.println("alert('Username does not exist');");
                     out.println("location='SignIn.html';");
                     out.println("</script>");
                }
                s.executeQuery("SELECT * FROM admin where username = '"+request.getParameter("username")+"'");
             rs = s.getResultSet();
             while(rs.next()){
                String pass= rs.getString("Password");
                String fname = rs.getString("Fname");
                String lname = rs.getString("Lname");
                 String aid=  rs.getString("aid");
                if(pass.equals(request.getParameter("password")))
                       {
                            
                     session.setAttribute("ID", aid);
                     out.println("<script type=\"text/javascript\">");
                     out.println("alert('Welcome Admin');");
                     out.println("</script>");
                     ServletContext context1= getServletContext();
                     RequestDispatcher rd1= context1.getRequestDispatcher("/A_Checkstatus");
                     rd1.forward(request, response);
                       }
                     else
                         {
                     out.println("<script type=\"text/javascript\">");
                     out.println("alert('Username and password does not match');");
                     out.println("location='SignIn.html';");
                     out.println("</script>");
                        }
           }
           }
           
s.close();
 
}
         catch(MySQLIntegrityConstraintViolationException exc){
         out.println("<script type=\"text/javascript\">");
   out.println("alert('Invalid Data');");
   out.println("location='SignIn.html");
   out.println("</script>");
         }
         catch (Exception e) {
out.println(e.toString());
} finally {
out.close();
if (conn != null) {
                try {
                    conn.close();
                    out.println("Database connection terminated");
                } catch (Exception e) {
                }
            }
 
        }  
    }

   
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request,response);
      
    }

}
