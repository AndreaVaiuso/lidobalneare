<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:useBean id="connecteduser" class="it.lidobalneare.bean.User" scope="session" />
<%
try{
	if(!connecteduser.isCustomer()){
		response.sendRedirect("./errorpage.html");
		return;
	}
} catch (NullPointerException e){
	response.sendRedirect("login.html");
	return;
}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

</body>
</html>