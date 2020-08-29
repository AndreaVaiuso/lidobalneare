<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
	<div class="alertscreen">
		<div class="alertwindow">
			<span class="lidoalerttitle">Alert screen title!</span>
			<hr class="lidohr">
			<span class="logindescription">This is an accurate description
				of the error, or whatever you should know. Yeah, maybe something
				went wrong, so check your last steps and do each step with more
				attention</span>
			<div class="btn-group lidobtngroup" role="group">
				<button class="btn btn-primary lidobtnofbtngroup" type="button">Yes</button>
				<button class="btn btn-primary lidobtnofbtngroup" type="button">No</button>
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
				style="background-color: rgb(220, 220, 220);">Choose the
				date, the time slot and the chair.</span><label class="labelReservation">Date</label><input
				id="datefield" onchange="updateLayout()" class="border rounded border-primary" type="date"
				style="margin: 20px; margin-left: 10px;" required>
				<label class="labelReservation">Time slot</label>
			<select
				class="border rounded border-primary" id="timeslotSelect" name="timeZone"
				style="margin: 20px; margin-left: 10px;" onchange="updateLayout()">
				<option value="0" selected>All Day</option>
				<option value="1">9:00-12:00</option>
				<option value="2">12:00-15:00</option>
				<option value="3">15:00-18:00</option>
			</select>
			<hr>
			<label class="labelReservation">Choose your seat</label>
			<div class="contentdivscreen-layout">
				<%@include file="lidolayout.jsp" %>
			</div>
			<hr>
			<div class="buttoncontainer">
				<button id="backbtn" class="btn btn-primary" type="button" onclick="javascript:location.href='home.jsp'">Back to home</button>
				<button id="passbtn" class="btn btn-primary" type="button" onclick="javascript:location.href='pass.jsp'">Buy a season pass</button>
			</div>
		</div>
	</div>
	<script>
	urlParam = function(name){
	    var results = new RegExp('[\?&]' + name + '=([^&#]*)').exec(window.location.href);
	    if (results==null) {
	       return null;
	    }
	    return decodeURI(results[1]) || 0;
	}
	var today = new Date();
	var dd = today.getDate();
	var mm = today.getMonth()+1; //January is 0!
	var yyyy = today.getFullYear();
	if(dd<10){
		dd='0'+dd
	} 
	if(mm<10){
		mm='0'+mm
	} 
	today = yyyy+'-'+mm+'-'+dd;
	document.getElementById("datefield").setAttribute("min", today);
	let sdate = urlParam("date");
	if(sdate){
		document.getElementById("datefield").setAttribute("value", sdate);
	} else document.getElementById("datefield").setAttribute("value", today);
	let sts = urlParam("timeslot");
	if(sts){
		document.getElementById("timeslotSelect").selectedIndex = sts;
	}
	</script>
	<script src="assets/js/jquery.min.js"></script>
	<script src="assets/js/reservation.js"></script>
	<script src="assets/bootstrap/js/bootstrap.min.js"></script>
</body>

</html>
