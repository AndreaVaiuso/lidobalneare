<script src="assets/js/qrcode.js"></script>
<script src="assets/js/qrcode.min.js"></script>

<%@ page import="it.lidobalneare.db.DBConnect"%>
<%@ page import="it.lidobalneare.bean.User"%>
<%@ page import="it.lidobalneare.bean.Pass"%>
<%@ page import="it.lidobalneare.bean.Booking"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.sql.Date"%>
<%@ page import="java.util.Calendar"%>

<% 
java.util.Date javatoday = Calendar.getInstance().getTime();
java.text.DateFormat dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd");
Date today = Date.valueOf(dateFormat.format(javatoday));

User cntusr = (User) session.getAttribute("connecteduser");

if (cntusr == null) {
	response.sendRedirect("login.html");
	return;
}

String customer = (String) session.getAttribute("customer");
ArrayList<Pass> passes = new ArrayList<Pass>();
ArrayList<Booking> bookings = new ArrayList<Booking>();

if (customer == null) {
	customer = cntusr.getEmail();
}
if ( !customer.equals(cntusr.getEmail()) && !cntusr.isAdmin() ) {
	response.sendRedirect("errorpage.html");
	return;
}

boolean unregistered = false;
try { unregistered = request.getParameter("unregistered").equals("YES"); } catch (NullPointerException e) {}

try{
	if(unregistered){
		bookings = DBConnect.getNotRegisteredBookings();
	} else {
		java.util.Collections.reverse(passes);
		passes = DBConnect.getCustomerPasses(customer);
		bookings = DBConnect.getCustomerBookings(customer);
	}
} catch (Exception e){
	e.printStackTrace();
}


java.util.Collections.reverse(passes);
java.util.Collections.reverse(bookings);

for (int i = 0; i < passes.size(); i++) { %>
	<div class="prenpass" 
	<% if(cntusr.isAdmin()) { %> 
		onclick="selectPass('<%= passes.get(i).getPass_email() // String
					   %>', '<%= passes.get(i).getPass_begin() // Date
					   %>', '<%= passes.get(i).getPass_end() // Date
					   %>', '<%= passes.get(i).getSeat() // String
					   %>') "
   <% } %> >
   
	<% if ( passes.get(i).getPass_end().after(today) ) { %>
		<button class="btn btn-primary showqrcodebutton" type="button" onclick="passQr('<%= passes.get(i).getPass_id()
																				   %>','<%= passes.get(i).getPass_email() %>')">
			<i class="fa fa-qrcode"></i>
		</button>
		<span class="prenparag">PASS: Valid from <%= passes.get(i).getPass_begin() %>
											  to <%= passes.get(i).getPass_end() %></span>
		<span class="prenparag" style="color: limegreen; font-weight: bold">VALID</span>
	<% } else { %>
		<button class="btn btn-outline-secondary showqrcodebutton" type="button" disabled>
			<i class="fa fa-qrcode"></i>
		</button>
		<span class="prenparag">PASS: Valid from <%= passes.get(i).getPass_begin() %>
											  to <%= passes.get(i).getPass_end() %></span>
		<span class="prenparag" style="color: red; font-weight: bold">EXPIRED</span>
	<% } %>
	</div>
<% } %>

<hr>

<%

for (int i = 0; i < bookings.size(); i++) { %>
	<div class="prenpass"
	<% if(cntusr.isAdmin()) { %> 
		onclick="selectBooking('<%= bookings.get(i).getEmail() // String
						  %>', '<%= bookings.get(i).getDay() // String
						  %>',  <%= bookings.get(i).getTime_slot() // Int
						  %>,  '<%= bookings.get(i).getSeat() // String
						  %>')"
    <% } %> >
    
	<button class="btn btn-primary showqrcodebutton" type="button" onclick="bookingQr('<%= bookings.get(i).getBooking_id()
	  %>','<%= bookings.get(i).getEmail() %>')" <% if (today.after(bookings.get(i).getDay())) { %> disabled <% } %> >
		<i class="fa fa-qrcode"></i>
	</button>
	<span class="prentitle"><% if(unregistered){%><%=bookings.get(i).getEmail()%>: <%}%>Valid for: <%= bookings.get(i).getDay() %></span>
	<%
	switch ( bookings.get(i).getTime_slot() ) {
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
     } %>
</div>
<% } %>