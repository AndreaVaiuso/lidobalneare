<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<%@ page import="java.util.ArrayList"%>
<%@ page import="it.lidobalneare.bean.User"%>
<%@ page import="it.lidobalneare.db.DBConnect"%>
<% 
	try{
		User connecteduser = (User) session.getAttribute("user");
		if(!connecteduser.getRole().equals("admin")){
			System.out.println("NOT ADMIN!");
			response.sendRedirect("./errorpage.html");
			return;
		}
	} catch (NullPointerException e){
		System.out.println("Session deleted");
		response.sendRedirect("login.html");
		return;
	}
%>
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

		<%@include file="adminavbar.html"%>
		<script>
        	document.getElementById("nav_admin").firstChild().classList.add("active");
        </script>
		<div class="contentscreen">
			<span class="toptitle">Administration panel</span> <span
				class="logindescription"
				style="background-color: rgb(220, 220, 220);">If you want to
				manage your lido, you are in the right place.</span>
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
                        try{
                        	currentPage = Integer.valueOf(cp);
                        } catch(NumberFormatException e){
                        	currentPage = 1;
                        }
                        int itemsPerPage = 10;
                        
                        int firstElement = (currentPage-1)*itemsPerPage;
                        int lastElement = firstElement + itemsPerPage;
                        ArrayList<User> users = DBConnect.getUserList(firstElement,lastElement);
                        int userNumber = DBConnect.getUserNumber();
                        int pages = (int) Math.ceil(userNumber / itemsPerPage) + 1;
                        
                        try {
                        	for(int i = 0; i < users.size(); i++){
                        		User u = users.get(i);
                        %>
							<tr id="table_entry_<%=i%>"
								onclick="selectRow(<%=i%>,'<%=u.getEmail()%>')">
								<td><%= u.getEmail() %></td>
								<td><%= u.getName() %></td>
								<td><%= u.getSurname() %></td>
								<td><%= u.getGender() %></td>
								<td><%= u.getBirthdate() %></td>
							</tr>
							<% 
                        	}
                        } catch (java.lang.IndexOutOfBoundsException e) {}
                        %>

							<!-- 
                            <tr>
                                <td>name1.surname.@server.com</td>
                                <td>Andrea</td>
                                <td>Vaiuso</td>
                                <td>25</td>
                                <td>5/04/1995</td>
                            </tr>
                            <tr class="tablerowselected">
                                <td>name2.surname.@server.com</td>
                                <td>Carlo</td>
                                <td>Galatolo</td>
                                <td>24</td>
                                <td>13/04/1996</td>
                            </tr>
                        -->
						</tbody>
					</table>
				</div>
				<nav>
					<ul class="pagination">
						<% 
                    	if(currentPage != 1){
                    		%>
						<li class="page-item"><a class="page-link" href="#"
							aria-label="Previous" onclick="goToPage(<%=currentPage-1 %>)"><span
								aria-hidden="true">«</span></a></li>
						<%
                    	}

                        for(int i=0;i<pages;i++){
                        	if(i==(currentPage-1)){
                        	%>
						<li class="page-item"><a class="page-link"
							style="color: black"><%= i+1 %></a></li>
						<%
                        	} else {
                        	%>
						<li class="page-item"><a class="page-link" href="#"
							onclick="goToPage(<%=i+1%>)"><%= i+1 %></a></li>
						<%
                        	}
                        }
                        if(currentPage != pages){
                        %>
						<li class="page-item"><a class="page-link" href="#"
							aria-label="Next" onclick="goToPage(<%=currentPage+1 %>)"><span
								aria-hidden="true">»</span></a></li>
						<%
                    	}
                    	%>
					</ul>
				</nav>
			</div>
			<hr>
			<div class="buttoncontainer">
				<button id="admin_subscriptioninfobutton" class="btn btn-primary"
					type="button">See subscription informations</button>
				<button id="admin_checkprenotationbutton" class="btn btn-primary" type="button">Check prenotations</button>
			</div>
		</div>
	</div>
	<script src="assets/js/jquery.min.js"></script>
	<script src="assets/js/admin.js"></script>
	<script src="assets/bootstrap/js/bootstrap.min.js"></script>
</body>

</html>

