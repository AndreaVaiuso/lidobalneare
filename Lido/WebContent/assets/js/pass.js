function bookchair(chairname, price){
	
	let bookdate = $("#datefield").val();
	let booktimeslot = document.getElementById("timeslotSelect").selectedIndex + 1;
	$("#price_label").html(price+"&euro;");
	$("#description_label").html("You are going to buy a pass for the chair " + chairname + " that begins on " + bookdate + " for the duration of " + booktimeslot + " month(s)");
	$("#paymentScreen").fadeIn(500);
	$("#paymentyesbtn").click(function(){
		$.get("Customer?request=pass&chair="+chairname+"&start="+bookdate+"&duration="+booktimeslot,function(response){
			if(response.type=="success"){
				showalert("Done","Your reservation has been created. We have sent an email with all details",function(){
					location.href = "home.jsp";
				});
			} else {
				showDefaultError();
			}
		});
	});
	$("#paymentnobtn").click(function(){
		$("#paymentScreen").fadeOut(500);
	});
}

function updateLayout(){
	let booktimeslot = document.getElementById("timeslotSelect").selectedIndex;
	location.href = "pass.jsp?date="+$("#datefield").val()+"&timeslot="+booktimeslot;
}
