<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<!DOCTYPE html>
<html>
<head>
<title>ExecutiveModule</title>
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
    color:black;
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
<h1>Executive Module</h1>
<a id="log" href="LogOut">LOG OUT </a> 
</div>

<ul>
  
    <li style="color:#708090">Update Status</li>  
</ul>

<form action="E_acfa_3" method="POST" style="margin:30px">

    <pre>
File id : <b><i><input type="hidden"  name="FileID"><%=request.getAttribute("FileID")%></i></b>
<br><br>

Next Level/File Type    :
<input type="radio" name="next" value="Mr Subhash Gupta" checked>Mr Subhash Gupta (TA/DA Bill)<br>
<input type="radio" name="next" value="Mr Arjun Chopra">Mr Arjun Chopra (Medical Bill)<br>
<input type="radio" name="next" value="Mr Hemant Kapoor">Mr Hemant Kapoor (Sanction)<br>
<input type="radio" name="next" value="Cancelled">Cancelled<br>
<br>

Remarks       : <input type="text" name="Remark">
<br>
<input type="submit" name="Submit">
</pre>
</form>




<div id="footer">
Copyright @ pec.ac.in
</div>

</body>

</html>