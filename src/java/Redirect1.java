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
@WebServlet(urlPatterns = {"/Redirect1"})
public class Redirect1 extends HttpServlet {

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
             String fid=request.getParameter("FileID");
             ResultSet rs;
             String al="",al2="";
             s.execute("Select * from files where STRCMP(filen,'"+request.getParameter("FileID")+"')=0");
             rs=s.getResultSet();
             while(rs.next()){
                String[] lev=new String[9];                
                lev[0] = rs.getString("Level2");
                lev[1] = rs.getString("Level3");
                lev[2] = rs.getString("Level4");
                lev[3] = rs.getString("Level5");
                lev[4] = rs.getString("Level6");
                lev[5] = rs.getString("Level7");
                lev[6] = rs.getString("Level8");
                lev[7] = rs.getString("Level9");
                lev[8] = rs.getString("Level10");
                
                if(lev[0]!=null && lev[1]==null){
                    al="3";
                    
                }
                else if(lev[1]!=null && lev[2]==null){
                    if(lev[1].contains("Mr Subhash Gupta")){
                    al="4";
                    
                    }
                }
                else if(lev[2]!=null && lev[3]==null){
                    al="5";
                    
                }
                else if(lev[3]!=null && lev[4]==null){
                    al="6";
                    
                }
                else if(lev[4]!=null && lev[5]==null){
                    al="7";
                    
                }
                else if(lev[5]!=null && lev[6]==null){
                    al="8";
                    
                }
                else if(lev[6]!=null && lev[7]==null){
                    al="9";
                    
                }
                else if(lev[7]!=null && lev[8]==null){
                    al="10";
                    
                }
             }  
        
        session.setAttribute("AL", al);
        session.setAttribute("FileID", fid);
        if(al.equals("3")){
            
        RequestDispatcher dispatcher = request.getRequestDispatcher("acfa_3.jsp");
                request.setAttribute("FileID", fid);
                dispatcher.forward( request, response );   
        }
        else if(al.equals("4")){
            
                ServletContext context= getServletContext();
                request.setAttribute("FileID", fid);             
                RequestDispatcher rd= context.getRequestDispatcher("/E_subhash");
                rd.forward(request, response);
                
        }
        else if(al.equals("5") || al.equals("6") || al.equals("7") || al.equals("8") || al.equals("9") || al.equals("10")){
            
        RequestDispatcher dispatcher = request.getRequestDispatcher("only_update.jsp");
                request.setAttribute("FileID", fid);
                dispatcher.forward( request, response );   
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
