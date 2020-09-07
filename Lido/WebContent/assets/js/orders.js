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
	$.get("OrderServlet?table="+table, function(response){
		if (response.type == "success") {
			location.href = "orders.jsp";
		} else {
			showDefaultError();
		}
	}, "json")
}

$(document).ready(function(){
	// Reloads the entire page every chosen period of time, so that the orders can be reloaded. 
	// The timer is reset if in input is detected.
	var time = new Date().getTime();
	
	$(document.body).bind("mousemove keypress", function() {
         time = new Date().getTime();
     });

	if (new Date().getTime() - time > 1000*60*10) {	
		window.location.reload(true);
	}
	
	function refresh() {
         if(new Date().getTime() - time >= 1000*60*10)
             window.location.reload(true);
         else 
             setTimeout(refresh, 10000);
     }

     setTimeout(refresh, 10000);	// Set timeout every 10 seconds.
});