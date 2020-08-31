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
function bookchair(chairname){
	let bookdate = $("#datefield").val();
	let booktimeslot = document.getElementById("timeslotSelect").selectedIndex;
	showquery("Confirm your booking","You are going to book chair " + chairname + " for " + bookdate + ". A payment module will be opened before",function(){
		$.get("Customer?request=book&chair="+chairname+"&date="+bookdate+"&timeslot="+booktimeslot,function(response){
			if(response.type=="success"){
				showalert("Done","Your reservation has been created. We have sent an email with all details",function(){
					location.href = "home.jsp";
				});
			} else {
				showDefaultError();
			}
		});
	}, "Proceed with payment");
}

function updateLayout(){
	location.href = "reservation.jsp?pass=no&date="+$("#datefield").val()+"&timeslot="+$("#timeslotSelect :selected").val();
}

urlParam = function(name){
	var results = new RegExp('[\?&]' + name + '=([^&#]*)').exec(window.location.href);
	if (results==null) {
		return null;
	}
	return decodeURI(results[1]) || 0;
}

var today = new Date();
var dd = today.getDate();
var mm = today.getMonth()+1;
var yyyy = today.getFullYear();

if(dd<10){
	dd='0'+dd
} 
if(mm<10){
	mm='0'+mm
} 
today = yyyy+'-'+mm+'-'+dd;
document.getElementById("datefield").setAttribute("min", today);
let sdate = urlParam("date");
if(sdate){
	document.getElementById("datefield").setAttribute("value", sdate);
} else document.getElementById("datefield").setAttribute("value", today);
let sts = urlParam("timeslot");
if(sts){
	document.getElementById("timeslotSelect").selectedIndex = sts;
}