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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
@WebServlet(urlPatterns = {"/E_dept_regis"})
public class E_dept_regis extends HttpServlet {

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
         ArrayList<String> data1=new ArrayList<String>();
        
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection(url, userName, password);
             Statement s = conn.createStatement();
             //String id=(String)session.getAttribute("ID");
             String prevLev="";
             int f1=0;
             String nprevLev="";
             String l=(String)session.getAttribute("LEV");
             //int level= Integer.getInteger(l);
             ResultSet rs;
////DEPARTMENT
if(l.equals("1")){
    
                 
             s.executeQuery("select * from files where Level1 IS NULL");
             rs=s.getResultSet();
                 while (rs.next())
                {
                    String fid = rs.getString("FID");
                    data1.add(fid);    
                }
             
                RequestDispatcher dispatcher = request.getRequestDispatcher("Dept_regis.jsp");
                // set your String value in the attribute
                request.setAttribute("Drop_File",data1);
                dispatcher.forward( request, response );     
                rs.close();
                s.close();
                
             }
////REGISTRAR
else if(l.equals("2")){
             s.executeQuery("select * from files where Level2 IS NULL AND Level1 IS NOT NULL AND Level1 NOT LIKE '%Cancelled%'");
             rs=s.getResultSet();
                while (rs.next())
                {
                     String fid = rs.getString("filen");
                     data1.add(fid);    
                }
             
                RequestDispatcher dispatcher = request.getRequestDispatcher("Dept_regis.jsp");
                // set your String value in the attribute
                request.setAttribute("Drop_File",data1);
                dispatcher.forward( request, response );     
                rs.close();
                s.close();
             
             }
         
 
}
         catch(MySQLIntegrityConstraintViolationException exc){
         out.println("<script type=\"text/javascript\">");
         out.println("alert('Invalid');");
         out.println("location='SignIn.html';");
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
