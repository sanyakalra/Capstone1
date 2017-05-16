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
@WebServlet(urlPatterns = {"/Dispatch"})
public class Dispatch extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        Connection conn = null;     
        String userName = "root";
        String password = "root";
        String url = "jdbc:mysql://localhost:3306/FileTrack1";
        HttpSession session=request.getSession();  
        
        
         try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection(url, userName, password);
            Statement s = conn.createStatement();
            Statement s1=conn.createStatement();
            String id=(String)session.getAttribute("ID"); 
            s.executeQuery("SELECT * from files where FID ='"+request.getParameter("FileID")+"'");
            ResultSet rs;
            rs = s.getResultSet();
             if(rs.next())
             {
                out.println("<script type=\"text/javascript\">");
                out.println("alert('File Id already exists');");
                out.println("location='User_Dispatch1.html';");
                out.println("</script>");   
             }
             else{
            Date now=new Date();
            SimpleDateFormat ft=new SimpleDateFormat("yyyy-MM-dd"); 
            s.executeUpdate("INSERT INTO files (FID,ID,LEVEL0) VALUES ('"+request.getParameter("FileID")+"',"+id+",'Dispatched#"+ft.format(now)+"#"+request.getParameter("Remark")+"')");
            out.println("<script type=\"text/javascript\">");
            out.println("alert('Entry submitted successfully!');");
            out.println("location='User_Dispatch1.html';");
            out.println("</script>");
             }
s.close();
s1.close();
 
}
         catch(MySQLIntegrityConstraintViolationException exc){
         out.println("<script type=\"text/javascript\">");
         out.println("alert('File Id already exists');");
         out.println("location='User_Dispatch1.html';");
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
