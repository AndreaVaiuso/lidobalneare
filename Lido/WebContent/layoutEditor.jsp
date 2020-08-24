<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<jsp:useBean id="connecteduser" class="it.lidobalneare.bean.User"
	scope="session" />
<!DOCTYPE html>
<%
	try {
	if (!connecteduser.getRole().equals("admin")) {
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

<html>
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

		<%@include file="adminavbar.html"%>
		<script>
			document.getElementById("nav_layout").classList.add("active");
			document.getElementById("nav_layout").style = "background-color : white; border-radius : 3px"
		</script>

		<div class="contentscreen">
			<span class="toptitle">Lido layout editor</span><span
				class="logindescription"
				style="background-color: rgb(220, 220, 220);">If you want to
				manage your lido, you are in the right place.</span>
			
			<%@include file="lidolayout.jsp" %>
			
			<hr>
			<div class="buttoncontainer">
				<button id="addchairbtn" class="btn btn-primary" type="button">Add chair</button>
				<button class="btn btn-primary" type="button">Remove chair</button>
				<button class="btn btn-primary" type="button">Edit background image</button>
			</div>
		</div>
	</div>
	<script src="assets/js/jquery.min.js"></script>
	<script src="assets/js/layout.js"></script>
	<script src="assets/bootstrap/js/bootstrap.min.js"></script>
</body>

</html>
