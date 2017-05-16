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
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Sanya
 */
@WebServlet(urlPatterns = {"/SignUp"})
public class SignUp extends HttpServlet {

 
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
      PrintWriter out = response.getWriter();
        Connection conn = null;      
        String branch ;
        String userName = "root";
        String password = "root";
        String url = "jdbc:mysql://localhost:3306/FileTrack1";
        
         try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection(url, userName, password);
            Statement s = conn.createStatement();
            Statement t = conn.createStatement();
           
            s.executeQuery("SELECT * FROM executive where EID = "+request.getParameter("uid"));
            ResultSet rs,rs1;
             rs = s.getResultSet();
             if(!rs.next())
            {
                     out.println("<script type=\"text/javascript\">");
                     out.println("alert('Invalid Data');");
                     out.println("location='SignUp.html';");
                     out.println("</script>");
            }
            s.executeQuery("SELECT * FROM executive where EID = "+request.getParameter("uid")); 
            rs = s.getResultSet();
            while(rs.next()){
                String fname = rs.getString("Fname");
                String lname = rs.getString("Lname");
                String username=rs.getString("username");
                String email=rs.getString("Email");
                if(fname.equals(request.getParameter("firstname")) && lname.equals(request.getParameter("lastname")) && email.equals(request.getParameter("email"))){
                    s.executeQuery("Select * from executive where username='"+request.getParameter("username")+"'");
                    rs1=s.getResultSet();
                    
                    if(rs1.next())
                    {
                    out.println("<script type=\"text/javascript\">");
                     out.println("alert('Username already exists');");
                     out.println("location='SignUp.html';");
                     out.println("</script>");
                    }
                    else if(username==null){
                    s.executeUpdate("Update executive SET username='"+request.getParameter("username")+"',password='"+request.getParameter("password")+"' where EID="+request.getParameter("uid")+" and username IS NULL");
                     out.println("<script type=\"text/javascript\">");
                     out.println("alert('Submitted Successfully, Please Login for confirmation');");
                     out.println("location='SignIn.html';");
                     out.println("</script>");
                    }
                    else {
                     out.println("<script type=\"text/javascript\">");
                     out.println("alert('You are already registered');");
                     out.println("location='SignUp.html';");
                     out.println("</script>"); 
                    }
                }
                    else{
                     out.println("<script type=\"text/javascript\">");
                     out.println("alert('Invalid Data');");
                     out.println("location='SignUp.html';");
                     out.println("</script>");
                }
           }
            
           
           
s.close();
 
}
         catch(MySQLIntegrityConstraintViolationException exc){
         out.println("<script type=\"text/javascript\">");
   out.println("alert('Invalid Data');");
   out.println("location='SignUp.html");
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
