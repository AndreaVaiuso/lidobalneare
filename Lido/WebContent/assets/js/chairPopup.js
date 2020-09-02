function showChairPopup(id) {
	var chair = document.getElementById(id);
	var popup = chair.firstElementChild;
	if(chair.offsetTop <= 80){
		popup.style.bottom = "-40px";
	} else popup.style.bottom = "40px";
	popup.style.display = "block";
}

function hideChairPopup(){
	var chairspopup = document.getElementsByClassName("chairpopup");
	for(let i = 0; i<chairspopup.length; i++){
		chairspopup[i].style.display = "none";
	}
}
