<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>

<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    
    <title>LidoBalneare</title>
    
    <script src="assets/js/jquery.min.js"></script>
    
    <link rel="stylesheet" href="assets/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Acme">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Akronim">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Anonymous+Pro">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto:100,300,400,500,700">
    <link rel="stylesheet" href="assets/fonts/font-awesome.min.css">
    <link rel="stylesheet" href="assets/css/menu-collapse-ultimate.css">
    <link rel="stylesheet" href="assets/css/styles.css">
</head>

<body>
    <div class="topDivBkg">
    	<img class="titleimage" src="assets/img/menuEditorLogo.png" />
    </div>
    
    <div class="menuContainerDiv" style="display: table;">
        <div class="menuCategoriesPanel">
            <div class="card lidocard">
            	<img class="card-img w-100 d-block" src="assets/img/appetizers.jpg" />
            	
                <div class="card-img-overlay">
                    <h4>Appetizers</h4>
                    <p class="lidoparagraph">A selection of fresh appetizers</p>
                </div>
            </div>
            
            <div class="card lidocard">
            	<img class="card-img w-100 d-block" src="assets/img/first.jpg" />
            	
                <div class="card-img-overlay">
                    <h4>First dishes</h4>
                    <p class="lidoparagraph">We love pasta with fishes</p>
                </div>
            </div>
            
            <div class="card lidocard">
            	<img class="card-img w-100 d-block" src="assets/img/second.jpg" />
            	
                <div class="card-img-overlay">
                    <h4>Second dishes</h4>
                    <p class="lidoparagraph">Meat, fishes, veg</p>
                </div>
            </div>
            
            <div class="card lidocard">
            	<img class="card-img w-100 d-block" src="assets/img/dessert.jpg" />
            	
                <div class="card-img-overlay">
                    <h4>Desserts</h4>
                    <p class="lidoparagraph">Something to sweet your mouth</p>
                </div>
            </div>
            
            <div class="card lidocard">
            	<img class="card-img w-100 d-block" src="assets/img/bar.jpg" />
            	
                <div class="card-img-overlay">
                    <h4>Bar</h4>
                    <p class="lidoparagraph">Espresso only, hate non italian coffee</p>
                </div>
            </div>
        </div>
        
        <div class="menuMenuPanel">
            <div class="card menuMenuItem">
                <div class="card-body">
                    <h4 class="card-title">Mezze maniche alla carbonara</h4>
                    <h6 class="text-muted card-subtitle mb-2">999.99&euro;</h6>
                    <p class="card-text">Guanciale affumicato, pecorino romano, tuorlo d'uova bio da galline allevate a terra, pepe nero.<br></p>
                    <button class="btn btn-primary" type="button">Edit</button>
                </div>
            </div>
            
            <div class="card menuMenuItem">
                <div class="card-body">
                	<input type="text" class="dishInsert h4" placeholder="Dish name" />
                	<input type="text" class="dishInsert h6" style="display: block;" placeholder="Price" />
                	<input type="text" class="dishInsert ingredients" style="display: block;" placeholder="Ingredients" />
                    <button class="btn btn-primary" type="button">Add</button>
                </div>
            </div>
        </div>
        
        <button class="btn btn-primary" type="button" style="display: block;margin: 20px;position: relative;margin-left: 420px;">Add dish</button>
    </div>
    
    <script src="assets/bootstrap/js/bootstrap.min.js"></script>
</body>

</html>