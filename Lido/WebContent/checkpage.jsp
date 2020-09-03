<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="it.lidobalneare.bean.User" %>
<%@ page import="it.lidobalneare.bean.Booking" %>
<%@ page import="it.lidobalneare.bean.Pass" %>
<%@ page import="it.lidobalneare.db.DBConnect" %>
<jsp:useBean id="booking" class="it.lidobalneare.bean.Booking" scope="session" />
<jsp:useBean id="pass" class="it.lidobalneare.bean.Pass" scope="session" />

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
 <link rel="stylesheet" href="assets/bootstrap/css/bootstrap.min.css" />
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Acme" />
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Akronim" />
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Anonymous+Pro" />
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto:100,300,400,500,700" />
    <link rel="stylesheet" href="assets/fonts/font-awesome.min.css" />
    <link rel="stylesheet" href="assets/css/menu-collapse-ultimate.css" />
    <link rel="stylesheet" href="assets/css/styles.css" />
<title>Verify</title>
</head>
<body>
<div id="alertscreen" class="alertscreen" style="display : block;">
	<div class="alertwindow">
<%
	User usr = null;
	if(booking.getEmail() != null){
		usr = DBConnect.getUserName(booking.getEmail());
		%>
		<span id="alerttitle" class="lidoalerttitle">Booking valid</span>
		<hr class="lidohr">
		<span class="logindescription">Registered: <%= usr.getEmail()  %></span>
		<span class="logindescription">Name: <%= usr.getName()  %></span>
		<span class="logindescription">Surname: <%= usr.getSurname()  %></span>
		<span class="logindescription">Booking day: <%= booking.getDay()  %></span>
		<span class="logindescription">Seat: <%= booking.getSeat()  %></span>
		<span class="logindescription">Time slot: <%
				switch ( booking.getTime_slot() ) {
				case 0 : %>
					<span class="prenparag">Time slot: all day</span>
					<%
			        break;
		       	case 1 : %>
					<span class="prenparag">Time slot: 9:00 - 12:00</span>
					<%
		           	break;
		        case 2 : %>
					<span class="prenparag">Time slot: 12:00 - 15:00</span>
					<%
		           	break;
		        case 3 : %>
					<span class="prenparag">Time slot: 15:00 - 18:00</span>
					<%
		           	break;
		        default :
		           	break;
		     } 
		
		%></span>
		<%
	} else if(pass.getPass_email() != null){
		usr = DBConnect.getUserName(pass.getPass_email());
		%>
		<span class="lidoalerttitle">Booking valid</span>
		<hr class="lidohr">
		<span class="logindescription">Registered: <%= usr.getEmail()  %></span>
		<span class="logindescription">Name: <%= usr.getName()  %></span>
		<span class="logindescription">Surname: <%= usr.getSurname()  %></span>
		<span class="logindescription">Beginning day: <%= pass.getPass_begin()  %></span>
		<span class="logindescription">Expiration: <%= pass.getPass_end()  %></span>
		<span class="logindescription">Seat: <%= pass.getSeat()  %></span>
		<%
	} else {
		%>
		<span class="logindescription" style="font-size:25px ; color:red; font-weight: strong">QR NOT VALID</span>
		<%
	}
%>	
	</div>
</div>

</body>
</html>