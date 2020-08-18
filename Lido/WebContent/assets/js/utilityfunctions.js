$(document).ready(function(){

	function hideerror(){
		$("#alertscreen").hide();
	}
	
	function showerror(title, description){
		$("#alerttitle").html(title);
		$("#alerttitle").color("red");
		$("#alertcontent").html(description);
		$("#alertnobtn").hide();
		$("#alertyesbtn").html("Ok");
		$("#alertyesbtn").click(hideerror());
		$("#alertscreen").display("block");
		/*
		document.getElementById("alerttitle").innerHTML = title;
		document.getElementById("alerttitle").style.color = "red";
		document.getElementById("alertcontent").innerHTML = description;
		document.getElementById("alertnobtn").style.display = "none";
		document.getElementById("alertyesbtn").innerHTML = "Ok";
		document.getElementById("alertyesbtn").onclick = function(){hideerror()};
		document.getElementById("alertscreen").style.display = "block";
		*/
	}
	
	function showalert(title, description){
		$("#alerttitle").html(title);
		$("#alerttitle").color("black");
		$("#alertcontent").html(description);
		$("#alertnobtn").hide();
		$("#alertyesbtn").html("Ok");
		$("#alertyesbtn").click(hideerror());
		$("#alertscreen").display("block");
		/*
		document.getElementById("alerttitle").innerHTML = title;
		document.getElementById("alerttitle").style.color = "black";
		document.getElementById("alertcontent").innerHTML = description;
		document.getElementById("alertnobtn").style.display = "none";
		document.getElementById("alertyesbtn").innerHTML = "Ok";
		document.getElementById("alertyesbtn").onclick = function(){hideerror()};
		document.getElementById("alertscreen").style.display = "block";
		*/
	}
	
});