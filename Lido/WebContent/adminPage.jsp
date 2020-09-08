<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="it.lidobalneare.db.DBConnect"%>
<%@ page import="it.lidobalneare.bean.User"%>
<jsp:useBean id="connecteduser" class="it.lidobalneare.bean.User" scope="session" />

<!DOCTYPE html>

<% 
	try{
		if(!connecteduser.isAdmin()){
			response.sendRedirect("./errorpage.html");
			return;
		}
	} catch (NullPointerException e){
		response.sendRedirect("login.html");
		return;
	}
%>

<html>

<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
	
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

	<!-- Communications form box -->
	<form id="communication_form" class="alertscreen">
		<div class="alertwindow">
			<span class="lidoalerttitle">Send communication</span>
			<hr class="lidohr" />
			
			<input id="title" type="text" class="lidoblockstyle" placeholder='Title of your message' />
			<input id="msg" type="text" class="lidoblockstyle" placeholder='Type here the message, then press "Send".' />
			<select id="messagetype" class="lidoblockstyle">
				<option value="0" selected>Standard</option>
				<option value="1">Warning</option>
				<option value="2">Info</option>
			</select>
			
			<div class="btn-group lidobtngroup" role="group">
				<button id="send_btn" class="btn btn-primary lidobtnofbtngroup" type="button">Send</button>
				<button class="btn btn-primary lidobtnofbtngroup" type="button" onclick='$("#communication_form").fadeOut(500);'>Cancel</button>
			</div>
		</div>
	</form>

	<div class="divcontainer">

		<%@include file="navAdmin.html"%>
		<script>
        	document.getElementById("nav_admin").classList.add("active");
        	//document.getElementById("nav_admin").style = "background-color : white; border-radius : 3px"
        </script>
        
		<div class="contentscreen">
			<span class="toptitle">Administration panel</span> <span
				class="logindescription"
				style="background-color: rgb(220, 220, 220);">If you want to manage your lido, you are in the right place.</span>
			<div class="contentdivscreen">
				<div class="table-responsive table-borderless">
					<table class="table table-bordered">
						<thead>
							<tr>
								<th>Email</th>
								<th>Name</th>
								<th>Surname</th>
								<th>Gender</th>
								<th>Birth date</th>
							</tr>
						</thead>
						
						<tbody>
						<%
                        String cp = request.getParameter("currentPage");
                        int currentPage = 1;
                        
                        try {
                        	currentPage = Integer.valueOf(cp);
                        } catch(NumberFormatException e){
                        	currentPage = 1;
                        }
                        
                        int itemsPerPage = 10;
                        
                        int firstElement = (currentPage - 1) * itemsPerPage;
                        int lastElement = firstElement + itemsPerPage;
                        
                        ArrayList<User> users = DBConnect.getUserList(firstElement,lastElement);
                        
                        int pages = (int) Math.ceil( users.size() / itemsPerPage ) + 1;
                        
                        try {
                        	for(int i = 0; i < users.size(); i++){
                        		User u = users.get(i);
                        		%>
									<tr id="table_entry_<%=i%>" onclick="selectRow(<%=i%>,'<%=u.getEmail()%>')">
										<td><%= u.getEmail() %></td>
										<td><%= u.getName() %></td>
										<td><%= u.getSurname() %></td>
										<td><%= u.getGender() %></td>
										<td><%= u.getFormattedBirthDate() %></td>
									</tr>
								<% 
                        	}
                        } catch (java.lang.IndexOutOfBoundsException e) {}
                        %>

						</tbody>
					</table>
				</div>
				
				<nav>
					<ul class="pagination">
						<% 
				    	if(currentPage != 1){
				   		%>
							<li class="page-item">
								<a class="page-link" href="#" aria-label="Previous" onclick="goToPage(<%=currentPage-1 %>)">
									<span aria-hidden="true">«</span>
								</a>
							</li>
						<%
				    	}
						
				        for (int i = 0; i < pages; i++) {
				        	if ( i == (currentPage - 1) ) {
				        	%>
								<li class="page-item">
									<a class="page-link" style="color: black"><%= i+1 %></a>
								</li>
							<%
				           	} else {
				           	%>
								<li class="page-item">
									<a class="page-link" href="#" onclick="goToPage(<%=i+1%>)"><%= i+1 %></a>
								</li>
							<%
				        	}
				        }
				        
				        if (currentPage != pages) {
				        %>
							<li class="page-item">
								<a class="page-link" href="#" aria-label="Next" onclick="goToPage(<%=currentPage+1 %>)">
									<span aria-hidden="true">»</span>
								</a>
							</li>
						<%
				      	}
				      	%>
					</ul>
				</nav>
			</div>
			
			<hr>
			
			<div class="buttoncontainer">
				<button id="admin_checkreservationbutton" class="btn btn-primary" type="button">Check reservations</button>
				<button id="admin_checkunregreservationbutton" class="btn btn-primary" type="button">Check unregistered reservations</button>
				<button class="btn btn-primary" type="button" onclick='$("#communication_form").fadeIn(500);'>Send communication</button>
			</div>
		</div>
	</div>
	
	<script src="assets/bootstrap/js/bootstrap.min.js"></script>
	<script src="assets/js/admin.js"></script>
</body>

</html>

