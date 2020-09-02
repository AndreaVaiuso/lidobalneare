function tableQr(){
	var num = $("#tablein").val();
	let url = "http://localhost:8080/LidoBalneare/MenuServlet?type=tableqr&num="+num;
	qrcode.clear();
	qrcode.makeCode(url);
}