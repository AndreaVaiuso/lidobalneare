<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<% 
	if (request.getParameter("logout") == "logout") {
		session.invalidate();
		response.sendRedirect("login.html");
	}
%>

<nav class="navbar navbar-light navbar-expand-md lidonavbar">
	<div class="container-fluid">
		<a class="navbar-brand" href="#">Lido Logo</a>
		<button class="navbar-toggler" data-toggle="collapse" data-target="#navcol-1">
			<span class="sr-only">Toggle navigation</span>
			<span class="navbar-toggler-icon"></span>
		</button>

		<!-- IMPORTANT: on importing, remember to set the active link using
		getElementById("LI_ELEMENT_ID").firstChild().classList.add("active"); -->

		<div id="navcol-1" class="collapse navbar-collapse">
			<ul class="nav navbar-nav">
				<li id="nav_admin" class="nav-item" role="presentation">
					<a class="nav-link" href="adminPage.jsp">Administration panel</a>
				</li>
				
				<li id="nav_layout" class="nav-item" role="presentation">
					<a class="nav-link"	href="layoutEditor.jsp">Lido layout editor</a>
				</li>
				
				<li  id ="nav_communications" class="nav-item" role="presentation">
					<a class="nav-link"	href="communications.jsp">Communications</a>
				</li>
				
				<li id = "nav_logout" class="nav-item" role="presentation">
					<a class="nav-link"	onclick="logout()">Logout</a>
				</li>
			</ul>
		</div>
	</div>
	
	<script>
		function logout() {
			$.get("adminavbar.jsp?logout=logout");
		}
	</script>
</nav>