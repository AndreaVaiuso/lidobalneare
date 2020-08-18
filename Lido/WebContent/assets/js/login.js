
$("#loginbutton").click(	// Login button click event handler.
		function(){
			var email = $("#emailinput").val(); 
			var password = $("#passwordinput").val();
			console.log(email + " " + password);	// DEBUG
			var userdata = {
					"type" : "login",
					"email" : email,
					"password" : password,
			};
			$.post("LoginServlet",userdata,function(data,status,xhr){
				if(data.type == "loginError"){
					showerror("Login error","Your credentials do not match");
				}
				else if(data.type == "success"){

				}
			},"json")
		}
);

$("#registerbutton").click(
		function(){
			location.href = "register.html";
		}
);
