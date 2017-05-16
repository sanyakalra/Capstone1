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
@WebServlet(urlPatterns = {"/E_onlyupdate"})
public class E_onlyupdate extends HttpServlet {

 @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       PrintWriter out = response.getWriter();
        Connection conn = null;      
        String branch ;
        String userName = "root";
        String password = "root";
        int level=0;
        String email="";
        String url = "jdbc:mysql://localhost:3306/FileTrack1";
        HttpSession session=request.getSession();
        String fn= (String)session.getAttribute("FileID");
        
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection(url, userName, password);
             Statement s = conn.createStatement();
             Statement s1=conn.createStatement();
             //String id=(String)session.getAttribute("ID");
             String prevLev="";
             int f1=0;
             String nprevLev="";
             String l=(String)session.getAttribute("AL");
             int lev=Integer.valueOf(l);
             ResultSet rs,rs1;


            s.executeQuery("select * from files where filen='"+fn+"'");
             rs=s.getResultSet();
             while (rs.next())
             {
                 prevLev=rs.getString("Level"+(lev-1));    
             }
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
                    out.println("</script>");  
             }
            
            else
            {
                String Ustatus="";
                String status=request.getParameter("status");
                String filename=fn;
                Date now=new Date();
                SimpleDateFormat ft=new SimpleDateFormat("yyyy-MM-dd");
                String remark=request.getParameter("Remark");
                Ustatus=status+"#"+ft.format(now)+"#"+remark;
                s.executeUpdate("update files  set Level"+l+"= '"+Ustatus+"' where STRCMP (filen,'"+filename+"') = 0");
               level=Integer.parseInt(l);
                if(level!=10 && !Ustatus.contains("Cancelled"))
                {
                    level=level+1;
                    String query="select Email from executive where Level LIKE '%"+level+"%'";
                s1.executeQuery(query);
                 rs1=s1.getResultSet();
                 while (rs1.next())
                {
                    email = rs1.getString("Email");  
                }
                BackgroundJobManager b= new BackgroundJobManager();
                b.mail_time(email,filename,level+1);
                }
                s1.close();
                s.close();
              
                
            }
                ServletContext context= getServletContext();
                RequestDispatcher rd= context.getRequestDispatcher("/E_acfa_subhash");
                  out.println("<script type=\"text/javascript\">");
                out.println("alert('Entry submitted successfully!');");
                out.println("</script>");
                rd.forward(request, response);

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
