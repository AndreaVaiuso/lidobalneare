function showChairPopup(id) {
	var chair = document.getElementById(id);
	var popup = chair.firstElementChild;
	if(chair.offsetTop <= 80){
		popup.style.bottom = "-70px";
	} else popup.style.bottom = "70px";
	popup.style.display = "block";
}

function hideChairPopup(){
	var chairs = document.getElementsByClassName("chair");
	for(let i = 0; i<chairs.length; i++){
		chairs[i].firstElementChild.style.display = "none";
	}
}

function bookchair(chairname){
	$.get("CustomerServlet?bookchair="+chairname,function(response){
		
	});
}

function updateLayout(){
	location.href = "reservation.jsp?date="+$("#datefield").val()+"&timeslot="+$("#timeslotSelect :selected").val();
}