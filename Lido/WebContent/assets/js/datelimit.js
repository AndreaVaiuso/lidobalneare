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