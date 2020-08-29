<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

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
	<%@include file="navRestaurant.html"%>
    <script>
    	document.getElementById("res_orders").classList.add("active");
    </script>
	
    <div class="topDivBkg">
    	<span id="orders" style="padding-top: 80px;">Orders</span>
    </div>
    
    <div class="menuContainerDivKitchen">
        <div class="kitchenOrder">
        	<a class="btn kitchenOrderButton" data-toggle="collapse" aria-expanded="false" aria-controls="collapse-1" href="#collapse-1" role="button">Tavolo 1</a>
            
            <div class="collapse" id="collapse-1">
                <div class="card">
                    <div class="card-body">
                        <div class="table-responsive">
                            <table class="table">
                                <thead>
                                    <tr>
                                        <th class="tableHeader" style="width: 70%;">Dish</th>
                                        <th>Quantity</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr>
                                        <td>Carbonara</td>
                                        <td>2</td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                        
                    	<button class="btn completeOrderKitchenButton" type="button">Complete order</button>
                    </div>
                </div>
            </div>
        </div>
        
        <div class="kitchenOrder">
        	<a class="btn kitchenOrderButton" data-toggle="collapse" aria-expanded="false" aria-controls="collapse-2" href="#collapse-2" role="button">Tavolo 2</a>
            <div class="collapse" id="collapse-2">
                <div class="card">
                    <div class="card-body">
                        <div class="table-responsive">
                            <table class="table">
                                <thead>
                                    <tr>
                                        <th class="tableHeader" style="width: 70%;">Dish</th>
                                        <th>Quantity</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr>
                                        <td>Carbonara</td>
                                        <td>2</td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                        
                    	<button class="btn completeOrderKitchenButton" type="button">Complete order</button>
                	</div>
                </div>
            </div>
        </div>
    </div>

    <script src="assets/bootstrap/js/bootstrap.min.js"></script>
</body>

</html>