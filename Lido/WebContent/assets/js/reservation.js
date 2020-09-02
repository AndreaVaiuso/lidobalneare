function bookchair(chairname, price){
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
	location.href = "reservation.jsp?date="+$("#datefield").val()+"&timeslot="+$("#timeslotSelect :selected").val();
}