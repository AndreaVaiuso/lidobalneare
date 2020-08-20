function hideerror(){
	$("#alertscreen").fadeOut(500);
}

function showerror(title, description){
	$("#alerttitle").html(title);
	$("#alerttitle").css("color", "red");
	$("#alertcontent").html(description);
	$("#alertnobtn").hide();
	$("#alertyesbtn").html("Ok");
	$("#alertyesbtn").click(function(){hideerror()});
	$("#alertscreen").fadeIn(500);
}

function showalert(title, description){
	$("#alerttitle").html(title);
	$("#alerttitle").css("color", "black");
	$("#alertcontent").html(description);
	$("#alertnobtn").hide();
	$("#alertyesbtn").html("Ok");
	$("#alertyesbtn").click(function(){hideerror()});
	$("#alertscreen").fadeIn(500);
}
