<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="it.lidobalneare.db.DBConnect"%>
<%@ page import="it.lidobalneare.bean.Dish" %>
<%@ page import="it.lidobalneare.bean.OrderQuantity" %>
<%@ page import="it.lidobalneare.bean.OrderTable" %>
<jsp:useBean id="connecteduser" class="it.lidobalneare.bean.User" scope="session" />

<% 
try{
	if(!connecteduser.isCook()){
		response.sendRedirect("./errorpage.html");
		return;
	}
} catch (NullPointerException e){
	response.sendRedirect("login.html");
	return;
}

ArrayList<OrderTable> tables = new ArrayList<OrderTable>();

try {
	tables = DBConnect.getTables();
} catch (Exception e) { e.printStackTrace(); return; }

SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
%>

<!DOCTYPE html>

<html>

<head>
    <meta charset="utf-8" name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no" />

    <title>LidoBalneare</title>    
    
    <script src="assets/js/jquery.min.js"></script>
    <script src="assets/bootstrap/js/bootstrap.min.js"></script>
    <script src="assets/js/qrcode.min.js"></script>

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
	<%@ include file="alertbox.html" %>
	<%@ include file="navRestaurant.html" %>
    <script>
    	document.getElementById("res_orders").classList.add("active");
    </script>
    
    <div id="qrcodescreen" class="alertscreen" style="display: none">
		<div class="qrcodecontainer" onclick="javascript:$('#qrcodescreen').fadeOut(500)">
			<div id="qrcode"></div>
		</div>
	</div>
	
    <div class="topDivBkg" style="padding-top: 50px;">
    	<span class="orders" style="padding-top: 80px;">Orders</span>
    	<button id="show_order_btn" class="btn btn-primary btn-lg orderButton" type="button" onclick='generateQr()'>Tables QR-code</button>
    </div>
    
    <div class="menuContainerDivKitchen">
	<%	for (int i = 0; i < tables.size(); i++) { %>
	        <div id='table_<%= tables.get(i).getTableNumber() %>' class="kitchenOrder">
	        	<a class="btn kitchenOrderButton" data-toggle="collapse" aria-expanded="false"
	        	  aria-controls="collapse-<%= tables.get(i).getTableNumber() %>" href="#collapse-<%= tables.get(i).getTableNumber() %>" role="button">
	        	  Table <%= tables.get(i).getTableNumber() %>; time <%= sdf.format(tables.get(i).getDate()) %></a>   
	            <div id="collapse-<%= tables.get(i).getTableNumber() %>" class="collapse">
	                <div class="card">
	                    <div class="card-body">
	                        <div class="table-responsive">
	                            <table class="table">
	                                <thead>
	                                    <tr>
	                                    	<th>Checked</th>
	                                        <th class="tableHeader" style="width: 70%;">Dish</th>
	                                        <th>Quantity</th>
	                                        <th>Time</th>
	                                    </tr>
	                                </thead>
	                                <tbody>
	                            <%	ArrayList<OrderQuantity> o = new ArrayList<OrderQuantity>();
	                                
	                                try {
	                                	o = DBConnect.getOrderQuantitiesByTable(tables.get(i).getTableNumber());
	                                } catch (Exception e) { e.printStackTrace(); return; }
	                                
	                                for (int j = 0; j < o.size(); j++) { %>
	                                    <tr>
	                                    	<td><input type="checkbox"/></td>
	                                        <td><%= o.get(j).getDish() %></td>
	                                        <td><%= o.get(j).getQuantity() %></td>
	                                        <td></td>
	                                    </tr>
	                            <%	} %>
	                                </tbody>
	                            </table>
	                        </div>
	                        
	                    	<button class="btn completeOrderKitchenButton" type="button" onclick='completeOrder(<%= tables.get(i).getTableNumber() %>)' disabled>Complete order</button>
	                    </div>
	                </div>
	            </div>
	        </div>
	<%	} %>
	</div>

    <script src="assets/js/orders.js"></script>
</body>

</html>