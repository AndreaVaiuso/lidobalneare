$("#registerbuttonreg").click(
		function(){
			var email = document.getElementById("emailinputreg").value;
			var password = document.getElementById("passwordinputreg").value;
			var birthdate = $("#birthdatereg").val();
			
			console.log(username + " " + password);
			
			var userdata = {
					"type" : "register",
					"email" : email,
					"password" : password,
					"birthdate" : birthdate
			};
			
			$.post("RegisterServlet", userdata, "json");
		}
);