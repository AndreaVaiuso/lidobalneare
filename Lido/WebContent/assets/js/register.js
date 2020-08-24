
$("#registerbuttonreg").click(
		function(){
			var email = $("#emailinputreg").val();
			var name = $("#nameinputreg").val();
			var surname = $("#surnameinputreg").val();
			var password1 = $("#passwordinputreg").val();
			var password2 = $("#passwordinputrepeatreg").val();
			var birthdate = $("#birthdatereg").val();
			var gender;

			if ($("#male_check").attr("checked")) gender = "M";
			else if ($("#female_check").attr("checked")) gender = "F";
			else if ($("#other_check").attr("checked")) gender = "O";

			var userdata = {
					"type" : "register",
					"email" : email,
					"name" : name,
					"surname" : surname,
					"password1" : password1,
					"password2" : password2,
					"birthdate" : birthdate,
					"gender" : gender,
			};
			$.post("RegisterServlet",userdata,function(data,status,xhr){
				response = data;
				if(response.type == "typerror"){
					showerror("You must compile all fields!",response.missingField + " field is empty");
				}
				else if(response.type == "alreadyexists") {
					showerror("Account already exists","There is already an account with that email address in our servers");
				} 
				else if(response.type == "passwordsnotequals") {
					showerror("Passwords are not equals","Please retype your password!");
				} 
				else if(response.type == "success"){
					showalert("Registration successful!","You will recieve a confirmation email soon.");
					$("#alertyesbtn").click(function(){
						location.href = "login.html";
					});
				}
			},"json")
		}
);
