
$("#loginbutton").click(	// Login button click event handler.
	function(){
		var email = $("#emailinput").val(); 
		var password = $("#passwordinput").val();
		//console.log(email + " " + password);	// DEBUG
		var userdata = {
				"type" : "login",
				"email" : email,
				"password" : password,
		};
		
		//document.getElementById("ajaxloaderscreen").display = "block";
		$("#ajaxloaderscreen").toggle();
		
		$.post("LoginServlet",userdata,function(data){
			//document.getElementById("ajaxloaderscreen").display = "none";
			$("#ajaxloaderscreen").toggle();
			if(data.type == "loginError"){
				showerror("Login error","Your credentials do not match");
			}
			else if(data.type == "notActive"){
				showerror("Account not verified","Check your e-mail, your account must be verified before you can access!");
			}
			else if(data.type == "loginSuccess"){
				switch(data.role){
				case "admin":
					location.href = "adminPage.jsp";
					break;
				case "customer":
					location.href = "home.jsp"
					break;
				case "cook":
					break;
				case "ticket":
					break;
				case "lifeguard":
					break;
				case "info":
					break;
				default:
					location.href = "errorpage.html";
					break;
				}
			}
		},"json")
	}
);


$("#registerbutton").click(
		function(){
			location.href = "register.html";
		}
);
