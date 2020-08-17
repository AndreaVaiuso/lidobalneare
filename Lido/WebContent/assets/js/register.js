$("#registerbuttonreg").click(
		function(){
			alert("Hello!!");
			var email = document.getElementById("emailinputreg").value;
			var password1 = document.getElementById("passwordinputreg").value;
			var password2 = document.getElementById("passwordinputrepeatreg").value;
			var birthdate = $("#birthdatereg").val();
			var gender;
			
			if ($("#male_check").attr("checked")) gender = "M";
			else if ($("#female_check").attr("checked")) gender = "F";
			else if ($("#other_check").attr("checked")) gender = "O";
			
			console.log(username + " " + password);
			
			var userdata = {
					"type" : "register",
					"email" : email,
					"password1" : password1,
					"password2" : password2,
					"birthdate" : birthdate,
					"gender" : gender,
			};
			
			$.post("RegisterServlet", userdata, "json");
		}
);

console.log("Hello");