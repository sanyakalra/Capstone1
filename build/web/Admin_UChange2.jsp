<%-- 
    Document   : Admin_UChange2
    Created on : Nov 30, 2015, 12:17:43 PM
    Author     : Sanya
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Admin_UChange2</title>
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
a#log
{
  color:white;
  position:absolute;
  right: 100px;
  top: 100px;
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

</style>
</head>

<body>
<div id="header">
<h1>Administrator Module</h1>
</div>
<a id="log" href="LogOut">LOG OUT </a> 
<ul >
  <li><a href="A_Checkstatus" style="text-decoration:none;">Check Status</a></li>
   <li><a href="A_History" style="text-decoration:none;">History</a></li>
  <li style="color:#708090"><a href="Admin_Update.html" style="text-decoration:none;">Update Database</a></li>
</ul>
  
<form action="A_UChange2" method="Post" style="margin-top:30px">
  First Name<br>
  <input type="text" name="firstname" pattern="^[A-Za-z]+$" title="Only letters allowed" value="<%=request.getAttribute("Fname")%>" required><br><br>
  Last Name<br>
  <input type="text" name="lastname" pattern="^[A-Za-z]+$" title="Only letters allowed" required value="<%=request.getAttribute("Lname")%>"><br><br>
  Grade<br>
  <input type="text" name="grade" pattern="^[A-Z]{1,2}$" title="Maximum 2 letters allowed" required value="<%=request.getAttribute("Grade")%>"><br><br>

  <input type="submit" name="Save">
</form>



<div id="footer">
Copyright © pec.ac.in
</div>

</body>
</html>