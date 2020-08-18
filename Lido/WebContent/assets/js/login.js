$("#loginbutton").click(	// Login button click handler.
		function(){
			var email = $("#emailinput").val(); 
			var password = $("#passwordinput").val();
			console.log(email + " " + password);	// DEBUG
			var userdata = {
					"type" : "login",
					"email" : email,
					"password" : password,
			};
			
			$.post("LoginServlet", userdata, function(data,status,xhr){
				alert("Done");
			},"json")
		}
);

$("#registerbutton").click(
		function(){
			location.href = "register.html";
		}
);