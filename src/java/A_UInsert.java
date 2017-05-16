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
@WebServlet(urlPatterns = {"/A_UInsert"})
public class A_UInsert extends HttpServlet {

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
          
           
           s.executeQuery("SELECT * FROM executive where EID = "+request.getParameter("uid"));
            ResultSet rs;
             rs = s.getResultSet();
             if(rs.next())
            {
                     out.println("<script type=\"text/javascript\">");
                     out.println("alert('ID Already Exists');");
                     out.println("location='Admin_UInsert.html';");
                     out.println("</script>");
            }
             else{
               
                     s.executeUpdate("Insert into executive (EID,Fname,Lname,Grade) VALUES ("+request.getParameter("uid")+",'"+request.getParameter("firstname")+"','"+request.getParameter("lastname")+"','"+request.getParameter("grade")+"')");
                     out.println("<script type=\"text/javascript\">");
                     out.println("alert('Inserted Successfully');");
                     out.println("location='Admin_Update.html';");
                     out.println("</script>");
                    }
           
s.close();
 
}
         catch(MySQLIntegrityConstraintViolationException exc){
         out.println("<script type=\"text/javascript\">");
   out.println("alert('Invalid Data');");
   out.println("location='Admin_UInsert.html");
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
        doGet(request, response);
    }

    

}
