var qrcode = new QRCode(document.getElementById("qrcode"), "");

function generateQr(){
	$("#alertinput").show();
	$("#alerttitle").html("Generate QR for table");
	$("#alertcontent").html("Insert table number and then click generate. Next, print your QR code and put it on your table.");
	$("#alertscreen").fadeIn(500);
	$("#alertyesbtn").html("Generate");
	$("#alertnobtn").html("Cancel");
	$("#alertyesbtn").click(function(){
		var num = $("#alertinput").val();
		let url = "http://localhost:8080/LidoBalneare/menu.jsp?table_number="+num;
		qrcode.clear();
		qrcode.makeCode(url);
		$("#qrcodescreen").fadeIn(500);
	});
	$("#alertnobtn").click(function(){
		hideerror();
	});
}

function completeOrder (table) {
	$.post("OrderServlet", table, function(response){
		if (response.type == "success") {
			location.href = "orders.jsp";
		} else {
			location.href = "errorpage.html";
		}
	}, "json")
}

$(document).ready(function(){
	var time = new Date().getTime();
	
	if (new Date().getTime() - time > 1000*3) {	// 1000*60*5 milliseconds = 5 minutes.
		window.location.reload(true);
	}
});