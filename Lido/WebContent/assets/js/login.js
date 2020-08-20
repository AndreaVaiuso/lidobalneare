
function login(){
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
		else if(data.type == "notActive"){
			showerror("Account not verified","Check your e-mail, your account must be verified before you can access!");
		}
		else if(data.type == "success"){

		}
	},"json")
}

$("#loginbutton").click(	// Login button click event handler.
		function(){login();}
);

document.getElementById("passwordinput").addEventListener('keypress', function (e) {
    if (e.keyCode !== 13) {
        login();
    }
}, false);

$("#registerbutton").click(
		function(){
			location.href = "register.html";
		}
);
