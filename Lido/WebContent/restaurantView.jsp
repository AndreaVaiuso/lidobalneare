<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.ArrayList"%>
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
    
    <title>Lido Restaurant</title>
    
    <link rel="stylesheet" href="assets/bootstrap/css/bootstrap.min.css" />
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Acme" />
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Akronim" />
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Anonymous+Pro" />
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto:100,300,400,500,700" />
    <link rel="stylesheet" href="assets/css/menu-collapse-ultimate.css" />
    <link rel="stylesheet" href="assets/css/styles.css" />
    
    <script src="assets/js/jquery.min.js"></script>
</head>

<body>
    <%@include file="alertbox.html"%>
    
    <div class="divcontainer" style='background-image: url("assets/img/menuSkin.png");'>
    
        <%@include file="navRestaurant.html"%>
        <script>
        	document.getElementById("res_tables").classList.add("active");
        </script>
        
        <div class="contentscreen">
        	<span class="toptitle menu">Resturant table editor</span>
        	<span class="logindescription" style="background-color: rgb(220,220,220);">Here you can manage the active tables in the restaurant.
        		For each of them, you can generate the QR-code to access the restaurant menu and send an order linked to the table.</span>
            
            <div class="contentdivscreen">
	            <%
				/*
	            ArrayList<TableRestaurant> tables = new ArrayList<TableRestaurant>();
	            
	            try {
	            	tables = DBConnect.getTables();
	            } catch (Exception e) {
	            	e.printStackTrace();
	            }
	            */
	            %>
            
                <div class="table-responsive table-borderless" style="text-align: center;">
                    <table class="table table-bordered table-hover res_editor">
                        <thead style="border-bottom: 1px solid lightgray;">
                            <tr>
                                <th>Table #</th>
                                <th>QR-code</th>
                                <th>Remove</th>
                            </tr>
                        </thead>
                        
                        <tbody>
                        	<%
                        	/*
                        	for (int i = 0; i < tables.size(); i++) {	// You can remove a table only if it has the highest number.
                        	*/	%>
                        		<tr>
                        			<td><%= /*i + 1*/ %></td>
                        			<td><button class="btn btn-primary" type="button">Generate QR-code</button></td>
                        			<td><button class="btn btn-danger" type="button" <% /*if ( (i + 1) < tables.size() ) {*/ %>style="display: none;"<% /*}*/ %>>Remove table</button></td>
                        		</tr>
                        		<%
                        	/*
                        	}
                        	*/
                        	%>
                        </tbody>
                    </table>
                </div>
        	</div>
        	
        	<hr>
        	<button class="btn btn-primary lidobtn1" type="button">Add new table</button>
        </div>
    </div>
    
    <script src="assets/bootstrap/js/bootstrap.min.js"></script>
</body>

</html>