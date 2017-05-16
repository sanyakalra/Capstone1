/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.Security;
import java.util.Properties;
import java.sql.*;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.security.AccessController;
import java.security.Provider;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;


/**
 *
 * @author Sanya
 */
@WebServlet(urlPatterns = {"/BackgroundJobManager"})
    @WebListener
public class BackgroundJobManager {
 
   private ScheduledExecutorService scheduler;
   String email="",nextStatus="",FileID="";
   int lev=0;

        
   
  public BackgroundJobManager() {
         }
  
  public void mail_time(String email,String FileID,int lev){
   
        scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(new Runnable(){ public void run() {
            System.out.println("In run");
//        // Do your daily job here.
           Connection conn = null;  
            String userName = "root";
            String password = "root";
            String url = "jdbc:mysql://localhost:3306/FileTrack1";
      try{
         Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn = DriverManager.getConnection(url, userName, password);
            Statement s = conn.createStatement();   
            ResultSet rs;
            System.out.println("In database");
            s.executeQuery("select Level"+lev+" from files where STRCMP(filen,'"+FileID+"')=0");
            rs=s.getResultSet();
            while(rs.next()){
            nextStatus=rs.getString("Level"+lev);
            System.out.println("level "+lev+" email "+email+" FileID "+FileID+" nextstatus "+nextStatus);
            }
            
           
        }catch(Exception e){
            System.out.println(e.toString());
        }
            if(nextStatus==null)
                try {
//                   System.out.println("Started "+email+" "+lev);
                   
                    GmailSender gs= new GmailSender("filetrackingcapstone@gmail.com", "FileTrack2016");
                    System.out.println("In gmail");
                    gs.sendMail("File Tracking System", "You have not updated the status of the file: "+FileID, "filetrackingcapstone@gmail.com", email);
                } catch (Exception ex) {
                    Logger.getLogger(BackgroundJobManager.class.getName()).log(Level.SEVERE, null, ex);
                }
        
            }
                else{
                System.out.println("Stopped "+nextStatus);
                    scheduler.shutdownNow();
           }
       
        //scheduler.shutdownNow();
       
    }}, 5, 5, TimeUnit.MINUTES);
  
}
}
class GmailSender extends javax.mail.Authenticator{
    private String mailhost = "smtp.gmail.com";
    private String user;
    private String password;
    private Session session;
    static {
        Security.addProvider(new JSSEProvider());
    }
    public GmailSender(final String user, final String password) {
        this.user = user;
        this.password = password;
        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.host", mailhost);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");
        props.put("mail.smtp.starttls.enable", "true");

        props.setProperty("mail.smtp.quitwait", "false");
        session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, password);
            }
        });
        session = Session.getDefaultInstance(props, this);
    }

    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(user, password);
    }

    public synchronized void sendMail(String subject, String body,
                                      String sender, String recipients) throws Exception {
        MimeMessage message = new MimeMessage(session);
        DataHandler handler = new DataHandler(new ByteArrayDataSource(
                body.getBytes(), "text/plain"));
        message.setSender(new InternetAddress(sender));
        message.setSubject(subject);
        message.setDataHandler(handler);

        if (recipients.indexOf(',') > 0)
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(recipients));
        else
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(
                    recipients));
        Transport.send(message);
    }

    public class ByteArrayDataSource implements DataSource {
        private byte[] data;
        private String type;

        public ByteArrayDataSource(byte[] data, String type) {
            super();
            this.data = data;
            this.type = type;
        }

        public ByteArrayDataSource(byte[] data) {
            super();
            this.data = data;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getContentType() {
            if (type == null)
                return "application/octet-stream";
            else
                return type;
        }

        public InputStream getInputStream() throws IOException {
            return new ByteArrayInputStream(data);
        }

        public String getName() {
            return "ByteArrayDataSource";
        }

        public OutputStream getOutputStream() throws IOException {
            throw new IOException("Not Supported");
        }
    }
}

class JSSEProvider extends Provider {

    public JSSEProvider() {
        super("HarmonyJSSE", 1.0, "Harmony JSSE Provider");
        AccessController.doPrivileged(new java.security.PrivilegedAction<Void>() {
            public Void run() {
                put("SSLContext.TLS",
                        "org.apache.harmony.xnet.provider.jsse.SSLContextImpl");
                put("Alg.Alias.SSLContext.TLSv1", "TLS");
                put("KeyManagerFactory.X509",
                        "org.apache.harmony.xnet.provider.jsse.KeyManagerFactoryImpl");
                put("TrustManagerFactory.X509",
                        "org.apache.harmony.xnet.provider.jsse.TrustManagerFactoryImpl");
                return null;
            }
        });
    }
}

  

   


