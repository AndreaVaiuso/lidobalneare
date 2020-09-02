function bookchair(chairname, price){
	let bookdate = $("#datefield").val();
	let booktimeslot = document.getElementById("timeslotSelect").selectedIndex + 1;
	showquery("Confirm your pass","You are going to buy a pass for the chair " + chairname + " that begins on " + bookdate + " for the duration of " + booktimeslot + " month(s)",function(){
		$.get("Customer?request=pass&chair="+chairname+"&start="+bookdate+"&duration="+booktimeslot,function(response){
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
	let booktimeslot = document.getElementById("timeslotSelect").selectedIndex;
	location.href = "pass.jsp?date="+$("#datefield").val()+"&timeslot="+booktimeslot;
}
