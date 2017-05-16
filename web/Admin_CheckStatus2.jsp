<%-- 
    Document   :A_CheckStatus2
    Created on : Nov 30, 2015, 4:17:21 PM
    Author     : Sanya
--%>

<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Checkstatus2</title>
<style>
#header{
         font-family:"Palatino Linotype", "Book Antiqua", Palatino, serif;
         text-align:center;
      	 background-color:#000080;
    	 color:white;
    	 padding:20px;
	 margin-top:0px;
}
h3{
    font-family:"Palatino Linotype", "Book Antiqua", Palatino, serif;
    color:#000099;
}
pre{
    font-family:"Palatino Linotype", "Book Antiqua", Palatino, serif;
    font-size: 16px;
    color: #006666;
}
ul {
    line-height:40px;
    font-size:20px;
    left:0px;
    float: left;
    list-style-type: none;
    height:1200px;	      
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
  <li style="color:#708090">Check Status</li>
  <li><a href="A_History" style="text-decoration:none;">History</a></li>
  <li><a href="Admin_Update.html" style="text-decoration:none;">Update Database</a></li>  
</ul>

    <h3>  File id : <b><i><%=request.getAttribute("FileID")%></i></b></h3>
    
<% Iterator <String> itr;%>
    <%List data =(List)request.getAttribute("Levels");
    for (itr = data.iterator();
    itr.hasNext();){%>
    <pre>

    <b>Dispatcher Level             : 
    </b><%= (String)(itr.next())%>
    
    <b>Level 1 (Department)         : 
    </b><%= (String)(itr.next())%>

    <b>Level 2 (Registrar)          : 
    </b><%= (String)(itr.next())%>

    <b>Level 3 (ACFA)               : 
    </b><%= (String)(itr.next())%>

    <b>Level 4 (Data Entry Operator): 
    </b><%= (String)(itr.next())%>

    <b>Level 5 (Sanction Officer)   : 
    </b><%= (String)(itr.next())%>

    <b>Level 6 (ACFA)               : 
    </b><%= (String)(itr.next())%>

    <b>Level 7 (Deputy Director)    : 
    </b><%= (String)(itr.next())%>

    <b>Level 8 (ACFA)               : 
    </b><%= (String)(itr.next())%>

    <b>Level 9 (Data Entry Operator): 
    </b><%= (String)(itr.next())%>

    <b>Final Level (Cashier)        :
    </b><%= (String)(itr.next())%>
    </pre>
    
  <%} %>


<div id="footer">
Copyright @ pec.ac.in
</div>

</body>
</html>
