<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<jsp:useBean id="connecteduser" class="it.lidobalneare.bean.User"
	scope="session" />
<%@ page import="it.lidobalneare.db.DBConnect"%>
<%@ page import="it.lidobalneare.bean.Message"%>
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
Message m = DBConnect.getMessage();
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

<script src="assets/js/jquery.min.js"></script>
<script src="assets/bootstrap/js/bootstrap.min.js"></script>

</head>

<body>
	<%@ include file="alertbox.html"%>

	<div class="divcontainer">
		<div class="contentscreen" style="text-align: center; margin-top: 5px;">
			<div class="contentdivscreen-layout">
				<hr>
				<a target="_blank" style="display: inline-block; margin-right:30px; vertical-align:top;" href="https://www.booked.net/weather/palermo-18798"><img src="https://w.bookcdn.com/weather/picture/3_18798_1_1_137AE9_250_ffffff_333333_08488D_1_ffffff_333333_0_6.png?scode=124&domid=w209&anc_id=78065"  alt="booked.net"/></a><!-- weather widget end -->
				<div class="infobox" style="display: inline-block; vertical-align:top;">
					<figure class="figure" style="width: 100%;display: inline-block">
						<img class="img-fluid figure-img" style=" width: 50px; display:inline-block;" 
						<% 
						switch(m.getMessagetype()){
						case "STANDARD":
							%>src="assets/img/standard_message.png"<%
							break;
						case "WARNING":
							%>src="assets/img/warning_message.png"<%
							break;
						case "INFO":
							%>src="assets/img/info_message.png"<%
							break;
						default:
							%>src="assets/img/standard_message.png"<%
							break;
						}
						%>
						>
						<div style="width:100%; display: block">
							<span class="font-weight-bold" style="display: inline-block; font-size: 25px; font-weight: bold;"><%= m.getTitle() %></span>
							<hr>
							<marquee style="display:inline-block" loop="infinite" scrollamount="5" class="marquemessage"><%=m.getDate()%> : <%= m.getMessage() %></marquee>
						</div>
					</figure>
					
				</div>
				<hr class="lidohr">
				<jsp:include page="lidolayout.jsp">
					<jsp:param name="prenpass" value="NO" />
				</jsp:include>
				<hr>
			</div>
		</div>
	</div>
	<script src="assets/js/chairPopup.js"></script>
	<script src="assets/js/datelimit.js"></script>
	<script src="assets/js/reservation.js"></script>
	<script src="assets/bootstrap/js/bootstrap.min.js"></script>
</body>
</html>
