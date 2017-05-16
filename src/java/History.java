/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import static java.sql.Types.NULL;
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
@WebServlet(urlPatterns = {"/History"})
public class History extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        Connection conn = null;      
        String branch ;
        String userName = "root";
        String password = "root";
        String url = "jdbc:mysql://localhost:3306/FileTrack1";
        HttpSession session=request.getSession();
        ArrayList<String> data=new ArrayList<String>();
        
         try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection(url, userName, password);
            Statement s = conn.createStatement();
            
             String id=(String)session.getAttribute("ID"); 
           
            s.executeQuery("SELECT * FROM files where ID = "+id);
            ResultSet rs;
             rs = s.getResultSet();
            while (rs.next()) {
                String fid = rs.getString("FID");
                String l10 = rs.getString("Level10");
                String status="";
                String date="";
                int i,f1,f2,cindex;
                f1=0;f2=0;
                cindex=0;
                if(l10==null){
                   status="Not Yet Completed";
                   date=" ";
                }
                else{
                for(i=0;i<l10.length();i++)
                {
                    if((l10.charAt(i)=='#')&&(cindex==0))
                    {
                        f1=i;
                        cindex=1;
                    }
                    else if((l10.charAt(i)=='#')&&(cindex==1)){
                        f2=i;
                    }
                }
                status=l10.substring(0,f1);
                date=l10.substring(f1+1,f2);
                }                       
                data.add(fid);
                data.add(status);
                data.add(date);
                
        
}
    RequestDispatcher dispatcher = request.getRequestDispatcher("User_History.jsp");
        // set your String value in the attribute
        request.setAttribute("Hist",data);
        dispatcher.forward( request, response );     
rs.close();
s.close();
 
} catch (Exception e) {
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

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        doGet(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */

}
