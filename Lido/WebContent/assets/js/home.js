$("#configurepaypalbtn").click(function(){
	$("#alertinput").show();
	showalert("Change PayPal account","Configure your paypal account to make payments faster");
	$("#alertnobtn").show();
	$("#alertyesbtn").show();
	$("#alertnobtn").html("Cancel");
	$("#alertyesbtn").html("Apply");
	$("#alertnobtn").click(function(){
		$("#alertscreen").fadeOut(500);
	})
	$("#alertyesbtn").click(function(){
		let newpaypalemail = $("#alertinput").val();
		$("#alertscreen").fadeOut(500);
		$("#alertinput").hide();
		$.post("Customer?request=paypalchange&paypal="+newpaypalemail,function(data){
			if(data.type="success"){
				showalert("Done","Now you can make payment quickly with your PayPal account");
				$("#alertyesbtn").click(function(){location.reload()});
			} else {
				showDefaultError();
			}
		});
		
	})
	
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