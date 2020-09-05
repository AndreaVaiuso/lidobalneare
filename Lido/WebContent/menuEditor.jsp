<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="it.lidobalneare.db.DBConnect"%>
<%@ page import="it.lidobalneare.bean.Dish" %>
<jsp:useBean id="connecteduser" class="it.lidobalneare.bean.User" scope="session" />

<% 
try {
	if ( !connecteduser.isCook() ) {
		response.sendRedirect("./errorpage.html");
		return;
	}
} catch (NullPointerException e){
	response.sendRedirect("login.html");
	return;
}

ArrayList<Dish> dishes = new ArrayList<Dish>();
try {
	dishes = DBConnect.getDishes();
} catch (Exception e) {	System.out.println(e); return; }
%>

<!DOCTYPE html>

<html>

<head>
    <meta charset="utf-8" name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no" />
    
    <title>Lido Menu Editor</title>
    
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
    <%@include file="navRestaurant.html"%>
    <script>
    	document.getElementById("res_menu").classList.add("active");
    	
    	
    <%	if ( request.getParameter("openCard") != null ) { %>
	    	$(document).ready(function(){
	    		cardOpen(<%= Integer.valueOf(request.getParameter("openCard")) %>);
	    		showEditor();
    		});
    <%	} %>
    </script>
	    
	<div class="topDivBkg">
    	<img class="titleimage" src="assets/img/menuEditorLogo.png" style="margin-top: 50px;" />
	</div>
    
    <div class="menuContainerDiv" style="display: table;">
        <div class="menuCategoriesPanel">
            <div id="card_1" class="card lidocard" onclick='cardOpen(1); showEditor();'>
   		      	<img class="card-img w-100 d-block" src="assets/img/appetizers.jpg" />
                <div class="card-img-overlay">
                    <h4>Appetizers</h4>
                    <p class="lidoparagraph">A selection of fresh appetizers</p>
                </div>
            </div>
            
            <div id="card_2" class="card lidocard" onclick='cardOpen(2); showEditor();'>
   		     	<img class="card-img w-100 d-block" src="assets/img/first.jpg" />
            	
                <div class="card-img-overlay">
                    <h4>First dishes</h4>
                    <p class="lidoparagraph">We love pasta with fish</p>
                </div>
            </div>
            
            <div id="card_3" class="card lidocard" onclick='cardOpen(3); showEditor();'>
            	<img class="card-img w-100 d-block" src="assets/img/second.jpg" />
            	
                <div class="card-img-overlay">
                    <h4>Second dishes</h4>
                    <p class="lidoparagraph">Meat, fish, veg</p>
                </div>
            </div>
            
            <div id="card_4" class="card lidocard" onclick='cardOpen(4); showEditor();'>
            	<img class="card-img w-100 d-block" src="assets/img/dessert.jpg" />
            	
                <div class="card-img-overlay">
                    <h4>Desserts</h4>
                    <p class="lidoparagraph">Something to sweet your mouth</p>
                </div>
            </div>
            
            <div id="card_5" class="card lidocard" onclick='cardOpen(5); showEditor();'>
            	<img class="card-img w-100 d-block" src="assets/img/bar.jpg" />
            	
                <div class="card-img-overlay">
                    <h4>Bar</h4>
                    <p class="lidoparagraph">Espresso only, hate non-italian coffee</p>
                </div>
            </div>
        </div>
        
        <div class="menuMenuPanel">
        <%	for (int i = 0; i < dishes.size(); i++) { %>
             <div id="dish_<%= dishes.get(i).getId() %>" class="card category_<%= dishes.get(i).getCategory() %> menuMenuItem" style="display: none">
                <div class="card-body">
                    <h4 class="card-title"><%= dishes.get(i).getName() %></h4>
                    <h6 class="price-span text-muted card-subtitle mb-2"><%= dishes.get(i).getPrice() %></h6>
                    <span class="price-span h6" style="display: inline-block;"> &euro;</span>
                    <p class="card-text"><%= dishes.get(i).getIngredients() %></p>
                    <br />
                    <button class="btn btn-primary" type="button" onclick="dishEdit(<%= dishes.get(i).getId() %>)">Edit</button>
                </div>
            </div>
        <%	} %>
            
            <div class="card menuMenuItem">
                <form id="dishAddForm" action="MenuEditorServlet" method="post" class="card-body" style="display: none">
                	<input id="nameAdd" type="text" class="dishInsert h4" placeholder="Dish name" required />
                	<input id="priceAdd" type="number" class="price-field dishInsert h6" placeholder="Price" required />
                	<span class="price-span h6"> &euro;</span>
                	<input id="ingrAdd" type="text" class="dishInsert ingredients" style="display: block;" placeholder="Ingredients" required />
                    <button class="btn btn-primary" type="submit">Add</button>
                    <button class="btn btn-primary" type="reset">Reset</button>
                </form>
            </div>
        </div>    
	</div>
    
    <script src="assets/bootstrap/js/bootstrap.min.js"></script>
    <script src="assets/js/menu.js"></script>
</body>

</html>