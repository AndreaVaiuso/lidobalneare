<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="it.lidobalneare.db.DBConnect"%>
<%@ page import="it.lidobalneare.bean.Dish" %>
<jsp:useBean id="connecteduser" class="it.lidobalneare.bean.User" scope="session" />

<% 
	try{
		if(!connecteduser.getRole().equals("cook")){
			response.sendRedirect("./errorpage.html");
			return;
		}
	} catch (NullPointerException e){
		System.out.println("Session deleted");
		response.sendRedirect("login.html");
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
    <div class="topDivBkg">
    	<img class="titleimage" src="assets/img/menuEditorLogo.png" />
    </div>
    
    <%@include file="navRestaurant.html"%>
    <script>
    	document.getElementById("res_menu").classList.add("active");
    </script>
    
    <%
    ArrayList<Dish> dishes = new ArrayList<Dish>();
    %>
    
    <div class="menuContainerDiv" style="display: table;">
        <div class="menuCategoriesPanel">
            <div id="appetizers_card" class="card lidocard" onclick='cardOpen("#appetizers_card"); <% 
            	try {
            		dishes = DBConnect.getDishesByCategory("Appetizers");
            	} catch (Exception e) {
            		e.printStackTrace();
            	}
              %>'>
            	<img class="card-img w-100 d-block" src="assets/img/appetizers.jpg" />
                <div class="card-img-overlay">
                    <h4>Appetizers</h4>
                    <p class="lidoparagraph">A selection of fresh appetizers</p>
                </div>
            </div>
            
            <div id="fist_dishes_card" class="card lidocard" onclick='cardOpen("#fist_dishes_card"); <% 
            	try {
            		dishes = DBConnect.getDishesByCategory("First dishes");
            	} catch (Exception e) {
            		e.printStackTrace();
            	}
              %>'>
            	<img class="card-img w-100 d-block" src="assets/img/first.jpg" />
            	
                <div class="card-img-overlay">
                    <h4>First dishes</h4>
                    <p class="lidoparagraph">We love pasta with fish</p>
                </div>
            </div>
            
            <div id="second_dishes_card" class="card lidocard" onclick='cardOpen("#second_dishes_card"); <% 
            	try {
            		dishes = DBConnect.getDishesByCategory("Second dishes");
            	} catch (Exception e) {
            		e.printStackTrace();
            	}
              %>'>
            	<img class="card-img w-100 d-block" src="assets/img/second.jpg" />
            	
                <div class="card-img-overlay">
                    <h4>Second dishes</h4>
                    <p class="lidoparagraph">Meat, fish, veg</p>
                </div>
            </div>
            
            <div id="desserts_card" class="card lidocard" onclick='cardOpen("#desserts_card"); <% 
            	try {
            		dishes = DBConnect.getDishesByCategory("Desserts");
            	} catch (Exception e) {
            		e.printStackTrace();
            	}
              %>'>
            	<img class="card-img w-100 d-block" src="assets/img/dessert.jpg" />
            	
                <div class="card-img-overlay">
                    <h4>Desserts</h4>
                    <p class="lidoparagraph">Something to sweet your mouth</p>
                </div>
            </div>
            
            <div id="bar_card" class="card lidocard" onclick='cardOpen("#bar_card"); <% 
            	try {
            		dishes = DBConnect.getDishesByCategory("Bar");
            	} catch (Exception e) {
            		e.printStackTrace();
            	}
              %>'>
            	<img class="card-img w-100 d-block" src="assets/img/bar.jpg" />
            	
                <div class="card-img-overlay">
                    <h4>Bar</h4>
                    <p class="lidoparagraph">Espresso only, hate non-italian coffee</p>
                </div>
            </div>
        </div>
        
        <div class="menuMenuPanel">
        	<%
        	for (int i = 0; i < dishes.size(); i++) {
        	%>
            <div class="card menuMenuItem">
                <div class="card-body">
                    <h4 class="card-title"><%= dishes.get(i).getName() %></h4>
                    <h6 class="text-muted card-subtitle mb-2"><%= dishes.get(i).getPrice() %>&euro;</h6>
                    <p class="card-text"><%= dishes.get(i).getIngredients() %><br /></p>
                    <button class="btn btn-primary" type="button">Edit</button>
                </div>
            </div>
            <%
        	}
            %>
            
            <div class="card menuMenuItem">
                <form class="card-body">
                	<input type="text" class="dishInsert h4" placeholder="Dish name" required />
                	<input type="text" class="dishInsert h6" style="display: block;" placeholder="Price" required />
                	<input type="text" class="dishInsert ingredients" style="display: block;" placeholder="Ingredients" required />
                    <button class="btn btn-primary" type="submit">Add</button>
                </form>
            </div>
        </div>
        
        <button class="btn btn-primary" type="button" style="display: block;margin: 20px;position: relative;margin-left: 420px;">Add dish</button>
    </div>
    
    <script src="assets/bootstrap/js/bootstrap.min.js"></script>
    <script src="assets/js/menu.js"></script>
</body>

</html>