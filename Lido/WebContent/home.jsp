<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="it.lidobalneare.bean.User"%>
<%@ page import="it.lidobalneare.db.DBConnect"%>
<jsp:useBean id="connecteduser" class="it.lidobalneare.bean.User" scope="session" />
<% 
	try{
		if(!connecteduser.getRole().equals("customer")){
			response.sendRedirect("./errorpage.html");
			return;
		}
	} catch (NullPointerException e){
		response.sendRedirect("login.html");
		return;
	}
	if(connecteduser.getPaypal() == null){
		connecteduser.setPaypal("Not configured yet");
	}
%>

<!DOCTYPE html>
<html>

<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
<title>LidoBalneare</title>
<link rel="stylesheet" href="assets/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet"
	href="https://fonts.googleapis.com/css?family=Acme">
<link rel="stylesheet"
	href="https://fonts.googleapis.com/css?family=Akronim">
<link rel="stylesheet"
	href="https://fonts.googleapis.com/css?family=Anonymous+Pro">
<link rel="stylesheet"
	href="https://fonts.googleapis.com/css?family=Roboto:100,300,400,500,700">
<link rel="stylesheet" href="assets/fonts/font-awesome.min.css">
<link rel="stylesheet" href="assets/css/menu-collapse-ultimate.css">
<link rel="stylesheet" href="assets/css/styles.css">
</head>

<body>
	<div id="ajaxloaderscreen" class="alertscreen" style="display: none">
		<div class="ajaxloader"></div>
	</div>
	<div id="alertscreen" class="alertscreen" style="display : none">
		<div class="alertwindow">
			<span id="alerttitle" class="lidoalerttitle">Alert screen title!</span>
			<hr class="lidohr">
			<span id="alertcontent" class="logindescription">This is an accurate description
				of the error, or whatever you should know. Yeah, maybe something
				went wrong, so check your last steps and do each step with more
				attention</span>
			<div class="btn-group lidobtngroup" role="group">
				<button id="alertyesbtn" class="btn btn-primary lidobtnofbtngroup" type="button">Yes</button>
				<button id="alertnobtn" class="btn btn-primary lidobtnofbtngroup" type="button">No</button>
			</div>
		</div>
	</div>
	<div class="divcontainer">
		<nav class="navbar navbar-light navbar-expand-md lidonavbar">
			<div class="container-fluid">
				<a class="navbar-brand" href="#">Lido Logo</a>
			</div>
		</nav>
		<div class="contentscreen">
			<span class="toptitle">Welcome back <jsp:getProperty name="connecteduser" property="name"/> <jsp:getProperty name="connecteduser" property="surname"/></span><span
				class="logindescription"
				style="background-color: rgb(220, 220, 220);">Time to have a
				nice vacation!</span>
			<div class="contentdivscreen">
				<span style="display: block; margin-bottom: 10px;">Paypal
					account configured: <jsp:getProperty name="connecteduser" property="paypal"/></span>
				<button id="configurepaypalbtn" class="btn btn-primary" type="button">
					Configure payment account&nbsp;<i class="fa fa-paypal"></i>
				</button>
			</div>
			<hr>
			<div class="contentdivscreen">
			
			<!--  
				<div class="prenpass">
					<button class="btn btn-primary showqrcodebutton" type="button">
						<i class="fa fa-qrcode"></i>
					</button>
					<span class="prentitle"><strong>Pass for 4 people</strong></span><span
						class="prenparag">Valid from 02/10/2020 to 02/11/2020</span> <span
						class="validlabel" style="color: green;">VALID</span>
				</div>
				<div class="prenpass">
					<button class="btn btn-outline-secondary showqrcodebutton"
						type="button" disabled="">
						<i class="fa fa-qrcode"></i>
					</button>
					<span class="prentitle"><strong>Pass for 4 people</strong></span><span
						class="prenparag">Valid from 02/09/2020 to 02/10/2020</span> <span
						class="validlabel" style="color: red;">EXPIRIED</span>
				</div>
			-->
				<hr>
			<!-- 
				<div class="prenpass">
					<button class="btn btn-primary showqrcodebutton" type="button">
						<i class="fa fa-qrcode"></i>
					</button>
					<span class="prentitle">01/10/2020 for 4 people</span><span
						class="prenparag">Time slot: 15:00 - 20:00</span>
				</div>
			-->
			</div>
			<hr>
			<div class="buttoncontainer">
				<button class="btn btn-primary" type="button">Buy a pass</button>
				<button class="btn btn-primary" type="button">Book a place</button>
			</div>
		</div>
	</div>
	<script src="assets/js/jquery.min.js"></script>
	<script src="assets/js/home.js"></script>
	<script src="assets/bootstrap/js/bootstrap.min.js"></script>
</body>

</html>
