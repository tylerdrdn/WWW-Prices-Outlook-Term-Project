 <%@page contentType="text/html; charset=UTF-8" %>
<%           
request.setCharacterEncoding("UTF-8");
response.setCharacterEncoding("UTF-8");
	String[][] category1 = (String [][])request.getAttribute("category1");
	String shop_id1 = (String)request.getAttribute("shop_id1");
	String[][] products2 = (String [][])request.getAttribute("products2");
	String shop_id2 = (String)request.getAttribute("shop_id2");
	Object meg = (Object)request.getAttribute("meg");
	String[] price_name = (String [])request.getAttribute("klo");
	Double price = (Double)request.getAttribute("price");
%>

<!DOCTYPE html>
<html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<link rel="stylesheet" type="text/css" href="List.css">
	<title>Prices Outlook - My Grocery List</title>
</head>

<body>

<h1>Prices Outlook</h1>
<h2>Hunt down the best offers and fill your grocery basket!</h2>

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
<form action="/super/mega" method="get" enctype="form_container">
<div id="categories">
	<%
	
		for(int i=0; i<category1.length-4; i++){
	
			out.println("<input type=radio onclick='(f1(),submit())' name="+shop_id1+" id="+category1[i][0]+" value='"+ category1[i][1] + "'>"+category1[i][1]);
	out.println("</br>");
	}
	
	%>
</div>

<div id="products">
	<%
	  if(products2 != null){
		for(int i=0; i<products2.length; i++){
	
			out.println("<input type=checkbox name="+ products2[i][2]+" id="+products2[i][0]+" value='"+products2[i][1]+"    "+products2[i][2]+" euro'>"+products2[i][1]+"    "+products2[i][2]+" euro</br>");
	
	}

	}
	
	%>
</div>

<div id="list">
	<%
	  if(price_name != null){
		for(int i=0; i<price_name.length; i++){
	
			out.println(price_name[i]+"</br>");
	
	}
	
	}
	 
	%>
</div>
<div id="sum">Total: <% out.println(Math.round(price*100.0)/100.0); %></div>
<div id="underbuttons">
 <input id=prod type="submit" onclick='f2()' name=button value=Submit>
 <input type="submit" id="li" name=delete value=Delete>
</div>
</form>
<a href="app.htm">Return to Homepage</a>
<script>

function f1() { var e = "<%session.setAttribute("mega_lock",2); %>" ;  }
function f2() {  var i = "<%session.setAttribute("mega_lock",2); %>"; }



</script>


</body>
</html>