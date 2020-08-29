$("#loginbutton").click( function () {
	var email = $("#emailinput").val(); 
	var password = $("#passwordinput").val();
	var userdata = {
			"type" : "login",
			"email" : email,
			"password" : password,
	};
	
	$("#ajaxloaderscreen").toggle();
	
	$.post("LoginServlet", userdata, function (data) {
		$("#ajaxloaderscreen").toggle();
		
		if (data.type == "loginError") {
			showerror("Login error", "Your credentials do not match");
		} else if (data.type == "notActive") {
			showerror("Account not verified", "Check your e-mail, your account must be verified before you can access!");
		}
		else if (data.type == "loginSuccess") {
			switch (data.role) {
				case "admin":
					location.href = "adminPage.jsp";
					break;
				case "customer":
					location.href = "home.jsp";
					break;
				case "cook":
					location.href = "restaurantView.jsp";
					break;
				case "ticket":
					break;
				/*case "lifeguard":	// Stessa cosa di info
					break;*/
				case "info":
					break;
				default:
					location.href = "errorpage.html";
					break;
			}
		}
	},"json")
});


$("#registerbutton").click(	function () {
	location.href = "register.html";
});
