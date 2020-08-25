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
    <div class="alertscreen">
        <div class="alertwindow">
        	<span class="lidoalerttitle">Alert screen title!</span>
            <hr class="lidohr">
            <span class="logindescription">This is an accurate description of the error, or whatever you should know. Yeah, maybe something went wrong, so check your last steps and do each step with more attention</span>
            
            <div class="btn-group lidobtngroup" role="group">
            	<button class="btn btn-primary lidobtnofbtngroup" type="button">Yes</button>
            	<button class="btn btn-primary lidobtnofbtngroup" type="button">No</button>
           	</div>
        </div>
    </div>
    
    <div class="divcontainer">
        <%@include file="adminavbar.html"%>
		<script>
        	document.getElementById("nav_admin").classList.add("active");
        	document.getElementById("nav_admin").style = "background-color : white; border-radius : 3px"
        </script>
        
        <div class="contentscreen">
        	<span class="toptitle">Reservations</span>
        	<span class="logindescription" style="background-color: rgb(220,220,220);">Customer: CUSTOMER_NAME</span>
            
            <div class="contentdivscreen">
                <div class="prenpass">
                	<button class="btn btn-primary showqrcodebutton" type="button"><i class="fa fa-qrcode"></i></button>
                	<span class="prentitle"><strong>Pass for 4 people</strong></span>
                	<span class="prenparag">Valid from 02/10/2020 to 02/11/2020</span>
                    <span class="validlabel" style="color: green;">VALID</span>
                </div>
                
                <div class="prenpass">
                	<button class="btn btn-outline-secondary showqrcodebutton" type="button" disabled=""><i class="fa fa-qrcode"></i></button>
                	<span class="prentitle"><strong>Pass for 4 people</strong></span>
                	<span class="prenparag">Valid from 02/09/2020 to 02/10/2020</span>
                    <span class="validlabel" style="color: red;">EXPIRIED</span>
                </div>
                
                <hr>
                
                <div class="prenpass">
                	<button class="btn btn-primary showqrcodebutton" type="button"><i class="fa fa-qrcode"></i></button>
                	<span class="prentitle">01/10/2020 for 4 people</span>
                	<span class="prenparag">Time slot: 15:00 - 20:00</span>
                </div>
            </div>
            
            <hr>
            
            <div class="buttoncontainer">
            	<button class="btn btn-primary" type="button">Edit reservation</button>
            	<button class="btn btn-primary" type="button">Back</button>
            </div>
        </div>
    </div>
    
    <script src="assets/bootstrap/js/bootstrap.min.js"></script>
</body>

</html>