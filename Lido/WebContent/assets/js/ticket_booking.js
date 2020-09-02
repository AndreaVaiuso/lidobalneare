function bookchair(chairname, price){
	let bookdate = $("#datefield").val();
	$("#price_label").html(price+"&euro;");
	let booktimeslot = document.getElementById("timeslotSelect").selectedIndex;
	$("#paymentScreen").fadeIn(500);
	$("#paymentyesbtn").click(function(){
		let name = $("#name_field").val();
		let surname = $("#surname_field").val();
		$.get("Ticket?request=book&chair="+chairname+"&date="+bookdate+"&timeslot="+booktimeslot+"&name="+name+"&surname="+surname,function(response){
			if(response.type=="success"){
				$("#paymentScreen").hide();
				showalert("Done","Your reservation has been created",function(){
					bookingQr(response.code,name+" "+surname);
				});
			} else if(response.type=="fielderror") {
				$("#paymentScreen").hide();
				showerror("Error","Compile all fields!");
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
	location.href = "ticket.jsp?date="+$("#datefield").val()+"&timeslot="+$("#timeslotSelect :selected").val();
}

$("#verifyticketbtn").click(function(){
	window.open("qrscanner.html");
});

var qrcode = new QRCode(document.getElementById("qrcode"), "");

function bookingQr(code,email){
	email.replace(/\s/g,"%20");
	let url = "http://localhost:8080/LidoBalneare/CheckPrenotation?type=booking&code="+code+"&email="+email;
	qrcode.clear();
	qrcode.makeCode(url);
	console.log("QR");
	$("#qrcodescreen").fadeIn(500);
}