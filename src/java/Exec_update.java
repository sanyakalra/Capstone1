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
import java.util.Date;
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
@WebServlet(urlPatterns = {"/Exec_update"})
public class Exec_update extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        Connection conn = null;      
        String branch ;
        String userName = "root";
        String password = "03121";
        String url = "jdbc:mysql://localhost:3306/FileTrack1";
        HttpSession session=request.getSession();
        
         try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection(url, userName, password);
             Statement s = conn.createStatement();
             String id=(String)session.getAttribute("ID");
             String prevLev="";
             int f1=0;
             String nprevLev="";
             s.executeQuery("Select level from executive where EID="+id);
             ResultSet rs;
             rs = s.getResultSet();
             while(rs.next())
             {
             int level=rs.getInt("level");
             if(String.valueOf(level)==null)
             {
             out.println("<script type=\"text/javascript\">");
             out.println("alert('You are not permitted to update the file');");
             out.println("location='Executive_Update.html';");
             out.println("</script>");  
             }
             else{
             s.executeQuery("select * from files where FID='"+request.getParameter("FileID")+"'");
             rs=s.getResultSet();
             while (rs.next())
             {
                 int l=level-1;
                 prevLev=rs.getString("Level"+l);
                 
             }
             
                
             if(prevLev==null)
                 {
                    out.println("<script type=\"text/javascript\">");
                    out.println("alert('File is still in process. Previous level is not updated');");
                    out.println("location='Executive_Update.html';");
                    out.println("</script>");  
                 }
             else{
             
              for(int i=0;i<prevLev.length();i++)
                {
                    if((prevLev.charAt(i)=='#'))
                    {
                        f1=i;
                        nprevLev=prevLev.substring(0,f1);
                        break;
                    }                
                }
            if(nprevLev.equals("Cancelled"))
             {
                    out.println("<script type=\"text/javascript\">");
                    out.println("alert('Previous level rejected the file');");
                    out.println("location='Executive_Update.html';");
                    out.println("</script>");  
             }
             else
                 {
                 String Ustatus="";
                String status=request.getParameter("status");
                Date now=new Date();
                SimpleDateFormat ft=new SimpleDateFormat("yyyy-MM-dd");
                String remark=request.getParameter("Remark");
                Ustatus=status+"#"+ft.format(now)+"#"+remark;
                s.executeUpdate("update files  set Level"+level+"= '"+Ustatus+"' where FID='"+request.getParameter("FileID")+"'");
                out.println("<script type=\"text/javascript\">");
                out.println("alert('Entry submitted successfully!');");
                out.println("location='Executive_Update.html';");
                out.println("</script>");
             }
             }}
             }
s.close();
 
}
         catch(MySQLIntegrityConstraintViolationException exc){
         out.println("<script type=\"text/javascript\">");
   out.println("alert('Invalid');");
   out.println("location='Executive_Update.html';");
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

   

