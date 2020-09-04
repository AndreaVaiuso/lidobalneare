function bookchair(chairname, price){
	let bookdate = $("#datefield").val();
	let booktimeslot = document.getElementById("timeslotSelect").selectedIndex;
	$("#price_label").html(price+"&euro;");
	$("#description_label").html("You are going to book chair " + chairname + " for " + bookdate + ". A payment module will be opened before");
	$("#paymentScreen").fadeIn(500);
	$("#paymentyesbtn").click(function(){
		$.get("Customer?request=book&chair="+chairname+"&date="+bookdate+"&timeslot="+booktimeslot,function(response){
			if(response.type=="success"){
				$("#paymentScreen").hide();
				showalert("Done","Your reservation has been created. We have sent an email with all details",function(){
					location.href = "home.jsp";
				});
			} else {
				$("#paymentScreen").hide();
				showDefaultError();
			}
		});
	});
	$("#paymentnobtn").click(function(){
		$("#paymentScreen").fadeOut(500);
	});
}

function updateLayout(){
	location.href = "reservation.jsp?date="+$("#datefield").val()+"&timeslot="+$("#timeslotSelect :selected").val();
}