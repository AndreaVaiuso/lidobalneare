function showerror(title, description){
	$("#alerttitle").html(title);
	$("#alerttitle").css("color", "black");
	$("#alertcontent").html(description);
	$("#alertscreen").fadeIn(500);
}

function showalert(title, description){
	$("#alerttitle").html(title);
	$("#alerttitle").css("color", "black");
	$("#alertcontent").html(description);
	$("#alertscreen").fadeIn(500);
}

function showerror(){
	showerror("Whoops!","Something went wrong! Please try again later!");
}
