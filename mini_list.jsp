<%@page contentType="text/html; charset=utf8" pageEncoding="UTF8"%>
<%           
	response.setCharacterEncoding("UTF-8");
	request.setCharacterEncoding("UTF-8");
	Object[] name_pro = (Object [])request.getAttribute("name_pro");
	Object[] min_pri = (Object [])request.getAttribute("min_pri");
	Object[] name_shop = (Object [])request.getAttribute("name_shop");
	
%>
<!DOCTYPE html>
<html>
<body>

<head>
	<link rel="stylesheet" type="text/css" href="mini_list.css">
	<title>Prices Outlook - Best Prices in Volos</title>
</head>

<h1>Prices Outlook</h1>
<h2>Find the best prices in your area</h2>

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

	<h3>Minimum prices in Volos, Magnesia for the selected category of products</h3>

<div id="products">
	
	<%
	
		for(int i=0; i<name_pro.length; i++){
	
			out.println("<p>"+name_pro[i]+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+min_pri[i]+"&nbsp;&nbsp;eyro&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+name_shop[i]+"</p>");
		}

	%>
	
</div>
	
<a href="app.htm">Return to Homepage</a>	

</body>
</html>