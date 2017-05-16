<%-- 
    Document   : Admin_History
    Created on : Nov 30, 2015, 6:52:09 PM
    Author     : Sanya
--%>

<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>History</title>
<style>
#header{
         font-family:"Palatino Linotype", "Book Antiqua", Palatino, serif;
         text-align:center;
      	 background-color:#000080;
    	 color:white;
    	 padding:20px;
	 margin-top:0px;
       }
ul {
    line-height:40px;
    font-size:20px;
    left:0px;
    float: left;
    list-style-type: none;
    height:435px;	      
}
li { 
    margin:5px;
    padding: 8px;
    width:170px;
    background-color :#33b5e5;
    color: #ffffff;
    box-shadow: 0 1px 3px rgba(0,0,0,0.12), 0 1px 2px rgba(0,0,0,0.24);
}
#footer {
    background-color:#000080;
    color:white;
    clear:both;
    position:relative;
    text-align:center;
    padding:10px;	 	 
}
table, th, td {
    border: 1px solid black;
    border-collapse: collapse;
    margin-top:20px;
    margin-left:170px;
}
th, td {
    padding: 5px;
}
tr:nth-child(even) {
    background-color: #eee;
}
a#log
{
  color:white;
  position:absolute;
  right: 100px;
  top: 100px;
}

</style>
</head>

<body>
<div id="header">
<h1>Administrator Module</h1>
</div>
<a id="log" href="LogOut">LOG OUT </a> 
<ul>
  <li><a href="A_Checkstatus" style="text-decoration:none;">Check Status</a></li>
  <li style="color:#708090">History</li>
  <li><a href="Admin_Update.html" style="text-decoration:none;">Update Database</a></li>  
</ul>
 
    

<table style="width:80%">
  <tr>
    <th>File ID</th>
    <th>Status</th>		
    <th>Date</th>
   
  </tr>
   <% Iterator <String> itr;%>
    <%List data =(List)request.getAttribute("Hist");
    for (itr = data.iterator();
    itr.hasNext();){%>
    <tr>
        <td><%= (String)(itr.next())%></td>
        <td><%= (String)(itr.next())%></td>
         <td><%= (String)(itr.next())%></td>
    </tr>
  <%} %>
</table>
  

<div id="footer">
Copyright @ pec.ac.in
</div>

</body>

</html>

