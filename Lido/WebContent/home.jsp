<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.sql.Date"%>
<%@ page import="java.util.Calendar"%>
<%@ page import="it.lidobalneare.bean.User"%>
<%@ page import="it.lidobalneare.bean.Pass"%>
<%@ page import="it.lidobalneare.bean.Booking"%>
<%@ page import="it.lidobalneare.db.DBConnect"%>
<jsp:useBean id="connecteduser" class="it.lidobalneare.bean.User" scope="session" />

<% 
	try{
		if(!connecteduser.isCustomer()){
			response.sendRedirect("./errorpage.html");
			return;
		}
	} catch (NullPointerException e){
		response.sendRedirect("login.html");
		return;
	}
%>

<!DOCTYPE html>
<html>

<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
	
	<title>Lido Home</title>
	
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
	<%@include file="alertbox.html"%>
	<div id="qrcodescreen" class="alertscreen" style="display : none">
		<div class="qrcodecontainer" onclick="javascript:$('#qrcodescreen').fadeOut(500)">
			<div id="qrcode"></div>
		</div>
	</div>
	
	<div class="divcontainer">
		<%@ include file="navCustomer.html" %>
		<script>
			document.getElementById("nav_home").classList.add("active");
		</script>
		
		<div class="contentscreen">
			<span class="toptitle">Welcome back <jsp:getProperty name="connecteduser" property="name"/> <jsp:getProperty name="connecteduser" property="surname"/></span>
			<span class="logindescription" style="background-color: rgb(220, 220, 220);">Time to have a	nice vacation!</span>
			
			<div class="contentdivscreen">
				<span style="display: block; margin-bottom: 10px;">Paypal account configured: <% 
					if ( connecteduser.getPaypal() == null ) { %>
						<i>Not configured yet.</i>
					<% } else {	%>
						<jsp:getProperty name="connecteduser" property="paypal"/></span>
					<% } %>
				<hr>
				<button id="configurepaypalbtn" class="btn btn-primary" type="button">Configure payment account&nbsp;<i class="fa fa-paypal"></i></button>
			</div>
			
			<hr>
			
			<div class="contentdivscreen">
			
				<%@include file="booking.jsp" %>

			</div>
			<hr>
			<div class="buttoncontainer">
				<button id="reservationbtn" class="btn btn-primary" type="button">Make your reservation</button>
			</div>
		</div>
	</div>

	<script src="assets/bootstrap/js/bootstrap.min.js"></script>
	<script src="assets/js/alert.js"></script>
	<script src="assets/js/home.js"></script>
</body>

</html>
