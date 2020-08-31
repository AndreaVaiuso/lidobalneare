<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" session="true" %>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.sql.Date"%>
<%@ page import="java.util.Calendar"%>
<%@ page import="it.lidobalneare.db.DBConnect"%>
<%@ page import="it.lidobalneare.bean.User"%>
<%@ page import="it.lidobalneare.bean.Pass"%>
<%@ page import="it.lidobalneare.bean.Booking"%>
<jsp:useBean id="connecteduser" class="it.lidobalneare.bean.User" scope="session" />

<% 
	try{
		if(!connecteduser.isAdmin()){
			response.sendRedirect("./errorpage.html");
			return;
		}
	} catch (NullPointerException e){
		System.out.println("Session deleted");
		response.sendRedirect("login.html");
		return;
	}

	String customer = (String) session.getAttribute("customer");
	java.util.Date javatoday = Calendar.getInstance().getTime();
	java.text.DateFormat dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd");
	Date today = Date.valueOf(dateFormat.format(javatoday));
%>

<!DOCTYPE html>

<html>

<head>
    <meta charset="utf-8" name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    
    <title>LidoBalneare</title>
    
    <script src="assets/js/jquery.min.js"></script>
    
    <link rel="stylesheet" href="assets/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Acme">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Akronim">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Anonymous+Pro">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto:100,300,400,500,700">
    <link rel="stylesheet" href="assets/fonts/font-awesome.min.css">
    <link rel="stylesheet" href="assets/css/menu-collapse-ultimate.css">
    <link rel="stylesheet" href="assets/css/styles.css">
</head>

<body>
	<%@include file="alertbox.html"%>
    
    <!-- Pass edit form box -->
	<form id="pass_edit_form" class="alertscreen">
		<div class="alertwindow">
			<span class="lidoalerttitle">Edit pass</span>
			<hr class="lidohr" />
			
			<input id="pass_email" type="text" class="lidoblockstyle" placeholder='Email address' required />
			<input id="pass_begin" type="text" class="lidoblockstyle" placeholder='Start date' required />
			<input id="pass_end" type="text" class="lidoblockstyle" placeholder='End date' required />
			<input id="pass_seat" type="text" class="lidoblockstyle" placeholder='Seat name' required />
			
			<div class="btn-group lidobtngroup" role="group">
				<button class="btn btn-primary lidobtnofbtngroup" type="submit" onclick="applyPass()">Apply</button>
				<button class="btn btn-primary lidobtnofbtngroup" type="button" onclick='$("#pass_edit_form").toggle();'>Cancel</button>
			</div>
		</div>
	</form>
	
	<!-- Booking edit form box -->
	<form id="booking_edit_form" class="alertscreen">
		<div class="alertwindow">
			<span class="lidoalerttitle">Edit booking</span>
			<hr class="lidohr" />
			
			<input id="book_email" type="text" class="lidoblockstyle" placeholder='Email address' required />
			<input id="book_day" type="text" class="lidoblockstyle" placeholder='Start date' required />
			<input id="book_slot" type="text" class="lidoblockstyle" placeholder='End date' required />
			<input id="book_seat" type="text" class="lidoblockstyle" placeholder='Seat name' required />
			
			<div class="btn-group lidobtngroup" role="group">
				<button class="btn btn-primary lidobtnofbtngroup" type="submit" onclick="applyBooking()">Apply</button>
				<button class="btn btn-primary lidobtnofbtngroup" type="button" onclick='$("#booking_edit_form").toggle();'>Cancel</button>
			</div>
		</div>
	</form>
    
    <div class="divcontainer">
        <%@include file="navAdmin.html"%>
		<script>
        	document.getElementById("nav_admin").classList.add("active");
        </script>
        
        <div class="contentscreen">
        	<span class="toptitle">Reservations</span>
        	<span class="logindescription" style="background-color: rgb(220,220,220);">Customer: <%= session.getAttribute("customer") %></span>
            
            <div class="contentdivscreen">
            <% 
            // Passes
            ArrayList<Pass> passes = new ArrayList<Pass>();
            //System.out.println(passes);
            try {
            	passes = DBConnect.getCustomerPasses(customer);
            } catch (Exception e) {
            	e.printStackTrace();
            }
            
            for (int i = 0; i < passes.size(); i++) {
            	if ( passes.get(i).getPass_end().after(today) ) {
            	%>
                <div class="prenpass" onclick="selectPass('<%= passes.get(i).getPass_email() // String
                									 %>', '<%= passes.get(i).getPass_begin() // Date
                									 %>', '<%= passes.get(i).getPass_end() // Date
                									 %>', '<%= passes.get(i).getSeat() // String
                									 %>') ">
                	<button class="btn btn-primary showqrcodebutton" type="button"><i class="fa fa-qrcode"></i></button>
                	<span class="prenparag">PASS: Valid from <%= passes.get(i).getPass_begin() %> to <%= passes.get(i).getPass_end() %></span>
		            <span class="prenparag" style="color: limegreen; font-weight: bold">VALID</span>
                </div>
                <%
                } else {
                %>
                <div class="prenpass" onclick="selectPass('<%= passes.get(i).getPass_email() // String
                									 %>', '<%= passes.get(i).getPass_begin() // Date
                									 %>', '<%= passes.get(i).getPass_end() // Date
                									 %>', '<%= passes.get(i).getSeat() // String
                									 %>')">
                	<button class="btn btn-outline-secondary showqrcodebutton" type="button" disabled><i class="fa fa-qrcode"></i></button>
                	<span class="prenparag">PASS: Valid from <%= passes.get(i).getPass_begin() %> to <%= passes.get(i).getPass_end() %></span>
		            <span class="prenparag" style="color: red; font-weight: bold">EXPIRIED</span>
                </div>
            	<%
                }
            } %>
            	<hr>
            <%
            // Bookings
            ArrayList<Booking> bookings = new ArrayList<Booking>();
            
            try {
            	bookings = DBConnect.getCustomerBookings(customer);
            } catch (Exception e1) {
            	e1.printStackTrace();
            }
            
            for (int i = 0; i < bookings.size(); i++) { %>
                <div class="prenpass" onclick="selectBooking('<%= bookings.get(i).getEmail() // String
                										%>', '<%= bookings.get(i).getDay() // String
                										%>',  <%= bookings.get(i).getTime_slot() // Int
                										%>,  '<%= bookings.get(i).getSeat() // String
                										%>')">
                	<button class="btn btn-primary showqrcodebutton" type="button" 
                	  <% if (today.after(bookings.get(i).getDay())) { %> disabled <% } %>>
                		<i class="fa fa-qrcode"></i>
                	</button>
                	<span class="prentitle">Valid for: <%= bookings.get(i).getDay() %></span>
                	<%
               		switch ( bookings.get(i).getTime_slot() ) {
               			case 0 :
               				%>
               				<span class="prenparag">Time slot: all day</span>
               				<%
               				break;
               			case 1 :
               				%>
               				<span class="prenparag">Time slot: 9:00 - 12:00</span>
               				<%
               				break;
               			case 2 :
               				%>
               				<span class="prenparag">Time slot: 12:00 - 15:00</span>
               				<%
               				break;
               			case 3 :
               				%>
               				<span class="prenparag">Time slot: 15:00 - 18:00</span>
               				<%
               				break;
               			default :
               				break;
               		}		%>
                </div>
            <% } %>
            </div>
            
            <hr>
            
            <div class="buttoncontainer">
            	<button class="btn btn-primary" type="button" onclick="editReservation()">Edit reservation</button>
            	<button class="btn btn-primary" type="button" onclick="back()">Back</button>
            </div>
        </div>
    </div>
    
    <script src="assets/bootstrap/js/bootstrap.min.js"></script>
    <script src="assets/js/alert.js"></script>
    <script src="assets/js/bookings.js"></script>
</body>

</html>