<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:useBean id="connecteduser" class="it.lidobalneare.bean.User"
	scope="session" />
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
		<div class="contentscreen" style="text-align: center;">
			<span class="toptitle">Make your reservation</span><span
				class="logindescription"
				style="background-color: rgb(220, 220, 220);">Chose the beginning date for your pass and chose the duration.</span><label class="labelReservation">Date</label><input
				id="datefield" onchange="updateLayout()"
				class="border rounded border-primary" type="date"
				style="margin: 20px; margin-left: 10px;" required> <label
				class="labelReservation">Time slot</label> <select
				class="border rounded border-primary" id="timeslotSelect"
				name="timeZone" style="margin: 20px; margin-left: 10px;"
				onchange="updateLayout()">
				<option value="1">1 month</option>
				<option value="2">2 months</option>
				<option value="3">3 months</option>
			</select>
			<hr>
			<label class="labelReservation">Choose your seat</label>
			<div class="contentdivscreen-layout">

				<%@include file="lidolayout.jsp"%>

			</div>
			<hr>
			<div class="buttoncontainer">
				<button id="backbtn" class="btn btn-primary" type="button"
					onclick="javascript:location.href='home.jsp'">Back to home</button>
				<button id="passbtn" class="btn btn-primary" type="button"
					onclick="javascript:location.href='reservation.jsp'">Buy a single reservation</button>
			</div>
		</div>
	</div>
	<script src="assets/js/jquery.min.js"></script>
	<script src="assets/js/alert.js"></script>
	<script src="assets/js/pass.js"></script>
	<script src="assets/bootstrap/js/bootstrap.min.js"></script>
</body>

</html>