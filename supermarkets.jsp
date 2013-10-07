<%@page contentType="text/html; charset=utf8" pageEncoding="UTF8"%>
<%           
	request.setCharacterEncoding("UTF-8");
	String[][] shops = (String [][])request.getAttribute("shops");

%>
<!DOCTYPE html>
<html>
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">

<head>
	<link rel="stylesheet" type="text/css" href="supermarkets.css">
	<title>Prices Outlook - Available Supermarkets In Your Area</title>
</head>

<body>

	<h1>Prices Outlook</h1>
	<h2>Available Points of Sale</h2>

	<div id="menu">
		<ul>
			<li id="home">
				<a href="app.htm">Home</a>
			</li>
			<li id="contact">
				<a href="contact.htm">Contact</a>
			</li>
			<li id="about">
				<a href="about.htm">About</a>
			</li>
		</ul>
	</div>

<div id="superbuttons">

	<form accept-charset="utf-8" action="/super/mega" method="get" enctype="form_container">
	
	<%
	
		for(int i=0; i<shops.length; i++){
	
			out.println("<button type=submit name="+shops[i][0]+" value='"+ shops[i][1] +"'><img src='data/"+shops[i][0]+".gif' height=80 width=288><br>"+shops[i][1]+"</button>");
	
	}
	session.setAttribute("mega_lock",1);
	
	%>
	
	</form>

</div>

<a href="app.htm">Return to Homepage</a>

</body>
</html>