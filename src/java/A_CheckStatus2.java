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
@WebServlet(urlPatterns = {"/A_CheckStatus2"})
public class A_CheckStatus2 extends HttpServlet {

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
            Statement t = conn.createStatement();
            Statement u = conn.createStatement();
            String id=(String)session.getAttribute("ID"); 
            s.executeQuery("SELECT * FROM files where FID = '" +  request.getParameter("FileID") + "'");
            ResultSet rs,r,r1,r2;
            int eid;
             rs = s.getResultSet();
            while (rs.next()) {
                String[] lev=new String[11];                
                lev[0] = rs.getString("Level0");
                lev[1] = rs.getString("Level1");
                lev[2] = rs.getString("Level2");
                lev[3] = rs.getString("Level3");
                lev[4] = rs.getString("Level4");
                lev[5] = rs.getString("Level5");
                lev[6] = rs.getString("Level6");
                lev[7] = rs.getString("Level7");
                lev[8] = rs.getString("Level8");
                lev[9] = rs.getString("Level9");
                lev[10] = rs.getString("Level10");
                
                t.executeQuery("Select ID from files where FID= '"+request.getParameter("FileID")+"'");
                r1=t.getResultSet();
                while(r1.next())
                {
                    int did=r1.getInt("ID");
                    lev[0]="Initiator id: "+did+"\n    "+lev[0].replace("#","\n    ");
                }
                               
                for(int i=1;i<=10;i++){
                   
                     if(i==6 || i==8){
                        u.executeQuery("Select EID from executive where Level='3@6@8'");
                        r2=u.getResultSet();
                        while(r2.next()){
                        eid=r2.getInt("EID");
                        if(lev[i]!=null)
                    {
                      lev[i]="Id: "+eid+"\n    "+lev[i].replace("#","\n    ");
                    }
                    else
                    {
                      lev[i]="Id: "+eid+"\n    Still in process";
                       
                    }}
                    }
                    else if(i==9){
                        u.executeQuery("Select EID from executive where Level='4@9'");
                        r2=u.getResultSet();
                        while(r2.next()){
                        eid=r2.getInt("EID");
                        if(lev[i]!=null)
                    {
                      lev[i]="Id: "+eid+"\n    "+lev[i].replace("#","\n    ");
                    }
                    else
                    {
                      lev[i]="Id: "+eid+"\n    Still in process";
                       
                    }}
                    }
                    else{
                    t.executeQuery("Select EID from executive where Level="+i);
                    r=t.getResultSet();
                    while(r.next())
                    {
                    eid=r.getInt("EID");                  
                    if(lev[i]!=null)
                    {
                      lev[i]="Id: "+eid+"\n    "+lev[i].replace("#","\n    ");
                    }
                    else
                    {
                      lev[i]="Id: "+eid+"\n    Still in process";
                       
                    }
                    }}
               } 
                
                data.add(lev[0]);
                data.add(lev[1]);
                data.add(lev[2]);
                data.add(lev[3]);
                data.add(lev[4]);
                data.add(lev[5]);
                data.add(lev[6]);
                data.add(lev[7]);
                data.add(lev[8]);
                data.add(lev[9]);
                data.add(lev[10]);
                
        
}
    RequestDispatcher dispatcher = request.getRequestDispatcher("Admin_CheckStatus2.jsp");
        request.setAttribute("FileID", request.getParameter("FileID"));// set your String value in the attribute
        request.setAttribute("Levels",data);
        
        dispatcher.forward( request, response );

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

   
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

 

}
