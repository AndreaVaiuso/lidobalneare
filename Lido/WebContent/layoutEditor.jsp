<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<jsp:useBean id="connecteduser" class="it.lidobalneare.bean.User"
	scope="session" />

<%
	try {
	if (!connecteduser.isAdmin()) {
		System.out.println("NOT ADMIN!");
		response.sendRedirect("./errorpage.html");
		return;
	}
} catch (NullPointerException e) {
	System.out.println("Session deleted");
	response.sendRedirect("login.html");
	return;
}
%>

<!DOCTYPE html>

<html>

<head>
<meta charset="utf-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, shrink-to-fit=no" />

<script src="assets/js/jquery.min.js"></script>

<title>Lido Layout Editor</title>

<link rel="stylesheet" href="assets/bootstrap/css/bootstrap.min.css" />
<link rel="stylesheet"
	href="https://fonts.googleapis.com/css?family=Acme" />
<link rel="stylesheet"
	href="https://fonts.googleapis.com/css?family=Akronim" />
<link rel="stylesheet"
	href="https://fonts.googleapis.com/css?family=Anonymous+Pro" />
<link rel="stylesheet"
	href="https://fonts.googleapis.com/css?family=Roboto:100,300,400,500,700" />
<link rel="stylesheet" href="assets/fonts/font-awesome.min.css" />
<link rel="stylesheet" href="assets/css/menu-collapse-ultimate.css" />
<link rel="stylesheet" href="assets/css/styles.css" />
</head>

<body>

	<%@include file="alertbox.html"%>

	<div id="newchairwindow" class="alertscreen" style="display: none">
		<div class="alertwindow">

			<span id="newchairTitle" class="lidoalerttitle">Add a new
				chair to lido layout</span>
			<hr class="lidohr">

			<span class="logindescription"> Chair name: </span> <input
				class="lidoblockstyle" type="text" id="chairname_field"
				placeholder="Chair Name"> <span class="logindescription">
				Price per time slot: </span> <input class="lidoblockstyle" type="number"
				id="timeslot_price_field" placeholder="Price per time slot">

			<span class="logindescription"> All day price: </span> <input
				class="lidoblockstyle" type="number" id="allday_price_field"
				placeholder="All day price"> <span class="logindescription">
				Pass price: </span> <input class="lidoblockstyle" type="number"
				id="pass_price_field" placeholder="Pass price"> <span
				class="logindescription"> Note: </span> <input
				class="lidoblockstyle" type="text" id="note_field" placeholder="...">

			<div class="btn-group lidobtngroup" role="group">
				<button id="createchairbtn"
					class="btn btn-primary lidobtnofbtngroup" type="button">Create</button>
				<button id="updatechairbtn"
					class="btn btn-primary lidobtnofbtngroup" type="button"
					style="display: none">Update</button>
				<button id="cancelbtn" class="btn btn-primary lidobtnofbtngroup"
					type="button">Cancel</button>
			</div>


		</div>
	</div>


	<div class="divcontainer">
		<%@include file="navAdmin.html"%>
		<script>
			document.getElementById("nav_layout").classList.add("active");
			document.getElementById("nav_layout").style = "background-color : white; border-radius : 3px"
		</script>

		<div class="contentscreen">
			<span class="toptitle">Lido layout editor</span><span
				class="logindescription"
				style="background-color: rgb(220, 220, 220);">If you want to
				manage your lido, you are in the right place.</span>

			<%@include file="lidolayout.jsp"%>

			<hr>
			<div class="buttoncontainer">
				<button id="addchairbtn" class="btn btn-primary" type="button">Add
					chair</button>
				<input type="file" id="fileupload" accept=".jpg, .png, .jpeg"
					style="display: none">
				<form style="display:inline; margin-left:20px" action="UploadServlet" method="post" enctype="multipart/form-data">
					<span>Update background image: </span>
					<input type="file" name="file" />
					<input type="submit" />
				</form>
			</div>
		</div>
	</div>
	<script src="assets/js/chairPopup.js"></script>
	<script src="assets/js/layout.js"></script>
	<script src="assets/bootstrap/js/bootstrap.min.js"></script>
</body>

</html>
