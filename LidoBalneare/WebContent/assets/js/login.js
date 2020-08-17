$("#loginbutton").click(
		function(){
			var username = document.getElementById("emailinput").value;
			var password = document.getElementById("passwordinput").value;
			console.log(username + " " + password);
			var data = {
					"type":"login",
					"username":username,
					"password":password
			};
			$.post("LoginServlet",data,function(data,status,xhr){
				alert("Done");
			},"json")
		}
);

$("#registerbutton").click(
		function(){
			location.href = "register.html";
		}
);