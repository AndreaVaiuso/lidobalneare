<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<jsp:useBean id="connecteduser" class="it.lidobalneare.bean.User"
	scope="session" />
<%@ page import="it.lidobalneare.db.DBConnect" %>
<%
try {
	if (!connecteduser.isLifeGuard() && !connecteduser.isInfoMonitor()) {
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

<meta charset="utf-8" name="viewport"
	content="width=device-width, initial-scale=1.0, shrink-to-fit=no" />

<title>LidoBalneare</title>

<script src="assets/js/jquery.min.js"></script>

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
	<%@ include file="alertbox.html"%>

	<div class="divcontainer">
		<div class="contentscreen" style="text-align: center; margin-top:10px">
			<div class="contentdivscreen-layout">
				<hr>
				<div class="infobox">
					<span class="infobox_text"> <%=DBConnect.getMessage() %> </span>
					<hr>
				</div>
				<jsp:include page="lidolayout.jsp">
					<jsp:param name="prenpass" value="NO" />
				</jsp:include>
			</div>
			<hr>
		</div>
	</div>
	<script src="assets/js/chairPopup.js"></script>
	<script src="assets/js/datelimit.js"></script>
	<script src="assets/js/reservation.js"></script>
	<script src="assets/bootstrap/js/bootstrap.min.js"></script>
</body>
</html>
