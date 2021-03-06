<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="it.lidobalneare.db.DBConnect"%>
<%@ page import="it.lidobalneare.bean.Dish"%>
<%@ page import="it.lidobalneare.bean.Order"%>

<%
	// No need to login. Customer must pay in this page to order.
try {
	if (Integer.valueOf(request.getParameter("table_number")) < 1) {
		response.sendRedirect("./errorpage.html");
		return;
	}
} catch (NullPointerException e) {
	response.sendRedirect("login.html");
	return;
}

ArrayList<Dish> dishes = new ArrayList<Dish>();
try {
	dishes = DBConnect.getDishes();
} catch (Exception e) {	
	e.printStackTrace();
	response.sendRedirect("./errorpage.html");
	return;
}
%>

<!DOCTYPE html>

<html>

<head>
	<meta charset="utf-8" name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no" />
	
	<title>LidoBalneare</title>
	
	<script src="assets/js/jquery.min.js"></script>
	
	<link rel="stylesheet" href="assets/bootstrap/css/bootstrap.min.css" />
	<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Acme" />
	<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Akronim" />
	<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Anonymous+Pro" />
	<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto:100,300,400,500,700" />
	<link rel="stylesheet" href="assets/fonts/font-awesome.min.css" />
	<link rel="stylesheet" href="assets/css/menu-collapse-ultimate.css" />
	<link rel="stylesheet" href="assets/css/styles.css" />
</head>

<body>
	<div id="paymentScreen" class="alertscreen" style="display: none;">
		<div class="alertwindow">
			<span id="paymenttitle" class="lidoalerttitle">Order confirmation</span>
			<hr class="lidohr" />
			<span class="logindescription">Price: </span>
			<span id="price_label" class="logindescription"></span>
			<div class="btn-group lidobtngroup" role="group">
				<button id="paymentyesbtn" class="btn btn-primary lidobtnofbtngroup" type="button">Confirm order</button>
				<button id="paymentnobtn" class="btn btn-primary lidobtnofbtngroup" type="button">Cancel</button>
			</div>
		</div>
	</div>

	<div class="topDivBkg">
		<img class="titleimage" src="assets/img/menu.png" />
	</div>

	<div class="menuContainerDiv">
		<div class="menuCategoriesPanel">
			<div id="card_1" class="card lidocard" onclick='cardOpen(1)'>
				<img class="card-img w-100 d-block" src="assets/img/appetizers.jpg" />
				<div class="card-img-overlay">
					<h4>Appetizers</h4>
					<p class="lidoparagraph">A selection of fresh appetizers</p>
				</div>
			</div>

			<div id="card_2" class="card lidocard" onclick='cardOpen(2)'>
				<img class="card-img w-100 d-block" src="assets/img/first.jpg" />
				<div class="card-img-overlay">
					<h4>First dishes</h4>
					<p class="lidoparagraph">We love pasta with fish</p>
				</div>
			</div>

			<div id="card_3" class="card lidocard" onclick='cardOpen(3)'>
				<img class="card-img w-100 d-block" src="assets/img/second.jpg" />
				<div class="card-img-overlay">
					<h4>Second dishes</h4>
					<p class="lidoparagraph">Meat, fish, veg</p>
				</div>
			</div>

			<div id="card_4" class="card lidocard" onclick='cardOpen(4)'>
				<img class="card-img w-100 d-block" src="assets/img/dessert.jpg" />
				<div class="card-img-overlay">
					<h4>Desserts</h4>
					<p class="lidoparagraph">Something to sweet your mouth</p>
				</div>
			</div>

			<div id="card_5" class="card lidocard" onclick='cardOpen(5)'>
				<img class="card-img w-100 d-block" src="assets/img/bar.jpg" />
				<div class="card-img-overlay">
					<h4>Bar</h4>
					<p class="lidoparagraph">Espresso only, hate non italian coffee</p>
				</div>
			</div>
		</div>
		
		<div class="menuMenuPanel">
		<%	for (int i = 0; i < dishes.size(); i++) { %>
			<div class="card category_<%=dishes.get(i).getCategory()%> menuMenuItem" style="display: none">
				<div class="card-body">
					<h4 class="card-title"><%= dishes.get(i).getName() %></h4>
					<h6 class="price-span text-muted card-subtitle mb-2"><%=dishes.get(i).getPrice()%></h6>
					<span class="price-span h6"> &euro;</span>
					<p class="card-text"><%=dishes.get(i).getIngredients()%></p>
					<br />
					<button class="btn btn-primary" type="button" onclick='addToOrder(<%= dishes.get(i).getId()
																				 %>, "<%= dishes.get(i).getName()
																				 %>", <%= dishes.get(i).getPrice() %>)'>Add to your order</button>
				</div>
			</div>
		<%	} %>
		</div>
	</div>

	<div id="orderDiv">
		<!-- JavaScript will insert the orders here. -->	
	</div>
	
	<hr class="lidohr" />

	<div id="totalDiv" class="card menuMenuItemtotal" style="display: none">
		<div class="card-body">
			<h4 class="card-title menutitleorder">Table </h4>
			<h4 id="table_number" class="card-title menutitleorder"><%= request.getParameter("table_number") %></h4>
			<h4>Total: </h4>
			<h6 id="total" class="price-span text-muted card-subtitle mb-2 menupriceordertotal"></h6>
			<span class="price-span h6"> &euro;</span>
			<button class="btn btn-primary menuRemoveOrder" type="button" onclick="confirmOrder()">Confirm order</button>
		</div>
	</div>
	
	<script src="assets/bootstrap/js/bootstrap.min.js"></script>
	<script src="assets/js/menu.js"></script>
</body>

</html>