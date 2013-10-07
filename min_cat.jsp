 <%@page contentType="text/html; charset=UTF-8" %>
<%           
	response.setCharacterEncoding("UTF-8");
	request.setCharacterEncoding("UTF-8");
	String[][] category = (String [][])request.getAttribute("category");
	
%>

<!DOCTYPE html>
<html>
<body>

<head>
	<link rel="stylesheet" type="text/css" href="ChkPrices.css">
	<title>Prices Outlook - Available Categories</title>
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

	<h3>Select one of the following categories</h3>



	<form id="categories" action="/super/min_prod" method="post" enctype="form_container">
	
	<%
	
		for(int i=0; i<category.length-4; i++){
	
			out.println("<input type=radio onclick=submit() name=jj id="+category[i][1]+" value='"+ category[i][0] + "'>"+category[i][1]);
			out.println("</br>");
		}

	%>
	
	</form>


	
<a href="app.htm">Return to Homepage</a>	

</body>
</html>