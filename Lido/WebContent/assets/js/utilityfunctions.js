function hideerror(){
	document.getElementById("alertscreen").style.display = "none";
}

function showerror(title, description){
	document.getElementById("alerttitle").innerHTML = title;
	document.getElementById("alerttitle").style.color = "red";
	document.getElementById("alertcontent").innerHTML = description;
	document.getElementById("alertnobtn").style.display = "none";
	document.getElementById("alertyesbtn").innerHTML = "Ok";
	document.getElementById("alertyesbtn").onclick = function(){hideerror()};
	document.getElementById("alertscreen").style.display = "block";
}

function showalert(title, description){
	document.getElementById("alerttitle").innerHTML = title;
	document.getElementById("alerttitle").style.color = "black";
	document.getElementById("alertcontent").innerHTML = description;
	document.getElementById("alertnobtn").style.display = "none";
	document.getElementById("alertyesbtn").innerHTML = "Ok";
	document.getElementById("alertyesbtn").onclick = function(){hideerror()};
	document.getElementById("alertscreen").style.display = "block";
}