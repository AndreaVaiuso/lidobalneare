function hideerror(){
	$("#alertscreen").fadeOut(500);
}

function showerror(title, description, func){
	$("#alerttitle").html(title);
	$("#alerttitle").css("color", "red");
	$("#alertcontent").html(description);
	$("#alertnobtn").hide();
	$("#alertyesbtn").html("Ok");
	if(func!=undefined) $("#alertyesbtn").click(func);
	else $("#alertyesbtn").click(function(){hideerror()});
	$("#alertscreen").fadeIn(500);
}

function showalert(title, description,okfunc){
	$("#alerttitle").html(title);
	$("#alerttitle").css("color", "black");
	$("#alertcontent").html(description);
	$("#alertnobtn").hide();
	$("#alertyesbtn").html("Ok");
	$("#alertyesbtn").click(function(){hideerror();if(okfunc!=null) okfunc.call();});
	$("#alertscreen").fadeIn(500);
}

function showquery(title, description,yesfunc,btntext){
	$("#alerttitle").html(title);
	$("#alerttitle").css("color", "black");
	$("#alertcontent").html(description);
	$("#alertyesbtn").click(yesfunc);
	if(btntext!=undefined) $("#alertyesbtn").html(btntext);
	else $("#alertyesbtn").html("Yes");
	$("#alertnobtn").click(function(){hideerror();});
	$("#alertscreen").fadeIn(500);
}

function showDefaultError(){
	showerror("Whoops!","Something went wrong! Please try again later!");
}
