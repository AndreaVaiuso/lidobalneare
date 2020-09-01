$("#configurepaypalbtn").click(function(){
	$("#alerttitle").html("Change payment account");
	$("#alertcontent").html("<p style='display:block'>Insert your paypal account email to make payment, book seats and reservations:</p>" +
			"<hr>" +
			"<input id='paypal_input' placeholder='Paypal Account' style='width:100%'> </input>");
	$("#alertnobtn").show();
	$("#alertyesbtn").show();
	$("#alertnobtn").html("Cancel");
	$("#alertnobtn").click(function(){
		$("#alertscreen").fadeOut(500);
	})
	$("#alertyesbtn").click(function(){
		$.get("Admin?paypal="+$("#paypal_input").val(),function(data){
			if(data.type="success"){
				$("#alertscreen").fadeOut(500);
			} else {
				$("#alertscreen").fadeOut(500);
				showDefaultError();
			}
		});
		
	})
	$("#alertyesbtn").html("Apply");
	$("#alertscreen").fadeIn(500);
});

$("#reservationbtn").click(function(){
	location.href = "reservation.jsp?pass=no";
});

var qrcode = new QRCode(document.getElementById("qrcode"), "");

function bookingQr(code,email){
	let url = "http://localhost:8080/CheckPrenotation?type=booking&code="+code+"&email="+email;
	qrcode.clear();
	qrcode.makeCode(url);
	console.log("QR");
	$("#qrcodescreen").fadeIn(500);
}

function passQr(code,email){
	let url = "http://localhost:8080/CheckPrenotation?type=pass&code="+code+"&email="+email;
	qrcode.clear();
	qrcode.makeCode(url);
	console.log("QR");
	$("#qrcodescreen").fadeIn(500);
}