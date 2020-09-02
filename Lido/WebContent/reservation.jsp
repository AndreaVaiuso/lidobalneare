<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<jsp:useBean id="connecteduser" class="it.lidobalneare.bean.User" scope="session" />

<%
try {
	if (!connecteduser.isCustomer()) {
		response.sendRedirect("./errorpage.html");
		return;
	}
} catch (NullPointerException e) {
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
	<%@ include file="alertbox.html" %>

	<div class="divcontainer">
		<%@ include file="navCustomer.html" %>
		
		<div class="contentscreen" style="text-align: center;">
			<span class="toptitle">Make your reservation</span>
			<span class="logindescription" style="background-color: rgb(220, 220, 220);">Choose the	date, the time slot and the chair.</span>
			<label class="labelReservation">Date</label>
			<input id="datefield" onchange="updateLayout()"	class="border rounded border-primary" type="date" style="margin: 20px; margin-left: 10px;" required />
			<label class="labelReservation">Time slot</label>
			<select	class="border rounded border-primary" id="timeslotSelect" name="timeZone" style="margin: 20px; margin-left: 10px;" onchange="updateLayout()">
				<option value="0" selected>All Day</option>
				<option value="1">9:00-12:00</option>
				<option value="2">12:00-15:00</option>
				<option value="3">15:00-18:00</option>
			</select>
			
			<hr>

			<label class="labelReservation">Choose your seat</label>
			<div class="contentdivscreen-layout">
				<jsp:include page="lidolayout.jsp">
       				 <jsp:param name="prenpass" value="NO"/>
    			</jsp:include>
			</div>
			
			<hr>
			
			<div class="buttoncontainer">
				<button id="backbtn" class="btn btn-primary" type="button" onclick="javascript:location.href='home.jsp'">Back to home</button>
				<button id="passbtn" class="btn btn-primary" type="button" onclick="javascript:location.href='pass.jsp'">Buy a monthly pass</button>
			</div>
		</div>
	</div>
	<script src="assets/js/chairPopup.js"></script>
	<script src="assets/js/datelimit.js"></script>
	<script src="assets/js/reservation.js"></script>
	<script src="assets/bootstrap/js/bootstrap.min.js"></script>
</body>
</html>
