function showerror(title, description){
	document.getElementById("alerttitle").innerHTML = title;
	document.getElementById("alertcontent").innerHTML = description;
	document.getElementById("alertscreen").style.display = "block";
}

$("#registerbuttonreg").click(
		function(){
			var email = document.getElementById("emailinputreg").value;
			var name = document.getElementById("nameinputreg").value;
			var surname = document.getElementById("surnameinputreg").value;
			var password1 = document.getElementById("passwordinputreg").value;
			var password2 = document.getElementById("passwordinputrepeatreg").value;
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
					showerror("You must compile all fields!",response.missingField + " is empty");
				}
				else if(response.type == "alreadyexists") {
					showerror("Account already exists","There is already an account with that email address in our servers");
				} 
				else if(response.type == "passwordsnotequals") {
					showerror("Passwords are not equals","Please retype your password!");
				} 
				else if(response.type == "success"){
					
				}
			},"json")
		}
);

