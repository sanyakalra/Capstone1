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
import java.util.ArrayList;
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
@WebServlet(urlPatterns = {"/E_acfa_subhash"})
public class E_acfa_subhash extends HttpServlet {

    
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
             
             String l=(String)session.getAttribute("LEV");
             
             ResultSet rs;
             if(l.equals("3@6@8"))
             {
              s.executeQuery("select * from files where (Level2 IS NOT NULL AND Level2 NOT LIKE '%Cancelled%' AND Level3 IS NULL) OR (Level5 IS NOT NULL AND Level5 NOT LIKE '%Cancelled%' AND Level6 IS NULL) OR (Level7 IS NOT NULL AND Level7 NOT LIKE '%Cancelled%' AND Level8 IS NULL) ");
             rs=s.getResultSet();
                 while (rs.next())
                {
                    String fid = rs.getString("filen");
                    data1.add(fid);    
                }
                 
                RequestDispatcher dispatcher = request.getRequestDispatcher("file_pending.jsp");
                // set your String value in the attribute
                request.setAttribute("Drop_File",data1);
                dispatcher.forward( request, response );     
                rs.close();
                s.close();}          
             
                
             else if(l.equals("4@9"))
             {
              s.executeQuery("select * from files where (Level3 IS NOT NULL AND Level3 NOT LIKE '%Cancelled%' AND Level4 IS NULL) OR (Level8 IS NOT NULL AND Level8 NOT LIKE '%Cancelled%' AND Level9 IS NULL)");
             rs=s.getResultSet();
                 while (rs.next())
                {
                    String fid = rs.getString("filen");
                    data1.add(fid);    
                }
                 
                RequestDispatcher dispatcher = request.getRequestDispatcher("file_pending.jsp");
                // set your String value in the attribute
                request.setAttribute("Drop_File",data1);
                dispatcher.forward( request, response );     
                rs.close();
                s.close();
             }   
              else if(l.equals("5") || l.equals("7") ||l.equals("10"))
             {
                 int al=Integer.valueOf(l);
                 int al2=al-1;
              s.executeQuery("select * from files where (Level"+al2+" IS NOT NULL AND Level"+al2+" NOT LIKE '%Cancelled%' AND Level"+al+" IS NULL)");
             rs=s.getResultSet();
                 while (rs.next())
                {
                    String fid = rs.getString("filen");
                    data1.add(fid);    
                }
                 
                RequestDispatcher dispatcher = request.getRequestDispatcher("file_pending.jsp");
                // set your String value in the attribute
                request.setAttribute("Drop_File",data1);
                dispatcher.forward( request, response );     
                rs.close();
                s.close();} 
 
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
        doGet(request,response);
    }

    

}
