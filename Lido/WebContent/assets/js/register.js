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
			console.log("Sending ajax");
			$.post("RegisterServlet",userdata,function(data,status,xhr){
				alert("Done");
			},"json")
		}
);

console.log("Hello");