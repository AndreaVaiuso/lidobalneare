function tableQr () {
	var num = $("#tablein").val();
	let url = "http://localhost:8080/LidoBalneare/menu.jsp?table_number="+num;
	qrcode.clear();
	qrcode.makeCode(url);
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