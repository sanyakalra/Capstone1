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
@WebServlet(urlPatterns = {"/E_subhash"})
public class E_subhash extends HttpServlet {
 
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
             //String id=(String)session.getAttribute("ID");
             String prevLev="";
             String fname="",lname="",grade="",pay="",allow="";
             int id=0;
             String fid="";
             String nprevLev="";
             String l=(String)session.getAttribute("AL");
             ArrayList<String> data=new ArrayList<String>();
             
             ResultSet rs;
         String fn=(String)request.getAttribute("FileID");
            s.executeQuery("select ID from files where filen= '"+request.getAttribute("FileID")+"'");
            rs=s.getResultSet();
            while(rs.next()){
                id=rs.getInt("ID");
                fid=String.valueOf(id);
            }
            s.executeQuery("select Fname,Lname,Grade from executive where EID="+id);
            rs=s.getResultSet();
            while(rs.next()){
                fname=rs.getString("Fname")+" "+rs.getString("Lname");
                grade=rs.getString("Grade");
                data.add(fname);
            }
            s.executeQuery("select Gpay,Allowance from grade where GID='"+grade+"'");
            rs=s.getResultSet();
            while(rs.next()){
                pay=rs.getString("Gpay");
                allow=rs.getString("Allowance");
                data.add(pay);
                data.add(allow);
            }
                RequestDispatcher dispatcher = request.getRequestDispatcher("subhashsir.jsp");
                request.setAttribute("Gradeinfo", data);
                dispatcher.forward( request, response ); 
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
