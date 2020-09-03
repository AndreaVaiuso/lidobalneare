function tableQr(){
	var num = $("#tablein").val();
	let url = "http://localhost:8080/LidoBalneare/menu.jsp?table_number="+num;
	qrcode.clear();
	qrcode.makeCode(url);
}