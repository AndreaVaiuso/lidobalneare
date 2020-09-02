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
		$.get("Customer?request=paypalchange&paypal="+$("#paypal_input").val(),function(data){
			if(data.type="success"){
				$("#alertscreen").fadeOut(500);
				showalert("Done","Now you can make payment quickly with your PayPal account");
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

// Used in booking.jsp
function bookingQr(code,email){
	let url = "http://localhost:8080/LidoBalneare/CheckPrenotation?type=booking&code="+code+"&email="+email;
	qrcode.clear();
	qrcode.makeCode(url);
	console.log("QR");
	$("#qrcodescreen").fadeIn(500);
}

// Used in pass.jsp
function passQr(code,email){
	let url = "http://localhost:8080/LidoBalneare/CheckPrenotation?type=pass&code="+code+"&email="+email;
	qrcode.clear();
	qrcode.makeCode(url);
	console.log("QR");
	$("#qrcodescreen").fadeIn(500);
}