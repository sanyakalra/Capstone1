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
@WebServlet(urlPatterns = {"/A_UChange"})
public class A_UChange extends HttpServlet {

    

    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        PrintWriter out = response.getWriter();
        Connection conn = null;      
        String branch ;
        String userName = "root";
        String password = "root";
        String url = "jdbc:mysql://localhost:3306/FileTrack1";
        HttpSession ses=request.getSession();
        String newid=(String)request.getParameter("uid");
        ses.setAttribute("changeID", newid);
         try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection(url, userName, password);
            Statement s = conn.createStatement();
            String fname="";
            String lname="";
            String grade=""; 
             
                      // Executive
          
           s.executeQuery("SELECT * FROM executive where EID = "+request.getParameter("uid"));
            ResultSet rs;
             rs = s.getResultSet();
             if(!rs.next())
            {
                     out.println("<script type=\"text/javascript\">");
                     out.println("alert('Executive does not exist');");
                     out.println("location='Admin_UChange.html';");
                     out.println("</script>");
            }
             else{
             ses.setAttribute("type", "2");
             s.executeQuery("SELECT * FROM executive where EID = "+request.getParameter("uid"));
             rs = s.getResultSet();
             while(rs.next()){
                fname = rs.getString("Fname");
                lname = rs.getString("Lname");
                grade= rs.getString("Grade");
                
           } 
             RequestDispatcher dispatcher = request.getRequestDispatcher("Admin_UChange2.jsp");
        request.setAttribute("Fname", fname);
        request.setAttribute("Lname", lname);
        request.setAttribute("Grade",grade);
        dispatcher.forward( request, response ); 
             }
           

s.close();
 
}
         catch(MySQLIntegrityConstraintViolationException exc){
         out.println("<script type=\"text/javascript\">");
   out.println("alert('Invalid Data');");
   out.println("location='Admin_UChange.html");
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
